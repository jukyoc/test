package com.speed.base.dao;

import java.util.List;

import com.speed.base.vo.PageBean;
import com.speed.base.vo.ParamObj;

public interface BaseDao {
	/**
	 * 执行数据库插入操作
	 * @param condition : 
	 * @return 
	 */
	public int insertObj(ParamObj condition);
	
	/**
	 * 分页查询操作
	 * @param condition ： 分页条件
	 * @param currentlyPage ：当前第几页
	 * @param pageSize ：每页多少条
	 * @return
	 */
	public PageBean getDataListForPage(ParamObj condition,int pageNo,int pageSize);
	/**
	 * 根据条件查询结果列表
	 * @param condition ：查询条件
	 * @return 查询结果列表
	 */
	public List getDataList(ParamObj condition);
	/**
	 * 根据条件查询单个结果
	 * @param condition
	 * @return
	 */
	public Object getObject(ParamObj condition);
	/**
	 * 根据条件更新操作
	 * @param condition
	 * @return
	 */
	public int updateObj(ParamObj condition);
	/**
	 * 根据条件删除的操作
	 * @param condition
	 * @return
	 */
	public int deleteObj(ParamObj condition);
}
