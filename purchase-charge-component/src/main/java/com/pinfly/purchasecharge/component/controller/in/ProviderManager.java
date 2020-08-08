package com.pinfly.purchasecharge.component.controller.in;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
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

import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.ActionResult.ActionResultStatus;
import com.pinfly.purchasecharge.component.bean.CustomerBean;
import com.pinfly.purchasecharge.component.bean.DataGridRequestForm;
import com.pinfly.purchasecharge.component.bean.GenericPagingResult;
import com.pinfly.purchasecharge.component.bean.SearchRequestForm;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.controller.GenericController;
import com.pinfly.purchasecharge.component.controller.out.CustomerManager;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.BeanConvertUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.persistence.in.OrderIn;
import com.pinfly.purchasecharge.core.model.persistence.in.Provider;
import com.pinfly.purchasecharge.core.model.persistence.in.ProviderContact;
import com.pinfly.purchasecharge.core.model.persistence.in.ProviderPayment;
import com.pinfly.purchasecharge.core.util.Arith;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.dal.DaoContext;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/provider")
public class ProviderManager extends GenericController <CustomerBean>
{
    private static final Logger logger = Logger.getLogger (ProviderManager.class);
    private String providerMessage = ComponentMessage.createMessage ("PROVIDER", "PROVIDER").getI18nMessageCode ();

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "providerManagement";
    }

    @Override
    protected String getJsName ()
    {
        return "javascript/providerMgmtJS";
    }

    @Override
    protected String getCssName ()
    {
        return "stylesheet/providerMgmtCSS";
    }

    @Override
    public String checkExist (@RequestParam String providerId, @RequestParam String name, HttpServletRequest request)
    {
        if (StringUtils.isBlank (providerId))
        {
            // new
            Provider obj = componentContext.getQueryService ().getProvider (name);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long id = Long.parseLong (providerId);
            Provider customer = componentContext.getQueryService ().getProvider (name);
            if (null != customer && customer.getId () != id)
            {
                return "false";
            }
        }

        return "true";
    }

    @Override
    public String getAllModel (HttpServletRequest request)
    {
        List <Provider> providerList = componentContext.getQueryService ().getAllProvider ("", true);
        List <Provider> providerListCopied = new ArrayList <Provider> ();
        for (Provider p : providerList)
        {
            providerListCopied.add (p);
        }
        Collections.sort (providerListCopied, new Comparator <Provider> ()
        {

            @Override
            public int compare (Provider o1, Provider o2)
            {
                if (o1.getUnpayMoney () > o2.getUnpayMoney ())
                {
                    return -1;
                }
                else if (o1.getUnpayMoney () < o2.getUnpayMoney ())
                {
                    return 1;
                }
                return 0;
            }
        });
        List <CustomerBean> results = new ArrayList <CustomerBean> ();
        if (CollectionUtils.isNotEmpty (providerListCopied))
        {
            results = BeanConvertUtils.providerList2CustomerBeanList (providerListCopied);
        }
        JSONArray jsonArray = JSONArray.fromObject (results);
        return jsonArray.toString ();
    }

    @Override
    protected String getModelBySearchForm (DataGridRequestForm dataGridRequestForm,
                                           SearchRequestForm searchRequestForm, HttpServletRequest request)
    {
        logger.debug (dataGridRequestForm);
        int page = dataGridRequestForm.getPage () - 1;
        int size = dataGridRequestForm.getRows ();
        String sortField = parseSortField (dataGridRequestForm.getSort ());
        String order = dataGridRequestForm.getOrder ();
        Pageable pageable = new PageRequest (page, size, new Sort ("asc".equalsIgnoreCase (order) ? Direction.ASC
                                                                                                 : Direction.DESC,
                                                                   sortField));
        String searchKey = (null == dataGridRequestForm.getSearchKey () ? "" : dataGridRequestForm.getSearchKey ()
                                                                                                  .trim ());
        LoginUser loginUser = componentContext.getLoginUser (request);
        GenericPagingResult <CustomerBean> customerPagingResult = new GenericPagingResult <CustomerBean> ();
        try
        {
            if (null != loginUser)
            {
                Page <Provider> customerPage = componentContext.getQueryService ()
                                                               .findProviderByFuzzy (pageable, searchKey,
                                                                                     loginUser.getUserId (),
                                                                                     loginUser.isAdmin ());
                List <Provider> customerList = customerPage.getContent ();
                long total = customerPage.getTotalElements ();

                List <CustomerBean> customerBeans = BeanConvertUtils.providerList2CustomerBeanList (customerList);
                List <CustomerBean> customerBeans2 = new ArrayList <CustomerBean> ();
                for (CustomerBean bean : customerBeans)
                {
                    long providerId = Long.parseLong (bean.getId ());
                    OrderIn orderIn = DaoContext.getOrderInDao ().findLast (providerId);
                    if (null != orderIn)
                    {
                        bean.setLastSaleDate (DateUtils.getInterval (orderIn.getDateCreated (), null));
                    }
                    ProviderPayment pp = DaoContext.getProviderPaymentDao ().findLast (providerId);
                    if (null != pp)
                    {
                        bean.setLastPaidDate (DateUtils.getInterval (pp.getPaidDate (), null));
                    }
                    customerBeans2.add (bean);
                }
                customerPagingResult.setRows (customerBeans2);
                customerPagingResult.setTotal (total);

                float sumUnpay = componentContext.getQueryService ().countProviderUnpay (searchKey,
                                                                                         loginUser.getUserId (),
                                                                                         loginUser.isAdmin ());
                List <Map <String, String>> footer = new ArrayList <Map <String, String>> ();
                Map <String, String> map = new HashMap <String, String> ();
                map.put ("shortName", "合计");
                map.put ("unpayMoney", "" + Arith.round (sumUnpay, -1));
                footer.add (map);
                customerPagingResult.setFooter (footer);
            }
        }
        catch (Exception e)
        {
            logger.warn (e.getMessage ());
        }

        JSONObject jsonObject = JSONObject.fromObject (customerPagingResult);
        return jsonObject.toString ();
    }

    @Override
    public String getModelById (@RequestParam String providerId, HttpServletRequest request)
    {
        if (StringUtils.isNotBlank (providerId))
        {
            Provider provider = componentContext.getQueryService ().getProvider (Long.parseLong (providerId));
            if (null != provider)
            {
                CustomerBean customerBean = BeanConvertUtils.provider2CustomerBean (provider);
                JSONObject json = JSONObject.fromObject (customerBean);
                return json.toString ();
            }
        }
        return "";
    }

    @Override
    public String addModel (@Valid CustomerBean bean, BindingResult bindingResult, HttpServletRequest request)
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
                Provider provider = null;
                try
                {
                    bean.setContactBeans (CustomerManager.parseCustomerContact (bean));
                    provider = BeanConvertUtils.customerBean2Provider (bean);
                    provider = componentContext.getPersistenceService ().addProvider (provider);
                    ar = createAddSuccessResult (providerMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createAddFailedResult (providerMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("AddProvider", "LogEvent.AddProvider",
                                                                            request.getLocale ());
                        componentContext.getLogService ().log (logEventName,
                                                               loginUser.getUid (),
                                                               logEventName + ": "
                                                                       + LogUtil.createLogComment (provider) + generateLogComment (provider.getContacts ()));
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
    public String updateModel (@Valid CustomerBean bean, BindingResult bindingResult, HttpServletRequest request)
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
                Provider provider = null;
                Provider oldProvider = null;
                try
                {
                    bean.setContactBeans (CustomerManager.parseCustomerContact (bean));
                    provider = BeanConvertUtils.customerBean2Provider (bean);
                    oldProvider = DaoContext.getProviderDao ().findOne (provider.getId ());
                    provider.setUnpayMoney (oldProvider.getUnpayMoney ());
                    provider = componentContext.getPersistenceService ().updateProvider (provider);
                    ar = createUpdateSuccessResult (providerMessage);
                }
                catch (PcServiceException e) 
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createUpdateFailedResult (providerMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("UpdateProvider",
                                                                            "LogEvent.UpdateProvider",
                                                                            request.getLocale ());
                        componentContext.getLogService ()
                                        .log (logEventName,
                                              loginUser.getUid (),
                                              logEventName + ": "
                                                      + LogUtil.createLogComment (oldProvider, provider) + generateLogComment (provider.getContacts ()));
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
            List <Provider> customers = new ArrayList <Provider> ();
            Provider customer;
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    customer = new Provider ();
                    customer.setId (Long.parseLong (typeId));
                    customers.add (customer);
                }
            }
            else
            {
                customer = new Provider ();
                customer.setId (Long.parseLong (ids));
                customers.add (customer);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    componentContext.getPersistenceService ().deleteProvider (customers);
                    ar = createDeleteSuccessResult (providerMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (providerMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("DeleteProvider",
                                                                            "LogEvent.DeleteProvider",
                                                                            request.getLocale ());
                        componentContext.getLogService ().log (logEventName, loginUser.getUid (),
                                                               logEventName + ": " + ids);
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

    @RequestMapping (value = "/checkCodeExist", method = RequestMethod.POST)
    public @ResponseBody
    String checkCodeExist (@RequestParam String providerId, @RequestParam String shortCode, HttpServletRequest request)
    {
        if (StringUtils.isBlank (providerId))
        {
            // new
            Provider obj = componentContext.getQueryService ().getProviderByShortCode (shortCode);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long id = Long.parseLong (providerId);
            Provider customer = componentContext.getQueryService ().getProviderByShortCode (shortCode);
            if (null != customer && customer.getId () != id)
            {
                return "false";
            }
        }

        return "true";
    }

    @RequestMapping (value = "/getProviderByShortNameOrCodeLike", method = RequestMethod.POST)
    public void getProviderByShortNameOrCodeLike (@RequestParam String shortName, HttpServletRequest request,
                                                  HttpServletResponse response) throws Exception
    {
        logger.debug (shortName);
        if (StringUtils.isNotBlank (shortName))
        {
            LoginUser loginUser = componentContext.getLoginUser (request);
            response.setContentType ("text/html;charset=utf-8");
            PrintWriter writer = response.getWriter ();
            List <Provider> customers = componentContext.getQueryService ()
                                                        .getProviderByShortNameLike (shortName, loginUser.getUserId (),
                                                                                     loginUser.isAdmin ());
            JSONArray jsonArray = JSONArray.fromObject (BeanConvertUtils.providerList2CustomerBeanList (customers));
            writer.write ("flightHandler(" + jsonArray.toString () + ")");
        }
    }

    @RequestMapping (value = "/getProviderByUser", method = RequestMethod.POST)
    public @ResponseBody
    String getProviderByUser (HttpServletRequest request)
    {
        LoginUser loginUser = componentContext.getLoginUser (request);
        if (null != loginUser)
        {
            List <Provider> customerList = componentContext.getQueryService ().getAllProvider (loginUser.getUserId (),
                                                                                               loginUser.isAdmin ());
            List <CustomerBean> customerBeans = BeanConvertUtils.providerList2CustomerBeanList (customerList);

            return AjaxUtils.getJsonArray (customerBeans);
        }
        return "";
    }

    @RequestMapping (value = "/getProviderPayment", method = RequestMethod.POST)
    public @ResponseBody
    String getProviderPayment (long customerId)
    {
        Timestamp end = DateUtils.date2Timestamp (new Date ());
        Timestamp start = DateUtils.date2Timestamp (DateUtils.getDateBefore (end, 90));
        List <ProviderPayment> payments = componentContext.getQueryService ().findProviderPaymentByAdvance (customerId,
                                                                                                            start, end);
        JSONArray jsonArr = JSONArray.fromObject (BeanConvertUtils.providerPaymentList2PaymentBeanList (payments));
        return jsonArr.toString ();
    }

    private String parseSortField (final String sortField)
    {
        String sortFieldAfterParse = "shortName";
        if (!StringUtils.isBlank (sortField))
        {
            sortFieldAfterParse = sortField;
        }
        return sortFieldAfterParse;
    }

    private String generateLogComment (List <ProviderContact> providerContacts) 
    {
        String logComment = "";
        if(CollectionUtils.isNotEmpty (providerContacts)) 
        {
            for(ProviderContact contact : providerContacts) 
            {
                logComment += LogUtil.createLogComment (contact);
            }
        }
        return logComment;
    }

}
