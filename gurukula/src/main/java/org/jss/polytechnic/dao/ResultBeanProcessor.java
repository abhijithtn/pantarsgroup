package org.jss.polytechnic.dao;

import java.sql.ResultSet;
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
			Result result = new Result();
			result.setStudentName(rs.getString("STUDENT_NAME"));
			result.setResult(rs.getString("RESULT"));
			result.setRegNo(rs.getString("REG_NO"));
			result.setSem(rs.getString("SEMESTER"));
			String[] ex = result.getEx();
			ex[0] = rs.getString("ex1");
			ex[1] = rs.getString("ex2");
			ex[2] = rs.getString("ex3");
			ex[3] = rs.getString("ex4");
			ex[4] = rs.getString("ex5");
			ex[5] = rs.getString("ex6");
			ex[6] = rs.getString("ex7");
			ex[7] = rs.getString("ex8");
			ex[8] = rs.getString("ex9");

			String[] ia = result.getIn();
			ia[0] = rs.getString("ia1");
			ia[1] = rs.getString("ia2");
			ia[2] = rs.getString("ia3");
			ia[3] = rs.getString("ia4");
			ia[4] = rs.getString("ia5");
			ia[5] = rs.getString("ia6");
			ia[6] = rs.getString("ia7");
			ia[7] = rs.getString("ia8");
			ia[8] = rs.getString("ia9");

			return (T) result;
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
			Result result = new Result();
			result.setStudentName(rs.getString("STUDENT_NAME"));
			result.setResult(rs.getString("RESULT"));
			result.setRegNo(rs.getString("REG_NO"));
			result.setSem(rs.getString("SEMESTER"));
			String[] ex = result.getEx();
			ex[0] = rs.getString("ex1");
			ex[1] = rs.getString("ex2");
			ex[2] = rs.getString("ex3");
			ex[3] = rs.getString("ex4");
			ex[4] = rs.getString("ex5");
			ex[5] = rs.getString("ex6");
			ex[6] = rs.getString("ex7");
			ex[7] = rs.getString("ex8");
			ex[8] = rs.getString("ex9");

			String[] ia = result.getIn();
			ia[0] = rs.getString("ia1");
			ia[1] = rs.getString("ia2");
			ia[2] = rs.getString("ia3");
			ia[3] = rs.getString("ia4");
			ia[4] = rs.getString("ia5");
			ia[5] = rs.getString("ia6");
			ia[6] = rs.getString("ia7");
			ia[7] = rs.getString("ia8");
			ia[8] = rs.getString("ia9");

			beanList.add(result);
		}

		return (List<T>) beanList;

	}

}
