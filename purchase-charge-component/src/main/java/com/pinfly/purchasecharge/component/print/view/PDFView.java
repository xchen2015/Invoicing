package com.pinfly.purchasecharge.component.print.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.pinfly.purchasecharge.component.bean.GoodsBean;
import com.pinfly.purchasecharge.core.config.PurchaseChargeProperties;
import com.pinfly.purchasecharge.core.util.PurchaseChargeConstants;
import com.pinfly.purchasecharge.framework.print.AbstractPdfView;
import com.pinfly.purchasecharge.framework.print.ExportPdf;
import com.pinfly.purchasecharge.framework.print.PageHeader;
import com.pinfly.purchasecharge.framework.print.PdfMetadata;
import com.pinfly.purchasecharge.framework.print.PdfWatermark;

public class PDFView extends AbstractPdfView
{

    protected void buildPdfDocument (Map <String, Object> model, Document document, PdfWriter writer,
                                     HttpServletRequest req, HttpServletResponse resp) throws Exception
    {
        @SuppressWarnings ("unchecked")
        List <GoodsBean> goodses = (List <GoodsBean>) model.get ("goodsesExported");
        // new BaseColor (72, 121, 145), 1f, 0.5f, 1f, 1.5f
        // new BaseColor (97, 97, 97), 1f, 0.5f, 1f, 1.5f

        if (CollectionUtils.isNotEmpty (goodses))
        {
            PdfMetadata metadata = new PdfMetadata ();
            metadata.setTitle (PurchaseChargeProperties.getInstance ().getConfig (PurchaseChargeConstants.HEADER_TITLE));

            String[] headers =
            { "name", "shortCode", "importPrice", "exportPrice", "averagePrice", "totalStock", "totalValue" };
            ExportPdf.export (metadata, headers, goodses, document, writer);
        }
    }

    @Override
    protected void buildPdfMetadata (Map <String, Object> model, Document document, HttpServletRequest request)
    {
        super.buildPdfMetadata (model, document, request);
        document.setPageSize (PageSize.A4);
        document.setMargins (36, 36, 80, 36);
    }

    @Override
    protected void prepareWriter (Map <String, Object> model, PdfWriter writer, HttpServletRequest request)
                                                                                                           throws DocumentException
    {
        super.prepareWriter (model, writer, request);
        PageHeader event = new PageHeader ();
        event.setHeader (PurchaseChargeProperties.getInstance ().getConfig (PurchaseChargeConstants.HEADER_TITLE));
        writer.setPageEvent (event);
        PdfWatermark watermark = new PdfWatermark ();
        watermark.setMark (PurchaseChargeProperties.getInstance ().getConfig (PurchaseChargeConstants.HEADER_TITLE));
        writer.setPageEvent (watermark);
    }

}
