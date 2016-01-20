/**
 * 
 */
package com.speed.hfreport.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.speed.autoreport.dao.BusinessInfoDao;
import com.speed.autoreport.vo.BusinessInfo;
import com.speed.hfreport.dao.HfReportDao;
import com.speed.hfreport.service.HfReportService;
import com.speed.hfreport.vo.HfReportVo;
import com.speed.util.KeyValueVo;

/**
 * @author wangjian
 *
 */
@Service("hfReportService")
public class HfReportServiceImpl implements HfReportService{

	@Resource(name="hfReportDao")
	private HfReportDao hfReportDao;
	
	/*
	 * 查询业务线名称
	 */
	public List<KeyValueVo> queryBusinessName(){
		return hfReportDao.queryBusinessName();
	}
	 
	@Override
	public List<KeyValueVo> queryQueueInfo(Integer businessId) throws Exception {
		return hfReportDao.queryQueueInfo(  businessId);
	}
}
