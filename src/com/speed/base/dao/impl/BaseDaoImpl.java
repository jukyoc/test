package com.speed.base.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.speed.base.dao.BaseDao;
import com.speed.base.vo.PageBean;
import com.speed.base.vo.ParamObj;
public class BaseDaoImpl implements BaseDao {

	private Map<String, SqlSessionFactory> sqlSessionBeans;

	@Override
	public int insertObj(ParamObj condition) {
		SqlSessionFactory sqlSessionFactory = this.sqlSessionBeans
				.get(condition.getDs());
		int insertResult = 0;
		SqlSession session = sqlSessionFactory.openSession();
		try {
			if (condition.getCondition() != null) {
				insertResult = session.insert(condition.getSqlId(),
						condition.getCondition());
			} else {
				insertResult = session.insert(condition.getSqlId());
			}
		} finally {
			session.close();
		}
		return insertResult;
	}

	@Override
	public List<?> getDataList(ParamObj condition) {
		SqlSessionFactory sqlSessionFactory = this.sqlSessionBeans
				.get(condition.getDs());
		SqlSession session = sqlSessionFactory.openSession();
		List<?> resultList = null;
		try {
			if (condition.getCondition() != null) {
				resultList = session.selectList(condition.getSqlId(),
						condition.getCondition());
			} else {
				resultList = session.selectList(condition.getSqlId());
			}
		} finally {
			session.close();
		}
		return resultList;
	}

	@Override
	public Object getObject(ParamObj condition) {
		SqlSessionFactory sqlSessionFactory = this.sqlSessionBeans
				.get(condition.getDs());
		SqlSession session = sqlSessionFactory.openSession();
		Object result = null;
		try {
			if (condition.getCondition() != null) {
				result = session.selectOne(condition.getSqlId(),
						condition.getCondition());
			} else {
				result = session.selectOne(condition.getSqlId());
			}
		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public int updateObj(ParamObj condition) {
		SqlSessionFactory sqlSessionFactory = this.sqlSessionBeans
				.get(condition.getDs());
		SqlSession session = sqlSessionFactory.openSession();
		int updateResult = 0;
		try {
			if (condition.getCondition() != null) {
				updateResult = session.update(condition.getSqlId(),
						condition.getCondition());
			} else {
				updateResult = session.update(condition.getSqlId());
			}
		} finally {
			session.close();
		}
		return updateResult;
	}

	@Override
	public int deleteObj(ParamObj condition) {
		SqlSessionFactory sqlSessionFactory = this.sqlSessionBeans
				.get(condition.getDs());
		SqlSession session = sqlSessionFactory.openSession();
		int deleteResult = 0;
		try {
			if (condition.getCondition() != null) {
				deleteResult = session.delete(condition.getSqlId(),
						condition.getCondition());
			} else {
				deleteResult = session.delete(condition.getSqlId());
			}
		} finally {
			session.close();
		}
		return deleteResult;
	}

	@Override
	public PageBean getDataListForPage(ParamObj condition, int pageNo,
			int pageSize) {
		SqlSessionFactory sqlSessionFactory = this.sqlSessionBeans.get(condition
				.getDs());
		SqlSession session = sqlSessionFactory.openSession();
		PageBean pb = null;
		try {
			Integer recordCount = session.selectOne(condition.getCountSqlId(),condition);
			pb = new PageBean(pageNo, pageSize, recordCount);
			condition.setStart(pb.getStart());
			condition.setEnd(pb.getEnd());
			List<?> dataList = session.selectList(condition.getSqlId(), condition);
			pb.setDataList(dataList);
		} finally {
			session.close();
		}
		return pb;
	}

	public Map<String, SqlSessionFactory> getSqlSessionBeans() {
		return sqlSessionBeans;
	}

	public void setSqlSessionBeans(
			Map<String, SqlSessionFactory> sqlSessionBeans) {
		this.sqlSessionBeans = sqlSessionBeans;
	}

}
