package org.jss.polytechnic.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.ServletException;

import org.jss.polytechnic.bean.UserInfo;
import org.jss.polytechnic.utils.ApplicationContextProvider;
import org.jss.polytechnic.utils.JsfUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

@ManagedBean(name = "loginController")
@RequestScoped
public class LoginController {

	private UserInfo userInfo;

	private boolean hasLoggedIn;

	@PostConstruct
	public void postContruct() {
		userInfo = new UserInfo();
		hasLoggedIn = false;
	}

	/**
	 * @return the userInfo
	 */
	public UserInfo getUserInfo() {
		return userInfo;
	}

	/**
	 * @param userInfo
	 *            the userInfo to set
	 */
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * @return the hasLoggedIn
	 */
	public boolean isHasLoggedIn() {
		return hasLoggedIn;
	}

	public String login() throws ServletException, IOException {

		try {

			AuthenticationManager manager = (AuthenticationManager) ApplicationContextProvider
					.getBean("authenticationManager");

			Authentication request = new UsernamePasswordAuthenticationToken(
					userInfo.getUsername(), userInfo.getPassword());

			Authentication result = manager.authenticate(request);

			SecurityContextHolder.getContext().setAuthentication(result);

		} catch (AuthenticationException e) {
			e.printStackTrace();
			JsfUtils.addErrorMessage("Invalid Username or Password");
			return null;
		}

		return "success";
	}
}
