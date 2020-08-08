<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="com.pinfly.purchasecharge.core.util.PurchaseChargeConstants"%>
<%@ page import="com.pinfly.purchasecharge.core.config.PurchaseChargeProperties"%>

<% response.setContentType ("text/javascript"); %>

var inOrder_editIndex = undefined;
function inOrder_endEditing(){
	if (inOrder_editIndex == undefined){return true;}
	if ($('#dg-inorderItem-new').datagrid('validateRow', inOrder_editIndex)){
		var ed = $('#dg-inorderItem-new').datagrid('getEditor', {index:inOrder_editIndex,field:'goodsId'});
		if(ed) 
		{
			var productname = $(ed.target).combobox('getText');
			$('#dg-inorderItem-new').datagrid('getRows')[inOrder_editIndex]['name'] = productname;
			var depositoryNameEditor = $('#dg-inorderItem-new').datagrid('getEditor', {index:inOrder_editIndex,field:'depository'});
			$('#dg-inorderItem-new').datagrid('getRows')[inOrder_editIndex]['depositoryName'] = $(depositoryNameEditor.target).combobox('getText');
			
			$('#dg-inorderItem-new').datagrid('endEdit', inOrder_editIndex);
		}
		inOrder_editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function inOrder_onClickRow(index){
	if (inOrder_editIndex != index){
		if (inOrder_endEditing()){
			$('#dg-inorderItem-new').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			inOrder_editIndex = index;
		} else {
			$('#dg-inorderItem-new').datagrid('selectRow', inOrder_editIndex);
		}
	}
}
function inOrder_append(){
	if (inOrder_endEditing()){
		//calculateInItemCount (0, 0);
	
		$('#dg-inorderItem-new').datagrid('appendRow',{id:0, goodsId:'', unit:'', unitPrice:'', amount:'', sum:'', note:''});
		inOrder_editIndex = $('#dg-inorderItem-new').datagrid('getRows').length-1;
		$('#dg-inorderItem-new').datagrid('selectRow', inOrder_editIndex)
				.datagrid('beginEdit', inOrder_editIndex);
	}
}
function inOrder_removeit(){
	if (inOrder_editIndex == undefined){return;}
	$('#dg-inorderItem-new').datagrid('cancelEdit', inOrder_editIndex)
			.datagrid('deleteRow', inOrder_editIndex);
	inOrder_editIndex = undefined;
	
	//calculateInItemCount (0, 0);
}
function inOrder_accept(){
	if (inOrder_endEditing()){
		$('#dg-inorderItem-new').datagrid('acceptChanges');
		var allRows = $('#dg-inorderItem-new').datagrid('getRows');
		//console.log(allRows.length+' rows in current page!');
		var orderItemList = "";
		for(var i = 0; i < allRows.length; i++) 
		{
			//console.log(allRows[i]);
			var orderItem = "";
			var item = allRows[i];
			if(item.goodsId == '' || item.goodsId == undefined) 
			{
				throw '您选择的货物不存在！';
			}
			if(item.amount <= 0) 
			{
				throw '货物数量必须大于0！';
			}
			if(item.sum <= 0) 
			{
				throw '货物金额必须大于0！';
			}
			orderItem += (item.id +","+item.goodsId+","+item.unit+","+item.unitPrice+","+item.amount+","+item.sum+","+item.depository+","+item.note);
			orderItemList += orderItem + ";";
		}
		//console.log(orderItemList.substring(0, orderItemList.length-1));
		$('#inorderItemList').val(orderItemList);
	}
}

function setValidateInorderMsg(msg) 
{
	$('#dlg-inorder-new #fm-inorder #tb-inorderList #validateOutorderMsg').html(msg);
}

// order item
function inOrder_onSelectGoods (record) 
{
	//console.log("inOrder_editIndex--->" + inOrder_editIndex);
	if (inOrder_editIndex != undefined)
	{
		setValidateInorderMsg('');
		
		var depositoryEditor = $('#dg-inorderItem-new').datagrid('getEditor', {index:inOrder_editIndex, field:'depository'});
		if(record.preferedDepositoryBean) 
		{
			$(depositoryEditor.target).combobox('setValue', record.preferedDepositoryBean.id);
		}
		
		var unitEditor = $('#dg-inorderItem-new').datagrid('getEditor', {index:inOrder_editIndex, field:'unit'});
		if(record.unitBean) 
		{
			unitEditor.target.val(record.unitBean.name);
		}
		// get latest goods import price for specific customer
		var unitPriceEditor = $('#dg-inorderItem-new').datagrid('getEditor', {index:inOrder_editIndex, field:'unitPrice'});
		var customerId = $('#dlg-inorder-new #fm-inorder #customerId').combobox('getValue');
		$.post('<c:url value='/inOrder/getLatestGoodsUnitPrice.html' />', {providerId:customerId, goodsId:record.id}, 
				function(result) 
				{
					var latestOrderItem = result;
					if(latestOrderItem != '') 
					{
						unitPriceEditor.target.numberbox('setValue', latestOrderItem.unitPrice);
					}
				}, 
			'json');
		$('#dlg-buttons-inorder-new #save-inorder-btn').linkbutton('enable');
		$('#dlg-buttons-inorder-new #preview-inorder-btn').linkbutton('enable');
	}
}

function inOrder_onChangeAmount (newValue, oldValue) 
{
	//console.log("newValue-->"+newValue + " oldValue-->"+oldValue);
	if (inOrder_editIndex != undefined)
	{
		setValidateInorderMsg('');
		var sumEditor = $('#dg-inorderItem-new').datagrid('getEditor', {index:inOrder_editIndex, field:'sum'});
		var unitPriceEditor = $('#dg-inorderItem-new').datagrid('getEditor', {index:inOrder_editIndex, field:'unitPrice'});
		var unitPrice = unitPriceEditor.target.val();
		sumEditor.target.numberbox('setValue', new Number(unitPrice).mul(newValue));
		
		calculateInItemCount (newValue, new Number(unitPrice).mul(newValue));
	}
}
function inOrder_onChangeUnitPrice (newValue, oldValue) 
{
	//console.log("newValue-->"+newValue + " oldValue-->"+oldValue);
	if (inOrder_editIndex != undefined)
	{
		setValidateInorderMsg('');
		var sumEditor = $('#dg-inorderItem-new').datagrid('getEditor', {index:inOrder_editIndex, field:'sum'});
		var amountEditor = $('#dg-inorderItem-new').datagrid('getEditor', {index:inOrder_editIndex, field:'amount'});
		var amount = amountEditor.target.val();
		//sumEditor.target.numberbox('setValue', amount * newValue);
		sumEditor.target.numberbox('setValue', new Number(newValue).mul(amount));
		
		//calculateInItemCount (amount, amount * newValue);
		calculateInItemCount (amount, new Number(newValue).mul(amount));
	}
}

function calculateInItemCount (amount, sum) 
{
	inOrder_accept();

	// 计算合计在页脚
	var allRows = $('#dg-inorderItem-new').datagrid('getRows');
	var totalAmount = 0;
	var totalSum = 0;
	for(var i = 0; i < allRows.length; i++) 
	{
		var item = allRows[i];
		if(item.goodsId != '' && item.goodsId != undefined) 
		{
			totalAmount += parseInt(item.amount);
			//totalSum += parseFloat(item.sum);
			totalSum = totalSum.add(item.sum);
		}
	}
	/*if(amount > 0 && sum > 0) 
	{
		totalAmount += parseInt(amount);
		//totalSum += parseFloat(sum);
		totalSum = totalSum.add(sum);
	}*/
	$('#dlg-inorder-new #fm-inorder #dg-inorderItem-new').datagrid('reloadFooter',[{goodsId:'合计', amount:totalAmount, sum:totalSum}]);
	inOrder_calculateDiscount (sum);
}
function inOrder_calculateDiscount (sum) 
{
	var allRows = $('#dg-inorderItem-new').datagrid('getRows');
	if(allRows.length > 0) 
	{
		var totalSum = 0;
		for(var i = 0; i < allRows.length; i++) 
		{
			var item = allRows[i];
			if(item.goodsId != '' && item.goodsId != undefined) 
			{
				//totalSum += parseFloat(item.sum);
				totalSum = totalSum.add(item.sum);
			}
		}
		/*if(sum > 0) 
		{
			//totalSum += sum;
			totalSum = totalSum.add(sum);
		}*/
		var value = $('#dlg-inorder-new #fm-inorder #discount').numberbox('getValue');
		//var receivable = value * totalSum;
		var receivable = totalSum.mul(value);
		$('#dlg-inorder-new #fm-inorder #receivable').numberbox('setValue', receivable);
	}
	else 
	{
		$('#dlg-inorder-new #fm-inorder #receivable').numberbox('setValue', 0);
	}
}

var inOrder_newOrderCallback = function () 
{
	$('#dg-inorderItem-new').datagrid('loadData', []);
	$('#dg-inorderItem-new').datagrid('reloadFooter', []);
	$('#dlg-inorder-new #fm-inorder #userCreated').val('${sessionScope.login_user.userId}');
	$('#dlg-inorder-new #fm-inorder #operateUserFullName').textbox('setValue', '${sessionScope.login_user.fullName}');
	//$('#dlg-buttons-order #save-order-btn').linkbutton('enable');
	var orderType = 'IN';
	if('IN_RETURN' == inorder_typeCode)
	{
		orderType = 'IN_RETURN';
	}
	$('#dlg-inorder-new #fm-inorder #inorder-typeCode').val(orderType);
	$('#dlg-inorder-new #fm-inorder #discount').numberspinner('setValue', 1.00);
	$('#dlg-inorder-new #fm-inorder #paidMoney').numberbox('setValue', 0);
	
	var todayDate = new Date().format("yyyy-MM-dd");
	$('#dlg-inorder-new #fm-inorder #createTime').datebox('setValue', todayDate);
	var param = {orderTypeCode:'IN'};
	if('IN_RETURN' == inorder_typeCode)
	{
		param = {orderTypeCode:'IN_RETURN'};
	}
	$.post('<c:url value='/outOrder/generateOrderBid.html' />', param,
			function(result) 
			{
				$('#dlg-inorder-new #fm-inorder #bid').textbox('setValue', result);
			}
		,'text');
}

var inOrder_viewOrderCallback = function () 
{
	var checkedRows = $('#dg-inorder').datagrid('getChecked');
	var order = checkedRows[0];
	if(order.customerBean) 
	{
		$('#dlg-inorder-view #fm-inorder #customerId').val(order.customerBean.id);
		$('#dlg-inorder-view #fm-inorder #customerName').val(order.customerBean.shortName);
		var preferedContact = getPreferedCustomerContact (order.customerBean);
		if(preferedContact) 
		{
			$('#dlg-inorder-view #fm-inorder #contactName').val(preferedContact.name);
			$('#dlg-inorder-view #fm-inorder #contactPhone').val(preferedContact.mobilePhone);
		}
	}
	if(order.paymentAccountBean) 
	{
		$('#dlg-inorder-view #fm-inorder #paymentAccount').val(order.paymentAccountBean.name);
	}
	if(order.statusCode == 'NEW') 
	{
		$('#dlg-inorder-view #fm-inorder #statusCode').val('新建');
	}
	else if(order.statusCode == 'COMPLETED') 
	{
		$('#dlg-inorder-view #fm-inorder #statusCode').val('完成');
	}
	else if(order.statusCode == 'CANCEL') 
	{
		$('#dlg-inorder-view #fm-inorder #statusCode').val('取消');
	}
	
	//$('#dg-inorderItem-view').datagrid('load', {orderId : $('#dg-inorder').datagrid('getSelected').id});
	loadGridData ('#dg-inorderItem-view', '<c:url value='/inOrder/getModelById.html' />', {orderId : order.id});
	//$('#dlg-buttons-inorder #save-inorder-btn').linkbutton('disable');
	$('#dlg-inorder-view #order-id-span').html(order.bid);
}

function inOrder_gotoPrintDialog () 
{
	//$('#dlg-inorder-view').dialog('close');
	var order = $('#dg-inorder').datagrid('getSelected');
	
	// fill print order header and footer for edit
	var createDate = order.createTime.substring(0, 10);
	$('#div-print-inorder-edit #div-inorder-header #provider').html(order.customerBean.shortName);
	var preferedContact = getPreferedCustomerContact (order.customerBean);
	if(preferedContact) 
	{
		$('#div-print-inorder-edit #div-inorder-header #contact').val(preferedContact.name);
		$('#div-print-inorder-edit #div-inorder-header #phone').val(preferedContact.mobilePhone);
		$('#div-print-inorder-edit #div-inorder-header #address').val(preferedContact.address);
	}
	$('#div-print-inorder-edit #div-inorder-header #createDate').html(createDate);
	$('#div-print-inorder-edit #div-inorder-header #orderId').html(order.bid);
	$('#div-print-inorder-edit #div-inorder-footer #user').html(order.userCreated);
	
	$('#div-print-inorder #div-inorder-header #provider').html(order.customerBean.shortName);
	$('#div-print-inorder #div-inorder-header #createDate').html(createDate);
	$('#div-print-inorder #div-inorder-header #orderId').html(order.bid);
	$('#div-print-inorder #div-inorder-footer #user').html(order.userCreated);
	
	if('IN' == order.typeCode)
	{
		$('#div-print-inorder-edit #div-print-inorder-title').html('<%=PurchaseChargeProperties.getInstance ().getConfig (PurchaseChargeConstants.PRINT_ORDER_HEADER)%>' + '采购进货单');
		$('#div-print-inorder #div-print-inorder-title').html('<%=PurchaseChargeProperties.getInstance ().getConfig (PurchaseChargeConstants.PRINT_ORDER_HEADER)%>' + '采购进货单');
	}
	else if('IN_RETURN' == order.typeCode)
	{
		$('#div-print-inorder-edit #div-print-inorder-title').html('<%=PurchaseChargeProperties.getInstance ().getConfig (PurchaseChargeConstants.PRINT_ORDER_HEADER)%>' + '采购退货单');
		$('#div-print-inorder #div-print-inorder-title').html('<%=PurchaseChargeProperties.getInstance ().getConfig (PurchaseChargeConstants.PRINT_ORDER_HEADER)%>' + '采购退货单');
	}
	
	$('#dlg-print-inorder').dialog('open');
	// fill print order table
	var requestUrl = '<c:url value='/inOrder/getOrderItemTable.html' />';
	var param = {orderId :order.id, templateFile:'orderTableTemplate.html'};
	$('#div-print-inorder-edit').mask('加载中...');
	$.post(requestUrl, param,
			function(result) 
			{
				//console.log(result);
				$('#div-print-inorder-edit').unmask();
				var $table = $('#div-print-inorder #inorder-table table');
				if($table.get(0)) 
				{
					$table.replaceWith($(result));
				}
				else 
				{
					$('#div-print-inorder #inorder-table').append($(result));
				}
				var $table2 = $('#div-print-inorder-edit #inorder-table table');
				if($table2.get(0)) 
				{
					$table2.replaceWith($(result));
				}
				else 
				{
					$('#div-print-inorder-edit #inorder-table').append($(result));
				}
			}, 
		'text');
}

function inOrder_printOrder () 
{
	var contact = $('#div-print-inorder-edit #div-inorder-header #contact').val();
	var phone = $('#div-print-inorder-edit #div-inorder-header #phone').val();
	var address = $('#div-print-inorder-edit #div-inorder-header #address').val();
	var attachment = $('#div-print-inorder-edit #div-inorder-footer #attachment').val();
	
	$('#div-print-inorder #div-inorder-header #contact').html(contact);
	$('#div-print-inorder #div-inorder-header #phone').html(phone);
	$('#div-print-inorder #div-inorder-header #address').html(address);
	$('#div-print-inorder #div-inorder-footer #attachment').html(attachment);

	$('#div-print-inorder').print();
}

function newInOrder() 
{
	if('IN' == inorder_typeCode || '0' == inorder_typeCode) 
	{
		newModel('#dlg-inorder-new', '新增采购进货单', '#fm-inorder', '<c:url value='/inOrder/addModel.html' />', inOrder_newOrderCallback);
	}
	else
	{
		newModel('#dlg-inorder-new', '新增采购退货单', '#fm-inorder', '<c:url value='/inOrder/addModel.html' />', inOrder_newOrderCallback);
	}
	$('#dlg-inorder-new #tb-inorderList #btn-add-orderItem').linkbutton('disable');
	$('#dlg-inorder-new #tb-inorderList #btn-delete-orderItem').linkbutton('disable');
	$('#dlg-buttons-inorder-new #save-inorder-btn').linkbutton('disable');
	$('#dlg-buttons-inorder-new #preview-inorder-btn').linkbutton('disable');
	
	$('#dlg-inorder-new #fm-inorder #paidMoney').numberbox('disable');
}
function viewInOrder()
{
	var dialogTitle;
	var singleChecked;
	if('IN' == inorder_typeCode) 
	{
		var checkedRows = $('#dg-inorder').datagrid('getChecked');
		if (checkedRows.length > 1) 
		{
			dialogTitle = '采购进货对账单';
			singleChecked = false;
		}
		else if (checkedRows.length == 1) 
		{
			dialogTitle = '查看采购进货单';
			singleChecked = true;
		}
	}
	else if('IN_RETURN' == inorder_typeCode)
	{
		var checkedRows = $('#dg-inorder').datagrid('getChecked');
		if (checkedRows.length > 1) 
		{
			dialogTitle = '采购退货对账单';
			singleChecked = false;
		}
		else if (checkedRows.length == 1) 
		{
			dialogTitle = '查看采购退货单';
			singleChecked = true;
		}
	}
	else 
	{
		var checkedRows = $('#dg-inorder').datagrid('getChecked');
		if (checkedRows.length > 1) 
		{
			dialogTitle = '采购对账单';
			singleChecked = false;
		}
		else if (checkedRows.length == 1) 
		{
			dialogTitle = '查看采购单';
			singleChecked = true;
		}
	}
	
	if(singleChecked == false) 
	{
		var orderIds = "";
		for(var i = 0; i < checkedRows.length; i++) 
		{
			orderIds += (checkedRows[i].id + ";");
		}
		orderIds = orderIds.substring(0, orderIds.length-1);
		
		$('#dlg-multi-inorder-view').dialog('open').dialog('setTitle', dialogTitle);
		loadGridData ('#dlg-multi-inorder-view #dg-inorderItem-view', '<c:url value='/inOrder/getOrderItemsByOrderIds.html' />', {orderIds : orderIds});
	}
	else 
	{
		editModel('#dg-inorder', '#dlg-inorder-view', dialogTitle, '#fm-inorder', '', inOrder_viewOrderCallback);
	}
}
function deleteInOrder()
{
	destroyMultipleModel('#dg-inorder', '采购单', '<c:url value='/inOrder/deleteModels.html' />');
}
var inOrder_onChangeDiscount = function(value)
{
	inOrder_accept ();
	inOrder_calculateDiscount (0);
}
var onClickInOrderRow = function (rowIndex, rowData) 
{
	$('#toolbar-inorder #btn-viewInorder').linkbutton('enable');
	//$('#toolbar-inorder #btn-deleteInorder').linkbutton('enable');
}
var onLoadInOrderSuccess = function (data) 
{
	$('#toolbar-inorder #btn-viewInorder').linkbutton('disable');
	$('#toolbar-inorder #btn-deleteInorder').linkbutton('disable');
	$('#toolbar-inorder #btn-editStatus').linkbutton('disable');
}
var onCheckAllInOrder = function(rows) 
{
	$('#toolbar-inorder #btn-deleteInorder').linkbutton('enable');
	$('#toolbar-inorder #btn-viewInorder').linkbutton('enable');
}
var onUnCheckInOrder = function(rowIndex,rowData) 
{
	var rows = $('#dg-inorder').datagrid('getChecked');
	if (rows.length == 0) {
		$('#toolbar-inorder #btn-deleteInorder').linkbutton('disable');
		$('#toolbar-inorder #btn-viewInorder').linkbutton('disable');
	}
}

var onSelectProvider = function(record) 
{
	setValidateInorderMsg('');
	//$('#dlg-inorder-new #fm-inorder #customerId').val(record.id);
	$('#dlg-inorder-new #tb-inorderList #btn-add-orderItem').linkbutton('enable');
	$('#dlg-inorder-new #tb-inorderList #btn-delete-orderItem').linkbutton('enable');
}
var onChangeProvider = function(newValue, oldValue) 
{
	if(newValue == undefined) 
	{
		$('#dlg-inorder-new #tb-inorderList #btn-add-orderItem').linkbutton('disable');
		$('#dlg-inorder-new #tb-inorderList #btn-delete-orderItem').linkbutton('disable');
	}
}

var inOrder_onSelectStartDate = function (date) 
{
	$('#toolbar-inorder #advanceSearchSpan #timeFrame').combobox('setValue', 'CUSTOMIZE');
}
var inOrder_onSelectTimeFrame = function(record) 
{
	var startDate = generateStartDate(record.value);
	var endDate = new Date().format("yyyy-MM-dd");
	$('#toolbar-inorder #advanceSearchSpan #startDate').datebox('setValue', startDate);
	$('#toolbar-inorder #advanceSearchSpan #endDate').datebox('setValue', endDate);
}
var inorder_typeCode = "IN";
var getSelectedInOrderTypeCode = function() 
{
	var $typeCode = document.getElementsByName("inorder_typeCode_mode");
	for(var i = 0; i < $typeCode.length; i ++) 
	{
		if($typeCode[i].checked) 
		{
			inorder_typeCode = $typeCode[i].value;
			break;
		}
	}
	//console.log('inord typecode --- ' + inorder_typeCode);
}

var inOrder_onSearchOrderById = function () 
{
	var orderId = $('#toolbar-inorder #orderIdSearchSpan #orderId').textbox('getValue');
	if(orderId != '') 
	{
		getSelectedInOrderTypeCode();
		$('#dg-inorder').datagrid('reload');
	}
	else 
	{
		$.messager.alert('警告','请填写订单号!','warning');
	}
}
var inOrder_onSearchOrderByAdvance = function () 
{
	$('#toolbar-inorder #orderIdSearchSpan #orderId').textbox('setValue', '');
	var startDate = $('#toolbar-inorder #advanceSearchSpan #startDate').combo('getValue');
	var endDate = $('#toolbar-inorder #advanceSearchSpan #endDate').combo('getValue');
	var customerId = $('#toolbar-inorder #advanceSearchSpan #customerId').combo('getValue');
	if((startDate != '' && endDate == '') || (startDate == '' && endDate != '')) 
	{
		$.messager.alert('警告','开始时间和结束时间必须同时填!','warning');
		return;
	}
	if(startDate == '' && endDate == '' && customerId == '') 
	{
		$.messager.alert('警告','请填写查询条件!','warning');
		return;
	}
	
	getSelectedInOrderTypeCode();
	$('#dg-inorder').datagrid('reload');
}
var onBeforeLoadInOrder = function (param) 
{
	var orderId = $('#toolbar-inorder #orderIdSearchSpan #orderId').textbox('getValue');
	var startDate = $('#toolbar-inorder #advanceSearchSpan #startDate').combo('getValue');
	var endDate = $('#toolbar-inorder #advanceSearchSpan #endDate').combo('getValue');
	var customerId = $('#toolbar-inorder #advanceSearchSpan #customerId').combo('getValue');
	if(customerId == undefined) 
	{
		customerId = '';
	}
	if(startDate == '' && endDate == '' && customerId == '') 
	{
		var timeFrame = $('#toolbar-inorder #advanceSearchSpan #timeFrame').combo('getValue');
		startDate = generateStartDate(timeFrame);
		endDate = new Date().format("yyyy-MM-dd");
		$('#toolbar-inorder #advanceSearchSpan #startDate').datebox('setValue', startDate);
		$('#toolbar-inorder #advanceSearchSpan #endDate').datebox('setValue', endDate);
		
		startDate = $('#toolbar-inorder #advanceSearchSpan #startDate').combo('getValue');
		endDate = $('#toolbar-inorder #advanceSearchSpan #endDate').combo('getValue');
	}
	
	getSelectedInOrderTypeCode();
	if(param.page == undefined || param.page == 0) 
	{
		param.page = 1;
	}
	$('#dg-inorder').datagrid('options').url = "<c:url value='/inOrder/getModelBySearchForm.html' />?type="+inorder_typeCode+"&startDate="+startDate+"&endDate="+endDate+"&customerId="+customerId+"&page="+param.page+"&orderBid="+orderId;
	return true;
}
var onDblClickInOrderRow = function(rowIndex, rowData) 
{
	viewInOrder();
}
var beforeSubmitInOrder = function() 
{
	if(!$('#dlg-inorder-new #fm-inorder').form('validate')) 
	{
		return false;
	}
	$('#dlg-buttons-inorder-new-review #submit-inorder-btn').linkbutton('enable');
	
	try 
	{
		inOrder_accept();
	
		$('#dlg-inorder-new-review').dialog('open').dialog('setTitle', '采购单预览');
		var customerName = $('#dlg-inorder-new #fm-inorder #customerId').combobox('getText');
		$('#dlg-inorder-new-review #fm-inorder #customerName').val(customerName);
		var createTime = $('#dlg-inorder-new #fm-inorder #createTime').datebox('getValue');
		$('#dlg-inorder-new-review #fm-inorder #createTime').val(createTime);
		var operateUser = $('#dlg-inorder-new #fm-inorder #operateUserFullName').val();
		$('#dlg-inorder-new-review #fm-inorder #operateUserFullName').val(operateUser);
		var orderBid = $('#dlg-inorder-new #fm-inorder #bid').val();
		$('#dlg-inorder-new-review #fm-inorder #bid').val(orderBid);
		var result = $('#dlg-inorder-new #fm-inorder #dg-inorderItem-new').datagrid('getData');
		$('#dlg-inorder-new-review #fm-inorder #dg-inorderItem-new-review').datagrid('loadData', result.rows);
		var discount = $('#dlg-inorder-new #fm-inorder #discount').numberspinner('getValue');
		$('#dlg-inorder-new-review #fm-inorder #discount').val(discount);
		var receivable = $('#dlg-inorder-new #fm-inorder #receivable').numberbox('getValue');
		$('#dlg-inorder-new-review #fm-inorder #receivable').val(receivable);
		var paidMoney = $('#dlg-inorder-new #fm-inorder #paidMoney').numberbox('getValue');
		$('#dlg-inorder-new-review #fm-inorder #paidMoney').val(paidMoney);
		var paymentAccount = $('#dlg-inorder-new #fm-inorder #paymentAccount').combobox('getText');
		$('#dlg-inorder-new-review #fm-inorder #paymentAccount').val(paymentAccount);
		var comment = $('#dlg-inorder-new #fm-inorder #comment').val();
		$('#dlg-inorder-new-review #fm-inorder #comment').val(comment);
	}
	catch(err) 
	{
		$('#dlg-buttons-inorder-new-review #submit-inorder-btn').linkbutton('disable');
		setValidateInorderMsg('<font color="red">' + err + '</font>');
	}
}
var submitInOrder = function() 
{
	try 
	{
		//inOrder_accept();
	
		saveModel('#dg-inorder', '#dlg-inorder-new', '#fm-inorder', null, inOrder_accept);
	}
	catch(err) 
	{
		setValidateInorderMsg('<font color="red">' + err + '</font>');
	}
}
function formatter_orderGoodsDepository(value,row,index) 
{
	return row.depositoryName;
}
$.extend($.fn.validatebox.defaults.rules, {
	checkOrderInPaidAndPaymentAccount: {
		validator: function(value, param){
			var paymentAccount;
			if(param[0].startsWith('#')) 
			{
				paymentAccount = $(param[0]).combobox('getValue');
			}
			value = parseFloat(value);
			if(value != 0 && paymentAccount == '') 
			{
				return false;
			}
			return true;
		},
		message: '当本次付款额大于0时，结算账号不能为空'
	}
});

	function inOrder_selectPaidAccount(record)
	{
		if(record && record.id) 
		{
			$('#dlg-inorder-new #fm-inorder #paidMoney').numberbox('enable');
		}
		else 
		{
			$('#dlg-inorder-new #fm-inorder #paidMoney').numberbox('disable');
		}
	}
