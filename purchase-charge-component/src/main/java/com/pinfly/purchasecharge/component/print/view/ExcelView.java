package com.pinfly.purchasecharge.component.print.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;

import com.pinfly.purchasecharge.component.bean.GoodsBean;
import com.pinfly.purchasecharge.framework.print.AbstractExcelView;
import com.pinfly.purchasecharge.framework.print.ExportExcel;

public class ExcelView extends AbstractExcelView
{

    @Override
    protected void buildExcelDocument (Map <String, Object> model, Workbook workbook, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception
    {
        @SuppressWarnings ("unchecked")
        List <GoodsBean> goodses = (List <GoodsBean>) model.get ("goodsesExported");

        if (CollectionUtils.isNotEmpty (goodses))
        {
            String[] headers =
            { "name", "shortCode", "importPrice", "exportPrice", "averagePrice", "totalStock", "totalValue" };
            ExportExcel.export ("Test", headers, goodses, workbook);
        }
    }

}
