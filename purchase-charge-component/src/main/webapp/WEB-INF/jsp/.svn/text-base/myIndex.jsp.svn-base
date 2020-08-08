<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
	$(function() 
	{
		$('#profileDom .tad.t1').mask('');
		$('#profileDom .tad.t2').mask('');
		$('#profileDom .tad.t3').mask('');
		$('#profileDom .tad.t4').mask('');
		$('#profileDom .tad.t5').mask('');
		$.post('<c:url value='/index/countRestAmountAndWorth.html' />', 
			function(result) 
			{
				$('#profileDom .tad.t1').unmask();
				$('#profileDom #restGoodsAmount').html(result[0]);
				$('#profileDom #restGoodsWorth').html(result[1]);
			}, 
			'json');
		
		$.post('<c:url value='/index/countDepositAndCash.html' />', 
			function(result) 
			{
				$('#profileDom .tad.t2').unmask();
				$('#profileDom #cash').html(result[0]);
				$('#profileDom #deposit').html(result[1]);
			}, 
			'json');
		
		$.post('<c:url value='/index/countReceivableAndPayment.html' />', 
			function(result) 
			{
				$('#profileDom .tad.t3').unmask();
				$('#profileDom #customerReceivable').html(result[0]);
				$('#profileDom #providerReceivable').html(result[1]);
			}, 
			'json');
		
		$.post('<c:url value='/index/countReceivableAndProfit.html' />', 
			function(result) 
			{
				$('#profileDom .tad.t4').unmask();
				$('#profileDom #orderReceivable').html(result[0]);
				$('#profileDom #orderProfit').html(result[1]);
			}, 
			'json');
		
		$.post('<c:url value='/index/countPurchaseMoneyAndGoodsIncrement.html' />', 
			function(result) 
			{
				$('#profileDom .tad.t5').unmask();
				$('#profileDom #purchaseMoney').html(result[0]);
				$('#profileDom #goodsIncrement').html(result[1]);
			}, 
			'json');
	});
</script>

<meta charset="utf-8">
<meta name="viewport"
	content="width=1280, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="renderer" content="webkit|ie-comp|ie-stand">

<div id="bd" class="index-body cf">
	<div class="col-main">
		<div class="main-wrap cf">
			<div class="m-top cf" id="profileDom">
				<table width="100%" border="0" cellspacing="0" cellpadding="20">
					<tbody>
						<tr>
							<sec:authorize url="/goods/goodsManagement.do">
								<td>
									<a class="tad t1" href="<c:url value='/goods/goodsManagement.do' />"><span>库存总量:<b id="restGoodsAmount"></b></span><span>库存成本:<b id="restGoodsWorth"></b></span></a>
								</td>
							</sec:authorize>
							<sec:authorize url="/finance/payAccountFinance.do">
								<td>
									<a class="tad t2" href="<c:url value='/finance/payAccountFinance.do' />"><span>现金:<b id="cash"></b></span><span>银行存款:<b id="deposit"></b></span></a>
								</td>
							</sec:authorize>
							<sec:authorize var="accessPayIn" url="/finance/payInManagement.do" />
							<sec:authorize var="accessPayOut" url="/finance/payOutManagement.do" />
							<c:if test="${accessPayIn eq true && accessPayOut eq true}">
								<td>
									<a class="tad t3" href="#">
										<span>客户欠款:<b id="customerReceivable"></b></span>
										<span>供应商欠款:<b id="providerReceivable"></b></span>
									</a>
								</td>
							</c:if>
							<sec:authorize url="/order/outOrderManagement.do">
								<td>
									<a class="tad t4" href="#"><span>销售收入(本月):<b id="orderReceivable"></b></span><span>销售毛利(本月):<b id="orderProfit"></b></span></a>
								</td>
							</sec:authorize>
							<sec:authorize url="/goods/inOrderManagement.do">
								<td>
									<a class="tad t5" href="<c:url value='/goods/inOrderManagement.do' />"><span>采购金额(本月):<b id="purchaseMoney"></b></span><span>商品种类(本月):<b id="goodsIncrement"></b></span></a>
								</td>
							</sec:authorize>
						</tr>
					</tbody>
				</table>
				<i></i>
			</div>
			<ul class="quick-links">
				<sec:authorize url="/goods.do">
					<li class="purchase-purchase">
						<a href="javascript:void(0)" onclick="newInOrder()"><span></span>采购入库</a>
					</li>
				</sec:authorize>
				<sec:authorize url="/order.do">
					<li class="sales-sales">
						<a href="javascript:void(0)" onclick="newOutOrder()"><span></span>销货出库</a>
					</li>
				</sec:authorize>
				<li class="storage-inventory">
					<a href="<c:url value='/report/storageStatisticQuery.do' />"><span></span>库存查询</a>
				</li>
				<!-- 
				<li class="storage-transfers">
					<a href="#"><span></span>仓库调拨</a>
				</li>
				<li class="feedback">
					<a href="#" id="feedback"><span></span>意见反馈</a>
				</li>
				<li class="storage-otherWarehouse">
					<a href="#"><span></span></a>
				</li>
				<li class="storage-otherOutbound">
					<a href="#"><span></span></a>
				</li>
				<li class="added-service">
					<a href="#"><span></span></a>
				</li>
				 -->
			</ul>
		</div>
	</div>
	<div class="col-extra">
		<div class="extra-wrap">
			<h2>快速查看</h2>
			<div class="list">
				<ul>
					<sec:authorize url="/goods/goodsManagement.do">
						<li><span class="bulk-import">导入</span>
							<a href="<c:url value='/goods/goodsManagement.do' />">商品管理</a></li>
					</sec:authorize>
					<sec:authorize url="/order/customerManagement.do">
						<li><span class="bulk-import">导入</span>
							<a href="<c:url value='/order/customerManagement.do' />">客户管理</a></li>
					</sec:authorize>
					<sec:authorize url="/goods/providerManagement.do">
						<li><span class="bulk-import">导入</span>
							<a href="<c:url value='/goods/providerManagement.do' />">供应商管理</a></li>
					</sec:authorize>
					<sec:authorize url="/order.do">
						<li><a href="<c:url value='/order.do' />">销售记录</a></li>
					</sec:authorize>
					<sec:authorize url="/goods.do">
						<li><a href="<c:url value='/goods.do' />">采购记录</a></li>
					</sec:authorize>
					<sec:authorize url="/finance/payInManagement.do">
						<li><a href="<c:url value='/finance/payInManagement.do' />">收款记录</a></li>
					</sec:authorize>
					<sec:authorize url="/finance/payOutManagement.do">
						<li><a href="<c:url value='/finance/payOutManagement.do' />">付款记录</a></li>
					</sec:authorize>
					<!--
					<li><a href="#">销售明细表</a></li>
					<li><a href="#">采购明细表</a></li>
					<li style="border-bottom: none; line-height: 42px;"><a href="#">调拨记录</a></li>
		        	<li><a href="">其他入库记录</a></li>
		        	<li><a href="">其他出库记录</a></li>
		        	<li><a href="">商品库存余额</a></li>
		        	<li><a href="">往来单位欠款</a></li>
		        	-->
				</ul>
			</div>
		</div>
	</div>
</div>

