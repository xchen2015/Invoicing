<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:useBean id="navigation" scope="request" type="com.pinfly.purchasecharge.app.nav.Navigation" />
<%@ page import="java.util.*" pageEncoding="UTF-8" %>

    <script type="text/javascript" src="<c:url value='/common/jquery/jquery-1.10.2.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/common/easyui/1.4/jquery.easyui.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/common/easyui/1.4/locale/easyui-lang-zh_CN.js' />"></script>
	<script type="text/javascript" src="<c:url value='/common/js/jquery.loadmask.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/common/js/jquery.notification.js' />"></script>
	<script type="text/javascript" src="<c:url value='/common/js/jquery.print.js' />"></script>
	
    <script type="text/javascript">
        try {
			var logoutURL = '<c:url value="/logout"/>';
			var loginURL = '<c:url value="/login.do"/>';
			var logoutIntercept = function() {
				if (logoutURL) {
					window.location.href = logoutURL;
					//window.location.href = loginURL;
				}
			};

		} catch (ex) {
			window.console && console.info(ex);
		}
		
		var addTab = function (tabId, title, url)
		{
			var exists = $(tabId).tabs('exists', title);
			if(!exists) 
			{
				$(tabId).tabs('add',{
					title:title,
					href:url,
					closable:true,
					fit:true,
					cls:'tab'
				});
			}
			else 
			{
				$(tabId).tabs('select', title);
			}
		}
		
		var selectTreeNode = function(node) 
		{
			$('#' + node.domId + ' a')[0].click();
			//console.log($('#' + node.domId + ' a')[0]);
		}
		var onLoadNavTreeSuccess = function(node,data){
			if (data.length){
				var id = data[0].id;
				var n = $(this).tree('find', id);
				$(this).tree('select', n.target);
			}
		}
    </script>
	<script type="text/javascript">
		var start;

		window.onload = function() {
			start = setInterval('documentState()', 1000);
		}

		window.onresize = function() {
			start = setInterval('documentState()', 1000);
		}

		//judge if the document load complete
		function documentState() {
			if (document.readyState == "complete") {
				try {
					if (setContentHeight()) {
						clearInterval(start);
					}
				} catch (err) {				
					return true;
				}
			}
		}

		//set suitable height for content div if get banner div height then return true, else return false
		function setContentHeight() {
			var head = document.getElementById("headerDiv");
			var navigation = document.getElementById("navigationBarWidget");
			var foot = document.getElementById("footerDiv");

			var headDivHeight = 0;
			var navigationBarHeight = 0;
			var footerTop = 0;
			var componentMargin = 7;

			if (null != head) {
				headDivHeight = document.getElementById("headerDiv").clientHeight;
			}

			if (null != navigation) {
				navigationBarHeight = document
						.getElementById("navigationBarWidget").clientHeight;
			}

			if (null != foot) {
				footerTop = document.getElementById("footerDiv")
						.getBoundingClientRect().top;
			}

			document.getElementById("content").style.height = footerTop
					- navigationBarHeight - parseInt(componentMargin) * 1
					- headDivHeight + "px";
			return true;
		}
	</script>
	
	<script type='text/javascript'>
		function menuHandler(item)
		{
			$('#win').window('open');
			$('#win').window('refresh', item.name);
		}			
		function menuHelpHandler(item)
		{
			window.open(item.name, "_blank", "");
		}
		
		$(function() 
		{
			var greetings = "", cur_time = new Date().getHours();
			if(cur_time >= 0 && cur_time <= 4 ) {
				greetings = "已经夜深了，请注意休息";
			} else if (cur_time > 4 && cur_time <= 7 ) {
				greetings = "早上好";
			} else if (cur_time > 7 && cur_time < 12 ) {
				greetings = "上午好";
			} else if (cur_time >= 12 && cur_time <= 18 ) {
				greetings = "下午好";
			} else {
				greetings = "晚上好";
			};
			$("#greetings").html(greetings + ",");
			
			var hoveredLi;
			var timoutid;
			$(".navigationMenu1 .mainMenuItem:not('.selected')").hover(
				function() {
					hoveredLi = $(this);
					timoutid = setTimeout(
						function() {
							hoveredLi.children("ul").slideDown();
							hoveredLi.children("ul").children("li").children("a").hover(function() {
								$(this).css("background-color","#1FAEFF");
								$(this).css("color","#fff");
							}, function() {
								$(this).css("background-color","#fff");
								$(this).css("color","#999");
							});
						},300);
				}, 
				function() {
					clearTimeout(timoutid);
					hoveredLi.children("ul").slideUp("fast");
				});
		});
	</script>
