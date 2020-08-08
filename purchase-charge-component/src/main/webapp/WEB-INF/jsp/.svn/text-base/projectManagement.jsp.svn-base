	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	<%@ page import="com.pinfly.purchasecharge.core.util.PurchaseChargeConstants"%>
	
	<div style="width:100%; height:430px;">
		<table id="dg-project" title="<spring:message code="project.projectManagement" />" class="easyui-datagrid" 
			 url="<c:url value='/project/getModelBySearchForm.html' />"
			toolbar="#toolbar-project" pagination="false" rownumbers="true" 
			 singleSelect="true" checkOnSelect="true" selectOnCheck="false"
			data-options="fitColumns:true, fit:true, onBeforeLoad:onBeforeLoadProject">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'id',hidden:true"></th>
					<th field="action" width="15" data-options="formatter:formatter_updateProjectAction"></th>
					<th field="name" width="80" data-options=""><spring:message code="project.name" /></th>
					<th field="customerName" width="60" data-options="formatter:formatter_projectCustomer"><spring:message code="project.customer" /></th>
					<th field="start" width="80"><spring:message code="project.start" /></th>
					<th field="end" width="80"><spring:message code="project.end" /></th>
					<th field="orderMoney" width="60" data-options="formatter:formatter_orderMoney"><spring:message code="project.orderMoney" /></th>
					<th field="constructionFee" width="60" data-options=""><spring:message code="project.constructionFee" /></th>
					<th field="otherFee" width="60" data-options=""><spring:message code="project.otherFee" /></th>
					<th field="dealMoney" width="60" data-options=""><spring:message code="project.dealMoney" /></th>
					<th field="profit" width="60" data-options=""><spring:message code="project.profit" /></th>
					<th field="userCreatedBy" width="60"><spring:message code="project.userCreated" /></th>
					<th field="userUpdatedBy" width="60"><spring:message code="project.userUpdated" /></th>
					<th field="statusCode" width="60" data-options="formatter:formatter_projectStatus"><spring:message code="project.status" /></th>
					<th field="comment" width="100" data-options=""><spring:message code="project.comment" /></th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="toolbar-project" style="padding:5px;height:auto">
		<span>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" 
				onclick="newModel('#dlg-project', '<spring:message code="project.newProject" />', '#fm-project', '<c:url value='/project/addModel.html' />', newProjectCallback)" title="<spring:message code="project.newProject" />"><spring:message code="project.newProject" /></a> 
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" 
				onclick="" title=""><spring:message code="project.editProject" /></a> -->
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" 
				onclick="destroyMultipleModel('#dg-project', '<spring:message code="project.project" />', '<c:url value='/project/deleteModels.html' />', function() {loadGridData ('#dg-project', '<c:url value='/project/getModelBySearchForm.html' />')})" title="<spring:message code="project.removeProject" />"><spring:message code="project.removeProject" /></a>
		</span>
		<span style="float: right; margin-right: 5px"> 
			<input class="easyui-searchbox" style="" 
				data-options="prompt:'<spring:message code="pleaseInputValue" />',searcher:doSearchProject" />
		</span>
	</div>
	
	<div id="dlg-project" class="easyui-dialog"
		style="width: 530px; height: 400px; padding: 15px 5px" closed="true"
		buttons="#dlg-buttons-project" data-options="modal:true">
		<div style="width:100%;height:100%">
			<form id="fm-project" class="fm" method="post" style="margin:0 auto;padding:0px;width:100%;height:100%;" novalidate>
				<div class="fitem divHidden">
					<input id="projectId" name="id" value="0">
					<input id="userCreatedBy" name="userCreatedBy" readonly="readonly" />
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label><spring:message code="project.name" /><span class="iconSpan16 required" />:</label> 
						<input id="projectName" name="name" class="easyui-textbox" required="true" data-options="prompt:'<spring:message code="requiredFieldAndUnique" />'" 
							validType="myRemote['<c:url value='/project/checkExist.html' />', 'projectName', '#projectId']" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label><spring:message code="project.customer" /><span class="iconSpan16 required" />:</label> 
						<input id="projectCustomer" name="customer.id" class="easyui-combobox" required="true" data-options="
							valueField:'id',
							textField:'shortName',
							panelHeight:'250',
							prompt:'输入名称查询',
							mode:'local', 
							filter: comboboxFilter,
							onShowPanel: onShowAllCustomer" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label><spring:message code="project.start" />:</label> 
						<input id="projectDateCreated" name="start" class="easyui-datebox" editable="false" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label><spring:message code="project.constructionFee" />:</label> 
						<input id="projectConstructionFee" name="constructionFee" class="easyui-numberbox" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label><spring:message code="project.otherFee" />:</label> 
						<input id="projectOtherFee" name="otherFee" class="easyui-numberbox" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3" style="width:100%">
					<div style="width:410px">
						<label><spring:message code="project.order" />:</label> 
						<input id="projectOrderOut" name="ordersText" class="easyui-combobox" editable="false" style="width:100%; height:50px"   
							data-options="valueField:'id',textField:'id',panelHeight:'250',method:'post', multiple:true, multiline:true, onShowPanel:showProjectOrderOutPanel, formatter:formatter_orderOutCustomerName" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3" style="width:100%">
					<div style="width:410px">
						<label><spring:message code="project.comment" />:</label> 
						<input id="projectComment" name="comment" class="easyui-textbox" data-options="multiline:true" style="width:100%; height:50px" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="dlg-buttons-project">
		<a id="save-project" href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="saveModel('#dg-project', '#dlg-project', '#fm-project', function() {loadGridData ('#dg-project', '<c:url value='/project/getModelBySearchForm.html' />')})"><spring:message code="save" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg-project').dialog('close')"><spring:message code="cancel" /></a>
	</div>
	
	<!-- 编辑工程状态 -->
	<div id="dlg-project-updateStatus" class="easyui-dialog"
		style="width: 340px; height: 370px; padding: 15px 5px" closed="true"
		buttons="#dlg-buttons-project-updateStatus" data-options="modal:true">
		<div style="width:100%;height:100%">
			<form id="fm-project-updateStatus" class="fm" method="post" style="margin:0 auto;padding:0px;width:100%;height:100%;" novalidate>
				<div class="fitem divHidden">
					<input id="projectId" name="id" value="0">
					<input id="projectConstructionFee" name="constructionFee" />
					<input id="projectOtherFee" name="otherFee" />
					<input id="projectDateCreated" name="start" />
					<input id="projectCustomer" name="customer.id" />
					<input id="projectOrderOut" name="ordersText" />
					<input id="userCreatedBy" name="userCreatedBy" />
				</div>
				<div class="fitem css-dlg-formField2">
					<div>
						<label><spring:message code="project.name" />:</label> 
						<input id="projectName" name="name" readonly="readonly" style="border: none" />
					</div>
				</div>
				<div class="fitem css-dlg-formField2">
					<div>
						<label><spring:message code="project.status" />:</label> 
						<select id="projectStatus" name="statusCode" class="easyui-combobox" editable="false" panelHeight="auto" style="width:200px;">
							<option value="NEW" selected="selected"><spring:message code="project.status.new" /></option>
							<option value="APPROVED"><spring:message code="project.status.approved" /></option>
							<option value="PROCESSED"><spring:message code="project.status.processed" /></option>
							<option value="COMPLETED"><spring:message code="project.status.completed" /></option>
							<option value="CANCELED"><spring:message code="project.status.canceled" /></option>
						</select>
					</div>
				</div>
				<div class="fitem css-dlg-formField2" style="">
					<div style="">
						<label><spring:message code="project.comment" />:</label> 
						<input id="comment" name="comment" class="easyui-textbox" data-options="multiline:true" style="width:100%;height:50px" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="dlg-buttons-project-updateStatus">
		<a id="save-project-updateStatus" href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="saveModel('#dg-project', '#dlg-project-updateStatus', '#fm-project-updateStatus', function() {loadGridData ('#dg-project', '<c:url value='/project/getModelBySearchForm.html' />')})"><spring:message code="save" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg-project-updateStatus').dialog('close')"><spring:message code="cancel" /></a>
	</div>
	<!-- 编辑工程状态 -->
	
	<!-- 查看多个订单 -->
	<div id="dlg-project-multi-outorder-view" class="easyui-dialog"
		style="width: 800px; height: 500px; padding: 5px" closed="true"
		buttons="#dlg-project-buttons-multi-outorder-view" data-options="modal:true">
		<table id="dg-project-outorderItem-view" class="easyui-datagrid" style=""
			data-options="
				rownumbers: true,
				singleSelect: true,
				showFooter: true,
                fitColumns:true,
                noheader:true,
                fit:true,
                view:groupview,
                groupField:'orderId',
                groupFormatter:orderGroupFormatter
			">
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
	<div id="dlg-project-buttons-multi-outorder-view">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg-project-multi-outorder-view').dialog('close')"><spring:message code="cancel" /></a>
	</div>
	<!-- 查看多个订单 -->
	
	<script type="text/javascript">
