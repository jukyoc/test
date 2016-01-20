package com.speed.autoreport.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 业务线信息
 * @author yangbb
 *
 */
public class BusinessInfo implements Serializable{

	/**
	 * 业务线ID
	 */
	private String businessId;
	/**
	 * 业务线名称
	 */
	private String businessName;
	/**
	 * 状态  0：可用 1：不可用
	 */
	private String businessStatus = "0";
	
	/**
	 * case source id
	 */
	private String caseSourceId;
	
	/**
	 * caseSourceStr
	 */
	private String caseSourceStr;
	
	
	/**
	 * queue id
	 */
	private String queueId;
	/**
	 * queueStr
	 */
	private String queueStr;
	/**
	 * operate
	 */
	private String operate;
	
	/**
	 * getter
	 * @return businessId
	 */
	public String getBusinessId() {
		return businessId;
	}
	/**
	 * setter
	 * @param businessId businessId
	 */
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	/**
	 * getter
	 * @return  businessName
	 */
	public String getBusinessName() {
		return businessName;
	}
	/**
	 * setter
	 * @param businessName businessName
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	/**
	 * getter
	 * @return  buesinessStatus
	 */
	public String getBusinessStatus() {
		return businessStatus;
	}
	/**
	 * setter
	 * @param businessStatus businessStatus
	 */
	public void setBusinessStatus(String businessStatus) {
		this.businessStatus = businessStatus;
	}
	/**
	 * getter
	 * @return caseSourceId
	 */
	public String getCaseSourceId() {
		return caseSourceId;
	}
	/**
	 * setter
	 * @param caseSourceId caseSourceId
	 */
	public void setCaseSourceId(String caseSourceId) {
		this.caseSourceId = caseSourceId;
	}
	/**
	 * getter
	 * @return queueId
	 */
	public String getQueueId() {
		return queueId;
	}
	/**
	 * setter
	 * @param queueId queueId
	 */
	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}
	/**
	 * getter
	 * @return  operate
	 */
	public String getOperate() {
		return operate;
	}
	/**
	 * setter
	 * @param operate operate
	 */
	public void setOperate(String operate) {
		this.operate = operate;
	}
	/**
	 * getter
	 * @return  caseSourceStr
	 */
	public String getCaseSourceStr() {
		return caseSourceStr;
	}
	/**
	 * setter
	 * @param caseSourceStr caseSourceStr
	 */
	public void setCaseSourceStr(String caseSourceStr) {
		this.caseSourceStr = caseSourceStr;
	}
	/**
	 * getter
	 * @return  queueStr
	 */
	public String getQueueStr() {
		return queueStr;
	}
	/**
	 * setter
	 * @param queueStr queueStr
	 */
	public void setQueueStr(String queueStr) {
		this.queueStr = queueStr;
	}
	
	
}
