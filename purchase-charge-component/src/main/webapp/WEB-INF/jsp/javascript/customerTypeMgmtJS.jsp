<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<% response.setContentType ("text/javascript"); %>

	function reloadCustomerType() 
	{
		$('#dlg-manage-customerType #dg-customerType').datagrid('reload');
	}
	function onBeforeLoadCustomerType() 
	{
		customerType_editingIndex = undefined;
		customerType_rowId = undefined;
		$('#dlg-manage-customerType #dg-customerType').datagrid('options').url = "<c:url value='/customerType/getAllModel.html' />";
		return true;
	}
	function formatter_customerTypeAction (value,row,index) 
	{
		if (row.editing){
			if(row.id == '') 
			{
				var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="saveCustomerType(this)">&nbsp;</a>&nbsp;&nbsp;';
				var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteCustomerType(this)">&nbsp;</a>';
				return s+d;
			}
			else 
			{
				var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="saveCustomerType(this,' + row.id + ')">&nbsp;</a>&nbsp;&nbsp;';
				var c = '<a href="#" class="icon-no" style="display:inline-block;width:16px;" title="取消" onclick="cancelCustomerType(this)">&nbsp;</a>';
				return s+c;
			}
		} else {
			var e = '<a href="#" class="icon-edit" style="display:inline-block;width:16px;" title="编辑" onclick="editCustomerType(this)">&nbsp;</a>&nbsp;&nbsp;';
			var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteCustomerType(this,' + row.id + ')">&nbsp;</a>';
			return e+d;
		}
	}
	var customerType_editingIndex;
	var customerType_rowId;
	var customerType_onBeforeEdit = function(index,row){
        row.editing = true;
		customerType_editingIndex = index;
		customerType_rowId = row.id;
        updateCustomerTypeActions(index);
    }
	var customerType_onAfterEdit = function(index,row){
        row.editing = false;
		customerType_editingIndex = undefined;
		customerType_rowId = '';
        updateCustomerTypeActions(index);
    }
	var customerType_onCancelEdit = function(index,row){
        row.editing = false;
		customerType_editingIndex = undefined;
		customerType_rowId = '';
        updateCustomerTypeActions(index);
    }
	function updateCustomerTypeActions(index){
		$('#dg-customerType').datagrid('updateRow',{
			index: index,
			row:{}
		});
	}
	function editCustomerType(target){
		if(customerType_editingIndex == undefined) 
		{
			$('#dg-customerType').datagrid('beginEdit', getRowIndex(target));
		}
	}
	function deleteCustomerType(target, rowId){
		if(rowId == undefined) 
		{
			$('#dg-customerType').datagrid('deleteRow', getRowIndex(target));
		}
		else 
		{
			if(customerType_editingIndex == undefined) 
			{
				$.messager.confirm('确认','您确认要删除?',function(r){
					if (r){
						//$('#dg-customerType').datagrid('deleteRow', getRowIndex(target));
						ajaxPostRequest ('<c:url value='/customerType/deleteModels.html' />', {ids:rowId}, reloadCustomerType);
					}
				});
			}
		}
	}
	function saveCustomerType(target, rowId){
		//console.log(rowId);
		if ($('#dg-customerType').datagrid('validateRow', getRowIndex(target)))
		{
			var customerTypeNameEditor = $('#dg-customerType').datagrid('getEditor', {index:getRowIndex(target), field:'name'});
			var customerTypeName = $(customerTypeNameEditor.target).val();
			//console.log(accountingMode);
			//console.log(customerTypeName);
			$('#dg-customerType').datagrid('endEdit', getRowIndex(target));
			if(rowId == undefined) 
			{
				ajaxPostRequest ('<c:url value='/customerType/addModel.html' />', {id:rowId, name:customerTypeName}, reloadCustomerType);
			}
			else 
			{
				ajaxPostRequest ('<c:url value='/customerType/updateModel.html' />', {id:rowId, name:customerTypeName}, reloadCustomerType);
			}
		}
	}
	function cancelCustomerType(target){
		$('#dg-customerType').datagrid('cancelEdit', getRowIndex(target));
	}
	function insertCustomerType(){
		if(customerType_editingIndex == undefined) 
		{
			var row = $('#dg-customerType').datagrid('getSelected');
			if (row){
				var index = $('#dg-customerType').datagrid('getRowIndex', row);
			} else {
				index = 0;
			}
			$('#dg-customerType').datagrid('insertRow', {
				index: index,
				row:{
					id:''
				}
			});
			$('#dg-customerType').datagrid('selectRow',index);
			$('#dg-customerType').datagrid('beginEdit',index);
		}
	}
	$.extend($.fn.validatebox.defaults.rules, {
		remoteInlineCheckExist: {
			validator: function(value, param){
				var datagridId = '#dg-customerType';
				var idField = 'customerTypeId';
				var nameField = 'name';
				var data={};
				data[nameField]=value;
				data[idField] = customerType_rowId;
				//var nameEditor = $(datagridId).datagrid('getEditor', {index:customerType_editingIndex, field:nameField});
				//data[nameField] = $(nameEditor.target).val();
				//console.log(data);
				var _3ee=$.ajax({url:"<c:url value='/customerType/checkExist.html' />",dataType:"json",data:data,async:false,cache:false,type:"post"}).responseText;
				return _3ee=="true";
			},
			message: remoteMessage
		}
	});
