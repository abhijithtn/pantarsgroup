package org.jss.polytechnic.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.ServletException;

import org.jss.polytechnic.bean.UserInfo;
import org.jss.polytechnic.utils.ApplicationContextProvider;
import org.jss.polytechnic.utils.JsfUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;

@ManagedBean(name = "loginController")
@RequestScoped
public class LoginController implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4103888558748962458L;

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

	@Override
	public void afterPhase(PhaseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		Exception e = (Exception) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap()
				.get(WebAttributes.AUTHENTICATION_EXCEPTION);

		e.printStackTrace();

		if (e instanceof BadCredentialsException) {
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap()
					.put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
			JsfUtils.addErrorMessage("Invalid Username or Password");
		}

	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

}
