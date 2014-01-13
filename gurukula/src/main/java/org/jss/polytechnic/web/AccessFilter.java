package org.jss.polytechnic.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;

public class AccessFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc)
			throws IOException, ServletException {

		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpSession session = httpReq.getSession(true);
		if (ObjectUtils.equals(session.getAttribute("AUTHORIZED"),
				ObjectUtils.NULL)) {
			httpReq.getRequestDispatcher(
					httpReq.getContextPath() + "/jsf/login.faces").forward(req,
					res);
		} else {
			httpReq.getRequestDispatcher(
					httpReq.getContextPath() + "/jsf/welcome.faces").forward(
					req, res);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
