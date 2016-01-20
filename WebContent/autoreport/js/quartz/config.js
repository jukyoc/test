$(function(){
	$.parser.parse('#show_div');
	fillCondition();
	$('#show_div').datagrid({
		columns:[[{
			title: '任务名称',
			field: 'jobName',
			align: 'center',
			formatter: function(value,row,index){
				return value;
			}
		},{
			title: '任务描述',
			field: 'jobDesc',
			align: 'center',
			formatter: function(value,row,index){
				if(value.length>30){
					value = value.substr(0,30)+'<br>'+value.substr(30,value.length);
				}
				return value;
			}
		},{
			title: '下次执行时间',
			field: 'nextExecDate',
			align: 'center',
			width: '120',
			formatter: function(value,row,index){
				return value;
			}
		},{
			title: '上一次执行时间',
			field: 'lastExecDate',
			align: 'center',
			width: '120',
			formatter: function(value,row,index){
				return value;
			}
		},{
			title: '执行class',
			field: 'jobClassFullName',
			align: 'center',
			formatter: function(value,row,index){
				//value = '<a href="javascript:showDetail(\''+row.caseId+'\')">'+value+'</a>';
				return value;
			}
		},{
			title: '状态',
			field: 'jobStatus',
			align: 'center',
			width:'50',
			formatter: function(value,row,index){
				if(value=="PAUSED"){
					value="暂停中";
				}else if(value=="ACQUIRED"){
					value="运行中";
				}else if(value=="WAITING"){
					value="等待中";
				}else if(value=="COMPLETE"){
					value="运行中";
				}
				return value;
			},
			styler: function(value,row,index){
				var r ;
				if(value=="PAUSED")
					r = 'color:red';
				return r;
			}
		},{
			title: '日志',
			field: 'runLog',
			align: 'center',
			width:'70',
			formatter: function(value,row,index){
				var jobOperateLog = '<div onclick="javascript:queryJobLog(\''+row.jobName+'\',\''+row.jobGroup+'\',\''+row.triggerName+'\',\''+row.triggerGroup+'\')" style="color: #ffffff; padding-top: 3px; cursor: pointer; background-color: #AED0EA;text-align: center; width: 60px; height: 20px; float:left;margin-top:2px;">操作日志</div>';
				var jobRunLog = '<br><div onclick="javascript:queryJobRunLog(\''+row.jobName+'\',\''+row.jobGroup+'\',\''+row.triggerName+'\',\''+row.triggerGroup+'\')" style="color: #ffffff; padding-top: 3px; cursor: pointer; background-color: #AED0EA;text-align: center; width: 60px;   height: 20px; float:left;margin-top:2px;margin-bottom:2px">运行日志</div>';
				return jobOperateLog+jobRunLog;
			}
		},{
			title: '操作',
			field: 'operation',
			align: 'center',
			width:'70',
			formatter: function(value,row,index){
				//var deleteBtn = '<div onclick="javascript:deleteJob(\''+row.jobName+'\',\''+row.jobGroup+'\',\''+row.triggerName+'\',\''+row.triggerGroup+'\')" style="color: #ffffff; padding-top: 3px; cursor: pointer; background-color: #AED0EA;text-align: center; width: 60px; height: 20px; float:left;">删除</div>';
				var pauseBtn = ' <div onclick="javascript:pauseJob(\''+row.jobName+'\',\''+row.jobGroup+'\',\''+row.triggerName+'\',\''+row.triggerGroup+'\')" style="color: #ffffff; padding-top: 3px; cursor: pointer; background-color: #AED0EA;text-align: center; width: 60px; height: 20px; float:left;">暂停任务</div>';
				var resumeBtn = '<br> <div onclick="javascript:resumeJob(\''+row.jobName+'\',\''+row.jobGroup+'\',\''+row.triggerName+'\',\''+row.triggerGroup+'\')" style="color: #ffffff; padding-top: 3px; cursor: pointer; background-color: #AED0EA;text-align: center; width: 60px; height: 20px; float:left;margin-top:2px">恢复任务</div>';
				var forceRunBtn = '<br> <div onclick="javascript:forceRunBtn(\''+row.jobName+'\',\''+row.jobGroup+'\',\''+row.triggerName+'\',\''+row.triggerGroup+'\')" style="color: #ffffff; padding-top: 3px; cursor: pointer; background-color: #AED0EA;text-align: center; width: 60px; height: 20px; float:left;margin-top:2px">立即执行</div>';
				var dataTimeRunBtn = '<br> <div onclick="javascript:jobTimeDivShow(\''+row.jobName+'\')" style="color: #ffffff; padding-top: 3px; cursor: pointer; background-color: #AED0EA;text-align: center; width: 60px; height: 20px; float:left;margin-top:2px">数据时间</div>';
				return pauseBtn+resumeBtn+forceRunBtn+dataTimeRunBtn;
				
			}
		}]],
		rownumbers:true,
		//fitColumns: true,
		singleSelect:true,
		autoRowHeight:true,
		pagination:true,
		pageSize:10,
		nowrap: true,
		loadFilter:pagerFilter,
		loadMsg:'正在查询...',
		//width:1300,
		//height:180,
		url: '/autoreport/schedulerManageAction_queryJob.do'
	});
	
/*	$('#show_div').datagrid('getPager').pagination({
        onSelectPage:function(pageNum, pageSize){
        	var dg = $('#show_div');
        	var opts = dg.datagrid('options');
        	
        	$.extend(opts.queryParams || {}, {
        		start: pageNum,
        		limit: pageSize
            });
        	dg.datagrid('load');
        	
            $.extend(opts || {}, {
            	start: pageNum,
            	limit: pageSize
            });
            
            dg.datagrid('getPager').pagination('refresh',{
                pageNumber:pageNum,
                pageSize:pageSize
            });
        }
    });*/
	
	$('#queryButton').click(function(){
		
		var dg = $('#show_div');
		var opts = dg.datagrid('options');
		var temp = {
			'params':JSON.stringify({
				jobName:$('#jobName').val(),
				jobGroup:$('#jobGroup').combobox('getValue'),
				jobStatus:$('#jobStatus').combobox('getValue'),
				jobClassFullName:$('#jobClassFullName').val(),
				nextExecDateBegin:$('#nextExecDateBegin').val(),
				nextExecDateEnd:$('#nextExecDateEnd').val()
			})
		};
		$.extend(opts.queryParams || {}, temp);
		
		dg.datagrid('load');
	});
	
	$('#addButton').click(function(){
		showAddJobWindow();
	});
	
});

