package org.jss.polytechnic.utils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import javax.faces.convert.FacesConverter;

import org.jss.polytechnic.dao.SubjectDao;

@FacesConverter(value = "subjectConverter")
public class SubjectConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		throw new UnsupportedOperationException(" Operation Not supported");
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object obj) {
		if (obj instanceof String) {
			StringBuilder sb = new StringBuilder(50);
			sb.append(SubjectDao.getSubjectName(obj.toString()));
			sb.append("(").append(obj).append(")");
			return sb.toString();
		} else {
			return obj.toString();
		}
	}

}
