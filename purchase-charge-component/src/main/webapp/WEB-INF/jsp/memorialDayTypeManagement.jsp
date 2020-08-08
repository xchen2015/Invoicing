	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<script type="text/javascript">
		var newMemorialDayTypeCallback = function () {$('#dlg-memorialDayType #fm-memorialDayType #name').focus();}
	</script>
	
	<div>
		<table id="dg-memorialDayType" title="<spring:message code="memorialDayTypeManagement" />" class="easyui-datagrid"
			style="width: 500px; height: 400px" url="<c:url value='/memorialDayType/getAllModel.html' />"
			toolbar="#toolbar-memorialDayType" pagination="false" rownumbers="true" 
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
		
	<div id="toolbar-memorialDayType">
		<a href="javascript:void(0)" class="easyui-linkbutton" 
			iconCls="icon-add" plain="true" onclick="newModel('#dlg-memorialDayType', '<spring:message code="newType" />', '#fm-memorialDayType', '<c:url value='/memorialDayType/addModel.html' />', newMemorialDayTypeCallback)" title="<spring:message code="newType" />"><spring:message code="newType" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true" onclick="editModel('#dg-memorialDayType', '#dlg-memorialDayType', '<spring:message code="editType" />', '#fm-memorialDayType', '<c:url value='/memorialDayType/updateModel.html' />', newMemorialDayTypeCallback)"  title="<spring:message code="selectOneRow" /><spring:message code="editType" />"><spring:message code="editType" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true" onclick="destroyMultipleModel('#dg-memorialDayType', '<spring:message code="memorialDayType" />', '<c:url value='/memorialDayType/deleteModels.html' />', function(){loadGridData ('#dg-memorialDayType', '<c:url value='/memorialDayType/getAllModel.html' />')})" title="<spring:message code="checkOneOrMultiple" /><spring:message code="removeType" />"><spring:message code="removeType" /></a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-reload" plain="true" onclick="loadGridData('#dg-memorialDayType', '<c:url value='/memorialDayType/getAllModel.html' />')" title="<spring:message code="refresh" />"><spring:message code="refresh" /></a>
	</div>

	<div id="dlg-memorialDayType" class="easyui-dialog"
		style="width: 400px; height: 300px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons-memorialDayType" data-options="modal:true">
		<div class="ftitle"><spring:message code="memorialDayType" /></div>
		<form id="fm-memorialDayType" class="fm" method="post" novalidate>
			<div class="fitem divHidden">
				<input name="id" id="memorialDayTypeId" value="0" />
			</div>
			<div class="fitem">
				<label><spring:message code="name" />:</label> 
				<input id="name" name="name" class="easyui-validatebox" required="true" 
				validType="myRemote['<c:url value='/memorialDayType/checkExist.html' />', 'name', '#memorialDayTypeId']" 
				onkeyup="if(event.keyCode == 13) {$('#save-memorialDayType').click()}" />
			</div>
		</form>
	</div>
	<div id="dlg-buttons-memorialDayType">
		<a id="save-memorialDayType" href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="saveModel('#dg-memorialDayType', '#dlg-memorialDayType', '#fm-memorialDayType', function(){loadGridData ('#dg-memorialDayType', '<c:url value='/memorialDayType/getAllModel.html' />')})"><spring:message code="save" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg-memorialDayType').dialog('close')"><spring:message code="cancel" /></a>
	</div>
