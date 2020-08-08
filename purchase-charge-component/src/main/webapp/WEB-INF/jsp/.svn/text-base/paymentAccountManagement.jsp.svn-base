	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<div id="dlg-manage-paymentAccount" class="easyui-dialog" title="<spring:message code="payment.paymentAccountManagement" />" 
		style="width: 800px; height: 500px; padding: 5px;" closed="true"
		buttons="#dlg-buttons-manage-paymentAccount" data-options="modal:true">
		<table id="dg-paymentAccount" class="easyui-datagrid" toolbar="#toolbar-paymentAccount" 
			 fit="true" pagination="false" rownumbers="true" singleSelect="true" checkOnSelect="true" selectOnCheck="false" 
			 data-options="fitColumns:true, onBeforeEdit:paymentAccount_onBeforeEdit, 
				onAfterEdit:paymentAccount_onAfterEdit, onCancelEdit:paymentAccount_onCancelEdit, onBeforeLoad:onBeforeLoadPaymentAccount">
			<thead>
				<tr>
					<th field="id" hidden="true"><spring:message code="id" /></th>
					<th field="name" width="100" data-options="editor:{type:'validatebox', options:{required:true, validType:'checkPaymentAccountNameExist[]'}}"><spring:message code="name" /></th>
					<th field="accountId" width="100" data-options="editor:{type:'validatebox', options:{required:true, validType:'checkPaymentAccountIdExist[]'}}">账号</th>
					<th field="accountMode" width="100" sortable="true" 
						data-options="
							formatter:formatter_accountMode,
							editor:{
	                           type:'combobox',
	                           options:{
	                               valueField:'value',
	                               textField:'label',
	                               editable:false,
								   panelHeight: 'auto',
	                               data:[{label:'现金',value:'CASH'},{label:'储蓄存款',value:'DEPOSIT'}],
	                               required:true
	                           }
	                       }">类型</th>
					<th field="remainMoney" width="100" data-options="editor:{type:'numberbox',options:{precision:2}}">余额</th>
					<th field="action" width="50" data-options="formatter:formatter_paymentAccountAction"><spring:message code="action" /></th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="toolbar-paymentAccount">
		<a href="javascript:void(0)" class="easyui-linkbutton" 
			iconCls="icon-add" plain="true" onclick="insertPaymentAccount()" title="新增账号">新增账号</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-reload" plain="true" onclick="reloadPaymentAccount()" title="<spring:message code="refresh" />"><spring:message code="refresh" /></a>
	</div>
	<div id="dlg-buttons-manage-paymentAccount">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg-manage-paymentAccount').dialog('close')"><spring:message code="close" /></a>
	</div>
	
	<script type="text/javascript">
		function reloadPaymentAccount() 
		{
			$('#dlg-manage-paymentAccount #dg-paymentAccount').datagrid('reload');
		}
		function onBeforeLoadPaymentAccount() 
		{
			paymentAccount_editingIndex = undefined;
			paymentAccount_rowId = undefined;
			$('#dlg-manage-paymentAccount #dg-paymentAccount').datagrid('options').url = "<c:url value='/paymentAccount/getAllModel.html' />";
			return true;
		}
		function formatter_paymentAccountAction (value,row,index) 
		{
			if (row.editing){
				if(row.id == '') 
				{
					var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="savePaymentAccount(this)">&nbsp;</a>&nbsp;&nbsp;';
					var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deletePaymentAccount(this)">&nbsp;</a>';
					return s+d;
				}
				else 
				{
					var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="savePaymentAccount(this,' + row.id + ')">&nbsp;</a>&nbsp;&nbsp;';
					var c = '<a href="#" class="icon-no" style="display:inline-block;width:16px;" title="取消" onclick="cancelPaymentAccount(this)">&nbsp;</a>';
					return s+c;
				}
			} else {
				var e = '<a href="#" class="icon-edit" style="display:inline-block;width:16px;" title="编辑" onclick="editPaymentAccount(this)">&nbsp;</a>&nbsp;&nbsp;';
				var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deletePaymentAccount(this,' + row.id + ')">&nbsp;</a>';
				return e+d;
			}
		}
		var paymentAccount_editingIndex;
		var paymentAccount_rowId;
		var paymentAccount_onBeforeEdit = function(index,row){
	        row.editing = true;
			paymentAccount_editingIndex = index;
			paymentAccount_rowId = row.id;
	        updatePaymentAccountActions(index);
	    }
		var paymentAccount_onAfterEdit = function(index,row){
	        row.editing = false;
			paymentAccount_editingIndex = undefined;
			paymentAccount_rowId = '';
	        updatePaymentAccountActions(index);
	    }
		var paymentAccount_onCancelEdit = function(index,row){
	        row.editing = false;
			paymentAccount_editingIndex = undefined;
			paymentAccount_rowId = '';
	        updatePaymentAccountActions(index);
	    }
		function updatePaymentAccountActions(index){
			$('#dg-paymentAccount').datagrid('updateRow',{
				index: index,
				row:{}
			});
		}
		function editPaymentAccount(target){
			if(paymentAccount_editingIndex == undefined) 
			{
				var col = $('#dg-paymentAccount').datagrid('getColumnOption', 'remainMoney');
				col.editor = null;
				$('#dg-paymentAccount').datagrid('selectRow', getRowIndex(target));
				$('#dg-paymentAccount').datagrid('beginEdit', getRowIndex(target));
			}
		}
		function deletePaymentAccount(target, rowId){
			if(rowId == undefined) 
			{
				$('#dg-paymentAccount').datagrid('deleteRow', getRowIndex(target));
			}
			else 
			{
				if(paymentAccount_editingIndex == undefined) 
				{
					$.messager.confirm('确认','您确认要删除?',function(r){
						if (r){
							//$('#dg-paymentAccount').datagrid('deleteRow', getRowIndex(target));
							ajaxPostRequest ('<c:url value='/paymentAccount/deleteModels.html' />', {ids:rowId}, reloadPaymentAccount);
						}
					});
				}
			}
		}
		function savePaymentAccount(target, rowId){
			//console.log(rowId);
			if ($('#dg-paymentAccount').datagrid('validateRow', getRowIndex(target)))
			{
				var paymentAccountNameEditor = $('#dg-paymentAccount').datagrid('getEditor', {index:getRowIndex(target), field:'name'});
				var paymentAccountName = $(paymentAccountNameEditor.target).val();
				var paymentAccountIdEditor = $('#dg-paymentAccount').datagrid('getEditor', {index:getRowIndex(target), field:'accountId'});
				var paymentAccountId = $(paymentAccountIdEditor.target).val();
				var accountModeEditor = $('#dg-paymentAccount').datagrid('getEditor', {index:getRowIndex(target), field:'accountMode'});
				var accountMode = $(accountModeEditor.target).combobox('getValue');
				var remainMoneyEditor = $('#dg-paymentAccount').datagrid('getEditor', {index:getRowIndex(target), field:'remainMoney'});
				var remainMoney = 0;
				if(remainMoneyEditor) 
				{
					remainMoney = $(remainMoneyEditor.target).val();
				}
				
				$('#dg-paymentAccount').datagrid('endEdit', getRowIndex(target));
				if(rowId == undefined) 
				{
					ajaxPostRequest ('<c:url value='/paymentAccount/addModel.html' />', {id:rowId, name:paymentAccountName, accountId:paymentAccountId, accountMode:accountMode, remainMoney:remainMoney}, reloadPaymentAccount);
				}
				else 
				{
					ajaxPostRequest ('<c:url value='/paymentAccount/updateModel.html' />', {id:rowId, name:paymentAccountName, accountId:paymentAccountId, accountMode:accountMode}, reloadPaymentAccount);
				}
			}
		}
		function cancelPaymentAccount(target){
			$('#dg-paymentAccount').datagrid('cancelEdit', getRowIndex(target));
		}
		function insertPaymentAccount(){
			if(paymentAccount_editingIndex == undefined) 
			{
				var row = $('#dg-paymentAccount').datagrid('getSelected');
				if (row){
					var index = $('#dg-paymentAccount').datagrid('getRowIndex', row);
				} else {
					index = 0;
				}
				$('#dg-paymentAccount').datagrid('insertRow', {
					index: index,
					row:{
						id:'',
						accountMode: 'DEPOSIT',
						remainMoney: 0
					}
				});
				
				var col = $('#dg-paymentAccount').datagrid('getColumnOption', 'remainMoney');
				col.editor = {type:'numberbox',options:{precision:2}};
				$('#dg-paymentAccount').datagrid('selectRow',index);
				$('#dg-paymentAccount').datagrid('beginEdit',index);
			}
		}
		$.extend($.fn.validatebox.defaults.rules, {
			checkPaymentAccountNameExist: {
				validator: function(value, param){
					var datagridId = '#dg-paymentAccount';
					var idField = 'paymentAccountId';
					var nameField = 'name';
					var data={};
					data[nameField]=value;
					data[idField] = paymentAccount_rowId;
					//var nameEditor = $(datagridId).datagrid('getEditor', {index:paymentAccount_editingIndex, field:nameField});
					//data[nameField] = $(nameEditor.target).val();
					//console.log(data);
					var _3ee=$.ajax({url:"<c:url value='/paymentAccount/checkExist.html' />",dataType:"json",data:data,async:false,cache:false,type:"post"}).responseText;
					return _3ee=="true";
				},
				message: remoteMessage
			}
		});
		$.extend($.fn.validatebox.defaults.rules, {
			checkPaymentAccountIdExist: {
				validator: function(value, param){
					var datagridId = '#dg-paymentAccount';
					var idField = 'paymentAccountId';
					var nameField = 'accountId';
					var data={};
					data[nameField]=value;
					data[idField] = paymentAccount_rowId;
					//var nameEditor = $(datagridId).datagrid('getEditor', {index:paymentAccount_editingIndex, field:nameField});
					//data[nameField] = $(nameEditor.target).val();
					//console.log(data);
					var _3ee=$.ajax({url:"<c:url value='/paymentAccount/checkExistByAccount.html' />",dataType:"json",data:data,async:false,cache:false,type:"post"}).responseText;
					return _3ee=="true";
				},
				message: remoteMessage
			}
		});
	</script>
