package com.speed.management.quartz.service;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Component;

import com.speed.autoreport.util.DateUtil;
import com.speed.base.vo.PageBean;
import com.speed.management.quartz.dao.SchedulerManageDao;
import com.speed.management.quartz.vo.JobDataTime;
import com.speed.management.quartz.vo.JobInfo;
import com.speed.management.quartz.vo.JobRunLog;
import com.speed.management.quartz.vo.OperateLog;
import com.speed.management.quartz.vo.SchedulerJob;
import com.speed.ocss.man.vo.AgentInfoVObj;

@Component("schedulerManageService")
public class SchedulerManageServiceImpl implements SchedulerManageService{
	
	private static final Log logger = LogFactory.getLog(SchedulerManageServiceImpl.class);
	
	@Resource(name="quartzScheduler")
	private Scheduler quartzScheduler;
	
	@Resource(name="schedulerManageDao")
	private SchedulerManageDao schedulerManageDao;
	
	@Override
	public String addJob(SchedulerJob obj,AgentInfoVObj agent) throws Exception{
		String result = "false";
		try {
			Class a = Class.forName(obj.getJobClassFullName());
			JobDetail jobDetail = JobBuilder.newJob(a)
					.withIdentity(obj.getJobName(),obj.getJobGroup())
					.withDescription(obj.getJobDesc())
					.build();
	
			Trigger trigger = null;
			if("0".equals(obj.getTriggerType())){
				trigger = TriggerBuilder.newTrigger()
						.withIdentity(obj.getTriggerName(), obj.getTriggerGroup())
						.withSchedule(simpleSchedule().withIntervalInSeconds(Integer.parseInt(obj.getRepeatInterval())).repeatForever())
						.build();
			}else if("1".equals(obj.getTriggerType())){
				trigger = TriggerBuilder.newTrigger()
						.withIdentity(obj.getTriggerName(), obj.getTriggerGroup())
						.withSchedule(cronSchedule(obj.getCronExpression()))
						.build();
			}
			jobDetail.getJobDataMap().put("JOB_NAME", obj.getJobName());
			jobDetail.getJobDataMap().put("JOB_GROUP", obj.getJobGroup());
			boolean isExist = quartzScheduler.checkExists(jobDetail.getKey());
			if(!isExist){
				//保存任务明细中间表
				JobInfo jobInfo = new JobInfo();
				jobInfo.setCreateAgentId(agent.getCsId());
				jobInfo.setCreateAgentName(agent.getCsName());
				jobInfo.setJobName(obj.getJobName());
				jobInfo.setJobGroup(obj.getJobGroup());
				schedulerManageDao.saveJobInfo(jobInfo);
				//保存操作日志
				saveOperateLog("create", agent, obj);
				
				quartzScheduler.scheduleJob(jobDetail, trigger);
				result = "true";
				logger.info("create new job success");
			}else{
				logger.info("create new job failed, job has exists");
			}
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		}
		return result;
	}
	
	@Override
	public String pauseJob(SchedulerJob obj,AgentInfoVObj agent) throws Exception{
		String result;
		JobKey jobKey = new JobKey(obj.getJobName(), obj.getJobGroup());
		TriggerKey triggerKey = new TriggerKey(obj.getTriggerName(), obj.getTriggerGroup());
		try {
			this.saveOperateLog("pause",agent,obj);
			quartzScheduler.pauseJob(jobKey);
			quartzScheduler.pauseTrigger(triggerKey);
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		}
		result = "true";
		return result;
	}

	@Override
	public PageBean queryJobForPage(SchedulerJob obj,int pageNo, int pageSize) throws Exception{
		PageBean pb = null;
		try {
			pb  = schedulerManageDao.queryJobForPage(obj,pageNo,pageSize);
		} catch (Exception e) {
			logger.error("", e);
			pb = null;
		}
		return pb;
	}

	@Override
	public String resumeJob(SchedulerJob obj,AgentInfoVObj agent) throws Exception{
		String result;
		JobKey jobKey = new JobKey(obj.getJobName(), obj.getJobGroup());
		TriggerKey triggerKey = new TriggerKey(obj.getTriggerName(), obj.getTriggerGroup());
		
		try {
			this.saveOperateLog("resume",agent,obj);
			quartzScheduler.resumeJob(jobKey);
			quartzScheduler.resumeTrigger(triggerKey);
		} catch (Exception e) {
			logger.error("", e);
			result = "false";
		}
		result = "true";
		return result;
	}
	private void saveOperateLog(String status,AgentInfoVObj agent,SchedulerJob obj){
		String logStatus = "";
		String remark = "";
		if(status.equalsIgnoreCase("resume")){
			logStatus = "2";
			//remark = "重新启用";
		}else if(status.equalsIgnoreCase("pause")){
			logStatus = "3";
			//remark = "暂停";
		}else if(status.equalsIgnoreCase("create")){
			logStatus = "1";
			//remark = "创建任务";
		}else if(status.equalsIgnoreCase("delete")){
			logStatus = "4";
			//remark = "删除任务";
		}else if(status.equalsIgnoreCase("forceRun")){
			logStatus = "5";
		}
		remark = obj.getRemark();
		OperateLog log = new OperateLog();
		log.setAgentId(agent.getCsId());
		log.setAgentName(agent.getCsName());
		log.setCreateTime(DateUtil.getCurrDateStr());
		log.setLogStatus(logStatus);
		log.setRemark(remark);
		
		JobInfo jobInfo = new JobInfo();
		jobInfo.setJobName(obj.getJobName());
		jobInfo.setJobGroup(obj.getJobGroup());
		
		PageBean temp = schedulerManageDao.queryJobInfoForPage(jobInfo,1,10);
		jobInfo = (JobInfo) temp.getDataList().get(0);
		
		log.setJobId(String.valueOf(jobInfo.getJobId()));
		
		schedulerManageDao.saveOperateLog(log);
	}

