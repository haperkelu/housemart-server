/**   
* @Title: UserAccessEntity.java 
* @Package org.housemart.server.dao.entities 
* @Description: TODO
* @author Pie.Li   
* @date 2014-4-3 下午4:58:49 
* @version V1.0   
*/
package org.housemart.server.dao.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Pie.Li
 *
 */
@Entity
@Table(name="user_access_log")
public class UserAccessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name="BizTag")
    private String bizTag;
    
    @Column(name="UserID")    
    private int userId;
    
    @Column(name="AccessText")    
    private String accessText;
    
    @Column(name="URL")    
    private String url;
    
    @Column(name="AddTime")    
    private Date addTime;   

    /**
	 * @return the addTime
	 */
	public Date getAddTime() {
		return addTime;
	}

	/**
	 * @param addTime the addTime to set
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	/**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the bizTag
     */
    public String getBizTag() {
        return bizTag;
    }

    /**
     * @param bizTag the bizTag to set
     */
    public void setBizTag(String bizTag) {
        this.bizTag = bizTag;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the accessText
     */
    public String getAccessText() {
        return accessText;
    }

    /**
     * @param accessText the accessText to set
     */
    public void setAccessText(String accessText) {
        this.accessText = accessText;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
}