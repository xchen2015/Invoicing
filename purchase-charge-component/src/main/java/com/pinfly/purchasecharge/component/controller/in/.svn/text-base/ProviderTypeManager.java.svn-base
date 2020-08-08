package com.pinfly.purchasecharge.component.controller.in;

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
import com.pinfly.purchasecharge.core.model.persistence.in.ProviderType;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/providerType")
public class ProviderTypeManager extends GenericController <CustomerTypeBean>
{
    private static final Logger logger = Logger.getLogger (ProviderTypeManager.class);
    private String providerTypeMessage = ComponentMessage.createMessage ("PROVIDER_TYPE", "PROVIDER_TYPE")
                                                         .getI18nMessageCode ();

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "providerTypeManagement";
    }

    @Override
    protected String getJsName ()
    {
        return "javascript/providerTypeMgmtJS";
    }

    @Override
    protected String getCssName ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String checkExist (@RequestParam String providerTypeId, @RequestParam String name, HttpServletRequest request)
    {
        if (StringUtils.isBlank (providerTypeId))
        {
            // new
            ProviderType obj = componentContext.getQueryService ().getProviderType (name);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long id = Long.parseLong (providerTypeId);
            ProviderType providerType = componentContext.getQueryService ().getProviderType (name);
            if (null != providerType && providerType.getId () != id)
            {
                return "false";
            }
        }

        return "true";
    }

    @Override
    public String getAllModel (HttpServletRequest request)
    {
        List <ProviderType> units = (List <ProviderType>) componentContext.getQueryService ().getAllProviderType ();

        JSONArray jsonObject = JSONArray.fromObject (units);
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
            ProviderType type = new ProviderType (bean.getName ());
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
                    type = componentContext.getPersistenceService ().addProviderType (type);
                    ar = createAddSuccessResult (providerTypeMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createAddFailedResult (providerTypeMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("AddProviderType",
                                                                            "LogEvent.AddProviderType",
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
            ProviderType type = new ProviderType (bean.getName ());
            String typeId = bean.getId ();
            if (null != typeId && !"".equals (typeId))
            {
                type.setId (Long.parseLong (typeId));
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                ProviderType oldProviderType = componentContext.getQueryService ().getProviderType (type.getId ());
                try
                {
                    type = componentContext.getPersistenceService ().updateProviderType (type);
                    ar = createUpdateSuccessResult (providerTypeMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createUpdateFailedResult (providerTypeMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("UpdateProviderType",
                                                                            "LogEvent.UpdateProviderType",
                                                                            request.getLocale ());
                        componentContext.getLogService ()
                                        .log (logEventName,
                                              loginUser.getUid (),
                                              logEventName + ": "
                                                      + LogUtil.createLogComment (oldProviderType, type));
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
            List <ProviderType> providerTypes = new ArrayList <ProviderType> ();
            ProviderType providerType;
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    providerType = new ProviderType ();
                    providerType.setId (Long.parseLong (typeId));
                    providerTypes.add (providerType);
                }
            }
            else
            {
                providerType = new ProviderType ();
                providerType.setId (Long.parseLong (ids));
                providerTypes.add (providerType);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    componentContext.getPersistenceService ().deleteProviderType (providerTypes);
                    ar = createDeleteSuccessResult (providerTypeMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (providerTypeMessage);
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

    @Override
    public void afterPropertiesSet () throws Exception
    {
    }

}
