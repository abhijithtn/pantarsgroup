package org.jss.polytechnic.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.jss.polytechnic.bean.Result;
import org.jss.polytechnic.dao.ResultBeanProcessor;
import org.junit.Before;
import org.junit.Test;

public class RSMapperTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/test", "root", "root");
			QueryRunner qr = new QueryRunner();
			List<Result> resultList = qr.query(conn,
					"select * from exam_results", new BeanListHandler<Result>(
							Result.class, new BasicRowProcessor(
									new ResultBeanProcessor())));

			System.out.println(resultList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
