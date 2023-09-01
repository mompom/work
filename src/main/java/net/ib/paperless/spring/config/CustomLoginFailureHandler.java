package net.ib.paperless.spring.config;

import net.ib.paperless.spring.common.IpUtil;
import net.ib.paperless.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

@Service
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String username = request.getParameter("id");
        String errorMessage;

        if(exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException) {
            errorMessage = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
        } else if (exception instanceof DisabledException) {
            errorMessage = "비활성화된 계정입니다. 10분뒤 다시 시도해주세요.";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            errorMessage = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
        } else {
            errorMessage = "알 수 없는 오류로 로그인 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
        }

        if (username != null) {
            HashMap<String, Object> userMap = new HashMap<>();
            userMap.put("id", username);
            userMap.put("login_yn", "N");
            userMap.put("ip_address", IpUtil.getClientIpAddr(request));
            userMap.put("err_msg", errorMessage);
            userRepository.userLoginHistoryInsert(userMap);

            // 이미 비활성화된 경우 실패 횟수 증가시키지 않음
            if(!(exception instanceof DisabledException)) userRepository.incrementLoginFailCount(userMap);

        }

        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
        setDefaultFailureUrl("/login?error=" + errorMessage);

        super.onAuthenticationFailure(request, response, exception);
    }

}