package com.speed.base.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OracleDialect extends Dialect {
	// 日志对象
	protected static Logger log = LoggerFactory.getLogger(OracleDialect.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mybatis.extend.interceptor.IDialect#getLimitString(java.lang.String,
	 * int, int)
	 */
	@Override
	public String getLimitString(String sql, int start, int end) {
		sql = sql.trim();
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);

		pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");

		pagingSelect.append(sql);

		pagingSelect.append(" ) row_ ) where rownum_ >= ").append(start)
				.append(" and rownum_ <= ").append(end);
		log.debug(pagingSelect.toString());
		return pagingSelect.toString();
	}

}
