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
		$('#jobName').validatebox({
			required:true
		});
		$('#jobClassFullName').validatebox({
			required:true
		});
		$('#jobDesc').validatebox({
			required:true
		});
		$('#jobGroup').combobox({
			valueField:'id',
			textField:'text',
			editable:false,
			required:true,
			panelHeight:'auto',
			data:[{id:'DEFAULT_JOB_GROUP',text:'DEFAULT_JOB_GROUP'}],
			value:'DEFAULT_JOB_GROUP'
		});
		$('#triggerGroup').combobox({
			valueField:'id',
			textField:'text',
			editable:false,
			required:true,
			panelHeight:'auto',
			data:[{id:'DEFAULT_TRIGGER_GROUP',text:'DEFAULT_TRIGGER_GROUP'}],
			value:'DEFAULT_TRIGGER_GROUP'
		});
		$('#intervalUnit').combobox({
			valueField:'id',
			textField:'text',
			editable:false,
			required:true,
			panelHeight:'auto',
			data:[{id:'second',text:'秒'},{id:'minute',text:'分'},{id:'hour',text:'小时'},{id:'day',text:'天'}],
			value:'秒'
		});
		var selectTriggerTypeId = "";
		$('#triggerType').combobox({
			valueField:'id',
			textField:'text',
			editable:false,
			required:true,
			panelHeight:'auto',
			data:[{id:'0',text:'时间间隔执行'},
				  {id:'1',text:'cron表达式'}],
			value:'',
			onSelect:function(record){
				if(selectTriggerTypeId == record.id)return;
				if(record.id==0){
					$("#simpleTriggerDetail").css({display:'inline'});
					$("#cronTriggerDetail").css({display:'none'});
					
					$("#repeatInterval").validatebox('enableValidation');
					$("#cronExpression").validatebox('disableValidation');
				}else if(record.id==1){
					$("#simpleTriggerDetail").css({display:'none'});
					$("#cronTriggerDetail").css({display:'inline'});
					$("#repeatInterval").validatebox('disableValidation');
					$("#cronExpression").validatebox('enableValidation');
				}
				selectTriggerTypeId = record.id;
			}
		});
		function checkBeforeSubmit(){
			var r = false;
			var values = applyValues();
			$.ajax({
				url: '/autoreport/schedulerManageAction_checkJobInfo.do',
				data: {
					params:JSON.stringify(values)
				},
				async:false,
				dataType: 'text',
				type: 'POST',
				success: function(data){
					data = eval('('+decodeURIComponent(data)+')');
					if(data.status!=true){
						$.messager.alert('注意', data.message, 'warning');
					}else{
						r = true;
					}
				},
				error: function(){
					$.messager.alert('注意', '后台异常，请联系系统管理员！', 'warning');
				}
			});
			return r;
		}
		function applyValues(){
			var values = {
					type:1,
					jobName:$('#jobName').val(),
					jobGroup:$('#jobGroup').combobox('getValue'),
					jobClassFullName:$('#jobClassFullName').val(),
					triggerName:$('#triggerName').val(),
					jobDesc:$('#jobDesc').val(),
					triggerGroup:$('#triggerGroup').combobox('getValue'),
					remark:''
				};
			var triggerType = $('#triggerType').combobox('getValue');
			values['triggerType']=triggerType;
			if(triggerType==0){
				var repeatInterval=$('#repeatInterval').val();
				
				var unit = $('#intervalUnit').combobox('getValue');
				if(unit=='second'){
					
				}else if(unit=='minute'){
					repeatInterval = repeatInterval*60;
				}else if(unit=='hour'){
					repeatInterval = repeatInterval*60*60;
				}else if(unit=='day'){
					repeatInterval = repeatInterval*60*60*24;
				}
				values['repeatInterval']=repeatInterval;
				
			}else if(triggerType==1){
				values['cronExpression']=$('#cronExpression').val();
			}
			return values;
		}
		
		var returnObj = new Object();
		window.returnValue = returnObj;
		
		$('#closeButton').bind('click', function(){
			returnObj.type='close';
			window.close();
		});
		$('#addButton').bind('click', function(){
			var isValid = $('#queryForm').form('validate');
			if (!isValid){
				//$.messager.alert('注意','请正确填写查询条件！','warning');
				return ;
			}
			if (!checkBeforeSubmit()){
				return ;
			}
			var values = applyValues();
			var addResult = false;
			$.ajax({
				url: '/autoreport/schedulerManageAction_addJob.do',
				data: {
					params:JSON.stringify(values)
				},
				dataType: 'json',
				async:false,
				type: 'POST',
				success: function(data){
					if(data==true)
						addResult=true;
					else
						$.messager.alert('注意', '操作失败！', 'warning');
				},
				error: function(){
					addResult=false;
					$.messager.alert('注意', '后台异常，请联系系统管理员！', 'warning');
				}
			});
			if(addResult){
				returnObj.type='add';
				window.close();
			}
		});
		
	});
