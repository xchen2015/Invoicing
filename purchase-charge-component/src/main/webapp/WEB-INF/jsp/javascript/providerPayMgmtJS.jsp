<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<% response.setContentType ("text/javascript"); %>

var addProviderPaymentCallback = function () 
{
	$('#tb-provider-payment-new #btn-add-payment-record').show();
	$('#dlg-buttons-add-provider-payment #save-provider-payment').show();
	$('#dlg-add-provider-payment #fm-provider-payment #dg-provider-payment-new').datagrid('showColumn', 'action');
	var createDate = new Date().format("yyyy-MM-dd");
	$('#dlg-add-provider-payment #fm-provider-payment #payDate').datebox('setValue', createDate);
	$.post('<c:url value='/providerPay/generateProviderPaymentBid.html' />',
   			function(result) 
   			{
				$('#dlg-add-provider-payment #fm-provider-payment #bid').textbox('setValue', result);
   			}
		,'text');

	resetProviderPaymentEditor();
	$('#dlg-add-provider-payment #fm-provider-payment #dg-provider-payment-new').datagrid('loadData', []);
	$('#dlg-add-provider-payment #fm-provider-payment #btn-add-payment-record').linkbutton('disable');
	$('#dlg-buttons-add-provider-payment #save-provider-payment').linkbutton('disable');
}
var resetProviderPaymentEditor = function () 
{	
	providerPayment_editingIndex = undefined;
	providerPayment_rowId = undefined;
}

var onProviderSelectStartDate = function (date) 
{
	$('#toolbar-provider-payment-detail #advanceSearchSpan #timeFrame').combobox('setValue', 'CUSTOMIZE');
}
var onProviderSelectTimeFrame = function(record) 
{
	setProviderInitialTimeFrame(record.value);
}
var onSearchProviderPaymentByAdvance = function () 
{
	var startDate = $('#toolbar-provider-payment-detail #advanceSearchSpan #startDate').combo('getValue');
	var endDate = $('#toolbar-provider-payment-detail #advanceSearchSpan #endDate').combo('getValue');
	var paymentTypeCode = $('#toolbar-provider-payment-detail #advanceSearchSpan #paymentTypeCode').combo('getValue');
	var customerId = $('#toolbar-provider-payment-detail #advanceSearchSpan #customerId').combo('getValue');
	
	$('#dg-provider-payment').datagrid('reload');
}
var defaultProviderTimeFrame = 'RECENT_THIRTY_DAYS';
var initial = false;
var onBeforeLoadProviderPayment = function (param) 
{
	pageInfo = param;
	if(initial == false) 
	{
		var timeFrame = $('#toolbar-provider-payment-detail #advanceSearchSpan #timeFrame').combo('getValue');
		setProviderInitialTimeFrame(timeFrame);
	}
	initial = true;
	var startDate = $('#toolbar-provider-payment-detail #advanceSearchSpan #startDate').combo('getValue');
	var endDate = $('#toolbar-provider-payment-detail #advanceSearchSpan #endDate').combo('getValue');
	var paymentTypeCode = $('#toolbar-provider-payment-detail #advanceSearchSpan #paymentTypeCode').combo('getValue');
	var customerId = $('#toolbar-provider-payment-detail #advanceSearchSpan #customerId').combo('getValue');
	if(customerId == undefined) 
	{
		customerId = '';
	}
	if(paymentTypeCode == undefined) 
	{
		paymentTypeCode = '';
	}
	
	if(param.page == undefined || param.page == 0) 
	{
		param.page = 1;
	}
	$('#dg-provider-payment').datagrid('options').url = "<c:url value='/providerPay/getModelBySearchForm.html' />?startDate="+startDate+"&endDate="+endDate+ "&paymentTypeCode=" + paymentTypeCode + "&customerId="+customerId+"&page="+param.page;
	return true;
}
var setProviderInitialTimeFrame = function(timeFrame) 
{
	var startDate = generateStartDate(timeFrame);
	var endDate = new Date().format("yyyy-MM-dd");
	$('#toolbar-provider-payment-detail #advanceSearchSpan #startDate').datebox('setValue', startDate);
	$('#toolbar-provider-payment-detail #advanceSearchSpan #endDate').datebox('setValue', endDate);
}

var onSelectProvider = function(record) 
{
	$('#dlg-add-provider-payment #fm-provider-payment #btn-add-payment-record').linkbutton('enable');
}

