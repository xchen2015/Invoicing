	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<div style="width:100%; height:430px;">
		<table id="dg-goodsSerial" title="<spring:message code="goodsSerial.goodsSerialManagement" />" class="easyui-datagrid"
			toolbar="#toolbar-goodsSerial" pagination="false" rownumbers="true" 
			 singleSelect="true" checkOnSelect="true" selectOnCheck="false"
			data-options="fitColumns:true, fit:true, onBeforeLoad:onBeforeLoadGoodsSerialNumber">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'id',hidden:true"></th>
					<th field="goods" width="100" data-options="formatter:goodsSerial_goods"><spring:message code="goodsSerial.goods" /></th>
					<th field="serialNumber" width="100"><spring:message code="goodsSerial.goodsSerialNumber" /></th>
					<th field="provider" width="100" data-options="formatter:goodsSerial_provider"><spring:message code="goodsSerial.provider" /></th>
					<th field="inDate" width="100" data-options="formatter:goodsSerial_inDate"><spring:message code="goodsSerial.inDate" /></th>
					<th field="customer" width="100" data-options="formatter:goodsSerial_customer"><spring:message code="goodsSerial.customer" /></th>
					<th field="outDate" width="100" data-options="formatter:goodsSerial_outDate"><spring:message code="goodsSerial.outDate" /></th>
				</tr>
			</thead>
		</table>
	</div>
		
	<div id="toolbar-goodsSerial" style="padding:5px;height:auto">
		<span>
			<a href="javascript:void(0)" class="easyui-linkbutton" 
				iconCls="icon-add" plain="true" onclick="newModel('#dlg-goodsSerial', '<spring:message code="goodsSerial.newGoodsSerial" />', '#fm-goodsSerial', '<c:url value='/goodsSerialNumber/addModel.html' />')" title="<spring:message code="goodsSerial.newGoodsSerial" />"><spring:message code="goodsSerial.newGoodsSerial" /></a> 
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editModel('#dg-goodsSerial', '#dlg-goodsSerial', '<spring:message code="goodsSerial.editGoodsSerial" />', '#fm-goodsSerial', '<c:url value='/goodsSerialNumber/updateModel.html' />', editGoodsSerialNumberCallback)" title="<spring:message code="goodsSerial.editGoodsSerial" />"><spring:message code="goodsSerial.editGoodsSerial" /></a> 
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true" onclick="destroyMultipleModel('#dg-goodsSerial', '<spring:message code="goodsSerial.goodsSerialNumber" />', '<c:url value='/goodsSerialNumber/deleteModels.html' />', function() {loadGridData ('#dg-goodsSerial', '<c:url value='/goodsSerialNumber/getModelBySearchForm.html' />')})" title="<spring:message code="goodsSerial.removeGoodsSerial" />"><spring:message code="goodsSerial.removeGoodsSerial" /></a>
		</span>
		<span style="margin-left: 50px;">
			<label><spring:message code="goods.type" />:</label> 
			<input id="goodsTypeId" name="goodsTypeId" class="easyui-combotree" style="width:200px" 
				data-options="panelHeight:400, formatter:goodsTypeWithGoodsAmountFormatter, 
					onShowPanel:showAllGoodsType, onChange:onChangeGoodsType_viewSerialNumber" />
			&nbsp;
			<label>货物:</label> 
			<input id="goodsId" name="goodsId" class="easyui-combobox" data-options="
				valueField:'id',
				textField:'name',
				panelHeight:'250',
				mode:'local', 
				filter: comboboxFilter,
				prompt:'输入名称查询', 
				formatter: goodsStorageFormatter,
				onShowPanel: onShowGoodsWhenSerialNumber" />
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="" onclick="onSearchGoodsIssueByGoods()">查询</a>
		</span>
	</div>

	<div id="dlg-goodsSerial" class="easyui-dialog"
		style="width: 340px; height: 370px; padding: 15px 5px" closed="true"
		buttons="#dlg-buttons-goodsSerial" data-options="modal:true">
		<div style="width:100%;height:100%">
			<form id="fm-goodsSerial" class="fm" method="post" style="margin:0 auto;padding:0px;width:100%;height:100%;" novalidate>
				<div class="fitem divHidden">
					<input id="serialNumberId" name="id" value="0">
				</div>
				<div class="fitem css-dlg-formField2">
					<div>
						<label>货物<span class="iconSpan16 required" />:</label> 
						<input id="goodsId" name="goodsBean.id" class="easyui-combobox" required="true" 
							data-options="valueField:'id',
								textField:'name',
								panelHeight:'250',
								mode:'local', 
								filter: comboboxFilter,
								prompt:'输入名称查询', 
								formatter: goodsStorageFormatter,
								onShowPanel: onShowGoodsWhenSerialNumber2,
								onChange: onChangeGoods_viewOrderItem" />
					</div>
				</div>
				<div class="fitem css-dlg-formField2">
					<div>
						<label>序列号<span class="iconSpan16 required" />:</label> 
						<input id="serialNumber" name="serialNumber" class="easyui-textbox" required="true" data-options="prompt:'<spring:message code="requiredFieldAndUnique" />'" 
							validType="myRemote['<c:url value='/goodsSerialNumber/checkExist.html' />', 'serialNumber', '#serialNumberId']" />
					</div>
				</div>
				<div class="fitem css-dlg-formField2">
					<div>
						<label>入库项:</label> 
						<input id="orderInItemId" name="orderInItemBean.id" editable="false" class="easyui-combobox"  
							data-options="valueField:'id',textField:'customerName',panelHeight:'250',method:'post',prompt:'请先选择货物', onShowPanel:showOrderInItemPanel, formatter:formatter_customerName" />
					</div>
				</div>
				<div class="fitem css-dlg-formField2">
					<div>
						<label>出库项:</label> 
						<input id="orderOutItemId" name="orderOutItemBean.id" editable="false" class="easyui-combobox"  
							data-options="valueField:'id',textField:'customerName',panelHeight:'250',method:'post',prompt:'请先选择货物', onShowPanel:showOrderOutItemPanel, formatter:formatter_customerName" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="dlg-buttons-goodsSerial">
		<a id="save-goodsSerial" href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="saveModel('#dg-goodsSerial', '#dlg-goodsSerial', '#fm-goodsSerial', function() {loadGridData ('#dg-goodsSerial', '<c:url value='/goodsSerialNumber/getModelBySearchForm.html' />')})"><spring:message code="save" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg-goodsSerial').dialog('close')"><spring:message code="cancel" /></a>
	</div>
	
	<script type="text/javascript">
		var editGoodsSerialNumberCallback = function() 
		{
			var goodsSerial = $('#dg-goodsSerial').datagrid('getSelected');
			if(goodsSerial) 
			{
				if(goodsSerial.goodsBean) 
				{
					$('#dlg-goodsSerial #fm-goodsSerial #goodsId').combobox('setValue', goodsSerial.goodsBean.id);
					$('#dlg-goodsSerial #fm-goodsSerial #goodsId').combobox('setText', goodsSerial.goodsBean.name);
				}
				if(goodsSerial.orderInItemBean) 
				{
					$('#dlg-goodsSerial #fm-goodsSerial #orderInItemId').combobox('setValue', goodsSerial.orderInItemBean.id);
					$('#dlg-goodsSerial #fm-goodsSerial #orderInItemId').combobox('setText', goodsSerial.orderInItemBean.customerName);
				}
				if(goodsSerial.orderOutItemBean) 
				{
					$('#dlg-goodsSerial #fm-goodsSerial #orderOutItemId').combobox('setValue', goodsSerial.orderOutItemBean.id);
					$('#dlg-goodsSerial #fm-goodsSerial #orderOutItemId').combobox('setText', goodsSerial.orderOutItemBean.customerName);
				}
			}
		}
		
		var showOrderInItemPanel = function() 
		{
			var goodsId = $('#dlg-goodsSerial #fm-goodsSerial #goodsId').combobox('getValue');
			var url = '<c:url value='/inOrder/getOrderItemsByGoods.html' />?goodsId=' + goodsId;
			$(this).combobox('reload', url);
		}
		
		var showOrderOutItemPanel = function() 
		{
			var goodsId = $('#dlg-goodsSerial #fm-goodsSerial #goodsId').combobox('getValue');
			var url = '<c:url value='/outOrder/getOrderItemsByGoods.html' />?goodsId=' + goodsId;
			$(this).combobox('reload', url);
		}
		
		var goodsSerial_goods = function(value,row,index) 
		{
			if(row.goodsBean) 
			{
				return row.goodsBean.name;
			}
		}
		var goodsSerial_provider = function(value,row,index) 
		{
			if(row.orderInItemBean) 
			{
				return row.orderInItemBean.customerName;
			}
		}
		var goodsSerial_inDate = function(value,row,index) 
		{
			if(row.orderInItemBean) 
			{
				return row.orderInItemBean.orderCreate;
			}
		}
		var goodsSerial_customer = function(value,row,index) 
		{
			if(row.orderOutItemBean) 
			{
				return row.orderOutItemBean.customerName;
			}
		}
		var goodsSerial_outDate = function(value,row,index) 
		{
			if(row.orderOutItemBean) 
			{
				return row.orderOutItemBean.orderCreate;
			}
		}
		var formatter_customerName = function(row) 
		{
			if(row) 
			{
				var s = '<span style="font-weight:bold">' + row.customerName + '</span><span>(' + row.orderCreate + ')</span><br/>' +
				'<span>' + row.goodsName + ' - <b>' + row.amount + '</b>' + row.goodsUnit + '</span>';
				return s;
			}
		}
		
		var onBeforeLoadGoodsSerialNumber = function() 
		{
			var goodsId = $('#toolbar-goodsSerial #goodsId').combo('getValue');
			if(goodsId == undefined) 
			{
				goodsId = '';
			}
			
			$('#dg-goodsSerial').datagrid('options').url = "<c:url value='/goodsSerialNumber/getModelBySearchForm.html' />?goodsId="+goodsId;
			return true;
		}
		var onSearchGoodsIssueByGoods = function () 
		{
			$('#dg-goodsSerial').datagrid('reload');
		}
		var onChangeGoodsType_viewSerialNumber = function(newValue, oldValue) 
		{
			$('#toolbar-goodsSerial #goodsId').combobox('setValue', '');
			$('#toolbar-goodsSerial #goodsId').combobox('loadData', []);
		}
		var onShowGoodsWhenSerialNumber = function(){
			var goodsTypeId = $('#toolbar-goodsSerial #goodsTypeId').combotree('getValue');
			/*if(goodsTypeId == '') 
			{
				$.messager.alert('警告','请先选择货物类型.','warning');
				return;
			}*/
			$(this).combobox('reload', '<c:url value='/goods/getGoodsByTypeOrDepository.html' />?goodsTypeId='+goodsTypeId+'&goodsDepositoryId=');
		}
		var onShowGoodsWhenSerialNumber2 = function(){
			$(this).combobox('reload', '<c:url value='/goods/getGoodsByTypeOrDepository.html' />?goodsTypeId='+0+'&goodsDepositoryId=');
		}
		var onChangeGoods_viewOrderItem = function(newValue, oldValue) 
		{
			$('#dlg-goodsSerial #fm-goodsSerial #orderInItemId').combobox('setValue', '');
			$('#dlg-goodsSerial #fm-goodsSerial #orderInItemId').combobox('loadData', []);
			$('#dlg-goodsSerial #fm-goodsSerial #orderOutItemId').combobox('setValue', '');
			$('#dlg-goodsSerial #fm-goodsSerial #orderOutItemId').combobox('loadData', []);
		}
	</script>
