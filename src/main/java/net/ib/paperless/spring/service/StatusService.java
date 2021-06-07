package net.ib.paperless.spring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clipsoft.org.json.simple.JSONObject;
import com.clipsoft.org.json.simple.parser.ParseException;

import net.ib.paperless.spring.common.DateUtil;
import net.ib.paperless.spring.common.FileFtpHandler;
import net.ib.paperless.spring.common.UUIDs;
import net.ib.paperless.spring.domain.AdminInfo;
import net.ib.paperless.spring.domain.EformAttach;
import net.ib.paperless.spring.domain.EformInfo;
import net.ib.paperless.spring.domain.EformUserData;
import net.ib.paperless.spring.domain.FtpFile;
import net.ib.paperless.spring.domain.JsonFile;
import net.ib.paperless.spring.domain.LoanInfo;
import net.ib.paperless.spring.domain.Progress;
import net.ib.paperless.spring.domain.ProgressStatus;
import net.ib.paperless.spring.domain.Status;
import net.ib.paperless.spring.repository.StatusRepository;

@Service
public class StatusService {
	private String localPath = "C:\\temp\\";
	
	@Autowired
	StatusRepository statusRepository;
	
	public List<Status> counselSelectList(Map params){
		int pageSize = 0;
		int pageNo = 0;
		int pagePoint = 0;
		
		if(params.get("pageSize") == null) pageSize = 20;
		else pageSize = Integer.parseInt(params.get("pageSize").toString());
		
		if(params.get("pageNo") == null) pageNo = 1;
		else pageNo = Integer.parseInt(params.get("pageNo").toString());
		
		pagePoint = (pageNo - 1) * pageSize;
		
		params.put("pageSize", pageSize);
		params.put("pageNo", pageNo);
		params.put("pagePoint", pagePoint);
		//System.out.println("params : " + params);
		return statusRepository.counselSelectList(params);
	}
	
	public Status counselSelectOne(String progressId){
		return statusRepository.counselSelectOne(progressId);
	}
	
	public int	progressUpdate(Map<String,Object> map){
		return statusRepository.progressUpdate(map);
	}
	
	public List<Status> statusCodeSelectList(){
		return statusRepository.statusCodeSelectList();
	}
	
	public List<Status> progressNoteSelectList(String progressId){
		return statusRepository.progressNoteSelectList(progressId);
	}
	
	public int progressNoteInsert(Map map){
		return statusRepository.progressNoteInsert(map);
	}
	
	public int progressStatusUpdate(Map map){
		return statusRepository.progressStatusUpdate(map);
	}
	
	public List<EformInfo> eformInfoSelectList(String load_id){
		return statusRepository.eformInfoSelectList(load_id);
	}
	
	public List<EformAttach> eformAttachSelectList(String progressId){
		return statusRepository.eformAttachSelectList(progressId);
	}

	@Transactional
	public boolean attachProgressUpdate(Map map) throws ParseException{
		boolean boo = true;
		boolean boo1 = true;
		
		if(map.get("eform_attachs") != null){
			List<Object> list = (List<Object>) map.get("eform_attachs");
			if(list.size() > 0){
				boo = true;
				for(Object s : list){
					Map<String,Object> o = (Map<String, Object>) s;
					o.put("progress_id", map.get("progressId"));
					//o.put("eform_attach_id", o.get("seq"));
					//System.out.println("nMap: " + o);
					if(o.get("admin_confirm_yn").toString() == "2")
						boo1 = false;
					if(statusRepository.attachProgressInsert(o) <= 0){
						boo = false;
						break;
					}
				}
				
				if(boo1){
					Map<String,Object> m = new HashMap<String,Object>();
					m.put("progressId", map.get("progressId"));
					m.put("attach_yn", 1);
					
					if(statusRepository.progressStatusUpdate(m) <= 0 ) boo = false;
				}
					
				
			}
		}
		if(statusRepository.progressUpdate(map) <= 0) boo = false;
		if(map.get("body") != null && !map.get("body").equals("")){
			if(statusRepository.progressNoteInsert(map) <= 0) boo = false;	
		}
		
		return boo;
	}
	
