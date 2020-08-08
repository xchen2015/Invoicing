<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<% response.setContentType ("text/javascript"); %>

	<!--  -->
	var onPaymentAccountSelectStartDate = function (date) 
	{
		$('#toolbar-paymentDetail #advanceSearchSpan #timeFrame').combobox('setValue', 'CUSTOMIZE');
	}
	var onPaymentAccountSelectTimeFrame = function(record) 
	{
		setInitialPaymentDetailTimeFrame(record.value);
	}
	
	var onSearchPaymentAccountByAdvance = function () 
	{
		$('#dlg-view-paymentAccount #dg-payment-detail').datagrid('loadData', []);
		var startDate = $('#toolbar-paymentDetail #advanceSearchSpan #startDate').combo('getValue');
		var endDate = $('#toolbar-paymentDetail #advanceSearchSpan #endDate').combo('getValue');
		var paymentId = $('#toolbar-paymentDetail #advanceSearchSpan #paymentId').val();
		if((startDate != '' && endDate == '') || (startDate == '' && endDate != '')) 
		{
			$.messager.alert('警告','开始时间和结束时间必须同时填!','warning');
			return;
		}
		if(startDate == '' && endDate == '') 
		{
			$.messager.alert('警告','请填写查询条件!','warning');
			return;
		}
		
		$('#dg-payment-detail').datagrid('reload');
	}
	var onBeforeLoadPaymentDetail = function (param) 
	{
		var startDate = $('#toolbar-paymentDetail #advanceSearchSpan #startDate').combo('getValue');
		var endDate = $('#toolbar-paymentDetail #advanceSearchSpan #endDate').combo('getValue');
		var paymentId = $('#toolbar-paymentDetail #advanceSearchSpan #paymentId').val();
		var transferTypeCode = $('#toolbar-paymentDetail #advanceSearchSpan #transferTypeCode').combo('getValue');
		if(startDate == '' && endDate == '') 
		{
			var timeFrame = $('#toolbar-paymentDetail #advanceSearchSpan #timeFrame').combo('getValue');
			setInitialPaymentDetailTimeFrame(timeFrame);
			
			startDate = $('#toolbar-paymentDetail #advanceSearchSpan #startDate').combo('getValue');
			endDate = $('#toolbar-paymentDetail #advanceSearchSpan #endDate').combo('getValue');
		}
		if(transferTypeCode == undefined) 
		{
			transferTypeCode = '';
		}
		
		if(param.page == undefined || param.page == 0) 
		{
			param.page = 1;
		}
		
		$('#dg-payment-detail').datagrid('options').url = "<c:url value='/paymentAccount/getAccountTransfer.html' />?startDate="+startDate+"&endDate="+endDate+"&paymentAccountId="+paymentId+"&transferTypeCode="+transferTypeCode+"&page="+param.page;
		return true;
	}

	var addInternalAccountTransfer = function() 
	{
		newModel('#dlg-add-paymentAccountTransfer', '新增内部转账', '#fm-payment-transfer', '<c:url value='/paymentAccount/addAccountTransfer.html' />');
	}
	
	var defaultPaymentDetailTimeFrame = 'RECENT_SEVEN_DAYS';
	var showSearchPaymentDetailDialog = function (index) 
	{
		$('#dg-paymentAccount').datagrid('selectRow',index);
		var payment = $('#dg-paymentAccount').datagrid('getSelected');
		$('#toolbar-paymentDetail #advanceSearchSpan #paymentId').val(payment.id);
		setInitialPaymentDetailTimeFrame(defaultPaymentDetailTimeFrame);
		$('#toolbar-paymentDetail #advanceSearchSpan #timeFrame').combobox('setValue', defaultPaymentDetailTimeFrame);
		$('#dlg-view-paymentAccount').dialog('open').dialog('setTitle', '账号'+ payment.accountId +'的交易记录');
		$('#dg-payment-detail').datagrid('options').onBeforeLoad = onBeforeLoadPaymentDetail;
		$('#dg-payment-detail').datagrid('reload');
	}
	var setInitialPaymentDetailTimeFrame = function(timeFrame) 
	{
		var startDate = generateStartDate(timeFrame);
		var endDate = new Date().format("yyyy-MM-dd");
		$('#toolbar-paymentDetail #advanceSearchSpan #startDate').datebox('setValue', startDate);
		$('#toolbar-paymentDetail #advanceSearchSpan #endDate').datebox('setValue', endDate);
	}
	
	function formatter_accountMode(value,row)
	{
		if('CASH' == value) 
		{
			return '现金';
		}
		if('DEPOSIT' == value) 
		{
			return '储蓄存款';
		}
	}
	function formatter_paymentType(value,row)
	{
		if(value) 
		{
			if('INTERNAL_TRANSFER' == value) 
			{
				return '<spring:message code="payment.transferType.internal" />';
			}
			if('CUSTOMER_TRANSFER' == value) 
			{
				return '<spring:message code="payment.transferType.customer" />';
			}
			if('PROVIDER_TRANSFER' == value) 
			{
				return '<spring:message code="payment.transferType.provider" />';
			}
			if('INITIAL_REMAIN' == value) 
			{
				return '<spring:message code="payment.transferType.remain" />';
			}
			if('ACCOUNTING_IN' == value) 
			{
				return '<spring:message code="payment.transferType.accountingIn" />';
			}
			if('ACCOUNTING_OUT' == value) 
			{
				return '<spring:message code="payment.transferType.accountingOut" />';
			}
			if('RECHARGE' == value) 
			{
				return '<spring:message code="payment.transferType.recharge" />';
			}
		}
	}
	function formatter_paymentAccount_targetAccount(vaue,row,index) 
	{
		if(row && row.targetAccountBean) 
		{
			return row.targetAccountBean.name;
		}
	}
	function showPaymentWayManage() 
	{
		$('#dlg-manage-paymentWay').dialog('open');
		$('#dlg-manage-paymentWay #dg-paymentWay').datagrid('options').url = "<c:url value='/paymentWay/getAllModel.html' />";
		$('#dlg-manage-paymentWay #dg-paymentWay').datagrid('reload');
	}
	function showAddValueDialog() 
	{
		newModel('#dlg-addValueTransfer', '充值', '#fm-addValue-transfer', '<c:url value='/paymentAccount/addAccountTransfer.html' />');
	}
	<!--  -->
	
	
	<!--  -->
	function reloadPaymentAccount() 
	{
		$('#dg-paymentAccount').datagrid('reload');
	}
	function onBeforeLoadPaymentAccount() 
	{
		paymentAccount_editingIndex = undefined;
		paymentAccount_rowId = undefined;
		$('#dg-paymentAccount').datagrid('options').url = "<c:url value='/paymentAccount/getModelBySearchForm.html' />";
		return true;
	}
	function formatter_paymentAccountAction (value,row,index) 
	{
		if(row.id != undefined) 
		{
			var q = '<a href="javascript:void(0)" class="icon-search" style="display:inline-block;width:16px;" title="查看交易明细" onclick="showSearchPaymentDetailDialog('+index+')">&nbsp;</a>&nbsp;&nbsp;';
			if (row.editing){
				if(row.id == '') 
				{
					var s = '<a href="javascript:void(0)" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="savePaymentAccount(this)">&nbsp;</a>&nbsp;&nbsp;';
					var d = '<a href="javascript:void(0)" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deletePaymentAccount(this)">&nbsp;</a>';
					if('${sessionScope.login_user.admin}' == 'true') 
					{
						return s+d;
					}
				}
				else 
				{
					var s = '<a href="javascript:void(0)" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="savePaymentAccount(this,' + row.id + ')">&nbsp;</a>&nbsp;&nbsp;';
					var c = '<a href="javascript:void(0)" class="icon-no" style="display:inline-block;width:16px;" title="取消" onclick="cancelPaymentAccount(this)">&nbsp;</a>';
					if('${sessionScope.login_user.admin}' == 'true') 
					{
						return s+c;
					}
				}
			} else {
				var e = '<a href="javascript:void(0)" class="icon-edit" style="display:inline-block;width:16px;" title="编辑" onclick="editPaymentAccount(this)">&nbsp;</a>&nbsp;&nbsp;';
				var d = '<a href="javascript:void(0)" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deletePaymentAccount(this,' + row.id + ')">&nbsp;</a>';
				if('${sessionScope.login_user.admin}' == 'true') 
				{
					return q+e+d;
				}
			}
			return q;
		}
	}
	var paymentAccount_editingIndex;
	var paymentAccount_rowId;
	var paymentAccount_onBeforeEdit = function(index,row){
        row.editing = true;
		paymentAccount_editingIndex = index;
		paymentAccount_rowId = row.id;
        updatePaymentAccountActions(index);
    }
	var paymentAccount_onAfterEdit = function(index,row){
        row.editing = false;
		paymentAccount_editingIndex = undefined;
		paymentAccount_rowId = '';
        updatePaymentAccountActions(index);
    }
	var paymentAccount_onCancelEdit = function(index,row){
        row.editing = false;
		paymentAccount_editingIndex = undefined;
		paymentAccount_rowId = '';
        updatePaymentAccountActions(index);
    }
	function updatePaymentAccountActions(index){
		$('#dg-paymentAccount').datagrid('updateRow',{
			index: index,
			row:{}
		});
	}
	function editPaymentAccount(target){
		if(paymentAccount_editingIndex == undefined) 
		{
			var col = $('#dg-paymentAccount').datagrid('getColumnOption', 'remainMoney');
			col.editor = null;
			$('#dg-paymentAccount').datagrid('selectRow', getRowIndex(target));
			$('#dg-paymentAccount').datagrid('beginEdit', getRowIndex(target));
		}
	}
	function deletePaymentAccount(target, rowId){
		if(rowId == undefined) 
		{
			$('#dg-paymentAccount').datagrid('deleteRow', getRowIndex(target));
		}
		else 
		{
			if(paymentAccount_editingIndex == undefined) 
			{
				$.messager.confirm('确认','您确认要删除?',function(r){
					if (r){
						//$('#dg-paymentAccount').datagrid('deleteRow', getRowIndex(target));
						ajaxPostRequest ('<c:url value='/paymentAccount/deleteModels.html' />', {ids:rowId}, reloadPaymentAccount);
					}
				});
			}
		}
	}
	function savePaymentAccount(target, rowId){
		//console.log(rowId);
		if ($('#dg-paymentAccount').datagrid('validateRow', getRowIndex(target)))
		{
			var paymentAccountNameEditor = $('#dg-paymentAccount').datagrid('getEditor', {index:getRowIndex(target), field:'name'});
			var paymentAccountName = $(paymentAccountNameEditor.target).val();
			var paymentAccountIdEditor = $('#dg-paymentAccount').datagrid('getEditor', {index:getRowIndex(target), field:'accountId'});
			var paymentAccountId = $(paymentAccountIdEditor.target).val();
			var accountModeEditor = $('#dg-paymentAccount').datagrid('getEditor', {index:getRowIndex(target), field:'accountMode'});
			var accountMode = $(accountModeEditor.target).combobox('getValue');
			var remainMoneyEditor = $('#dg-paymentAccount').datagrid('getEditor', {index:getRowIndex(target), field:'remainMoney'});
			var remainMoney = 0;
			if(remainMoneyEditor) 
			{
				remainMoney = $(remainMoneyEditor.target).val();
			}
			
			$('#dg-paymentAccount').datagrid('endEdit', getRowIndex(target));
			if(rowId == undefined) 
			{
				ajaxPostRequest ('<c:url value='/paymentAccount/addModel.html' />', {id:rowId, name:paymentAccountName, accountId:paymentAccountId, accountMode:accountMode, remainMoney:remainMoney}, reloadPaymentAccount);
			}
			else 
			{
				ajaxPostRequest ('<c:url value='/paymentAccount/updateModel.html' />', {id:rowId, name:paymentAccountName, accountId:paymentAccountId, accountMode:accountMode}, reloadPaymentAccount);
			}
		}
	}
	function cancelPaymentAccount(target){
		$('#dg-paymentAccount').datagrid('cancelEdit', getRowIndex(target));
	}
	function insertPaymentAccount(){
		if(paymentAccount_editingIndex == undefined) 
		{
			var row = $('#dg-paymentAccount').datagrid('getSelected');
			if (row){
				var index = $('#dg-paymentAccount').datagrid('getRowIndex', row);
			} else {
				index = 0;
			}
			$('#dg-paymentAccount').datagrid('insertRow', {
				index: index,
				row:{
					id:'',
					accountMode: 'DEPOSIT',
					remainMoney: 0
				}
			});
			
			var col = $('#dg-paymentAccount').datagrid('getColumnOption', 'remainMoney');
			col.editor = {type:'numberbox',options:{precision:2}};
			$('#dg-paymentAccount').datagrid('selectRow',index);
			$('#dg-paymentAccount').datagrid('beginEdit',index);
		}
	}
	$.extend($.fn.validatebox.defaults.rules, {
		checkPaymentAccountNameExist: {
			validator: function(value, param){
				var datagridId = '#dg-paymentAccount';
				var idField = 'paymentAccountId';
				var nameField = 'name';
				var data={};
				data[nameField]=value;
				data[idField] = paymentAccount_rowId;
				//var nameEditor = $(datagridId).datagrid('getEditor', {index:paymentAccount_editingIndex, field:nameField});
				//data[nameField] = $(nameEditor.target).val();
				//console.log(data);
				var _3ee=$.ajax({url:"<c:url value='/paymentAccount/checkExist.html' />",dataType:"json",data:data,async:false,cache:false,type:"post"}).responseText;
				return _3ee=="true";
			},
			message: remoteMessage
		}
	});
	$.extend($.fn.validatebox.defaults.rules, {
		checkPaymentAccountIdExist: {
			validator: function(value, param){
				var datagridId = '#dg-paymentAccount';
				var idField = 'paymentAccountId';
				var nameField = 'accountId';
				var data={};
				data[nameField]=value;
				data[idField] = paymentAccount_rowId;
				//var nameEditor = $(datagridId).datagrid('getEditor', {index:paymentAccount_editingIndex, field:nameField});
				//data[nameField] = $(nameEditor.target).val();
				//console.log(data);
				var _3ee=$.ajax({url:"<c:url value='/paymentAccount/checkExistByAccount.html' />",dataType:"json",data:data,async:false,cache:false,type:"post"}).responseText;
				return _3ee=="true";
			},
			message: remoteMessage
		}
	});
	<!--  -->
