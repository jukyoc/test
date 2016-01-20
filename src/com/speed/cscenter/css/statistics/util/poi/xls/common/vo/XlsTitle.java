package com.speed.cscenter.css.statistics.util.poi.xls.common.vo;
import org.apache.poi.ss.usermodel.CellStyle;
public class XlsTitle {
	private String title;
	private XlsFieldValue fieldValue;
	private CellStyle cellStyle;
	private Integer width;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public XlsFieldValue getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(XlsFieldValue fieldValue) {
		this.fieldValue = fieldValue;
	}
	public CellStyle getCellStyle() {
		return cellStyle;
	}
	public void setCellStyle(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public XlsTitle(String title) {
		super();
		this.title = title;
	}
	public XlsTitle(String title, XlsFieldValue fieldValue) {
		super();
		this.title = title;
		this.fieldValue = fieldValue;
	}
	public XlsTitle(String title, XlsFieldValue fieldValue, CellStyle cellStyle) {
		super();
		this.title = title;
		this.fieldValue = fieldValue;
		this.cellStyle = cellStyle;
	}
	public XlsTitle(String title, XlsFieldValue fieldValue, CellStyle cellStyle, Integer width) {
		super();
		this.title = title;
		this.fieldValue = fieldValue;
		this.cellStyle = cellStyle;
		this.width = width;
	}
	public XlsTitle() {
		super();
	}
	public XlsTitle(String title, CellStyle cellStyle) {
		super();
		this.title = title;
		this.cellStyle = cellStyle;
	}
}