</script>
</head>
<body>
<div class="easyui-panel" title="新增任务配置" id="query_div" style="width:auto;height:auto; margin-bottom: 3px;">
	<form id="queryForm">
		<table class="tableStyle1" style="margin-left:0px;margin-right:0px">
			<tr>
				<td align="right" style="width: 7%;">任务名称：</td>
				<td align="left" style="width: 13%;">
					<input class="easyui-validatebox"  id="jobName" name="jobName" style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<td align="right" style="width: 7%;">任务描述：</td>
				<td align="left" style="width: 13%;">
					<textarea id="jobDesc" name="jobDesc" style="width: 200px; height:100px"></textarea>
				</td>
			</tr>
			<tr>
				<td align="right">执行class：</td>
				<td align="left">
					<input class="easyui-validatebox" id="jobClassFullName" name="jobClassFullName" style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<td align="right">任务组：</td>
				<td align="left">
					<select class="easyui-validatebox" data-options="required: true,validType:'myRequiredValid[\'type\']'" id="jobGroup" name="jobGroup" style="width: 200px;">
					</select>
				</td>
			</tr>
			<tr>
				<td align="right">触发器名称：</td>
				<td align="left">
					<input class="easyui-validatebox" data-options="required: true,validType:'myRequiredValid[\'businessName\']'" id="triggerName" name="triggerName" style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<td align="right">触发器组：</td>
				<td align="left">
					<select class="easyui-validatebox" data-options="required: true,validType:'myRequiredValid[\'type\']'" id="triggerGroup" name="triggerGroup" style="width: 200px;">
					</select>
				</td>
			</tr>
<!-- 			<tr> -->
<!-- 				<td align="right" class="time_dom" style="width: 7%;"> -->
<!-- 					执行间隔： -->
<!-- 				</td> -->
<!-- 				<td align="left" class="time_dom" style=" width: 23%;"> -->
<!-- 					<input class="easyui-validatebox" data-options="required: true,validType:'myRequiredValid[\'businessName\']'" id="repeatInterval" name="repeatInterval" style="width: 200px;" /> -->
<!-- 				</td> -->
<!-- 			</tr> -->
			<tr>
				<td align="right">
					触发器类型：
				</td>
				<td align="left">
					<select class="easyui-validatebox" id="triggerType" name="triggerType" style="width: 150px;">
					</select>
					<span id="simpleTriggerDetail" style="display:none">
						<input class="easyui-validatebox" data-options="required: true" id="repeatInterval" name="repeatInterval" style="width: 50px;" />
						<select class="easyui-validatebox" id="intervalUnit" style="width: 50px;"></select>
					</span>
					<span id="cronTriggerDetail" style="display:none">
						<input class="easyui-validatebox" data-options="required: true" id="cronExpression" name="cronExpression" style="width: 200px;" />
					</span>
				</td>
			</tr>
			<tr>
				<td align="right">
					
				</td>
				<td>	
					<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="addButton">添加</a>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="closeButton">关闭</a>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>