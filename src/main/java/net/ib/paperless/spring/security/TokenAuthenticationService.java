package net.ib.paperless.spring.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import net.ib.paperless.spring.service.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security
            .authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


class TokenAuthenticationService {
	static final long EXPIRATIONTIME = 36_000_000; // 10 days   15분 900_000;  10시간 : 36_000_000
	static final String SECRET = "ThisIsASecret";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";
  
	@Autowired
	AuthenticationService authenticationService;

	static void addAuthentication(HttpServletResponse res, String username, String password) {
		String JWT = Jwts.builder()
				.setSubject(username)
				.setIssuer(password)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	}

	static Authentication getAuthentication(HttpServletRequest request) throws SignatureException{
		
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			// parse the token.
			String user = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody()
					.getSubject();
			String pw = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody()
					.getIssuer();
			//return user != null ?new UsernamePasswordAuthenticationToken(user, null) :null;
			return user != null ?new UsernamePasswordAuthenticationToken(user, pw, AuthorityUtils.createAuthorityList("ROLE_USER")) :null;
		}
		return null;
	}
}