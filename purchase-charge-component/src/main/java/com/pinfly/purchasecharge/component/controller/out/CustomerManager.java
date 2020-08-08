package com.pinfly.purchasecharge.component.controller.out;

import java.io.PrintWriter;
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
import com.pinfly.purchasecharge.component.bean.ContactBean;
import com.pinfly.purchasecharge.component.bean.CustomerBean;
import com.pinfly.purchasecharge.component.bean.DataGridRequestForm;
import com.pinfly.purchasecharge.component.bean.GenericPagingResult;
import com.pinfly.purchasecharge.component.bean.SearchRequestForm;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.controller.GenericController;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.BeanConvertUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.PaymentSearchForm;
import com.pinfly.purchasecharge.core.model.persistence.out.Customer;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerContact;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerPayment;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOut;
import com.pinfly.purchasecharge.core.util.Arith;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.dal.DaoContext;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/customer")
public class CustomerManager extends GenericController <CustomerBean>
{
    private static final Logger logger = Logger.getLogger (CustomerManager.class);
    private String customerMessage = ComponentMessage.createMessage ("CUSTOMER", "CUSTOMER").getI18nMessageCode ();

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "customerManagement";
    }

    @Override
    protected String getJsName ()
    {
        return "javascript/customerMgmtJS";
    }

    @Override
    protected String getCssName ()
    {
        return "stylesheet/customerMgmtCSS";
    }

    @Override
    public String checkExist (@RequestParam String custoId, @RequestParam String name, HttpServletRequest request)
    {
        if (StringUtils.isBlank (custoId))
        {
            // new
            Customer obj = componentContext.getQueryService ().getCustomer (name);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long id = Long.parseLong (custoId);
            Customer customer = componentContext.getQueryService ().getCustomer (name);
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
        List <Customer> customerList = componentContext.getQueryService ().getAllCustomer ("", true);
        List <Customer> customerListCopied = new ArrayList <Customer> ();
        for (Customer customer : customerList)
        {
            customerListCopied.add (customer);
        }
        Collections.sort (customerListCopied, new Comparator <Customer> ()
        {

            @Override
            public int compare (Customer o1, Customer o2)
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
        if (CollectionUtils.isNotEmpty (customerListCopied))
        {
            results = BeanConvertUtils.customerList2CustomerBeanList (customerListCopied);
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
                Page <Customer> customerPage = componentContext.getQueryService ()
                                                               .findCustomerByFuzzy (pageable, searchKey,
                                                                                     loginUser.getUserId (),
                                                                                     loginUser.isAdmin ());
                List <Customer> customerList = customerPage.getContent ();

                long total = customerPage.getTotalElements ();
                List <CustomerBean> customerBeans = BeanConvertUtils.customerList2CustomerBeanList (customerList);
                List <CustomerBean> customerBeans2 = new ArrayList <CustomerBean> ();
                for (CustomerBean bean : customerBeans)
                {
                    long customerId = Long.parseLong (bean.getId ());
                    OrderOut orderOut = DaoContext.getOrderOutDao ().findLast (customerId);
                    if (null != orderOut)
                    {
                        bean.setLastSaleDate (DateUtils.getInterval (orderOut.getDateCreated (), null));
                    }
                    CustomerPayment cp = DaoContext.getCustomerPaymentDao ().findLast (customerId);
                    if (null != cp)
                    {
                        bean.setLastPaidDate (DateUtils.getInterval (cp.getPaidDate (), null));
                    }
                    customerBeans2.add (bean);
                }
                customerPagingResult.setRows (customerBeans2);
                customerPagingResult.setTotal (total);

                float sumUnpay = componentContext.getQueryService ().countCustomerUnpay (searchKey,
                                                                                         loginUser.getUserId (),
                                                                                         loginUser.isAdmin ());
                List <Map <String, String>> footer = new ArrayList <Map <String, String>> ();
                Map <String, String> map = new HashMap <String, String> ();
                map.put ("shortName", "合计");
                map.put ("unpayMoney", Arith.round (sumUnpay, -1) + "");
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
                Customer customer = null;
                try
                {
                    bean.setContactBeans (parseCustomerContact (bean));
                    customer = BeanConvertUtils.customerBean2Customer (bean);
                    customer = componentContext.getPersistenceService ().addCustomer (customer);
                    ar = createAddSuccessResult (customerMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createAddFailedResult (customerMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("AddCustomer", "LogEvent.AddCustomer",
                                                                            request.getLocale ());
                        componentContext.getLogService ().log (logEventName,
                                                               loginUser.getUid (),
                                                               logEventName + ": "
                                                                       + LogUtil.createLogComment (customer) + generateLogComment (customer.getContacts ()));
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
                Customer customer = null;
                Customer oldCustomer = null;
                try
                {
                    bean.setContactBeans (parseCustomerContact (bean));
                    customer = BeanConvertUtils.customerBean2Customer (bean);
                    oldCustomer = DaoContext.getCustomerDao ().findOne (customer.getId ());
                    customer.setUnpayMoney (oldCustomer.getUnpayMoney ());
                    customer = componentContext.getPersistenceService ().updateCustomer (customer);
                    ar = createUpdateSuccessResult (customerMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createUpdateFailedResult (customerMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("UpdateCustomer",
                                                                            "LogEvent.UpdateCustomer",
                                                                            request.getLocale ());
                        componentContext.getLogService ()
                                        .log (logEventName,
                                              loginUser.getUid (),
                                              logEventName + ": "
                                                      + LogUtil.createLogComment (oldCustomer, customer) + generateLogComment (customer.getContacts ()));
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
            List <Customer> customers = new ArrayList <Customer> ();
            Customer customer;
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    customer = new Customer ();
                    customer.setId (Long.parseLong (typeId));
                    customers.add (customer);
                }
            }
            else
            {
                customer = new Customer ();
                customer.setId (Long.parseLong (ids));
                customers.add (customer);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    componentContext.getPersistenceService ().deleteCustomer (customers);
                    ar = createDeleteSuccessResult (customerMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (customerMessage);
                }
            }

            if (ActionResultStatus.OK.equals (ar.getStatus ()))
            {
                try
                {
                    String logEventName = LogEventName.createEventName ("DeleteCustomer", "LogEvent.DeleteCustomer",
                                                                        request.getLocale ());
                    componentContext.getLogService ()
                                    .log (logEventName, loginUser.getUid (), logEventName + ": " + ids);
                }
                catch (Exception e)
                {
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

    @Override
    public String getModelById (@RequestParam String customerId, HttpServletRequest request)
    {
        if (StringUtils.isNotBlank (customerId))
        {
            Customer customer = componentContext.getQueryService ().getCustomer (Long.parseLong (customerId));
            if (null != customer)
            {
                CustomerBean customerBean = BeanConvertUtils.customer2CustomerBean (customer);
                JSONObject json = JSONObject.fromObject (customerBean);
                return json.toString ();
            }
        }
        return "";
    }

    @RequestMapping (value = "/checkCodeExist", method = RequestMethod.POST)
    public @ResponseBody
    String checkCodeExist (@RequestParam String custoId, @RequestParam String shortCode, HttpServletRequest request)
    {
        if (StringUtils.isBlank (custoId))
        {
            // new
            Customer obj = componentContext.getQueryService ().getCustomerByShortCode (shortCode);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long id = Long.parseLong (custoId);
            Customer customer = componentContext.getQueryService ().getCustomerByShortCode (shortCode);
            if (null != customer && customer.getId () != id)
            {
                return "false";
            }
        }

        return "true";
    }

    @RequestMapping (value = "/getCustomerByShortNameLike", method = RequestMethod.POST)
    public void getCustomerByShortNameLike (@RequestParam String shortName, HttpServletRequest request,
                                            HttpServletResponse response) throws Exception
    {
        logger.debug (shortName);
        if (StringUtils.isNotBlank (shortName))
        {
            LoginUser loginUser = componentContext.getLoginUser (request);
            response.setContentType ("text/html;charset=utf-8");
            PrintWriter writer = response.getWriter ();
            List <Customer> customers = componentContext.getQueryService ()
                                                        .getCustomerByShortNameLike (shortName, loginUser.getUserId (),
                                                                                     loginUser.isAdmin ());
            JSONArray jsonArray = JSONArray.fromObject (BeanConvertUtils.customerList2CustomerBeanList (customers));
            writer.write ("flightHandler(" + jsonArray.toString () + ")");
        }
    }

    @RequestMapping (value = "/getCustomerByUser", method = RequestMethod.POST)
    public @ResponseBody
    String getCustomerByUser (HttpServletRequest request)
    {
        LoginUser loginUser = componentContext.getLoginUser (request);
        if (null != loginUser)
        {
            List <Customer> customerList = componentContext.getQueryService ().getAllCustomer (loginUser.getUserId (),
                                                                                               loginUser.isAdmin ());
            List <CustomerBean> customerBeans = BeanConvertUtils.customerList2CustomerBeanList (customerList);

            return AjaxUtils.getJsonArray (customerBeans);
        }
        return "";
    }

    @RequestMapping (value = "/getCustomerPayment", method = RequestMethod.POST)
    public @ResponseBody
    String getCustomerPayment (long customerId)
    {
        Date end = new Date ();
        Date start = DateUtils.getDateBefore (end, 90);
        PaymentSearchForm searchForm = new PaymentSearchForm ();
        searchForm.setCustomerId (customerId);
        searchForm.setStartDate (start);
        searchForm.setEndDate (end);
        List <CustomerPayment> payments = componentContext.getQueryService ().findCustomerPaymentByAdvance (searchForm);
        JSONArray jsonArr = JSONArray.fromObject (BeanConvertUtils.customerPaymentList2PaymentBeanList (payments));
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

    public static List <ContactBean> parseCustomerContact (CustomerBean customerBean)
    {
        List <ContactBean> contactBeans = new ArrayList <ContactBean> ();
        String contactListStr = customerBean.getContactList ();

        if (StringUtils.isNotBlank (contactListStr))
        {
            String[] contactArr = contactListStr.split (";");
            if (contactArr.length > 0)
            {
                for (String contactss : contactArr)
                {
                    if (StringUtils.isNotBlank (contactss))
                    {
                        String[] itemArr = contactss.split (",");
                        ContactBean contactBean = new ContactBean ();
                        //contactBean.setId (itemArr[0]);
                        contactBean.setName (itemArr[1]);
                        contactBean.setMobilePhone (itemArr[2]);
                        contactBean.setFixedPhone (itemArr[3]);
                        contactBean.setNetCommunityId (itemArr[4]);
                        contactBean.setAddress (itemArr[5]);
                        contactBean.setPrefered (StringUtils.isNotBlank (itemArr[6]) ? "1".equals (itemArr[6])
                                                                                       || Boolean.parseBoolean (itemArr[6])
                                                                                    : false);
                        contactBeans.add (contactBean);
                    }
                }
            }
        }
        return contactBeans;
    }

    private String generateLogComment (List <CustomerContact> customerContacts) 
    {
        String logComment = "";
        if(CollectionUtils.isNotEmpty (customerContacts)) 
        {
            for(CustomerContact contact : customerContacts) 
            {
                logComment += LogUtil.createLogComment (contact);
            }
        }
        return logComment;
    }

}
