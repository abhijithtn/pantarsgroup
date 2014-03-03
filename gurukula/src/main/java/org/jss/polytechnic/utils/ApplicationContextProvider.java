package org.jss.polytechnic.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware {

	private static ApplicationContext ctx;

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		ctx = context;
	}

	public static Object getBean(String name) {
		return ctx.getBean(name);
	}

}
