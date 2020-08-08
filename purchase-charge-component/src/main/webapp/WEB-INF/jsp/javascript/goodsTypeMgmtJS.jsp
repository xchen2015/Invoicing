<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<% response.setContentType ("text/javascript"); %>

	var expand = true;
	function toggleAll(treeId) 
	{
		if(expand) 
		{
			collapseAll(treeId);
			expand = false;
			$('#gt-toggleAll').html('<span class="l-btn-left l-btn-icon-left"><span class="l-btn-text"></span><span class="l-btn-icon datagrid-row-expand">&nbsp;</span></span>');
		}
		else 
		{
			expandAll(treeId);
			expand = true;
			$('#gt-toggleAll').html('<span class="l-btn-left l-btn-icon-left"><span class="l-btn-text"></span><span class="l-btn-icon datagrid-row-collapse">&nbsp;</span></span>');
		}
	}
	
	function goodsType_onAfterEdit(node) 
	{
		var url = '<c:url value='/goodsType/addModelWithResponse.html' />';
		if(node.id != newNodeIndex) 
		{
			url = '<c:url value='/goodsType/updateModelWithResponse.html' />';
		}
		saveTree2('#tree_goodsType', url, {id:node.id, text:node.text, parentId:node.parentId});
	}
	var newNodeIndex = 0;
	function goodsType_addType () 
	{
		var t = $('#tree_goodsType');
		//var selectedNode = t.tree('getSelected');
		var selectedNode = parentGoodsType;
		var parentId = selectedNode ? selectedNode.id : '';
		newNodeIndex --;
		t.tree('append', {
			parent: (selectedNode ? selectedNode.target : null),
			data: [{
				id: newNodeIndex,
				text: '',
				parentId: parentId
			}]
		});
		var node = t.tree('find', newNodeIndex);
		t.tree('beginEdit', node.target);
	}
	function goodsType_updateType () 
	{
		var t = $('#tree_goodsType');
		//var node = t.tree('getSelected');
		var node = parentGoodsType;
		t.tree('beginEdit', node.target);
	}
	function goodsType_deleteType(){
		var t = $('#tree_goodsType');
	    var node = parentGoodsType;
	    var requestUrl = '<c:url value='/goodsType/deleteModels.html' />';
		if (node) {
			$.messager.confirm('确认',
				'您确认要删除 ' + node.text + '?', function(r) {
					if (r) {
						$.post(requestUrl, {
							ids : node.id
						}, function(result) {
							console.log(result);
							if (result.statusCode == '400' || result.statusCode == '500') {
								showFailureMsg(result.message);
							}
							else if (result.statusCode == '401' || result.statusCode == '403') {
								showWarningMsg(result.message);
							} else {
								t.tree('remove', node.target);
								showSuccessMsg (result.message);
							}
						}, 'json');
					}
				});
		}
	}
	
	var parentGoodsType;
	function goodsType_onContextMenu(e,node) 
	{
		if(node.id != '0') 
		{
			e.preventDefault();
			$(this).tree('check',node.target);
			parentGoodsType = node;
			$('#mm_goodsType').menu('show',{
				left: e.pageX,
				top: e.pageY
			});
		}
	}
	function goodsType_onBeforeLoad(node, param)
	{
		parentGoodsType = undefined;
		if(!expand) 
		{
			expand = true;
			$('#gt-toggleAll').html('<span class="l-btn-left l-btn-icon-left"><span class="l-btn-text"></span><span class="l-btn-icon datagrid-row-collapse">&nbsp;</span></span>');
		}
		return true;
	}
	function goodsType_formatterGoodsType(node)
	{
		var s = node.text;
		if(node.goodsAmount && node.goodsAmount > 0) 
		{
			s += '&nbsp;<span style=\'color:black\'>(' + node.goodsAmount + ')</span>';
		}
		if (node.children && node.children.length > 0){
			s += '&nbsp;<span style=\'color:blue\'>(' + node.children.length + ')</span>';
		}
		return s;
	}
	
	// goods type section
	var selectedGoodsType;
	var goodsType_onSelectGoodsType = function(node) {
		selectedGoodsType = node;
		$('#dg-goods').datagrid(
			'load',
			{
				searchKey : $('#toolbar-goods #goodsSearchBox').searchbox('getValue'),
				goodsTypeId : node.id
			});
	}
