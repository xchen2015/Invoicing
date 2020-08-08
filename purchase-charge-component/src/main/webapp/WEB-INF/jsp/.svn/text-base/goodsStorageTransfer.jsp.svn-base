	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<div style="width:100%; height:430px;">
		<table id="dg-storage-transfer" title="<spring:message code="goodsStorage.storageTransfer" />" class="easyui-datagrid" 
			rownumbers="true" singleSelect="true" showFooter="true" toolbar="#toolbar-storage-transfer" 
			data-options="fitColumns:true, fit:true, onBeforeLoad:onBeforeLoadStorageTransfer">
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true"></th>
					<th data-options="field:'bid', width:50">流水号</th>
					<th field="goodsName" width="80" data-options="formatter:formatter_storageTransfer_goodsName"><spring:message code="goodsStorage.goods" /></th>
					<th field="fromDepository" width="50" data-options="formatter:formatter_storageTransfer_depositoryFrom"><spring:message code="goodsStorage.fromDepository" /></th>
					<th field="fromDepositoryAmount" width="30">调拨前数量</th>
					<th field="toDepositoryAmount" width="30">调拨后数量</th>
					<th field="transferAmount" width="30">调拨数量</th>
					<th field="dateCreated" width="50"><spring:message code="goodsStorage.transferDate" /></th>
					<th field="userCreated" width="50"><spring:message code="goodsStorage.operator" /></th>
					<th field="comment" width="80"><spring:message code="goodsStorage.comment" /></th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="toolbar-storage-transfer" style="padding:5px;height:auto">
		<span>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" 
				onclick="newModel('#dlg-storage-new-transfer', '<spring:message code="goodsStorage.newTransfer" />', '#fm-storage-transfer', '<c:url value='/goodsStorage/addModel.html' />')" title=""><spring:message code="goodsStorage.newTransfer" /></a> 
		</span>
		<span id="storage-transfer-search" style="margin-left:50px">
			<spring:message code="goodsStorage.depository" />: 
			<input id="goodsDepository" name="goodsDepository" class="easyui-combobox" panelHeight="auto" editable="false" 
				data-options="valueField:'id',textField:'name',method:'post', onShowPanel:showAllDepository" style="width:100px" />
			&nbsp;
			<label><spring:message code="goods.type" />:</label> 
			<input id="goodsTypeId" name="goodsTypeId" class="easyui-combotree" style="width:200px" 
				data-options="panelHeight:400, formatter:goodsTypeWithGoodsAmountFormatter, 
					onShowPanel:showAllGoodsType, onChange:onChangeGoodsType_viewTransfer" />
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
					onShowPanel: onShowGoodsWhenTransfer" />
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="loadGoodsStorageTransfer()"><spring:message code="query" /></a>
		</span>
	</div>
	
	<div id="dlg-storage-new-transfer" class="easyui-dialog" title="<spring:message code="goodsStorage.newTransfer" />" 
		style="width: 340px; height: 380px; padding: 15px 5px" closed="true"
		buttons="#dlg-buttons-storage-new-transfer" data-options="modal:true">
		<div style="width:100%;height:100%">
			<form id="fm-storage-transfer" class="fm" method="post" style="margin:0 auto;padding:0px;width:100%;height:100%;" novalidate>
				<div class="fitem divHidden">
					<input id="storageTransferId" name="id" value="0" />
				</div>
				<div class="fitem css-dlg-formField2">
					<div>
						<label><spring:message code="goodsStorage.goods" />:</label>
						<input id="goodsName" name="goodsBean.id" class="easyui-combobox" required="true" 
							data-options="valueField:'id',
								textField:'name',
								panelHeight:200,
								prompt:'输入名称查询', 
								mode:'local', 
								formatter: goodsStorageFormatter,
								filter: comboboxFilter,
								url:'<c:url value='/goods/getAllModel.html' />'" />
					</div>
				</div>
				<div class="fitem css-dlg-formField2">
					<div>
						<label><spring:message code="goodsStorage.fromDepository" />:</label>
						<input id="fromDepository" name="fromDepositoryBean.id" class="easyui-combobox" panelHeight="auto" editable="false" required="true" 
							data-options="valueField:'id',textField:'name',method:'post', onShowPanel:showAllDepository" />
					</div>
				</div>
				<div class="fitem css-dlg-formField2">
					<div>
						<label><spring:message code="goodsStorage.toDepository" />:</label>
						<input id="toDepository" name="toDepositoryBean.id" class="easyui-combobox" panelHeight="auto" editable="false" required="true" 
							data-options="valueField:'id',textField:'name',method:'post', onShowPanel:showAllDepository" />
					</div>
				</div>
				<div class="fitem css-dlg-formField2">
					<div>
						<label><spring:message code="goodsStorage.amount" />:</label>
						<input id="transferAmount" name="transferAmount" class="easyui-numberbox" required="true" />
					</div>
				</div>
				<div class="fitem css-dlg-formField2">
					<div>
						<label><spring:message code="goodsStorage.comment" />:</label>
						<input id="comment" name="comment" class="easyui-textbox" data-options="multiline:true" style="height:50px" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="dlg-buttons-storage-new-transfer">
		<a href="javascript:void(0)" id="" class="easyui-linkbutton" iconCls="icon-save" 
			onclick="saveModel('#dg-storage-transfer', '#dlg-storage-new-transfer', '#fm-storage-transfer')"><spring:message code="save" /></a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" 
			onclick="javascript:$('#dlg-storage-new-transfer').dialog('close')"><spring:message code="cancel" /></a>
	</div>