	/**
	 * @param phoneNumber
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Map<String,Object> setLoanUserInfo(Map<String, Object> map){
		Map<String, Object> retMap = new HashMap<String, Object>();
		try{
			if (map.get("tel_number")==null || map.get("loan_id")==null || map.get("require_amount")==null || map.get("user_name")==null) {
				retMap.put("result",false);
				retMap.put("msg","parameters error.");
				return retMap;
			}
			//String localPath = "C:\\temp\\";
			String telNumber = map.get("tel_number").toString();
			String currentDate = DateUtil.getCurrentDateString();
			String loanId = map.get("loan_id").toString();
			String userName = map.get("user_name").toString();
			//String callStartTime = map.get("call_start_time").toString();
			//String callEndTime = map.get("call_end_time").toString();
			String uuids = UUIDs.createNameUUID(telNumber.getBytes()).toString();
			String requireAmount =  map.get("require_amount").toString();
			String adminId = map.get("admin_id").toString();
			String eformId = map.get("eform_id").toString();
			String jumin = map.get("jumin")==null?"":map.get("jumin").toString();
			String bank = map.get("bank")==null?"":map.get("bank").toString();
			String account = map.get("account_number")==null?"":map.get("account_number").toString();
			
			LoanInfo loanInfoVo = loanInfoSelectOne(loanId);
			if(loanInfoVo == null){
				retMap.put("result",false);
				retMap.put("msg","loanInfo Data not find.");
				return retMap;
			}

			//progress 생성
			Progress progressEntity = new Progress();
			progressEntity.setLoan_id(loanId);
			progressEntity.setEform_id(eformId);
			progressEntity.setUser_name(userName.substring(0,2));
			progressEntity.setTel_number(telNumber.substring(telNumber.length()-4,telNumber.length()));
			progressEntity.setRequire_amount(requireAmount);
			progressEntity.setAdmin_id(adminId);
			progressEntity.setProgress_status(1);
			progressEntity.setUser_key(uuids);
			//progressEntity.setCall_start_time(callStartTime);
			//progressEntity.setCall_end_time(callEndTime);
			
			if(statusRepository.progressInsert(progressEntity) == 0){
				retMap.put("result",false);
				retMap.put("msg"," failed make progress.");
				return retMap;
			}

			ProgressStatus progressStatusEntity = new ProgressStatus();
			progressStatusEntity.setProgress_id(progressEntity.getLoan_id() + progressEntity.getSeq());
			if(statusRepository.progressStatusInsert(progressStatusEntity) == 0){
				retMap.put("result",false);
				retMap.put("msg"," failed make progressStatus.");
				return retMap;
			}
			
			// json 파일 생성
			JSONObject obj = new JSONObject();
			// 데이터세팅
			obj.put("progress_id", progressStatusEntity.getProgress_id());
			obj.put("name", userName);
			obj.put("tel", telNumber);
			obj.put("reg", currentDate);
			obj.put("jumin", jumin);
			obj.put("bank", bank);
			obj.put("account", account);
			obj.put("account_regdate", "");
			obj.put("account_holder_name", "");
			obj.put("account_holder_number", "");

			FtpFile ftpFileVo = new FtpFile();
			ftpFileVo.setFtpIp(loanInfoVo.getFtp_ip());
			ftpFileVo.setFtpFilePath(loanInfoVo.getFtp_base_pass() + "/" + currentDate);
			ftpFileVo.setFileName(uuids + "_" + progressEntity.getSeq() + ".json");
			ftpFileVo.setFtpFileFullPath(ftpFileVo.getFtpFilePath() + "/" + ftpFileVo.getFileName());
			ftpFileVo.setFtpId(loanInfoVo.getFtp_id());
			ftpFileVo.setFtpPwd(loanInfoVo.getFtp_pwd());
			ftpFileVo.setLocalPath(localPath + uuids + ".json");
			ftpFileVo.setFtpPort(loanInfoVo.getFtp_port());
			ftpFileVo.setFtpFileType("local");
			
			if(!FileFtpHandler.jsonFileLocalMake(obj, ftpFileVo.getLocalPath())){
				retMap.put("result",false);
				retMap.put("msg"," failed make json file.");
				return retMap;
			}
			
			if(FileFtpHandler.fileUploader(ftpFileVo)){
				//FileFtpHandler.ftpReadData();
				retMap.put("result",true);
				retMap.put("entity",progressStatusEntity);
				retMap.put("msg","등록되었습니다.");
				//System.out.println("obj3 : " + obj);
				return retMap;	
			}else{
				retMap.put("result",false);
				retMap.put("msg"," failed upload file.");
				//System.out.println("obj4 : " + obj);
				return retMap;
			}

		}catch(Exception e){
			retMap.put("result",false);
			retMap.put("msg",e);
			return retMap;
		}
	}
	
	public Map<String,Object> jsonFileUpdate(JsonFile jsonFile , Map<String,Object> map){
		Map<String,Object> retMap = new HashMap<String,Object>();
		//-------------------
		Status status = statusRepository.counselSelectOne(map.get("progress_id").toString());
		// json 파일 생성
		JSONObject obj = new JSONObject();
		// 데이터세팅
		obj.put("progress_id", map.get("progress_id").toString());
		obj.put("name", map.get("user_name").toString());
		obj.put("tel", jsonFile.getTel());
		obj.put("reg", jsonFile.getReg());
		obj.put("jumin", map.get("jumin").toString());
		obj.put("bank", map.get("bank").toString());
		obj.put("account", map.get("account_number").toString());
		obj.put("account_yn", jsonFile.getAccount_yn());
		obj.put("account_holder_name", jsonFile.getAccount_holder_name());
		obj.put("account_holder_number", jsonFile.getAccount_holder_number());
		
		LoanInfo loanInfoVo = loanInfoSelectOne(map.get("loan_id").toString());
		if(loanInfoVo == null){
			retMap.put("result",false);
			retMap.put("msg","loanInfo Data not find.");
			return retMap;
		}
		FtpFile ftpFileVo = new FtpFile();
		ftpFileVo.setFtpIp(loanInfoVo.getFtp_ip());
		ftpFileVo.setFtpFilePath(loanInfoVo.getFtp_base_pass() + "/" + jsonFile.getReg());
		ftpFileVo.setFileName(status.getUser_key() + "_" + status.getSeq() + ".json");
		ftpFileVo.setFtpFileFullPath(ftpFileVo.getFtpFilePath() + "/" + ftpFileVo.getFileName());
		ftpFileVo.setFtpId(loanInfoVo.getFtp_id());
		ftpFileVo.setFtpPwd(loanInfoVo.getFtp_pwd());
		ftpFileVo.setLocalPath(localPath + status.getUser_key() + ".json");
		ftpFileVo.setFtpPort(loanInfoVo.getFtp_port());
		ftpFileVo.setFtpFileType("local");
		
		if(!FileFtpHandler.jsonFileLocalMake(obj, ftpFileVo.getLocalPath())){
			retMap.put("result",false);
			retMap.put("msg"," failed make json file.");
			return retMap;
		}
		
		if(FileFtpHandler.fileUploader(ftpFileVo)){
			//FileFtpHandler.ftpReadData();
			retMap.put("result",true);
			//retMap.put("entity",progressStatusEntity);
			retMap.put("msg","등록되었습니다.");
			return retMap;
		}else{
			retMap.put("result",false);
			retMap.put("msg"," failed upload file.");
			return retMap;
		}
	}
	

	/**
	 * @param loanId
	 * @return
	 */
	public LoanInfo loanInfoSelectOne(String loanId) {
		return statusRepository.loanInfoSelectOne(loanId);
	}
	
