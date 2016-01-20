package com.speed.cscenter.css.statistics.service.impl;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.speed.cscenter.css.statistics.dao.OrderQueryDao;
import com.speed.cscenter.css.statistics.service.OrderQueryService;
import com.speed.cscenter.css.statistics.util.DateUtil;
import com.speed.cscenter.css.statistics.vo.OrderQueryStatistics;
@Service
public class OrderQueryServiceImpl implements OrderQueryService {
	@Resource
	private OrderQueryDao orderQueryDao;
	@Override
	public List<OrderQueryStatistics> queryOrderStatisticsRecordList(OrderQueryStatistics orderQueryStatistics) throws Exception {
		List<OrderQueryStatistics> list = orderQueryDao.queryOrderStatisticsRecordList(orderQueryStatistics);
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat dfymd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		OrderQueryStatistics vo = null;
		for (int i = 0; i < list.size(); i++) {
			vo = list.get(i);
			vo.setOrderDealFormat(DateUtil.secondFormat(c, df, vo.getOrderDeal()));
			vo.setOptTimeFormat(DateUtil.secondFormat(c, df, vo.getOptTime()));
			vo.setRecordTimeFormat(DateUtil.secondFormat(c, df, vo.getRecordTime()));
			vo.setSubTimeFormat(DateUtil.dateFormat(vo.getSubTime(), dfymd));
			vo.setFinishTimeFormat(DateUtil.dateFormat(vo.getFinishTime(), dfymd));
		}
		return list;
	}
	@Override
	public List<OrderQueryStatistics> queryOrderStatisticsTime20List(OrderQueryStatistics orderQueryVo) throws Exception {
		List<OrderQueryStatistics> list = orderQueryDao.queryOrderStatisticsTime20List(orderQueryVo);
		SimpleDateFormat df = null;
		if (list.size() > 0) {
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		for (OrderQueryStatistics vo : list) {
			vo.setCallerTimeFormat(DateUtil.dateFormat(vo.getCallerTime(), df));
		}
		return list;
	}
}
