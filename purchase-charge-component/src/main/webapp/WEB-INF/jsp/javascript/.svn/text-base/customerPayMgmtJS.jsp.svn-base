<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<% response.setContentType ("text/javascript"); %>

var addCustomerPaymentCallback = function () 
{
	$('#tb-customer-payment-new #btn-add-payment-record').show();
	$('#dlg-buttons-add-customer-payment #save-customer-payment').show();
	$('#dlg-add-customer-payment #fm-customer-payment #dg-customer-payment-new').datagrid('showColumn', 'action');
	var createDate = new Date().format("yyyy-MM-dd");
	$('#dlg-add-customer-payment #fm-customer-payment #payDate').datebox('setValue', createDate);
	$.post('<c:url value='/customerPay/generateCustomerPaymentBid.html' />',
   			function(result) 
   			{
				$('#dlg-add-customer-payment #fm-customer-payment #bid').textbox('setValue', result);
   			}
		,'text');
	
	resetCustomerPaymentEditor();
	$('#dlg-add-customer-payment #fm-customer-payment #dg-customer-payment-new').datagrid('loadData', []);
	$('#dlg-add-customer-payment #fm-customer-payment #btn-add-payment-record').linkbutton('disable');
	$('#dlg-buttons-add-customer-payment #save-customer-payment').linkbutton('disable');
}
var resetCustomerPaymentEditor = function () 
{	
	customerPayment_editingIndex = undefined;
	customerPayment_rowId = undefined;
}

var onCustomerSelectStartDate = function (date) 
{
	$('#toolbar-customer-payment-detail #advanceSearchSpan #timeFrame').combobox('setValue', 'CUSTOMIZE');
}
var onCustomerSelectTimeFrame = function(record) 
{
	setCustomerInitialTimeFrame(record.value);
}
var onSearchCustomerPaymentByAdvance = function () 
{
	var startDate = $('#toolbar-customer-payment-detail #advanceSearchSpan #startDate').combo('getValue');
	var endDate = $('#toolbar-customer-payment-detail #advanceSearchSpan #endDate').combo('getValue');
	var paymentTypeCode = $('#toolbar-customer-payment-detail #advanceSearchSpan #paymentTypeCode').combo('getValue');
	var customerId = $('#toolbar-customer-payment-detail #advanceSearchSpan #customerId').combo('getValue');
	
	if((startDate != '' && endDate == '') || (startDate == '' && endDate != '')) 
	{
		$.messager.alert('警告','开始时间和结束时间必须同时填!','warning');
		return;
	}
	if(startDate == '' && endDate == '' && paymentTypeCode == '' && customerId == '') 
	{
		$.messager.alert('警告','请填写查询条件!','warning');
		return;
	}
	$('#dg-customerPayment').datagrid('reload');
}
var defaultCustomerTimeFrame = 'RECENT_THIRTY_DAYS';
var initial = false;
var onBeforeLoadCustomerPayment = function (param) 
{
	if(initial == false) 
	{
		var timeFrame = $('#toolbar-customer-payment-detail #advanceSearchSpan #timeFrame').combo('getValue');
		setCustomerInitialTimeFrame(timeFrame);
	}
	initial = true;
	var startDate = $('#toolbar-customer-payment-detail #advanceSearchSpan #startDate').combo('getValue');
	var endDate = $('#toolbar-customer-payment-detail #advanceSearchSpan #endDate').combo('getValue');
	var paymentTypeCode = $('#toolbar-customer-payment-detail #advanceSearchSpan #paymentTypeCode').combo('getValue');
	var customerId = $('#toolbar-customer-payment-detail #advanceSearchSpan #customerId').combo('getValue');
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
	$('#dg-customerPayment').datagrid('options').url = "<c:url value='/customerPay/getModelBySearchForm.html' />?startDate="+startDate+"&endDate="+endDate + "&paymentTypeCode=" + paymentTypeCode + "&customerId="+customerId+"&page="+param.page;
	return true;
}
var setCustomerInitialTimeFrame = function(timeFrame) 
{
	var startDate = generateStartDate(timeFrame);
	var endDate = new Date().format("yyyy-MM-dd");
	$('#toolbar-customer-payment-detail #advanceSearchSpan #startDate').datebox('setValue', startDate);
	$('#toolbar-customer-payment-detail #advanceSearchSpan #endDate').datebox('setValue', endDate);
}
var onSelectCustomer = function(record) 
{
	$('#dlg-add-customer-payment #fm-customer-payment #btn-add-payment-record').linkbutton('enable');
}

