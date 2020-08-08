<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="com.pinfly.purchasecharge.core.util.PurchaseChargeConstants"%>
<%@ page import="com.pinfly.purchasecharge.core.config.PurchaseChargeProperties"%>

<script type="text/javascript">
	$(function () {
		$('#providerSalesPieContainer').highcharts({
			chart: {
				type: 'pie',
				options3d: {
					enabled: true,
					alpha: 45,
					beta: 0
				}
			},
			title: {
				text: '<spring:message code="report.providerSalesPie" />'
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
		
		$('#providerSalesColumnContainer').highcharts({
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
				text: '<spring:message code="report.providerSalesColumn" />'
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
				name: '<spring:message code="report.provider" />',
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
		$('#providerReportCriteria #startDate').datebox('setValue', startDate);
		$('#providerReportCriteria #endDate').datebox('setValue', endDate);
		
		searchProviderReportByCreteria();
	});
	
	var searchProviderReportByCreteria = function() 
	{
		searchProviderSalesPie('<c:url value='/reportProvider/generatePieForProviderSalesVolume.html' />');
		searchProviderSalesColumn('<c:url value='/reportProvider/generateColumnForProviderSalesVolume.html' />');
	}
	
	var searchProviderSalesPie = function(requestUrl) 
	{
		var type = $('#providerReportCriteria #type').combobox('getValue');
		if(type == undefined) 
		{
			type = '';
		}
		var startDate = $('#providerReportCriteria #startDate').datebox('getValue');
		var endDate = $('#providerReportCriteria #endDate').datebox('getValue');
		var chart = $('#providerSalesPieContainer').highcharts();
		chart.showLoading();
		$.post(requestUrl, {providerType:type, start:startDate, end:endDate}, 
			function(result) 
			{
				//console.log(result);
				chart.hideLoading();
				chart.series[0].setData(result);
			}, 
		'json');
	}
	var searchProviderSalesColumn = function(requestUrl) 
	{
		var type = $('#providerReportCriteria #type').combobox('getValue');
		if(type == undefined) 
		{
			type = '';
		}
		var startDate = $('#providerReportCriteria #startDate').datebox('getValue');
		var endDate = $('#providerReportCriteria #endDate').datebox('getValue');
		var chart2 = $('#providerSalesColumnContainer').highcharts();
		chart2.showLoading();
		$.post(requestUrl, {providerType:type, start:startDate, end:endDate}, 
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
				chart2.xAxis[0].setCategories(categories);
				chart2.series[0].setData(data);
			}, 
		'json');
	}
</script>

<div id="providerReportCriteria">
	<span>
		<label><spring:message code="customer.type" />:</label> 
		<input id="type" class="easyui-combobox" editable="true" panelHeight="auto" 
			data-options="valueField:'id',textField:'name',filter: comboboxFilter, onShowPanel:onShowProviderType">
	</span>
	时间: <input id="startDate" name="startDate" class="easyui-datebox" style="width:100px" 
	editable="false" title="开始时间" data-options="">
	- <input id="endDate" name="endDate" class="easyui-datebox" style="width:100px" 
	editable="false" title="结束时间" data-options="">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="" onclick="searchProviderReportByCreteria()">查询</a>
</div>

<div style="width:100%;">
	<div style="margin:0 auto;width:95%;height:450px;">
		<div id="providerSalesPieContainer" style="min-width: 310px;width: 100%; height: 400px;"></div>
		<div id="providerSalesColumnContainer" style="min-width: 310px;width: 100%; height: 400px;"></div>
	</div>
</div>
