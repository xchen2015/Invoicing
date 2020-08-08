<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<% response.setContentType ("text/javascript"); %>

	var formatter_storageTransfer_goodsName = function(value, row, index) 
	{
		if(row.goodsBean) 
		{
			return row.goodsBean.name;
		}
	}
	var formatter_storageTransfer_depositoryFrom = function(value, row, index) 
	{
		if(row.fromDepositoryBean) 
		{
			return row.fromDepositoryBean.name;
		}
	}
	var formatter_storageTransfer_depositoryTo = function(value, row, index) 
	{
		if(row.toDepositoryBean) 
		{
			return row.toDepositoryBean.name;
		}
	}
	
	var onShowGoodsWhenTransfer = function(){
		var goodsDepositoryId = $('#toolbar-storage-transfer #goodsDepository').combobox('getValue');
		var goodsTypeId = $('#toolbar-storage-transfer #goodsTypeId').combotree('getValue');
		/*if(goodsDepositoryId == '' || goodsTypeId == '') 
		{
			$.messager.alert('警告','请先选择货物仓库和类型.','warning');
			return;
		}*/
		$(this).combobox('reload', '<c:url value='/goods/getGoodsByTypeOrDepository.html' />?goodsTypeId='+goodsTypeId+'&goodsDepositoryId='+goodsDepositoryId);
	}
	
	function loadGoodsStorageTransfer() 
	{
		$('#dg-storage-transfer').datagrid('reload');
	}
	function onBeforeLoadStorageTransfer(param) 
	{
		var goodsId = $('#toolbar-storage-transfer #goodsId').combobox('getValue');
		var goodsDepository = $('#toolbar-storage-transfer #goodsDepository').combobox('getValue');
		if(param.page == undefined || param.page == 0) 
		{
			param.page = 1;
		}
		$('#dg-storage-transfer').datagrid('options').url = "<c:url value='/goodsStorage/getModelBySearchForm.html' />?goodsDepository="+goodsDepository+"&goodsId="+goodsId+"&page="+param.page;
		return true;
	}
	
	var onChangeGoodsType_viewTransfer = function(newValue, oldValue) 
	{
		$('#toolbar-storage-transfer #goodsId').combobox('setValue', '');
		$('#toolbar-storage-transfer #goodsId').combobox('loadData', []);
	}
