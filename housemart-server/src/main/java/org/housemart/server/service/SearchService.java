/**
 * Created on 2012-11-28
 * 
 */
package org.housemart.server.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.search.HouseMartIndexer;
import org.housemart.framework.search.HouseMartWriter;
import org.housemart.framework.web.context.SpringContextHolder;
import org.housemart.server.dao.entities.AreaPositionEntity;
import org.housemart.server.dao.entities.GooglePlaceBaseEntity;
import org.housemart.server.dao.entities.GooglePlaceBaseEntity.FIELD;
import org.housemart.server.dao.entities.HouseEntity;
import org.housemart.server.dao.entities.HouseInteractionNoticeEntity;
import org.housemart.server.dao.entities.HousePicEntity;
import org.housemart.server.dao.entities.HousePicSortEntity;
import org.housemart.server.map.MapSearchUtils;
import org.housemart.server.util.GenericCollections;
import org.housemart.server.util.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author pqin
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class SearchService {
  
  public static final int DEFAULT_SEARCH_MAX_SIZE = 99999;
  public static final int HOUSE_INDEX_MUTEX_EXPECT = 1;
  public static Integer HOUSE_INDEX_MUTEX = HOUSE_INDEX_MUTEX_EXPECT;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final static String houseIndexBizCode = "houseIndex";
  private final static String houseIndexTmpBizCode = "houseIndexTmp";
  private final static String googleIndexBizCode = "googleIndex";
  private int[] enumRadius = new int[] {1000, 2000, 4000, 6000};
  private GenericDao houseDao = SpringContextHolder.getBean("houseDao");
  private GenericDao googlePlaceDao = SpringContextHolder.getBean("googlePlaceDao");
  private GenericDao housePicDao = SpringContextHolder.getBean("housePicDao");
  private GenericDao housePicSortDao = SpringContextHolder.getBean("housePicSortDao");
  private GenericDao houseInteractionNoticeDao = SpringContextHolder.getBean("houseInteractionNoticeDao");
  public static final String PIC_SPLITTER = "12qwaszx,";
  
  /**
   * 添加BigTableHouse
   * 
   * @param houses
   * @throws Exception
   */
  public void addOrUpdateHouses(String bizCode, HouseEntity[] houses) throws Exception {
    
    IndexWriter writer = HouseMartWriter.getBizIndexWrieter(bizCode);
    Document[] docs = null;
    if (!ArrayUtils.isEmpty(houses)) {
      docs = new Document[houses.length];
      for (int i = 0; i < houses.length; i++) {
        docs[i] = houses[i].toDocument();
      }
    }
    HouseMartWriter.addOrUpdateDocuments(writer, docs, HouseEntity.FIELD.id.toString());
    HouseMartWriter.closeWriter(bizCode);
    
  }
  
  /**
   * 
   * @throws Exception
   */
  public void resetLastTaskTime() throws Exception {
    logger.info("reset all lastTaskTime");
    houseDao.update("resetAllTaskTime", null);
  }
  
  /**
   * 
   * @throws IOException
   */
  public void resetTmpDir() throws IOException {
    logger.info("reset tmp dir");
    File tmpDir = new File(HouseMartWriter.getIndexDirOf(houseIndexTmpBizCode));
    if (tmpDir.exists()) {
      FileUtils.forceDelete(tmpDir);
    }
  }
  
  /**
   * 搜索BigTableHouse
   * 
   * @param queries
   * @param occurs
   * @param sort
   * @param pageIndex
   * @param pageSize
   * @return
   * @throws Exception
   */
  public HouseEntity[] searchHouse(Query[] queries, Occur[] occurs, Sort sort, int pageIndex, int pageSize) throws Exception {
    
    ScoreDoc[] results = null;
    HouseEntity[] entities = null;
    
    Query query = mergeQueries(queries, occurs);
    if (sort != null) {
      results = HouseMartIndexer.search(houseIndexBizCode, query, DEFAULT_SEARCH_MAX_SIZE, sort);
    } else {
      results = HouseMartIndexer.search(houseIndexBizCode, query, DEFAULT_SEARCH_MAX_SIZE);
    }
    
    if (!ArrayUtils.isEmpty(results)) {
      int skip = PageUtils.generateSkipNumber(pageIndex, pageSize);
      int size = PageUtils.generateSize(results.length, skip, pageSize);
      
      IndexSearcher searcher = HouseMartIndexer.getBizIndexSearcher(houseIndexBizCode);
      entities = new HouseEntity[size];
      for (int i = skip; i < skip + size; i++) {
        entities[i - skip] = HouseEntity.fromDocument(searcher.doc(results[i].doc));
      }
    }
    
    return entities;
    
  }
  
  public int countHouse(Query[] queries, Occur[] occurs) throws Exception {
    
    int result = 0;
    ScoreDoc[] results = null;
    
    Query query = mergeQueries(queries, occurs);
    results = HouseMartIndexer.search(houseIndexBizCode, query, DEFAULT_SEARCH_MAX_SIZE);
    
    if (!ArrayUtils.isEmpty(results)) {
      result = results.length;
    }
    
    return result;
  }
  
  /**
   * 添加经纬度
   * 
   * @param places
   * @throws Exception
   */
  public void addOrUpdateGooglePlace(GooglePlaceBaseEntity[] places) throws Exception {
    
    IndexWriter writer = HouseMartWriter.getBizIndexWrieter(googleIndexBizCode);
    Document[] docs = null;
    if (!ArrayUtils.isEmpty(places)) {
      docs = new Document[places.length * enumRadius.length];
      int k = 0;
      for (int i = 0; i < places.length; i++) {
        for (int j = 0; j < enumRadius.length; j++) {
          docs[k] = places[i].toDocument(enumRadius[j]);
          k++;
        }
      }
    }
    HouseMartWriter.addOrUpdateDocuments(writer, docs, FIELD.key.toString());
    HouseMartWriter.closeWriter(googleIndexBizCode);
    
  }
  
  /**
   * 根据经纬度搜索
   * 
   * @param lat
   * @param lng
   * @param radius
   * @return
   * @throws Exception
   */
  // TODO:为了提高检索效率，可以增加在售，和在租的状态信息
  public GooglePlaceBaseEntity[] searchGooglePlace(double lat, double lng, double radius, boolean hasOnSale, boolean hasOnRent)
      throws Exception {
    
    ScoreDoc[] resultsLng = null;
    List<GooglePlaceBaseEntity> entities = null;
    
    List<Query> queryListLng = new ArrayList<Query>();
    queryListLng.add(NumericRangeQuery.newDoubleRange(FIELD.latFrom.toString(), null, lat, true, true));
    queryListLng.add(NumericRangeQuery.newDoubleRange(FIELD.latTo.toString(), lat, null, true, true));
    queryListLng.add(NumericRangeQuery.newDoubleRange(FIELD.lngFrom.toString(), null, lng, true, true));
    queryListLng.add(NumericRangeQuery.newDoubleRange(FIELD.lngTo.toString(), lng, null, true, true));
    queryListLng.add(NumericRangeQuery.newDoubleRange(FIELD.radius.toString(), radius, radius, true, true));
    if (hasOnSale) {
      BooleanQuery bq = new BooleanQuery();
      bq.add(NumericRangeQuery.newIntRange(FIELD.onSaleCount.toString(), 1, null, true, true), Occur.SHOULD);
      bq.add(NumericRangeQuery.newIntRange(FIELD.forceShow.toString(), 1, null, true, true), Occur.SHOULD);
      queryListLng.add(bq);
    }
    if (hasOnRent) {
      BooleanQuery bq = new BooleanQuery();
      bq.add(NumericRangeQuery.newIntRange(FIELD.onRentCount.toString(), 1, null, true, true), Occur.SHOULD);
      bq.add(NumericRangeQuery.newIntRange(FIELD.forceShow.toString(), 1, null, true, true), Occur.SHOULD);
      queryListLng.add(bq);
    }
    
    Query[] queries = queryListLng.toArray(new Query[queryListLng.size()]);
    Occur[] occurs = new Occur[queries.length];
    for (int i = 0; i < occurs.length; i++)
      occurs[i] = Occur.MUST;
    Query queryLng = mergeQueries(queries, occurs);
    resultsLng = HouseMartIndexer.search("googleIndex", queryLng, DEFAULT_SEARCH_MAX_SIZE);
    
    IndexSearcher searcher = HouseMartIndexer.getBizIndexSearcher(googleIndexBizCode);
    entities = new ArrayList<GooglePlaceBaseEntity>();
    for (int i = 0; i < resultsLng.length; i++) {
      GooglePlaceBaseEntity entity = GooglePlaceBaseEntity.fromDocument(searcher.doc(resultsLng[i].doc));
      if (entity != null && !entities.contains(entity)) {
        double dist = MapSearchUtils.getDistance(lat, lng, Float.valueOf(entity.getLat()), Float.valueOf(entity.getLng()));
        if (dist <= radius) {
          entity.setDistance(dist);
          entities.add(entity);
        }
      }
    }
    
    Collections.sort(entities, new Comparator<GooglePlaceBaseEntity>() {
      
      @Override
      public int compare(GooglePlaceBaseEntity o1, GooglePlaceBaseEntity o2) {
        return (int) (o1.getDistance() - o2.getDistance());
      }
      
    });
    
    return entities.toArray(new GooglePlaceBaseEntity[entities.size()]);
    
  }
  
  /**
   * 
   * @param lat
   * @param lng
   * @param positions
   * @return
   */
  public AreaPositionEntity searchPlateByPosition(double lat, double lng, List<AreaPositionEntity> positions) {
    AreaPositionEntity p = null;
    double minDistance = Double.MAX_VALUE;
    for (AreaPositionEntity ap : positions) {
      double d = 0;
      if ((d = MapSearchUtils.getDistance(lat, lng, Double.valueOf(ap.getLat()), Double.valueOf(ap.getLng()))) < minDistance) {
        minDistance = d;
        p = ap;
      }
    }
    return p;
  }
  
  /**
   * 合并Query
   * 
   * @param queries
   * @param occurs
   * @return
   */
  public Query mergeQueries(Query[] queries, Occur[] occurs) {
    
    BooleanQuery mergedQuery = new BooleanQuery();
    int index = 0;
    for (Query query : queries) {
      if (query != null) mergedQuery.add(query, occurs[index]);
      index++;
    }
    return mergedQuery;
    
  }
  
  /**
   * 构建BigTableHouse索引 先删除所有房源，再创建
   * 
   * @throws Exception
   */
  public void buildHouseIndex() throws Exception {
    
    logger.info("start build house index ... ");
    
    try {
      int indexNumber = 0;
      if (HOUSE_INDEX_MUTEX < HOUSE_INDEX_MUTEX_EXPECT) {
        logger.warn("one house index task is runing, this task will exit");
        return;
      }
      HOUSE_INDEX_MUTEX--;
      
      File tmpDir = new File(HouseMartWriter.getIndexDirOf(houseIndexTmpBizCode));
      
      // 房源信息引起的改动，并索引
      int min = houseDao.count("findMinHouseId", null);
      int max = houseDao.count("findMaxHouseId", null) + 1;
      int task = 5000;
      int loop = (max - min) / task;
      int minVal = min;
      int maxVal = max;
      for (int i = 0; i <= loop; i++) {
        
        logger.info("buildHouseIndex " + ((int) min + i * task) + ", total " + max);
        
        minVal = task * i + min;
        if (loop != i) {
          maxVal = minVal + task;
        } else {
          maxVal = max;
        }
        
        Date timeStamp = new Date();
        // house with interaction
        Map para = new HashMap();
        para.put("houseIdFrom", minVal);
        para.put("houseIdTo", maxVal);
        para.put("hasNewUpdate", true);
        List<HouseEntity> housesWithInteraction = houseDao.select("findHouseListWithInteraction", para);
        try {
          if (CollectionUtils.isNotEmpty(housesWithInteraction)) {
            for (HouseEntity h : housesWithInteraction) {
              try {
                h.setInteraction(HouseEntity.InteractionEnum.With.value);
                h.setPicURL(sortPics(h.getId(), h.getPicURL()));
              } catch (Exception e) {
                logger.error(e.getMessage(), e);
              }
            }
            addOrUpdateHouses(houseIndexTmpBizCode, housesWithInteraction.toArray(new HouseEntity[housesWithInteraction.size()]));
            indexNumber += housesWithInteraction.size();
          }
        } catch (Exception e) {
          try {
            HouseMartWriter.closeWriter(houseIndexTmpBizCode);
          } catch (Exception ex) {
            HouseMartWriter.unlockBizIndexWriter(houseIndexTmpBizCode);
          }
          logger.error(e.getMessage(), e);
        }
        
        // house without interaction
        List<HouseEntity> housesWithoutInteraction = houseDao.select("findHouseListWithoutInteraction", para);
        try {
          if (CollectionUtils.isNotEmpty(housesWithoutInteraction)) {
            for (HouseEntity h : housesWithoutInteraction) {
              try {
                h.setInteraction(HouseEntity.InteractionEnum.Without.value);
                h.setPicURL(sortPics(h.getId(), h.getPicURL()));
              } catch (Exception e) {
                logger.error(e.getMessage(), e);
              }
            }
            addOrUpdateHouses(houseIndexTmpBizCode,
                housesWithoutInteraction.toArray(new HouseEntity[housesWithoutInteraction.size()]));
            indexNumber += housesWithoutInteraction.size();
          }
        } catch (Exception e) {
          try {
            HouseMartWriter.closeWriter(houseIndexTmpBizCode);
          } catch (Exception ex) {
            HouseMartWriter.unlockBizIndexWriter(houseIndexTmpBizCode);
          }
          logger.error(e.getMessage(), e);
        }
        
        Map pUpdate = new HashMap();
        pUpdate.put("houseIdFrom", minVal);
        pUpdate.put("houseIdTo", maxVal);
        pUpdate.put("lastTaskTime", timeStamp);
        houseDao.update("updateLastTaskTime", pUpdate);
      }
      
      // 交互权变更引起的改动，并索引
      
      // 变更为有交互权
      Map paraNoticeWithInteraction = new HashMap();
      paraNoticeWithInteraction.put("type", HouseInteractionNoticeEntity.TypeEnum.ToWithInteraction.value);
      paraNoticeWithInteraction.put("status", HouseInteractionNoticeEntity.StatusEnum.Unread);
      paraNoticeWithInteraction.put("limitCount", 100);
      List<HouseInteractionNoticeEntity> noticeToWithInteraction = houseInteractionNoticeDao.select(
          "findHouseInteractionNoticeList", paraNoticeWithInteraction);
      if (CollectionUtils.isNotEmpty(noticeToWithInteraction)) {
        Set<Integer> houseIds = new HashSet<Integer>();
        Map<Integer,Date> addTimes = new HashMap<Integer,Date>();
        
        for (HouseInteractionNoticeEntity entity : noticeToWithInteraction) {
          houseIds.add(entity.getHouseId());
          addTimes.put(entity.getHouseId(), entity.getAddTime());
          
          try {
            Map updatePara = new HashMap();
            updatePara.put("id", entity.getId());
            updatePara.put("status", HouseInteractionNoticeEntity.StatusEnum.Read.value);
            updatePara.put("updateTime", new Date());
            houseInteractionNoticeDao.update("updateHouseInteractionNotice", updatePara);
          } catch (Exception e) {
            logger.error(e.getMessage(), e);
          }
        }
        
        Map para01 = new HashMap();
        para01.put("houseIdsIn", GenericCollections.join(houseIds, ","));
        para01.put("status", 1);
        List<HouseEntity> housesWithInteraction = houseDao.select("findHouseList", para01);
        
        try {
          if (CollectionUtils.isNotEmpty(housesWithInteraction)) {
            
            Iterator<HouseEntity> iterator = housesWithInteraction.iterator();
            while (iterator.hasNext()) {
              HouseEntity h = iterator.next();
              
              try {
                if (h.getUpdateTime().after(addTimes.get(h.getId()))) {
                  // 忽略
                  iterator.remove();
                  continue;
                }
                
                h.setInteraction(HouseEntity.InteractionEnum.With.value);
                h.setPicURL(sortPics(h.getId(), h.getPicURL()));
              } catch (Exception e) {
                logger.error(e.getMessage(), e);
              }
            }
            
            addOrUpdateHouses(houseIndexTmpBizCode, housesWithInteraction.toArray(new HouseEntity[housesWithInteraction.size()]));
            indexNumber += housesWithInteraction.size();
          }
        } catch (Exception e) {
          try {
            HouseMartWriter.closeWriter(houseIndexTmpBizCode);
          } catch (Exception ex) {
            HouseMartWriter.unlockBizIndexWriter(houseIndexTmpBizCode);
          }
          logger.error(e.getMessage(), e);
        }
      }
      
      // 变更为无交互权
      Map paraNoticeWithoutInteraction = new HashMap();
      paraNoticeWithoutInteraction.put("type", HouseInteractionNoticeEntity.TypeEnum.ToWithoutInteraction.value);
      paraNoticeWithoutInteraction.put("status", HouseInteractionNoticeEntity.StatusEnum.Unread);
      paraNoticeWithoutInteraction.put("limitCount", 100);
      List<HouseInteractionNoticeEntity> noticeToWithoutInteraction = houseInteractionNoticeDao.select(
          "findHouseInteractionNoticeList", paraNoticeWithoutInteraction);
      if (CollectionUtils.isNotEmpty(noticeToWithoutInteraction)) {
        Set<Integer> houseIds = new HashSet<Integer>();
        Map<Integer,Date> addTimes = new HashMap<Integer,Date>();
        
        for (HouseInteractionNoticeEntity entity : noticeToWithoutInteraction) {
          houseIds.add(entity.getHouseId());
          addTimes.put(entity.getHouseId(), entity.getAddTime());
          
          try {
            Map updatePara = new HashMap();
            updatePara.put("id", entity.getId());
            updatePara.put("status", HouseInteractionNoticeEntity.StatusEnum.Read.value);
            updatePara.put("updateTime", new Date());
            houseInteractionNoticeDao.update("updateHouseInteractionNotice", updatePara);
          } catch (Exception e) {
            logger.error(e.getMessage(), e);
          }
        }
        
        Map para01 = new HashMap();
        para01.put("houseIdsIn", GenericCollections.join(houseIds, ","));
        List<HouseEntity> housesWithoutInteraction = houseDao.select("findHouseList", para01);
        
        try {
          if (CollectionUtils.isNotEmpty(housesWithoutInteraction)) {
            
            Iterator<HouseEntity> iterator = housesWithoutInteraction.iterator();
            while (iterator.hasNext()) {
              HouseEntity h = iterator.next();
              
              try {
                if (h.getUpdateTime().after(addTimes.get(h.getId()))) {
                  // 忽略
                  iterator.remove();
                  continue;
                }
                
                h.setInteraction(HouseEntity.InteractionEnum.Without.value);
                h.setPicURL(sortPics(h.getId(), h.getPicURL()));
                
              } catch (Exception e) {
                logger.error(e.getMessage(), e);
              }
            }
            
            addOrUpdateHouses(houseIndexTmpBizCode,
                housesWithoutInteraction.toArray(new HouseEntity[housesWithoutInteraction.size()]));
            indexNumber += housesWithoutInteraction.size();
          }
        } catch (Exception e) {
          try {
            HouseMartWriter.closeWriter(houseIndexTmpBizCode);
          } catch (Exception ex) {
            HouseMartWriter.unlockBizIndexWriter(houseIndexTmpBizCode);
          }
          logger.error(e.getMessage(), e);
        }
      }
      
      Thread.sleep(1000);
      
      HouseMartWriter.getBizIndexWrieter(houseIndexTmpBizCode);
      HouseMartWriter.optimize(houseIndexTmpBizCode);
      HouseMartWriter.closeWriter(houseIndexTmpBizCode);
      
      if (indexNumber > 0) {
        File indexDir = new File(HouseMartWriter.getIndexDirOf(houseIndexBizCode));
        
        if (indexDir.exists()) {
          try {
            IndexSearcher searcher = HouseMartIndexer.getBizIndexSearcher(houseIndexBizCode);
            searcher.getIndexReader().close();
          } catch (Exception e) {
            logger.error(e.getMessage(), e);
          }
          FileUtils.forceDelete(indexDir);
        }
        Thread.sleep(1000);
        FileUtils.copyDirectory(tmpDir, indexDir);
      }
      HouseMartIndexer.resetIndexerMap(null);
      
      HOUSE_INDEX_MUTEX = HOUSE_INDEX_MUTEX_EXPECT;
      logger.info("buildHouseIndex finish, total " + indexNumber + " houses updated");
      
    } catch (Exception e) {
      HOUSE_INDEX_MUTEX = HOUSE_INDEX_MUTEX_EXPECT;
      HouseMartWriter.unlockBizIndexWriter(houseIndexTmpBizCode);
      logger.error(e.getMessage(), e);
    } finally {
      HOUSE_INDEX_MUTEX = HOUSE_INDEX_MUTEX_EXPECT;
    }
  }
  
  public void deleteAllInteractionNotice() {
    Map<String,Object> para = new HashMap<String,Object>();
    para.put("status", HouseInteractionNoticeEntity.StatusEnum.Unread.value);
    houseInteractionNoticeDao.delete("deleteHouseInteractionNotice", para);
  }
  
  /**
   * 构建经纬度索引
   * 
   * @throws Exception
   */
  public void buildGooglePlaceIndex() throws Exception {
    
    synchronized (googleIndexBizCode) {
      int min = googlePlaceDao.count("findMinId", null);
      int max = googlePlaceDao.count("findMaxId", null);
      int task = 1000;
      int loop = (max - min) / task;
      int minVal = min;
      int maxVal = max;
      for (int i = 0; i <= loop; i++) {
        minVal = task * i + min;
        if (loop != i) maxVal = minVal + task;
        else maxVal = max;
        
        Map para = new HashMap();
        para.put("idFrom", minVal);
        para.put("idTo", maxVal);
        List<GooglePlaceBaseEntity> googlePlaces = googlePlaceDao.select("findRawDat", para);
        try {
          if (CollectionUtils.isNotEmpty(googlePlaces)) {
            addOrUpdateGooglePlace(googlePlaces.toArray(new GooglePlaceBaseEntity[googlePlaces.size()]));
          }
        } catch (Exception e) {
          logger.error(e.getMessage(), e);
        }
      }
      
      HouseMartWriter.getBizIndexWrieter(googleIndexBizCode);
      HouseMartWriter.optimize(googleIndexBizCode);
      HouseMartWriter.closeWriter(googleIndexBizCode);
      
      HouseMartIndexer.resetIndexerMap(googleIndexBizCode);
    }
    
  }
  
  public String sortPics(int houseId, String originPicURL) {
    
    String finalSortedPics = "";
    
    // sort
    Map housePicsSortPara = new HashMap();
    housePicsSortPara.put("houseId", houseId);
    housePicsSortPara.put("type", HousePicEntity.Type.HousePic.getValue());
    List<HousePicSortEntity> housePicsSort = housePicSortDao.select("findHousePicSortByHouseIdAndType", housePicsSortPara);
    Map roomTypesSortPara = new HashMap();
    roomTypesSortPara.put("houseId", houseId);
    roomTypesSortPara.put("type", HousePicEntity.Type.RoomType.getValue());
    List<HousePicSortEntity> roomTypesSort = housePicSortDao.select("findHousePicSortByHouseIdAndType", roomTypesSortPara);
    
    if (CollectionUtils.isEmpty(housePicsSort) && CollectionUtils.isEmpty(roomTypesSort)) {
      return originPicURL;
    }
    
    // pics
    List<HousePicEntity> housePics = housePicDao.select("findHousePicByHouseIdAndType", housePicsSortPara);
    List<HousePicEntity> roomTypes = housePicDao.select("findHousePicByHouseIdAndType", roomTypesSortPara);
    
    if (CollectionUtils.isNotEmpty(housePics)) {
      
      List<HousePicEntity> sortedPics = housePics;
      if (CollectionUtils.isNotEmpty(housePicsSort)) {
        sortedPics = sortPics(housePics, housePicsSort.get(0).getSort());
      }
      
      List<String> sortedPicsURL = new ArrayList<String>();
      for (HousePicEntity se : sortedPics) {
        if (StringUtils.isBlank(se.getCloudUrl())) {
          continue;
        }
        sortedPicsURL.add(se.getCloudUrl());
      }
      finalSortedPics = finalSortedPics + GenericCollections.join(sortedPicsURL, PIC_SPLITTER);
      
    }
    
    if (CollectionUtils.isNotEmpty(roomTypes)) {
      
      List<HousePicEntity> sortedPics = roomTypes;
      if (CollectionUtils.isNotEmpty(roomTypesSort)) {
        sortedPics = sortPics(roomTypes, roomTypesSort.get(0).getSort());
      }
      
      List<String> sortedPicsURL = new ArrayList<String>();
      for (HousePicEntity se : sortedPics) {
        if (StringUtils.isBlank(se.getCloudUrl())) {
          continue;
        }
        sortedPicsURL.add(se.getCloudUrl());
      }
      
      if (StringUtils.isNotBlank(finalSortedPics)) {
        finalSortedPics = finalSortedPics + PIC_SPLITTER;
      }
      
      finalSortedPics = finalSortedPics + GenericCollections.join(sortedPicsURL, PIC_SPLITTER);
      
    }
    
    return finalSortedPics;
  }
  
  public List<HousePicEntity> sortPics(List<HousePicEntity> pics, String sort) {
    
    List<HousePicEntity> sortedPics = null;
    
    if (StringUtils.isNotBlank(sort)) {
      sortedPics = new ArrayList<HousePicEntity>();
      List<String> picIds = GenericCollections.split(sort, ",");
      for (int i = 0; i < picIds.size(); i++) {
        if (StringUtils.isBlank(picIds.get(i))) {
          continue;
        }
        HousePicEntity entity = new HousePicEntity();
        entity.setId(Integer.valueOf(picIds.get(i)));
        entity = GenericCollections.getData(pics, entity);
        GenericCollections.removeData(pics, entity);
        if (entity != null) {
          sortedPics.add(entity);
        }
      }
      sortedPics.addAll(pics);
      
    } else {
      sortedPics = pics;
    }
    
    return sortedPics;
    
  }
  
  public List<HousePicEntity> findResidencePicWithSort(int residenceId) {
    
    Map<Object,Object> map = new HashMap<Object,Object>();
    map.put("residenceId", residenceId);
    List<HousePicEntity> list = housePicDao.select("findResidencePicById", map);
    
    String sort = null;
    List<HousePicSortEntity> picSorts = null;
    Map<String,Object> para = new HashMap<String,Object>();
    para.put("type", HousePicEntity.Type.ExternalResidence.getValue());
    para.put("residenceId", residenceId);
    picSorts = housePicSortDao.select("findHousePicSortByResidenceIdAndType", para);
    
    if (CollectionUtils.isNotEmpty(picSorts)) {
      sort = picSorts.get(0).getSort();
    }
    if (list != null && StringUtils.isNotBlank(sort)) {
      list = sortPics(list, sort);
    }
    
    return list;
  }
  
}
