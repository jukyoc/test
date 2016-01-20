package com.speed.cscenter.css.statistics.quzrtz;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.quartz.JobExecutionContext;

import com.speed.autoreport.util.DateUtil;
import com.speed.cscenter.css.statistics.service.OrderQueryService;
import com.speed.cscenter.css.statistics.util.poi.xls.common.css.XlsStyle;
import com.speed.cscenter.css.statistics.util.poi.xls.common.vo.XlsFieldValue;
import com.speed.cscenter.css.statistics.util.poi.xls.common.vo.XlsTitle;
import com.speed.cscenter.css.statistics.util.poi.xls.xls.XlsCommon;
import com.speed.cscenter.css.statistics.vo.OrderQueryStatistics;
import com.speed.hfreport.service.HfReportService;
import com.speed.management.quartz.dao.SchedulerManageDao;
import com.speed.management.quartz.service.BaseJobTemplate;
import com.speed.management.quartz.util.SpringUtils;
import com.speed.management.quartz.vo.JobDataTime;
import com.speed.util.KeyValueVo;
/**
 * 工单查询排队超时20s接起文件生成
 */
public class StatisticsTime20sDaylyJob extends BaseJobTemplate {
	private static Logger log = Logger.getLogger("InfoFile");
	private final static String DAY_KEY = "day";
	private final static String JOBN_AME_KEY = "JOB_NAME";
	private SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	@Override
	public void executes(JobExecutionContext context) throws Exception {
		String jobName=context.getJobDetail().getJobDataMap().getString(JOBN_AME_KEY).trim();
		SchedulerManageDao schedulerManageDao= (SchedulerManageDao) SpringUtils.getContext().getBean("schedulerManageDao");
		JobDataTime jt=new JobDataTime();jt.setJobName(jobName);
		List<JobDataTime> jobDataTimes=schedulerManageDao.queryJobDataTime(jt);
		DateUtil.dealWithJobDataTimesDay(jobDataTimes);
		String day = "";
		Map map = context.getMergedJobDataMap();
		if (map.containsKey(DAY_KEY)) {
			day = String.valueOf(map.get(DAY_KEY));
			if (day.length() != 10) {
				log.error("工单查询日期错误:" + day);
				return;
			}
		} else {
			day = DateUtil.getYestodayStr();
		}
		
		long start = 0;
		long end = 0;
		try {
			start = System.currentTimeMillis();
			{
				for (JobDataTime jobDataTime : jobDataTimes) {
					Calendar today =DateUtil. getToday(day);
					//初始化昨天 yestoday : 昨天的00:00:00
					Calendar yestoday = DateUtil.getYestoday(day);
					String fileDataPath=jobDataTime.getJobStartTime().replace(":", "_")+"_"+jobDataTime.getJobEndTime().replace(":", "_")+File.separator;
					DateUtil.dealWithJobCalendarDay(today, yestoday, jobDataTime);
					OrderQueryStatistics param;
					{
						param = new OrderQueryStatistics();
//						param.setBeginTime(day + " 00:00:00");
//						param.setEndTime(day + " 23:59:59");
						param.setBeginTime(format.format(yestoday.getTime()));
						param.setEndTime(format.format(today.getTime()));
					}
					time20(day, param,fileDataPath);
				}
			}
			
			
			end = System.currentTimeMillis();
			log.info("创建_排队超时20s接起 文件成功(" + day + ")耗时" + (end - start));
		} catch (Exception e) {
			log.info("创建_排队超时20s接起 文件失败(" + day + ")耗时" + (end - start));
			e.printStackTrace();
		}
	}
//	public static void time20(String day) {
//		// String day = DateUtil.getYestodayStr();
//		OrderQueryStatistics param;
//		{
//			param = new OrderQueryStatistics();
//			param.setBeginTime(day + " 00:00:00");
//			param.setEndTime(day + " 23:59:59");
//		}
//		long start = 0;
//		long end = 0;
//		try {
//			start = System.currentTimeMillis();
//			new StatisticsTime20sDaylyJob().time20(day, param);
//			end = System.currentTimeMillis();
//			log.info("创建_排队超时20s接起 文件成功(" + day + ")耗时" + (end - start));
//		} catch (Exception e) {
//			log.info("创建_排队超时20s接起 文件失败(" + day + ")耗时" + (end - start));
//			e.printStackTrace();
//		}
//	}
	public void time20(String day, OrderQueryStatistics param,String fileDataPath) throws Exception {
		{
			try {
				OrderQueryService orderQueryService = SpringUtils.getContext().getBean(OrderQueryService.class);
				HfReportService hfReportService = SpringUtils.getContext().getBean(HfReportService.class);
				List<OrderQueryStatistics> list = orderQueryService.queryOrderStatisticsTime20List(param);
				List<OrderQueryStatistics> listForMap;
				Map<String, List<OrderQueryStatistics>> map = new HashMap<String, List<OrderQueryStatistics>>();
				List<KeyValueVo> keyValue = hfReportService.queryBusinessName();
				for (OrderQueryStatistics vo : list) {
					listForMap = map.get(vo.getBusinessId());
					if (listForMap == null) {
						listForMap = new ArrayList<OrderQueryStatistics>();
						map.put(vo.getBusinessId(), listForMap);
					}
					listForMap.add(vo);
				}
				for (OrderQueryStatistics vo : list) {
					listForMap = map.get(vo.getBusinessId()+"_"+vo.getQueueId());
					if (listForMap == null) {
						listForMap = new ArrayList<OrderQueryStatistics>();
						map.put(vo.getBusinessId()+"_"+vo.getQueueId(), listForMap);
					}
					listForMap.add(vo);
				}
				
				{
					//创建表格
					for (KeyValueVo kv : keyValue) {
						createXls(map.get(kv.getKey()), xlsPath(kv.getKey(),"all", day,fileDataPath), day);
					}
					List<KeyValueVo> queueInfos = null;
					for (KeyValueVo kv : keyValue) {
						queueInfos=hfReportService.queryQueueInfo(Integer.parseInt(kv.getKey()) );
						for (KeyValueVo queueKV : queueInfos) {
							createXls(map.get(kv.getKey()+"_"+queueKV.getKey()), xlsPath(kv.getKey(),queueKV.getKey(), day,fileDataPath), day);
						}
					}	
				}
			} catch (Exception e) {
				log.error(day + "天工单查询出错");
				e.printStackTrace();
				throw e;
			}
		}
	}
	public String xlsPath(String businessId,String queueId, String day,String timePath) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(day);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String basicPath = System.getProperty("webapp.root");
		String path = basicPath + File.separator + "autoreport" + File.separator + "data" + File.separator + businessId + File.separator+queueId+ File.separator +timePath+ "工单查询超时20s表格" + File.separator + "日报表" + File.separator + calendar.get(Calendar.YEAR) + "年" + File.separator + (calendar.get(Calendar.MONTH) + 1) + "月";
		return path;
	}
	private void createXls(List<OrderQueryStatistics> list, String dirPath, String day) throws Exception {
		HSSFWorkbook wk = new HSSFWorkbook();
		HSSFSheet sheet = wk.createSheet("排队超时20s接起");
		CellStyle titleCellStyle = XlsStyle.xlsStyleTitle(wk);
		CellStyle DataCellStyle = XlsStyle.xlsStyleData(wk);
		List<XlsTitle> titles = getTime20Titles(titleCellStyle, DataCellStyle);
		XlsCommon.xlsSheet(sheet, list, titles);
		File file = new File(dirPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		FileOutputStream out = null;
		String path = dirPath + "/" + day + ".xls";
		try {
			try {
				out = new FileOutputStream(path);
			} catch (FileNotFoundException e) {
				log.error("创建_排队超时20s接起 文件" + path + "失败");
				e.printStackTrace();
				throw e;
			}
			try {
				wk.write(out);
			} catch (IOException e) {
				log.error("写入_排队超时20s接起 文件" + path + "失败");
				e.printStackTrace();
				throw e;
			}
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				log.error("关闭_排队超时20s接起 文件" + path + "失败");
				e.printStackTrace();
				throw e;
			}
		}
	}
	private List<XlsTitle> getTime20Titles(CellStyle titleCellStyle, CellStyle DataCellStyle) {
		List<XlsTitle> titles = new ArrayList<XlsTitle>();
		// 业务组
		XlsTitle businessName = new XlsTitle("业务组", new XlsFieldValue("businessName", DataCellStyle), titleCellStyle, 4000);
		titles.add(businessName);
		// 来电时间
		XlsTitle callerTimeFormat = new XlsTitle("来电时间", new XlsFieldValue("callerTimeFormat", DataCellStyle), titleCellStyle, 6000);
		titles.add(callerTimeFormat);
		// 来电号码
		XlsTitle callerNumber = new XlsTitle("来电号码", new XlsFieldValue("callerNumber", DataCellStyle), titleCellStyle, 4000);
		titles.add(callerNumber);
		// 工号
		XlsTitle agentId = new XlsTitle("工号", new XlsFieldValue("agentId", DataCellStyle), titleCellStyle);
		titles.add(agentId);
		return titles;
	}
}
