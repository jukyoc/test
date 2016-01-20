/**
 * 
 */
package com.speed.autoreport.quartz;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;

import com.speed.autoreport.service.CallAllStatisticsService;
import com.speed.autoreport.util.DateUtil;
import com.speed.management.quartz.dao.SchedulerManageDao;
import com.speed.management.quartz.service.BaseJobTemplate;
import com.speed.management.quartz.util.SpringUtils;
import com.speed.management.quartz.vo.JobDataTime;

/**
 * @author liuhl
 *
 */
public class MonthlyDailyCallAllStatisticsJob extends BaseJobTemplate{
	
	private static Logger log = Logger.getLogger("InfoFile");
	private final static String MONTH_KEY = "month";
	
	private CallAllStatisticsService callAllStatisticsService;
	private final static String JOBN_AME_KEY = "JOB_NAME";
	@Override
	public void executes(JobExecutionContext context)throws Exception{
		List<JobDataTime> jobDataTimes;
		String month = "";
		Map map = context.getMergedJobDataMap();
		if(map.containsKey(MONTH_KEY)){
			month = String.valueOf(map.get(MONTH_KEY));
		}else{
			month = DateUtil.getLastMonthStr();
		}
		{
			String jobName=context.getJobDetail().getJobDataMap().getString(JOBN_AME_KEY).trim();
			SchedulerManageDao schedulerManageDao= (SchedulerManageDao) SpringUtils.getContext().getBean("schedulerManageDao");
			JobDataTime jt=new JobDataTime();jt.setJobName(jobName);
			  jobDataTimes=schedulerManageDao.queryJobDataTime(jt);
			DateUtil.dealWithJobDataTimesMonth(jobDataTimes);
		}
		log.info(">>>热线整体数据表格-按月统计  job start , month:"+month);
		
		if(callAllStatisticsService==null){
			callAllStatisticsService = (CallAllStatisticsService) SpringUtils.getContext().getBean("callAllStatisticsService");
		}
		try {
			callAllStatisticsService.createExcelForMonth(month,jobDataTimes);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw e;
		}
		log.info("<<<热线整体数据表格-按月统计 job end , month:"+month);
	}
	
}