function validateQryCondition(){
	
	return true;
}

function fillCondition(){
	/*var now = new Date();
	var dateString = now.getFullYear()+'-'+(now.getMonth()+1<10?('0'+(now.getMonth()+1)):(now.getMonth()+1))+'-'+(now.getDate()<10?('0'+now.getDate()):now.getDate());
	var begint = dateString+' 00:00:00';
	var endt = dateString+' '+'23:59:59';*/
	
	$('#nextExecDateBegin').datepicker({dateFormat:'yy-mm-dd HH:II:SS'});
	$('#nextExecDateEnd').datepicker({dateFormat:'yy-mm-dd HH:II:SS'});
	$('#jobGroup').combobox({
		valueField:'id',
		textField:'text',
		editable:false,
		panelHeight:'auto',
		data:[{id:'',text:'全部'},{id:'DEFAULT_JOB_GROUP',text:'DEFAULT_JOB_GROUP'}]
	});
	$('#jobStatus').combobox({
		valueField:'id',
		textField:'text',
		editable:false,
		panelHeight:'auto',
		data:[{id:'',text:'全部'},
		      {id:'PAUSED',text:'暂停中'},
		      {id:'ACQUIRED',text:'运行中'},
		      {id:'WAITING',text:'等待中'}]
	});
}

function pagerFilter(data){
    data = {
        total: data.total,
        rows: data.list
    };
    return data;
}
function pagerFilter_detail(data){
	data = {
		total: data.total,
		rows: data.list
	};
	return data;
}

