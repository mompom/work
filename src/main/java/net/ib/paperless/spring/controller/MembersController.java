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

//18.07.06 김웅희 추가
@Controller
@RequestMapping("/{loanId}/members/*")
public class MembersController{
	
	@Autowired
	AuthenticationService authenticationService;
	
	//기본형
	@RequestMapping(value="/index",method={RequestMethod.GET , RequestMethod.POST})
	public String members(Model model , @PathVariable String loanId, Principal principal){
		
		User user = authenticationService.loadUserOpenApi(principal.getName());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("loanId", loanId);
		map.put("admin_name", user.getName());
		map.put("admin_level", user.getLevel());
		map.put("level_name", user.getLevel_name());
		
		map.put("submenu","members");
		map.put("topmenu","members_mng");
		model.addAttribute("map", map);
		return "/members/index";
	}
	
	@RequestMapping(value="/meritz",method={RequestMethod.GET , RequestMethod.POST})
	public String meritz(Model model , @PathVariable String loanId, Principal principal){
		
		User user = authenticationService.loadUserOpenApi(principal.getName());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("loanId", loanId);
		map.put("admin_name", user.getName());
		map.put("admin_level", user.getLevel());
		map.put("level_name", user.getLevel_name());
		
		map.put("submenu","meritz");
		map.put("topmenu","members_mng");
		model.addAttribute("map", map);
		return "/members/meritz";
	}
}