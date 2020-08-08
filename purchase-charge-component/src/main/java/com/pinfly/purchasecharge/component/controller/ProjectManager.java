package com.pinfly.purchasecharge.component.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.pinfly.purchasecharge.component.bean.DataGridRequestForm;
import com.pinfly.purchasecharge.component.bean.OrderBean;
import com.pinfly.purchasecharge.component.bean.ProjectBean;
import com.pinfly.purchasecharge.component.bean.SearchRequestForm;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.BeanConvertUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.OrderStatusCode;
import com.pinfly.purchasecharge.core.model.OrderTypeCode;
import com.pinfly.purchasecharge.core.model.ProjectStatusCode;
import com.pinfly.purchasecharge.core.model.persistence.Project;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOut;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.User;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.dal.DaoContext;
import com.pinfly.purchasecharge.dal.ProjectDao;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/project")
public class ProjectManager extends GenericController <ProjectBean>
{
    private static final Logger logger = Logger.getLogger (ProjectManager.class);

    private static String projectMessage = ComponentMessage.createMessage ("PROJECT", "PROJECT").getI18nMessageCode ();
    private static ProjectDao projectDao = DaoContext.getProjectDao ();

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "projectManagement";
    }

    @Override
    protected String checkExist (@RequestParam String projectId, @RequestParam String projectName,
                                 HttpServletRequest request)
    {
        if (StringUtils.isBlank (projectId))
        {
            // new
            Project obj = projectDao.findByName (projectName);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long id = Long.parseLong (projectId);
            Project obj = projectDao.findByName (projectName);
            if (null != obj && obj.getId () != id)
            {
                return "false";
            }
        }

        return "true";
    }

    @Override
    protected String getModelBySearchForm (DataGridRequestForm dataGridRequestForm,
                                           SearchRequestForm searchRequestForm, HttpServletRequest request)
    {
        List <Project> projects = (List <Project>) projectDao.findAll ();
        List <ProjectBean> projectBeans = new ArrayList <ProjectBean> ();
        for (Project project : projects)
        {
            projectBeans.add (convert (project));
        }

        JSONArray jsonArr = JSONArray.fromObject (projectBeans);
        return jsonArr.toString ();
    }

    @Override
    protected String getModelById (String id, HttpServletRequest request)
    {
        // TODO Auto-generated method stub
        return super.getModelById (id, request);
    }

    @Override
    protected String getAllModel (HttpServletRequest request)
    {
        List <Project> projects = new ArrayList <Project> ();
        String customer = request.getParameter ("customerId");
        if (StringUtils.isNotBlank (customer))
        {
            projects = projectDao.findByCustomer (Long.parseLong (customer));
        }
        else
        {
            projects = (List <Project>) projectDao.findAll ();
        }

        List <ProjectBean> projectBeans = new ArrayList <ProjectBean> ();
        for (Project project : projects)
        {
            if (!ProjectStatusCode.CANCELED.equals (project.getStatusCode ())
                && !ProjectStatusCode.COMPLETED.equals (project.getStatusCode ()))
            {
                projectBeans.add (convert (project));
            }
        }
        JSONArray jsonArr = JSONArray.fromObject (projectBeans);
        return jsonArr.toString ();
    }

    @Override
    protected String addModel (@Valid ProjectBean bean, BindingResult bindingResult, HttpServletRequest request)
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
                bean.setUserCreatedBy (loginUser.getUserId ());
                bean.setUserUpdatedBy (loginUser.getUserId ());
                Project project = convert (bean);
                try
                {
                    project = componentContext.getPersistenceService ().addProject (project);
                    ar = createAddSuccessResult (projectMessage);
                }
                catch (Exception e)
                {
                    ar = createAddFailedResult (projectMessage);
                    logger.warn (e.getMessage (), e);
                }

                if (ActionResult.ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventMsg = LogEventName.createEventName ("AddProject", "LogEvent.AddProject",
                                                                           request.getLocale ());
                        componentContext.getLogService ().log (logEventMsg,
                                                               loginUser.getUid (),
                                                               logEventMsg + ": "
                                                                       + LogUtil.createLogComment (project));
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
    protected String updateModel (@Valid ProjectBean bean, BindingResult bindingResult, HttpServletRequest request)
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
                bean.setUserUpdatedBy (loginUser.getUserId ());
                if (ProjectStatusCode.COMPLETED.equals (bean.getStatusCode ()))
                {
                    bean.setEnd (DateUtils.date2String (new Date (), DateUtils.DATE_TIME_NO_SECOND_PATTERN));
                }
                Project project = convert (bean);
                Project oldProject = projectDao.findOne (project.getId ());
                try
                {
                    project = componentContext.getPersistenceService ().updateProject (project);
                    ar = createUpdateSuccessResult (projectMessage);
                }
                catch (Exception e)
                {
                    ar = createUpdateFailedResult (projectMessage);
                    logger.warn (e.getMessage (), e);
                }

                if (ActionResult.ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventMsg = LogEventName.createEventName ("UpdateProject", "LogEvent.UpdateProject",
                                                                           request.getLocale ());
                        componentContext.getLogService ().log (logEventMsg,
                                                               loginUser.getUid (),
                                                               logEventMsg
                                                                       + ": "
                                                                       + LogUtil.createLogComment (oldProject,
                                                                                                            project));
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
            List <Project> projects = new ArrayList <Project> ();
            Project project;
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    project = new Project ();
                    long projectId = Long.parseLong (typeId);
                    project.setId (projectId);
                    projects.add (project);
                }
            }
            else
            {
                project = new Project ();
                long projectId = Long.parseLong (ids);
                project.setId (projectId);
                projects.add (project);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    componentContext.getPersistenceService ().deleteProject (projects);
                    ar = createDeleteSuccessResult (projectMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (projectMessage);
                }

                if (ActionResult.ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventMsg = LogEventName.createEventName ("DeleteProject", "LogEvent.DeleteProject",
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
            logger.warn (ActionResultStatus.BAD_REQUEST + " ids=" + ids + " when delete project");
            return createBadRequestResult (null);
        }
    }

    @RequestMapping (value = "/getAvailableOrderByCustomer", method = RequestMethod.POST)
    public @ResponseBody
    String getAvailableOrderByCustomer (@RequestParam String projectId, @RequestParam String customerId)
    {
        List <OrderBean> orderBeans = new ArrayList <OrderBean> ();
        if (StringUtils.isNotBlank (customerId))
        {
            long project = 0;
            try
            {
                project = Long.parseLong (projectId);
            }
            catch (NumberFormatException e)
            {
                logger.warn (e.getMessage ());
            }

            long customer = 0;
            try
            {
                customer = Long.parseLong (customerId);
            }
            catch (NumberFormatException e)
            {
                logger.warn (e.getMessage ());
            }

            if (0 != customer)
            {
                List <OrderOut> orderOuts = componentContext.getQueryService ().getOrderOutByCustomer (customer);
                for (OrderOut orderOut : orderOuts)
                {
                    if (!OrderTypeCode.OUT_RETURN.equals (orderOut.getTypeCode ())
                        && !OrderStatusCode.CANCELED.equals (orderOut.getStatusCode ()))
                    {
                        if (null == orderOut.getProject ()
                            || (null != orderOut.getProject () && project == orderOut.getProject ().getId ()))
                        {
                            orderBeans.add (BeanConvertUtils.orderOut2OrderBean (orderOut));
                        }
                    }
                }
            }
        }

        Collections.sort (orderBeans, new Comparator <OrderBean> ()
        {
            @Override
            public int compare (OrderBean o1, OrderBean o2)
            {
                return DateUtils.string2Date (o2.getCreateTime (), DateUtils.DATE_TIME_PATTERN)
                                .compareTo (DateUtils.string2Date (o1.getCreateTime (), DateUtils.DATE_TIME_PATTERN));
            }
        });

        JSONArray jsonArr = JSONArray.fromObject (orderBeans);
        return jsonArr.toString ();
    }

    public static ProjectBean convert (Project bean)
    {
        if (null != bean)
        {
            ProjectBean project = new ProjectBean ();
            project.setId (bean.getId () + "");
            project.setName (bean.getName ());
            project.setCustomer (BeanConvertUtils.customer2CustomerBean (bean.getCustomer ()));
            project.setStart (DateUtils.date2String (bean.getStart (), DateUtils.DATE_TIME_NO_SECOND_PATTERN));
            project.setEnd (DateUtils.date2String (bean.getEnd (), DateUtils.DATE_TIME_NO_SECOND_PATTERN));
            project.setStatusCode (bean.getStatusCode ());
            project.setConstructionFee (bean.getConstructionFee ());
            project.setOtherFee (bean.getOtherFee ());
            project.setOrders (BeanConvertUtils.orderOutList2OrderBeanList (bean.getOrders ()));
            project.setComment (bean.getComment ());
            User user = DaoContext.getUserDao ().findOne (bean.getUserCreatedBy ());
            if (null != user)
            {
                project.setUserCreatedBy (user.getUserId ());
            }
            user = DaoContext.getUserDao ().findOne (bean.getUserUpdatedBy ());
            if (null != user)
            {
                project.setUserUpdatedBy (user.getUserId ());
            }
            return project;
        }
        return null;
    }

    public static Project convert (ProjectBean bean)
    {
        if (null != bean)
        {
            Project project = new Project ();
            long projectId = 0;
            if (StringUtils.isNotBlank (bean.getId ()))
            {
                try
                {
                    projectId = Long.parseLong (bean.getId ());
                }
                catch (NumberFormatException e)
                {
                    logger.warn (e.getMessage ());
                }
            }
            project.setId (projectId);
            project.setName (bean.getName ());
            project.setCustomer (BeanConvertUtils.customerBean2Customer (bean.getCustomer ()));
            project.setStart (DateUtils.string2Date (bean.getStart (), DateUtils.DATE_PATTERN));
            project.setEnd (DateUtils.string2Date (bean.getEnd (), DateUtils.DATE_TIME_NO_SECOND_PATTERN));
            project.setStatusCode (bean.getStatusCode ());
            project.setConstructionFee (bean.getConstructionFee ());
            project.setOtherFee (bean.getOtherFee ());
            if (StringUtils.isNotBlank (bean.getOrdersText ()))
            {
                List <OrderOut> orders = new ArrayList <OrderOut> ();
                String[] orderArr = bean.getOrdersText ().split (",");
                if (orderArr.length > 0)
                {
                    for (int i = 0; i < orderArr.length; i++)
                    {
                        OrderOut order = new OrderOut ();
                        order.setId (Long.parseLong (orderArr[i]));
                        orders.add (order);
                    }
                }
                project.setOrders (orders);
            }
            project.setComment (bean.getComment ());
            if (StringUtils.isNotBlank (bean.getUserCreatedBy ()))
            {
                project.setUserCreatedBy (DaoContext.getUserDao ().getUniqueIdByUserId (bean.getUserCreatedBy ()));
            }
            if (StringUtils.isNotBlank (bean.getUserUpdatedBy ()))
            {
                project.setUserUpdatedBy (DaoContext.getUserDao ().getUniqueIdByUserId (bean.getUserUpdatedBy ()));
            }
            return project;
        }
        return null;
    }

}
