	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<div style="text-align:center;">
		<div class="" style="margin: 0 auto; width:400px">
			<div><spring:message code="backupAndRestore.backup.info" /></div>
			<div><spring:message code="backupAndRestore.restore.info" /></div>
		</div>
		<div id="" style="height:100px; margin-top:20px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" style="width:150px" data-options="size:'large'" iconCls="icon-reload" 
				onclick="" title="">恢复出厂设置</a>
		</div>
	</div>
	
	<script type="text/javascript">
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
		
	</script>
