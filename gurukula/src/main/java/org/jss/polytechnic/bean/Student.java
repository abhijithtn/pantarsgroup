package org.jss.polytechnic.bean;

public class Student {

	private String reg_no;

	private String name;

	private String father;

	private String mother;

	private String gender;

	private String category;

	public Student() {
		super();
	}

	public Student(String reg_no, String name, String father, String mother,
			String gender, String category) {
		super();
		this.reg_no = reg_no;
		this.name = name;
		this.father = father;
		this.mother = mother;
		this.gender = gender;
		this.category = category;
	}

	/**
	 * @return the reg_no
	 */
	public String getReg_no() {
		return reg_no;
	}

	/**
	 * @param reg_no
	 *            the reg_no to set
	 */
	public void setReg_no(String reg_no) {
		this.reg_no = reg_no;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the father
	 */
	public String getFather() {
		return father;
	}

	/**
	 * @param father
	 *            the father to set
	 */
	public void setFather(String father) {
		this.father = father;
	}

	/**
	 * @return the mother
	 */
	public String getMother() {
		return mother;
	}

	/**
	 * @param mother
	 *            the mother to set
	 */
	public void setMother(String mother) {
		this.mother = mother;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

}
