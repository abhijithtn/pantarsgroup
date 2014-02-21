package org.jss.polytechnic.security;

import java.util.LinkedList;
import java.util.List;

import org.jss.polytechnic.bean.UserInfo;
import org.jss.polytechnic.bean.UserRole;
import org.jss.polytechnic.dao.LoginDao;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class CustomAuthentication implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication paramAuthentication)
			throws AuthenticationException {
		String userName = paramAuthentication.getPrincipal().toString();
		String password = paramAuthentication.getCredentials().toString();
		LoginDao dao = new LoginDao();
		UserInfo userInfo = new UserInfo();
		userInfo.setUsername(userName);
		userInfo.setPassword(password);

		try {
			dao.validateCredentials(userInfo);
		} catch (Exception e) {
			userInfo.setRole(0);
			e.printStackTrace();
		}

		List<GrantedAuthority> authorities = new LinkedList<GrantedAuthority>();

		if (userInfo.getRole() != 0) {
			if (userInfo.getRole() == 2) {
				authorities.add(new SimpleGrantedAuthority(UserRole.ROLE_ADMIN
						.toString()));
				authorities.add(new SimpleGrantedAuthority(UserRole.ROLE_USER
						.toString()));
			} else {
				authorities.add(new SimpleGrantedAuthority(UserRole.ROLE_USER
						.toString()));
			}

			return new UsernamePasswordAuthenticationToken(userName, password,
					authorities);
		}

		throw new BadCredentialsException("Invalid Username or Password");
	}

	@Override
	public boolean supports(Class<?> paramClass) {
		return false;
	}

}
