package com.speed.base.vo;

import java.io.Serializable;

import com.speed.base.vo.ParamObj;

public class Entry extends ParamObj implements Serializable {
	private String text;
	private String value;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
