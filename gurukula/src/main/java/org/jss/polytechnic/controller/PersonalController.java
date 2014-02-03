package org.jss.polytechnic.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.jss.polytechnic.bean.Student;
import org.jss.polytechnic.dao.QueryData;
import org.jss.polytechnic.dao.StudentDao;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

@ManagedBean(name = "personalController")
@ViewScoped
public class PersonalController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1762563989143654209L;

	private LazyDataModel<Student> lazyModel;

	private Student student = new Student();
	private Student selectedStudent = new Student();
	private transient StudentDao dao = new StudentDao();

	@PostConstruct
	void intializeSession() {
		FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}

	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student
	 *            the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * @return the selectedStudent
	 */
	public Student getSelectedStudent() {
		return selectedStudent;
	}

	/**
	 * @param selectedStudent
	 *            the selectedStudent to set
	 */
	public void setSelectedStudent(Student selectedStudent) {
		this.selectedStudent = selectedStudent;
	}

	public List<Student> findAll() {
		Student filter = new Student();
		QueryData<Student> data = new QueryData<Student>(0, 1000000000, "name",
				"DESC", filter);
		dao.search(data);
		return data.getData();
	}

	public LazyDataModel<Student> getLazyModel() {
		if (lazyModel == null) {
			lazyModel = new LazyDataModel<Student>() {

				private static final long serialVersionUID = 1L;

				@Override
				public Student getRowData(String rowKey) {
					return fetchRow(rowKey);
				}

				@Override
				public Object getRowKey(Student object) {
					return object;
				}

				@Override
				public List<Student> load(int first, int pageSize,
						String sortField, SortOrder sortOrder,
						Map<String, String> filters) {

					String order = (SortOrder.DESCENDING.equals(sortOrder)) ? "DESC"
							: "ASC";

					Student student = new Student();
					student.mask(filters);

					QueryData<Student> data = new QueryData<Student>(first,
							pageSize, sortField, order, student);

					dao.search(data);

					this.setRowCount(data.getTotalResultCount());

					return data.getData();
				}

				@Override
				public void setRowIndex(int rowIndex) {
					if (rowIndex == -1 || getPageSize() == 0) {
						super.setRowIndex(-1);
					} else {
						super.setRowIndex(rowIndex % getPageSize());
					}
				}

			};
		}
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<Student> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public Student fetchRow(final String rowKey) {
		Student filter = new Student();
		filter.setReg_no(rowKey);
		QueryData<Student> data = new QueryData<Student>(0, 1, "name", "DESC",
				filter);
		dao.search(data);
		if (data.getData().size() > 0) {
			return data.getData().get(0);
		}

		return null;
	}

}
