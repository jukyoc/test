package com.speed.cscenter.css.statistics.dao.impl;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.speed.base.dao.BaseDao;
import com.speed.base.vo.ParamObj;
import com.speed.cscenter.css.statistics.dao.OrderQueryDao;
import com.speed.cscenter.css.statistics.vo.OrderQueryStatistics;
@Repository
public class OrderQueryDaoImpl implements OrderQueryDao {
	@Resource(name = "baseDao")
	private BaseDao baseDao;
	public static final String dbJndi = "report";
	public static final String namespace = "com.speed.cscenter.css.statistics.dao.OrderQueryDao.";
	@Override
	public List<OrderQueryStatistics> queryOrderStatisticsRecordList(OrderQueryStatistics orderQueryVo) throws Exception {
		ParamObj param = new ParamObj();
		param.setDs(dbJndi);
		param.setCondition(orderQueryVo);
		param.setSqlId(namespace.concat("orderQueryStatisticsDownload"));
		return baseDao.getDataList(param);
	}
	@Override
	public List<OrderQueryStatistics> queryOrderStatisticsTime20List(OrderQueryStatistics orderQueryVo) throws Exception {
		ParamObj param = new ParamObj();
		param.setDs(dbJndi);
		param.setCondition(orderQueryVo);
		param.setSqlId(namespace.concat("orderQueryStatisticsTime20"));
		return baseDao.getDataList(param);
	}
}
