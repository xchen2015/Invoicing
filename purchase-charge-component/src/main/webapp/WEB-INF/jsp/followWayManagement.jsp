	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<script type="text/javascript">
		var newFollowWayCallback = function () {$('#dlg-followWay #fm-followWay #name').focus();}
	</script>
	
	<div>
		<table id="dg-followWay" title="<spring:message code="followWayManagement" />" class="easyui-datagrid"
			style="width: 500px; height: 400px" url="<c:url value='/followWay/getAllModel.html' />"
			toolbar="#toolbar-followWay" pagination="false" rownumbers="true" 
			 singleSelect="true" checkOnSelect="true" selectOnCheck="false"
			data-options="fitColumns:true">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="id" width="30" sortable="true"><spring:message code="id" /></th>
					<th field="name" width="100" sortable="true"><spring:message code="name" /></th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="toolbar-followWay">
		<a href="javascript:void(0)" class="easyui-linkbutton" 
			iconCls="icon-add" plain="true" onclick="newModel('#dlg-followWay', '<spring:message code="newWay" />', '#fm-followWay', '<c:url value='/followWay/addModel.html' />', newFollowWayCallback)" title="<spring:message code="newWay" />"><spring:message code="newWay" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true" onclick="editModel('#dg-followWay', '#dlg-followWay', '<spring:message code="editWay" />', '#fm-followWay', '<c:url value='/followWay/updateModel.html' />', newFollowWayCallback)" title="<spring:message code="selectOneRow" /><spring:message code="editWay" />"><spring:message code="editWay" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true" onclick="destroyMultipleModel('#dg-followWay', '<spring:message code="followWay" />', '<c:url value='/followWay/deleteModels.html' />', function(){loadGridData ('#dg-followWay', '<c:url value='/followWay/getAllModel.html' />')})" title="<spring:message code="checkOneOrMultiple" /><spring:message code="removeWay" />"><spring:message code="removeWay" /></a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-reload" plain="true" onclick="loadGridData ('#dg-followWay', '<c:url value='/followWay/getAllModel.html' />')" title="<spring:message code="refresh" />"><spring:message code="refresh" /></a>
	</div>

	<div id="dlg-followWay" class="easyui-dialog"
		style="width: 400px; height: 300px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons-followWay" data-options="modal:true">
		<div class="ftitle"><spring:message code="followWay" /></div>
		<form id="fm-followWay" class="fm" method="post" novalidate>
			<div class="fitem divHidden">
				<input name="id" id="followWayId" value="0" />
			</div>
			<div class="fitem">
				<label><spring:message code="name" />:</label> 
				<input id="name" name="name" class="easyui-validatebox" required="true" 
				validType="myRemote['<c:url value='/followWay/checkExist.html' />', 'name', '#followWayId']" 
				onkeyup="if(event.keyCode == 13) {$('#save-followWay').click()}" />
			</div>
		</form>
	</div>
	<div id="dlg-buttons-followWay">
		<a id="save-followWay" href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="saveModel('#dg-followWay', '#dlg-followWay', '#fm-followWay', function(){loadGridData ('#dg-followWay', '<c:url value='/followWay/getAllModel.html' />')})"><spring:message code="save" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg-followWay').dialog('close')"><spring:message code="cancel" /></a>
	</div>
