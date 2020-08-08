	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<div id="dlg-manage-customerType" class="easyui-dialog" title="<spring:message code="customerTypeManagement" />" 
		style="width: 500px; height: 400px; padding: 5px;" closed="true"
		buttons="#dlg-buttons-manage-customerType" data-options="modal:true">
		<table id="dg-customerType" class="easyui-datagrid"  
			toolbar="#toolbar-customerType" pagination="false" rownumbers="true" singleSelect="true" 
			data-options="fit:true, fitColumns:true, onBeforeEdit:customerType_onBeforeEdit, onAfterEdit:customerType_onAfterEdit, 
				onCancelEdit:customerType_onCancelEdit, onBeforeLoad:onBeforeLoadCustomerType">
			<thead>
				<tr>
					<th field="id" data-options="hidden:true"><spring:message code="id" /></th>
					<th field="name" width="100" data-options="sortable:false, editor:{type:'validatebox', options:{required:true, validType:'remoteInlineCheckExist[]'}}"><spring:message code="name" /></th>
					<th field="action" width="50" data-options="formatter:formatter_customerTypeAction">操作</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="toolbar-customerType">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="insertCustomerType()" title="<spring:message code="newType" />"></a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload', plain:true" onclick="reloadCustomerType()" title="<spring:message code="refresh" />"></a>
	</div>
	<div id="dlg-buttons-manage-customerType">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg-manage-customerType').dialog('close')"><spring:message code="close" /></a>
	</div>
