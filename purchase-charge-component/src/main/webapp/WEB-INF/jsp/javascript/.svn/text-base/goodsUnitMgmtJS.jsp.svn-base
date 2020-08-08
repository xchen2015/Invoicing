<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<% response.setContentType ("text/javascript"); %>

	function reloadGoodsUnit() 
	{
		$('#dlg-manage-goodsUnit #dg-goodsUnit').datagrid('reload');
	}
	function onBeforeLoadGoodsUnit() 
	{
		goodsUnit_editingIndex = undefined;
		goodsUnit_rowId = undefined;
		$('#dlg-manage-goodsUnit #dg-goodsUnit').datagrid('options').url = "<c:url value='/goodsUnit/getAllModel.html' />";
		return true;
	}
	function formatter_goodsUnitAction (value,row,index) 
	{
		if (row.editing){
			if(row.id == '') 
			{
				var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="saveGoodsUnit(this)">&nbsp;</a>&nbsp;&nbsp;';
				var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteGoodsUnit(this)">&nbsp;</a>';
				return s+d;
			}
			else 
			{
				var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="saveGoodsUnit(this,' + row.id + ')">&nbsp;</a>&nbsp;&nbsp;';
				var c = '<a href="#" class="icon-no" style="display:inline-block;width:16px;" title="取消" onclick="cancelGoodsUnit(this)">&nbsp;</a>';
				return s+c;
			}
		} else {
			var e = '<a href="#" class="icon-edit" style="display:inline-block;width:16px;" title="编辑" onclick="editGoodsUnit(this)">&nbsp;</a>&nbsp;&nbsp;';
			var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteGoodsUnit(this,' + row.id + ')">&nbsp;</a>';
			return e+d;
		}
	}
	var goodsUnit_editingIndex;
	var goodsUnit_rowId;
	var goodsUnit_onBeforeEdit = function(index,row){
        row.editing = true;
		goodsUnit_editingIndex = index;
		goodsUnit_rowId = row.id;
        updateGoodsUnitActions(index);
    }
	var goodsUnit_onAfterEdit = function(index,row){
        row.editing = false;
		goodsUnit_editingIndex = undefined;
		goodsUnit_rowId = '';
        updateGoodsUnitActions(index);
    }
	var goodsUnit_onCancelEdit = function(index,row){
        row.editing = false;
		goodsUnit_editingIndex = undefined;
		goodsUnit_rowId = '';
        updateGoodsUnitActions(index);
    }
	function updateGoodsUnitActions(index){
		$('#dg-goodsUnit').datagrid('updateRow',{
			index: index,
			row:{}
		});
	}
	function editGoodsUnit(target){
		if(goodsUnit_editingIndex == undefined) 
		{
			$('#dg-goodsUnit').datagrid('beginEdit', getRowIndex(target));
		}
	}
	function deleteGoodsUnit(target, rowId){
		if(rowId == undefined) 
		{
			$('#dg-goodsUnit').datagrid('deleteRow', getRowIndex(target));
		}
		else 
		{
			if(goodsUnit_editingIndex == undefined) 
			{
				$.messager.confirm('确认','您确认要删除?',function(r){
					if (r){
						//$('#dg-goodsUnit').datagrid('deleteRow', getRowIndex(target));
						ajaxPostRequest ('<c:url value='/goodsUnit/deleteModels.html' />', {ids:rowId}, reloadGoodsUnit);
					}
				});
			}
		}
	}
	function saveGoodsUnit(target, rowId){
		//console.log(rowId);
		if ($('#dg-goodsUnit').datagrid('validateRow', getRowIndex(target)))
		{
			var goodsUnitNameEditor = $('#dg-goodsUnit').datagrid('getEditor', {index:getRowIndex(target), field:'name'});
			var goodsUnitName = $(goodsUnitNameEditor.target).val();
			$('#dg-goodsUnit').datagrid('endEdit', getRowIndex(target));
			if(rowId == undefined) 
			{
				ajaxPostRequest ('<c:url value='/goodsUnit/addModel.html' />', {id:rowId, name:goodsUnitName}, reloadGoodsUnit);
			}
			else 
			{
				ajaxPostRequest ('<c:url value='/goodsUnit/updateModel.html' />', {id:rowId, name:goodsUnitName}, reloadGoodsUnit);
			}
		}
	}
	function cancelGoodsUnit(target){
		$('#dg-goodsUnit').datagrid('cancelEdit', getRowIndex(target));
	}
	function insertGoodsUnit(){
		if(goodsUnit_editingIndex == undefined) 
		{
			var index = 0;
			var row = $('#dg-goodsUnit').datagrid('getSelected');
			if (row && row.id){
				index = $('#dg-goodsUnit').datagrid('getRowIndex', row);
				if(index == -1) 
				{
					index = 0;
				}
			}
			$('#dg-goodsUnit').datagrid('insertRow', {
				index: index,
				row:{
					id:''
				}
			});
			$('#dg-goodsUnit').datagrid('selectRow',index);
			$('#dg-goodsUnit').datagrid('beginEdit',index);
		}
	}
	$.extend($.fn.validatebox.defaults.rules, {
		checkGoodsUnitExist: {
			validator: function(value, param){
				var datagridId = '#dg-goodsUnit';
				var idField = 'goodsUnitId';
				var nameField = 'name';
				var data={};
				data[nameField]=value;
				data[idField] = goodsUnit_rowId;
				var _3ee=$.ajax({url:"<c:url value='/goodsUnit/checkExist.html' />",dataType:"json",data:data,async:false,cache:false,type:"post"}).responseText;
				return _3ee=="true";
			},
			message: remoteMessage
		}
	});
