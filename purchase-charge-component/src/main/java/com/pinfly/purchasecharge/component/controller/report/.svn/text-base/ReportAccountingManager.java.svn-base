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
import com.pinfly.purchasecharge.core.model.AccountingModeCode;
import com.pinfly.purchasecharge.core.model.persistence.Accounting;
import com.pinfly.purchasecharge.core.model.persistence.AccountingType;
import com.pinfly.purchasecharge.core.util.Arith;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.dal.AccountingDao;

@Controller
@RequestMapping ("/reportAccounting")
public class ReportAccountingManager extends GenericController <BaseBean>
{
    @SuppressWarnings ("unused")
    private static final Logger logger = Logger.getLogger (ReportAccountingManager.class);

    @Autowired
    private AccountingDao accountingDao;

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "reportAccounting";
    }

    @RequestMapping (value = "/generatePieForAccounting", method = RequestMethod.POST)
    public @ResponseBody
    String generatePieForAccounting (AccountingModeCode modeCode, @RequestParam String start, @RequestParam String end)
    {
        Timestamp std = DateUtils.date2Timestamp (DateUtils.string2Date (start + " 00:00:00.000", DateUtils.DATE_TIME_MILLISECOND_PATTERN));
        Timestamp endd = DateUtils.date2Timestamp (DateUtils.string2Date (end + " 23:59:59.999", DateUtils.DATE_TIME_MILLISECOND_PATTERN));

        Map <String, Float> accountingMoneyMap = new HashMap <String, Float> ();
        Float count = 0f;

        List <Accounting> accountings = accountingDao.findByTimeRange (std, endd);
        for (Accounting accounting : accountings)
        {
            AccountingType accountingType = accounting.getType ();
            boolean flag = false;
            if (null != modeCode)
            {
                if (null != accountingType && modeCode.equals (accountingType.getAccountingMode ()))
                {
                    flag = true;
                }
            }
            else
            {
                flag = true;
            }

            if (flag && null != accountingType)
            {
                count += accounting.getMoney ();
                String accountingName = accountingType.getName ();
                if (!accountingMoneyMap.containsKey (accountingName))
                {
                    accountingMoneyMap.put (accountingName, accounting.getMoney ());
                }
                else
                {
                    Float orderMoney = accountingMoneyMap.get (accountingName);
                    accountingMoneyMap.put (accountingName, orderMoney + accounting.getMoney ());
                }
            }
        }

        List <PieChartDataBean> chartDatas = new ArrayList <PieChartDataBean> ();
        for (Map.Entry <String, Float> entry : accountingMoneyMap.entrySet ())
        {
            float money = entry.getValue ();
            if (money < 0)
            {
                money = -money;
            }
            if (count < 0)
            {
                count = -count;
            }
            PieChartDataBean chartDataBean = new PieChartDataBean ();
            chartDataBean.setName (entry.getKey ());
            chartDataBean.setY (money / (count * 1.00f));
            chartDatas.add (chartDataBean);
        }

        if (CollectionUtils.isNotEmpty (chartDatas))
        {
            Collections.sort (chartDatas);
            chartDatas.get (0).setSliced (true);
        }

        JSONArray json = JSONArray.fromObject (chartDatas);
        return json.toString ();
    }

    @RequestMapping (value = "/generateColumnForAccounting", method = RequestMethod.POST)
    public @ResponseBody
    String generateColumnForAccounting (AccountingModeCode modeCode, @RequestParam String start,
                                        @RequestParam String end)
    {
        Timestamp std = DateUtils.date2Timestamp (DateUtils.string2Date (start + " 00:00:00.000", DateUtils.DATE_TIME_MILLISECOND_PATTERN));
        Timestamp endd = DateUtils.date2Timestamp (DateUtils.string2Date (end + " 23:59:59.999", DateUtils.DATE_TIME_MILLISECOND_PATTERN));

        Map <String, Float> accountingMoneyMap = new HashMap <String, Float> ();

        List <Accounting> accountings = accountingDao.findByTimeRange (std, endd);
        for (Accounting accounting : accountings)
        {
            AccountingType accountingType = accounting.getType ();
            boolean flag = false;
            if (null != modeCode)
            {
                if (null != accountingType && modeCode.equals (accountingType.getAccountingMode ()))
                {
                    flag = true;
                }
            }
            else
            {
                flag = true;
            }

            if (flag && null != accountingType)
            {
                String accountingName = accountingType.getName ();
                if (!accountingMoneyMap.containsKey (accountingName))
                {
                    accountingMoneyMap.put (accountingName, accounting.getMoney ());
                }
                else
                {
                    Float orderMoney = accountingMoneyMap.get (accountingName);
                    accountingMoneyMap.put (accountingName, orderMoney + accounting.getMoney ());
                }
            }
        }

        List <PieChartDataBean> chartDatas = new ArrayList <PieChartDataBean> ();
        for (Map.Entry <String, Float> entry : accountingMoneyMap.entrySet ())
        {
            float money = entry.getValue ();
            if (money < 0)
            {
                money = -money;
            }
            PieChartDataBean chartDataBean = new PieChartDataBean ();
            chartDataBean.setName (entry.getKey ());
            chartDataBean.setY (Arith.round (money, -1));
            chartDatas.add (chartDataBean);
        }

        if (CollectionUtils.isNotEmpty (chartDatas))
        {
            Collections.sort (chartDatas);
            chartDatas.get (0).setSliced (true);
        }

        JSONArray json = JSONArray.fromObject (chartDatas);
        return json.toString ();
    }

}