function formatter_providerPaymentAction2 (value,row,index) 
{
	if('IN_PAID_MONEY' == row.typeCode) 
	{
		return '<a href="#" class="viewEye" style="display:inline-block;width:16px;" title="查看" onclick="viewProviderPayment('+index+')">&nbsp;</a>';
	}
}
function viewProviderPayment (index) 
{
	$('#dg-provider-payment').datagrid('selectRow', index);
	var row = $('#dg-provider-payment').datagrid('getSelected');
	$('#dlg-add-provider-payment #fm-provider-payment').form('clear');
	if (row) {
		$('#dlg-add-provider-payment').dialog('open').dialog('setTitle', '查看已支付收款');
		$('#tb-provider-payment-new #btn-add-payment-record').hide();
		$('#dlg-buttons-add-provider-payment #save-provider-payment').hide();
		$('#dlg-add-provider-payment #fm-provider-payment #dg-provider-payment-new').datagrid('hideColumn', 'action');
		$('#dlg-add-provider-payment #fm-provider-payment').form('load', row);
		if(row.customerBean) 
		{
			$('#dlg-add-provider-payment #fm-provider-payment #customerId').combobox('setValue', row.customerBean.id);
			$('#dlg-add-provider-payment #fm-provider-payment #customerId').combobox('setText', row.customerBean.shortName);
		}
		
		$('#dlg-add-provider-payment #fm-provider-payment #dg-provider-payment-new').datagrid('loadData', []);
		$('#dlg-add-provider-payment #fm-provider-payment #dg-provider-payment-new').datagrid('loading');
		if(row.paymentRecordBeans) 
		{
			$('#dlg-add-provider-payment #fm-provider-payment #dg-provider-payment-new').datagrid('loadData', row.paymentRecordBeans);
			$('#dlg-add-provider-payment #fm-provider-payment #dg-provider-payment-new').datagrid('loaded');
		}
		/* $.post('<c:url value='/providerPay/getModelById.html' />', {paymentId : row.id}, 
			function(result) 
			{
				if(result && result.paymentRecordBeans) 
				{
					$('#dlg-add-provider-payment #fm-provider-payment #dg-provider-payment-new').datagrid('loadData', result.paymentRecordBeans);
				}
				$('#dlg-add-provider-payment #fm-provider-payment #dg-provider-payment-new').datagrid('loaded');
			}, 
			'json'); */
	}
	
}

function formatter_providerPaymentAction (value,row,index) 
{
	if (row.editing){
		if(row.id == '') 
		{
			var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="saveProviderPayment(this)">&nbsp;</a>&nbsp;&nbsp;';
			var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteProviderPayment(this)">&nbsp;</a>';
			return s+d;
		}
		else 
		{
			var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="saveProviderPayment(this,' + row.id + ')">&nbsp;</a>&nbsp;&nbsp;';
			var c = '<a href="#" class="icon-no" style="display:inline-block;width:16px;" title="取消" onclick="cancelProviderPayment(this)">&nbsp;</a>';
			return s+c;
		}
	} else {
		if(row.id == '') 
		{
			var e = '<a href="#" class="icon-edit" style="display:inline-block;width:16px;" title="编辑" onclick="editProviderPayment(this)">&nbsp;</a>&nbsp;&nbsp;';
			var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteProviderPayment(this)">&nbsp;</a>';
			return e+d;
		}
		else 
		{
			var e = '<a href="#" class="icon-edit" style="display:inline-block;width:16px;" title="编辑" onclick="editProviderPayment(this)">&nbsp;</a>&nbsp;&nbsp;';
			var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteProviderPayment(this,' + row.id + ')">&nbsp;</a>';
			return e+d;
		}
	}
}
var providerPayment_editingIndex;
var providerPayment_rowId;
var providerPayment_onBeforeEdit = function(index,row){
       row.editing = true;
	providerPayment_editingIndex = index;
	providerPayment_rowId = row.id;
       updateProviderPaymentActions(index);
   }
var providerPayment_onAfterEdit = function(index,row){
       row.editing = false;
	providerPayment_editingIndex = undefined;
	providerPayment_rowId = '';
       updateProviderPaymentActions(index);
   }
var providerPayment_onCancelEdit = function(index,row){
       row.editing = false;
	providerPayment_editingIndex = undefined;
	providerPayment_rowId = '';
       updateProviderPaymentActions(index);
   }
