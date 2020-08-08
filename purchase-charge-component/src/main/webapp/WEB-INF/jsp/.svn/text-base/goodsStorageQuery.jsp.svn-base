	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<div style="width:100%; height:430px;">
		<table id="dg-goods-storage-statistic">
		</table>
	</div>
	<div id="toolbar-get-storage-statistic" style="padding:5px;height:auto">
		<span style="width: 100%; display: inline-block;">
			<label><spring:message code="goods.type" />:</label> 
			<input id="goodsTypeId" name="goodsTypeId" class="easyui-combotree" style="width:200px" 
				data-options="panelHeight:400, formatter:goodsTypeWithGoodsAmountFormatter, 
					onShowPanel:showAllGoodsType, onChange:onChangeGoodsType_viewStorageStatistic" />
			&nbsp;
			<label><spring:message code="goodsStorage.goods" />:</label> 
			<input id="goodsId" name="goodsId" class="easyui-combobox" data-options="
					valueField:'id',
					textField:'name',
					panelHeight:'250',
					mode:'local', 
					filter: comboboxFilter,
					prompt:'输入名称查询', 
					formatter: goodsStorageFormatter,
					onShowPanel: onShowGoodsWhenCheckType" />
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="loadGoodsStorageStatistic()">查询</a>
		</span>
	</div>
	
	<script type="text/javascript">
	<!--
	$(function() 
	{
		var requestUrl = "<c:url value='/goodsDepository/getAllModel.html' />";
		var param = {};
		$.post(requestUrl, param,
		 	function(result) 
			{
				if(result != '') 
				{
					var columnHeaders = [];
					var basicHeaders = [];
					basicHeaders.push({field:'name',title:'货物名称',width:100,rowspan:2});
					basicHeaders.push({field:'shortCode',title:'编码',width:50,rowspan:2});
					basicHeaders.push({field:'specificationModel',title:'型号',width:50,rowspan:2});
					basicHeaders.push({field:'goodsType',title:'类型',width:50,rowspan:2,formatter:cellFormatter_goodsType});
					basicHeaders.push({field:'goodsUnit',title:'单位',width:50,rowspan:2,formatter:cellFormatter_goodsUnit});
					basicHeaders.push({field:'totalStock',title:'总数量',width:50,rowspan:2});
					basicHeaders.push({field:'totalValue',title:'总价值',width:50,rowspan:2});
					for(var i = 0; i < result.length; i ++) 
					{
						basicHeaders.push({title:result[i].name,colspan:2});
					}
					columnHeaders.push(basicHeaders);
					var subHeaders = [];
					for(var i = 0; i < result.length; i ++) 
					{
						subHeaders.push({field:'goodsAmount_'+result[i].name,title:'数量',width:50,formatter:formatter_storageStatistic_goodsAmount});
						subHeaders.push({field:'goodsWorth_'+result[i].name,title:'价值',width:50,formatter:formatter_storageStatistic_goodsWorth});
					}
					columnHeaders.push(subHeaders);
					$('#dg-goods-storage-statistic').datagrid({
					    title:'库存余额查询',
					    columns:columnHeaders,
					    rownumbers:true,
					    singleSelect:true,
					    showFooter:true,
					    toolbar:'#toolbar-get-storage-statistic',
					    fitColumns:true,
					    fit:true
					});
				}
			}, 
		'json');
	});
	
	var onShowGoodsWhenCheckType = function(){
		var goodsTypeId = $('#toolbar-get-storage-statistic #goodsTypeId').combotree('getValue');
		$(this).combobox('reload', '<c:url value='/goods/getGoodsByTypeOrDepository.html' />?goodsTypeId='+goodsTypeId+'&goodsDepositoryId=');
	}
	var onChangeGoodsType_viewStorageStatistic = function(newValue, oldValue) 
	{
		$('#toolbar-get-storage-statistic #goodsId').combobox('setValue', '');
		$('#toolbar-get-storage-statistic #goodsId').combobox('loadData', []);
	}
	var formatter_storageStatistic_goodsAmount = function(value, row, index) 
	{
		if(row.storageBeans && row.storageBeans.length > 0) 
		{
			for(var i = 0; i < row.storageBeans.length; i++) 
			{
				if(this.field == 'goodsAmount_' + row.storageBeans[i].depositoryBean.name) 
				{
					if(row.storageBeans[i].currentAmount < 0) 
					{
						return '<span style="color:red;font-weight:bold">'+row.storageBeans[i].currentAmount+'</span>';
					}
					return row.storageBeans[i].currentAmount;
				}
			}
		}
	}
	var formatter_storageStatistic_goodsWorth = function(value, row, index) 
	{
		if(row.storageBeans && row.storageBeans.length > 0) 
		{
			for(var i = 0; i < row.storageBeans.length; i++) 
			{
				if(this.field == 'goodsWorth_' + row.storageBeans[i].depositoryBean.name) 
				{
					return row.storageBeans[i].worth;
				}
			}
		}
	}
	var loadGoodsStorageStatistic = function()
	{
		var goodsType = $('#toolbar-get-storage-statistic #goodsTypeId').combotree('getValue');
		var goodsId = $('#toolbar-get-storage-statistic #goodsId').combobox('getValue');
		if(goodsType || goodsId) 
		{
			var requestUrl = "<c:url value='/goodsStorage/getGoodsStorageStatistic.html' />";
			var param = {goodsType: goodsType, goodsId : goodsId};
			loadGridData('#dg-goods-storage-statistic', requestUrl, param);
		}
	}
	//-->
	</script>
	