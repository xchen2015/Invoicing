package com.pinfly.purchasecharge.component.controller.report;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinfly.purchasecharge.component.bean.BaseBean;
import com.pinfly.purchasecharge.component.bean.PieChartDataBean;
import com.pinfly.purchasecharge.component.controller.GenericController;
import com.pinfly.purchasecharge.core.model.persistence.in.OrderIn;
import com.pinfly.purchasecharge.core.model.persistence.in.Provider;
import com.pinfly.purchasecharge.core.util.Arith;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.dal.in.OrderInDao;

@Controller
@RequestMapping ("/reportProvider")
public class ReportProviderManager extends GenericController <BaseBean>
{
    @SuppressWarnings ("unused")
    private static final Logger logger = Logger.getLogger (ReportProviderManager.class);

    @Autowired
    private OrderInDao orderInDao;

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "reportProvider";
    }

    @RequestMapping (value = "/generatePieForProviderSalesVolume", method = RequestMethod.POST)
    public @ResponseBody
    String generatePieForProviderSalesVolume (@RequestParam String providerType, @RequestParam String start,
                                              @RequestParam String end)
    {
        Timestamp std = DateUtils.date2Timestamp (DateUtils.string2Date (start + " 00:00:00.000", DateUtils.DATE_TIME_MILLISECOND_PATTERN));
        Timestamp endd = DateUtils.date2Timestamp (DateUtils.string2Date (end + " 23:59:59.999", DateUtils.DATE_TIME_MILLISECOND_PATTERN));

        Map <String, Float> customerSalesMoneyMap = new HashMap <String, Float> ();
        Float count = 0f;
        List <OrderIn> orderInFinishedList = orderInDao.findByRangeCreateDate (std, endd);
        for (OrderIn order : orderInFinishedList)
        {
            boolean flag = false;
            if (StringUtils.isNotBlank (providerType))
            {
                long customerTypeId = Long.parseLong (providerType);
                Provider customer = order.getProvider ();
                if (null != customer && null != customer.getProviderType ()
                    && customerTypeId == customer.getProviderType ().getId ())
                {
                    flag = true;
                }
            }
            else
            {
                flag = true;
            }

            if (flag)
            {
                count += order.getReceivable ();
                String customerName = order.getProvider ().getShortName ();
                if (!customerSalesMoneyMap.containsKey (customerName))
                {
                    customerSalesMoneyMap.put (customerName, order.getReceivable ());
                }
                else
                {
                    Float orderMoney = customerSalesMoneyMap.get (customerName);
                    customerSalesMoneyMap.put (customerName, orderMoney + order.getReceivable ());
                }
            }
        }

        List <PieChartDataBean> chartDatas = new ArrayList <PieChartDataBean> ();
        for (Map.Entry <String, Float> entry : customerSalesMoneyMap.entrySet ())
        {
            if (entry.getValue () > 0)
            {
                PieChartDataBean chartDataBean = new PieChartDataBean ();
                chartDataBean.setName (entry.getKey ());
                chartDataBean.setY (entry.getValue () / (count * 1.00f));
                chartDatas.add (chartDataBean);
            }
        }

        if (CollectionUtils.isNotEmpty (chartDatas))
        {
            Collections.sort (chartDatas);
            chartDatas.get (0).setSliced (true);
        }

        JSONArray json = JSONArray.fromObject (chartDatas);
        return json.toString ();
    }

    @RequestMapping (value = "/generateColumnForProviderSalesVolume", method = RequestMethod.POST)
    public @ResponseBody
    String generateColumnForProviderSalesVolume (@RequestParam String providerType, @RequestParam String start,
                                                 @RequestParam String end)
    {
        Timestamp std = DateUtils.date2Timestamp (DateUtils.string2Date (start + " 00:00:00.000", DateUtils.DATE_TIME_MILLISECOND_PATTERN));
        Timestamp endd = DateUtils.date2Timestamp (DateUtils.string2Date (end + " 23:59:59.999", DateUtils.DATE_TIME_MILLISECOND_PATTERN));

        Map <String, Float> customerSalesMoneyMap = new HashMap <String, Float> ();
        List <OrderIn> orderInFinishedList = orderInDao.findByRangeCreateDate (std, endd);
        for (OrderIn order : orderInFinishedList)
        {
            boolean flag = false;
            if (StringUtils.isNotBlank (providerType))
            {
                long customerTypeId = Long.parseLong (providerType);
                Provider customer = order.getProvider ();
                if (null != customer && null != customer.getProviderType ()
                    && customerTypeId == customer.getProviderType ().getId ())
                {
                    flag = true;
                }
            }
            else
            {
                flag = true;
            }

            if (flag)
            {
                String customerName = order.getProvider ().getShortName ();
                if (!customerSalesMoneyMap.containsKey (customerName))
                {
                    customerSalesMoneyMap.put (customerName, order.getReceivable ());
                }
                else
                {
                    Float orderMoney = customerSalesMoneyMap.get (customerName);
                    customerSalesMoneyMap.put (customerName, orderMoney + order.getReceivable ());
                }
            }
        }

        List <PieChartDataBean> chartDatas = new ArrayList <PieChartDataBean> ();
        for (Map.Entry <String, Float> entry : customerSalesMoneyMap.entrySet ())
        {
            PieChartDataBean chartDataBean = new PieChartDataBean ();
            chartDataBean.setName (entry.getKey ());
            chartDataBean.setY (Arith.round (entry.getValue (), -1));
            chartDatas.add (chartDataBean);
        }

        JSONArray json = JSONArray.fromObject (chartDatas);
        return json.toString ();
    }

}
