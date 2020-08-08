package com.pinfly.purchasecharge.component.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.ActionResult.ActionResultStatus;
import com.pinfly.purchasecharge.component.bean.DeliveryCompanyBean;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.persistence.DeliveryCompany;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/deliveryCompany")
public class DeliveryCompanyController extends GenericController <DeliveryCompanyBean>
{
    private static final Logger logger = Logger.getLogger (DeliveryCompanyController.class);
    private static String deliveryCompanyMessage = ComponentMessage.createMessage ("DELIVERY_COMPANY",
                                                                                   "DELIVERY_COMPANY")
                                                                   .getI18nMessageCode ();

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "deliveryCompanyManagement";
    }

    @Override
    protected String getAllModel (HttpServletRequest request)
    {
        List <DeliveryCompany> companies = componentContext.getQueryService ().getAllDeliveryCompany ();
        JSONArray jsonObject = JSONArray.fromObject (companies);
        return jsonObject.toString ();
    }

    @Override
    protected String checkExist (@RequestParam String deliveryCompanyId, @RequestParam String name,
                                 HttpServletRequest request)
    {
        if (StringUtils.isBlank (deliveryCompanyId))
        {
            // new
            DeliveryCompany obj = componentContext.getQueryService ().getDeliveryCompany (name);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long id = Long.parseLong (deliveryCompanyId);
            DeliveryCompany deliveryCompany = componentContext.getQueryService ().getDeliveryCompany (name);
            if (null != deliveryCompany && deliveryCompany.getId () != id)
            {
                return "false";
            }
        }
        return "true";
    }

    @Override
    protected String addModel (@Valid DeliveryCompanyBean bean, BindingResult bindingResult, HttpServletRequest request)
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
                DeliveryCompany company = convert (bean);
                try
                {
                    company = componentContext.getPersistenceService ().addDeliveryCompany (company);
                    ar = createAddSuccessResult (deliveryCompanyMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createAddFailedResult (deliveryCompanyMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("AddDeliveryCompany",
                                                                            "LogEvent.AddDeliveryCompany",
                                                                            request.getLocale ());
                        componentContext.getLogService ().log (logEventName,
                                                               loginUser.getUid (),
                                                               logEventName + ": "
                                                                       + LogUtil.createLogComment (company));
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
    protected String updateModel (@Valid DeliveryCompanyBean bean, BindingResult bindingResult,
                                  HttpServletRequest request)
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
                DeliveryCompany company = convert (bean);
                DeliveryCompany oldCompany = componentContext.getQueryService ().getDeliveryCompany (company.getId ());
                try
                {
                    company = componentContext.getPersistenceService ().updateDeliveryCompany (company);
                    ar = createUpdateSuccessResult (deliveryCompanyMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createUpdateFailedResult (deliveryCompanyMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("UpdateDeliveryCompany",
                                                                            "LogEvent.UpdateDeliveryCompany",
                                                                            request.getLocale ());
                        componentContext.getLogService ().log (logEventName,
                                                               loginUser.getUid (),
                                                               logEventName
                                                                       + ": "
                                                                       + LogUtil.createLogComment (oldCompany,
                                                                                                            company));
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
    protected String deleteModels (@RequestParam String ids, HttpServletRequest request)
    {
        if (null != ids && ids.trim ().length () > 0)
        {
            List <DeliveryCompany> deliveryCompanies = new ArrayList <DeliveryCompany> ();
            DeliveryCompany deliveryCompany;
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    deliveryCompany = new DeliveryCompany ();
                    deliveryCompany.setId (Long.parseLong (typeId));
                    deliveryCompanies.add (deliveryCompany);
                }
            }
            else
            {
                deliveryCompany = new DeliveryCompany ();
                deliveryCompany.setId (Long.parseLong (ids));
                deliveryCompanies.add (deliveryCompany);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    componentContext.getPersistenceService ().deleteDeliveryCompany (deliveryCompanies);
                    ar = createDeleteSuccessResult (deliveryCompanyMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (deliveryCompanyMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("DeleteDeliveryCompany",
                                                                            "LogEvent.DeleteDeliveryCompany",
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

    public static DeliveryCompany convert (DeliveryCompanyBean bean)
    {
        DeliveryCompany company = null;
        if (null != bean)
        {
            company = new DeliveryCompany ();
            company.setId (StringUtils.isNotBlank (bean.getId ()) ? Long.parseLong (bean.getId ()) : 0);
            company.setName (bean.getName ());
        }
        return company;
    }

}
