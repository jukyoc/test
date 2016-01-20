package com.speed.cscenter.css.statistics.util.poi.xls.xls;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

import com.speed.cscenter.css.statistics.util.poi.xls.common.css.XlsStyle;
import com.speed.cscenter.css.statistics.util.poi.xls.common.util.NumStr;
import com.speed.cscenter.css.statistics.util.poi.xls.common.vo.VO;
import com.speed.cscenter.css.statistics.util.poi.xls.common.vo.XlsFieldValue;
import com.speed.cscenter.css.statistics.util.poi.xls.common.vo.XlsTitle;
public class XlsCommon {
	// public static void main(String[] args) {
	// XlsCommon jc = new XlsCommon();
	// String path = "C:/Users/K/Desktop/testxlsx.xlsx";
	// List<VO> vos = jc.listVO();
	// List<Title> titles = jc.listTitle();
	// List<FieldValue> fvs = jc.listFieldValue();
	// jc.test(path, vos, titles, fvs);
	// }
	public void test(String path, List datas, List<XlsTitle> titles, List<XlsFieldValue> fvs) {
		try {
			HSSFWorkbook wk = new HSSFWorkbook();
			FileOutputStream out = new FileOutputStream(path);
			HSSFSheet sheet = wk.createSheet();
			CellStyle titleCellStyle = XlsStyle.xlsStyleTitle(wk);
			CellStyle DataCellStyle = XlsStyle.xlsStyleData(wk);
			for (XlsTitle title : titles) {
				title.setCellStyle(titleCellStyle);
			}
			for (XlsFieldValue fv : fvs) {
				fv.setCellStyle(DataCellStyle);
			}
			xlsSheet(sheet, datas, titles, fvs);
			wk.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void xlsSheet(HSSFSheet sheet, List datas, List<XlsTitle> titles, List<XlsFieldValue> fvs) {
		if (datas == null) {
			datas = new ArrayList();
		}
		try {
			int row = 0;
			int col = 0;
			int titleRowNum = row;
			int fvRowNum = titleRowNum + 1;
			HSSFRow titleRow = sheet.createRow(titleRowNum);
			HSSFCell cell;
			XlsTitle title;
			for (int i = 0; i < titles.size(); i++) {
				title = titles.get(i);
				cell = titleRow.createCell(i + col);
				cell.setCellValue(title.getTitle());
				if (title.getWidth() != null) {
					sheet.setColumnWidth(cell.getColumnIndex(), title.getWidth());
				}
				if (title.getCellStyle() != null) {
					cell.setCellStyle(title.getCellStyle());
				}
			}
			Object obj;
			HSSFRow fvRow;
			for (int i = 0; i < datas.size(); i++) {
				fvRow = sheet.createRow(fvRowNum + i);
				obj = datas.get(i);
				if (fvs == null) {
					for (int j = 0; j < titles.size(); j++) {
						cell = fvRow.createCell(j + col);
						putStyle(cell, titles.get(j).getFieldValue());
						putVal(cell, obj, titles.get(j).getFieldValue());
					}
				} else {
					for (int j = 0; j < fvs.size(); j++) {
						cell = fvRow.createCell(j + col);
						putStyle(cell, fvs.get(j));
						putVal(cell, obj, fvs.get(j));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void xlsSheet(HSSFSheet sheet, List datas, List<XlsTitle> titles) {
		xlsSheet(sheet, datas, titles, null);
	}
	private static void putStyle(HSSFCell cell, XlsFieldValue fv) throws Exception {
		if (fv.getCellStyle() != null) {
			cell.setCellStyle(fv.getCellStyle());
		}
	}
	private static void putVal(HSSFCell cell, Object obj, XlsFieldValue fv) throws Exception {
		Object o = PropertyUtils.getProperty(obj, fv.getFildName());
		Field field = obj.getClass().getDeclaredField(fv.getFildName());
		if (o == null) {
			cell.setCellValue("");
			return;
		}
		if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
			fv.setNs(NumStr.intEnum);
		} else if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
			fv.setNs(NumStr.longEnum);
		} else if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
			fv.setNs(NumStr.doubleEnum);
		} else if (field.getType().equals(String.class)) {
			fv.setNs(NumStr.stringEnum);
		}
		switch (fv.getNs()) {
			case intEnum :
				cell.setCellValue((Integer) o);
				break;
			case longEnum :
				cell.setCellValue((Long) o);
				break;
			case doubleEnum :
				cell.setCellValue((Double) o);
				break;
			case stringEnum :
				cell.setCellValue((String) o);
				break;
		}
	}
	public List<VO> listVO() {
		List<VO> list = new ArrayList<VO>();
		for (int i = 0; i < 10; i++) {
			VO vo = new VO();
			vo.setId(i);
			vo.setName("name" + i);
			vo.setSex(Long.parseLong(i + ""));
			list.add(vo);
		}
		return list;
	}
	public List<XlsTitle> listTitle() {
		List<XlsTitle> list = new ArrayList<XlsTitle>();
		for (int i = 0; i < 10; i++) {
			XlsTitle vo = new XlsTitle();
			vo.setTitle("title" + i);
			list.add(vo);
		}
		return list;
	}
	public List<XlsFieldValue> listFieldValue() {
		List<XlsFieldValue> list = new ArrayList<XlsFieldValue>();
		XlsFieldValue voId = new XlsFieldValue();
		voId.setFildName("id");
		XlsFieldValue voName = new XlsFieldValue();
		voName.setFildName("name");
		XlsFieldValue voSex = new XlsFieldValue();
		voSex.setFildName("sex");
		XlsFieldValue voMoney = new XlsFieldValue();
		voMoney.setFildName("money");
		list.add(voId);
		list.add(voName);
		list.add(voSex);
		list.add(voMoney);
		return list;
	}
}
