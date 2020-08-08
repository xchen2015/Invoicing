<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<% response.setContentType ("text/javascript"); %>

	function reloadCustomerLevel() 
	{
		$('#dlg-manage-customerLevel #dg-customerLevel').datagrid('reload');
	}
	function onBeforeLoadCustomerLevel() 
	{
		customerLevel_editingIndex = undefined;
		customerLevel_rowId = undefined;
		$('#dlg-manage-customerLevel #dg-customerLevel').datagrid('options').url = "<c:url value='/customerLevel/getAllModel.html' />";
		return true;
	}
	function formatter_customerLevelAction (value,row,index) 
	{
		if (row.editing){
			if(row.id == '') 
			{
				var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="saveCustomerLevel(this)">&nbsp;</a>&nbsp;&nbsp;';
				var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteCustomerLevel(this)">&nbsp;</a>';
				return s+d;
			}
			else 
			{
				var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="saveCustomerLevel(this,' + row.id + ')">&nbsp;</a>&nbsp;&nbsp;';
				var c = '<a href="#" class="icon-no" style="display:inline-block;width:16px;" title="取消" onclick="cancelCustomerLevel(this)">&nbsp;</a>';
				return s+c;
			}
		} else {
			var e = '<a href="#" class="icon-edit" style="display:inline-block;width:16px;" title="编辑" onclick="editCustomerLevel(this)">&nbsp;</a>&nbsp;&nbsp;';
			var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteCustomerLevel(this,' + row.id + ')">&nbsp;</a>';
			return e+d;
		}
	}
	var customerLevel_editingIndex;
	var customerLevel_rowId;
	var customerLevel_onBeforeEdit = function(index,row){
        row.editing = true;
		customerLevel_editingIndex = index;
		customerLevel_rowId = row.id;
        updateCustomerLevelActions(index);
    }
	var customerLevel_onAfterEdit = function(index,row){
        row.editing = false;
		customerLevel_editingIndex = undefined;
		customerLevel_rowId = '';
        updateCustomerLevelActions(index);
    }
	var customerLevel_onCancelEdit = function(index,row){
        row.editing = false;
		customerLevel_editingIndex = undefined;
		customerLevel_rowId = '';
        updateCustomerLevelActions(index);
    }
	function updateCustomerLevelActions(index){
		$('#dg-customerLevel').datagrid('updateRow',{
			index: index,
			row:{}
		});
	}
	function editCustomerLevel(target){
		if(customerLevel_editingIndex == undefined) 
		{
			$('#dg-customerLevel').datagrid('beginEdit', getRowIndex(target));
		}
	}
	function deleteCustomerLevel(target, rowId){
		if(rowId == undefined) 
		{
			$('#dg-customerLevel').datagrid('deleteRow', getRowIndex(target));
		}
		else 
		{
			if(customerLevel_editingIndex == undefined) 
			{
				$.messager.confirm('确认','您确认要删除?',function(r){
					if (r){
						//$('#dg-customerLevel').datagrid('deleteRow', getRowIndex(target));
						ajaxPostRequest ('<c:url value='/customerLevel/deleteModels.html' />', {ids:rowId}, reloadCustomerLevel);
					}
				});
			}
		}
	}
	function saveCustomerLevel(target, rowId){
		//console.log(rowId);
		if ($('#dg-customerLevel').datagrid('validateRow', getRowIndex(target)))
		{
			var customerLevelNameEditor = $('#dg-customerLevel').datagrid('getEditor', {index:getRowIndex(target), field:'name'});
			var customerLevelName = $(customerLevelNameEditor.target).val();
			var customerLevelOrderEditor = $('#dg-customerLevel').datagrid('getEditor', {index:getRowIndex(target), field:'order'});
			var customerLevelOrder = $(customerLevelOrderEditor.target).val();
			var saleMoneyEditor = $('#dg-customerLevel').datagrid('getEditor', {index:getRowIndex(target), field:'saleMoney'});
			var saleMoney = $(saleMoneyEditor.target).val();
			var profitMoneyEditor = $('#dg-customerLevel').datagrid('getEditor', {index:getRowIndex(target), field:'profitMoney'});
			var profitMoney = $(profitMoneyEditor.target).val();
			var paymentDaysEditor = $('#dg-customerLevel').datagrid('getEditor', {index:getRowIndex(target), field:'paymentDays'});
			var paymentDays = $(paymentDaysEditor.target).val();
			var maxDebtEditor = $('#dg-customerLevel').datagrid('getEditor', {index:getRowIndex(target), field:'maxDebt'});
			var maxDebt = $(maxDebtEditor.target).val();
			var priceRateEditor = $('#dg-customerLevel').datagrid('getEditor', {index:getRowIndex(target), field:'priceRate'});
			var priceRate = $(priceRateEditor.target).val();
			//console.log(accountingMode);
			//console.log(customerLevelName);
			$('#dg-customerLevel').datagrid('endEdit', getRowIndex(target));
			if(rowId == undefined) 
			{
				ajaxPostRequest ('<c:url value='/customerLevel/addModel.html' />', {id:rowId, name:customerLevelName, order:customerLevelOrder, saleMoney:saleMoney, profitMoney:profitMoney, paymentDays:paymentDays, maxDebt:maxDebt, priceRate:priceRate}, reloadCustomerLevel);
			}
			else 
			{
				ajaxPostRequest ('<c:url value='/customerLevel/updateModel.html' />', {id:rowId, name:customerLevelName, order:customerLevelOrder, saleMoney:saleMoney, profitMoney:profitMoney, paymentDays:paymentDays, maxDebt:maxDebt, priceRate:priceRate}, reloadCustomerLevel);
			}
		}
	}
	function cancelCustomerLevel(target){
		$('#dg-customerLevel').datagrid('cancelEdit', getRowIndex(target));
	}
	function insertCustomerLevel(){
		if(customerLevel_editingIndex == undefined) 
		{
			var row = $('#dg-customerLevel').datagrid('getSelected');
			if (row){
				var index = $('#dg-customerLevel').datagrid('getRowIndex', row);
			} else {
				index = 0;
			}
			$('#dg-customerLevel').datagrid('insertRow', {
				index: index,
				row:{
					id:'',
					saleMoney: 100000,
					profitMoney: 10000,
					maxDebt: 10000,
					paymentDays: 30,
					priceRate: 1,
					enabled: true
				}
			});
			$('#dg-customerLevel').datagrid('selectRow',index);
			$('#dg-customerLevel').datagrid('beginEdit',index);
		}
	}
	$.extend($.fn.validatebox.defaults.rules, {
		checkCustomerLevelExist: {
			validator: function(value, param){
				var datagridId = '#dg-customerLevel';
				var idField = 'customerLevelId';
				var nameField = 'name';
				var data={};
				data[nameField]=value;
				data[idField] = customerLevel_rowId;
				//var nameEditor = $(datagridId).datagrid('getEditor', {index:customerLevel_editingIndex, field:nameField});
				//data[nameField] = $(nameEditor.target).val();
				//console.log(data);
				var _3ee=$.ajax({url:"<c:url value='/customerLevel/checkExist.html' />",dataType:"json",data:data,async:false,cache:false,type:"post"}).responseText;
				return _3ee=="true";
			},
			message: remoteMessage
		}
	});
	$.extend($.fn.validatebox.defaults.rules, {
		checkLevelOrderExist: {
			validator: function(value, param){
				var datagridId = '#dg-customerLevel';
				var idField = 'customerLevelId';
				var nameField = 'order';
				var data={};
				data[nameField]=value;
				data[idField] = customerLevel_rowId;
				//var nameEditor = $(datagridId).datagrid('getEditor', {index:customerLevel_editingIndex, field:nameField});
				//data[nameField] = $(nameEditor.target).val();
				//console.log(data);
				var _3ee=$.ajax({url:"<c:url value='/customerLevel/checkLevelOrderExist.html' />",dataType:"json",data:data,async:false,cache:false,type:"post"}).responseText;
				return _3ee=="true";
			},
			message: remoteMessage
		}
	});
	
	function enableCheckedLevel () 
	{
		enableMultipleModel('#dg-customerLevel', '<spring:message code="customerLevel" />', '<c:url value='/customerLevel/enableLevel.html' />', true, function(){loadGridData ('#dg-customerLevel', '<c:url value='/customerLevel/getAllModel.html' />')});
	}
	function disableCheckedLevel () 
	{
		enableMultipleModel('#dg-customerLevel', '<spring:message code="customerLevel" />', '<c:url value='/customerLevel/disableLevel.html' />', false, function(){loadGridData ('#dg-customerLevel', '<c:url value='/customerLevel/getAllModel.html' />')});
	}
