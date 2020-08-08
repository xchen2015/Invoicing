	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

	<div id="dlg-role-grid" class="easyui-dialog" title="<spring:message code="role.mgmt" />" 
		style="width: 800px; height: 500px; padding: 5px;" closed="true" 
		buttons="#dlg-buttons-role-grid" data-options="modal:true">
		<table id="dg-role" class="easyui-datagrid" toolbar="#toolbar-role" rownumbers="true" fit="true"
			 singleSelect="true" checkOnSelect="true" selectOnCheck="false" fitColumns="true" data-options="onDblClickRow:onDblClickRoleRow">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'id',hidden:true"></th>
					<th field="name" width="50" sortable="true"><spring:message code="role.name" /></th>
					<th field="enabled" width="30" sortable="true" data-options="formatter:cellFormatter_enable, align:'center'"><spring:message code="role.ifAvailable" /></th>
					<th field="authority" width="70" sortable="false" data-options="formatter:cellFormatter_roleAuthority"><spring:message code="role.authority" /></th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="toolbar-role">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" 
			onclick="newModel('#dlg-role', '<spring:message code="role.newRole" />', '#fm-role', '<c:url value='/role/addModel.html' />', newRoleCallback)" title="<spring:message code="role.newRole" />"><spring:message code="role.newRole" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btn-editRole" 
			onclick="editModel('#dg-role', '#dlg-role', '<spring:message code="role.editRole" />', '#fm-role', '<c:url value='/role/updateModel.html' />', editRoleCallback)" title="<spring:message code="selectOneRow" /><spring:message code="role.editRole" />"><spring:message code="role.editRole" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" 
			onclick="destroyMultipleModel('#dg-role', '<spring:message code="role" />', '<c:url value='/role/deleteModels.html' />')" title="<spring:message code="checkOneOrMultiple" /><spring:message code="role.removeRole" />"><spring:message code="role.removeRole" /></a>
	</div>
	<div id="dlg-buttons-role-grid">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" 
			onclick="javascript:$('#dlg-role-grid').dialog('close')"><spring:message code="close" /></a>
	</div>

	<div id="dlg-role" class="easyui-dialog"
		style="width: 530px; height: 300px; padding: 15px 5px" closed="true"
		buttons="#dlg-buttons-role" data-options="modal:true">
		<div style="width:100%;height:100%">
			<form id="fm-role" class="fm" style="margin:0 auto;padding:0px;width:100%;height:100%;" method="post" novalidate>
				<div class="fitem divHidden">
					<input id="roleId" name="id" value="0">
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label><spring:message code="role.name" />:</label> 
						<input name="name" class="easyui-textbox" required="true"
						validType="myRemote['<c:url value='/role/checkExist.html' />', 'name', '#roleId']">
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label><spring:message code="role.ifAvailable" />:</label> 
						<input id="enabled" name="enabled" type="checkbox" style="height:18px" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3" style="width:100%">
					<div style="width:410px">
						<label><spring:message code="role.authority" />:</label> 
						<input name="authority" id="authority" style="width:100%;" class="easyui-combotree"   
							data-options="onShowPanel:showAuthorityPanel,required:true, 
							multiple:true, checkbox:true, onHidePanel:hideAuthPanelAfterSelect">
						<input name="authorities" id="authorities" type="hidden" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="dlg-buttons-role">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" 
			onclick="saveModel('#dg-role', '#dlg-role', '#fm-role')"><spring:message code="save" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" 
			onclick="javascript:$('#dlg-role').dialog('close')"><spring:message code="cancel" /></a>
	</div>
