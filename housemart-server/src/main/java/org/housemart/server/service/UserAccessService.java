/**   
* @Title: UserAccessService.java 
* @Package org.housemart.server.service 
* @Description: TODO
* @author Pie.Li   
* @date 2014-4-3 下午5:14:39 
* @version V1.0   
*/
package org.housemart.server.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.housemart.framework.web.context.SpringContextHolder;
import org.housemart.server.dao.entities.UserAccessEntity;
import org.housemart.server.util.LoggerUtils;

/**
 * @author Pie.Li
 *
 */
public class UserAccessService {

	public void addUserAccess(UserAccessEntity user) {
		
		if(user == null){			
			throw new IllegalArgumentException("User Access Eneity should not be null");			
		}
		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = SpringContextHolder.getBean("entityManagerFactory");              
			em = emf.createEntityManager();         
			EntityTransaction tx = em.getTransaction();                                 
			                                   
			tx.begin();
			em.persist(user);
			tx.commit();
		} catch (Exception e) {
			LoggerUtils.error(e.getMessage(), e);
			throw new RuntimeException("Error in persisting user access entity!");
		} finally {
			if(em != null) {em.close();}
			if(emf != null) {emf.close();}
		}
         

		
	}
	
	
}
