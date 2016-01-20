/**
 * 
 */
package com.speed.autoreport.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.speed.autoreport.dao.NotConnectedStatisticsDao;
import com.speed.autoreport.vo.NotConnectedStatisticsObj;
import com.speed.base.dao.BaseDao;
import com.speed.base.vo.ParamObj;

/**
 * @author gengxj
 *
 */
@Repository("notConnectedStatisticsDao")
public class NotConnectedStatisticsDaoImpl implements NotConnectedStatisticsDao {
	
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	public static final String DS_NAME = "report";
	
	@Override
	public List<NotConnectedStatisticsObj> queryNoConnectedStatisticsData(
			NotConnectedStatisticsObj notConnectedStatisticsObj) throws Exception {
		ParamObj paramObj = new ParamObj();
		paramObj.setDs(DS_NAME);
		paramObj.setSqlId("queryNoConnectedStatistics");
		paramObj.setCondition(notConnectedStatisticsObj);
		return this.baseDao.getDataList(paramObj);
	}
	public List queryExampleList(NotConnectedStatisticsObj notConnectedStatisticsObj)throws Exception {
		ParamObj paramObj = new ParamObj();
		paramObj.setDs(DS_NAME);
		paramObj.setSqlId("queryExampleList");
		paramObj.setCondition(notConnectedStatisticsObj);
		return this.baseDao.getDataList(paramObj);
	}
}
