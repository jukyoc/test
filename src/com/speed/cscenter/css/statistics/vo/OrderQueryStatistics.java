package com.speed.cscenter.css.statistics.vo;
import java.util.Date;
public class OrderQueryStatistics {
	// 订单ID
	private String caseId;
	// 来电号码
	private String callerNumber;
	// 订单号
	private String clientOrderNum;
	// 问题分类
	private String qcName;
	// 操作人
	private String csName;
	// 工单生成时间
	private Date subTime;
	private String subTimeFormat;
	// 客服处理时间
	private Date finishTime;
	private String finishTimeFormat;
	// 工单处理结果
	private Integer flowType;
	// 备注
	private String caseRemark;
	// //外呼时长
	// private Integer callout;
	// 工单处理时长
	private Integer orderDeal;
	// String类型工单处理时长格式为00:00:00
	private String orderDealFormat;
	// 客服处理时长
	private Integer optTime;
	// String类型客服处理时长格式00:00:00
	private String optTimeFormat;
	// 问题来源
	private Integer caseSourceid;
	// 问题分类
	private Integer qcId;
	// 组ID
	private Integer groupId;
	// 部门ID
	private Integer groupParentId;
	// 开始时间
	private String beginTime;
	// 结束时间
	private String endTime;
	// 队列类型
	private String queueType;
	// 来电号码/查询ID
	private String caseIndex;
	// 用户满意度
	private String userGrade;
	private String agentId;
	private Integer srvsPcnt;
	private String srvsPcntFormat;
	// 录音时间
	private Integer recordTime;
	private String recordTimeFormat;
	// 排队超过20s接起
	// 业务线id
	private String businessId;
	// 业务组
	private String businessName;
	// 来点时间
	private Date callerTime;
	private String callerTimeFormat;
	private String queueId;
	public String getCaseIndex() {
		return caseIndex;
	}
	public void setCaseIndex(String caseIndex) {
		this.caseIndex = caseIndex;
	}
	public String getQueueType() {
		return queueType;
	}
	public void setQueueType(String queueType) {
		this.queueType = queueType;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getCallerNumber() {
		return callerNumber;
	}
	public void setCallerNumber(String callerNumber) {
		this.callerNumber = callerNumber;
	}
	public String getClientOrderNum() {
		return clientOrderNum;
	}
	public void setClientOrderNum(String clientOrderNum) {
		this.clientOrderNum = clientOrderNum;
	}
	public String getQcName() {
		return qcName;
	}
	public void setQcName(String qcName) {
		this.qcName = qcName;
	}
	public String getCsName() {
		return csName;
	}
	public void setCsName(String csName) {
		this.csName = csName;
	}
	public Date getSubTime() {
		return subTime;
	}
	public void setSubTime(Date subTime) {
		this.subTime = subTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public Integer getFlowType() {
		return flowType;
	}
	public void setFlowType(Integer flowType) {
		this.flowType = flowType;
	}
	public String getCaseRemark() {
		return caseRemark;
	}
	public void setCaseRemark(String caseRemark) {
		this.caseRemark = caseRemark;
	}
	public Integer getOrderDeal() {
		return orderDeal;
	}
	public void setOrderDeal(Integer orderDeal) {
		this.orderDeal = orderDeal;
	}
	public Integer getOptTime() {
		return optTime;
	}
	public void setOptTime(Integer optTime) {
		this.optTime = optTime;
	}
	public Integer getCaseSourceid() {
		return caseSourceid;
	}
	public void setCaseSourceid(Integer caseSourceid) {
		this.caseSourceid = caseSourceid;
	}
	public Integer getQcId() {
		return qcId;
	}
	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Integer getGroupParentId() {
		return groupParentId;
	}
	public void setGroupParentId(Integer groupParentId) {
		this.groupParentId = groupParentId;
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
	public String getOrderDealFormat() {
		return orderDealFormat;
	}
	public void setOrderDealFormat(String orderDealFormat) {
		this.orderDealFormat = orderDealFormat;
	}
	public String getOptTimeFormat() {
		return optTimeFormat;
	}
	public void setOptTimeFormat(String optTimeFormat) {
		this.optTimeFormat = optTimeFormat;
	}
	public String getUserGrade() {
		return userGrade;
	}
	public void setUserGrade(String userGrade) {
		this.userGrade = userGrade;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public Integer getSrvsPcnt() {
		return srvsPcnt;
	}
	public void setSrvsPcnt(Integer srvsPcnt) {
		this.srvsPcnt = srvsPcnt;
	}
	public String getSrvsPcntFormat() {
		return srvsPcntFormat;
	}
	public void setSrvsPcntFormat(String srvsPcntFormat) {
		this.srvsPcntFormat = srvsPcntFormat;
	}
	public Integer getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Integer recordTime) {
		this.recordTime = recordTime;
	}
	public String getRecordTimeFormat() {
		return recordTimeFormat;
	}
	public void setRecordTimeFormat(String recordTimeFormat) {
		this.recordTimeFormat = recordTimeFormat;
	}
	public String getSubTimeFormat() {
		return subTimeFormat;
	}
	public void setSubTimeFormat(String subTimeFormat) {
		this.subTimeFormat = subTimeFormat;
	}
	public String getFinishTimeFormat() {
		return finishTimeFormat;
	}
	public void setFinishTimeFormat(String finishTimeFormat) {
		this.finishTimeFormat = finishTimeFormat;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public Date getCallerTime() {
		return callerTime;
	}
	public void setCallerTime(Date callerTime) {
		this.callerTime = callerTime;
	}
	public String getCallerTimeFormat() {
		return callerTimeFormat;
	}
	public void setCallerTimeFormat(String callerTimeFormat) {
		this.callerTimeFormat = callerTimeFormat;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getQueueId() {
		return queueId;
	}
	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}
}