	public int insertEformUserData(Map<String , Object> params){
		return statusRepository.insertEformUserData(params);
	}
	
	public int editEformUserData(Map<String , Object> params){
		int ret = statusRepository.editEformUserData(params);
		if(ret > 0 ){
			params.put("eform_yn", 0);
			statusRepository.progressStatusUpdate(params);
		}
		return ret;
	}
	

	public JsonFile getFtpJsonData(String progressId){
		//String loanId , String uuids , int seq , String regDate
		Status progress = statusRepository.counselSelectOne(progressId);
		
		LoanInfo loanInfoVo = statusRepository.loanInfoSelectOne(progress.getLoan_id());
		
		if(loanInfoVo != null){
			FtpFile ftpFileVo = new FtpFile();
			ftpFileVo.setFtpIp(loanInfoVo.getFtp_ip());
			ftpFileVo.setFtpFilePath(loanInfoVo.getFtp_base_pass() + "/" + progress.getReg_date().replaceAll("-", "").substring(0,8));
			ftpFileVo.setFileName(progress.getUser_key() + "_" + progress.getSeq() + ".json");
			ftpFileVo.setFtpFileFullPath(ftpFileVo.getFtpFilePath() + "/" + ftpFileVo.getFileName());
			ftpFileVo.setFtpId(loanInfoVo.getFtp_id());
			ftpFileVo.setFtpPwd(loanInfoVo.getFtp_pwd());
			ftpFileVo.setLocalPath(localPath + progress.getUser_key() + ".json");
			ftpFileVo.setFtpPort(loanInfoVo.getFtp_port());
			//System.out.println("FileFtpHandler_test : "+  FileFtpHandler.ftpReadData_test(ftpFileVo));
			return FileFtpHandler.ftpReadData(ftpFileVo);
		}
		return null;
	}


