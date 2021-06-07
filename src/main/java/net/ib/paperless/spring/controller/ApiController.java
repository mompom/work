package net.ib.paperless.spring.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.clipsoft.org.json.simple.parser.ParseException;

import net.ib.paperless.spring.common.ApiResponse;
import net.ib.paperless.spring.common.FileFtpHandler;
import net.ib.paperless.spring.domain.AccountAuthHistoryInfo;
import net.ib.paperless.spring.domain.AdminInfo;
import net.ib.paperless.spring.domain.BankInfo;
import net.ib.paperless.spring.domain.BlockUserInfo;
import net.ib.paperless.spring.domain.CompanyInfo;
import net.ib.paperless.spring.domain.CustomerChargeInfo;
import net.ib.paperless.spring.domain.CustomerServiceInfo;
import net.ib.paperless.spring.domain.DomainInfo;
import net.ib.paperless.spring.domain.EformAttach;
import net.ib.paperless.spring.domain.EformInfo;
import net.ib.paperless.spring.domain.EformUserData;
import net.ib.paperless.spring.domain.FileUploadInfo;
import net.ib.paperless.spring.domain.JsonFile;
import net.ib.paperless.spring.domain.LoanInfo;
import net.ib.paperless.spring.domain.NoticeInfo;
import net.ib.paperless.spring.domain.ProdMng;
import net.ib.paperless.spring.domain.ProgressStatus;
import net.ib.paperless.spring.domain.Status;
import net.ib.paperless.spring.domain.User;
import net.ib.paperless.spring.service.AdminMngService;
import net.ib.paperless.spring.service.AdminNoticeService;
import net.ib.paperless.spring.service.AuthHistoryMngService;
import net.ib.paperless.spring.service.AuthenticationService;
import net.ib.paperless.spring.service.BankService;
import net.ib.paperless.spring.service.BlockUserService;
import net.ib.paperless.spring.service.StatusService;

@RestController
@RequestMapping("/{loanId}/api/*")
public class ApiController {
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

	@Autowired
    AuthenticationService authenticationService;
	@Autowired
	StatusService statusService; 
	@Autowired
	AdminMngService adminMngService; 
	@Autowired
	AdminNoticeService adminNoticeService;
	
	//18.04.26 김웅희
	@Autowired
	AuthHistoryMngService authHistoryMngService; 
	@Autowired
	BlockUserService blockUserService;
	@Autowired
	BankService bankService;
	
