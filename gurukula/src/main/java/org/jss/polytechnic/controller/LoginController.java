package org.jss.polytechnic.controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.jss.polytechnic.bean.UserInfo;
import org.jss.polytechnic.dao.LoginDao;

@ManagedBean(name = "loginController")
@SessionScoped
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

	public String login() {

		System.err.println(userInfo);

		LoginDao dao = new LoginDao();

		dao.validateCredentials(userInfo);

		if (userInfo.getRole() != 0) {
			hasLoggedIn = true;
			return "welcome";
		}

		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Invalid username or password.", "Login denied"));

		return null;
	}

}
