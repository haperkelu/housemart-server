/**   
* @Title: UserAccessTestcase.java 
* @Package org.housemart.dao 
* @Description: TODO
* @author Pie.Li   
* @date 2014-4-3 下午10:03:14 
* @version V1.0   
*/
package org.housemart.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import org.housemart.framework.web.context.SpringContextHolder;
import org.housemart.server.dao.entities.UserAccessEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Pie.Li
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/beans/spring*.xml"})
public class UserAccessTestcase {

	@Test
	public void test() {
		
		EntityManagerFactory emf =  SpringContextHolder.getBean("entityManagerFactory");              
		EntityManager em = emf.createEntityManager();         
        EntityTransaction tx = em.getTransaction();                                 
        UserAccessEntity test = em.find(UserAccessEntity.class, 1);                                                

        if(test == null){
            test = new UserAccessEntity();
            test.setBizTag("222");
            test.setUserId(1);
            test.setUrl("http://");
            test.setAccessText("text");
            tx.begin();
            em.persist(test);
            tx.commit();

        }
        
        UserAccessEntity test1 = em.find(UserAccessEntity.class, test.getId());  
        System.out.println(test1.getUrl());            
        em.close();                                                                 
        emf.close(); 
		
	}
	
}
