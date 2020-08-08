	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	<%@ page import="com.pinfly.purchasecharge.component.bean.TimeSpan"%>
	
	<jsp:include page="paymentWayManagement.jsp" />
	<div style="width:100%; height:430px;">
		<table id="dg-paymentAccount" class="easyui-datagrid" toolbar="#toolbar-paymentAccountFinance" title="财务账号管理" 
			 fit="true" pagination="false" rownumbers="true" singleSelect="true" checkOnSelect="true" selectOnCheck="false" showFooter="true" 
			 data-options="fitColumns:true, onBeforeEdit:paymentAccount_onBeforeEdit, 
				onAfterEdit:paymentAccount_onAfterEdit, onCancelEdit:paymentAccount_onCancelEdit, onBeforeLoad:onBeforeLoadPaymentAccount">
			<thead>
				<tr>
					<th field="id" hidden="true"><spring:message code="id" /></th>
					<th field="name" width="100" data-options="editor:{type:'validatebox', options:{required:true, validType:'checkPaymentAccountNameExist[]'}}"><spring:message code="name" /></th>
					<th field="accountId" width="100" data-options="editor:{type:'validatebox', options:{required:true, validType:'checkPaymentAccountIdExist[]'}}">账号</th>
					<th field="accountMode" width="100" sortable="true" 
						data-options="
							formatter:formatter_accountMode,
							editor:{
	                           type:'combobox',
	                           options:{
	                               valueField:'value',
	                               textField:'label',
	                               editable:false,
								   panelHeight: 'auto',
	                               data:[{label:'现金',value:'CASH'},{label:'储蓄存款',value:'DEPOSIT'}],
	                               required:true
	                           }
	                       }">类型</th>
					<th field="remainMoney" width="100" data-options="editor:{type:'numberbox',options:{precision:2}}">余额</th>
					<th field="action" width="50" data-options="formatter:formatter_paymentAccountAction"><spring:message code="action" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="toolbar-paymentAccountFinance" style="padding:5px;height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" 
			iconCls="icon-add" plain="true" onclick="insertPaymentAccount()" title="新增账号">新增账号</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" 
			onclick="addInternalAccountTransfer()" title="">新增内部转账</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" 
			onclick="showAddValueDialog()" title="">充值</a> 
		<c:if test="${sessionScope.login_user.admin}">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" 
				onclick="showPaymentWayManage()" title=""><spring:message code="payment.paymentWayManagement" /></a> 
		</c:if>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-reload" plain="true" onclick="reloadPaymentAccount()" title="<spring:message code="refresh" />"><spring:message code="refresh" /></a>
	</div>
	
	<!-- 查看付款记录弹出框 -->
	<div id="dlg-view-paymentAccount" class="easyui-dialog" 
		style="width: 800px; height: 500px; padding: 5px;" closed="true"
		buttons="#dlg-buttons-view-paymentAccount" data-options="modal:true">
		<table id="dg-payment-detail" class="easyui-datagrid" rownumbers="true" fit="true" 
			singleSelect="true" fitColumns="true" showFooter="true" toolbar="#toolbar-paymentDetail" data-options="pagination:true">
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true"></th>
					<th field="createDate" width="70">日期</th>
					<th field="targetAccount" width="50" data-options="formatter:formatter_paymentAccount_targetAccount">目标账号</th>
					<th field="source" width="50" data-options="">来源</th>
					<th field="transferTypeCode" width="40" data-options="formatter:formatter_paymentType">类型</th>
					<th field="inMoney" width="30" data-options="">支入金额</th>
					<th field="outMoney" width="30" data-options="">支出金额</th>
					<th field="remainMoney" width="30" data-options="">余额</th>
					<th field="userCreated" width="30">操作者</th>
					<th field="comment" width="70">备注</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="toolbar-paymentDetail" style="padding:5px;height:auto">
		<span id="advanceSearchSpan" style="width: 100%; display: inline-block;">
			<form id="" action="">
				<input id="paymentId" name="paymentId" type="hidden" />
				
				<label>时间:</label>
            	<input id="startDate" name="startDate" class="easyui-datebox" style="width:100px" 
				editable="false" title="开始时间" data-options="onSelect: onPaymentAccountSelectStartDate" />
				- <input id="endDate" name="endDate" class="easyui-datebox" style="width:100px" 
				editable="false" title="结束时间" data-options="onSelect: onPaymentAccountSelectStartDate" />&nbsp;
				<select id="timeFrame" name="timeFrame" class="easyui-combobox" style="width:80px" panelHeight="auto" editable="false" data-options="onSelect: onPaymentAccountSelectTimeFrame">
					<option value="CUSTOMIZE"><spring:message code="<%=TimeSpan.CUSTOMIZE.getMessageCode ()%>" /></option>
					<option value="TODAY"><spring:message code="<%=TimeSpan.TODAY.getMessageCode ()%>" /></option>
					<option value="RECENT_THREE_DAYS"><spring:message code="<%=TimeSpan.RECENT_THREE_DAYS.getMessageCode ()%>" /></option>
					<option value="RECENT_SEVEN_DAYS" selected="selected"><spring:message code="<%=TimeSpan.RECENT_SEVEN_DAYS.getMessageCode ()%>" /></option>
					<option value="RECENT_FIFTEEN_DAYS"><spring:message code="<%=TimeSpan.RECENT_FIFTEEN_DAYS.getMessageCode ()%>" /></option>
					<option value="RECENT_THIRTY_DAYS"><spring:message code="<%=TimeSpan.RECENT_THIRTY_DAYS.getMessageCode ()%>" /></option>
					<option value="CURRENT_MONTH"><spring:message code="<%=TimeSpan.CURRENT_MONTH.getMessageCode ()%>" /></option>
				</select>
				&nbsp;
				<label>类型:</label>
				<select id="transferTypeCode" name="transferTypeCode" class="easyui-combobox" panelHeight="auto" style="width:100px">
					<option value="">所有</option>
					<option value="CUSTOMER_TRANSFER"><spring:message code="payment.transferType.customer" /></option>
					<option value="PROVIDER_TRANSFER"><spring:message code="payment.transferType.provider" /></option>
					<option value="ACCOUNTING_OUT"><spring:message code="payment.transferType.accountingOut" /></option>
					<option value="ACCOUNTING_IN"><spring:message code="payment.transferType.accountingIn" /></option>
					<option value="RECHARGE"><spring:message code="payment.transferType.recharge" /></option>
					<option value="INTERNAL_TRANSFER"><spring:message code="payment.transferType.internal" /></option>
					<option value="INITIAL_REMAIN"><spring:message code="payment.transferType.remain" /></option>
				</select>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="查询支付明细" onclick="onSearchPaymentAccountByAdvance()">查询</a>
			</form>
        </span>
	</div>
	<div id="dlg-buttons-view-paymentAccount">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg-view-paymentAccount').dialog('close')"><spring:message code="close" /></a>
	</div>
	<!-- 查看付款记录弹出框 -->
	
	<!-- 转账 -->
	<div id="dlg-add-paymentAccountTransfer" class="easyui-dialog" 
		style="width: 340px; height: 370px; padding: 15px 5px" closed="true"
		buttons="#dlg-buttons-add-paymentAccountTransfer" data-options="modal:true">
		<div style="width:100%;height:100%">
			<form id="fm-payment-transfer" class="fm" style="margin:0 auto;padding:0px;width:100%;height:100%;" method="post" novalidate>
				<div class="fitem divHidden">
					<input id="paymentTransferId" name="id" value="0" />
				</div>
				<div class="fitem css-dlg-formField2">
					<div>
						<label>源账号:</label>
						<input id="fromAccount" name="targetAccountBean.id" class="easyui-combobox" panelHeight="auto" editable="false" required="true" 
							data-options="valueField:'id',textField:'name',method:'post', onShowPanel:onShowPaymentAccount, formatter:accountRemainMoneyFormatter" />
					</div>
				</div>
				<div class="fitem css-dlg-formField2">
					<div>
						<label>目标账号:</label>
						<input id="toAccount" name="source" class="easyui-combobox" panelHeight="auto" editable="false" required="true" 
							data-options="valueField:'id',textField:'name',method:'post', onShowPanel:onShowPaymentAccount, formatter:accountRemainMoneyFormatter" />
					</div>
				</div>
				<div class="fitem css-dlg-formField2">
					<div>
						<label>金额:</label>
						<input id="outMoney" name="outMoney" class="easyui-numberbox" required="true" />
					</div>
				</div>
				<div class="fitem css-dlg-formField2">
					<div>
						<label>备注:</label>
						<input id="comment" name="comment" class="easyui-textbox" data-options="multiline:true" style="height:50px" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="dlg-buttons-add-paymentAccountTransfer">
		<a href="javascript:void(0)" id="" class="easyui-linkbutton" iconCls="icon-save" 
			onclick="saveModel('#dg-paymentAccount', '#dlg-add-paymentAccountTransfer', '#fm-payment-transfer')"><spring:message code="save" /></a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg-add-paymentAccountTransfer').dialog('close')">关闭</a>
	</div>
	<!-- 转账 -->
	
	<!-- 充值 -->
	<div id="dlg-addValueTransfer" class="easyui-dialog" 
		style="width: 340px; height: 370px; padding: 15px 5px" closed="true"
		buttons="#dlg-buttons-addValueTransfer" data-options="modal:true">
		<div style="width:100%;height:100%">
			<form id="fm-addValue-transfer" class="fm" style="margin:0 auto;padding:0px;width:100%;height:100%;" method="post" novalidate>
				<div class="fitem divHidden">
					<input id="paymentTransferId" name="id" value="0" />
				</div>
				<div class="fitem css-dlg-formField2">
					<div>
						<label>目标账号:</label>
						<input id="toAccount" name="targetAccountBean.id" class="easyui-combobox" panelHeight="auto" editable="false" required="true" 
							data-options="valueField:'id',textField:'name',method:'post', onShowPanel:onShowPaymentAccount, formatter:accountRemainMoneyFormatter" />
					</div>
				</div>
				<div class="fitem css-dlg-formField2">
					<div>
						<label>金额:</label>
						<input id="inMoney" name="inMoney" class="easyui-numberbox" required="true" />
					</div>
				</div>
				<div class="fitem css-dlg-formField2">
					<div>
						<label>备注:</label>
						<input id="comment" name="comment" class="easyui-textbox" data-options="multiline:true" style="height:50px" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="dlg-buttons-addValueTransfer">
		<a href="javascript:void(0)" id="" class="easyui-linkbutton" iconCls="icon-save" 
			onclick="saveModel('#dg-paymentAccount', '#dlg-addValueTransfer', '#fm-addValue-transfer')"><spring:message code="save" /></a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg-addValueTransfer').dialog('close')">关闭</a>
	</div>
	<!-- 充值 -->
