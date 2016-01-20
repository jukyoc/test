package com.speed.autoreport.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.speed.autoreport.dao.BusinessInfoDao;
import com.speed.autoreport.vo.BusinessInfo;
import com.speed.base.dao.BaseDao;
import com.speed.base.vo.ParamObj;


@Component("businessInfoDao")
public class BusinessInfoDaoImpl implements BusinessInfoDao {

	/**
	 * dao public interface
	 */
	@Resource(name="baseDao")
	private BaseDao baseDao;
	
	public static final String DS_NAME = "report";

	@Override
	public List<BusinessInfo> queryBusinessInfo(BusinessInfo info) throws Exception {
		ParamObj obj = new ParamObj();
		obj.setDs(DS_NAME);
		obj.setSqlId("queryBusinessEntity");
		obj.setCondition(info);
		return baseDao.getDataList(obj);
	}

	
}
