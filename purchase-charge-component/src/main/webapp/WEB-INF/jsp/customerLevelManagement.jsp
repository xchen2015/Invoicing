	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<div id="dlg-manage-customerLevel" class="easyui-dialog" title="<spring:message code="customerLevel.mgmt" />" 
		style="width: 800px; height: 500px; padding: 5px;" closed="true"
		buttons="#dlg-buttons-manage-customerLevel" data-options="modal:true">
		<table id="dg-customerLevel" class="easyui-datagrid"  
			toolbar="#toolbar-customerLevel" pagination="false" rownumbers="true" singleSelect="true" 
			data-options="fit:true, fitColumns:true, onBeforeEdit:customerLevel_onBeforeEdit, 
				onAfterEdit:customerLevel_onAfterEdit, onCancelEdit:customerLevel_onCancelEdit, onBeforeLoad:onBeforeLoadCustomerLevel">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="id" data-options="hidden:true"><spring:message code="id" /></th>
					<th field="name" width="100" data-options="editor:{type:'validatebox', options:{required:true, validType:'checkCustomerLevelExist[]'}}"><spring:message code="customerLevel.name" /></th>
					<th field="order" width="30" data-options="editor:{type:'numberbox', options:{required:true, validType:'checkLevelOrderExist[]'}}"><spring:message code="customerLevel.order" /></th>
					<th field="saleMoney" width="70" data-options="editor:{type:'numberbox'}"><spring:message code="customerLevel.saleMoney" /></th>
					<th field="profitMoney" width="70" data-options="editor:{type:'numberbox'}"><spring:message code="customerLevel.profitMoney" /></th>
					<th field="paymentDays" width="50" data-options="editor:{type:'numberbox'}"><spring:message code="customerLevel.paymentDays" /></th>
					<th field="maxDebt" width="70" data-options="editor:{type:'numberbox'}"><spring:message code="customerLevel.maxDebt" /></th>
					<th field="priceRate" width="50" data-options="editor:{type:'numberbox',options:{precision:2}}"><spring:message code="customerLevel.priceRate" /></th>
					<th field="enabled" width="50" data-options="formatter:cellFormatter_enable, align:'center'"><spring:message code="customerLevel.enabled" /></th>
					<th field="action" width="50" data-options="formatter:formatter_customerLevelAction"><spring:message code="action" /></th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="toolbar-customerLevel">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
			onclick="insertCustomerLevel()" title="<spring:message code="customerLevel.newLevel" />"></a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" 
			onclick="enableCheckedLevel()" title="">启用已选中</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" 
			onclick="disableCheckedLevel()" title="">禁用已选中</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload', plain:true" 
			onclick="reloadCustomerLevel()" title="<spring:message code="refresh" />"></a>
	</div>
	<div id="dlg-buttons-manage-customerLevel">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg-manage-customerLevel').dialog('close')"><spring:message code="close" /></a>
	</div>
