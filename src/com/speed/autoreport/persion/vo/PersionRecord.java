package com.speed.autoreport.persion.vo;

import com.speed.base.vo.ParamObj;

public class PersionRecord extends ParamObj{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7429705592099351581L;
	
	/**
	 * 客服id 姓名
	 */
	private String csId,csName;
	/**
	 * 业务线id 名称
	 */
	private String businessId,businessName;
	/**
	 * 开始时间，结束时间
	 */
	private String startTime,endTime;
	/**
	 * 接起量
	 */
	private long connectCount;
	/**
	 * 转接量
	 */
	private long transCount;
	/**
	 * 漏接量
	 */
	private long looseCount;
	/**
	 * 外呼量
	 */
	private long outBoundCallNum;
	/**
	 * 平均通话时长
	 */
	private long avgConnectTime;
	/**
	 * 工作时长
	 */
	private long workTime;
	/**
	 * 签出次数
	 */
	private int outTime;
	
	/**
	 * 小休次数
	 */
	private int restCount;
	/**
	 * 小休时长
	 */
	private long restTime;
	/**
	 * 保存时长
	 */
	private long keepTime;
	/**
	 * 评价量
	 */
	private long satisfacingCount;
	/**
	 * 未评价量
	 */
	private long unSatisfacingCount;
	/**
	 * 满意量
	 */
	private long goodCount;
	/**
	 * 不满意量（对服务，对方案，全部）
	 */
	private long badServiceCount,badPlanCount,badCount;
	/**
	 * 一般评价量
	 */
	private long generalCount;
	/**
	 * 评价率
	 */
	private String saticfacingPercent;
	/**
	 * 满意度
	 */
	private String goodPercent;
	/**
	 * 差评率
	 */
	private String badPercent;

    /**
     * 来电分配量
     */
    private String callAlloction;

    /**
     * 转出量
     */
    private String transOutCount;

    /**
     * 20s接起量
     */
    private String connectCountIn20Sec;

	public String getCsId() {
		return csId;
	}
	public void setCsId(String csId) {
		this.csId = csId;
	}
	public String getCsName() {
		return csName;
	}
	public void setCsName(String csName) {
		this.csName = csName;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public long getConnectCount() {
		return connectCount;
	}
	public void setConnectCount(long connectCount) {
		this.connectCount = connectCount;
	}
	public long getTransCount() {
		return transCount;
	}
	public void setTransCount(long transCount) {
		this.transCount = transCount;
	}
	public long getLooseCount() {
		return looseCount;
	}
	public void setLooseCount(long looseCount) {
		this.looseCount = looseCount;
	}
	public long getOutBoundCallNum() {
		return outBoundCallNum;
	}
	public void setOutBoundCallNum(long outBoundCallNum) {
		this.outBoundCallNum = outBoundCallNum;
	}
	public long getAvgConnectTime() {
		return avgConnectTime;
	}
	public void setAvgConnectTime(long avgConnectTime) {
		this.avgConnectTime = avgConnectTime;
	}
	public long getWorkTime() {
		return workTime;
	}
	public void setWorkTime(long workTime) {
		this.workTime = workTime;
	}
	public int getOutTime() {
		return outTime;
	}
	public void setOutTime(int outTime) {
		this.outTime = outTime;
	}
	public int getRestCount() {
		return restCount;
	}
	public void setRestCount(int restCount) {
		this.restCount = restCount;
	}
	public long getRestTime() {
		return restTime;
	}
	public void setRestTime(long restTime) {
		this.restTime = restTime;
	}
	public long getKeepTime() {
		return keepTime;
	}
	public void setKeepTime(long keepTime) {
		this.keepTime = keepTime;
	}
	public long getSatisfacingCount() {
		return satisfacingCount;
	}
	public void setSatisfacingCount(long satisfacingCount) {
		this.satisfacingCount = satisfacingCount;
	}
	public long getUnSatisfacingCount() {
		return unSatisfacingCount;
	}
	public void setUnSatisfacingCount(long unSatisfacingCount) {
		this.unSatisfacingCount = unSatisfacingCount;
	}
	public long getBadServiceCount() {
		return badServiceCount;
	}
	public void setBadServiceCount(long badServiceCount) {
		this.badServiceCount = badServiceCount;
	}
	public long getBadPlanCount() {
		return badPlanCount;
	}
	public void setBadPlanCount(long badPlanCount) {
		this.badPlanCount = badPlanCount;
	}
	public long getBadCount() {
		return badCount;
	}
	public void setBadCount(long badCount) {
		this.badCount = badCount;
	}
	public long getGeneralCount() {
		return generalCount;
	}
	public void setGeneralCount(long generalCount) {
		this.generalCount = generalCount;
	}
	public String getSaticfacingPercent() {
		return saticfacingPercent;
	}
	public void setSaticfacingPercent(String saticfacingPercent) {
		this.saticfacingPercent = saticfacingPercent;
	}
	public String getGoodPercent() {
		return goodPercent;
	}
	public void setGoodPercent(String goodPercent) {
		this.goodPercent = goodPercent;
	}
	public String getBadPercent() {
		return badPercent;
	}
	public void setBadPercent(String badPercent) {
		this.badPercent = badPercent;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public long getGoodCount() {
		return goodCount;
	}
	public void setGoodCount(long goodCount) {
		this.goodCount = goodCount;
	}

    public String getCallAlloction() {
        return callAlloction;
    }

    public void setCallAlloction(String callAlloction) {
        this.callAlloction = callAlloction;
    }

    public String getTransOutCount() {
        return transOutCount;
    }

    public void setTransOutCount(String transOutCount) {
        this.transOutCount = transOutCount;
    }

    public String getConnectCountIn20Sec() {
        return connectCountIn20Sec;
    }

    public void setConnectCountIn20Sec(String connectCountIn20Sec) {
        this.connectCountIn20Sec = connectCountIn20Sec;
    }
}
