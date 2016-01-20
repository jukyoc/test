<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新增任务</title>
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="/css/forTable.css">
<script type="text/javascript" src="/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/autoreport/js/json2.js"></script>
<script type="text/javascript" src="/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui/easyui-lang-zh_CN.js" charset="gb2312"></script>
<script type="text/javascript" charset="utf-8">

	$(document).ready(function(){
		
		$('#jobGroup').combobox({
			valueField:'id',
			textField:'text',
			editable:false,
			required:true,
			panelHeight:'auto',
			data:[{id:'DEFAULT_JOB_GROUP',text:'DEFAULT_JOB_GROUP'}]
		});
		$('#triggerGroup').combobox({
			valueField:'id',
			textField:'text',
			editable:false,
			required:true,
			panelHeight:'auto',
			data:[{id:'DEFAULT_TRIGGER_GROUP',text:'DEFAULT_TRIGGER_GROUP'}]
		});
		
		function applyValues(){
			var values = {
					type:1,
					jobName:$('#jobName').val(),
					jobGroup:$('#jobGroup').combobox('getValue'),
					jobClassFullName:$('#jobClassFullName').val(),
					triggerName:$('#triggerName').val(),
					triggerGroup:$('#triggerGroup').combobox('getValue'),
					
					repeatInterval:$('#repeatInterval').val()
				};
			return values;
		}
		
		var returnObj = new Object();
		window.returnValue = returnObj;
		
		$('#closeButton').bind('click', function(){
			returnObj.type='close';
			window.close();
		});
		$('#addButton').bind('click', function(){
			
			var values = applyValues();
			$.ajax({
				url: '/autoreport/schedulerManageAction_addJob.do',
				data: {
					params:JSON.stringify(values)
				},
				dataType: 'json',
				type: 'POST',
				success: function(data){
					alert(data);
				},
				error: function(){
					$.messager.alert('注意', '后台异常，请联系系统管理员！', 'warning');
				}
			});
			
			returnObj.type='add';
			window.close();
		});
	});
</script>
</head>
<body>
<div class="easyui-panel" title="新增任务配置" id="query_div" style="width:auto;height:auto; margin-bottom: 3px;">
	<form id="queryForm">
		<table class="tableStyle1" style="margin-left:200px;margin-right:200px">
			<tr>
				<td align="right" style="width: 7%;">任务名称：</td>
				<td align="left" style="width: 13%;">
					<input class="easyui-validatebox" data-options="required: true,validType:'myRequiredValid[\'businessName\']'" id="jobName" name="jobName" style="width: 120px;" />
				</td>
			</tr>
			<tr>
				<td align="right" style="width: 7%;">执行class：</td>
				<td align="left" style="width: 13%;">
					<input class="easyui-validatebox" data-options="required: true,validType:'myRequiredValid[\'businessName\']'" id="jobClassFullName" name="jobClassFullName" style="width: 120px;" />
				</td>
			</tr>
			<tr>
				<td align="right" style="width: 7%;">任务组：</td>
				<td align="left" style="width: 23%;">
					<select class="easyui-validatebox" data-options="required: true,validType:'myRequiredValid[\'type\']'" id="jobGroup" name="jobGroup" style="width: 120px;">
					</select>
				</td>
			</tr>
			<tr>
				<td align="right" style="width: 7%;">触发器名称：</td>
				<td align="left" style="width: 23%;">
					<input class="easyui-validatebox" data-options="required: true,validType:'myRequiredValid[\'businessName\']'" id="triggerName" name="triggerName" style="width: 120px;" />
				</td>
			</tr>
			<tr>
				<td align="right" style="width: 7%;">触发器组：</td>
				<td align="left" style="width: 23%;">
					<select class="easyui-validatebox" data-options="required: true,validType:'myRequiredValid[\'type\']'" id="triggerGroup" name="triggerGroup" style="width: 120px;">
					</select>
				</td>
			</tr>
			<tr>
				<td align="right" class="time_dom" style="width: 7%;">
					执行间隔：
				</td>
				<td align="left" class="time_dom" style=" width: 23%;">
					<input class="easyui-validatebox" data-options="required: true,validType:'myRequiredValid[\'businessName\']'" id="repeatInterval" name="repeatInterval" style="width: 120px;" />
				</td>
			</tr>
			<tr>
				<td align="center">
					<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="addButton">添加</a>
				</td>
				<td>	
					<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="closeButton">关闭</a>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>