package org.housemart.server.handler;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.brilliance.middleware.client.ClientWrapper;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.web.context.SpringContextHolder;
import org.housemart.rpc.stubs.log.UserAccessLoggerServiceStub;
import org.housemart.server.concurrent.TheadServiceProvider;
import org.housemart.server.dao.entities.ClientRegisterEntity;
import org.housemart.server.service.AuthenticationService;
import org.housemart.server.util.RPCUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * @author Administrator
 *
 */
public class HandlerInterceptor  extends HandlerInterceptorAdapter {

	private static final ThreadLocal<StopWatch> threadSession = new ThreadLocal<StopWatch>(){
	    protected synchronized StopWatch initialValue() {
	        return new StopWatch();
	      }
	    }; 
	final static UserAccessLoggerServiceStub USER_ACCESS_REMOTE_INSTANCE = (UserAccessLoggerServiceStub) ClientWrapper.powerStub(UserAccessLoggerServiceStub.class, 
			 RPCUtils.getDefaultRPCServer(), RPCUtils.getDefaultRPCServerPort());

	    
	@Autowired
	AuthenticationService authenticationService = SpringContextHolder.getBean("authenticationService");
	
	@SuppressWarnings("rawtypes")
	private GenericDao clientRegisterDao = SpringContextHolder.getBean("clientRegisterDao");
	
	private static final Logger logger = LoggerFactory.getLogger("CommonLogger");

	private static class GetAndPutAdapter {
		
		static void populateParameters(final HttpServletRequest request) throws UnsupportedEncodingException {
			
			String queryString = request.getQueryString();
			if(queryString == null || queryString.trim().equalsIgnoreCase("")){
				return;
			}
			String decoded = URLDecoder.decode(queryString, "UTF-8");
			String[] pares = decoded.split("&");
			Map<String, String> parameters = new HashMap<String, String>();
			if(pares != null && pares.length >= 1){
				for(String pare : pares) {
				    String[] nameAndValue = pare.split("=");
				    if(nameAndValue != null && nameAndValue.length == 2){
					    parameters.put(nameAndValue[0], nameAndValue[1]);
				    }
				}
			}
			
			request.setAttribute("QueryString", parameters);
			return;
		}
		
	}
	
	//before the actual handler will be executed
	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(final HttpServletRequest request, 
		HttpServletResponse response, Object handler)
	    throws Exception {
		
		setCurrentHttpSessionID(request);
		super.preHandle(request, response, handler);			
		
		try {
			GetAndPutAdapter.populateParameters(request);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("QueryString", new HashMap<String, String>());
		}
		
		final String requestKey = String.valueOf(Calendar.getInstance().getTime().getTime());
		String URL = request.getRequestURI();
		if(URL != null && URL.equalsIgnoreCase("/server/serverLoggerTool.controller")){
			return true;
		}
		
		String query = request.getQueryString();
		Enumeration<String> paraNames = request.getParameterNames();
		StringBuilder para = new StringBuilder();
		while(paraNames.hasMoreElements()){
			String name = (String) paraNames.nextElement();
			para.append(name + ":" + request.getParameter(name) + ";");
		}
		
		StringBuilder header = new StringBuilder();
		Enumeration<String> names = request.getHeaderNames();
		while(names.hasMoreElements()){
			String name = (String) names.nextElement();
			header.append(name + ":" + request.getHeader(name) + ";");
		}
		
		logger.info("requestKey: " + requestKey + ";URL==>" + URL + (query == null? StringUtils.EMPTY: "?" + query));
		logger.info("requestKey: " + requestKey + ";Para==>" + para);
		logger.info("requestKey: " + requestKey + ";Header==>" + header.toString());
		
		String secret = request.getParameter("secret");
		
		if (secret != null && !URL.contains("logout.controller"))
		{
			Map<String, String> brokerInfo = authenticationService.decodeSecretKey(secret);
			
			Map<Object, Object> map = new HashMap<Object, Object>();
			boolean pass = false;
			int isExpired = 1;
			
			if (brokerInfo.containsKey("brokerId") && brokerInfo.containsKey("clientUId"))
			{
				map.put("brokerId", brokerInfo.get("brokerId"));
				map.put("clientID", brokerInfo.get("clientUId"));
				map.put("brokerLogin", 1);
				List<ClientRegisterEntity> list = (List<ClientRegisterEntity>) clientRegisterDao.select("findClientRegisterList", map);
				
				pass = list.size() == 1;
				isExpired = 0;
			}
			
			if (!pass)
			{
				response.sendRedirect(request.getContextPath() + "/broker/relogin.controller?isExpired=" + isExpired);
				return false;
			}
		}
		
		return true;
	}

