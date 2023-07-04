package net.ib.paperless.spring.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import net.ib.paperless.spring.domain.User;
import net.ib.paperless.spring.security.JWTAuthenticationFilter;
import net.ib.paperless.spring.security.JWTLoginFilter;
import net.ib.paperless.spring.security.PasswordEncoding;
import net.ib.paperless.spring.service.AuthenticationService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
	
    @Autowired
    AuthenticationService authenticationService;
    
    @Autowired
    private HttpSession httpSession;
    
    @Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()
            .authorizeRequests()
            .antMatchers("/**/api/**").permitAll()
            .antMatchers("/**/open_api/login").permitAll()
            .antMatchers("/**/open_api/**").permitAll()
            .antMatchers("/open_api/error/**").permitAll()
            .antMatchers("/**/admin/company_mng/**").access("hasAuthority('ADMIN')")
            .antMatchers("/**/admin/user_mng/**").access("hasAuthority('ADMIN')")
            .antMatchers("/**/authHistory/**").hasAnyAuthority("ADMIN", "ROLE_USER")
            .antMatchers("/**/service/**").access("hasAuthority('ADMIN')")
            .antMatchers("/**/members/**").hasAnyAuthority("ADMIN", "ROLE_USER")
            .anyRequest().authenticated()
            .and()
            // We filter the api/login requests //작엄 내용 : open api 토큰 로그인 필터 적용. 작업자 : 김범래, 작업일 : 2017.06.12
            .addFilterBefore(new JWTLoginFilter("/**/open_api/login", authenticationManager()),
                    UsernamePasswordAuthenticationFilter.class)
            // And filter other requests to check the presence of JWT in header
            .addFilterBefore(new JWTAuthenticationFilter(),
                    UsernamePasswordAuthenticationFilter.class)
            .formLogin()
            	.loginPage("/login")
            	.loginProcessingUrl("/login_process")
            	//.defaultSuccessUrl("/sanwa/status/loan")
            	.failureUrl("/login?error=loginFailed")
            	.successHandler(successHandler())
            	.usernameParameter("id")
            	.passwordParameter("password")
            	.permitAll()
            	.and()
            .logout();
    }
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        /*auth
            .inMemoryAuthentication()
                .withUser("test").password("1").roles("USER");*/
    	auth.authenticationProvider(new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String id = authentication.getName();
                String password = (String) authentication.getCredentials();
                
                //System.out.println("username : " + id + " // password : " + password );
                // 이곳에서 DB에 저장된 사용자 정보(username, password)를 읽어들인후
                UserDetails ud = authenticationService.loadUserByUsername(id);
            	// 이곳에서 html page에서 사용자가 입력한 값과 비교를 하여, 인증처리를 진행한다.
                
                
                //암호화 추가
				PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				PasswordEncoding passwordEncoding = new PasswordEncoding(passwordEncoder);
                	
                /*if (ud.getPassword().equals(password)) {*/
				if (passwordEncoding.matches(password, ud.getPassword())) {
                    List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
                    grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
                    
                    User user = authenticationService.loadUserOpenApi((String)authentication.getPrincipal());
                    int userLevel = user.getLevel();
                    
                    Authentication auth = new UsernamePasswordAuthenticationToken(id, password, AuthorityUtils.createAuthorityList("ROLE_USER"));
                    if(userLevel == 1) {
                    	auth = new UsernamePasswordAuthenticationToken(id, password, AuthorityUtils.createAuthorityList("ADMIN"));
                    }
                    
                    httpSession.setAttribute("user", id);
                    return auth;
                }
                return null;
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return authentication.equals(UsernamePasswordAuthenticationToken.class);
            }
        });
    }
    
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomLoginSuccessHandler("/detail");
    }
}