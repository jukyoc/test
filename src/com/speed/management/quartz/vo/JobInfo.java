package com.speed.management.quartz.vo;

import com.speed.base.vo.ParamObj;

public class JobInfo extends ParamObj {
	private Integer jobId;
	private String jobName;
	private String jobGroup;
	private String isDelete;
	private String createAgentId;
	private String createAgentName;
	private String createTime;
	
	public Integer getJobId() {
		return jobId;
	}
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
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
	public String getCreateAgentId() {
		return createAgentId;
	}
	public void setCreateAgentId(String createAgentId) {
		this.createAgentId = createAgentId;
	}
	public String getCreateAgentName() {
		return createAgentName;
	}
	public void setCreateAgentName(String createAgentName) {
		this.createAgentName = createAgentName;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
