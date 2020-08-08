<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.pinfly.purchasecharge.core.util.PurchaseChargeConstants"%>
<%@ page import="com.pinfly.purchasecharge.component.bean.TimeSpan"%>

	<div style="width:100%; height:430px;">
		<table id="dg-provider" title="<spring:message code="providerFinanceManagement" />" class="easyui-datagrid" 
			url="<c:url value='/provider/getModelBySearchForm.html' />" toolbar="#toolbar-provider-payment" pagination="true" 
			rownumbers="true" singleSelect="true" checkOnSelect="true" selectOnCheck="false" showFooter="true" 
			data-options="fitColumns:true, sortName:'unpayMoney', sortOrder:'desc', fit:true, onDblClickRow:viewProviderPaymentDetail">
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true"></th>
					<th field="shortName" width="100" sortable="true" data-options="formatter:formatter_cellShowMore"><spring:message code="customer.shortName" /></th>
					<th field="type" width="100" data-options="formatter:typeFormatter"><spring:message code="customer.type" /></th>
					<th field="contactName" width="100" data-options="formatter:contactNameFormatter"><spring:message code="customer.contact" /></th>
					<th field="mobilePhone" width="100" data-options="formatter:contactMobilePhoneFormatter"><spring:message code="customer.mobilePhone" /></th>
					<th field="unpayMoney" width="60" sortable="true" data-options="styler:dealMoney_styler"><spring:message code="provider.payment.totalUnPaid" /></th>
					<th field="lastSaleDate" width="60" data-options="formatter:formatter_customerLatestSaleTimeInterval"><spring:message code="customer.lastSaleDate" /></th>
					<th field="lastPaidDate" width="60" data-options="formatter:formatter_customerLatestSaleTimeInterval"><spring:message code="customer.lastPaidDate" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="toolbar-provider-payment" style="padding:5px; height:auto">
		<a id="" href="javascript:void(0)" class="easyui-linkbutton c7" iconCls="icon-add" plain="true"  
			onclick="newModel('#dlg-add-provider-payment', '付款', '#fm-provider-payment', '<c:url value='/providerPay/updateModel.html' />', addProviderPaymentCallback)"
			title="<spring:message code="selectOneRow" />供应商付款">新增供应商付款</a> 
		<a id="" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" 
			onclick="viewProviderPaymentDetail()" title="">查看供应商付款</a>
		<span style="float: right; margin-right: 5px"> 
			<input class="easyui-searchbox" data-options="prompt:'<spring:message code="pleaseInputValue" />',searcher:doSearchProvider" />
		</span>
	</div>
	
	<div id="dlg-view-provider-payment" class="easyui-dialog" style="width: 1000px; height: 500px; padding:5px;"  
		buttons="#dlg-buttons-view-provider-payment" data-options="modal:true, closed:true">
		<table id="dg-provider-payment" class="easyui-datagrid" title="" toolbar="#toolbar-provider-payment-detail"
			style="" url="" rownumbers="true" fit="true" fitColumns="true" singleSelect="true" showFooter="true" 
			data-options="pagination:true, sortName:'<%=PurchaseChargeConstants.PAY_DATE%>', sortOrder:'desc', onBeforeLoad:onBeforeLoadProviderPayment">
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true"></th>
					<th field="action" width="10" data-options="formatter:formatter_providerPaymentAction2"></th>
					<th field="<%=PurchaseChargeConstants.PAY_DATE%>" width="50"><spring:message code="customer.payment.date" /></th>
					<th field="customerName" width="50" data-options="formatter:formatter_payment_customer"><spring:message code="provider" /></th>
					<th field="receiptId" width="50"><spring:message code="customer.payment.receiptId" /></th>
					<th field="typeCode" width="30" data-options="formatter:formatter_payment_typeCode"><spring:message code="customer.payment.type" /></th>
					<th field="userCreated" width="30"><spring:message code="customer.payment.operator" /></th>
					<th field="addUnPaid" width="30" data-options="styler:dealMoney_styler"><spring:message code="provider.payment.addUnPaid" /></th>
					<th field="<%=PurchaseChargeConstants.PAID%>" width="30" data-options="styler:dealMoney_styler"><spring:message code="provider.payment.addPaid" /></th>
					<th field="remainUnPaid" width="30" data-options="styler:dealMoney_styler"><spring:message code="provider.payment.totalUnPaid" /></th>
					<th field="comment" width="60"><spring:message code="customer.comment" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="toolbar-provider-payment-detail" style="padding:5px; height:auto">
		<span id="advanceSearchSpan" style="display: inline-block;">
			<form id="" action="">
            	<label>时间:</label>
            	<input id="startDate" name="startDate" class="easyui-datebox" style="width:100px" 
				editable="false" title="开始时间" data-options="onSelect: onProviderSelectStartDate">
				- <input id="endDate" name="endDate" class="easyui-datebox" style="width:100px" 
				editable="false" title="结束时间" data-options="onSelect: onProviderSelectStartDate">&nbsp;
				<select id="timeFrame" name="timeFrame" class="easyui-combobox" style="width:80px" panelHeight="auto" editable="false" data-options="onSelect: onProviderSelectTimeFrame">
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
					<option value="IN_PAID_MONEY"><spring:message code="provider.payment.addPaid" /></option>
					<option value="IN_ORDER"><spring:message code="provider.payment.addUnPaid" /></option>
					<option value="IN_ORDER_RETURN"><spring:message code="provider.payment.returnUnPaid" /></option>
					<option value="INITIAL_BALANCE"><spring:message code="provider.initialUnpayMoney" /></option>
				</select>
				&nbsp;
				<label>供应商:</label>
				<input id="customerId" name="customerId" class="easyui-combobox" style="width:150px" data-options="
					valueField:'id',
					textField:'shortName',
					panelHeight:'250',
					filter: comboboxFilter,
					formatter:customerUnpayMoneyFormatter,
					onShowPanel: onShowAllProvider" />
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="查询付款单" onclick="onSearchProviderPaymentByAdvance()">查询</a>
			</form>
        </span>
	</div>
	<div id="dlg-buttons-view-provider-payment">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg-view-provider-payment').dialog('close')"><spring:message code="close" /></a>
	</div>

	<!-- 增加付款弹出框 -->
	<div id="dlg-add-provider-payment" class="easyui-dialog pc-dialog" style="width: 800px; height: 500px; padding:15px;"  
		buttons="#dlg-buttons-add-provider-payment" data-options="modal:true, closed:true">
		<div style="width:100%; height:100%;">
			<form id="fm-provider-payment" class="fm" style="margin:0 auto;padding:0px;width:100%;height:100%;" method="post" novalidate>
				<div class="fitem css-dlg-formField">
					<div>
						<label>供应商<span class="iconSpan16 required" />:</label> 
						<input id="customerId" name="customerBean.id" class="easyui-combobox" data-options="
							valueField:'id',
							textField:'shortName',
							panelHeight:'250',
							required:true,
							prompt:'输入名称查询',
							filter: comboboxFilter,
							onSelect: onSelectProvider,
							formatter:customerUnpayMoneyFormatter,
							onShowPanel: onShowAllProvider" />
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
				
				<div class="fitem divHidden">
					<input id="paymentRecordList" name="paymentRecordList" />
				</div>
				
				<div style="width:100%;float:left">
					<div style="margin:0 auto;width:660px;">
						<table id="dg-provider-payment-new" class="easyui-datagrid" title="款项" style="width:660px;height:250px" 
							data-options="
								rownumbers: true,
								singleSelect: true,
								toolbar: '#tb-provider-payment-new',
								style:{marginBottom:0},
								showFooter: true,
								fitColumns:true,
								onBeforeEdit:providerPayment_onBeforeEdit, 
								onAfterEdit:providerPayment_onAfterEdit, 
								onCancelEdit:providerPayment_onCancelEdit
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
									<th data-options="field:'paid',width:60,required:true,editor:{type:'numberbox',options:{required:true,precision:2}}">付款金额</th>
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
									<th field="action" width="50" data-options="formatter:formatter_providerPaymentAction">操作</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
				 
			    <div id="tb-provider-payment-new" style="height:auto">
			        <a id="btn-add-payment-record" href="javascript:void(0)" class="easyui-linkbutton" disabled="true" data-options="iconCls:'icon-add',plain:true" onclick="insertProviderPayment()">新增款项</a>
			    </div>
				
				<div class="fitem css-dlg-formField" style="width:100%">
					<div style="width:660px">
						<label>备注:</label>
						<input id="comment" name="comment" class="easyui-textbox" style="width:100%;" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="dlg-buttons-add-provider-payment">
		<a id="save-provider-payment" href="javascript:void(0)" class="easyui-linkbutton c7" iconCls="icon-ok"
			onclick="saveModel('#dg-provider-payment', '#dlg-add-provider-payment', '#fm-provider-payment', function(){$('#dg-provider').datagrid('reload')}, checkProviderPaymentRecord)">
			<spring:message code="save" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg-add-provider-payment').dialog('close')"><spring:message code="cancel" /></a>
	</div>
	<!-- 增加付款弹出框 -->

