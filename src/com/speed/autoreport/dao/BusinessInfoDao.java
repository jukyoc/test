package com.speed.autoreport.dao;

import java.util.List;

import com.speed.autoreport.vo.BusinessInfo;

/**
 * business dao interface
 * @author yangbb
 *
 */
public interface BusinessInfoDao {
	public List<BusinessInfo> queryBusinessInfo(BusinessInfo info) throws Exception;
}
