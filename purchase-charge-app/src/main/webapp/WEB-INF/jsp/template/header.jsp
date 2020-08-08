<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="com.pinfly.purchasecharge.app.nav.NavigationItem"%>
<%@ page import="com.pinfly.purchasecharge.core.util.PurchaseChargeConstants"%>
<%@ page import="com.pinfly.purchasecharge.core.config.PurchaseChargeProperties"%>
<%@ page import="java.util.*"%>

<jsp:useBean id="subNavigation" scope="request"
	type="com.pinfly.purchasecharge.app.nav.Navigation" />
<jsp:useBean id="helpNavigation" scope="request"
	type="com.pinfly.purchasecharge.app.nav.Navigation" />
	
<%
	pageContext.setAttribute("currentPath", "/");
	List<NavigationItem> items = subNavigation.getNavigationItems((String)pageContext.getAttribute("currentPath"));
	pageContext.setAttribute("items", items);
	List<NavigationItem> helpItems = helpNavigation.getNavigationItems((String)pageContext.getAttribute("currentPath"));
	pageContext.setAttribute("helpItems", helpItems);
%>

<div class="header" id="headerDiv">
    <div style='float:right'>
		<table>
			<tr style="height:31px">
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td>
					<div class="userMenu" style="margin-right: 0px">
						<span id="greetings"></span>
					</div>
				</td>
				<td>
					<div class="userMenu">
						<sec:authorize access="isAuthenticated()">
							<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#mm'"><sec:authentication property="principal.username" /></a>
							<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#mm-help',iconCls:'icon-help'"></a>
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="logoutIntercept();"><spring:message code="Logout" /></a>
						</sec:authorize>
					</div>
				</td>
			</tr>
		</table>
	    
	</div>
    
    <div class="logo">
        <a href='<c:url value="/index.jsp"/>' class="companyName">
        	<%=PurchaseChargeProperties.getInstance ().getConfig (PurchaseChargeConstants.HEADER_TITLE) %>
        </a>
    </div>
	
    <div id="mm" class="easyui-menu" data-options="onClick:menuHandler" style="width:120px;">
    	<c:forEach var="navItem" items="${items}">
    		<div data-options="name:'<c:url value='${navItem.path}.sub' />'"><spring:message code='${navItem.titleCode}' /></div>
    	</c:forEach>
    </div>
    
    <div id="mm-help" class="easyui-menu" data-options="onClick:menuHelpHandler" style="width:120px;">
    	<c:forEach var="helpItem" items="${helpItems}">
    		<div data-options="name:'<c:url value='${helpItem.path}' />'"><spring:message code='${helpItem.titleCode}' /></div>
    	</c:forEach>
    </div>
    
    <div id="win" class="easyui-window" title="<spring:message code='appSetting.userSetting' />" style="width:800px;height:500px"
        data-options="modal:true, closed:true, collapsible:false, minimizable:false, maximizable:false">
	</div>
</div>

