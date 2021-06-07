package net.ib.paperless.spring.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import net.ib.paperless.spring.domain.User;
import net.ib.paperless.spring.service.AuthenticationService;

@Controller
@RequestMapping("/{loanId}/stats/*")
public class StatsController{
	
	@Autowired
	AuthenticationService authenticationService;
	
	@RequestMapping("/id")
	public String id(Model model , @PathVariable String loanId, Principal principal){

		User user = authenticationService.loadUserOpenApi(principal.getName());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("loanId", loanId);
		map.put("admin_name", user.getId());
		map.put("admin_level", user.getLevel());
		map.put("level_name", user.getLevel_name());
		
		map.put("submenu","id");
		map.put("topmenu","stats");
		model.addAttribute("map", map);
		return "/stats/id";
	}

	
	@RequestMapping("/month")
	public String month(Model model , @PathVariable String loanId, Principal principal){

		User user = authenticationService.loadUserOpenApi(principal.getName());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("loanId", loanId);
		map.put("admin_name", user.getId());
		map.put("admin_level", user.getLevel());
		map.put("level_name", user.getLevel_name());
		
		map.put("submenu","month");
		map.put("topmenu","stats");
		model.addAttribute("map", map);
		return "/stats/month";
	}

	
	@RequestMapping("/year")
	public String year(Model model , @PathVariable String loanId, Principal principal){

		User user = authenticationService.loadUserOpenApi(principal.getName());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("loanId", loanId);
		map.put("admin_name", user.getId());
		map.put("admin_level", user.getLevel());
		map.put("level_name", user.getLevel_name());
		
		map.put("submenu","year");
		map.put("topmenu","stats");
		model.addAttribute("map", map);
		return "/stats/year";
	}
}