	@Override
	public PageBean queryJobLog(SchedulerJob obj, int pageNo, int pageSize) throws Exception {
		PageBean pb = null;
		try {
			JobInfo jobInfo = new JobInfo();
			jobInfo.setJobName(obj.getJobName());
			jobInfo.setJobGroup(obj.getJobGroup());
			PageBean temp = schedulerManageDao.queryJobInfoForPage(jobInfo,1,10);
			jobInfo = (JobInfo) temp.getDataList().get(0);
			
			
			OperateLog log = new OperateLog();
			log.setJobId(String.valueOf(jobInfo.getJobId()));
			pb  = schedulerManageDao.queryJobLogForPage(log,pageNo,pageSize);
		} catch (Exception e) {
			logger.error("", e);
			pb = null;
		}
		return pb;
	}

	@Override
	public void saveJobExecLog(JobRunLog log) throws Exception {
		
		int r = schedulerManageDao.saveJobExecLog(log);
		
	}

	@Override
	public PageBean queryJobInfoForPage(JobInfo jobInfo, int i, int j) throws Exception {
		return schedulerManageDao.queryJobInfoForPage(jobInfo,i,j);
	}

	@Override
	public PageBean queryJobRunningLog(SchedulerJob obj,  int pageNo, int pageSize) {
		PageBean pb = null;
		try {
			JobInfo jobInfo = new JobInfo();
			jobInfo.setJobName(obj.getJobName());
			jobInfo.setJobGroup(obj.getJobGroup());
			PageBean temp = schedulerManageDao.queryJobInfoForPage(jobInfo,1,10);
			jobInfo = (JobInfo) temp.getDataList().get(0);
			
			JobRunLog log = new JobRunLog();
			log.setJobId(jobInfo.getJobId());
			
			pb  = schedulerManageDao.queryJobRunningLogForPage(log,pageNo,pageSize);
		} catch (Exception e) {
			logger.error("", e);
			pb = null;
		}
		return pb;
	}

	@Override
	public String checkJobInfo(SchedulerJob obj, AgentInfoVObj agent) {
		JSONObject json = new JSONObject();
		String result = "";
		boolean status = false;
		String message = "";

		try {
			if(isClassExist(obj)){//检查class是否存在
				if(!isJobInfoExist(obj)){//检查job是否存在
					if(!isTriggerExist(obj)){//检查trigger是否存在
						status = true;
					}else message = "触发器名称已存在";
				}else message = "任务名已经存在";
			}else message = "指定class不存在";
				
			json.put("status", status);
			json.put("message", message);
		
			result = URLEncoder.encode(json.toString(),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private boolean isClassExist(SchedulerJob obj){
		try {
			Class a = Class.forName(obj.getJobClassFullName());
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
	private boolean isTriggerExist(SchedulerJob obj) throws SchedulerException{

		Trigger trigger = TriggerBuilder.newTrigger()
					.withIdentity(obj.getTriggerName(), obj.getTriggerGroup())
					.build();
		
		return quartzScheduler.checkExists(trigger.getKey());
	}
	
	private boolean isJobInfoExist(SchedulerJob obj) throws ClassNotFoundException, SchedulerException{
		Class a = Class.forName(obj.getJobClassFullName());
		JobDetail jobDetail = JobBuilder.newJob(a)
				.withIdentity(obj.getJobName(),obj.getJobGroup())
				.build();

		return quartzScheduler.checkExists(jobDetail.getKey());
	}

	@Override
	public String forceRunJob(SchedulerJob obj, AgentInfoVObj agent) throws Exception{
		String result;
		JobKey jobKey = new JobKey(obj.getJobName(), obj.getJobGroup());
		
		JobDataMap data = new JobDataMap();
		String str = obj.getParams();
		if(str!=null && !str.equals("")){
			JSONObject json = JSONObject.fromObject(str);
			Object[] keys = json.keySet().toArray();
			for(Object key : keys){
				data.put(String.valueOf(key), json.get(key));
			}
		}
		try {
			this.saveOperateLog("forceRun",agent,obj);
			quartzScheduler.triggerJob(jobKey, data);
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		}
		result = "true";
		return result;
	}
	public static void main(String[] args) {
		String str = "date:20";
		if(str!=null && !str.equals("")){
			JSONArray array = JSONArray.fromObject(str);
			
		}
	}

	@Override
	public List<JobDataTime> queryJobDataTime(JobDataTime jobDataTime) throws Exception {
		return schedulerManageDao.queryJobDataTime(jobDataTime);
	}

	@Override
	public Integer insertJobDataTime(JobDataTime jobDataTime) throws Exception {
		return schedulerManageDao.insertJobDataTime(jobDataTime);
	}

	@Override
	public Integer deleteJobDataTime(JobDataTime jobDataTime) throws Exception {
		return schedulerManageDao.deleteJobDataTime(jobDataTime);
	}

	@Override
	public List<JobDataTime> queryJobDataTimeDealTimes() throws Exception {
		return schedulerManageDao.queryJobDataTimeDealTimes();
	}
}
