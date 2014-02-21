package org.jss.polytechnic.bean;

public enum UserRole {

	ROLE_USER(1), ROLE_ADMIN(2), ROLE_NO_ACCESS(0);

	int roleId;

	UserRole(int roleId) {
		this.roleId = roleId;
	}

	public UserRole getUserRole(int roleId) {
		UserRole[] userRoles = UserRole.values();
		for (UserRole userRole : userRoles) {
			if (userRole.roleId == roleId)
				return userRole;
		}

		return UserRole.ROLE_NO_ACCESS;
	}

}
