package org.jss.polytechnic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.StringUtils;
import org.jss.polytechnic.bean.Student;

public class StudentDao {

	public boolean insertPersonalDetails(List<Student> studentList) {

		Connection conn = null;

		boolean isSuccessful = false;

		try {

			conn = DatabaseConnection.DB.getConnection();

			conn.setAutoCommit(false);

			Object[][] params = getParamForInsertStudentInfo(studentList);

			QueryRunner qr = new QueryRunner();

			qr.batch(conn, Queries.INSERT_STUDENT_INFO, params);

			isSuccessful = true;

			conn.commit();

		} catch (SQLException e) {
			try {
				System.out.println("Rolled back");
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}

		return isSuccessful;
	}

	public void search(QueryData<Student> qd) {

		Connection conn = null;

		int first = qd.getFirst();
		int pagesize = qd.getPageSize();
		Student filter = qd.getFilter();
		String sortOrder = qd.getSortOrder();
		String sortField = qd.getSortField();

		try {

			String countQuery = "select count(*) from student_info where 1 =1 ";
			String resultQuery = "select * from student_info where 1 = 1 ";
			String whereClause = buildWhereClause(filter);
			Object[] params = getParams(filter);

			conn = DatabaseConnection.DB.getConnection();
			QueryRunner qr = new QueryRunner();

			PreparedStatement pstmt = conn.prepareStatement(countQuery
					+ whereClause);

			qr.fillStatement(pstmt, params);

			ResultSet rs = pstmt.executeQuery();
			int count = 0;
			while (rs.next()) {
				count = rs.getInt(1);
			}

			DbUtils.closeQuietly(null, pstmt, rs);

			qd.setTotalResultCount(count);

			if (count > 0) {
				StringBuilder sortOrderBuilder = new StringBuilder();
				if ("reg_no".equals(sortField)) {
					sortOrderBuilder.append(" ORDER BY REG_NO ").append(
							sortOrder);
				} else if ("category".equals(sortField)) {
					sortOrderBuilder.append(" ORDER BY CATEGORY ").append(
							sortOrder);
				} else if ("gender".equals(sortField)) {
					sortOrderBuilder.append(" ORDER BY GENDER ").append(
							sortOrder);
				} else {
					sortOrderBuilder.append(" ORDER BY NAME ")
							.append(sortOrder);
				}

				sortOrderBuilder.append(" LIMIT ").append(first).append(",")
						.append(pagesize);

				List<Student> resultList = qr.query(conn, resultQuery
						+ whereClause + sortOrderBuilder.toString(),
						new BeanListHandler<Student>(Student.class,
								new BasicRowProcessor(
										new StudentBeanProcessor())), params);

				qd.setData(resultList);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}

	private String buildWhereClause(Student student) {
		StringBuilder sb = new StringBuilder(100);
		if (StringUtils.isNotBlank(student.getReg_no())) {
			sb.append(" AND REG_NO LIKE ? ");
		}

		if (StringUtils.isNotBlank(student.getName())) {
			sb.append(" AND NAME like ? ");
		}

		if (StringUtils.isNotBlank(student.getCategory())) {
			sb.append(" AND CATEGORY = ? ");
		}

		if (StringUtils.isNotBlank(student.getGender())) {
			sb.append(" AND GENDER = ? ");
		}

		return sb.toString();
	}

	private Object[] getParams(Student student) throws SQLException {

		List<String> params = new ArrayList<String>();

		if (StringUtils.isNotBlank(student.getReg_no())) {
			params.add("%" + student.getReg_no() + "%");
		}

		if (StringUtils.isNotBlank(student.getName())) {
			params.add("%" + student.getName() + "%");
		}

		if (StringUtils.isNotBlank(student.getCategory())) {
			params.add(student.getCategory());
		}

		if (StringUtils.isNotBlank(student.getGender())) {
			params.add(student.getGender());
		}

		if (params.size() == 0) {
			return null;
		} else {
			return params.toArray();
		}

	}

	/**
	 * @param resultList
	 */
	private Object[][] getParamForInsertStudentInfo(List<Student> studentList) {
		Object[][] params = new Object[studentList.size()][6];
		// REG_NO, NAME,FATHER,MOTHER,GENDER,CATEGORY
		for (int i = 0; i < studentList.size(); i++) {
			Student student = studentList.get(i);
			int j = 0;
			params[i][j++] = student.getReg_no();
			params[i][j++] = student.getName();
			params[i][j++] = student.getFather();
			params[i][j++] = student.getMother();
			params[i][j++] = student.getGender();
			params[i][j++] = student.getCategory();
		}

		return params;
	}

	public boolean updateStudent(Student student) {

		Connection conn = null;

		try {

			String updateQuery = "UPDATE STUDENT_INFO SET NAME = ?, FATHER = ?, MOTHER = ?, GENDER = ?, CATEGORY = ?, BRANCH = ? WHERE REG_NO = ?";

			conn = DatabaseConnection.DB.getConnection();

			conn.setAutoCommit(true);

			PreparedStatement pstmt = conn.prepareStatement(updateQuery);
			int i = 1;
			pstmt.setString(i++, student.getName());
			pstmt.setString(i++, student.getFather());
			pstmt.setString(i++, student.getMother());
			pstmt.setString(i++, student.getGender());
			pstmt.setString(i++, student.getCategory());
			pstmt.setString(i++, student.getBranch());
			pstmt.setString(i++, student.getReg_no());

			int rowCount = pstmt.executeUpdate();

			return rowCount == 1;

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}

		return false;

	}

	public boolean deleteStudent(Student student) {

		Connection conn = null;

		try {

			String updateQuery = "DELETE FROM STUDENT_INFO WHERE REG_NO = ?";

			conn = DatabaseConnection.DB.getConnection();

			conn.setAutoCommit(true);

			PreparedStatement pstmt = conn.prepareStatement(updateQuery);
			int i = 1;
			pstmt.setString(i++, student.getReg_no());

			int rowCount = pstmt.executeUpdate();

			return rowCount == 1;

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}

		return false;

	}
}
