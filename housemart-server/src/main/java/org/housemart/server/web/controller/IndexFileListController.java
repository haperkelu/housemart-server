/** 
 * @Title: IndexFileListController.java
 * @Package org.housemart.server.web.controller
 * @Description: TODO
 * @author Pie.Li
 * @date 2013-3-18 下午3:38:05
 * @version V1.0 
 */
package org.housemart.server.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName: IndexFileListController
 * @Description: TODO
 * @date 2013-3-18 下午3:38:05
 * 
 */
@Controller
public class IndexFileListController {

	private static final Map<String, FilePair> _map = new HashMap<String, FilePair>();
	private static final String p1 = "/data/store/housemart/index/houseIndex";
	
	@RequestMapping(value = "indexFileView.controller")
	public ModelAndView indexFileView() {

		StringBuilder result = new StringBuilder();
		synchronized (_map) {
			_map.clear();
			List<FilePair> list = generatePath(p1);
			if (!CollectionUtils.isEmpty(list)) {
				for (FilePair item : list) {
					// final String key =
					// String.valueOf(Calendar.getInstance().getTime().getTime());
					final String key = UUID.randomUUID().toString();
					_map.put(key, item);
					result.append("<a href=\"/server/indexFileContent.controller?key=" + key + "\">" + item.getPath()
							+ "</a>" + "<br/>");
				}
			}
		}

		return new ModelAndView("textView", "content", result.toString());

	}
	
	@RequestMapping(value = "indexFileAchive.controller")
	public ModelAndView indexFileAchive(){
		return new ModelAndView("zipView", "path", p1);
	}

	@RequestMapping(value = "indexFileContent.controller")
	public ModelAndView indexFileViewContent(@RequestParam String key, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Object target = null;
		synchronized (_map) {
			target = _map.get(key);
		}
//		StringBuilder result = new StringBuilder();
		if (target != null) {
			File file = new File(((FilePair) target).path);
			 
	        
			if (file.exists()) {

//				FileInputStream in = null;
//				InputStreamReader inReader = null;
//				BufferedReader bufReader = null;
//				try {
//					in = new FileInputStream(file);
//					inReader = new InputStreamReader(in);
//					bufReader = new BufferedReader(inReader);
//					String data = null;
//					while ((data = bufReader.readLine()) != null) {
//						result.append(data);
//					}

				BufferedInputStream bis = null;  
		        BufferedOutputStream bos = null;
		        try{
					long fileLength = file.length();  
		            response.setContentType("application/x-msdownload;");  
		            response.setHeader("Content-disposition", "attachment; filename="  
		                    + new String(file.getName().getBytes("utf-8"), "ISO8859-1"));  
		            response.setHeader("Content-Length", String.valueOf(fileLength));  
		            bis = new BufferedInputStream(new FileInputStream(file));  
		            bos = new BufferedOutputStream(response.getOutputStream());  
		            byte[] buff = new byte[2048];  
		            int bytesRead;  
		            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
		                bos.write(buff, 0, bytesRead);  
		            }  
				} catch (Exception e) {
					throw e;
				} finally {
//					if (bufReader != null) {
//						bufReader.close();
//					}
//					if (inReader != null) {
//						inReader.close();
//					}
//					if (in != null) {
//						in.close();
//					}
					if (bis != null)  
		                bis.close();  
		            if (bos != null)  
		                bos.close();  
				}

			}
		}
//		return new ModelAndView("textView", "content", result.toString());
		return null;

	}

	private List<FilePair> generatePath(String path) {

		List<FilePair> result = new ArrayList<FilePair>();
		File file = new File(path);

		if (file.exists()) {
			String[] subFiles = file.list();
			if (subFiles != null) {
				for (String item : subFiles) {
					FilePair filePair = new FilePair();
					filePair.setName(item);
					filePair.setPath(path + "/" + item);
					result.add(filePair);
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * @ClassName: FilePair
	 * @Description: TODO
	 * @date 2013-3-18 下午4:35:29
	 * 
	 */
	private static class FilePair {
		private String name;
		private String path;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
	}

}
