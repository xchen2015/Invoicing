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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<jsp:useBean id="navigation" scope="request" type="com.pinfly.purchasecharge.app.nav.Navigation" />

<%
//  Get the region name and put that region's widgets in the page context
String regionName = request.getParameter("region");
if (regionName == null)
{
    throw new Exception ("Missing required parameter 'region'");
}
pageContext.setAttribute("widgets", navigation.getWidgetsForRegion(regionName));

// Get the div name and put it in the page context
String divName = request.getParameter("div");
if (divName == null) divName = regionName;
pageContext.setAttribute("divName", divName);

pageContext.setAttribute("currentPath", navigation.getActivePath().substring(navigation.getActivePath().lastIndexOf('/')+1));
%>

<div id="${divName}">
	<div class='component'>
		<div class="easyui-layout" data-options=""
			style="width: 100%; height: 450px;">
			<div data-options="region:'west',title:'<spring:message code='navigation.tabMenu' />',split:true"
				style="width: 200px;">
				<ul class="easyui-tree" data-options="onSelect:selectTreeNode">
					<c:forEach var="widget" items="${widgets}">
						<li>
							<span>
								<spring:message code='${widget.uniqueName}' />
								<a href="#" style="text-decoration:underline;visibility:hidden;" onclick="addTab('#${divName} #tta', '<spring:message code='${widget.uniqueName}' />', '<c:url value='${widget.getViewServletPath}' />')">
								</a>
							</span>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div data-options="region:'center',border:false" style="">
				<div id="tta" class="easyui-tabs" data-options="fit:true">
					<%-- <c:forEach var="widget" items="${widgets}"> --%>
						<div title="<spring:message code='${widgets[0].uniqueName}' />" 
							data-options="closable:true, cls:'tab'">
							<jsp:include page="${widgets[0].getViewServletPath}" />
						</div>
					<%-- </c:forEach> --%>
				</div>
			</div>
		</div>
	</div>
</div>