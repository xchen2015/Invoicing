package com.pinfly.purchasecharge.framework.print;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.pinfly.purchasecharge.core.util.DateUtils;

public class ExportExcel
{
    public static void export (String sheetName, String[] headers, Collection dataSet, OutputStream out)
    {
        Workbook workbook = new XSSFWorkbook ();
        // 生成一个表格
        Sheet sheet = workbook.createSheet (sheetName);

        Row row = null;
        Cell cell = null;
        int rowIndex = 0;

        // Style for header cell
        CellStyle headerStyle = workbook.createCellStyle ();
        headerStyle.setFillForegroundColor (IndexedColors.GREY_25_PERCENT.index);
        headerStyle.setFillPattern (CellStyle.SOLID_FOREGROUND);
        headerStyle.setAlignment (CellStyle.ALIGN_CENTER);

        CellStyle bodyStyle = workbook.createCellStyle ();
        // bodyStyle.setFillForegroundColor
        // (IndexedColors.GREY_25_PERCENT.index);
        // bodyStyle.setFillPattern (CellStyle.SOLID_FOREGROUND);
        bodyStyle.setAlignment (CellStyle.ALIGN_LEFT);

        // Create header cells
        if (null != headers && headers.length > 0)
        {
            row = sheet.createRow (rowIndex++);
            for (int i = 0; i < headers.length; i++)
            {
                cell = row.createCell (i);
                cell.setCellStyle (headerStyle);
                cell.setCellValue (headers[i]);
            }

            // 遍历集合,产生数据行
            if (null != dataSet && dataSet.size () > 0)
            {
                Iterator it = dataSet.iterator ();
                while (it.hasNext ())
                {
                    row = sheet.createRow (rowIndex++);
                    Object t = it.next ();
                    Class tClass = t.getClass ();
                    for (int j = 0; j < headers.length; j++)
                    {
                        cell = row.createCell (j);
                        String getMethodName = "get" + headers[j].substring (0, 1).toUpperCase ()
                                               + headers[j].substring (1);
                        try
                        {
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
                            cell.setCellStyle (bodyStyle);
                            cell.setCellValue (text);
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

                for (int i = 0; i < headers.length; i++)
                {
                    sheet.autoSizeColumn (i, true);
                }
            }
        }

        try
        {
            // 写出excel
            workbook.write (out);
        }
        catch (IOException e)
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

    public static void export (String sheetName, String[] headers, Collection dataSet, Workbook workbook)
    {
        // 生成一个表格
        Sheet sheet = workbook.createSheet (sheetName);

        Row row = null;
        Cell cell = null;
        int rowIndex = 0;

        // Style for header cell
        CellStyle headerStyle = workbook.createCellStyle ();
        headerStyle.setFillForegroundColor (IndexedColors.GREY_25_PERCENT.index);
        headerStyle.setFillPattern (CellStyle.SOLID_FOREGROUND);
        headerStyle.setAlignment (CellStyle.ALIGN_CENTER);

        CellStyle bodyStyle = workbook.createCellStyle ();
        // bodyStyle.setFillForegroundColor
        // (IndexedColors.GREY_25_PERCENT.index);
        // bodyStyle.setFillPattern (CellStyle.SOLID_FOREGROUND);
        bodyStyle.setAlignment (CellStyle.ALIGN_LEFT);

        // Create header cells
        if (null != headers && headers.length > 0)
        {
            row = sheet.createRow (rowIndex++);
            for (int i = 0; i < headers.length; i++)
            {
                cell = row.createCell (i);
                cell.setCellStyle (headerStyle);
                cell.setCellValue (headers[i]);
            }

            // 遍历集合,产生数据行
            if (null != dataSet && dataSet.size () > 0)
            {
                Iterator it = dataSet.iterator ();
                while (it.hasNext ())
                {
                    row = sheet.createRow (rowIndex++);
                    Object t = it.next ();
                    Class tClass = t.getClass ();
                    for (int j = 0; j < headers.length; j++)
                    {
                        cell = row.createCell (j);
                        String getMethodName = "get" + headers[j].substring (0, 1).toUpperCase ()
                                               + headers[j].substring (1);
                        try
                        {
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
                            cell.setCellStyle (bodyStyle);
                            cell.setCellValue (text);
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

                for (int i = 0; i < headers.length; i++)
                {
                    sheet.autoSizeColumn (i, true);
                }
            }
        }
    }

}