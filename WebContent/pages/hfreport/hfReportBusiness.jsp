<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>话务报表</title>
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="/css/forTable.css">
<script type="text/javascript" src="/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui/easyui-lang-zh_CN.js" charset="gb2312"></script>
<script type="text/javascript" charset="utf-8">
	$(function(){
		$.extend($.fn.validatebox.defaults.rules, {
			myRequiredValid : {
				validator : function(value, param) {
					if(value=='--请选择--'){
						return false;
					}
					return true;
				},
				message : '该输入项为必输项！'
			}
		});
		
		$('#month').combobox({
			textField : 'month',
			valueField : 'month',
			panelHeight : 'auto',
			disabled: true
		});
		$('#year').combobox({
			textField : 'year',
			valueField : 'year',
			panelHeight : 'auto',
			disabled: true,
			onSelect: function(record){
				var date = $('#date').combobox('getValue');
				if(record.year!='--请选择--' && date=='日报表'){
					$.ajax({
						url: '/autoreport/HfReportAction_queryMonth.do',
						data: {
							businessName: $('#businessName').combobox('getValue'),
							type: $('#type').combobox('getValue'),
							date: date,
							year: record.year
						},
						dataType: 'json',
						type: 'POST',
						success: function(data){
							data.sort(function(a,b){
								var aS = a.month.split('月')[0];
								var bS = b.month.split('月')[0];
								var aI = new Number(aS);
								var bI = new Number(bS);
								return  ((aI==bI)?0:(aI<bI?1:-1));
							});
							data.splice(0,0,{month:'--请选择--'});
							$('#month').combobox({disabled: false});
							$('#month').combobox('loadData', data);
							if(data.length > 0){
								$('#month').combobox('select', data[0].month);								
							}
							$('#month_div').show();
						},
						error: function(){
							$.messager.alert('注意', '后台异常，请联系系统管理员！', 'warn');
						}
					});
				}else{
					$('#month').combobox('loadData', [{month: '--请选择--'}]);
					$('#month').combobox('select', '--请选择--');
					$('#month').combobox({disabled: true});
					$('#month_div').hide();
				}
			}
		});
		
		$('#date').combobox({
			textField : 'date',
			valueField : 'date',
			panelHeight : 'auto',
			disabled: true,
			onSelect: function(record){
				if(record.date!='--请选择--'){
					$.ajax({
						url: '/autoreport/HfReportAction_queryYear.do',
						data: {
							businessName: $('#businessName').combobox('getValue'),
							type: $('#type').combobox('getValue'),
							date: record.date
						},
						dataType: 'json',
						type: 'POST',
						success: function(data){
							data.sort(function(a,b){
								var aS = a.year.split('年')[0];
								var bS = b.year.split('年')[0];
								var aI = new Number(aS);
								var bI = new Number(bS);
								return  ((aI==bI)?0:(aI<bI?1:-1));
							});
							data.splice(0,0,{year:'--请选择--'});
							$('#year').combobox({disabled: false});
							$('#year').combobox('loadData', data);
							if(data.length > 0){
								$('#year').combobox('select', data[0].year);								
							}
							$('.time_dom').show();
						},
						error: function(){
							$.messager.alert('注意', '后台异常，请联系系统管理员！', 'warn');
						}
					});
				}else{
					$('#year').combobox('loadData', [{year: '--请选择--'}]);
					$('#year').combobox('select', '--请选择--');
					$('#year').combobox({disabled: true});
					$('.time_dom').hide();
				}
			}
		});
		
		$('#type').combobox({
			textField : 'type',
			valueField : 'type',
			panelHeight : 'auto',
			disabled: true,
			onSelect: function(record){
				if(record.type!='--请选择--'){
					$.ajax({
						url: '/autoreport/HfReportAction_queryDate.do',
						data: {businessName: $('#businessName').combobox('getValue'), type: record.type},
						dataType: 'json',
						type: 'POST',
						success: function(data){
							data.splice(0,0,{date:'--请选择--'});
							$('#date').combobox({disabled: false});
							$('#date').combobox('loadData', data);
							if(data.length > 0){
								$('#date').combobox('select', data[0].date);
							}
							$('#date_div').show();
						},
						error: function(){
							$.messager.alert('注意', '后台异常，请联系系统管理员！', 'warn');
						}
					});
				}else{
					$('#date').combobox('loadData', [{date: '--请选择--'}]);
					$('#date').combobox('select', '--请选择--');
					$('#date').combobox({disabled: true});
					$('#date_div').hide();
				}
			}
		});
		
		
		$('#businessName').combobox({
			textField : 'value',
			valueField : 'key',
			panelHeight : 'auto',
			onSelect: function(record){
				if(record.key=='--请选择--'){
					$('#type').combobox('loadData', [{type: '--请选择--'}]);
					$('#type').combobox('select', '--请选择--');
					$('#type').combobox({disabled: true});
				}else{
					$.ajax({
						url: '/autoreport/HfReportAction_queryType.do',
						data: {businessName: record.key},
						dataType: 'json',
						type: 'POST',
						success: function(data){
							$('#type').combobox({disabled: false});
							data.splice(0,0,{type:'--请选择--'});
							$('#type').combobox('loadData', data);
							if(data.length > 0){
								$('#type').combobox('select', data[0].type);
							}
						},
						error: function(){
							$.messager.alert('注意', '后台异常，请联系系统管理员！', 'warn');
						}
					});
				}				
			},data: [{
				value: '工单查询录音时间',
				key: 'record'
			}]
		});
		$('#queryButton').click(function(){
			/* var isValid = $('#queryForm').form('validate');
			if(!isValid){
				return;
			} */
			//window.console.log(new Date().getTime());
			var arr = ['businessName', 'type', 'date', 'year', 'month'];
			var isDisabled = false;
			for(var ii=0;ii<arr.length;ii++){
				isDisabled = $('#'+arr[ii]).combobox('options').disabled;
				//window.console.log(arr[ii]+": "+isDisabled);
				if(!isDisabled){
					//window.console.log($('#'+arr[ii]).attr('id')+' isValid '+$('#'+arr[ii]).validatebox('validate'));
					//window.console.log($('#'+arr[ii]).combobox('getValue'));
					if($('#'+arr[ii]).combobox('getValue')=='--请选择--'){
						return ;
					}
				}
			}
			
			var businessName = $('#businessName').combobox('getValue');
			var type = $('#type').combobox('getValue');
			var date = $('#date').combobox('getValue');
			var year = $('#year').combobox('getValue');
			var month = $('#month').combobox('getValue');
			
			var sub_path = '';
			if(date=='日报表'){
				sub_path = businessName+'/'+type+'/'+date+'/'+year+'/'+month;
			}else{
				sub_path = businessName+'/'+type+'/'+date+'/'+year;
			}
			$.ajax({
				url: '/autoreport/HfReportAction_queryReport.do',
				data: $('#queryForm').serialize(),
				type: 'POST',
				dataType: 'json',
				success: function(data){
					$('#result_div').empty();
					$('#result_div').append('<table id="result_table" style="width: 100%;"></table>');
					var tab = $('#result_table');
					var tr = null;
					var obj = null;
					var td = null;
					
					for(var ii=0;ii< data.list.length;ii++){
						obj = data.list[ii];
						tr = $('<tr/>');
						tab.append(tr);
						for(var jj=1;jj<= 10;jj++){
							td = $('<td align="center"/>');
							if(obj['column'+jj] != null){
								td.html('<a href="../autoreport/data/'+sub_path+'/'+obj['column'+jj]+'" >'+obj['column'+jj]+'</a>');
							}else{
								
							}
							tr.append(td);
						}
					}
				},
				error: function(){
					$.messager.alert('注意', '后台异常，请联系系统管理员！', 'warn');
				}
			});
		});
	});
