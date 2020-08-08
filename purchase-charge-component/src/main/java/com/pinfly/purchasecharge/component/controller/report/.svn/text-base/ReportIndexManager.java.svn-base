package com.pinfly.purchasecharge.component.controller.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinfly.purchasecharge.component.bean.ComponentContext;
import com.pinfly.purchasecharge.component.bean.LineChartDataBean;
import com.pinfly.purchasecharge.core.model.AccountingSearchForm;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.OrderSearchForm;
import com.pinfly.purchasecharge.core.util.Arith;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.dal.DaoContext;
import com.pinfly.purchasecharge.service.QueryService;

@Controller
@RequestMapping ("/reportIndex")
public class ReportIndexManager
{
    private static final Logger logger = Logger.getLogger (ReportIndexManager.class);

    @Autowired
    protected QueryService queryService;
    @Autowired
    protected ComponentContext pcContext;

    @RequestMapping (value = "/")
    public String redirectToMainView (ModelMap model)
    {
        return "reportIndex";
    }

    @RequestMapping (value = "/generateLineForOrderVolumePerMonth", method = RequestMethod.POST)
    public @ResponseBody
    String generateLineForOrderVolumePerMonth (HttpServletRequest request)
    {
        LoginUser loginUser = pcContext.getLoginUser (request);
        if (null != loginUser)
        {
            Calendar c = Calendar.getInstance ();
            int nowYear = c.get (Calendar.YEAR);
            int nowMonth = c.get (Calendar.MONTH);

            Date oldestOrderDate = DaoContext.getOrderOutDao ().findOldest ();
            c.setTime (oldestOrderDate);
            int oldestOrderYear = c.get (Calendar.YEAR);

            List <LineChartDataBean> lineDataBeans = new ArrayList <LineChartDataBean> ();
            for (int year = oldestOrderYear; year <= nowYear; year++)
            {
                LineChartDataBean lineDataBean = new LineChartDataBean ();
                lineDataBean.setName (year + "");
                float[] monthTotal = new float[12];
                if (year == nowYear)
                {
                    monthTotal = new float[nowMonth + 1];
                }
                for (int month = 0; month < monthTotal.length; month++)
                {
                    Date[] dateRange = DateUtils.getDateRange (year, month);

                    OrderSearchForm searchForm = new OrderSearchForm ();
                    searchForm.setStartDate (dateRange[0]);
                    searchForm.setEndDate (dateRange[1]);
                    float total = 0;
                    try
                    {
                        total = queryService.countOrderOutReceivableBySearchForm (null, searchForm,
                                                                               loginUser.getUserId (),
                                                                               loginUser.isAdmin ());
                    }
                    catch (Exception e)
                    {
                        logger.warn (e.getMessage ());
                    }
                    monthTotal[month] = Arith.round (total, -1);
                }
                lineDataBean.setData (monthTotal);
                lineDataBeans.add (lineDataBean);
            }

            JSONArray json = JSONArray.fromObject (lineDataBeans);
            return json.toString ();
        }
        return "";
    }

    @RequestMapping (value = "/generateLineForOrderProfitPerMonth", method = RequestMethod.POST)
    public @ResponseBody
    String generateLineForOrderProfitPerMonth (HttpServletRequest request)
    {
        LoginUser loginUser = pcContext.getLoginUser (request);
        if (null != loginUser)
        {
            Calendar c = Calendar.getInstance ();
            int nowYear = c.get (Calendar.YEAR);
            int nowMonth = c.get (Calendar.MONTH);

            Date oldestOrderDate = DaoContext.getOrderOutDao ().findOldest ();
            c.setTime (oldestOrderDate);
            int oldestOrderYear = c.get (Calendar.YEAR);

            List <LineChartDataBean> lineDataBeans = new ArrayList <LineChartDataBean> ();
            for (int year = oldestOrderYear; year <= nowYear; year++)
            {
                LineChartDataBean lineDataBean = new LineChartDataBean ();
                lineDataBean.setName (year + "");
                float[] monthTotal = new float[12];
                if (year == nowYear)
                {
                    monthTotal = new float[nowMonth + 1];
                }
                for (int month = 0; month < monthTotal.length; month++)
                {
                    float total = 0;
                    Date[] dateRange = DateUtils.getDateRange (year, month);

                    OrderSearchForm searchForm = new OrderSearchForm ();
                    searchForm.setStartDate (dateRange[0]);
                    searchForm.setEndDate (dateRange[1]);

                    try
                    {
                        total = queryService.countOrderOutProfitBySearchForm (null, searchForm,
                                                                           loginUser.getUserId (), loginUser.isAdmin ());
                    }
                    catch (Exception e)
                    {
                        logger.warn (e.getMessage ());
                    }

                    AccountingSearchForm accountingSearchForm = new AccountingSearchForm ();
                    accountingSearchForm.setStartDate (dateRange[0]);
                    accountingSearchForm.setEndDate (dateRange[1]);

                    float accountingRevenue = 0;
                    try
                    {
                        accountingRevenue = queryService.countAccountingRevenue (accountingSearchForm);
                    }
                    catch (Exception e)
                    {
                        logger.warn (e.getMessage ());
                    }
                    total += accountingRevenue;
                    monthTotal[month] = Arith.round (total, -1);
                }
                lineDataBean.setData (monthTotal);
                lineDataBeans.add (lineDataBean);
            }

            JSONArray json = JSONArray.fromObject (lineDataBeans);
            return json.toString ();
        }
        return "";
    }

}
