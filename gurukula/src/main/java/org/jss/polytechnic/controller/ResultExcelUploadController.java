package org.jss.polytechnic.controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.jss.polytechnic.bean.BoardResult;
import org.jss.polytechnic.dao.ResultDao;
import org.jss.polytechnic.utils.JsfUtils;
import org.jss.polytechnic.web.ExcelUtility;
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "resultExcelUploadController")
@RequestScoped
public class ResultExcelUploadController extends ExcelUploadController {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jss.polytechnic.controller.ExcelUploadController#processRequest(org
	 * .primefaces.model.UploadedFile)
	 */
	@Override
	protected void processRequest(UploadedFile file) {
		List<BoardResult> resultList = null;
		try {
			resultList = ExcelUtility.parseResultSheet(file.getInputstream(),
					file.getFileName().endsWith("xlsx"));
		} catch (Exception e) {
			JsfUtils.addErrorMessage("Error while uploading the file. Please check the file before uploading");
			return;
		}

		ResultDao dao = new ResultDao();
		boolean isSuccessful = dao.insertExamResults(resultList);
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
