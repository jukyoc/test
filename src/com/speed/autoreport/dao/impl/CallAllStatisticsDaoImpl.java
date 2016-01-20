/**
 *
 */
package com.speed.autoreport.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.speed.autoreport.dao.CallAllStatisticsDao;
import com.speed.autoreport.vo.CallAgentStatisticsObj;
import com.speed.autoreport.vo.CallAllStatisticsObj;
import com.speed.base.dao.BaseDao;
import com.speed.base.vo.ParamObj;

/**
 * @author liuhl
 *
 */
@Repository("callAllStatisticsDao")
public class CallAllStatisticsDaoImpl implements CallAllStatisticsDao {

	@Resource(name="baseDao")
	private BaseDao baseDao;

	public static final String DS_NAME = "report";

	@Override
	public List<CallAllStatisticsObj> queryCallAllStatisticsData(
			CallAllStatisticsObj callAllStatisticsObj) throws Exception {
		ParamObj paramObj = new ParamObj();
		paramObj.setDs(DS_NAME);
		paramObj.setSqlId("queryCallAllStatistics");
		paramObj.setCondition(callAllStatisticsObj);
		return baseDao.getDataList(paramObj);
	}

	@Override
	public List<CallAgentStatisticsObj> queryAgentCallStatisticsData(CallAllStatisticsObj callAllStatisticsObj) throws Exception {
		ParamObj paramObj = new ParamObj();
		paramObj.setDs(DS_NAME);
		paramObj.setSqlId("queryAgentCallStatisticsData");
		paramObj.setCondition(callAllStatisticsObj);
		return baseDao.getDataList(paramObj);
	}
	@Override
	public List<CallAgentStatisticsObj> queryAgentCountCallStatisticsData(CallAllStatisticsObj callAllStatisticsObj) throws Exception {
		ParamObj paramObj = new ParamObj();
		paramObj.setDs(DS_NAME);
		paramObj.setSqlId("queryAgentCountCallStatisticsData");
		paramObj.setCondition(callAllStatisticsObj);
		return baseDao.getDataList(paramObj);
	}
}
