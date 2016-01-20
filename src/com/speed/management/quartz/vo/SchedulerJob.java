package com.speed.management.quartz.vo;

import java.util.Map;

import com.speed.base.vo.ParamObj;

public class SchedulerJob extends ParamObj {
	
	private String jobName;//任务名称
	private String jobDesc;//任务描述
	private String nextExecDate;//下次执行时间
	private String lastExecDate;//上次执行时间
	private String jobStatus;//任务状态
	private String jobGroup;//任务组
	private String jobClassFullName;//任务执行类全名称
	
	private String triggerName;//触发器名称
	private String triggerGroup;//触发器组
	private String triggerType;//触发器类型 0 simple  1 cron
	private String cronExpression;
	private String repeatInterval;
	
	private String remark;
	private String nextExecDateBegin;
	private String nextExecDateEnd;
	private String params;
	
	public String getRepeatInterval() {
		return repeatInterval;
	}
	public void setRepeatInterval(String repeatInterval) {
		this.repeatInterval = repeatInterval;
	}
	public String getTriggerName() {
		return triggerName;
	}
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	public String getTriggerGroup() {
		return triggerGroup;
	}
	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}
	public String getJobClassFullName() {
		return jobClassFullName;
	}
	public void setJobClassFullName(String jobClassFullName) {
		this.jobClassFullName = jobClassFullName;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getJobDesc() {
		return jobDesc;
	}
	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}
	public String getNextExecDate() {
		return nextExecDate;
	}
	public void setNextExecDate(String nextExecDate) {
		this.nextExecDate = nextExecDate;
	}
	public String getLastExecDate() {
		return lastExecDate;
	}
	public void setLastExecDate(String lastExecDate) {
		this.lastExecDate = lastExecDate;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getTriggerType() {
		return triggerType;
	}
	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public final String getRemark() {
		return remark;
	}
	public final void setRemark(String remark) {
		this.remark = remark;
	}
	public final String getNextExecDateBegin() {
		return nextExecDateBegin;
	}
	public final void setNextExecDateBegin(String nextExecDateBegin) {
		this.nextExecDateBegin = nextExecDateBegin;
	}
	public final String getNextExecDateEnd() {
		return nextExecDateEnd;
	}
	public final void setNextExecDateEnd(String nextExecDateEnd) {
		this.nextExecDateEnd = nextExecDateEnd;
	}
	public final String getParams() {
		return params;
	}
	public final void setParams(String params) {
		this.params = params;
	}
	
}
