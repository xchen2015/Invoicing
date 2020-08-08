package com.pinfly.purchasecharge.service.schedule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import com.pinfly.common.util.Mail;
import com.pinfly.purchasecharge.core.config.PurchaseChargeProperties;
import com.pinfly.purchasecharge.core.model.AccountingSearchForm;
import com.pinfly.purchasecharge.core.model.OrderSearchForm;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.User;
import com.pinfly.purchasecharge.core.util.Arith;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.core.util.PurchaseChargeUtils;
import com.pinfly.purchasecharge.service.QueryService;

public class SendReportMailJob implements Job
{
    private static final Logger logger = Logger.getLogger (SendReportMailJob.class);

    public static final String[] staticColumnHeaders =
    { "sumOrderReceivable", "sumProfit", "sumCustomerUnPay", "sumProviderUnPay" };
    private QueryService queryService;

    @Override
    public void execute (JobExecutionContext context) throws JobExecutionException
    {
        logger.info ("SendReportMailJob execute start");
        try
        {
            if (null == queryService)
            {
                this.queryService = (QueryService) context.getScheduler ().getContext ().get ("queryService");
            }
        }
        catch (SchedulerException e)
        {
            logger.warn (e.getMessage ());
        }

        User adminUser = queryService.getUser (PurchaseChargeProperties.getDefaultUser ());
        if (null != adminUser && StringUtils.isNotBlank (adminUser.getEmail ()))
        {
            Calendar c = Calendar.getInstance ();
            int nowYear = c.get (Calendar.YEAR);
            int nowMonth = c.get (Calendar.MONTH);
            Date[] dateRange = DateUtils.getDateRange (nowYear, nowMonth - 1);

            OrderSearchForm searchForm = new OrderSearchForm ();
            searchForm.setStartDate (dateRange[0]);
            searchForm.setEndDate (dateRange[1]);

            float sumOrderReceivable = 0;
            try
            {
                sumOrderReceivable = queryService.countOrderOutReceivableBySearchForm (null, searchForm, "", true);
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage ());
            }

            float sumOrderProfit = 0;
            try
            {
                sumOrderProfit = queryService.countOrderOutProfitBySearchForm (null, searchForm, "", true);
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage ());
            }

            AccountingSearchForm accountingSearchForm = new AccountingSearchForm ();
            accountingSearchForm.setStartDate (dateRange[0]);
            accountingSearchForm.setEndDate (dateRange[1]);
            float sumOtherRevenue = 0;
            try
            {
                sumOtherRevenue = queryService.countAccountingRevenue (accountingSearchForm);
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage ());
            }

            sumOrderReceivable = Arith.round (sumOrderReceivable, -1);
            float sumProfit = Arith.round (sumOrderProfit + sumOtherRevenue, -1);

            float sumCustomerUnPay = 0;
            float sumProviderUnPay = 0;
            try
            {
                sumCustomerUnPay = queryService.countCustomerUnpay ("", "", true);
                sumProviderUnPay = queryService.countProviderUnpay ("", "", true);
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage ());
            }
            sumCustomerUnPay = Arith.round (sumCustomerUnPay, -1);
            sumProviderUnPay = Arith.round (sumProviderUnPay, -1);

            try
            {
                String orderProfitReportTemplate = PurchaseChargeUtils.readFileToString ("orderProfitReportTemplate.html");
                List <String> configuredColumnHeaders = getColumnHeaderTemplate (orderProfitReportTemplate);
                List <String> availableColumnHeaders = new ArrayList <String> ();
                for (String orderColumnHeader : staticColumnHeaders)
                {
                    if (configuredColumnHeaders.contains (orderColumnHeader))
                    {
                        availableColumnHeaders.add (orderColumnHeader);
                    }
                }

                String tr = "";
                tr += "<tr>";
                for (String columnHeader : availableColumnHeaders)
                {
                    if (staticColumnHeaders[0].equalsIgnoreCase (columnHeader))
                    {
                        tr += "<td>" + sumOrderReceivable + "</td>";
                    }
                    else if (staticColumnHeaders[1].equalsIgnoreCase (columnHeader))
                    {
                        tr += "<td>" + sumProfit + "</td>";
                    }
                    else if (staticColumnHeaders[2].equalsIgnoreCase (columnHeader))
                    {
                        tr += "<td>" + sumCustomerUnPay + "</td>";
                    }
                    else if (staticColumnHeaders[3].equalsIgnoreCase (columnHeader))
                    {
                        tr += "<td>" + sumProviderUnPay + "</td>";
                    }
                }
                tr += "</tr>";

                orderProfitReportTemplate = orderProfitReportTemplate.replace ("{:tbody}", tr);
                String[] toAddrs =
                { adminUser.getEmail () };
                Mail.sendMessage (toAddrs,
                                  PurchaseChargeProperties.getInstance ().getConfigFormatted ("pc.sendReportMailTitle",
                                                                                              nowYear + "",
                                                                                              nowMonth + ""),
                                  orderProfitReportTemplate, null);
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage ());
            }
        }
    }

    private List <String> getColumnHeaderTemplate (String templateStr) throws IOException
    {
        Pattern pattern = Pattern.compile ("<th id=\".*@\"");
        Matcher matcher = pattern.matcher (templateStr);
        List <String> configuredColumnHeaders = new ArrayList <String> ();
        while (matcher.find ())
        {
            String th = matcher.group ();
            Pattern pattern2 = Pattern.compile ("\".*@");
            Matcher matcher2 = pattern2.matcher (th);
            while (matcher2.find ())
            {
                String ths = matcher2.group ();
                configuredColumnHeaders.add (ths.substring (1, ths.lastIndexOf ("@")));
            }
        }
        return configuredColumnHeaders;
    }

}
