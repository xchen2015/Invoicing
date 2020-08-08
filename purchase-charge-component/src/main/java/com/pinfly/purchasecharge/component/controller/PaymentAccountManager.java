package com.pinfly.purchasecharge.component.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.ActionResult.ActionResultStatus;
import com.pinfly.purchasecharge.component.bean.DataGridRequestForm;
import com.pinfly.purchasecharge.component.bean.GenericPagingResult;
import com.pinfly.purchasecharge.component.bean.PaymentAccountBean;
import com.pinfly.purchasecharge.component.bean.PaymentTransferBean;
import com.pinfly.purchasecharge.component.bean.SearchRequestForm;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.BeanConvertUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.PaymentTransferSearchForm;
import com.pinfly.purchasecharge.core.model.PaymentTransferTypeCode;
import com.pinfly.purchasecharge.core.model.persistence.PaymentAccount;
import com.pinfly.purchasecharge.core.model.persistence.PaymentTransfer;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.User;
import com.pinfly.purchasecharge.core.util.Arith;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.dal.DaoContext;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/paymentAccount")
public class PaymentAccountManager extends GenericController <PaymentAccountBean>
{
    private static final Logger logger = Logger.getLogger (PaymentAccountManager.class);
    private String paymentAccountMessage = ComponentMessage.createMessage ("PAYMENT_ACCOUNT", "PAYMENT_ACCOUNT")
                                                           .getI18nMessageCode ();
    private String paymentTransferMessage = ComponentMessage.createMessage ("PAYMENT_ACCOUNT_TRANSFER",
                                                                            "PAYMENT_ACCOUNT_TRANSFER")
                                                            .getI18nMessageCode ();

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "paymentAccountFinance";
    }

    @Override
    protected String getJsName ()
    {
        return "javascript/paymentAccountJS";
    }

    @Override
    public String checkExist (@RequestParam String paymentAccountId, @RequestParam String name,
                              HttpServletRequest request)
    {
        if (StringUtils.isBlank (paymentAccountId))
        {
            // new
            PaymentAccount obj = componentContext.getQueryService ().getPaymentAccountByName (name);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long id = Long.parseLong (paymentAccountId);
            PaymentAccount paymentAccount = componentContext.getQueryService ().getPaymentAccountByName (name);
            if (null != paymentAccount && paymentAccount.getId () != id)
            {
                return "false";
            }
        }

        return "true";
    }

    @Override
    public String getAllModel (HttpServletRequest request)
    {
        List <PaymentAccount> paymentAccounts = (List <PaymentAccount>) componentContext.getQueryService ()
                                                                              .getAllPaymentAccount ();

        List <PaymentAccountBean> paymentAccountBeans = new ArrayList <PaymentAccountBean> ();
        for(PaymentAccount paymentAccount : paymentAccounts) 
        {
            paymentAccountBeans.add (BeanConvertUtils.paymentAccount2PaymentAccountBean (paymentAccount));
        }
        JSONArray jsonObject = JSONArray.fromObject (paymentAccountBeans);
        return jsonObject.toString ();
    }

    @Override
    protected String getModelBySearchForm (DataGridRequestForm dataGridRequestForm,
                                           SearchRequestForm searchRequestForm, HttpServletRequest request)
    {
        GenericPagingResult <PaymentAccountBean> paymentAccountResult = new GenericPagingResult <PaymentAccountBean> ();
        List <PaymentAccount> paymentAccounts = (List <PaymentAccount>) componentContext.getQueryService ()
                .getAllPaymentAccount ();

        List <PaymentAccountBean> paymentAccountBeans = new ArrayList <PaymentAccountBean> ();
        float sumRemainMoney = 0;
        for(PaymentAccount paymentAccount : paymentAccounts) 
        {
            sumRemainMoney += paymentAccount.getRemainMoney ();
            paymentAccountBeans.add (BeanConvertUtils.paymentAccount2PaymentAccountBean (paymentAccount));
        }
        paymentAccountResult.setRows (paymentAccountBeans);
        List<Map<String, String>> footer = new ArrayList <Map<String, String>> ();
        Map <String, String> map = new HashMap <String, String> ();
        map.put ("name", "合计");
        map.put ("remainMoney", Arith.round (sumRemainMoney, -1) + "");
        footer.add (map);
        paymentAccountResult.setFooter (footer);
        
        JSONObject json = JSONObject.fromObject (paymentAccountResult);
        return json.toString ();
    }

    @Override
    public String addModel (PaymentAccountBean bean, BindingResult bindingResult, HttpServletRequest request)
    {
        if (bindingResult.hasErrors ())
        {
            logger.warn (bindingResult.getAllErrors ());
            return createBadRequestResult (null);
        }
        else
        {
            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                PaymentAccount pa = BeanConvertUtils.paymentAccountBean2PaymentAccount (bean);
                try
                {
                    pa = componentContext.getPersistenceService ().addPaymentAccount (pa);
                    ar = createAddSuccessResult (paymentAccountMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createAddFailedResult (paymentAccountMessage);
                }

                if (ActionResult.ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventMsg = LogEventName.createEventName ("AddPaymentAccount",
                                                                           "LogEvent.AddPaymentAccount",
                                                                           request.getLocale ());
                        componentContext.getLogService ()
                                        .log (logEventMsg, loginUser.getUid (),
                                              logEventMsg + ": " + LogUtil.createLogComment (pa));
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
            return AjaxUtils.getJsonObject (ar);
        }
    }

    @Override
    public String updateModel (PaymentAccountBean bean, BindingResult bindingResult, HttpServletRequest request)
    {
        if (bindingResult.hasErrors ())
        {
            logger.warn (bindingResult.getAllErrors ());
            return createBadRequestResult (null);
        }
        else
        {
            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                PaymentAccount pa = BeanConvertUtils.paymentAccountBean2PaymentAccount (bean);
                PaymentAccount oldPaymentAcc = componentContext.getQueryService ().getPaymentAccount (pa.getId ());
                pa.setRemainMoney (oldPaymentAcc.getRemainMoney ());
                try
                {
                    pa = componentContext.getPersistenceService ().updatePaymentAccount (pa);
                    ar = createUpdateSuccessResult (paymentAccountMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage ());
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createUpdateFailedResult (paymentAccountMessage);
                }

                if (ActionResult.ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventMsg = LogEventName.createEventName ("UpdatePaymentAccount",
                                                                           "LogEvent.UpdatePaymentAccount",
                                                                           request.getLocale ());
                        componentContext.getLogService ()
                                        .log (logEventMsg,
                                              loginUser.getUid (),
                                              logEventMsg + ": "
                                                      + LogUtil.createLogComment (oldPaymentAcc, pa));
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
            return AjaxUtils.getJsonObject (ar);
        }
    }

    @Override
    public String deleteModels (@RequestParam String ids, HttpServletRequest request)
    {
        if (null != ids && ids.trim ().length () > 0)
        {
            List <PaymentAccount> paymentAccounts = new ArrayList <PaymentAccount> ();
            PaymentAccount paymentAccount;
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    paymentAccount = new PaymentAccount ();
                    paymentAccount.setId (Long.parseLong (typeId));
                    paymentAccounts.add (paymentAccount);
                }
            }
            else
            {
                paymentAccount = new PaymentAccount ();
                paymentAccount.setId (Long.parseLong (ids));
                paymentAccounts.add (paymentAccount);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    componentContext.getPersistenceService ().deletePaymentAccount (paymentAccounts);
                    ar = createDeleteSuccessResult (paymentAccountMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (paymentAccountMessage);
                }

                if (ActionResult.ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventMsg = LogEventName.createEventName ("DeletePaymentAccount",
                                                                           "LogEvent.DeletePaymentAccount",
                                                                           request.getLocale ());
                        componentContext.getLogService ().log (logEventMsg, loginUser.getUid (),
                                                               logEventMsg + ": " + ids);
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
            return AjaxUtils.getJsonObject (ar);
        }
        else
        {
            logger.warn (ActionResultStatus.BAD_REQUEST);
            return createBadRequestResult (null);
        }
    }

    @RequestMapping (value = "/checkExistByAccount", method = RequestMethod.POST)
    public @ResponseBody
    String checkExistByAccount (@RequestParam String paymentAccountId, @RequestParam String accountId)
    {
        if (StringUtils.isBlank (paymentAccountId))
        {
            // new
            PaymentAccount obj = componentContext.getQueryService ().getPaymentAccountByAccount (accountId);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long id = Long.parseLong (paymentAccountId);
            PaymentAccount paymentAccount = componentContext.getQueryService ().getPaymentAccountByAccount (accountId);
            if (null != paymentAccount && paymentAccount.getId () != id)
            {
                return "false";
            }
        }

        return "true";
    }

    @RequestMapping (value = "/addAccountTransfer", method = RequestMethod.POST)
    public @ResponseBody
    String addAccountTransfer (@Valid PaymentTransferBean bean, BindingResult bindingResult, HttpServletRequest request)
    {
        if (bindingResult.hasErrors ())
        {
            logger.warn (bindingResult.getAllErrors ());
            return createBadRequestResult (null);
        }
        else
        {
            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            try
            {
                if (null != loginUser)
                {
                    PaymentTransfer transfer = new PaymentTransfer ();
                    if (null != bean.getTargetAccountBean ()
                        && StringUtils.isNotBlank (bean.getTargetAccountBean ().getId ()))
                    {
                        PaymentAccount fromAccount = new PaymentAccount ();
                        fromAccount.setId (Long.parseLong (bean.getTargetAccountBean ().getId ()));
                        transfer.setTargetAccount (fromAccount);
                    }
                    if (StringUtils.isNotBlank (bean.getSource ()))
                    {
                        transfer.setSource (bean.getSource ());
                        transfer.setTransferTypeCode (PaymentTransferTypeCode.INTERNAL_TRANSFER);
                        transfer.setOutMoney (bean.getOutMoney ());
                    }
                    else
                    {
                        transfer.setTransferTypeCode (PaymentTransferTypeCode.RECHARGE);
                        transfer.setInMoney (bean.getInMoney ());
                    }
                    transfer.setComment (bean.getComment ());
                    transfer.setUserCreatedBy (loginUser.getUid ());

                    if (null != componentContext.getPersistenceService ().addPaymentTransfer (transfer))
                    {
                        ar = createAddSuccessResult (paymentTransferMessage);
                    }
                }
            }
            catch (PcServiceException e)
            {
                ar = createServerErrorMessageResult (e.getMessage ());
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage (), e);
                ar = createAddFailedResult (paymentTransferMessage);
            }
            return AjaxUtils.getJsonObject (ar);
        }
    }

    @RequestMapping (value = "/getAccountTransfer", method = RequestMethod.POST)
    public @ResponseBody
    String getAccountTransfer (DataGridRequestForm dataGridRequestForm, SearchRequestForm searchForm,
                               HttpServletRequest request)
    {
        List <PaymentTransferBean> list2 = new ArrayList <PaymentTransferBean> ();
        long total = 0;
        PaymentTransferSearchForm searchRequest = new PaymentTransferSearchForm ();
        long fromAccount = 0;
        try
        {
            searchRequest.setTransferTypeCode (searchForm.getTransferTypeCode ());
            if (StringUtils.isNotBlank (searchForm.getPaymentAccountId ()))
            {
                fromAccount = Long.parseLong (searchForm.getPaymentAccountId ());
            }
            searchRequest.setTargetAccount (fromAccount);
            if (StringUtils.isNotBlank (searchForm.getStartDate ()))
            {
                searchRequest.setStartDate (DateUtils.string2Date (searchForm.getStartDate () + " 00:00:000.000",
                                                                   DateUtils.DATE_TIME_MILLISECOND_PATTERN));
            }
            if (StringUtils.isNotBlank (searchForm.getEndDate ()))
            {
                searchRequest.setEndDate (DateUtils.string2Date (searchForm.getEndDate () + " 23:59:59.999",
                                                                 DateUtils.DATE_TIME_MILLISECOND_PATTERN));
            }
        }
        catch (Exception e)
        {
        }

        int page = dataGridRequestForm.getPage () - 1;
        int size = dataGridRequestForm.getRows ();
        Pageable pageable = new PageRequest (page, size);
        Page <PaymentTransfer> transferPage = DaoContext.getPaymentTransferDao ().findBySearchForm (pageable,
                                                                                                    searchRequest);
        List <PaymentTransfer> list = transferPage.getContent ();
        total = transferPage.getTotalElements ();
        if (CollectionUtils.isNotEmpty (list))
        {
            for (PaymentTransfer transfer : list)
            {
                PaymentTransferBean transferBean = new PaymentTransferBean ();
                transferBean.setId (transfer.getId () + "");
                transferBean.setTargetAccountBean (BeanConvertUtils.paymentAccount2PaymentAccountBean (transfer.getTargetAccount ()));
                if(StringUtils.isNotBlank (transfer.getSource ())) 
                {
                    transferBean.setSource (componentContext.getQueryService ().getPaymentTransferSource (transfer.getTransferTypeCode (), Long.parseLong (transfer.getSource ())));
                }
                transferBean.setTransferTypeCode (transfer.getTransferTypeCode ());
                transferBean.setInMoney (transfer.getInMoney ());
                transferBean.setOutMoney (transfer.getOutMoney ());
                transferBean.setRemainMoney (transfer.getRemainMoney ());
                transferBean.setComment (transfer.getComment ());
                transferBean.setCreateDate (DateUtils.date2String (transfer.getDateCreated (),
                                                                   DateUtils.DATE_TIME_PATTERN));
                User user = componentContext.getQueryService ().getUser (transfer.getUserCreatedBy ());
                if (null != user)
                {
                    transferBean.setUserCreated (user.getUserId ());
                }
                list2.add (transferBean);
            }
        }

        float sumInMoney = DaoContext.getPaymentTransferDao ().countInMoney (searchRequest);
        float sumOutMoney = DaoContext.getPaymentTransferDao ().countOutMoney (searchRequest);
        List<Map<String, String>> footer = new ArrayList <Map<String, String>> ();
        Map <String, String> map = new HashMap <String, String> ();
        map.put ("createDate", "合计");
        map.put ("inMoney", Arith.round (sumInMoney, -1) + "");
        map.put ("outMoney", Arith.round (sumOutMoney, -1) + "");
        footer.add (map);
        
        GenericPagingResult <PaymentTransferBean> pagingResult = new GenericPagingResult <PaymentTransferBean> ();
        pagingResult.setRows (list2);
        pagingResult.setTotal (total);
        pagingResult.setFooter (footer);
        JSONObject json = JSONObject.fromObject (pagingResult);
        return json.toString ();
    }

}
