package com.speed.management.quartz.service;

import java.util.List;

import com.speed.base.vo.PageBean;
import com.speed.management.quartz.vo.JobDataTime;
import com.speed.management.quartz.vo.JobInfo;
import com.speed.management.quartz.vo.JobRunLog;
import com.speed.management.quartz.vo.SchedulerJob;
import com.speed.ocss.man.vo.AgentInfoVObj;

public interface SchedulerManageService {
	
	String addJob(SchedulerJob obj,AgentInfoVObj agent)throws Exception;
	
	String pauseJob(SchedulerJob obj,AgentInfoVObj agent)throws Exception;
	
	String resumeJob(SchedulerJob obj,AgentInfoVObj agent)throws Exception;

	PageBean queryJobForPage(SchedulerJob obj,int pageNo, int pageSize)throws Exception;

	PageBean queryJobLog(SchedulerJob obj, int pageNo, int pageSize)throws Exception;
	
	void saveJobExecLog(JobRunLog log)throws Exception;

	PageBean queryJobInfoForPage(JobInfo jobInfo, int i, int j)throws Exception;

	PageBean queryJobRunningLog(SchedulerJob obj,  int pageNo, int pageSize);
	
	/**
	 * 检查job信息
	 * @param classFullName
	 * @param agent
	 * @return
	 */
	String checkJobInfo(SchedulerJob obj, AgentInfoVObj agent);
	
	/**
	 * 立即执行job
	 * @param obj
	 * @param agent
	 * @return
	 */
	String forceRunJob(SchedulerJob obj, AgentInfoVObj agent)throws Exception;
	public List<JobDataTime> queryJobDataTime(JobDataTime jobDataTime )throws Exception;
	public Integer insertJobDataTime(JobDataTime jobDataTime )throws Exception;
	public Integer deleteJobDataTime(JobDataTime jobDataTime )throws Exception;
	public List<JobDataTime> queryJobDataTimeDealTimes() throws Exception;
}
