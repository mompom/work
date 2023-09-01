package net.ib.paperless.spring.support;

import net.ib.paperless.spring.domain.User;
import net.ib.paperless.spring.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class UserSessionInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
    AuthenticationService authenticationService;
	
	@Override 
	public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception { 
		// HTTP 요청 처리 전 수행할 로직 작성 

		@SuppressWarnings("unchecked")
		Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

		String loanId = (String) pathVariables.get("loanId");
		String getLoanId;
		if(SecurityContextHolder.getContext().getAuthentication() != null) {
			User user = authenticationService.loadUserOpenApi(SecurityContextHolder.getContext().getAuthentication().getName());
			getLoanId = user.getLoan_id();
			if(!getLoanId.equals(loanId)){
				pathVariables.put("loanId", getLoanId);
			}
		}
		
		return true; 
	}
	
	@Override 
	public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView )throws Exception { 
		// HTTP 요청 처리 후 수행할 로직 작성 }
		//System.out.println("============postHandle");
	}

}