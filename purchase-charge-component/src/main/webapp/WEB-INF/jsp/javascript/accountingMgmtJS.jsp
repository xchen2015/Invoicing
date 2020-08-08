<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<% response.setContentType ("text/javascript"); %>

var onClickExpenseTypeCombox = function() 
{
	var accountingMode = $("#dlg-accounting #fm-accounting input:checked").val();
	$(this).combobox('reload', '<c:url value='/accountingType/getTypeByMode.html' />?accountingMode=' + accountingMode);
}

var onSelectStartDate = function (date) 
{
	$('#toolbar-accounting #advanceSearchSpan #timeFrame').combobox('setValue', 'CUSTOMIZE');
}
var onSelectTimeFrame = function(record) 
{
	var startDate = generateStartDate(record.value);
	var endDate = new Date().format("yyyy-MM-dd");
	$('#toolbar-accounting #advanceSearchSpan #startDate').datebox('setValue', startDate);
	$('#toolbar-accounting #advanceSearchSpan #endDate').datebox('setValue', endDate);
}

var onBeforeLoadAccounting = function (param) 
{
	//console.log(param);
	var startDate = $('#toolbar-accounting #advanceSearchSpan #startDate').combo('getValue');
	var endDate = $('#toolbar-accounting #advanceSearchSpan #endDate').combo('getValue');
	var typeId = $('#toolbar-accounting #advanceSearchSpan #typeId').combo('getValue');
	if(typeId == undefined) 
	{
		typeId = '';
	}
	if(startDate == '' && endDate == '' && typeId == '') 
	{
		var timeFrame = $('#toolbar-accounting #advanceSearchSpan #timeFrame').combo('getValue');
		startDate = generateStartDate(timeFrame);
		endDate = new Date().format("yyyy-MM-dd");
		$('#toolbar-accounting #advanceSearchSpan #startDate').datebox('setValue', startDate);
		$('#toolbar-accounting #advanceSearchSpan #endDate').datebox('setValue', endDate);
		
		startDate = $('#toolbar-accounting #advanceSearchSpan #startDate').combo('getValue');
		endDate = $('#toolbar-accounting #advanceSearchSpan #endDate').combo('getValue');
	}
	
	if(param.page == undefined || param.page == 0) 
	{
		param.page = 1;
	}
	getSelectedAccountingTypeCode();
	$('#dg-accounting').datagrid('options').url = "<c:url value='/accounting/getModelBySearchForm.html' />?startDate="+startDate+"&endDate="+endDate+"&accountingMode=" + accounting_typeCode + "&accountingTypeId="+typeId+"&page="+param.page;
	return true;
}
var accounting_typeCode = 'OUT_LAY';
var getSelectedAccountingTypeCode = function() 
{
	accounting_typeCode = $("#toolbar-accounting input:checked").val();
}
var accounting_onClickAccountingType = function() 
{
	$('#dlg-accounting #fm-accounting #typeId').combobox('setValue', '');
}
var onSelectedAccountingTypeCode = function() 
{
	$('#toolbar-accounting #advanceSearchSpan #typeId').combobox('setValue', '');
	onSearchAccountingByAdvance();
}
var onSearchAccountingByAdvance = function () 
{
	var startDate = $('#toolbar-accounting #advanceSearchSpan #startDate').combo('getValue');
	var endDate = $('#toolbar-accounting #advanceSearchSpan #endDate').combo('getValue');
	var typeId = $('#toolbar-accounting #advanceSearchSpan #typeId').combo('getValue');
	if((startDate != '' && endDate == '') || (startDate == '' && endDate != '')) 
	{
		$.messager.alert('警告','开始时间和结束时间必须同时填!','warning');
		return;
	}
	if(startDate == '' && endDate == '' && typeId == '') 
	{
		$.messager.alert('警告','请填写查询条件!','warning');
		return;
	}
	
	$('#dg-accounting').datagrid('reload');
}
var newAccounting = function () 
{
	newModel('#dlg-accounting', '新增记账', '#fm-accounting', '<c:url value='/accounting/addModel.html' />', newAccountingCallback);
}
var newAccountingCallback = function () 
{
	resetAccountingForm();
	
	$('#dlg-buttons-accounting #save-accounting-new').show();
	$('#dlg-buttons-accounting #save-accounting-edit').hide();
}
var resetAccountingForm = function() 
{
	//var createDate = new Date().format("yyyy-MM-dd");
	//$('#dlg-accounting #fm-accounting #createDate').datebox('setValue', createDate);
	if(accounting_typeCode == 'OUT_LAY' || accounting_typeCode == '0') 
	{
		$("#dlg-accounting #fm-accounting #accountingTypeCode_out")[0].checked = true;
	}
	else 
	{
		$("#dlg-accounting #fm-accounting #accountingTypeCode_in")[0].checked = true;
	}
	var createDate = new Date().format("yyyy-MM-dd hh:mm");
	$('#dlg-accounting #fm-accounting #createDate').datetimebox('setValue', createDate);
}
var saveAccountingCallback = function() 
{
	resetAccountingForm();
	$('#dg-accounting').datagrid('reload');
}
var saveEditAccounting = function () 
{
	saveModel('#dg-accounting', '#dlg-accounting', '#fm-accounting');
}
var saveNewAccounting = function () 
{
	saveModel('#dg-accounting', '#dlg-accounting', '#fm-accounting', saveAccountingCallback, null, false);
}

