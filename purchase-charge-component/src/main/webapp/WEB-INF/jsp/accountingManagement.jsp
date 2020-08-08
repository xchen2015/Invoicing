<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="com.pinfly.purchasecharge.core.util.PurchaseChargeConstants"%>
<%@ page import="com.pinfly.purchasecharge.component.bean.TimeSpan" %>
<%@ page import="com.pinfly.purchasecharge.core.model.AccountingModeCode"%>
	
	<div style="width:100%; height:430px;">
		<table id="dg-accounting" title="<spring:message code="expenseRecordManagement" />" class="easyui-datagrid"
			showFooter="true" fit="true" toolbar="#toolbar-accounting" rownumbers="true" singleSelect="true" checkOnSelect="true" selectOnCheck="false"
			data-options="fitColumns:true, pagination:true, sortName:'<%=PurchaseChargeConstants.EXPENSE_CREATE_DATE%>', sortOrder:'desc', 
				onClickRow:onClickAccountingRow, onLoadSuccess:onLoadAccountingSuccess, onCheckAll:onCheckAllAccounting, onUncheckAll:onUnCheckAccounting, 
				onCheck:onCheckAllAccounting, onUncheck:onUnCheckAccounting, onBeforeLoad:onBeforeLoadAccounting">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="id" width="" data-options="hidden:true"><spring:message code="id" /></th>
					<th field="mode" width="50" data-options="sortable:true, formatter:formatter_accountingMode2">支/收</th>
					<th field="<%=PurchaseChargeConstants.EXPENSE_TYPE%>" width="50" data-options="sortable:true, formatter:formatter_accountingType">类型</th>
					<th field="money" width="50" data-options="sortable:true, formatter:formatter_accountingMoney">金额</th>
					<th field="paymentAccount" width="50" data-options="formatter:formatter_paymentAccount">账号</th>
					<th field="customerId" hidden="true" data-options=""></th>
					<th field="customerName" width="50" data-options="">往来单位</th>
					<!-- <th field="orderId" width="50" data-options="">订单号</th> -->
					<th field="<%=PurchaseChargeConstants.EXPENSE_CREATE_DATE%>" width="50" data-options="sortable:true">创建时间</th>
					<%-- <th field="<%=PurchaseChargeConstants.EXPENSE_UPDATE_DATE%>" width="50" data-options="sortable:true">更新时间</th> --%>
					<th field="userCreated" width="50" data-options="sortable:true">创建人</th>
					<th field="comment" width="100" data-options="sortable:true">备注</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="toolbar-accounting" style="padding:5px;height:auto">
		<div>
			<span id="accounting-typecode" style="margin-right:50px">
				<label style="cursor:pointer;"><input id="accountingTypeCode_all" name="accountingTypeCode_mode" type="radio" value="0" checked="checked" onclick="onSelectedAccountingTypeCode()">全部</label>&nbsp;&nbsp;
				<label style="cursor:pointer;"><input id="accountingTypeCode_out" name="accountingTypeCode_mode" type="radio" value="<%=AccountingModeCode.OUT_LAY%>" onclick="onSelectedAccountingTypeCode()">支出</label>&nbsp;&nbsp;
				<label style="cursor:pointer;"><input id="accountingTypeCode_in" name="accountingTypeCode_mode" type="radio" value="<%=AccountingModeCode.IN_COME%>" onclick="onSelectedAccountingTypeCode()">收入</label>
			</span>
			<span>
				<a href="javascript:void(0)" class="easyui-linkbutton c7" iconCls="icon-add" plain="true" 
					onclick="newAccounting()" title="新增记账">新增记账</a> 
				<a id="btn-deleteAccounting" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" 
					onclick="destroyMultipleModel('#dg-accounting', '<spring:message code="expense.expense" />', '<c:url value='/accounting/deleteModels.html' />')" title="<spring:message code="checkOneOrMultiple" />删除记账">删除记账</a>
				<c:if test="${sessionScope.login_user.admin}">
					<a id="btn-manageAccountingType" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" 
						onclick="showAccountingTypeGrid()" title="">管理<spring:message code="navigation.expenseTypeManagement" /></a> 
				</c:if>
			</span>
		</div>	
		<div>
			<span id="advanceSearchSpan" style="">
            	<label>时间:</label> 
            	<input id="startDate" name="startDate" class="easyui-datebox" style="width:100px" 
				editable="false" title="开始时间" data-options="onSelect: onSelectStartDate">
            	- <input id="endDate" name="endDate" class="easyui-datebox" style="width:100px" 
				editable="false" title="结束时间" data-options="onSelect: onSelectStartDate">&nbsp;
				<select id="timeFrame" name="timeFrame" class="easyui-combobox" style="width:80px" panelHeight="auto" editable="false" data-options="onSelect: onSelectTimeFrame">
					<option value="CUSTOMIZE"><spring:message code="<%=TimeSpan.CUSTOMIZE.getMessageCode ()%>" /></option>
					<option value="TODAY"><spring:message code="<%=TimeSpan.TODAY.getMessageCode ()%>" /></option>
					<option value="RECENT_THREE_DAYS"><spring:message code="<%=TimeSpan.RECENT_THREE_DAYS.getMessageCode ()%>" /></option>
					<option value="RECENT_SEVEN_DAYS"><spring:message code="<%=TimeSpan.RECENT_SEVEN_DAYS.getMessageCode ()%>" /></option>
					<option value="RECENT_FIFTEEN_DAYS"><spring:message code="<%=TimeSpan.RECENT_FIFTEEN_DAYS.getMessageCode ()%>" /></option>
					<option value="RECENT_THIRTY_DAYS"><spring:message code="<%=TimeSpan.RECENT_THIRTY_DAYS.getMessageCode ()%>" /></option>
					<option value="CURRENT_MONTH" selected="selected"><spring:message code="<%=TimeSpan.CURRENT_MONTH.getMessageCode ()%>" /></option>
				</select>
            	&nbsp;
            	<label>类型:</label> 
           		<input id="typeId" name="typeId" class="easyui-combobox" style="width:100px" data-options="valueField:'id',
					textField:'name',
					panelHeight:'250',
					mode:'local',
					filter:comboboxFilter,
					onShowPanel:onClickExpenseTypeCombox" />
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="" onclick="onSearchAccountingByAdvance()">查询</a>
	        </span>
        </div>
	</div>

	<div id="dlg-accounting" class="easyui-dialog" style="width: 530px; height: 370px; padding: 15px 5px" closed="true"
		buttons="#dlg-buttons-accounting" data-options="modal:true">
        <div id="" style="width:100%;height:100%">
            <form id="fm-accounting" class="fm" method="post" style="margin:0 auto;padding:0px;width:100%;height:100%;" novalidate>
				<div class="fitem divHidden">
					<input id="expenseId" name="id">
				</div>
				<div class="fitem divHidden">
					<input id="userCreated" name="userCreated">
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label>收支类型<span class="iconSpan16 required" />:</label>
						<label style="width:50px;"><input id="accountingTypeCode_out" name="accountingMode" type="radio" value="<%=AccountingModeCode.OUT_LAY%>" style="width:20px;" checked="checked" onclick="accounting_onClickAccountingType()">支出</label>
						<label style="width:50px;"><input id="accountingTypeCode_in" name="accountingMode" type="radio" value="<%=AccountingModeCode.IN_COME%>" style="width:20px;" onclick="accounting_onClickAccountingType()">收入</label>
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label>费用类型<span class="iconSpan16 required" />:</label> 
						<input id="typeId" name="typeBean.id" class="easyui-combobox" editable="false" data-options="valueField:'id',
							textField:'name',
							panelHeight:'250',
							required: true,
							mode:'local',
							filter:comboboxFilter,
							onShowPanel:onClickExpenseTypeCombox" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label>金额<span class="iconSpan16 required" />:</label> 
						<input id="money" name="money" class="easyui-numberbox" data-options="precision:2" required="true" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label>账号<span class="iconSpan16 required" />:</label>
						<input id="paymentAccount" name="paymentAccountBean.id" class="easyui-combobox" panelHeight="auto" editable="false" required="true" 
							data-options="valueField:'id',textField:'name',method:'post', onShowPanel:onShowPaymentAccount, formatter:accountRemainMoneyFormatter" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label>日期:</label> 
						<input id="createDate" name="createDate" class="easyui-datetimebox" editable="false" required="true" 
							data-options="showSeconds:false" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label>往来单位:</label> 
						<input id="customerId" name="customerId" class="easyui-combobox" data-options="
							valueField:'id',
							textField:'shortName',
							panelHeight:'250',
							prompt:'输入名称查询',
							filter: comboboxFilter,
							groupField: 'group',
							groupFormatter: accounting_customerProviderGroupFormatter,
							onShowPanel: onShowAllCustomerProvider" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3" style="width:100%">
					<div style="width:410px">
						<label>备注:</label> 
						<input id="comment" name="comment" class="easyui-textbox" data-options="multiline:true" style="width:100%;height:80px" />
					</div>
				</div>
			</form>
        </div>
	</div>
	<div id="dlg-buttons-accounting">
		<a id="save-accounting-new" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" 
			onclick="saveNewAccounting()"><spring:message code="save" /></a> 
		<a id="save-accounting-edit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" 
			onclick="saveEditAccounting()"><spring:message code="save" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" 
			onclick="javascript:$('#dlg-accounting').dialog('close')"><spring:message code="cancel" /></a>
	</div>
