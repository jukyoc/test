/**
 * 
 */
package com.speed.hfreport.action;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.speed.hfreport.service.HfReportService;
import com.speed.hfreport.vo.HfReportVo;
import com.speed.management.quartz.service.SchedulerManageService;
import com.speed.management.quartz.vo.JobDataTime;
import com.speed.util.FileDate;
import com.speed.util.KeyValueVo;
import com.speed.util.columnVo;

/**
 * @author wangjian
 *
 */
@Controller
@RequestMapping("/autoreport")
public class HfReportAction {

	@Resource(name="hfReportService")
	private HfReportService hfReportService;
	@Resource(name="schedulerManageService")
	private SchedulerManageService schedulerManageService;
	private static Logger log = Logger.getLogger("InfoFile");
	
	private static ArrayList<String> fileList = new ArrayList<String>();
	
	/*
	 * 跳转到话务报表页面
	 */
	@RequestMapping(value="/HfReportAction_queryPage.do")
	public String toQueryPage(HttpServletRequest request,HttpServletResponse response) {
		log.info("跳到话务报表页面");
		return "hfreport/hfReport";
	}
	@RequestMapping(value="/HfReportAction_queryBusinessPage.do")
	public String toQueryBusinessPage(HttpServletRequest request,HttpServletResponse response) {
		log.info("跳到话务报表和业务无关页面页面");
		return "hfreport/hfReportBusiness";
	}
	
	/*
	 * 查询业务线名称
	 */
	@RequestMapping(value="/HfReportAction_queryBusinessName.do")
	@ResponseBody
	public List<KeyValueVo> queryGroupName(HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("获取业务线名称");
		List<KeyValueVo> keyValue=hfReportService.queryBusinessName();
		return keyValue;
	}
	
	/*
	 * 查询报表类型
	 */
	@RequestMapping(value="/HfReportAction_queryType.do")
	@ResponseBody
	public List<HfReportVo> queryType(HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("查询报表类型");
		String business=request.getParameter("businessName");
		String queueName=request.getParameter("queueName");
		String startTime=request.getParameter("startTime").replace(" ", ":").replace(":", "_");
		String endTime=request.getParameter("endTime").replace(" ", ":").replace(":", "_");
		String startEnd=startTime+"_"+endTime;
		List<HfReportVo> fileNames = new ArrayList<HfReportVo>();
//		URL path = Thread.currentThread().getContextClassLoader().getResource("data");
		String path = request.getSession().getServletContext().getRealPath("");//得到WebContent的路径
		if(StringUtils.isBlank(queueName)){
			queueName="";
		}else{
			queueName="/"+queueName;
		}
		String filePath = path+"/autoreport/data/"+business+queueName+"/"+startEnd;
		File root = new File(filePath);
		String[] files = root.list();
		if(files!=null){
			for(int i=0;i<files.length;i++){
				HfReportVo hfReportVo=new HfReportVo();
				File file = new File(files[i]);
				hfReportVo.setType(file.getName());
				fileList.add(file.getAbsolutePath());
				fileNames.add(hfReportVo);
			}
		}
		return fileNames;
	}
	
	/*
	 * 查询日报表或者月报表
	 */
	@RequestMapping(value="/HfReportAction_queryDate.do")
	@ResponseBody
	public List<HfReportVo> queryDate(HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("查询日报表或者月报表");
		String business=request.getParameter("businessName");
		String queueName=request.getParameter("queueName");
		String startTime=request.getParameter("startTime").replace(" ", ":").replace(":", "_");
		String endTime=request.getParameter("endTime").replace(" ", ":").replace(":", "_");
		String startEnd=startTime+"_"+endTime;
		String type=request.getParameter("type");
		List<HfReportVo> fileNames = new ArrayList<HfReportVo>();
		String path = request.getSession().getServletContext().getRealPath("");//得到WebContent的路径
		if(StringUtils.isBlank(queueName)){
			queueName="";
		}else{
			queueName="/"+queueName;
		}
		String filePath = path+"/autoreport/data/"+business+queueName+"/"+startEnd+"/"+type;
		File root = new File(filePath);
		String[] files = root.list();
		if(files!=null){
			for(int i=0;i<files.length;i++){
				HfReportVo hfReportVo=new HfReportVo();
				File file = new File(files[i]);
				hfReportVo.setDate(file.getName());
				fileList.add(file.getAbsolutePath());
				fileNames.add(hfReportVo);
			}
		}
		return fileNames;
	}
	
	/*
	 * 查询报表年份
	 */
	@RequestMapping(value="/HfReportAction_queryYear.do")
	@ResponseBody
	public List<HfReportVo> queryYear(HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("查询报表年份");
		String business=request.getParameter("businessName");
		String queueName=request.getParameter("queueName");
		String startTime=request.getParameter("startTime").replace(" ", ":").replace(":", "_");
		String endTime=request.getParameter("endTime").replace(" ", ":").replace(":", "_");
		String startEnd=startTime+"_"+endTime;
		String type=request.getParameter("type");
		String date=request.getParameter("date");
		List<HfReportVo> fileNames = new ArrayList<HfReportVo>();
		String path = request.getSession().getServletContext().getRealPath("");//得到WebContent的路径
		if(StringUtils.isBlank(queueName)){
			queueName="";
		}else{
			queueName="/"+queueName;
		}
		String filePath = path+"/autoreport/data/"+business+queueName+"/"+startEnd+"/"+type+"/"+date;
		File root = new File(filePath);
		String[] files = root.list();
		if(files!=null){
			for(int i=0;i<files.length;i++){
				HfReportVo hfReportVo=new HfReportVo();
				File file = new File(files[i]);
				hfReportVo.setYear(file.getName());
				fileList.add(file.getAbsolutePath());
				fileNames.add(hfReportVo);
			}
		}
		return fileNames;
	}
	