function formatter_customerPaymentAction2 (value,row,index) 
{
	if('OUT_PAID_MONEY' == row.typeCode) 
	{
		return '<a href="#" class="iconSpan16 viewEye" style="display:inline-block;width:16px;" title="查看" onclick="viewCustomerPayment('+index+')">&nbsp;</a>';
	}
}
function viewCustomerPayment (index) 
{
	$('#dg-customerPayment').datagrid('selectRow', index);
	var row = $('#dg-customerPayment').datagrid('getSelected');
	$('#dlg-add-customer-payment #fm-customer-payment').form('clear');
	if (row) {
		$('#dlg-add-customer-payment').dialog('open').dialog('setTitle', '查看已支付收款');
		$('#tb-customer-payment-new #btn-add-payment-record').hide();
		$('#dlg-buttons-add-customer-payment #save-customer-payment').hide();
		$('#dlg-add-customer-payment #fm-customer-payment #dg-customer-payment-new').datagrid('hideColumn', 'action');
		$('#dlg-add-customer-payment #fm-customer-payment').form('load', row);
		if(row.customerBean) 
		{
			$('#dlg-add-customer-payment #fm-customer-payment #customerId').combobox('setValue', row.customerBean.id);
			$('#dlg-add-customer-payment #fm-customer-payment #customerId').combobox('setText', row.customerBean.shortName);
		}
		
		$('#dlg-add-customer-payment #fm-customer-payment #dg-customer-payment-new').datagrid('loadData', []);
		$('#dlg-add-customer-payment #fm-customer-payment #dg-customer-payment-new').datagrid('loading');
		if(row.paymentRecordBeans) 
		{
			$('#dlg-add-customer-payment #fm-customer-payment #dg-customer-payment-new').datagrid('loadData', row.paymentRecordBeans);
			$('#dlg-add-customer-payment #fm-customer-payment #dg-customer-payment-new').datagrid('loaded');
		}
		/* $.post('<c:url value='/customerPay/getModelById.html' />', {paymentId : row.id}, 
			function(result) 
			{
				if(result && result.paymentRecordBeans) 
				{
					$('#dlg-add-customer-payment #fm-customer-payment #dg-customer-payment-new').datagrid('loadData', result.paymentRecordBeans);
				}
				$('#dlg-add-customer-payment #fm-customer-payment #dg-customer-payment-new').datagrid('loaded');
			}, 
			'json'); */
	}
	
}

function formatter_customerPaymentAction (value,row,index) 
{
	if (row.editing){
		if(row.id == '') 
		{
			var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="saveCustomerPayment(this)">&nbsp;</a>&nbsp;&nbsp;';
			var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteCustomerPayment(this)">&nbsp;</a>';
			return s+d;
		}
		else 
		{
			var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="saveCustomerPayment(this,' + row.id + ')">&nbsp;</a>&nbsp;&nbsp;';
			var c = '<a href="#" class="icon-no" style="display:inline-block;width:16px;" title="取消" onclick="cancelCustomerPayment(this)">&nbsp;</a>';
			return s+c;
		}
	} else {
		if(row.id == '') 
		{
			var e = '<a href="#" class="icon-edit" style="display:inline-block;width:16px;" title="编辑" onclick="editCustomerPayment(this)">&nbsp;</a>&nbsp;&nbsp;';
			var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteCustomerPayment(this)">&nbsp;</a>';
			return e+d;
		}
		else 
		{
			var e = '<a href="#" class="icon-edit" style="display:inline-block;width:16px;" title="编辑" onclick="editCustomerPayment(this)">&nbsp;</a>&nbsp;&nbsp;';
			var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteCustomerPayment(this,' + row.id + ')">&nbsp;</a>';
			return e+d;
		}
	}
}
var customerPayment_editingIndex;
var customerPayment_rowId;
var customerPayment_onBeforeEdit = function(index,row){
       row.editing = true;
	customerPayment_editingIndex = index;
	customerPayment_rowId = row.id;
       updateCustomerPaymentActions(index);
   }
var customerPayment_onAfterEdit = function(index,row){
       row.editing = false;
	customerPayment_editingIndex = undefined;
	customerPayment_rowId = '';
       updateCustomerPaymentActions(index);
   }
var customerPayment_onCancelEdit = function(index,row){
       row.editing = false;
	customerPayment_editingIndex = undefined;
	customerPayment_rowId = '';
       updateCustomerPaymentActions(index);
   }
