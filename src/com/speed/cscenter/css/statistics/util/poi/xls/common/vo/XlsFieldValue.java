package com.speed.cscenter.css.statistics.util.poi.xls.common.vo;
import org.apache.poi.ss.usermodel.CellStyle;

import com.speed.cscenter.css.statistics.util.poi.xls.common.util.NumStr;
public class XlsFieldValue {
	private NumStr ns;
	private String fildName;
	private CellStyle cellStyle;
	public NumStr getNs() {
		return ns;
	}
	public void setNs(NumStr ns) {
		this.ns = ns;
	}
	public String getFildName() {
		return fildName;
	}
	public void setFildName(String fildName) {
		this.fildName = fildName;
	}
	public CellStyle getCellStyle() {
		return cellStyle;
	}
	public void setCellStyle(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}
	public XlsFieldValue(String fildName) {
		super();
		this.fildName = fildName;
	}
	public XlsFieldValue(String fildName, CellStyle cellStyle) {
		super();
		this.fildName = fildName;
		this.cellStyle = cellStyle;
	}
	public XlsFieldValue() {
		super();
	}
}
