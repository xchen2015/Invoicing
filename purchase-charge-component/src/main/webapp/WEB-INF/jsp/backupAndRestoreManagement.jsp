	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<div style="">
		<div id="dataBackupAndLoad-div" style="height:auto; margin-bottom:10px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" style="width:220px" data-options="size:'large'" iconCls="icon-redo" 
				onclick="backup()" title=""><spring:message code="backupAndRestore.backup" />(<spring:message code="backupAndRestore.backup.tip" />)</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton" style="" data-options="" iconCls="icon-remove" 
				onclick="destroyMultipleModel('#dg-dataBackup', '备份', '<c:url value='/dataBackupAndRestore/deleteModels.html' />')" title="">批量删除</a>
		</div>
	</div>
	
	<div style="width:60%; height:400px;">
		<table id="dg-dataBackup" title="备份历史" class="easyui-datagrid" rownumbers="true" singleSelect="true" checkOnSelect="true" selectOnCheck="false" 
			url="<c:url value='/dataBackupAndRestore/getAllModel.html' />" data-options="fitColumns:true, fit:true, 
				tools:[{
                    iconCls:'icon-reload',
                    handler:function(){
                        $('#dg-dataBackup').datagrid('reload');
                    }
                }]">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="id" data-options="hidden:true"></th>
					<th field="action" width="20" data-options="formatter:formatter_backupAndRestoreAction"><spring:message code="action" /></th>
					<th field="fileName" width="100" data-options="">备份名称</th>
					<th field="createdDate" width="60" data-options="">时间</th>
					<th field="fileSize" width="60" data-options="formatter:formatter_fileSize">文件大小</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="dlg-dataBackupAndLoadForm" class="easyui-dialog" style="" closed="true" 
		data-options="modal:true" buttons="#dlg-dataBackupAndLoadForm-btns">
		<form method="post" enctype="multipart/form-data">
			<input type="file" name="backupFile" id="backupFile" onchange="onSubmit()" />
		</form>
	</div>
	<div id="dlg-dataBackupAndLoadForm-btns">
		<a href="javascript:void(0)" id="saveBtn" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="onSubmit()"><spring:message code="save" /></a> 
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg-dataBackupAndLoadForm').dialog('close')"><spring:message code="cancel" /></a>
	</div>
	
	<script type="text/javascript">
		var onBackup = function () 
		{
			window.location.href='<c:url value='/dataBackupAndRestore/backup.html' />';
		}
		var showLoadWindow = function () 
		{
			$('#dlg-dataBackupAndLoadForm #backupFile').click();
		}
		var onSubmit = function() 
		{
			/*$.ajax({
		        url: '<c:url value='/dataBackupAndRestore/load.html' />',
		        type: 'post',
		        dataType: 'json',
		        data: $('#dlg-dataBackupAndLoadForm' + ' form').serialize(),
				beforeSend: 
					function (){
						return $('#dlg-dataBackupAndLoadForm' + ' form').form('validate');
					},
		        success: function(result){
		        	console.log(result);
					if (result.statusCode == '400' || result.statusCode == '500') {
						showFailureMsg (result.message);
					} else {
						showSuccessMsg (result.message);
						$('#dlg-dataBackupAndLoadForm' + ' form').form('clear');
						$('#dlg-dataBackupAndLoadForm').dialog('close'); // close the dialog  
					}
		        }
		    });*/
		    
			$('#dlg-dataBackupAndLoadForm').dialog('close');
			$('body').mask('<spring:message code="doing" />');
			$('#dlg-dataBackupAndLoadForm' + ' form').form('submit', {
				url : '<c:url value='/dataBackupAndRestore/restore.html' />',
				onSubmit : function() {
					var filename = $('#dlg-dataBackupAndLoadForm #backupFile').val();
					var fileExtension = filename.substring(filename.lastIndexOf('.'));
					//console.log(fileExtension);
					if(fileExtension == '' || '.sqlbak' != fileExtension) 
					{
						$('body').unmask();
						showWarningMsg("必须是文件扩展名为'.sqlbak'的备份文件!");
						return false;
					}
					return $(this).form('validate');
				},
				success : function(result) {
					$('#dlg-dataBackupAndLoadForm' + ' form').form('clear');
					$('body').unmask();
					var result = eval('(' + result + ')');
					//console.log(result);
					if (result.statusCode == '400' || result.statusCode == '500') 
					{
						showFailureMsg (result.message);
					} 
					else 
					{
						showSuccessMsg (result.message);
					}
				}
			});
		}
		
		function formatter_backupAndRestoreAction (value,row,index) 
		{
			if(row) 
			{
				var s = '<a href="#" class="icon-back" style="display:inline-block; width:16px;" title="<spring:message code="backupAndRestore.restore" />" onclick="restore('+row.id+')">&nbsp;</a>&nbsp;&nbsp;';
				var d = '<a href="#" class="icon-remove" style="display:inline-block; width:16px;" title="<spring:message code="remove" />" onclick="deleteBackup('+row.id+')">&nbsp;</a>';
				return s+d;
			}
		}
		function formatter_fileSize (value,row,index) 
		{
			return humanFileSize(value);
		}
		function backup () 
		{
			ajaxPostRequest ('<c:url value='/dataBackupAndRestore/addModel.html' />', {}, function() {$('#dg-dataBackup').datagrid('reload');});
		}
		function restore (rowId) 
		{
			$.messager.confirm('确认',
				'您确认要恢复这个 ' + '数据备份' + ' 吗，恢复后不可回滚?', 
				function(r) {
					if (r) {
						$('body').mask('<spring:message code="doing" />');
						ajaxPostRequest ('<c:url value='/dataBackupAndRestore/updateModel.html' />', {id:rowId}, function(){$('body').unmask();});
					}
				});
		}
		function deleteBackup (rowId) 
		{
			$.messager.confirm('确认',
				'您确认要删除这个 ' + '数据备份' + ' 吗?', 
				function(r) {
					if (r) {
						ajaxPostRequest ('<c:url value='/dataBackupAndRestore/deleteModels.html' />', {ids:rowId}, function() {$('#dg-dataBackup').datagrid('reload');});
					}
				});
		}
		
		function humanFileSize(bytes) {
		    var exp = Math.log(bytes) / Math.log(1024) | 0;
		    var result = (bytes / Math.pow(1024, exp)).toFixed(2);
		    return result + ' ' + (exp == 0 ? 'B': 'KMGTPEZY'[exp - 1] + 'B');
		}
	</script>
