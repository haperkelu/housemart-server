package org.housemart.server.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ServerLogToolController {

	
	@RequestMapping(value = "serverLoggerTool.controller")
	public ModelAndView serverLoggerTool() {
		
		
		return new ModelAndView("textView", "path", "/mnt/log/IOS-Server/access.log");
		
	}
	
	@RequestMapping(value = "serverIndexJob.controller")
	public ModelAndView serverIndexJob() {
		
		return new ModelAndView("textView", "path", "/mnt/log/IOS-Server/common.log");
		
	}
	
}
