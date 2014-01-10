package org.housemart.server.web.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class BaseController {
	
	/**
	 * 
	 * @return
	 */
	protected HttpServletRequest getRequest(){
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    return attr.getRequest();
	}
	
}
