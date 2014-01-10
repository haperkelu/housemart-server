/**
 * Created on 2012-12-3
 * 
 */
package org.housemart.server.schedule;

import org.housemart.server.schedule.tasks.APNSTask;
import org.housemart.server.schedule.tasks.GooglePlaceIndexTask;
import org.housemart.server.schedule.tasks.HouseIndexTask;
import org.housemart.server.schedule.tasks.NotificationTask;
import org.housemart.server.schedule.tasks.ResidencePriceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HouseMartServerTimer {
  private final static int INTERVAL = 1000 * 60 * 30;
  private static final Logger logger = LoggerFactory.getLogger(HouseMartServerTimer.class);
  
  public HouseMartServerTimer() {
    
    //Timer timer = new Timer();
    //timer.schedule(new HouseIndexTask(), 1000 * 5, INTERVAL * 2);
    //timer.schedule(new GooglePlaceIndexTask(), 1000 * 20, INTERVAL * 48);
    //timer.schedule(new APNSTask(), 1000 * 60, INTERVAL / 6);
    //timer.schedule(new ResidencePriceTask(), 1000 * 5, INTERVAL * 48 * 10);
    
    final HouseIndexTask task1 = new HouseIndexTask();
    Thread t1 = new Thread(new Runnable() {
		
		@SuppressWarnings("static-access")
		@Override
		public void run() {
			
			while(true){
			
				try {
					logger.info("HouseIndexTask begin to execute");
					task1.run();
				} catch (Exception e1) {
					logger.error(e1.getMessage(), e1);
				}
				try {
					Thread.currentThread().sleep(INTERVAL/10);
				} catch (InterruptedException e) {
				}
				
			}			
		}		
	});
    
    final GooglePlaceIndexTask task2 = new GooglePlaceIndexTask();
    Thread t2 = new Thread(new Runnable() {
		
		@SuppressWarnings("static-access")
		@Override
		public void run() {
			
			while(true){
			
				try {
					logger.info("GooglePlaceIndexTask begin to execute");
					task2.run();
				} catch (Exception e1) {
					logger.error(e1.getMessage(), e1);
				}
				try {
					Thread.currentThread().sleep(INTERVAL * 2);
				} catch (InterruptedException e) {
				}
				
			}			
		}		
	});
    
    final APNSTask task3 = new APNSTask();
    Thread t3 = new Thread(new Runnable() {
		
		@SuppressWarnings("static-access")
		@Override
		public void run() {
			
			while(true){
			
				try {
					logger.info("APNSTask begin to execute");
					task3.run();
				} catch (Exception e1) {
					logger.error(e1.getMessage(), e1);
				}
				try {
					Thread.currentThread().sleep(INTERVAL / 30);
				} catch (InterruptedException e) {
				}
				
			}			
		}		
	});
    
    final ResidencePriceTask task4 = new ResidencePriceTask();
    Thread t4 = new Thread(new Runnable() {
		
		@SuppressWarnings("static-access")
		@Override
		public void run() {
			
			while(true){
			
				try {
					logger.info("ResidencePriceTask begin to execute");
					task4.run();
				} catch (Exception e1) {
					logger.error(e1.getMessage(), e1);
				}
				try {
					Thread.currentThread().sleep(INTERVAL * 48 * 10);
				} catch (InterruptedException e) {
				}
				
			}			
		}		
	});
    
    final NotificationTask task5 = new NotificationTask();
    Thread t5 = new Thread(new Runnable() {
		
		@SuppressWarnings("static-access")
		@Override
		public void run() {
			
			while(true){
			
				try {
					logger.info("NotificationTask begin to execute");
					task5.run();
				} catch (Exception e1) {
					logger.error(e1.getMessage(), e1);
				}
				try {
					Thread.currentThread().sleep(INTERVAL * 2);
				} catch (InterruptedException e) {
				}
				
			}			
		}		
	});
	
	t1.start();   
    t2.start();  
    t3.start();
	t4.start();
	t5.start();
  }
}
