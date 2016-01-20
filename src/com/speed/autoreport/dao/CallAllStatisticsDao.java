/**
 *
 */
package com.speed.autoreport.dao;

import java.util.List;

import com.speed.autoreport.vo.CallAgentStatisticsObj;
import com.speed.autoreport.vo.CallAllStatisticsObj;

/**
 * @author liuhl
 *
 */
public interface CallAllStatisticsDao {

	public List<CallAllStatisticsObj> queryCallAllStatisticsData(CallAllStatisticsObj callAllStatisticsObj)throws Exception;
	/**
	 * 按照人统计电话详细
	 */
	public List<CallAgentStatisticsObj> queryAgentCallStatisticsData(CallAllStatisticsObj callAllStatisticsObj) throws Exception;
	/**
	 * 按照人统计电话详细
	 */
	public List<CallAgentStatisticsObj> queryAgentCountCallStatisticsData(CallAllStatisticsObj callAllStatisticsObj) throws Exception;
}
