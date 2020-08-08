<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="com.pinfly.purchasecharge.component.bean.ComponentContext" %>
<%@ page import="com.pinfly.purchasecharge.core.model.LoginUser" %>
<%@ page import="com.pinfly.purchasecharge.core.util.PurchaseChargeConstants" %>
<%@ page import="java.lang.String" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/common/easyui/1.4/themes/default/easyui.css' />" />
<link rel="stylesheet" type="text/css" href="<c:url value='/common/easyui/1.4/themes/icon.css' />" />
<link rel="stylesheet" type="text/css" href="<c:url value='/common/easyui/1.4/themes/color.css' />" />
<link rel="stylesheet" type="text/css" href="<c:url value='/common/css/purchase.charge.css' />" />
<link rel="stylesheet" type="text/css" href="<c:url value='/common/css/jquery.loadmask.css' />" />
<link rel="stylesheet" type="text/css" href="<c:url value='/common/css/jquery.notification.css' />" />
<script type="text/javascript" src="<c:url value='/common/jquery/jquery-1.10.2.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/common/easyui/1.4/jquery.easyui.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/common/easyui/1.4/locale/easyui-lang-zh_CN.js' />"></script>
<script type='text/javascript' src="<c:url value='/common/js/jquery.loadmask.min.js' />"></script>
<script type='text/javascript' src="<c:url value='/common/js/jquery.notification.js' />"></script>
<script type='text/javascript' src="<c:url value='/common/js/jquery.print.js' />"></script>
<script type='text/javascript' src="<c:url value='/common/js/jquery.datagrid.detailview.js' />"></script>
<script type='text/javascript' src="<c:url value='/common/js/jquery.datagrid.groupview.js' />"></script>

<jsp:include page="/WEB-INF/jsp/commonJs.jsp" />
<jsp:include page="/WEB-INF/jsp/commonChartJs.jsp" />

<link rel="stylesheet" type="text/css" href="<c:url value='/inOrder/getCss.html' />" />
<script type="text/javascript" src="<c:url value='/inOrder/getJs.html' />"></script>

<style>
<!--
body {
	font: 80%/1.4 "宋体", Verdana, Arial, Helvetica, sans-serif;
	background-color: #fefefe;
	margin: 0;
	padding: 0;
	color: #000000;
}
-->
</style>

<%
    ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
	ComponentContext componentContext = context.getBean ("componentContext", ComponentContext.class);

    LoginUser loginUser = componentContext.getLoginUser ();
    componentContext.putLoginUser (loginUser);
    pageContext.setAttribute(PurchaseChargeConstants.LOGIN_USER, loginUser, pageContext.SESSION_SCOPE);
%>