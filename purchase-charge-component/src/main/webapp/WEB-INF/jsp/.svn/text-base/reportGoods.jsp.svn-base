<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="com.pinfly.purchasecharge.core.util.PurchaseChargeConstants"%>
<%@ page import="com.pinfly.purchasecharge.core.config.PurchaseChargeProperties"%>

<script type="text/javascript">
	$(function () {
		$('#goodsSalesAmountPieContainer').highcharts({
			chart: {
				type: 'pie',
				options3d: {
					enabled: true,
					alpha: 45,
					beta: 0
				}
			},
			title: {
				text: '<spring:message code="report.goodsSalesAmountPie" />'
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
		
		$('#goodsSalesAmountColumnContainer').highcharts({
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
				text: '<spring:message code="report.goodsSalesAmountColumn" />'
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
					text:'<spring:message code="report.quantity" />'
				},
				opposite: true
			},
			series: [{
				name: '<spring:message code="report.goods" />',
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
		$('#goodsSalesSumPieContainer').highcharts({
			chart: {
				type: 'pie',
				options3d: {
					enabled: true,
					alpha: 45,
					beta: 0
				}
			},
			title: {
				text: '<spring:message code="report.goodsSalesSumPie" />'
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
		
		$('#goodsSalesSumColumnContainer').highcharts({
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
				text: '<spring:message code="report.goodsSalesSumColumn" />'
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
				name: '<spring:message code="report.goods" />',
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
		$('#goodsReportCriteria #startDate').datebox('setValue', startDate);
		$('#goodsReportCriteria #endDate').datebox('setValue', endDate);
		
		searchGoodsReportByCreteria();
	});
	
	var searchGoodsReportByCreteria = function() 
	{
		searchGoodsSalesAmountPie('<c:url value='/reportGoods/generatePieForGoodsSalesAmount.html' />');
		searchGoodsSalesAmountColumn('<c:url value='/reportGoods/generateColumnForGoodsSalesAmount.html' />');
		searchGoodsSalesSumPie('<c:url value='/reportGoods/generatePieForGoodsSalesSum.html' />');
		searchGoodsSalesSumColumn('<c:url value='/reportGoods/generateColumnForGoodsSalesSum.html' />');
	}
	
	var searchGoodsSalesAmountPie = function(requestUrl) 
	{
		var goodsTypeId = $('#goodsReportCriteria #goodsTypeId').combotree('getValue');
		if(goodsTypeId == '0') 
		{
			goodsTypeId = '';
		}
		var startDate = $('#goodsReportCriteria #startDate').datebox('getValue');
		var endDate = $('#goodsReportCriteria #endDate').datebox('getValue');
		var chart = $('#goodsSalesAmountPieContainer').highcharts();
		chart.showLoading();
		$.post(requestUrl, {goodsType:goodsTypeId, start:startDate, end:endDate}, 
			function(result) 
			{
				//console.log(result);
				chart.hideLoading();
				chart.series[0].setData(result);
			}, 
		'json');
	}
	var searchGoodsSalesAmountColumn = function(requestUrl) 
	{
		var goodsTypeId = $('#goodsReportCriteria #goodsTypeId').combotree('getValue');
		if(goodsTypeId == '0') 
		{
			goodsTypeId = '';
		}
		var startDate = $('#goodsReportCriteria #startDate').datebox('getValue');
		var endDate = $('#goodsReportCriteria #endDate').datebox('getValue');
		var chart2 = $('#goodsSalesAmountColumnContainer').highcharts();
		chart2.showLoading();
		$.post(requestUrl, {goodsType:goodsTypeId, start:startDate, end:endDate}, 
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
	var searchGoodsSalesSumPie = function(requestUrl) 
	{
		var goodsTypeId = $('#goodsReportCriteria #goodsTypeId').combotree('getValue');
		if(goodsTypeId == '0') 
		{
			goodsTypeId = '';
		}
		var startDate = $('#goodsReportCriteria #startDate').datebox('getValue');
		var endDate = $('#goodsReportCriteria #endDate').datebox('getValue');
		var chart = $('#goodsSalesSumPieContainer').highcharts();
		chart.showLoading();
		$.post(requestUrl, {goodsType:goodsTypeId, start:startDate, end:endDate}, 
			function(result) 
			{
				//console.log(result);
				chart.hideLoading();
				chart.series[0].setData(result);
			}, 
		'json');
	}
	var searchGoodsSalesSumColumn = function(requestUrl) 
	{
		var goodsTypeId = $('#goodsReportCriteria #goodsTypeId').combotree('getValue');
		if(goodsTypeId == '0') 
		{
			goodsTypeId = '';
		}
		var startDate = $('#goodsReportCriteria #startDate').datebox('getValue');
		var endDate = $('#goodsReportCriteria #endDate').datebox('getValue');
		var chart2 = $('#goodsSalesSumColumnContainer').highcharts();
		chart2.showLoading();
		$.post(requestUrl, {goodsType:goodsTypeId, start:startDate, end:endDate}, 
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

<div id="goodsReportCriteria">
	<span>
		<label><spring:message code="goods.type" />:</label> 
		<input id="goodsTypeId" class="easyui-combotree" editable="false" data-options="panelHeight:400, onShowPanel:showAllGoodsType" />
	</span>
	时间: <input id="startDate" name="startDate" class="easyui-datebox" style="width:100px" 
	editable="false" title="开始时间" data-options="">
	- <input id="endDate" name="endDate" class="easyui-datebox" style="width:100px" 
	editable="false" title="结束时间" data-options="">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="" onclick="searchGoodsReportByCreteria()">查询</a>
</div>

<div style="width:100%;">
	<div style="margin:0 auto;width:95%;height:450px;">
		<div id="goodsSalesAmountPieContainer" style="min-width: 310px;width: 100%; height: 400px;"></div>
		<div id="goodsSalesAmountColumnContainer" style="min-width: 310px;width: 100%; height: 400px;"></div>
		<div id="goodsSalesSumPieContainer" style="min-width: 310px;width: 100%; height: 400px;"></div>
		<div id="goodsSalesSumColumnContainer" style="min-width: 310px;width: 100%; height: 400px;"></div>
	</div>
</div>
