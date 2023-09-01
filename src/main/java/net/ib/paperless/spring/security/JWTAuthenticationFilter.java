package net.ib.paperless.spring.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import net.ib.paperless.spring.service.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

public class JWTAuthenticationFilter extends GenericFilterBean {

    static final String HEADER_STRING = "Authorization";

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {
        try {
            //match token
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String headerString = httpServletRequest.getHeader(HEADER_STRING);
            if (StringUtils.hasText(headerString)) {
                Authentication authentication = TokenAuthenticationService.getAuthentication(httpServletRequest);
                //set auth info
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (SignatureException e) {
            //redirect error message url when not match token
            throw new AuthenticationServiceException("not match token");
        } catch (ExpiredJwtException e) {
            //redirect error message url when not match token
            throw new AuthenticationServiceException("token expired");
        }
    }
}
