package org.jss.polytechnic.controller;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.tabview.Tab;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

@ManagedBean(name = "gurukulaMenu")
@RequestScoped
public class GurukulaMenu {

	public static final String student_INDEX = "0";
	public static final String PERSONAL_ACCESS_URL = "/pages/student/personal.xhtml";
	public static final String ACADEMIC_ACCESS_URL = "/pages/student/academic.xhtml";
	public static final String upload_INDEX = "1";
	public static final String UPLOAD_RESULT_URL = "/pages/upload/uploadResult.xhtml";
	public static final String UPLOAD_PERSONAL_DETAILS_URL = "/pages/upload/uploadPersonalDetails.xhtml";
	public static final String settings_INDEX = "2";
	public static final String CHANGE_PASSWORD_URL = "/pages/settings/changePassword.xhtml";
	public static final String LOGOUT_URL = "/j_spring_security_logout";

	private MenuModel model;
	private AccordionPanel accordion;
	private Map<String, MenuTuple> map;

	private class MenuTuple {
		private String packageIndex, url, entry, title;

		private MenuTuple(String url, String packageIndex, String entry,
				String title) {
			this.packageIndex = packageIndex;
			this.url = url;
			this.entry = entry;
			this.title = title;
		}

		private String getPackageIndex() {
			return packageIndex;
		}

		private String getUrl() {
			return url;
		}

		private String getEntry() {
			return entry;
		}

		private String getTitle() {
			return title;
		}
	}

	public GurukulaMenu() {
		if (map == null)
			populateMenuMap();
	}

	public void populateMenuMap() {
		map = new HashMap<String, MenuTuple>();
		map.put(PERSONAL_ACCESS_URL, new MenuTuple(PERSONAL_ACCESS_URL,
				student_INDEX, "PERSONAL", "Personal"));
		map.put(ACADEMIC_ACCESS_URL, new MenuTuple(ACADEMIC_ACCESS_URL,
				student_INDEX, "ACADEMIC", "Academic"));
		map.put(UPLOAD_RESULT_URL, new MenuTuple(UPLOAD_RESULT_URL,
				upload_INDEX, "UPLOAD_RESULT", "Upload Results"));
		map.put(UPLOAD_PERSONAL_DETAILS_URL, new MenuTuple(
				UPLOAD_PERSONAL_DETAILS_URL, upload_INDEX, "UPLOAD_PD",
				"Upload Student Details"));
		map.put(CHANGE_PASSWORD_URL, new MenuTuple(CHANGE_PASSWORD_URL,
				settings_INDEX, "CHANGEPASSWORD", "Change Password"));
		map.put(LOGOUT_URL, new MenuTuple(LOGOUT_URL, settings_INDEX, "LOGOUT",
				"Logout"));
	}

	public void load() {
		model = new DefaultMenuModel();
		accordion = new AccordionPanel();
		DefaultSubMenu submenuStudent = new DefaultSubMenu();
		submenuStudent.setLabel("Student");

		//
		Tab tabStudent = new Tab();
		tabStudent.setTitle("Student");

		submenuStudent.addElement(getItem("Personal", PERSONAL_ACCESS_URL));
		tabStudent.getChildren().add(getPanel(getTuple(PERSONAL_ACCESS_URL)));

		submenuStudent.addElement(getItem("Academic", ACADEMIC_ACCESS_URL));
		tabStudent.getChildren().add(getPanel(getTuple(ACADEMIC_ACCESS_URL)));

		model.addElement(submenuStudent);
		accordion.getChildren().add(tabStudent);

		DefaultSubMenu submenuUpload = new DefaultSubMenu();
		submenuUpload.setLabel("Upload");
		//
		Tab tabUpload = new Tab();
		tabUpload.setTitle("Upload");

		submenuUpload.addElement(getItem("Upload Results", UPLOAD_RESULT_URL));
		tabUpload.getChildren().add(getPanel(getTuple(UPLOAD_RESULT_URL)));

		submenuUpload.addElement(getItem("Upload Student Details",
				UPLOAD_PERSONAL_DETAILS_URL));
		tabUpload.getChildren().add(
				getPanel(getTuple(UPLOAD_PERSONAL_DETAILS_URL)));

		model.addElement(submenuUpload);
		accordion.getChildren().add(tabUpload);

		//
		DefaultSubMenu submenusettings = new DefaultSubMenu();
		submenusettings.setLabel("Settings");
		//
		Tab tabsettings = new Tab();
		tabsettings.setTitle("Settings");

		submenusettings.addElement(getItem("Change Password",
				CHANGE_PASSWORD_URL));
		tabsettings.getChildren().add(getPanel(getTuple(CHANGE_PASSWORD_URL)));

		submenusettings.addElement(getItem("Logout", LOGOUT_URL));
		tabsettings.getChildren().add(getPanel(getTuple(LOGOUT_URL)));

		model.addElement(submenusettings);
		accordion.getChildren().add(tabsettings);

	}

	public MenuModel getModel() {
		if (model == null)
			load();
		return model;
	}

	public void activateMenu() {
		MenuTuple activeTuple = getCurrentTuple();
		if (activeTuple != null) {
			accordion.setActiveIndex(activeTuple.getPackageIndex());
			activateTab(activeTuple);
		}
	}

	public AccordionPanel getAccordion() {
		if (accordion == null)
			load();
		activateMenu();
		return accordion;
	}

	public void setAccordion(AccordionPanel accordion) {
		this.accordion = accordion;
	}

	private void activateTab(MenuTuple tuple) {
		UIComponent c = accordion.findComponent("menuTitle" + tuple.getEntry());
		Panel p = (Panel) c;
		p.setStyleClass(" ui-state-active ");
	}

	private Panel getPanel(MenuTuple tuple) {
		Panel panel = new Panel();
		panel.setId("menuTitle" + tuple.getEntry());
		// panel.setStyleClass(" ui-state-disactive ");
		HtmlOutputLink link = new HtmlOutputLink();
		link.setValue(getContextPath() + tuple.getUrl());
		HtmlOutputText h = new HtmlOutputText();
		h.setValue(tuple.getTitle());
		link.getChildren().add(h);
		panel.getChildren().add(link);
		return panel;
	}

	private DefaultMenuItem getItem(String title, String url) {
		DefaultMenuItem item = new DefaultMenuItem();
		item.setValue(title);
		item.setUrl(url);
		return item;
	}

	public MenuTuple getCurrentTuple() {
		return getTuple(getServletPath());
	}

	public MenuTuple getTuple(String url) {
		return map.get(url);
	}

	public String getRequestURL() {
		Object request = FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request instanceof HttpServletRequest) {
			return ((HttpServletRequest) request).getRequestURL().toString();
		} else {
			return "";
		}
	}

	public String getServletPath() {
		Object request = FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request instanceof HttpServletRequest) {
			return ((HttpServletRequest) request).getServletPath();
		} else {
			return "";
		}
	}

	public String getContextPath() {
		Object request = FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (request instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest) request;
			return req.getContextPath();
		} else {
			return "";
		}
	}

}