package com.speed.management.quartz.service;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.speed.base.vo.PageBean;
import com.speed.management.quartz.util.Constants;
import com.speed.management.quartz.util.SpringUtils;
import com.speed.management.quartz.vo.JobInfo;
import com.speed.management.quartz.vo.JobRunLog;

public abstract class BaseJobTemplate implements Job, Constants{
	
	private SchedulerManageService schedulerManageService;
	
	public BaseJobTemplate(){
		
	}
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		if(schedulerManageService==null){
			//long l1 = System.currentTimeMillis();
			schedulerManageService = (SchedulerManageService) SpringUtils.getContext().getBean("schedulerManageService");
			//long l2 = System.currentTimeMillis();
			//System.err.println("获取schedulerManageService:time="+(l2-l1));
		}
		
		JobRunLog log = new JobRunLog();
		log.setExecBeginTime(getTimeNow());
		String jobName = (String) context.getJobDetail().getJobDataMap().get("JOB_NAME");
		String jobGroup = (String) context.getJobDetail().getJobDataMap().get("JOB_GROUP");
		
		JobInfo jobInfo = new JobInfo();
		jobInfo.setJobName(jobName);
		jobInfo.setJobGroup(jobGroup);
		
		try{
			PageBean temp = schedulerManageService.queryJobInfoForPage(jobInfo,1,10);
			jobInfo = (JobInfo) temp.getDataList().get(0);
			log.setJobId(jobInfo.getJobId());
			
			executes(context);
			log.setExecEndTime(getTimeNow());
			log.setExecStatus(JOB_EXEC_STATUS_SUCCESS);
		}catch(Exception e){
			log.setExecEndTime(getTimeNow());
			log.setExecStatus(JOB_EXEC_STATUS_FAILED);
			String msg = e.getMessage();
			if(msg.length()>200){
				log.setErrorMessage(e.getMessage().substring(0, 150));
			}else{
				log.setErrorMessage(e.getMessage());
			}
			e.printStackTrace();
		}finally{
			try {
				schedulerManageService.saveJobExecLog(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public abstract void executes(JobExecutionContext context)throws Exception;

	
	private static String getTimeNow(){
		return DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
}