function updateCustomerPaymentActions(index){
	$('#dg-customer-payment-new').datagrid('updateRow',{
		index: index,
		row:{}
	});
}
function editCustomerPayment(target){
	if(customerPayment_editingIndex == undefined) 
	{
		$('#dg-customer-payment-new').datagrid('beginEdit', getRowIndex(target));
	}
}
function deleteCustomerPayment(target, rowId){
	if(rowId == undefined) 
	{
		$('#dg-customer-payment-new').datagrid('deleteRow', getRowIndex(target));
		setCustomerPaymentRecordList();
	}
	else 
	{
		if(customerPayment_editingIndex == undefined) 
		{
			$.messager.confirm('确认','您确认要删除?',function(r){
				if (r){
					$('#dg-customer-payment-new').datagrid('deleteRow', getRowIndex(target));
					$('#dg-customer-payment-new').datagrid('acceptChanges');
				}
			});
		}
	}
}
function saveCustomerPayment(target, rowId){
	//console.log(rowId);
	if ($('#dg-customer-payment-new').datagrid('validateRow', getRowIndex(target)))
	{
		var paymentAccountEditor = $('#dg-customer-payment-new').datagrid('getEditor', {index:getRowIndex(target), field:'paymentAccount'});
		var paymentAccount = $(paymentAccountEditor.target).combobox('getText');
		$('#dg-customer-payment-new').datagrid('getRows')[customerPayment_editingIndex]['accountId'] = paymentAccount;
		var paymentWayEditor = $('#dg-customer-payment-new').datagrid('getEditor', {index:getRowIndex(target), field:'paymentWay'});
		var paymentWay = $(paymentWayEditor.target).combobox('getText');
		$('#dg-customer-payment-new').datagrid('getRows')[customerPayment_editingIndex]['paymentWayName'] = paymentWay;
		//console.log(accountingMode);
		//console.log(customerPaymentName);
		$('#dg-customer-payment-new').datagrid('endEdit', getRowIndex(target));
		if(rowId == undefined) 
		{
		}
		else 
		{
		}
		setCustomerPaymentRecordList();
	}
}
var setCustomerPaymentRecordList = function () 
{
	var paymentRecordList = "";
	var allRows = $('#dg-customer-payment-new').datagrid('getRows');
	//console.log(allRows.length);
	for(var i = 0; i < allRows.length; i++) 
	{
		var customerPaymentRecord = "";
		var item = allRows[i];
		customerPaymentRecord += (item.id +","+item.paymentAccount+","+item.paid+","+item.paymentWay+","+item.note);
		paymentRecordList += customerPaymentRecord + ";";
	}
	//console.log(storageList.substring(0, storageList.length-1));
	$('#dlg-add-customer-payment #fm-customer-payment #paymentRecordList').val(paymentRecordList);
	if(allRows.length > 0) 
	{
		$('#dlg-buttons-add-customer-payment #save-customer-payment').linkbutton('enable');
	}
	else 
	{
		$('#dlg-buttons-add-customer-payment #save-customer-payment').linkbutton('disable');
	}
}
var checkCustomerPaymentRecord = function()
{
	if(customerPayment_editingIndex != undefined) 
	{
		$('#dg-customer-payment-new').datagrid('endEdit', customerPayment_editingIndex);
		setCustomerPaymentRecordList();
	}
}
function cancelCustomerPayment(target){
	$('#dg-customer-payment-new').datagrid('cancelEdit', getRowIndex(target));
}
function insertCustomerPayment(){
	if(customerPayment_editingIndex == undefined) 
	{
		var row = $('#dg-customer-payment-new').datagrid('getSelected');
		if (row){
			var index = $('#dg-customer-payment-new').datagrid('getRowIndex', row);
		} else {
			index = 0;
		}
		$('#dg-customer-payment-new').datagrid('insertRow', {
			index: index,
			row:{
				id:''
			}
		});
		$('#dg-customer-payment-new').datagrid('selectRow',index);
		$('#dg-customer-payment-new').datagrid('beginEdit',index);
	}
}

	var viewCustomerPaymentDue = function () 
	{
		$('#dlg-view-customer-payment-dued #dg-customerPaymentDued').datagrid('loadData', []);
		$('#dlg-view-customer-payment-dued').dialog('open').dialog('setTitle', '已到期的客户应收款');
		if('${sessionScope.autoShowDueCustomerPayment}' == '0') 
		{
			$('#autoShowDueCustomerPayment').hide();
		}
		
		var requestUrl = "<c:url value='/customerPay/getCustomerPaymentDue.html' />";
		var param = {};
		loadGridData('#dlg-view-customer-payment-dued #dg-customerPaymentDued', requestUrl, param);
	}

	var viewCustomerPaymentDetail = function () 
	{
		var row = $('#dg-customer').datagrid('getSelected');
		if(row) 
		{
			$('#dlg-view-customer-payment #dg-customerPayment').datagrid('loadData', []);
			$('#dlg-view-customer-payment').dialog('open').dialog('setTitle', row.shortName + '的收款记录');
			$('#toolbar-customer-payment-detail #advanceSearchSpan #customerId').combobox('setValue', row.id);
			$('#toolbar-customer-payment-detail #advanceSearchSpan #customerId').combobox('setText', row.shortName);
			$('#dlg-view-customer-payment #dg-customerPayment').datagrid('reload');
		}
	}
	function doSearchCustomer(value) {
		$('#dg-customer').datagrid('load', {
			searchKey : value
		});
	}
	
	$(function() {
    	if('${sessionScope.autoShowDueCustomerPayment}' == '1') 
		{
	    	viewCustomerPaymentDue();
		}
    });
	var customerPayment_autoShowDuePayment = function(checkbox) 
	{
		var requestUrl = "<c:url value='/customerPay/setAutoShowDuePayment.html' />";
		var requestParam = {autoShowDueCustomerPayment:'1'};
		if(checkbox.checked)
		{
			requestParam = {autoShowDueCustomerPayment:'0'};
		}
		$.post(requestUrl, requestParam, function(result){}, 'json');
	}