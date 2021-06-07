package net.ib.paperless.spring.support;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import net.ib.paperless.spring.domain.User;
import net.ib.paperless.spring.service.AuthenticationService;

/**
 * @author BRKIM-IBD1 김범래
 * @since 2017.06.12
 * open api 유저만 처리 되게 분리
 */
@Component
public class OpenApiUserSessionInterceptor extends HandlerInterceptorAdapter{
	
	static final String HEADER_STRING = "Authorization";
	
	@Autowired
    AuthenticationService authenticationService;
	
	@Override 
	public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//System.out.println("============preHandle open api");
		// HTTP 요청 처리 전 수행할 로직 작성 
		String token = request.getHeader(HEADER_STRING);
		Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		try{
			if (!StringUtils.hasText(token)) {
				response.sendRedirect("/open_api/error/token");
			}else{
				if(!StringUtils.isEmpty(token)&&!StringUtils.isEmpty(pathVariables.get("loanId"))){
					//토큰이 널 아니면 loanId 를 확인 해야 한다. (loanID 위변조 방지)
					User user = authenticationService.loadUserOpenApi(SecurityContextHolder.getContext().getAuthentication().getName());
					//System.out.println(user + "openapiusersessioninterceptor");
					if(!StringUtils.isEmpty(user)){
						if(!user.getLoan_id().equals(pathVariables.get("loanId"))){
							response.sendRedirect("/open_api/error/loanid");
						}
					}else{
						response.sendRedirect("/open_api/error/token");
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return true; 
	}
	
	@Override 
	public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView )throws Exception { 
		// HTTP 요청 처리 후 수행할 로직 작성 }
		//System.out.println("============postHandle open api");
	}

}