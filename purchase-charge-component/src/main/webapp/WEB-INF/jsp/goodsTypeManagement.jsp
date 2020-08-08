	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<div style="height:100%;">
		<div id="toolbar-goodsType" style="width:100%; background-color:#F4F4F4">
			<c:if test="${sessionScope.login_user.admin}">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" 
					onclick="goodsType_addType()" title="<spring:message code="appendRootType" />"></a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" 
					onclick="removeMultipleFromTree('#tree_goodsType', '<c:url value='/goodsType/deleteModels.html' />')" title="<spring:message code="checkOneOrMultiple2" /><spring:message code="removeType" />"></a>
    		</c:if>
			<a id="gt-toggleAll" href="javascript:void(0)" class="easyui-linkbutton" iconCls="datagrid-row-collapse" plain="true" 
				onclick="toggleAll('#tree_goodsType')" title="<spring:message code="collapseAll" />/<spring:message code="expandAll" />"></a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" 
				onclick="$('#tree_goodsType').tree('reload', '')" title="<spring:message code="refresh" />"></a>
		</div>
		<ul id="tree_goodsType" style="width:100%; height: 90%" class="easyui-tree" data-options="
			url: '<c:url value='/goodsType/getAllGoodsType.html' />',
			animate: true,
			checkbox: true,
			onBeforeLoad: goodsType_onBeforeLoad,
			onContextMenu: goodsType_onContextMenu,
			onAfterEdit: goodsType_onAfterEdit,
			onSelect: goodsType_onSelectGoodsType,
			formatter: goodsType_formatterGoodsType">
		</ul>
	</div>
	
    <div id="mm_goodsType" class="easyui-menu" style="width:120px;">
    	<c:if test="${sessionScope.login_user.admin}">
	        <div onclick="goodsType_addType()" data-options="iconCls:'icon-add'"><spring:message code="appendSubType" /></div>
	        <div onclick="goodsType_updateType()" data-options="iconCls:'icon-edit'"><spring:message code="editType" /></div>
	        <div onclick="goodsType_deleteType()" data-options="iconCls:'icon-remove'"><spring:message code="removeType" /></div>
    	</c:if>
    </div>
