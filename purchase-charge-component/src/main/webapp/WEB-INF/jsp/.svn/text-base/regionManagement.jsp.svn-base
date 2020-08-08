	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<script type="text/javascript">
		var newRegionCallback = function () {$('#dlg-region #fm-region #text').focus();}
	</script>
	
	<div id="regionManagement-panel" class="" title="" style="width:500px; height:400px; padding:2px 10px 10px 2px;" data-options="">
		<div id="toolbar-region" style="width: 100%; background-color: #F4F4F4">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="newRootTreeNode('#dlg-region', '<spring:message code="newRootRegion" />', '<c:url value='/region/addModel.html' />', newRegionCallback)" title="<spring:message code="newRootRegion" />"><spring:message code="newRootRegion" /></a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true" onclick="removeMultipleFromTree('#tt_region', '<c:url value='/region/deleteModels.html' />')" title="<spring:message code="checkOneOrMultiple" /><spring:message code="removeRegion" />"><spring:message code="removeRegion" /></a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-reload" plain="true" onclick="loadTreeData ('#tt_region', '<c:url value='/region/getAllModel.html' />')" title="<spring:message code="refresh" />"><spring:message code="refresh" /></a>
		</div>
		<ul id="tt_region" style="width:478px; height: 92%" class="easyui-tree" data-options="
	        url: '<c:url value='/region/getAllModel.html' />',
	        animate: true,
	        checkbox: true,
	        onContextMenu: function(e,node) {
	            e.preventDefault();
	            $(this).tree('select',node.target);
	            $('#mm_region').menu('show',{
	                left: e.pageX,
	                top: e.pageY
	            });
	        },
			formatter:function(node){
				var s = node.text;
				if (node.children && node.children.length > 0){
					s += '&nbsp;<span style=\'color:blue\'>(' + node.children.length + ')</span>';
				}
				return s;
			}
		">
	    </ul>
	</div>
	
    <div id="mm_region" class="easyui-menu" style="width:120px;">
        <div onclick="newRootTreeNode('#dlg-region', '<spring:message code="newRootRegion" />', '<c:url value='/region/addModel.html' />', newRegionCallback)" data-options="iconCls:'icon-add'"><spring:message code="newRootRegion" /></div>
        <div onclick="appendToTree('#tt_region', '#fm-region #parentId', '#dlg-region', '<spring:message code="appendRegion" />', '<c:url value='/region/addModel.html' />', newRegionCallback)" data-options="iconCls:'icon-add'"><spring:message code="appendRegion" /></div>
        <div onclick="editTree('#tt_region', '#fm-region #parentId', '#fm-region #regionId', '#fm-region #text', '#dlg-region', '<spring:message code="editRegion" />', '<c:url value='/region/updateModel.html' />', newRegionCallback)" data-options="iconCls:'icon-edit'"><spring:message code="editRegion" /></div>
        <div onclick="removeFromTree('#tt_region', '<c:url value='/region/deleteModels.html' />')" data-options="iconCls:'icon-remove'"><spring:message code="removeRegion" /></div>
        <div class="menu-sep"></div>
        <div onclick="expandTree('#tt_region')"><spring:message code="expand" /></div>
        <div onclick="collapseTree('#tt_region')"><spring:message code="collapse" /></div>
    </div>

	<div id="dlg-region" class="easyui-dialog"
		style="width: 400px; height: 300px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons-region" data-options="modal:true">
		<div class="ftitle"><spring:message code="region" /></div>
		<form id="fm-region" class="fm" method="post" novalidate>
			<div class="fitem divHidden">
				<input id="parentId" name="parentId" class="easyui-validatebox" readonly="readonly" />
			</div>
			<div class="fitem divHidden">
				<input id="regionId" name="id" value="0" />
			</div>
			<div class="fitem">
				<label><spring:message code="name" />:</label> 
				<input id="text" name="text" class="easyui-validatebox" required="true" 
				validType="myRemote['<c:url value='/region/checkExist.html' />', 'name', '#regionId']" 
				onkeyup="if(event.keyCode == 13) {$('#save-region').click()}" />
			</div>
		</form>
	</div>
	<div id="dlg-buttons-region">
		<a id="save-region" href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="saveTree('#tt_region', '#fm-region', '#dlg-region')"><spring:message code="save" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg-region').dialog('close')"><spring:message code="cancel" /></a>
	</div>
