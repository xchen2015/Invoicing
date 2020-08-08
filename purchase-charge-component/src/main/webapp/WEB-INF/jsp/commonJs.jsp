<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>

    <script type="text/javascript" src="<c:url value='/common/js/purchase.charge.js' />"></script>
	
	<script type="text/javascript">
		var onClickGoods = function() 
		{
			$(this).combobox('reload', '<c:url value='/goods/getAllModel.html' />');
		}
		var onShowCustomerType = function() 
		{
			$(this).combobox('reload', '<c:url value='/customerType/getAllModel.html' />');
		}
		var onShowProviderType = function() 
		{
			$(this).combobox('reload', '<c:url value='/providerType/getAllModel.html' />');
		}
		var onShowPaymentAccount = function() 
		{
			$(this).combobox('reload', '<c:url value='/paymentAccount/getAllModel.html' />');
		}
		var onShowPaymentWay = function() 
		{
			$(this).combobox('reload', '<c:url value='/paymentWay/getAllModel.html' />');
		}
		var showAllDepository = function () 
		{
			$(this).combobox('reload', '<c:url value='/goodsDepository/getAllModel.html' />');
		}
		var showAllGoodsType2 = function () 
		{
			$(this).combotree('reload', '<c:url value='/goodsType/getAllModel.html' />');
		}
		var showAllGoodsType = function () 
		{
			$(this).combotree('reload', '<c:url value='/goodsType/getAllGoodsType.html' />');
		}
		var onShowUserPanel = function() 
		{
			$(this).combobox('reload', '<c:url value='/user/getAllModel.html' />');
		}
		var onShowAllCustomer = function() 
		{
			$(this).combobox('reload', '<c:url value='/customer/getAllModel.html' />');
		}
		var onShowAllProvider = function() 
		{
			$(this).combobox('reload', '<c:url value='/provider/getAllModel.html' />');
		}
		var onShowAllDeliveryCompany = function() 
		{
			$(this).combobox('reload', '<c:url value='/deliveryCompany/getAllModel.html' />');
		}
		var onShowAllCustomerLevel = function() 
		{
			$(this).combobox('reload', '<c:url value='/customerLevel/getAllModel.html' />');
		}
	</script>
