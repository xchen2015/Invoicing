<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="com.pinfly.purchasecharge.core.util.PurchaseChargeConstants"%>
<%@ page import="com.pinfly.purchasecharge.core.config.PurchaseChargeProperties"%>
	
	<div style="width:100%; height:430px;">
		<table id="dg-user" title="<spring:message code="userManagement" />" class="easyui-datagrid" url="<c:url value='/user/getModelBySearchForm.html' />" 
			toolbar="#toolbar-user" pagination="true" rownumbers="true" fit="true" singleSelect="true" checkOnSelect="true" selectOnCheck="false" 
			fitColumns="true" sortName="userId" sortOrder="asc" data-options="onClickRow:onClickUserRow, onLoadSuccess:onLoadUserSuccess,onDblClickRow:onDblClickUserRow">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'id',hidden:true"></th>
					<th field="userId" width="50" sortable="true"><spring:message code="userId" /></th>
					<th field="firstName" width="30"><spring:message code="firstName" /></th>
					<th field="lastName" width="30"><spring:message code="lastName" /></th>
					<th field="bod" width="30" sortable="true"><spring:message code="user.birthday" /></th>
					<!-- <th field="pwd" width="50">密码</th> -->
					<th field="admin" width="30" sortable="true" data-options="formatter:cellFormatter_enable, align:'center'"><spring:message code="user.ifAdmin" /></th>
					<th field="enabled" width="30" sortable="true" data-options="formatter:cellFormatter_enable, align:'center'"><spring:message code="user.ifAvailable" /></th>
					<th field="<%=PurchaseChargeConstants.PHONE %>" width="50"><spring:message code="phone" /></th>
					<th field="email" width="50" sortable="false"><spring:message code="email" /></th>
					<th field="role" width="50" sortable="false" data-options="formatter:cellFormatter_userRole"><spring:message code="user.role" /></th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="toolbar-user" style="padding:5px;height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" 
			onclick="newModel('#dlg-user', '<spring:message code="newUser" />', '#fm-user', '<c:url value='/user/addModel.html' />', newUserCallback)" title="<spring:message code="newUser" />"><spring:message code="newUser" /></a> 
		<a id="btn-editUser" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" 
			onclick="editModel('#dg-user', '#dlg-user', '<spring:message code="editUser" />', '#fm-user', '<c:url value='/user/updateModel.html' />', editUserCallback)" title="<spring:message code="selectOneRow" /><spring:message code="editUser" />"><spring:message code="editUser" /></a> 
		<a id="btn-delUser" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" 
			onclick="destroyMultipleModel('#dg-user', '<spring:message code="user" />', '<c:url value='/user/deleteModels.html' />')" title="<spring:message code="checkOneOrMultiple" /><spring:message code="removeUser" />"><spring:message code="removeUser" /></a>
		<a id="btn-resetPwd" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true" 
			onclick="resetUserPwd()" title="<spring:message code="selectOneRow" />重置密码，默认为<%=PurchaseChargeProperties.getDefaultPassword()%>">重置密码</a>
		<a id="btn-showRoleGrid" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" 
			onclick="showRoleGrid()" title="">管理角色</a>
		
		<span style="float: right; margin-right: 5px">
			<input id="searchbox-user" class="easyui-textbox" data-options="width:200, prompt:'<spring:message code="pleaseInputValue" />', icons:[{
                iconCls:'icon-search',
                handler: function(e){
                    var v = $(e.data.target).textbox('getValue');
                    doSearchUser(v);
                }
            }]" />
		</span>
	</div>

	<div id="dlg-user" class="easyui-dialog" style="width: 800px; height: 320px; padding: 15px" 
		buttons="#dlg-buttons-user" data-options="modal:true, closed:true">
		<div style="width:100%;height:100%">
			<form id="fm-user" class="fm" style="margin:0 auto;padding:0px;width:100%;height:100%;" method="post" novalidate>
				<div class="fitem divHidden">
					<input id="userUniqueId" name="id" value="0">
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label><spring:message code="userId" />:</label> 
						<input id="userId" name="userId" class="easyui-textbox" required="true"
						validType="myRemote['<c:url value='/user/checkExist.html' />', 'userId', '#userUniqueId']">
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label><spring:message code="firstName" />:</label> 
						<input name="firstName" class="easyui-textbox" required="true">
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label><spring:message code="lastName" />:</label> 
						<input name="lastName" class="easyui-textbox" required="true">
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label><spring:message code="user.birthday" />:</label> 
						<input name="bod" class="easyui-datebox" editable="false" style="width:150px">
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label><spring:message code="phone" />:</label> 
						<input name="<%=PurchaseChargeConstants.PHONE %>" class="easyui-textbox" validType="phoneCheck" />
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label><spring:message code="email" />:</label> 
						<input name="email" class="easyui-textbox" validType="email">
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label><spring:message code="user.ifAdmin" />:</label> 
						<input id="admin" name="admin" type="checkbox" checked="checked" style="height:18px" />
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label><spring:message code="user.ifAvailable" />:</label> 
						<input id="enabled" name="enabled" type="checkbox" style="height:18px" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div style="width:280px">
						<label><spring:message code="user.role" />:</label> 
						<input name="role" id="role" class="easyui-combotree" panelHeight="250" style="width:100%" 
							data-options="onShowPanel:showRolePanel,required:true, multiple:true, checkbox:true, 
							onHidePanel:hideSelectRolePanelAfterSelect">
						<input name="roles" id="roles" type="hidden" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div style="width:280px">
						<label><spring:message code="goods.comment" />:</label> 
						<input name="comments" class="easyui-textbox" data-options="" style="width:100%;" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="dlg-buttons-user">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" 
			onclick="saveModel('#dg-user', '#dlg-user', '#fm-user')"><spring:message code="save" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" 
			onclick="javascript:$('#dlg-user').dialog('close')"><spring:message code="cancel" /></a>
	</div>
