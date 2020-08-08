	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<div id="dlg-dg-accountingType" class="easyui-dialog" title="<spring:message code="navigation.expenseTypeManagement" />" 
		style="width: 500px; height: 400px; padding: 5px;" closed="true" 
		buttons="#dlg-buttons-dg-accountingType" data-options="modal:true">
		<table id="dg-accountingType" class="easyui-datagrid"  
			toolbar="#toolbar-expenseType2" pagination="false" rownumbers="true" singleSelect="true" 
			data-options="fit:true, fitColumns:true, sortName:'accountingMode', sortOrder:'desc', onBeforeEdit:accountingType_onBeforeEdit, 
				onAfterEdit:accountingType_onAfterEdit, onCancelEdit:accountingType_onCancelEdit, onBeforeLoad:onBeforeLoadExpenseType">
			<thead>
				<tr>
					<th field="id" data-options="hidden:true"><spring:message code="id" /></th>
					<th field="accountingMode" width="30" data-options="
	                       sortable:true,
	                       formatter: formatter_accountingMode,
	                       editor:{
	                           type:'combobox',
	                           options:{
	                               valueField:'value',
	                               textField:'label',
	                               editable:false,
								   panelHeight: 'auto',
	                               data:[{label:'收入',value:'IN_COME'},{label:'支出',value:'OUT_LAY'}],
	                               required:true
	                           }
	                       }">模式</th>
					<th field="name" width="50" data-options="sortable:false, editor:{type:'validatebox', options:{required:true, validType:'remoteInlineCheckExist[]'}}"><spring:message code="name" /></th>
					<th field="action" width="50" data-options="formatter:formatter_accountingTypeAction"><spring:message code="action" /></th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="toolbar-expenseType2" style="height:auto">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="insert()" title="<spring:message code="newType" />"></a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload', plain:true" onclick="reloadExpenseType()" title="<spring:message code="refresh" />"></a>
    </div>
    <div id="dlg-buttons-dg-accountingType">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" 
			onclick="javascript:$('#dlg-dg-accountingType').dialog('close')"><spring:message code="close" /></a>
	</div>
