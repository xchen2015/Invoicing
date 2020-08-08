<%--
 Page fragment that handles a region of a page.
 It takes two parameters
    region is the name of the region in the region map that defines the
            widgets that are to be placed in the region.  This parameter
            is required
    div is the id to assign to the div that will contain the widgets.
            This parameter is optional.  If not present, it defaults to
            the region name.  Note that the div id can be important because
            there is CSS style information assigned to the div based on 
            its id
--%>

<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<jsp:useBean id="navigation" scope="request" type="com.pinfly.purchasecharge.app.nav.Navigation" />
<link rel="stylesheet" type="text/css" href="<c:url value='/common/css/purchase.charge.css' />" />
<jsp:include page="../commonJs.jsp" />

<%
//  Get the region name and put that region's widgets in the page context
String regionName = request.getParameter("region");
if (regionName == null)
{
    throw new Exception ("Missing required parameter 'region'");
}
pageContext.setAttribute("widgets", navigation.getWidgetsForRegion(regionName));
%>

<c:forEach var="widget" items="${widgets}">
	<c:if test="${!empty widget.getJsServletPath}">
   		<script type="text/javascript" src="<c:url value='${widget.getJsServletPath}' />"></script>
   	</c:if>
   	<c:if test="${!empty widget.getCssServletPath}">
		<link rel="stylesheet" type="text/css" href="<c:url value='${widget.getCssServletPath}' />" />
   	</c:if>
</c:forEach>



    