	/*
	 * 查询报表月份
	 */
	@RequestMapping(value="/HfReportAction_queryMonth.do")
	@ResponseBody
	public List<HfReportVo> queryMonth(HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("查询报表月份");
		String business=request.getParameter("businessName");
		String queueName=request.getParameter("queueName");
		String startTime=request.getParameter("startTime").replace(" ", ":").replace(":", "_");
		String endTime=request.getParameter("endTime").replace(" ", ":").replace(":", "_");
		String startEnd=startTime+"_"+endTime;
		String type=request.getParameter("type");
		String date=request.getParameter("date");
		String year=request.getParameter("year");
		List<HfReportVo> fileNames = new ArrayList<HfReportVo>();
		String path = request.getSession().getServletContext().getRealPath("");//得到WebContent的路径
		if(StringUtils.isBlank(queueName)){
			queueName="";
		}else{
			queueName="/"+queueName;
		}
		String filePath = path+"/autoreport/data/"+business+queueName+"/"+startEnd+"/"+type+"/"+date+"/"+year;
		File root = new File(filePath);
		String[] files = root.list();
		if(files!=null){
			for(int i=0;i<files.length;i++){
				HfReportVo hfReportVo=new HfReportVo();
				File file = new File(files[i]);
				hfReportVo.setMonth(file.getName());
				fileList.add(file.getAbsolutePath());
				fileNames.add(hfReportVo);
			}
		}
		return fileNames;
	}
	
	/*
	 * 查询报表信息
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/HfReportAction_queryReport.do")
//	@ResponseBody
	public String queryReport(HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("查询报表信息");
		String business=request.getParameter("businessName");
		String queueName=request.getParameter("queueName");
		String startTime=request.getParameter("startTime").replace(" ", ":").replace(":", "_");
		String endTime=request.getParameter("endTime").replace(" ", ":").replace(":", "_");
		String startEnd=startTime+"_"+endTime;
		String type=request.getParameter("type");
		String date=request.getParameter("date");
		String year=request.getParameter("year");
		String month=request.getParameter("month");
		String filePath = new String();
		String path = request.getSession().getServletContext().getRealPath("");//得到WebContent的路径
		if(StringUtils.isBlank(queueName)){
			queueName="";
		}else{
			queueName="/"+queueName;
		}
		if(date.equals("日报表")){
			filePath = path+"/autoreport/data/"+business+queueName+"/"+startEnd+"/"+type+"/"+date+"/"+year+"/"+month;
		}else{
			filePath = path+"/autoreport/data/"+business+queueName+"/"+startEnd+"/"+type+"/"+date+"/"+year;
		}
		log.info(filePath);
		List<String> fileName = new ArrayList<String>();
		File root = new File(filePath);
		if(root.exists()){
		File[] files = root.listFiles();
		Arrays.sort(files,new FileDate());
			if (files != null) {
				for (int i = 1; i < (files.length + 1); i++) {
					File file = files[i - 1];
					fileName.add(i - 1, file.getName());
				}
			}
		}
		response.setContentType("text/html;charset=UTF-8");
		String json = columnVo.createColumnVoJsonString(fileName,10);
		response.getWriter().write(json);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	@RequestMapping(value="/HfReportAction_queryQueueName.do")
	@ResponseBody
	public Map queryQueueName(Integer businessId){
		Map map=new HashMap();
		try {
			List<KeyValueVo> list=	hfReportService.queryQueueInfo(  businessId);
			map.put("list", list);
			map.put("flag", 1);
		} catch (Exception e) {
			log.error("queryQueueName_error",e);
		}
		return map;
	}
	@RequestMapping(value="/HfReportAction_queryJobDataTimesDealTimes.do")
	@ResponseBody
	public Map queryJobDataTimesDealTimes(){
		Map map=new HashMap();
		try {
			List<JobDataTime> jobDataTimes= schedulerManageService.queryJobDataTimeDealTimes();
			List<String> startTimes=new ArrayList<String>();
			startTimes.add("01 00:00:00");
			List<String> endTimes=new ArrayList<String>();
			endTimes.add("01 00:00:00");
			for (JobDataTime jobDataTime : jobDataTimes) {
				if(!startTimes.contains(jobDataTime.getJobStartTime())){
					startTimes.add(jobDataTime.getJobStartTime());
				}
				if(!endTimes.contains(jobDataTime.getJobEndTime())){
					endTimes.add(jobDataTime.getJobEndTime());
				}
			}
			Collections.sort(startTimes);
			Collections.sort(endTimes);
			map.put("startTimes", startTimes);
			map.put("endTimes", endTimes);
			map.put("flag", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	public void setHfReportService(HfReportService hfReportService) {
		this.hfReportService = hfReportService;
	}

	public HfReportService getHfReportService() {
		return hfReportService;
	}
}
