package net.ib.paperless.spring.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.ib.paperless.spring.domain.User;
import net.ib.paperless.spring.service.AuthenticationService;

//18.04.26 김웅희 추가
@Controller
@RequestMapping("/{loanId}/service/*")
public class ServiceContoroller{
	
	@Autowired
	AuthenticationService authenticationService;
	
	@RequestMapping(value="/domainManage",method={RequestMethod.GET , RequestMethod.POST})
	public String domain_mng(Model model , @PathVariable String loanId, Principal principal){
		//System.out.println("domain");
		
		User user = authenticationService.loadUserOpenApi(principal.getName());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("loanId", loanId);
		map.put("admin_name", user.getName());
		map.put("admin_level", user.getLevel());
		map.put("level_name", user.getLevel_name());
		
		map.put("submenu","domainManage");
		map.put("topmenu","service_mng");
		model.addAttribute("map", map);
		return "/service/domainManage";
	}
	
	@RequestMapping(value="/customerChargeManage",method={RequestMethod.GET , RequestMethod.POST})
	public String customerChargeManage(Model model , @PathVariable String loanId, Principal principal){
		//System.out.println("domain");
		
		User user = authenticationService.loadUserOpenApi(principal.getName());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("loanId", loanId);
		map.put("admin_name", user.getName());
		map.put("admin_level", user.getLevel());
		map.put("level_name", user.getLevel_name());
		
		map.put("submenu","customerChargeManage");
		map.put("topmenu","service_mng");
		model.addAttribute("map", map);
		return "/service/customerChargeManage";
	}
	
	@RequestMapping(value="/serviceManage",method={RequestMethod.GET , RequestMethod.POST})
	public String serviceManage(Model model , @PathVariable String loanId, Principal principal){
		//System.out.println("domain");
		
		User user = authenticationService.loadUserOpenApi(principal.getName());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("loanId", loanId);
		map.put("admin_name", user.getName());
		map.put("admin_level", user.getLevel());
		map.put("level_name", user.getLevel_name());
		
		map.put("submenu","serviceManage");
		map.put("topmenu","service_mng");
		model.addAttribute("map", map);
		return "/service/serviceManage";
	}
	
	@RequestMapping(value="/blockUser",method={RequestMethod.GET , RequestMethod.POST})
	public String blockUser(Model model ,@PathVariable String loanId, Principal principal){
		
		User user = authenticationService.loadUserOpenApi(principal.getName());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("loanId", loanId);
		map.put("admin_name", user.getName());
		map.put("admin_level", user.getLevel());
		map.put("level_name", user.getLevel_name());
		
		map.put("submenu","blockUser");
		map.put("topmenu","service_mng");
		model.addAttribute("map", map);
		return "/service/blockUser";
	}
}