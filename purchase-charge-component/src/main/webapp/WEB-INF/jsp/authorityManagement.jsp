	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<script type="text/javascript">
		var editAuthorityCallback = function() 
		{
			var authority = $('#dg-authority').datagrid('getSelected');
			if(authority) 
			{
				if(authority.enabled) 
				{
					$('#dlg-authority #fm-authority #enabled')[0].checked = true;
				}
				else 
				{
					$('#dlg-authority #fm-authority #enabled')[0].checked = false;
				}
			}
		}
    </script>

	<table id="dg-authority" title="<spring:message code="authority.mgmt" />" class="easyui-treegrid" url="<c:url value='/authority/getAllModel.html' />" 
		toolbar="" rownumbers="true" fit="true" singleSelect="true" checkOnSelect="true" selectOnCheck="false" 
		fitColumns="true" data-options="idField:'id',treeField:'name'">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'id',hidden:true"></th>
				<th field="name" width="50" sortable="true"><spring:message code="authority.name" /></th>
				<th field="enabled" width="30" sortable="true" data-options="formatter:cellFormatter_enable, align:'center'"><spring:message code="authority.ifAvailable" /></th>
				<th field="url" width="80" sortable="true" data-options=""><spring:message code="authority.url" /></th>
			</tr>
		</thead>
	</table>
	
	<div id="toolbar-authority">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" 
			onclick="newModel('#dlg-authority', '<spring:message code="authority.newAuth" />', '#fm-authority', '<c:url value='/authority/addModel.html' />')" title="<spring:message code="authority.newAuth" />"><spring:message code="authority.newAuth" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" 
			onclick="editModel('#dg-authority', '#dlg-authority', '<spring:message code="authority.editAuth" />', '#fm-authority', '<c:url value='/authority/updateModel.html' />', editAuthorityCallback)" title="<spring:message code="selectOneRow" /><spring:message code="authority.editAuth" />"><spring:message code="authority.editAuth" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" 
			onclick="destroyMultipleModel('#dg-authority', '<spring:message code="authority" />', '<c:url value='/authority/deleteModels.html' />')" title="<spring:message code="checkOneOrMultiple" /><spring:message code="authority.removeAuth" />"><spring:message code="authority.removeAuth" /></a>
	</div>

	<div id="dlg-authority" class="easyui-dialog"
		style="width: 800px; height: 300px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons-authority" data-options="modal:true">
		<form id="fm-authority" class="fm" method="post" novalidate>
			<div class="fitem divHidden">
				<input id="authorityId" name="id" value="0">
			</div>
			<div class="fitem">
				<label><spring:message code="authority.name" />:</label> 
				<input name="name" class="easyui-validatebox" required="true"
				validType="myRemote['<c:url value='/authority/checkExist.html' />', 'name', '#authorityId']">
			</div>
			<div class="fitem">
				<label><spring:message code="authority.ifAvailable" />:</label> 
				<input id="enabled" name="enabled" type="checkbox" style="width:20px">
			</div>
			<div class="fitem">
				<label><spring:message code="authority.url" />:</label> 
				<input name="url" id="url" class="easyui-textbox" data-options="">
			</div>
		</form>
	</div>
	<div id="dlg-buttons-authority">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" 
			onclick="saveModel('#dg-authority', '#dlg-authority', '#fm-authority')"><spring:message code="save" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" 
			onclick="javascript:$('#dlg-authority').dialog('close')"><spring:message code="close" /></a>
	</div>