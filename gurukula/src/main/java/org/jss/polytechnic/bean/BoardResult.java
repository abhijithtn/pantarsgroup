package org.jss.polytechnic.bean;

public class BoardResult extends Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = -84745799971631158L;

	private int slNo;

	private int exTotal;

	private int inTotal;

	private String[] qp;

	public BoardResult() {
		super();
		qp = new String[9];
	}

	/**
	 * @return the slNo
	 */
	public int getSlNo() {
		return slNo;
	}

	/**
	 * @param slNo
	 *            the slNo to set
	 */
	public void setSlNo(int slNo) {
		this.slNo = slNo;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BoardResult [slNo=" + slNo + ", exTotal=" + exTotal
				+ ", inTotal=" + inTotal + ", toString()=" + super.toString()
				+ "]";
	}

}
