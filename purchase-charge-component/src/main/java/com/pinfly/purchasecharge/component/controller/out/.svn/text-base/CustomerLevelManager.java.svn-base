package com.pinfly.purchasecharge.component.controller.out;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
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
import com.pinfly.purchasecharge.component.bean.CustomerLevelBean;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.controller.GenericController;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.BeanConvertUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerLevel;
import com.pinfly.purchasecharge.dal.DaoContext;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/customerLevel")
public class CustomerLevelManager extends GenericController <CustomerLevelBean>
{
    private static final Logger logger = Logger.getLogger (CustomerLevelManager.class);
    private String customerLevelMessage = ComponentMessage.createMessage ("CUSTOMER_LEVEL", "CUSTOMER_LEVEL")
                                                          .getI18nMessageCode ();

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "customerLevelManagement";
    }
    
    @Override
    protected String getJsName ()
    {
        return "javascript/customerLevelMgmtJS";
    }

    @Override
    public String checkExist (@RequestParam String customerLevelId, @RequestParam String name,
                              HttpServletRequest request)
    {
        if (StringUtils.isBlank (customerLevelId))
        {
            // new
            CustomerLevel obj = DaoContext.getCustomerLevelDao ().findByName (name);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long id = Long.parseLong (customerLevelId);
            CustomerLevel customerLevel = DaoContext.getCustomerLevelDao ().findByName (name);
            if (null != customerLevel && customerLevel.getId () != id)
            {
                return "false";
            }
        }

        return "true";
    }

    @RequestMapping (value = "checkLevelOrderExist", method = RequestMethod.POST)
    public @ResponseBody
    String checkLevelOrderExist (@RequestParam String customerLevelId, @RequestParam String order,
                                 HttpServletRequest request)
    {
        int levelOrder = 0;
        try
        {
            if (StringUtils.isNotBlank (order))
            {
                levelOrder = Integer.parseInt (order);
            }
        }
        catch (Exception e)
        {
            return "false";
        }

        if (StringUtils.isBlank (customerLevelId))
        {
            // new
            CustomerLevel obj = DaoContext.getCustomerLevelDao ().findByOrder (levelOrder);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long id = Long.parseLong (customerLevelId);
            CustomerLevel customerLevel = DaoContext.getCustomerLevelDao ().findByOrder (levelOrder);
            if (null != customerLevel && customerLevel.getId () != id)
            {
                return "false";
            }
        }

        return "true";
    }

    @Override
    public String getAllModel (HttpServletRequest request)
    {
        List <CustomerLevel> levels = componentContext.getQueryService ().getAllCustomerLevel ();
        List <CustomerLevelBean> levelBeans = new ArrayList <CustomerLevelBean> ();
        if (CollectionUtils.isNotEmpty (levels))
        {
            for (CustomerLevel level : levels)
            {
                levelBeans.add (convert (level));
            }
        }
        JSONArray jsonObject = JSONArray.fromObject (levelBeans);
        return jsonObject.toString ();
    }

    @Override
    public String addModel (@Valid CustomerLevelBean bean, BindingResult bindingResult, HttpServletRequest request)
    {
        if (bindingResult.hasErrors ())
        {
            logger.warn (bindingResult.getAllErrors ());
            return createBadRequestResult (null);
        }
        else
        {
            CustomerLevel level = convert (bean);
            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    level = componentContext.getPersistenceService ().addCustomerLevel (level);
                    ar = createAddSuccessResult (customerLevelMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createAddFailedResult (customerLevelMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("AddCustomerLevel",
                                                                            "LogEvent.AddCustomerLevel",
                                                                            request.getLocale ());
                        componentContext.getLogService ().log (logEventName,
                                                               loginUser.getUid (),
                                                               logEventName + ": "
                                                                       + LogUtil.createLogComment (level));
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
    public String updateModel (@Valid CustomerLevelBean bean, BindingResult bindingResult, HttpServletRequest request)
    {
        if (bindingResult.hasErrors ())
        {
            logger.warn (bindingResult.getAllErrors ());
            return createBadRequestResult (null);
        }
        else
        {
            CustomerLevel level = convert (bean);
            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                CustomerLevel oldLevel = componentContext.getQueryService ().getCustomerLevel (level.getId ());
                try
                {
                    List <CustomerLevel> levels = new ArrayList <CustomerLevel> ();
                    levels.add (level);
                    level = componentContext.getPersistenceService ().updateCustomerLevel (levels).get (0);
                    ar = createUpdateSuccessResult (customerLevelMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createUpdateFailedResult (customerLevelMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("UpdateCustomerLevel",
                                                                            "LogEvent.UpdateCustomerLevel",
                                                                            request.getLocale ());
                        componentContext.getLogService ().log (logEventName,
                                                               loginUser.getUid (),
                                                               logEventName
                                                                       + ": "
                                                                       + LogUtil.createLogComment (oldLevel,
                                                                                                            level));
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
            List <CustomerLevel> customerLevels = new ArrayList <CustomerLevel> ();
            CustomerLevel customerLevel;
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    customerLevel = new CustomerLevel ();
                    customerLevel.setId (Long.parseLong (typeId));
                    customerLevels.add (customerLevel);
                }
            }
            else
            {
                customerLevel = new CustomerLevel ();
                customerLevel.setId (Long.parseLong (ids));
                customerLevels.add (customerLevel);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    componentContext.getPersistenceService ().deleteCustomerLevel (customerLevels);
                    ar = createDeleteSuccessResult (customerLevelMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (customerLevelMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("DeleteCustomerLevel",
                                                                            "LogEvent.DeleteCustomerLevel",
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

    @RequestMapping (value = "/enableLevel", method = RequestMethod.POST)
    public @ResponseBody
    String enableLevel (@RequestParam String ids, HttpServletRequest request)
    {
        if (StringUtils.isNotBlank (ids))
        {
            List <CustomerLevel> customerLevels = new ArrayList <CustomerLevel> ();
            CustomerLevel customerLevel;
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    customerLevel = componentContext.getQueryService ().getCustomerLevelById (Long.parseLong (typeId));
                    customerLevel.setEnabled (true);
                    customerLevels.add (customerLevel);
                }
            }
            else
            {
                customerLevel = componentContext.getQueryService ().getCustomerLevelById (Long.parseLong (ids));
                customerLevel.setEnabled (true);
                customerLevels.add (customerLevel);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    componentContext.getPersistenceService ().updateCustomerLevel (customerLevels);
                    ar = createServerOkMessageResult (componentContext.getMessage ("generic.message.enable.success",
                                                                                   new String[]
                                                                                   { componentContext.getMessage ("customerLevel") }));
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (componentContext.getMessage ("generic.message.enable.fail",
                                                                                      new String[]
                                                                                      { componentContext.getMessage ("customerLevel") }));
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("EnableCustomerLevel",
                                                                            "LogEvent.EnableCustomerLevel",
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

    @RequestMapping (value = "/disableLevel", method = RequestMethod.POST)
    public @ResponseBody
    String disableLevel (@RequestParam String ids, HttpServletRequest request)
    {
        if (StringUtils.isNotBlank (ids))
        {
            List <CustomerLevel> customerLevels = new ArrayList <CustomerLevel> ();
            CustomerLevel customerLevel;
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    customerLevel = componentContext.getQueryService ().getCustomerLevelById (Long.parseLong (typeId));
                    customerLevel.setEnabled (false);
                    customerLevels.add (customerLevel);
                }
            }
            else
            {
                customerLevel = componentContext.getQueryService ().getCustomerLevelById (Long.parseLong (ids));
                customerLevel.setEnabled (false);
                customerLevels.add (customerLevel);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    componentContext.getPersistenceService ().updateCustomerLevel (customerLevels);
                    ar = createServerOkMessageResult (componentContext.getMessage ("generic.message.disable.success",
                                                                                   new String[]
                                                                                   { componentContext.getMessage ("customerLevel") }));
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (componentContext.getMessage ("generic.message.disable.fail",
                                                                                      new String[]
                                                                                      { componentContext.getMessage ("customerLevel") }));
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("DisableCustomerLevel",
                                                                            "LogEvent.DisableCustomerLevel",
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

    public static CustomerLevel convert (CustomerLevelBean levelBean)
    {
        if (null != levelBean)
        {
            CustomerLevel level = new CustomerLevel ();
            level.setId (BeanConvertUtils.isNotBlank (levelBean.getId ()) ? Long.parseLong (levelBean.getId ()) : 0);
            level.setName (levelBean.getName ());
            level.setOrder (levelBean.getOrder ());
            level.setSaleMoney (levelBean.getSaleMoney ());
            level.setProfitMoney (levelBean.getProfitMoney ());
            level.setMaxDebt (levelBean.getMaxDebt ());
            level.setPaymentDays (levelBean.getPaymentDays ());
            level.setPriceRate (levelBean.getPriceRate ());
            level.setEnabled (levelBean.isEnabled ());
            return level;
        }
        return null;
    }

    public static CustomerLevelBean convert (CustomerLevel level)
    {
        if (null != level)
        {
            CustomerLevelBean levelBean = new CustomerLevelBean ();
            levelBean.setId (level.getId () + "");
            levelBean.setName (level.getName ());
            levelBean.setOrder (level.getOrder ());
            levelBean.setSaleMoney (level.getSaleMoney ());
            levelBean.setProfitMoney (level.getProfitMoney ());
            levelBean.setMaxDebt (level.getMaxDebt ());
            levelBean.setPaymentDays (level.getPaymentDays ());
            levelBean.setPriceRate (level.getPriceRate ());
            levelBean.setEnabled (level.isEnabled ());
            return levelBean;
        }
        return null;
    }

}
