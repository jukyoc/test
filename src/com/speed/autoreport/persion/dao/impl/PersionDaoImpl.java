package com.speed.autoreport.persion.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.speed.autoreport.persion.dao.PersionDao;
import com.speed.autoreport.persion.vo.PersionRecord;
import com.speed.base.dao.BaseDao;
import com.speed.base.vo.ParamObj;

@Repository("persionDao")
public class PersionDaoImpl implements PersionDao {

	@Resource(name="baseDao")
	private BaseDao baseDao;
	private final String dataSource = "report";
	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<PersionRecord> queryPersionRecord(String startTime,
			String endTime,String businessId) throws Exception {
		ParamObj obj = new ParamObj();
		Map<String, String> map = new HashMap<String, String>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("businessId", businessId);
		obj.setCondition(map);
		obj.setDs(dataSource);
		obj.setSqlId("queryPersionRecord");
		return baseDao.getDataList(obj);
	}
	@Override
	public List<PersionRecord> queryPersionRecord(String startTime, String endTime, String businessId, String queueId) throws Exception {
		ParamObj obj = new ParamObj();
		Map<String, String> map = new HashMap<String, String>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("businessId", businessId);
		map.put("queueId", queueId);
		obj.setCondition(map);
		obj.setDs(dataSource);
		obj.setSqlId("queryPersionRecordQueue");
		return baseDao.getDataList(obj);
	}
	
}
