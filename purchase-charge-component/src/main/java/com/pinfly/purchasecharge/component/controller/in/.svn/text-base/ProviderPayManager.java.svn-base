package com.pinfly.purchasecharge.component.controller.in;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.ActionResult.ActionResultStatus;
import com.pinfly.purchasecharge.component.bean.DataGridRequestForm;
import com.pinfly.purchasecharge.component.bean.GenericPagingResult;
import com.pinfly.purchasecharge.component.bean.PaymentBean;
import com.pinfly.purchasecharge.component.bean.SearchRequestForm;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.controller.GenericController;
import com.pinfly.purchasecharge.component.controller.out.CustomerPayManager;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.BeanConvertUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.PaymentSearchForm;
import com.pinfly.purchasecharge.core.model.PaymentTypeCode;
import com.pinfly.purchasecharge.core.model.persistence.in.ProviderPayment;
import com.pinfly.purchasecharge.core.util.Arith;
import com.pinfly.purchasecharge.core.util.PurchaseChargeConstants;
import com.pinfly.purchasecharge.core.util.PurchaseChargeUtils;
import com.pinfly.purchasecharge.dal.DaoContext;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/providerPay")
public class ProviderPayManager extends GenericController <PaymentBean>
{
    private static final Logger logger = Logger.getLogger (ProviderPayManager.class);
    private String providerPayMessage = ComponentMessage.createMessage ("PROVIDER_PAID", "PROVIDER_PAID")
                                                        .getI18nMessageCode ();

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "providerPayManagement";
    }

    @Override
    protected String getJsName ()
    {
        return "javascript/providerPayMgmtJS";
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
        String sortField = parseSortField (dataGridRequestForm.getSort (), "paidDate");
        String order = dataGridRequestForm.getOrder ();
        Pageable pageable = new PageRequest (page, size, new Sort ("asc".equalsIgnoreCase (order) ? Direction.ASC
                                                                                                 : Direction.DESC,
                                                                   sortField));

        PaymentSearchForm searchForm = BeanConvertUtils.searchRequestForm2PaymentSearchForm (searchRequestForm);

        LoginUser loginUser = componentContext.getLoginUser (request);
        if (null != loginUser)
        {
            Page <ProviderPayment> paymentPage = componentContext.getQueryService ()
                                                                 .findPagedProviderPayment (searchForm, pageable);
            long total = paymentPage.getTotalElements ();
            GenericPagingResult <PaymentBean> payPagingResult = new GenericPagingResult <PaymentBean> ();
            payPagingResult.setRows (BeanConvertUtils.providerPaymentList2PaymentBeanList (paymentPage.getContent ()));
            payPagingResult.setTotal (total);

            float sumPaid = componentContext.getQueryService ().countProviderPaid (searchForm);
            float sumNewUnPaid = componentContext.getQueryService ().countProviderNewUnPaid (searchForm);
            List <Map <String, String>> footer = new ArrayList <Map <String, String>> ();
            Map <String, String> map = new HashMap <String, String> ();
            map.put (PurchaseChargeConstants.PAY_DATE, "实付款合计");
            map.put ("addUnPaid", Arith.round (sumNewUnPaid, -1) + "");
            map.put (PurchaseChargeConstants.PAID, Arith.round (sumPaid, -1) + "");
            footer.add (map);
            payPagingResult.setFooter (footer);

            JSONObject jsonObject = JSONObject.fromObject (payPagingResult);
            return jsonObject.toString ();
        }
        return "";
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
                ProviderPayment providerPayment = null;
                try
                {
                    String operator = loginUser.getUserId ();
                    bean.setUserCreated (operator);
                    bean.setPaymentRecordBeans (CustomerPayManager.parsePaymentRecord (bean));
                    providerPayment = BeanConvertUtils.paymentBean2ProviderPayment (bean);
                    providerPayment.setTypeCode (PaymentTypeCode.IN_PAID_MONEY);
                    providerPayment = componentContext.getPersistenceService ().addProviderPayment (providerPayment);
                    ar = createUpdateSuccessResult (providerPayMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createUpdateFailedResult (providerPayMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("AddProviderPaid",
                                                                            "LogEvent.AddProviderPaid",
                                                                            request.getLocale ());
                        componentContext.getLogService ()
                                        .log (logEventName, loginUser.getUid (),
                                              logEventName + ": " + LogUtil.createLogComment (providerPayment));
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
                long providerPaymentId = Long.parseLong (paymentId);
                ProviderPayment pp = DaoContext.getProviderPaymentDao ().findOne (providerPaymentId);
                if (null != pp)
                {
                    paymentBean = BeanConvertUtils.providerPayment2PaymentBean (pp, true);
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
    
    @RequestMapping (value = "/generateProviderPaymentBid", method = RequestMethod.POST)
    public @ResponseBody
    String generateProviderPaymentBid ()
    {
        return PurchaseChargeUtils.generateProviderPaymentBid ();
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

}
