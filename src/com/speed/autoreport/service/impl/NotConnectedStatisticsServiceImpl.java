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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.stereotype.Service;

import com.speed.autoreport.dao.BusinessInfoDao;
import com.speed.autoreport.dao.NotConnectedStatisticsDao;
import com.speed.autoreport.service.NotConnectedStatisticsService;
import com.speed.autoreport.util.DateUtil;
import com.speed.autoreport.util.MSExcelUtil;
import com.speed.autoreport.vo.BusinessInfo;
import com.speed.autoreport.vo.NotConnectedStatisticsObj;
import com.speed.hfreport.service.HfReportService;
import com.speed.management.quartz.dao.SchedulerManageDao;
import com.speed.management.quartz.util.SpringUtils;
import com.speed.management.quartz.vo.JobDataTime;
import com.speed.util.KeyValueVo;

/**
 * @author gengxj
 *
 */
@Service("notConnectedStatisticsService")
public class NotConnectedStatisticsServiceImpl implements NotConnectedStatisticsService {
	
	private static Logger log = Logger.getLogger("InfoFile");
	
	@Resource(name="notConnectedStatisticsDao")
	private NotConnectedStatisticsDao notConnectedStatisticsDao;
	
	@Resource(name="businessInfoDao")
	private BusinessInfoDao businessInfoDao;
	@Resource(name="hfReportService")
	private HfReportService hfReportService;
	
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
			queueInfos=hfReportService.queryQueueInfo(Integer.parseInt(busiInfo.get(j).getBusinessId()) );
			if(queueInfos==null){queueInfos=new ArrayList<KeyValueVo>();}
			KeyValueVo all=new KeyValueVo();
			all.setKey("all");
			queueInfos.add(all);
			for(int i=0;i<queueInfos.size();i++){
					for (JobDataTime jobDataTime : jobDataTimes) {
						Calendar today =DateUtil. getToday(day);
						//初始化昨天 yestoday : 昨天的00:00:00
						Calendar yestoday = DateUtil.getYestoday(day);
						String fileDataPath=jobDataTime.getJobStartTime().replace(":", "_")+"_"+jobDataTime.getJobEndTime().replace(":", "_")+File.separator;
						DateUtil.dealWithJobCalendarDay(today, yestoday, jobDataTime);
//						day = currentDateStr(day);
//						String date = day;
//						String startTime = date+" 00:00:00";
//						String endTime = date+" 23:59:59";
						String startTime =format.format(yestoday.getTime()) ;
						String endTime = format.format(today.getTime());
						
						//创建标题
						MSExcelUtil excel = createWorkBook();
						HSSFSheet sheet = excel.getSheet(0);
						sheet.createFreezePane(0, 1);//冻结住第一行
						int rowIndex = 1;
						NotConnectedStatisticsObj notConnectedStatisticsO = new NotConnectedStatisticsObj();
						notConnectedStatisticsO.setBeginTime(startTime);
						notConnectedStatisticsO.setEndTime(endTime);
						
						System.out.println(""+busiInfo.get(j).getBusinessId()
								+","+notConnectedStatisticsO.getBeginTime()
								+","+notConnectedStatisticsO.getEndTime());
						notConnectedStatisticsO.setBussinessId(busiInfo.get(j).getBusinessId());
						notConnectedStatisticsO.setBussinessName(busiInfo.get(j).getBusinessName());
						notConnectedStatisticsO.setQueueId(queueInfos.get(i).getKey());
						//invoke dao
						List<NotConnectedStatisticsObj> resultO =  notConnectedStatisticsDao.queryNoConnectedStatisticsData(notConnectedStatisticsO);
						setResultO(resultO);
						for (int n = 0; n < resultO.size(); n++) {
							writeData(excel,sheet,rowIndex,resultO.get(n),0);
							rowIndex++;
						}		
						
						//生成文件
						path = System.getProperty("webapp.root") + fielSep + "autoreport"+fielSep+"data"+fielSep;
						log.info("path==========" + path);
						System.out.println("path ===========" + path); 
						excel.write(path+busiInfo.get(j).getBusinessId()+fielSep+queueInfos.get(i).getKey()+fielSep+fileDataPath+"来电未接记录报表"+fielSep+"日报表"+fielSep
								+day.substring(0, 4)+"年"+fielSep+day.substring(5, 7)+"月"+fielSep,
								fielSep+day+".xls");
						}				
			}
		}
		return true;
	}
	
	public List setResultO(List resList) throws Exception {
		if (resList != null && resList.size() > 0) {
			for (int i = 0; i < resList.size(); i++) {
				String tempString = "";
				NotConnectedStatisticsObj obj = (NotConnectedStatisticsObj)resList.get(i);
				List tempList = notConnectedStatisticsDao.queryExampleList(obj);
				Map<String,String> codeTime = new HashMap<String, String>();
				if (tempList != null && tempList.size() > 0) {
					for (int j = 0; j < tempList.size(); j++) {
						NotConnectedStatisticsObj o  = (NotConnectedStatisticsObj)tempList.get(j);
						tempString = tempString + o.getSampleId();
						if (j != tempList.size() - 1) {
							tempString = tempString + ",";
						}
						codeTime.put(o.getSampleId(), o.getSampleTime());
					}
				}
				if (tempString.indexOf(",1014") != -1) {
					obj.setTelType("坐席挂断"); // 0坐席挂断
				}
				if(tempString.contains("1002")){
					obj.setTelType("呼叫排队超时");
				}else if(tempString.contains("1016")){
					obj.setTelType("到坐席超时未接");
				}else if(tempString.contains("1003") && tempString.contains("1013")){
					obj.setTelType("到坐席用户挂断");
				}else if(!tempString.contains("1003") && tempString.contains("1013")){
					obj.setTelType("排队用户挂断");
					obj.setQueueTime(subStringDate(codeTime.get("1013"), codeTime.get("1001")));
				}else{
					//obj.setTelType("排队用户挂断");
					obj.setQueueTime("网络异常");
				}
				
			/*	if (tempString.indexOf(",1014") != -1) {
					obj.setTelType("坐席挂断"); // 0坐席挂断
				}
				if (tempString.indexOf("1003,1016") != -1) {
					obj.setTelType("到坐席超时未接"); // 3到坐席超时未接
				}
				if (tempString.indexOf("1004,1016") != -1) {
					obj.setTelType("到坐席超时未接"); // 3到坐席超时未接
				}
				if (tempString.indexOf("1005,1016") != -1) {
					obj.setTelType("到坐席超时未接"); // 3到坐席超时未接
				}
				if (tempString.indexOf("1006,1016") != -1) {
					obj.setTelType("到坐席超时未接"); // 3到坐席超时未接
				}
				
				if (tempString.indexOf("1003,1013") != -1 || tempString.equals("1003")) {
					obj.setTelType("到坐席用户挂断"); // 2到坐席用户挂断
				}
				if (tempString.indexOf("1004,1013") != -1 || tempString.equals("1004")) {
					obj.setTelType("到坐席用户挂断"); // 2到坐席用户挂断
				}
				if (tempString.indexOf("1005,1013") != -1 || tempString.equals("1005")) {
					obj.setTelType("到坐席用户挂断"); // 2到坐席用户挂断
				}
				if (tempString.indexOf("1006,1013") != -1 || tempString.equals("1006")) {
					obj.setTelType("到坐席用户挂断"); // 2到坐席用户挂断
				}
				
				if (tempString.indexOf("1001,1013") != -1 || tempString.equals("1001")) {
					obj.setTelType("排队用户挂断"); // 1排队用户挂断
					obj.setQueueTime(subStringDate(codeTime.get("1013"), codeTime.get("1001")));
					if(tempString.equals("1001")){
						obj.setQueueTime("网络异常");
					}
				}
				if (tempString.indexOf("1002,1013") != -1 || tempString.equals("1002")) {
					obj.setTelType("排队用户挂断"); // 1排队用户挂断
					obj.setQueueTime(subStringDate(codeTime.get("1013"), codeTime.get("1002")));
					if(tempString.equals("1002")){
						obj.setQueueTime("网络异常");
					}
				}*/
			}
		}
		return null;
	}
	private String subStringDate(String begin, String end){
		
		if(begin==null||"".equals(begin)||end==null||"".equals(end)){
			return "网络异常";
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date beginDate = format.parse(begin);
			Date endDate = format.parse(end);
			long r =  (beginDate.getTime()-endDate.getTime())/1000;
			long hour = r/(3600)+r%(24*3600)/3600;
			long min = r%3600/60;
			long sec = r%60;
			
			return (hour+":"+min+":"+sec);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return "-";
	}
	public MSExcelUtil createWorkBook() throws IOException {
		MSExcelUtil excel = new MSExcelUtil();
		HSSFSheet sheet = excel.addSheet("来电未接记录统计表格");
		excel.writeToSheetWithStyle(sheet, 0, 0,"业务组");
		excel.writeToSheetWithStyle(sheet, 0, 1,"来电时间");
		excel.writeToSheetWithStyle(sheet, 0, 2,"来电号码");
		excel.writeToSheetWithStyle(sheet, 0, 3,"挂断类型");
		excel.writeToSheetWithStyle(sheet, 0, 4,"工号");
		excel.writeToSheetWithStyle(sheet, 0, 5,"排队时长");
		return excel;
	}
	
	
	private void writeData(MSExcelUtil excel,HSSFSheet sheet,int rowNum,NotConnectedStatisticsObj data,int type)throws Exception{
		excel.writeToSheet(sheet, rowNum, 0,data.getBussinessName());
		excel.writeToSheet(sheet, rowNum, 1,data.getTelTime());
		excel.writeToSheet(sheet, rowNum, 2,data.getTelPhone());
		excel.writeToSheet(sheet, rowNum, 3,data.getTelType());
		excel.writeToSheet(sheet, rowNum, 4,data.getAgentId());
		excel.writeToSheet(sheet, rowNum, 5,data.getQueueTime());
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
		return (String.valueOf(day));
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
	 * 返回2013-01-31
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
