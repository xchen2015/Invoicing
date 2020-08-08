package com.cxx.purchasecharge.component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pinfly.purchasecharge.core.model.persistence.goods.Goods;
import com.pinfly.purchasecharge.dal.goods.GoodsDao;

public class PcJasperReportTest
{
    private static GoodsDao goodsDao;

    public void testViewPieChartByGoodsType () throws Exception
    {
        String jasperFile = JasperCompileManager.compileReportToFile ("src/test/resources/reports/pieChart-goodsType.jrxml");

        Class.forName ("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection ("jdbc:mysql://localhost:3306/test?user=root&password=root");
        System.out.println ("Filling report...");
        Map <String, Object> params = new HashMap <String, Object> ();
        params.put ("reportTitle", "按类型统计货物数量");
        String printFile = JasperFillManager.fillReportToFile (jasperFile, params, connection);
        System.out.println ("printFile : " + printFile);

        JasperViewer.viewReport (printFile, false);
        System.out.println ("Done");
    }

    public void testViewBarChartByGoodsType () throws Exception
    {
        String jasperFile = JasperCompileManager.compileReportToFile ("src/test/resources/reports/barChart-goodsType.jrxml");

        Class.forName ("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection ("jdbc:mysql://localhost:3306/test?user=root&password=root");
        System.out.println ("Filling report...");
        Map <String, Object> params = new HashMap <String, Object> ();
        params.put ("reportTitle", "按类型统计货物数量");
        String printFile = JasperFillManager.fillReportToFile (jasperFile, params, connection);
        System.out.println ("printFile : " + printFile);

        JasperViewer.viewReport (printFile, false);
        System.out.println ("Done");
    }

    public void testViewCrosstabsByGoodsType () throws Exception
    {
        String jasperFile = JasperCompileManager.compileReportToFile ("src/test/resources/reports/crosstabs-goodsType.jrxml");

        Class.forName ("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection ("jdbc:mysql://localhost:3306/test?user=root&password=root");
        System.out.println ("Filling report...");
        Map <String, Object> params = new HashMap <String, Object> ();
        params.put ("reportTitle", "按类型统计货物价格");
        String printFile = JasperFillManager.fillReportToFile (jasperFile, params, connection);

        JasperViewer.viewReport (printFile, false);
        System.out.println ("Done");
    }

    public void testViewBeanCollectionDataSource () throws Exception
    {
        JRDataSource dataSource = new JRBeanCollectionDataSource ((List <Goods>) goodsDao.findAll ());

        String jasperFile = JasperCompileManager.compileReportToFile ("src/test/resources/reports/template-DbReportDS.jrxml");

        System.out.println ("Filling report...");
        String printFile = JasperFillManager.fillReportToFile (jasperFile, new HashMap <String, Object> (), dataSource);

        JasperViewer.viewReport (printFile, false);
        System.out.println ("Done!");
    }

    static
    {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext (
                                                                                    "META-INF/spring/purchase-charge-dao.xml");
        goodsDao = (GoodsDao) applicationContext.getBean ("goodsDao");
    }

    public static void main (String[] args) throws Exception
    {
        new PcJasperReportTest ().testViewPieChartByGoodsType ();
        new PcJasperReportTest ().testViewBarChartByGoodsType ();
        new PcJasperReportTest ().testViewCrosstabsByGoodsType ();
        // new PcJasperReportTest ().testViewBeanCollectionDataSource ();

    }

}
