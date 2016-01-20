/**
 *
 */
package com.speed.autoreport.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.stereotype.Service;

import com.speed.autoreport.dao.BusinessInfoDao;
import com.speed.autoreport.dao.CallAllStatisticsDao;
import com.speed.autoreport.service.CallAllStatisticsService;
import com.speed.autoreport.util.DateUtil;
import com.speed.autoreport.util.MSExcelUtil;
import com.speed.autoreport.vo.BusinessInfo;
import com.speed.autoreport.vo.CallAgentStatisticsObj;
import com.speed.autoreport.vo.CallAllStatisticsObj;
import com.speed.hfreport.dao.HfReportDao;
import com.speed.hfreport.service.HfReportService;
import com.speed.management.quartz.vo.JobDataTime;
import com.speed.util.KeyValueVo;

/**
 * @author liuhl
 *
 */
@Service("callAllStatisticsService")
public class CallAllStatisticsServiceImpl implements CallAllStatisticsService {

	private static Logger log = Logger.getLogger("InfoFile");

	@Resource(name="callAllStatisticsDao")
	private CallAllStatisticsDao callAllStatisticsDao;

	@Resource(name="businessInfoDao")
	private BusinessInfoDao businessInfoDao;
	@Resource(name="hfReportDao")
	private HfReportDao hfReportDao;
	private static String path = "";
	private static String fielSep ="";
	static{
		fielSep = File.separator;
	}
	private SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	@Override
	public Object createExcelForDay(String day,List<JobDataTime> jobDataTimes) throws Exception {
		List<BusinessInfo> busiInfo = businessInfoDao.queryBusinessInfo(null);
		List<KeyValueVo> queueInfos = null;
		for(int j=0; j<busiInfo.size(); j++){
			queueInfos=hfReportDao.queryQueueInfo(Integer.parseInt(busiInfo.get(j).getBusinessId()) );
			if(queueInfos==null){queueInfos=new ArrayList<KeyValueVo>();}
			KeyValueVo all=new KeyValueVo();
			all.setKey("all");
			queueInfos.add(all);
			for(int k=0;k<queueInfos.size();k++){
				for (JobDataTime jobDataTime : jobDataTimes) {
//						day = currentDateStr(day);
//						String date = day;
//						date = date+" 00:00:00";
					Calendar today =DateUtil. getToday(day);
					//初始化昨天 yestoday : 昨天的00:00:00
					Calendar yestoday = DateUtil.getYestoday(day);
					String fileDataPath=jobDataTime.getJobStartTime().replace(":", "_")+"_"+jobDataTime.getJobEndTime().replace(":", "_")+File.separator;
					DateUtil.dealWithJobCalendarDay(today, yestoday, jobDataTime);
						String startTime =null;
						String endTime =null;
						//创建标题
						MSExcelUtil excel = createWorkBook();
						HSSFSheet sheet = excel.getSheet(0);
						sheet.createFreezePane(0, 1);//冻结住第一行
						int rowIndex = 1;
						Calendar calendar=null;
						for(int i=0; i<48; i++){
							if(calendar==null){
								calendar=Calendar.getInstance();
								calendar.setTime(yestoday.getTime());
							}
							startTime=format.format(calendar.getTime()) ;
							calendar.add(Calendar.MINUTE, 30);
							endTime = format.format(calendar.getTime());
							if(calendar.compareTo(today)>0){
								endTime = format.format(today.getTime());
							}
							CallAllStatisticsObj callAllStatisticsObj = new CallAllStatisticsObj();
							callAllStatisticsObj.setBeginTime(startTime);
							callAllStatisticsObj.setEndTime(endTime);
			
							System.out.println(""+busiInfo.get(j).getBusinessId()
									+","+callAllStatisticsObj.getBeginTime()
									+","+callAllStatisticsObj.getEndTime());
							callAllStatisticsObj.setBussinessId(busiInfo.get(j).getBusinessId());
							callAllStatisticsObj.setBussinessName(busiInfo.get(j).getBusinessName());
							callAllStatisticsObj.setQueueId(queueInfos.get(k).getKey());
							//invoke dao
							List<CallAllStatisticsObj> result =  callAllStatisticsDao.queryCallAllStatisticsData(callAllStatisticsObj);
							writeData(excel,sheet,rowIndex,result.get(0),0);
							rowIndex++;
							if(calendar.compareTo(today)>=0){
								break;
							}
						}
						//生成汇总行
						CallAllStatisticsObj callAllStatisticsObj = new CallAllStatisticsObj();
//						callAllStatisticsObj.setBeginTime(day+" 00:00:00");
//						callAllStatisticsObj.setEndTime(nextDayStr(day)+" 00:00:00");
						callAllStatisticsObj.setBeginTime(format.format(yestoday.getTime()) );
						callAllStatisticsObj.setEndTime(format.format(today.getTime()));
			
						callAllStatisticsObj.setBussinessId(busiInfo.get(j).getBusinessId());
						callAllStatisticsObj.setBussinessName(busiInfo.get(j).getBusinessName());
						callAllStatisticsObj.setQueueId(queueInfos.get(k).getKey());
						List<CallAllStatisticsObj> result =  callAllStatisticsDao.queryCallAllStatisticsData(callAllStatisticsObj);
						writeData(excel,sheet,rowIndex,result.get(0),2);
			
			
						//生成文件
						path = System.getProperty("webapp.root") + fielSep + "autoreport"+fielSep+"data"+fielSep;
						log.info("path==========" + path);
						System.out.println("path ===========" + path);
						excel.write(path+busiInfo.get(j).getBusinessId()+fielSep+queueInfos.get(k).getKey()+fielSep+fileDataPath+"话务综合统计报表"+fielSep+"日报表"+fielSep
								+day.substring(0, 4)+"年"+fielSep+day.substring(5, 7)+"月"+fielSep,
								fielSep+day+".xls");
				}	
			}
		}
		return true;
	}
	private HSSFSheet MonthDataSheet(MSExcelUtil excel) {
		HSSFSheet sheet = excel.addSheet("实际人工接起数量数据表格");
		excel.writeToSheetWithStyle(sheet, 0, 0, "员工号");
		excel.writeToSheetWithStyle(sheet, 0, 1, "姓名");
		excel.writeToSheetWithStyle(sheet, 0, 2, "接听数量");
		return sheet;
	}
	private void writeMonthData(MSExcelUtil excel, HSSFSheet sheet, int rowNum, CallAgentStatisticsObj data, int type) throws Exception {
		int i = 0;
		if (type == 1) {
			// 汇总行
			excel.writeToSheet(sheet, rowNum, i++, "汇总");
		} else {
			excel.writeToSheet(sheet, rowNum, i++, data.getAgentId());
		}
		excel.writeToSheet(sheet, rowNum, i++, data.getAgentName());
		excel.writeToSheet(sheet, rowNum, i++, data.getConnectCount());

	}
	public MSExcelUtil createWorkBook() throws IOException {
		int num=0;
		MSExcelUtil excel = new MSExcelUtil();
		HSSFSheet sheet = excel.addSheet("热线整体数据表格");
		excel.writeToSheetWithStyle(sheet, 0, num++,"时间段");
		excel.writeToSheetWithStyle(sheet, 0, num++,"业务组");
		excel.writeToSheetWithStyle(sheet, 0, num++,"服务总请求量");
		excel.writeToSheetWithStyle(sheet, 0, num++,"IVR自助服务数量");
		excel.writeToSheetWithStyle(sheet, 0, num++,"IVR放弃数量");
		excel.writeToSheetWithStyle(sheet, 0, num++,"IVR自助服务占比");
		excel.writeToSheetWithStyle(sheet, 0, num++,"人工请求量");
		excel.writeToSheetWithStyle(sheet, 0, num++,"实际人工接起数量");
		excel.writeToSheetWithStyle(sheet, 0, num++,"转出量");
		excel.writeToSheetWithStyle(sheet, 0, num++,"20秒内接起数量");
		excel.writeToSheetWithStyle(sheet, 0, num++,"人工队列放弃数量");
		excel.writeToSheetWithStyle(sheet, 0, num++,"20秒内放弃数量");
		excel.writeToSheetWithStyle(sheet, 0, num++,"20秒服务水平");
		excel.writeToSheetWithStyle(sheet, 0, num++,"平均通话时长");
		excel.writeToSheetWithStyle(sheet, 0, num++,"通话排队最长时长");
		excel.writeToSheetWithStyle(sheet, 0, num++,"通话排队等待平均时长");
		excel.writeToSheetWithStyle(sheet, 0, num++,"队列呼损排队最长时长");
		excel.writeToSheetWithStyle(sheet, 0, num++,"队列呼损排队等待平均时长");
		excel.writeToSheetWithStyle(sheet, 0, num++,"转接量");
		excel.writeToSheetWithStyle(sheet, 0, num++,"外呼量");
		excel.writeToSheetWithStyle(sheet, 0, num++,"重复来电量");
		excel.writeToSheetWithStyle(sheet, 0, num++,"评价量");
		excel.writeToSheetWithStyle(sheet, 0, num++,"未评价");
		excel.writeToSheetWithStyle(sheet, 0, num++,"满意评价量");
		excel.writeToSheetWithStyle(sheet, 0, num++,"对服务态度不满意");
		excel.writeToSheetWithStyle(sheet, 0, num++,"对解决方案不满意");
		excel.writeToSheetWithStyle(sheet, 0, num++,"双不满意");
		excel.writeToSheetWithStyle(sheet, 0, num++,"一般");
		excel.writeToSheetWithStyle(sheet, 0, num++,"评价率");
		excel.writeToSheetWithStyle(sheet, 0, num++,"满意度");
		excel.writeToSheetWithStyle(sheet, 0, num++,"差评率");
		return excel;
	}


