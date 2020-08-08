	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<div id="dlg-manage-providerType" class="easyui-dialog" title="<spring:message code="providerTypeManagement" />" 
		style="width: 500px; height: 400px; padding: 5px;" closed="true"
		buttons="#dlg-buttons-manage-providerType" data-options="modal:true">
		<table id="dg-providerType" class="easyui-datagrid" 
			toolbar="#toolbar-providerType" pagination="false" rownumbers="true" singleSelect="true" 
			data-options="fit:true, fitColumns:true, onBeforeEdit:providerType_onBeforeEdit, 
				onAfterEdit:providerType_onAfterEdit, onCancelEdit:providerType_onCancelEdit, onBeforeLoad:onBeforeLoadProviderType">
			<thead>
				<tr>
					<th field="id" data-options="hidden:true"><spring:message code="id" /></th>
					<th field="name" width="100" data-options="sortable:false, editor:{type:'validatebox', options:{required:true, validType:'remoteInlineProviderTypeCheckExist[]'}}"><spring:message code="name" /></th>
					<th field="action" width="50" data-options="formatter:formatter_providerTypeAction">操作</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="toolbar-providerType">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="insertProviderType()" title="<spring:message code="newType" />"></a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload', plain:true" onclick="reloadProviderType()" title="<spring:message code="refresh" />"></a>
	</div>
	<div id="dlg-buttons-manage-providerType">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg-manage-providerType').dialog('close')"><spring:message code="close" /></a>
	</div>
