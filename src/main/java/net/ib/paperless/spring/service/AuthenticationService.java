package net.ib.paperless.spring.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.ib.paperless.spring.repository.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException{
		UserDetails ud = null;
		net.ib.paperless.spring.domain.User user = userRepository.findByIdIgnoreCase(id);
		if (user == null) {
			UsernameNotFoundException ue = new UsernameNotFoundException("Not found id");
			throw ue;
		}
		ud = (UserDetails) new User(id, user.getPasswd(), AuthorityUtils.createAuthorityList("ROLE_USER"));

        return ud;
	}
	
	public Integer userLevelSelectOne(String id) throws UsernameNotFoundException{
	
		HashMap<String, Object> user = userRepository.userLevelSelectOne(id);
		
        return (Integer) user.get("level");
	}
	
	
	/**
	 * 론아이디를 가져와서 비교 하기 위하여 작성
	 * @author BRKIM-IBD1(김범래)
	 * @param id
	 * @since 2017.06.16
	 * @return
	 * @throws UsernameNotFoundException
	 */
	public net.ib.paperless.spring.domain.User loadUserOpenApi(String id) throws UsernameNotFoundException{
		net.ib.paperless.spring.domain.User user = userRepository.findByIdIgnoreCase(id);
        return user;
	}
}