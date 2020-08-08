<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.pinfly.purchasecharge.core.util.PurchaseChargeConstants"%>
<%@ page import="com.pinfly.purchasecharge.component.bean.TimeSpan"%>

	<div style="width:100%; height:430px;">
		<table id="dg-customer" title="<spring:message code="customerFinanceManagement" />" class="easyui-datagrid" 
			url="<c:url value='/customer/getModelBySearchForm.html' />" toolbar="#toolbar-customer-payment" pagination="true" rownumbers="true" 
			singleSelect="true" checkOnSelect="true" selectOnCheck="false" showFooter="true" 
			data-options="fitColumns:true, sortName:'unpayMoney', sortOrder:'desc', fit:true, onDblClickRow:viewCustomerPaymentDetail">
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true"></th>
					<th field="shortName" width="100" sortable="true" data-options="formatter:formatter_cellShowMore"><spring:message code="customer.shortName" /></th>
					<th field="type" width="100" data-options="formatter:typeFormatter"><spring:message code="customer.type" /></th>
					<th field="level" width="100" data-options="formatter:customerLevelFormatter"><spring:message code="customer.level" /></th>
					<th field="contactName" width="100" data-options="formatter:contactNameFormatter"><spring:message code="customer.contact" /></th>
					<th field="mobilePhone" width="100" data-options="formatter:contactMobilePhoneFormatter"><spring:message code="customer.mobilePhone" /></th>
					<th field="unpayMoney" width="60" sortable="true" data-options="styler:dealMoney_styler"><spring:message code="customer.payment.totalUnPaid" /></th>
					<th field="lastSaleDate" width="60" data-options="formatter:formatter_customerLatestSaleTimeInterval"><spring:message code="customer.lastSaleDate" /></th>
					<th field="lastPaidDate" width="60" data-options="formatter:formatter_customerLatestSaleTimeInterval"><spring:message code="customer.lastPaidDate" /></th>
					<th field="userSignedTo" width="60" sortable="true"><spring:message code="customer.signUserId" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="toolbar-customer-payment" style="padding:5px;height:auto">
		<a id="" href="javascript:void(0)" class="easyui-linkbutton c7" iconCls="icon-add" plain="true" 
			onclick="newModel('#dlg-add-customer-payment', '收款', '#fm-customer-payment', '<c:url value='/customerPay/updateModel.html' />', addCustomerPaymentCallback)"
			title="<spring:message code="selectOneRow" />客户收款">新增客户收款</a> 
		<a id="" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" 
			onclick="viewCustomerPaymentDetail()" title="">查看客户收款</a> 
		<a id="" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" 
			onclick="viewCustomerPaymentDue()" title="">查看已到期的客户应收款</a>
		<span style="float: right; margin-right: 5px"> 
			<input class="easyui-searchbox" data-options="prompt:'<spring:message code="pleaseInputValue" />',searcher:doSearchCustomer" />
		</span>
	</div>
	
	<div id="dlg-view-customer-payment" class="easyui-dialog" style="width: 1000px; height: 500px; padding:5px;" 
		buttons="#dlg-buttons-view-customer-payment" data-options="modal:true, closed:true">
		<table id="dg-customerPayment" class="easyui-datagrid" title="" 
			rownumbers="true" fit="true" singleSelect="true" fitColumns="true" showFooter="true" toolbar="#toolbar-customer-payment-detail" 
			data-options="pagination:true, sortName:'<%=PurchaseChargeConstants.PAY_DATE%>', sortOrder:'desc', onBeforeLoad:onBeforeLoadCustomerPayment">
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true"></th>
					<th field="action" width="10" data-options="formatter:formatter_customerPaymentAction2"></th>
					<th field="<%=PurchaseChargeConstants.PAY_DATE%>" width="50"><spring:message code="customer.payment.date" /></th>
					<th field="customerName" width="50" data-options="formatter:formatter_payment_customer"><spring:message code="customer" /></th>
					<th field="receiptId" width="50"><spring:message code="customer.payment.receiptId" /></th>
					<th field="typeCode" width="30" data-options="formatter:formatter_payment_typeCode"><spring:message code="customer.payment.type" /></th>
					<th field="userCreated" width="30"><spring:message code="customer.payment.operator" /></th>
					<th field="addUnPaid" width="30" data-options="styler:dealMoney_styler"><spring:message code="customer.payment.addUnPaid" /></th>
					<th field="<%=PurchaseChargeConstants.PAID%>" width="30" data-options="styler:dealMoney_styler"><spring:message code="customer.payment.addPaid" /></th>
					<th field="remainUnPaid" width="30" data-options="styler:dealMoney_styler"><spring:message code="customer.payment.totalUnPaid" /></th>
					<th field="comment" width="60"><spring:message code="customer.comment" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="toolbar-customer-payment-detail" style="padding:5px;height:auto">
		<span id="advanceSearchSpan" style="display: inline-block;">
			<form id="" action="">
				<label>时间:</label>
				<input id="startDate" name="startDate" class="easyui-datebox" style="width:100px" 
				editable="false" title="开始时间" data-options="onSelect: onCustomerSelectStartDate">
				- <input id="endDate" name="endDate" class="easyui-datebox" style="width:100px" 
				editable="false" title="结束时间" data-options="onSelect: onCustomerSelectStartDate">&nbsp;
				<select id="timeFrame" name="timeFrame" class="easyui-combobox" style="width:80px" panelHeight="auto" editable="false" data-options="onSelect: onCustomerSelectTimeFrame">
					<option value="CUSTOMIZE"><spring:message code="<%=TimeSpan.CUSTOMIZE.getMessageCode ()%>" /></option>
					<option value="TODAY"><spring:message code="<%=TimeSpan.TODAY.getMessageCode ()%>" /></option>
					<option value="RECENT_THREE_DAYS"><spring:message code="<%=TimeSpan.RECENT_THREE_DAYS.getMessageCode ()%>" /></option>
					<option value="RECENT_SEVEN_DAYS"><spring:message code="<%=TimeSpan.RECENT_SEVEN_DAYS.getMessageCode ()%>" /></option>
					<option value="RECENT_FIFTEEN_DAYS"><spring:message code="<%=TimeSpan.RECENT_FIFTEEN_DAYS.getMessageCode ()%>" /></option>
					<option value="RECENT_THIRTY_DAYS" selected="selected"><spring:message code="<%=TimeSpan.RECENT_THIRTY_DAYS.getMessageCode ()%>" /></option>
					<option value="CURRENT_MONTH"><spring:message code="<%=TimeSpan.CURRENT_MONTH.getMessageCode ()%>" /></option>
				</select>
				&nbsp;
            	<label>类型:</label>
				<select id="paymentTypeCode" name="paymentTypeCode" class="easyui-combobox" panelHeight="auto" style="width:100px">
					<option value="">所有</option>
					<option value="OUT_PAID_MONEY"><spring:message code="customer.payment.addPaid" /></option>
					<option value="OUT_ORDER"><spring:message code="customer.payment.addUnPaid" /></option>
					<option value="OUT_ORDER_RETURN"><spring:message code="customer.payment.returnUnPaid" /></option>
					<option value="INITIAL_BALANCE"><spring:message code="customer.initialUnpayMoney" /></option>
				</select>
				&nbsp;
				<label>客户:</label>
				<input id="customerId" name="customerId" class="easyui-combobox" style="width:150px" data-options="
					valueField:'id',
					textField:'shortName',
					panelHeight:'250',
					filter: comboboxFilter,
					formatter:customerUnpayMoneyFormatter,
					onShowPanel: onShowAllCustomer" />
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="查询收款单" onclick="onSearchCustomerPaymentByAdvance()">查询</a>
			</form>
        </span>
	</div>
	<div id="dlg-buttons-view-customer-payment">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" 
			onclick="javascript:$('#dlg-view-customer-payment').dialog('close')"><spring:message code="close" /></a>
	</div>

	<!-- 增加收款弹出框 -->
	<div id="dlg-add-customer-payment" class="easyui-dialog" style="width: 800px; height: 500px; padding:15px;" 
		buttons="#dlg-buttons-add-customer-payment" data-options="modal:true, closed:true">
		<div style="width:100%;height:100%">
			<form id="fm-customer-payment" class="fm" style="margin:0 auto;padding:0px;width:100%;height:100%;" method="post" novalidate>
				<div class="fitem css-dlg-formField">
					<div>
						<label><spring:message code="order.customer" /><span class="iconSpan16 required" />:</label> 
						<input id="customerId" name="customerBean.id" class="easyui-combobox" data-options="
							valueField:'id',
							textField:'shortName',
							panelHeight:'250',
							required:true,
							prompt:'输入名称查询',
							filter: comboboxFilter,
							onSelect: onSelectCustomer,
							formatter:customerUnpayMoneyFormatter,
							onShowPanel: onShowAllCustomer"/>
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label>单据日期:</label> 
						<input id="payDate" name="payDate" class="easyui-datebox" editable="false" />
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label>单据编号:</label> 
						<input id="bid" name="bid" class="easyui-textbox" disabled="true" />
					</div>
				</div>
				
				<div class="divHidden">
					<input id="paymentRecordList" name="paymentRecordList" />
				</div>
				
				<div style="width:100%;float:left">
					<div style="margin:0 auto;width:660px;">
						<table id="dg-customer-payment-new" class="easyui-datagrid" title="款项" style="width:660px;height:250px;"
								data-options="
									rownumbers: true,
									singleSelect: true,
									toolbar: '#tb-customer-payment-new',
									style:{marginBottom:0},
									showFooter: true,
									fitColumns:true,
									onBeforeEdit:customerPayment_onBeforeEdit, 
									onAfterEdit:customerPayment_onAfterEdit, 
									onCancelEdit:customerPayment_onCancelEdit
								">
							<thead>
								<tr>
									<th data-options="field:'id',hidden:true"></th>
									<th data-options="field:'accountId',hidden:true"></th>
									<th data-options="field:'paymentAccount',width:100,
											formatter:function(value,row){
												if(row.paymentAccountBean) 
												{
													return row.paymentAccountBean.accountId;
												}
												return row.accountId;
											},
											editor:{
												type:'combobox',
												options:{
													valueField:'id',
													textField:'name',
													url:'<c:url value='/paymentAccount/getAllModel.html' />',
													formatter:accountRemainMoneyFormatter,
													required:true,
													method:'post',
													editable:false,
													panelHeight:'auto'
												}
											}
										">结算账号</th>
									<th data-options="field:'paid',width:60,required:true,editor:{type:'numberbox',options:{required:true,precision:2}}">收款金额</th>
									<th data-options="field:'paymentWayName',hidden:true"></th>
									<th data-options="field:'paymentWay',width:60,
											formatter:function(value,row){
												if(row.paymentWayBean) 
												{
													return row.paymentWayBean.name;
												}
												return row.paymentWayName;
											},
											editor:{
												type:'combobox',
												options:{
													valueField:'id',
													textField:'name',
													url:'<c:url value='/paymentWay/getAllModel.html' />',
													method:'post',
													editable:false,
													panelHeight:'auto'
												}
											}
										">结算方式</th>
									<th data-options="field:'note',width:100,editor:'text'"><spring:message code="order.note" /></th>
									<th field="action" width="50" data-options="formatter:formatter_customerPaymentAction">操作</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			 
				<div id="tb-customer-payment-new" style="height:auto">
					<a id="btn-add-payment-record" href="javascript:void(0)" class="easyui-linkbutton" disabled="true" data-options="iconCls:'icon-add',plain:true" onclick="insertCustomerPayment()">新增款项</a>
				</div>
				
				<div class="fitem css-dlg-formField" style="width:100%;">
					<div style="width:660px">
						<label>备注:</label>
						<input id="comment" name="comment" class="easyui-textbox" style="width:100%;" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="dlg-buttons-add-customer-payment">
		<a id="save-customer-payment" href="javascript:void(0)" class="easyui-linkbutton c7" iconCls="icon-ok" 
			onclick="saveModel('#dg-customerPayment', '#dlg-add-customer-payment', '#fm-customer-payment', function(){$('#dg-customer').datagrid('reload')}, checkCustomerPaymentRecord)"><spring:message code="save" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" 
			onclick="javascript:$('#dlg-add-customer-payment').dialog('close')"><spring:message code="cancel" /></a>
	</div>
	<!-- 增加收款弹出框 -->
	
	<!-- 查看到期客户应收款 -->
	<div id="dlg-view-customer-payment-dued" class="easyui-dialog" style="width: 800px; height: 500px; padding:5px;" 
		buttons="#dlg-buttons-view-customer-payment-dued" data-options="modal:true, closed:true">
		<table id="dg-customerPaymentDued" class="easyui-datagrid" showFooter="true" rownumbers="true" fit="true" 
			singleSelect="true" fitColumns="true" data-options="">
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true"></th>
					<th field="action" width="7" data-options="formatter:formatter_customerPaymentAction2"></th>
					<th field="customerName" width="50" data-options="formatter:formatter_payment_customer"><spring:message code="customer" /></th>
					<th field="<%=PurchaseChargeConstants.PAY_DATE%>" width="50" data-options="formatter:formatter_customerPaymentDuedInterval"><spring:message code="customer.payment.duedInterval" /></th>
					<th field="<%=PurchaseChargeConstants.PAID%>" width="30" data-options="styler:dealMoney_styler"><spring:message code="customer.payment.allUnPay" /></th>
					<th field="addUnPaid" width="30" data-options="styler:dealMoney_styler"><spring:message code="customer.payment.duedUnPay" /></th>
					<th field="remainUnPaid" width="30" data-options="styler:dealMoney_styler"><spring:message code="customer.payment.remainUnPay" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="dlg-buttons-view-customer-payment-dued">
		<span id="autoShowDueCustomerPayment" style="float:left;"><label><input type="checkbox" name="customerPayment_autoShowDuePayment" onclick="customerPayment_autoShowDuePayment(this)" />本次会话不再自动显示此窗口</label></span>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" 
			onclick="javascript:$('#dlg-view-customer-payment-dued').dialog('close')"><spring:message code="close" /></a>
	</div>
	<!-- 查看到期客户应收款 -->

