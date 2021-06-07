package net.ib.paperless.spring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.ib.paperless.spring.common.ApiResponse;
import net.ib.paperless.spring.domain.MembersMeritzInfo;
import net.ib.paperless.spring.domain.MembersMeritzTotalCntInfo;
import net.ib.paperless.spring.service.MembersService;

@RestController
@RequestMapping("/{loanId}/api/meritz/*")
public class MembersApiController {
	private static final Logger logger = LoggerFactory.getLogger(MembersApiController.class);

	@Autowired
	MembersService membersService;
	
	@RequestMapping(value="/selectTotalUsedList" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> selectTotalUsedList(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		List<MembersMeritzTotalCntInfo> list = membersService.selectTotalUsedList(params);
		
		/*for (MembersMeritzTotalCntInfo asd : list) {
			System.out.println(asd.getTypeNumber() + " " + asd.getTotalCnt());
		}*/
		params.put("loanId", loanId);
		
		responseJson.setResult(true);
		responseJson.setTotalItems(list.size() <= 0 ? 0 : list.size());
		responseJson.setResultType("list");
		responseJson.setList(list);
		return responseJson;
	}
	
	@RequestMapping(value="/selectUsedList" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> selectUsedList(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		List<MembersMeritzInfo> list = membersService.selectUsedList(params);
		
		params.put("loanId", loanId);
		
		responseJson.setResult(true);
		responseJson.setTotalItems(list.size() <= 0 ? 0 : list.size());
		responseJson.setResultType("list");
		responseJson.setList(list);
		return responseJson;
	}
	
	@RequestMapping(value="/countUsedList" , method={RequestMethod.GET , RequestMethod.POST},produces="application/json")
	public ApiResponse<Map<String,Object>> countUsedList(@RequestBody HashMap<String,Object> params ,@PathVariable String loanId){
		ApiResponse<Map<String , Object>> responseJson = new ApiResponse<Map<String,Object>>();
		int totCnt = membersService.countUsedList(params);
		responseJson.setResult(true);
		responseJson.setTotalItems(totCnt <= 0 ? 0 : totCnt);
		return responseJson;
	}
	
}
