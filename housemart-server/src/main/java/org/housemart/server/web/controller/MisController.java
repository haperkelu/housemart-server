package org.housemart.server.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.server.beans.ResultBean;
import org.housemart.server.beans.ResutlCodeEnum;
import org.housemart.server.beans.VersionBean;
import org.housemart.server.dao.entities.AppClientFileEntity;
import org.housemart.server.dao.entities.FeedBackEntity;
import org.housemart.server.dao.entities.HouseLeadsEntity;
import org.housemart.server.dao.entities.KeyValueEntity;
import org.housemart.server.util.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MisController extends BaseController {
  
  @SuppressWarnings("rawtypes")
  @Autowired
  private GenericDao houseLeadsDao;
  @Autowired
  private GenericDao<KeyValueEntity> keyValueDao;
  @Autowired
  private GenericDao<AppClientFileEntity> appClientFileDao;
  
  private static final String KEY_CLIENT_VERSION = "client_version";
  private static final String ATTR_REQUIRED_VERSION = "requiredVersion";
  private static final String ATTR_CURRENT_VERSION = "currentVersion";
  private static final String ATTR_CURRENT_VERSION_INFO = "currentVersionInfo";
  private ObjectMapper mapper = new ObjectMapper();
  
  @RequestMapping(value = "feedback/add.controller")
  public ModelAndView feedBackAdd(@RequestParam String name, @RequestParam String mobile, @RequestParam String text) {
    
    ResultBean bean = new ResultBean();
    FeedBackEntity entity = new FeedBackEntity();
    entity.setName(name);
    entity.setContent(text);
    entity.setMobile(mobile);
    
    String clientUID;
    if ((clientUID = this.getRequest().getParameter("clientUId")) != null) {
      entity.setClientUid(clientUID);
    }
    try {
      houseLeadsDao.add("addFeeback", entity);
      bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    } catch (Exception e) {
      LoggerUtils.error(e.getMessage(), e);
      bean.setCode(ResutlCodeEnum.ERROR.getType());
    }
    return new ModelAndView("jsonView", "json", bean);
    
  }
  
  @RequestMapping(value = "house/add.controller")
  public ModelAndView houseAdd(@RequestParam String name, @RequestParam String mobile, @RequestParam String residenceName,
      @RequestParam String address, @RequestParam String area) {
    
    ResultBean bean = new ResultBean();
    HouseLeadsEntity entity = new HouseLeadsEntity();
    entity.setContactName(name);
    entity.setMobile(mobile);
    entity.setAddress(address);
    entity.setResidenceName(residenceName);
    entity.setArea(area);
    
    String clientUID;
    if ((clientUID = this.getRequest().getParameter("clientUId")) != null) {
      entity.setClientUid(clientUID);
    }
    
    try {
      houseLeadsDao.add("addHouseLeads", entity);
      bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    } catch (Exception e) {
      LoggerUtils.error(e.getMessage(), e);
      bean.setCode(ResutlCodeEnum.ERROR.getType());
    }
    
    return new ModelAndView("jsonView", "json", bean);
    
  }
  
  
  /**
   * 
   * @param osType (i-ios, w-wp, a-android)
   * @param clientType (b-broker, p-popular)
   * @return
   */
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "version.controller")
  public ModelAndView version(@RequestParam String osType, @RequestParam String clientType) {
    
    ResultBean bean = new ResultBean();
    try {
      String finalKey = KEY_CLIENT_VERSION + "_" + clientType + "_" + osType;
      Map<String,Object> para = new HashMap<String,Object>();
      para.put("key", finalKey);
      List<KeyValueEntity> keyValues = keyValueDao.select("selectByKey", para);
      
      VersionBean vb = new VersionBean();
      if (CollectionUtils.isNotEmpty(keyValues)) {
        KeyValueEntity entity = keyValues.get(0);
        Map<String,String> value = mapper.readValue(entity.getValue(), Map.class);
        String currentVersion = value.get(ATTR_CURRENT_VERSION) == null ? "" : value.get(ATTR_CURRENT_VERSION);
        String requiredVersion = value.get(ATTR_REQUIRED_VERSION) == null ? "" : value.get(ATTR_REQUIRED_VERSION);
        String versionInfo = value.get(ATTR_CURRENT_VERSION_INFO) == null ? "" : value.get(ATTR_CURRENT_VERSION_INFO);
        vb.setCurrentVersion(currentVersion);
        vb.setRequiredVersion(requiredVersion);
        vb.setCurrentVersionInfo(versionInfo);
        
        Map<String,Object> para1 = new HashMap<String,Object>();
        para1.put("osType", osType);
        para1.put("clientType", clientType);
        para1.put("version", currentVersion);
        List<AppClientFileEntity> appFileList = appClientFileDao.select("findAppClientFileList", para1);
        if (CollectionUtils.isNotEmpty(appFileList)) {
          vb.setLink(appFileList.get(0).getCloudURL());
        }
        
      } else {
        vb.setCurrentVersion("1.1");
        vb.setRequiredVersion("1.1");
        vb.setCurrentVersionInfo("");
        vb.setLink("");
      }
      
      bean.setData(vb);
      bean.setCode(ResutlCodeEnum.SUCCESS.getType());
    } catch (Exception e) {
      LoggerUtils.error(e.getMessage(), e);
      bean.setData(new VersionBean());
      bean.setCode(ResutlCodeEnum.ERROR.getType());
    }
    
    return new ModelAndView("jsonView", "json", bean);
    
  }
  
}
