package org.jss.polytechnic.bean;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

public class Student implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4122612149984056928L;

	private String reg_no;

	private String name;

	private String father;

	private String mother;

	private String gender;

	private String category;

	private String branch;

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

	/**
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	public Student mask(Map<String, String> filter) {

		for (Entry<String, String> entry : filter.entrySet()) {
			mask(entry.getKey(), entry.getValue());
		}

		return this;
	}

	public Student mask(String pattern, String value) {
		if (pattern == null || value == null)
			return this;

		if ("name".equals(pattern)) {
			setName(value);
		} else if ("reg_no".equals(pattern)) {
			setReg_no(value);
		} else if ("category".equals(pattern)) {
			setCategory(value);
		} else if ("gender".equals(pattern)) {
			setGender(value);
		}

		return this;
	}

}
