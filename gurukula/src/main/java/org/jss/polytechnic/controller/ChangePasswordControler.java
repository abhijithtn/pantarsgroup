package org.jss.polytechnic.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.jss.polytechnic.dao.LoginDao;
import org.jss.polytechnic.utils.JsfUtils;

@ManagedBean(name = "passwordController")
@RequestScoped
public class ChangePasswordControler {

	private String currentPassword;

	private String newPassword;

	private String confirmPassword;

	/**
	 * @return the currentPassword
	 */
	public String getCurrentPassword() {
		return currentPassword;
	}

	/**
	 * @param currentPassword
	 *            the currentPassword to set
	 */
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword
	 *            the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword
	 *            the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String submit() {
		LoginDao dao = new LoginDao();
		try {
			dao.changePassword(JsfUtils.getCurrentUser(), currentPassword,
					newPassword);
			JsfUtils.addSuccessMessage("Password changed successfully");
		} catch (Exception e) {
			JsfUtils.addErrorMessage(e.getMessage());
		}
		return null;
	}

}
