package com.pinfly.purchasecharge.framework.print;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pinfly.purchasecharge.core.util.DateUtils;

public class ExportPdf
{
    public static void export (PdfMetadata metadata, String[] headers, Collection dataSet, OutputStream out)
    {
        Document document = new Document (PageSize.A4, 36, 36, 80, 36);

        try
        {
            PdfWriter writer = PdfWriter.getInstance (document, out);
            // 基本配置信息
            if (null != metadata)
            {
                document.addTitle (metadata.getTitle ());
                document.addAuthor (metadata.getAuthor ());
                document.addCreator (metadata.getCreator ());
                document.addKeywords (metadata.getKeywords ());
                document.addSubject (metadata.getSubject ());

                // 定义页眉和页脚
                PageHeader event = new PageHeader ();
                event.setHeader (metadata.getMark ());
                writer.setPageEvent (event);
                PdfWatermark watermark = new PdfWatermark ();
                watermark.setMark (metadata.getMark ());
                writer.setPageEvent (watermark);
            }
            document.addCreationDate ();

            // 打开pdf文档
            document.open ();

            if (null != headers && headers.length > 0)
            {
                PdfPTable table = new PdfPTable (headers.length);
                table.setHorizontalAlignment (Element.ALIGN_CENTER);
                table.setWidthPercentage (16 * headers.length);

                // 产生表格栏
                PdfPCell cell = null;
                for (int i = 0; i < headers.length; i++)
                {
                    cell = new PdfPCell (new Paragraph (headers[i], ChineseFont.MEDIUM_BOLD));
                    cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                    cell.setVerticalAlignment (Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (BaseColor.GRAY);
                    table.addCell (cell);
                }

                // 装载数据行
                if (null != dataSet && dataSet.size () > 0)
                {
                    Iterator it = dataSet.iterator ();
                    while (it.hasNext ())
                    {
                        Object t = it.next ();
                        Class tClass = t.getClass ();
                        for (int j = 0; j < headers.length; j++)
                        {
                            String getMethodName = "get" + headers[j].substring (0, 1).toUpperCase ()
                                                   + headers[j].substring (1);
                            Method getMethod = tClass.getMethod (getMethodName, new Class[]
                            {});
                            Object value = getMethod.invoke (t, new Object[]
                            {});
                            value = value == null ? "" : value;
                            String text = value.toString ();
                            if (value != null)
                            {
                                if (value instanceof Date)
                                {
                                    text = DateUtils.date2String ((Date) value, DateUtils.DATE_TIME_NO_SECOND_PATTERN);
                                }
                            }
                            cell = new PdfPCell (new Paragraph (text, ChineseFont.MEDIUM));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment (Element.ALIGN_MIDDLE);
                            table.addCell (cell);
                        }
                    }
                    document.add (table);
                }
            }

            document.close ();
        }
        catch (DocumentException e)
        {
            e.printStackTrace ();
        }
        catch (SecurityException e)
        {
            e.printStackTrace ();
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace ();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace ();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace ();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace ();
        }
        finally
        {
            try
            {
                out.close ();
            }
            catch (IOException e)
            {
                e.printStackTrace ();
            }
        }
    }

    public static void export (PdfMetadata metadata, String[] headers, Collection dataSet, Document document,
                               PdfWriter writer)
    {
        try
        {
            // 基本配置信息
            if (null != metadata)
            {
                document.addTitle (metadata.getTitle ());
                document.addAuthor (metadata.getAuthor ());
                document.addCreator (metadata.getCreator ());
                document.addKeywords (metadata.getKeywords ());
                document.addSubject (metadata.getSubject ());
            }
            document.addCreationDate ();

            if (null != headers && headers.length > 0)
            {
                PdfPTable table = new PdfPTable (headers.length);
                table.setHorizontalAlignment (Element.ALIGN_CENTER);
                table.setWidthPercentage (16 * headers.length);

                // 产生表格栏
                PdfPCell cell = null;
                for (int i = 0; i < headers.length; i++)
                {
                    cell = new PdfPCell (new Paragraph (headers[i], ChineseFont.MEDIUM_BOLD));
                    cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                    cell.setVerticalAlignment (Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor (BaseColor.GRAY);
                    table.addCell (cell);
                }

                // 装载数据行
                if (null != dataSet && dataSet.size () > 0)
                {
                    Iterator it = dataSet.iterator ();
                    while (it.hasNext ())
                    {
                        Object t = it.next ();
                        Class tClass = t.getClass ();
                        for (int j = 0; j < headers.length; j++)
                        {
                            String getMethodName = "get" + headers[j].substring (0, 1).toUpperCase ()
                                                   + headers[j].substring (1);
                            Method getMethod = tClass.getMethod (getMethodName, new Class[]
                            {});
                            Object value = getMethod.invoke (t, new Object[]
                            {});
                            value = value == null ? "" : value;
                            String text = value.toString ();
                            if (value != null)
                            {
                                if (value instanceof Date)
                                {
                                    text = DateUtils.date2String ((Date) value, DateUtils.DATE_TIME_NO_SECOND_PATTERN);
                                }
                            }
                            cell = new PdfPCell (new Paragraph (text, ChineseFont.MEDIUM));
                            cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                            cell.setVerticalAlignment (Element.ALIGN_MIDDLE);
                            table.addCell (cell);
                        }
                    }
                    document.add (table);
                }
            }
        }
        catch (DocumentException e)
        {
            e.printStackTrace ();
        }
        catch (SecurityException e)
        {
            e.printStackTrace ();
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace ();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace ();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace ();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace ();
        }
    }

}