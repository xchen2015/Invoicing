	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<div id="dlg-manage-paymentWay" class="easyui-dialog" title="<spring:message code="payment.paymentWayManagement" />" 
		style="width: 500px; height: 400px; padding: 5px;" closed="true" 
		buttons="#dlg-buttons-manage-paymentWay" data-options="modal:true">
		<table id="dg-paymentWay" class="easyui-datagrid" 
			toolbar="#toolbar-paymentWay" pagination="false" rownumbers="true" singleSelect="true" 
			data-options="fit:true, fitColumns:true, onBeforeEdit:paymentWay_onBeforeEdit, onAfterEdit:paymentWay_onAfterEdit, 
				onCancelEdit:paymentWay_onCancelEdit, onBeforeLoad:onBeforeLoadPaymentWay">
			<thead>
				<tr>
					<th field="id" data-options="hidden:true"><spring:message code="id" /></th>
					<th field="name" width="100" data-options="sortable:false, editor:{type:'validatebox', options:{required:true, validType:'checkPaymentWayExist[]'}}"><spring:message code="name" /></th>
					<th field="action" width="50" data-options="formatter:formatter_paymentWayAction"><spring:message code="action" /></th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="toolbar-paymentWay">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="insertPaymentWay()" title="<spring:message code="newType" />"></a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload', plain:true" onclick="reloadPaymentWay()" title="<spring:message code="refresh" />"></a>
	</div>
	<div id="dlg-buttons-manage-paymentWay">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg-manage-paymentWay').dialog('close')"><spring:message code="close" /></a>
	</div>
	
	<script type="text/javascript">
		function reloadPaymentWay() 
		{
			$('#dlg-manage-paymentWay #dg-paymentWay').datagrid('reload');
		}
		function onBeforeLoadPaymentWay() 
		{
			paymentWay_editingIndex = undefined;
			paymentWay_rowId = undefined;
			$('#dlg-manage-paymentWay #dg-paymentWay').datagrid('options').url = "<c:url value='/paymentWay/getAllModel.html' />";
			return true;
		}
		function formatter_paymentWayAction (value,row,index) 
		{
			if (row.editing){
				if(row.id == '') 
				{
					var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="savePaymentWay(this)">&nbsp;</a>&nbsp;&nbsp;';
					var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deletePaymentWay(this)">&nbsp;</a>';
					return s+d;
				}
				else 
				{
					var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="savePaymentWay(this,' + row.id + ')">&nbsp;</a>&nbsp;&nbsp;';
					var c = '<a href="#" class="icon-no" style="display:inline-block;width:16px;" title="取消" onclick="cancelPaymentWay(this)">&nbsp;</a>';
					return s+c;
				}
			} else {
				var e = '<a href="#" class="icon-edit" style="display:inline-block;width:16px;" title="编辑" onclick="editPaymentWay(this)">&nbsp;</a>&nbsp;&nbsp;';
				var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deletePaymentWay(this,' + row.id + ')">&nbsp;</a>';
				return e+d;
			}
		}
		var paymentWay_editingIndex;
		var paymentWay_rowId;
		var paymentWay_onBeforeEdit = function(index,row){
	        row.editing = true;
			paymentWay_editingIndex = index;
			paymentWay_rowId = row.id;
	        updatePaymentWayActions(index);
	    }
		var paymentWay_onAfterEdit = function(index,row){
	        row.editing = false;
			paymentWay_editingIndex = undefined;
			paymentWay_rowId = '';
	        updatePaymentWayActions(index);
	    }
		var paymentWay_onCancelEdit = function(index,row){
	        row.editing = false;
			paymentWay_editingIndex = undefined;
			paymentWay_rowId = '';
	        updatePaymentWayActions(index);
	    }
		function updatePaymentWayActions(index){
			$('#dg-paymentWay').datagrid('updateRow',{
				index: index,
				row:{}
			});
		}
		function editPaymentWay(target){
			if(paymentWay_editingIndex == undefined) 
			{
				$('#dg-paymentWay').datagrid('beginEdit', getRowIndex(target));
			}
		}
		function deletePaymentWay(target, rowId){
			if(rowId == undefined) 
			{
				$('#dg-paymentWay').datagrid('deleteRow', getRowIndex(target));
			}
			else 
			{
				if(paymentWay_editingIndex == undefined) 
				{
					$.messager.confirm('确认','您确认要删除?',function(r){
						if (r){
							//$('#dg-paymentWay').datagrid('deleteRow', getRowIndex(target));
							ajaxPostRequest ('<c:url value='/paymentWay/deleteModels.html' />', {ids:rowId}, reloadPaymentWay);
						}
					});
				}
			}
		}
		function savePaymentWay(target, rowId){
			//console.log(rowId);
			if ($('#dg-paymentWay').datagrid('validateRow', getRowIndex(target)))
			{
				var paymentWayNameEditor = $('#dg-paymentWay').datagrid('getEditor', {index:getRowIndex(target), field:'name'});
				var paymentWayName = $(paymentWayNameEditor.target).val();
				//console.log(accountingMode);
				//console.log(paymentWayName);
				$('#dg-paymentWay').datagrid('endEdit', getRowIndex(target));
				if(rowId == undefined) 
				{
					ajaxPostRequest ('<c:url value='/paymentWay/addModel.html' />', {id:rowId, name:paymentWayName}, reloadPaymentWay);
				}
				else 
				{
					ajaxPostRequest ('<c:url value='/paymentWay/updateModel.html' />', {id:rowId, name:paymentWayName}, reloadPaymentWay);
				}
			}
		}
		function cancelPaymentWay(target){
			$('#dg-paymentWay').datagrid('cancelEdit', getRowIndex(target));
		}
		function insertPaymentWay(){
			if(paymentWay_editingIndex == undefined) 
			{
				var row = $('#dg-paymentWay').datagrid('getSelected');
				if (row){
					var index = $('#dg-paymentWay').datagrid('getRowIndex', row);
				} else {
					index = 0;
				}
				$('#dg-paymentWay').datagrid('insertRow', {
					index: index,
					row:{
						id:''
					}
				});
				$('#dg-paymentWay').datagrid('selectRow',index);
				$('#dg-paymentWay').datagrid('beginEdit',index);
			}
		}
		$.extend($.fn.validatebox.defaults.rules, {
			checkPaymentWayExist: {
				validator: function(value, param){
					var datagridId = '#dg-paymentWay';
					var idField = 'paymentWayId';
					var nameField = 'name';
					var data={};
					data[nameField]=value;
					data[idField] = paymentWay_rowId;
					//var nameEditor = $(datagridId).datagrid('getEditor', {index:paymentWay_editingIndex, field:nameField});
					//data[nameField] = $(nameEditor.target).val();
					//console.log(data);
					var _3ee=$.ajax({url:"<c:url value='/paymentWay/checkExist.html' />",dataType:"json",data:data,async:false,cache:false,type:"post"}).responseText;
					return _3ee=="true";
				},
				message: remoteMessage
			}
		});
	</script>
