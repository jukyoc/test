package com.speed.management.quartz.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.speed.base.vo.PageBean;
import com.speed.management.quartz.service.SchedulerManageService;
import com.speed.management.quartz.vo.JobDataTime;
import com.speed.management.quartz.vo.SchedulerJob;
import com.speed.ocss.man.vo.AgentInfoVObj;

@Controller
@RequestMapping("/autoreport")
public class SchedulerManageAction {
	
	@Resource(name="schedulerManageService")
	private SchedulerManageService schedulerManageService;
	
	/**
	 * 任务管理页面
	 */
	@RequestMapping(value="/schedulerManageAction_toConfigPage.do")
	public String toConfigPage(HttpServletRequest request){
/*		AgentInfoVObj agent = new AgentInfoVObj();
		agent.setCsId("6542");
		agent.setCsName("刘hl");
		request.getSession().setAttribute("loginAgent", agent);
*/		
		return "quartz/configPage";
	}
	/**
	 * 新增任务页面
	 */
	@RequestMapping(value="/schedulerManageAction_addNewJobPage.do")
	public String addNewJobPage(HttpServletRequest request){
		
		return "quartz/addNewJobPage";
	}
	/**
	 * 查询任务运行日志页面
	 */
	@RequestMapping(value="/schedulerManageAction_queryJobRunLogPage.do")
	public String queryJobRunLogPage(HttpServletRequest request){
		
		return "quartz/queryJobRunLogPage";
	}
	/**
	 * 新增任务
	 */
	@RequestMapping(value="/schedulerManageAction_addJob.do")
	@ResponseBody
	public String addJob(HttpServletRequest request,HttpServletResponse response){
		String params=request.getParameter("params");
		
		JSONObject arg0 = JSONObject.fromObject(params);
		SchedulerJob obj = (SchedulerJob)JSONObject.toBean(arg0,SchedulerJob.class);
		
		AgentInfoVObj agent = (AgentInfoVObj) WebUtils.getSessionAttribute(request, "loginAgent");
		String result = "false";
		try {
			result = schedulerManageService.addJob(obj,agent);
		} catch (Exception e) {
			e.printStackTrace();
			result = "false";
		}
		
		return result;
	}
	/**
	 * 新增任务时 校验信息
	 */
	@RequestMapping(value="/schedulerManageAction_checkJobInfo.do")
	@ResponseBody
	public String checkJobInfo(HttpServletRequest request,HttpServletResponse response){
		String params=request.getParameter("params");
		
		JSONObject arg0 = JSONObject.fromObject(params);
		SchedulerJob obj = (SchedulerJob)JSONObject.toBean(arg0,SchedulerJob.class);
		
		AgentInfoVObj agent = (AgentInfoVObj) WebUtils.getSessionAttribute(request, "loginAgent");
		String result = "";
		try {
			result = schedulerManageService.checkJobInfo(obj,agent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	/**
	 *查询任务
	 */
	@RequestMapping(value="/schedulerManageAction_queryJob.do")
	@ResponseBody
	public Map queryJob(HttpServletRequest request,HttpServletResponse response){
		String params=request.getParameter("params");
		int page = Integer.parseInt(request.getParameter("page"));
		int limit = Integer.parseInt(request.getParameter("rows"));
		
		JSONObject arg0 = JSONObject.fromObject(params);
		SchedulerJob obj = (SchedulerJob)JSONObject.toBean(arg0,SchedulerJob.class);

		PageBean pageBean = null;
		try {
			pageBean = schedulerManageService.queryJobForPage(obj,page, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List list = null;
		int total;
		if (pageBean != null && pageBean.getCountResults()!=0){
			list = pageBean.getDataList();
			total = pageBean.getCountResults();
		} else {
			list = new ArrayList();
			total = 0;
		}
		Map map = new HashMap();
		map.put("list", list);
		map.put("total", total);
		
		return map;
	}
	
	/**
	 * 暂停任务
	 */
	@RequestMapping(value="/schedulerManageAction_pauseJob.do")
	@ResponseBody
	public String pauseJob(HttpServletRequest request,HttpServletResponse response){
		String params=request.getParameter("params");
		
		JSONObject arg0 = JSONObject.fromObject(params);
		SchedulerJob obj = (SchedulerJob)JSONObject.toBean(arg0,SchedulerJob.class);
		
		AgentInfoVObj agent = (AgentInfoVObj) WebUtils.getSessionAttribute(request, "loginAgent");
		
		String result;
		try {
			result = schedulerManageService.pauseJob(obj,agent);
		} catch (Exception e) {
			result = "false";
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 恢复任务
	 */
	@RequestMapping(value="/schedulerManageAction_resumeJob.do")
	@ResponseBody
	public String resumeJob(HttpServletRequest request,HttpServletResponse response){
		String params=request.getParameter("params");
		
		JSONObject arg0 = JSONObject.fromObject(params);
		SchedulerJob obj = (SchedulerJob)JSONObject.toBean(arg0,SchedulerJob.class);
		
		AgentInfoVObj agent = (AgentInfoVObj) WebUtils.getSessionAttribute(request, "loginAgent");
		
		String result;
		try {
			result = schedulerManageService.resumeJob(obj,agent);
		} catch (Exception e) {
			result = "false";
			e.printStackTrace();
		}
		
		return result;
	}
	/**
	 * 立即执行任务
	 */
	@RequestMapping(value="/schedulerManageAction_forceRunJob.do")
	@ResponseBody
	public String forceRunJob(HttpServletRequest request,HttpServletResponse response){
		String params=request.getParameter("params");
		
		JSONObject arg0 = JSONObject.fromObject(params);
		SchedulerJob obj = (SchedulerJob)JSONObject.toBean(arg0,SchedulerJob.class);
		
		AgentInfoVObj agent = (AgentInfoVObj) WebUtils.getSessionAttribute(request, "loginAgent");
		
		String result;
		try {
			result = schedulerManageService.forceRunJob(obj,agent);
		} catch (Exception e) {
			result = "false";
			e.printStackTrace();
		}
		
		return result;
	}
	/**
	 *删除任务
	 */
	@RequestMapping(value="/schedulerManageAction_deleteJob.do")
	@ResponseBody
	public String deleteJob(HttpServletRequest request,HttpServletResponse response){
		String params=request.getParameter("params");
		
		JSONObject arg0 = JSONObject.fromObject(params);
		SchedulerJob obj = (SchedulerJob)JSONObject.toBean(arg0,SchedulerJob.class);

		AgentInfoVObj agent = (AgentInfoVObj) WebUtils.getSessionAttribute(request, "loginAgent");
		String result = null;
		try {
			schedulerManageService.pauseJob(obj,agent);
		} catch (Exception e) {
			e.printStackTrace();
			result = "false";
		}
		
		return result;
	}
	/**
	 *查询任务操作日志
	 */
	@RequestMapping(value="/schedulerManageAction_queryJobLog.do")
	@ResponseBody
	public Map queryJobLog(HttpServletRequest request,HttpServletResponse response){
		String params=request.getParameter("params");
		int page = Integer.parseInt(request.getParameter("page"));
		int limit = Integer.parseInt(request.getParameter("rows"));
		
		JSONObject arg0 = JSONObject.fromObject(params);
		SchedulerJob obj = (SchedulerJob)JSONObject.toBean(arg0,SchedulerJob.class);
		
		AgentInfoVObj agent = (AgentInfoVObj) WebUtils.getSessionAttribute(request, "loginAgent");
		PageBean pageBean = null;
		try {
			pageBean = schedulerManageService.queryJobLog(obj,page, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List list = null;
		int total;
		if (pageBean != null) {
			list = pageBean.getDataList();
			total = pageBean.getCountResults();
		} else {
			total = 0;
		}
		Map map = new HashMap();
		map.put("list", list);
		map.put("total", total);
		
		return map;
	}
	
	/**
	 *查询任务执行日志
	 */
	@RequestMapping(value="/schedulerManageAction_queryJobRunningLog.do")
	@ResponseBody
	public Map queryJobRunningLog(HttpServletRequest request,HttpServletResponse response){
		String params=request.getParameter("params");
		int page = Integer.parseInt(request.getParameter("page"));
		int limit = Integer.parseInt(request.getParameter("rows"));
		
		JSONObject arg0 = JSONObject.fromObject(params);
		SchedulerJob obj = (SchedulerJob)JSONObject.toBean(arg0,SchedulerJob.class);
		
		AgentInfoVObj agent = (AgentInfoVObj) WebUtils.getSessionAttribute(request, "loginAgent");
		PageBean pageBean = null;
		try {
			pageBean = schedulerManageService.queryJobRunningLog(obj,page, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List list = null;
		int total;
		if (pageBean != null) {
			list = pageBean.getDataList();
			total = pageBean.getCountResults();
		} else {
			total = 0;
		}
		Map map = new HashMap();
		map.put("list", list);
		map.put("total", total);
		
		return map;
	}
	@RequestMapping(value="/schedulerManageAction_queryJobDataTimes.do")
	@ResponseBody
	public Map queryJobDataTimes(JobDataTime jobDataTime){
		Map map=new HashMap();
		try {
			List<JobDataTime> list=	schedulerManageService.queryJobDataTime(jobDataTime);
			map.put("list", list);
			map.put("total", list.size());
			map.put("flag", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	@RequestMapping(value="/schedulerManageAction_addJobDataTimes.do")
	@ResponseBody
	public Map addJobDataTimes(@RequestBody JobDataTime jobDataTime){
		Map map=new HashMap();
		try {
			schedulerManageService.insertJobDataTime(jobDataTime);
			map.put("flag", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	@RequestMapping(value="/schedulerManageAction_deleteJobDataTimes.do")
	@ResponseBody
	public Map deleteJobDataTimes(@RequestBody JobDataTime jobDataTime){
		Map map=new HashMap();
		try {
			schedulerManageService.deleteJobDataTime(jobDataTime);
			map.put("flag", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
}
