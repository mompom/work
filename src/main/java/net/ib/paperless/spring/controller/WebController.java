package net.ib.paperless.spring.controller;

import lombok.RequiredArgsConstructor;
import net.ib.paperless.spring.common.IpUtil;
import net.ib.paperless.spring.domain.User;
import net.ib.paperless.spring.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequiredArgsConstructor
public class WebController {

	private final AuthenticationService authenticationService;
	
	private static final Logger logger = LoggerFactory.getLogger(WebController.class);
	
	@RequestMapping(value = "/", method={RequestMethod.GET , RequestMethod.POST})
	public String index(Model model, HttpServletRequest request) {

		User user = authenticationService.loadUserOpenApi(SecurityContextHolder.getContext().getAuthentication().getName());
		String loanId = user.getLoan_id();
		
		return "redirect:/"+loanId+"/admin/notice";
	}
	
	@RequestMapping("/empty")
	public String empty(Model model) {
		model.addAttribute("menu", "empty");
		return "empty.tiles";
	}
	
	@RequestMapping("/login")
	public String login(Model model , HttpServletRequest request) {

 	    request.getSession().setAttribute("prevPage", "/admin/notice");

	    String clientIp = IpUtil.getClientIpAddr(request);
	    logger.info("##### clientIp "+clientIp);
		if ("222.108.100.216".equals(clientIp)// 티소프트 본사 네트워크 IP
			|| "39.117.40.3".equals(clientIp)// 인포뱅크 공인 IP 
			|| "0:0:0:0:0:0:0:1".equals(clientIp) // 로컬 IPv6 
			|| "127.0.0.1".equals(clientIp) // 로컬 IPv4 
			|| "175.197.92.204".equals(clientIp)) // 디딤365 
		{
			return "login";
		} else {
			return "error/error";
		}		
	    
	}
	
	@RequestMapping("/detail")
	public String detail(Model model) {
		//model.addAttribute("menu", "empty");
		return "detail";
	}

}
