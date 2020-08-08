<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="com.pinfly.purchasecharge.core.util.PurchaseChargeConstants"%>
<%@ page import="com.pinfly.purchasecharge.core.config.PurchaseChargeProperties"%>
<%@ page import="com.pinfly.purchasecharge.core.model.OrderTypeCode" %>
<%@ page import="com.pinfly.purchasecharge.component.bean.TimeSpan"%>

	<jsp:include page="deliveryCompanyManagement.jsp" />
	<div style="width:100%; height:430px;">
		<table id="dg-outorder" title="<spring:message code="order.orderOutDeliveryManagement" />" class="easyui-datagrid" toolbar="#toolbar-outorder" pagination="true" rownumbers="true" 
			singleSelect="true" checkOnSelect="true" selectOnCheck="false" fit="true"  
			fitColumns="true" sortName="<%=PurchaseChargeConstants.CREATE_TIME%>" sortOrder="desc" 
			data-options="onClickRow:onClickOutOrderRow, onLoadSuccess:onLoadOutOrderSuccess, onCheckAll:onCheckAllOutOrder, onUncheckAll:onUnCheckOutOrder, 
				onCheck:onCheckAllOutOrder, onUncheck:onUnCheckOutOrder,onBeforeLoad:onBeforeLoadOutOrderDelivery, onDblClickRow:onDblClickOutOrderRow">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'id',hidden:true"></th>
					<th field="bid" width="50"><spring:message code="order.id" /></th>
					<th field="<%=PurchaseChargeConstants.CUSTOMER_NAME%>" width="50" sortable="true" data-options="formatter:customerNameFormatter"><spring:message code="order.customer" /></th>
					<th field="typeCode" width="30" data-options="formatter:orderTypeFormatter">类型</th>
					<th field="statusCode" width="50" data-options="formatter:formatter_orderStatusForDelivery, align:'center'"><spring:message code="order.statusCode" /></th>
					<th field="<%=PurchaseChargeConstants.CREATE_TIME%>" width="50" sortable="true"><spring:message code="order.createDate" /></th>
					<th field="userSignedTo" width="50" data-options="formatter:orderUserSignedFormatter"><spring:message code="order.signUserId" /></th>
					<th field="userCreated" width="50" sortable="true">操作员</th>
					<th field="comment" width="100" data-options=""><spring:message code="order.note" /></th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="toolbar-outorder" style="padding:5px;height:auto">
		<div>
			<span id="orderIdSearchSpan" style="">
            	<label>订单号:</label> 
            	<input id="orderId" name="orderId" class="easyui-textbox" style="width:120px">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="查询出库单" onclick="onSearchOrderById()">查询</a>
	        </span>
			<span id="advanceSearchSpan" style="margin-left:100px">
            	<label>时间:</label> 
            	<input id="startDate" name="startDate" class="easyui-datebox" style="width:100px" 
				editable="false" title="开始时间" data-options="onSelect: onSelectStartDate" />
            	- <input id="endDate" name="endDate" class="easyui-datebox" style="width:100px" 
				editable="false" title="结束时间" data-options="onSelect: onSelectStartDate" />&nbsp;
				<select id="timeFrame" name="timeFrame" class="easyui-combobox" style="width:80px" panelHeight="auto" editable="false" data-options="onSelect: onSelectTimeFrame">
					<option value="CUSTOMIZE"><spring:message code="<%=TimeSpan.CUSTOMIZE.getMessageCode ()%>" /></option>
					<option value="TODAY" selected="selected"><spring:message code="<%=TimeSpan.TODAY.getMessageCode ()%>" /></option>
					<option value="RECENT_THREE_DAYS"><spring:message code="<%=TimeSpan.RECENT_THREE_DAYS.getMessageCode ()%>" /></option>
					<option value="RECENT_SEVEN_DAYS"><spring:message code="<%=TimeSpan.RECENT_SEVEN_DAYS.getMessageCode ()%>" /></option>
					<option value="RECENT_FIFTEEN_DAYS"><spring:message code="<%=TimeSpan.RECENT_FIFTEEN_DAYS.getMessageCode ()%>" /></option>
					<option value="RECENT_THIRTY_DAYS"><spring:message code="<%=TimeSpan.RECENT_THIRTY_DAYS.getMessageCode ()%>" /></option>
					<option value="CURRENT_MONTH"><spring:message code="<%=TimeSpan.CURRENT_MONTH.getMessageCode ()%>" /></option>
				</select>
            	&nbsp;
            	<label>客户:</label> 
           		<input id="customerId" name="customerId" class="easyui-combobox" style="width:150px" data-options="
					valueField:'id',
					textField:'shortName',
					panelHeight:'250',
					onSelect: onSelectCustomer,
					mode:'local', 
					filter: comboboxFilter,
					onShowPanel: onShowAllCustomer" />
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="查询出库单" 
					onclick="onSearchOrderByAdvance()">查询</a>
				<a id="btn-viewOutorder" href="javascript:void(0)" class="easyui-linkbutton" disabled="true" iconCls="iconSpan16 viewEye" plain="true" 
					onclick="viewOutOrderForDelivery()" title="选择一行查看订单详情或勾选多行查看订单项">查看销售单</a>
				<a id="btn-manage-deliveryCompany" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" 
					onclick="showManageDeliveryCompany()" title="">管理物流公司</a>
	        </span>
        </div>
	</div>

	<!-- view order dialog -->
	<div id="dlg-outorder-view" class="easyui-dialog"
		style="width: 800px; height: 500px; padding: 15px" closed="true"
		buttons="#dlg-buttons-outorder-view" data-options="modal:true">
		<div class="ftitle" style="margin-left: 30px; margin-right: 30px;">销售单&nbsp;-&nbsp;<span id="order-id-span"></span></div>
		<div style="width:100%; weight:100%;">
			<form id="fm-outorder" class="fm" style="margin:0 auto;padding:0px;width:650px;height:100%;" method="post" novalidate>
				<div class="fitem divHidden">
					<input id="id" name="id" value="0">
				</div>
				<div class="fitem divHidden">
					<input id="customerId" name="customerId" />
				</div>
				<div class="fitem css-dlg-formField">
					<label><spring:message code="order.customer" />:</label> 
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
					<label><spring:message code="order.signUserId" />:</label> 
					<input id="userSignedTo" name="userSignedTo" readonly="readonly" />
				</div>
				<div class="fitem css-dlg-formField">
					<label>所属工程:</label> 
					<input id="projectId" name="projectId" readonly="readonly" style="color:blue" />
				</div>
				<div class="fitem css-dlg-formField">
					<label>开单日期:</label> 
					<input id="<%=PurchaseChargeConstants.CREATE_TIME %>" name="<%=PurchaseChargeConstants.CREATE_TIME %>" readonly="readonly" />
				</div>
				<div class="fitem css-dlg-formField">
					<label>状态:</label> 
					<input id="statusCode" name="statusCode" readonly="readonly" />
				</div>
				<!-- <div class="fitem css-dlg-formField">
					<label>折扣率:</label> 
					<input id="discount" name="discount" readonly="readonly" />
				</div>
				<div class="fitem css-dlg-formField">
					<label>折后应付:</label> 
					<input id="receivable" name="receivable" readonly="readonly" />
				</div>
				<div class="fitem css-dlg-formField">
					<label>实收款:</label> 
					<input id="paidMoney" name="paidMoney" readonly="readonly" />
				</div>
				<div class="fitem css-dlg-formField">
					<label>收款账号:</label> 
					<input id="" name="" readonly="readonly" />
				</div> -->
				<div class="fitem css-dlg-formField" style="clear:left; width:650px;">
					<label><spring:message code="order.note" />:</label>
					<input id="comment" name="comment" style="width:645px;" readonly="readonly" />
				</div>
				
				<table id="dg-outorderItem-view" class="easyui-datagrid" title="<spring:message code="order.items" />" style="width:650px;height:auto"
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
							<%-- <th data-options="field:'<%=PurchaseChargeConstants.ORDER_SUM %>',width:40,align:'right'"><spring:message code="order.sum" /></th> --%>
							<th data-options="field:'comment',width:80"><spring:message code="order.note" /></th>
						</tr>
					</thead>
				</table>
			</form>
		</div>
	</div>
	<div id="dlg-buttons-outorder-view">
		<a href="javascript:void(0)" id="print-outorder-btn" class="easyui-linkbutton"
			iconCls="icon-print" onclick="gotoPrintDialog ()"><spring:message code="print" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg-outorder-view').dialog('close')"><spring:message code="cancel" /></a>
	</div>
	
	<!-- 查看多个订单 -->
	<div id="dlg-multi-outorder-view" class="easyui-dialog" style="width: 800px; height: 500px; padding: 5px" closed="true"
		buttons="#dlg-buttons-multi-outorder-view" data-options="modal:true">
		<table id="dg-outorderItem-view" class="easyui-datagrid" 
			data-options="rownumbers: true, singleSelect: true, showFooter: true,
                fitColumns:true, noheader:true, fit:true, view:groupview,
                groupField:'orderBid', groupFormatter:orderGroupFormatter">
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true"></th>
					<!--<th data-options="field:'orderCreate',width:120"></th>-->
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
	<div id="dlg-buttons-multi-outorder-view">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg-multi-outorder-view').dialog('close')"><spring:message code="cancel" /></a>
	</div>
	<!-- 查看多个订单 -->
	
	<!-- update delivery -->
	<div id="dlg-update-delivery" class="easyui-dialog"
		style="width: 530px; height: 300px; padding: 15px 5px" closed="true"
		buttons="#dlg-buttons-update-delivery" data-options="modal:true">
		<div style="width:100%;height:100%">
			<form id="fm-outorder-delivery" class="fm" method="post" style="margin:0 auto;padding:0px;width:100%;height:100%;" novalidate>
				<div class="fitem divHidden">
					<input id="deliveryId" name="id" value="0">
					<input id="orderId" name="orderBean.id" type="hidden" />
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label style="color:#606B73">物流公司:</label> 
						<input id="deliveryCompany" name="deliveryCompanyBean.id" class="easyui-combobox" editable="false" required="true" 
							data-options="valueField:'id',textField:'name',panelHeight:'250',mode:'remote', onShowPanel: onShowAllDeliveryCompany" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label>物流单号:</label> 
						<input id="deliveryNumber" name="number" class="easyui-textbox" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label>包件数量:</label> 
						<input id="deliveryAmount" name="amount" class="easyui-numberbox" required="true" data-options="precision:0" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label>发货日期:</label> 
						<input id="deliveryDateCreated" name="dateCreated" class="easyui-datebox" editable="false" data-options="" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label>运费:</label> 
						<input id="deliveryFee" name="fee" class="easyui-numberbox" data-options="" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="dlg-buttons-update-delivery">
		<a href="javascript:void(0)" id="" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="saveModel('#dg-outorder', '#dlg-update-delivery', '#fm-outorder-delivery')"><spring:message code="save" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg-update-delivery').dialog('close')"><spring:message code="cancel" /></a>
	</div>
	<!-- update delivery -->
	
	<!-- print order -->
	<div id="dlg-print-outorder" class="easyui-dialog" title="打印"
		style="width: 800px; height: 500px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons-print-outorder" toolbar="#div-print-outorder-setting" data-options="modal:true">
		<div id="div-print-outorder-edit" style="margin:0px 20px">
			<div id="div-outorder-header" style="margin-bottom:10px;">
				<div id="div-print-outorder-title" style="height:30px; font-size:20px; font-weight:bold; text-align: center; margin-bottom:10px;">
					<input id="titleHeader" style="width: auto;" />
					<span id="titleSpan"></span>
				</div>
				<table style="width:100%">
					<tr>
						<td width="10%">客户:</td>
						<td width="40%"><span style="width:100%" id="customer"></span></td>
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
			<div id="outorder-table" style="margin-bottom:10px;"></div>
			<div id="div-outorder-footer">
				<table style="width:100%">
					<tbody>
						<tr>
							<td width="25%">经手人:&nbsp;<span id="user"></span></td>
							<td width="25%">业务员:&nbsp;<span id="salesman"></span></td>
							<td width="25%">客户签字:&nbsp;<span id="customerSign"></span></td>
							<td width="25%">日期:&nbsp;<span id="signDate"></span></td>
						</tr>
						<tr>
							<td colspan="4"><textarea id="attachment" name="attachment" rows="3" cols="97"><%=PurchaseChargeProperties.getInstance ().getConfig (PurchaseChargeConstants.PRINT_ORDER_FOOTER) %></textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div id="div-print-outorder" style="display:none"></div>
	</div>
	<div id="div-print-outorder-setting">
		<label><input id="checkMoney" type="checkbox" checked="checked" style="width:20px" onclick="checkPrintPrice(this)">显示金额</label>
	</div>
	<div id="dlg-buttons-print-outorder">
		<a href="javascript:void(0)" id="" class="easyui-linkbutton"
			iconCls="icon-print" onclick="printOrder()"><spring:message code="print" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg-print-outorder').dialog('close')"><spring:message code="cancel" /></a>
	</div>
	<!-- print order -->
