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
import org.apache.commons.lang3.math.NumberUtils;
import org.jss.polytechnic.bean.BoardResult;
import org.jss.polytechnic.bean.Result;

public class ResultDao {

	public boolean insertExamResults(List<BoardResult> resultList) {

		Connection conn = null;

		boolean isSuccessful = false;

		try {

			conn = DatabaseConnection.DB.getConnection();

			conn.setAutoCommit(false);

			Object[][] params = getParamForInsertExamResults(resultList);

			QueryRunner qr = new QueryRunner();
			qr.batch(conn, Queries.INSERT_STAGING_EXAM_RESULTS, params);

			List<Result> updatableResult = qr.query(conn,
					Queries.GET_RESULTS_TO_BE_UPDATED,
					new BeanListHandler<Result>(Result.class,
							new BasicRowProcessor(new ResultBeanProcessor())));

			calculateTotalResult(updatableResult);

			params = getParamForUpdateExamResults(updatableResult);

			qr.batch(conn, Queries.UPDATE_EXAM_RESULTS, params);

			// params = getParamForUpdateExamResults(updatableResult);

			// qr.batch(conn, Queries.UPDATE_EXAM_MARKS, params);

			// qr.update(conn, Queries.UPDATE_EXAM_TOTAL);

			// Object[] param = getParamForUpdateFailExamResult();

			// qr.update(conn, Queries.UPDATE_FAIL_EXAM_RESULT, param);

			// qr.update(conn, Queries.UPDATE_PASS_EXAM_RESULT, "Pass", "Fail");

			qr.update(conn, Queries.INSERT_EXAM_RESULTS);

			qr.update(conn, Queries.UPDATE_STAGING_EXAM_STATUS);

			conn.commit();

			isSuccessful = true;

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
	// private Object[] getParamForUpdateFailExamResult() {
	// Object[] param = new Object[19];
	// param[0] = "Fail";
	// for (int i = 1; i < param.length; i++) {
	// if (i < 11) {
	// param[i] = Constants.EX_MIN;
	// } else {
	// param[i] = Constants.IA_MIN;
	// }
	// }
	// return param;
	// }

	/**
	 * @param updatableResult
	 * @return
	 */
	// private Object[][] getParamForUpdateExamResults(List<Result>
	// updatableResult) {
	// Object[][] params;
	// params = new Object[updatableResult.size()][20];
	//
	// for (int i = 0; i < updatableResult.size(); i++) {
	// Result result = updatableResult.get(i);
	// int j = 0;
	// String[] ex = result.getEx();
	// params[i][j++] = StringUtils.trimToNull(ex[0]);
	// params[i][j++] = StringUtils.trimToNull(ex[1]);
	// params[i][j++] = StringUtils.trimToNull(ex[2]);
	// params[i][j++] = StringUtils.trimToNull(ex[3]);
	// params[i][j++] = StringUtils.trimToNull(ex[4]);
	// params[i][j++] = StringUtils.trimToNull(ex[5]);
	// params[i][j++] = StringUtils.trimToNull(ex[6]);
	// params[i][j++] = StringUtils.trimToNull(ex[7]);
	// params[i][j++] = StringUtils.trimToNull(ex[8]);
	//
	// String[] ia = result.getIn();
	// params[i][j++] = StringUtils.trimToNull(ia[0]);
	// params[i][j++] = StringUtils.trimToNull(ia[1]);
	// params[i][j++] = StringUtils.trimToNull(ia[2]);
	// params[i][j++] = StringUtils.trimToNull(ia[3]);
	// params[i][j++] = StringUtils.trimToNull(ia[4]);
	// params[i][j++] = StringUtils.trimToNull(ia[5]);
	// params[i][j++] = StringUtils.trimToNull(ia[6]);
	// params[i][j++] = StringUtils.trimToNull(ia[7]);
	// params[i][j++] = StringUtils.trimToNull(ia[8]);
	//
	// params[i][j++] = result.getRegNo();
	// params[i][j++] = result.getSem();
	// }
	// return params;
	// UPDATE EXAM_RESULTS SET EX_TOTAL = ?, IA_TOTAL = ?, TOTAL = ?, RESULT = ?
	// WHERE REG_NO = ? AND SEMESTER = ?
	// }

	private Object[][] getParamForUpdateExamResults(List<Result> updatebleResult) {
		Object[][] params = new Object[updatebleResult.size()][6];

		for (int i = 0; i < params.length; i++) {
			int j = 0;
			Result result = updatebleResult.get(i);
			params[i][j++] = result.getExTotal();
			params[i][j++] = result.getInTotal();
			params[i][j++] = result.getTotal();
			params[i][j++] = result.getResult();
			params[i][j++] = result.getRegNo();
			params[i][j++] = result.getSem();
		}

		return params;
	}

	/**
	 * @param resultList
	 */
	private Object[][] getParamForInsertExamResults(List<BoardResult> resultList) {
		Object[][] params = new Object[resultList.size()][36];

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

	private void calculateTotalResult(List<Result> resultList) {

		boolean isResultSet = false;
		boolean isAllZZ = false;

		for (Result result : resultList) {

			isResultSet = false;
			isAllZZ = true;

			String[] ex = result.getEx();
			String[] in = result.getIn();
			String[] qp = result.getQp();

			int total = 0;
			int qpCount = 0;
			int exTotal = 0;
			int inTotal = 0;

			for (int i = 0; i < ex.length; i++) {

				if (!StringUtils.equalsIgnoreCase(ex[i], "ZZ")) {
					isAllZZ = false;
				}

				int exMarks = NumberUtils.toInt(ex[i], 0);
				int intMarks = NumberUtils.toInt(in[i], 0);
				int totMarks = exMarks + intMarks;

				exTotal += exMarks;
				inTotal += intMarks;

				if (StringUtils.isNotEmpty(qp[i])) {
					qpCount++;
					if (!isResultSet) {
						if (StringUtils.endsWith(qp[i], "P")) {
							if (exMarks < 50 || totMarks < 60) {
								isResultSet = true;
								result.setResult("Fail");
							}
						} else {
							if (exMarks < 35 || totMarks < 45) {
								isResultSet = true;
								result.setResult("Fail");
							}
						}
					}
				}

				total = total + totMarks;
			}

			result.setExTotal(exTotal);
			result.setInTotal(inTotal);
			result.setTotal(total);

			if (isAllZZ) {
				result.setResult("Withheld");
				continue;
			}

			if (!isResultSet && qpCount > 0) {
				result.setResult("Pass");
				double percentage = (total * 1.0) / qpCount;
				if (percentage >= 75.0) {
					result.setResult("Distinction");
				} else if (percentage >= 60.0) {
					result.setResult("First Class");
				}
			}
		}
	}
}