<!--
	function onBeforeLoadProject () {}
	var isNewProject = false;
	function newProjectCallback () 
	{
		isNewProject = true;
		var createDate = new Date().format("yyyy-MM-dd");
		$('#dlg-project #fm-project #projectDateCreated').datebox('setValue', createDate);
		$('#dlg-project #fm-project #projectConstructionFee').numberbox('setValue', 0);
		$('#dlg-project #fm-project #projectOtherFee').numberbox('setValue', 0);
	}
	function editProjectCallback () 
	{
		isNewProject = false;
		var row = $('#dg-project').datagrid('getSelected');
		if(row) 
		{
			$('#dlg-project #fm-project #projectCustomer').combobox('setValue', row.customer.id);
			$('#dlg-project #fm-project #projectCustomer').combobox('setText', row.customer.shortName);
			if(row.orders && row.orders.length > 0) 
			{
				var projectOrders = new Array();
				for(var i = 0; i < row.orders.length; i ++) 
				{
					projectOrders[i] = row.orders[i].id;
				}
				$('#dlg-project #fm-project #projectOrderOut').combobox('setValues', projectOrders);
				$('#dlg-project #fm-project #projectOrderOut').combobox('setText', row.ordersText);
			}
		}
	}
	function showProjectOrderOutPanel () 
	{
		var projectId = '';
		if(isNewProject == false) 
		{
			var row = $('#dg-project').datagrid('getSelected');
			projectId = row.id;
		}
		var customerId = $('#dlg-project #fm-project #projectCustomer').combobox('getValue');
		var url = '<c:url value='/project/getAvailableOrderByCustomer.html' />?customerId=' + customerId + "&projectId=" + projectId;
		$(this).combobox('reload', url);
	}
	function formatter_projectCustomer (value,row,index) 
	{
		if(row) 
		{
			return row.customer.shortName;
		}
	}
	function formatter_orderOutCustomerName (row) 
	{
		if(row) 
		{
			var s = '客户: <span style="font-weight:bold">' + row.customerBean.shortName + '</span></br>' + 
				'开单时间: <span>' + row.createTime.substring(0, 10) + '</span><br/>' +
				'订单金额: <span>' + row.receivable + '</span>';
			return s;
		}
	}
	function doSearchProject () {}
	function formatter_orderMoney (value,row,index) 
	{
		var orderMoney = 0;
		if(row && row.orders) 
		{
			for(var i = 0; i < row.orders.length; i ++) 
			{
				orderMoney += row.orders[i].receivable;
			}
		}
		if(orderMoney > 0) 
		{
			return "<a href='javascript:void(0)' style='color:#000; font-weight:bold' id='"+row.id+"' onclick='showProjectOrderDetail("+index+")' title='设备明细'>" + orderMoney + "</a>";
		}
		return orderMoney;
	}
	function formatter_projectStatus (value,row,index) 
	{
		if('NEW' == value) 
		{
			return "<a href='javascript:void(0)' style='color:#000; font-weight:bold' id='"+row.id+"' onclick='showUpdateProjectStatus("+index+")' title='编辑状态'>" + "<spring:message code='project.status.new' />" + "</a>";
		}
		else if('APPROVED' == value) 
		{
			return "<a href='javascript:void(0)' style='color:#000; font-weight:bold' id='"+row.id+"' onclick='showUpdateProjectStatus("+index+")' title='编辑状态'>" + "<spring:message code="project.status.approved" />" + "</a>";
		}
		else if('PROCESSED' == value) 
		{
			return "<a href='javascript:void(0)' style='color:#000; font-weight:bold' id='"+row.id+"' onclick='showUpdateProjectStatus("+index+")' title='编辑状态'>" + "<spring:message code="project.status.processed" />" + "</a>";
		}
		else if('COMPLETED' == value) 
		{
			return "<span class='icon-ok' style='display:block;' title='<spring:message code="project.status.completed" />，不可编辑状态'>&nbsp;</span>";
		}
		else if('CANCELED' == value) 
		{
			return "<span class='icon-cancel' style='display:block;' title='<spring:message code="project.status.canceled" />，不可编辑状态'>&nbsp;</span>";
		}
		return '';
	}
	var showProjectOrderDetail = function (rowIndex) 
	{
		$('#dg-project').datagrid('selectRow', rowIndex);
		var row = $('#dg-project').datagrid('getSelected');
		if(row) 
		{
			var orderIds = "";
			for(var i = 0; i < row.orders.length; i++) 
			{
				orderIds += (row.orders[i].id + ";");
			}
			orderIds = orderIds.substring(0, orderIds.length-1);
			
			$('#dlg-project-multi-outorder-view').dialog('open').dialog('setTitle', '设备明细');
			loadGridData ('#dlg-project-multi-outorder-view #dg-project-outorderItem-view', '<c:url value='/outOrder/getOrderItemsByOrderIds.html' />', {orderIds : orderIds});
		}
	}
	var showUpdateProjectStatus = function (rowIndex) 
	{
		$('#dg-project').datagrid('selectRow', rowIndex);
		var row = $('#dg-project').datagrid('getSelected');
		if(row) 
		{
			editModel('#dg-project', '#dlg-project-updateStatus', '编辑工程状态', '#fm-project-updateStatus', '<c:url value='/project/updateModel.html' />');
			$('#dlg-project-updateStatus #fm-project-updateStatus #projectCustomer').val(row.customer.id);
		}
	}
	function formatter_updateProjectAction (value,row,index) 
	{
		if('NEW' == row.statusCode || 'APPROVED' == row.statusCode || 'PROCESSED' == row.statusCode) 
		{
			return '<a href="#" class="icon-edit" style="display:inline-block;width:16px;" title="<spring:message code="project.editProject" />" onclick="updateProject('+index+')">&nbsp;</a>';
		}
	}
	function updateProject(rowIndex) 
	{
		$('#dg-project').datagrid('selectRow', rowIndex);
		var row = $('#dg-project').datagrid('getSelected');
		if(row) 
		{
			editModel('#dg-project', '#dlg-project', '<spring:message code="project.editProject" />', '#fm-project', '<c:url value='/project/updateModel.html' />', editProjectCallback);
		}
	}
//-->
	</script>
