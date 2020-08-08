package com.pinfly.purchasecharge.component.controller.usersecurity;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinfly.common.util.Mail;
import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.ActionResult.ActionResultStatus;
import com.pinfly.purchasecharge.component.bean.BaseBean;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.controller.GenericController;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.core.config.PurchaseChargeProperties;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.User;
import com.pinfly.purchasecharge.core.util.PurchaseChargeConstants;

@Controller
@RequestMapping ("/password")
public class PasswordManager extends GenericController <BaseBean>
{
    private static final Logger logger = Logger.getLogger (PasswordManager.class);
    private static final String VALIDATION_REGEX = "password.validation.regex";
    private static final String VALIDATION_EXPRESSION = "password.validation.expression";
    private static final String VALIDATION_SEPARATOR = "\\|\\|";
    private String passwordMessage = ComponentMessage.createMessage ("PASSWORD", "PASSWORD").getI18nMessageCode ();

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "passwordManagement";
    }

    @RequestMapping (value = "/updatePassword", method = RequestMethod.POST)
    public @ResponseBody
    String updatePassword (@RequestParam String userId, @RequestParam String newPwd, HttpServletRequest request)
    {
        if (StringUtils.isNotBlank (userId) && StringUtils.isNotBlank (newPwd))
        {
            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    componentContext.getPersistenceService ().updatePassword (userId, newPwd);
                    ar = createUpdateSuccessResult (passwordMessage);
                }
                catch (Exception e)
                {
                    logger.warn ("Update password failed. (userId=" + userId + "), " + e.getMessage ());
                    ar = createUpdateFailedResult (passwordMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("UpdateUserPassword",
                                                                            "LogEvent.UpdateUserPassword",
                                                                            request.getLocale ());
                        componentContext.getLogService ().log (logEventName, loginUser.getUid (), logEventName);

                        sendNotificationToUser (userId);
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

    @RequestMapping (value = "/resetPassword", method = RequestMethod.POST)
    public @ResponseBody
    String resetPassword (@RequestParam String userId, HttpServletRequest request)
    {
        if (StringUtils.isNotBlank (userId))
        {
            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    componentContext.getPersistenceService ()
                                    .updatePassword (userId, PurchaseChargeProperties.getDefaultPassword ());
                    ar = createServerOkMessageResult (PurchaseChargeConstants.RESET_PWD_SUCCESS, true);
                }
                catch (Exception e)
                {
                    logger.warn ("Update password failed. (userId=" + userId + "), " + e.getMessage ());
                    ar = createServerErrorMessageResult (PurchaseChargeConstants.RESET_PWD_FAIL, true);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("resetUserPassword",
                                                                            "LogEvent.resetUserPassword",
                                                                            request.getLocale ());
                        componentContext.getLogService ().log (logEventName, loginUser.getUid (), logEventName);

                        sendNotificationToUser (userId);
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

    @RequestMapping (value = "/checkOldPassword", method = RequestMethod.POST)
    public @ResponseBody
    String checkOldPassword (@RequestParam String userId, @RequestParam String oldPwd)
    {
        if (StringUtils.isNotBlank (userId) && StringUtils.isNotBlank (oldPwd))
        {
            if (componentContext.getQueryService ().checkPassword (userId, oldPwd))
            {
                return "true";
            }
        }

        return "false";
    }

    @RequestMapping (value = "/checkPasswordComplexity", method = RequestMethod.POST)
    public @ResponseBody
    String checkPasswordComplexity (@RequestParam String password)
    {
        String[] results = new String[2];
        results[0] = "true";
        results[1] = "";
        if (StringUtils.isNotBlank (password))
        {
            String validationRegex = PurchaseChargeProperties.getInstance ().getConfig (VALIDATION_REGEX);
            String validationExpression = PurchaseChargeProperties.getInstance ().getConfig (VALIDATION_EXPRESSION);
            if (StringUtils.isNotBlank (validationRegex) && StringUtils.isNotBlank (validationExpression))
            {
                String[] regexArr = validationRegex.split (VALIDATION_SEPARATOR);
                String[] expressionArr = validationExpression.split (VALIDATION_SEPARATOR);
                if (regexArr.length != expressionArr.length)
                {
                    logger.warn ("Password's validationRegex and validationExpression are not fitable");
                }
                else
                {
                    for (int i = 0; i < regexArr.length; i++)
                    {
                        if (!password.matches (regexArr[i]))
                        {
                            results[0] = "false";
                            results[1] = expressionArr[i];
                            break;
                        }
                    }
                }
            }
        }

        JSONArray jsonArray = JSONArray.fromObject (results);
        return jsonArray.toString ();
    }

    private void sendNotificationToUser (String userId)
    {
        if (StringUtils.isNotBlank (userId))
        {
            User user = componentContext.getQueryService ().getUser (userId);
            if (null != user && user.isEnabled () && StringUtils.isNotBlank (user.getEmail ()))
            {
                String[] toAddrs =
                { user.getEmail () };
                String systemTitle = PurchaseChargeProperties.getInstance ().getConfig ("pc.title");
                String subject = PurchaseChargeProperties.getInstance ()
                                                         .getConfigFormatted ("pc.sendPasswordChangeMailTitle",
                                                                              systemTitle);
                String adminPhone = StringUtils.isNotBlank (user.getMobilePhone ()) ? user.getMobilePhone () : "";
                String text = PurchaseChargeProperties.getInstance ()
                                                      .getConfigFormatted ("pc.sendPasswordChangeMailBody",
                                                                           user.getUserId (), systemTitle,
                                                                           user.getEmail () + " " + adminPhone);
                try
                {
                    Mail.sendMessage (toAddrs, subject, text, null);
                }
                catch (Exception e)
                {
                    logger.warn ("Send password change notification mail failed", e);
                }
            }
        }
    }

}
