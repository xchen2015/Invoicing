<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<% response.setContentType ("text/javascript"); %>

	function reloadProviderType() 
	{
		$('#dlg-manage-providerType #dg-providerType').datagrid('reload');
	}
	function onBeforeLoadProviderType() 
	{
		providerType_editingIndex = undefined;
		providerType_rowId = undefined;
		$('#dlg-manage-providerType #dg-providerType').datagrid('options').url = "<c:url value='/providerType/getAllModel.html' />";
		return true;
	}
	function formatter_providerTypeAction (value,row,index) 
	{
		if (row.editing){
			if(row.id == '') 
			{
				var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="saveProviderType(this)">&nbsp;</a>&nbsp;&nbsp;';
				var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteProviderType(this)">&nbsp;</a>';
				return s+d;
			}
			else 
			{
				var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="saveProviderType(this,' + row.id + ')">&nbsp;</a>&nbsp;&nbsp;';
				var c = '<a href="#" class="icon-no" style="display:inline-block;width:16px;" title="取消" onclick="cancelProviderType(this)">&nbsp;</a>';
				return s+c;
			}
		} else {
			var e = '<a href="#" class="icon-edit" style="display:inline-block;width:16px;" title="编辑" onclick="editProviderType(this)">&nbsp;</a>&nbsp;&nbsp;';
			var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteProviderType(this,' + row.id + ')">&nbsp;</a>';
			return e+d;
		}
	}
	var providerType_editingIndex;
	var providerType_rowId;
	var providerType_onBeforeEdit = function(index,row){
        row.editing = true;
		providerType_editingIndex = index;
		providerType_rowId = row.id;
        updateProviderTypeActions(index);
    }
	var providerType_onAfterEdit = function(index,row){
        row.editing = false;
		providerType_editingIndex = undefined;
		providerType_rowId = '';
        updateProviderTypeActions(index);
    }
	var providerType_onCancelEdit = function(index,row){
        row.editing = false;
		providerType_editingIndex = undefined;
		providerType_rowId = '';
        updateProviderTypeActions(index);
    }
	function updateProviderTypeActions(index){
		$('#dg-providerType').datagrid('updateRow',{
			index: index,
			row:{}
		});
	}
	
	function editProviderType(target){
		if(providerType_editingIndex == undefined) 
		{
			$('#dg-providerType').datagrid('beginEdit', getRowIndex(target));
		}
	}
	function deleteProviderType(target, rowId){
		if(rowId == undefined) 
		{
			$('#dg-providerType').datagrid('deleteRow', getRowIndex(target));
		}
		else 
		{
			if(providerType_editingIndex == undefined) 
			{
				$.messager.confirm('确认','您确认要删除?',function(r){
					if (r){
						//$('#dg-providerType').datagrid('deleteRow', getRowIndex(target));
						ajaxPostRequest ('<c:url value='/providerType/deleteModels.html' />', {ids:rowId}, reloadProviderType);
					}
				});
			}
		}
	}
	function saveProviderType(target, rowId){
		//console.log(rowId);
		if ($('#dg-providerType').datagrid('validateRow', getRowIndex(target)))
		{
			var providerTypeNameEditor = $('#dg-providerType').datagrid('getEditor', {index:getRowIndex(target), field:'name'});
			var providerTypeName = $(providerTypeNameEditor.target).val();
			//console.log(accountingMode);
			//console.log(providerTypeName);
			$('#dg-providerType').datagrid('endEdit', getRowIndex(target));
			if(rowId == undefined) 
			{
				ajaxPostRequest ('<c:url value='/providerType/addModel.html' />', {id:rowId, name:providerTypeName}, reloadProviderType);
			}
			else 
			{
				ajaxPostRequest ('<c:url value='/providerType/updateModel.html' />', {id:rowId, name:providerTypeName}, reloadProviderType);
			}
		}
	}
	function cancelProviderType(target){
		$('#dg-providerType').datagrid('cancelEdit', getRowIndex(target));
	}
	function insertProviderType(){
		if(providerType_editingIndex == undefined) 
		{
			var row = $('#dg-providerType').datagrid('getSelected');
			if (row){
				var index = $('#dg-providerType').datagrid('getRowIndex', row);
			} else {
				index = 0;
			}
			$('#dg-providerType').datagrid('insertRow', {
				index: index,
				row:{
					id:''
				}
			});
			$('#dg-providerType').datagrid('selectRow',index);
			$('#dg-providerType').datagrid('beginEdit',index);
		}
	}
	$.extend($.fn.validatebox.defaults.rules, {
		remoteInlineProviderTypeCheckExist: {
			validator: function(value, param){
				var datagridId = '#dg-providerType';
				var idField = 'providerTypeId';
				var nameField = 'name';
				var data={};
				data[nameField]=value;
				data[idField] = providerType_rowId;
				//var nameEditor = $(datagridId).datagrid('getEditor', {index:providerType_editingIndex, field:nameField});
				//data[nameField] = $(nameEditor.target).val();
				//console.log(data);
				var _3ee=$.ajax({url:"<c:url value='/providerType/checkExist.html' />",dataType:"json",data:data,async:false,cache:false,type:"post"}).responseText;
				return _3ee=="true";
			},
			message: remoteMessage
		}
	});
