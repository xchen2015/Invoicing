package com.cxx.purchasecharge.component;

import java.text.MessageFormat;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.framework.print.ChineseFont;

public class PatReportPageEventHandler extends PdfPageEventHelper
{
    // Default values for the various message codes
    public static final String DEFAULT_FACILITY_CODE = "facility";
    public static final String DEFAULT_PRIMARY_PAT_INFO_CODE = "primaryPatientId";
    public static final String DEFAULT_SECONDARY_PAT_INFO_CODE = "secondaryPatientId";
    public static final String DEFAULT_TITLE_CODE = "title";
    public static final String DEFAULT_REQUESTED_BY_CODE = "requestedBy";
    public static final String DEFAULT_REQUESTED_AT_CODE = "requestedAt";
    public static final String DEFAULT_PAGE_NUMBER_CODE = "page";

    // Bean properties
    private String m_facilityCode = DEFAULT_FACILITY_CODE;
    private String m_primaryPatInfoCode = DEFAULT_PRIMARY_PAT_INFO_CODE;
    private String m_secondaryPatInfoCode = DEFAULT_SECONDARY_PAT_INFO_CODE;
    private String m_titleCode = DEFAULT_TITLE_CODE;
    private String m_requestedByCode = DEFAULT_REQUESTED_BY_CODE;
    private String m_requestedAtCode = DEFAULT_REQUESTED_AT_CODE;
    private String m_pageNumCode = DEFAULT_PAGE_NUMBER_CODE;

    // The margins.
    private static final float LEFT_MARGIN = 36;
    private static final float RIGHT_MARGIN = 36;
    private static final float TOP_MARGIN = 80;
    private static final float BOTTOM_MARGIN = 36;

    private Phrase m_header1;
    private Phrase m_header2;
    private Phrase m_header3;
    private Phrase m_header4;
    private Phrase m_header5;
    private Phrase m_header6;
    private String m_footerFormat;
    private PdfTemplate m_totalPages;
    private float m_center;

    public void onOpenDocument (PdfWriter writer, Document doc)
    {
        m_totalPages = writer.getDirectContent ().createTemplate (100, 100);
        m_center = (doc.getPageSize ().getWidth () + doc.leftMargin () - doc.rightMargin ()) / 2;
    }

    public void onEndPage (PdfWriter writer, Document doc)
    {
        String facilityName = "Facility Name";
        m_header1 = new Phrase (facilityName, ChineseFont.MEDIUM_LARGE);

        String patId = "Patient Id";
        String patName = "Patient Name";

        String patInfo1 = patId + patName;
        m_header2 = new Phrase (patInfo1, ChineseFont.LARGE);

        String patInfo2 = m_secondaryPatInfoCode;
        m_header3 = new Phrase (patInfo2, ChineseFont.MEDIUM);

        String reportTitle = m_titleCode;
        m_header4 = new Phrase (reportTitle, ChineseFont.MEDIUM);

        String dateArg = DateUtils.date2String (new Date (), DateUtils.DATE_PATTERN);
        String generated = m_requestedAtCode + dateArg;
        m_header5 = new Phrase (generated, ChineseFont.SMALL);

        String userId = "User ID";
        String userName = "User Name";
        String requested = userId + userName;
        m_header6 = new Phrase (requested, ChineseFont.SMALL);

        // This is a little kludgy.
        // The footer includes the page number, but we don't have a page
        // number here, so we can't do the substitution. But in the
        // onPageEnd method, where we have the page number, we don't
        // have the locale. So we get the message here, and substitute
        // the page number argument with "{0}", so it is still an argument
        // reference. Then we do the actual number substitution in
        // onPageEnd
        m_footerFormat = "Page {0} of ";

        PdfContentByte cb = writer.getDirectContent ();
        cb.saveState ();

        // Add header
        float headerStart = doc.top () + TOP_MARGIN - 18;
        ColumnText.showTextAligned (cb, Element.ALIGN_CENTER, m_header1, m_center, headerStart, 0);
        ColumnText.showTextAligned (cb, Element.ALIGN_LEFT, m_header2, doc.left (), headerStart - 23, 0);
        ColumnText.showTextAligned (cb, Element.ALIGN_RIGHT, m_header3, doc.right (), headerStart - 23, 0);
        ColumnText.showTextAligned (cb, Element.ALIGN_LEFT, m_header4, doc.left (), headerStart - 36, 0);
        ColumnText.showTextAligned (cb, Element.ALIGN_LEFT, m_header5, doc.left (), headerStart - 47, 0);
        ColumnText.showTextAligned (cb, Element.ALIGN_RIGHT, m_header6, doc.right (), headerStart - 47, 0);

        // Add footer
        String footer = MessageFormat.format (m_footerFormat, new Object[]
        { String.valueOf (writer.getPageNumber ()) });
        float textWidth = ChineseFont.MEDIUM.getBaseFont ().getWidthPoint (footer,
                                                                           ChineseFont.MEDIUM.getCalculatedSize ());
        float textBase = doc.bottom () - 20;
        cb.beginText ();
        cb.setFontAndSize (ChineseFont.MEDIUM.getBaseFont (), ChineseFont.MEDIUM.getCalculatedSize ());
        cb.setTextMatrix (doc.left (), textBase);
        cb.showText (footer);
        cb.endText ();
        cb.addTemplate (m_totalPages, doc.left () + textWidth, textBase);

        cb.restoreState ();

    }

    public void onCloseDocument (PdfWriter writer, Document doc)
    {
        m_totalPages.beginText ();
        m_totalPages.setFontAndSize (ChineseFont.MEDIUM.getBaseFont (), ChineseFont.MEDIUM.getCalculatedSize ());
        m_totalPages.setTextMatrix (0, 0);
        m_totalPages.showText (String.valueOf (writer.getPageNumber () - 1));
        m_totalPages.endText ();
    }

}
