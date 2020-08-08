	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<div style="width:100%; height:430px;">
		<table id="dg-goodsIssue" title="<spring:message code="goodsIssue.goodsIssueManagement" />" class="easyui-datagrid" 
			toolbar="#toolbar-goodsIssue" pagination="false" rownumbers="true" singleSelect="true" checkOnSelect="true" selectOnCheck="false" 
			data-options="fitColumns:true, fit:true, onBeforeLoad:onBeforeLoadGoodsIssue">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'id',hidden:true"></th>
					<th field="action" width="15" data-options="formatter:formatter_updateIssueAction"></th>
					<th field="goods" width="100" data-options="formatter:goodsIssue_goods"><spring:message code="goodsIssue.goods" /></th>
					<th field="goodsSerial" width="60"><spring:message code="goodsIssue.goodsSerialNumber" /></th>
					<th field="customer" width="60" data-options="formatter:goodsIssue_customer"><spring:message code="goodsIssue.customer" /></th>
					<th field="provider" width="60" data-options="formatter:goodsIssue_provider"><spring:message code="goodsIssue.provider" /></th>
					<th field="createDate" width="60"><spring:message code="goodsIssue.createDate" /></th>
					<th field="signUser" width="60" data-options="formatter:goodsIssue_signUser"><spring:message code="goodsIssue.signUser" /></th>
					<th field="updateUser" width="60" data-options="formatter:goodsIssue_updateUser"><spring:message code="goodsIssue.updateUser" /></th>
					<th field="updateDate" width="60"><spring:message code="goodsIssue.updateDate" /></th>
					<th field="description" width="100"><spring:message code="goodsIssue.description" /></th>
					<th field="comment" width="100"><spring:message code="goodsIssue.comment" /></th>
					<th field="statusCode" width="60" data-options="formatter:formatter_updateIssueStatus"><spring:message code="goodsIssue.status" /></th>
				</tr>
			</thead>
		</table>
	</div>
		
	<div id="toolbar-goodsIssue" style="padding:5px;height:auto">
		<span>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" 
				onclick="newModel('#dlg-goodsIssue', '<spring:message code="goodsIssue.newGoodsIssue" />', '#fm-goodsIssue', '<c:url value='/goodsIssue/addModel.html' />', newGoodsIssueCallback)" title="<spring:message code="goodsIssue.newGoodsIssue" />"><spring:message code="goodsIssue.newGoodsIssue" /></a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" 
				onclick="destroyMultipleModel('#dg-goodsIssue', '<spring:message code="goodsIssue.goodsIssue" />', '<c:url value='/goodsIssue/deleteModels.html' />', saveGoodsIssueCallback)" title="<spring:message code="goodsIssue.removeGoodsIssue" />"><spring:message code="goodsIssue.removeGoodsIssue" /></a>
		</span>
		<%-- <span id="goodsSerialNumberSearchSpan" style="width: 25%; display: inline-block;">
			<form id="goodsSerialNumberSearchForm" action="">
            	<label><spring:message code="goodsIssue.goodsSerialNumber" />:</label> 
            	<input id="goodsSerialNumber" name="goodsSerialNumber" class="easyui-textbox" style="width:120px">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="onSearchGoodsIssueBySerialNumber()"><spring:message code="query" /></a>
			</form>
        </span> --%>
		<span id="advanceSearchSpan" style="margin-left: 50px;">
			<label><spring:message code="goodsIssue.goods" />:</label> 
			<input id="goodsId" name="goodsId" class="easyui-combobox" data-options="
				valueField:'id',
				textField:'name',
				panelHeight:'250',
				mode:'local', 
				filter: comboboxFilter,
				prompt:'输入名称查询', 
				formatter: goodsStorageFormatter,
				onShowPanel: onShowGoodsWhenIssue" />&nbsp;
					
			<label><spring:message code="goodsIssue.customer" />:</label> 
			<input id="customerId" name="customerId" class="easyui-combobox" data-options="
				valueField:'id',
				textField:'shortName',
				panelHeight:'250',
				prompt:'输入名称查询', 
				mode:'local', 
				filter: comboboxFilter,
				onShowPanel: onShowAllCustomer" />&nbsp;
			
			<label><spring:message code="goodsIssue.status" />:</label> 
			<select id="issueStatusCode" name="issueStatusCode" class="easyui-combobox" editable="false" panelHeight="auto">
				<option value="">全部</option>
				<option value="NEW" selected="selected"><spring:message code="goodsIssue.new" /></option>
				<option value="APPROVED"><spring:message code="goodsIssue.approved" /></option>
				<option value="PROCESSED"><spring:message code="goodsIssue.processed" /></option>
				<option value="COMPLETED"><spring:message code="goodsIssue.completed" /></option>
				<option value="INVALID"><spring:message code="goodsIssue.invalid" /></option>
			</select>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="onSearchGoodsIssueByAdvance()"><spring:message code="query" /></a>
		</span>
	</div>

	<div id="dlg-goodsIssue" class="easyui-dialog"
		style="width: 530px; height: 370px; padding: 15px 5px" closed="true"
		buttons="#dlg-buttons-goodsIssue" data-options="modal:true">
		<div style="width:100%;height:100%">
			<form id="fm-goodsIssue" class="fm" method="post" style="margin:0 auto;padding:0px;width:100%;height:100%;" novalidate>
				<div class="fitem divHidden">
					<input id="issueId" name="id" value="0">
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label><spring:message code="goodsIssue.goods" /><span class="iconSpan16 required" />:</label> 
						<input id="goodsId" name="goodsBean.id" class="easyui-combobox" required="true" 
							data-options="valueField:'id',
								textField:'name',
								panelHeight:'250',
								mode:'local', 
								filter: comboboxFilter,
								prompt:'输入名称查询', 
								formatter: goodsStorageFormatter,
								onShowPanel: onShowGoodsWhenIssue" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label><spring:message code="goodsIssue.goodsSerialNumber" />:</label> 
						<input id="goodsSerial" name="goodsSerial" class="easyui-textbox"  
							validType="myRemote['<c:url value='/goodsIssue/checkExist.html' />', 'goodsSerial', '#issueId']" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label><spring:message code="goodsIssue.customer" />:</label> 
						<input id="customerId" name="customerBean.id" class="easyui-combobox" data-options="
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
						<label><spring:message code="goodsIssue.provider" />:</label> 
						<input id="providerId" name="providerBean.id" class="easyui-combobox" data-options="
							valueField:'id',
							textField:'shortName',
							panelHeight:'250',
							prompt:'输入名称查询', 
							mode:'local', 
							filter: comboboxFilter,
							onShowPanel: onShowAllProvider" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label><spring:message code="goodsIssue.signUser" />:</label> 
						<input id="signUser" name="signUser.id" class="easyui-combobox" editable="false" data-options="
							valueField:'id',
							textField:'userId',
							panelHeight:'250',
							onShowPanel: onShowUserPanel" />
					</div>
				</div>
				<div class="fitem divHidden">
					<label><spring:message code="goodsIssue.status" />:</label> 
					<select id="issueStatus" name="statusCode" class="easyui-combobox" editable="false" panelHeight="auto">
						<option value="NEW"><spring:message code="goodsIssue.new" /></option>
						<option value="APPROVED"><spring:message code="goodsIssue.approved" /></option>
						<option value="PROCESSED"><spring:message code="goodsIssue.processed" /></option>
						<option value="COMPLETED"><spring:message code="goodsIssue.completed" /></option>
						<option value="INVALID"><spring:message code="goodsIssue.invalid" /></option>
					</select>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label><spring:message code="goodsIssue.date" />:</label> 
						<input id="createDate" name="createDate" class="easyui-datebox" editable="false" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3" style="width:100%">
					<div style="width:410px">
						<label><spring:message code="goodsIssue.description" />:</label> 
						<input id="description" name="description" class="easyui-textbox" data-options="multiline:true" style="width:100%;height:50px" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="dlg-buttons-goodsIssue">
		<a id="save-goodsIssue" href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="saveModel('#dg-goodsIssue', '#dlg-goodsIssue', '#fm-goodsIssue', saveGoodsIssueCallback)"><spring:message code="save" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg-goodsIssue').dialog('close')"><spring:message code="cancel" /></a>
	</div>
	
	<!-- 编辑问题货物状态 -->
	<div id="dlg-goodsIssue-updateStatus" class="easyui-dialog"
		style="width: 530px; height: 370px; padding: 15px 5px" closed="true"
		buttons="#dlg-buttons-goodsIssue-updateStatus" data-options="modal:true">
		<div style="width:100%;height:100%">
			<form id="fm-goodsIssue-updateStatus" class="fm" method="post" style="margin:0 auto;padding:0px;width:100%;height:100%;" novalidate>
				<div class="fitem divHidden">
					<input id="issueId" name="id" value="0">
					<input id="createDate" name="createDate" />
					<input id="description" name="description" />
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label><spring:message code="goodsIssue.goods" />:</label> 
						<input id="goodsId" name="goodsBean.id" type="hidden" readonly="readonly" style="border: none" />
						<input id="goodsName" readonly="readonly" style="border: none" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label><spring:message code="goodsIssue.goodsSerialNumber" />:</label> 
						<input id="goodsSerial" name="goodsSerial" readonly="readonly" style="border: none" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label><spring:message code="goodsIssue.customer" />:</label> 
						<input id="customerId" name="customerBean.id" type="hidden" readonly="readonly" style="border: none" />
						<input id="customerName" readonly="readonly" style="border: none" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label><spring:message code="goodsIssue.provider" />:</label> 
						<input id="providerId" name="providerBean.id" type="hidden" readonly="readonly" style="border: none" />
						<input id="providerName" readonly="readonly" style="border: none" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label><spring:message code="goodsIssue.signUser" />:</label> 
						<input id="signUserId" name="signUser.id" type="hidden" readonly="readonly" style="border: none" />
						<input id="signUser"  readonly="readonly" style="border: none" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label><spring:message code="goodsIssue.status" />:</label> 
						<select id="issueStatus" name="statusCode" class="easyui-combobox" editable="false" panelHeight="auto" style="width:150px;">
							<option value="NEW" selected="selected"><spring:message code="goodsIssue.new" /></option>
							<option value="APPROVED"><spring:message code="goodsIssue.approved" /></option>
							<option value="PROCESSED"><spring:message code="goodsIssue.processed" /></option>
							<option value="COMPLETED"><spring:message code="goodsIssue.completed" /></option>
							<option value="INVALID"><spring:message code="goodsIssue.invalid" /></option>
						</select>
					</div>
				</div>
				<div class="fitem css-dlg-formField3" style="width:100%">
					<div style="width:410px">
						<label><spring:message code="goodsIssue.comment" />:</label> 
						<input id="comment" name="comment" class="easyui-textbox" data-options="multiline:true" style="width:100%;height:50px" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="dlg-buttons-goodsIssue-updateStatus">
		<a id="save-goodsIssue-updateStatus" href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="saveModel('#dg-goodsIssue', '#dlg-goodsIssue-updateStatus', '#fm-goodsIssue-updateStatus', saveGoodsIssueCallback)"><spring:message code="save" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg-goodsIssue-updateStatus').dialog('close')"><spring:message code="cancel" /></a>
	</div>
	<!-- 编辑问题货物状态 -->
	
	<script type="text/javascript">
		var newGoodsIssueCallback = function() 
		{
			var createDate = new Date().format("yyyy-MM-dd");
			$('#dlg-goodsIssue #fm-goodsIssue #createDate').datebox('setValue', createDate);
		}
		var editGoodsIssueCallback = function() 
		{
			var goodsIssue = $('#dg-goodsIssue').datagrid('getSelected');
			if(goodsIssue) 
			{
				if(goodsIssue.goodsBean) 
				{
					$('#dlg-goodsIssue #fm-goodsIssue #goodsId').combobox('setValue', goodsIssue.goodsBean.id);
					$('#dlg-goodsIssue #fm-goodsIssue #goodsId').combobox('setText', goodsIssue.goodsBean.name);
				}
				if(goodsIssue.customerBean) 
				{
					$('#dlg-goodsIssue #fm-goodsIssue #customerId').combobox('setValue', goodsIssue.customerBean.id);
					$('#dlg-goodsIssue #fm-goodsIssue #customerId').combobox('setText', goodsIssue.customerBean.shortName);
				}
				if(goodsIssue.providerBean) 
				{
					$('#dlg-goodsIssue #fm-goodsIssue #providerId').combobox('setValue', goodsIssue.providerBean.id);
					$('#dlg-goodsIssue #fm-goodsIssue #providerId').combobox('setText', goodsIssue.providerBean.shortName);
				}
				if(goodsIssue.signUser) 
				{
					$('#dlg-goodsIssue #fm-goodsIssue #signUser').combobox('setValue', goodsIssue.signUser.id);
					$('#dlg-goodsIssue #fm-goodsIssue #signUser').combobox('setText', goodsIssue.signUser.userId);
				}
			}
		}
		
		var goodsIssue_goods = function(value,row,index) 
		{
			if(row.goodsBean) 
			{
				return row.goodsBean.name;
			}
		}
		var goodsIssue_customer = function(value,row,index) 
		{
			if(row.customerBean) 
			{
				return row.customerBean.shortName;
			}
		}
		var goodsIssue_provider = function(value,row,index) 
		{
			if(row.providerBean) 
			{
				return row.providerBean.shortName;
			}
		}
		var goodsIssue_signUser = function(value,row,index) 
		{
			if(row.signUser) 
			{
				return row.signUser.userId;
			}
		}
		var goodsIssue_updateUser = function(value,row,index) 
		{
			if(row.updateUser) 
			{
				return row.updateUser.userId;
			}
		}
		function formatter_updateIssueStatus (value,row,index) 
		{
			if('NEW' == row.statusCode) 
			{
				return '<a href="#" style="color:#000; font-weight:bold" title="编辑状态" onclick="updateIssueStatus('+index+')"><spring:message code="goodsIssue.new" /></a>';
			}
			else if('APPROVED' == row.statusCode) 
			{
				return '<a href="#" style="color:#000; font-weight:bold" title="编辑状态" onclick="updateIssueStatus('+index+')"><spring:message code="goodsIssue.approved" /></a>';
			}
			else if('PROCESSED' == row.statusCode) 
			{
				return '<a href="#" style="color:#000; font-weight:bold" title="编辑状态" onclick="updateIssueStatus('+index+')"><spring:message code="goodsIssue.processed" /></a>';
			}
			else if('COMPLETED' == value) 
			{
				return "<span class='icon-ok' style='display:block;' title='完成的，不可编辑状态'>&nbsp;</span>";
			}
			else if('INVALID' == value) 
			{
				return "<span class='icon-cancel' style='display:block;' title='无效的，不可编辑状态'>&nbsp;</span>";
			}
		}
		function formatter_updateIssueAction (value,row,index) 
		{
			if('NEW' == row.statusCode || 'APPROVED' == row.statusCode || 'PROCESSED' == row.statusCode) 
			{
				return '<a href="#" class="icon-edit" style="display:inline-block;width:16px;" title="<spring:message code="goodsIssue.editGoodsIssue" />" onclick="updateIssue('+index+')">&nbsp;</a>';
			}
		}
		function updateIssue(rowIndex) 
		{
			$('#dg-goodsIssue').datagrid('selectRow', rowIndex);
			var row = $('#dg-goodsIssue').datagrid('getSelected');
			if(row) 
			{
				editModel('#dg-goodsIssue', '#dlg-goodsIssue', '<spring:message code="goodsIssue.editGoodsIssue" />', '#fm-goodsIssue', '<c:url value='/goodsIssue/updateModel.html' />', editGoodsIssueCallback);
			}
		}
		function updateIssueStatus(rowIndex) 
		{
			$('#dg-goodsIssue').datagrid('selectRow', rowIndex);
			var row = $('#dg-goodsIssue').datagrid('getSelected');
			if(row) 
			{
				editModel('#dg-goodsIssue', '#dlg-goodsIssue-updateStatus', '编辑返修货物状态', '#fm-goodsIssue-updateStatus', '<c:url value='/goodsIssue/updateModel.html' />');
				$('#dlg-goodsIssue-updateStatus #fm-goodsIssue-updateStatus #goodsId').val(row.goodsBean.id);
				$('#dlg-goodsIssue-updateStatus #fm-goodsIssue-updateStatus #goodsName').val(row.goodsBean.name);
				if(row.customerBean) 
				{
					$('#dlg-goodsIssue-updateStatus #fm-goodsIssue-updateStatus #customerId').val(row.customerBean.id);
					$('#dlg-goodsIssue-updateStatus #fm-goodsIssue-updateStatus #customerName').val(row.customerBean.shortName);
				}
				if(row.providerBean) 
				{
					$('#dlg-goodsIssue-updateStatus #fm-goodsIssue-updateStatus #providerId').val(row.providerBean.id);
					$('#dlg-goodsIssue-updateStatus #fm-goodsIssue-updateStatus #providerName').val(row.providerBean.shortName);
				}
				if(row.signUser) 
				{
					$('#dlg-goodsIssue-updateStatus #fm-goodsIssue-updateStatus #signUserId').val(row.signUser.id);
					$('#dlg-goodsIssue-updateStatus #fm-goodsIssue-updateStatus #signUser').val(row.signUser.userId);
				}
				$('#dlg-goodsIssue-updateStatus #fm-goodsIssue-updateStatus #createDate').val(row.createDate);
				$('#dlg-goodsIssue-updateStatus #fm-goodsIssue-updateStatus #description').val(row.description);
			}
		}
		
		var onBeforeLoadGoodsIssue = function (param) 
		{
			//var goodsSerialNumber = $('#toolbar-goodsIssue #goodsSerialNumberSearchSpan #goodsSerialNumber').textbox('getValue');
			var goodsId = $('#toolbar-goodsIssue #advanceSearchSpan #goodsId').combo('getValue');
			var customerId = $('#toolbar-goodsIssue #advanceSearchSpan #customerId').combo('getValue');
			var issueStatusCode = $('#toolbar-goodsIssue #advanceSearchSpan #issueStatusCode').combo('getValue');
			if(customerId == undefined) 
			{
				customerId = '';
			}
			
			$('#dg-goodsIssue').datagrid('options').url = "<c:url value='/goodsIssue/getModelBySearchForm.html' />?goodsId="+goodsId+"&customerId="+customerId+"&issueStatusCode="+issueStatusCode+"&goodsSerialNumber=";
			return true;
		}
		var onSearchGoodsIssueByAdvance = function () 
		{
			//$('#toolbar-goodsIssue #goodsSerialNumberSearchSpan #goodsSerialNumber').textbox('setValue', '');
			var goodsId = $('#toolbar-goodsIssue #advanceSearchSpan #goodsId').combo('getValue');
			var customerId = $('#toolbar-goodsIssue #advanceSearchSpan #customerId').combo('getValue');
			var issueStatusCode = $('#toolbar-goodsIssue #advanceSearchSpan #issueStatusCode').combo('getValue');
			
			$('#dg-goodsIssue').datagrid('reload');
		}
		var onSearchGoodsIssueBySerialNumber = function () 
		{
			var goodsSerialNumber = $('#toolbar-goodsIssue #goodsSerialNumberSearchSpan #goodsSerialNumber').textbox('getValue');
			if(goodsSerialNumber == '') 
			{
				$.messager.alert('警告','请填写货物序列号!','warning');
				return;
			}
			$('#dg-goodsIssue').datagrid('reload');
		}
		var saveGoodsIssueCallback = function() 
		{
			$('#dg-goodsIssue').datagrid('reload');
		}
		var onShowGoodsWhenIssue = function(){
			$(this).combobox('reload', '<c:url value='/goods/getGoodsByTypeOrDepository.html' />?goodsTypeId='+0+'&goodsDepositoryId=');
		}
	</script>
