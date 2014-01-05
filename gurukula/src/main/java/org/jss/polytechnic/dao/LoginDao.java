package org.jss.polytechnic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.jss.polytechnic.bean.UserInfo;

public class LoginDao {

	public void validateCredentials(final UserInfo userInfo) {

		Connection conn = DatabaseConnection.DB.getConnection();

		try {
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT ROLE FROM USER_INFO WHERE UPPER(USERNAME)=UPPER(?) AND PASSWORD=MD5(?)");
			pstmt.setString(1, userInfo.getUsername());
			pstmt.setString(2, userInfo.getPassword());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				userInfo.setRole(rs.getInt(1));
			}

			DbUtils.closeQuietly(conn, pstmt, rs);

			System.out.println(userInfo);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
