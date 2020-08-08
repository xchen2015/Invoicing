<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="com.pinfly.purchasecharge.core.util.PurchaseChargeConstants"%>
<%@ page import="com.pinfly.purchasecharge.core.model.OrderTypeCode" %>
<%@ page import="com.pinfly.purchasecharge.component.bean.TimeSpan" %>

	<jsp:include page="inOrderAdd.jsp" />
	<div style="width:100%; height:430px;">
		<table id="dg-inorder" title="<spring:message code="order.orderInManagement" />" class="easyui-datagrid" url=""
			toolbar="#toolbar-inorder" pagination="true" rownumbers="true" showFooter="true" 
			singleSelect="true" checkOnSelect="true" selectOnCheck="false" fit="true" 
			fitColumns="true" sortName="<%=PurchaseChargeConstants.CREATE_TIME%>" sortOrder="desc" 
			data-options="onClickRow:onClickInOrderRow, onLoadSuccess:onLoadInOrderSuccess, onCheckAll:onCheckAllInOrder, 
				onUncheckAll:onUnCheckInOrder, onCheck:onCheckAllInOrder, onUncheck:onUnCheckInOrder,onBeforeLoad:onBeforeLoadInOrder, 
				onDblClickRow:onDblClickInOrderRow">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'id',hidden:true"></th>
					<th field="bid" width="70"><spring:message code="order.id" /></th>
					<th field="<%=PurchaseChargeConstants.CUSTOMER_NAME%>" width="50" sortable="true" data-options="formatter:customerNameFormatter">供应商</th>
					<th field="typeCode" width="30" data-options="formatter:orderTypeFormatter">类型</th>
					<th field="statusCode" width="50" data-options="formatter:orderStatusFormatter, align:'center'"><spring:message code="order.statusCode" /></th>
					<th field="<%=PurchaseChargeConstants.CREATE_TIME%>" width="50" sortable="true"><spring:message code="order.createDate" /></th>
					<%-- <th field="<%=PurchaseChargeConstants.DEAL_MONEY %>" width="50" sortable="true"><spring:message code="order.outDealMoney" /></th> --%>
					<th field="<%=PurchaseChargeConstants.RECEIVABLE_MONEY%>" width="50" sortable="true"><spring:message code="order.outDealMoney" /></th>
					<th field="<%=PurchaseChargeConstants.PAID_MONEY %>" width="50" sortable="true" data-options=""><spring:message code="order.paidMoney" /></th>
					<%-- <th field="<%=PurchaseChargeConstants.PAY_TIME %>" width="50" sortable="true"><spring:message code="order.payDate" /></th> --%>
					<%-- <th field="userSignedTo" width="50" data-options="formatter:orderUserSignedFormatter"><spring:message code="order.signUserId" /></th> --%>
					<th field="userCreated" width="50" sortable="true">操作员</th>
					<th field="comment" width="100" data-options=""><spring:message code="order.note" /></th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="toolbar-inorder" style="padding:5px;height:auto">
		<div>
			<span id="div_inorder_typecode" style="margin-bottom: 5px; margin-right:50px">
				<label><input id="typeCode_in_all" name="inorder_typeCode_mode" type="radio" value="0" checked="checked" onclick="inOrder_onSearchOrderByAdvance()">所有</label>&nbsp;&nbsp;
				<label><input id="typeCode_in" name="inorder_typeCode_mode" type="radio" value="<%=OrderTypeCode.IN%>" onclick="inOrder_onSearchOrderByAdvance()">采购进货</label>&nbsp;&nbsp;
				<label><input id="typeCode_in_return" name="inorder_typeCode_mode" type="radio" value="<%=OrderTypeCode.IN_RETURN%>" onclick="inOrder_onSearchOrderByAdvance()">采购退货</label>
			</span>
			<span>
				<a id="btn-newInorder" href="javascript:void(0)" class="easyui-linkbutton c7" iconCls="icon-add" plain="true" 
					onclick="newInOrder()" title="新增采购单">新增采购单</a> 
				<a id="btn-viewInorder" href="javascript:void(0)" class="easyui-linkbutton" disabled="true" iconCls="iconSpan16 viewEye" plain="true" 
					onclick="viewInOrder()" title="选择一行查看订单详情或勾选多行查看订单项">查看订单</a> 
				<c:if test="${sessionScope.login_user.admin}">
					<a id="btn-deleteInorder" href="javascript:void(0)" class="easyui-linkbutton" disabled="true" iconCls="icon-remove" plain="true" 
						onclick="deleteInOrder()" title="选择一行删除订单">删除采购单</a>
				</c:if>
			</span>
		</div>
		
		<div>
			<span id="orderIdSearchSpan" style="">
            	<label>订单号:</label> 
            	<input id="orderId" name="orderId" class="easyui-textbox" style="width:120px">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="查询出库单" onclick="inOrder_onSearchOrderById()">查询</a>
	        </span>
			<span id="advanceSearchSpan" style="margin-left: 100px;">
            	<label>时间:</label> 
            	<input id="startDate" name="startDate" class="easyui-datebox" style="width:100px" 
				editable="false" title="开始时间" data-options="onSelect: inOrder_onSelectStartDate">
            	- <input id="endDate" name="endDate" class="easyui-datebox" style="width:100px" 
				editable="false" title="结束时间" data-options="onSelect: inOrder_onSelectStartDate">&nbsp;
				<select id="timeFrame" name="timeFrame" class="easyui-combobox" style="width:80px" panelHeight="auto" editable="false" data-options="onSelect: inOrder_onSelectTimeFrame">
					<option value="CUSTOMIZE"><spring:message code="<%=TimeSpan.CUSTOMIZE.getMessageCode ()%>" /></option>
					<option value="TODAY" selected="selected"><spring:message code="<%=TimeSpan.TODAY.getMessageCode ()%>" /></option>
					<option value="RECENT_THREE_DAYS"><spring:message code="<%=TimeSpan.RECENT_THREE_DAYS.getMessageCode ()%>" /></option>
					<option value="RECENT_SEVEN_DAYS"><spring:message code="<%=TimeSpan.RECENT_SEVEN_DAYS.getMessageCode ()%>" /></option>
					<option value="RECENT_FIFTEEN_DAYS"><spring:message code="<%=TimeSpan.RECENT_FIFTEEN_DAYS.getMessageCode ()%>" /></option>
					<option value="RECENT_THIRTY_DAYS"><spring:message code="<%=TimeSpan.RECENT_THIRTY_DAYS.getMessageCode ()%>" /></option>
					<option value="CURRENT_MONTH"><spring:message code="<%=TimeSpan.CURRENT_MONTH.getMessageCode ()%>" /></option>
				</select>
            	&nbsp;
            	<label>供应商:</label> 
           		<input id="customerId" name="customerId" class="easyui-combobox" style="width:150px" data-options="
					valueField:'id',
					textField:'shortName',
					panelHeight:'250',
					onSelect: onSelectProvider,
					mode:'local', 
					filter: comboboxFilter,
					onShowPanel: onShowAllProvider"/>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="inOrder_onSearchOrderByAdvance()">查询</a>
	        </span>
		</div>
	</div>

	<!-- review new order dialog -->
	<div id="dlg-inorder-new-review" class="easyui-dialog"
		style="width: 800px; height: 500px; padding:15px;" closed="true"
		buttons="#dlg-buttons-inorder-new-review" data-options="modal:true">
		<div style="width:100%; height:100%;">
			<form id="fm-inorder" class="fm" style="margin:0 auto;padding:0px;width:100%;height:100%;" method="post" novalidate>
				<div class="fitem divHidden">
					<input id="id" name="id" value="0">
				</div>
				<div class="fitem divHidden">
					<input id="inorder-typeCode" name="typeCode">
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label>供应商:</label> 
						<input id="customerName" readonly="readonly" />
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label>开单日期:</label> 
						<input id="createTime" readonly="readonly" />
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label>订单号:</label> 
						<input id="bid" readonly="readonly" />
					</div>
				</div>
				
				<div style="width:100%;float:left;margin-top:10px;margin-bottom:10px;">
					<div style="margin:0 auto;width:660px;">
						<table id="dg-inorderItem-new-review" class="easyui-datagrid" style="width:660px; height:auto;" title="<spring:message code="order.items" />" 
							data-options="
								rownumbers: true,
								singleSelect: true,
								style:{marginBottom:0},
								showFooter: true,
								fitColumns:true
							">
							<thead>
								<tr>
									<th data-options="field:'id',hidden:true"></th>
									<th data-options="field:'name',width:100"><spring:message code="order.product" /></th>
									<th data-options="field:'depositoryName',width:40"><spring:message code="goods.depository" /></th>
									<th data-options="field:'unitPrice',width:30"><spring:message code="order.unitPrice" /></th>
									<th data-options="field:'amount',width:30,align:'right'"><spring:message code="order.amount" /></th>
									<th data-options="field:'unit',width:30"><spring:message code="order.unit" /></th>
									<th data-options="field:'sum',width:40,align:'right'"><spring:message code="order.sum" /></th>
									<th data-options="field:'note',width:80"><spring:message code="order.note" /></th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
				 
				<div class="fitem css-dlg-formField">
					<div>
						<label>折扣率:</label> 
						<input id="discount" name="discount" readonly="readonly" />
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label>折后应付:</label> 
						<input id="receivable" name="receivable" readonly="readonly" />
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label>实收款:</label> 
						<input id="paidMoney" name="paidMoney" readonly="readonly" />
					</div>
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label>收款账号:</label> 
						<input id="paymentAccount" name="paymentAccount" readonly="readonly" />
					</div>
				</div>
				<div class="fitem divHidden">
					<input id="userCreated" name="userCreated" readonly="readonly" />
				</div>
				<div class="fitem css-dlg-formField">
					<div>
						<label>操作员:</label> 
						<input id="operateUserFullName" name="operateUserFullName" readonly="readonly" />
					</div>
				</div>
				<div class="fitem css-dlg-formField" style="float:left;width:100%;">
					<div style="width:660px">
						<label><spring:message code="order.note" />:</label>
						<input id="comment" name="comment" style="width:100%;" readonly="readonly" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="dlg-buttons-inorder-new-review">
		<a href="javascript:void(0)" id="submit-inorder-btn" class="easyui-linkbutton c7" 
			iconCls="icon-ok" onclick="saveModel('#dg-inorder', '#dlg-inorder-new', '#fm-inorder', function() {$('#dlg-inorder-new-review').dialog('close');$('#dg-inorder').datagrid('reload');}, inOrder_accept)">提交</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg-inorder-new-review').dialog('close')">返回</a>
	</div>
	
	<!-- view order dialog -->
	<div id="dlg-inorder-view" class="easyui-dialog"
		style="width: 800px; height: 500px; padding: 15px" closed="true"
		buttons="#dlg-buttons-inorder-view" data-options="modal:true">
		<div class="ftitle" style="margin-left: 30px; margin-right: 30px;">入库单&nbsp;-&nbsp;<span id="order-id-span"></span></div>
		<div style="width:100%; height:85%;">
			<form id="fm-inorder" class="fm" style="margin:0 auto;padding:0px;width:650px;height:100%;" method="post" novalidate>
				<div class="fitem divHidden">
					<input id="id" name="id" value="0">
				</div>
				<div class="fitem divHidden">
					<input id="customerId" name="customerId" />
				</div>
				<div class="fitem css-dlg-formField">
					<label>供应商:</label> 
					<input id="customerName" name="<%=PurchaseChargeConstants.CUSTOMER_NAME %>" readonly="readonly" />
				</div>
				<div class="fitem css-dlg-formField">
					<label>联系人:</label> 
					<input id="contactName" name="contactName" readonly="readonly" />
				</div>
				<div class="fitem css-dlg-formField">
					<label>联系电话:</label> 
					<input id="contactPhone" name="contactPhone" readonly="readonly" />
				</div>
				<div class="fitem css-dlg-formField">
					<label>操作员:</label> 
					<input id="userCreated" name="userCreated" readonly="readonly" />
				</div>
				<div class="fitem css-dlg-formField">
					<label>开单日期:</label> 
					<input id="<%=PurchaseChargeConstants.CREATE_TIME %>" name="<%=PurchaseChargeConstants.CREATE_TIME %>" readonly="readonly" />
				</div>
				<div class="fitem css-dlg-formField">
					<label>折扣率:</label> 
					<input id="discount" name="discount" readonly="readonly" />
				</div>
				<div class="fitem css-dlg-formField">
					<label>折后应付:</label> 
					<input id="receivable" name="receivable" readonly="readonly" />
				</div>
				<div class="fitem css-dlg-formField">
					<label>实付款:</label> 
					<input id="<%=PurchaseChargeConstants.PAID_MONEY %>" name="<%=PurchaseChargeConstants.PAID_MONEY %>" readonly="readonly" />
				</div>
				<div class="fitem css-dlg-formField">
					<label>付款账号:</label> 
					<input id="paymentAccount" name="paymentAccount" readonly="readonly" />
				</div>
				<div class="fitem css-dlg-formField">
					<label>状态:</label> 
					<input id="statusCode" name="statusCode" readonly="readonly" />
				</div>
				<div class="fitem css-dlg-formField" style="clear:left; width:650px;">
					<label><spring:message code="order.note" />:</label>
					<input id="comment" name="comment" style="width:645px;" readonly="readonly" />
				</div>
				
				<table id="dg-inorderItem-view" class="easyui-datagrid" title="<spring:message code="order.items" />" style="width:650px;height:auto;"
					data-options="
						rownumbers: true,
						singleSelect: true,
						showFooter: true,
						style:{display:'inline-block'},
						fitColumns:true
					">
					<thead>
						<tr>
							<th data-options="field:'id',hidden:true"></th>
							<th data-options="field:'goodsName',width:100"><spring:message code="order.product" /></th>
							<th data-options="field:'goodsDepository',width:40, formatter:formatter_orderItemGoodsDepository"><spring:message code="goods.depository" /></th>
							<th data-options="field:'<%=PurchaseChargeConstants.ORDER_UNIT_PRICE %>',width:30"><spring:message code="order.unitPrice" /></th>
							<th data-options="field:'<%=PurchaseChargeConstants.ORDER_AMOUNT %>',width:30,align:'right'"><spring:message code="order.amount" /></th>
							<th data-options="field:'goodsUnit',width:30"><spring:message code="order.unit" /></th>
							<th data-options="field:'<%=PurchaseChargeConstants.ORDER_SUM %>',width:40,align:'right'"><spring:message code="order.sum" /></th>
							<th data-options="field:'comment',width:80"><spring:message code="order.note" /></th>
						</tr>
					</thead>
				</table>
			</form>
		</div>
	</div>
	<div id="dlg-buttons-inorder-view">
		<a href="javascript:void(0)" id="print-inorder-btn" class="easyui-linkbutton"
			iconCls="icon-print" onclick="inOrder_gotoPrintDialog ()"><spring:message code="print" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg-inorder-view').dialog('close')"><spring:message code="cancel" /></a>
	</div>
	
	<!-- 查看多个订单 -->
	<div id="dlg-multi-inorder-view" class="easyui-dialog"
		style="width: 800px; height: 500px; padding: 5px" closed="true"
		buttons="#dlg-buttons-multi-inorder-view" data-options="modal:true">
		<table id="dg-inorderItem-view" class="easyui-datagrid" title="订单明细" style=""
			data-options="rownumbers: true, singleSelect: true, showFooter: true, fitColumns:true,
                fit:true, view:groupview, groupField:'orderBid', groupFormatter:orderGroupFormatter">
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true"></th>
					<th data-options="field:'goodsName',width:100"><spring:message code="order.product" /></th>
					<th data-options="field:'goodsDepository',width:40, formatter:formatter_orderItemGoodsDepository"><spring:message code="goods.depository" /></th>
					<th data-options="field:'<%=PurchaseChargeConstants.ORDER_UNIT_PRICE %>',width:30"><spring:message code="order.unitPrice" /></th>
					<th data-options="field:'<%=PurchaseChargeConstants.ORDER_AMOUNT %>',width:30"><spring:message code="order.amount" /></th>
					<th data-options="field:'goodsUnit',width:30"><spring:message code="order.unit" /></th>
					<th data-options="field:'<%=PurchaseChargeConstants.ORDER_SUM %>',width:40"><spring:message code="order.sum" /></th>
					<th data-options="field:'comment',width:80"><spring:message code="order.note" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="dlg-buttons-multi-inorder-view">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg-multi-inorder-view').dialog('close')"><spring:message code="close" /></a>
	</div>
	<!-- 查看多个订单 -->
	
	<!-- print order -->
	<div id="dlg-print-inorder" class="easyui-dialog" title="打印"
		style="width: 800px; height: 500px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons-print-inorder" data-options="modal:true">
		<div id="div-print-inorder-edit" style="margin: 0px 25px;">
			<div id="div-inorder-header" style="margin-bottom:10px;">
				<div id="div-print-inorder-title" style="height:30px; font-size:20px; font-weight:bold; text-align: center; margin-bottom:10px;"></div>
				<table style="width:100%">
					<tr>
						<td width="10%">供应商:</td>
						<td width="40%"><span style="width:100%" id="provider"></span></td>
						<td width="10%">联系人:</td>
						<td width="40%"><input style="width:100%" type="text" id="contact" name="contact"/></td>
					</tr>
					<tr>
						<td width="10%">开单日期:</td>
						<td width="40%"><span style="width:100%" id="createDate"></span></td>
						<td width="10%">手机:</td>
						<td width="40%"><input style="width:100%" type="text" id="phone" name="phone"/></td>
					</tr>
					<tr>
						<td width="10%">订单号:</td>
						<td width="40%"><span style="width:100%" id="orderId"></span></td>
						<td width="10%">地址:</td>
						<td width="40%"><input style="width:100%" type="text" id="address" name="address"/></td>
					</tr>
				</table>
			</div>
			<div id="inorder-table" style="margin-bottom:10px;"></div>
			<div id="div-inorder-footer">
				<table style="width:100%">
					<tbody>
						<tr>
							<td width="33%">经手人:&nbsp;<span id="user"></span></td>
							<td width="33%">签字:&nbsp;<span id="customerSign"></span></td>
							<td width="33%">日期:&nbsp;<span id="signDate"></span></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div id="div-print-inorder" style="display:none">
			<div id="div-inorder-header" style="margin-bottom:5px;">
				<div id="div-print-inorder-title" style="height:30px; font-size:20px; font-weight:bold; text-align: center; margin-bottom:10px;"></div>
				<table style="width:100%; font-size:12px;">
					<tr>
						<td width="10%">供应商:</td>
						<td width="20%"><span id="provider"></span></td>
						<td width="10%">联系人:</td>
						<td width="20%"><span id="contact"></span></td>
						<td width="10%">手机:</td>
						<td width="20%"><span id="phone"></span></td>
					</tr>
					<tr>
						<td width="10%">地址:</td>
						<td width="20%"><span id="address"></span></td>
						<td width="10%">开单日期:</td>
						<td width="20%"><span id="createDate"></span></td>
						<td width="10%">订单号:</td>
						<td width="20%"><span id="orderId"></span></td>
					</tr>
				</table>
			</div>
			<div id="inorder-table" style="margin-bottom:5px; font-size:12px;"></div>
			<div id="div-inorder-footer">
				<table style="width:100%; font-size:12px;">
					<tbody>
						<tr>
							<td width="10%">经手人:</td>
							<td width="20%"><span id="user"></span></td>
							<td width="10%">签字:</td>
							<td width="20%"><span id="customerSign"></span></td>
							<td width="10%">日期:</td>
							<td width="20%"><span id="signDate"></span></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div id="dlg-buttons-print-inorder">
		<a href="javascript:void(0)" id="" class="easyui-linkbutton"
			iconCls="icon-print" onclick="inOrder_printOrder()"><spring:message code="print" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg-print-inorder').dialog('close')"><spring:message code="cancel" /></a>
	</div>
	<!-- print order -->