package com.speed.base.dao;

import java.util.List;

public interface BatchDao {
	/**
	 * 批量插入数据
	 * @param list
	 * @param dataSource 数据源
	 * @throws Exception
	 */
	long insertBatchObjects(List list, String dataSource, String sqlId);
	/**
	 * 批量更新数据
	 * @param list
	 * @param dataSource
	 * @param sqlId
	 * @return
	 */
	long updateBatchObjects(List list, String dataSource, String sqlId);
}
