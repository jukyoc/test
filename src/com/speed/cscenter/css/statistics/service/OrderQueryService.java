package com.speed.cscenter.css.statistics.service;
import java.util.List;

import com.speed.cscenter.css.statistics.vo.OrderQueryStatistics;
public interface OrderQueryService {
	/**
	 * 导出工单统计列表
	 * @param orderQueryStatistics
	 */
	public List<OrderQueryStatistics> queryOrderStatisticsRecordList(OrderQueryStatistics orderQueryStatistics) throws Exception;
	/**
	 * 排队超过20s接起
	 * @param orderQueryVo(只包含开始时间可结束时间)
	 */
	public List<OrderQueryStatistics> queryOrderStatisticsTime20List(OrderQueryStatistics orderQueryVo) throws Exception;
}
