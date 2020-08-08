	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<div style="width:100%; height:430px;">
		<table id="dg-storage-check" title="<spring:message code="goodsStorage.storageCheck" />" class="easyui-datagrid" 
			rownumbers="true" singleSelect="true" showFooter="true" toolbar="#toolbar-storage-check" 
			data-options="fitColumns:true, fit:true, onBeforeLoad:onBeforeLoadStorageCheck">
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true"></th>
					<th data-options="field:'bid', width:50">流水号</th>
					<th field="goods" width="100" data-options="formatter:formatter_storageCheck_goods"><spring:message code="goodsStorage.goods" /></th>
					<th field="depository" width="50" data-options="formatter:formatter_storageCheck_depository">仓库</th>
					<th field="currentAmount" width="50">盘前数量</th>
					<th field="actualAmount" width="50">盘后数量</th>
					<th field="dateCreated" width="60">盘点日期</th>
					<th field="userCreated" width="50"><spring:message code="goodsStorage.operator" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="toolbar-storage-check" style="padding:5px;height:auto">
		<span>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" 
				onclick="viewGoodsStorage()" title=""><spring:message code="goods.searchGoodsStorage" /></a> 
		</span>
		<span id="storage-check-search" style="margin-left:50px">
			<spring:message code="goodsStorage.depository" />: 
			<input id="goodsDepository" name="goodsDepository" class="easyui-combobox" panelHeight="auto" editable="false" 
				data-options="valueField:'id',textField:'name',method:'post', onShowPanel:showAllDepository" style="width:100px" />
			&nbsp;
			<label><spring:message code="goods.type" />:</label> 
			<input id="goodsTypeId" name="goodsTypeId" class="easyui-combotree" style="width:200px" 
				data-options="panelHeight:400, formatter:goodsTypeWithGoodsAmountFormatter, 
					onShowPanel:showAllGoodsType, onChange:onChangeGoodsType_viewStorage2" />
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
					onShowPanel: onShowGoodsWhenCheck2" />
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="loadGoodsStorageCheck()"><spring:message code="query" /></a>
		</span>
	</div>
	
	<!-- 盘库存 -->
	<div id="dlg-view-storage-rest" class="easyui-dialog" title="<spring:message code="goods.searchGoodsStorage" />" 
		style="width: 800px; height: 500px; padding: 5px;" closed="true"
		buttons="#dlg-buttons-view-storage-rest" data-options="modal:true">
		<table id="dg-goods-storage" title="" class="easyui-datagrid" 
			rownumbers="true" singleSelect="true" showFooter="true" toolbar="#toolbar-view-storage-rest" 
			data-options="fitColumns:true, fit:true, onClickCell: goodsStorage_onClickCell">
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true"></th>
					<th field="name" width="60" data-options="formatter:formatter_storageCheck_goods"><spring:message code="goodsStorage.goods" /></th>
					<th field="shortCode" width="30" data-options="formatter:formatter_storageCheck_goodsCode"><spring:message code="goods.shortCode" /></th>
					<th field="specificationModel" width="30" data-options="formatter:formatter_storageCheck_goodsSpecification"><spring:message code="goods.specificationModel" /></th>
					<th field="goodsType" width="30" data-options="formatter:formatter_storageCheck_goodsType"><spring:message code="goods.type" /></th>
					<th field="goodsDeposity" width="30" data-options="formatter:formatter_storageCheck_depository"><spring:message code="goods.depository" /></th>
					<th field="currentAmount" width="30" data-options=""><spring:message code="goodsStorage.currentStock" /></th>
					<th field="goodsUnit" width="30" data-options="formatter:formatter_storageCheck_goodsUnit"><spring:message code="goods.unit" /></th>
					<th field="actualAmount" width="30" data-options="editor:{type:'numberbox',options:{min:0, onChange:goodsStorage_onChangeActualAmount}}">盘点库存</th>
					<th field="balanceAmount" width="30" data-options="">盘盈盘亏</th>
				</tr>
			</thead>
		</table>
		<div id="goodsStorage-table" style="margin:15px;">
		</div>
	</div>
	<div id="toolbar-view-storage-rest" style="padding:5px;height:auto">
		<span style="width: 100%; display: inline-block;">
			<label><spring:message code="goods.type" />:</label> 
			<input id="goodsTypeId" name="goodsTypeId" class="easyui-combotree" style="width:200px" 
				data-options="panelHeight:400, formatter:goodsTypeWithGoodsAmountFormatter, 
					onShowPanel:showAllGoodsType, onChange:onChangeGoodsType_viewStorage" />
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
					onShowPanel: onShowGoodsWhenCheck" />
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="loadGoodsStorage()">查询</a>
		</span>
	</div>
	<div id="dlg-buttons-view-storage-rest">
		<c:if test="${sessionScope.login_user.admin}">
			<a href="javascript:void(0)" id="generateStorageCheckRecord" class="easyui-linkbutton" iconCls="icon-save" 
				data-options="disabled:true" onclick="generateGoodsStorageCheckRecord()">更新库存数量</a>
		</c:if>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" 
			onclick="javascript:$('#dlg-view-storage-rest').dialog('close')"><spring:message code="close" /></a>
	</div>
	<!-- 盘库存 -->
	