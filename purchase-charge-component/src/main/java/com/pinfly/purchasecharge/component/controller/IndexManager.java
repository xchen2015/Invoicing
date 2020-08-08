package com.pinfly.purchasecharge.component.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinfly.purchasecharge.component.bean.BaseBean;
import com.pinfly.purchasecharge.core.model.AccountingSearchForm;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.OrderSearchForm;
import com.pinfly.purchasecharge.core.model.PaymentAccountMode;
import com.pinfly.purchasecharge.core.model.persistence.PaymentAccount;
import com.pinfly.purchasecharge.core.util.Arith;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.dal.DaoContext;

@Controller
@RequestMapping ("/index")
public class IndexManager extends GenericController <BaseBean>
{
    private static final Logger logger = Logger.getLogger (IndexManager.class);

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "myIndex";
    }

    @Override
    protected String getJsName ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getCssName ()
    {
        return "stylesheet/myIndexCSS";
    }

    @RequestMapping (value = "/countReceivableAndProfit", method = RequestMethod.POST)
    public @ResponseBody
    String countReceivableAndProfit (HttpServletRequest request)
    {
        LoginUser loginUser = componentContext.getLoginUser (request);
        if (null != loginUser)
        {
            Calendar c = Calendar.getInstance ();
            int nowYear = c.get (Calendar.YEAR);
            int nowMonth = c.get (Calendar.MONTH);
            Date[] dateRange = DateUtils.getDateRange (nowYear, nowMonth);

            OrderSearchForm searchForm = new OrderSearchForm ();
            searchForm.setStartDate (dateRange[0]);
            searchForm.setEndDate (dateRange[1]);

            float sumOrderReceivable = 0;
            try
            {
                sumOrderReceivable = componentContext.getQueryService ()
                                                     .countOrderOutReceivableBySearchForm (null, searchForm,
                                                                                        loginUser.getUserId (),
                                                                                        loginUser.isAdmin ());
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage ());
            }

            float sumOrderProfit = 0;
            try
            {
                sumOrderProfit = componentContext.getQueryService ()
                                                 .countOrderOutProfitBySearchForm (null, searchForm,
                                                                                loginUser.getUserId (),
                                                                                loginUser.isAdmin ());
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
                if (loginUser.isAdmin ())
                {
                    sumOtherRevenue = componentContext.getQueryService ().countAccountingRevenue (accountingSearchForm);
                }
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage ());
            }

            float[] countData = new float[2];
            countData[0] = Arith.round (sumOrderReceivable, -1);
            countData[1] = Arith.round (sumOrderProfit + sumOtherRevenue, -1);

            JSONArray json = JSONArray.fromObject (countData);
            return json.toString ();
        }
        return "";
    }

    @RequestMapping (value = "/countReceivableAndPayment", method = RequestMethod.POST)
    public @ResponseBody
    String countReceivableAndPayment (HttpServletRequest request)
    {
        LoginUser loginUser = componentContext.getLoginUser (request);
        float customerUnPay = 0;
        float providerUnPay = 0;
        try
        {
            if (null != loginUser)
            {
                customerUnPay = componentContext.getQueryService ().countCustomerUnpay ("", loginUser.getUserId (),
                                                                                        loginUser.isAdmin ());
                providerUnPay = componentContext.getQueryService ().countProviderUnpay ("", loginUser.getUserId (),
                                                                                        loginUser.isAdmin ());
            }
        }
        catch (Exception e)
        {
            logger.warn (e.getMessage ());
        }
        float[] unPayData = new float[2];
        unPayData[0] = Arith.round (customerUnPay, -1);
        unPayData[1] = Arith.round (providerUnPay, -1);

        JSONArray json = JSONArray.fromObject (unPayData);
        return json.toString ();
    }

    @RequestMapping (value = "/countRestAmountAndWorth", method = RequestMethod.POST)
    public @ResponseBody
    String countRestAmountAndWorth (HttpServletRequest request)
    {
        LoginUser loginUser = componentContext.getLoginUser (request);
        long sumGoodsAmount = 0;
        float sumGoodsWorth = 0;
        try
        {
            if (null != loginUser)
            {
                sumGoodsAmount = componentContext.getQueryService ().countGoodsRestAmount (0, "");
                sumGoodsWorth = componentContext.getQueryService ().countGoodsRestWorth (0, "");
            }
        }
        catch (Exception e)
        {
            logger.warn (e.getMessage ());
        }
        float[] goodsRestData = new float[2];
        goodsRestData[0] = sumGoodsAmount;
        goodsRestData[1] = Arith.round (sumGoodsWorth, -1);

        JSONArray json = JSONArray.fromObject (goodsRestData);
        return json.toString ();
    }

    @RequestMapping (value = "/countPurchaseMoneyAndGoodsIncrement", method = RequestMethod.POST)
    public @ResponseBody
    String countPurchaseMoneyAndGoodsIncrement (HttpServletRequest request)
    {
        LoginUser loginUser = componentContext.getLoginUser (request);
        if (null != loginUser)
        {
            Calendar c = Calendar.getInstance ();
            int nowYear = c.get (Calendar.YEAR);
            int nowMonth = c.get (Calendar.MONTH);
            Date[] dateRange = DateUtils.getDateRange (nowYear, nowMonth);

            OrderSearchForm searchForm = new OrderSearchForm ();
            searchForm.setStartDate (dateRange[0]);
            searchForm.setEndDate (dateRange[1]);

            float purchaseMoney = 0;
            try
            {
                if (loginUser.isAdmin ())
                {
                    purchaseMoney = componentContext.getQueryService ()
                                                    .countOrderInReceivableBySearchForm (null, searchForm,
                                                                                       loginUser.getUserId (),
                                                                                       loginUser.isAdmin ());
                }
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage ());
            }

            long goodsIncrement = 0;
            try
            {
                goodsIncrement = componentContext.getQueryService ().countGoodsIncrement (dateRange[0], dateRange[1]);
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage ());
            }

            float[] goodsData = new float[2];
            goodsData[0] = Arith.round (purchaseMoney, -1);
            goodsData[1] = goodsIncrement;

            JSONArray json = JSONArray.fromObject (goodsData);
            return json.toString ();
        }
        return "";
    }

    @RequestMapping (value = "/countDepositAndCash", method = RequestMethod.POST)
    public @ResponseBody
    String countDepositAndCash (HttpServletRequest request)
    {
        LoginUser loginUser = componentContext.getLoginUser (request);
        if (null != loginUser)
        {
            float deposit = 0;
            float cash = 0;
            if (loginUser.isAdmin ())
            {
                List <PaymentAccount> paymentAccounts = (List <PaymentAccount>) DaoContext.getPaymentAccountDao ()
                                                                                          .findAll ();
                if (CollectionUtils.isNotEmpty (paymentAccounts))
                {
                    for (PaymentAccount paymentAccount : paymentAccounts)
                    {
                        if (PaymentAccountMode.CASH.equals (paymentAccount.getAccountMode ()))
                        {
                            cash += paymentAccount.getRemainMoney ();
                        }
                        else if (PaymentAccountMode.DEPOSIT.equals (paymentAccount.getAccountMode ()))
                        {
                            deposit += paymentAccount.getRemainMoney ();
                        }
                    }
                }
            }

            float[] moneyData = new float[2];
            moneyData[0] = Arith.round (cash, -1);
            moneyData[1] = Arith.round (deposit, -1);

            JSONArray json = JSONArray.fromObject (moneyData);
            return json.toString ();
        }
        return "";
    }

}
