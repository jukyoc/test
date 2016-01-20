/**
 * 
 */
package com.speed.autoreport.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFErrorConstants;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;

public class MSExcelUtil {
	private HSSFWorkbook workbook;//工作簿，相当于MSExcel工作簿的概念，一个workbook可以看做一个独立的Execel文件
	
	/**
	 * @说明 创建一个空Excel工作簿
	 */
	public MSExcelUtil(){
		workbook=new HSSFWorkbook();
	}
	
	/**
	 * @说明 由输入流，创建一个Excel工作簿
	 */
	public MSExcelUtil(InputStream is){
		try {
			workbook=new HSSFWorkbook(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean write(String filePath,String fileName) throws IOException{
		File f = new File(filePath);
		if(!f.exists()){
			f.mkdirs();
		}
		FileOutputStream stream = new FileOutputStream(new File(f.getAbsolutePath())+fileName);
		workbook.write(stream);
		return true;
	}
	/**
	 * @返回值 HSSFSheet，工作簿中的一个Sheet页，数据操作的具体对象。
	 */
	public HSSFSheet addSheet(){
		return workbook.createSheet();
	}
	
	/**
	 * @参数 index 将要插入Sheet的位置
	 * @返回值 HSSFSheet，工作簿中的一个Sheet页，数据操作的具体对象。
	 * @说明 在指定位置插入一个Sheet页。
	 */
	public HSSFSheet addSheet(int index){
		HSSFSheet sheet=workbook.createSheet();
		workbook.setSheetOrder(sheet.getSheetName(), index);
		return sheet;
	}
	
	/**
	 * @参数 sheetName 将要插入Sheet的标签名
	 * @返回值 HSSFSheet，工作簿中的一个Sheet页，数据操作的具体对象。
	 * @说明 插入指定名称的Sheet页。
	 */
	public HSSFSheet addSheet(String sheetName){
		return workbook.createSheet(sheetName);
	}
	
	/**
	 * @参数 index 将要插入Sheet的位置；sheetName 将要插入Sheet的标签名
	 * @返回值 HSSFSheet，工作簿中的一个Sheet页，数据操作的具体对象。
	 * @说明 在指定位置插入一个Sheet页，并指定名称。
	 */
	public HSSFSheet addSheetAt(int index,String sheetName){
		HSSFSheet sheet=workbook.createSheet(sheetName);
		workbook.setSheetOrder(sheetName, index);
		return sheet;
	}
	
	/**
	 * @参数 index 将要插入Sheet的位置
	 * @返回值 HSSFSheet，工作簿中的一个Sheet页，数据操作的具体对象。
	 * @说明 取得指定位置上的Sheet页。
	 */
	public HSSFSheet getSheet(int index){
		return workbook.getSheetAt(index);
	}
	
	/**
	 * @参数 name 将要插入Sheet的标签名
	 * @返回值 HSSFSheet，工作簿中的一个Sheet页，数据操作的具体对象。
	 * @说明 取得指定名称的Sheet页。
	 */
	public HSSFSheet getSheet(String name){
		return workbook.getSheet(name);
	}
	
	/**
	 * @参数 sheet 将要操作的Sheet页；
	 * 		row 行数（从0索引）
	 * 		column 列数（从0索引）
	 * @返回值 HSSFCellStyle，单元格的样式对象。
	 * @说明 在给定的Sheet中，取得指定单元格上的样式对象。
	 */
	public HSSFCellStyle getCellStyle(HSSFSheet sheet,int row,int column){
		HSSFRow r=sheet.getRow(row);
		if(r!=null)
		{
			HSSFCell c=r.getCell(column);
			if(c!=null)
			{
				return c.getCellStyle();
			}
		}
		return null;
	}
	
	/**
	 * @参数 row 行数（从0索引）
	 * 		column 列数（从0索引）
	 * @返回值 String，单元格的Excel地址（类似A1）。
	 * @说明 转化行列坐标为Excel的单元格地址。
	 */
	public String getCellAddress(int row,int column){
		return new CellRangeAddress(row,row,column,column).toString();
	}
	
	/**
	 * @参数 firstRow 开始行数（从0索引）
	 * 		lastRow 结束行数（从0索引）
	 * 		firstColumn 开始列数（从0索引）
	 * 		lastColumn 结束列数（从0索引）
	 * @返回值 String，单元格的Excel地址（类似A1:B2）。
	 * @说明 转化行列区域表示为Excel的单元格地址区域。
	 */
	public String getCellAddress(int firstRow,int lastRow,int firstColumn,int lastColumn){
		String tmp=new CellRangeAddress(firstRow,lastRow,firstColumn,lastColumn).toString();
		return tmp.substring(tmp.indexOf("[")+1, tmp.indexOf("]"));
	}
	
	/**
	 * @参数 sheet 将要操作的Sheet页
	 * 		row 行数（从0索引）
	 * 		column 列数（从0索引）
	 * 		value 值
	 * @说明 向给定Sheet页中指定的行列单元格写入字符串值。
	 */
	public void writeToSheet(HSSFSheet sheet,int row,int column,String value){
		HSSFRow r=sheet.getRow(row);
		if(r==null)
		{
			r=sheet.createRow(row);
		}
		HSSFCell cell=r.getCell(column);
		if(cell==null)
		{
			cell=r.createCell(column);
		}
		cell.setCellValue(value);
	}
	/**
	 * @参数 sheet 将要操作的Sheet页
	 * 		row 行数（从0索引）
	 * 		column 列数（从0索引）
	 * 		value 值
	 * @说明 向给定Sheet页中指定的行列单元格写入字符串值。
	 */
	public void writeToSheetForNum(HSSFSheet sheet,int row,int column,String value){
		HSSFRow r=sheet.getRow(row);
		if(r==null)
		{
			r=sheet.createRow(row);
		}
		HSSFCell cell=r.getCell(column);
		if(cell==null)
		{
			cell=r.createCell(column);
		}
		cell.setCellValue(Double.parseDouble(value));
	}
	
	/**
	 * @参数 sheet 将要操作的Sheet页
	 * 		row 行数（从0索引）
	 * 		column 列数（从0索引）
	 * 		value 值
	 * @说明 向给定Sheet页中指定的行列单元格写入字符串值。样式为黑体剧中
	 */
	public void writeToSheetWithStyle(HSSFSheet sheet,int row,int column,String value){
		HSSFRow r=sheet.getRow(row);
		if(r==null)
		{
			r=sheet.createRow(row);
		}
		HSSFCell cell=r.getCell(column);
		if(cell==null)
		{
			cell=r.createCell(column);
		}
		cell.setCellValue(value);
		
		HSSFCellStyle theaderStyle = workbook.createCellStyle();
		theaderStyle.setLocked(true);

		theaderStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		theaderStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		HSSFFont theaderFont = workbook.createFont();
		theaderFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		theaderStyle.setFont(theaderFont);
		
		cell.setCellStyle(theaderStyle);
	}
	
	/**
	 * @参数 sheet 将要操作的Sheet页
	 * 		row 行数（从0索引）
	 * 		column 列数（从0索引）
	 * 		value 值
	 * @说明 向给定Sheet页中指定的行列单元格写入日期值。
	 */
	public void writeToSheet(HSSFSheet sheet,int row,int column,Date value){
		HSSFRow r=sheet.getRow(row);
		if(r==null)
		{
			r=sheet.createRow(row);
		}
		HSSFCell cell=r.getCell(column);
		if(cell==null)
		{
			cell=r.createCell(column);
		}
		cell.setCellValue(value);
	}
	
	/**
	 * @参数 sheet 将要操作的Sheet页
	 * 		row 行数（从0索引）
	 * 		column 列数（从0索引）
	 * 		value 值
	 * @说明 向给定Sheet页中指定的行列单元格写入浮点数值。
	 */
	public void writeToSheet(HSSFSheet sheet,int row,int column,double value){
		HSSFRow r=sheet.getRow(row);
		if(r==null)
		{
			r=sheet.createRow(row);
		}
		HSSFCell cell=r.getCell(column);
		if(cell==null)
		{
			cell=r.createCell(column);
		}
		cell.setCellValue(value);
	}
	
	/**
	 * @参数 sheet 将要操作的Sheet页
	 * 		cellRange 区域单元
	 * 		value 值
	 * @说明 向给定Sheet页中指定的单元格区域写入字符串值。
	 */
	public void writeToSheet(HSSFSheet sheet,String cellRange,String value){
		CellRangeAddress cellAddress=CellRangeAddress.valueOf(cellRange);
		int startRow=cellAddress.getFirstRow();
		int startColumn=cellAddress.getFirstColumn();
		HSSFRow row=sheet.getRow(startRow);
		if(row==null)
		{
			row=sheet.createRow(startRow);
		}
		HSSFCell cell=row.getCell(startColumn);
		if(cell==null)
		{
			cell=row.createCell(startColumn);
		}
		cell.setCellValue(value);
		if(cellAddress.getNumberOfCells()>1)
		{
			sheet.addMergedRegion(cellAddress);
		}
	}
	
	/**
	 * @参数 sheet 将要操作的Sheet页
	 * 		cellRange 区域单元
	 * 		value 值
	 * @说明 向给定Sheet页中指定的单元格区域写入日期值。
	 */
	public void writeToSheet(HSSFSheet sheet,String cellRange,Date value){
		CellRangeAddress cellAddress=CellRangeAddress.valueOf(cellRange);
		int startRow=cellAddress.getFirstRow();
		int startColumn=cellAddress.getFirstColumn();
		HSSFRow row=sheet.getRow(startRow);
		if(row==null)
		{
			row=sheet.createRow(startRow);
		}
		HSSFCell cell=row.getCell(startColumn);
		if(cell==null)
		{
			cell=row.createCell(startColumn);
		}
		cell.setCellValue(value);
		if(cellAddress.getNumberOfCells()>1)
		{
			sheet.addMergedRegion(cellAddress);
		}
	}
	
	/**
	 * @参数 sheet 将要操作的Sheet页
	 * 		cellRange 区域单元
	 * 		value 值
	 * @说明 向给定Sheet页中指定的单元格区域写入浮点数值。
	 */
	public void writeToSheet(HSSFSheet sheet,String cellRange,double value){
		CellRangeAddress cellAddress=CellRangeAddress.valueOf(cellRange);
		int startRow=cellAddress.getFirstRow();
		int startColumn=cellAddress.getFirstColumn();
		HSSFRow row=sheet.getRow(startRow);
		if(row==null)
		{
			row=sheet.createRow(startRow);
		}
		HSSFCell cell=row.getCell(startColumn);
		if(cell==null)
		{
			cell=row.createCell(startColumn);
		}
		cell.setCellValue(value);
		if(cellAddress.getNumberOfCells()>1)
		{
			sheet.addMergedRegion(cellAddress);
		}
	}
	
	/**
	 * @返回值 HSSFCellStyle 可以应用到单元格上的单元格样式对象
	 */
	public HSSFCellStyle createCellStyle(){
		return workbook.createCellStyle();
	}
	
	public short createDataFormat(String expr){
		return workbook.createDataFormat().getFormat(expr);
	}
	
	/**
	 * @返回值 HSSFFont 可以应用到单元格上的字体样式对象
	 */
	public HSSFFont createFont(){
		return workbook.createFont();
	}
	
	/**
	 * @参数 sheet 将要操作的Sheet页
	 * 		cellRange 区域单元
	 * 		cellStyle 单元格样式对象
	 * @说明 在给定Sheet页中指定的单元格区域上应用单元格样式
	 */
	public void formatCells(HSSFSheet sheet,String cellRange,HSSFCellStyle cellStyle){
		CellRangeAddress cellAddress=CellRangeAddress.valueOf(cellRange);
		int startRow=cellAddress.getFirstRow();
		int startColumn=cellAddress.getFirstColumn();
		int endRow=cellAddress.getLastRow();
		int endColumn=cellAddress.getLastColumn();
		for(int i=startRow;i<=endRow;i++)
		{
			HSSFRow row=sheet.getRow(i);
			if(row==null)
			{
				row=sheet.createRow(i);
			}
			for(int j=startColumn;j<=endColumn;j++)
			{
				HSSFCell cell=row.getCell(j);
				if(cell==null)
				{
					cell=row.createCell(j);
				}
				cell.setCellStyle(cellStyle);
			}
		}
	}
	
	/**
	 * @参数 sheet 将要操作的Sheet页
	 * 		row 行索引（从0索引）
	 * 		column 列索引（从0索引）
	 * 		cellStyle 单元格样式对象
	 * @说明 在给定Sheet页中指定的单元格上应用单元格样式
	 */
	public void formatCell(HSSFSheet sheet,int row,int column,HSSFCellStyle cellStyle){
		HSSFRow r=sheet.getRow(row);
		if(r==null)
		{
			r=sheet.createRow(row);
		}
		HSSFCell cell=r.getCell(column);
		if(cell==null)
		{
			cell=r.createCell(column);
		}
		cell.setCellStyle(cellStyle);
	}
	
	/**
	 * @参数 sheet 将要操作的Sheet页
	 * 		cellRange 区域单元
	 * @说明 在给定Sheet页中指定的单元格区域中从左上至右下方向画一条斜线
	 */
	public void drawLine(HSSFSheet sheet,String cellRange){
		CellRangeAddress range=CellRangeAddress.valueOf(cellRange);
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		HSSFClientAnchor anchor = new HSSFClientAnchor();
		anchor.setAnchor((short)range.getFirstColumn(), range.getFirstRow(), 0, 0, (short)(range.getLastColumn()+1),
				range.getLastRow()+1, 0, 0);
		patriarch.createSimpleShape(anchor);
	}
	
	/**
	 * @参数 os 输出流对象
	 * @说明 在更改后的工作簿保存到输出流中
	 */
	public void saveWorkBook(OutputStream os) throws IOException{
		workbook.write(os);
	}
	
	public byte[] getBytes(){
		byte[] bytes=null;
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		try {
			this.saveWorkBook(os);
			bytes=os.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(os!=null)
			{
				try {
					os.close();
				} catch (Exception e) {
					os=null;
				}
			}
		}
		return bytes;
	}
	
	public Object getCellValue(HSSFCell cell){
		if(cell==null) return null;
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case HSSFCell.CELL_TYPE_NUMERIC:
			if(HSSFDateUtil.isCellDateFormatted(cell))
				return cell.getDateCellValue();
			else{
				double value=cell.getNumericCellValue();
				if(value!=(long)value)
					return value;
				else 
					return (long)value;
			}
		case HSSFCell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();
		case HSSFCell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case HSSFCell.CELL_TYPE_BLANK:
			return null;
		case HSSFCell.CELL_TYPE_ERROR:
			return HSSFErrorConstants.getText(cell.getErrorCellValue());
		default:
			return cell.getRichStringCellValue();
		}
	}
	
	public static void main(String[] args) throws IOException {
		MSExcelUtil excel = new MSExcelUtil();
		HSSFSheet sheet = excel.addSheet("热线整体数据表格");
		excel.writeToSheet(sheet, 0, 0,"时间段");
		excel.writeToSheet(sheet, 0, 1,"业务组");
		excel.writeToSheet(sheet, 0, 2,"服务总请求量");
		excel.writeToSheet(sheet, 0, 3,"IVR自助服务数量");
		excel.writeToSheet(sheet, 0, 4,"IVR放弃数量");
		excel.writeToSheet(sheet, 0, 5,"IVR自助服务占比");
		excel.writeToSheet(sheet, 0, 6,"人工请求量");
		excel.writeToSheet(sheet, 0, 7,"实际人工接起数量");
		excel.writeToSheet(sheet, 0, 8,"20秒内接起数量");
		excel.writeToSheet(sheet, 0, 9,"人工队列放弃数量");
		excel.writeToSheet(sheet, 0, 10,"20秒内放弃数量");
		excel.writeToSheet(sheet, 0, 11,"转接IVR数量");
		excel.writeToSheet(sheet, 0, 12,"20秒服务水平");
		excel.writeToSheet(sheet, 0, 13,"平均通话时长");
		excel.writeToSheet(sheet, 0, 14,"通话排队最长时长");
		excel.writeToSheet(sheet, 0, 15,"通话排队等待平均时长");
		excel.writeToSheet(sheet, 0, 16,"呼损排队最长时长");
		excel.writeToSheet(sheet, 0, 17,"呼损排队等待平均时长");
		excel.writeToSheet(sheet, 0, 18,"转接量");
		excel.writeToSheet(sheet, 0, 19,"外呼量");
		excel.writeToSheet(sheet, 0, 20,"重复来电量");
		excel.writeToSheet(sheet, 0, 21,"评价量");
		excel.writeToSheet(sheet, 0, 22,"未评价");
		excel.writeToSheet(sheet, 0, 23,"对服务态度不满意");
		excel.writeToSheet(sheet, 0, 24,"对解决方案不满意");
		excel.writeToSheet(sheet, 0, 25,"双不满意");
		excel.writeToSheet(sheet, 0, 26,"一般");
		excel.writeToSheet(sheet, 0, 27,"评价率");
		excel.writeToSheet(sheet, 0, 28,"满意度");
		excel.writeToSheet(sheet, 0, 29,"差评率");
		
		excel.write("D://test.xls","");
	}
}
