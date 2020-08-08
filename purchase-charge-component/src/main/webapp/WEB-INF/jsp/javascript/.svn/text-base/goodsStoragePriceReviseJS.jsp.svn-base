<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<% response.setContentType ("text/javascript"); %>

	var loadGoodsStoragePriceRevise = function () 
	{
		$('#dg-storage-priceRevise').datagrid('reload');
	}
	var onBeforeLoadStoragePriceRevise = function(param) 
	{
		var goodsId = $('#toolbar-storage-priceRevise #goodsId').combobox('getValue');
		var goodsDepository = $('#toolbar-storage-priceRevise #goodsDepository').combobox('getValue');
		if(param.page == undefined || param.page == 0) 
		{
			param.page = 1;
		}
		$('#dg-storage-priceRevise').datagrid('options').url = "<c:url value='/goodsStorage/getStoragePriceRevise.html' />?goodsDepository="+goodsDepository+"&goodsId="+goodsId+"&page="+param.page;
		return true;
	}
	var loadGoodsStoragePrice = function () 
	{
		var goodsType = $('#dlg-view-storage-priceRevise #goodsTypeId').combotree('getValue');
		var goodsId = $('#dlg-view-storage-priceRevise #goodsId').combobox('getValue');
		var goodsName = $('#dlg-view-storage-priceRevise #goodsId').combobox('getText');
		if(goodsType || goodsId) 
		{
			var requestUrl = "<c:url value='/goodsStorage/getGoodsStorage.html' />";
			var param = {goodsType: goodsType, goodsId : goodsId};
			loadGridData('#dlg-view-storage-priceRevise #dg-goods-storage', requestUrl, param);
		}
	}
	var onChangeGoodsType_viewPrice = function(newValue, oldValue) 
	{
		$('#toolbar-view-storage-priceRevise #goodsId').combobox('setValue', '');
		$('#toolbar-view-storage-priceRevise #goodsId').combobox('loadData', []);
	}
	var onChangeGoodsType_viewPrice2 = function(newValue, oldValue) 
	{
		$('#toolbar-storage-priceRevise #goodsId').combobox('setValue', '');
		$('#toolbar-storage-priceRevise #goodsId').combobox('loadData', []);
	}
	var viewGoodsStoragePrice = function () 
	{
		$('#dlg-view-storage-priceRevise #goodsStorage-table').html('');
		$('#dlg-view-storage-priceRevise #dg-goods-storage').datagrid('loadData', []);
		$('#dlg-view-storage-priceRevise #goodsTypeId').combotree('setValue', '');
		$('#dlg-view-storage-priceRevise #goodsId').combobox('setValue', '');
		$('#dlg-view-storage-priceRevise').dialog('open');
	}
	
	var onShowGoodsWhenPriceRevise = function(){
		var goodsTypeId = $('#dlg-view-storage-priceRevise #goodsTypeId').combotree('getValue');
		/*if(goodsTypeId == '') 
		{
			$.messager.alert('警告','请先选择货物类型.','warning');
			return;
		}*/
		$(this).combobox('reload', '<c:url value='/goods/getGoodsByTypeOrDepository.html' />?goodsTypeId='+goodsTypeId+'&goodsDepositoryId=');
	}
	var onShowGoodsWhenPriceRevise2 = function(){
		var goodsDepositoryId = $('#toolbar-storage-priceRevise #goodsDepository').combotree('getValue');
		var goodsTypeId = $('#toolbar-storage-priceRevise #goodsTypeId').combotree('getValue');
		/*if(goodsDepositoryId == '' || goodsTypeId == '') 
		{
			$.messager.alert('警告','请先选择货物仓库和类型.','warning');
			return;
		}*/
		$(this).combobox('reload', '<c:url value='/goods/getGoodsByTypeOrDepository.html' />?goodsTypeId='+goodsTypeId+'&goodsDepositoryId='+goodsDepositoryId);
	}
	
	var formatter_storagePriceRevise_depository = function(value, row, index) 
	{
		if(row.depositoryBean) 
		{
			return row.depositoryBean.name;
		}
	}
	var formatter_storagePriceRevise_currentPrice = function(value, row, index) 
	{
		return "<span style='font-weight:bold'>" + row.currentPrice + "</span>";
	}
	var formatter_storagePriceRevise_goodsType = function(value, row, index) 
	{
		if(row.goodsBean && row.goodsBean.typeBean) 
		{
			return row.goodsBean.typeBean.text;
		}
	}
	var formatter_storagePriceRevise_goodsUnit = function(value, row, index) 
	{
		if(row.goodsBean && row.goodsBean.unitBean) 
		{
			return row.goodsBean.unitBean.name;
		}
	}
	var formatter_storagePriceRevise_goods = function(value, row, index) 
	{
		if(row.goodsBean) 
		{
			return row.goodsBean.name;
		}
	}
	var formatter_storagePriceRevise_goodsCode = function(value, row, index) 
	{
		if(row.goodsBean) 
		{
			return row.goodsBean.shortCode;
		}
	}
	var formatter_storagePriceRevise_goodsSpecification = function(value, row, index) 
	{
		if(row.goodsBean) 
		{
			return row.goodsBean.specificationModel;
		}
	}
	
	$.extend($.fn.datagrid.methods, {
        editCell: function(jq,param){
            return jq.each(function(){
                var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
                for(var i=0; i<fields.length; i++){
                    var col = $(this).datagrid('getColumnOption', fields[i]);
                    col.editor1 = col.editor;
                    if (fields[i] != param.field){
                        col.editor = null;
                    }
                }
                $(this).datagrid('beginEdit', param.index);
                for(var i=0; i<fields.length; i++){
                    var col = $(this).datagrid('getColumnOption', fields[i]);
                    col.editor = col.editor1;
                }
            });
        }
    });
    
    var goodsStoragePrice_editIndex = undefined;
    function goodsStoragePrice_endEditing(){
        if (goodsStoragePrice_editIndex == undefined){return true}
        if ($('#dlg-view-storage-priceRevise #dg-goods-storage').datagrid('validateRow', goodsStoragePrice_editIndex)){
            $('#dlg-view-storage-priceRevise #dg-goods-storage').datagrid('endEdit', goodsStoragePrice_editIndex);
            goodsStoragePrice_editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }
    function goodsStoragePrice_onClickCell(index, field){
        if (goodsStoragePrice_endEditing()){
            $('#dlg-view-storage-priceRevise #dg-goods-storage').datagrid('selectRow', index)
                    .datagrid('editCell', {index:index,field:field});
            goodsStoragePrice_editIndex = index;
        }
    }
	
	function goodsStorage_onChangeRevisePrice (newValue, oldValue) 
	{
		//console.log("newValue-->"+newValue + " oldValue-->"+oldValue);
		if (goodsStoragePrice_editIndex != undefined)
		{
			var row = $('#dlg-view-storage-priceRevise #dg-goods-storage').datagrid('getSelected');
			row.revisePrice = newValue;
			row.balancePrice = newValue-row.currentPrice;
			$('#dlg-view-storage-priceRevise #dg-goods-storage').datagrid('refreshRow', goodsStoragePrice_editIndex);
			if(newValue != row.currentPrice) 
			{
				$('#dlg-buttons-view-storage-priceRevise #generateStoragePriceRevise').linkbutton('enable');
			}
		}
	}
	
	function generateStoragePriceRevise() 
	{
		var rows = $('#dlg-view-storage-priceRevise #dg-goods-storage').datagrid('getRows');
		if(rows && rows.length > 0) 
		{
			var goodsStoragePriceReviseInfo = '';
			for(var i = 0; i < rows.length; i ++) 
			{
				if(rows[i].revisePrice && rows[i].revisePrice != rows[i].currentPrice) 
				{
					goodsStoragePriceReviseInfo += (rows[i].id + ',' + rows[i].revisePrice + ';');
				}
			}
			goodsStoragePriceReviseInfo = goodsStoragePriceReviseInfo.substring(0, goodsStoragePriceReviseInfo.length-1);
			
			if(goodsStoragePriceReviseInfo.length > 0) 
			{
				var requestUrl = '<c:url value='/goodsStorage/generateStoragePriceRevise.html' />';
				var param = {goodsStoragePriceReviseInfo:goodsStoragePriceReviseInfo};
				$.post(requestUrl, param, 
						function(result) 
						{
							console.log(result);
							if (result.statusCode == '400' || result.statusCode == '500') {
								showFailureMsg (result.message);
							}
							else if (result.statusCode == '401' || result.statusCode == '403') {
								showWarningMsg(result.message);
							}
							else {
								showSuccessMsg (result.message);
								loadGoodsStoragePrice();
								loadGoodsStoragePriceRevise();
							}
						},
						'json');
			}
		}
	}
