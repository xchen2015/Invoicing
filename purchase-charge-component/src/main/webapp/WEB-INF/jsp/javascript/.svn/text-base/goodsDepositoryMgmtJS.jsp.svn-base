<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<% response.setContentType ("text/javascript"); %>

	function reloadGoodsDepository() 
	{
		$('#dlg-manage-goodsDepository #dg-goodsDepository').datagrid('reload');
	}
	function onBeforeLoadGoodsDepository() 
	{
		goodsDepository_editingIndex = undefined;
		goodsDepository_rowId = undefined;
		$('#dlg-manage-goodsDepository #dg-goodsDepository').datagrid('options').url = "<c:url value='/goodsDepository/getAllModel.html' />";
		return true;
	}
	function formatter_goodsDepositoryAction (value,row,index) 
	{
		if (row.editing){
			if(row.id == '') 
			{
				var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="saveGoodsDepository(this)">&nbsp;</a>&nbsp;&nbsp;';
				var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteGoodsDepository(this)">&nbsp;</a>';
				return s+d;
			}
			else 
			{
				var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="saveGoodsDepository(this,' + row.id + ')">&nbsp;</a>&nbsp;&nbsp;';
				var c = '<a href="#" class="icon-no" style="display:inline-block;width:16px;" title="取消" onclick="cancelGoodsDepository(this)">&nbsp;</a>';
				return s+c;
			}
		} else {
			var e = '<a href="#" class="icon-edit" style="display:inline-block;width:16px;" title="编辑" onclick="editGoodsDepository(this)">&nbsp;</a>&nbsp;&nbsp;';
			var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteGoodsDepository(this,' + row.id + ')">&nbsp;</a>';
			return e+d;
		}
	}
	var goodsDepository_editingIndex;
	var goodsDepository_rowId;
	var goodsDepository_onBeforeEdit = function(index,row){
        row.editing = true;
		goodsDepository_editingIndex = index;
		goodsDepository_rowId = row.id;
        updateGoodsDepositoryActions(index);
    }
	var goodsDepository_onAfterEdit = function(index,row){
        row.editing = false;
		goodsDepository_editingIndex = undefined;
		goodsDepository_rowId = '';
        updateGoodsDepositoryActions(index);
    }
	var goodsDepository_onCancelEdit = function(index,row){
        row.editing = false;
		goodsDepository_editingIndex = undefined;
		goodsDepository_rowId = '';
        updateGoodsDepositoryActions(index);
    }
	function updateGoodsDepositoryActions(index){
		$('#dg-goodsDepository').datagrid('updateRow',{
			index: index,
			row:{}
		});
	}
	function editGoodsDepository(target){
		if(goodsDepository_editingIndex == undefined) 
		{
			$('#dg-goodsDepository').datagrid('beginEdit', getRowIndex(target));
		}
	}
	function deleteGoodsDepository(target, rowId){
		if(rowId == undefined) 
		{
			$('#dg-goodsDepository').datagrid('deleteRow', getRowIndex(target));
		}
		else 
		{
			if(goodsDepository_editingIndex == undefined) 
			{
				$.messager.confirm('确认','您确认要删除?',function(r){
					if (r){
						//$('#dg-goodsDepository').datagrid('deleteRow', getRowIndex(target));
						ajaxPostRequest ('<c:url value='/goodsDepository/deleteModels.html' />', {ids:rowId}, reloadGoodsDepository);
					}
				});
			}
		}
	}
	function saveGoodsDepository(target, rowId){
		//console.log(rowId);
		if ($('#dg-goodsDepository').datagrid('validateRow', getRowIndex(target)))
		{
			var goodsDepositoryNameEditor = $('#dg-goodsDepository').datagrid('getEditor', {index:getRowIndex(target), field:'name'});
			var goodsDepositoryName = $(goodsDepositoryNameEditor.target).val();
			$('#dg-goodsDepository').datagrid('endEdit', getRowIndex(target));
			if(rowId == undefined) 
			{
				ajaxPostRequest ('<c:url value='/goodsDepository/addModel.html' />', {id:rowId, name:goodsDepositoryName}, reloadGoodsDepository);
			}
			else 
			{
				ajaxPostRequest ('<c:url value='/goodsDepository/updateModel.html' />', {id:rowId, name:goodsDepositoryName}, reloadGoodsDepository);
			}
		}
	}
	function cancelGoodsDepository(target){
		$('#dg-goodsDepository').datagrid('cancelEdit', getRowIndex(target));
	}
	function insertGoodsDepository(){
		if(goodsDepository_editingIndex == undefined) 
		{
			var index = 0;
			var row = $('#dg-goodsDepository').datagrid('getSelected');
			if (row && row.id){
				index = $('#dg-goodsDepository').datagrid('getRowIndex', row);
				if(index == -1) 
				{
					index = 0;
				}
			}
			$('#dg-goodsDepository').datagrid('insertRow', {
				index: index,
				row:{
					id:'',
					enabled:true
				}
			});
			$('#dg-goodsDepository').datagrid('selectRow',index);
			$('#dg-goodsDepository').datagrid('beginEdit',index);
		}
	}
	$.extend($.fn.validatebox.defaults.rules, {
		checkGoodsDepositoryExist: {
			validator: function(value, param){
				var datagridId = '#dg-goodsDepository';
				var idField = 'goodsDepositoryId';
				var nameField = 'name';
				var data={};
				data[nameField]=value;
				data[idField] = goodsDepository_rowId;
				var _3ee=$.ajax({url:"<c:url value='/goodsDepository/checkExist.html' />",dataType:"json",data:data,async:false,cache:false,type:"post"}).responseText;
				return _3ee=="true";
			},
			message: remoteMessage
		}
	});
	
	function enableCheckedDepository () 
	{
	}
	function disableCheckedDepository () 
	{
	}
