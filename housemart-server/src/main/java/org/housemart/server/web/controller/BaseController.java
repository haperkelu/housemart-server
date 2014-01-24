package org.housemart.server.web.controller;

import java.util.Map;

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
	
	@SuppressWarnings("unchecked")
	protected String getQueryString(String name){
		
		HttpServletRequest req = this.getRequest();
		if(req != null) {
			
			Map<String, String> map = (Map<String, String>) req.getAttribute("QueryString");
			if(map != null ){
				return map.get(name) == null ? null : map.get(name).toString();
			}
		}
		return null;
	}
	
}
