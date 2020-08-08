	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<div style="width:100%; height:430px;">
		<table id="dg-contract" title="<spring:message code="contract.contractManagement" />" class="easyui-datagrid" 
			 url="<c:url value='/contract/getModelBySearchForm.html' />"
			toolbar="#toolbar-contract" pagination="false" rownumbers="true" 
			 singleSelect="true" checkOnSelect="true" selectOnCheck="false"
			data-options="fitColumns:true, fit:true, onBeforeLoad:onBeforeLoadContract">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'id',hidden:true"></th>
					<th field="action" width="10" data-options="formatter:formatter_updateContract"></th>
					<th field="name" width="100" data-options=""><spring:message code="contract.name" /></th>
					<th field="source" width="60" data-options="hidden:true"><spring:message code="contract.source" /></th>
					<th field="sourceName" width="60"><spring:message code="contract.source" /></th>
					<th field="dealMoney" width="60" data-options=""><spring:message code="contract.dealMoney" /></th>
					<th field="dateCreated" width="60" data-options=""><spring:message code="contract.dateCreated" /></th>
					<th field="userCreatedBy" width="60"><spring:message code="contract.userCreated" /></th>
					<th field="typeCode" width="60" data-options="formatter:formatter_contractType"><spring:message code="contract.type" /></th>
					<th field="comment" width="100" data-options=""><spring:message code="contract.comment" /></th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="toolbar-contract" style="padding:5px;height:auto">
		<span>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" 
				onclick="newModel('#dlg-contract', '<spring:message code="contract.newContract" />', '#fm-contract', '<c:url value='/contract/addModel.html' />', newContractCallback)" title="<spring:message code="contract.newContract" />"><spring:message code="contract.newContract" /></a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" 
				onclick="destroyMultipleModel('#dg-contract', '<spring:message code="contract.contract" />', '<c:url value='/contract/deleteModels.html' />', function() {loadGridData ('#dg-contract', '<c:url value='/contract/getModelBySearchForm.html' />')})" title="<spring:message code="contract.removeContract" />"><spring:message code="contract.removeContract" /></a>
		</span>
		<span style="float: right; margin-right: 5px"> 
			<input class="easyui-searchbox" style="" 
				data-options="prompt:'<spring:message code="pleaseInputValue" />',searcher:doSearchContract" />
		</span>
	</div>
	
	<div id="dlg-contract" class="easyui-dialog"
		style="width: 530px; height: 370px; padding: 15px 5px" closed="true"
		buttons="#dlg-buttons-contract" data-options="modal:true">
		<div style="width:100%;height:100%">
			<form id="fm-contract" class="fm" method="post" style="margin:0 auto;padding:0px;width:100%;height:100%;" novalidate>
				<div class="fitem divHidden">
					<input id="contractId" name="id" value="0">
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label><spring:message code="contract.name" /><span class="iconSpan16 required" />:</label> 
						<input id="contractName" name="name" class="easyui-textbox" required="true" data-options="prompt:'<spring:message code="requiredFieldAndUnique" />'" 
							validType="myRemote['<c:url value='/contract/checkExist.html' />', 'contractName', '#contractId']" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label><spring:message code="contract.type" /><span class="iconSpan16 required" />:</label> 
						<select id="contracctType" name="typeCode" class="easyui-combobox" editable="false" panelHeight="auto" required="true" data-options="onSelect:contract_onSelectType">
							<option value="PROJECT"><spring:message code="contract.type.project" /></option>
							<option value="ORDER_IN"><spring:message code="contract.type.orderIn" /></option>
							<option value="ORDER_OUT"><spring:message code="contract.type.orderOut" /></option>
						</select>
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label><spring:message code="contract.source" />:</label> 
						<input id="contractSource" name="source" editable="false" class="easyui-combobox" required="true" 
							data-options="valueField:'id',textField:'shortName',panelHeight:'250',prompt:'请先选择类型', onShowPanel:showContractPartyPanel" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label><spring:message code="contract.dealMoney" />:</label> 
						<input id="contractDealMoney" name="dealMoney" class="easyui-numberbox" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3">
					<div>
						<label><spring:message code="contract.dateCreated" />:</label> 
						<input id="contractDateCreated" name="dateCreated" class="easyui-datebox" editable="false" />
					</div>
				</div>
				<div class="fitem css-dlg-formField3" style="width:100%">
					<div style="width:410px">
						<label><spring:message code="contract.comment" />:</label> 
						<input id="contractComment" name="comment" class="easyui-textbox" data-options="multiline:true" style="width:100%;height:80px" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="dlg-buttons-contract">
		<a id="save-contract" href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="saveModel('#dg-contract', '#dlg-contract', '#fm-contract', function() {loadGridData ('#dg-contract', '<c:url value='/contract/getModelBySearchForm.html' />')})"><spring:message code="save" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg-contract').dialog('close')"><spring:message code="cancel" /></a>
	</div>
	
	<script type="text/javascript">
<!--
	function onBeforeLoadContract () {}
	function newContractCallback () 
	{
		var createDate = new Date().format("yyyy-MM-dd");
		$('#dlg-contract #fm-contract #contractDateCreated').datebox('setValue', createDate);
		$('#dlg-contract #fm-contract #contractDealMoney').numberbox('setValue', 0);
		$('#dlg-contract #fm-contract #contractSource').combobox('disable');
	}
	function editContractCallback () 
	{
		var row = $('#dg-contract').datagrid('getSelected');
		if(row) 
		{
			$('#dlg-contract #fm-contract #contractSource').combobox('enable');
			$('#dlg-contract #fm-contract #contractSource').combobox('setValue', row.source);
			$('#dlg-contract #fm-contract #contractSource').combobox('setText', row.sourceName);
		}
	}
	function doSearchContract () {}
	function showContractPartyPanel () 
	{
		var contractType = $('#dlg-contract #fm-contract #contracctType').combobox('getValue');
		var url = '';
		if('ORDER_OUT' == contractType || 'PROJECT' == contractType) 
		{
			url = '<c:url value='/customer/getAllModel.html' />';
		}
		else 
		{
			url = '<c:url value='/provider/getAllModel.html' />';
		}
		$(this).combobox('reload', url);
	}
	function formatter_contractType (value,row,index) 
	{
		if('PROJECT' == value) 
		{
			return '<spring:message code="contract.type.project" />';
		}
		else if('ORDER_IN' == value) 
		{
			return '<spring:message code="contract.type.orderIn" />';
		}
		else if('ORDER_OUT' == value) 
		{
			return '<spring:message code="contract.type.orderOut" />';
		}
		return '';
	}
	function formatter_updateContract (value,row,index) 
	{
		return '<a href="#" class="icon-edit" style="display:inline-block;width:16px;" title="<spring:message code="contract.editContract" />" onclick="updateContract('+index+')">&nbsp;</a>';
	}
	function updateContract(rowIndex) 
	{
		$('#dg-contract').datagrid('selectRow', rowIndex);
		var row = $('#dg-contract').datagrid('getSelected');
		if(row) 
		{
			editModel('#dg-contract', '#dlg-contract', '<spring:message code="contract.editContract" />', '#fm-contract', '<c:url value='/contract/updateModel.html' />', editContractCallback);
		}
	}
	function contract_onSelectType(record) 
	{
		if(record) 
		{
			$('#dlg-contract #fm-contract #contractSource').combobox('enable');
		}
		else 
		{
			$('#dlg-contract #fm-contract #contractSource').combobox('disable');
		}
	}
//-->
	</script>