	/**
	 * @param progressId
	 * @return
	 */
	public EformUserData eformUserDataSelectOne(String progressId) {
		return statusRepository.eformUserDataSelectOne(progressId);
	}
	

	/**
	 * @param progressId
	 * @return
	 */
	public List<AdminInfo> adminInfoSelectList(Map params) {
		int pageSize = 0;
		int pageNo = 0;
		int pagePoint = 0;
		
		if(params.get("pageSize") == null) pageSize = 20;
		else pageSize = Integer.parseInt(params.get("pageSize").toString());
		
		if(params.get("pageNo") == null) pageNo = 1;
		else pageNo = Integer.parseInt(params.get("pageNo").toString());
		
		pagePoint = (pageNo - 1) * pageSize;
		
		params.put("pageSize", pageSize);
		params.put("pageNo", pageNo);
		params.put("pagePoint", pagePoint);
		
		return statusRepository.adminInfoSelectList(params);
	}
	
	public int adminInfoUpdate(Map<String , Object> params){
		return statusRepository.adminInfoUpdate(params);
	}

	public int adminInfoInsert(Map<String , Object> params){
		return statusRepository.adminInfoInsert(params);
	}
	

	/**
	 * @param id
	 * @return
	 */
	public int adminInfoSelectCnt(String id) {
		return statusRepository.adminInfoSelectCnt(id);
	}
	
	public int eformDelete(Map<String , Object> params){

		//System.out.println("params : " + params.get("adminInfoDel"));
		List<String> list = (List<String>)params.get("adminInfoDel");
		//System.out.println("list : " + list);
		for(String i : list){
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("seq", Integer.parseInt(i));
			m.put("use_yn", 0);
			//System.out.println("m : " + m);
			statusRepository.adminInfoUpdate(m);
			m.clear();
		}
		return 1;
		//return statusRepository.adminInfoUpdate(params);
	}
}