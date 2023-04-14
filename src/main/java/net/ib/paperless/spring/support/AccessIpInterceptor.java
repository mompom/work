package net.ib.paperless.spring.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import net.ib.paperless.spring.common.IpUtil;

/**
 * @author 양우정
 * @since 2023.04.13
 * 허용된 IP만 접근 되도록 처리 
 */
@Component
public class AccessIpInterceptor extends HandlerInterceptorAdapter{
	
	static final String HEADER_STRING = "Authorization";
		
	@Override 
	public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("============preHandle AccessIpInterceptor");
		// HTTP 요청 처리 전 수행할 로직 작성 
		String clientIp = IpUtil.getClientIpAddr(request);
		System.out.println(clientIp);
		if ("222.108.100.216".equals(clientIp)// 티소프트 본사 네트워크 IP
			|| "39.117.40.3".equals(clientIp)// 인포뱅크 공인 IP 
			|| "0:0:0:0:0:0:0:1".equals(clientIp) // 로컬 IPv6 
			|| "127.0.0.1".equals(clientIp) // 로컬 IPv4 
			|| "175.197.92.204".equals(clientIp)) // 디딤365 
		{
			return true; 
		} else {
			response.sendRedirect("/open_api/error/no-allow-ip");
			return false; 
		}
	}
	
	@Override 
	public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView )throws Exception { 
		// HTTP 요청 처리 후 수행할 로직 작성 }
		//System.out.println("============postHandle AccessIpInterceptor");
	}

}