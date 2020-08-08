<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<% response.setContentType ("text/javascript"); %>

	var providerGridDetailFormatter = function(index,row){
		return '<div class="ddv-provider" style="padding:5px 0"></div>';
	}
	
	var providerGridExpandRow = function(index,row){
		var ddv = $(this).datagrid('getRowDetail',index).find('div.ddv-provider');
		var trade = (null != row.tradeBean ? row.tradeBean.name : '');
		ddv.panel({
			height:65,
			border:false,
			cache:false,
			content:"<style type='text/css'>.dv-table-provider td{border:0;}.dv-label-provider{font-weight:normal;color:#15428B;width:10%;}.dv-nonlabel-provider{width:23%;}</style>" + 
			"<table class='dv-table-provider' border='0' style='width:100%;'>" + 
			"<tr><td class='dv-label-provider'>公司名称:</td><td class='dv-nonlabel-provider'>" + row.companyName + 
			"</td><td class='dv-label-provider'>地址:</td><td class='dv-nonlabel-provider'>" + row.address + 
			"</td><td class='dv-label-provider'>简介:</td><td class='dv-nonlabel-provider'>" + row.companyIntroduction + 
			"</td></tr>" + "<tr><td class='dv-label-provider'>邮编:</td><td class='dv-nonlabel-provider'>" + row.zipCode + 
			"</td><td class='dv-label-provider'>规模:</td><td class='dv-nonlabel-provider'>" + row.scale + 
			"</td><td class='dv-label-provider'>行业:</td><td class='dv-nonlabel-provider'>" + trade + "</td></tr>" + 
			"<tr><td class='dv-label-provider'>网址:</td><td class='dv-nonlabel-provider'>" + row.webSite + 
			"</td><td class='dv-label-provider'>注册日期:</td><td class='dv-nonlabel-provider'>" + row.regDate + 
			"</td><td class='dv-label-provider'>注册资金:</td><td class='dv-nonlabel-provider'>" + row.regFund + 
			"</td></tr>" + "</table>",
			onLoad:function(){
				$('#dg-provider').datagrid('fixDetailRowHeight',index);
			}
		});
		$('#dg-provider').datagrid('fixDetailRowHeight',index);
	}

	var newProviderCallback = function() 
	{
		resetProviderContactEditor();
		$('#fm-provider #dg-providerContact').datagrid('loadData', []);
		provider_editingIndex = undefined;
		
		//$('#fm-provider input.easyui-combobox').combobox('reload');
		$('#fm-provider #userSignedTo').val('${sessionScope.login_user.userId}');
		$('#fm-provider #signUserFullName').textbox('setValue', '${sessionScope.login_user.fullName}');
		$('#dlg-provider #fm-provider #sharable').combobox('setValue', false);
		$('#dlg-provider #fm-provider #sharable').combobox('setText', '不可共享');
		$('#dlg-provider #fm-provider #unpayMoney').numberbox('enable');
		$('#dlg-provider #fm-provider #unpayMoney').numberbox('setValue', 0);
	}
	var editProviderCallback = function () 
	{
		resetProviderContactEditor();
		$('#dlg-provider #fm-provider #unpayMoney').numberbox('disable');
		var provider = $('#dg-provider').datagrid('getSelected');
		if(provider.typeBean) 
		{
			loadComboboxData('#dlg-provider #fm-provider #type', '<c:url value='/providerType/getAllModel.html' />', provider.typeBean.id);
		}
		if(provider.sharable) 
		{
			$('#dlg-provider #fm-provider #sharable').combobox('setValue', provider.sharable);
			$('#dlg-provider #fm-provider #sharable').combobox('setText', '可共享');
		}
		else 
		{
			$('#dlg-provider #fm-provider #sharable').combobox('setValue', provider.sharable);
			$('#dlg-provider #fm-provider #sharable').combobox('setText', '不可共享');
		}
		
		$('#fm-provider #dg-providerContact').datagrid('loadData', []);
		$('#fm-provider #dg-providerContact').datagrid('loading');
		$.post('<c:url value='/provider/getModelById.html' />', {providerId : provider.id}, 
			function(result) 
			{
				if(result != '' && result.contactBeans) 
				{
					$('#fm-provider #dg-providerContact').datagrid('loadData', result.contactBeans);
					setProviderContactList();
				}
				$('#fm-provider #dg-providerContact').datagrid('loaded');
			}, 
			'json');
	}
	var viewProviderPaymentRecord = function () 
	{
		var row = $('#dg-provider').datagrid('getSelected');
		if (row) {
			$('#dlg-view-provider-payment').dialog('open').dialog('setTitle', '查看' + row.shortName + '的付款记录');
			loadGridData ('#dg-provider-payment', '<c:url value='/provider/getProviderPayment.html' />', {customerId : row.id});
		}
	}
	
	var onClickProviderRow = function (rowIndex, rowData) 
	{
		$('#toolbar-provider #btn-editProvider').linkbutton('enable');
		//$('#toolbar-provider #btn-deleteProvider').linkbutton('enable');
		//$('#toolbar-provider #btn-receivePay').linkbutton('enable');
		$('#toolbar-provider #btn-viewPay').linkbutton('enable');
		$('#toolbar-provider #btn-bindContact').linkbutton('enable');
		$('#toolbar-provider #btn-viewContact').linkbutton('enable');
	}
	var onLoadProviderSuccess = function(data) 
	{
		$('#toolbar-provider #btn-editProvider').linkbutton('disable');
		$('#toolbar-provider #btn-deleteProvider').linkbutton('disable');
		//$('#toolbar-provider #btn-receivePay').linkbutton('disable');
		$('#toolbar-provider #btn-viewPay').linkbutton('disable');
		$('#toolbar-provider #btn-bindContact').linkbutton('disable');
		$('#toolbar-provider #btn-viewContact').linkbutton('disable');
	}
	function doSearchProvider(value) {
		$('#dg-provider').datagrid('load', {
			searchKey : value
		});
	}
	var onCheckAllProvider = function(rows) 
	{
		$('#toolbar-provider #btn-deleteProvider').linkbutton('enable');
	}
	var onUnCheckProvider = function(rowIndex,rowData) 
	{
		var rows = $('#dg-provider').datagrid('getChecked');
		if (rows.length == 0) {
			$('#toolbar-provider #btn-deleteProvider').linkbutton('disable');
		}
	}
	
	
	function formatter_providerAction (value,row,index) 
	{
		if (row.editing){
			if(row.id == '') 
			{
				var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="saveProviderContact(this)">&nbsp;</a> ';
				var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteProviderContact(this)">&nbsp;</a>';
				return s+d;
			}
			else 
			{
				var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="saveProviderContact(this,' + row.id + ')">&nbsp;</a> ';
				var c = '<a href="#" class="icon-no" style="display:inline-block;width:16px;" title="取消" onclick="cancelProviderContact(this)">&nbsp;</a>';
				return s+c;
			}
		} else {
			if(row.id == '') 
			{
				var e = '<a href="#" class="icon-edit" style="display:inline-block;width:16px;" title="编辑" onclick="editProviderContact(this)">&nbsp;</a> ';
				var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteProviderContact(this)">&nbsp;</a>';
				return e+d;
			}
			else 
			{
				var e = '<a href="#" class="icon-edit" style="display:inline-block;width:16px;" title="编辑" onclick="editProviderContact(this)">&nbsp;</a> ';
				var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteProviderContact(this,' + row.id + ')">&nbsp;</a>';
				return e+d;
			}
		}
	}
	var provider_editingIndex = undefined;
	var provider_rowId = undefined;
	var provider_onBeforeEdit = function(index,row){
        row.editing = true;
		provider_editingIndex = index;
		provider_rowId = row.id;
        updateProviderContactActions(index);
    }
	var provider_onAfterEdit = function(index,row){
        row.editing = false;
		provider_editingIndex = undefined;
		provider_rowId = '';
        updateProviderContactActions(index);
    }
	var provider_onCancelEdit = function(index,row){
        row.editing = false;
		provider_editingIndex = undefined;
		provider_rowId = '';
        updateProviderContactActions(index);
    }
	var resetProviderContactEditor = function () 
	{	
		provider_editingIndex = undefined;
		provider_rowId = undefined;
	}
	function updateProviderContactActions(index){
		$('#dg-providerContact').datagrid('updateRow',{
			index: index,
			row:{}
		});
	}
	function editProviderContact(target){
		if(provider_editingIndex == undefined) 
		{
			$('#dg-providerContact').datagrid('beginEdit', getRowIndex(target));
		}
	}
	function deleteProviderContact(target, rowId){
		if(rowId == undefined) 
		{
			$('#dg-providerContact').datagrid('deleteRow', getRowIndex(target));
			$('#dg-providerContact').datagrid('acceptChanges');
		}
		else 
		{
			if(provider_editingIndex == undefined) 
			{
				$.messager.confirm('确认','您确认要删除?',function(r){
					if (r){
						$('#dg-providerContact').datagrid('deleteRow', getRowIndex(target));
						$('#dg-providerContact').datagrid('acceptChanges');
						//console.log(rowId);
						//ajaxPostRequest ('<c:url value='/provider/deleteModels.html' />', {ids:rowId}, reloadCustomerType);
					}
				});
			}
		}
		setProviderContactList();
	}
	function saveProviderContact(target, rowId){
		//console.log(rowId);
		if ($('#dg-providerContact').datagrid('validateRow', getRowIndex(target)))
		{
			var providerNameEditor = $('#dg-providerContact').datagrid('getEditor', {index:getRowIndex(target), field:'name'});
			var providerName = $(providerNameEditor.target).val();
			//console.log(accountingMode);
			//console.log(providerName);
			$('#dg-providerContact').datagrid('endEdit', getRowIndex(target));
			if(rowId == undefined) 
			{
				//ajaxPostRequest ('<c:url value='/provider/addModel.html' />', {id:rowId, name:providerName}, reloadCustomerType);
			}
			else 
			{
				//ajaxPostRequest ('<c:url value='/provider/updateModel.html' />', {id:rowId, name:providerName}, reloadCustomerType);
			}
			setProviderContactList();
		}
	}
	function cancelProviderContact(target){
		$('#dg-providerContact').datagrid('cancelEdit', getRowIndex(target));
	}
	function insertProviderContact(){
		if(provider_editingIndex == undefined) 
		{
			var row = $('#dg-providerContact').datagrid('getSelected');
			if (row){
				var index = $('#dg-providerContact').datagrid('getRowIndex', row);
			} else {
				index = 0;
			}
			$('#dg-providerContact').datagrid('insertRow', {
				index: index,
				row:{
					id:'',
					prefered:'1'
				}
			});
			$('#dg-providerContact').datagrid('selectRow',index);
			$('#dg-providerContact').datagrid('beginEdit',index);
		}
	}
	var setProviderContactList = function () 
	{
		var allRows = $('#dg-providerContact').datagrid('getRows');
		var contactList = "";
		for(var i = 0; i < allRows.length; i++) 
		{
			var contact = "";
			var item = allRows[i];
			contact += (item.id +","+item.name+","+item.mobilePhone+","+item.fixedPhone+","+item.netCommunityId+","+item.address+","+ item.prefered);
			contactList += contact + ";";
		}
		//console.log(contactList.substring(0, contactList.length-1));
		$('#dlg-provider #fm-provider #contactList').val(contactList);
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
	function checkProviderContact () 
	{
		if(provider_editingIndex != undefined) 
		{
			$('#dg-providerContact').datagrid('endEdit', provider_editingIndex);
			setProviderContactList();
		}
		return true;
	}
	var onDblClickProviderRow = function(rowIndex, rowData) 
	{
		$('#toolbar-provider #btn-editProvider').click();
	}
	function showManageProviderType() 
	{
		$('#dlg-manage-providerType').dialog('open');
		$('#dlg-manage-providerType #dg-providerType').datagrid('options').url = "<c:url value='/providerType/getAllModel.html' />";
		$('#dlg-manage-providerType #dg-providerType').datagrid('reload');
	}
	var onBeforeLoadProvider = function(param) {
		if (param.page == undefined || param.page == 0) {
			param.page = 1;
		}
		$('#dg-provider').datagrid('options').url = "<c:url value='/provider/getModelBySearchForm.html' />";
		return true;
	}
		