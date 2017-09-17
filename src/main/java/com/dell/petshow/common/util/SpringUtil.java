package com.dell.petshow.common.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Spring 工具类
 * @author p1ma
 *
 */
public class SpringUtil implements ServletContextListener {

	private static WebApplicationContext springContext;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		springContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {

	}

	public static ApplicationContext getApplicationContext() {
		return springContext;
	}

	public SpringUtil() {}


	public static <T> T getBean(Class<T> requiredType) {

		if (springContext == null) {

			throw new RuntimeException("springContext is null.");
		}
		return springContext.getBean(requiredType);
	}

}
