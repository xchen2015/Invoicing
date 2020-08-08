<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<% response.setContentType ("text/javascript"); %>

	var warnGoodsStorage = function () 
	{
		$('#dlg-view-storage-warn #dg-goods-storage').datagrid('loadData', []);
		$('#dlg-view-storage-warn').dialog('open');
		if('${sessionScope.autoShowStorageWarning}' == '0') 
		{
			$('#autoShowStorageWarn').hide();
		}
		
		var requestUrl = "<c:url value='/goodsStorage/getGoodsStorageWarn.html' />";
		var param = {};
		loadGridData('#dlg-view-storage-warn #dg-goods-storage', requestUrl, param);
	}
	
	var formatter_storageWarn_goodsType = function(value, row, index) 
	{
		if(row.typeBean) 
		{
			return row.typeBean.text;
		}
	}
	var formatter_storageWarn_goodsUnit = function(value, row, index) 
	{
		if(row.unitBean) 
		{
			return row.unitBean.name;
		}
	}
	var formatter_storageWarn_balanceStock = function(value, row, index) 
	{
		var balanceStock = row.totalStock - row.maxStock;
		if(balanceStock > 0) 
		{
			return '<span style="color:orange; font-weight:bold; display:block;" title="超出最高限值">' + balanceStock + '</span>';
		}
		else 
		{
			if(row.totalStock < 0) 
			{
				return '<span style="color:red; font-weight:bold; display:block;" title="库存为负">' + row.totalStock + '</span>';
			}
			balanceStock = row.totalStock - row.minStock;
			if(balanceStock < 0) 
			{
				return '<span style="color:red; font-weight:bold; display:block;" title="低于最低限值">' + balanceStock + '</span>';
			}
		}
	}
	
	var notAutoShowStorageWarn = function(checkbox) 
	{
		var requestUrl = "<c:url value='/goodsStorage/setAutoShowWarning.html' />";
		var requestParam = {autoShowWarning:'1'};
		if(checkbox.checked)
		{
			requestParam = {autoShowWarning:'0'};
		}
		$.post(requestUrl, requestParam, function(result){}, 'json');
	}
