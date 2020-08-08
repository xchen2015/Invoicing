<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

	<!-- new order dialog -->
	<div id="dlg-inorder-new" class="easyui-dialog"
		style="width: 800px; height: 600px; padding:15px;" closed="true"
		buttons="#dlg-buttons-inorder-new" data-options="modal:true">
		<div style="width:100%; height:100%;">
			<form id="fm-inorder" class="fm" style="margin:0 auto;padding:0px;width:100%;height:100%;" method="post" novalidate>
				<div class="fitem divHidden">
					<input id="inorder-typeCode" name="typeCode">
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label>供应商<span class="iconSpan16 required" />:</label> 
						<input id="customerId" name="customerBean.id" class="easyui-combobox" data-options="
							valueField:'id',
							textField:'shortName',
							panelHeight:'250',
							required:true,
							prompt:'输入名称查询',
							onSelect: onSelectProvider,
							onChange:onChangeProvider,
							filter: comboboxFilter,
							onShowPanel: onShowAllProvider" />
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
				
				<div class="fitem divHidden">
					<input id="inorderItemList" name="orderItemList" />
				</div>
				
				<div style="width:100%;float:left">
					<div style="margin:0 auto;width:660px;">
						<table id="dg-inorderItem-new" class="easyui-datagrid" title="<spring:message code="order.items" />" style="width:660px;height:250px" 
								data-options="
									rownumbers: true,
									singleSelect: true,
									toolbar: '#tb-inorderList',
									onClickRow: inOrder_onClickRow,
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
												onSelect: inOrder_onSelectGoods,
												formatter: goodsStorageFormatter,
												mode:'local', 
												filter: comboboxFilter,
												url:'<c:url value='/goods/getAllModel.html' />'
											}
										}"><spring:message code="order.product" /></th>
									<th data-options="field:'depository',width:40,
										formatter:formatter_orderGoodsDepository,
										editor:{
											type:'combobox',
											options:{
												valueField:'id',textField:'name',method:'post',editable:false, panelHeight:'auto', 
												url:'<c:url value='/goodsDepository/getAllModel.html' />', required:true
											}
										}">
										仓库</th>
									<th data-options="field:'unitPrice',width:30,editor:{type:'numberbox',options:{required:true, precision:2, onChange: inOrder_onChangeUnitPrice}}"><spring:message code="order.unitPrice" /></th>
									<th data-options="field:'amount',width:30,align:'right',editor:{type:'numberbox', options:{required:true, onChange: inOrder_onChangeAmount}}"><spring:message code="order.amount" /></th>
									<th data-options="field:'unit',width:30,editor:'text'"><spring:message code="order.unit" /></th>
									<th data-options="field:'sum',width:40,align:'right',editor:{type:'numberbox',options:{precision:2}}"><spring:message code="order.sum" /></th>
									<th data-options="field:'depositoryName',hidden:true"></th>
									<th data-options="field:'note',width:80,editor:'text'"><spring:message code="order.note" /></th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
				 
			    <div id="tb-inorderList" style="height:auto">
			        <a id="btn-add-orderItem" href="javascript:void(0)" class="easyui-linkbutton" disabled="true" data-options="iconCls:'icon-add',plain:true" onclick="inOrder_append()"><spring:message code="order.append" /></a>
<%-- 				        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="inOrder_accept()"><spring:message code="order.accept" /></a> --%>
			        <a id="btn-delete-orderItem" href="javascript:void(0)" class="easyui-linkbutton" disabled="true" data-options="iconCls:'icon-remove',plain:true" onclick="inOrder_removeit()"><spring:message code="order.remove" /></a>
					<span id="validateOutorderMsg"></span>
			    </div>
				
				<div class="fitem css-dlg-formField" style="width:100%">
					<div style="width:660px">
						<label><spring:message code="order.note" />:</label>
						<input id="comment" name="comment" class="easyui-textbox" style="width:100%;" />
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label>折扣率:</label> 
						<input id="discount" name="discount" class="easyui-numberspinner" value="1.00" data-options="min:0.01,max:1.00,increment:0.02,precision:2,onChange:inOrder_onChangeDiscount" />
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label>折后应付:</label> 
						<input id="receivable" name="receivable" class="easyui-numberbox" data-options="precision:2" />
					</div>
				</div>
				<div class="fitem divHidden">
					<input id="userCreated" name="userCreated" />
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label>操作员:</label> 
						<input id="operateUserFullName" name="operateUserFullName" class="easyui-textbox" disabled="true" />
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label>结算账号:</label>
						<input id="paymentAccount" name="paymentAccountBean.id" class="easyui-combobox" panelHeight="auto" editable="false" 
							data-options="valueField:'id',textField:'name',method:'post', onShowPanel:onShowPaymentAccount, 
								formatter:accountRemainMoneyFormatter, onSelect:inOrder_selectPaidAccount" />
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label>本次付款:</label> 
						<input id="paidMoney" name="paidMoney" class="easyui-numberbox" data-options="precision:2" 
							validType="checkOrderInPaidAndPaymentAccount['#dlg-inorder-new #fm-inorder #paymentAccount']" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="dlg-buttons-inorder-new">
		<a href="javascript:void(0)" id="preview-inorder-btn" class="easyui-linkbutton" disabled="true" 
			iconCls="icon-ok" onclick="beforeSubmitInOrder()">预览</a>
		<a href="javascript:void(0)" id="save-inorder-btn" class="easyui-linkbutton c7" disabled="true" 
			iconCls="icon-ok" onclick="submitInOrder()">提交</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg-inorder-new').dialog('close')"><spring:message code="cancel" /></a>
	</div>
