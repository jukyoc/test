package com.speed.autoreport.persion.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.quartz.JobExecutionContext;

import com.speed.autoreport.dao.BusinessInfoDao;
import com.speed.autoreport.persion.dao.PersionDao;
import com.speed.autoreport.persion.vo.PersionRecord;
import com.speed.autoreport.util.DateUtil;
import com.speed.autoreport.vo.BusinessInfo;
import com.speed.hfreport.dao.HfReportDao;
import com.speed.management.quartz.dao.SchedulerManageDao;
import com.speed.management.quartz.service.BaseJobTemplate;
import com.speed.management.quartz.util.SpringUtils;
import com.speed.management.quartz.vo.JobDataTime;
import com.speed.util.KeyValueVo;

/**
 * 個人報表生成 包括日報表和月報表
 * 
 * @author yangbb
 * 
 */
public class DailyPersionTask extends BaseJobTemplate{

	/**
	 * log
	 */
	Logger log = Logger.getLogger(DailyPersionTask.class);

	/**
	 * 表示 日报表的标识
	 */
	private final int Day = 0;
	/**
	 * 月报表标识
	 */
	private final int Month = 1;
	/**
	 * 实际数据的开始行数
	 */
	private final int fixedRowIndex = 2;
	
	private final long maxSecond = 1800;

	/**
	 * 操作数据库dao 查询报表信息
	 */
	@Resource(name = "persionDao")
	private PersionDao dao;
	/**
	 * 操作数据库dao 查询业务线信息
	 */
	@Resource(name = "businessInfoDao")
	private BusinessInfoDao businessDao;
	@Resource(name="hfReportDao")
	private HfReportDao hfReportDao;
	@Resource(name="schedulerManageDao")
	private SchedulerManageDao schedulerManageDao;
	private SimpleDateFormat format = new SimpleDateFormat(
	"yyyy-MM-dd HH:mm:ss");
	
	private final static String DAY_KEY = "day";
	private final static String JOBN_AME_KEY = "JOB_NAME";
	
	/**
	 * 日報表生成
	 */
	
