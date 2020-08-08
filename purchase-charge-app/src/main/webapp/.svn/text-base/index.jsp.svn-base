<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="java.lang.String" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="com.pinfly.purchasecharge.app.nav.NavigationService" %>
<%@ page import="com.pinfly.purchasecharge.app.nav.Navigation" %>
<%@ page import="com.pinfly.purchasecharge.component.bean.ComponentContext" %>
<%@ page import="com.pinfly.purchasecharge.core.model.LoginUser" %>

<%
    ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
    NavigationService navigationService = context.getBean ("navigationService", NavigationService.class);

    Navigation navigation = navigationService.getNavigation ("/");
    pageContext.setAttribute("items", navigation.getNavigationItems("/"));
    
    ComponentContext componentContext = context.getBean ("componentContext", ComponentContext.class);
    LoginUser user = null;
    try 
    {
	    user = componentContext.getLoginUser(request);
    }
    catch(Exception e) 
    {
        
    }
    pageContext.setAttribute("user", (null != user ? user.getUserId() : ""));
%>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title><spring:message code="pc.name" /></title>
	<link rel="shortcut icon" href='<c:url value="/common/images/favicon.ico" />' type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/common/css/purchase.charge.css' />" />
	<script>
		try {
			var logoutURL = '<c:url value="/logout"/>';
			var loginURL = '<c:url value="/login.do"/>';
			var logoutIntercept2 = function() {
				if (logoutURL) {
					window.location.href = logoutURL;
					//window.location.href = loginURL;
				}
			};
		} catch (ex) {
			window.console && console.info(ex);
		}
	</script>
</head>

<c:if test="${empty user}">
    <c:redirect url="/login.do" />
</c:if>
<!-- if there is a redirect parameter and the user has access to it, redirect there -->
<c:if test="${not empty param.redirect}">
    <sec:authorize url="${param.redirect}">
        <c:redirect url="${param.redirect}"/>
    </sec:authorize>
</c:if>
<%-- otherwise, find the first page the user has access to, and redirect there --%>
<c:if test="${fn:length(items) > 0}">
    <%-- doesn't seem to be a break/continue in jstl --%>
    <c:set var="redirected" value="false" />
    <c:set var="noAnyAuth" value="true" />
    <c:forEach var="nav" items="${items}">
        <c:if test="${redirected eq false }">
            <sec:authorize url="${nav.path}.do">
                <c:redirect url="${nav.path}.do"/>
                <c:set var="redirected" value="true" />
				<c:set var="noAnyAuth" value="false" />
            </sec:authorize>
        </c:if>
    </c:forEach>
	<c:if test="${noAnyAuth eq true }">
		<div><spring:message code="accessDeny.info" /> <a href='javascript:void(0);' onclick="logoutIntercept2();"><spring:message code="concurrencyLogin.switchUser" /></a></div>
	</c:if>
</c:if>
