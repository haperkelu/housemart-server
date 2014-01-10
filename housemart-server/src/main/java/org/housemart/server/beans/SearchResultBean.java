/**
 * Created on 2013-10-30
 * 
 */
package org.housemart.server.beans;

import java.util.List;

public class SearchResultBean <T>{
  List<T> data;
  int totalCount;
  public List<T> getData() {
    return data;
  }
  public void setData(List<T> data) {
    this.data = data;
  }
  public int getTotalCount() {
    return totalCount;
  }
  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }
}
