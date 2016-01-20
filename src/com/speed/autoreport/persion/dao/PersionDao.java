package com.speed.autoreport.persion.dao;

import java.util.List;

import com.speed.autoreport.persion.vo.PersionRecord;

public interface PersionDao {

	public List<PersionRecord> queryPersionRecord(String startTime,String endTime,String businessId) throws Exception;
	public List<PersionRecord> queryPersionRecord(String startTime,String endTime,String businessId,String queueId) throws Exception;
	
}
