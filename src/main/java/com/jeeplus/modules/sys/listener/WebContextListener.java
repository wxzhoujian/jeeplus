package com.jeeplus.modules.sys.listener;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;

import com.jeeplus.modules.sys.service.SystemService;

public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {
	
	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
		if (!SystemService.printKeyLoadMessage()){
			return null;
		}
		return super.initWebApplicationContext(servletContext);
	}
}
