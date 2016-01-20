/**
 * 
 */
package com.speed.hfreport.service;

import java.util.List;

import com.speed.autoreport.vo.BusinessInfo;
import com.speed.util.KeyValueVo;

/**
 * @author wangjian
 *
 */
public interface HfReportService {

	/*
	 *查询业务线名称 
	 */
	public List<KeyValueVo> queryBusinessName();
	/**根据业务查询业务下的队列(id,name)
	 * @param Integer businessId
	 * @return
	 * @throws Exception
	 */
	public List<KeyValueVo> queryQueueInfo(Integer businessId) throws Exception;
}
