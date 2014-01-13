package org.jss.polytechnic.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.StringUtils;
import org.jss.polytechnic.bean.BoardResult;
import org.jss.polytechnic.bean.Result;
import org.jss.polytechnic.web.Constants;

public class ResultDao {

	public boolean insertExamResults(List<BoardResult> resultList) {

		Connection conn = null;

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

			params = getParamForUpdateExamResults(updatableResult);

			qr.batch(conn, Queries.UPDATE_EXAM_MARKS, params);

			qr.update(conn, Queries.UPDATE_EXAM_TOTAL);

			Object[] param = getParamForUpdateFailExamResult();

			qr.update(conn, Queries.UPDATE_FAIL_EXAM_RESULT, param);

			qr.update(conn, Queries.UPDATE_PASS_EXAM_RESULT, "PASS", "FAIL");

			qr.update(conn, Queries.INSERT_EXAM_RESULTS);

			qr.update(conn, Queries.UPDATE_STAGING_EXAM_STATUS);

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

		return true;
	}

	/**
	 * @return
	 */
	private Object[] getParamForUpdateFailExamResult() {
		Object[] param = new Object[19];
		param[0] = "FAIL";
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
}
