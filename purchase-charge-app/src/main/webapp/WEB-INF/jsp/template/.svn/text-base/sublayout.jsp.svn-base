<%--
 Template for laying out components on a page.
 
 This template has a single region called 'main' that spans the entire
 width of the page.
--%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>

<!DOCTYPE html>
<html>

<head>
    <%-- <link rel="stylesheet" type="text/css" href="<c:url value='/common/css/easyui.css' />" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/common/css/pc.css' />" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/common/css/jquery.notification.css' />" />
    <jsp:include page="script.jsp"/> --%>
	
	<style type="text/css">
	</style>
	
	<script type="text/javascript">
	</script>
</head>

<body>

<div id="subContent">

    <%-- Include any static content --%>
    <jsp:include page="addStatic.jsp" />

	
	<%-- Now get the components for the region.
	     The position of the region on the page is controlled
	     by CSS styles applied to the div containing the
	     components.  The styles are applied based on the
	     div id, therefore it is important that the div
	     name not be changed.
	 --%>
	<jsp:include page="addWidgets2.jsp?region=main&div=main" />

</div>

</body>
</html>