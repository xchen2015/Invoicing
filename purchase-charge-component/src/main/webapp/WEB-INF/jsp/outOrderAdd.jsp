<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

	<!-- new order dialog -->
	<div id="dlg-outorder-new" class="easyui-dialog"
		style="width: 800px; height: 600px; padding:15px;" closed="true"
		buttons="#dlg-buttons-outorder-new" data-options="modal:true">
		<div style="width:100%;height:100%">
			<form id="fm-outorder" class="fm" style="margin:0 auto;padding:0px;width:100%;height:100%;" method="post" novalidate>
				<div class="divHidden">
					<input id="outorder-typeCode" name="typeCode">
				</div>
				<!-- <div class="divHidden">
					<input id="customerId" name="customerBean.id" class="easyui-textbox" />
				</div> -->
				<div class="fitem css-dlg-formField">
					<div>
						<label><spring:message code="order.customer" /><span class="iconSpan16 required" />:</label> 
						<input id="customerId" name="customerBean.id" class="easyui-combobox" data-options="
							valueField:'id',
							textField:'shortName',
							panelHeight:'250',
							required:true,
							prompt:'输入名称查询',
							onSelect: onSelectCustomer,
							onChange:onChangeCustomer,
							filter: comboboxFilter,
							onShowPanel: onShowAllCustomer"/>
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label>开单日期:</label> 
						<input id="createTime" name="createTime" class="easyui-datebox" editable="false" />
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label>订单号:</label> 
						<input id="bid" name="bid" class="easyui-textbox" disabled="true" />
					</div>
				</div>
				
				<div class="divHidden">
					<input id="outorderItemList" name="orderItemList" />
				</div>
				
				<div style="width:100%;float:left">
					<div style="margin:0 auto;width:660px;">
						<table id="dg-outorderItem-new" class="easyui-datagrid" title="<spring:message code="order.items" />" style="width:660px;height:250px;"
								data-options="
									rownumbers: true,
									singleSelect: true,
									toolbar: '#tb-outorderList',
									onClickRow: onClickRow,
									style:{marginBottom:0},
									showFooter: true,
									fitColumns:true">
							<thead>
								<tr>
									<th data-options="field:'id',hidden:true"></th>
									<th data-options="field:'goodsId',width:100,
										formatter:function(value,row){
											return row.name;
										},
										editor:{
											type:'combobox',
											options:{
												valueField:'id',
												textField:'name',
												panelHeight:200,
												required:true,
												onSelect: onSelectGoods,
												formatter: goodsStorageFormatter,
												mode:'local', 
												filter: comboboxFilter,
												url:'<c:url value='/goods/getAllModel.html' />'
											}
										}"><spring:message code="order.product" /></th>
									<th data-options="field:'depository',width:40,
										formatter:formatter_order_goods,
										editor:{
											type:'combobox',
											options:{
												valueField:'id',textField:'name',method:'post',editable:false, panelHeight:'auto', 
												url:'<c:url value='/goodsDepository/getAllModel.html' />', required:true
											}
										}">
										仓库</th>
									<th data-options="field:'unitPrice',width:30,editor:{type:'numberbox',options:{required:true, precision:2, onChange: onChangeUnitPrice}}"><spring:message code="order.unitPrice" /></th>
									<th data-options="field:'amount',width:30,align:'right',editor:{type:'numberbox', options:{required:true, onChange: onChangeAmount}}"><spring:message code="order.amount" /></th>
									<th data-options="field:'unit',width:30,editor:'text'"><spring:message code="order.unit" /></th>
									<th data-options="field:'sum',width:40,align:'right',editor:{type:'numberbox',options:{precision:2}}"><spring:message code="order.sum" /></th>
									<th data-options="field:'depositoryName',hidden:true"></th>
									<th data-options="field:'note',width:80,editor:'text'"><spring:message code="order.note" /></th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			 
				<div id="tb-outorderList" style="height:auto">
					<a id="btn-add-orderItem" href="javascript:void(0)" class="easyui-linkbutton" disabled="true" data-options="iconCls:'icon-add',plain:true" onclick="append()"><spring:message code="order.append" /></a>
<%-- 				        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()"><spring:message code="order.accept" /></a> --%>
					<a id="btn-delete-orderItem" href="javascript:void(0)" class="easyui-linkbutton" disabled="true" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()"><spring:message code="order.remove" /></a>
					<span id="validateOutorderMsg"></span>
				</div>
				
				<div class="fitem css-dlg-formField" style="width:100%;">
					<div style="width:660px">
						<label><spring:message code="order.note" />:</label>
						<input id="comment" name="comment" class="easyui-textbox" style="width:100%;" />
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label>折扣率:</label> 
						<input id="discount" name="discount" class="easyui-numberspinner" value="1.00" data-options="min:0.01,max:1.00,increment:0.02,precision:2,onChange:onChangeDiscount" />
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label>折后应付:</label> 
						<input id="receivable" name="receivable" class="easyui-numberbox" data-options="precision:2" />
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label>所属工程:</label> 
						<input id="projectId" name="projectBean.id" class="easyui-combobox" editable="false" data-options="valueField:'id',
							textField:'name', mode:'remote', onShowPanel:showAllAvailableProject" />
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label>结算账号:</label>
						<input id="paymentAccount" name="paymentAccountBean.id" class="easyui-combobox" panelHeight="auto" editable="false" 
							data-options="valueField:'id',textField:'name',onShowPanel:onShowPaymentAccount, 
								formatter:accountRemainMoneyFormatter, onSelect:outOrder_selectPaidAccount" />
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label>本次收款:</label> 
						<input id="paidMoney" name="paidMoney" class="easyui-numberbox" data-options="precision:2, disabled:true" 
							validType="orderOut_checkPaidAndPaymentAccount['#dlg-outorder-new #fm-outorder #paymentAccount']" />
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label><spring:message code="order.signUserId" />:</label> 
						<input id="userSignedTo" name="userSignedTo" class="easyui-textbox" readonly="readonly" required="true" />
					</div>
				</div>
				<!-- <div class="fitem css-dlg-formField">
					<div>
						<label>其它费用类型:</label>
						<input id="accountingType" name="accountingTypeBean.id" class="easyui-combobox" panelHeight="auto" editable="false" 
							data-options="valueField:'id',textField:'name',filter:comboboxFilter,groupField:'accountingMode',disabled:true,
								groupFormatter:outOrder_accountingTypeGroupFormatter, onShowPanel:outOrder_onClickAccountingType, onSelect:outOrder_selectAccounting" />
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label>其它费用金额:</label> 
						<input id="accountingMoney" name="accountingMoney" class="easyui-numberbox" data-options="precision:2, disabled:true" 
							validType="orderOut_checkAccountingTypeAndAccounting['#dlg-outorder-new #fm-outorder #accountingType']" />
					</div>
				</div> -->
				<div class="fitem divHidden">
					<input id="userCreated" name="userCreated" />
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label>操作员:</label> 
						<input id="operateUserFullName" name="operateUserFullName" class="easyui-textbox" disabled="true" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="dlg-buttons-outorder-new">
		<a href="javascript:void(0)" id="preview-outorder-btn" class="easyui-linkbutton" disabled="true"
			iconCls="icon-ok" onclick="previewOutOrder()">预览</a>
		<a href="javascript:void(0)" id="save-outorder-btn" class="easyui-linkbutton c7" disabled="true"
			iconCls="icon-ok" onclick="submitOutOrder()">提交</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg-outorder-new').dialog('close')"><spring:message code="cancel" /></a>
	</div>
