<%@ page import="com.pinfly.purchasecharge.core.util.PurchaseChargeConstants"%>
<%@ page import="com.pinfly.purchasecharge.core.config.PurchaseChargeProperties"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title><spring:message code="pc.name" />-<spring:message code="loginForm.loginButton" text="Log In" /></title>
	<jsp:include page="template/addStatic.jsp" />
	<script src="<c:url value='/common/jquery/jquery-1.10.2.min.js' />"></script>
	<!-- Bootstrap
	<script src="<c:url value='/common/js/bootstrap.min.js' />"></script>
	<link href="<c:url value='/common/css/bootstrap.min.css' />" rel="stylesheet" media="screen"> -->
	<link rel="stylesheet" type="text/css" href='<c:url value="/common/css/layout.css" />' />
	
	<style type="text/css">
		.loginBox input 
		{
			border: 1px solid #95B8E7;
			border-radius: 5px 5px 5px 5px;
			margin-left: 0px;
			margin-right: 0px;
			padding-top: 3px;
			padding-bottom: 3px;
			outline-style: none;
			padding: 4px;
		}
		
		.btn-login {
			background-position: 0 -208px;
			font-family: inherit;
		}
		.btn-login-hover {
			background-position: 0 -256px;
		}
		.btn-main {
			color: #fff;
			box-shadow: 0 2px 5px rgba(0,28,88,.3);
		}
		.btn-disabled 
		{
			color:gray;
		}
		.btn {
			width: 110px;
			height: 38px;
			float: left;
			text-align: center;
			cursor: pointer;
			border: 0;
			padding: 0;
			font-weight: 600;
			font-size: 16px;
			display: inline-block;
			vertical-align: baseline;
			line-height: 38px;
			outline: 0;
			background-color: transparent;
			background-image: url(common/images/bg_v3.png);
		}
	</style>
	<script type="text/javascript">
		function onload () 
		{
			document.getElementById('j_username').focus();
			validateValue ();
		}
		
		function validateValue () 
		{
			var username = document.getElementById('j_username').value;
			var password = document.getElementById('j_password').value;
			if(username != '' && password != '') 
			{
				document.getElementById('j_submit').disabled='';
				$('#j_submit').removeClass("btn-disabled");
			}
			else 
			{
				document.getElementById('j_submit').disabled='disabled';
				$('#j_submit').addClass("btn-disabled");
			}
		}
		$(document).ready(function(){
			$('#j_submit').hover(
				function () {
					$(this).addClass("btn-login-hover");
				},
				function () {
					$(this).removeClass("btn-login-hover");
				}
			);
		});
	</script>
</head>

<body style="background:#efefef;" onload="onload()">
	<div style="position: relative; margin-top: -32px; width: 100%; min-height: 100%; height: auto !important; height: 100%;">
       
		<div id="news" style="position: relative; display: block; width: auto; margin-top: 0; left: 0">
			<div style="width:80%; height:500px;">
				<div style="height: 500px; background-repeat: no-repeat; background-position: 20% 50px; background-image: url('<c:url value='/common/images/clinicians.jpg' />')"></div> 
			</div>
		</div>  

		<div class="loginBox" id="loginBox" style="position:absolute; top:150px; right:150px; width:245px;">
			<form class="" name="f" action="<c:url value='j_spring_security_check'/>"
				method="post">
				<div>
					<p style="margin-top:10px;">
						<font color="#244668" size="5"><spring:message code="pc.name" /></font>
					</p>
					<p style="margin-top:5px; margin-bottom:5px;">
						<c:if test="${not empty param.authfailed}">
							<font color="red">
							<spring:message code="loginForm.unsucessfulLogin" text="Your login attempt was not successful, try again." />
							&nbsp;<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/></font>
						</c:if>
					</p>
					
					<p style="margin-top:5px; margin-bottom:5px;">
						<label><spring:message code="loginForm.userId" />:</label>
						<br />
						<input type="text" class=""
							placeholder="<spring:message code='loginForm.userId' text='User ID' />"
							name='j_username' id='j_username' value='<c:if test="${not empty param.authfailed}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>' size="28" style="width:200px;height:25px">
					</p>
					<p style="margin-top:5px; margin-bottom:5px;">
						<label><spring:message code="loginForm.password" />:</label>
						<br />
						<input type="password" class=""
							placeholder="<spring:message code='loginForm.password' text='Password' />"
							name='j_password' id='j_password' size="28" onkeyup="validateValue()" onfocus="validateValue()" style="width:200px;height:25px">
					</p>
					<!--<p style="margin-top:5px; margin-bottom:5px;">		
						<label style="cursor: pointer;display: inline-block;"><input type="checkbox" name="_spring_security_remember_me"><spring:message code="loginForm.rememberMeTwoWeeks" text="Don't ask for my password for two weeks" /></label>
					</p>-->
					
					<p style="font-size: 12px; margin-top: 5px; margin-bottom: 5px;">
						<spring:message code="loginForm.helpInfo" />
					</p>
					
					<p style="margin-top:5px; margin-bottom:5px; height:38px;">
						<button id="j_submit" class="btn btn-main btn-login btn-disabled" type="submit">登&nbsp;&nbsp;&nbsp;录</button>
					</p>
				</div>
			</form>
		</div>
		
		<div id="welcome" style="position: relative; display: block; width: auto; margin-top: 20px; left: 0;">
			<div style="width: 100%; height: 70px;">
				<table border="0" cellpadding="0" cellspacing="0" align="center" width="80%" style="margin: 0 auto; text-align: center;">
					<tr>
						<td><font color="#244668" size="6"><spring:message code="WelcomeTo" /><spring:message code="pc.name" /><spring:message code="System" /></font></td>
					</tr>
					<tr>
						<td><font color="#34608c" size="5"><spring:message code="pc.version" /></font></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
	<div class="footer" style="">
		<div style="text-align: left;">
			&copy;<a href="<%=PurchaseChargeProperties.getInstance ().getConfig (PurchaseChargeConstants.HOST_PAGE) %>">
			<spring:message code="pc.copyright" />;<spring:message code="pc.name" />&nbsp;<spring:message code="pc.version" /></a>
		</div>
		<div class="footerMenu" style="top: 11px;">
			<ul style="">
				<li><a href="<%=PurchaseChargeProperties.getInstance ().getConfig (PurchaseChargeConstants.ABOUT_US) %>"><spring:message code="loginFooter.aboutUs" /></a>
				</li>
				<li><a href="<%=PurchaseChargeProperties.getInstance ().getConfig (PurchaseChargeConstants.CONTACT_US) %>"><spring:message code="loginFooter.contactUs" /></a>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>
