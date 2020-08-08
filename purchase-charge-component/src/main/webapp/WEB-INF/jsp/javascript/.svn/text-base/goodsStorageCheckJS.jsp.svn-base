<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<% response.setContentType ("text/javascript"); %>

	function loadGoodsStorageCheck() 
	{
		$('#dg-storage-check').datagrid('reload');
	}
	function onBeforeLoadStorageCheck(param) 
	{
		var goodsId = $('#toolbar-storage-check #goodsId').combobox('getValue');
		var goodsDepository = $('#toolbar-storage-check #goodsDepository').combobox('getValue');
		if(param.page == undefined || param.page == 0) 
		{
			param.page = 1;
		}
		$('#dg-storage-check').datagrid('options').url = "<c:url value='/goodsStorage/getStorageCheckRecord.html' />?goodsDepository="+goodsDepository+"&goodsId="+goodsId+"&page="+param.page;
		return true;
	}
	var loadGoodsStorage = function () 
	{
		var goodsType = $('#dlg-view-storage-rest #goodsTypeId').combotree('getValue');
		var goodsId = $('#dlg-view-storage-rest #goodsId').combobox('getValue');
		if(goodsType || goodsId) 
		{
			//$('#dlg-view-storage-rest').mask('加载中...');
			var requestUrl = "<c:url value='/goodsStorage/getGoodsStorage.html' />";
			var param = {goodsType: goodsType, goodsId : goodsId};
			loadGridData('#dlg-view-storage-rest #dg-goods-storage', requestUrl, param);
		}
	}
	var onChangeGoodsType_viewStorage = function(newValue, oldValue) 
	{
		$('#toolbar-view-storage-rest #goodsId').combobox('setValue', '');
		$('#toolbar-view-storage-rest #goodsId').combobox('loadData', []);
	}
	var onChangeGoodsType_viewStorage2 = function(newValue, oldValue) 
	{
		$('#toolbar-storage-check #goodsId').combobox('setValue', '');
		$('#toolbar-storage-check #goodsId').combobox('loadData', []);
	}
	var viewGoodsStorage = function () 
	{
		$('#dlg-view-storage-rest #goodsStorage-table').html('');
		$('#dlg-view-storage-rest #dg-goods-storage').datagrid('loadData', []);
		$('#dlg-view-storage-rest #goodsTypeId').combotree('setValue', '');
		$('#dlg-view-storage-rest #goodsId').combobox('setValue', '');
		$('#dlg-view-storage-rest').dialog('open');
	}
	
	var onShowGoodsWhenCheck = function(){
		var goodsTypeId = $('#dlg-view-storage-rest #goodsTypeId').combotree('getValue');
		$(this).combobox('reload', '<c:url value='/goods/getGoodsByTypeOrDepository.html' />?goodsTypeId='+goodsTypeId+'&goodsDepositoryId=');
	}
	var onShowGoodsWhenCheck2 = function(){
		var goodsDepositoryId = $('#toolbar-storage-check #goodsDepository').combobox('getValue');
		var goodsTypeId = $('#toolbar-storage-check #goodsTypeId').combotree('getValue');
		$(this).combobox('reload', '<c:url value='/goods/getGoodsByTypeOrDepository.html' />?goodsTypeId='+goodsTypeId+'&goodsDepositoryId='+goodsDepositoryId);
	}
	
	var formatter_storageCheck_depository = function(value, row, index) 
	{
		if(row.depositoryBean) 
		{
			return row.depositoryBean.name;
		}
	}
	var formatter_storageCheck_currentAmount = function(value, row, index) 
	{
		return "<span style='font-weight:bold'>" + row.currentAmount + "</span>";
	}
	var formatter_storageCheck_goodsType = function(value, row, index) 
	{
		if(row.goodsBean && row.goodsBean.typeBean) 
		{
			return row.goodsBean.typeBean.text;
		}
	}
	var formatter_storageCheck_goodsUnit = function(value, row, index) 
	{
		if(row.goodsBean && row.goodsBean.unitBean) 
		{
			return row.goodsBean.unitBean.name;
		}
	}
	var formatter_storageCheck_goods = function(value, row, index) 
	{
		if(row.goodsBean) 
		{
			return row.goodsBean.name;
		}
	}
	var formatter_storageCheck_goodsCode = function(value, row, index) 
	{
		if(row.goodsBean) 
		{
			return row.goodsBean.shortCode;
		}
	}
	var formatter_storageCheck_goodsSpecification = function(value, row, index) 
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
    
    var goodsStorage_editIndex = undefined;
    function goodsStorage_endEditing(){
        if (goodsStorage_editIndex == undefined){return true}
        if ($('#dlg-view-storage-rest #dg-goods-storage').datagrid('validateRow', goodsStorage_editIndex)){
            $('#dlg-view-storage-rest #dg-goods-storage').datagrid('endEdit', goodsStorage_editIndex);
            goodsStorage_editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }
    function goodsStorage_onClickCell(index, field){
        if (goodsStorage_endEditing()){
            $('#dlg-view-storage-rest #dg-goods-storage').datagrid('selectRow', index)
                    .datagrid('editCell', {index:index,field:field});
            goodsStorage_editIndex = index;
        }
    }
	
	function goodsStorage_onChangeActualAmount (newValue, oldValue) 
	{
		//console.log("newValue-->"+newValue + " oldValue-->"+oldValue);
		if (goodsStorage_editIndex != undefined)
		{
			var row = $('#dlg-view-storage-rest #dg-goods-storage').datagrid('getSelected');
			row.actualAmount = newValue;
			row.balanceAmount = newValue-row.currentAmount;
			$('#dlg-view-storage-rest #dg-goods-storage').datagrid('refreshRow', goodsStorage_editIndex);
			if(newValue != row.currentAmount) 
			{
				$('#dlg-buttons-view-storage-rest #generateStorageCheckRecord').linkbutton('enable');
			}
		}
	}
	
	function generateGoodsStorageCheckRecord() 
	{
		var rows = $('#dlg-view-storage-rest #dg-goods-storage').datagrid('getRows');
		if(rows && rows.length > 0) 
		{
			var goodsStorageCheckInfo = '';
			for(var i = 0; i < rows.length; i ++) 
			{
				if(rows[i].actualAmount && rows[i].actualAmount != rows[i].currentAmount) 
				{
					goodsStorageCheckInfo += (rows[i].id + ',' + rows[i].actualAmount + ';');
				}
			}
			goodsStorageCheckInfo = goodsStorageCheckInfo.substring(0, goodsStorageCheckInfo.length-1);
			
			if(goodsStorageCheckInfo.length > 0) 
			{
				var requestUrl = '<c:url value='/goodsStorage/generateStorageCheckRecord.html' />';
				var param = {goodsStorageCheckInfo:goodsStorageCheckInfo};
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
								loadGoodsStorage();
								loadGoodsStorageCheck();
							}
						},
						'json');
			}
		}
	}
