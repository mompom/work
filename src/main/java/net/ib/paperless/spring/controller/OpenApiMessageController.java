package net.ib.paperless.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open_api/*")
public class OpenApiMessageController {
	
	@RequestMapping("/error/loanid")
	public @ResponseBody String errorLoanId() {
		return "{\"code\":\"400\", \"detail\":\"not match loan id\"}";
	}
	
	@RequestMapping("/error/token")
	public @ResponseBody String errorToken() {
		return "{\"code\":\"400\", \"detail\":\"not exist token\"}";
	}
}