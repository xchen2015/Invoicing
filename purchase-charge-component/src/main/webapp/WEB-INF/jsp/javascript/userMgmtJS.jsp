<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="com.pinfly.purchasecharge.core.config.PurchaseChargeProperties"%>

<% response.setContentType ("text/javascript"); %>

	$(function(){
        var searchUserBox = $('#searchbox-user');
        searchUserBox.textbox().textbox('addClearBtn', 'icon-clear');
        searchUserBox.textbox('textbox').bind('keydown', function(e){
	        if (e.keyCode == 13){	// when press ENTER key, accept the inputed value.
	            searchUserBox.textbox('setValue', $(this).val());
	           	var v = searchUserBox.textbox('getValue');
	           	doSearchUser(v);
	        }
        });
    });
	var hideSelectRolePanelAfterSelect = function() 
	{
		var roles = $('#dlg-user #fm-user #role').combotree('getValues');
		$('#dlg-user #fm-user #roles').val(roles);
	}
	var cellFormatter_userRole = function(value,row,index)
	{
		var roles = '';
		if(row.roleBeans)
		{
			if(row.roleBeans.length >= 1) 
			{
				for(var i = 0; i < row.roleBeans.length; i++) 
				{
					roles += (row.roleBeans[i].name + ',');
				}
				roles = roles.substring(0, roles.length - 1);
			}
		}
		return '<span title="' + roles + '">' + roles + '</span>';
	}
	var newUserCallback = function() 
	{
		$('#dlg-user #fm-user #enabled')[0].checked = true;
	}
	var editUserCallback = function() 
	{
		var user = $('#dg-user').datagrid('getSelected');
		//console.log(user);
		if(user) 
		{
			var roles = user.roleBeans;
			if(roles && roles.length > 0) 
			{
				var roleIds = new Array(roles.length);
				for(var i = 0; i < roles.length; i ++) 
				{
					roleIds[i] = roles[i].id;
				}
				showRolePanel();
				$('#dlg-user #fm-user #role').combotree('setValues', roleIds);
				hideSelectRolePanelAfterSelect();
			}
			
			// handle checkbox issue
			if(user.enabled) 
			{
				$('#dlg-user #fm-user #enabled')[0].checked = true;
			}
			else 
			{
				$('#dlg-user #fm-user #enabled')[0].checked = false;
			}
			if(user.admin) 
			{
				$('#dlg-user #fm-user #admin')[0].checked = true;
			}
			else 
			{
				$('#dlg-user #fm-user #admin')[0].checked = false;
			}
		}
	}
	var onClickUserRow = function (rowIndex, rowData) 
	{
		$('#toolbar-user #btn-editUser').linkbutton('enable');
		$('#toolbar-user #btn-delUser').linkbutton('enable');
		$('#toolbar-user #btn-resetPwd').linkbutton('enable');
	}
	var onLoadUserSuccess = function (data) 
	{
		$('#toolbar-user #btn-editUser').linkbutton('disable');
		$('#toolbar-user #btn-delUser').linkbutton('disable');
		$('#toolbar-user #btn-resetPwd').linkbutton('disable');
	}
	
	var resetUserPwd = function () 
	{
		var row = $('#dg-user').datagrid('getSelected');
		if(row) 
		{
			var userId = row.userId;
			var password = '<%=PurchaseChargeProperties.getDefaultPassword()%>';
			$.messager.confirm('确认',
					'您确认要重置' + userId + '的密码为' + password + '?', function(r) {
						if (r) {
							ajaxPostRequest('<c:url value='/password/resetPassword.html' />', {userId:row.userId});
						}
			});
		}
	}
	function doSearchUser(value) {
		$('#dg-user').datagrid('load', {
			searchKey : value
		});
	}
	var showRolePanel = function() 
	{
		$('#dlg-user #fm-user #role').combotree('reload', '<c:url value='/role/getAllModel.html' />');
	}
	var showRoleGrid = function() 
	{
		$('#dlg-role-grid').dialog('open');
		$('#dlg-role-grid #dg-role').datagrid('options').url = '<c:url value='/role/getAllModel.html' />';
		$('#dlg-role-grid #dg-role').datagrid('reload');
	}
	var onDblClickUserRow = function(rowIndex, rowData) {
		$('#toolbar-user #btn-editUser').click();
	}
