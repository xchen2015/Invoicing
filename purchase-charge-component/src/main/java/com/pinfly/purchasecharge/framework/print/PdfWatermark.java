package com.pinfly.purchasecharge.framework.print;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfWatermark extends PdfPageEventHelper
{
    private final static Font FONT = new Font (ChineseFont.baseFont, 52, Font.BOLD, new GrayColor (0.75f));

    private String mark;

    public String getMark ()
    {
        return mark;
    }

    public void setMark (String mark)
    {
        this.mark = mark;
    }

    public void onEndPage (PdfWriter writer, Document document)
    {
        ColumnText.showTextAligned (writer.getDirectContentUnder (), Element.ALIGN_CENTER,
                                    new Phrase (this.mark, FONT), 297.5f, 421, writer.getPageNumber () % 2 == 1 ? 45
                                                                                                               : -45);
    }
}