	private void writeData(MSExcelUtil excel,HSSFSheet sheet,int rowNum,CallAllStatisticsObj data,int type)throws Exception{
		int num=0;
		String beginTime = data.getBeginTime();
		String endTime = data.getEndTime();
		if(type ==0){
			excel.writeToSheet(sheet, rowNum,num++,beginTime.split(" ")[1]+"-"+endTime.split(" ")[1]);
		}else if(type==1){//2013-01-02 00:00:00
			excel.writeToSheet(sheet, rowNum,num++,converDayOfMonth(beginTime)+"日");
		}else if(type==2){//汇总行
			excel.writeToSheet(sheet, rowNum,num++,"汇总");
		}
		excel.writeToSheet(sheet, rowNum, num++,data.getBussinessName());
		excel.writeToSheetForNum(sheet, rowNum, num++,data.getSysAllCallInCount());
		excel.writeToSheetForNum(sheet, rowNum, num++,data.getAutoQueryCount());
		excel.writeToSheetForNum(sheet, rowNum, num++,data.getLoseCount());
		excel.writeToSheet(sheet, rowNum, num++,data.getAutoServicePercent());
		excel.writeToSheetForNum(sheet, rowNum, num++,data.getCallInCount());
		excel.writeToSheetForNum(sheet, rowNum, num++,data.getConnectCount());
		excel.writeToSheetForNum(sheet, rowNum, num++,data.getTransOutCount());
		excel.writeToSheetForNum(sheet, rowNum, num++,data.getConnectCountIn20s());
		excel.writeToSheetForNum(sheet, rowNum, num++,data.getUnConnectCount());
		excel.writeToSheetForNum(sheet, rowNum, num++,data.getUnConnectCountIn20s());
		excel.writeToSheet(sheet, rowNum, num++,data.getSrvsPcntIn20s());
		excel.writeToSheet(sheet, rowNum, num++,data.getAvgConnectTime());
		excel.writeToSheet(sheet, rowNum, num++,data.getMaxWaitTime());
		excel.writeToSheet(sheet, rowNum, num++,data.getAvgWaitTime());
		excel.writeToSheet(sheet, rowNum, num++,data.getMaxWaitTimeOfLose());
		excel.writeToSheet(sheet, rowNum, num++,data.getAvgWaitTimeOfLose());
		excel.writeToSheetForNum(sheet, rowNum, num++,data.getTransCount());
		excel.writeToSheetForNum(sheet, rowNum, num++,data.getCallOutCount());
		excel.writeToSheetForNum(sheet, rowNum, num++,data.getReCallinCount());
		excel.writeToSheetForNum(sheet, rowNum, num++,data.getAppraiseCount());
		excel.writeToSheetForNum(sheet, rowNum, num++,data.getUnAppraiseCount());
		excel.writeToSheetForNum(sheet, rowNum, num++,data.getGoodCount());
		excel.writeToSheetForNum(sheet, rowNum, num++,data.getBadServiceCount());
		excel.writeToSheetForNum(sheet, rowNum, num++,data.getBadPlanCount());
		excel.writeToSheetForNum(sheet, rowNum, num++,data.getBadCount());
		excel.writeToSheetForNum(sheet, rowNum, num++,data.getGeneralCount());
		excel.writeToSheet(sheet, rowNum, num++,data.getAppraisePercent());
		excel.writeToSheet(sheet, rowNum, num++,data.getGoodPercent());
		excel.writeToSheet(sheet, rowNum, num++,data.getBadAppraisePercent());
	}
	@Override
	public Object createExcelForMonth(String month,List<JobDataTime> jobDataTimes) throws Exception {
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
//		Date newDate = format.parse(month);
//		Calendar calender = Calendar.getInstance();
//		calender.setTime(newDate);
//		int monthLength = calender.getActualMaximum(Calendar.DAY_OF_MONTH);

		List<BusinessInfo> busiInfo = businessInfoDao.queryBusinessInfo(null);
		List<KeyValueVo> queueInfos = null;
		for(int j=0; j<busiInfo.size(); j++){
			queueInfos=hfReportDao.queryQueueInfo(Integer.parseInt(busiInfo.get(j).getBusinessId()) );
			if(queueInfos==null){queueInfos=new ArrayList<KeyValueVo>();}
			KeyValueVo all=new KeyValueVo();
			all.setKey("all"); 
			queueInfos.add(all);
			for(int k=0;k<queueInfos.size();k++){
					for (JobDataTime jobDataTime : jobDataTimes) {
							Calendar start=Calendar.getInstance();
							Calendar end=Calendar.getInstance();
							{
								String newMonth=month;
								String year=month.substring(0, 4);
								newMonth=newMonth.substring(5);
								start.set(Calendar.YEAR, Integer.parseInt(year));
								start.set(Calendar.MONTH, Integer.parseInt(newMonth)-1);
								end.setTime(start.getTime());
								end.add(Calendar.MONTH, 1);
							}
							DateUtil.dealWithJobCalendarMonth(start, end, jobDataTime);
							String fileDataPath=jobDataTime.getJobStartTime().replace(" ", "_").replace(":", "_")+"_"+jobDataTime.getJobEndTime().replace(" ", "_").replace(":", "_")+File.separator;
//							month = currentMonthStr(month);
//							String date = month;
//							date = date+"-01 00:00:00";
							String startTime = "";
							String endTime = "";
							//创建标题
							MSExcelUtil excel = createWorkBook();
							HSSFSheet sheet = excel.getSheet(0);
							sheet.createFreezePane(0, 1);//冻结住第一行
							int rowIndex = 1;
							Calendar startTemp=null;
							for(int i=0; i<31; i++){
//								startTime = date;
//								date = createDayStr(date);
//								endTime = date;
								{
									if(startTemp==null){
										startTemp=Calendar.getInstance();
										startTemp.setTime(start.getTime());
									}
								}
								{
									startTime=format.format(startTemp.getTime());
									startTemp.add(Calendar.DAY_OF_MONTH, 1);
									endTime=format.format(startTemp.getTime());
									if(startTemp.compareTo(end)>0){
										endTime=format.format(end.getTime());
									}
								}
				
								System.out.println("\n 第"+(i+1)+"时间段，begin-"+startTime);
								System.out.println("end-"+endTime);
				
								CallAllStatisticsObj callAllStatisticsObj = new CallAllStatisticsObj();
								callAllStatisticsObj.setBeginTime(startTime);
								callAllStatisticsObj.setEndTime(endTime);
				
								callAllStatisticsObj.setBussinessId(busiInfo.get(j).getBusinessId());
								callAllStatisticsObj.setBussinessName(busiInfo.get(j).getBusinessName());
								callAllStatisticsObj.setQueueId(queueInfos.get(k).getKey());
								List<CallAllStatisticsObj> result =  callAllStatisticsDao.queryCallAllStatisticsData(callAllStatisticsObj);
								writeData(excel,sheet,rowIndex,result.get(0),1);
								rowIndex++;
								if(startTemp.compareTo(end)>=0){
									break;
								}
							}
							startTime=format.format(start.getTime());
							endTime=format.format(end.getTime());
							//生成汇总
							CallAllStatisticsObj callAllStatisticsObj = new CallAllStatisticsObj();
//							callAllStatisticsObj.setBeginTime(month + "-01 00:00:00");
//							callAllStatisticsObj.setEndTime(nextMonthStr(month)+ "-01 00:00:00");
							callAllStatisticsObj.setBeginTime(startTime);
							callAllStatisticsObj.setEndTime(endTime);
				
							callAllStatisticsObj.setBussinessId(busiInfo.get(j).getBusinessId());
							callAllStatisticsObj.setBussinessName(busiInfo.get(j).getBusinessName());
							callAllStatisticsObj.setQueueId(queueInfos.get(k).getKey());
							List<CallAllStatisticsObj> result =  callAllStatisticsDao.queryCallAllStatisticsData(callAllStatisticsObj);
							writeData(excel,sheet,rowIndex,result.get(0),2);
							{
								// 添加人工实际接通量详细数据
								// 人工详细数据
								List<CallAgentStatisticsObj> data = callAllStatisticsDao.queryAgentCallStatisticsData(callAllStatisticsObj);
								// 汇总数据
								List<CallAgentStatisticsObj> dataSum = callAllStatisticsDao.queryAgentCountCallStatisticsData(callAllStatisticsObj);
								HSSFSheet monthSheet = MonthDataSheet(excel);
								CallAgentStatisticsObj obj;
								for (int i = 0; i < data.size(); i++) {
									obj = data.get(i);
									writeMonthData(excel, monthSheet, i + 1, obj, 0);
								}
								// 填写汇总
								writeMonthData(excel, monthSheet, data.size() + 1, dataSum.get(0), 1);
							}
							//生成文件
							path = System.getProperty("webapp.root") + fielSep + "autoreport"+fielSep+"data"+fielSep;
							log.info("path==========" + path);
							excel.write(path+busiInfo.get(j).getBusinessId()+fielSep+queueInfos.get(k).getKey()+fielSep+fileDataPath+"话务综合统计报表"+fielSep+"月报表"+fielSep
									+month.substring(0,4)+"年"+fielSep,fielSep+month
									+ ".xls");
						}
					}
			}

		return true;
	}