function pauseJob(jobName,jobGroup,triggerName,triggerGroup){
	$.messager.prompt('提示','请输入操作备注。。。',function(r){
		if(r){
			$.ajax({
				url: '/autoreport/schedulerManageAction_pauseJob.do',
				data: {
					params:JSON.stringify({
						jobName:jobName,
						jobGroup:jobGroup,
						triggerName:triggerName,
						triggerGroup:triggerGroup,
						remark:r
					})
				},
				dataType: 'json',
				type: 'POST',
				success: function(data){
					if(data==true){
						$.messager.alert('提示', '操作成功！', 'info');
						reloadDataGrid();
					}else{
						$.messager.alert('注意', '操作失败！', 'warning');
					}
				},
				error: function(){
					$.messager.alert('注意', '后台异常，请联系系统管理员！', 'warning');
				}
			});
		}
	});
}
function resumeJob(jobName,jobGroup,triggerName,triggerGroup){
	$.messager.prompt('提示','请输入操作备注。。。',function(r){
		if(r){
			$.ajax({
				url: '/autoreport/schedulerManageAction_resumeJob.do',
				data: {
					params:JSON.stringify({
						jobName:jobName,
						jobGroup:jobGroup,
						triggerName:triggerName,
						triggerGroup:triggerGroup,
						remark:r
					})
				},
				dataType: 'json',
				type: 'POST',
				success: function(data){
					if(data==true){
						$.messager.alert('提示', '操作成功！', 'info');
						reloadDataGrid();
					}else{
						$.messager.alert('注意', '操作失败！', 'warning');
					}
				},
				error: function(){
					$.messager.alert('注意', '后台异常，请联系系统管理员！', 'warning');
				}
			});
		}
	});
}
function forceRunBtn(jobName,jobGroup,triggerName,triggerGroup){
	$.messager.confirm('提示','确认立即马上执行任务：'+jobName+'?',function(r){
		if(!r)return;
		$.messager.prompt('提示','请输入参数（json格式）',function(r){
			if(r){
				try{
					var obj = eval('('+r+')');
				}catch(e){
					$.messager.alert('注意', '参数必须为json格式：{"key":"value"}', 'warning');
					return false;
				}
				$.ajax({
					url: '/autoreport/schedulerManageAction_forceRunJob.do',
					data: {
						params:JSON.stringify({
							jobName:jobName,
							jobGroup:jobGroup,
							triggerName:triggerName,
							triggerGroup:triggerGroup,
							remark:r,
							params:r
						})
					},
					dataType: 'json',
					type: 'POST',
					success: function(data){
						if(data==true){
							$.messager.alert('提示', '操作成功！', 'info');
							reloadDataGrid();
						}else{
							$.messager.alert('注意', '操作失败！', 'warning');
						}
					},
					error: function(){
						$.messager.alert('注意', '后台异常，请联系系统管理员！', 'warning');
					}
				});
			}else{
				$.messager.alert('注意', '必须输入参数', 'warning');
				return false;
			}
		});
	});
}
function dataTimeRunBtn(jobName,jobGroup,triggerName,triggerGroup){
	$.messager.confirm('提示','确认立即马上执行任务：'+jobName+'?',function(r){
		if(!r)return;
		$.messager.prompt('提示','请输入参数（json格式）',function(r){
			if(r){
				try{
					var obj = eval('('+r+')');
				}catch(e){
					$.messager.alert('注意', '参数必须为json格式：{"key":"value"}', 'warning');
					return false;
				}
				$.ajax({
					url: '/autoreport/schedulerManageAction_forceRunJob.do',
					data: {
						params:JSON.stringify({
							jobName:jobName,
							jobGroup:jobGroup,
							triggerName:triggerName,
							triggerGroup:triggerGroup,
							remark:r,
							params:r
						})
					},
					dataType: 'json',
					type: 'POST',
					success: function(data){
						if(data==true){
							$.messager.alert('提示', '操作成功！', 'info');
							reloadDataGrid();
						}else{
							$.messager.alert('注意', '操作失败！', 'warning');
						}
					},
					error: function(){
						$.messager.alert('注意', '后台异常，请联系系统管理员！', 'warning');
					}
				});
			}else{
				$.messager.alert('注意', '必须输入参数', 'warning');
				return false;
			}
		});
	});
}
function queryJobLog(jobName,jobGroup,triggerName,triggerGroup){
	$('#show_log_div').empty();
	$('#show_log_window').window({
		width:750,
		height:300,
		modal:true,
		title:'操作日志',
		collapsible:false,
		minimizable:false,
		maximizable:false,
		resizable:true,
		onOpen:function(){
			$('#show_log_div').datagrid({
				columns:[[{
					title: '操作人工号',
					field: 'agentId',
					align: 'center'
				},{
					title: '操作人姓名',
					field: 'agentName',
					align: 'center'
				},{
					title: '操作时间',
					field: 'createTime',
					align: 'center'
				},{
					title: '操作状态',
					field: 'logStatus',
					align: 'center',
					formatter: function(value,row,index){
						if(value=="1"){
							value="创建任务";
						}else if(value=="2"){
							value="重新启用";
						}else if(value=="3"){
							value="暂停";	
						}else if(value=="4"){
							value="删除";		
						}else if(value=="5"){
							value="强制执行";
						}
						return value;
					}
				},{
					title: '备注',
					field: 'remark',
					align: 'center'
				}]],
				rownumbers:true,
				//fitColumns: true,
				singleSelect:true,
				autoRowHeight:true,
				pagination:true,
				pageSize:10,
				nowrap: true,
				loadFilter:pagerFilter_detail,
				loadMsg:'正在查询...',
				height:'auto',
				url: '/autoreport/schedulerManageAction_queryJobLog.do',
				queryParams: {
					params:JSON.stringify({
						jobName:jobName,
						jobGroup:jobGroup,
						triggerName:triggerName,
						triggerGroup:triggerGroup
					})
				}
			});
		}
	});
}
function showAddJobWindow(){
	var respStr = window.showModalDialog(
			"/autoreport/schedulerManageAction_addNewJobPage.do"
			+"","dialogWidth:200px;dialogHeight:150px;scroll:yes;resizable:yes;status:no;help:no;");
	reloadDataGrid();
}
function queryJobRunLog(jobName,jobGroup,triggerName,triggerGroup){
/*	var respStr = window.showModalDialog(
			"/autoreport/schedulerManageAction_queryJobRunLogPage.do"
			+"","dialogWidth:400px;dialogHeight:300px;scroll:yes;resizable:yes;status:no;help:no;");
	reloadDataGrid();*/
	$('#show_log_div').empty();
	$('#show_log_window').window({
		width:750,
		height:300,
		modal:true,
		title:'任务执行日志',
		collapsible:false,
		minimizable:false,
		maximizable:false,
		resizable:true,
		onOpen:function(){
			$('#show_log_div').datagrid({
				columns:[[{
					title: '任务Id',
					field: 'jobId',
					align: 'center'
				},{
					title: '任务名',
					field: 'jobName',
					align: 'center',
					formatter: function(value,row,index){
						return jobName;
					}
				},{
					title: '执行开始时间',
					field: 'execBeginTime',
					align: 'center'
				},{
					title: '执行结束时间',
					field: 'execEndTime',
					align: 'center'
				},{
					title: '执行结果',
					field: 'execStatus',
					align: 'center',
					formatter: function(value,row,index){
						if(value==0){
							value='失败';
						}else if(value==1){
							value='成功';
						}
						return value;
					},
					styler: function(value,row,index){
						var r ;
						if(value==0)
							r = 'color:red';
						return r;
					}
				},{
					title: '异常信息',
					field: 'errorMessage',
					align: 'center'
				}]],
				rownumbers:true,
				//fitColumns: true,
				singleSelect:true,
				autoRowHeight:true,
				pagination:true,
				pageSize:10,
				nowrap: true,
				loadFilter:pagerFilter_detail,
				loadMsg:'正在查询...',
				height:'auto',
				url: '/autoreport/schedulerManageAction_queryJobRunningLog.do',
				queryParams: {
					params:JSON.stringify({
						jobName:jobName,
						jobGroup:jobGroup,
						triggerName:triggerName,
						triggerGroup:triggerGroup
					})
				}
			});
		}
	});
}	
function reloadDataGrid(){
	$('#show_div').datagrid('reload');
}
function deleteJob(jobName,jobGroup,triggerName,triggerGroup){
	
	alert("不支持");
}
function jobTimeDivShow(jobName) {
	$('#job_time_list').datagrid({
		columns:[[{
			title: '任务名称',
			field: 'jobName',
			align: 'center',
			width:'160',
			formatter: function(value,row,index){
				return value;
			}
		},{
			title: '开始时间',
			field: 'jobStartTime',
			align: 'center',
			width:'120',
			formatter: function(value,row,index){
				return value;
			}
		},{
			title: '结束时间',
			field: 'jobEndTime',
			align: 'center',
			width:'120',
			formatter: function(value,row,index){
				return value;
			}
		},{
			title: '操作',
			field: 'operation',
			align: 'center',
			width:'120',
			formatter: function(value,row,index){
				var jobName=row['jobName'];		
				var jobStartTime=row['jobStartTime'];
				var jobEndTime=row['jobEndTime'];
				var dataTimeRunBtn = "<div onclick=\"javascript:deleteJobTime('"+jobName+"','"+jobStartTime+"','"+jobEndTime+"');\" style=\"color: #ffffff;  cursor: pointer; background-color: #AED0EA;text-align: center; \">删除</div>";
				return dataTimeRunBtn;
			}
		}]],
		rownumbers:true,
		singleSelect:true,
		autoRowHeight:true,
		pagination:true,
		pageSize:100,
		nowrap: true,
		loadFilter:pagerFilter_job_time,
		loadMsg:'正在查询...',
		//width:1300,
		//height:180,
		url:  '/autoreport/schedulerManageAction_queryJobDataTimes.do',
		queryParams:{'jobName':jobName}
	});
	$('#jobStartTime_add').datetimebox({showSeconds:true});
	$('#jobEndTime_add').datetimebox({showSeconds:true});
	$('#jobName_add').val(jobName);
	$('#job_time_window').window('open');
}
function pagerFilter_job_time(map){
	var data = {
	        total: map.total,
	        rows: map.list
	    };
	    return data;
}
$(document).ready(function(){
	$('#job_time_window').window({
	    width:600,
	    height:400,
	    title:'任务执行时间配置',
	   // modal:true,
	    collapsible:false,
	    minimizable:false,
	    maximizable:false
	}); 
	$('#job_time_window').window('close');
});
function myformatter(date){
	var d = date.getDate();
	var h=date.getHours();
	var m=date.getMinutes();
	var s=date.getSeconds();
	return less10Format(d)+' '+less10Format(h)+':'+less10Format(m)+':'+less10Format(s);
}
function myparser(ss){
	if (!ss) return new Date();
	var dt=new Date();
	var d = parseInt(ss.substr(0,2),10);
	var h = parseInt(ss.substr(3,2),10);
	var m = parseInt(ss.substr(6,2),10);
	var s = parseInt(ss.substr(9,2),10);
	if (!isNaN(d) && !isNaN(h) && !isNaN(m)&& !isNaN(s)){
		dt.setDate(d);
		dt.setHours(h, m, s, 0);
		return dt;
	} else {
		return new Date();
	}
}
function less10Format(d){
	return (d<10?('0'+d):d);
}
function addJobTime(){
	if(addJobTimeCheck()){
		var data={
				jobName : $('#jobName_add').val(),
				jobStartTime : $('#jobStartTime_add').datetimebox('getValue'),
				jobEndTime : $('#jobEndTime_add').datetimebox('getValue')
		};
		var url = '/autoreport/schedulerManageAction_addJobDataTimes.do';
		 ajaxSupport(url, data, function(data){
			 if (data.flag) {
					$.messager.alert('提示', '操作成功！', 'info');
					$('#job_time_list').datagrid('reload');
				} else {
					$.messager.alert('注意', '操作失败！', 'warning');
				}
		 },function(){
				$.messager.alert('注意', '后台异常，请联系系统管理员！', 'warning');
		 });
	}
};
function addJobTimeCheck(){
	var data={
			jobName : $('#jobName_add').val(),
			jobStartTime : $('#jobStartTime_add').datetimebox('getValue'),
			jobEndTime : $('#jobEndTime_add').datetimebox('getValue')
	};
	if(data.jobStartTime==''){
		$.messager.alert('注意', '任务开始时间！', 'warning');
		return false;
	}
	if(data.jobEndTime==''){
		$.messager.alert('注意', '任务结束时间！', 'warning');
		return false;
	}
	return true;
};
function deleteJobTime(jobName,jobStartTime,jobEndTime){
	$.messager.confirm('删除确认','确认删除',function(r){
		if(r){
			var url = '/autoreport/schedulerManageAction_deleteJobDataTimes.do';
			var data = {
				'jobName' : jobName,
				'jobStartTime' :jobStartTime,
				'jobEndTime' : jobEndTime
		};
			 ajaxSupport(url, data, function(data){
				 if (data.flag) {
						$.messager.alert('提示', '操作成功！', 'info');
						$('#job_time_list').datagrid('reload');
					} else {
						$.messager.alert('注意', '操作失败！', 'warning');
					}
			 },function(){
					$.messager.alert('注意', '后台异常，请联系系统管理员！', 'warning');
			 });
		}
	});
};
function ajaxSupport(url, data, successFunc, errorFunc) {
	if (typeof data == 'function') {
		errorFunc = successFunc;
		successFunc = data;
		$.ajax({
			url : url,
			dataType : 'json',
			type : 'post',
			success : function(data) {
				successFunc(data);
			}
		});
	} else {
		data = JSON.stringify(data);
		$.ajax({
			url : url,
			dataType : 'json',
			type : 'post',
			data : data,
			contentType : "application/json",
			success : function(data) {
				successFunc(data);
			}
		});
	}
};
function ajaxSupportForm(url, data, successFunc, errorFunc) {
	if (typeof data == 'function') {
		errorFunc = successFunc;
		successFunc = data;
		$.ajax({
			url : url,
			dataType : 'json',
			type : 'post',
			success : function(data) {
				successFunc(data);
			}
		});
	} else {
		$.ajax({
			url : url,
			dataType : 'json',
			type : 'post',
			data : data,
			success : function(data) {
				successFunc(data);
			}
		});
	}
};