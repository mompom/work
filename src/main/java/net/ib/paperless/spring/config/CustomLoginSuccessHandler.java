package net.ib.paperless.spring.config;

import lombok.RequiredArgsConstructor;
import net.ib.paperless.spring.common.IpUtil;
import net.ib.paperless.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    public CustomLoginSuccessHandler(String defaultTargetUrl) {
        setDefaultTargetUrl(defaultTargetUrl);
    }

    @Autowired
    private UserRepository userRepository;

    @SuppressWarnings("unused")
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
    	Authentication authentication) throws ServletException, IOException {
        HttpSession session = request.getSession();

        try {

            HashMap<String, Object> userMap = new HashMap<>();
            userMap.put("id", authentication.getName());
            userMap.put("login_yn", "Y");
            userMap.put("ip_address",IpUtil.getClientIpAddr(request));
            userRepository.userLoginHistoryInsert(userMap);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (session != null) {
        	String redirectUrl = "/".concat(authentication.getName());
        	redirectUrl = redirectUrl.concat((String) session.getAttribute("prevPage"));

            //redirectUrl = (String) session.getAttribute("prevPage");
            if (redirectUrl != null && redirectUrl.indexOf("common.css") < 0) {
                session.removeAttribute("prevPage");
                getRedirectStrategy().sendRedirect(request, response, redirectUrl);
            } else {
                super.onAuthenticationSuccess(request, response, authentication);
            }
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}