	/*은행리스트 - bank*/
	@RequestMapping(value="/bankSelect" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> bankSelect(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		 
		params.put("loanId", loanId);
		List<BankInfo> list = bankService.bankSelect(params);

		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		responseJson.setResult(true);
		responseJson.setTotalItems(list.size() <= 0 ? 0 : list.get(0).getTotalCnt());
		responseJson.setResultType("list");
		responseJson.setList(list);
		return responseJson;
	}
	
	@RequestMapping(value="/bankUpdate", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> bankUpdate(@RequestBody HashMap<String, Object> params){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		int updateStatus = bankService.bankUpdate(params);
		
		if(updateStatus > 0){
			responseJson.setResult(true);
			responseJson.setMessage("수정 되었습니다.");
		}else{
			responseJson.setResult(false);
			responseJson.setMessage("수정 실패.");
		}
		return responseJson;
	}
	
	@RequestMapping(value="/bankDelete", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> bankDelete(@RequestBody HashMap<String, Object> params){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		int deleteStatus = bankService.bankDelete(params);

		if(deleteStatus > 0){
			responseJson.setResult(true);			
			responseJson.setMessage("삭제 되었습니다.");
		}else{
			responseJson.setResult(false);
			responseJson.setMessage("삭제 실패.");
		}
		return responseJson;
	}
	
	@RequestMapping(value="/bankInsert", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> bankInsert(@RequestBody HashMap<String, Object> params, Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		
		params.put("admin_id", principal.getName());
		int insertStatus = bankService.bankInsert(params);
		
		if(insertStatus > 0){
			responseJson.setResult(true);
			responseJson.setMessage("등록 되었습니다.");
		}else{
			responseJson.setResult(false);
			responseJson.setMessage("등록 실패.");
		}
		return responseJson;
	}
	/*은행리스트 - bank End*/
	
	/*차단리스트 - blockUser*/
	@RequestMapping(value="/blockUserSelect" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> blockUserSelect(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		 
		params.put("loanId", loanId);
		List<BlockUserInfo> list = blockUserService.blockUserSelect(params);

		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		responseJson.setResult(true);
		responseJson.setTotalItems(list.size() <= 0 ? 0 : list.get(0).getTotalCnt());
		responseJson.setResultType("list");
		responseJson.setList(list);
		return responseJson;
	}
	
	@RequestMapping(value="/blockUserUpdate", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> blockUserUpdate(@RequestBody HashMap<String, Object> params){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		int updateStatus = blockUserService.blockUserUpdate(params);
		
		if(updateStatus > 0){
			responseJson.setResult(true);
			responseJson.setMessage("수정 되었습니다.");
		}else{
			responseJson.setResult(false);
			responseJson.setMessage("수정 실패.");
		}
		return responseJson;
	}
	
	@RequestMapping(value="/blockUserDelete", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> blockUserDelete(@RequestBody HashMap<String, Object> params){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		int deleteStatus = blockUserService.blockUserDelete(params);

		if(deleteStatus > 0){
			responseJson.setResult(true);			
			responseJson.setMessage("삭제 되었습니다.");
		}else{
			responseJson.setResult(false);
			responseJson.setMessage("삭제 실패.");
		}
		return responseJson;
	}
	
	@RequestMapping(value="/blockUserInsert", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> blockUserInsert(@RequestBody HashMap<String, Object> params, Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		
		params.put("admin_id", principal.getName());
		int insertStatus = blockUserService.blockUserInsert(params);
		
		if(insertStatus > 0){
			responseJson.setResult(true);
			responseJson.setMessage("등록 되었습니다.");
		}else{
			responseJson.setResult(false);
			responseJson.setMessage("등록 실패.");
		}
		return responseJson;
	}
	/*차단리스트 - blockUser End*/
	
	@RequestMapping(value="/newPasswordUpdate", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> newPasswordUpdate(@RequestBody HashMap<String, Object> params, Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		
		params.put("id", principal.getName());
		int updateStatus = adminMngService.newPasswordUpdate(params);
		
		if(updateStatus > 0){
			responseJson.setResult(true);
			responseJson.setMessage("수정 되었습니다.");
		}else{
			responseJson.setResult(false);
			responseJson.setMessage("수정 실패.");
		}
		return responseJson;
	}
	/**
	 * 상담신청 리스트
	 * @param progress_id
	 * @return
	 */
	@RequestMapping(value="/counselSelectList", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> counselSelectList(@RequestBody HashMap<String, Object> params , @PathVariable String loanId) {
		 
		params.put("loanId", loanId);
		//System.out.println("params : "+ params);
		List<Status> counselList = statusService.counselSelectList(params);
		//System.out.println("counselList : "+ counselList);

		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		responseJson.setResult(true);
		responseJson.setTotalItems(counselList.size() <= 0 ? 0 : counselList.get(0).getTotalCnt());
		responseJson.setResultType("list");
		responseJson.setList(counselList);
		return responseJson;
	}

	/**
	 * 상담신청 상세
	 * @param progress_id
	 * @return
	 */
	@RequestMapping(value="/counselSelectOne", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> counselSelectOne(@RequestBody HashMap<String, Object> params , @PathVariable String loanId) {
		params.put("loanId", loanId);
		Status counsel = statusService.counselSelectOne(params.get("progressId").toString());
		JsonFile jsonfile = statusService.getFtpJsonData(params.get("progressId").toString());
		//System.out.println("params : " + params);
		//System.out.println("jsonfile : " + jsonfile);
		counsel.setUser_name(jsonfile.getName());
		counsel.setTel_number(jsonfile.getTel());
		counsel.setBank(jsonfile.getBank());
		counsel.setAccount(jsonfile.getAccount());
		counsel.setJumin(jsonfile.getJumin());
		
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		responseJson.setResult(true);
		responseJson.setTotalItems(counsel==null?0:1);
		responseJson.setResultType("list");
		responseJson.setMap(counsel);
		return responseJson;
	}
	

	/**
	 * 상담메모 리스트
	 * @param progress_id
	 * @return
	 */
	@RequestMapping(value="/progressNoteSelectList", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> progressNoteSelectList(@RequestBody HashMap<String, Object> params , @PathVariable String loanId) {
		params.put("loanId", loanId);
		List<Status> noteList = statusService.progressNoteSelectList(params.get("progressId").toString());
		//System.out.println("noteList : " + noteList);
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		responseJson.setResult(true);
		responseJson.setTotalItems(noteList==null?0:1);
		responseJson.setResultType("list");
		responseJson.setList(noteList);
		return responseJson;
	}
	
	/**
	 * 대출상담원등록
	 * @param params
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/progressAdminUpdate", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> progressAdminUpdate(@RequestBody HashMap<String, Object> params,Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		
		if(params.get("progressId") == null || principal.getName() == null){
			responseJson.setResult(false);
			responseJson.setMessage("담당지정이 실패하였습니다.");
		}else{
			params.put("admin_id", principal.getName());
			int result = statusService.progressUpdate(params);
			//System.out.println("result : " + result);
			responseJson.setResult(result == 0 ? false:true);
			responseJson.setMessage(principal.getName() +"으로 담당자가 지정되었습니다.");
		}
		return responseJson;
	}
	
	/**
	 * 상태코드리스트
	 * @return
	 */
	@RequestMapping(value="/statusCodeSelectList", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> statusCodeSelectList(){
		List<Status> statusList = statusService.statusCodeSelectList();

		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		responseJson.setResult(true);
		responseJson.setTotalItems(statusList.size());
		responseJson.setResultType("list");
		responseJson.setList(statusList);
		return responseJson;
	}
	
	/**
	 * 메모등록
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/progressNoteInsert", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> progressNoteInsert(@RequestBody HashMap<String, Object> params,Principal principal){
		int result = 0;	
		if(params.get("body")!=null && !params.get("body").equals("")){
			params.put("admin_id", principal.getName());
			result = statusService.progressNoteInsert(params);
		}
		
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		responseJson.setResult(result == 0 ? false:true);
		responseJson.setMessage(result == 0 ? "메모가 실패하었습니다.":"메모가 등록되었습니다.");
		return responseJson;
	}
	

	
	/**
	 * 대출신청 금액 변경
	 * @param params
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/requireAmountUpdate", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> requireAmountUpdate(@RequestBody HashMap<String, Object> params,Principal principal){
		int result = statusService.progressUpdate(params);
		params.put("admin_id", principal.getName());
		if(params.get("body") != null){
			int result2 = statusService.progressNoteInsert(params);	
		}
		
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		responseJson.setResult(result == 0 ? false:true);
		responseJson.setMessage(result == 0 ?"실패 하였습니다":"변경 되었습니다.");
		
		return responseJson;
	}
	
	
	/**
	 * 대출신청 금액 변경
	 * @param params
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/progressStatusUpdate", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> progressStatusUpdate(@RequestBody HashMap<String, Object> params,Principal principal){
		int result = statusService.progressStatusUpdate(params);
		//System.out.println("params:" + params);
		params.put("admin_id", principal.getName());
		if(params.get("body") != null){
			int result2 = statusService.progressNoteInsert(params);	
		}
		
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		responseJson.setResult(result == 0 ? false:true);
		responseJson.setMessage(result == 0 ?"실패 하였습니다":"변경 되었습니다.");
		
		return responseJson;
	}

	
	/**
	 * 대출신청 금액 변경
	 * @param params
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/eformInfoSelectList", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> eformInfoSelectList(@PathVariable String loanId){
		List<EformInfo> list =  statusService.eformInfoSelectList(loanId);
		//System.out.println("list : " + list);
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		responseJson.setResult(true);
		responseJson.setTotalItems(list.size());
		responseJson.setResultType("list");
		responseJson.setList(list);
		return responseJson;
	}
	
	/**
	 * 금융상품 설정
	 * @param params
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/progressUpdate", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> progressUpdate(@RequestBody HashMap<String, Object> params,Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();

		//System.out.println("params : " + params);
		int result = statusService.progressUpdate(params);
		//System.out.println("result : " + result);
		responseJson.setResult(result == 0 ? false:true);
		responseJson.setMessage(result == 0 ? "설정 실패":"설정 완료");
		return responseJson;
	}

	
	/**
	 * 대출신청 금액 변경
	 * @param params
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/eformAttachSelectList", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> eformAttachSelectList(@RequestBody HashMap<String, Object> params){
		List<EformAttach> list =  statusService.eformAttachSelectList(params.get("progressId").toString());
		
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		responseJson.setResult(true);
		responseJson.setTotalItems(list.size());
		responseJson.setResultType("list");
		responseJson.setList(list);
		return responseJson;
	}
	
	/**
	 * 서류제출 상태 업데이트
	 * @param params
	 * @param principal
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/attachProgressUpdate", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> attachProgressUpdate(@RequestBody HashMap<String, Object> params,Principal principal) throws ParseException{
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		params.put("admin_id",principal.getName());
		//System.out.println("params : " + params);
		//System.out.println(statusService.progressUpdate(params));
		boolean boo = statusService.attachProgressUpdate(params);
		//System.out.println("boo : " + boo);
		if(boo){
			responseJson.setResult(true);
			responseJson.setMessage("설정변경 완료");
		}else{
			responseJson.setResult(false);
			responseJson.setMessage("설정변경 실패");
		}
		return responseJson;
	}

	
	/**
	 * 고객 신규등록
	 * @param params
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/eformUserdataInsert", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> eformUserdataInsert(@RequestBody HashMap<String, Object> params,@PathVariable String loanId ,Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		//progress insert , json 생성
		params.put("loan_id", loanId);
		params.put("admin_id",principal.getName());
		Map<String,Object> map = statusService.setLoanUserInfo(params);
		if((boolean) map.get("result")){
			ProgressStatus entity = (ProgressStatus) map.get("entity");
			//eform_userdata insert
			params.put("progress_id",entity.getProgress_id());
			int result = statusService.insertEformUserData(params);
			if(result > 0){
				responseJson.setResult(true);
				responseJson.setMessage("등록되었습니다.");
			}else{
				responseJson.setResult(false);
				responseJson.setMessage("등록실패.");
			}
		}else{

			responseJson.setResult(false);
			responseJson.setMessage("등록실패.");
		}
		
		return responseJson;
	}
	

	
	/**
	 * 고객 정보수정
	 * @param params
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/eformUserdataEdit", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> eformUserdataEdit(@RequestBody HashMap<String, Object> params,Principal principal,@PathVariable String loanId){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		//progress insert , json 생성
		params.put("admin_id",principal.getName());
		params.put("loan_id",loanId);
		//Map<String,Object> map = statusService.setLoanUserInfo(params);
		JsonFile jsonFile = statusService.getFtpJsonData(params.get("progressId").toString());
		Map<String,Object> map = statusService.jsonFileUpdate(jsonFile, params);
		
		if((boolean) map.get("result")){
			int result = statusService.editEformUserData(params);
				
			if(result > 0){
				responseJson.setResult(true);
				responseJson.setMessage("등록되었습니다.");
			}else{
				responseJson.setResult(false);
				responseJson.setMessage("등록실패.");
			}
		}else{
			responseJson.setResult(false);
			responseJson.setMessage("등록실패.");
		}
		return responseJson;
	}
	
	/**
	 * user json data 가져오기
	 * @param params
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/getFtpJsonData", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> getFtpJsonData(@RequestBody HashMap<String, Object> params){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		//System.out.println("params : " + params);
		//System.out.println(statusService.getFtpJsonData(params.get("progressId").toString()));
		//getFtpJsonData(params);
		/*int result = statusService.progressUpdate(params);
		//System.out.println("result : " + result);
		responseJson.setResult(result == 0 ? false:true);
		responseJson.setMessage(result == 0 ? "설정 실패":"설정 완료");*/
		return responseJson;
	}

	@RequestMapping(value="/eformUserDataSelectOne", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> eformUserDataSelectOne(@RequestBody HashMap<String, Object> params){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		EformUserData eformUserData = statusService.eformUserDataSelectOne(params.get("progressId").toString());
		JsonFile jsonFile = statusService.getFtpJsonData(params.get("progressId").toString());
		eformUserData.setReg(jsonFile.getReg());
		eformUserData.setName(jsonFile.getName());
		eformUserData.setBank(jsonFile.getBank());
		eformUserData.setAccount(jsonFile.getAccount());
		eformUserData.setJumin(jsonFile.getJumin());
		
		//System.out.println("eformUserData : " + eformUserData);
		responseJson.setResult(eformUserData == null ? false : true);
		responseJson.setMap(eformUserData);
		return responseJson;
	}
	
	
	@RequestMapping(value="/adminInfoSelectList", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> adminInfoSelectList(@RequestBody HashMap<String, Object> params){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		List<AdminInfo> adminInfoList = statusService.adminInfoSelectList(params);
		responseJson.setResult(true);
		responseJson.setTotalItems(adminInfoList.size() <= 0 ? 0 : adminInfoList.get(0).getTotalCnt());
		responseJson.setResultType("list");
		responseJson.setList(adminInfoList);
		return responseJson;
	}
	
	@RequestMapping(value="/noticeUpdate", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> noticeUpdate(@RequestBody HashMap<String, Object> params){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		int updateStatus = adminNoticeService.noticeUpdate(params);
		
		if(updateStatus > 0){
			responseJson.setResult(true);
			responseJson.setMessage("수정 되었습니다.");
		}else{
			responseJson.setResult(false);
			responseJson.setMessage("수정 실패.");
		}
		return responseJson;
	}
	@RequestMapping(value="/noticeDelete", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> noticeDelete(@RequestBody HashMap<String, Object> params){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		int updateStatus = adminNoticeService.noticeDelete(params);

		if(updateStatus > 0){
			responseJson.setResult(true);			
			responseJson.setMessage("삭제 되었습니다.");
		}else{
			responseJson.setResult(false);
			responseJson.setMessage("삭제 실패.");
		}
		return responseJson;
	}
	
	@RequestMapping(value="/noticeInsert", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> noticeInsert(@RequestBody HashMap<String, Object> params, Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		
		params.put("admin_id",principal.getName());
		int insertStatus = adminNoticeService.noticeInsert(params);
		
		if(insertStatus > 0){
			responseJson.setResult(true);
			responseJson.setMessage("등록 되었습니다.");
		}else{
			responseJson.setResult(false);
			responseJson.setMessage("등록 실패.");
		}
		return responseJson;
	}
	
	/**
	 * 사용자 정보수정
	 * @param params
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/adminInfoUpdate", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> adminInfoUpdate(@RequestBody HashMap<String, Object> params,Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		//progress insert , json 생성
		params.put("admin_id",principal.getName());
		//System.out.println("params : " + params);
		int result = statusService.adminInfoUpdate(params);
		//System.out.println("result : " + result);
			
		if(result > 0){
			responseJson.setResult(true);
			responseJson.setMessage("수정 되었습니다.");
		}else{
			responseJson.setResult(false);
			responseJson.setMessage("수정 실패.");
		}
		return responseJson;
	}

	
	/**
	 * 사용자 정보등록
	 * @param params
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/adminInfoInsert", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> adminInfoInsert(@RequestBody HashMap<String, Object> params,Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		//progress insert , json 생성
		params.put("admin_id",principal.getName());
		//System.out.println("params : " + params);
		int result = statusService.adminInfoInsert(params);
		//System.out.println("result : " + result);
			
		if(result > 0){
			responseJson.setResult(true);
			responseJson.setMessage("등록 되었습니다.");
		}else{
			responseJson.setResult(false);
			responseJson.setMessage("등록 실패.");
		}
		return responseJson;
	}
	
	
	/**
	 * 사용자 아이디 중복 체크
	 * @param params
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/adminInfoIdChk", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> adminInfoIdChk(@RequestBody HashMap<String, Object> params,Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		//progress insert , json 생성
		params.put("admin_id",principal.getName());
		//System.out.println("params : " + params);
		int result = statusService.adminInfoSelectCnt(params.get("id").toString());
		//System.out.println("result : " + result);
			
		if(result > 0){
			responseJson.setResult(false);
			responseJson.setMessage("이미 사용중인 아이디입니다.");
		}else{
			responseJson.setResult(true);
			responseJson.setMessage("사용 가능한 아이디 입니다.");
		}
		return responseJson;
	}
	
	@RequestMapping(value="/prodMngSelectList" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> prodMngSelectList(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		 
		params.put("loanId", loanId);
		//System.out.println("params : "+ params);
		List<ProdMng> prodMngSelectList = adminMngService.prodMngSelectList(params);
		//System.out.println("prodMngSelectList : "+ prodMngSelectList);

		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		responseJson.setResult(true);
		responseJson.setTotalItems(prodMngSelectList.size() <= 0 ? 0 : prodMngSelectList.get(0).getTotalCnt());
		responseJson.setResultType("list");
		responseJson.setList(prodMngSelectList);
		return responseJson;
	}

	
	@RequestMapping(value="/prodMngSelectOne" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> prodMngSelectOne(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		 
		params.put("loanId", loanId);
		//System.out.println("params : "+ params);
		ProdMng prodMngSelectOne = adminMngService.prodMngSelectOne(Integer.parseInt(params.get("seq").toString()));
		prodMngSelectOne.setEformAttach(adminMngService.eformAttachSelectList(Integer.parseInt(params.get("seq").toString())));
		//System.out.println("prodMngSelectOne : "+ prodMngSelectOne);

		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		responseJson.setResult(true);
		responseJson.setResultType("map");
		responseJson.setMap(prodMngSelectOne);
		return responseJson;
	}
	
	@RequestMapping(value="/noticeSelectOne" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> noticeSelectOne(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		 
		params.put("loanId", loanId);
		//System.out.println("params : "+ params);
		NoticeInfo noticeSelectOne = adminNoticeService.noticeSelectOne(Integer.parseInt(params.get("seq").toString()));
		//System.out.println("noticeSelectOne : "+ noticeSelectOne);

		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		responseJson.setResult(true);
		responseJson.setResultType("map");
		responseJson.setMap(noticeSelectOne);
		return responseJson;
	}
	
	
	@RequestMapping(value="/eformAttachInsert" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> eformAttachInsert(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		params.put("loanId", loanId);
		//System.out.println("params : "+ params);
		if( adminMngService.eformAttachInsert(params) > 0){
			responseJson.setResult(true);
			responseJson.setMessage("등록 되었습니다.");
		}else{
			responseJson.setResult(false);
			responseJson.setMessage("등록이 실패하였습니다.");
		}
		return responseJson;
	}
	
	@RequestMapping(value="/loanInfoSelectList" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> loanInfoSelectList(@RequestBody HashMap<String,Object> params){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		List<LoanInfo> list = adminMngService.loanInfoSelectList() ;
		responseJson.setResult(true);
		responseJson.setResultType("list");
		responseJson.setList(list);
	
		return responseJson;
	}
	
	@RequestMapping(value="/eformInfoInsert" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> eformInfoInsert(
			@RequestParam("eform_attach_insert_file") MultipartFile uploadfile , 
			@RequestParam HashMap<String,Object> params,HttpServletRequest request ,
			Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		params.put("admin_id",principal.getName());
		//System.out.println(request.getSession().getServletContext().getRealPath("WEB-INF"));
		if(FileFtpHandler.writeFile(uploadfile, request.getSession().getServletContext().getRealPath("WEB-INF") + "\\clipreport4", uploadfile.getOriginalFilename()) && uploadfile != null){
			params.put("eform_path",request.getSession().getServletContext().getRealPath("WEB-INF") + "\\clipreport4\\"+uploadfile.getOriginalFilename());
			//if( adminMngService.eformInfoInsert(params) > 0){
			if( adminMngService.eformInfoInsertAsMultipart(params) > 0){
				responseJson.setResult(true);
				responseJson.setMessage("등록 되었습니다.");
			}else{
				responseJson.setResult(false);
				responseJson.setMessage("등록이 실패하였습니다.");
			}
		}else{
			responseJson.setResult(false);
			responseJson.setMessage("등록이 실패하였습니다.");
		}
		return responseJson;
	}
	

	
	@RequestMapping(value="/eformInfoUpdate" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> eformInfoUpdate(
			@RequestParam("eform_attach_update_file") MultipartFile uploadfile , 
			@RequestParam HashMap<String,Object> params,HttpServletRequest request,Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		params.put("admin_id",principal.getName());
		//System.out.println("params : " + params);
		if(!uploadfile.getOriginalFilename().equals("")){
			FileFtpHandler.writeFile(uploadfile, request.getSession().getServletContext().getRealPath("WEB-INF") + "\\clipreport4", uploadfile.getOriginalFilename());
			params.put("eform_path",request.getSession().getServletContext().getRealPath("WEB-INF") + "\\clipreport4\\"+uploadfile.getOriginalFilename());
		}
		//if( adminMngService.eformInfoInsert(params) > 0){
		try {
			if( adminMngService.eformInfoUpdateAsMultipart(params) > 0){
				responseJson.setResult(true);
				responseJson.setMessage("등록 되었습니다.");
			}else{
				responseJson.setResult(false);
				responseJson.setMessage("등록이 실패하였습니다.");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return responseJson;
	}
	
	@RequestMapping(value="/noticeSelectList" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> noticeSelectList(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		 
		params.put("loanId", loanId);
		List<NoticeInfo> noticeSelectList = adminNoticeService.noticeSelectList(params);

		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		responseJson.setResult(true);
		responseJson.setTotalItems(noticeSelectList.size() <= 0 ? 0 : noticeSelectList.get(0).getTotalCnt());
		responseJson.setResultType("list");
		responseJson.setList(noticeSelectList);
		return responseJson;
	}

	
	@RequestMapping(value="/eformDel" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> eformDelete(@RequestBody HashMap<String,Object> params){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		int i = adminMngService.eformDelete(params);
		responseJson.setResult((i>0)?true:false);
		responseJson.setMessage((i>0)?"삭제되었습니다.":"실패하였습니다.");
	
		return responseJson;
	}
	//adminInfoDel

	
	@RequestMapping(value="/adminInfoDel" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> adminInfoDelete(@RequestBody HashMap<String,Object> params){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		//System.out.println("params: " + params);
		
		int i = statusService.eformDelete(params);
		responseJson.setResult((i>0)?true:false);
		responseJson.setMessage((i>0)?"삭제되었습니다.":"실패하였습니다.");
	
		return responseJson;
	}
	

	/**
	 * 로그인필요
	 * @param progress_id
	 * @return
	 */
	@RequestMapping(value="/checkLogin", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> checkLogin() {
		 
		//params.put("loanId", loanId);

		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		responseJson.setResult(false);
		responseJson.setMessage("로그인이 필요합니다.");
		responseJson.setUrl("/login");
		return responseJson;
	}
	
	//18.04.26 김웅희
	@RequestMapping(value="/historyCountSelectList" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> historyCountSelectList(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		List<AccountAuthHistoryInfo> list = authHistoryMngService.historyCountSelectList(params);
		
		params.put("loanId", loanId);
		
		responseJson.setResult(true);
		responseJson.setTotalItems(list.size() <= 0 ? 0 : list.get(0).getTotalCnt());
		responseJson.setResultType("list");
		responseJson.setList(list);
		return responseJson;
	}
	
	@RequestMapping(value="/historySelectList" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> historySelectList(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		List<AccountAuthHistoryInfo> list = authHistoryMngService.historySelectList(params);
		
		params.put("loanId", loanId);
		
		responseJson.setResult(true);
		responseJson.setTotalItems(list.size() <= 0 ? 0 : list.get(0).getTotalCnt());
		responseJson.setResultType("list");
		responseJson.setList(list);
		return responseJson;
	}
	
	@RequestMapping(value="/accountAuthHistorySelectList" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> accountAuthHistorySelectList(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		List<AccountAuthHistoryInfo> list = authHistoryMngService.accountAuthHistoryMngSelectList(params);
		
		params.put("loanId", loanId);
		
		responseJson.setResult(true);
		responseJson.setTotalItems(list.size() <= 0 ? 0 : list.get(0).getTotalCnt());
		responseJson.setResultType("list");
		responseJson.setList(list);
		return responseJson;
	}
	
	@RequestMapping(value="/accountAuthHistorySelectListExcel" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> accountAuthHistorySelectListExcel(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		List<AccountAuthHistoryInfo> list = authHistoryMngService.accountAuthHistoryMngSelectListExcel(params);
		params.put("loanId", loanId);
		
		responseJson.setResult(true);
		responseJson.setTotalItems(list.size() <= 0 ? 0 : list.get(0).getTotalCnt());
		responseJson.setResultType("list");
		responseJson.setList(list);
		return responseJson;
	}
	
	//금결원
	@RequestMapping(value="/accountAuthHistorySelectList2" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> accountAuthHistorySelectList2(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		List<AccountAuthHistoryInfo> accountAuthHistoryMngSelectList = authHistoryMngService.accountAuthHistoryMngSelectList2(params);
		
		params.put("loanId", loanId);
		
		responseJson.setResult(true);
		responseJson.setTotalItems(accountAuthHistoryMngSelectList.size() <= 0 ? 0 : accountAuthHistoryMngSelectList.get(0).getTotalCnt());
		responseJson.setResultType("list");
		responseJson.setList(accountAuthHistoryMngSelectList);
		return responseJson;
	}
	
	@RequestMapping(value="/accountAuthHistorySelectListExcel2" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> accountAuthHistorySelectListExcel2(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		List<AccountAuthHistoryInfo> list = authHistoryMngService.accountAuthHistoryMngSelectListExcel2(params);
		params.put("loanId", loanId);
		
		responseJson.setResult(true);
		responseJson.setTotalItems(list.size() <= 0 ? 0 : list.get(0).getTotalCnt());
		responseJson.setResultType("list");
		responseJson.setList(list);
		return responseJson;
	}
	
	@RequestMapping(value="/accountBankNameSelectList" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> accountBankNameSelectList(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		List<AccountAuthHistoryInfo> accountBankNameMngSelectList = authHistoryMngService.accountBankNameMngSelectList(params);
		
		params.put("loanId", loanId);
		
		responseJson.setResult(true);
		responseJson.setTotalItems(accountBankNameMngSelectList.size() <= 0 ? 0 : accountBankNameMngSelectList.get(0).getTotalCnt());
		responseJson.setResultType("list");
		responseJson.setList(accountBankNameMngSelectList);
		return responseJson;
	}
	
	@RequestMapping(value="/customerChargeSelectList" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> customerChargeSelectList(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		List<CustomerChargeInfo> list = authHistoryMngService.customerChargeMngSelectList(params);
		
		params.put("loanId", loanId);
		
		responseJson.setResult(true);
		responseJson.setTotalItems(list.size() <= 0 ? 0 : list.get(0).getTotalCnt());
		responseJson.setResultType("list");
		responseJson.setList(list);
		return responseJson;
	}
	
	@RequestMapping(value="/customerChargeBasicSelectList" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> customerChargeBasicSelectList(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		List<CustomerChargeInfo> list = authHistoryMngService.customerChargeBasicSelectList(params);
		
		params.put("loanId", loanId);
		
		responseJson.setResult(true);
		responseJson.setTotalItems(list.size() <= 0 ? 0 : list.get(0).getTotalCnt());
		responseJson.setResultType("list");
		responseJson.setList(list);
		return responseJson;
	}
	
	@RequestMapping(value="/customerServiceSelectList" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> customerServiceSelectList(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		List<CustomerServiceInfo> list = authHistoryMngService.customerServiceMngSelectList(params);
		
		params.put("loanId", loanId);
		
		responseJson.setResult(true);
		responseJson.setResultType("list");
		responseJson.setList(list);
		return responseJson;
	}
	
	@RequestMapping(value="/customerServiceUpdate", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> customerServiceUpdate(@RequestBody HashMap<String, Object> params,Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();

		//System.out.println("params : " + params);
		int result = authHistoryMngService.customerServiceMngUpdate(params);
		//System.out.println("result : " + result);
		responseJson.setResult(result == 0 ? false:true);
		responseJson.setMessage(result == 0 ? "설정 실패":"설정 완료");
		return responseJson;
	}
	
	@RequestMapping(value="/customerServiceBasicInsert", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> customerServiceBasicInsert(@RequestBody HashMap<String, Object> params, Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		
		int insertStatus = authHistoryMngService.customerServiceBasicInsert(params);
		
		if(insertStatus > 0){
			responseJson.setResult(true);
			responseJson.setMessage("등록 되었습니다.");
		}else{
			responseJson.setResult(false);
			responseJson.setMessage("등록 실패.");
		}
		return responseJson;
	}
	
	@RequestMapping(value="/userInsert", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> userInsert(@RequestBody HashMap<String, Object> params, Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		
		String id = params.get("id").toString();
		User ud = authenticationService.loadUserOpenApi(id);
		int insertStatus = 0;
		
		if(ud == null) {
			params.put("admin_id",principal.getName());
			insertStatus = adminMngService.userInsert(params);
		}
		
		if(insertStatus > 0){
			responseJson.setResult(true);
			responseJson.setMessage("등록 되었습니다.");
		}else{
			responseJson.setResult(false);
			responseJson.setMessage("등록 실패.");
		}
		
		return responseJson;
	}
	
	@RequestMapping(value="/userDelete", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> userDelete(@RequestBody HashMap<String, Object> params){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		int updateStatus = adminMngService.userDelete(params);

		if(updateStatus > 0){
			responseJson.setResult(true);			
			responseJson.setMessage("삭제 되었습니다.");
		}else{
			responseJson.setResult(false);
			responseJson.setMessage("삭제 실패.");
		}
		return responseJson;
	}
	@RequestMapping(value="/userUpdate", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> userUpdate(@RequestBody HashMap<String, Object> params){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		int updateStatus = adminMngService.userUpdate(params);
		if(updateStatus > 0){
			responseJson.setResult(true);
			responseJson.setMessage("수정 되었습니다.");
		}else{
			responseJson.setResult(false);
			responseJson.setMessage("수정 실패.");
		}
		return responseJson;
	}
	
	@RequestMapping(value="/companyInsert", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> companyInsert(@RequestBody HashMap<String, Object> params, Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		
		params.put("admin_id",principal.getName());
		int insertStatus = adminMngService.companyInsert(params);
		
		if(insertStatus > 0){
			responseJson.setResult(true);
			responseJson.setMessage("등록 되었습니다.");
		}else{
			responseJson.setResult(false);
			responseJson.setMessage("등록 실패.");
		}
		return responseJson;
	}
	@RequestMapping(value="/companyDelete", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> companyDelete(@RequestBody HashMap<String, Object> params){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		int updateStatus = adminMngService.companyDelete(params);

		if(updateStatus > 0){
			responseJson.setResult(true);			
			responseJson.setMessage("삭제 되었습니다.");
		}else{
			responseJson.setResult(false);
			responseJson.setMessage("삭제 실패.");
		}
		return responseJson;
	}
	@RequestMapping(value="/companyUpdate", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> companyUpdate(@RequestBody HashMap<String, Object> params){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		int updateStatus = adminMngService.companyUpdate(params);
		if(updateStatus > 0){
			responseJson.setResult(true);
			responseJson.setMessage("수정 되었습니다.");
		}else{
			responseJson.setResult(false);
			responseJson.setMessage("수정 실패.");
		}
		return responseJson;
	}
	@RequestMapping(value="/companyInfoSelectList" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> companyInfoSelectList(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		List<CompanyInfo> list = authHistoryMngService.companyInfoMngSelectList(params);
		
		params.put("loanId", loanId);
		
		responseJson.setResult(true);
		responseJson.setTotalItems(list.size() <= 0 ? 0 : list.get(0).getTotalCnt());
		responseJson.setResultType("list");
		responseJson.setList(list);
		return responseJson;
	}
	
	@RequestMapping(value="/companyInfoOne" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> companyInfoOne(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		CompanyInfo companyInfoOne = authHistoryMngService.companyInfoMngOne(params);
		params.put("loanId", loanId);
		
		responseJson.setResult(true);
		responseJson.setResultType("map");
		responseJson.setMap(companyInfoOne);
		return responseJson;
	}
	
	@RequestMapping(value="/companyInfoTitleSelect" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> companyInfoTitleSelect(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		List<CustomerChargeInfo> list = authHistoryMngService.companyInfoTitleSelect(params);
		
		params.put("loanId", loanId);
		
		responseJson.setResult(true);
		responseJson.setResultType("list");
		responseJson.setList(list);
		return responseJson;
	}
	
	/*service/domainManage*/
		/*도메인 리스트*/
		@RequestMapping(value="/domainInfoSelect" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
		public ApiResponse<Map<String,Object>> domainInfoSelect(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
			ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
			List<DomainInfo> list = authHistoryMngService.domainInfoSelect(params);
			
			params.put("loanId", loanId);
			
			responseJson.setResult(true);
			responseJson.setResultType("list");
			responseJson.setList(list);
			return responseJson;
		}
		
		/*도메인 리스트*/
		@RequestMapping(value="/domainListSelect" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
		public ApiResponse<Map<String,Object>> domainListSelect(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
			ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
			List<DomainInfo> list = authHistoryMngService.domainListSelect(params);
			
			params.put("loanId", loanId);
			
			responseJson.setResult(true);
			responseJson.setResultType("list");
			responseJson.setList(list);
			return responseJson;
		}
		
		/*도메인 존재유무 체크*/
		@RequestMapping(value="/domainCheck" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
		public ApiResponse<Map<String,Object>> doaminCheck(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
			ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
			List<DomainInfo> list = authHistoryMngService.domainCheck(params);
			
			params.put("loanId", loanId);
			
			responseJson.setResult(true);
			responseJson.setResultType("list");
			responseJson.setList(list);
			return responseJson;
		}
		
		/*도메인 등록*/
		@RequestMapping(value="/domainInsert", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
		public ApiResponse<Map<String,Object>> domainInsert(@RequestBody HashMap<String, Object> params, Principal principal){
			ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
			
			int insertStatus = authHistoryMngService.domainInsert(params);
			
			if(insertStatus > 0){
				responseJson.setResult(true);
				responseJson.setMessage("등록 되었습니다.");
			}else{
				responseJson.setResult(false);
				responseJson.setMessage("등록 실패.");
			}
			return responseJson;
		}

		/*도메인 사용안함*/
		@RequestMapping(value="/domainDelete", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
		public ApiResponse<Map<String,Object>> domainDelete(@RequestBody HashMap<String, Object> params,Principal principal){
			ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
	
			//System.out.println("params : " + params);
			int result = authHistoryMngService.domainDelete(params);
			//System.out.println("result : " + result);
			responseJson.setResult(result == 0 ? false:true);
			responseJson.setMessage(result == 0 ? "설정 실패":"설정 완료");
			return responseJson;
		}
		
		@RequestMapping(value="/domainUriCheck" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
		public ApiResponse<Map<String,Object>> domainUriCheck(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
			ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
			List<DomainInfo> list = authHistoryMngService.domainUriCheck(params);
			
			params.put("loanId", loanId);
			
			responseJson.setResult(true);
			responseJson.setResultType("list");
			responseJson.setList(list);
			return responseJson;
		}
		
		@RequestMapping(value="/domainUriInsert", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
		public ApiResponse<Map<String,Object>> domainUriInsert(@RequestBody HashMap<String, Object> params, Principal principal){
			ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
			
			int insertStatus = authHistoryMngService.domainUriInsert(params);
			
			if(insertStatus > 0){
				responseJson.setResult(true);
				responseJson.setMessage("등록 되었습니다.");
			}else{
				responseJson.setResult(false);
				responseJson.setMessage("등록 실패.");
			}
			return responseJson;
		}
	
		/*도메인 URL 업데이트*/
		@RequestMapping(value="/domainUriUpdate", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
		public ApiResponse<Map<String,Object>> domainUriUpdate(@RequestBody HashMap<String, Object> params,Principal principal){
			ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
	
			//System.out.println("params : " + params);
			int result = authHistoryMngService.domainUriUpdate(params);
			//System.out.println("result : " + result);
			responseJson.setResult(result == 0 ? false:true);
			responseJson.setMessage(result == 0 ? "설정 실패":"설정 완료");
			return responseJson;
		}
		
		/*도메인 URL 사용안함*/
		@RequestMapping(value="/domainUriDelete", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
		public ApiResponse<Map<String,Object>> domainUriDelete(@RequestBody HashMap<String, Object> params,Principal principal){
			ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
	
			//System.out.println("params : " + params);
			int result = authHistoryMngService.domainUriDelete(params);
			//System.out.println("result : " + result);
			responseJson.setResult(result == 0 ? false:true);
			responseJson.setMessage(result == 0 ? "설정 실패":"설정 완료");
			return responseJson;
		}
		
		@RequestMapping(value="/domainUriUse", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
		public ApiResponse<Map<String,Object>> domainUriUse(@RequestBody HashMap<String, Object> params,Principal principal){
			ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
	
			//System.out.println("params : " + params);
			int result = authHistoryMngService.domainUriUse(params);
			//System.out.println("result : " + result);
			responseJson.setResult(result == 0 ? false:true);
			responseJson.setMessage(result == 0 ? "설정 실패":"설정 완료");
			return responseJson;
		}
	
	/*service/domainManage End*/
	
	@RequestMapping(value="/companyInfoUpdate", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> companyInfoUpdate(@RequestBody HashMap<String, Object> params,Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();

		//System.out.println("params : " + params);
		int result = authHistoryMngService.companyInfoUpdate(params);
		//System.out.println("result : " + result);
		responseJson.setResult(result == 0 ? false:true);
		responseJson.setMessage(result == 0 ? "설정 실패":"설정 완료");
		return responseJson;
	}
	
	@RequestMapping(value="/customerServiceSlideInsert", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> customerServiceSlideInsert(@RequestBody HashMap<String, Object> params, Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();

		int insertStatus = authHistoryMngService.customerServiceSlideInsert(params);
		
		if(insertStatus > 0){
			responseJson.setResult(true);
			responseJson.setMessage("등록 되었습니다.");
		}else{
			responseJson.setResult(false);
			responseJson.setMessage("등록 실패.");
		}
		return responseJson;
	}
	
	@RequestMapping(value="/historySelect" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> historySelect(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		List<AccountAuthHistoryInfo> list = authHistoryMngService.historySelect(params);
		
		params.put("loanId", loanId);
		
		responseJson.setResult(true);
		responseJson.setTotalItems(list.size() <= 0 ? 0 : list.get(0).getTotalCnt());
		responseJson.setResultType("list");
		responseJson.setList(list);
		return responseJson;
	}
	
	@RequestMapping(value="/historyPublicSelect" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> historyPublicSelect(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		List<AccountAuthHistoryInfo> list = authHistoryMngService.historyPublicSelect(params);
		
		params.put("loanId", loanId);
		
		responseJson.setResult(true);
		responseJson.setTotalItems(list.size() <= 0 ? 0 : list.get(0).getTotalCnt());
		responseJson.setResultType("list");
		responseJson.setList(list);
		return responseJson;
	}
	
	@RequestMapping(value="/historyPublicTotalCountSelect" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> historyPublicTotalCountSelect(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		List<AccountAuthHistoryInfo> list = authHistoryMngService.historyPublicTotalCountSelect(params);
		
		params.put("loanId", loanId);
		
		responseJson.setResult(true);
		responseJson.setTotalItems(list.size() <= 0 ? 0 : list.get(0).getTotalCnt());
		responseJson.setResultType("list");
		responseJson.setList(list);
		return responseJson;
	}
	
	@RequestMapping(value="/historyPublicCountSelect" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> historyPublicCountSelect(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		AccountAuthHistoryInfo accountAuthHistoryInfo = authHistoryMngService.historyPublicCountSelect(params);
		
		params.put("loanId", loanId);
		
		responseJson.setResult(true);
		responseJson.setResultType("one");
		responseJson.setMap(accountAuthHistoryInfo);
		return responseJson;
	}
	
	@RequestMapping(value="/fileUploadSelect" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> fileUploadSelect(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		List<FileUploadInfo> list = authHistoryMngService.fileUploadSelect(params);
		
		params.put("loanId", loanId);
		
		responseJson.setResult(true);
		responseJson.setTotalItems(list.size() <= 0 ? 0 : list.get(0).getTotalCnt());
		responseJson.setResultType("list");
		responseJson.setList(list);
		return responseJson;
	}
}
