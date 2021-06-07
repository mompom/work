package net.ib.paperless.spring.domain;

import org.springframework.web.multipart.MultipartFile;

public class FtpFile{
	private String fileName;
	
	private String ftpIp;
	private String ftpFilePath;
	private String ftpFileFullPath;
	private String ftpId;
	private String ftpPwd;
	private int	ftpPort;
	private String ftpFileType;
	private MultipartFile file;
	
	private String localPath;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFtpIp() {
		return ftpIp;
	}

	public void setFtpIp(String ftpIp) {
		this.ftpIp = ftpIp;
	}

	public String getFtpFilePath() {
		return ftpFilePath;
	}

	public void setFtpFilePath(String ftpFilePath) {
		this.ftpFilePath = ftpFilePath;
	}

	public String getFtpFileFullPath() {
		return ftpFileFullPath;
	}

	public void setFtpFileFullPath(String ftpFileFullPath) {
		this.ftpFileFullPath = ftpFileFullPath;
	}

	public String getFtpId() {
		return ftpId;
	}

	public void setFtpId(String ftpId) {
		this.ftpId = ftpId;
	}

	public String getFtpPwd() {
		return ftpPwd;
	}

	public void setFtpPwd(String ftpPwd) {
		this.ftpPwd = ftpPwd;
	}

	public int getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(int ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getFtpFileType() {
		return ftpFileType;
	}

	public void setFtpFileType(String ftpFileType) {
		this.ftpFileType = ftpFileType;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}