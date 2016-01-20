/**
 * 
 */
package com.speed.autoreport.service;

import java.util.List;

import com.speed.management.quartz.vo.JobDataTime;

/**
 * @author gengxj
 *
 */
public interface NotConnectedStatisticsService {
	
	
	public Object createExcelForDay(String day,List<JobDataTime> jobDataTimes)throws Exception;
	
	//public Object createExcelForMonth(String month)throws Exception;

}