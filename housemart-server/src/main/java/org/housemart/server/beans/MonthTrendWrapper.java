/**
 * Created on 2014-1-6
 * 
 */
package org.housemart.server.beans;

public class MonthTrendWrapper {
  MonthTrend[] trends;
  double minRentPrice;
  double maxRentPrice;
  public MonthTrend[] getTrends() {
    return trends;
  }
  public void setTrends(MonthTrend[] trends) {
    this.trends = trends;
  }
  public double getMinRentPrice() {
    return minRentPrice;
  }
  public void setMinRentPrice(double minRentPrice) {
    this.minRentPrice = minRentPrice;
  }
  public double getMaxRentPrice() {
    return maxRentPrice;
  }
  public void setMaxRentPrice(double maxRentPrice) {
    this.maxRentPrice = maxRentPrice;
  }
}
