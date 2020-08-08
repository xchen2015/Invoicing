<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="com.pinfly.purchasecharge.core.util.PurchaseChargeConstants"%>
<%@ page import="com.pinfly.purchasecharge.core.config.PurchaseChargeProperties"%>

<script type="text/javascript">
	$(function () {
		var pieTitle = getPieTitle();
		var columnTitle = getColumnTitle();
		var seriesName = getSeriesName();
		$('#accountingPieContainer').highcharts({
			chart: {
				type: 'pie',
				options3d: {
					enabled: true,
					alpha: 45,
					beta: 0
				}
			},
			title: {
				text: pieTitle
			},
			tooltip: {
				pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
			},
			plotOptions: {
				pie: {
					allowPointSelect: true,
					cursor: 'pointer',
					depth: 35,
					dataLabels: {
						enabled: true,
						color: '#000000',
						connectorColor: '#000000',
						format: '<b>{point.name}</b>: {point.percentage:.2f} %'
					}
				}
			},
			series: [{
				type: 'pie',
				name: '<spring:message code="report.pencentage" />',
				data: []
			}],
			credits:{
				text:'<spring:message code="pc.name" />',               // 显示的文字
				href:'<%=PurchaseChargeProperties.getInstance ().getConfig (PurchaseChargeConstants.ABOUT_US) %>',   // 链接地址
				style: {                            // 样式设置
					fontSize: '16px'
				}
			}
		});
		
		$('#accountingColumnContainer').highcharts({
			chart: {
				type: 'column',
				margin: 75,
				options3d: {
					enabled: true,
					alpha: 10,
					beta: 25,
					depth: 70
				}
			},
			title: {
				text: columnTitle
			},

			plotOptions: {
				column: {
					depth: 25
				}
			},
			xAxis: {
				categories: Highcharts.getOptions().lang.months
			},
			yAxis: {
				title:{
					text:'<spring:message code="report.money" />'
				},
				opposite: true
			},
			series: [{
				name: seriesName,
				data: []
			}],
			credits:{
				text:'<spring:message code="pc.name" />',               // 显示的文字
				href:'<%=PurchaseChargeProperties.getInstance ().getConfig (PurchaseChargeConstants.ABOUT_US) %>',   // 链接地址
				style: {                            // 样式设置
					fontSize: '16px'
				}
			}
		});
		
		//var timeFrame = new Date().getDate();
		var startDate = generateStartDate(30);
		var endDate = new Date().format("yyyy-MM-dd");
		$('#accountingReportCriteria #startDate').datebox('setValue', startDate);
		$('#accountingReportCriteria #endDate').datebox('setValue', endDate);
		
		generateAccounting();
	});
	
	var getPieTitle = function() 
	{
		var accountingMode = $('#accountingReportCriteria #accountingMode').combobox('getValue');
		if('OUT_LAY' == accountingMode) 
		{
			return '<spring:message code="report.outLayPie" />';
		}
		else 
		{
			return '<spring:message code="report.inComePie" />';
		}
	}
	var getColumnTitle = function() 
	{
		var accountingMode = $('#accountingReportCriteria #accountingMode').combobox('getValue');
		if('OUT_LAY' == accountingMode) 
		{
			return '<spring:message code="report.outLayColumn" />';
		}
		else 
		{
			return '<spring:message code="report.inComeColumn" />';
		}
	}
	var getSeriesName = function() 
	{
		var accountingMode = $('#accountingReportCriteria #accountingMode').combobox('getValue');
		if('OUT_LAY' == accountingMode) 
		{
			return '<spring:message code="report.outLay" />';
		}
		else 
		{
			return '<spring:message code="report.inCome" />';
		}
	}
	
	var generateAccounting = function() 
	{
		generatePieForAccounting('<c:url value='/reportAccounting/generatePieForAccounting.html' />');
		generateColumnForAccounting('<c:url value='/reportAccounting/generateColumnForAccounting.html' />');
	}
	
	var generatePieForAccounting = function(requestUrl) 
	{
		var accountingMode = $('#accountingReportCriteria #accountingMode').combobox('getValue');
		var startDate = $('#accountingReportCriteria #startDate').datebox('getValue');
		var endDate = $('#accountingReportCriteria #endDate').datebox('getValue');
		var chart = $('#accountingPieContainer').highcharts();
		chart.showLoading();
		$.post(requestUrl, {modeCode:accountingMode, start:startDate, end:endDate}, 
			function(result) 
			{
				//console.log(result);
				chart.hideLoading();
				chart.setTitle({ text: getPieTitle()});
				chart.series[0].setData(result);
			}, 
		'json');
	}
	var generateColumnForAccounting = function(requestUrl) 
	{
		var accountingMode = $('#accountingReportCriteria #accountingMode').combobox('getValue');
		var startDate = $('#accountingReportCriteria #startDate').datebox('getValue');
		var endDate = $('#accountingReportCriteria #endDate').datebox('getValue');
		var chart2 = $('#accountingColumnContainer').highcharts();
		chart2.showLoading();
		$.post(requestUrl, {modeCode:accountingMode, start:startDate, end:endDate}, 
			function(result) 
			{
				//console.log(result);
				chart2.hideLoading();
				var categories = new Array(result.length);
				var data = new Array(result.length);
				for(var i = 0; i < result.length; i ++) 
				{
					categories[i] = result[i].name;
					data[i] = result[i].y;
				}
				chart2.setTitle({ text: getColumnTitle()});
				chart2.xAxis[0].setCategories(categories);
				chart2.series[0].setData(data);
				chart2.series[0].update({
		            name: getSeriesName()
		        });
			}, 
		'json');
	}
</script>

<div id="accountingReportCriteria">
	<span>
		<label><spring:message code="goods.type" />:</label> 
		<select id="accountingMode" editable="false" class="easyui-combobox" panelHeight="auto" style="width:80px">
			<option value="OUT_LAY">支出</option>
			<option value="IN_COME">收入</option>
		</select>
	</span>
	时间: <input id="startDate" name="startDate" class="easyui-datebox" style="width:100px" 
	editable="false" title="开始时间" data-options="">
	- <input id="endDate" name="endDate" class="easyui-datebox" style="width:100px" 
	editable="false" title="结束时间" data-options="">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="" onclick="generateAccounting()">查询</a>
</div>

<div style="width:100%;">
	<div style="margin:0 auto;width:95%;height:450px;">
		<div id="accountingPieContainer" style="min-width: 310px;width: 100%; height: 400px;"></div>
		<div id="accountingColumnContainer" style="min-width: 310px;width: 100%; height: 400px;"></div>
	</div>
</div>
