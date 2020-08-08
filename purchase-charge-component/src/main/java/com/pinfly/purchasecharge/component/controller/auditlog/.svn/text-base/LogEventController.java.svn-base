package com.pinfly.purchasecharge.component.controller.auditlog;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.ActionResult.ActionResultStatus;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventBean;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.controller.GenericController;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.persistence.auditlog.LogEvent;

@Controller
@RequestMapping ("/logEvent")
public class LogEventController extends GenericController <LogEventBean>
{
    private static final Logger logger = Logger.getLogger (LogEventController.class);
    private String logEventMessage = ComponentMessage.createMessage ("LOG_EVENT", "LOG_EVENT").getI18nMessageCode ();

    @Override
    @Deprecated
    protected String getViewName (HttpServletRequest request)
    {
        return "logEventManagement";
    }

    @Override
    @Deprecated
    public String checkExist (@RequestParam String logEventId, @RequestParam String name, HttpServletRequest request)
    {
        if (StringUtils.isBlank (logEventId))
        {
            // new
            LogEvent obj = componentContext.getLogService ().getLogEvent (name);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long id = Long.parseLong (logEventId);
            LogEvent logEvent = componentContext.getLogService ().getLogEvent (name);
            if (null != logEvent && logEvent.getId () != id)
            {
                return "false";
            }
        }

        return "true";
    }

    @Override
    public String getAllModel (HttpServletRequest request)
    {
        List <LogEvent> logEvents = (List <LogEvent>) componentContext.getLogService ().getAll ();

        JSONArray jsonObject = JSONArray.fromObject (logEvents);
        return jsonObject.toString ();
    }

    @Override
    @Deprecated
    public String addModel (LogEventBean bean, BindingResult bindingResult, HttpServletRequest request)
    {
        if (bindingResult.hasErrors ())
        {
            logger.warn (bindingResult.getAllErrors ());
            return createBadRequestResult (null);
        }
        else
        {
            LogEvent logEvent = new LogEvent (bean.getName ());
            logEvent.setEnabled (bean.isEnabled ());

            ActionResult ar = ActionResult.createActionResult ().build ();
            try
            {
                componentContext.getLogService ().saveLogEvent (logEvent);
                ar = createAddSuccessResult (logEventMessage);
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage (), e);
                ar = createAddFailedResult (logEventMessage);
            }
            return AjaxUtils.getJsonObject (ar);
        }
    }

    @Override
    @Deprecated
    public String updateModel (LogEventBean bean, BindingResult bindingResult, HttpServletRequest request)
    {
        if (bindingResult.hasErrors ())
        {
            logger.warn (bindingResult.getAllErrors ());
            return createBadRequestResult (null);
        }
        else
        {
            LogEvent logEvent = new LogEvent (bean.getName ());
            logEvent.setId (StringUtils.isNotBlank (bean.getId ()) ? Long.parseLong (bean.getId ()) : 0);
            logEvent.setEnabled (bean.isEnabled ());

            ActionResult ar = ActionResult.createActionResult ().build ();
            try
            {
                componentContext.getLogService ().saveLogEvent (logEvent);
                ar = createUpdateSuccessResult (logEventMessage);
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage (), e);
                ar = createUpdateFailedResult (logEventMessage);
            }
            return AjaxUtils.getJsonObject (ar);
        }
    }

    @Override
    @Deprecated
    public String deleteModels (@RequestParam String ids, HttpServletRequest request)
    {
        if (StringUtils.isNotBlank (ids))
        {
            List <LogEvent> logEvents = new ArrayList <LogEvent> ();
            LogEvent logEvent;
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    logEvent = new LogEvent ();
                    logEvent.setId (Long.parseLong (typeId));
                    logEvents.add (logEvent);
                }
            }
            else
            {
                logEvent = new LogEvent ();
                logEvent.setId (Long.parseLong (ids));
                logEvents.add (logEvent);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            try
            {
                componentContext.getLogService ().deleteLogEvent (logEvents);
                ar = createDeleteSuccessResult (logEventMessage);
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage (), e);
                ar = createDeleteFailedResult (logEventMessage);
            }
            return AjaxUtils.getJsonObject (ar);
        }
        else
        {
            logger.warn (ActionResultStatus.BAD_REQUEST);
            return createBadRequestResult (null);
        }
    }

    @RequestMapping (value = "/enableLogEvent", method = RequestMethod.POST)
    public @ResponseBody
    String enableLogEvent (@RequestParam String ids, HttpServletRequest request)
    {
        if (StringUtils.isNotBlank (ids))
        {
            List <LogEvent> logEvents = new ArrayList <LogEvent> ();
            LogEvent logEvent;
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    logEvent = componentContext.getLogService ().getLogEvent (Long.parseLong (typeId));
                    logEvent.setEnabled (true);
                    logEvents.add (logEvent);
                }
            }
            else
            {
                logEvent = componentContext.getLogService ().getLogEvent (Long.parseLong (ids));
                logEvent.setEnabled (true);
                logEvents.add (logEvent);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    componentContext.getLogService ().saveLogEvent (logEvents);
                    ar = createServerOkMessageResult (componentContext.getMessage ("generic.message.enable.success",
                                                                                   new String[]
                                                                                   { componentContext.getMessage ("log.logEvent") }));
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (componentContext.getMessage ("generic.message.enable.fail",
                                                                                      new String[]
                                                                                      { componentContext.getMessage ("log.logEvent") }));
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("EnableLogEvent",
                                                                            "LogEvent.EnableLogEvent",
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

    @RequestMapping (value = "/disableLogEvent", method = RequestMethod.POST)
    public @ResponseBody
    String disableLogEvent (@RequestParam String ids, HttpServletRequest request)
    {
        if (StringUtils.isNotBlank (ids))
        {
            List <LogEvent> logEvents = new ArrayList <LogEvent> ();
            LogEvent logEvent;
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    logEvent = componentContext.getLogService ().getLogEvent (Long.parseLong (typeId));
                    logEvent.setEnabled (false);
                    logEvents.add (logEvent);
                }
            }
            else
            {
                logEvent = componentContext.getLogService ().getLogEvent (Long.parseLong (ids));
                logEvent.setEnabled (false);
                logEvents.add (logEvent);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    componentContext.getLogService ().saveLogEvent (logEvents);
                    ar = createServerOkMessageResult (componentContext.getMessage ("generic.message.disable.success",
                                                                                   new String[]
                                                                                   { componentContext.getMessage ("log.logEvent") }));
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (componentContext.getMessage ("generic.message.disable.fail",
                                                                                      new String[]
                                                                                      { componentContext.getMessage ("log.logEvent") }));
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("DisableLogEvent",
                                                                            "LogEvent.DisableLogEvent",
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
