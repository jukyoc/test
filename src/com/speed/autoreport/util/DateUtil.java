/**
 * 
 */
package com.speed.autoreport.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.speed.management.quartz.vo.JobDataTime;

/**
 * @author liuhl
 *
 */
public class DateUtil {
	private static SimpleDateFormat format = new SimpleDateFormat(
		"yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 取得当天日期字符串，形如 2013-03-29
	 * @return
	 */
	public static String getCurrDateStr() {
		Date curDate = new Date();
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
		return d.format(curDate);
	}
	
	/**
	 * 取得前一天日期字符串，形如 2013-03-28
	 * @return
	 */
	public static String getYestodayStr() {
		Calendar calender = Calendar.getInstance();
		calender.add(calender.DATE, -1);
		Date date = calender.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	/**
	 * 取得前一天日期所在年，形如 2013
	 * @return
	 */
	public static String getYestodayYearStr() {
		String dayStr = getYestodayStr();
		return dayStr.split("-")[0];
	}
	/**
	 * 取得前一天日期所在月，形如 01
	 * @return
	 */
	public static String getYestodayMonthStr() {
		String dayStr = getYestodayStr();
		return dayStr.split("-")[1];
	}/**
	 * 取得前一天日期，形如 01
	 * @return
	 */
	public static String getYestodayDayStr() {
		String dayStr = getYestodayStr();
		return dayStr.split("-")[2];
	}
	/**
	 * 取得上个月日期字符串，形如 2013-02
	 * @return
	 */
	public static String getLastMonthStr() {
		Calendar calender = Calendar.getInstance();
		calender.add(calender.MONTH, -1);
		Date date = calender.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		return format.format(date);
	}
	/**
	 * 取得本个月日期字符串，形如 2013-02
	 * @return
	 */
	public static String getCurrMonthStr() {
		Calendar calender = Calendar.getInstance();
		calender.add(calender.MONTH,0);
		Date date = calender.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		return format.format(date);
	}
	public static void main(String[] args) throws ParseException {
		Calendar calender = Calendar.getInstance();
		calender.add(calender.MONTH, -2);
		Date date = calender.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		
		System.out.println(getYestodayStr());
		System.out.println(getYestodayYearStr());
		System.out.println(getYestodayMonthStr());
	}
	
	public static String getTest() {
		
		String da = getYestodayStr();
		Calendar calender = Calendar.getInstance();
		calender.add(calender.MONTH, -1);
		Date date = calender.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		return format.format(date);
	}
	public static void dealWithJobDataTimesDay(List<JobDataTime> jobDataTimes){
		JobDataTime jt=null;
		if(jobDataTimes==null||jobDataTimes.size()==0){
			jt=new JobDataTime();
			jt.setJobStartTime("00:00:00");
			jt.setJobEndTime("00:00:00");
			jobDataTimes.add(jt);
		}else{
			for(int i=0;i<jobDataTimes.size();i++){
				jt=jobDataTimes.get(i);
				jt.setJobStartTime(jt.getJobStartTime().substring(3));
				jt.setJobEndTime(jt.getJobEndTime().substring(3));
			}
		}
		
	}
	public static void dealWithJobDataTimesMonth(List<JobDataTime> jobDataTimes ){
		JobDataTime jt=null;
		if(jobDataTimes==null||jobDataTimes.size()==0){
			jt=new JobDataTime();
			jt.setJobStartTime("01 00:00:00");
			jt.setJobEndTime( "01 00:00:00");
			jobDataTimes.add(jt);
		}
		
	}
	public static void dealWithJobCalendarDay(Calendar today ,Calendar yestoday ,JobDataTime jobDataTime){
		//day 19
		String start=jobDataTime.getJobStartTime();
		String end=jobDataTime.getJobEndTime();
		Integer hit = null;
		Integer mit = null;
		Integer sst = null;
		
		Integer hiy;
		Integer miy;
		Integer ssy;
		hiy=Integer.parseInt(start.substring(0, 2));
		miy=Integer.parseInt(start.substring(3, 5));
		ssy=Integer.parseInt(start.substring(6));
		
		hit=Integer.parseInt(end.substring(0, 2));
		mit=Integer.parseInt(end.substring(3, 5));
		sst=Integer.parseInt(end.substring(6));
		//当天数据
		//原来时间
		//19 00:00:00 20 00:00:00
		//19 01:00:00 - 19 02:00:00
		if (start.compareTo(end) < 0) {
			today.add(Calendar.DAY_OF_MONTH, -1);
		} else if (start.compareTo(end) > 0) {
			// 03:00 -02:00
			// 18 03:00 -19 02:00
			today.add(Calendar.DAY_OF_MONTH, -1);
			yestoday.add(Calendar.DAY_OF_MONTH, -1);
		} else {
			// 00:00 不处理
			// 01:01
			// 18 01:01 19 01:01
			if (hit != 0||mit!=0 || sst != 0) {
				today.add(Calendar.DAY_OF_MONTH, -1);
				yestoday.add(Calendar.DAY_OF_MONTH, -1);
			}
		}
		Calendar c=yestoday;
		System.out.println(c.get(Calendar.MONTH)+"_"+c.get(Calendar.DAY_OF_MONTH)+"_"+c.get(Calendar.HOUR_OF_DAY)+"_"+c.get(Calendar.MINUTE)+"_"+c.get(Calendar.SECOND));
		c=today;
		System.out.println(c.get(Calendar.MONTH)+"_"+c.get(Calendar.DAY_OF_MONTH)+"_"+c.get(Calendar.HOUR_OF_DAY)+"_"+c.get(Calendar.MINUTE)+"_"+c.get(Calendar.SECOND));
		
		
		today.set(Calendar.HOUR, hit);
		today.set(Calendar.MINUTE, mit);
		today.set(Calendar.SECOND, sst);
		
		yestoday.set(Calendar.HOUR, hiy);
		yestoday.set(Calendar.MINUTE, miy);
		yestoday.set(Calendar.SECOND, ssy);
		 c=yestoday;
		 System.out.println(format.format(c.getTime()));
		System.out.println(c.get(Calendar.MONTH)+"_"+c.get(Calendar.DAY_OF_MONTH)+"_"+c.get(Calendar.HOUR_OF_DAY)+"_"+c.get(Calendar.MINUTE)+"_"+c.get(Calendar.SECOND));
		c=today;
		System.out.println(format.format(c.getTime()));
		System.out.println(c.get(Calendar.MONTH)+"_"+c.get(Calendar.DAY_OF_MONTH)+"_"+c.get(Calendar.HOUR_OF_DAY)+"_"+c.get(Calendar.MINUTE)+"_"+c.get(Calendar.SECOND));
	}
	public static void dealWithJobCalendarMonth(Calendar startTime ,Calendar endTime ,JobDataTime jobDataTime){
		//19 00:00:00
		String start=jobDataTime.getJobStartTime();
		String end=jobDataTime.getJobEndTime();
		Integer dayt=null;
		Integer hit = null;
		Integer mit = null;
		Integer sst = null;
		
		
		
		Integer dayy=null;
		Integer hiy;
		Integer miy;
		Integer ssy;
		dayy=Integer.parseInt(start.substring(0, 2));
		hiy=Integer.parseInt(start.substring(3, 5));
		miy=Integer.parseInt(start.substring(6, 8));
		ssy=Integer.parseInt(start.substring(9));
		
		dayt=Integer.parseInt(end.substring(0, 2));
		hit=Integer.parseInt(end.substring(3, 5));
		mit=Integer.parseInt(end.substring(6, 8));
		sst=Integer.parseInt(end.substring(9));
		
		//当天数据
		//原来时间
		//19 00:00:00 20 00:00:00
		//19 01:00:00 - 19 02:00:00
		if (start.compareTo(end) < 0) {
			endTime.add(Calendar.MONTH, -1);
		} else if (start.compareTo(end) > 0) {
			startTime.add(Calendar.MONTH, -1);
			endTime.add(Calendar.MONTH, -1);
		} else {
			if (dayt!=1||hit != 0||mit!=0 || sst != 0) {
				startTime.add(Calendar.MONTH, -1);
				endTime.add(Calendar.MONTH, -1);
			}
		}
		Calendar c=startTime;
		System.out.println(format.format(c.getTime()));
		System.out.println(c.get(Calendar.MONTH)+"_"+c.get(Calendar.DAY_OF_MONTH)+"_"+c.get(Calendar.HOUR_OF_DAY)+"_"+c.get(Calendar.MINUTE)+"_"+c.get(Calendar.SECOND));
		
		 c=endTime;
		System.out.println(format.format(c.getTime()));
		System.out.println(c.get(Calendar.MONTH)+"_"+c.get(Calendar.DAY_OF_MONTH)+"_"+c.get(Calendar.HOUR_OF_DAY)+"_"+c.get(Calendar.MINUTE)+"_"+c.get(Calendar.SECOND));
		
		endTime.set(Calendar.DAY_OF_MONTH, dayt);
		endTime.set(Calendar.HOUR_OF_DAY, hit);
		endTime.set(Calendar.MINUTE, mit);
		endTime.set(Calendar.SECOND, sst);
		
		startTime.set(Calendar.DAY_OF_MONTH, dayy);
		startTime.set(Calendar.HOUR_OF_DAY, hiy);
		startTime.set(Calendar.MINUTE, miy);
		startTime.set(Calendar.SECOND, ssy);
		c=startTime;
		System.out.println(format.format(c.getTime()));
		System.out.println(c.get(Calendar.MONTH)+"_"+c.get(Calendar.DAY_OF_MONTH)+"_"+c.get(Calendar.HOUR_OF_DAY)+"_"+c.get(Calendar.MINUTE)+"_"+c.get(Calendar.SECOND));
		 c=endTime;
		 System.out.println(format.format(c.getTime()));
		System.out.println(c.get(Calendar.MONTH)+"_"+c.get(Calendar.DAY_OF_MONTH)+"_"+c.get(Calendar.HOUR_OF_DAY)+"_"+c.get(Calendar.MINUTE)+"_"+c.get(Calendar.SECOND));
		
	}
	public static Calendar getToday(String dayStr) throws ParseException {
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
	public static Calendar getYestoday(String dayStr) throws ParseException {
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
	public static Calendar dataStrToCalender(String str){
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar c=Calendar.getInstance();
		try {
			 c.setTime(df.parse(str))	;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c;
	}
}
