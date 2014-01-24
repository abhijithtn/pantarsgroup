package org.jss.polytechnic.controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.jss.polytechnic.bean.Student;
import org.jss.polytechnic.dao.StudentDao;
import org.jss.polytechnic.utils.JsfUtils;
import org.jss.polytechnic.web.ExcelUtility;
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "personalExcelUploadController")
@RequestScoped
public class PersonalExcelUploadController extends ExcelUploadController {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jss.polytechnic.controller.ExcelUploadController#processRequest()
	 */
	@Override
	protected void processRequest(UploadedFile file) {
		List<Student> studentList = null;
		try {
			studentList = ExcelUtility.parsePersonalSheet(
					file.getInputstream(), file.getFileName().endsWith("xlsx"));
		} catch (Exception e) {
			JsfUtils.addErrorMessage("Error while uploading the file. Please check the file before uploading");
			return;
		}

		StudentDao dao = new StudentDao();
		boolean isSuccessful = dao.insertPersonalDetails(studentList);
		if (isSuccessful) {
			FacesMessage msg = new FacesMessage("Successful",
					file.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			FacesMessage msg = new FacesMessage("Unsuccessful",
					file.getFileName() + " is not uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

}
