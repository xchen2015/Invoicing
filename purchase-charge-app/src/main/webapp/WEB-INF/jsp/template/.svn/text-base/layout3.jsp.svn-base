<%--
 Template for laying out components on a page.
 
 This template has three columns side by side.  Each column takes up
 one third the page width. The components for the columns come from regions
 named 'left', 'middle' and 'right'.
--%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/less" href='<c:url value="/common/css/Layout.less"/>' />
    <link rel="stylesheet" type="text/css" href="<c:url value='/common/css/easyui.css' />">
  	<jsp:include page="script.jsp"/>
</head>
<body>
<jsp:include page="header.jsp"/>
<jsp:include page="topnav.jsp"/>

<div id="content">

    <%-- Include any static content --%>
    <jsp:include page="addStatic.jsp" />
    
    <%-- Now get the components for the regions.
         The positions of the regions on the page are controlled
         by CSS styles applied to the divs containing the
         components.  The styles are applied based on the
         div ids and also depend on the order in which the divs 
         are created. Therefore it is important that both the
         order and names of the divs be preserved.
     --%>
    <jsp:include page="addWidgets.jsp?region=left&div=left" />
    <jsp:include page="addWidgets.jsp?region=middle&div=middle" />
    <jsp:include page="addWidgets.jsp?region=right&div=right" />

</div>

<jsp:include page="footer.jsp" />
</body>
</html>