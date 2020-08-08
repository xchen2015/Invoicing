<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<% response.setContentType ("text/javascript"); %>

	function reloadExpenseType() 
	{
		$('#dlg-dg-accountingType #dg-accountingType').datagrid('reload');
	}
	function onBeforeLoadExpenseType() 
	{
		editingIndex = undefined;
		rowId = undefined;
		$('#dlg-dg-accountingType #dg-accountingType').datagrid('options').url = "<c:url value='/accountingType/getAllModel.html' />";
		return true;
	}
	function formatter_accountingMode(value,row,index)
	{
		if('IN_COME' == value) 
		{
			return '收入';
		}
		if('OUT_LAY' == value) 
		{
			return '支出';
		}
		return '';
	}
	function formatter_accountingTypeAction (value,row,index) 
	{
		if (row.editing){
			if(row.id == '') 
			{
				var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="saverow(this)">&nbsp;</a>&nbsp;&nbsp;';
				var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleterow(this)">&nbsp;</a>';
				return s+d;
			}
			else 
			{
				var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="saverow(this,' + row.id + ')">&nbsp;</a>&nbsp;&nbsp;';
				var c = '<a href="#" class="icon-no" style="display:inline-block;width:16px;" title="取消" onclick="cancelrow(this)">&nbsp;</a>';
				return s+c;
			}
		} else {
			var e = '<a href="#" class="icon-edit" style="display:inline-block;width:16px;" title="编辑" onclick="editrow(this)">&nbsp;</a>&nbsp;&nbsp;';
			var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleterow(this,' + row.id + ')">&nbsp;</a>';
			return e+d;
		}
	}
	var editingIndex;
	var rowId;
	var accountingType_onBeforeEdit = function(index,row){
           row.editing = true;
		editingIndex = index;
		rowId = row.id;
           updateActions(index);
       }
	var accountingType_onAfterEdit = function(index,row){
           row.editing = false;
		editingIndex = undefined;
		rowId = '';
           updateActions(index);
       }
	var accountingType_onCancelEdit = function(index,row){
           row.editing = false;
		editingIndex = undefined;
		rowId = '';
           updateActions(index);
       }
	function updateActions(index){
		$('#dg-accountingType').datagrid('updateRow',{
			index: index,
			row:{}
		});
	}
	function getRowIndex(target){
		var tr = $(target).closest('tr.datagrid-row');
		return parseInt(tr.attr('datagrid-row-index'));
	}
	function editrow(target){
		if(editingIndex == undefined) 
		{
			$('#dg-accountingType').datagrid('beginEdit', getRowIndex(target));
		}
	}
	function deleterow(target, rowId){
		if(rowId == undefined) 
		{
			$('#dg-accountingType').datagrid('deleteRow', getRowIndex(target));
		}
		else 
		{
			if(editingIndex == undefined) 
			{
				$.messager.confirm('确认','您确认要删除?',function(r){
					if (r){
						//$('#dg-accountingType').datagrid('deleteRow', getRowIndex(target));
						ajaxPostRequest ('<c:url value='/accountingType/deleteModels.html' />', {ids:rowId}, reloadExpenseType);
					}
				});
			}
		}
	}
	function saverow(target, rowId){
		//console.log(rowId);
		if ($('#dg-accountingType').datagrid('validateRow', getRowIndex(target)))
		{
			var accountingModeEditor = $('#dg-accountingType').datagrid('getEditor', {index:getRowIndex(target), field:'accountingMode'});
			var accountingTypeNameEditor = $('#dg-accountingType').datagrid('getEditor', {index:getRowIndex(target), field:'name'});
			var accountingMode = $(accountingModeEditor.target).combobox('getValue');
			var accountingTypeName = $(accountingTypeNameEditor.target).val();
			//console.log(accountingMode);
			//console.log(accountingTypeName);
			$('#dg-accountingType').datagrid('endEdit', getRowIndex(target));
			if(rowId == undefined) 
			{
				ajaxPostRequest ('<c:url value='/accountingType/addModel.html' />', {id:rowId, accountingMode:accountingMode, name:accountingTypeName}, reloadExpenseType);
			}
			else 
			{
				ajaxPostRequest ('<c:url value='/accountingType/updateModel.html' />', {id:rowId, accountingMode:accountingMode, name:accountingTypeName}, reloadExpenseType);
			}
		}
	}
	function cancelrow(target){
		$('#dg-accountingType').datagrid('cancelEdit', getRowIndex(target));
	}
	function insert(){
		if(editingIndex == undefined) 
		{
			var row = $('#dg-accountingType').datagrid('getSelected');
			if (row){
				var index = $('#dg-accountingType').datagrid('getRowIndex', row);
			} else {
				index = 0;
			}
			$('#dg-accountingType').datagrid('insertRow', {
				index: index,
				row:{
					id:'',
					accountingMode:'OUT_LAY'
				}
			});
			$('#dg-accountingType').datagrid('selectRow',index);
			$('#dg-accountingType').datagrid('beginEdit',index);
		}
	}
	$.extend($.fn.validatebox.defaults.rules, {
		remoteInlineCheckExist: {
			validator: function(value, param){
				var datagridId = '#dg-accountingType';
				var idField = 'expenseTypeId';
				var nameField = 'name';
				var data={};
				data[nameField]=value;
				data[idField] = rowId;
				//var nameEditor = $(datagridId).datagrid('getEditor', {index:editingIndex, field:nameField});
				//data[nameField] = $(nameEditor.target).val();
				//console.log(data);
				var _3ee=$.ajax({url:"<c:url value='/accountingType/checkExist.html' />",dataType:"json",data:data,async:false,cache:false,type:"post"}).responseText;
				return _3ee=="true";
			},
			message: remoteMessage
		}
	});
