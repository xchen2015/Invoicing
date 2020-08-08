<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<% response.setContentType ("text/javascript"); %>

	$.extend($.fn.textbox.defaults.rules, {
		checkPrice : {
			validator : function(value, param) {
				var importPrice;
				if (param[0].startsWith('#')) {
					importPrice = $(param[0]).numberbox('getValue');
				}
				if (param[0] != '') {
					return parseNumber(value) >= importPrice;
				}
			},
			message : '销售价不能低于采购价'
		},
		checkStock : {
			validator : function(value, param) {
				var minStock;
				if (param[0].startsWith('#')) {
					minStock = $(param[0]).numberbox('getValue');
				}
				if (param[0] != '') {
					return parseNumber(value) >= minStock;
				}
			},
			message : '最高库存不能小于最低库存'
		}
	});

	var newGoodsCallback = function() {
		//$('#fm-goods input.easyui-combobox').combobox('reload');
		$('#dlg-goods #fm-goods #dg-goodsStorage').datagrid('getPanel').panel('setTitle', '初始库存');
		var goods = $('#dg-goods').datagrid('getSelected');
		var goodsType = $('#panelEast-goodsType #tree_goodsType').tree('getSelected');
		if (goodsType) {
			//fillSelectedTreeNode ("<c:url value='/goodsType/getModelById.html' />?goodsTypeId="+goodsType.id, '#dlg-goods #fm-goods #goodsTypeId', goodsType.id);
			$('#dlg-goods #fm-goods #goodsTypeId').combotree('setValue', goodsType.id);
			$('#dlg-goods #fm-goods #goodsTypeId').combotree('setText', goodsType.text);
			goodsType = $('#dlg-goods #fm-goods #goodsTypeId').combotree('getValue');
		}

		resetGoodsStorageEditor();
		$('#dlg-goods #fm-goods #dg-goodsStorage').datagrid('loadData', []);
		$('#dlg-goods #fm-goods #btn-addGoodsStorage').show();
		$('#dlg-goods #fm-goods #dg-goodsStorage').datagrid('showColumn', 'action');
		$('#dlg-goods #fm-goods #minStock').numberbox('setValue', 0);
		$('#dlg-goods #fm-goods #maxStock').numberbox('setValue', 20);
		$('#dlg-goods #fm-goods #importPrice').numberbox('setValue', 0);
		$('#dlg-goods #fm-goods #retailPrice').numberbox('setValue', 0);
		$('#dlg-goods #fm-goods #tradePrice').numberbox('setValue', 0);
	}
	var editGoodsPredo = function() {
		var goods = $('#dg-goods').datagrid('getSelected');
		if (goods) {
			editModel('#dg-goods', '#dlg-goods',
					'<spring:message code="goods.editGoods" />', '#fm-goods',
					'<c:url value='/goods/updateModel.html' />',
					editGoodsCallback);
		}
	}
	var deleteGoodsPredo = function() {
		destroyMultipleModel('#dg-goods', '<spring:message code="goods.goods" />',
				'<c:url value='/goods/deleteModels.html' />',
				saveGoodsCallback);
	}
	var editGoodsCallback = function() {
		//$('#fm-goods input.easyui-combobox').combobox('reload');
		$('#dlg-goods #fm-goods #dg-goodsStorage').datagrid('getPanel').panel('setTitle', '当前库存');
		var goods = $('#dg-goods').datagrid('getSelected');
		if (goods.typeBean) {
			//fillSelectedTreeNode ("<c:url value='/goodsType/getModelById.html' />?goodsTypeId="+goods.typeBean.id, '#dlg-goods #fm-goods #goodsTypeId', goods.typeBean.id);
			$('#dlg-goods #fm-goods #goodsTypeId').combotree('setValue', goods.typeBean.id);
			$('#dlg-goods #fm-goods #goodsTypeId').combotree('setText', goods.typeBean.text);
			goodsType = $('#dlg-goods #fm-goods #goodsTypeId').combotree('getValue');
		}
		if (goods.unitBean) {
			$('#dlg-goods #fm-goods #unit').combobox('setValue', goods.unitBean.id);
			$('#dlg-goods #fm-goods #unit').combobox('setText', goods.unitBean.name);
		}
		if (goods.preferedDepositoryBean) {
			$('#dlg-goods #fm-goods #goodsDepositoryId').combobox('setValue', goods.preferedDepositoryBean.id);
			$('#dlg-goods #fm-goods #goodsDepositoryId').combobox('setText', goods.preferedDepositoryBean.name);
		}

		resetGoodsStorageEditor();
		$('#dlg-goods #fm-goods #dg-goodsStorage').datagrid('loadData', []);
		$('#dlg-goods #fm-goods #btn-addGoodsStorage').show();
		$('#dlg-goods #fm-goods #dg-goodsStorage').datagrid('showColumn', 'action');
		if (goods.storageBeans && goods.storageBeans.length > 0) {
			$('#dlg-goods #fm-goods #btn-addGoodsStorage').hide();
			$('#dlg-goods #fm-goods #dg-goodsStorage').datagrid('loadData', goods.storageBeans);
			$('#dlg-goods #fm-goods #dg-goodsStorage').datagrid('hideColumn', 'action');
		}
	}

	var goodsType;
	var saveGoodsCallback = function() {
		$('#dg-goods').datagrid('reload');
		if (goodsType != undefined) {
			$('#panelEast-goodsType #tree_goodsType').tree('reload', '');
		}
	}
	var onChangeGoodsType = function(newValue, oldValue) {
		goodsType = newValue;
	}
	var cellFormatter_goodsDepository = function(value, row, index) {
		if (row.preferedDepositoryBean) {
			return row.preferedDepositoryBean.name;
		}
	}

	var onClickGoodsRow = function(row) {
		$('#toolbar-goods #btn-editGoods').linkbutton('enable');
		$('#toolbar-goods #btn-deleteGoods').linkbutton('enable');
		$('#toolbar-goods #btn-viewStorageCourse').linkbutton('enable');
		//$('#toolbar-goods #btn-viewGoodsStorage').linkbutton('enable');
	}
	var onLoadGoodsSuccess = function(data) {
		$('#toolbar-goods #btn-editGoods').linkbutton('disable');
		$('#toolbar-goods #btn-deleteGoods').linkbutton('disable');
		$('#toolbar-goods #btn-viewStorageCourse').linkbutton('disable');
		//$('#toolbar-goods #btn-viewGoodsStorage').linkbutton('disable');
	}

	// goods storage list
	function formatter_goodsAction(value, row, index) {
		if (row.editing) {
			if (row.id == '') {
				var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="saveGoods(this)">&nbsp;</a> ';
				var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteGoods(this)">&nbsp;</a>';
				return s + d;
			} else {
				var s = '<a href="#" class="save16" style="display:inline-block;width:16px;" title="保存" onclick="saveGoods(this,' + row.id + ')">&nbsp;</a> ';
				var c = '<a href="#" class="icon-no" style="display:inline-block;width:16px;" title="取消" onclick="cancelGoods(this)">&nbsp;</a>';
				return s + c;
			}
		} else {
			if (row.id == '') {
				var e = '<a href="#" class="icon-edit" style="display:inline-block;width:16px;" title="编辑" onclick="editGoods(this)">&nbsp;</a> ';
				var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteGoods(this)">&nbsp;</a>';
				return e + d;
			} else {
				var e = '<a href="#" class="icon-edit" style="display:inline-block;width:16px;" title="编辑" onclick="editGoods(this)">&nbsp;</a> ';
				var d = '<a href="#" class="icon-remove" style="display:inline-block;width:16px;" title="删除" onclick="deleteGoods(this,' + row.id + ')">&nbsp;</a>';
				return e + d;
			}
		}
	}
	var goods_editingIndex = undefined;
	var goods_rowId = undefined;
	var goods_onBeforeEdit = function(index, row) {
		row.editing = true;
		goods_editingIndex = index;
		goods_rowId = row.id;
		updateGoodsActions(index);
	}
	var goods_onAfterEdit = function(index, row) {
		row.editing = false;
		goods_editingIndex = undefined;
		goods_rowId = '';
		updateGoodsActions(index);
	}
	var goods_onCancelEdit = function(index, row) {
		row.editing = false;
		goods_editingIndex = undefined;
		goods_rowId = '';
		updateGoodsActions(index);
	}
	var resetGoodsStorageEditor = function() {
		goods_editingIndex = undefined;
		goods_rowId = undefined;
	}
	function updateGoodsActions(index) {
		$('#dg-goodsStorage').datagrid('updateRow', {
			index : index,
			row : {}
		});
	}
	function editGoods(target) {
		if (goods_editingIndex == undefined) {
			$('#dg-goodsStorage').datagrid('beginEdit', getRowIndex(target));
		}
	}
	function deleteGoods(target, rowId) {
		if (rowId == undefined) {
			$('#dg-goodsStorage').datagrid('deleteRow', getRowIndex(target));
			$('#dg-goodsStorage').datagrid('acceptChanges');
		} else {
			if (goods_editingIndex == undefined) {
				$.messager.confirm('确认', '您确认要删除?', function(r) {
					if (r) {
						$('#dg-goodsStorage').datagrid('deleteRow', getRowIndex(target));
						$('#dg-goodsStorage').datagrid('acceptChanges');
						//console.log(rowId);
						//ajaxPostRequest ('<c:url value='/goods/deleteModels.html' />', {ids:rowId}, reloadGoods);
					}
				});
			}
		}
		setGoodsStorageList();
	}
	function saveGoods(target, rowId) {
		//console.log(rowId);
		if ($('#dg-goodsStorage').datagrid('validateRow', getRowIndex(target))) {
			var goodsNameEditor = $('#dg-goodsStorage').datagrid('getEditor', {
				index : getRowIndex(target),
				field : 'depository'
			});
			var goodsName = $(goodsNameEditor.target).combobox('getText');
			$('#dg-goodsStorage').datagrid('getRows')[goods_editingIndex]['name'] = goodsName;
			//console.log(goodsName);
			$('#dg-goodsStorage').datagrid('endEdit', getRowIndex(target));
			if (rowId == undefined) {
				//ajaxPostRequest ('<c:url value='/goods/addModel.html' />', {id:rowId, name:goodsName}, reloadGoods);
			} else {
				//ajaxPostRequest ('<c:url value='/goods/updateModel.html' />', {id:rowId, name:goodsName}, reloadGoods);
			}
			setGoodsStorageList();
		}
	}
	
    function onChangeGoodsUnitPrice (newValue, oldValue) 
	{
		if ($('#dg-goodsStorage').datagrid('validateRow', goods_editingIndex)) {
			var goodsAmountEditor = $('#dg-goodsStorage').datagrid('getEditor', {
				index : goods_editingIndex,
				field : 'currentAmount'
			});
			var goodsWorthEditor = $('#dg-goodsStorage').datagrid('getEditor', {
				index : goods_editingIndex,
				field : 'worth'
			});
			var amount = goodsAmountEditor.target.val();
			goodsWorthEditor.target.numberbox('setValue', new Number(newValue).mul(amount));
		}
	}
    function onChangeGoodsAmount (newValue, oldValue) 
	{
		if ($('#dg-goodsStorage').datagrid('validateRow', goods_editingIndex)) {
			var goodsUnitPriceEditor = $('#dg-goodsStorage').datagrid('getEditor', {
				index : goods_editingIndex,
				field : 'currentPrice'
			});
			var goodsWorthEditor = $('#dg-goodsStorage').datagrid('getEditor', {
				index : goods_editingIndex,
				field : 'worth'
			});
			var price = goodsUnitPriceEditor.target.val();
			goodsWorthEditor.target.numberbox('setValue', new Number(newValue).mul(price));
		}
	}
	
	function cancelGoods(target) {
		$('#dg-goodsStorage').datagrid('cancelEdit', getRowIndex(target));
	}
	function insertGoods() {
		if (goods_editingIndex == undefined) {
			var row = $('#dg-goodsStorage').datagrid('getSelected');
			if (row) {
				var index = $('#dg-goodsStorage').datagrid('getRowIndex', row);
			} else {
				index = 0;
			}
			$('#dg-goodsStorage').datagrid('insertRow', {
				index : index,
				row : {
					id : ''
				}
			});
			$('#dg-goodsStorage').datagrid('selectRow', index);
			$('#dg-goodsStorage').datagrid('beginEdit', index);
		}
	}
	var setGoodsStorageList = function() {
		var allRows = $('#dg-goodsStorage').datagrid('getRows');
		var storageList = "";
		//console.log(allRows.length);
		for ( var i = 0; i < allRows.length; i++) {
			var contact = "";
			var item = allRows[i];
			contact += (item.id + "," + item.depository + "," + item.currentAmount + "," + item.currentPrice + "," + item.worth);
			storageList += contact + ";";
		}
		//console.log(storageList.substring(0, storageList.length-1));
		$('#dlg-goods #fm-goods #storageList').val(storageList);
	}
	function cellFormatter_goodsStock(value, row, index) {
		var storageInfo = '';
		if(value > 0) 
		{
			if(row.storageBeans && row.storageBeans.length > 0) 
			{
				for(var i = 0; i < row.storageBeans.length; i ++) 
				{
					var storageBean = row.storageBeans[i];
					if(row.unitBean) 
					{
						storageInfo += (storageBean.depositoryBean.name + " - " + storageBean.currentAmount + row.unitBean.name + "&#10;");
					}
					else 
					{
						storageInfo += (storageBean.depositoryBean.name + " - " + storageBean.currentAmount + "&#10;");
					}
				}
			}
		}
		return '<span style="font-weight:bold; display:block" title="'+ storageInfo +'">' + value + '</span>';
	}
	function formatter_goods_storage(value, row, index) {
		if (row.depositoryBean) {
			return row.depositoryBean.name;
		}
		return row.name;
	}

	// check show has storage btn
	var onCheckShowHasStorage = function(checkBtn) {
		$('#dg-goods').datagrid(
				'load',
				{
					searchKey : $('#toolbar-goods #goodsSearchBox').searchbox('getValue')
				});
	}

	var onBeforeLoadGoods = function(param) {
		var checkBtn = $('#tb-view-hasStorage #checkHasStorage')[0];
		var showHasStorage = checkBtn.checked ? '1' : '0';

		var goodsTypeId = '';
		if (selectedGoodsType) {
			goodsTypeId = selectedGoodsType.id;
		}

		if (param.page == undefined || param.page == 0) {
			param.page = 1;
		}
		$('#dg-goods').datagrid('options').url = "<c:url value='/goods/getPagedModel.html' />?showHasStorage="+ showHasStorage+ "&goodsTypeId="+ goodsTypeId+ "&page="+ param.page;
		return true;
	}

	// goods storage reference
	var orderTooltipFormatter = function(value, row, index) {
		if(row.orderId) 
		{
			return "<a href='javascript:void(0)' style='color:#000; font-weight:bold' id='" + row.orderId + "' title='点击查看明细'>" + value + "</a>";
		}
	}
	var showOrderDetail = function(orderId, orderTypeCode) {
		//console.log(orderId);
		$('#dlg-view-storage-course a#' + orderId)
				.tooltip(
						{
							content : $('<div></div>'),
							showEvent : 'click',
							onUpdate : function(content) {
								var requestUrl = '<c:url value='/inOrder/getOrderItemTable.html' />';
								if (orderTypeCode == 'OUT' || orderTypeCode == 'OUT_RETURN') {
									requestUrl = '<c:url value='/outOrder/getOrderItemTable.html' />';
								}
								content.panel({
											width : 400,
											border : false,
											noheader : true,
											title : '订单明细',
											method : 'post',
											queryParams : {
												orderId : orderId,
												templateFile : 'orderTableNoMoneyTemplate.html'
											},
											href : requestUrl
										});
							},
							onShow : function() {
								var t = $(this);
								t.tooltip('tip').unbind().bind('mouseenter',
										function() {
											t.tooltip('show');
										}).bind('mouseleave', 
											function() {
												t.tooltip('hide');
											}
										);
							}
						});
	}

	var orderTypeCode = 'OUT';
	var getSelectedOrderType = function() {
		var $typeCode = document.getElementsByName("orderTypeCode_mode");
		for ( var i = 0; i < $typeCode.length; i++) {
			if ($typeCode[i].checked) {
				orderTypeCode = $typeCode[i].value;
				break;
			}
		}
		//console.log('orderTypeCode --- ' + orderTypeCode);
	}
	var onBeforeLoadStorageCourse = function(param) {
		var startDate = $('#toolbar-storage-course #advanceSearchSpan #startDate').combo('getValue');
		var endDate = $('#toolbar-storage-course #advanceSearchSpan #endDate').combo('getValue');
		var goodsId = $('#toolbar-storage-course #advanceSearchSpan #goodsId').combo('getValue');

		if (param.page == undefined || param.page == 0) {
			param.page = 1;
		}
		getSelectedOrderType();
		$('#dg-storage-course').datagrid('options').url = "<c:url value='/goods/getStorageCourse.html' />?startDate="
				+ startDate
				+ "&endDate="
				+ endDate
				+ "&orderType="
				+ orderTypeCode + "&goodsId=" + goodsId + "&page=" + param.page;
		return true;
	}
	var viewStorageCourse = function() {
		var goods = $('#dg-goods').datagrid('getSelected');
		if (goods) {
			setGoodsStorageInitialTimeFrame('RECENT_THIRTY_DAYS');
			$('#toolbar-storage-course #advanceSearchSpan #timeFrame').combobox('setValue', 'RECENT_THIRTY_DAYS');
			$('#dlg-view-storage-course').dialog('open').dialog('setTitle', goods.name + '的经营历程');

			$('#toolbar-storage-course #advanceSearchSpan #goodsId').combobox('setValue', goods.id);
			$('#toolbar-storage-course #advanceSearchSpan #goodsId').combobox('setText', goods.name);
			$('#dg-storage-course').datagrid('options').onBeforeLoad = onBeforeLoadStorageCourse;
			$('#dg-storage-course').datagrid('reload');
		}
	}

	var onSearchGoodsStorageByAdvance = function() {
		var startDate = $('#toolbar-storage-course #advanceSearchSpan #startDate').combo('getValue');
		var endDate = $('#toolbar-storage-course #advanceSearchSpan #endDate').combo('getValue');
		var goodsId = $('#toolbar-storage-course #advanceSearchSpan #goodsId').combo('getValue');

		if ((startDate != '' && endDate == '') || (startDate == '' && endDate != '')) {
			$.messager.alert('警告', '开始时间和结束时间必须同时填!', 'warning');
			return;
		}
		if (startDate == '' && endDate == '' && goodsId == '') {
			$.messager.alert('警告', '请填写查询条件!', 'warning');
			return;
		}
		$('#dg-storage-course').datagrid('loadData', []);
		$('#dg-storage-course').datagrid('reload');
	}
	var onLoadStorageCourseSuccess = function(data) {
		for ( var i = 0; i < data.rows.length; i++) {
			showOrderDetail(data.rows[i].orderId, data.rows[i].orderTypeCode);
		}
	}

	var onGoodsStorageSelectStartDate = function(date) {
		$('#toolbar-storage-course #advanceSearchSpan #timeFrame').combobox('setValue', 'CUSTOMIZE');
	}
	var onGoodsStorageSelectTimeFrame = function(record) {
		setGoodsStorageInitialTimeFrame(record.value);
	}
	var setGoodsStorageInitialTimeFrame = function(timeFrame) {
		var startDate = generateStartDate(timeFrame);
		var endDate = new Date().format("yyyy-MM-dd");
		$('#toolbar-storage-course #advanceSearchSpan #startDate').datebox('setValue', startDate);
		$('#toolbar-storage-course #advanceSearchSpan #endDate').datebox('setValue', endDate);
	}
	function checkGoodsStorage() {
		if (goods_editingIndex != undefined) {
			$('#dg-goodsStorage').datagrid('endEdit', goods_editingIndex);
			setGoodsStorageList();
		}
		return true;
	}
	var onDblClickGoodsRow = function(rowIndex, rowData) {
		$('#toolbar-goods #btn-editGoods').click();
	}

	/* upload */
	var currentUploader;
	var lastUploader;
	var uploaderInput;
	function jsuOnLoad() {
		new jsu.Upload({
			containerId : "uploadedPicture",
			multiple : true,
			multiSelection : false,
			auto : true,
			chooser : "anchor",
			onCancel : deleteImage,
			onFinish : loadImage,
			//onStart: onStatusUpload,
			//onChange: onStatusUpload,
			onStatus : onStatusUpload,
			validExtensions : ".jpg, .gif, .png",
			regional : {
				uploadStatusSuccess : "完成",
				uploadStatusError : "失败",
				uploaderInvalidExtension : "无效的文件.\n只有这些类型是允许的:\n",
				uploadStatusCanceling : "正在取消...",
				uploadStatusCanceled : "取消",
				uploadStatusError : "出错",
				uploadStatusInProgress : "正在发送...",
				uploadStatusQueued : "等待",
				uploadStatusSubmitting : "正在提交表单...",
				uploadBrowse : "请选择图片文件"
			}
		});
		currentUploader = $('#uploadedPicture .upld-multiple .GWTUpld:last');
		currentUploader.css('visibility', 'hidden');
		lastUploader = currentUploader;
	}
	var onChangeUpload = function() {

	}
	var onStatusUpload = function() {
		//console.log($('#uploadedPicture .upld-multiple .GWTUpld').length);
		lastUploader.css('visibility', 'visible');
		currentUploader = $('#uploadedPicture .upld-multiple .GWTUpld:last').has('input:file');
		currentUploader.css('visibility', 'hidden');
		lastUploader = currentUploader;
	}

	function loadXML(xmlString) {
		var xmlDoc;
		if (window.ActiveXObject) {
			xmlDoc = new ActiveXObject('Microsoft.XMLDOM');
			if (!xmlDoc)
				xmldoc = new ActiveXObject("MSXML2.DOMDocument.3.0");
			xmlDoc.async = false;
			xmlDoc.loadXML(xmlString);
		} else if (document.implementation && document.implementation.createDocument) {
			//xmlDoc = document.implementation.createDocument('', '', null);  
			//xmlDoc.load(xmlFile);  
			var domParser = new DOMParser();
			xmlDoc = domParser.parseFromString(xmlString, 'text/xml');
		} else {
			return null;
		}
		return xmlDoc;
	}

	var getContentType = function(xmlstring) {
		var oXmlDom = loadXML(xmlstring);
		//var root = oXmlDom.documentElement;
		//console.log(oXmlDom.getElementsByTagName("response")[0]);
		var cType = oXmlDom.getElementsByTagName("ctype")[0];
		cType = cType.firstChild.nodeValue;
		return cType;
	}
	var getPicId = function(xmlstring) {
		var oXmlDom = loadXML(xmlstring);
		//var root = oXmlDom.documentElement;
		//console.log(oXmlDom.getElementsByTagName("response")[0]);
		var pidId = oXmlDom.getElementsByTagName("field")[0];
		pidId = pidId.firstChild.nodeValue;
		return pidId;
	}

	// Method to show a picture using the class PreloadImage
	// The image is not shown until it has been sucessfully downloaded
	function loadImage(upl_data) {
		if (upl_data && upl_data[0] && upl_data[0].url) {
			for (i = 0; i < upl_data.length; i++) {
				loadImage(upl_data[i]);
			}
		} else if (upl_data && upl_data.url) {
			var picId = getPicId(upl_data.response);
			var contentType = getContentType(upl_data.response);
			var url = upl_data.url + '&ctype=' + contentType;
			appendPicLiEle(picId, contentType);
		}
	}
	function createPicLiEle(picId, ctype) {
		var random = Math.random();
		var showUrl = 'servlet.gupld?show=' + picId + '&amp;random=' + random + '&amp;ctype=' + ctype;
		var deleteUrl = '<c:url value='/goods/servlet.gupld' />?remove=' + picId + '&random=' + random + '&amp;ctype=' + ctype;
		var htmlLiEle = '<li><div class="imgDiv"><p class="imgControl"><span class="del" onclick="deleteImage2(this)" src="'
				+ deleteUrl
				+ '">X</span></p><img src="' + showUrl + '" alt=""></div></li>';
		return htmlLiEle;
	}
	function appendPicLiEle(picId, ctype) {
		var picContainer = $('#dlg-view-goods-photo #previewPicture');
		var picULEle = $('#dlg-view-goods-photo #previewPicture ul').get(0);
		if (picULEle == undefined) {
			picContainer.append('<ul style="display:inline-block; width:100%"></ul>');
		}
		picULEle = $('#dlg-view-goods-photo #previewPicture ul');
		picULEle.append(createPicLiEle(picId, ctype));
	}
	function deleteImage(upl_data) {
		if (upl_data) {
			//console.log(upl_data);
			var pics = $('#dlg-view-goods-photo #previewPicture ul li img');
			//console.log(pics.length);
			$.each(pics, function(i, n) {
				var imgSrc = $(n).attr('src');
				var imgId = imgSrc.substring(imgSrc.indexOf('show=') + 5, imgSrc.indexOf('&'));
				//console.log(imgId);
				if (upl_data.name == imgId) {
					$(n).parent('div').parent('li').remove();
				}
			});
		}
	}
	function deleteImage2(spanEle) {
		var url = $(spanEle).attr('src');
		var imgId = url.substring(url.indexOf('remove=') + 7, url.indexOf('&'));
		$.get(url, function(result) {
			var deleted = result.getElementsByTagName("deleted")[0];
			deleted = deleted.firstChild.nodeValue;
			//console.log(deleted);
			if ('true' == deleted) {
				$(spanEle).parent('p').parent('div').parent('li').remove();
			}
		}, 'xml');

		/*var upl_data = {};
		upl_data.url = 'servlet.gupld?show=' + imgId + '&random=0.5418334971182048';
		upl_data.name = imgId;
		
		deleteImage(upl_data);*/
	}

	function formatter_viewPictureAction(value, row, index) {
		if (row.id) {
			return '<a href="#" class="iconSpan16 img16" title="查看" onclick="viewGoodsPicture('
					+ index + ')">&nbsp;</a>';
		}
	}
	function viewGoodsPicture(rowIndex) {
		$('#dg-goods').datagrid('selectRow', rowIndex);
		var goods = $('#dg-goods').datagrid('getSelected');
		$.post(
			'<c:url value='/goods/setGoodsIdToSession.html' />',
			{
				goodsId : goods.id
			},
			function(result) {
				if ('true' == result) {
					//$('#dlg-view-goods-photo #previewPicture').html('');
					var pics = $('#dlg-view-goods-photo #previewPicture ul');
					//console.log(pics.length);
					$.each(pics, function(i, n) {
						$(n).remove();
					});

					var uploadedPics = $('#dlg-view-goods-photo #uploadedPicture .GWTUpld');
					var uploadedPicsLen = uploadedPics.length;
					if (1 < uploadedPicsLen) {
						$.each(uploadedPics, function(i, n) {
							if (i + 1 < uploadedPicsLen) {
								$(n).remove();
							}
						});
					}

					$('#dlg-view-goods-photo').dialog('open').dialog('setTitle', goods.name + '的图片');

					/*var uploaderEles = $('#dlg-view-goods-photo #uploadedPicture table.GWTUpld');
					uploaderEles.appendTo('#dlg-view-goods-photo #uploadPictureBtn');
					$('#dlg-view-goods-photo #uploadedPicture table').remove('.GWTUpld');*/
					$('#dlg-view-goods-photo').mask('加载中...');
					var param = {
						goodsId : goods.id
					};
					$.post(
						'<c:url value='/goods/getGoodsPicture.html' />',
						param,
						function(result) {
							$('#dlg-view-goods-photo').unmask();
							for ( var i = 0; i < result.length; i++) {
								/*var img = '<img class="gwt-Image" src="servlet.gupld?show=' + result[i].fileName + '&amp;random=' + Math.random() + '&amp;ctype=' + result[i].contentType + '" style="">';
								$('#dlg-view-goods-photo #previewPicture').append(img);*/
								appendPicLiEle(result[i].fileName, result[i].contentType);
							}
						}, 'json');
				}
			}, 'text');
	}
	function saveGoodsPicture() {
		var pics = $('#dlg-view-goods-photo #previewPicture .gwt-Image');
		var goodsPictures = '';
		$.each(pics, function(i, n) {
			var imgSrc = $(n).attr('src');
			var imgId = imgSrc.substring(imgSrc.indexOf('show=') + 5, imgSrc.indexOf('&'));
			goodsPictures += (imgId + ',');
		});

		var goods = $('#dg-goods').datagrid('getSelected');
		var param = {
			goodsId : goods.id,
			goodsPictures : goodsPictures
		};
		$('#dlg-view-goods-photo').mask('处理中...');
		$.post('<c:url value='/goods/saveGoodsPicture.html' />', param,
				function(result) {
					$('#dlg-view-goods-photo').unmask();
					//console.log(result);
					if (result.statusCode == '400' || result.statusCode == '500') {
						showFailureMsg(result.message);
					} else if (result.statusCode == '401' || result.statusCode == '403') {
						showWarningMsg(result.message);
					} else {
						showSuccessMsg(result.message);
					}
				}, 'json');
	}
	var showGoodsUnitPanel = function() {
		$(this).combobox('reload', '<c:url value='/goodsUnit/getAllModel.html' />');
	}
	var selectFile = function() {
		//console.log($('#uploadedPicture .upld-multiple input:file:last').length);
		uploaderInput = $('#uploadedPicture .upld-multiple input:file:last');
		uploaderInput.click();
	}
	
	function doSearchGoods(value) {
		$('#dg-goods').datagrid('load', {
			searchKey : value
		});
	}
	
	var onCheckAllGoods = function(rows) 
	{
		$('#toolbar-goods #btn-deleteGoods').linkbutton('enable');
	}
	var onUnCheckGoods = function(rowIndex,rowData) 
	{
		var rows = $('#dg-goods').datagrid('getChecked');
		if (rows.length == 0) {
			$('#toolbar-goods #btn-deleteGoods').linkbutton('disable');
		}
	}
	
	function showManageGoodsUnit() 
	{
		$('#dlg-manage-goodsUnit').dialog('open');
		$('#dg-goodsUnit').datagrid('options').url = '<c:url value='/goodsUnit/getAllModel.html' />';
		$('#dg-goodsUnit').datagrid('reload');
	}
	function showManageGoodsDepository() 
	{
		$('#dlg-manage-goodsDepository').dialog('open');
		$('#dg-goodsDepository').datagrid('options').url = '<c:url value='/goodsDepository/getAllModel.html' />';
		$('#dg-goodsDepository').datagrid('reload');
	}
	function exportGoods(dataGridId, requestUrl) 
	{
		var rows = $(dataGridId).datagrid('getChecked');
		if (rows.length > 0) {
			var ids = "";
			for(var i = 0; i < rows.length; i++) 
			{
				ids += (rows[i].id + ";");
			}
			window.location.href = requestUrl + "?ids=" + ids.substring(0, ids.length-1);
		}
	}
	
    var onGoodsHeaderContextMenu = function(e, field)
    {
        e.preventDefault();
        if (!cmenu){
            createColumnMenu($('#dg-goods'));
        }
        cmenu.menu('show', {
            left:e.pageX,
            top:e.pageY
        });
    }
    $(function() {
    	if('${sessionScope.autoShowStorageWarning}' == '1') 
		{
	    	warnGoodsStorage();
		}
    });
    
    <!-- goods type mgmt -->
    var expand = true;
	function toggleAll(treeId) 
	{
		if(expand) 
		{
			collapseAll(treeId);
			expand = false;
			$('#gt-toggleAll').html('<span class="l-btn-left l-btn-icon-left"><span class="l-btn-text"></span><span class="l-btn-icon datagrid-row-expand">&nbsp;</span></span>');
		}
		else 
		{
			expandAll(treeId);
			expand = true;
			$('#gt-toggleAll').html('<span class="l-btn-left l-btn-icon-left"><span class="l-btn-text"></span><span class="l-btn-icon datagrid-row-collapse">&nbsp;</span></span>');
		}
	}
	
	function goodsType_onAfterEdit(node) 
	{
		var url = '<c:url value='/goodsType/addModelWithResponse.html' />';
		if(node.id != newNodeIndex) 
		{
			url = '<c:url value='/goodsType/updateModelWithResponse.html' />';
		}
		saveTree2('#tree_goodsType', url, {id:node.id, text:node.text, parentId:node.parentId});
	}
	var newNodeIndex = 0;
	function goodsType_addType () 
	{
		var t = $('#tree_goodsType');
		//var selectedNode = t.tree('getSelected');
		var selectedNode = parentGoodsType;
		var parentId = selectedNode ? selectedNode.id : '';
		newNodeIndex --;
		t.tree('append', {
			parent: (selectedNode ? selectedNode.target : null),
			data: [{
				id: newNodeIndex,
				text: '',
				parentId: parentId
			}]
		});
		var node = t.tree('find', newNodeIndex);
		t.tree('beginEdit', node.target);
	}
	function goodsType_updateType () 
	{
		var t = $('#tree_goodsType');
		//var node = t.tree('getSelected');
		var node = parentGoodsType;
		t.tree('beginEdit', node.target);
	}
	function goodsType_deleteType(){
		var t = $('#tree_goodsType');
	    var node = parentGoodsType;
	    var requestUrl = '<c:url value='/goodsType/deleteModels.html' />';
		if (node) {
			$.messager.confirm('确认',
				'您确认要删除 ' + node.text + '?', function(r) {
					if (r) {
						$.post(requestUrl, {
							ids : node.id
						}, function(result) {
							console.log(result);
							if (result.statusCode == '400' || result.statusCode == '500') {
								showFailureMsg(result.message);
							}
							else if (result.statusCode == '401' || result.statusCode == '403') {
								showWarningMsg(result.message);
							} else {
								t.tree('remove', node.target);
								showSuccessMsg (result.message);
							}
						}, 'json');
					}
				});
		}
	}
	
	var parentGoodsType;
	function goodsType_onContextMenu(e,node) 
	{
		if(node.id != '0') 
		{
			e.preventDefault();
			$(this).tree('check',node.target);
			parentGoodsType = node;
			$('#mm_goodsType').menu('show',{
				left: e.pageX,
				top: e.pageY
			});
		}
	}
	function goodsType_onBeforeLoad(node, param)
	{
		parentGoodsType = undefined;
		selectedGoodsType = undefined;
		if(!expand) 
		{
			expand = true;
			$('#gt-toggleAll').html('<span class="l-btn-left l-btn-icon-left"><span class="l-btn-text"></span><span class="l-btn-icon datagrid-row-collapse">&nbsp;</span></span>');
		}
		return true;
	}
	function goodsType_formatterGoodsType(node)
	{
		var s = node.text;
		if(node.goodsAmount && node.goodsAmount > 0) 
		{
			s += '&nbsp;<span style=\'color:black\' title="该类型下有'+node.goodsAmount+'种货物">(' + node.goodsAmount + ')</span>';
		}
		if (node.children && node.children.length > 0){
			s += '&nbsp;<span style=\'color:blue\' title="该类型下有'+node.children.length+'个子类型">(' + node.children.length + ')</span>';
		}
		return s;
	}
	
	// goods type section
	var selectedGoodsType;
	var goodsType_onSelectGoodsType = function(node) {
		selectedGoodsType = node;
		$('#dg-goods').datagrid(
			'load',
			{
				searchKey : $('#toolbar-goods #goodsSearchBox').searchbox('getValue'),
				goodsTypeId : node.id
			});
	}
    <!-- goods type mgmt -->
	