	//创建起止时间
	public String createTimeStr(String date) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date newDate = format.parse(date);
		newDate.setMinutes(newDate.getMinutes()+30);
		date = format.format(newDate);
		return date;
	}

	//创建起止时间
	public String createDayStr(String date) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date newDate = format.parse(date);
		newDate.setDate(newDate.getDate()+1);
		date = format.format(newDate);
		return date;
	}

	private static String converDayOfMonth(String monthStr) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date newDate = format.parse(monthStr);
		int day = newDate.getDate();
		return String.valueOf(day);
	}

	/**
	 * day格式 2013-1-31
	 * 返回2013-02-01
	 */
	private static String nextDayStr(String day) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date newDate = format.parse(day);
		Calendar calender = Calendar.getInstance();
		calender.setTime(newDate);
		calender.add(calender.DATE, +1);
		Date date = calender.getTime();
		return format.format(date);
	}
	/**
	 * day格式 2013-1-31
	 * 返回2013-01-31
	 */
	private static String currentDateStr(String day) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date newDate = format.parse(day);
		return format.format(newDate);
	}
	/**
	 * day格式 2013-1-31
	 * 返回2013-01
	 */
	private static String currentMonthStr(String month) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Date newDate = format.parse(month);
		return format.format(newDate).substring(0, 7);
	}

	private static String nextMonthStr(String month) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Date newDate = format.parse(month);
		Calendar calender = Calendar.getInstance();
		calender.setTime(newDate);
		calender.add(calender.MONTH, +1);
		Date date = calender.getTime();
		return format.format(date);
	}

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Date newDate = format.parse("2012-1");

		Calendar calender = Calendar.getInstance();
		calender.setTime(newDate);
		int monthLength = calender.getActualMaximum(Calendar.DAY_OF_MONTH);

		System.out.println(monthLength);
	}

}