	@Override
	public void executes(JobExecutionContext context)throws Exception{
		try {
			String day = "";
			Map map = context.getMergedJobDataMap();
			String jobName=context.getJobDetail().getJobDataMap().getString(JOBN_AME_KEY).trim();
			if(map.containsKey(DAY_KEY)){
				day = String.valueOf(map.get(DAY_KEY));
			}else{
				day = DateUtil.getYestodayStr();
			}
			if(dao==null){
				dao = (PersionDao) SpringUtils.getContext().getBean("persionDao");
			}
			if(businessDao==null){
				businessDao = (BusinessInfoDao) SpringUtils.getContext().getBean("businessInfoDao");
			}
			if(hfReportDao==null){
				hfReportDao = (HfReportDao) SpringUtils.getContext().getBean("hfReportDao");
			}
			if(schedulerManageDao==null){
				schedulerManageDao = (SchedulerManageDao) SpringUtils.getContext().getBean("schedulerManageDao");
			}
			startForDay(day,  jobName);
		} catch (Exception e) {
			throw e;
		}
	}
	private void startForDay(String day,String jobName) throws Exception{

		log.info("persion day task start");
		long start = System.currentTimeMillis();
		// 查詢出所有業務線
		List<BusinessInfo> businessList = null;
		try {
			businessList = businessDao.queryBusinessInfo(null);
		} catch (Exception e) {
			log.error("persion day task query businessinfo error");
			e.printStackTrace();
			throw e;
		}
		List<KeyValueVo> queueInfos = null;
		JobDataTime jt=new JobDataTime();jt.setJobName(jobName);
		List<JobDataTime> jobDataTimes=schedulerManageDao.queryJobDataTime(jt);
		DateUtil.dealWithJobDataTimesDay(jobDataTimes);
		
		
		for (BusinessInfo info : businessList) {
			queueInfos=hfReportDao.queryQueueInfo(Integer.parseInt(info.getBusinessId()) );
			if(queueInfos==null){queueInfos=new ArrayList<KeyValueVo>();}
			KeyValueVo kv=new KeyValueVo();
			kv.setKey("all");
			queueInfos.add(kv);
			for (KeyValueVo keyValueVo : queueInfos) {
					for (JobDataTime jobDataTime : jobDataTimes) {
						queueData(  day,  info,  keyValueVo.getKey(),jobDataTime) ;
					}
				 
			}
		}
		log.info("persion day task use time : " + (System.currentTimeMillis()-start) + "ms");
		log.info("create excel end ....");
		log.info("persion day task end");
	}
private void queueData(String day,BusinessInfo info,String queueId,JobDataTime jobDataTime ) throws Exception{
	//初始化 today ：今天的00：00：00
	Calendar today = getToday(day);
	//初始化昨天 yestoday : 昨天的00:00:00
	Calendar yestoday = getYestoday(day);
	String fileDataPath=jobDataTime.getJobStartTime().replace(":", "_")+"_"+jobDataTime.getJobEndTime().replace(":", "_")+File.separator;
	DateUtil.dealWithJobCalendarDay(today, yestoday, jobDataTime);
	SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy年MM月dd日");
	HSSFWorkbook wb = createBasicExcel(
			simpleFormat.format(yestoday.getTime()), "热线个人数据日表格");
	int rowIndex = fixedRowIndex;
	log.info("create excel start...");
	Calendar c=yestoday;
	System.out.println(c.get(Calendar.MONTH)+"_"+c.get(Calendar.DAY_OF_MONTH)+"_"+c.get(Calendar.HOUR)+"_"+c.get(Calendar.MINUTE)+"_"+c.get(Calendar.SECOND));
	c=today;
	System.out.println(c.get(Calendar.MONTH)+"_"+c.get(Calendar.DAY_OF_MONTH)+"_"+c.get(Calendar.HOUR)+"_"+c.get(Calendar.MINUTE)+"_"+c.get(Calendar.SECOND));
	while ((yestoday.compareTo(today) < 0)) {
		String startTime = format.format(yestoday.getTime());
		yestoday.add(Calendar.MINUTE, 60);
		String endTime = format.format(yestoday.getTime());
		if(yestoday.compareTo(today)>0){
			endTime = format.format(today.getTime());
		}
		log.debug("startTime : " + startTime + ", endtime:" + endTime);
		List<PersionRecord> list = null;
		try {
			if(StringUtils.equals(queueId, "all")){
				list = dao.queryPersionRecord(startTime, endTime,
						info.getBusinessId());
			}else{
				list = dao.queryPersionRecord(startTime, endTime,
						info.getBusinessId(),queueId);
			}
			
		} catch (Exception e) {
			log.error("persion day task query record error");
			e.printStackTrace();
			throw e;
		}
		rowIndex = fillExcel(wb, list, rowIndex,
				startTime.substring(11, 16), endTime.substring(11, 16),
				Day);
	}
	//yestoday 进过循环后同today 相等，所以 减去 1天
	yestoday.add(Calendar.DATE, -1);
	String basicPath = System.getProperty("webapp.root");
	String path = basicPath + File.separator + "autoreport"
			+ File.separator + "data" + File.separator
			+ info.getBusinessId() +File.separator+ queueId+File.separator +fileDataPath+ "热线个人数据表格"
			+ File.separator + "日报表" + File.separator
			+ yestoday.get(Calendar.YEAR)+ "年" + File.separator
			+ (yestoday.get(Calendar.MONTH) + 1)+"月";
	File file = new File(path);
	if (!file.exists()) {
		file.mkdirs();
	}
	File outFile = new File(file.getAbsolutePath() + File.separator
			+ format.format(yestoday.getTime()).substring(0,10) + ".xls");
	FileOutputStream stream = null;
	try {
		stream = new FileOutputStream(outFile);
		wb.write(stream);
		stream.close();
		log.info("[" + outFile.getAbsolutePath() + File.separator
				+ outFile.getName() + "] create success !");
	} catch (FileNotFoundException e) {
		log.error("file not found...");
		e.printStackTrace();
	} catch (IOException ex) {
		log.error("may by file is using,write error ["
				+ outFile.getAbsolutePath() + File.separator
				+ outFile.getName() + "]");
		ex.printStackTrace();
	} finally {
		if(stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
	/**
	 * 根據業務線分組
	 * @param list
	 * @return
	 */
	private Map<String, List<PersionRecord>> groupByBusinessId(
			List<PersionRecord> list) {
		Map<String, List<PersionRecord>> map = new HashMap<String, List<PersionRecord>>();
		for (PersionRecord persion : list) {
			String key = persion.getBusinessId() + "|"
					+ persion.getBusinessName();
			if (map.containsKey(key)) {
				map.get(key).add(persion);
			} else {
				List<PersionRecord> persionList = new ArrayList<PersionRecord>();
				persionList.add(persion);
				map.put(key, persionList);
			}
		}
		return map;
	}

	/**
	 * 填充excel
	 * @param wb
	 * @param list
	 * @param rowIndex
	 * @param startTime
	 * @param endTime
	 * @param flag
	 * @return
	 */
	private int fillExcel(HSSFWorkbook wb, List<PersionRecord> list,
			int rowIndex, String startTime, String endTime, int flag) {
		HSSFCellStyle theaderStyle = wb.createCellStyle();
		theaderStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		theaderStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		//如果没有数据，则只显示第一列的时间段信息
		if (list == null || list.size() == 0) {
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row = sheet.createRow(rowIndex++);
			HSSFCell cell = row.createCell(0);
			cell.setCellStyle(theaderStyle);
			if (flag == Day) {
				cell.setCellValue(startTime + "-" + endTime);
			}
			if (flag == Month) {
				cell.setCellValue(startTime);
			}
			return rowIndex;
		}
		// Region cellRangeAdd = new Region(rowIndex, (short) 0,
		// rowIndex+list.size()-1, (short) 0);
		Map<String, List<PersionRecord>> map = groupByBusinessId(list);

		HSSFSheet sheet = wb.getSheetAt(0);
		CellRangeAddress cellRangeAdd = new CellRangeAddress(rowIndex, rowIndex
				+ list.size() - 1, 0, 0);
		HSSFRow row = sheet.createRow(rowIndex);
		sheet.addMergedRegion(cellRangeAdd);
		HSSFCell cell0 = row.createCell(0);
		cell0.setCellStyle(theaderStyle);
		if (flag == Day) {
			cell0.setCellValue(startTime + "-" + endTime);
		}
		if (flag == Month) {
			cell0.setCellValue(startTime);
		}
		Iterator<Entry<String, List<PersionRecord>>> it = map.entrySet()
				.iterator();
		while (it.hasNext()) {
			Entry<String, List<PersionRecord>> entry = it.next();
			String businessName = entry.getKey().split("\\|")[1];
			CellRangeAddress cellRangeAddBusiness = new CellRangeAddress(
					rowIndex, rowIndex + entry.getValue().size() - 1, 1, 1);
			HSSFRow rowBusiness = sheet.getRow(rowIndex);
			if (rowBusiness == null) {
				rowBusiness = sheet.createRow(rowIndex);
			}
			sheet.addMergedRegion(cellRangeAddBusiness);
			HSSFCell cell1 = rowBusiness.createCell(1);
			cell1.setCellValue(businessName);
			cell1.setCellStyle(theaderStyle);
			for (PersionRecord persion : entry.getValue()) {
				int columnIndex = 2;
				HSSFRow rowConent = sheet.getRow(rowIndex);
				if (rowConent == null) {
					rowConent = sheet.createRow(rowIndex);
				}
				rowIndex++;
				fillRow(rowConent, persion, columnIndex,wb);
			}
			// HSSFRow row
		}
		return rowIndex;
	}

	/**
	 * 填充一行
	 * 
	 * @param rowConent
	 *            excel的行對象
	 * @param persion
	 *            vo
	 * @param columnIndex
	 *            列
	 */
	private void fillRow(HSSFRow rowConent, PersionRecord persion,
			int columnIndex,HSSFWorkbook wb) {
		rowConent.createCell(columnIndex++).setCellValue(persion.getCsId());
		rowConent.createCell(columnIndex++).setCellValue(persion.getCsName());
//		HSSFCellStyle numStyle = wb.createCellStyle();
//		numStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
//		HSSFCellUtil.createCell(rowConent, columnIndex++, persion.getConnectCount() , numStyle);
        HSSFCell callAlloctionCell = rowConent.createCell(columnIndex++);
        callAlloctionCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        callAlloctionCell.setCellValue(
                persion.getCallAlloction());

        HSSFCell cell3 = rowConent.createCell(columnIndex++);
        cell3.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cell3.setCellValue(
                persion.getConnectCount());

        HSSFCell callCountIn20sCell = rowConent.createCell(columnIndex++);
        callCountIn20sCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        callCountIn20sCell.setCellValue(
                persion.getConnectCountIn20Sec());

//		HSSFCellUtil.createCell(rowConent, columnIndex++, persion.getTransCount() , numStyle);
        HSSFCell cell4 = rowConent.createCell(columnIndex++);
        cell4.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cell4.setCellValue(
                persion.getTransCount());


        HSSFCell transOutCountCell = rowConent.createCell(columnIndex++);
        transOutCountCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        transOutCountCell.setCellValue(
                persion.getTransOutCount());
//		HSSFCellUtil.createCell(rowConent, columnIndex++, persion.getLooseCount() , numStyle);
		HSSFCell cell5 = rowConent.createCell(columnIndex++);
		cell5.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell5.setCellValue(
				persion.getLooseCount());
//		HSSFCellUtil.createCell(rowConent, columnIndex++, persion.getOutBoundCallNum() , numStyle);
		HSSFCell cell6 = rowConent.createCell(columnIndex++);
		cell6.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell6.setCellValue(
				persion.getOutBoundCallNum());
//		HSSFCellUtil.createCell(rowConent, columnIndex++, tranform(persion.getAvgConnectTime()) , null);
		HSSFCell cell7 = rowConent.createCell(columnIndex++);
		cell7.setCellValue(
				tranform(persion.getAvgConnectTime()));
//		HSSFCellUtil.createCell(rowConent, columnIndex++, tranform(persion.getWorkTime()) , null);
		HSSFCell cell8 = rowConent.createCell(columnIndex++);
		cell8.setCellValue(
				tranform(persion.getWorkTime()));
//		HSSFCellUtil.createCell(rowConent, columnIndex++, tranform(persion.getRestTime()) , null);
		HSSFCell cell9 = rowConent.createCell(columnIndex++);
		cell9.setCellValue(
				tranform(persion.getRestTime()));
		HSSFCell cell1 = rowConent.createCell(columnIndex++);
		cell1.setCellValue(
				tranform(persion.getKeepTime()));
//		HSSFCellUtil.createCell(rowConent, columnIndex++, String.valueOf(persion.getOutTime()) , numStyle);
		HSSFCell cell10 = rowConent.createCell(columnIndex++);
		cell10.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell10.setCellValue(persion.getOutTime());
//		HSSFCellUtil.createCell(rowConent, columnIndex++, String.valueOf(persion.getRestCount()) , numStyle);
		HSSFCell cell11 = rowConent.createCell(columnIndex++);
		cell11.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell11.setCellValue(persion.getRestCount());
//		HSSFCellUtil.createCell(rowConent, columnIndex++, persion.getSatisfacingCount() , numStyle);
		HSSFCell cell12 = rowConent.createCell(columnIndex++);
		cell12.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell12.setCellValue(
				persion.getSatisfacingCount());
//		HSSFCellUtil.createCell(rowConent, columnIndex++, persion.getUnSatisfacingCount() , numStyle);
		HSSFCell cell13 = rowConent.createCell(columnIndex++);
		cell13.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell13.setCellValue(
			persion.getUnSatisfacingCount());
		
		HSSFCell cell21 = rowConent.createCell(columnIndex++);
		cell21.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell21.setCellValue(
				persion.getGoodCount());
		
//		HSSFCellUtil.createCell(rowConent, columnIndex++, persion.getBadServiceCount(), numStyle);
		HSSFCell cell14 = rowConent.createCell(columnIndex++);
		cell14.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell14.setCellValue(
				persion.getBadServiceCount());
//		HSSFCellUtil.createCell(rowConent, columnIndex++, persion.getBadPlanCount(), numStyle);
		HSSFCell cell15 = rowConent.createCell(columnIndex++);
		cell15.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell15.setCellValue(
				persion.getBadPlanCount());
//		HSSFCellUtil.createCell(rowConent, columnIndex++, persion.getBadCount(), numStyle);
		HSSFCell cell16 = rowConent.createCell(columnIndex++);
		cell16.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell16.setCellValue(persion.getBadCount());
//		HSSFCellUtil.createCell(rowConent, columnIndex++, persion.getGeneralCount(), numStyle);
		HSSFCell cell17 = rowConent.createCell(columnIndex++);
		cell17.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell17.setCellValue(
				persion.getGeneralCount());
//		HSSFCellUtil.createCell(rowConent, columnIndex++, persion.getSaticfacingPercent(), null);
		HSSFCell cell18 = rowConent.createCell(columnIndex++);
		cell18.setCellValue(
				persion.getSaticfacingPercent());
//		HSSFCellUtil.createCell(rowConent, columnIndex++, persion.getGoodPercent(), null);
		HSSFCell cell19 = rowConent.createCell(columnIndex++);
		cell19.setCellValue(
				persion.getGoodPercent());
//		HSSFCellUtil.createCell(rowConent, columnIndex++, persion.getBadPercent(), null);
		HSSFCell cell20 = rowConent.createCell(columnIndex++);
		cell20.setCellValue(
				persion.getBadPercent());
	}

	/**
	 * 生成excel的標題和列頭信息
	 * 
	 * @param date
	 * @param sheetText
	 * @return
	 */
	private HSSFWorkbook createBasicExcel(String date, String sheetText) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetText);
		sheet.createFreezePane(0, fixedRowIndex);// 冻结第二行
		HSSFCellStyle theaderStyle = wb.createCellStyle();
		theaderStyle.setLocked(true);

		theaderStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		theaderStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		HSSFFont theaderFont = wb.createFont();
		theaderFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		theaderStyle.setFont(theaderFont);
		// row.setHeight((short) 500);
		HSSFRow row = sheet.createRow(0);
		int index = 0;
		HSSFCellUtil.createCell(row,index,date + "查询结果",theaderStyle);
		
		HSSFRow row1 = sheet.createRow(1);
		// row1.setHeight((short) 650);
		HSSFCellUtil.createCell(row1,index++,"时间段",theaderStyle);
		HSSFCellUtil.createCell(row1,index++,"业务组",theaderStyle);
		HSSFCellUtil.createCell(row1,index++,"工号",theaderStyle);
		HSSFCellUtil.createCell(row1,index++,"姓名",theaderStyle);

        HSSFCellUtil.createCell(row1,index++,"来电分配量",theaderStyle);
		HSSFCellUtil.createCell(row1,index++,"(实际人工)\n接起量",theaderStyle);
        HSSFCellUtil.createCell(row1,index++,"20s接起量",theaderStyle);

		HSSFCellUtil.createCell(row1,index++,"转接量",theaderStyle);
        HSSFCellUtil.createCell(row1,index++,"转出量",theaderStyle);


		HSSFCellUtil.createCell(row1,index++,"漏接量",theaderStyle);
		HSSFCellUtil.createCell(row1,index++,"外呼量",theaderStyle);
		HSSFCellUtil.createCell(row1,index++,"平均通话时长",theaderStyle);
		HSSFCellUtil.createCell(row1,index++,"工作时长",theaderStyle);
		
		HSSFCellUtil.createCell(row1,index++,"小休时长",theaderStyle);
		HSSFCellUtil.createCell(row1,index++,"保持时长",theaderStyle);
		HSSFCellUtil.createCell(row1,index++,"签出次数",theaderStyle);
		HSSFCellUtil.createCell(row1,index++,"小休次数",theaderStyle);
		HSSFCellUtil.createCell(row1,index++,"评价量",theaderStyle);
		HSSFCellUtil.createCell(row1,index++,"未评价",theaderStyle);
		
		HSSFCellUtil.createCell(row1,index++,"满意量",theaderStyle);
		HSSFCellUtil.createCell(row1,index++,"对服务态度不满意",theaderStyle);
		HSSFCellUtil.createCell(row1,index++,"对解决方案不满意",theaderStyle);
		HSSFCellUtil.createCell(row1,index++,"双不满意",theaderStyle);
		HSSFCellUtil.createCell(row1,index++,"一般",theaderStyle);
		HSSFCellUtil.createCell(row1,index++,"评价率",theaderStyle);
		
		HSSFCellUtil.createCell(row1,index++,"满意度",theaderStyle);
		HSSFCellUtil.createCell(row1,index++,"差评率",theaderStyle);
		return wb;
	}

	/**
	 * 格式化秒為 xx時xx分xx秒
	 * 
	 * @param sec
	 * @return
	 */
	private String tranform(long sec) {
		if(sec >= maxSecond) {
			sec -= maxSecond - 1;
		}
		long hour = sec / (60 * 60);
		long min = (sec - hour * 60 * 60) / 60;
		String minStr = min<10?"0"+min:String.valueOf(min);
		long secInt = sec - hour * 60 * 60 - min * 60;
		String secStr = secInt<10?"0"+secInt:String.valueOf(secInt);
		return hour + ":" + minStr + ":" + secStr;
	}
	
	private Calendar getToday() {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		return today;
	}
	
	private Calendar getYestoday() {
		Calendar yestoday = Calendar.getInstance();
		// calendar.set(2013, 2, 1);
		yestoday.add(Calendar.DATE, -1);
		yestoday.set(Calendar.HOUR_OF_DAY, 0);
		yestoday.set(Calendar.MINUTE, 0);
		yestoday.set(Calendar.SECOND, 0);
		return yestoday;
	}
	private Calendar getToday(String dayStr) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(dayStr);
		Calendar today = Calendar.getInstance();
		today.setTime(date);
		today.add(Calendar.DAY_OF_MONTH, 1);
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		return today;
	}
	private Calendar getYestoday(String dayStr) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(dayStr);
		Calendar yestoday = Calendar.getInstance();
		// calendar.set(2013, 2, 1);
		yestoday.setTime(date);
		yestoday.set(Calendar.HOUR_OF_DAY, 0);
		yestoday.set(Calendar.MINUTE, 0);
		yestoday.set(Calendar.SECOND, 0);
		return yestoday;
	}
	
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		// calendar.set(2013, 2, 1);
		Date date = format.parse("2013-01-1");
		 c.setTime(date);
		
		String startTime = format.format(c.getTime());
		System.out.println(startTime);
	}
}