</script>
</head>
<body>
<div class="easyui-panel" title="话务报表" id="query_div" style="width:auto;height:auto; margin-bottom: 3px;">
	<form id="queryForm">
		<table width="100%" class="tableStyle1">
			<tr>
				<td align="right" style="width: 7%;">报表名称：</td>
				<td align="left" style="width: 13%;">
					<input class="easyui-validatebox" data-options="required: true,validType:'myRequiredValid[\'businessName\']'" id="businessName" name="businessName" style="width: 120px;" />
				</td>
				<td align="right" style="width: 7%;">报表类型：</td>
				<td align="left" style="width: 23%;">
					<select class="easyui-validatebox" data-options="required: true,validType:'myRequiredValid[\'type\']'" id="type" name="type" style="width: 120px;">
					</select>
					<span id="date_div" style="display: none">
						<select class="easyui-validatebox" data-options="required: true,validType:'myRequiredValid[\'date\']'" id="date" name="date" style="width: 120px;">
						</select>
					</span>
				</td>
				<td align="right" class="time_dom" style="display: none;width: 7%;">
					时间：
				</td>
				<td align="left" class="time_dom" style="display: none; width: 23%;">
					<select class="easyui-validatebox" data-options="required: true,validType:'myRequiredValid[\'year\']'" id="year" name="year" style="width: 120px;">
					</select>
					<span id="month_div" style="display: none">
						<select class="easyui-validatebox" data-options="required: true,validType:'myRequiredValid[\'month\']'" id="month" name="month" style="width: 120px;">
						</select>
					</span>
				</td>
				<td align="center">
					<a class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="queryButton">查询</a>
				</td>
			</tr>
		</table>
	</form>
</div>

<div class="easyui-panel" title="报表文件列表" id="result_div" style="width:auto; height: auto;"></div>
</body>
</html>