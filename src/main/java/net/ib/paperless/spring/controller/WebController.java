package net.ib.paperless.spring.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.ib.paperless.spring.domain.User;
import net.ib.paperless.spring.service.AuthenticationService;

//import net.ib.paperless.spring.service.DbService;

@Controller
public class WebController {
	//@Autowired
    //DbService dbService;
	@Autowired
    AuthenticationService authenticationService;
	
	@RequestMapping(value = "/", method={RequestMethod.GET , RequestMethod.POST})
	public String index(Model model, HttpServletRequest request) {
		String referrer = request.getHeader("Referer");
		//model.addAttribute("menu", "dashboard");
		
		User user = authenticationService.loadUserOpenApi(SecurityContextHolder.getContext().getAuthentication().getName());
		String loanId = user.getLoan_id();
		
		return "redirect:/"+loanId+"/admin/notice";
		//return "index.tiles";
	}
	
	@RequestMapping("/empty")
	public String empty(Model model) {
		//System.out.println("test : " + dbService.getDual());
		model.addAttribute("menu", "empty");
		return "empty.tiles";
	}
	
	@RequestMapping("/login")
	public String login(Model model , HttpServletRequest request) {
	    String referrer = request.getHeader("Referer");
	    //System.out.println("referrer : " + referrer);
	    //abc캐피탈 초기화면
	    //request.getSession().setAttribute("prevPage", "/abc/status/loan");
	    
	    //System.out.println("request.getSession() "  + request.getSession().getAttribute("prevPage"));
	    //model.addAttribute("base", "http://localhost:9080/");
	    
	    //request.getSession().setAttribute("prevPage", "/goodpaper/admin_mng/notice");
	    request.getSession().setAttribute("prevPage", "/admin/notice");
		return "login";
	}
	
	@RequestMapping("/detail")
	public String detail(Model model) {
		//model.addAttribute("menu", "empty");
		return "detail";
	}

	
	/*@RequestMapping("/error")
	public String error(Model model) {
		//model.addAttribute("menu", "empty");
		return "redirect:/sanwa/status/loan";
	}*/
}
