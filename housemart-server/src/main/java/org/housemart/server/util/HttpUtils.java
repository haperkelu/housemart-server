/**
 * Created on 2013-8-4
 * 
 */
package org.housemart.server.util;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.housemart.server.beans.AjaxResultBean;

public class HttpUtils {
  
  public static AjaxResultBean requestJson(HttpGet httpGet) throws ClientProtocolException, IOException, ParseException {
    
    DefaultHttpClient httpClient = new DefaultHttpClient();
    httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
    HttpResponse httpResponse = httpClient.execute(httpGet);
    
    HttpEntity entitys = httpResponse.getEntity();
    String response = EntityUtils.toString(entitys);
    AjaxResultBean responseBean = new ObjectMapper().readValue(response, AjaxResultBean.class);
    return responseBean;
    
  }
  
  public static AjaxResultBean requestJson(HttpPost httpPost) throws ClientProtocolException, IOException, ParseException {
	    
	    DefaultHttpClient httpClient = new DefaultHttpClient();
	    httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
	    HttpResponse httpResponse = httpClient.execute(httpPost);
	    
	    HttpEntity entitys = httpResponse.getEntity();
	    String response = EntityUtils.toString(entitys);
	    AjaxResultBean responseBean = new ObjectMapper().readValue(response, AjaxResultBean.class);
	    return responseBean;
	    
	  }
  
}
