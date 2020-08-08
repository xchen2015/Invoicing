	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<div id="dlg-manage-goodsUnit" class="easyui-dialog" title="<spring:message code="goodsUnitManagement" />" 
		style="width: 500px; height: 400px; padding: 5px;" closed="true" 
		buttons="#dlg-buttons-manage-goodsUnit" data-options="modal:true">
		<table id="dg-goodsUnit" class="easyui-datagrid" toolbar="#toolbar-goodsUnit" pagination="false" 
			rownumbers="true" singleSelect="true" checkOnSelect="true" selectOnCheck="false" 
			data-options="fitColumns:true, fit:true, onBeforeEdit:goodsUnit_onBeforeEdit, 
				onAfterEdit:goodsUnit_onAfterEdit, onCancelEdit:goodsUnit_onCancelEdit, onBeforeLoad:onBeforeLoadGoodsUnit">
			<thead>
				<tr>
					<!-- <th data-options="field:'ck',checkbox:true"></th> -->
					<th field="id" hidden="true"><spring:message code="id" /></th>
					<th field="name" width="50" data-options="editor:{type:'validatebox', options:{required:true, validType:'checkGoodsUnitExist[]'}}"><spring:message code="name" /></th>
					<th field="action" width="50" data-options="formatter:formatter_goodsUnitAction"><spring:message code="action" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="dlg-buttons-manage-goodsUnit">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg-manage-goodsUnit').dialog('close')"><spring:message code="close" /></a>
	</div>
	<div id="toolbar-goodsUnit">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" 
			onclick="insertGoodsUnit()" title="<spring:message code="newUnit" />"><spring:message code="newUnit" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" 
			onclick="reloadGoodsUnit()" title="<spring:message code="refresh" />"><spring:message code="refresh" /></a>
	</div>
