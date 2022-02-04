package net.ib.paperless.admin;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.ib.paperless.spring.security.PasswordEncoding;

public class AdminPwdTest {

	@Test
	public void test() {
	
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		PasswordEncoding passwordEncoding = new PasswordEncoding(passwordEncoder);
		
		String password ="1234";
		String encryptPassword = passwordEncoder.encode(password);

		System.out.println( encryptPassword );
		System.out.println(passwordEncoding.matches("1234", encryptPassword));
		
	}
	
}