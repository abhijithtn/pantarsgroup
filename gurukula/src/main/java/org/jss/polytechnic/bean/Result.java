package org.jss.polytechnic.bean;

public class Result {

	private String regNo;

	private String sem;

	private String[] ex;

	private String[] in;

	private int total;

	private String result;

	public Result() {
		super();
		ex = new String[9];
		in = new String[9];
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
