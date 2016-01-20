package com.speed.management.quartz.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.speed.base.dao.BaseDao;
import com.speed.base.vo.PageBean;
import com.speed.base.vo.ParamObj;
import com.speed.management.quartz.vo.JobDataTime;
import com.speed.management.quartz.vo.JobInfo;
import com.speed.management.quartz.vo.JobRunLog;
import com.speed.management.quartz.vo.OperateLog;
import com.speed.management.quartz.vo.SchedulerJob;

@Component("schedulerManageDao")
public class SchedulerManageDao {

	public static final String QUARTZ = "quartz_task";
	public static final String CSCENTER = "report";
	
	@Resource(name="baseDao")
	private BaseDao baseDao;

	/**
	 * 分页查询定时任务
	 * @param obj
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageBean queryJobForPage(SchedulerJob obj,int pageNo, int pageSize) throws Exception {
		if(obj==null)obj=new SchedulerJob();
		obj.setDs(QUARTZ);
		obj.setSqlId("querySchedulerJobForPage");
		obj.setCountSqlId("querySchedulerJobCount");
		obj.setCondition(obj);
        PageBean dataListForPage = baseDao.getDataListForPage(obj, pageNo, pageSize);
		return dataListForPage;
	}
	
	/**
	 * 保存任务操作日志
	 * @param log
	 * @return
	 */
	public int saveOperateLog(OperateLog log){
		ParamObj param = new ParamObj();
		param.setDs(QUARTZ);
		param.setSqlId("saveOperateLog");
        param.setCondition(log);
		return baseDao.insertObj(param);
	}
	
	/**
	 * 分页查询任务操作日志
	 * @param log
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBean queryJobLogForPage(OperateLog log, int pageNo, int pageSize) {
		log.setDs(QUARTZ);
		log.setSqlId("queryJobLogForPage");
		log.setCountSqlId("queryJobLogCount");
		log.setCondition(log);
        PageBean dataListForPage = baseDao.getDataListForPage(log, pageNo, pageSize);
		return dataListForPage;
	}
	
	/**
	 * 分页查询任务信息
	 * @param jobInfo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBean queryJobInfoForPage(JobInfo jobInfo,int pageNo, int pageSize) {
		jobInfo.setDs(QUARTZ);
		jobInfo.setSqlId("queryJobInfoForPage");
		jobInfo.setCountSqlId("queryJobInfoCount");
		jobInfo.setCondition(jobInfo);
        PageBean dataListForPage = baseDao.getDataListForPage(jobInfo, pageNo, pageSize);
		return dataListForPage;
	}
	/**
	 * 保存任务中间表信息
	 * @param jobInfo
	 * @return
	 */
	public int saveJobInfo(JobInfo jobInfo) {
		jobInfo.setDs(QUARTZ);
		jobInfo.setSqlId("saveJobInfo");
		jobInfo.setCondition(jobInfo);
		return baseDao.insertObj(jobInfo);
	}

	/**
	 * 保存任务执行日志
	 * @param log
	 * @return
	 */
	public int saveJobExecLog(JobRunLog log) {
		ParamObj param = new ParamObj();
		param.setDs(QUARTZ);
		param.setSqlId("saveJobExecLog");
        param.setCondition(log);
		return baseDao.insertObj(param);
	}

	public PageBean queryJobRunningLogForPage(JobRunLog log, int pageNo,
			int pageSize) {
		log.setDs(QUARTZ);
		log.setSqlId("queryJobRunningLogForPage");
		log.setCountSqlId("queryJobRunningLogCount");
		log.setCondition(log);
		PageBean dataListForPage = baseDao.getDataListForPage(log, pageNo, pageSize);
		return dataListForPage;
	}
	public List<JobDataTime> queryJobDataTime(JobDataTime jobDataTime ) {
		jobDataTime.setDs(QUARTZ);
		jobDataTime.setSqlId("queryJobDataTime");
		jobDataTime.setCondition(jobDataTime);
		return baseDao.getDataList(jobDataTime);
	}
	public Integer insertJobDataTime(JobDataTime jobDataTime ) {
		jobDataTime.setDs(QUARTZ);
		jobDataTime.setSqlId("insertIobDataTime");
		jobDataTime.setCondition(jobDataTime);
		return baseDao.insertObj(jobDataTime);
	}
	public Integer deleteJobDataTime(JobDataTime jobDataTime ) {
		jobDataTime.setDs(QUARTZ);
		jobDataTime.setSqlId("deleteIobDataTime");
		jobDataTime.setCondition(jobDataTime);
		return baseDao.insertObj(jobDataTime);
	}
	public List<JobDataTime> queryJobDataTimeDealTimes() {
		JobDataTime jobDataTime=new JobDataTime();
		jobDataTime.setDs(QUARTZ);
		jobDataTime.setSqlId("queryJobDataTimeDealTimes");
		return baseDao.getDataList(jobDataTime);
	}
}
