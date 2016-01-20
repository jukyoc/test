package com.speed.autoreport.vo;

/**
 * @author liuhl
 * 热线整体数据对象
 */
public class CallAllStatisticsObj {

	//业务线id(查询条件)
	private String bussinessId;

	//开始时间(查询条件)
	private String beginTime;

	//结束时间(查询条件)
	private String endTime;

	//业务组(业务线名称)
	private String bussinessName;

	//服务总请求量 (系统接入量)
	private String sysAllCallInCount;

	//IVR自助服务数量
	private String autoQueryCount;

	//IVR放弃数量
	private String loseCount;

	//IVR自助服务占比   = IVR自助服务数量/服务总请求量
	private String autoServicePercent;

	//人工请求量
	private String callInCount;

	//实际人工接起数量
	private String connectCount;

	//20秒内接起数量
	private String connectCountIn20s;

	//人工队列放弃数量
	private String unConnectCount;

	//20秒内放弃数量
	private String unConnectCountIn20s;

	//20秒服务水平(公式=20秒内接起数量/实际人工接起数量)
	private String srvsPcntIn20s;

	//平均通话时长(公式=人工接起总通话时长/人工坐席接起总量)
	private String avgConnectTime;

	//通话排队最长时长
	private String maxWaitTime;

	//通话排队等待平均时长(公式=用户等待总时长/用户等待总数)
	private String avgWaitTime;

	//呼损排队最长时长
	private String maxWaitTimeOfLose;

	//呼损排队等待平均时长
	private String avgWaitTimeOfLose;

	//转接量
	private String transCount;

	//外呼量
	private String callOutCount;

	//重复来电量
	private String reCallinCount;

	//评价量
	private String appraiseCount;

	//满意评价量
	private String goodCount;

	//未评价
	private String unAppraiseCount;

	//对服务态度不满意
	private String badServiceCount;

	//对解决方案不满意
	private String badPlanCount;

	//双不满意
	private String badCount;

	//一般
	private String generalCount;

	//评价率
	private String appraisePercent;

	//满意率
	private String goodPercent;

	//差评率 (公式=评价量/实际人工接起数量)
	private String badAppraisePercent;
	//队列id
	private String queueId;
	//转出量
	private String transOutCount;
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

	public String getSysAllCallInCount() {
		return sysAllCallInCount;
	}

	public void setSysAllCallInCount(String sysAllCallInCount) {
		this.sysAllCallInCount = sysAllCallInCount;
	}

	public String getAutoQueryCount() {
		return autoQueryCount;
	}

	public void setAutoQueryCount(String autoQueryCount) {
		this.autoQueryCount = autoQueryCount;
	}

	public String getLoseCount() {
		return loseCount;
	}

	public void setLoseCount(String loseCount) {
		this.loseCount = loseCount;
	}

	public String getAutoServicePercent() {
		return autoServicePercent;
	}

	public void setAutoServicePercent(String autoServicePercent) {
		this.autoServicePercent = autoServicePercent;
	}

	public String getCallInCount() {
		return callInCount;
	}

	public void setCallInCount(String callInCount) {
		this.callInCount = callInCount;
	}

	public String getConnectCount() {
		return connectCount;
	}

	public void setConnectCount(String connectCount) {
		this.connectCount = connectCount;
	}

	public String getConnectCountIn20s() {
		return connectCountIn20s;
	}

	public void setConnectCountIn20s(String connectCountIn20s) {
		this.connectCountIn20s = connectCountIn20s;
	}

	public String getUnConnectCount() {
		return unConnectCount;
	}

	public void setUnConnectCount(String unConnectCount) {
		this.unConnectCount = unConnectCount;
	}

	public String getUnConnectCountIn20s() {
		return unConnectCountIn20s;
	}

	public void setUnConnectCountIn20s(String unConnectCountIn20s) {
		this.unConnectCountIn20s = unConnectCountIn20s;
	}

	public String getSrvsPcntIn20s() {
		return srvsPcntIn20s;
	}

	public void setSrvsPcntIn20s(String srvsPcntIn20s) {
		this.srvsPcntIn20s = srvsPcntIn20s;
	}

	public String getAvgConnectTime() {
		return avgConnectTime;
	}

	public void setAvgConnectTime(String avgConnectTime) {
		this.avgConnectTime = avgConnectTime;
	}

	public String getMaxWaitTime() {
		return maxWaitTime;
	}

	public void setMaxWaitTime(String maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}

	public String getAvgWaitTime() {
		return avgWaitTime;
	}

	public void setAvgWaitTime(String avgWaitTime) {
		this.avgWaitTime = avgWaitTime;
	}

	public String getMaxWaitTimeOfLose() {
		return maxWaitTimeOfLose;
	}

	public void setMaxWaitTimeOfLose(String maxWaitTimeOfLose) {
		this.maxWaitTimeOfLose = maxWaitTimeOfLose;
	}

	public String getAvgWaitTimeOfLose() {
		return avgWaitTimeOfLose;
	}

	public void setAvgWaitTimeOfLose(String avgWaitTimeOfLose) {
		this.avgWaitTimeOfLose = avgWaitTimeOfLose;
	}

	public String getTransCount() {
		return transCount;
	}

	public void setTransCount(String transCount) {
		this.transCount = transCount;
	}

	public String getCallOutCount() {
		return callOutCount;
	}

	public void setCallOutCount(String callOutCount) {
		this.callOutCount = callOutCount;
	}

	public String getReCallinCount() {
		return reCallinCount;
	}

	public void setReCallinCount(String reCallinCount) {
		this.reCallinCount = reCallinCount;
	}

	public String getAppraiseCount() {
		return appraiseCount;
	}

	public void setAppraiseCount(String appraiseCount) {
		this.appraiseCount = appraiseCount;
	}

	public String getUnAppraiseCount() {
		return unAppraiseCount;
	}

	public void setUnAppraiseCount(String unAppraiseCount) {
		this.unAppraiseCount = unAppraiseCount;
	}

	public String getBadServiceCount() {
		return badServiceCount;
	}

	public void setBadServiceCount(String badServiceCount) {
		this.badServiceCount = badServiceCount;
	}

	public String getBadPlanCount() {
		return badPlanCount;
	}

	public void setBadPlanCount(String badPlanCount) {
		this.badPlanCount = badPlanCount;
	}

	public String getBadCount() {
		return badCount;
	}

	public void setBadCount(String badCount) {
		this.badCount = badCount;
	}

	public String getGeneralCount() {
		return generalCount;
	}

	public void setGeneralCount(String generalCount) {
		this.generalCount = generalCount;
	}

	public String getAppraisePercent() {
		return appraisePercent;
	}

	public void setAppraisePercent(String appraisePercent) {
		this.appraisePercent = appraisePercent;
	}

	public String getGoodPercent() {
		return goodPercent;
	}

	public void setGoodPercent(String goodPercent) {
		this.goodPercent = goodPercent;
	}

	public String getBadAppraisePercent() {
		return badAppraisePercent;
	}

	public void setBadAppraisePercent(String badAppraisePercent) {
		this.badAppraisePercent = badAppraisePercent;
	}

	public String getGoodCount() {
		return goodCount;
	}

	public void setGoodCount(String goodCount) {
		this.goodCount = goodCount;
	}

	public String getQueueId() {
		return queueId;
	}

	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}

	public String getTransOutCount() {
		return transOutCount;
	}

	public void setTransOutCount(String transOutCount) {
		this.transOutCount = transOutCount;
	}


}
