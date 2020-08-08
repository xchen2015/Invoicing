<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix='fn' uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:useBean id="navigation" scope="request" type="com.pinfly.purchasecharge.app.nav.Navigation" />
<%@ page import="java.util.*" pageEncoding="UTF-8" %>

<!-- First level navigation -->
<%
    pageContext.setAttribute("currentPath", "/");
    pageContext.setAttribute("items", navigation.getNavigationItems((String)pageContext.getAttribute("currentPath")));
%>
<div class="navigation" style="width:100%;" id="navigationBarWidget">
    <c:forEach var="loop" begin="1" end="${maxNavigationLevels}">
        <c:set var='singleLevel' value='' />
        <c:if test='${navigation.activePageAtTopLevel}'>
           <c:set var='singleLevel' value='style="border-bottom:none"' />
        </c:if>
        <c:if test="${fn:length(items) > 0}">
			<c:choose>
				<c:when test='${loop == 1}'>
					<div class="navigationMenuContentGroup${loop}">
						<div class="navigationMenuContent${loop} navigationMenuContentVisible${loop}">
							<div class="navigationMenu${loop}">
								<ul>
									<c:forEach var="nav" items="${items}">
										<sec:authorize url="${nav.path}.do">
											<c:choose>
												<c:when test="${nav.hidden and nav.active}">
													<c:set var="currentPath" value="${nav.path}" />
												</c:when>
												<c:when test="${nav.hidden}">
												</c:when>
												<c:when test="${nav.active}">
													<c:set var="subCurrentPath" value="${nav.path}" />
													<%
													pageContext.setAttribute("subItems", navigation.getNavigationItems((String)pageContext.getAttribute("subCurrentPath")));
													%>
													<c:choose>
														<c:when test='${fn:length(subItems) > 1}'>
															<li class="selected mainMenuItem"><a href="#"><span><spring:message code="${nav.titleCode}" /></span><span class="iconSpan16 downArrow16"></span></a>
																<ul class="verticalSubMenu">
																	<c:forEach var="subNav" items="${subItems}">
																		<li><a href='<c:url value="${subNav.path}.do"/>'><spring:message code="${subNav.titleCode}" /></a></li>
																	</c:forEach>
																</ul>
															</li>
														</c:when>
														<c:otherwise>
															<li class="selected mainMenuItem"><a href="#"><span><spring:message code="${nav.titleCode}" /></span><span class=""></span></a></li>
														</c:otherwise>
													</c:choose>
													<c:set var="currentPath" value="${nav.path}" />
												</c:when>
												<c:otherwise>
													<c:set var="subCurrentPath" value="${nav.path}" />
													<%
													pageContext.setAttribute("subItems", navigation.getNavigationItems((String)pageContext.getAttribute("subCurrentPath")));
													%>
													<c:choose>
														<c:when test='${fn:length(subItems) > 1}'>
															<li class="mainMenuItem"><a ${singleLevel} href='<c:url value="${nav.path}.do"/>'><span><spring:message code="${nav.titleCode}" /></span><span class="iconSpan16 downArrow16"></span></a>
																<ul class="verticalSubMenu">
																	<c:forEach var="subNav" items="${subItems}">
																		<li><a href='<c:url value="${subNav.path}.do"/>'><spring:message code="${subNav.titleCode}" /></a></li>
																	</c:forEach>
																</ul>
															</li>
														</c:when>
														<c:otherwise>
															<li class="mainMenuItem"><a ${singleLevel} href='<c:url value="${nav.path}.do"/>'><span><spring:message code="${nav.titleCode}" /></span><span class=""></span></a></li>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
										</sec:authorize>
									</c:forEach>
								</ul>
							</div>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="navigationMenuContentGroup${loop}">
						<div class="navigationMenuContent${loop} navigationMenuContentVisible${loop}">
							<div class="navigationMenu${loop}">
								<ul>
									<c:forEach var="nav" items="${items}">
										<sec:authorize url="${nav.path}.do">
											<c:choose>
												<c:when test="${nav.hidden and nav.active}">
													<c:set var="currentPath" value="${nav.path}" />
												</c:when>
												<c:when test="${nav.hidden}">
												</c:when>
												<c:when test="${nav.active}">
													<li class="selected"><a href="#"><spring:message code="${nav.titleCode}" /></a></li>
													<c:set var="currentPath" value="${nav.path}" />
												</c:when>
												<c:otherwise>
													<li><a ${singleLevel} href='<c:url value="${nav.path}.do"/>'><spring:message code="${nav.titleCode}" /></a></li>
												</c:otherwise>
											</c:choose>
										</sec:authorize>
									</c:forEach>
								</ul>
							</div>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
        </c:if>
        <%
        pageContext.setAttribute("items", navigation.getNavigationItems((String)pageContext.getAttribute("currentPath")));
        %>
    </c:forEach>
</div>
