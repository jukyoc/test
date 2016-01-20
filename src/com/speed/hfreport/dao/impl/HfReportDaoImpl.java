/**
 * 
 */
package com.speed.hfreport.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.speed.autoreport.vo.BusinessInfo;
import com.speed.base.dao.BaseDao;
import com.speed.base.vo.ParamObj;
import com.speed.hfreport.dao.HfReportDao;
import com.speed.util.KeyValueVo;

/**
 * @author wangjian
 *
 */
@Repository(value="hfReportDao")
public class HfReportDaoImpl implements HfReportDao {

	public static final String CSCENTER = "report";
	
	@Resource(name="baseDao")
	private BaseDao baseDao;

	/*
	 * 查询业务线名称
	 */
	@SuppressWarnings("unchecked")
	public List<KeyValueVo> queryBusinessName(){
		ParamObj param = new ParamObj();
		param.setDs(CSCENTER);
        param.setSqlId("queryBusinessName");
        return baseDao.getDataList(param);
	}
	@Override
	public List<KeyValueVo> queryQueueInfo(Integer businessId) throws Exception {
		ParamObj obj = new ParamObj();
		obj.setDs(CSCENTER);
		obj.setSqlId("queryQueueName");
		obj.setCondition(businessId);
		return baseDao.getDataList(obj);
	}
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public BaseDao getBaseDao() {
		return baseDao;
	}
	
}
