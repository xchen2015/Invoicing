package com.pinfly.purchasecharge.dal.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.core.config.PurchaseChargeProperties;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.core.util.PurchaseChargeConstants;
import com.pinfly.purchasecharge.dal.BackupAndRestore;

public class MysqlBackupAndRestore implements BackupAndRestore
{
    private static final Logger LOGGER = Logger.getLogger (MysqlBackupAndRestore.class);

    @Override
    public File backup (String dir)
    {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fout = null;
        OutputStreamWriter writer = null;
        File file = null;

        try
        {
            Runtime rt = Runtime.getRuntime ();

            // 调用 mysql 的mysqldump cmd:
            String pattern = "mysqldump -u {0} -p {1} --password={2} --set-charset=utf8";
            String command = MessageFormat.format (pattern, PurchaseChargeProperties.getDbUser (),
                                                   PurchaseChargeProperties.getDbInstance (),
                                                   PurchaseChargeProperties.getDbPassword ());
            Process process = rt.exec (command);// 设置导出编码为utf8。这里必须是utf8

            // 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
            is = process.getInputStream ();// 控制台的输出信息作为输入流

            isr = new InputStreamReader (is, "utf8");// 设置输出流编码为utf8。这里必须是utf8，否则从流中读入的是乱码

            String inStr;
            StringBuffer sb = new StringBuffer ("");
            String outStr;
            // 组合控制台输出信息字符串
            br = new BufferedReader (isr);
            while ((inStr = br.readLine ()) != null)
            {
                sb.append (inStr + "\r\n");
            }
            outStr = sb.toString ();

            // 要用来做导入用的sql目标文件：
            String fileName = DateUtils.date2String (new Date (), PurchaseChargeConstants.FILE_NAME_DATE_TIME);
            File fileDir = new File (dir);
            if (!fileDir.exists ())
            {
                fileDir.mkdir ();
            }
            String filePath = dir + File.separator + fileName + PurchaseChargeConstants.BACKUP_FILE_EXTENSION;
            file = new File (filePath);
            fout = new FileOutputStream (file);
            writer = new OutputStreamWriter (fout, "utf8");
            writer.write (outStr);
            // 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
            writer.flush ();

            LOGGER.info ("Backup data file (" + filePath + ") success");
        }
        catch (Exception e)
        {
            LOGGER.warn ("Backup data file fail", e);
        }
        finally
        {
            try
            {
                // 别忘记关闭输入输出流
                if (null != is)
                {
                    is.close ();
                }
                if (null != isr)
                {
                    isr.close ();
                }
                if (null != br)
                {
                    br.close ();
                }
                if (null != fout)
                {
                    fout.close ();
                }
                if (null != writer)
                {
                    writer.close ();
                }
            }
            catch (IOException e)
            {
                LOGGER.warn ("Close stream fail", e);
            }
        }
        return file;
    }

    @Override
    public void load (File fPath)
    {
        OutputStream out = null;
        BufferedReader br = null;
        OutputStreamWriter writer = null;

        try
        {
            Runtime rt = Runtime.getRuntime ();

            // 调用 mysql 的mysql cmd:
            String pattern = "mysql -u {0} -p {1} --password={2}";
            String command = MessageFormat.format (pattern, PurchaseChargeProperties.getDbUser (),
                                                   PurchaseChargeProperties.getDbInstance (),
                                                   PurchaseChargeProperties.getDbPassword ());
            Process process = rt.exec (command);

            out = process.getOutputStream ();// 控制台的输入信息作为输出流
            String inStr;
            StringBuffer sb = new StringBuffer ("");
            String outStr;
            br = new BufferedReader (new InputStreamReader (new FileInputStream (fPath), "utf8"));
            while ((inStr = br.readLine ()) != null)
            {
                sb.append (inStr + "\r\n");
            }
            outStr = sb.toString ();

            writer = new OutputStreamWriter (out, "utf8");
            writer.write (outStr);
            // 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
            writer.flush ();

            LOGGER.info ("Load data file (" + fPath.getAbsolutePath () + ") success");
        }
        catch (Exception e)
        {
            LOGGER.info ("Load data file (" + fPath.getAbsolutePath () + ") fail", e);
        }
        finally
        {
            try
            {
                // 别忘记关闭输入输出流
                if (null != out)
                {
                    out.close ();
                }
                if (null != br)
                {
                    br.close ();
                }
                if (null != writer)
                {
                    writer.close ();
                }
            }
            catch (IOException e)
            {
                LOGGER.warn ("Close stream fail", e);
            }
        }
    }

}
