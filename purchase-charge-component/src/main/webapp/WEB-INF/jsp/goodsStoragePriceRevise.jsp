	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<div style="width:100%; height:430px;">
		<table id="dg-storage-priceRevise" title="<spring:message code="goodsStorage.storagePriceRevise" />" class="easyui-datagrid" 
			rownumbers="true" singleSelect="true" showFooter="true" toolbar="#toolbar-storage-priceRevise" 
			data-options="fitColumns:true, fit:true, onBeforeLoad:onBeforeLoadStoragePriceRevise">
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true"></th>
					<th data-options="field:'bid', width:50">流水号</th>
					<th field="goods" width="100" data-options="formatter:formatter_storagePriceRevise_goods"><spring:message code="goodsStorage.goods" /></th>
					<th field="depository" width="50" data-options="formatter:formatter_storagePriceRevise_depository">仓库</th>
					<th field="currentPrice" width="50">调前价格</th>
					<th field="revisePrice" width="50">调后价格</th>
					<th field="dateCreated" width="60">调价日期</th>
					<th field="userCreated" width="50"><spring:message code="goodsStorage.operator" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="toolbar-storage-priceRevise" style="padding:5px;height:auto">
		<span>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" 
				onclick="viewGoodsStoragePrice()" title="">库存调价</a> 
		</span>
		<span id="storage-priceRevise-search" style="margin-left:50px">
			<spring:message code="goodsStorage.depository" />: 
			<input id="goodsDepository" name="goodsDepository" class="easyui-combobox" panelHeight="auto" editable="false" 
				data-options="valueField:'id',textField:'name',method:'post', onShowPanel:showAllDepository" style="width:100px" />
			&nbsp;
			<label><spring:message code="goods.type" />:</label> 
			<input id="goodsTypeId" name="goodsTypeId" class="easyui-combotree" style="width:200px" 
				data-options="panelHeight:400, formatter:goodsTypeWithGoodsAmountFormatter, 
					onShowPanel:showAllGoodsType, onChange:onChangeGoodsType_viewPrice2" />
			&nbsp;
			<spring:message code="goodsStorage.goods" />:
			<input id="goodsId" name="goodsId" class="easyui-combobox" 
				data-options="valueField:'id',
					textField:'name',
					panelHeight:'250',
					mode:'local', 
					filter: comboboxFilter,
					prompt:'输入名称查询', 
					formatter: goodsStorageFormatter,
					onShowPanel: onShowGoodsWhenPriceRevise2" />
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="loadGoodsStoragePriceRevise()"><spring:message code="query" /></a>
		</span>
	</div>
	
	<!-- 库存调价 -->
	<div id="dlg-view-storage-priceRevise" class="easyui-dialog" title="库存调价" 
		style="width: 800px; height: 500px; padding: 5px;" closed="true"
		buttons="#dlg-buttons-view-storage-priceRevise" data-options="modal:true">
		<table id="dg-goods-storage" title="" class="easyui-datagrid" 
			rownumbers="true" singleSelect="true" showFooter="true" toolbar="#toolbar-view-storage-priceRevise" 
			data-options="fitColumns:true, fit:true, onClickCell:goodsStoragePrice_onClickCell">
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true"></th>
					<th field="name" width="60" data-options="formatter:formatter_storagePriceRevise_goods"><spring:message code="goodsStorage.goods" /></th>
					<th field="shortCode" width="30" data-options="formatter:formatter_storagePriceRevise_goodsCode"><spring:message code="goods.shortCode" /></th>
					<th field="specificationModel" width="30" data-options="formatter:formatter_storagePriceRevise_goodsSpecification"><spring:message code="goods.specificationModel" /></th>
					<th field="goodsType" width="30" data-options="formatter:formatter_storagePriceRevise_goodsType"><spring:message code="goods.type" /></th>
					<th field="goodsDeposity" width="30" data-options="formatter:formatter_storagePriceRevise_depository"><spring:message code="goods.depository" /></th>
					<th field="currentPrice" width="30" data-options="">调前价格</th>
					<th field="goodsUnit" width="30" data-options="formatter:formatter_storagePriceRevise_goodsUnit"><spring:message code="goods.unit" /></th>
					<th field="revisePrice" width="30" data-options="editor:{type:'numberbox',options:{min:0, onChange:goodsStorage_onChangeRevisePrice}}">调后价格</th>
					<th field="balancePrice" width="30" data-options="">价格差额</th>
				</tr>
			</thead>
		</table>
		<div id="goodsStorage-table" style="margin:15px;">
		</div>
	</div>
	<div id="toolbar-view-storage-priceRevise" style="padding:5px;height:auto">
		<span style="width: 100%; display: inline-block;">
			<label><spring:message code="goods.type" />:</label> 
			<input id="goodsTypeId" name="goodsTypeId" class="easyui-combotree" style="width:200px" 
				data-options="panelHeight:400, formatter:goodsTypeWithGoodsAmountFormatter, 
					onShowPanel:showAllGoodsType, onChange:onChangeGoodsType_viewPrice" />
			&nbsp;
			<label><spring:message code="goodsStorage.goods" />:</label> 
			<input id="goodsId" name="goodsId" class="easyui-combobox" data-options="
					valueField:'id',
					textField:'name',
					panelHeight:'250',
					mode:'local', 
					filter: comboboxFilter,
					prompt:'输入名称查询', 
					formatter: goodsStorageFormatter,
					onShowPanel: onShowGoodsWhenPriceRevise" />
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="loadGoodsStoragePrice()">查询</a>
		</span>
	</div>
	<div id="dlg-buttons-view-storage-priceRevise">
		<c:if test="${sessionScope.login_user.admin}">
			<a href="javascript:void(0)" id="generateStoragePriceRevise" class="easyui-linkbutton" iconCls="icon-save" 
				data-options="disabled:true" onclick="generateStoragePriceRevise()">更新库存价格</a>
		</c:if>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" 
			onclick="javascript:$('#dlg-view-storage-priceRevise').dialog('close')"><spring:message code="close" /></a>
	</div>
	<!-- 库存调价 -->
	