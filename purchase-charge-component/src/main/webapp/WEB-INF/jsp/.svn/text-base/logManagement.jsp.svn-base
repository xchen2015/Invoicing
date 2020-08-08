	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	<%@ page import="com.pinfly.purchasecharge.core.util.PurchaseChargeConstants"%>
	<%@ page import="com.pinfly.purchasecharge.component.bean.TimeSpan"%>
	
	<style type="text/css">
		#dlg-log input, #dlg-log select
		{
			width: 150px;
		}
		#dlg-log #fm-log div 
		{
			float: left;
			width: 300px;
		}
		#dlg-log #fm-log div label
		{
			width: 80px;
		}
	</style>

	<div style="width:100%; height:430px;">
		<table id="dg-log" title="<spring:message code="log.logManagement" />" class="easyui-datagrid" 
			toolbar="#toolbar-log" pagination="true" rownumbers="true" fit="true" singleSelect="true" checkOnSelect="true" 
			selectOnCheck="false" fitColumns="true" sortName="dateTime" sortOrder="desc" data-options="onBeforeLoad: onBeforeLoadLog">
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true"></th>
					<th data-options="field:'<%=PurchaseChargeConstants.LOG_DATE_TIME%>',width:50, sortable:true">日期</th>
					<th data-options="field:'event',width:50, sortable:true, formatter:formatter_logEvent"><spring:message code="log.logEvent" /></th>
					<th data-options="field:'operator',width:50, sortable:true">用户</th>
					<th data-options="field:'comment',width:200"><spring:message code="comment" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="toolbar-log" style="padding:5px;height:auto">
		<div id="advanceSearchSpan" style="">
			<label>时间:</label>
			<input id="startDate" name="startDate" class="easyui-datebox" style="width:100px" 
				editable="false" title="开始时间" data-options="onSelect: onLogSelectStartDate">
			- <input id="endDate" name="endDate" class="easyui-datebox" style="width:100px" 
				editable="false" title="结束时间" data-options="onSelect: onLogSelectStartDate">
			<select id="timeFrame" name="timeFrame" class="easyui-combobox" style="width:80px" panelHeight="auto" editable="false" data-options="onSelect: onLogSelectTimeFrame">
				<option value="CUSTOMIZE"><spring:message code="<%=TimeSpan.CUSTOMIZE.getMessageCode ()%>" /></option>
				<option value="TODAY" selected="selected"><spring:message code="<%=TimeSpan.TODAY.getMessageCode ()%>" /></option>
				<option value="RECENT_THREE_DAYS"><spring:message code="<%=TimeSpan.RECENT_THREE_DAYS.getMessageCode ()%>" /></option>
				<option value="RECENT_SEVEN_DAYS"><spring:message code="<%=TimeSpan.RECENT_SEVEN_DAYS.getMessageCode ()%>" /></option>
				<option value="RECENT_FIFTEEN_DAYS"><spring:message code="<%=TimeSpan.RECENT_FIFTEEN_DAYS.getMessageCode ()%>" /></option>
				<option value="RECENT_THIRTY_DAYS"><spring:message code="<%=TimeSpan.RECENT_THIRTY_DAYS.getMessageCode ()%>" /></option>
				<option value="CURRENT_MONTH"><spring:message code="<%=TimeSpan.CURRENT_MONTH.getMessageCode ()%>" /></option>
			</select>
			&nbsp;
	   	 	<label><spring:message code="log.logEvent" />:</label>
            <input id="logEvent" name="logEvent" class="easyui-combobox" editable="false" style="width:150px" data-options="
            	valueField:'id',
				textField:'name',
				panelHeight:'250',
				onShowPanel: onShowLogEventPanel"/>
			&nbsp;
			<label>用户:</label>
            <input id="operator" name="operator" class="easyui-combobox" editable="false" style="width:100px" data-options="
            	valueField:'id',
				textField:'userId',
				panelHeight:'250',
				onShowPanel: onShowUserPanel"/>
				
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="查询" onclick="onSearchLogByAdvance()">查询</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" title="" onclick="showAndManageLogEvent()">管理操作事件</a>
        </div>
	</div>
	
	<div id="dlg-logEvent" class="easyui-dialog" title="<spring:message code="log.logEventManagement" />" 
		style="width: 500px; height: 400px; padding: 5px;" closed="true"
		buttons="#dlg-buttons-logEvent" data-options="modal:true">
		<table id="dg-logEvent" class="easyui-datagrid" singleSelect="true" checkOnSelect="true" selectOnCheck="false" 
			toolbar="#toolbar-logEvent" pagination="false" rownumbers="true" data-options="fitColumns:true, fit:true">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'id',hidden:true"></th>
					<th field="name" width="100" sortable="true"><spring:message code="name" /></th>
					<th field="enabled" width="50" data-options="formatter:cellFormatter_enable, align:'center'"><spring:message code="log.eventEnable" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="toolbar-logEvent">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" 
			onclick="enableCheckedEvent()" title="">启用已选中</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" 
			onclick="disableCheckedEvent()" title="">禁用已选中</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" 
			onclick="loadGridData ('#dg-logEvent', '<c:url value='/logEvent/getAllModel.html' />')" 
			title="<spring:message code="refresh" />"></a>
	</div>
	<div id="dlg-buttons-logEvent">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" 
			onclick="javascript:$('#dlg-logEvent').dialog('close')"><spring:message code="close" /></a>
	</div>
	
	<script type="text/javascript">
		var formatter_logEvent = function(value,row,index) 
		{
			if(row.eventBean) 
			{
				return row.eventBean.name;
			}
		}
		
		var onSearchLogByAdvance = function () 
		{
			var startDate = $('#toolbar-log #advanceSearchSpan #startDate').combo('getValue');
			var endDate = $('#toolbar-log #advanceSearchSpan #endDate').combo('getValue');
			var logEvent = $('#toolbar-log #advanceSearchSpan #logEvent').combo('getValue');
			var operator = $('#toolbar-log #advanceSearchSpan #operator').combo('getValue');
			if((startDate != '' && endDate == '') || (startDate == '' && endDate != '')) 
			{
				$.messager.alert('警告','开始时间和结束时间必须同时填!','warning');
				return;
			}
			if(startDate == '' && endDate == '' && logEvent == '' && operator == '') 
			{
				$.messager.alert('警告','请填写查询条件!','warning');
				return;
			}
			$('#dg-log').datagrid('reload');
			//loadGridData ('#dg-log', '<c:url value='/log/getModelBySearchForm.html' />', {page:page, rows:rows, sort:'date', order:'desc', startDate:startDate, endDate:endDate, logType:logType, userId:userId});
		}
		var onBeforeLoadLog = function (param) 
		{
			var startDate = $('#toolbar-log #advanceSearchSpan #startDate').combo('getValue');
			var endDate = $('#toolbar-log #advanceSearchSpan #endDate').combo('getValue');
			var logEvent = $('#toolbar-log #advanceSearchSpan #logEvent').combo('getValue');
			var operator = $('#toolbar-log #advanceSearchSpan #operator').combo('getValue');
			
			if(startDate == '' && endDate == '' && logEvent == '' && operator == '') 
			{
				var timeFrame = $('#toolbar-log #advanceSearchSpan #timeFrame').combo('getValue');
				setInitialLogTimeFrame(timeFrame);
				
				startDate = $('#toolbar-log #advanceSearchSpan #startDate').combo('getValue');
				endDate = $('#toolbar-log #advanceSearchSpan #endDate').combo('getValue');
			}
			
			if(param.page == undefined || param.page == 0) 
			{
				param.page = 1;
			}
			$('#dg-log').datagrid('options').url = "<c:url value='/log/getModelBySearchForm.html' />?startDate="+startDate+"&endDate="+endDate+"&logEventId="+logEvent+"&userCreate="+operator+"&page="+param.page;
			return true;
		}
		
		var onShowLogEventPanel = function() 
		{
			$(this).combobox('reload', '<c:url value='/logEvent/getAllModel.html' />');
		}
		var onLogSelectStartDate = function (date) 
		{
			$('#toolbar-log #advanceSearchSpan #timeFrame').combobox('setValue', 'CUSTOMIZE');
		}
		var onLogSelectTimeFrame = function(record) 
		{
			setInitialLogTimeFrame(record.value);
		}
		var setInitialLogTimeFrame = function(timeFrame) 
		{
			var startDate = generateStartDate(timeFrame);
			var endDate = new Date().format("yyyy-MM-dd");
			$('#toolbar-log #advanceSearchSpan #startDate').datebox('setValue', startDate);
			$('#toolbar-log #advanceSearchSpan #endDate').datebox('setValue', endDate);
		}
		function showAndManageLogEvent() 
		{
			$('#dlg-logEvent').dialog('open');
			loadGridData ('#dg-logEvent', '<c:url value='/logEvent/getAllModel.html' />');
		}
		function enableCheckedEvent () 
		{
			enableMultipleModel('#dg-logEvent', '<spring:message code="log.logEvent" />', '<c:url value='/logEvent/enableLogEvent.html' />', true, function(){loadGridData ('#dg-logEvent', '<c:url value='/logEvent/getAllModel.html' />')});
		}
		function disableCheckedEvent () 
		{
			enableMultipleModel('#dg-logEvent', '<spring:message code="log.logEvent" />', '<c:url value='/logEvent/disableLogEvent.html' />', false, function(){loadGridData ('#dg-logEvent', '<c:url value='/logEvent/getAllModel.html' />')});
		}
    </script>
	