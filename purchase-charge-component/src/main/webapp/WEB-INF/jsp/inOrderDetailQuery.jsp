<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="com.pinfly.purchasecharge.core.util.PurchaseChargeConstants"%>
<%@ page import="com.pinfly.purchasecharge.core.model.OrderTypeCode" %>
<%@ page import="com.pinfly.purchasecharge.component.bean.TimeSpan" %>

	<div style="width:100%; height:430px;">
		<table id="dg-inorderItem-query" class="easyui-datagrid" title="采购单明细查询" toolbar="#toolbar-inorderItem-query" 
			data-options="rownumbers: true, singleSelect: true, showFooter: true, fitColumns:true, fit:true, onBeforeLoad:onBeforeLoadInOrderItem">
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true"></th>
					<th field="orderBid" width="50" data-options="">订单号</th>
					<th field="customerName" width="30" data-options="">客户</th>
					<th field="orderCreate" width="50" data-options="">创建时间</th>
					<th field="typeCode" width="30" data-options="formatter:orderTypeFormatter">类型</th>
					<th field="statusCode" width="30" data-options="formatter:orderStatusFormatter, align:'center'"><spring:message code="order.statusCode" /></th>
					<th field="userCreated" width="30">操作员</th>
					<th data-options="field:'goodsName',width:60"><spring:message code="order.product" /></th>
					<th data-options="field:'goodsDepository',width:30, formatter:formatter_orderItemGoodsDepository"><spring:message code="goods.depository" /></th>
					<th data-options="field:'<%=PurchaseChargeConstants.ORDER_UNIT_PRICE %>',width:30"><spring:message code="order.unitPrice" /></th>
					<th data-options="field:'<%=PurchaseChargeConstants.ORDER_AMOUNT %>',width:30"><spring:message code="order.amount" /></th>
					<th data-options="field:'goodsUnit',width:30"><spring:message code="order.unit" /></th>
					<th data-options="field:'<%=PurchaseChargeConstants.ORDER_SUM %>',width:30"><spring:message code="order.sum" /></th>
					<th data-options="field:'comment',width:60"><spring:message code="order.note" /></th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="toolbar-inorderItem-query" style="padding:5px;height:auto">
       	<label>时间:</label> 
       	<input id="startDate" name="startDate" class="easyui-datebox" style="width:100px" 
			editable="false" title="开始时间" data-options="onSelect: inOrderItemQuery_onSelectStartDate">
       	- <input id="endDate" name="endDate" class="easyui-datebox" style="width:100px" 
			editable="false" title="结束时间" data-options="onSelect: inOrderItemQuery_onSelectStartDate">&nbsp;
		<select id="timeFrame" name="timeFrame" class="easyui-combobox" style="width:80px" panelHeight="auto" editable="false" data-options="onSelect: inOrderItemQuery_onSelectTimeFrame">
			<option value="CUSTOMIZE"><spring:message code="<%=TimeSpan.CUSTOMIZE.getMessageCode ()%>" /></option>
			<option value="TODAY"><spring:message code="<%=TimeSpan.TODAY.getMessageCode ()%>" /></option>
			<option value="RECENT_THREE_DAYS"><spring:message code="<%=TimeSpan.RECENT_THREE_DAYS.getMessageCode ()%>" /></option>
			<option value="RECENT_SEVEN_DAYS"><spring:message code="<%=TimeSpan.RECENT_SEVEN_DAYS.getMessageCode ()%>" /></option>
			<option value="RECENT_FIFTEEN_DAYS"><spring:message code="<%=TimeSpan.RECENT_FIFTEEN_DAYS.getMessageCode ()%>" /></option>
			<option value="RECENT_THIRTY_DAYS" selected="selected"><spring:message code="<%=TimeSpan.RECENT_THIRTY_DAYS.getMessageCode ()%>" /></option>
			<option value="CURRENT_MONTH"><spring:message code="<%=TimeSpan.CURRENT_MONTH.getMessageCode ()%>" /></option>
		</select>
	   	&nbsp;
	   	<label>供应商:</label> 
   		<input id="customerId" name="customerId" class="easyui-combobox" style="width:150px" data-options="
			valueField:'id',
			textField:'shortName',
			panelHeight:'250',
			prompt:'输入名称查询',
			mode:'local', 
			filter: comboboxFilter,
			onShowPanel: onShowAllProvider" />
		&nbsp;
		<label><spring:message code="goods.goods" />:</label>
		<input id="goodsId" name="goodsId" class="easyui-combobox" style="width:150px" data-options="
			valueField:'id',
			textField:'name',
			panelHeight:'250',
			prompt:'输入名称查询',
			mode:'local', 
			filter: comboboxFilter,
			formatter: goodsStorageFormatter,
			url:'<c:url value='/goods/getAllModel.html' />'" />
		&nbsp;
		<label><spring:message code="goodsIssue.signUser" />:</label> 
		<input id="userCreate" name="userCreate" class="easyui-combobox" editable="false" style="width:100px" data-options="
			valueField:'id',
			textField:'userId',
			panelHeight:'250',
			onShowPanel: onShowUserPanel" />
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="inOrderItemQuery_onSearchOrderItem()">查询</a>
	</div>
	
	<script type="text/javascript">
	<!--
	var inOrderItemQuery_onSelectStartDate = function (date) 
	{
		$('#toolbar-inorderItem-query #timeFrame').combobox('setValue', 'CUSTOMIZE');
	}
	var inOrderItemQuery_onSelectTimeFrame = function(record) 
	{
		var startDate = generateStartDate(record.value);
		var endDate = new Date().format("yyyy-MM-dd");
		$('#toolbar-inorderItem-query #startDate').datebox('setValue', startDate);
		$('#toolbar-inorderItem-query #endDate').datebox('setValue', endDate);
	}
	var inOrderItemQuery_onSearchOrderItem = function () 
	{
		var startDate = $('#toolbar-inorderItem-query #startDate').combo('getValue');
		var endDate = $('#toolbar-inorderItem-query #endDate').combo('getValue');
		var customerId = $('#toolbar-inorderItem-query #customerId').combo('getValue');
		if((startDate != '' && endDate == '') || (startDate == '' && endDate != '')) 
		{
			$.messager.alert('警告','开始时间和结束时间必须同时填!','warning');
			return;
		}
		if(startDate == '' && endDate == '' && customerId == '') 
		{
			$.messager.alert('警告','请填写查询条件!','warning');
			return;
		}
		$('#dg-inorderItem-query').datagrid('reload');
	}
	var onBeforeLoadInOrderItem = function (param) 
	{
		var startDate = $('#toolbar-inorderItem-query #startDate').combo('getValue');
		var endDate = $('#toolbar-inorderItem-query #endDate').combo('getValue');
		var customerId = $('#toolbar-inorderItem-query #customerId').combo('getValue');
		var goodsId = $('#toolbar-inorderItem-query #goodsId').combo('getValue');
		var userCreate = $('#toolbar-inorderItem-query #userCreate').combo('getValue');
		if(customerId == undefined) 
		{
			customerId = '';
		}
		if(startDate == '' && endDate == '' && customerId == '') 
		{
			var timeFrame = $('#toolbar-inorderItem-query #timeFrame').combo('getValue');
			startDate = generateStartDate(timeFrame);
			endDate = new Date().format("yyyy-MM-dd");
			$('#toolbar-inorderItem-query #startDate').datebox('setValue', startDate);
			$('#toolbar-inorderItem-query #endDate').datebox('setValue', endDate);
			
			startDate = $('#toolbar-inorderItem-query #startDate').combo('getValue');
			endDate = $('#toolbar-inorderItem-query #endDate').combo('getValue');
		}
		
		$('#dg-inorderItem-query').datagrid('options').url = "<c:url value='/inOrder/searchOrderItem.html' />?userCreate="+userCreate+"&startDate="+startDate+"&endDate="+endDate+"&customerId="+customerId+"&goodsId="+goodsId;
		return true;
	}
	//-->
	</script>
