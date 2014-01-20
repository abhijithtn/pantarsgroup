package org.jss.polytechnic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.MapHandler;

public class SubjectDao {

	public Map<String, Object> getSubjects() {

		Connection conn = null;
		Map<String, Object> subjectMap = null;

		try {

			conn = DatabaseConnection.DB.getConnection();
			String sql = new String(
					"SELECT SUBJECT_CODE, SUBJECT_NAME FROM SUBJECT").intern();

			QueryRunner qr = new QueryRunner();
			subjectMap = qr.query(conn, sql, new MapHandler(new RowProcessor() {

				@Override
				public Map<String, Object> toMap(ResultSet paramResultSet)
						throws SQLException {
					Map<String, Object> map = new LinkedHashMap<String, Object>();
					while (paramResultSet.next()) {
						map.put(paramResultSet.getString(1),
								paramResultSet.getString(2));
					}
					return map;
				}

				@Override
				public <T> List<T> toBeanList(ResultSet paramResultSet,
						Class<T> paramClass) throws SQLException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public <T> T toBean(ResultSet paramResultSet,
						Class<T> paramClass) throws SQLException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Object[] toArray(ResultSet paramResultSet)
						throws SQLException {
					// TODO Auto-generated method stub
					return null;
				}
			}));

		} catch (SQLException e) {
			e.printStackTrace();
			subjectMap = new HashMap<String, Object>();
		} finally {
			DbUtils.closeQuietly(conn);
		}

		return subjectMap;
	}

	public static String getSubjectName(final String subjectCode) {

		String subjectName = null;
		Connection conn = null;

		try {
			conn = DatabaseConnection.DB.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT SUBJECT_NAME FROM SUBJECT WHERE SUBJECT_CODE = ?");
			pstmt.setString(1, subjectCode);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				subjectName = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}

		return subjectName;
	}
}
