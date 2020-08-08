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
import com.pinfly.purchasecharge.component.controller.GenericController;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.BeanConvertUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.Authority;
import com.pinfly.purchasecharge.dal.DaoContext;

@Controller
@RequestMapping ("/authority")
public class AuthorityManager extends GenericController <AuthorityBean>
{
    private static final Logger logger = Logger.getLogger (AuthorityManager.class);
    private static List <AuthorityBean> cachedAuthorities = new ArrayList <AuthorityBean> ();
    private String authorityMessage = ComponentMessage.createMessage ("AUTHORITY", "AUTHORITY").getI18nMessageCode ();

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "authorityManagement";
    }

    @Override
    public String checkExist (@RequestParam String authorityId, @RequestParam String name, HttpServletRequest request)
    {
        if (StringUtils.isBlank (authorityId))
        {
            // new
            Authority obj = componentContext.getQueryService ().getAuthority (name);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long uid = Long.parseLong (authorityId);
            Authority authority = componentContext.getQueryService ().getAuthority (name);
            if (null != authority && authority.getId () != uid)
            {
                return "false";
            }
        }

        return "true";
    }

    @Override
    public String getAllModel (HttpServletRequest request)
    {
        if (cachedAuthorities.isEmpty ())
        {
            List <Authority> authorities = (List <Authority>) componentContext.getQueryService ().getAllAuthority ();
            if (null != authorities)
            {
                for (Authority authority : authorities)
                {
                    if (null != authority && null == authority.getParent ())
                    {
                        AuthorityBean authorityBean = authority2AuthorityBean (authority);
                        cachedAuthorities.add (authorityBean);
                    }
                }
            }
        }

        JSONArray jsonObject = JSONArray.fromObject (cachedAuthorities);
        return jsonObject.toString ();
    }

    @Override
    public String addModel (AuthorityBean bean, BindingResult bindingResult, HttpServletRequest request)
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
            if (null != loginUser && loginUser.isAdmin ())
            {
                Authority auth = null;
                try
                {
                    List <Authority> authorities = new ArrayList <Authority> ();
                    authorities.add (BeanConvertUtils.authorityBean2Authority (bean));
                    authorities = componentContext.getPersistenceService ().addAuthorities (authorities);
                    auth = CollectionUtils.isNotEmpty (authorities) ? authorities.get (0) : null;
                    ar = createAddSuccessResult (authorityMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createAddFailedResult (authorityMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("AddAuthority", "LogEvent.AddAuthority",
                                                                            request.getLocale ());
                        componentContext.getLogService ().log (logEventName,
                                                               loginUser.getUid (),
                                                               logEventName + ": "
                                                                       + LogUtil.createLogComment (auth));
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
    public String updateModel (AuthorityBean bean, BindingResult bindingResult, HttpServletRequest request)
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
            if (null != loginUser && loginUser.isAdmin ())
            {
                Authority auth = null;
                Authority oldAuth = null;
                try
                {
                    auth = BeanConvertUtils.authorityBean2Authority (bean);
                    oldAuth = DaoContext.getAuthorityDao ().findOne (auth.getId ());
                    auth = componentContext.getPersistenceService ().updateAuthority (auth);
                    ar = createUpdateSuccessResult (authorityMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createUpdateFailedResult (authorityMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("UpdateAuthority",
                                                                            "LogEvent.UpdateAuthority",
                                                                            request.getLocale ());
                        componentContext.getLogService ().log (logEventName,
                                                               loginUser.getUid (),
                                                               logEventName
                                                                       + ": "
                                                                       + LogUtil.createLogComment (oldAuth,
                                                                                                            auth));
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
            Authority authority;
            List <Authority> authoritys = new ArrayList <Authority> ();
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String id : idArr)
                {
                    authority = new Authority ();
                    authority.setId (Long.parseLong (id));
                    authoritys.add (authority);
                }
            }
            else
            {
                authority = new Authority ();
                authority.setId (Long.parseLong (ids));
                authoritys.add (authority);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser && loginUser.isAdmin ())
            {
                try
                {
                    componentContext.getPersistenceService ().deleteAuthority (authoritys);
                    ar = createDeleteSuccessResult (authorityMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (authorityMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("DeleteAuthority",
                                                                            "LogEvent.DeleteAuthority",
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

    private AuthorityBean authority2AuthorityBean (Authority authority)
    {
        AuthorityBean authorityBean = new AuthorityBean ();
        if (null != authority.getParent ())
        {
            authorityBean.setId (authority.getId () + "");
            authorityBean.setName (authority.getName ());
            authorityBean.setEnabled (authority.isEnabled ());
            authorityBean.setUrl (authority.getUrl ());
            List <AuthorityBean> children = new ArrayList <AuthorityBean> ();
            List <Authority> list = componentContext.getQueryService ().getParentAuthority (authority.getId ());
            if (CollectionUtils.isNotEmpty (list))
            {
                for (Authority auth : list)
                {
                    children.add (authority2AuthorityBean (auth));
                }
            }
            authorityBean.setChildren (children);
        }
        else
        {
            authorityBean.setId (authority.getId () + "");
            authorityBean.setName (authority.getName ());
            authorityBean.setEnabled (authority.isEnabled ());
            authorityBean.setUrl (authority.getUrl ());
            List <AuthorityBean> children = new ArrayList <AuthorityBean> ();
            List <Authority> list = componentContext.getQueryService ().getParentAuthority (authority.getId ());
            if (CollectionUtils.isNotEmpty (list))
            {
                for (Authority auth : list)
                {
                    children.add (authority2AuthorityBean (auth));
                }
            }
            authorityBean.setChildren (children);
        }
        return authorityBean;
    }

}
