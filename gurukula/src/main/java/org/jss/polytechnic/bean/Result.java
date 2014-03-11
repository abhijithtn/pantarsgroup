package org.jss.polytechnic.bean;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8125204286168763912L;

	private String regNo;

	private String studentName;

	private String sem;

	private String[] ex;

	private String[] in;

	private String[] qp;

	private int exTotal;

	private int inTotal;

	private int total;

	private String result;

	public Result() {
		super();
		ex = new String[9];
		in = new String[9];
		qp = new String[9];
	}

	/**
	 * @return the studentName
	 */
	public String getStudentName() {
		return studentName;
	}

	/**
	 * @param studentName
	 *            the studentName to set
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	/**
	 * @return the regNo
	 */
	public String getRegNo() {
		return regNo;
	}

	/**
	 * @param regNo
	 *            the regNo to set
	 */
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	/**
	 * @return the sem
	 */
	public String getSem() {
		return sem;
	}

	/**
	 * @param sem
	 *            the sem to set
	 */
	public void setSem(String sem) {
		this.sem = sem;
	}

	public String[] getEx() {
		return ex;
	}

	public void setEx(String[] ex) {
		this.ex = ex;
	}

	public String[] getIn() {
		return in;
	}

	public void setIn(String[] in) {
		this.in = in;
	}

	/**
	 * @return the qp
	 */
	public String[] getQp() {
		return qp;
	}

	/**
	 * @param qp
	 *            the qp to set
	 */
	public void setQp(String[] qp) {
		this.qp = qp;
	}

	/**
	 * @return the exTotal
	 */
	public int getExTotal() {
		return exTotal;
	}

	/**
	 * @param exTotal
	 *            the exTotal to set
	 */
	public void setExTotal(int exTotal) {
		this.exTotal = exTotal;
	}

	/**
	 * @return the inTotal
	 */
	public int getInTotal() {
		return inTotal;
	}

	/**
	 * @param inTotal
	 *            the inTotal to set
	 */
	public void setInTotal(int inTotal) {
		this.inTotal = inTotal;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	public Result mask(Map<String, String> filter) {

		for (Entry<String, String> entry : filter.entrySet()) {
			mask(entry.getKey(), entry.getValue());
		}

		return this;
	}

	public Result mask(String pattern, String value) {
		if (pattern == null || value == null)
			return this;

		if ("regNo".equals(pattern)) {
			setRegNo(value);
		} else if ("sem".equals(pattern)) {
			setSem(value);
		} else if ("result".equals(pattern)) {
			setResult(value);
		} else if ("studentName".equals(pattern)) {
			setStudentName(value);
		}

		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Result [regNo=" + regNo + ", sem=" + sem + ", total=" + total
				+ ", result=" + result + "]";
	}

}
