package com.pinfly.purchasecharge.component.controller.usersecurity;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.ActionResult.ActionResultStatus;
import com.pinfly.purchasecharge.component.bean.DataGridRequestForm;
import com.pinfly.purchasecharge.component.bean.GenericPagingResult;
import com.pinfly.purchasecharge.component.bean.SearchRequestForm;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.bean.usersecurity.RoleBean;
import com.pinfly.purchasecharge.component.bean.usersecurity.UserBean;
import com.pinfly.purchasecharge.component.controller.GenericController;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.BeanConvertUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.User;
import com.pinfly.purchasecharge.core.util.PurchaseChargeConstants;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/user")
public class UserManager extends GenericController <UserBean>
{
    private static final Logger logger = Logger.getLogger (UserManager.class);
    private String userMessage = ComponentMessage.createMessage ("USER", "USER").getI18nMessageCode ();

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "userManagement";
    }

    @Override
    protected String getJsName ()
    {
        return "javascript/userMgmtJS";
    }

    @Override
    public String checkExist (@RequestParam String userUniqueId, @RequestParam String userId, HttpServletRequest request)
    {
        if (StringUtils.isBlank (userUniqueId))
        {
            // new
            User obj = componentContext.getQueryService ().getUser (userId);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long uid = Long.parseLong (userUniqueId);
            User user = componentContext.getQueryService ().getUser (userId);
            if (null != user && user.getId () != uid)
            {
                return "false";
            }
        }

        return "true";
    }

    @Override
    public String getAllModel (HttpServletRequest request)
    {
        List <User> users = (List <User>) componentContext.getQueryService ().getAllUser ();

        JSONArray jsonArr = JSONArray.fromObject (BeanConvertUtils.userList2UserBeanList (users));
        return jsonArr.toString ();
    }

    @Override
    public String addModel (UserBean bean, BindingResult bindingResult, HttpServletRequest request)
    {
        preprocessUser (bean, request);
        if (bindingResult.hasErrors ())
        {
            logger.warn (bindingResult.getAllErrors ());
            return createBadRequestResult (null);
        }
        else
        {
            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser && loginUser.isAdmin ())
            {
                User user = null;
                try
                {
                    List <User> users = new ArrayList <User> ();
                    users.add (BeanConvertUtils.userBean2User (bean));
                    users = componentContext.getPersistenceService ().addUsers (users);
                    user = CollectionUtils.isNotEmpty (users) ? users.get (0) : null;
                    ar = createAddSuccessResult (userMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createAddFailedResult (userMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("AddUser", "LogEvent.AddUser",
                                                                            request.getLocale ());
                        componentContext.getLogService ().log (logEventName,
                                                               loginUser.getUid (),
                                                               logEventName + ": "
                                                                       + LogUtil.createLogComment (user));
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
            else
            {
                ar = createServerErrorMessageResult ("pc.nonAdminOperate.warn", true);
            }
            return AjaxUtils.getJsonObject (ar);
        }
    }

    @Override
    public String updateModel (UserBean bean, BindingResult bindingResult, HttpServletRequest request)
    {
        preprocessUser (bean, request);
        if (bindingResult.hasErrors ())
        {
            logger.warn (bindingResult.getAllErrors ());
            return createBadRequestResult (null);
        }
        else
        {
            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser && loginUser.isAdmin ())
            {
                User user = null;
                User storedUser = null;
                try
                {
                    user = BeanConvertUtils.userBean2User (bean);
                    storedUser = componentContext.getQueryService ().getUser (user.getId ());
                    user.setPwd (storedUser.getPwd ());
                    user = componentContext.getPersistenceService ().updateUser (user);
                    if (user != null && user.getUserId ().equals (loginUser.getUserId ())
                        && storedUser.isAdmin () != user.isAdmin ())
                    {
                        loginUser = componentContext.getLoginUser (request);
                        loginUser.setAdmin (user.isAdmin ());
                        componentContext.setLoginUserToSession (request, loginUser);
                    }
                    ar = createUpdateSuccessResult (userMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createUpdateFailedResult (userMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("UpdateUser", "LogEvent.UpdateUser",
                                                                            request.getLocale ());
                        componentContext.getLogService ().log (logEventName,
                                                               loginUser.getUid (),
                                                               logEventName
                                                                       + ": "
                                                                       + LogUtil.createLogComment (storedUser,
                                                                                                            user));
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
            else
            {
                ar = createServerErrorMessageResult ("pc.nonAdminOperate.warn", true);
            }
            return AjaxUtils.getJsonObject (ar);
        }
    }

    @Override
    public String deleteModels (@RequestParam String ids, HttpServletRequest request)
    {
        if (null != ids && ids.trim ().length () > 0)
        {
            User user;
            List <User> users = new ArrayList <User> ();
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String id : idArr)
                {
                    user = new User ();
                    user.setId (Long.parseLong (id));
                    users.add (user);
                }
            }
            else
            {
                user = new User ();
                user.setId (Long.parseLong (ids));
                users.add (user);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser && loginUser.isAdmin ())
            {
                try
                {
                    componentContext.getPersistenceService ().deleteUser (users);
                    ar = createDeleteSuccessResult (userMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (userMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("DeleteUser", "LogEvent.DeleteUser",
                                                                            request.getLocale ());
                        componentContext.getLogService ().log (logEventName, loginUser.getUid (),
                                                               logEventName + ": " + ids);
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
            else
            {
                ar = createServerErrorMessageResult ("pc.nonAdminOperate.warn", true);
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
    protected String getModelBySearchForm (DataGridRequestForm dataGridRequestForm,
                                           SearchRequestForm searchRequestForm, HttpServletRequest request)
    {
        int page = dataGridRequestForm.getPage () - 1;
        int size = dataGridRequestForm.getRows ();
        String sortField = parseSortField (dataGridRequestForm.getSort ());
        String order = dataGridRequestForm.getOrder ();
        Pageable pageable = new PageRequest (page, size, new Sort ("asc".equalsIgnoreCase (order) ? Direction.ASC
                                                                                                 : Direction.DESC,
                                                                   sortField));
        String searchKey = (null == dataGridRequestForm.getSearchKey () ? "" : dataGridRequestForm.getSearchKey ()
                                                                                                  .trim ());
        Page <User> userPage = componentContext.getQueryService ().findUser (pageable, searchKey);
        List <User> users = userPage.getContent ();
        long total = userPage.getTotalElements ();
        GenericPagingResult <UserBean> userPagingResult = new GenericPagingResult <UserBean> ();
        userPagingResult.setRows (BeanConvertUtils.userList2UserBeanList (users));
        userPagingResult.setTotal (total);

        JSONObject jsonObject = JSONObject.fromObject (userPagingResult);
        return jsonObject.toString ();
    }

    private String parseSortField (final String sortField)
    {
        String sortFieldAfterParse = "userId";
        if (!StringUtils.isBlank (sortField))
        {
            if (PurchaseChargeConstants.PHONE.equalsIgnoreCase (sortField))
            {
                sortFieldAfterParse = PurchaseChargeConstants.MOBILE_PHONE;
            }
        }
        return sortFieldAfterParse;
    }

    private void preprocessUser (UserBean userBean, HttpServletRequest request)
    {
        String roles = request.getParameter ("roles");
        if (StringUtils.isNotBlank (roles))
        {
            List <RoleBean> roleBeans = new ArrayList <RoleBean> ();
            RoleBean roleBean = null;
            String[] roleArr = roles.split (",");
            for (String roleId : roleArr)
            {
                roleBean = new RoleBean ();
                roleBean.setId (roleId);
                roleBeans.add (roleBean);
            }
            userBean.setRoleBeans (roleBeans);
        }
    }

}
