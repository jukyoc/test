package com.speed.management.quartz.vo;

import com.speed.base.vo.ParamObj;

public class JobRunLog extends ParamObj {
	private static final long serialVersionUID = 1L;
	private Integer logId;
	private Integer jobId;
	private String execBeginTime;
	private String execEndTime;
	private String execStatus;
	private String errorMessage;
	public Integer getLogId() {
		return logId;
	}
	public void setLogId(Integer logId) {
		this.logId = logId;
	}
	public Integer getJobId() {
		return jobId;
	}
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	public String getExecBeginTime() {
		return execBeginTime;
	}
	public void setExecBeginTime(String execBeginTime) {
		this.execBeginTime = execBeginTime;
	}
	public String getExecEndTime() {
		return execEndTime;
	}
	public void setExecEndTime(String execEndTime) {
		this.execEndTime = execEndTime;
	}
	public String getExecStatus() {
		return execStatus;
	}
	public void setExecStatus(String execStatus) {
		this.execStatus = execStatus;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
