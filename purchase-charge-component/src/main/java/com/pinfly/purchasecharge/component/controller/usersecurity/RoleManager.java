package com.pinfly.purchasecharge.component.controller.usersecurity;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.ActionResult.ActionResultStatus;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.bean.usersecurity.AuthorityBean;
import com.pinfly.purchasecharge.component.bean.usersecurity.RoleBean;
import com.pinfly.purchasecharge.component.controller.GenericController;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.BeanConvertUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.Role;
import com.pinfly.purchasecharge.dal.DaoContext;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/role")
public class RoleManager extends GenericController <RoleBean>
{
    private static final Logger logger = Logger.getLogger (RoleManager.class);
    private String roleMessage = ComponentMessage.createMessage ("ROLE", "ROLE").getI18nMessageCode ();

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "roleManagement";
    }
    
    @Override
    protected String getJsName ()
    {
        return "javascript/roleMgmtJS";
    }

    @Override
    public String checkExist (@RequestParam String roleId, @RequestParam String name, HttpServletRequest request)
    {
        if (StringUtils.isBlank (roleId))
        {
            // new
            Role obj = componentContext.getQueryService ().getRole (name);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long uid = Long.parseLong (roleId);
            Role role = componentContext.getQueryService ().getRole (name);
            if (null != role && role.getId () != uid)
            {
                return "false";
            }
        }

        return "true";
    }

    @Override
    public String getAllModel (HttpServletRequest request)
    {
        List <Role> roles = (List <Role>) componentContext.getQueryService ().getAllRole ();

        JSONArray jsonArray = JSONArray.fromObject (BeanConvertUtils.roleList2RoleBeanList (roles));
        return jsonArray.toString ();
    }

    @Override
    public String addModel (RoleBean bean, BindingResult bindingResult, HttpServletRequest request)
    {
        preprocessRole (bean, request);
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
                Role role = null;
                try
                {
                    List <Role> roles = new ArrayList <Role> ();
                    roles.add (BeanConvertUtils.roleBean2Role (bean));
                    roles = componentContext.getPersistenceService ().addRoles (roles);
                    role = CollectionUtils.isNotEmpty (roles) ? roles.get (0) : null;
                    componentContext.getSecurityResourceMap (true);
                    ar = createAddSuccessResult (roleMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createAddFailedResult (roleMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("AddRole", "LogEvent.AddRole",
                                                                            request.getLocale ());
                        componentContext.getLogService ().log (logEventName,
                                                               loginUser.getUid (),
                                                               logEventName + ": "
                                                                       + LogUtil.createLogComment (role));
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
    public String updateModel (RoleBean bean, BindingResult bindingResult, HttpServletRequest request)
    {
        preprocessRole (bean, request);
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
                Role role = null;
                Role oldRole = null;
                try
                {
                    role = BeanConvertUtils.roleBean2Role (bean);
                    oldRole = DaoContext.getRoleDao ().findOne (role.getId ());
                    role = componentContext.getPersistenceService ().updateRole (role);
                    componentContext.getSecurityResourceMap (true);
                    ar = createUpdateSuccessResult (roleMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createUpdateFailedResult (roleMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("UpdateRole", "LogEvent.UpdateRole",
                                                                            request.getLocale ());
                        componentContext.getLogService ().log (logEventName,
                                                               loginUser.getUid (),
                                                               logEventName
                                                                       + ": "
                                                                       + LogUtil.createLogComment (oldRole,
                                                                                                            role));
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
            Role role;
            List <Role> roles = new ArrayList <Role> ();
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String id : idArr)
                {
                    role = new Role ();
                    role.setId (Long.parseLong (id));
                    roles.add (role);
                }
            }
            else
            {
                role = new Role ();
                role.setId (Long.parseLong (ids));
                roles.add (role);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser && loginUser.isAdmin ())
            {
                try
                {
                    componentContext.getPersistenceService ().deleteRole (roles);
                    componentContext.getSecurityResourceMap (true);
                    ar = createDeleteSuccessResult (roleMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (roleMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("DeleteRole", "LogEvent.DeleteRole",
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

    private void preprocessRole (RoleBean roleBean, HttpServletRequest request)
    {
        String authorities = request.getParameter ("authorities");
        if (StringUtils.isNotBlank (authorities))
        {
            List <AuthorityBean> authBeans = new ArrayList <AuthorityBean> ();
            AuthorityBean authorityBean = null;
            String[] authArr = authorities.split (",");
            for (String authId : authArr)
            {
                authorityBean = new AuthorityBean ();
                authorityBean.setId (authId);
                authBeans.add (authorityBean);
            }
            roleBean.setAuthorityBeans (authBeans);
        }
    }

}
