<!-- Hosted Mode test file for the PurchaseCharge component -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <!--                                           -->
    <!-- Any title is fine                         -->
    <!--                                           -->
    <title>PurchaseCharge - 买卖管理系统</title>
    <jsp:include page="common.jsp" />
<script type="text/javascript">
	var goodsGridDetailFormatter = function(index, row) {
		if (row.goods) {
			return '<div class="ddv-goods" style="padding:5px 0"></div>';
		}
	}
	var goodsGridExpandRow = function(index, row) {
		var ddv = $(this).datagrid('getRowDetail', index).find('div.ddv-goods');
		ddv.panel({
			height : 65,
			border : false,
			cache : false,
			content : "<style type='text/css'>.dv-table-goods td{border:0;}.dv-label-goods{font-weight:normal;color:#15428B;width:10%;}.dv-nonlabel-goods{width:40%;}</style>"
					+ "<table class='dv-table-goods' border='0' style='width:100%;'>"
					+ "<tr><td class='dv-label-goods'>条形码:</td><td class='dv-nonlabel-goods'>"
					+ row.barCode
					+ "</td></tr>"
					+ "<tr><td class='dv-label-goods'>制造者:</td><td class='dv-nonlabel-goods'>"
					+ row.maker
					+ "</td><td class='dv-label-goods'>规格:</td><td class='dv-nonlabel-goods'>"
					+ row.specification
					+ "</td></tr>"
					+ "<tr><td class='dv-label-goods'>参数:</td><td class='dv-nonlabel-goods'>"
					+ row.technicalParameters
					+ "</td><td class='dv-label-goods'></td><td class='dv-nonlabel-goods'></td></tr>"
					+ "</table>",
			onLoad : function() {
				$('#dg-goods').datagrid('fixDetailRowHeight', index);
			}
		});
		$('#dg-goods').datagrid('fixDetailRowHeight', index);
	}
	
	/**
	mode:'remote', 
	prompt:'输入名称或编码查询',
	loader:goodsLoader,
	formatter: goodsStorageFormatter,
	onBeforeLoad: function(param){
		setAutoCompleteUrl ('<c:url value='/goods/getGoodsByNameLike.html' />');
	}
	*/
</script>
</head>

  <!--                                           -->
  <!-- The body can have arbitrary html, or      -->
  <!-- you can leave the body empty if you want  -->
  <!-- to create a completely dynamic UI.        -->
  <!--                                           -->
  <body>
    <table width="100%">
        <%-- 
        <tr>
            <td><jsp:include page="/user/.html" /></td>
        </tr> 
        <tr>
            <td><jsp:include page="/role/.html" /></td>
        </tr> 
        <tr>
            <td><jsp:include page="/authority/.html" /></td>
        </tr> 
        <tr>
            <td><jsp:include page="/resource/.html" /></td>
        </tr>
        <tr>
            <td><jsp:include page="/password/.html" /></td>
        </tr>
        --%>
        
        <%-- 
        <tr>
            <td><jsp:include page="/reportAccounting/reportAccountingQuery.html" /></td>
        </tr>
        <tr>
            <td><jsp:include page="/reportIndex/.html" /></td>
        </tr>
        <tr>
            <td><jsp:include page="/goods/.html" /></td>
        </tr>
        <tr>
            <td><jsp:include page="/goodsUnit/.html" /></td>
        </tr>
        <tr>
            <td><jsp:include page="/goodsType/.html" /></td>
        </tr>
        <tr>
            <td><jsp:include page="/goodsReport/.html" /></td>
        </tr>
        <tr>
            <td><jsp:include page="/goodsDepository/.html" /></td>
        </tr>
         --%>
        
        <%-- <tr>
            <td><jsp:include page="/logEventType/.html" /></td>
        </tr>
        <tr>
            <td><jsp:include page="/log/.html" /></td>
        </tr>
         --%>
        
        <%-- 
        <tr>
            <td><jsp:include page="/customerType/.html" /></td>
        </tr>
        <tr>
            <td><jsp:include page="/customer/.html" /></td>
        </tr>
        <tr>
            <td><jsp:include page="/provider/.html" /></td>
        </tr>
        <tr>
            <td><jsp:include page="/providerType/.html" /></td>
        </tr>
         --%>
        
        <%-- 
        <tr>
            <td><jsp:include page="/followWay/.html" /></td>
        </tr>
        <tr>
            <td><jsp:include page="/memorialDayType/.html" /></td>
        </tr>
        <tr>
            <td><jsp:include page="/region/.html" /></td>
        </tr>
        <tr>
            <td><jsp:include page="/paymentAccount/paymentAccountView.html" /></td>
        </tr>
        <tr>
            <td><jsp:include page="/outOrder/.html" /></td>
        </tr>
         --%>
        <tr>
            <td><jsp:include page="/inOrder/getView.html" /></td>
        </tr>
         
         <%-- 账单 --%>
        <%-- 
        <tr>
            <td><jsp:include page="/expenseType/.html" /></td>
        </tr>
        <tr>
            <td><jsp:include page="/expense/.html" /></td>
        </tr>
        <tr>
        	<td><jsp:include page="/dataBackupAndLoad/.html" /></td>
        </tr>
         --%>
         
    </table>
    
    <!-- RECOMMENDED if your web app will not function without JavaScript enabled -->
    <noscript>
      <div style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
        Your web browser must have JavaScript enabled
        in order for this application to display correctly.
      </div>
    </noscript>
  </body>
</html>
