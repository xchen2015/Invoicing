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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinfly.purchasecharge.component.bean.AccountingTypeBean;
import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.ActionResult.ActionResultStatus;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.BeanConvertUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.AccountingModeCode;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.persistence.AccountingType;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/accountingType")
public class AccountingTypeManager extends GenericController <AccountingTypeBean>
{
    private static final Logger logger = Logger.getLogger (AccountingTypeManager.class);
    private String accountingTypeMessage = ComponentMessage.createMessage ("EXPENSE_TYPE", "EXPENSE_TYPE")
                                                           .getI18nMessageCode ();

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "accountingTypeManagement";
    }
    
    @Override
    protected String getJsName ()
    {
        return "javascript/accountingTypeMgmtJS";
    }

    @Override
    public String checkExist (@RequestParam String expenseTypeId, @RequestParam String name, HttpServletRequest request)
    {
        if (StringUtils.isBlank (expenseTypeId))
        {
            // new
            AccountingType obj = componentContext.getQueryService ().getAccountingType (name);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long id = Long.parseLong (expenseTypeId);
            AccountingType accountType = componentContext.getQueryService ().getAccountingType (name);
            if (null != accountType && accountType.getId () != id)
            {
                return "false";
            }
        }

        return "true";
    }

    @Override
    public String getAllModel (HttpServletRequest request)
    {
        List <AccountingType> accountTypes = (List <AccountingType>) componentContext.getQueryService ()
                                                                                     .getAllAccountingType ();

        JSONArray jsonObject = JSONArray.fromObject (BeanConvertUtils.accountingTypeList2AccountingTypeBeanList (accountTypes));
        return jsonObject.toString ();
    }

    @Override
    public String addModel (AccountingTypeBean bean, BindingResult result, HttpServletRequest request)
    {
        if (result.hasErrors ())
        {
            logger.warn (result.getAllErrors ());
            return createBadRequestResult (null);
        }
        else
        {
            AccountingType accountType = BeanConvertUtils.accountingTypeBean2AccountingType (bean);
            ActionResult ar = ActionResult.createActionResult ().build ();

            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    accountType = componentContext.getPersistenceService ().addAccountingType (accountType);
                    ar = createAddSuccessResult (accountingTypeMessage);
                }
                catch (Exception e)
                {
                    ar = createAddFailedResult (accountingTypeMessage);
                    logger.warn (e.getMessage (), e);
                }

                if (ActionResult.ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventMsg = LogEventName.createEventName ("AddExpenseType", "LogEvent.AddExpenseType",
                                                                           request.getLocale ());
                        componentContext.getLogService ().log (logEventMsg,
                                                               loginUser.getUid (),
                                                               logEventMsg + ": "
                                                                       + LogUtil.createLogComment (accountType));
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
    public String updateModel (AccountingTypeBean bean, BindingResult result, HttpServletRequest request)
    {
        if (result.hasErrors ())
        {
            logger.warn (result.getAllErrors ());
            return createBadRequestResult (null);
        }
        else
        {
            AccountingType accountType = BeanConvertUtils.accountingTypeBean2AccountingType (bean);
            ActionResult ar = ActionResult.createActionResult ().build ();

            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                AccountingType oldAccType = componentContext.getQueryService ().getAccountingType (accountType.getId ());
                try
                {
                    accountType = componentContext.getPersistenceService ().updateAccountingType (accountType);
                    ar = createUpdateSuccessResult (accountingTypeMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createUpdateFailedResult (accountingTypeMessage);
                }

                if (ActionResult.ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventMsg = LogEventName.createEventName ("UpdateExpenseType",
                                                                           "LogEvent.UpdateExpenseType",
                                                                           request.getLocale ());
                        componentContext.getLogService ()
                                        .log (logEventMsg,
                                              loginUser.getUid (),
                                              logEventMsg + ": "
                                                      + LogUtil.createLogComment (oldAccType, accountType));
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
            List <AccountingType> expenseTypes = new ArrayList <AccountingType> ();
            AccountingType expenseType;
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    expenseType = new AccountingType ();
                    long expenseTypeId = Long.parseLong (typeId);
                    expenseType.setId (expenseTypeId);
                    expenseTypes.add (expenseType);
                }
            }
            else
            {
                expenseType = new AccountingType ();
                long expenseTypeId = Long.parseLong (ids);
                expenseType.setId (expenseTypeId);
                expenseTypes.add (expenseType);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    componentContext.getPersistenceService ().deleteAccountingType (expenseTypes);
                    ar = createDeleteSuccessResult (accountingTypeMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (accountingTypeMessage);
                }

                if (ActionResult.ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventMsg = LogEventName.createEventName ("DeleteExpenseType",
                                                                           "LogEvent.DeleteExpenseType",
                                                                           request.getLocale ());
                        componentContext.getLogService ().log (logEventMsg, loginUser.getUid (),
                                                               logEventMsg + ": " + ids);
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
            logger.warn (ActionResultStatus.BAD_REQUEST + " ids=" + ids + " when delete expense type");
            return createBadRequestResult (null);
        }
    }

    @RequestMapping (value = "/getTypeByMode", method = RequestMethod.POST)
    public @ResponseBody
    String getTypeByMode (HttpServletRequest request)
    {
        List <AccountingType> accountTypes = new ArrayList <AccountingType> ();
        Object obj = request.getParameter ("accountingMode");
        if (null != obj && StringUtils.isNotBlank (obj.toString ()))
        {
            AccountingModeCode mode = AccountingModeCode.IN_COME.toString ().equals (obj.toString ()) ? AccountingModeCode.IN_COME
                                                                                                     : AccountingModeCode.OUT_LAY;
            accountTypes = (List <AccountingType>) componentContext.getQueryService ().getAccountingType (mode);
        }
        else
        {
            accountTypes = (List <AccountingType>) componentContext.getQueryService ()
                    .getAllAccountingType ();
        }
        JSONArray jsonObject = JSONArray.fromObject (BeanConvertUtils.accountingTypeList2AccountingTypeBeanList (accountTypes));
        return jsonObject.toString ();
    }

}
