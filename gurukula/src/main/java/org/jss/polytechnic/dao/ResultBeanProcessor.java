package org.jss.polytechnic.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.jss.polytechnic.bean.Result;

public class ResultBeanProcessor extends BeanProcessor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.dbutils.BeanProcessor#toBean(java.sql.ResultSet,
	 * java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {

		while (rs.next()) {
			return (T) getResultObject(rs);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.dbutils.BeanProcessor#toBeanList(java.sql.ResultSet,
	 * java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> toBeanList(ResultSet rs, Class<T> type)
			throws SQLException {

		List<Result> beanList = new LinkedList<Result>();

		while (rs.next()) {
			beanList.add(getResultObject(rs));
		}

		return (List<T>) beanList;

	}

	private Result getResultObject(ResultSet rs) throws SQLException {
		Result result = new Result();

		String[] ex = result.getEx();
		String[] ia = result.getIn();
		String[] qp = result.getQp();

		ResultSetMetaData rsmd = rs.getMetaData();

		int columnCount = rsmd.getColumnCount();

		for (int i = 1; i <= columnCount; i++) {
			String columnName = rsmd.getColumnName(i);
			if ("STUDENT_NAME".equalsIgnoreCase(columnName)) {
				result.setStudentName(rs.getString("STUDENT_NAME"));
			} else if ("RESULT".equalsIgnoreCase(columnName)) {
				result.setResult(rs.getString("RESULT"));
			} else if ("REG_NO".equalsIgnoreCase(columnName)) {
				result.setRegNo(rs.getString("REG_NO"));
			} else if ("SEMESTER".equalsIgnoreCase(columnName)) {
				result.setSem(rs.getString("SEMESTER"));
			} else if ("TOTAL".equalsIgnoreCase(columnName)) {
				result.setTotal(rs.getInt("TOTAL"));
			} else if ("EX1".equalsIgnoreCase(columnName)) {
				ex[0] = rs.getString("ex1");
			} else if ("EX2".equalsIgnoreCase(columnName)) {
				ex[1] = rs.getString("ex2");
			} else if ("EX3".equalsIgnoreCase(columnName)) {
				ex[2] = rs.getString("ex3");
			} else if ("EX4".equalsIgnoreCase(columnName)) {
				ex[3] = rs.getString("ex4");
			} else if ("EX5".equalsIgnoreCase(columnName)) {
				ex[4] = rs.getString("ex5");
			} else if ("EX6".equalsIgnoreCase(columnName)) {
				ex[5] = rs.getString("ex6");
			} else if ("EX7".equalsIgnoreCase(columnName)) {
				ex[6] = rs.getString("ex7");
			} else if ("EX8".equalsIgnoreCase(columnName)) {
				ex[7] = rs.getString("ex8");
			} else if ("EX9".equalsIgnoreCase(columnName)) {
				ex[8] = rs.getString("ex9");
			} else if ("IA1".equalsIgnoreCase(columnName)) {
				ia[0] = rs.getString("ia1");
			} else if ("IA2".equalsIgnoreCase(columnName)) {
				ia[1] = rs.getString("ia2");
			} else if ("IA3".equalsIgnoreCase(columnName)) {
				ia[2] = rs.getString("ia3");
			} else if ("IA4".equalsIgnoreCase(columnName)) {
				ia[3] = rs.getString("ia4");
			} else if ("IA5".equalsIgnoreCase(columnName)) {
				ia[4] = rs.getString("ia5");
			} else if ("IA6".equalsIgnoreCase(columnName)) {
				ia[5] = rs.getString("ia6");
			} else if ("IA7".equalsIgnoreCase(columnName)) {
				ia[6] = rs.getString("ia7");
			} else if ("IA8".equalsIgnoreCase(columnName)) {
				ia[7] = rs.getString("ia8");
			} else if ("IA9".equalsIgnoreCase(columnName)) {
				ia[8] = rs.getString("ia9");
			} else if ("QP1".equalsIgnoreCase(columnName)) {
				qp[0] = rs.getString("qp1");
			} else if ("QP2".equalsIgnoreCase(columnName)) {
				qp[1] = rs.getString("qp2");
			} else if ("QP3".equalsIgnoreCase(columnName)) {
				qp[2] = rs.getString("qp3");
			} else if ("QP4".equalsIgnoreCase(columnName)) {
				qp[3] = rs.getString("qp4");
			} else if ("QP5".equalsIgnoreCase(columnName)) {
				qp[4] = rs.getString("qp5");
			} else if ("QP6".equalsIgnoreCase(columnName)) {
				qp[5] = rs.getString("qp6");
			} else if ("QP7".equalsIgnoreCase(columnName)) {
				qp[6] = rs.getString("qp7");
			} else if ("QP8".equalsIgnoreCase(columnName)) {
				qp[7] = rs.getString("qp8");
			} else if ("QP9".equalsIgnoreCase(columnName)) {
				qp[8] = rs.getString("qp9");
			}
		}

		return result;
	}

}
