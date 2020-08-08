package com.pinfly.purchasecharge.component.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinfly.purchasecharge.component.bean.AccountingBean;
import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.ActionResult.ActionResultStatus;
import com.pinfly.purchasecharge.component.bean.CustomerBean;
import com.pinfly.purchasecharge.component.bean.DataGridRequestForm;
import com.pinfly.purchasecharge.component.bean.GenericPagingResult;
import com.pinfly.purchasecharge.component.bean.SearchRequestForm;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.BeanConvertUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.AccountingModeCode;
import com.pinfly.purchasecharge.core.model.AccountingSearchForm;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.persistence.Accounting;
import com.pinfly.purchasecharge.core.model.persistence.in.Provider;
import com.pinfly.purchasecharge.core.model.persistence.out.Customer;
import com.pinfly.purchasecharge.core.util.Arith;
import com.pinfly.purchasecharge.core.util.PurchaseChargeConstants;
import com.pinfly.purchasecharge.dal.DaoContext;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/accounting")
public class AccountingManager extends GenericController <AccountingBean>
{
    private static final Logger logger = Logger.getLogger (AccountingManager.class);
    private String accountingMessage = ComponentMessage.createMessage ("EXPENSE", "EXPENSE").getI18nMessageCode ();

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "accountingManagement";
    }

    @Override
    protected String getJsName ()
    {
        return "javascript/accountingMgmtJS";
    }

    @Override
    protected String getCssName ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getModelBySearchForm (DataGridRequestForm dataGridRequestForm, SearchRequestForm searchRequestForm,
                                        HttpServletRequest request)
    {
        logger.debug (dataGridRequestForm);
        int page = dataGridRequestForm.getPage () - 1;
        int size = dataGridRequestForm.getRows ();
        String sortField = parseSortField (dataGridRequestForm.getSort ());
        String order = dataGridRequestForm.getOrder ();
        Pageable pageable = new PageRequest (page, size, new Sort ("asc".equalsIgnoreCase (order) ? Direction.ASC
                                                                                                 : Direction.DESC,
                                                                   sortField));
        LoginUser loginUser = componentContext.getLoginUser (request);
        if (null != loginUser)
        {
            AccountingSearchForm searchForm = BeanConvertUtils.searchRequestForm2AccountingSearchForm (searchRequestForm);
            Page <Accounting> expensePage = componentContext.getQueryService ().findPageAccounting (searchForm,
                                                                                                    pageable);
            float sumOutMoney = componentContext.getQueryService ().countAccountingExpense (searchForm);
            float sumInMoney = componentContext.getQueryService ().countAccountingIncome (searchForm);
            float sumRevenue = componentContext.getQueryService ().countAccountingRevenue (searchForm);
            List <Map <String, String>> footer = new ArrayList <Map <String, String>> ();
            Map <String, String> map = new HashMap <String, String> ();
            map.put ("mode", "支出合计");
            map.put ("money", Arith.round (sumOutMoney, -1) + "");
            footer.add (map);
            map = new HashMap <String, String> ();
            map.put ("mode", "收入合计");
            map.put ("money", Arith.round (sumInMoney, -1) + "");
            footer.add (map);
            map = new HashMap <String, String> ();
            map.put ("mode", "总计");
            map.put ("money", Arith.round (sumRevenue, -1) + "");
            footer.add (map);

            long total = expensePage.getTotalElements ();
            GenericPagingResult <AccountingBean> expensePagingResult = new GenericPagingResult <AccountingBean> ();
            List <AccountingBean> accountingBeans = new ArrayList <AccountingBean> ();
            for(Accounting accounting : expensePage.getContent ()) 
            {
                AccountingBean accountingBean = BeanConvertUtils.accounting2AccountingBean (accounting);
                accountingBean.setCustomerName (getAccountingCustomerName (accounting.getCustomerId ()));
                accountingBeans.add (accountingBean);
            }
            expensePagingResult.setRows (accountingBeans);
            expensePagingResult.setTotal (total);
            expensePagingResult.setFooter (footer);

            JSONObject jsonObject = JSONObject.fromObject (expensePagingResult);
            return jsonObject.toString ();
        }
        return "";
    }

    @Override
    public String addModel (AccountingBean bean, BindingResult result, HttpServletRequest request)
    {
        if (result.hasErrors ())
        {
            logger.warn (result.getAllErrors ());
            return createBadRequestResult (null);
        }
        else
        {
            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                Accounting expense = BeanConvertUtils.accountingBean2Accounting (bean);
                expense.setUpdated (expense.getCreated ());
                expense.setUserCreatedBy (componentContext.getQueryService ()
                                                          .getUniqueIdByUserId (loginUser.getUserId ()));
                expense.setUserUpdatedBy (componentContext.getQueryService ()
                                                          .getUniqueIdByUserId (loginUser.getUserId ()));

                try
                {
                    expense = componentContext.getPersistenceService ().addAccounting (expense);
                    ar = createAddSuccessResult (accountingMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage ());
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createAddFailedResult (accountingMessage);
                }

                if (ActionResult.ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventMsg = "";
                        if (AccountingModeCode.OUT_LAY.equals (expense.getType ().getAccountingMode ()))
                        {
                            logEventMsg = LogEventName.createEventName ("AddExpenseOutlay",
                                                                        "LogEvent.AddExpenseOutlay",
                                                                        request.getLocale ());
                        }
                        else
                        {
                            logEventMsg = LogEventName.createEventName ("AddExpenseIncome",
                                                                        "LogEvent.AddExpenseIncome",
                                                                        request.getLocale ());
                        }
                        componentContext.getLogService ().log (logEventMsg,
                                                               loginUser.getUid (),
                                                               logEventMsg + ": "
                                                                       + LogUtil.createLogComment (expense));
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
    public String updateModel (AccountingBean bean, BindingResult result, HttpServletRequest request)
    {
        if (result.hasErrors ())
        {
            logger.warn (result.getAllErrors ());
            return createBadRequestResult (null);
        }
        else
        {
            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                Accounting expense = BeanConvertUtils.accountingBean2Accounting (bean);
                expense.setUpdated (new Timestamp (System.currentTimeMillis ()));
                expense.setUserUpdatedBy (componentContext.getQueryService ()
                                                          .getUniqueIdByUserId (loginUser.getUserId ()));

                try
                {
                    componentContext.getPersistenceService ().updateAccounting (expense);
                    ar = createUpdateSuccessResult (accountingMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage ());
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createUpdateFailedResult (accountingMessage);
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
            List <Accounting> expenses = new ArrayList <Accounting> ();
            Accounting expense;
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    long expenseId = Long.parseLong (typeId);
                    expense = DaoContext.getAccountingDao ().findOne (expenseId);
                    expenses.add (expense);
                }
            }
            else
            {
                long expenseId = Long.parseLong (ids);
                expense = DaoContext.getAccountingDao ().findOne (expenseId);
                expenses.add (expense);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                String logEventMsg = LogEventName.createEventName ("DeleteExpense", "LogEvent.DeleteExpense",
                                                                   request.getLocale ());
                String logComment = logEventMsg + ": ";
                try
                {
                    for (Accounting acc : expenses)
                    {
                        logComment += ("[" + acc.getType ().getName () + " " + acc.getPaymentAccount ().getName ()
                                       + " " + acc.getMoney () + "] ");
                    }
                    componentContext.getPersistenceService ().deleteAccounting (expenses);
                    ar = createDeleteSuccessResult (accountingMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage ());
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (accountingMessage);
                }

                if (ActionResult.ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        componentContext.getLogService ().log (logEventMsg, loginUser.getUid (), logComment);
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
            logger.warn (ActionResultStatus.BAD_REQUEST + " ids=" + ids + " when delete expense type");
            return createBadRequestResult (null);
        }
    }
    
    @RequestMapping (value = "/getAllCustomerProvider", method = RequestMethod.POST)
    public @ResponseBody
    String getAllCustomerProvider ()
    {
        List <Customer> customers = componentContext.getQueryService ().getAllCustomer ("", true);
        List <Provider> providers = componentContext.getQueryService ().getAllProvider ("", true);
        List <CustomerBean> customerBeans = new ArrayList <CustomerBean> ();
        for(Customer customer : customers) 
        {
            CustomerBean customerBean = BeanConvertUtils.customer2CustomerBean (customer);
            customerBean.setGroup ("C");
            customerBeans.add (customerBean);
        }
        for(Provider provider : providers) 
        {
            CustomerBean customerBean = BeanConvertUtils.provider2CustomerBean (provider);
            customerBean.setGroup ("P");
            customerBeans.add (customerBean);
        }
        JSONArray jsonArray = JSONArray.fromObject (customerBeans);
        return jsonArray.toString ();
    }
    
    private String getAccountingCustomerName (String customerId) 
    {
        long custId = 0;
        try 
        {
            custId = Long.parseLong (customerId);
        }
        catch (Exception e) {
            logger.warn (e.getMessage ());
        }
        
        String customerName = "";
        Customer customer = componentContext.getQueryService ().getCustomer (custId);
        if(null != customer) 
        {
            customerName = customer.getShortName ();
        }
        Provider provider = componentContext.getQueryService ().getProvider (custId);
        if(null != provider) 
        {
            customerName = provider.getShortName ();
        }
        return customerName;
    }

    private String parseSortField (final String sortField)
    {
        String sortFieldAfterParse = PurchaseChargeConstants.EXPENSE_CREATE;
        if (!StringUtils.isBlank (sortField))
        {
            sortFieldAfterParse = sortField;
            if (PurchaseChargeConstants.EXPENSE_TYPE.equalsIgnoreCase (sortField))
            {
                sortFieldAfterParse = PurchaseChargeConstants.EXPENSE_TYPE_DOT_NAME;
            }
            else if (PurchaseChargeConstants.EXPENSE_CREATE_DATE.equalsIgnoreCase (sortField))
            {
                sortFieldAfterParse = PurchaseChargeConstants.EXPENSE_CREATE;
            }
            else if (PurchaseChargeConstants.EXPENSE_UPDATE_DATE.equalsIgnoreCase (sortField))
            {
                sortFieldAfterParse = PurchaseChargeConstants.EXPENSE_UPDATE;
            }
        }
        return sortFieldAfterParse;
    }

}
