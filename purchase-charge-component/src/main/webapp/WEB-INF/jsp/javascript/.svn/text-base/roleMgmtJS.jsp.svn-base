<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<% response.setContentType ("text/javascript"); %>

	var hideAuthPanelAfterSelect = function() 
	{
		var auths = $('#dlg-role #fm-role #authority').combotree('getValues');
		$('#dlg-role #fm-role #authorities').val(auths);
	}
	var cellFormatter_roleAuthority = function(value,row,index)
	{
		var auths = '';
		if(row.authorityBeans)
		{
			if(row.authorityBeans.length >= 1) 
			{
				for(var i = 0; i < row.authorityBeans.length; i++) 
				{
					auths += (row.authorityBeans[i].name + ',');
				}
				auths = auths.substring(0, auths.length - 1);
			}
		}
		return '<span>' + auths + '</span>';
	}
	var newRoleCallback = function() 
	{
		$('#dlg-role #fm-role #enabled')[0].checked = true;
	}
	var editRoleCallback = function() 
	{
		var role = $('#dg-role').datagrid('getSelected');
		if(role) 
		{
			$('#dlg-role').mask('');
			var auths = role.authorityBeans;
			if(auths && auths.length > 0) 
			{
				var authIds = new Array(auths.length);
				for(var i = 0; i < auths.length; i ++) 
				{
					authIds[i] = auths[i].id;
				}
				showAuthorityPanel();
				$('#dlg-role #fm-role #authority').combotree('setValues', authIds);
				hideAuthPanelAfterSelect();
			}
			
			if(role.enabled) 
			{
				$('#dlg-role #fm-role #enabled')[0].checked = true;
			}
			else 
			{
				$('#dlg-role #fm-role #enabled')[0].checked = false;
			}
			$('#dlg-role').unmask();
		}
	}
	var authorities;
	var showAuthorityPanel = function() 
	{
		if(authorities == undefined) 
		{
			var url = '<c:url value='/authority/getAllModel.html' />';
			authorities = $.ajax({url:url,dataType:"json",data:{},async:false,cache:true,type:"post"}).responseJSON;
		}
		$('#dlg-role #fm-role #authority').combotree('loadData', authorities);
	}
	var onDblClickRoleRow = function(rowIndex, rowData) {
		$('#toolbar-role #btn-editRole').click();
	}
