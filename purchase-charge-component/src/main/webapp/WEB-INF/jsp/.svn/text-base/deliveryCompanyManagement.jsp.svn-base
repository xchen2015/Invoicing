	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<div id="dlg-manage-deliveryCompany" class="easyui-dialog" title="<spring:message code="deliveryCompanyManagement" />" 
		style="width: 500px; height: 400px; padding: 5px;" closed="true"
		buttons="#dlg-buttons-manage-deliveryCompany" data-options="modal:true">
		<table id="dg-deliveryCompany" class="easyui-datagrid"  
			toolbar="#toolbar-deliveryCompany" pagination="false" rownumbers="true" singleSelect="true" 
			data-options="fit:true, fitColumns:true, onBeforeEdit:deliveryCompany_onBeforeEdit, onAfterEdit:deliveryCompany_onAfterEdit, 
				onCancelEdit:deliveryCompany_onCancelEdit, onBeforeLoad:onBeforeLoadDeliveryCompany">
			<thead>
				<tr>
					<th field="id" data-options="hidden:true"><spring:message code="id" /></th>
					<th field="name" width="100" data-options="sortable:false, editor:{type:'validatebox', options:{required:true, validType:'remoteInlineCheckExist[]'}}"><spring:message code="name" /></th>
					<th field="action" width="50" data-options="formatter:formatter_deliveryCompanyAction">操作</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="toolbar-deliveryCompany">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="insertDeliveryCompany()" title="新增物流公司"></a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload', plain:true" onclick="reloadDeliveryCompany()" title="<spring:message code="refresh" />"></a>
	</div>
	<div id="dlg-buttons-manage-deliveryCompany">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg-manage-deliveryCompany').dialog('close')"><spring:message code="close" /></a>
	</div>
	
	<script type="text/javascript">
		function reloadDeliveryCompany() 
		{
			$('#dg-deliveryCompany').datagrid('reload');
		}
		function onBeforeLoadDeliveryCompany() 
		{
			deliveryCompany_editingIndex = undefined;
			deliveryCompany_rowId = undefined;
			$('#dg-deliveryCompany').datagrid('options').url = '<c:url value='/deliveryCompany/getAllModel.html' />';
			return true;
		}
		function formatter_deliveryCompanyAction (value,row,index) 
		{
			if (row.editing){
				if(row.id == '') 
				{
					var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="saveDeliveryCompany(this)">&nbsp;</a>&nbsp;&nbsp;';
					var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteDeliveryCompany(this)">&nbsp;</a>';
					return s+d;
				}
				else 
				{
					var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="saveDeliveryCompany(this,' + row.id + ')">&nbsp;</a>&nbsp;&nbsp;';
					var c = '<a href="#" class="icon-no" style="display:inline-block;width:16px;" title="取消" onclick="cancelDeliveryCompany(this)">&nbsp;</a>';
					return s+c;
				}
			} else {
				var e = '<a href="#" class="icon-edit" style="display:inline-block;width:16px;" title="编辑" onclick="editDeliveryCompany(this)">&nbsp;</a>&nbsp;&nbsp;';
				var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteDeliveryCompany(this,' + row.id + ')">&nbsp;</a>';
				return e+d;
			}
		}
		var deliveryCompany_editingIndex;
		var deliveryCompany_rowId;
		var deliveryCompany_onBeforeEdit = function(index,row){
	        row.editing = true;
			deliveryCompany_editingIndex = index;
			deliveryCompany_rowId = row.id;
	        updateDeliveryCompanyActions(index);
	    }
		var deliveryCompany_onAfterEdit = function(index,row){
	        row.editing = false;
			deliveryCompany_editingIndex = undefined;
			deliveryCompany_rowId = '';
	        updateDeliveryCompanyActions(index);
	    }
		var deliveryCompany_onCancelEdit = function(index,row){
	        row.editing = false;
			deliveryCompany_editingIndex = undefined;
			deliveryCompany_rowId = '';
	        updateDeliveryCompanyActions(index);
	    }
		function updateDeliveryCompanyActions(index){
			$('#dg-deliveryCompany').datagrid('updateRow',{
				index: index,
				row:{}
			});
		}
		function editDeliveryCompany(target){
			if(deliveryCompany_editingIndex == undefined) 
			{
				$('#dg-deliveryCompany').datagrid('beginEdit', getRowIndex(target));
			}
		}
		function deleteDeliveryCompany(target, rowId){
			if(rowId == undefined) 
			{
				$('#dg-deliveryCompany').datagrid('deleteRow', getRowIndex(target));
			}
			else 
			{
				if(deliveryCompany_editingIndex == undefined) 
				{
					$.messager.confirm('确认','您确认要删除?',function(r){
						if (r){
							//$('#dg-deliveryCompany').datagrid('deleteRow', getRowIndex(target));
							ajaxPostRequest ('<c:url value='/deliveryCompany/deleteModels.html' />', {ids:rowId}, reloadDeliveryCompany);
						}
					});
				}
			}
		}
		function saveDeliveryCompany(target, rowId){
			//console.log(rowId);
			if ($('#dg-deliveryCompany').datagrid('validateRow', getRowIndex(target)))
			{
				var deliveryCompanyNameEditor = $('#dg-deliveryCompany').datagrid('getEditor', {index:getRowIndex(target), field:'name'});
				var deliveryCompanyName = $(deliveryCompanyNameEditor.target).val();
				//console.log(accountingMode);
				//console.log(deliveryCompanyName);
				$('#dg-deliveryCompany').datagrid('endEdit', getRowIndex(target));
				if(rowId == undefined) 
				{
					ajaxPostRequest ('<c:url value='/deliveryCompany/addModel.html' />', {id:rowId, name:deliveryCompanyName}, reloadDeliveryCompany);
				}
				else 
				{
					ajaxPostRequest ('<c:url value='/deliveryCompany/updateModel.html' />', {id:rowId, name:deliveryCompanyName}, reloadDeliveryCompany);
				}
			}
		}
		function cancelDeliveryCompany(target){
			$('#dg-deliveryCompany').datagrid('cancelEdit', getRowIndex(target));
		}
		function insertDeliveryCompany(){
			if(deliveryCompany_editingIndex == undefined) 
			{
				var row = $('#dg-deliveryCompany').datagrid('getSelected');
				if (row){
					var index = $('#dg-deliveryCompany').datagrid('getRowIndex', row);
				} else {
					index = 0;
				}
				$('#dg-deliveryCompany').datagrid('insertRow', {
					index: index,
					row:{
						id:''
					}
				});
				$('#dg-deliveryCompany').datagrid('selectRow',index);
				$('#dg-deliveryCompany').datagrid('beginEdit',index);
			}
		}
		$.extend($.fn.validatebox.defaults.rules, {
			remoteInlineCheckExist: {
				validator: function(value, param){
					var datagridId = '#dg-deliveryCompany';
					var idField = 'deliveryCompanyId';
					var nameField = 'name';
					var data={};
					data[nameField]=value;
					data[idField] = deliveryCompany_rowId;
					//var nameEditor = $(datagridId).datagrid('getEditor', {index:deliveryCompany_editingIndex, field:nameField});
					//data[nameField] = $(nameEditor.target).val();
					//console.log(data);
					var _3ee=$.ajax({url:"<c:url value='/deliveryCompany/checkExist.html' />",dataType:"json",data:data,async:false,cache:false,type:"post"}).responseText;
					return _3ee=="true";
				},
				message: remoteMessage
			}
		});
	</script>
