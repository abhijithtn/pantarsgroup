package org.jss.polytechnic.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = { "/login.xhtml", "/pages/*" }, filterName = "cacheControlFilter")
public class CacheControlFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc)
			throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) res;
		resp.setHeader("Expires", "Tue, 03 Jul 2001 06:00:00 GMT");
		resp.setHeader("Last-Modified", new Date().toString());
		resp.setHeader("Cache-Control",
				"no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0");
		resp.setHeader("Pragma", "no-cache");

		fc.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
