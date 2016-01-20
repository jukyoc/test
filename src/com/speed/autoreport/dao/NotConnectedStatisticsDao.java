/**
 * 
 */
package com.speed.autoreport.dao;

import java.util.List;

import com.speed.autoreport.vo.NotConnectedStatisticsObj;

/**
 * @author gengxj
 *
 */
public interface NotConnectedStatisticsDao {
	
	public List<NotConnectedStatisticsObj> queryNoConnectedStatisticsData(NotConnectedStatisticsObj notConnectedStatisticsObj)throws Exception;
	public List queryExampleList(NotConnectedStatisticsObj notConnectedStatisticsObj)throws Exception;
}
