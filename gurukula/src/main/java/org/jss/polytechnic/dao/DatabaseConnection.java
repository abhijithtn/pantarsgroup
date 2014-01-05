package org.jss.polytechnic.dao;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public enum DatabaseConnection {

	DB;

	public Connection getConnection() {

		try {

			Context ctx = new InitialContext();

			Context envCtx = (Context) ctx.lookup("java:comp/env");

			DataSource ds = (DataSource) envCtx.lookup("jdbc/MyDB");

			return ds.getConnection();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;

	}

}
