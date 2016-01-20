package com.speed.cscenter.css.statistics.quzrtz;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
import com.speed.management.quartz.service.BaseJobTemplate;
import com.speed.management.quartz.util.SpringUtils;
/**
 * 工单查询录音时间文件生成
 */
public class StatisticsDaylyRecordJob extends BaseJobTemplate {
	private static Logger log = Logger.getLogger("InfoFile");
	private final static String DAY_KEY = "day";
	@Override
	public void executes(JobExecutionContext context) throws Exception {
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
		{
			OrderQueryStatistics param = new OrderQueryStatistics();
			param.setBeginTime(day + " 00:00:00");
			param.setEndTime(day + " 23:59:59");
			try {
				OrderQueryService orderQueryService = SpringUtils.getContext().getBean(OrderQueryService.class);
				List<OrderQueryStatistics> list = orderQueryService.queryOrderStatisticsRecordList(param);
				createXls(list, xlsPath("record", day), day);
			} catch (Exception e) {
				log.error("工单查询录音时长" + day + "文件出错");
				e.printStackTrace();
			}
		}
	}
	public static void sta(String day) {
		StatisticsDaylyRecordJob job = new StatisticsDaylyRecordJob();
		// String day = "";
		// day = DateUtil.getYestodayStr();
		{
			OrderQueryStatistics param = new OrderQueryStatistics();
			param.setBeginTime(day + " 00:00:00");
			param.setEndTime(day + " 23:59:59");
			try {
				OrderQueryService orderQueryService = SpringUtils.getContext().getBean(OrderQueryService.class);
				List<OrderQueryStatistics> list = orderQueryService.queryOrderStatisticsRecordList(param);
				job.createXls(list, job.xlsPath("record", day), day);
			} catch (Exception e) {
				log.error("工单查询录音时长" + day + "文件出错");
				e.printStackTrace();
			}
		}
	}
	public String xlsPath(String businessId, String day) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(day);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String basicPath = System.getProperty("webapp.root");
		String path = basicPath + File.separator + "autoreport" + File.separator + "data" + File.separator + businessId + File.separator + "工单查询录音时间表格" + File.separator + "日报表" + File.separator + calendar.get(Calendar.YEAR) + "年" + File.separator + (calendar.get(Calendar.MONTH) + 1) + "月";
		return path;
	}
	public void createXls(List<OrderQueryStatistics> list, String dirPath, String day) throws Exception {
		HSSFWorkbook wk = new HSSFWorkbook();
		HSSFSheet sheet = wk.createSheet("工单查询录音时长");
		CellStyle titleCellStyle = XlsStyle.xlsStyleTitle(wk);
		CellStyle DataCellStyle = XlsStyle.xlsStyleData(wk);
		List<XlsTitle> titles = getRecordTitles(titleCellStyle, DataCellStyle);
		XlsCommon.xlsSheet(sheet, list, titles);
		File file = new File(dirPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String path = dirPath + "/" + day + ".xls";
		FileOutputStream out;
		try {
			out = new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			log.error("工单查询录音时长(" + day + ")创建文件出错");
			e.printStackTrace();
			throw e;
		}
		try {
			wk.write(out);
		} catch (IOException e) {
			log.error("工单查询录音时长(" + day + ")写入文件出错");
			e.printStackTrace();
			throw e;
		}
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			log.error("工单查询录音时长(" + day + ")关闭文件出错");
			e.printStackTrace();
			throw e;
		}
	}
	private List<XlsTitle> getRecordTitles(CellStyle titleCellStyle, CellStyle DataCellStyle) {
		List<XlsTitle> titles = new ArrayList<XlsTitle>();
		// 来电号码
		XlsTitle callerNumber = new XlsTitle("来电号码/查询id", new XlsFieldValue("callerNumber", DataCellStyle), titleCellStyle, 4000);
		titles.add(callerNumber);
		// 订单号
		XlsTitle clientOrderNum = new XlsTitle("订单号", new XlsFieldValue("clientOrderNum", DataCellStyle), titleCellStyle, 6000);
		titles.add(clientOrderNum);
		// 员工工号
		XlsTitle agentId = new XlsTitle("员工工号", new XlsFieldValue("agentId", DataCellStyle), titleCellStyle);
		titles.add(agentId);
		// 操作人
		XlsTitle csName = new XlsTitle("操作人", new XlsFieldValue("csName", DataCellStyle), titleCellStyle);
		titles.add(csName);
		// 工单生成时间
		XlsTitle subTimeFormat = new XlsTitle("工单生成时间", new XlsFieldValue("subTimeFormat", DataCellStyle), titleCellStyle, 6000);
		titles.add(subTimeFormat);
		// 客服处理时间
		XlsTitle finishTimeFormat = new XlsTitle("客服处理时间", new XlsFieldValue("finishTimeFormat", DataCellStyle), titleCellStyle, 6000);
		titles.add(finishTimeFormat);
		// 工单处理时长
		XlsTitle orderDealFormat = new XlsTitle("工单处理时长", new XlsFieldValue("orderDealFormat", DataCellStyle), titleCellStyle, 4000);
		titles.add(orderDealFormat);
		// 客服处理时长
		XlsTitle optTimeFormat = new XlsTitle("客服处理时长", new XlsFieldValue("optTimeFormat", DataCellStyle), titleCellStyle, 4000);
		titles.add(optTimeFormat);
		// 用户满意度
		XlsTitle userGrade = new XlsTitle("满意度", new XlsFieldValue("userGrade", DataCellStyle), titleCellStyle);
		titles.add(userGrade);
		// 录音时间
		XlsTitle recordTimeFormat = new XlsTitle("录音时长", new XlsFieldValue("recordTimeFormat", DataCellStyle), titleCellStyle);
		titles.add(recordTimeFormat);
		return titles;
	}
}
