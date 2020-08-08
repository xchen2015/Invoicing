package com.pinfly.purchasecharge.component.controller.out;

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
import com.pinfly.purchasecharge.component.bean.CustomerTypeBean;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.controller.GenericController;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerType;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/customerType")
public class CustomerTypeManager extends GenericController <CustomerTypeBean>
{
    private static final Logger logger = Logger.getLogger (CustomerTypeManager.class);
    private String customerTypeMessage = ComponentMessage.createMessage ("CUSTOMER_TYPE", "CUSTOMER_TYPE")
                                                         .getI18nMessageCode ();

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "customerTypeManagement";
    }
    
    @Override
    protected String getJsName ()
    {
        return "javascript/customerTypeMgmtJS";
    }

    @Override
    public String checkExist (@RequestParam String customerTypeId, @RequestParam String name, HttpServletRequest request)
    {
        if (StringUtils.isBlank (customerTypeId))
        {
            // new
            CustomerType obj = componentContext.getQueryService ().getCustomerType (name);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long id = Long.parseLong (customerTypeId);
            CustomerType customerType = componentContext.getQueryService ().getCustomerType (name);
            if (null != customerType && customerType.getId () != id)
            {
                return "false";
            }
        }

        return "true";
    }

    @Override
    public String getAllModel (HttpServletRequest request)
    {
        List <CustomerType> types = (List <CustomerType>) componentContext.getQueryService ().getAllCustomerType ();
        JSONArray jsonObject = JSONArray.fromObject (types);
        return jsonObject.toString ();
    }

    @Override
    public String addModel (CustomerTypeBean bean, BindingResult bindingResult, HttpServletRequest request)
    {
        if (bindingResult.hasErrors ())
        {
            logger.warn (bindingResult.getAllErrors ());
            return createBadRequestResult (null);
        }
        else
        {
            CustomerType type = new CustomerType (bean.getName ());
            String typeId = bean.getId ();
            if (null != typeId && !"".equals (typeId))
            {
                type.setId (Long.parseLong (typeId));
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    type = componentContext.getPersistenceService ().addCustomerType (type);
                    ar = createAddSuccessResult (customerTypeMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createAddFailedResult (customerTypeMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("AddCustomerType",
                                                                            "LogEvent.AddCustomerType",
                                                                            request.getLocale ());
                        componentContext.getLogService ().log (logEventName,
                                                               loginUser.getUid (),
                                                               logEventName + ": "
                                                                       + LogUtil.createLogComment (type));
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
    public String updateModel (CustomerTypeBean bean, BindingResult bindingResult, HttpServletRequest request)
    {
        if (bindingResult.hasErrors ())
        {
            logger.warn (bindingResult.getAllErrors ());
            return createBadRequestResult (null);
        }
        else
        {
            CustomerType type = new CustomerType (bean.getName ());
            String typeId = bean.getId ();
            if (null != typeId && !"".equals (typeId))
            {
                type.setId (Long.parseLong (typeId));
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                CustomerType oldType = componentContext.getQueryService ().getCustomerType (type.getId ());
                try
                {
                    type = componentContext.getPersistenceService ().updateCustomerType (type);
                    ar = createUpdateSuccessResult (customerTypeMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createUpdateFailedResult (customerTypeMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("UpdateCustomerType",
                                                                            "LogEvent.UpdateCustomerType",
                                                                            request.getLocale ());
                        componentContext.getLogService ().log (logEventName,
                                                               loginUser.getUid (),
                                                               logEventName
                                                                       + ": "
                                                                       + LogUtil.createLogComment (oldType,
                                                                                                            type));
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
            List <CustomerType> customerTypes = new ArrayList <CustomerType> ();
            CustomerType customerType;
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    customerType = new CustomerType ();
                    customerType.setId (Long.parseLong (typeId));
                    customerTypes.add (customerType);
                }
            }
            else
            {
                customerType = new CustomerType ();
                customerType.setId (Long.parseLong (ids));
                customerTypes.add (customerType);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    componentContext.getPersistenceService ().deleteCustomerType (customerTypes);
                    ar = createDeleteSuccessResult (customerTypeMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (customerTypeMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("DeleteCustomerType",
                                                                            "LogEvent.DeleteCustomerType",
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

}
