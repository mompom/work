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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.clipsoft.org.json.simple.parser.ParseException;

import net.ib.paperless.spring.common.ApiResponse;
import net.ib.paperless.spring.common.FileFtpHandler;
import net.ib.paperless.spring.domain.AdminInfo;
import net.ib.paperless.spring.domain.EformAttach;
import net.ib.paperless.spring.domain.EformInfo;
import net.ib.paperless.spring.domain.EformUserData;
import net.ib.paperless.spring.domain.JsonFile;
import net.ib.paperless.spring.domain.LoanInfo;
import net.ib.paperless.spring.domain.NoticeInfo;
import net.ib.paperless.spring.domain.ProdMng;
import net.ib.paperless.spring.domain.ProgressStatus;
import net.ib.paperless.spring.domain.Status;
import net.ib.paperless.spring.service.AdminMngService;
import net.ib.paperless.spring.service.AdminNoticeService;
import net.ib.paperless.spring.service.StatusService;


/**
 * 어드민 부분 오픈 api 처리
 * @author BRKIM-IBD1(김범래)
 * @since 2017.06.12
 */
@RestController
@RequestMapping("/{loanId}/open_api/*")
public class OpenApiController {
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	@Autowired
	StatusService statusService; 
	@Autowired
	AdminMngService adminMngService; 
	@Autowired
	AdminNoticeService adminNoticeService;
	//성공
	static final String MESSAGE_TRUE = "T";
	//실패
	static final String MESSAGE_FALSE = "F";

	
	@RequestMapping(value="/counsel/list",method={RequestMethod.GET , RequestMethod.POST}, produces="application/json")
	public ApiResponse<Map<String,Object>> counselSelectList(@RequestBody HashMap<String, Object> params,@PathVariable String loanId){
		
		params.put("loanId", loanId);
		System.out.println("params : "+ params);
		List<Status> counselList = statusService.counselSelectList(params);
		System.out.println("counselList : "+ counselList);

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
	@RequestMapping(value="/counsel", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> counselSelectOne(
			@RequestBody HashMap<String, Object> params ,
			@PathVariable String loanId) {
		params.put("loanId", loanId);
		Status counsel = statusService.counselSelectOne(params.get("progressId").toString());
		JsonFile jsonfile = statusService.getFtpJsonData(params.get("progressId").toString());
		System.out.println("params : " + params);
		System.out.println("jsonfile : " + jsonfile); 
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
	@RequestMapping(value="/progress/note/list", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> progressNoteSelectList(@RequestBody HashMap<String, Object> params , @PathVariable String loanId) {
		params.put("loanId", loanId);
		List<Status> noteList = statusService.progressNoteSelectList(params.get("progressId").toString());
		System.out.println("noteList : " + noteList);
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
	@RequestMapping(value="/progress/admin/update", method={RequestMethod.POST },produces="application/json")
	public ApiResponse<Map<String,Object>> progressAdminUpdate(@RequestBody HashMap<String, Object> params,Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		
		if(params.get("progressId") == null || principal.getName() == null){
			responseJson.setResult(false);
			responseJson.setMessage(MESSAGE_FALSE);
		}else{
			params.put("admin_id", principal.getName());
			int result = statusService.progressUpdate(params);
			System.out.println("result : " + result);
			responseJson.setResult(result == 0 ? false:true);
			responseJson.setMessage(MESSAGE_TRUE);
		}
		return responseJson;
	}
	
	/**
	 * 상태코드리스트
	 * @return
	 */
	@RequestMapping(value="/status/code/list", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
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
	@RequestMapping(value="/progress/note/insert", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> progressNoteInsert(@RequestBody HashMap<String, Object> params,Principal principal){
		int result = 0;	
		if(params.get("body")!=null && !params.get("body").equals("")){
			//params.put("admin_id", principal.getName());
			params.put("admin_id", "scv");
			result = statusService.progressNoteInsert(params);
		}
		
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		responseJson.setResult(result == 0 ? false:true);
		responseJson.setMessage(result == 0 ? MESSAGE_FALSE:MESSAGE_TRUE);
		return responseJson;
	}
	
	/**
	 * 대출신청 금액 변경
	 * @param params
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/require/amount/update", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> requireAmountUpdate(@RequestBody HashMap<String, Object> params,Principal principal){
		int result = statusService.progressUpdate(params);
		params.put("admin_id", principal.getName());
		if(params.get("body") != null){
			int result2 = statusService.progressNoteInsert(params);	
		}
		
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		responseJson.setResult(result == 0 ? false:true);
		responseJson.setMessage(result == 0 ?MESSAGE_FALSE:MESSAGE_TRUE);
		
		return responseJson;
	}
	
	/**
	 * 대출신청 금액 변경
	 * @param params
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/progress/status/update", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> progressStatusUpdate(@RequestBody HashMap<String, Object> params,Principal principal){
		int result = statusService.progressStatusUpdate(params);
		System.out.println("params:" + params);
		params.put("admin_id", principal.getName());
		if(params.get("body") != null){
			int result2 = statusService.progressNoteInsert(params);	
		}
		
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		responseJson.setResult(result == 0 ? false:true);
		responseJson.setMessage(result == 0 ?MESSAGE_FALSE:MESSAGE_TRUE);
		
		return responseJson;
	}
	
	/**
	 * 대출신청 금액 변경
	 * @param params
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/eform/info/list", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> eformInfoSelectList(@PathVariable String loanId){
		List<EformInfo> list =  statusService.eformInfoSelectList(loanId);
		System.out.println("list : " + list);
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
	@RequestMapping(value="/progress/update", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> progressUpdate(@RequestBody HashMap<String, Object> params,Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();

		System.out.println("params : " + params);
		int result = statusService.progressUpdate(params);
		System.out.println("result : " + result);
		responseJson.setResult(result == 0 ? false:true);
		responseJson.setMessage(result == 0 ? MESSAGE_FALSE:MESSAGE_TRUE);
		return responseJson;
	}
	
	/**
	 * 대출신청 금액 변경
	 * @param params
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/eform/attach/list", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
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
	@RequestMapping(value="/attach/progress/update", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> attachProgressUpdate(@RequestBody HashMap<String, Object> params,Principal principal) throws ParseException{
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		params.put("admin_id",principal.getName());
		System.out.println("params : " + params);
		System.out.println(statusService.progressUpdate(params));
		boolean boo = statusService.attachProgressUpdate(params);
		System.out.println("boo : " + boo);
		if(boo){
			responseJson.setResult(true);
			responseJson.setMessage(MESSAGE_TRUE);
		}else{
			responseJson.setResult(false);
			responseJson.setMessage(MESSAGE_FALSE);
		}
		return responseJson;
	}
	
	/**
	 * 고객 신규등록
	 * @param params
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/eform/userdata/insert", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
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
				responseJson.setMessage(MESSAGE_TRUE);
			}else{
				responseJson.setResult(false);
				responseJson.setMessage(MESSAGE_FALSE);
			}
		}else{

			responseJson.setResult(false);
			responseJson.setMessage(MESSAGE_FALSE);
		}
		
		return responseJson;
	}
	
	/**
	 * 고객 정보수정
	 * @param params
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/eform/userdata/update", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
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
				responseJson.setMessage(MESSAGE_TRUE);
			}else{
				responseJson.setResult(false);
				responseJson.setMessage(MESSAGE_FALSE);
			}
		}else{
			responseJson.setResult(false);
			responseJson.setMessage(MESSAGE_FALSE);
		}
		return responseJson;
	}
	
	/**
	 * user json data 가져오기
	 * @param params
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/ftp/data", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> getFtpJsonData(@RequestBody HashMap<String, Object> params){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		System.out.println("params : " + params);
		System.out.println(statusService.getFtpJsonData(params.get("progressId").toString()));
		//getFtpJsonData(params);
		/*int result = statusService.progressUpdate(params);
		System.out.println("result : " + result);
		responseJson.setResult(result == 0 ? false:true);
		responseJson.setMessage(result == 0 ? "설정 실패":"설정 완료");*/
		return responseJson;
	}

	@RequestMapping(value="/eform/userdata", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> eformUserDataSelectOne(@RequestBody HashMap<String, Object> params){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		EformUserData eformUserData = statusService.eformUserDataSelectOne(params.get("progressId").toString());
		JsonFile jsonFile = statusService.getFtpJsonData(params.get("progressId").toString());
		eformUserData.setReg(jsonFile.getReg());
		eformUserData.setName(jsonFile.getName());
		eformUserData.setBank(jsonFile.getBank());
		eformUserData.setAccount(jsonFile.getAccount());
		eformUserData.setJumin(jsonFile.getJumin());
		
		System.out.println("eformUserData : " + eformUserData);
		responseJson.setResult(eformUserData == null ? false : true);
		responseJson.setMap(eformUserData);
		return responseJson;
	}
	
	@RequestMapping(value="/admin/info/list", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> adminInfoSelectList(@RequestBody HashMap<String, Object> params){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		List<AdminInfo> adminInfoList = statusService.adminInfoSelectList(params);
		responseJson.setResult(true);
		responseJson.setTotalItems(adminInfoList.size() <= 0 ? 0 : adminInfoList.get(0).getTotalCnt());
		responseJson.setResultType("list");
		responseJson.setList(adminInfoList);
		return responseJson;
	}
	
	@RequestMapping(value="/notice/update", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> noticeUpdate(@RequestBody HashMap<String, Object> params){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		int updateStatus = adminNoticeService.noticeUpdate(params);
		
		if(updateStatus > 0){
			responseJson.setResult(true);
			responseJson.setMessage(MESSAGE_TRUE);
		}else{
			responseJson.setResult(false);
			responseJson.setMessage(MESSAGE_FALSE);
		}
		return responseJson;
	}
	
	@RequestMapping(value="/notice/delete", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> noticeDelete(@RequestBody HashMap<String, Object> params){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		int updateStatus = adminNoticeService.noticeDelete(params);

		if(updateStatus > 0){
			responseJson.setResult(true);			
			responseJson.setMessage(MESSAGE_TRUE);
		}else{
			responseJson.setResult(false);
			responseJson.setMessage(MESSAGE_FALSE);
		}
		return responseJson;
	}
	
	@RequestMapping(value="/notice/insert", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> noticeInsert(@RequestBody HashMap<String, Object> params, Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		
		params.put("admin_id",principal.getName());
		int insertStatus = adminNoticeService.noticeInsert(params);
		
		if(insertStatus > 0){
			responseJson.setResult(true);
			responseJson.setMessage(MESSAGE_TRUE);
		}else{
			responseJson.setResult(false);
			responseJson.setMessage(MESSAGE_FALSE);
		}
		return responseJson;
	}
	
	/**
	 * 사용자 정보수정
	 * @param params
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/admin/info/update", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> adminInfoUpdate(@RequestBody HashMap<String, Object> params,Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		//progress insert , json 생성
		params.put("admin_id",principal.getName());
		System.out.println("params : " + params);
		int result = statusService.adminInfoUpdate(params);
		System.out.println("result : " + result);
			
		if(result > 0){
			responseJson.setResult(true);
			responseJson.setMessage(MESSAGE_TRUE);
		}else{
			responseJson.setResult(false);
			responseJson.setMessage(MESSAGE_FALSE);
		}
		return responseJson;
	}
	
	/**
	 * 사용자 정보등록
	 * @param params
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/admin/info/insert", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> adminInfoInsert(@RequestBody HashMap<String, Object> params,Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		//progress insert , json 생성
		params.put("admin_id",principal.getName());
		System.out.println("params : " + params);
		int result = statusService.adminInfoInsert(params);
		System.out.println("result : " + result);
			
		if(result > 0){
			responseJson.setResult(true);
			responseJson.setMessage(MESSAGE_TRUE);
		}else{
			responseJson.setResult(false);
			responseJson.setMessage(MESSAGE_FALSE);
		}
		return responseJson;
	}
	
	/**
	 * 사용자 아이디 중복 체크
	 * @param params
	 * @param principal
	 * @return
	 */
	@RequestMapping(value="/admin/info/chk", method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> adminInfoIdChk(@RequestBody HashMap<String, Object> params,Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		//progress insert , json 생성
		params.put("admin_id",principal.getName());
		System.out.println("params : " + params);
		int result = statusService.adminInfoSelectCnt(params.get("id").toString());
		System.out.println("result : " + result);
			
		if(result > 0){
			responseJson.setResult(false);
			responseJson.setMessage(MESSAGE_FALSE);
		}else{
			responseJson.setResult(true);
			responseJson.setMessage(MESSAGE_TRUE);
		}
		return responseJson;
	}
	
	@RequestMapping(value="/prodmng/list" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> prodMngSelectList(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		 
		params.put("loanId", loanId);
		System.out.println("params : "+ params);
		List<ProdMng> prodMngSelectList = adminMngService.prodMngSelectList(params);
		System.out.println("prodMngSelectList : "+ prodMngSelectList);

		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		responseJson.setResult(true);
		responseJson.setTotalItems(prodMngSelectList.size() <= 0 ? 0 : prodMngSelectList.get(0).getTotalCnt());
		responseJson.setResultType("list");
		responseJson.setList(prodMngSelectList);
		return responseJson;
	}
	
	@RequestMapping(value="/prodmng" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> prodMngSelectOne(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		 
		params.put("loanId", loanId);
		System.out.println("params : "+ params);
		ProdMng prodMngSelectOne = adminMngService.prodMngSelectOne(Integer.parseInt(params.get("seq").toString()));
		prodMngSelectOne.setEformAttach(adminMngService.eformAttachSelectList(Integer.parseInt(params.get("seq").toString())));
		System.out.println("prodMngSelectOne : "+ prodMngSelectOne);

		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		responseJson.setResult(true);
		responseJson.setResultType("map");
		responseJson.setMap(prodMngSelectOne);
		return responseJson;
	}
	
	@RequestMapping(value="/notice" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> noticeSelectOne(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		 
		params.put("loanId", loanId);
		System.out.println("params : "+ params);
		NoticeInfo noticeSelectOne = adminNoticeService.noticeSelectOne(Integer.parseInt(params.get("seq").toString()));
		System.out.println("noticeSelectOne : "+ noticeSelectOne);

		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		responseJson.setResult(true);
		responseJson.setResultType("map");
		responseJson.setMap(noticeSelectOne);
		return responseJson;
	}
	
	@RequestMapping(value="/eform/attach/insert" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> eformAttachInsert(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		params.put("loanId", loanId);
		System.out.println("params : "+ params);
		if( adminMngService.eformAttachInsert(params) > 0){
			responseJson.setResult(true);
			responseJson.setMessage(MESSAGE_TRUE);
		}else{
			responseJson.setResult(false);
			responseJson.setMessage(MESSAGE_FALSE);
		}
		return responseJson;
	}
	
	@RequestMapping(value="/loan/info/list" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> loanInfoSelectList(@RequestBody HashMap<String,Object> params){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		List<LoanInfo> list = adminMngService.loanInfoSelectList() ;
		responseJson.setResult(true);
		responseJson.setResultType("list");
		responseJson.setList(list);
	
		return responseJson;
	}
	
	@RequestMapping(value="/eform/info/insert" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> eformInfoInsert(
			@RequestParam("eform_attach_insert_file") MultipartFile uploadfile , 
			@RequestParam HashMap<String,Object> params,HttpServletRequest request ,
			Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		params.put("admin_id",principal.getName());
		System.out.println(request.getSession().getServletContext().getRealPath("WEB-INF"));
		if(FileFtpHandler.writeFile(uploadfile, request.getSession().getServletContext().getRealPath("WEB-INF") + "\\clipreport4", uploadfile.getOriginalFilename()) && uploadfile != null){
			params.put("eform_path",request.getSession().getServletContext().getRealPath("WEB-INF") + "\\clipreport4\\"+uploadfile.getOriginalFilename());
			//if( adminMngService.eformInfoInsert(params) > 0){
			if( adminMngService.eformInfoInsertAsMultipart(params) > 0){
				responseJson.setResult(true);
				responseJson.setMessage(MESSAGE_TRUE);
			}else{
				responseJson.setResult(false);
				responseJson.setMessage(MESSAGE_FALSE);
			}
		}else{
			responseJson.setResult(false);
			responseJson.setMessage(MESSAGE_FALSE);
		}
		return responseJson;
	}
	
	@RequestMapping(value="/eform/info/update" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> eformInfoUpdate(
			@RequestParam("eform_attach_update_file") MultipartFile uploadfile , 
			@RequestParam HashMap<String,Object> params,HttpServletRequest request,Principal principal){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		params.put("admin_id",principal.getName());
		System.out.println("params : " + params);
		if(!uploadfile.getOriginalFilename().equals("")){
			FileFtpHandler.writeFile(uploadfile, request.getSession().getServletContext().getRealPath("WEB-INF") + "\\clipreport4", uploadfile.getOriginalFilename());
			params.put("eform_path",request.getSession().getServletContext().getRealPath("WEB-INF") + "\\clipreport4\\"+uploadfile.getOriginalFilename());
		}
		//if( adminMngService.eformInfoInsert(params) > 0){
		try {
			if( adminMngService.eformInfoUpdateAsMultipart(params) > 0){
				responseJson.setResult(true);
				responseJson.setMessage(MESSAGE_TRUE);
			}else{
				responseJson.setResult(false);
				responseJson.setMessage(MESSAGE_FALSE);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return responseJson;
	}
	
	@RequestMapping(value="/notice/list" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
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
	
	@RequestMapping(value="/eform/delete" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> eformDelete(@RequestBody HashMap<String,Object> params){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		int i = adminMngService.eformDelete(params);
		responseJson.setResult((i>0)?true:false);
		responseJson.setMessage((i>0)?MESSAGE_TRUE:MESSAGE_FALSE);
	
		return responseJson;
	}
	//adminInfoDel
	
	@RequestMapping(value="/admin/info/delete" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> adminInfoDelete(@RequestBody HashMap<String,Object> params){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		System.out.println("params: " + params);
		
		int i = statusService.eformDelete(params);
		responseJson.setResult((i>0)?true:false);
		responseJson.setMessage((i>0)?MESSAGE_TRUE:MESSAGE_FALSE);
	
		return responseJson;
	}
}