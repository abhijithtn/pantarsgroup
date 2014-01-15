package org.jss.polytechnic.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.jss.polytechnic.bean.BoardResult;
import org.jss.polytechnic.bean.Result;
import org.jss.polytechnic.dao.MapperUtil;
import org.jss.polytechnic.dao.Queries;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExcelUtilityTest {

	File file;
	Connection conn;

	@Before
	public final void setUp() {
		file = new File("src/test/resources/me.xlsx");
		System.out.println(file.getAbsolutePath());
		Assert.assertNotNull(file);
	}

	@Test
	public final void testParseResultSheet() throws FileNotFoundException {
		List<? extends Result> resultList = ExcelUtility.parseResultSheet(
				new FileInputStream(file), true);
		Assert.assertNotNull(resultList);
	}

	@Test
	public final void testInsertion() throws SQLException,
			FileNotFoundException {
		List<BoardResult> resultList = ExcelUtility.parseResultSheet(
				new FileInputStream(file), true);
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/test", "root", "root");

		conn.setAutoCommit(false);

		Object[][] params = getParamForInsertExamResults(resultList);

		QueryRunner qr = new QueryRunner();
		qr.batch(conn, Queries.INSERT_STAGING_EXAM_RESULTS, params);

		List<Result> updatableResult = qr.query(conn,
				Queries.GET_RESULTS_TO_BE_UPDATED, new BeanListHandler<Result>(
						Result.class, new BasicRowProcessor(new BeanProcessor(
								MapperUtil.getResultBeanMap()))));

		System.out.println(updatableResult.size());

		conn.rollback();
	}

	/**
	 * @param resultList
	 */
	private Object[][] getParamForInsertExamResults(List<BoardResult> resultList) {
		Object[][] params = new Object[resultList.size()][36];

		// SL_NO,
		// REG_NO,STUDENT_NAME,SEMESTER,EX1,EX2,EX3,EX4,EX5,EX6,EX7,EX8,EX9,EX_TOTAL,IA1,IA2,IA3,IA4,IA5,IA6,IA7,IA8,IA9,IA_TOTAL,TOTAL,RESULT,QP1,QP2,QP3,QP4,QP5,QP6,QP7,QP8,QP9,STATUS

		for (int i = 0; i < resultList.size(); i++) {
			BoardResult br = resultList.get(i);
			int j = 0;
			params[i][j++] = br.getSlNo();
			params[i][j++] = br.getRegNo();
			params[i][j++] = br.getStudentName();
			params[i][j++] = br.getSem();
			params[i][j++] = br.getEx()[0];
			params[i][j++] = br.getEx()[1];
			params[i][j++] = br.getEx()[2];
			params[i][j++] = br.getEx()[3];
			params[i][j++] = br.getEx()[4];
			params[i][j++] = br.getEx()[5];
			params[i][j++] = br.getEx()[6];
			params[i][j++] = br.getEx()[7];
			params[i][j++] = br.getEx()[8];
			params[i][j++] = br.getExTotal();
			params[i][j++] = br.getIn()[0];
			params[i][j++] = br.getIn()[1];
			params[i][j++] = br.getIn()[2];
			params[i][j++] = br.getIn()[3];
			params[i][j++] = br.getIn()[4];
			params[i][j++] = br.getIn()[5];
			params[i][j++] = br.getIn()[6];
			params[i][j++] = br.getIn()[7];
			params[i][j++] = br.getIn()[8];
			params[i][j++] = br.getInTotal();

			params[i][j++] = br.getTotal();
			params[i][j++] = br.getResult();

			params[i][j++] = br.getQp()[0];
			params[i][j++] = br.getQp()[1];
			params[i][j++] = br.getQp()[2];
			params[i][j++] = br.getQp()[3];
			params[i][j++] = br.getQp()[4];
			params[i][j++] = br.getQp()[5];
			params[i][j++] = br.getQp()[6];
			params[i][j++] = br.getQp()[7];
			params[i][j++] = br.getQp()[8];

			params[i][j++] = 0;
		}

		return params;
	}
}