var editAccounting = function()
{
	$('#dlg-accounting #fm-accounting').form('clear');
	var row = $('#dg-accounting').datagrid('getSelected');
	if (row) {
		$('#dlg-accounting').dialog('open').dialog('setTitle', '编辑记账');
		if(row.typeBean) 
		{
			if('IN_COME' == row.typeBean.accountingMode) 
			{
				$('#dlg-accounting #fm-accounting').form('load', row);
				$('#dlg-accounting #fm-accounting #typeId').combobox('setValue', row.typeBean.id);
				$('#dlg-accounting #fm-accounting #typeId').combobox('setText', row.typeBean.name);
				//loadComboboxData('#dlg-accounting #fm-accounting #typeId', '<c:url value='/accountingType/getTypeByMode.html' />?accountingMode=IN_COME', row.typeBean.id);
			}
			if('OUT_LAY' == row.typeBean.accountingMode) 
			{
				$('#dlg-accounting #fm-accounting').form('load', row);
				$('#dlg-accounting #fm-accounting #typeId').combobox('setValue', row.typeBean.id);
				$('#dlg-accounting #fm-accounting #typeId').combobox('setText', row.typeBean.name);
				//loadComboboxData('#dlg-accounting #fm-accounting #typeId', '<c:url value='/accountingType/getTypeByMode.html' />?accountingMode=OUT_LAY', row.typeBean.id);
			}
			setSaveOrEditUrl('<c:url value='/accounting/updateModel.html' />');
		}
		$('#dlg-buttons-accounting #save-accounting-new').hide();
		$('#dlg-buttons-accounting #save-accounting-edit').show();
	}
}

function formatter_accountingMode2(value,row)
{
	if(row.typeBean) 
	{
		if('IN_COME' == row.typeBean.accountingMode) 
		{
			return '收入';
		}
		if('OUT_LAY' == row.typeBean.accountingMode) 
		{
			return '支出';
		}
	}
	return '<span style="font-weight:bold">' + value + '</span>';
}
function formatter_accountingMoney(value,row)
{
	if(row.typeBean) 
	{
		if('IN_COME' == row.typeBean.accountingMode) 
		{
			return '<span style="color:blue;font-weight:bold">' + value + '</span>';
		}
		if('OUT_LAY' == row.typeBean.accountingMode) 
		{
			return '<span style="color:red;font-weight:bold">' + value + '</span>';
		}
	}
	return '<span style="font-weight:bold">' + value + '</span>';
}
function formatter_accountingType(value,row)
{
	if(row.typeBean) 
	{
		return row.typeBean.name;
	}
	return '';
}
function formatter_paymentAccount(value,row,index) 
{
	if(row.paymentAccountBean) 
	{
		return row.paymentAccountBean.name;
	}
}

var onClickAccountingRow = function (rowIndex, rowData) 
{
	//$('#toolbar-accounting #btn-deleteAccounting').linkbutton('enable');
}
var onLoadAccountingSuccess = function (data) 
{
	$('#toolbar-accounting #btn-deleteAccounting').linkbutton('disable');
}
var onCheckAllAccounting = function(rows) 
{
	$('#toolbar-accounting #btn-deleteAccounting').linkbutton('enable');
}
var onUnCheckAccounting = function(rowIndex,rowData) 
{
	var rows = $('#dg-accounting').datagrid('getChecked');
	if (rows.length == 0) {
		$('#toolbar-accounting #btn-deleteAccounting').linkbutton('disable');
	}
}
var showAccountingTypeGrid = function() 
{
	$('#dlg-dg-accountingType').dialog('open');
	$('#dlg-dg-accountingType #dg-accountingType').datagrid('options').url = "<c:url value='/accountingType/getAllModel.html' />";
	$('#dlg-dg-accountingType #dg-accountingType').datagrid('reload');
}
var onShowAllCustomerProvider = function() 
{
	$(this).combobox('reload', '<c:url value='/accounting/getAllCustomerProvider.html' />');
}
function accounting_customerProviderGroupFormatter(group)
{
	if(group == 'C') 
	{
		return '客户';
	}
	if(group == 'P') 
	{
		return '供应商';
	}
}
