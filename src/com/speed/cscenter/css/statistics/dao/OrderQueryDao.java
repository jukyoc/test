package com.speed.cscenter.css.statistics.dao;
import java.util.List;

import com.speed.cscenter.css.statistics.vo.OrderQueryStatistics;
public interface OrderQueryDao {
	/**
	 * 工单导出
	 */
	public List<OrderQueryStatistics> queryOrderStatisticsRecordList(OrderQueryStatistics orderQueryVo) throws Exception;
	/**
	 * 排队超过20s接起
	 * @param orderQueryVo(只包含开始时间可结束时间)
	 */
	public List<OrderQueryStatistics> queryOrderStatisticsTime20List(OrderQueryStatistics orderQueryVo) throws Exception;
}
