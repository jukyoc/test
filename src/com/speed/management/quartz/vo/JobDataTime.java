package com.speed.management.quartz.vo;

import com.speed.base.vo.ParamObj;

public class JobDataTime extends ParamObj {
	private String jobName;
	private String jobStartTime;
	private String jobEndTime;
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobStartTime() {
		return jobStartTime;
	}
	public void setJobStartTime(String jobStartTime) {
		this.jobStartTime = jobStartTime;
	}
	public String getJobEndTime() {
		return jobEndTime;
	}
	public void setJobEndTime(String jobEndTime) {
		this.jobEndTime = jobEndTime;
	}
}
