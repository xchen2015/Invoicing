package com.cxx.purchasecharge;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.pinfly.purchasecharge.dal.BackupAndRestore;
import com.pinfly.purchasecharge.dal.impl.MysqlBackupAndRestore;

public class DataOutputAndInput
{

    @Test
    public void testOutput ()
    {
        String path = "c:/mysql_bak/";

        Runtime rt = Runtime.getRuntime ();
        Process process = null;
        StringBuffer exp = new StringBuffer ("select * from pc_customer,pc_contact into outfile ");
        Date date = new Date ();
        SimpleDateFormat format = new SimpleDateFormat ("yyyy年MM月dd日");
        String lastCommand = " fields terminated by '|' enclosed by '\"' lines terminated by '\r\n'";
        exp.append (path).append (format.format (date)).append ("数据库备份").append (".bak").append (lastCommand);

        try
        {
            System.out.println ("command: " + exp.toString ());
            process = rt.exec (exp.toString ());
            System.out.println ("success!!!!");
        }
        catch (IOException e)
        {
            e.printStackTrace ();
        }
    }

    @Test
    public void testOutput2 ()
    {
        String path = "c:/mysql_bak/";

        Runtime rt = Runtime.getRuntime ();
        Process process = null;
        StringBuffer exp = new StringBuffer ("mysqldump -u root -p test --password=root");
        Date date = new Date ();
        SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        // exp.append (path).append (format.format (date)).append
        // ("数据库备份").append (".bak");

        try
        {
            System.out.println ("command: " + exp.toString ());
            process = rt.exec (exp.toString ());

            InputStreamReader ir = new InputStreamReader (process.getInputStream ());
            LineNumberReader input = new LineNumberReader (ir);
            String line;
            while ((line = input.readLine ()) != null)
                System.out.println (line);
            input.close ();

            System.out.println ("success!!!!");
        }
        catch (IOException e)
        {
            e.printStackTrace ();
        }
    }

    public static void main (String[] args)
    {
        /*
         * 备份和导入是一个互逆的过程。 备份：程序调用mysql的备份命令，读出控制台输入流信息，写入.sql文件；
         * 导入：程序调用mysql的导入命令，把从.sql文件中读出的信息写入控制台的输出流 注意：此时定向符">"和"<"是不能用的
         */
        String path = "c:/mysql_bak";
        BackupAndRestore dataBackAndLoad = new MysqlBackupAndRestore ();
        dataBackAndLoad.backup (path);

        File fPath = new File ("c:/mysql_bak/2014年04月16日12时43分00秒.sqlbak");
        dataBackAndLoad.load (fPath);
    }

}
