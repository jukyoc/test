/**
 * 
 */
package com.speed.autoreport.quartz;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;

import com.speed.autoreport.service.CallAllStatisticsService;
import com.speed.autoreport.util.DateUtil;
import com.speed.management.quartz.dao.SchedulerManageDao;
import com.speed.management.quartz.service.BaseJobTemplate;
import com.speed.management.quartz.service.SchedulerManageService;
import com.speed.management.quartz.util.SpringUtils;
import com.speed.management.quartz.vo.JobDataTime;

/**
 * @author liuhl
 *
 */
public class DailyCallAllStatisticsJob extends BaseJobTemplate{
	
	private static Logger log = Logger.getLogger("InfoFile");
	
	private final static String DAY_KEY = "day";
	
	private CallAllStatisticsService callAllStatisticsService;
	private final static String JOBN_AME_KEY = "JOB_NAME";
	@Override
	public void executes(JobExecutionContext context)throws Exception{
		List<JobDataTime> jobDataTimes;
		{
			String jobName=context.getJobDetail().getJobDataMap().getString(JOBN_AME_KEY).trim();
			SchedulerManageDao schedulerManageDao= (SchedulerManageDao) SpringUtils.getContext().getBean("schedulerManageDao");
			JobDataTime jt=new JobDataTime();jt.setJobName(jobName);
			  jobDataTimes=schedulerManageDao.queryJobDataTime(jt);
			DateUtil.dealWithJobDataTimesDay(jobDataTimes);
		}
		String day = "";
		Map map = context.getMergedJobDataMap();
		if(map.containsKey(DAY_KEY)){
			day = String.valueOf(map.get(DAY_KEY));
		}else{
			day = DateUtil.getYestodayStr();
		}
		
		log.info(">>>话务综合统计-按天统计  job start , date:"+day);
		if(callAllStatisticsService==null){
			callAllStatisticsService = (CallAllStatisticsService) SpringUtils.getContext().getBean("callAllStatisticsService");
		}
		try {
			callAllStatisticsService.createExcelForDay(day,jobDataTimes);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw e;
		}
		log.info("<<<话务综合统计-按天统计 job end , date:"+day);
	}
	public static void main(String[] args) {
		System.out.println(DateUtil.getYestodayStr());
	}
	
}
