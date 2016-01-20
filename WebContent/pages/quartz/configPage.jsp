<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>定时任务配置</title>
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="/css/forTable.css">
<link href="/autoreport/css/themes/base/ui.all.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/autoreport/js/json2.js"></script>
<script type="text/javascript" src="/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui/easyui-lang-zh_CN.js" charset="gb2312"></script>
<script type="text/javascript" src="/autoreport/js/quartz/config.js"></script>
<script type="text/javascript" src="/autoreport/js/ui/ui.datetimepicker.js" charset="gb2312"></script>
<script type="text/javascript" charset="utf-8">

	$(document).ready(function(){
		function applyValues(){
			var values = {
					type:1,
					jobName:$('#jobName').val(),
					jobGroup:$('#jobGroup').val(),
					jobClassFullName:$('#jobClassFullName').val(),
					triggerName:$('#triggerName').val(),
					triggerGroup:$('#triggerGroup').val(),
					
					repeatInterval:$('#repeatInterval').val()
				};
			return values;
		}
	});
</script>
</head>
<body>
<div class="easyui-panel" title="定时任务配置" id="query_div" style="width:auto;height:auto; margin-bottom: 3px;">
	<form id="queryForm">
		<table width="100%" class="tableStyle1">
			<tr>
				 <td align="right" style="width: 7%;">任务名称：</td>
				<td align="left" style="width: 13%;">
					<input class="easyui-validatebox" id="jobName" name="jobName" style="width: 150px;height:17px" />
				</td>
				<td align="right" style="width: 7%;">任务组：</td>
				<td align="left" style="width: 23%;">
					<select class="easyui-validatebox" id="jobGroup" name="jobGroup" style="width: 150px;;height:22px">
					</select>
					<span id="date_div" style="display: none">
						<select class="easyui-validatebox" data-options="required: true,validType:'myRequiredValid[\'date\']'" id="date" name="date" style="width: 120px;">
						</select>
					</span>
				</td>
				<td align="right" class="time_dom" style="">
					下次执行时间：
				</td>
				<td align="left" class="time_dom" style="">
					<input class="easyui-validatebox" id="nextExecDateBegin" style="width: 150px;height:17px" />
					至：
					<input class="easyui-validatebox" id="nextExecDateEnd" style="width: 150px;height:18px" />
				</td> 
			</tr>
			
			<tr>
				<td align="right" style="width: 7%;">任务状态：</td>
				<td align="left" style="width: 23%;">
					<select class="easyui-validatebox" id="jobStatus" name="jobStatus" style="width: 120px;;height:22px">
					</select>
				</td>
				<td align="right" style="">
					执行class：
				</td>
				<td align="left" style="">
					<input class="easyui-validatebox" id="jobClassFullName" name="jobClassFullName" style="width: 300px;height:17px" />
				</td> 
				<td colspan="2" align="left">
					<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="queryButton">查询</a>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="addButton">添加任务</a>
				</td>
			</tr>
		</table>
	</form>
<!-- 	名&nbsp;&nbsp;&nbsp;&nbsp;称： -->
<!-- 	<input id="jobName" value="my-job-1"></input><br>类全名： -->
<!-- 	<input id="jobClassFullName" value="testjobs.MyJob"></input><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;组： -->
<!-- 	<input id="jobGroup" value="TEST_JOB_GROUP_1"></input><br> -->
<!-- 	<input id="triggerName" value="my-trigger-1"></input><br> -->
<!-- 	<input id="triggerGroup" value="TEST_TRIGGER_GROUP_1"></input><br> -->
<!-- 	<input id="repeatInterval" value="1"></input><br> -->
</div>
<div id="show_div" ></div>
<div id="show_log_window">
	<div id="show_log_div"></div>
</div>

<div id="add_new_job_window">
</div>
<div id="job_time_window" >
		<div  id='job_time_list'>
		</div>
		<table width="100%" class="tableStyle1">
			<tr>
				<td align="center" style="width: 6%;">任务名称</td>
				<td align="center" style="width: 4%;">开始时间</td>
				<td align="center" style="width: 4%;">结束时间</td>
				<td align="center" style="width: 3%;">操作</td>
			</tr>
			<tr>
				<td align="center" ><input size="15" id='jobName_add' readonly="readonly" style="width: 180px;"></td>
				<td  align="center" ><input size="15" id='jobStartTime_add' class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"></td>
				<td  align="center" > <input size="15" id='jobEndTime_add' class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"></td>
				<td align="center" ><a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="javascript:addJobTime();">添加</a></td>
			</tr>
		</table>
	</div>
</body>
</html>