	/** **/
	public void setCurrentHttpSessionID(final HttpServletRequest request) {
		
		StopWatch watch = new StopWatch();
		threadSession.set(watch);
		watch.start();
		try {
			request.setAttribute("RequestUID",  InetAddress.getLocalHost().getHostName() + ":" + String.valueOf(Calendar.getInstance().getTime().getTime()));
		} catch (UnknownHostException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("RequestUID",  String.valueOf(Calendar.getInstance().getTime().getTime()));
		}

		
	}
	
	@Override
	public void afterCompletion(final HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		super.afterCompletion(request, response, handler, ex);
		StopWatch watch = threadSession.get();
		watch.stop();
		final long timeDiff = watch.getTime();
		logger.info("begin data tracking!");
		TheadServiceProvider.getThreadService().execute(new Runnable(){

			@Override
			public void run() {																						
								
				try {
					Object urlObj = request.getRequestURL();
					USER_ACCESS_REMOTE_INSTANCE.access("Page Request Context", -1, urlObj == null? "":urlObj.toString(), generateContext(request));				
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}	
			}
			
		});
		
		TheadServiceProvider.getThreadService().execute(new Runnable(){

			@Override
			public void run() {																						
								
				try {
					Object urlObj = request.getRequestURL();
					USER_ACCESS_REMOTE_INSTANCE.access("Page Load Performance", -1, urlObj == null? "":urlObj.toString(), generateTimeDiffString(request, timeDiff));
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}	
			}
			
		});
		
		
	}
	
	@SuppressWarnings({"unchecked"})
	private String generateContext(final HttpServletRequest request) {
		
		StringBuilder result = new StringBuilder();
		Object json = null;
		result.append("{");
		
		result.append("'RequestUID':" + "'" + request.getAttribute("RequestUID") + "',");
		
		result.append("'Headers':{");
		Enumeration<String> headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()){
			String name = (String) headerNames.nextElement();
			result.append("'" + name + "':'" + request.getHeader(name) + (headerNames.hasMoreElements() ?"',":"'"));
		}
		result.append("},");
		
		result.append("{'Query:'" + request.getQueryString() + "'},");
		
		result.append("'Parameters':{");
		Enumeration<String> paraNames = request.getParameterNames();
		while(paraNames.hasMoreElements()){
			String name = (String) paraNames.nextElement();
			result.append("'" + name + "':'" + request.getParameter(name) + (paraNames.hasMoreElements() ?"',":"'"));
		}
		result.append("}");
		
		if((json = request.getAttribute("JSON_RESULT")) != null){
			result.append(",{'Result':'" + json + "'");
		}
		result.append("}");
		return result.toString();
		
	}

	/**
	 * 
	 * @param request
	 * @param time
	 * @return
	 */
	private String generateTimeDiffString(final HttpServletRequest request, long time) {
		
		StringBuilder result = new StringBuilder();
		result.append("{");
		result.append("'RequestUID':" + "'" + request.getAttribute("RequestUID") + "',");
		result.append("'Duration':" + "'" + time + "'");
		result.append("}");
		
		return result.toString();
	}
	
		
}
