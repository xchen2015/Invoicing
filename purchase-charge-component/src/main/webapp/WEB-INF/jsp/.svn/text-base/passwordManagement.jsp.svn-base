	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<script type="text/javascript">
		var passwordValidationMsg = '<spring:message code="password.oldPwdAndNewPwdCannotSame" />';
		$.extend($.fn.validatebox.defaults.rules, {
			remoteCheckOldPwd: {
				validator: function(value, param){
					var data={};
					data[param[1]]=value;
					if(param.length > 2) 
					{
						for(var i=2; i<param.length; i++) 
						{
							if(param[i].startsWith('#')) 
							{
								data[param[i].substring(1)] = $(param[i]).val();
							}
							else 
							{
								data[param[i]] = $(param[i]).val();
							}
						}
					}
					var _3ee = $.ajax({url:param[0],dataType:"json",data:data,async:false,cache:false,type:"post"}).responseText;
					return _3ee == "true";
				},
				message: '<spring:message code="password.oldPwdIncorrect" />'
			},
			checkOldPwdAndNewPwd: {
				validator: function(value, param){
					var oldPwd;
					if(param[0].startsWith('#')) 
					{
						oldPwd = $(param[0]).val();
					}
					if(oldPwd != '') 
					{
						if(value == oldPwd) 
						{
							$(this).validatebox.defaults.rules.checkOldPwdAndNewPwd.message = '<spring:message code="password.oldPwdAndNewPwdCannotSame" />';
							return false;
						}
						else 
						{
							var data={};
							data['password']=value;
							var url = "<c:url value='/password/checkPasswordComplexity.html' />";
							var _3ee = $.ajax({url:url,dataType:"json",data:data,async:false,cache:false,type:"post"}).responseJSON;
							$(this).validatebox.defaults.rules.checkOldPwdAndNewPwd.message = _3ee[1];
				        	return _3ee[0] == "true";
						}
					}
				}
			},
			checkNewPwdAndConfirmNewPwd: {
				validator: function(value, param){
					var oldPwd;
					if(param[0].startsWith('#')) 
					{
						oldPwd = $(param[0]).val();
					}
					if(oldPwd != '') 
					{
						return value == oldPwd;
					}
				},
				message: '<spring:message code="password.newPwdAndNewPwdConfirmMustBeSame" />'
			}
		});
	
		var changePassword = function() 
		{
			var userId = $('#div-password-change #fm-password-change #userId').val();
			var newPwd = $('#div-password-change #fm-password-change #newPwd').val();
			if($('#div-password-change #fm-password-change').form('validate')) 
			{
				$('#div-password-change').mask();
				ajaxPostRequest('<c:url value='/password/updatePassword.html' />', {userId:userId, newPwd:newPwd},
					function(){
						$('#div-password-change').unmask();
						$.messager.alert('系统提示','更新密码后，您需要重新登录','info', 
							function() {
								logoutIntercept();
							}
						);
					}		
				);
			}
		}
		var resetPasswordFields = function() 
		{
			$('#div-password-change #fm-password-change #oldPwd').textbox('reset');
			$('#div-password-change #fm-password-change #newPwd').textbox('reset');
			$('#div-password-change #fm-password-change #confirmNewPwd').textbox('reset');
		}
	</script>
	
	<div id="div-password-change" style="padding: 10px 20px">
		<form id="fm-password-change" class="fm" method="post" novalidate>
			<div class="fitem">
				<label style="width:70px;"><spring:message code="password.userId" />:</label> 
				<input id="userId" name="userId" value="${sessionScope.login_user.userId}" readonly="readonly" class="easyui-validatebox" required="true" style="border:none" />
			</div>
			<div class="fitem">
				<label style="width:70px;"><spring:message code="password.oldPwd" />:</label> 
				<input id="oldPwd" name="oldPwd" class="easyui-textbox" required="true" type="password" 
					validType="remoteCheckOldPwd['<c:url value='/password/checkOldPassword.html' />', 'oldPwd', '#userId']" />
			</div>
			<div class="fitem">
				<label style="width:70px;"><spring:message code="password.newPwd" />:</label> 
				<input id="newPwd" name="newPwd" class="easyui-textbox" required="true" type="password" 
					validType="checkOldPwdAndNewPwd['#div-password-change #fm-password-change #oldPwd']" />
			</div>
			<div class="fitem">
				<label style="width:70px;"><spring:message code="password.newPwdConfirm" />:</label> 
				<input id="confirmNewPwd" name="confirmNewPwd" class="easyui-textbox" required="true" type="password" 
					validType="checkNewPwdAndConfirmNewPwd['#div-password-change #fm-password-change #newPwd']" />
			</div>
			<div style="float:right; margin-top:20px">
				<a id="save-password-change" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" 
					onclick="changePassword()"><spring:message code="save" /></a> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" 
					onclick="resetPasswordFields()"><spring:message code="reset" /></a>
			</div>
		</form>
	</div>