function updateProviderPaymentActions(index){
	$('#dg-provider-payment-new').datagrid('updateRow',{
		index: index,
		row:{}
	});
}
function editProviderPayment(target){
	if(providerPayment_editingIndex == undefined) 
	{
		$('#dg-provider-payment-new').datagrid('beginEdit', getRowIndex(target));
	}
}
function deleteProviderPayment(target, rowId){
	if(rowId == undefined) 
	{
		$('#dg-provider-payment-new').datagrid('deleteRow', getRowIndex(target));
		setProviderPaymentRecordList();
	}
	else 
	{
		if(providerPayment_editingIndex == undefined) 
		{
			$.messager.confirm('确认','您确认要删除?',function(r){
				if (r){
					$('#dg-provider-payment-new').datagrid('deleteRow', getRowIndex(target));
					$('#dg-provider-payment-new').datagrid('acceptChanges');
				}
			});
		}
	}
}
function saveProviderPayment(target, rowId){
	//console.log(rowId);
	if ($('#dg-provider-payment-new').datagrid('validateRow', getRowIndex(target)))
	{
		var paymentAccountEditor = $('#dg-provider-payment-new').datagrid('getEditor', {index:getRowIndex(target), field:'paymentAccount'});
		var paymentAccount = $(paymentAccountEditor.target).combobox('getText');
		$('#dg-provider-payment-new').datagrid('getRows')[providerPayment_editingIndex]['accountId'] = paymentAccount;
		var paymentWayEditor = $('#dg-provider-payment-new').datagrid('getEditor', {index:getRowIndex(target), field:'paymentWay'});
		var paymentWay = $(paymentWayEditor.target).combobox('getText');
		$('#dg-provider-payment-new').datagrid('getRows')[providerPayment_editingIndex]['paymentWayName'] = paymentWay;
		//console.log(accountingMode);
		//console.log(providerPaymentName);
		$('#dg-provider-payment-new').datagrid('endEdit', getRowIndex(target));
		if(rowId == undefined) 
		{
		}
		else 
		{
		}
		setProviderPaymentRecordList();
	}
}
var setProviderPaymentRecordList = function () 
{
	var paymentRecordList = "";
	var allRows = $('#dg-provider-payment-new').datagrid('getRows');
	//console.log(allRows.length);
	for(var i = 0; i < allRows.length; i++) 
	{
		var customerPaymentRecord = "";
		var item = allRows[i];
		customerPaymentRecord += (item.id +","+item.paymentAccount+","+item.paid+","+item.paymentWay+","+item.note);
		paymentRecordList += customerPaymentRecord + ";";
	}
	//console.log(storageList.substring(0, storageList.length-1));
	$('#dlg-add-provider-payment #fm-provider-payment #paymentRecordList').val(paymentRecordList);
	if(allRows.length > 0) 
	{
		$('#dlg-buttons-add-provider-payment #save-provider-payment').linkbutton('enable');
	}
	else 
	{
		$('#dlg-buttons-add-provider-payment #save-provider-payment').linkbutton('disable');
	}
}
var checkProviderPaymentRecord = function() 
{
	if(providerPayment_editingIndex != undefined) 
	{
		$('#dg-provider-payment-new').datagrid('endEdit', providerPayment_editingIndex);
		setProviderPaymentRecordList();
	}
}
function cancelProviderPayment(target){
	$('#dg-provider-payment-new').datagrid('cancelEdit', getRowIndex(target));
}
function insertProviderPayment(){
	if(providerPayment_editingIndex == undefined) 
	{
		var row = $('#dg-provider-payment-new').datagrid('getSelected');
		if (row){
			var index = $('#dg-provider-payment-new').datagrid('getRowIndex', row);
		} else {
			index = 0;
		}
		$('#dg-provider-payment-new').datagrid('insertRow', {
			index: index,
			row:{
				id:''
			}
		});
		$('#dg-provider-payment-new').datagrid('selectRow',index);
		$('#dg-provider-payment-new').datagrid('beginEdit',index);
	}
}

	var viewProviderPaymentDetail = function () 
	{
		var row = $('#dg-provider').datagrid('getSelected');
		if(row) 
		{
			$('#dlg-view-provider-payment #dg-provider-payment').datagrid('loadData', []);
			$('#dlg-view-provider-payment').dialog('open').dialog('setTitle', row.shortName + '的付款记录');
			$('#toolbar-provider-payment-detail #advanceSearchSpan #customerId').combobox('setValue', row.id);
			$('#toolbar-provider-payment-detail #advanceSearchSpan #customerId').combobox('setText', row.shortName);
			$('#dlg-view-provider-payment #dg-provider-payment').datagrid('reload');
		}
	}
	function doSearchProvider(value) {
		$('#dg-provider').datagrid('load', {
			searchKey : value
		});
	}