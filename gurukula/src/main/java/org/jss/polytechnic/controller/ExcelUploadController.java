package org.jss.polytechnic.controller;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

public class ExcelUploadController {

	private UploadedFile file;

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void handleFileUpload(FileUploadEvent event) {
		this.processRequest(event.getFile());
	}

	protected void processRequest(UploadedFile file) {
		// do nothing. This method is implemented in the sub-class
	}

}
