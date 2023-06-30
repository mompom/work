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

@Controller
@RequestMapping("/{loanId}/admin/*")
public class AdminController{
	
	@Autowired
	AuthenticationService authenticationService;
	
	@RequestMapping(value="/prod_mng",method={RequestMethod.GET , RequestMethod.POST})
	public String prodMng(Model model ,@PathVariable String loanId, Principal principal){
		
		User user = authenticationService.loadUserOpenApi(principal.getName());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("loanId", loanId);
		map.put("admin_name", user.getName());
		map.put("admin_level", user.getLevel());
		map.put("level_name", user.getLevel_name());
		
		map.put("submenu","prod_mng");
		map.put("topmenu","admin_mng");
		model.addAttribute("map", map);
		return "/admin/prod_mng";
	}
	
	@RequestMapping(value="/company_mng",method={RequestMethod.GET , RequestMethod.POST})
	public String companyMng(Model model ,@PathVariable String loanId, Principal principal){
		
		User user = authenticationService.loadUserOpenApi(principal.getName());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("loanId", loanId);
		map.put("admin_name", user.getName());
		map.put("admin_level", user.getLevel());
		map.put("level_name", user.getLevel_name());
		
		map.put("submenu","company_mng");
		map.put("topmenu","admin_mng");
		model.addAttribute("map", map);
		return "/admin/company_mng";
	}
	
	@RequestMapping(value="/user_mng",method={RequestMethod.GET , RequestMethod.POST})
	public String userMng(Model model ,@PathVariable String loanId, Principal principal){
		
		User user = authenticationService.loadUserOpenApi(principal.getName());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("loanId", loanId);
		map.put("admin_name", user.getName());
		map.put("admin_level", user.getLevel());
		map.put("level_name", user.getLevel_name());
		
		map.put("submenu","user_mng");
		map.put("topmenu","admin_mng");
		model.addAttribute("map", map);
		return "/admin/user_mng";
	}
	
	@RequestMapping(value="/notice",method={RequestMethod.GET , RequestMethod.POST})
	public String notice(Model model ,@PathVariable String loanId, Principal principal){
		User user = authenticationService.loadUserOpenApi(principal.getName());
		Map<String,Object> map = new HashMap<String,Object>();
		
		//map.put("loanId", loanId);
		map.put("loanId", loanId);
		map.put("admin_name", user.getName());
		map.put("admin_level", user.getLevel());
		map.put("level_name", user.getLevel_name());
		map.put("need_chg_password", user.getChg_password());
		
		map.put("submenu","notice");
		map.put("topmenu","admin_mng");
		model.addAttribute("map", map);
		
		return "/admin/notice";
	}
}