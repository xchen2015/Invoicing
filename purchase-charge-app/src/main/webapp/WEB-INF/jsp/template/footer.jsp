<%@ page import="com.pinfly.purchasecharge.core.util.PurchaseChargeConstants"%>
<%@ page import="com.pinfly.purchasecharge.core.config.PurchaseChargeProperties"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="footer" style="" id="footerDiv">
    <div style="text-align: left;">
        &copy;<a href="<%=PurchaseChargeProperties.getInstance ().getConfig (PurchaseChargeConstants.HOST_PAGE) %>"><spring:message code="pc.copyright" />;<spring:message code="pc.name" />&nbsp;<spring:message code="pc.version" /></a>
    </div>
    <!-- <div class="footerMenu" style="top: 11px;">
        <ul style="">
            <li><a href="http://shop107943531.taobao.com">关于我们</a>
            </li>
            <li><a href="http://shop107943531.taobao.com">联系我们</a>
            </li>
            <li><a href="http://shop107943531.taobao.com">隐私 &amp; 安全</a>
            </li>
            <li><a href="http://shop107943531.taobao.com">帮助</a>
            </li>
        </ul>
    </div> -->
</div>