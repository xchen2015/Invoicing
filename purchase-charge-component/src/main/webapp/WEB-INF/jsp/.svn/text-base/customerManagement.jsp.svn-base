<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	<div style="width:100%; height:430px;">
		<table id="dg-customer" title="<spring:message code="customerManagement" />" class="easyui-datagrid" 
			toolbar="#toolbar-customer" pagination="true" rownumbers="true" singleSelect="true" checkOnSelect="true" selectOnCheck="false" showFooter="true" 
			data-options="fitColumns:true, sortName:'unpayMoney', sortOrder:'desc', fit:true, onClickRow:onClickCustomerRow, onLoadSuccess:onLoadCustomerSuccess, 
				onCheckAll:onCheckAllCustomer, onUncheckAll:onUnCheckCustomer, onCheck:onCheckAllCustomer, onUncheck:onUnCheckCustomer, onDblClickRow:onDblClickCustomerRow, onBeforeLoad:onBeforeLoadCustomer">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'id',hidden:true"></th>
					<th field="shortName" width="100" sortable="true" data-options="formatter:formatter_cellShowMore"><spring:message code="customer.shortName" /></th>
					<th field="shortCode" width="100" sortable="true"><spring:message code="customer.shortCode" /></th>
					<th field="type" width="100" data-options="formatter:typeFormatter"><spring:message code="customer.type" /></th>
					<th field="level" width="100" data-options="formatter:customerLevelFormatter"><spring:message code="customer.level" /></th>
					<th field="contactName" width="100" data-options="formatter:contactNameFormatter"><spring:message code="customer.contact" /></th>
					<th field="mobilePhone" width="100" data-options="formatter:contactMobilePhoneFormatter"><spring:message code="customer.mobilePhone" /></th>
					<th field="fixedPhone" width="100" data-options="formatter:contactFixedPhoneFormatter"><spring:message code="customer.fixedPhone" /></th>
					<th field="netCommunityId" width="100" data-options="formatter:contactNetCommunityIdFormatter"><spring:message code="customer.netCommunityId" /></th>
					<th field="email" width="100" data-options="hidden:true"><spring:message code="customer.email" /></th>
					<th field="address" width="100" data-options="formatter:contactAddressFormatter"><spring:message code="customer.address" /></th>
					<th field="unpayMoney" width="60" sortable="true" data-options="styler:dealMoney_styler"><spring:message code="customer.payment.totalUnPaid" /></th>
					<th field="lastSaleDate" width="60" data-options="formatter:formatter_customerLatestSaleTimeInterval"><spring:message code="customer.lastSaleDate" /></th>
					<th field="lastPaidDate" width="60" data-options="formatter:formatter_customerLatestSaleTimeInterval"><spring:message code="customer.lastPaidDate" /></th>
					<th field="userSignedTo" width="60" sortable="true"><spring:message code="customer.signUserId" /></th>
					<th field="sharable" width="60" sortable="true" data-options="formatter:cellFormatter_customerSharable"><spring:message code="customer.isSharable" /></th>
				</tr>
			</thead>
		</table>
	</div>

	<div id="toolbar-customer" style="padding: 5px; height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton c7" iconCls="icon-add" plain="true"
			onclick="newModel('#dlg-customer', '<spring:message code="newCustomer" />', '#fm-customer', '<c:url value='/customer/addModel.html' />', newCustomerCallback)"
			title="<spring:message code="newCustomer" />"><spring:message code="newCustomer" /></a> 
		<a id="btn-editCustomer" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" disabled="true" 
			onclick="editModel('#dg-customer', '#dlg-customer', '<spring:message code="editCustomer" />', '#fm-customer', '<c:url value='/customer/updateModel.html' />', editCustomerCallback)"
			title="<spring:message code="selectOneRow" /><spring:message code="editCustomer" />"><spring:message code="editCustomer" /></a> 
		<a id="btn-deleteCustomer" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" disabled="true" 
			onclick="destroyMultipleModel('#dg-customer', '<spring:message code="customer" />', '<c:url value='/customer/deleteModels.html' />')"
			title="<spring:message code="checkOneOrMultiple" /><spring:message code="removeCustomer" />"><spring:message code="removeCustomer" /></a> 
		<c:if test="${sessionScope.login_user.admin}">
			<a id="" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" 
				onclick="showManageCustomerType()"><spring:message code="customerTypeManagement" /></a> 
		</c:if>
		<c:if test="${sessionScope.login_user.admin}">
			<a id="" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" 
				onclick="showManageCustomerLevel()"><spring:message code="customerLevel.mgmt" /></a> 
		</c:if>
		<span style="float: right; margin-right: 5px"> 
			<input class="easyui-searchbox" style="" 
				data-options="prompt:'<spring:message code="pleaseInputValue" />',searcher:doSearchCustomer" />
		</span>
	</div>
	
	<div id="dlg-customer" class="easyui-dialog" 
		style="width: 800px; height: 500px; padding:15px 5px" closed="true" 
		buttons="#dlg-buttons-customer" data-options="modal:true">
		<div id="" style="width:100%;height:100%">
			<form id="fm-customer" class="fm" style="margin:0 auto;padding:0px;width:100%;height:100%;" method="post" novalidate>
				<div class="fitem divHidden">
					<input id="custoId" name="id" value="0">
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label><spring:message code="customer.shortName" /><span class="iconSpan16 required" />:</label> 
						<input name="shortName" class="easyui-textbox" required="true" data-options="prompt:'<spring:message code="requiredFieldAndUnique" />'"
							validType="myRemote['<c:url value='/customer/checkExist.html' />', 'name', '#custoId']" />
					</div>
				</div>
	
				<div class="fitem css-dlg-formField">
					<div>
						<label><spring:message code="customer.shortCode" /><span class="iconSpan16 required" />:</label> 
						<input name="shortCode" class="easyui-textbox" required="true" data-options="prompt:'<spring:message code="requiredFieldAndUnique" />'"  
							validType="myRemote['<c:url value='/customer/checkCodeExist.html' />', 'shortCode', '#custoId']" />
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label><spring:message code="customer.initialUnpayMoney" />:</label> 
						<input id="unpayMoney" name="unpayMoney" class="easyui-numberbox" data-options="precision:2">
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label><spring:message code="customer.isSharable" />:</label> 
						<select id="sharable" name="sharable" class="easyui-combobox" editable="false" required="true" panelHeight="auto">
							<option value="true"><spring:message code="customer.option.enSharable" /></option>
							<option value="false"><spring:message code="customer.option.unSharable" /></option>
						</select>
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label><spring:message code="customer.type" />:</label> 
						<input id="type" name="typeBean.id" class="easyui-combobox" editable="false" panelHeight="auto" 
							data-options="valueField:'id',textField:'name',method:'post',url:'', onShowPanel:onShowCustomerType">
					</div>
				</div>
				<%-- <div class="fitem css-dlg-formField">
					<div>
						<label><spring:message code="customer.level" />:</label> 
						<input id="level" name="levelBean.id" class="easyui-combobox" editable="false" panelHeight="auto" 
							data-options="valueField:'id',textField:'name',method:'post',url:'', onShowPanel:onShowAllCustomerLevel">
					</div>
				</div> --%>
				<div class="fitem css-dlg-formField">
					<div>
						<label><spring:message code="customer.signUserId" />:</label> 
						<input id="signUserFullName" name="signUserFullName" class="easyui-textbox" readonly="readonly" />
					</div>
				</div>
				<div class="fitem divHidden">
					<input id="userSignedTo" name="userSignedTo" readonly="readonly" />
				</div>
				<div style="width:100%;float:left">
					<div style="margin:0 auto;width:670px;">
						<table id="dg-customerContact" class="easyui-datagrid" title="联系人" style="width:670px;height:150px" 
							data-options="
								rownumbers: true,
								singleSelect: true,
								toolbar: '#toolbar-customerContact',
								style:{marginBottom:0},
								showFooter: true,
								fitColumns:true,
								onBeforeEdit:customerContact_onBeforeEdit, onAfterEdit:customerContact_onAfterEdit, onCancelEdit:customerContact_onCancelEdit
							">
							<thead>
								<tr>
									<th data-options="field:'id',hidden:true"></th>
									<th data-options="field:'name',width:100,editor:'text'">联系人</th>
									<th data-options="field:'mobilePhone',width:100,editor:{type:'numberbox',options:{validType:'phoneCheck'}}">手机</th>
									<th data-options="field:'fixedPhone',width:100,editor:{type:'validatebox',options:{validType:'phoneCheck'}}">座机</th>
									<th data-options="field:'netCommunityId',width:100,editor:'text'">QQ/WangWang</th>
									<th data-options="field:'address',width:100,editor:'text'">送货地址</th>
									<th data-options="field:'prefered',width:50,align:'center',
										formatter: formatter_prefered,
										editor:{
											type:'checkbox',options:{on:'1',off:'0'}
										}">首要?</th>
									<th field="action" width="50" data-options="formatter:formatter_customerContactAction">操作</th>
								</tr>
							</thead>
						</table>
						
						<div id="toolbar-customerContact">
							<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="insertCustomerContact()" title="">新增联系人</a>
						</div>
					</div>
				</div>
				<div class="fitem divHidden">
					<input name="contactList" id="contactList" />
				</div>
				<div class="fitem css-dlg-formField" style="width:100%">
					<div style="width:670px">
						<label><spring:message code="customer.comment" />:</label>
						<input id="comment" name="comment" class="easyui-textbox" data-options="multiline:true" style="width:100%;height:50px" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="dlg-buttons-customer">
		<a id="save-customer" href="javascript:void(0)" class="easyui-linkbutton c7" iconCls="icon-ok" 
			onclick="saveModel('#dg-customer', '#dlg-customer', '#fm-customer', null, checkCustomerContact)">
			<spring:message code="save" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" 
			onclick="javascript:$('#dlg-customer').dialog('close')">
			<spring:message code="cancel" /></a>
	</div>
