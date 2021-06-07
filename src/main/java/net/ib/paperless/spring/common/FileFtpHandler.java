package net.ib.paperless.spring.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.clipsoft.org.json.simple.JSONObject;

import net.ib.paperless.spring.domain.FtpFile;
import net.ib.paperless.spring.domain.JsonFile;

public class FileFtpHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileFtpHandler.class);
	private static InputStream inputStream;
	private static FTPClient client = null;
    private static FileOutputStream fos;

	/**
	 * @param loanInfoEntity
	 */
	public static boolean fileUploader(FtpFile entity) {
		boolean boo = false;
		try {
			client = new FTPClient();
			client.connect(entity.getFtpIp());
			if (!FTPReply.isPositiveCompletion(client.getReplyCode())) {
				client.disconnect();
				LOGGER.error("FTP서버 응답 비정상!");
				boo = false;
			}else{ 

				if (!client.login(entity.getFtpId(), entity.getFtpPwd())) {
					client.disconnect();
					LOGGER.error("FTP서버 로그인 실패!");
					boo = false;
				}else{

					// directory 생성
					client.enterLocalPassiveMode();
					client.setFileType(FTP.BINARY_FILE_TYPE);
					client.setControlEncoding("EUC-KR");
					client.setSoTimeout(5000);

					makeDirectory(entity.getFtpFilePath());
					System.out.println("entity.getFtpFilePath() : " + entity.getFtpFilePath());
					System.out.println("entity.getFtpFileType() : " + entity.getFtpFileType());
					//Local , Multipart 파일 구분
					if(entity.getFtpFileType().equals("local")){
						inputStream = new FileInputStream(entity.getLocalPath());	
						fileLocalDelete(entity.getLocalPath());
					}else if(entity.getFtpFileType().equals("multipart")){
						inputStream = entity.getFile().getInputStream();
					}

					System.out.println("entity.getFtpFileFullPath() : " + entity.getFtpFileFullPath());
					if(client.storeFile(entity.getFtpFileFullPath(), inputStream)){
						System.out.println("obj555555555 ");
						boo = true;
					}else{
						boo = false;
					}
					inputStream.close();
					client.logout();
				}
			}

		} catch (Exception e) {
			LOGGER.info("해당 ftp 로그인 실패하였습니다.");
			e.printStackTrace();
			boo = false;
		} finally {
			if (client != null && client.isConnected()) {
				try {
					client.disconnect();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}

		return boo;
	}

	public static JsonFile ftpReadData(FtpFile entity){
		try{
			client = new FTPClient();
			client.setControlEncoding("EUC-KR");
			client.connect(entity.getFtpIp(),entity.getFtpPort());
			if(FTPReply.isPositiveCompletion(client.getReplyCode())==false){
				client.disconnect();
				throw new Exception("FTP Server connect error...");
			}else{
				client.setSoTimeout(5000);
				if(client.login(entity.getFtpId(), entity.getFtpPwd())==false){
					throw new Exception("FTP Server Login error...");
				}
				client.enterRemotePassiveMode();
				client.enterLocalPassiveMode();
				client.setFileType(FTP.BINARY_FILE_TYPE);
				client.changeWorkingDirectory(entity.getFtpFilePath());
				inputStream = client.retrieveFileStream(entity.getFileName());
				StringBuffer sb = new StringBuffer();
			     byte[] b = new byte[4096];
			     for (int n; (n = inputStream.read(b)) != -1;) {
			         sb.append(new String(b, 0, n));
			     }
			     JsonFile jsonFile = new ObjectMapper().readValue(sb.toString(), JsonFile.class) ;
			     return jsonFile;
			}
		}catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
	public static JsonFile ftpReadData_test(FtpFile entity){
		try{
			client = new FTPClient();
			client.setControlEncoding("UTF-8");
			client.connect(entity.getFtpIp(),entity.getFtpPort());
			if(FTPReply.isPositiveCompletion(client.getReplyCode())==false){
				client.disconnect();
				throw new Exception("FTP Server connect error...");
			}else{
				client.setSoTimeout(5000);
				if(client.login(entity.getFtpId(), entity.getFtpPwd())==false){
					throw new Exception("FTP Server Login error...");
				}
				client.enterRemotePassiveMode();
				client.enterLocalPassiveMode();
				client.setFileType(FTP.BINARY_FILE_TYPE);
				client.changeWorkingDirectory(entity.getFtpFilePath());
				inputStream = client.retrieveFileStream(entity.getFileName());
				StringBuffer sb = new StringBuffer();
			     byte[] b = new byte[4096];
			     for (int n; (n = inputStream.read(b)) != -1;) {
			         sb.append(new String(b, 0, n));
			     }
			     JsonFile jsonFile = new ObjectMapper().readValue(sb.toString(), JsonFile.class) ;
			     return jsonFile;
			}
		}catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
	/**
	 * @param obj
	 * @param path
	 */
	public static boolean jsonFileLocalMake(JSONObject obj, String path) {
		try {
			FileWriter file = new FileWriter(path);
			file.write(obj.toJSONString());
			file.flush();
			file.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void fileLocalDelete(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}

	public static void makeDirectory(String ftpFilePath) throws IOException {
		String[] dirArr = ftpFilePath.split("/");
		String saveLoc = "";

		for (int i = 0; i < dirArr.length; i++) {
			saveLoc = saveLoc + dirArr[i] + "/";
			client.makeDirectory(saveLoc);
		}
	}
	
	public static boolean writeFile(MultipartFile file, String path, String fileName){
        try{
        	File f = new File(path);
        	f.mkdirs();
            byte fileData[] = file.getBytes();
            fos = new FileOutputStream(path + "\\" + fileName);
            fos.write(fileData);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally{
            if(fos != null){
                try{
                    fos.close();
                }catch(Exception e){}
                 
                }
        }// try end;
         
    }// wirteFile() end;
}