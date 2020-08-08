package com.pinfly.purchasecharge.component.controller.report;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pinfly.purchasecharge.core.config.PurchaseChargeProperties;

public class GoodsReportManager extends MultiActionController
{
    private static final Logger logger = Logger.getLogger (GoodsReportManager.class);

    // @Autowired
    // private GoodsDao goodsDao;
    // @Autowired
    // private GoodsUnitDao goodsUnitDao;

    private static Connection connection;

    static
    {
        try
        {
            Class.forName (PurchaseChargeProperties.getDbDriver ());
        }
        catch (ClassNotFoundException e)
        {
            logger.warn (e.getMessage (), e);
        }
        try
        {
            // connection = DriverManager.getConnection
            // ("jdbc:mysql://localhost:3306/test?user=root&password=root");
            connection = DriverManager.getConnection (PurchaseChargeProperties.getDbUrl (),
                                                      PurchaseChargeProperties.getDbUser (),
                                                      PurchaseChargeProperties.getDbPassword ());
        }
        catch (SQLException e)
        {
            logger.warn (e.getMessage (), e);
        }
    }

    public void reportGoodsByPrice (HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        logger.debug ("report goods start");
        response.setContentType ("application/pdf;charset=utf-8");
        ServletOutputStream servletOutputStream = response.getOutputStream ();
        InputStream reportStream = this.getClass ().getResourceAsStream ("/reports/crosstabs-goodsType.jasper");

        Map <String, Object> params = new HashMap <String, Object> ();
        params.put ("reportTitle", "Statistics chart for goods price by type");
        params.put ("reportSubTitle", "按类型统计货物价格");
        JasperRunManager.runReportToPdfStream (reportStream, servletOutputStream, params, connection);
        servletOutputStream.flush ();
        servletOutputStream.close ();
        logger.debug ("report goods end");
    }

    public void generatePieForGoodsQuantityByType (HttpServletRequest request, HttpServletResponse response)
                                                                                                            throws Exception
    {
        logger.debug ("report goods start");
        response.setContentType ("application/pdf;charset=utf-8");
        ServletOutputStream servletOutputStream = response.getOutputStream ();
        InputStream reportStream = this.getClass ().getResourceAsStream ("/reports/pieChart-goodsType.jasper");

        Map <String, Object> params = new HashMap <String, Object> ();
        params.put ("reportTitle", "Statistics chart for goods quantity by type");
        JasperRunManager.runReportToPdfStream (reportStream, servletOutputStream, params, connection);
        servletOutputStream.flush ();
        servletOutputStream.close ();
        logger.debug ("report goods end");
    }

    public void generateBarForGoodsQuantityByType (HttpServletRequest request, HttpServletResponse response)
                                                                                                            throws Exception
    {
        logger.debug ("report goods start");
        response.setContentType ("application/pdf;charset=utf-8");
        ServletOutputStream servletOutputStream = response.getOutputStream ();
        InputStream reportStream = this.getClass ().getResourceAsStream ("/reports/barChart-goodsType.jasper");

        Map <String, Object> params = new HashMap <String, Object> ();
        params.put ("reportTitle", "Statistics chart for goods quantity by type");
        JasperRunManager.runReportToPdfStream (reportStream, servletOutputStream, params, connection);
        servletOutputStream.flush ();
        servletOutputStream.close ();
        logger.debug ("report goods end");
    }

}
