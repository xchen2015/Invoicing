<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="com.pinfly.purchasecharge.core.util.PurchaseChargeConstants"%>
<%@ page import="com.pinfly.purchasecharge.core.config.PurchaseChargeProperties"%>

<% response.setContentType ("text/javascript"); %>

var outOrder_editIndex = undefined;
    function endEditing(){
        if (outOrder_editIndex == undefined){return true;}
        if ($('#dg-outorderItem-new').datagrid('validateRow', outOrder_editIndex)){
            var ed = $('#dg-outorderItem-new').datagrid('getEditor', {index:outOrder_editIndex,field:'goodsId'});
			if(ed) 
			{
				var productname = $(ed.target).combobox('getText');
				$('#dg-outorderItem-new').datagrid('getRows')[outOrder_editIndex]['name'] = productname;
				var depositoryNameEditor = $('#dg-outorderItem-new').datagrid('getEditor', {index:outOrder_editIndex,field:'depository'});
				$('#dg-outorderItem-new').datagrid('getRows')[outOrder_editIndex]['depositoryName'] = $(depositoryNameEditor.target).combobox('getText');
				$('#dg-outorderItem-new').datagrid('endEdit', outOrder_editIndex);
			}
            outOrder_editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }
    function onClickRow(index){
        if (outOrder_editIndex != index){
            if (endEditing()){
                $('#dg-outorderItem-new').datagrid('selectRow', index)
                        .datagrid('beginEdit', index);
                outOrder_editIndex = index;
            } else {
                $('#dg-outorderItem-new').datagrid('selectRow', outOrder_editIndex);
            }
        }
    }
    function append(){
        if (endEditing()){
			//calculateItemCount (0, 0);
			
            $('#dg-outorderItem-new').datagrid('appendRow',{id:0, goodsId:'', unit:'', unitPrice:'', amount:'', sum:'', note:''});
            outOrder_editIndex = $('#dg-outorderItem-new').datagrid('getRows').length-1;
            $('#dg-outorderItem-new').datagrid('selectRow', outOrder_editIndex)
                    .datagrid('beginEdit', outOrder_editIndex);
        }
    }
    function removeit(){
        if (outOrder_editIndex == undefined){return;}
        $('#dg-outorderItem-new').datagrid('cancelEdit', outOrder_editIndex)
                .datagrid('deleteRow', outOrder_editIndex);
        outOrder_editIndex = undefined;
		
		//calculateItemCount (0, 0);
    }
    function accept(){
        if (endEditing()){
            $('#dg-outorderItem-new').datagrid('acceptChanges');
            var allRows = $('#dg-outorderItem-new').datagrid('getRows');
            //console.log(allRows.length+' rows in current page!');
            var orderItemList = "";
            for(var i = 0; i < allRows.length; i++) 
            {
    	        //console.log(allRows[i]);
            	var orderItem = "";
    	        var item = allRows[i];
				//console.log(item);
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
    	        orderItemList += orderItem + ";"
            }
            //console.log(orderItemList.substring(0, orderItemList.length-1));
            $('#outorderItemList').val(orderItemList);
        }
    }
    
    // order item
    function onSelectGoods (record) 
    {
    	//var goods = JSON.stringify(record);
		//console.log("outOrder_editIndex--->" + outOrder_editIndex);
		if (outOrder_editIndex != undefined)
		{
			setValidateOutorderMsg('');
			
			var depositoryEditor = $('#dg-outorderItem-new').datagrid('getEditor', {index:outOrder_editIndex, field:'depository'});
			if(record.preferedDepositoryBean) 
			{
				$(depositoryEditor.target).combobox('setValue', record.preferedDepositoryBean.id);
			}
			
			var unitEditor = $('#dg-outorderItem-new').datagrid('getEditor', {index:outOrder_editIndex, field:'unit'});
			if(record.unitBean) 
			{
				unitEditor.target.val(record.unitBean.name);
			}
			// get latest goods retail price for specific customer
			var unitPriceEditor = $('#dg-outorderItem-new').datagrid('getEditor', {index:outOrder_editIndex, field:'unitPrice'});
			var customerId = $('#dlg-outorder-new #fm-outorder #customerId').combobox('getValue');
			$.post('<c:url value='/outOrder/getLatestGoodsUnitPrice.html' />', {customerId:customerId, goodsId:record.id}, 
					function(result) 
					{
						var latestOrderItem = result;
						if(latestOrderItem != '') 
						{
							unitPriceEditor.target.numberbox('setValue', latestOrderItem.unitPrice);
						}
					}, 
				'json');
			
			$('#dlg-buttons-outorder-new #save-outorder-btn').linkbutton('enable');
			$('#dlg-buttons-outorder-new #preview-outorder-btn').linkbutton('enable');
		}
    }
	function onChangeAmount (newValue, oldValue) 
	{
		//console.log("newValue-->"+newValue + " oldValue-->"+oldValue);
		if (outOrder_editIndex != undefined)
		{
			if(newValue != '') 
			{
				setValidateOutorderMsg('');
				
				var sumEditor = $('#dg-outorderItem-new').datagrid('getEditor', {index:outOrder_editIndex, field:'sum'});
				var unitPriceEditor = $('#dg-outorderItem-new').datagrid('getEditor', {index:outOrder_editIndex, field:'unitPrice'});
				var unitPrice = unitPriceEditor.target.val();
				//sumEditor.target.numberbox('setValue', unitPrice * newValue);
				sumEditor.target.numberbox('setValue', new Number(newValue).mul(unitPrice));
				
				//calculateItemCount (newValue, unitPrice * newValue);
				calculateItemCount (newValue, new Number(newValue).mul(unitPrice));
				$('#dlg-buttons-outorder-new #save-outorder-btn').linkbutton('enable');
				$('#dlg-buttons-outorder-new #preview-outorder-btn').linkbutton('enable');
			}
		}
	}
	function onChangeUnitPrice (newValue, oldValue) 
	{
		//console.log("newValue-->"+newValue + " oldValue-->"+oldValue);
		if (outOrder_editIndex != undefined)
		{
			setValidateOutorderMsg('');
			
			$('#dlg-buttons-outorder-new #save-outorder-btn').linkbutton('enable');
			$('#dlg-buttons-outorder-new #preview-outorder-btn').linkbutton('enable');
		
			var sumEditor = $('#dg-outorderItem-new').datagrid('getEditor', {index:outOrder_editIndex, field:'sum'});
			var amountEditor = $('#dg-outorderItem-new').datagrid('getEditor', {index:outOrder_editIndex, field:'amount'});
			var amount = amountEditor.target.val();
			//sumEditor.target.numberbox('setValue', amount * newValue);
			sumEditor.target.numberbox('setValue', new Number(newValue).mul(amount));
			
			//calculateItemCount (amount, amount * newValue);
			calculateItemCount (amount, new Number(newValue).mul(amount));
		}
	}
	function calculateItemCount (amount, sum) 
	{
		accept();
	
		// 计算合计在页脚
		var allRows = $('#dg-outorderItem-new').datagrid('getRows');
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
		$('#dlg-outorder-new #fm-outorder #dg-outorderItem-new').datagrid('reloadFooter',[{goodsId:'合计', amount:totalAmount, sum:totalSum}]);
		calculateDiscount (sum);
	}
	function calculateDiscount (sum) 
	{
		var allRows = $('#dg-outorderItem-new').datagrid('getRows');
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
			var value = $('#dlg-outorder-new #fm-outorder #discount').numberbox('getValue');
			//var receivable = value * totalSum;
			var receivable = totalSum.mul(value);
			$('#dlg-outorder-new #fm-outorder #receivable').numberbox('setValue', receivable);
		}
		else 
		{
			$('#dlg-outorder-new #fm-outorder #receivable').numberbox('setValue', 0);
		}
	}
    
    var newOrderCallback = function () 
    {
    	$('#dg-outorderItem-new').datagrid('loadData', []);
    	$('#dg-outorderItem-new').datagrid('reloadFooter', []);
    	$('#dlg-outorder-new #fm-outorder #userCreated').val('${sessionScope.login_user.userId}');
    	$('#dlg-outorder-new #fm-outorder #operateUserFullName').textbox('setValue', '${sessionScope.login_user.fullName}');
    	//$('#dlg-buttons-outorder #save-outorder-btn').linkbutton('enable');
    	var orderType = 'OUT';
    	if('OUT_RETURN' == outorder_typeCode) 
    	{
    		orderType = 'OUT_RETURN';
    	}
		$('#dlg-outorder-new #fm-outorder #outorder-typeCode').val(orderType);
		$('#dlg-outorder-new #fm-outorder #discount').numberspinner('setValue', 1.00);
		$('#dlg-outorder-new #fm-outorder #paidMoney').numberbox('setValue', 0);
		$('#dlg-outorder-new #fm-outorder #accountingMoney').numberbox('setValue', 0);
		
		var todayDate = new Date().format("yyyy-MM-dd");
		$('#dlg-outorder-new #fm-outorder #createTime').datebox('setValue', todayDate);
		var param = {orderTypeCode:'OUT'};
		if('OUT_RETURN' == outorder_typeCode)
		{
			param = {orderTypeCode:'OUT_RETURN'};
		}
		$.post('<c:url value='/outOrder/generateOrderBid.html' />', param,
    			function(result) 
    			{
					$('#dlg-outorder-new #fm-outorder #bid').textbox('setValue', result);
    			}
			,'text');
    }
    
    var viewOrderCallback = function () 
    {
    	var checkedRows = $('#dg-outorder').datagrid('getChecked');
		var order = checkedRows[0];
		if(order.customerBean) 
		{
			$('#dlg-outorder-view #fm-outorder #customerId').val(order.customerBean.id);
			$('#dlg-outorder-view #fm-outorder #customerName').val(order.customerBean.shortName);
			$('#dlg-outorder-view #fm-outorder #userSignedTo').val(order.customerBean.userSignedTo);
			var preferedContact = getPreferedCustomerContact (order.customerBean);
			if(preferedContact) 
			{
				$('#dlg-outorder-view #fm-outorder #contactName').val(preferedContact.name);
				$('#dlg-outorder-view #fm-outorder #contactPhone').val(preferedContact.mobilePhone);
			}
		}
		if(order.projectBean) 
		{
			$('#dlg-outorder-view #fm-outorder #projectId').val(order.projectBean.name);
		}
		if(order.paymentAccountBean) 
		{
			$('#dlg-outorder-view #fm-outorder #paymentAccount').val(order.paymentAccountBean.name);
		}
		
		if(order.statusCode == 'NEW') 
		{
			$('#dlg-outorder-view #fm-outorder #statusCode').val('新建');
		}
		else if(order.statusCode == 'COMPLETED') 
		{
			$('#dlg-outorder-view #fm-outorder #statusCode').val('完成');
		}
		else if(order.statusCode == 'CANCEL') 
		{
			$('#dlg-outorder-view #fm-outorder #statusCode').val('取消');
		}
    	//$('#dg-outorderItem-view').datagrid('load', {orderId : $('#dg-outorder').datagrid('getSelected').id});
		loadGridData ('#dlg-outorder-view #dg-outorderItem-view', '<c:url value='/outOrder/getModelById.html' />', {orderId : order.id});
    	//$('#dlg-buttons-outorder #save-outorder-btn').linkbutton('disable');
		$('#dlg-outorder-view #order-id-span').html(order.bid);
    }
    
    function gotoPrintDialog () 
    {
    	//$('#dlg-outorder-view').dialog('close');
		var order = $('#dg-outorder').datagrid('getSelected');
		
		// fill print order header and footer for edit
		var createDate = order.createTime.substring(0, 10);
		$('#div-print-outorder-edit #div-outorder-header #customer').html(order.customerBean.shortName);
		var preferedContact = getPreferedCustomerContact (order.customerBean);
		if(preferedContact) 
		{
			$('#div-print-outorder-edit #div-outorder-header #contact').val(preferedContact.name);
			$('#div-print-outorder-edit #div-outorder-header #phone').val(preferedContact.mobilePhone);
			$('#div-print-outorder-edit #div-outorder-header #address').val(preferedContact.address);
		}
		$('#div-print-outorder-edit #div-outorder-header #createDate').html(createDate);
		$('#div-print-outorder-edit #div-outorder-header #orderId').html(order.bid);
		$('#div-print-outorder-edit #div-outorder-footer #user').html(order.userCreated);
		$('#div-print-outorder-edit #div-outorder-footer #salesman').html(order.customerBean.userSignedTo);
		
		if('OUT' == order.typeCode)
		{
			$('#div-print-outorder-edit #div-print-outorder-title #titleHeader').val('<%=PurchaseChargeProperties.getInstance ().getConfig (PurchaseChargeConstants.PRINT_ORDER_HEADER)%>');
			$('#div-print-outorder-edit #div-print-outorder-title #titleSpan').html('销售出货单');
		}
		else 
		{
			$('#div-print-outorder-edit #div-print-outorder-title #titleHeader').val('<%=PurchaseChargeProperties.getInstance ().getConfig (PurchaseChargeConstants.PRINT_ORDER_HEADER)%>');
			$('#div-print-outorder-edit #div-print-outorder-title #titleSpan').html('销售退货单');
		}
		
		$('#dlg-print-outorder').dialog('open');
		// fill print order table
    	checkPrintPrice ($('#div-print-outorder-setting #checkMoney')[0]);
    }
	
	function printOrder () 
    {
		var titleHeader = $('#div-print-outorder-edit #div-print-outorder-title #titleHeader').val();
		var titleSpan = $('#div-print-outorder-edit #div-print-outorder-title #titleSpan').html();
		var printTitle = titleHeader + titleSpan;
		$('#div-print-outorder #div-print-outorder-title').html(printTitle);
	
		var contact = $('#div-print-outorder-edit #div-outorder-header #contact').val();
		var phone = $('#div-print-outorder-edit #div-outorder-header #phone').val();
		var address = $('#div-print-outorder-edit #div-outorder-header #address').val();
		var attachment = $('#div-print-outorder-edit #div-outorder-footer #attachment').val();
		
		var checkMoneyBox = $('#div-print-outorder-setting #checkMoney')[0];
		var order = $('#dg-outorder').datagrid('getSelected');
		if(checkMoneyBox.checked) 
		{
			var requestUrl = '<c:url value='/outOrder/printOrder.html' />';
			var param = {orderId:order.id, template:'orderPrintTemplate.html', title:printTitle, contactName:contact, contactPhone:phone, contactAddress:address, footer:attachment};
			$.post(requestUrl, param,
				function(result) 
				{
					//console.log(result);
					var $table2 = $('#div-print-outorder table');
					if($table2.get(0)) 
					{
						$table2.replaceWith($(result));
					}
					else 
					{
						$('#div-print-outorder').append($(result));
					}
					$('#div-print-outorder').print();
				}, 
			'text');
		}
		else 
		{
			var requestUrl = '<c:url value='/outOrder/printOrder.html' />';
			var param = {orderId:order.id, template:'orderPrintNoMoneyTemplate.html', title:printTitle, contactName:contact, contactPhone:phone, contactAddress:address, footer:attachment};
			$.post(requestUrl, param,
				function(result) 
				{
					//console.log(result);
					var $table2 = $('#div-print-outorder table');
					if($table2.get(0)) 
					{
						$table2.replaceWith($(result));
					}
					else 
					{
						$('#div-print-outorder').append($(result));
					}
					$('#div-print-outorder').print();
				}, 
			'text');
		}
	}
	function checkPrintPrice (checkbox) 
	{
		$('#div-print-outorder-edit').mask('加载中...');
		var order = $('#dg-outorder').datagrid('getSelected');
		if(checkbox.checked) 
		{
			var requestUrl = '<c:url value='/outOrder/getOrderItemTable.html' />';
			var param = {orderId:order.id, templateFile:'orderTableTemplate.html'};
			$.post(requestUrl, param,
    			function(result) 
    			{
    				//console.log(result);
					$('#div-print-outorder-edit').unmask();
    				
					var $table2 = $('#div-print-outorder-edit #outorder-table table');
					if($table2.get(0)) 
					{
						$table2.replaceWith($(result));
					}
					else 
					{
						$('#div-print-outorder-edit #outorder-table').append($(result));
					}
    			}, 
    		'text');
		}
		else 
		{
			var requestUrl = '<c:url value='/outOrder/getOrderItemTable.html' />';
			var param = {orderId:order.id, templateFile:'orderTableNoMoneyTemplate.html'};
			$.post(requestUrl, param,
    			function(result) 
    			{
    				//console.log(result);
					$('#div-print-outorder-edit').unmask();
    				
					var $table2 = $('#div-print-outorder-edit #outorder-table table');
					if($table2.get(0)) 
					{
						$table2.replaceWith($(result));
					}
					else 
					{
						$('#div-print-outorder-edit #outorder-table').append($(result));
					}
    			}, 
    		'text');
		}
	}
	
	function newOutOrder() 
	{
		if('OUT' == outorder_typeCode || '0' == outorder_typeCode) 
		{
			newModel('#dlg-outorder-new', '新增销售出货单', '#fm-outorder', '<c:url value='/outOrder/addModel.html' />', newOrderCallback);
		}
		else 
		{
			newModel('#dlg-outorder-new', '新增销售退货单', '#fm-outorder', '<c:url value='/outOrder/addModel.html' />', newOrderCallback);
		}
		$('#dlg-outorder-new #tb-outorderList #btn-add-orderItem').linkbutton('disable');
		$('#dlg-outorder-new #tb-outorderList #btn-delete-orderItem').linkbutton('disable');
		$('#dlg-buttons-outorder-new #save-outorder-btn').linkbutton('disable');
		$('#dlg-buttons-outorder-new #preview-outorder-btn').linkbutton('disable');
		
		$('#dlg-outorder-new #fm-outorder #paidMoney').numberbox('disable');
		//$('#dlg-outorder-new #fm-outorder #accountingType').combobox('disable');
		//$('#dlg-outorder-new #fm-outorder #accountingMoney').numberbox('disable'); 
	}
	function viewOutOrder()
	{
		var dialogTitle;
		var singleChecked;
		if('OUT' == outorder_typeCode) 
		{
			var checkedRows = $('#dg-outorder').datagrid('getChecked');
			if (checkedRows.length > 1) 
			{
				dialogTitle = '销售出货对账单';
				singleChecked = false;
			}
			else if(checkedRows.length == 1) 
			{
				dialogTitle = '查看销售出货单';
				singleChecked = true;
			}
		}
		else if('OUT_RETURN' == outorder_typeCode)
		{
			var checkedRows = $('#dg-outorder').datagrid('getChecked');
			if (checkedRows.length > 1) 
			{
				dialogTitle = '销售退货对账单';
				singleChecked = false;
			}
			else if(checkedRows.length == 1)  
			{
				dialogTitle = '查看销售退货单';
				singleChecked = true;
			}
		}
		else 
		{
			var checkedRows = $('#dg-outorder').datagrid('getChecked');
			if (checkedRows.length > 1) 
			{
				dialogTitle = '销售对账单';
				singleChecked = false;
			}
			else if(checkedRows.length == 1)  
			{
				dialogTitle = '查看销售单';
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
			
			$('#dlg-multi-outorder-view').dialog('open').dialog('setTitle', dialogTitle);
			loadGridData ('#dlg-multi-outorder-view #dg-outorderItem-view', '<c:url value='/outOrder/getOrderItemsByOrderIds.html' />', {orderIds : orderIds});
		}
		else 
		{
			editModel('#dg-outorder', '#dlg-outorder-view', dialogTitle, '#fm-outorder', '', viewOrderCallback);
		}
	}
	function viewOutOrderForDelivery()
	{
		var dialogTitle = '查看销售出货单';
		var singleChecked;
		var checkedRows = $('#dg-outorder').datagrid('getChecked');
		if (checkedRows.length > 1) 
		{
			singleChecked = false;
		}
		else if(checkedRows.length == 1) 
		{
			singleChecked = true;
		}
		
		if(singleChecked == false) 
		{
			var orderIds = "";
			for(var i = 0; i < checkedRows.length; i++) 
			{
				orderIds += (checkedRows[i].id + ";");
			}
			orderIds = orderIds.substring(0, orderIds.length-1);
			
			$('#dlg-multi-outorder-view').dialog('open').dialog('setTitle', dialogTitle);
			loadGridData ('#dlg-multi-outorder-view #dg-outorderItem-view', '<c:url value='/outOrder/getOrderItemsByOrderIds.html' />', {orderIds : orderIds});
		}
		else 
		{
			editModel('#dg-outorder', '#dlg-outorder-view', dialogTitle, '#fm-outorder', '', viewOrderCallback);
		}
	}
	function deleteOutOrder()
	{
		destroyMultipleModel('#dg-outorder', '销售单', '<c:url value='/outOrder/deleteModels.html' />');
	}
	function updateOutOrderStatus(rowIndex) 
	{
		$('#dg-outorder').datagrid('selectRow', rowIndex);
		var row = $('#dg-outorder').datagrid('getSelected');
		if(row) 
		{
			editModel('#dg-outorder', '#dlg-update-status', '编辑销售出货单状态', '#fm-outorder', '<c:url value='/outOrder/updateModel.html' />');
			$('#dlg-update-status #fm-outorder #customerName').val(row.customerBean.shortName);
			var orderStatusData;
			if('NEW' == row.statusCode) 
			{
				orderStatusData = [{value:'NEW', text:'<spring:message code="order.option.new" />'}, {value:'CANCELED', text:'<spring:message code="order.option.cancel" />'}];
			}
			else if('SHIPPED' == row.statusCode) 
			{
				orderStatusData = [{value:'SHIPPED', text:'已发货'}, {value:'COMPLETED', text:'<spring:message code="order.option.finished" />'}];
			}
			$('#dlg-update-status #fm-outorder #orderStatus').combobox('loadData', orderStatusData);
		}
	}
	function updateOutOrderDelivery(rowIndex) 
	{
		$('#dg-outorder').datagrid('selectRow', rowIndex);
		var row = $('#dg-outorder').datagrid('getSelected');
		if(row) 
		{
			var requestUrl = '<c:url value='/outOrderDelivery/updateModel.html' />';
			$('#dlg-update-delivery #fm-outorder-delivery').form('clear');
			if(row.deliveryBean) 
			{
				$('#dlg-update-delivery #fm-outorder-delivery').form('load', row.deliveryBean);
				if(row.deliveryBean.deliveryCompanyBean) 
				{
					$('#dlg-update-delivery #fm-outorder-delivery #deliveryCompany').combobox('setValue', row.deliveryBean.deliveryCompanyBean.id);
					$('#dlg-update-delivery #fm-outorder-delivery #deliveryCompany').combobox('setText', row.deliveryBean.deliveryCompanyBean.name);
				}
			}
			else 
			{
				var todayDate = new Date().format("yyyy-MM-dd");
				$('#dlg-update-delivery #fm-outorder-delivery #deliveryDateCreated').datebox('setValue', todayDate);
				$('#dlg-update-delivery #fm-outorder-delivery #deliveryAmount').numberbox('setValue', 1);
				$('#dlg-update-delivery #fm-outorder-delivery #deliveryFee').numberbox('setValue', 0);
			}
			$('#dlg-update-delivery').dialog('open').dialog('setTitle', '出库单' + row.bid + '发货');
			saveOrEditUrl = requestUrl;
			$('#dlg-update-delivery #fm-outorder-delivery #orderId').val(row.id);
		}
	}
	
	var onChangeDiscount = function(value)
	{
		accept ();
		calculateDiscount (0);
	}
	var onClickOutOrderRow = function (rowIndex, rowData) 
	{
		$('#toolbar-outorder #btn-viewOutorder').linkbutton('enable');
		//$('#toolbar-outorder #btn-deleteOutorder').linkbutton('enable');
	}
	var onLoadOutOrderSuccess = function (data) 
	{
		$('#toolbar-outorder #btn-viewOutorder').linkbutton('disable');
		$('#toolbar-outorder #btn-deleteOutorder').linkbutton('disable');
	}
	var onCheckAllOutOrder = function(rows) 
	{
		$('#toolbar-outorder #btn-deleteOutorder').linkbutton('enable');
		$('#toolbar-outorder #btn-viewOutorder').linkbutton('enable');
	}
	var onUnCheckOutOrder = function(rowIndex,rowData) 
	{
		var rows = $('#dg-outorder').datagrid('getChecked');
		if (rows.length == 0) {
			$('#toolbar-outorder #btn-deleteOutorder').linkbutton('disable');
			$('#toolbar-outorder #btn-viewOutorder').linkbutton('disable');
		}
	}
	
	var onSelectCustomer = function(record) 
	{
		setValidateOutorderMsg('');
		var requestUrl = "<c:url value='/customerPay/checkCustomerUnPayMoney.html' />";
		var param = {custoId: record.id};
		$.post(requestUrl, param, 
			function(result) 
			{
				if(result != "true") 
				{
					$.messager.alert('警告','当前客户欠款已超过最大限值!!!','warning');
				}
			}
		);
		
		//$('#dlg-outorder-new #fm-outorder #customerId').val(record.id);
		$('#dlg-outorder-new #fm-outorder #userSignedTo').textbox('setValue', record.userSignedTo);
		$('#dlg-outorder-new #tb-outorderList #btn-add-orderItem').linkbutton('enable');
		$('#dlg-outorder-new #tb-outorderList #btn-delete-orderItem').linkbutton('enable');
	}
	var onChangeCustomer = function(newValue, oldValue) 
	{
		if(newValue == undefined) 
		{
			$('#dlg-outorder-new #fm-outorder #userSignedTo').val('');
			$('#dlg-outorder-new #tb-outorderList #btn-add-orderItem').linkbutton('disable');
			$('#dlg-outorder-new #tb-outorderList #btn-delete-orderItem').linkbutton('disable');
		}
	}
	
	var onSelectStartDate = function (date) 
	{
		$('#toolbar-outorder #advanceSearchSpan #timeFrame').combobox('setValue', 'CUSTOMIZE');
	}
	var onSelectTimeFrame = function(record) 
	{
		var startDate = generateStartDate(record.value);
		var endDate = new Date().format("yyyy-MM-dd");
		$('#toolbar-outorder #advanceSearchSpan #startDate').datebox('setValue', startDate);
		$('#toolbar-outorder #advanceSearchSpan #endDate').datebox('setValue', endDate);
	}
	var outorder_typeCode = 'OUT';
	var getSelectedOutOrderTypeCode = function() 
	{
		var $typeCode = document.getElementsByName("outorder_typeCode_mode");
		for(var i = 0; i < $typeCode.length; i ++) 
		{
			if($typeCode[i].checked) 
			{
				outorder_typeCode = $typeCode[i].value;
				break;
			}
		}
		//console.log('outord typecode --- ' + outorder_typeCode);
	}
	
	var onSearchOrderById = function () 
	{
		var orderId = $('#toolbar-outorder #orderIdSearchSpan #orderId').textbox('getValue');
		if(orderId != '') 
		{
			getSelectedOutOrderTypeCode();
			$('#dg-outorder').datagrid('reload');
		}
		else 
		{
			$.messager.alert('警告','请填写订单号!','warning');
		}
	}
	var onSearchOrderByAdvance = function () 
	{
		$('#toolbar-outorder #orderIdSearchSpan #orderId').textbox('setValue', '');
		var startDate = $('#toolbar-outorder #advanceSearchSpan #startDate').combo('getValue');
		var endDate = $('#toolbar-outorder #advanceSearchSpan #endDate').combo('getValue');
		var customerId = $('#toolbar-outorder #advanceSearchSpan #customerId').combo('getValue');
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
		
		getSelectedOutOrderTypeCode();
		$('#dg-outorder').datagrid('reload');
	}
	
	var onBeforeLoadOutOrder = function (param) 
	{
		var orderId = $('#toolbar-outorder #orderIdSearchSpan #orderId').textbox('getValue');
		var startDate = $('#toolbar-outorder #advanceSearchSpan #startDate').combo('getValue');
		var endDate = $('#toolbar-outorder #advanceSearchSpan #endDate').combo('getValue');
		var customerId = $('#toolbar-outorder #advanceSearchSpan #customerId').combo('getValue');
		if(customerId == undefined) 
		{
			customerId = '';
		}
		if(startDate == '' && endDate == '' && customerId == '') 
		{
			var timeFrame = $('#toolbar-outorder #advanceSearchSpan #timeFrame').combo('getValue');
			startDate = generateStartDate(timeFrame);
			endDate = new Date().format("yyyy-MM-dd");
			$('#toolbar-outorder #advanceSearchSpan #startDate').datebox('setValue', startDate);
			$('#toolbar-outorder #advanceSearchSpan #endDate').datebox('setValue', endDate);
			
			startDate = $('#toolbar-outorder #advanceSearchSpan #startDate').combo('getValue');
			endDate = $('#toolbar-outorder #advanceSearchSpan #endDate').combo('getValue');
		}
		
		getSelectedOutOrderTypeCode();
		if(param.page == undefined || param.page == 0) 
		{
			param.page = 1;
		}
		$('#dg-outorder').datagrid('options').url = "<c:url value='/outOrder/getModelBySearchForm.html' />?type="+outorder_typeCode+"&startDate="+startDate+"&endDate="+endDate+"&customerId="+customerId+"&page="+param.page+"&orderBid="+orderId;
		return true;
	}
	var onBeforeLoadOutOrderDelivery = function (param) 
	{
		var orderId = $('#toolbar-outorder #orderIdSearchSpan #orderId').textbox('getValue');
		var startDate = $('#toolbar-outorder #advanceSearchSpan #startDate').combo('getValue');
		var endDate = $('#toolbar-outorder #advanceSearchSpan #endDate').combo('getValue');
		var customerId = $('#toolbar-outorder #advanceSearchSpan #customerId').combo('getValue');
		if(customerId == undefined) 
		{
			customerId = '';
		}
		if(startDate == '' && endDate == '' && customerId == '') 
		{
			var timeFrame = $('#toolbar-outorder #advanceSearchSpan #timeFrame').combo('getValue');
			startDate = generateStartDate(timeFrame);
			endDate = new Date().format("yyyy-MM-dd");
			$('#toolbar-outorder #advanceSearchSpan #startDate').datebox('setValue', startDate);
			$('#toolbar-outorder #advanceSearchSpan #endDate').datebox('setValue', endDate);
			
			startDate = $('#toolbar-outorder #advanceSearchSpan #startDate').combo('getValue');
			endDate = $('#toolbar-outorder #advanceSearchSpan #endDate').combo('getValue');
		}
		
		if(param.page == undefined || param.page == 0) 
		{
			param.page = 1;
		}
		$('#dg-outorder').datagrid('options').url = "<c:url value='/outOrderDelivery/getModelBySearchForm.html' />?type="+outorder_typeCode+"&startDate="+startDate+"&endDate="+endDate+"&customerId="+customerId+"&page="+param.page+"&orderBid="+orderId;
		return true;
	}
	var onDblClickOutOrderRow = function(rowIndex, rowData) 
	{
		viewOutOrder();
	}
	var previewOutOrder = function() 
	{
		if(!$('#dlg-outorder-new #fm-outorder').form('validate')) 
		{
			return false;
		}
		$('#dlg-buttons-outorder-preview #submit-outorder-btn').linkbutton('enable');
		try 
		{
			accept();
			$('#dlg-outorder-preview').dialog('open').dialog('setTitle', '出库单预览');
			var customerName = $('#dlg-outorder-new #fm-outorder #customerId').combobox('getText');
			$('#dlg-outorder-preview #fm-outorder #customerName').val(customerName);
			var createTime = $('#dlg-outorder-new #fm-outorder #createTime').textbox('getValue');
			$('#dlg-outorder-preview #fm-outorder #createTime').val(createTime);
			var orderBid = $('#dlg-outorder-new #fm-outorder #bid').textbox('getValue');
			$('#dlg-outorder-preview #fm-outorder #bid').val(orderBid);
			var operateUser = $('#dlg-outorder-new #fm-outorder #operateUserFullName').val();
			$('#dlg-outorder-preview #fm-outorder #operateUserFullName').val(operateUser);
			var userSignedTo = $('#dlg-outorder-new #fm-outorder #userSignedTo').val();
			$('#dlg-outorder-preview #fm-outorder #userSignedTo').val(userSignedTo);
			var projectName = $('#dlg-outorder-new #fm-outorder #projectId').combobox('getText');
			$('#dlg-outorder-preview #fm-outorder #projectName').val(projectName);
			var result = $('#dlg-outorder-new #fm-outorder #dg-outorderItem-new').datagrid('getData');
			$('#dlg-outorder-preview #fm-outorder #dg-outorderItem-preview').datagrid('loadData', result.rows);
			var discount = $('#dlg-outorder-new #fm-outorder #discount').numberspinner('getValue');
			$('#dlg-outorder-preview #fm-outorder #discount').val(discount);
			var receivable = $('#dlg-outorder-new #fm-outorder #receivable').numberbox('getValue');
			$('#dlg-outorder-preview #fm-outorder #receivable').val(receivable);
			var paidMoney = $('#dlg-outorder-new #fm-outorder #paidMoney').numberbox('getValue');
			$('#dlg-outorder-preview #fm-outorder #paidMoney').val(paidMoney);
			var paymentAccount = $('#dlg-outorder-new #fm-outorder #paymentAccount').combobox('getText');
			$('#dlg-outorder-preview #fm-outorder #paymentAccount').val(paymentAccount);
			var comment = $('#dlg-outorder-new #fm-outorder #comment').val();
			$('#dlg-outorder-preview #fm-outorder #comment').val(comment);
		}
		catch(err) 
		{
			$('#dlg-buttons-outorder-preview #submit-outorder-btn').linkbutton('disable');
			setValidateOutorderMsg('<font color="red">' + err + '</font>');
		}
	}
	function submitOutOrder() 
	{
		var userSignedTo = $('#dlg-outorder-new #userSignedTo').val();
		if(userSignedTo == '' || userSignedTo == undefined) 
		{
			setValidateOutorderMsg('<font color="red">' + '请检查客户所属业务员！' + '</font>');
			return;
		}
		try 
		{
			//accept();
			saveModel('#dg-outorder', '#dlg-outorder-new', '#fm-outorder', null, accept);
		}
		catch(err) 
		{
			setValidateOutorderMsg('<font color="red">' + err + '</font>');
		}
	}
	function setValidateOutorderMsg (msg) 
	{
		$('#dlg-outorder-new #fm-outorder #tb-outorderList #validateOutorderMsg').html(msg);
	}
	var formatter_orderStatus = function (value,row,index) 
	{
		if(value == 'CANCELED') 
		{
			return "<span class='icon-cancel' style='display:block;' title='订单已取消，不可编辑'>&nbsp;</span>";
		}
		else if(value == 'COMPLETED') 
		{
			var deliveryInfo = ""; 
			if(row && row.deliveryBean) 
			{
				deliveryInfo = row.deliveryBean.deliveryCompanyBean.name + ': ' + row.deliveryBean.number + '&#10;'
					+ '数量: ' + row.deliveryBean.amount + ' 运费: ' + row.deliveryBean.fee + '&#10;'
					+ row.deliveryBean.userCreated + ' ' + row.deliveryBean.dateCreated;
			}
			return "<span class='icon-ok' style='display:block;' title='"+ deliveryInfo +"'>&nbsp;</span>";
		}
		else if(value == 'NEW') 
		{
			return "<a href='javascript:void(0)' style='color:#000; font-weight:bold' id='"+row.id+"' onclick='updateOutOrderDelivery("+index+")'>" + "点击发货" + "</a>"
				+ "&nbsp;&nbsp;&nbsp;" 
				+ "<a href='javascript:void(0)' style='color:#000; font-weight:bold' id='"+row.id+"' onclick='updateOutOrderStatus("+index+")'>" + "编辑状态" + "</a>";
		}
		else if(value == 'SHIPPED') 
		{
			if(row && row.deliveryBean) 
			{
				return "<a href='javascript:void(0)' style='color:#000; font-weight:bold' id='"+row.id+"' onclick='updateOutOrderDelivery("+index+")'>" + "已发货" + "</a>" 
					+ "&nbsp;&nbsp;&nbsp;" 
					+ "<a href='javascript:void(0)' style='color:#000; font-weight:bold' id='"+row.id+"' onclick='updateOutOrderStatus("+index+")'>" + "编辑状态" + "</a>";
			}
		}
	}
	var formatter_orderStatusForDelivery = function (value,row,index) 
	{
		if(value == 'CANCELED') 
		{
			return "<span class='icon-cancel' style='display:block;' title='订单已取消，不可编辑'>&nbsp;</span>";
		}
		else if(value == 'COMPLETED') 
		{
			var deliveryInfo = ""; 
			if(row && row.deliveryBean) 
			{
				deliveryInfo = row.deliveryBean.deliveryCompanyBean.name + ': ' + row.deliveryBean.number + '&#10;'
					+ '数量: ' + row.deliveryBean.amount + ' 运费: ' + row.deliveryBean.fee + '&#10;'
					+ row.deliveryBean.userCreated + ' ' + row.deliveryBean.dateCreated;
			}
			return "<span class='icon-ok' style='display:block;' title='"+ deliveryInfo +"'>&nbsp;</span>";
		}
		else if(value == 'NEW') 
		{
			return "<a href='javascript:void(0)' style='color:#000; font-weight:bold' id='"+row.id+"' onclick='updateOutOrderDelivery("+index+")'>" + "点击发货" + "</a>";
		}
		else if(value == 'SHIPPED') 
		{
			if(row && row.deliveryBean) 
			{
				return "<a href='javascript:void(0)' style='color:#000; font-weight:bold' id='"+row.id+"' onclick='updateOutOrderDelivery("+index+")'>" + "已发货" + "</a>";
			}
		}
	}
	
	function formatter_order_goods(value,row,index) 
	{
		return row.depositoryName;
	}
	function showManageDeliveryCompany() 
	{
		$('#dlg-manage-deliveryCompany').dialog('open');
		$('#dg-deliveryCompany').datagrid('options').url = '<c:url value='/deliveryCompany/getAllModel.html' />';
		$('#dg-deliveryCompany').datagrid('reload');
	}
	function showAllAvailableProject() 
	{
		$(this).combobox('reload', '<c:url value='/project/getAllModel.html' />');
	}
	$.extend($.fn.validatebox.defaults.rules, {
		orderOut_checkPaidAndPaymentAccount: {
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
			message: '当本次收款额大于0时，结算账号不能为空'
		}
	});
	$.extend($.fn.validatebox.defaults.rules, {
		orderOut_checkAccountingTypeAndAccounting: {
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
			message: '当本次收款额大于0时，结算账号不能为空'
		}
	});
	
	function outOrder_selectPaidAccount(record)
	{
		if(record && record.id) 
		{
			$('#dlg-outorder-new #fm-outorder #paidMoney').numberbox('enable');
			//$('#dlg-outorder-new #fm-outorder #accountingType').combobox('enable');
		}
		else 
		{
			$('#dlg-outorder-new #fm-outorder #paidMoney').numberbox('disable');
			//$('#dlg-outorder-new #fm-outorder #accountingType').combobox('disable');
		}
	}
	function outOrder_selectAccounting(record)
	{
		if(record && record.id) 
		{
			$('#dlg-outorder-new #fm-outorder #accountingMoney').numberbox('enable'); 
		}
		else 
		{
			$('#dlg-outorder-new #fm-outorder #accountingMoney').numberbox('disable'); 
		}
	}
	var outOrder_onClickAccountingType = function() 
	{
		$(this).combobox('reload', '<c:url value='/accountingType/getTypeByMode.html' />');
	}
	function outOrder_accountingTypeGroupFormatter(group)
	{
		if(group == 'IN_COME') 
		{
			return '收入';
		}
		if(group == 'OUT_LAY') 
		{
			return '支出';
		}
	}
