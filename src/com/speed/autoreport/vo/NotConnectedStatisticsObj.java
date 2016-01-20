package com.speed.autoreport.vo;

/**
 * @author liuhl
 * 热线整体数据对象
 */
public class NotConnectedStatisticsObj {
	
	//业务线id(查询条件)
	private String bussinessId;
	
	//开始时间(查询条件)
	private String beginTime;
		
	//结束时间(查询条件)
	private String endTime;	
	
	//业务组(业务线名称) 
	private String bussinessName;
	
	//来电号码
	private String telPhone;
	
	//来电时间
	private String telTime;
	
	private String sampleId;
	
	private String sessionId;
	
	private String traceId;
	
	private String agentId;
	private String sampleTime;
	private String queueTime;//排队用户挂断的，显示‘排队时长’
	private String queueId;
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	//来电类型 
	private String telType = "排队用户挂断";

	public String getSampleId() {
		return sampleId;
	}

	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}

	public String getBussinessId() {
		return bussinessId;
	}

	public void setBussinessId(String bussinessId) {
		this.bussinessId = bussinessId;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getBussinessName() {
		return bussinessName;
	}

	public void setBussinessName(String bussinessName) {
		this.bussinessName = bussinessName;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getTelTime() {
		return telTime;
	}

	public void setTelTime(String telTime) {
		this.telTime = telTime;
	}

	public String getTelType() {
		return telType;
	}

	public void setTelType(String telType) {
		this.telType = telType;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getSampleTime() {
		return sampleTime;
	}

	public void setSampleTime(String sampleTime) {
		this.sampleTime = sampleTime;
	}

	public String getQueueTime() {
		return queueTime;
	}

	public void setQueueTime(String queueTime) {
		this.queueTime = queueTime;
	}

	public String getQueueId() {
		return queueId;
	}

	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}
	
}
