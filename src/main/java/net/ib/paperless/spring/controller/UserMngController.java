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
@RequestMapping("/{loanId}/user_mng/*")
public class UserMngController {

	@Autowired
	AuthenticationService authenticationService;
	
	@RequestMapping(value="/user_mng",method={RequestMethod.GET , RequestMethod.POST})
	public String loan(Model model , @PathVariable String loanId, Principal principal){
		
		User user = authenticationService.loadUserOpenApi(principal.getName());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("loanId", loanId);
		map.put("admin_name", user.getId());
		map.put("admin_level", user.getLevel());
		map.put("level_name", user.getLevel_name());
		
		map.put("submenu","user_mng");
		map.put("topmenu","user_mng");
		model.addAttribute("map", map);
		return "/user_mng/user_mng";
	}
}