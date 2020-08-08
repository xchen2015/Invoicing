package com.pinfly.purchasecharge.component.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.ActionResult.ActionResultStatus;
import com.pinfly.purchasecharge.component.bean.PaymentWayBean;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.BeanConvertUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.persistence.PaymentWay;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/paymentWay")
public class PaymentWayManager extends GenericController <PaymentWayBean>
{
    private static final Logger logger = Logger.getLogger (PaymentWayManager.class);
    private String paymentWayMessage = ComponentMessage.createMessage ("PAYMENT_WAY", "PAYMENT_WAY")
                                                       .getI18nMessageCode ();

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "paymentWayManagement";
    }

    @Override
    public String checkExist (@RequestParam String paymentWayId, @RequestParam String name, HttpServletRequest request)
    {
        if (StringUtils.isBlank (paymentWayId))
        {
            // new
            PaymentWay obj = componentContext.getQueryService ().getPaymentWay (name);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long id = Long.parseLong (paymentWayId);
            PaymentWay paymentWay = componentContext.getQueryService ().getPaymentWay (name);
            if (null != paymentWay && paymentWay.getId () != id)
            {
                return "false";
            }
        }

        return "true";
    }

    @Override
    public String getAllModel (HttpServletRequest request)
    {
        List <PaymentWay> units = (List <PaymentWay>) componentContext.getQueryService ().getAllPaymentWay ();
        JSONArray jsonObject = JSONArray.fromObject (units);
        return jsonObject.toString ();
    }

    @Override
    public String addModel (PaymentWayBean bean, BindingResult bindingResult, HttpServletRequest request)
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
                PaymentWay paymentWay = BeanConvertUtils.paymentWayBean2PaymentWay (bean);
                try
                {
                    paymentWay = componentContext.getPersistenceService ().addPaymentWay (paymentWay);
                    ar = createAddSuccessResult (paymentWayMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createAddFailedResult (paymentWayMessage);
                }

                if (ActionResult.ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("AddPaymentWay", "LogEvent.AddPaymentWay",
                                                                            request.getLocale ());
                        componentContext.getLogService ().log (logEventName,
                                                               loginUser.getUid (),
                                                               logEventName + ": "
                                                                       + LogUtil.createLogComment (paymentWay));
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
    public String updateModel (PaymentWayBean bean, BindingResult bindingResult, HttpServletRequest request)
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
                PaymentWay paymentWay = BeanConvertUtils.paymentWayBean2PaymentWay (bean);
                PaymentWay oldPaymentWay = componentContext.getQueryService ().getPaymentWay (paymentWay.getId ());
                try
                {
                    paymentWay = componentContext.getPersistenceService ().updatePaymentWay (paymentWay);
                    ar = createUpdateSuccessResult (paymentWayMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createUpdateFailedResult (paymentWayMessage);
                }

                if (ActionResult.ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("UpdatePaymentWay",
                                                                            "LogEvent.UpdatePaymentWay",
                                                                            request.getLocale ());
                        componentContext.getLogService ()
                                        .log (logEventName,
                                              loginUser.getUid (),
                                              logEventName + ": "
                                                      + LogUtil.createLogComment (oldPaymentWay, paymentWay));
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
            List <PaymentWay> paymentWays = new ArrayList <PaymentWay> ();
            PaymentWay paymentWay;
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    paymentWay = new PaymentWay ();
                    paymentWay.setId (Long.parseLong (typeId));
                    paymentWays.add (paymentWay);
                }
            }
            else
            {
                paymentWay = new PaymentWay ();
                paymentWay.setId (Long.parseLong (ids));
                paymentWays.add (paymentWay);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    componentContext.getPersistenceService ().deletePaymentWay (paymentWays);
                    ar = createDeleteSuccessResult (paymentWayMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (paymentWayMessage);
                }

                if (ActionResult.ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("DeletePaymentWay",
                                                                            "LogEvent.DeletePaymentWay",
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

    @Override
    public void afterPropertiesSet () throws Exception
    {
        // TODO Auto-generated method stub

    }

}
