	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<div id="dlg-manage-goodsDepository" class="easyui-dialog" title="<spring:message code="goodsDepository.depositoryManagement" />" 
		style="width: 500px; height: 400px; padding: 5px;" closed="true" 
		buttons="#dlg-buttons-manage-goodsDepository" data-options="modal:true">
		<table id="dg-goodsDepository" class="easyui-datagrid" toolbar="#toolbar-goodsDepository" 
			pagination="false" rownumbers="true" singleSelect="true"  
			data-options="fitColumns:true, fit:true, onBeforeEdit:goodsDepository_onBeforeEdit, 
				onAfterEdit:goodsDepository_onAfterEdit, onCancelEdit:goodsDepository_onCancelEdit, onBeforeLoad:onBeforeLoadGoodsDepository">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="id" hidden="true"><spring:message code="id" /></th>
					<th field="name" width="100" data-options="editor:{type:'validatebox', options:{required:true, validType:'checkGoodsDepositoryExist[]'}}"><spring:message code="name" /></th>
					<th field="enabled" width="50" data-options="formatter:cellFormatter_enable, align:'center'"><spring:message code="customerLevel.enabled" /></th>
					<th field="action" width="50" data-options="formatter:formatter_goodsDepositoryAction"><spring:message code="action" /></th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="toolbar-goodsDepository">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" 
			onclick="insertGoodsDepository()" title="<spring:message code="goodsDepository.newDepository" />"><spring:message code="goodsDepository.newDepository" /></a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" 
			onclick="enableCheckedDepository()" title="">启用已选中</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" 
			onclick="disableCheckedDepository()" title="">禁用已选中</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" 
			onclick="reloadGoodsDepository()" title="<spring:message code="refresh" />"><spring:message code="refresh" /></a>
	</div>
	<div id="dlg-buttons-manage-goodsDepository">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg-manage-goodsDepository').dialog('close')"><spring:message code="close" /></a>
	</div>
	