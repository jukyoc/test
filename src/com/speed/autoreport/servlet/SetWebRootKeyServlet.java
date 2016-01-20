package com.speed.autoreport.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.speed.management.quartz.util.SpringUtils;

public class SetWebRootKeyServlet extends HttpServlet {
	Logger log = Logger.getLogger(SetWebRootKeyServlet.class);
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		String webAppRootKey = getServletContext().getRealPath("/");
		log.debug("webapp.app is [" + webAppRootKey + "]" );
		System.setProperty("webapp.root", webAppRootKey);
		
		WebApplicationContext c = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		SpringUtils.getInstancs().setContext(c);
	}
}
