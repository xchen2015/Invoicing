<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="com.pinfly.purchasecharge.core.util.PurchaseChargeConstants"%>
<%@ page import="com.pinfly.purchasecharge.core.config.PurchaseChargeProperties"%>

<script type="text/javascript">
	$(function () {
	    $('#orderSalesPerMonthContainer').highcharts({
	        title: {
	            text: '<spring:message code="report.orderSalesLinePerMonth" />'
	        },

			legend: {
				align: 'right',
				verticalAlign: 'top',
				x: 0,
				y: 100
			},

	        xAxis: {
	            categories: Highcharts.getOptions().lang.months
	        },
			yAxis:{
				title:{
					text:'<spring:message code="report.money" />'
				},
				plotLines: [{
					value: 0,
					width: 1,
					color: '#808080'
				}]
			},

			credits:{
				text:'<spring:message code="pc.name" />',               // 显示的文字
				href:'<%=PurchaseChargeProperties.getInstance ().getConfig (PurchaseChargeConstants.ABOUT_US) %>',   // 链接地址
				style: {                            // 样式设置
					fontSize: '16px'
				}
			}
	    });
		
		$('#orderProfitPerMonthContainer').highcharts({
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
	            text: '<spring:message code="report.orderProfitColumnPerMonth" />'
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
			credits:{
				text:'<spring:message code="pc.name" />',               // 显示的文字
				href:'<%=PurchaseChargeProperties.getInstance ().getConfig (PurchaseChargeConstants.ABOUT_US) %>',   // 链接地址
				style: {                            // 样式设置
					fontSize: '16px'
				}
			}
		});
		
		loadOrderReport();
	});
	
	
	var loadOrderReport = function() 
	{
		searchOrderSales('<c:url value='/reportIndex/generateLineForOrderVolumePerMonth.html' />');
		searchOrderProfit('<c:url value='/reportIndex/generateLineForOrderProfitPerMonth.html' />');
	}
	
	function searchOrderSales(requestUrl) 
	{
		$.post(requestUrl, {}, 
			function(result) 
			{
				//console.log(result);
				var chart = $('#orderSalesPerMonthContainer').highcharts();
				chart.showLoading();
				chart.hideLoading();
				for(var i = 0; i < result.length; i ++) 
				{
					chart.addSeries(result[i]);
				}
			}, 
		'json');
	}
	function searchOrderProfit(requestUrl) 
	{
		$.post(requestUrl, {}, 
			function(result) 
			{
				//console.log(result);
				var chart2 = $('#orderProfitPerMonthContainer').highcharts();
				chart2.showLoading();
				chart2.hideLoading();
				for(var i = 0; i < result.length; i ++) 
				{
					chart2.addSeries(result[i]);
				}
			}, 
		'json');
	}
</script>

<!--<div id="goodsReportCriteria">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" title="" onclick="loadOrderReport()"></a>
</div>-->

<div style="width:100%;">
	<div style="margin:0 auto;width:95%;height:450px;">
		<div id="orderSalesPerMonthContainer" style="float:left;min-width: 310px;width: 50%; height: 400px;"></div>
		<div id="orderProfitPerMonthContainer" style="float:left;min-width: 310px;width: 50%; height: 400px;"></div>
	</div>
</div>
