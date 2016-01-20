/**
 * 
 */
package com.speed.autoreport.quartz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.speed.autoreport.service.CallAllStatisticsService;
import com.speed.autoreport.util.DateUtil;

/**
 * @author liuhl
 * delete 方法已废弃  job持久化入数据库
 */
public class CallAllStatisticsJob {
	
	private static Logger log = Logger.getLogger("InfoFile");
	
	private CallAllStatisticsService callAllStatisticsService;
	
	public void createExcelForDay(){
		log.info(">>>热线整体数据表格-按天统计  job start , date:"+DateUtil.getYestodayStr());
		try {
			
			//callAllStatisticsService.createExcelForDay();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		
		
		log.info("<<<热线整体数据表格-按天统计 job end , date:"+DateUtil.getYestodayStr());
	}
	
	public void createExcelForMonth(){
		log.info(">>>热线整体数据表格-按月统计  job start , month:"+DateUtil.getLastMonthStr());
		try {
			//callAllStatisticsService.createExcelForMonth();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		
		log.info("<<<热线整体数据表格-按月统计 job end , month:"+DateUtil.getLastMonthStr());
	}
	
	public CallAllStatisticsService getCallAllStatisticsService() {
		return callAllStatisticsService;
	}

	public void setCallAllStatisticsService(
			CallAllStatisticsService callAllStatisticsService) {
		this.callAllStatisticsService = callAllStatisticsService;
	}
	
}
