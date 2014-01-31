package org.jss.polytechnic.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.jss.polytechnic.bean.Student;

public class StudentBeanProcessor extends BeanProcessor {

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

		List<Student> beanList = new LinkedList<Student>();

		while (rs.next()) {
			beanList.add(getResultObject(rs));
		}

		return (List<T>) beanList;

	}

	private Student getResultObject(ResultSet rs) throws SQLException {
		Student student = new Student();

		ResultSetMetaData rsmd = rs.getMetaData();

		int columnCount = rsmd.getColumnCount();

		for (int i = 1; i <= columnCount; i++) {
			String columnName = rsmd.getColumnName(i);
			if ("REG_NO".equalsIgnoreCase(columnName)) {
				student.setReg_no(rs.getString(columnName));
			} else if ("NAME".equalsIgnoreCase(columnName)) {
				student.setName(rs.getString(columnName));
			} else if ("FATHER".equalsIgnoreCase(columnName)) {
				student.setFather(rs.getString(columnName));
			} else if ("MOTHER".equalsIgnoreCase(columnName)) {
				student.setMother(rs.getString(columnName));
			} else if ("GENDER".equalsIgnoreCase(columnName)) {
				student.setGender(rs.getString(columnName));
			} else if ("CATEGORY".equalsIgnoreCase(columnName)) {
				student.setCategory(rs.getString(columnName));
			} else if ("BRANCH".equalsIgnoreCase(columnName)) {
				student.setBranch(rs.getString(columnName));
			}
		}

		return student;
	}

}
