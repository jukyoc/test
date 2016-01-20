package com.speed.hfreport.vo;

/**
 * @author wangjian
 *
 */
public class HfReportVo {

	private String businessName;//业务线名称
	private String type;//报表类型（综合报表和个人报表）
	private String date;//日报表或月报表
	private String year;
	private String month;
	private String name;//Excel文件名称
	private int index;
	
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getYear() {
		return year;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getMonth() {
		return month;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

}
