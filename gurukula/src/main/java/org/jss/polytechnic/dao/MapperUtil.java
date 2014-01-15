package org.jss.polytechnic.dao;

import java.util.HashMap;
import java.util.Map;

public class MapperUtil {

	public static Map<String, String> getResultBeanMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("REG_NO", "regNo");
		map.put("STUDENT_NAME", "studentName");
		map.put("SEMESTER", "sem");
		map.put("TOTAL", "total");
		map.put("RESULT", "result");

		map.put("EX1", "ex[0]");
		map.put("EX2", "ex[1]");
		map.put("EX3", "ex[2]");
		map.put("EX4", "ex[3]");
		map.put("EX5", "ex[4]");
		map.put("EX6", "ex[5]");
		map.put("EX7", "ex[6]");
		map.put("EX8", "ex[7]");
		map.put("EX9", "ex[8]");

		map.put("IA1", "ia[0]");
		map.put("IA2", "ia[1]");
		map.put("IA3", "ia[2]");
		map.put("IA4", "ia[3]");
		map.put("IA5", "ia[4]");
		map.put("IA6", "ia[5]");
		map.put("IA7", "ia[6]");
		map.put("IA8", "ia[7]");
		map.put("IA9", "ia[8]");

		map.put("QP1", "qp[0]");
		map.put("QP2", "qp[1]");
		map.put("QP3", "qp[2]");
		map.put("QP4", "qp[3]");
		map.put("QP5", "qp[4]");
		map.put("QP6", "qp[5]");
		map.put("QP7", "qp[6]");
		map.put("QP8", "qp[7]");
		map.put("QP9", "qp[8]");

		return map;
	}
}