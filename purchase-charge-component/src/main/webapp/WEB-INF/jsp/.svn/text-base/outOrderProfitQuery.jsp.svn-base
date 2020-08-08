	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	<%@ page import="com.pinfly.purchasecharge.core.util.PurchaseChargeConstants"%>
	<%@ page import="com.pinfly.purchasecharge.component.bean.TimeSpan"%>
	
	<div style="width:100%; height:430px;">
		<table id="dg-outorder-profit" title="<spring:message code="orderProfitManagement" />" class="easyui-datagrid"
			toolbar="#toolbar-outorder-profit" pagination="true" rownumbers="true" showFooter="true" 
			singleSelect="true" checkOnSelect="true" selectOnCheck="false" fit="true"  
			fitColumns="true" sortName="<%=PurchaseChargeConstants.CREATE_TIME%>" sortOrder="desc" 
			data-options="onBeforeLoad:onBeforeLoadOrderProfit, onLoadSuccess:onLoadOrderProfitSuccess">
			<thead>
				<tr>
					<th data-options="field:'id', hidden:true"><spring:message code="order.id" /></th>
					<th width="50" data-options="field:'bid', formatter:orderTooltipFormatter"><spring:message code="order.id" /></th>
					<th field="<%=PurchaseChargeConstants.CUSTOMER_NAME%>" width="50" sortable="true" data-options="formatter:customerNameFormatter"><spring:message code="order.customer" /></th>
					<th field="<%=PurchaseChargeConstants.CREATE_TIME%>" width="50" sortable="true"><spring:message code="order.createDate" /></th>
					<th field="typeCode" width="40" sortable="true" data-options="formatter:orderTypeFormatter, align:'center'">类型</th>
					<th field="statusCode" width="40" data-options="formatter:orderStatusFormatter, align:'center'"><spring:message code="order.statusCode" /></th>
					<th field="<%=PurchaseChargeConstants.RECEIVABLE_MONEY%>" width="40" sortable="true" data-options="styler:dealMoney_styler"><spring:message code="order.inDealMoney" /></th>
					<th field="<%=PurchaseChargeConstants.PROFIT_MONEY%>" width="40" sortable="true" data-options="styler:dealMoney_styler"><spring:message code="order.profit" /></th>
					<th field="userSignedTo" width="40" data-options="formatter:orderUserSignedFormatter"><spring:message code="order.signUserId" /></th>
					<th field="userCreated" width="40" sortable="true">操作员</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="toolbar-outorder-profit" style="padding:5px;height:auto">
		<div>
			<span id="advanceSearchSpan" style="width: 100%; display: inline-block;">
            	<label>时间:</label> 
            	<input id="startDate" name="startDate" class="easyui-datebox" style="width:100px" 
				editable="false" title="开始时间" data-options="onSelect: onOrderProfitSelectStartDate">
            	- <input id="endDate" name="endDate" class="easyui-datebox" style="width:100px" 
				editable="false" title="结束时间" data-options="onSelect: onOrderProfitSelectStartDate">&nbsp;
				<select id="timeFrame" name="timeFrame" class="easyui-combobox" style="width:80px" panelHeight="auto" editable="false" data-options="onSelect: onOrderProfitSelectTimeFrame">
					<option value="CUSTOMIZE"><spring:message code="<%=TimeSpan.CUSTOMIZE.getMessageCode ()%>" /></option>
					<option value="TODAY"><spring:message code="<%=TimeSpan.TODAY.getMessageCode ()%>" /></option>
					<option value="RECENT_THREE_DAYS"><spring:message code="<%=TimeSpan.RECENT_THREE_DAYS.getMessageCode ()%>" /></option>
					<option value="RECENT_SEVEN_DAYS"><spring:message code="<%=TimeSpan.RECENT_SEVEN_DAYS.getMessageCode ()%>" /></option>
					<option value="RECENT_FIFTEEN_DAYS"><spring:message code="<%=TimeSpan.RECENT_FIFTEEN_DAYS.getMessageCode ()%>" /></option>
					<option value="RECENT_THIRTY_DAYS"><spring:message code="<%=TimeSpan.RECENT_THIRTY_DAYS.getMessageCode ()%>" /></option>
					<option value="CURRENT_MONTH" selected="selected"><spring:message code="<%=TimeSpan.CURRENT_MONTH.getMessageCode ()%>" /></option>
				</select>
            	&nbsp;
            	<label>客户:</label> 
           		<input id="customerId" name="customerId" class="easyui-combobox" style="width:150px" data-options="
					valueField:'id',
					textField:'shortName',
					panelHeight:'250',
					prompt:'输入名称查询',
					filter: comboboxFilter,
					onShowPanel: onShowAllCustomer" />
            	&nbsp;
            	<label>业务员:</label> 
            	<c:if test="${sessionScope.login_user.admin}">
	           		<input id="userCreate" name="userCreate" class="easyui-combobox" editable="false" style="width:100px" data-options="
						valueField:'id',
						textField:'userId',
						panelHeight:'250',
						onShowPanel: onShowUserPanel" />
            	</c:if>
            	<c:if test="${!sessionScope.login_user.admin}">
            		<input id="userCreate" name="userCreate" class="easyui-combobox" editable="false" style="width:100px" data-options="
						valueField:'id',
						textField:'userId',
						disabled: true" />
            	</c:if>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="查询出库单" onclick="onSearchOrderProfitByAdvance()">查询</a>
	        </span>
        </div>
	</div>
	
	<script type="text/javascript">
		var onOrderProfitSelectStartDate = function (date) 
		{
			$('#toolbar-outorder-profit #advanceSearchSpan #timeFrame').combobox('setValue', 'CUSTOMIZE');
		}
		var onOrderProfitSelectTimeFrame = function(record) 
		{
			setInitialOrderProfitTimeFrame(record.value);
		}
		var onSearchOrderProfitByAdvance = function () 
		{
			var startDate = $('#toolbar-outorder-profit #advanceSearchSpan #startDate').combo('getValue');
			var endDate = $('#toolbar-outorder-profit #advanceSearchSpan #endDate').combo('getValue');
			var customerId = $('#toolbar-outorder-profit #advanceSearchSpan #customerId').combo('getValue');
			var userCreate = $('#toolbar-outorder-profit #advanceSearchSpan #userCreate').combo('getValue');
			if((startDate != '' && endDate == '') || (startDate == '' && endDate != '')) 
			{
				$.messager.alert('警告','开始时间和结束时间必须同时填!','warning');
				return;
			}
			if(startDate == '' && endDate == '' && customerId == '' && userCreate == '') 
			{
				$.messager.alert('警告','请填写查询条件!','warning');
				return;
			}
			$('#dg-outorder-profit').datagrid('reload');
		}
		var defaultOrderProfitTimeFrame = 'RECENT_THIRTY_DAYS';
		var onBeforeLoadOrderProfit = function (param) 
		{
			var startDate = $('#toolbar-outorder-profit #advanceSearchSpan #startDate').combo('getValue');
			var endDate = $('#toolbar-outorder-profit #advanceSearchSpan #endDate').combo('getValue');
			var customerId = $('#toolbar-outorder-profit #advanceSearchSpan #customerId').combo('getValue');
			var userCreate = $('#toolbar-outorder-profit #advanceSearchSpan #userCreate').combo('getValue');
			if(customerId == undefined) 
			{
				customerId = '';
			}
			if(startDate == '' && endDate == '' && customerId == '' && userCreate == '') 
			{
				var timeFrame = $('#toolbar-outorder-profit #advanceSearchSpan #timeFrame').combo('getValue');
				setInitialOrderProfitTimeFrame(timeFrame);
				
				startDate = $('#toolbar-outorder-profit #advanceSearchSpan #startDate').combo('getValue');
				endDate = $('#toolbar-outorder-profit #advanceSearchSpan #endDate').combo('getValue');
				
				$('#toolbar-outorder-profit #advanceSearchSpan #userCreate').combo('setValue', '${sessionScope.login_user.uid}');
				$('#toolbar-outorder-profit #advanceSearchSpan #userCreate').combo('setText', '${sessionScope.login_user.userId}');
				userCreate = $('#toolbar-outorder-profit #advanceSearchSpan #userCreate').combo('getValue');
			}
			
			if(param.page == undefined || param.page == 0) 
			{
				param.page = 1;
			}
			$('#dg-outorder-profit').datagrid('options').url = "<c:url value='/outOrder/getOrderProfit.html' />?startDate="+startDate+"&endDate="+endDate+"&customerId="+customerId+"&page="+param.page+"&userCreate="+userCreate;
			return true;
		}
		var setInitialOrderProfitTimeFrame = function(timeFrame) 
		{
			var startDate = generateStartDate(timeFrame);
			var endDate = new Date().format("yyyy-MM-dd");
			$('#toolbar-outorder-profit #advanceSearchSpan #startDate').datebox('setValue', startDate);
			$('#toolbar-outorder-profit #advanceSearchSpan #endDate').datebox('setValue', endDate);
		}
		var orderTooltipFormatter = function (value, row, index) 
		{
			if(value) 
			{
				return "<a href='javascript:void(0)' style='color:#000; font-weight:bold' id='"+row.id+"' title='点击查看明细'>" + value + "</a>";
			}
		}
		var onLoadOrderProfitSuccess = function(data) 
		{
			for(var i = 0; i < data.rows.length; i ++) 
			{
				showOrderDetail(data.rows[i].id);
			}
		}
		var showOrderDetail = function (orderId) 
		{
			//console.log(orderId);
			$('a#' + orderId).tooltip({
                content: $('<div></div>'),
                showEvent: 'click',
                onUpdate: function(content){
                    content.panel({
                        width: 500,
                        border: false,
                        noheader: true,
                        title: '订单明细',
						method: 'post',
						queryParams:{orderId:orderId, templateFile:'orderTableTemplate.html'},
                        href: '<c:url value='/outOrder/getOrderItemTable.html' />'
                    });
                },
                onShow: function(){
                    var t = $(this);
                    t.tooltip('tip').unbind().bind('mouseenter', function(){
                        t.tooltip('show');
                    }).bind('mouseleave', function(){
                        t.tooltip('hide');
                    });
                }
            });
		}
	</script>
