package com.pinfly.purchasecharge.component.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 */
@SuppressWarnings ("all")
public class ImportUtil
{
    public static List ImportExcel (InputStream is, Class clss)
    {
        List listResult = new ArrayList ();
        String[] headers = null;
        try
        {
            HSSFWorkbook wbs = new HSSFWorkbook (is);
            HSSFSheet sheet = wbs.getSheetAt (0);
            Iterator rows = sheet.rowIterator ();
            while (rows.hasNext ())
            {
                Object obj = clss.newInstance ();
                HSSFRow row = (HSSFRow) rows.next ();
                if (row.getRowNum () == 0)
                {
                    headers = new String[row.getLastCellNum ()];
                    Iterator cells = row.cellIterator ();
                    int i = 0;
                    while (cells.hasNext ())
                    {
                        HSSFCell cell = (HSSFCell) cells.next ();
                        headers[i] = new String (cell.getStringCellValue ());
                        i++;
                    }
                }
                else
                {
                    if (headers != null && headers.length > 0)
                    {
                        Object value = null;
                        int i = row.getFirstCellNum ();
                        for (; i < row.getLastCellNum (); i++)
                        {
                            HSSFCell cell = (HSSFCell) row.getCell (i);
                            if (cell != null)
                            {
                                int cellType = cell.getCellType ();
                                HSSFCellStyle style = cell.getCellStyle ();
                                short format = style.getDataFormat ();
                                switch (cellType)
                                {
                                case HSSFCell.CELL_TYPE_STRING: // 字符串
                                    value = cell.getStringCellValue ();
                                    break;
                                case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                                    double numTxt = cell.getNumericCellValue ();
                                    if (format == 22 || format == 14)
                                        value = HSSFDateUtil.getJavaDate (numTxt);
                                    else
                                        value = numTxt;
                                    break;
                                case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                                    value = cell.getBooleanCellValue ();
                                    break;
                                case HSSFCell.CELL_TYPE_FORMULA: // 公式
                                    value = cell.getCellFormula ();
                                    break;
                                case HSSFCell.CELL_TYPE_BLANK: // 空值
                                    break;
                                case HSSFCell.CELL_TYPE_ERROR: // 故障
                                    break;
                                default: // unknown type
                                    break;
                                }
                            }
                            else
                            {
                                value = null;
                            }
                            String setMethodName = "set" + headers[i].substring (0, 1).toUpperCase ()
                                                   + headers[i].substring (1);
                            Field field = clss.getDeclaredField (headers[i]);
                            if (field != null)
                            {
                                Method setMethod = clss.getMethod (setMethodName, new Class[]
                                { field.getType () });
                                setMethod.invoke (obj, new Object[]
                                { value });
                            }

                        }
                        listResult.add (obj);
                    }
                }
            }
        }
        catch (IOException e)
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
        catch (InstantiationException e)
        {
            e.printStackTrace ();
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace ();
        }
        finally
        {
            if (is != null)
            {
                try
                {
                    is.close ();
                }
                catch (IOException e)
                {
                    e.printStackTrace ();
                }
            }
        }

        return listResult;
    }

    @SuppressWarnings ("unchecked")
    public static List ImportCsv (File csvFile, Class clss)
    {
        List listResult = new ArrayList ();
        LineNumberReader lnr = null;
        try
        {
            lnr = new LineNumberReader (new FileReader (csvFile));
            String str = "";
            int lineNumber = 0;
            String[] headers = null;
            while ((str = lnr.readLine ()) != null)
            {
                lineNumber = lnr.getLineNumber ();
                String[] values = str.split (",");
                if (lineNumber == 1)
                {
                    headers = values;
                }
                else
                {
                    Object obj = clss.newInstance ();
                    if (headers != null && headers.length > 0)
                    {
                        for (int i = 0; i < values.length; i++)
                        {
                            String setMethodName = "set" + headers[i].substring (0, 1).toUpperCase ()
                                                   + headers[i].substring (1);
                            Field field = clss.getDeclaredField (headers[i]);
                            if (field != null)
                            {
                                Method setMethod = clss.getMethod (setMethodName, new Class[]
                                { field.getType () });
                                setMethod.invoke (obj, new Object[]
                                { values[i] });
                            }
                        }
                    }
                    listResult.add (obj);
                }
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace ();
        }
        catch (IOException e)
        {
            e.printStackTrace ();
        }
        catch (SecurityException e)
        {
            e.printStackTrace ();
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace ();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace ();
        }
        catch (IllegalAccessException e)
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
        catch (InvocationTargetException e)
        {
            e.printStackTrace ();
        }
        finally
        {
            if (lnr != null)
            {
                try
                {
                    lnr.close ();
                }
                catch (IOException e)
                {
                    e.printStackTrace ();
                }
            }
        }

        return listResult;
    }

}
