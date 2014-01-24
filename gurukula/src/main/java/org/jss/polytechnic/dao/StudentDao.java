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
import org.jss.polytechnic.bean.Result;
import org.jss.polytechnic.bean.Student;
import org.jss.polytechnic.web.Constants;

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

	public void search(QueryData<Result> qd) {

		Connection conn = null;

		int first = qd.getFirst();
		int pagesize = qd.getPageSize();
		Result filter = qd.getFilter();
		String sortOrder = qd.getSortOrder();
		String sortField = qd.getSortField();

		try {

			String countQuery = "select count(*) from exam_results where 1 =1 ";
			String resultQuery = "select * from exam_results where 1 = 1 ";
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
				if ("regNo".equals(sortField)) {
					sortOrderBuilder.append(" ORDER BY  REG_NO ").append(
							sortOrder);
				} else if ("sem".equals(sortField)) {
					sortOrderBuilder.append(" ORDER BY   SEMESTER ").append(
							sortOrder);
				} else if ("result".equals(sortField)) {
					sortOrderBuilder.append(" ORDER BY   RESULT ").append(
							sortOrder);
				} else {
					sortOrderBuilder.append(" ORDER BY STUDENT_NAME ").append(
							sortOrder);
				}

				sortOrderBuilder.append(" LIMIT ").append(first).append(",")
						.append(pagesize);

				List<Result> resultList = qr
						.query(conn, resultQuery + whereClause
								+ sortOrderBuilder.toString(),
								new BeanListHandler<Result>(Result.class,
										new BasicRowProcessor(
												new ResultBeanProcessor())),
								params);

				qd.setData(resultList);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}

	private String buildWhereClause(Result result) {
		StringBuilder sb = new StringBuilder(100);
		if (StringUtils.isNotBlank(result.getRegNo())) {
			sb.append(" AND REG_NO LIKE ? ");
		}

		if (StringUtils.isNotBlank(result.getStudentName())) {
			sb.append(" AND STUDENT_NAME like ? ");
		}

		if (StringUtils.isNotBlank(result.getSem())) {
			sb.append(" AND SEMESTER = ? ");
		}

		if (StringUtils.isNotBlank(result.getResult())) {
			sb.append(" AND RESULT = ? ");
		}

		return sb.toString();
	}

	private Object[] getParams(Result result) throws SQLException {

		List<String> params = new ArrayList<String>();

		if (StringUtils.isNotBlank(result.getRegNo())) {
			params.add("%" + result.getRegNo() + "%");
		}

		if (StringUtils.isNotBlank(result.getStudentName())) {
			params.add("%" + result.getStudentName() + "%");
		}

		if (StringUtils.isNotBlank(result.getSem())) {
			params.add(result.getSem());
		}

		if (StringUtils.isNotBlank(result.getResult())) {
			params.add(result.getResult());
		}

		if (params.size() == 0) {
			return null;
		} else {
			return params.toArray();
		}

	}

	/**
	 * @return
	 */
	private Object[] getParamForUpdateFailExamResult() {
		Object[] param = new Object[19];
		param[0] = "Fail";
		for (int i = 1; i < param.length; i++) {
			if (i < 11) {
				param[i] = Constants.EX_MIN;
			} else {
				param[i] = Constants.IA_MIN;
			}
		}
		return param;
	}

	/**
	 * @param updatableResult
	 * @return
	 */
	private Object[][] getParamForUpdateExamResults(List<Result> updatableResult) {
		Object[][] params;
		params = new Object[updatableResult.size()][20];

		for (int i = 0; i < updatableResult.size(); i++) {
			Result result = updatableResult.get(i);
			int j = 0;
			String[] ex = result.getEx();
			params[i][j++] = StringUtils.trimToNull(ex[0]);
			params[i][j++] = StringUtils.trimToNull(ex[1]);
			params[i][j++] = StringUtils.trimToNull(ex[2]);
			params[i][j++] = StringUtils.trimToNull(ex[3]);
			params[i][j++] = StringUtils.trimToNull(ex[4]);
			params[i][j++] = StringUtils.trimToNull(ex[5]);
			params[i][j++] = StringUtils.trimToNull(ex[6]);
			params[i][j++] = StringUtils.trimToNull(ex[7]);
			params[i][j++] = StringUtils.trimToNull(ex[8]);

			String[] ia = result.getIn();
			params[i][j++] = StringUtils.trimToNull(ia[0]);
			params[i][j++] = StringUtils.trimToNull(ia[1]);
			params[i][j++] = StringUtils.trimToNull(ia[2]);
			params[i][j++] = StringUtils.trimToNull(ia[3]);
			params[i][j++] = StringUtils.trimToNull(ia[4]);
			params[i][j++] = StringUtils.trimToNull(ia[5]);
			params[i][j++] = StringUtils.trimToNull(ia[6]);
			params[i][j++] = StringUtils.trimToNull(ia[7]);
			params[i][j++] = StringUtils.trimToNull(ia[8]);

			params[i][j++] = result.getRegNo();
			params[i][j++] = result.getSem();
		}
		return params;
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
}
