	<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
	<!-- 库存预警 -->
	<div id="dlg-view-storage-warn" class="easyui-dialog" title="<spring:message code="goods.warnGoodsStorage" />" 
		style="width: 800px; height: 500px; padding: 5px;" closed="true"
		buttons="#dlg-buttons-view-storage-warn" data-options="modal:true">
		<table id="dg-goods-storage" title="" class="easyui-datagrid" 
			rownumbers="true" singleSelect="true" showFooter="true" 
			data-options="fitColumns:true, fit:true">
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true"></th>
					<th field="name" width="80"><spring:message code="goodsStorage.goods" /></th>
					<th field="shortCode" width="30"><spring:message code="goods.shortCode" /></th>
					<th field="specificationModel" width="30"><spring:message code="goods.specificationModel" /></th>
					<th field="goodsUnit" width="30" data-options="formatter:formatter_storageWarn_goodsUnit"><spring:message code="goods.unit" /></th>
					<th field="minStock" width="30"><spring:message code="goodsStorage.minStock" /></th>
					<th field="maxStock" width="30"><spring:message code="goodsStorage.maxStock" /></th>
					<th field="totalStock" width="30"><spring:message code="goodsStorage.currentStock" /></th>
					<th field="balanceStock" width="30" data-options="formatter:formatter_storageWarn_balanceStock">超限数量</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="dlg-buttons-view-storage-warn">
		<span id="autoShowStorageWarn" style="float:left;"><label><input type="checkbox" name="storageWarn_autoShowWindow" onclick="notAutoShowStorageWarn(this)" />本次会话不再自动显示此窗口</label></span>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" 
			onclick="javascript:$('#dlg-view-storage-warn').dialog('close')"><spring:message code="close" /></a>
	</div>
	<!-- 库存预警 -->
	