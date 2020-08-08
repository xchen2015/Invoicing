<%--
 Template for laying out components on a page.
 
 This template has a single region called 'main' that spans the entire
 width of the page.
--%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title><spring:message code="pc.name" /></title>
	<jsp:include page="addStatic.jsp" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/common/css/layout.css' />" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/common/easyui/1.4/themes/bootstrap/easyui.css' />" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/common/easyui/1.4/themes/icon.css' />" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/common/easyui/1.4/themes/color.css' />" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/common/css/jquery.loadmask.css' />" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/common/css/jquery.notification.css' />" />
    <jsp:include page="script.jsp"/>
    
    <script type="text/javascript" src="<c:url value='/common/js/jsupload.nocache.js' />"></script>
    <jsp:include page="addModules.jsp?region=main" />
</head>

<body>

<jsp:include page="header.jsp"/>
<jsp:include page="topnav.jsp"/>

<div id="content">

    <%-- Include any static content --%>
    <%-- <jsp:include page="addStatic.jsp" /> --%>

	<%-- Now get the components for the region.
	     The position of the region on the page is controlled
	     by CSS styles applied to the div containing the
	     components.  The styles are applied based on the
	     div id, therefore it is important that the div
	     name not be changed.
	 --%>
	<jsp:include page="addWidgets.jsp?region=main&div=main" />

</div>

<jsp:include page="footer.jsp" />
</body>
</html>