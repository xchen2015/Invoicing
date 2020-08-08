package com.pinfly.purchasecharge.component.controller.out;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.pinfly.purchasecharge.component.bean.DataGridRequestForm;
import com.pinfly.purchasecharge.component.bean.GenericPagingResult;
import com.pinfly.purchasecharge.component.bean.PaymentAccountBean;
import com.pinfly.purchasecharge.component.bean.PaymentBean;
import com.pinfly.purchasecharge.component.bean.PaymentRecordBean;
import com.pinfly.purchasecharge.component.bean.PaymentWayBean;
import com.pinfly.purchasecharge.component.bean.SearchRequestForm;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.controller.GenericController;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.BeanConvertUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.config.PurchaseChargeProperties;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.PaymentSearchForm;
import com.pinfly.purchasecharge.core.model.PaymentTypeCode;
import com.pinfly.purchasecharge.core.model.persistence.out.Customer;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerLevel;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerPayment;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOut;
import com.pinfly.purchasecharge.core.util.Arith;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.core.util.PurchaseChargeConstants;
import com.pinfly.purchasecharge.core.util.PurchaseChargeUtils;
import com.pinfly.purchasecharge.dal.DaoContext;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/customerPay")
public class CustomerPayManager extends GenericController <PaymentBean>
{
    private static final Logger logger = Logger.getLogger (CustomerPayManager.class);
    private String customerPayMessage = ComponentMessage.createMessage ("CUSTOMER_PAID", "CUSTOMER_PAID")
                                                        .getI18nMessageCode ();
    public static final String autoShowDuePayment = "autoShowDueCustomerPayment";
    private List <PaymentBean> duedPayments = new ArrayList <PaymentBean> ();

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        LoginUser loginUser = componentContext.getLoginUser (request);
        if (null != loginUser)
        {
            getDuedPayment (loginUser);
        }
        if(null != request.getSession (false)) 
        {
            Object obj = request.getSession (false).getAttribute (autoShowDuePayment);
            if(null != obj) 
            {
                String autoShowFlag = (String)obj;
                if(!"0".equals (autoShowFlag)) 
                {
                    if(CollectionUtils.isNotEmpty (duedPayments)) 
                    {
                        request.getSession (false).setAttribute (autoShowDuePayment, "1");
                    }
                }
            }
            else 
            {
                if(CollectionUtils.isNotEmpty (duedPayments)) 
                {
                    request.getSession (false).setAttribute (autoShowDuePayment, "1");
                }
            }
        }
        return "customerPayManagement";
    }

    @Override
    protected String getJsName ()
    {
        return "javascript/customerPayMgmtJS";
    }

    @Override
    public String getModelBySearchForm (DataGridRequestForm dataGridRequestForm, SearchRequestForm searchRequestForm,
                                        HttpServletRequest request)
    {
        logger.debug (dataGridRequestForm);
        int page = dataGridRequestForm.getPage () - 1;
        int size = dataGridRequestForm.getRows ();
        String sortField = parseSortField (dataGridRequestForm.getSort (), "paidDate");
        String order = dataGridRequestForm.getOrder ();
        Pageable pageable = new PageRequest (page, size, new Sort ("asc".equalsIgnoreCase (order) ? Direction.ASC
                                                                                                 : Direction.DESC,
                                                                   sortField));

        PaymentSearchForm searchForm = BeanConvertUtils.searchRequestForm2PaymentSearchForm (searchRequestForm);

        Page <CustomerPayment> paymentPage = componentContext.getQueryService ().findPagedCustomerPayment (searchForm,
                                                                                                           pageable);
        long total = paymentPage.getTotalElements ();
        GenericPagingResult <PaymentBean> payPagingResult = new GenericPagingResult <PaymentBean> ();
        payPagingResult.setRows (BeanConvertUtils.customerPaymentList2PaymentBeanList (paymentPage.getContent ()));
        payPagingResult.setTotal (total);

        float sumPaid = componentContext.getQueryService ().countCustomerPaid (searchForm);
        float sumNewUnPaid = componentContext.getQueryService ().countCustomerNewUnPaid (searchForm);
        List <Map <String, String>> footer = new ArrayList <Map <String, String>> ();
        Map <String, String> map = new HashMap <String, String> ();
        map.put (PurchaseChargeConstants.PAY_DATE, "实收款合计");
        map.put ("addUnPaid", Arith.round (sumNewUnPaid, -1) + "");
        map.put (PurchaseChargeConstants.PAID, Arith.round (sumPaid, -1) + "");
        footer.add (map);
        payPagingResult.setFooter (footer);

        JSONObject jsonObject = JSONObject.fromObject (payPagingResult);
        return jsonObject.toString ();
    }

    @Override
    public String updateModel (PaymentBean bean, BindingResult bindingResult, HttpServletRequest request)
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
                CustomerPayment customerPayment = null;
                try
                {
                    String user = loginUser.getUserId ();
                    bean.setUserCreated (user);
                    bean.setPaymentRecordBeans (parsePaymentRecord (bean));
                    customerPayment = BeanConvertUtils.paymentBean2CustomerPayment (bean);
                    customerPayment.setTypeCode (PaymentTypeCode.OUT_PAID_MONEY);
                    customerPayment = componentContext.getPersistenceService ().addCustomerPayment (customerPayment);
                    ar = createUpdateSuccessResult (customerPayMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createUpdateFailedResult (customerPayMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("AddCustomerPaid",
                                                                            "LogEvent.AddCustomerPaid",
                                                                            request.getLocale ());
                        componentContext.getLogService ()
                                        .log (logEventName, loginUser.getUid (),
                                              logEventName + ": " + LogUtil.createLogComment (customerPayment));
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
    protected String getModelById (@RequestParam String paymentId, HttpServletRequest request)
    {
        PaymentBean paymentBean = new PaymentBean ();
        if (StringUtils.isNotBlank (paymentId))
        {
            try
            {
                long customerPaymentId = Long.parseLong (paymentId);
                CustomerPayment cp = DaoContext.getCustomerPaymentDao ().findOne (customerPaymentId);
                if (null != cp)
                {
                    paymentBean = BeanConvertUtils.customerPayment2PaymentBean (cp, true);
                }
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage ());
            }
        }

        JSONObject json = JSONObject.fromObject (paymentBean);
        return json.toString ();
    }

    @RequestMapping (value = "/getCustomerPaymentDue", method = RequestMethod.POST)
    public @ResponseBody
    String getCustomerPaymentDue (HttpServletRequest request)
    {
        LoginUser loginUser = componentContext.getLoginUser (request);
        if (null != loginUser)
        {
            getDuedPayment (loginUser);
            float allUnPaid = 0;
            float duedUnPaid = 0;
            float restUnPaid = 0;
            for(PaymentBean paymentBean : duedPayments) 
            {
                allUnPaid += paymentBean.getPaid ();
                duedUnPaid += paymentBean.getAddUnPaid ();
                restUnPaid += paymentBean.getRemainUnPaid ();
            }
            List <Map<String, String>> footer = new ArrayList <Map<String,String>> ();
            Map <String, String> map = new HashMap <String, String> ();
            map.put ("customerName", "合计");
            map.put (PurchaseChargeConstants.PAID, allUnPaid + "");
            map.put ("addUnPaid", duedUnPaid + "");
            map.put ("remainUnPaid", restUnPaid + "");
            footer.add (map);
            
            GenericPagingResult <PaymentBean> payPagingResult = new GenericPagingResult <PaymentBean> ();
            payPagingResult.setRows (duedPayments);
            payPagingResult.setFooter (footer);
            JSONObject json = JSONObject.fromObject (payPagingResult);
            return json.toString ();
        }
        return null;
    }

    @RequestMapping (value = "/checkCustomerUnPayMoney", method = RequestMethod.POST)
    public @ResponseBody
    String checkCustomerUnPayMoney (@RequestParam String custoId)
    {
        long id = Long.parseLong (custoId);
        Customer customer = componentContext.getQueryService ().getCustomer (id);
        CustomerLevel customerLevel = componentContext.getQueryService ().getCustomerLevel (id);
        float customerMaxDebt = PurchaseChargeProperties.getDefaultCustomerMaxDebt ();
        if (null != customerLevel && customerLevel.isEnabled ())
        {
            customerMaxDebt = customerLevel.getMaxDebt ();
        }
        if (null != customer && customer.getUnpayMoney () > customerMaxDebt)
        {
            return "false";
        }
        return "true";
    }
    
    @RequestMapping (value = "/generateCustomerPaymentBid", method = RequestMethod.POST)
    public @ResponseBody
    String generateCustomerPaymentBid ()
    {
        return PurchaseChargeUtils.generateCustomerPaymentBid ();
    }
    
    @RequestMapping (value = "/setAutoShowDuePayment", method = RequestMethod.POST)
    public @ResponseBody
    String setAutoShowDuePayment (@RequestParam String autoShowDueCustomerPayment, HttpServletRequest request)
    {
        if (StringUtils.isNotBlank (autoShowDueCustomerPayment))
        {
            request.getSession (false).setAttribute (autoShowDuePayment, autoShowDueCustomerPayment);
            return "true";
        }
        return "false";
    }

    private String parseSortField (final String sortField, String defaultSortField)
    {
        String sortFieldAfterParse = defaultSortField;
        if (!StringUtils.isBlank (sortField))
        {
            sortFieldAfterParse = sortField;
        }
        return sortFieldAfterParse;
    }
    
    private void getDuedPayment (LoginUser loginUser) 
    {
        if(CollectionUtils.isEmpty (duedPayments)) 
        {
            int paymentDuedDays = PurchaseChargeProperties.getDefaultPaymentDueDays ();
            
            List <Customer> customers = componentContext.getQueryService ().getAllCustomer (loginUser.getUserId (),
                                                                                            loginUser.isAdmin ());
            for (Customer customer : customers)
            {
                CustomerLevel customerLevel = componentContext.getQueryService ().getCustomerLevel (customer.getId ());
                if (null != customerLevel && customerLevel.isEnabled ())
                {
                    paymentDuedDays = customerLevel.getPaymentDays ();
                }
                OrderOut lastOrderOut = DaoContext.getOrderOutDao ().findLast (customer.getId ());
                if (null != lastOrderOut)
                {
                    long paymentIntervalFromLast = DateUtils.getInterval (lastOrderOut.getDateCreated (), null);
                    if (24 * 3600 * paymentDuedDays < paymentIntervalFromLast && customer.getUnpayMoney () > 0)
                    {
                        PaymentBean paymentBean = new PaymentBean ();
                        paymentBean.setPayDate ("" + (paymentIntervalFromLast - 24 * 3600 * paymentDuedDays));
                        paymentBean.setPaid (customer.getUnpayMoney ());
                        paymentBean.setAddUnPaid (customer.getUnpayMoney ());
                        paymentBean.setRemainUnPaid (0);
                        paymentBean.setCustomerBean (BeanConvertUtils.customer2CustomerBean (customer));
                        duedPayments.add (paymentBean);
                        continue;
                    }
                }
                
                CustomerPayment lastCustomerPayment = DaoContext.getCustomerPaymentDao ().findLast (customer.getId ());
                if (null != lastCustomerPayment)
                {
                    long paymentIntervalFromLast = DateUtils.getInterval (lastCustomerPayment.getPaidDate (), null);
                    if (24 * 3600 * paymentDuedDays < paymentIntervalFromLast)
                    {
                        Date start = lastCustomerPayment.getPaidDate ();
                        Date end = DateUtils.getDateBefore (new Date (), paymentDuedDays);
                        if (start.before (end))
                        {
                            PaymentSearchForm searchForm = new PaymentSearchForm ();
                            searchForm.setCustomerId (customer.getId ());
                            searchForm.setStartDate (start);
                            searchForm.setEndDate (end);
                            searchForm.setTypeCode (PaymentTypeCode.OUT_ORDER);
                            List <CustomerPayment> payments = componentContext.getQueryService ()
                                    .findCustomerPaymentByAdvance (searchForm);
                            float unPayMoney = 0;
                            if (CollectionUtils.isNotEmpty (payments))
                            {
                                for (CustomerPayment cp : payments)
                                {
                                    unPayMoney += cp.getAddUnPaid ();
                                }
                            }
                            
                            searchForm.setTypeCode (PaymentTypeCode.OUT_ORDER_RETURN);
                            payments = componentContext.getQueryService ().findCustomerPaymentByAdvance (searchForm);
                            if (CollectionUtils.isNotEmpty (payments))
                            {
                                for (CustomerPayment cp : payments)
                                {
                                    unPayMoney += cp.getAddUnPaid ();
                                }
                            }
                            
                            unPayMoney += lastCustomerPayment.getRemainUnPaid ();
                            if (unPayMoney > 0 && customer.getUnpayMoney () > unPayMoney)
                            {
                                PaymentBean paymentBean = new PaymentBean ();
                                paymentBean.setPayDate ((paymentIntervalFromLast - 24 * 3600 * paymentDuedDays) + "");
                                paymentBean.setPaid (customer.getUnpayMoney ());
                                paymentBean.setAddUnPaid (unPayMoney);
                                paymentBean.setRemainUnPaid (customer.getUnpayMoney () - unPayMoney);
                                paymentBean.setCustomerBean (BeanConvertUtils.customer2CustomerBean (customer));
                                duedPayments.add (paymentBean);
                            }
                        }
                    }
                }
            }
        }
    }

    public static List <PaymentRecordBean> parsePaymentRecord (PaymentBean paymentBean)
    {
        List <PaymentRecordBean> paymentRecordBeans = new ArrayList <PaymentRecordBean> ();
        String paymentRecordListStr = paymentBean.getPaymentRecordList ();
        if (StringUtils.isNotBlank (paymentRecordListStr))
        {
            String[] paymentRecordArr = paymentRecordListStr.split (";");
            if (paymentRecordArr.length > 0)
            {
                for (String paymentRecordss : paymentRecordArr)
                {
                    if (StringUtils.isNotBlank (paymentRecordss))
                    {
                        String[] itemArr = paymentRecordss.split (",");
                        PaymentRecordBean paymentRecordBean = new PaymentRecordBean ();
                        paymentRecordBean.setId (itemArr[0]);
                        PaymentAccountBean payAccountBean = new PaymentAccountBean ();
                        payAccountBean.setId (itemArr[1]);
                        paymentRecordBean.setPaymentAccountBean (payAccountBean);
                        paymentRecordBean.setPaid (StringUtils.isNotBlank (itemArr[2]) ? Float.parseFloat (itemArr[2])
                                                                                      : 0);
                        try
                        {
                            PaymentWayBean paymentWayBean = new PaymentWayBean ();
                            paymentWayBean.setId (itemArr[3]);
                            paymentRecordBean.setPaymentWayBean (paymentWayBean);
                        }
                        catch (ArrayIndexOutOfBoundsException e)
                        {
                            paymentRecordBean.setPaymentWayBean (null);
                        }

                        try
                        {
                            paymentRecordBean.setComment (itemArr[4]);
                        }
                        catch (ArrayIndexOutOfBoundsException e)
                        {
                            paymentRecordBean.setComment ("");
                        }
                        paymentRecordBeans.add (paymentRecordBean);
                    }
                }
            }
        }
        return paymentRecordBeans;
    }

}
