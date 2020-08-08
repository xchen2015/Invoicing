<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<% response.setContentType ("text/javascript"); %>

	var customerGridDetailFormatter = function(index,row){
		return '<div class="ddv-customer" style="padding:5px 0"></div>';
	}
	
	var customerGridExpandRow = function(index,row){
		var ddv = $(this).datagrid('getRowDetail',index).find('div.ddv-customer');
		var trade = (null != row.tradeBean ? row.tradeBean.name : '');
		ddv.panel({
			height:65,
			border:false,
			cache:false,
			content:"<style type='text/css'>.dv-table-customer td{border:0;}.dv-label-customer{font-weight:normal;color:#15428B;width:10%;}.dv-nonlabel-customer{width:23%;}</style>" + 
			"<table class='dv-table-customer' border='0' style='width:100%;'>" + 
			"<tr><td class='dv-label-customer'>公司名称:</td><td class='dv-nonlabel-customer'>" + row.companyName + "</td><td class='dv-label-customer'>地址:</td><td class='dv-nonlabel-customer'>" + row.address + "</td><td class='dv-label-customer'>简介:</td><td class='dv-nonlabel-customer'>" + row.companyIntroduction + "</td></tr>" + 
			"<tr><td class='dv-label-customer'>邮编:</td><td class='dv-nonlabel-customer'>" + row.zipCode + "</td><td class='dv-label-customer'>规模:</td><td class='dv-nonlabel-customer'>" + row.scale + "</td><td class='dv-label-customer'>行业:</td><td class='dv-nonlabel-customer'>" + trade + "</td></tr>" + 
			"<tr><td class='dv-label-customer'>网址:</td><td class='dv-nonlabel-customer'>" + row.webSite + "</td><td class='dv-label-customer'>注册日期:</td><td class='dv-nonlabel-customer'>" + row.regDate + "</td><td class='dv-label-customer'>注册资金:</td><td class='dv-nonlabel-customer'>" + row.regFund + "</td></tr>" + 
			"</table>",
			onLoad:function(){
				$('#dg-customer').datagrid('fixDetailRowHeight',index);
			}
		});
		$('#dg-customer').datagrid('fixDetailRowHeight',index);
	}

	var newCustomerCallback = function() 
	{
		resetCustomerContactEditor();
		$('#fm-customer #dg-customerContact').datagrid('loadData', []);
		customerContact_editingIndex = undefined;
		//$('#fm-customer input.easyui-combobox').combobox('reload');
		$('#fm-customer #userSignedTo').val('${sessionScope.login_user.userId}');
		$('#fm-customer #signUserFullName').textbox('setValue', '${sessionScope.login_user.fullName}');
		$('#dlg-customer #fm-customer #sharable').combobox('setValue', false);
		$('#dlg-customer #fm-customer #sharable').combobox('setText', '不可共享');
		$('#dlg-customer #fm-customer #unpayMoney').numberbox('enable');
		$('#dlg-customer #fm-customer #unpayMoney').numberbox('setValue', 0);
	}
	var editCustomerCallback = function () 
	{
		resetCustomerContactEditor();
		$('#dlg-customer #fm-customer #unpayMoney').numberbox('disable');
		var customer = $('#dg-customer').datagrid('getSelected');
		if(customer.typeBean) 
		{
			loadComboboxData('#dlg-customer #fm-customer #type', '<c:url value='/customerType/getAllModel.html' />', customer.typeBean.id);
		}
		if(customer.levelBean) 
		{
			$('#dlg-customer #fm-customer #level').combobox('setValue', customer.typeBean.id);
			$('#dlg-customer #fm-customer #level').combobox('setText', customer.typeBean.name);
		}
		if(customer.sharable) 
		{
			$('#dlg-customer #fm-customer #sharable').combobox('setValue', customer.sharable);
			$('#dlg-customer #fm-customer #sharable').combobox('setText', '可共享');
		}
		else 
		{
			$('#dlg-customer #fm-customer #sharable').combobox('setValue', customer.sharable);
			$('#dlg-customer #fm-customer #sharable').combobox('setText', '不可共享');
		}
		//$('#fm-customer #signUserFullName').textbox('setValue', '${sessionScope.login_user.fullName}');
		$('#fm-customer #dg-customerContact').datagrid('loadData', []);
		$('#fm-customer #dg-customerContact').datagrid('loading');
		$.post('<c:url value='/customer/getModelById.html' />', {customerId : customer.id}, 
			function(result) 
			{
				if(result != '' && result.contactBeans) 
				{
					$('#fm-customer #dg-customerContact').datagrid('loadData', result.contactBeans);
					setCustomerContactList();
				}
				$('#fm-customer #dg-customerContact').datagrid('loaded');
			}, 
			'json');
	}
	
	var viewPaymentRecord = function () 
	{
		var row = $('#dg-customer').datagrid('getSelected');
		if (row) {
			$('#dlg-view-payment').dialog('open').dialog('setTitle', '查看' + row.shortName + '的收款记录');
			loadGridData ('#dg-customerPayment', '<c:url value='/customer/getCustomerPayment.html' />', {customerId : row.id});
		}
	}
	
	var onClickCustomerRow = function (rowIndex, rowData) 
	{
		$('#toolbar-customer #btn-editCustomer').linkbutton('enable');
		//$('#toolbar-customer #btn-deleteCustomer').linkbutton('enable');
		//$('#toolbar-customer #btn-receivePay').linkbutton('enable');
		$('#toolbar-customer #btn-viewPay').linkbutton('enable');
		$('#toolbar-customer #btn-viewContact').linkbutton('enable');
	}
	var onLoadCustomerSuccess = function (data) 
	{
		$('#toolbar-customer #btn-editCustomer').linkbutton('disable');
		$('#toolbar-customer #btn-deleteCustomer').linkbutton('disable');
		//$('#toolbar-customer #btn-receivePay').linkbutton('disable');
		$('#toolbar-customer #btn-viewPay').linkbutton('disable');
		$('#toolbar-customer #btn-viewContact').linkbutton('disable');
	}
	var onCheckAllCustomer = function(rows) 
	{
		$('#toolbar-customer #btn-deleteCustomer').linkbutton('enable');
	}
	var onUnCheckCustomer = function(rowIndex,rowData) 
	{
		var rows = $('#dg-customer').datagrid('getChecked');
		if (rows.length == 0) {
			$('#toolbar-customer #btn-deleteCustomer').linkbutton('disable');
		}
	}
	
	function formatter_customerContactAction (value,row,index) 
	{
		if (row.editing){
			if(row.id == '') 
			{
				var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="saveCustomerContact(this)">&nbsp;</a> ';
				var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteCustomerContact(this)">&nbsp;</a>';
				return s+d;
			}
			else 
			{
				var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="saveCustomerContact(this,' + row.id + ')">&nbsp;</a> ';
				var c = '<a href="#" class="icon-no" style="display:inline-block;width:16px;" title="取消" onclick="cancelCustomerContact(this)">&nbsp;</a>';
				return s+c;
			}
		} else {
			if(row.id == '') 
			{
				var e = '<a href="#" class="icon-edit" style="display:inline-block;width:16px;" title="编辑" onclick="editCustomerContact(this)">&nbsp;</a> ';
				var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteCustomerContact(this)">&nbsp;</a>';
				return e+d;
			}
			else 
			{
				var e = '<a href="#" class="icon-edit" style="display:inline-block;width:16px;" title="编辑" onclick="editCustomerContact(this)">&nbsp;</a> ';
				var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteCustomerContact(this,' + row.id + ')">&nbsp;</a>';
				return e+d;
			}
		}
	}
	var customerContact_editingIndex = undefined;
	var customerContact_rowId = undefined;
	var customerContact_onBeforeEdit = function(index,row){
        row.editing = true;
		customerContact_editingIndex = index;
		customerContact_rowId = row.id;
        updateCustomerContactActions(index);
    }
	var customerContact_onAfterEdit = function(index,row){
        row.editing = false;
		customerContact_editingIndex = undefined;
		customerContact_rowId = '';
        updateCustomerContactActions(index);
    }
	var customerContact_onCancelEdit = function(index,row){
        row.editing = false;
		customerContact_editingIndex = undefined;
		customerContact_rowId = '';
        updateCustomerContactActions(index);
    }
	var resetCustomerContactEditor = function () 
	{	
		customerContact_editingIndex = undefined;
		customerContact_rowId = undefined;
	}
	function updateCustomerContactActions(index){
		$('#dg-customerContact').datagrid('updateRow',{
			index: index,
			row:{}
		});
	}
	function editCustomerContact(target){
		if(customerContact_editingIndex == undefined) 
		{
			$('#dg-customerContact').datagrid('beginEdit', getRowIndex(target));
		}
	}
	function deleteCustomerContact(target, rowId){
		if(rowId == undefined) 
		{
			$('#dg-customerContact').datagrid('deleteRow', getRowIndex(target));
			$('#dg-customerContact').datagrid('acceptChanges');
		}
		else 
		{
			if(customerContact_editingIndex == undefined) 
			{
				$.messager.confirm('确认','您确认要删除?',function(r){
					if (r){
						$('#dg-customerContact').datagrid('deleteRow', getRowIndex(target));
						$('#dg-customerContact').datagrid('acceptChanges');
					}
				});
			}
		}
		setCustomerContactList();
	}
	function saveCustomerContact(target, rowId){
		//console.log(rowId);
		if ($('#dg-customerContact').datagrid('validateRow', getRowIndex(target)))
		{
			var customerContactNameEditor = $('#dg-customerContact').datagrid('getEditor', {index:getRowIndex(target), field:'name'});
			var customerContactName = $(customerContactNameEditor.target).val();
			//console.log(accountingMode);
			//console.log(customerContactName);
			$('#dg-customerContact').datagrid('endEdit', getRowIndex(target));
			if(rowId == undefined) 
			{
			}
			else 
			{
			}
			setCustomerContactList();
		}
	}
	function cancelCustomerContact(target){
		$('#dg-customerContact').datagrid('cancelEdit', getRowIndex(target));
	}
	function insertCustomerContact(){
		if(customerContact_editingIndex == undefined) 
		{
			var row = $('#dg-customerContact').datagrid('getSelected');
			if (row){
				var index = $('#dg-customerContact').datagrid('getRowIndex', row);
			} else {
				index = 0;
			}
			$('#dg-customerContact').datagrid('insertRow', {
				index: index,
				row:{
					id:'',
					prefered:'1'
				}
			});
			$('#dg-customerContact').datagrid('selectRow',index);
			$('#dg-customerContact').datagrid('beginEdit',index);
		}
	}
	var setCustomerContactList = function () 
	{
		var allRows = $('#dg-customerContact').datagrid('getRows');
		var contactList = "";
		for(var i = 0; i < allRows.length; i++) 
		{
			var contact = "";
			var item = allRows[i];
			contact += (item.id +","+item.name+","+item.mobilePhone+","+item.fixedPhone+","+item.netCommunityId+","+item.address+","+ item.prefered);
			contactList += contact + ";";
		}
		//console.log(contactList.substring(0, contactList.length-1));
		$('#dlg-customer #fm-customer #contactList').val(contactList);
	}
	function formatter_prefered(value,row,index)
	{
		if('1' == value || value == true) 
		{
			return '是';
		}
		if('0' == value || value == false) 
		{
			return '否';
		}
		return '';
	}
	function checkCustomerContact () 
	{
		if(customerContact_editingIndex != undefined) 
		{
			$('#dg-customerContact').datagrid('endEdit', customerContact_editingIndex);
			setCustomerContactList();
		}
		return true;
	}
	var onDblClickCustomerRow = function(rowIndex, rowData) 
	{
		$('#toolbar-customer #btn-editCustomer').click();
	}
	
	function doSearchCustomer(value) {
		$('#dg-customer').datagrid('load', {
			searchKey : value
		});
	}
	function showManageCustomerType() 
	{
		$('#dlg-manage-customerType').dialog('open');
		$('#dlg-manage-customerType #dg-customerType').datagrid('options').url = "<c:url value='/customerType/getAllModel.html' />";
		$('#dlg-manage-customerType #dg-customerType').datagrid('reload');
	}
	function showManageCustomerLevel() 
	{
		$('#dlg-manage-customerLevel').dialog('open');
		$('#dlg-manage-customerLevel #dg-customerLevel').datagrid('options').url = "<c:url value='/customerLevel/getAllModel.html' />";
		$('#dlg-manage-customerLevel #dg-customerLevel').datagrid('reload');
	}
	var onBeforeLoadCustomer = function(param) {
		if (param.page == undefined || param.page == 0) {
			param.page = 1;
		}
		$('#dg-customer').datagrid('options').url = "<c:url value='/customer/getModelBySearchForm.html' />";
		return true;
	}
	