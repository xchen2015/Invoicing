package com.pinfly.purchasecharge.component.controller.auditlog;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.DataGridRequestForm;
import com.pinfly.purchasecharge.component.bean.GenericPagingResult;
import com.pinfly.purchasecharge.component.bean.SearchRequestForm;
import com.pinfly.purchasecharge.component.bean.ActionResult.ActionResultStatus;
import com.pinfly.purchasecharge.component.bean.auditlog.LogBean;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventBean;
import com.pinfly.purchasecharge.component.controller.GenericController;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.BeanConvertUtils;
import com.pinfly.purchasecharge.core.model.persistence.auditlog.Log;
import com.pinfly.purchasecharge.core.model.persistence.auditlog.LogEvent;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.User;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.core.util.PurchaseChargeConstants;

@Controller
@RequestMapping ("/log")
public class LogController extends GenericController <LogBean>
{
    private static final Logger LOGGER = Logger.getLogger (LogController.class);

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "logManagement";
    }

    @Override
    public String getModelBySearchForm (DataGridRequestForm dataGridRequestForm, SearchRequestForm searchRequestForm,
                                        HttpServletRequest request)
    {
        LOGGER.debug (dataGridRequestForm);
        int page = dataGridRequestForm.getPage () - 1;
        int size = dataGridRequestForm.getRows ();
        String sortField = parseSortField (dataGridRequestForm.getSort ());
        String order = dataGridRequestForm.getOrder ();
        Pageable pageable = new PageRequest (page, size, new Sort ("asc".equalsIgnoreCase (order) ? Direction.ASC
                                                                                                 : Direction.DESC,
                                                                   sortField));
        Page <Log> logPage = componentContext.getLogService ()
                                             .getPagedLog (pageable,
                                                           BeanConvertUtils.searchRequestForm2LogSearchForm (searchRequestForm));

        List <LogBean> logBeans = new ArrayList <LogBean> ();
        List <Log> orderList = logPage.getContent ();
        if (!CollectionUtils.isEmpty (orderList))
        {
            for (Log log : orderList)
            {
                logBeans.add (log2LogBean (log));
            }
        }
        long total = logPage.getTotalElements ();
        GenericPagingResult <LogBean> logPagingResult = new GenericPagingResult <LogBean> ();
        logPagingResult.setRows (logBeans);
        logPagingResult.setTotal (total);

        JSONObject jsonObject = JSONObject.fromObject (logPagingResult);
        return jsonObject.toString ();
    }

    @Override
    public String deleteModels (@RequestParam String ids, HttpServletRequest request)
    {
        if (StringUtils.isNotBlank (ids))
        {
            List <Log> logs = new ArrayList <Log> ();
            Log log;
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    log = new Log ();
                    log.setId (Long.parseLong (typeId));
                    logs.add (log);
                }
            }
            else
            {
                log = new Log ();
                log.setId (Long.parseLong (ids));
                logs.add (log);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            try
            {
                // logService.delete (logs);
                // ar = createDeleteSuccessResult ();
            }
            catch (Exception e)
            {
                LOGGER.warn (e.getMessage (), e);
                // ar = createDeleteFailedResult ();
            }
            return AjaxUtils.getJsonObject (ar);
        }
        else
        {
            LOGGER.warn (ActionResultStatus.BAD_REQUEST);
            return createBadRequestResult (null);
        }
    }

    private String parseSortField (final String sortField)
    {
        String sortFieldAfterParse = "dateCreate";
        if (!StringUtils.isBlank (sortField))
        {
            if (PurchaseChargeConstants.LOG_DATE_TIME.equalsIgnoreCase (sortField))
            {
                sortFieldAfterParse = "dateCreate";
            }
        }
        return sortFieldAfterParse;
    }

    public LogBean log2LogBean (Log log)
    {
        if (null != log)
        {
            LogBean logBean = new LogBean ();
            BeanUtils.copyProperties (log, logBean, new String[]
            { "id" });
            logBean.setId (log.getId () + "");
            User user = componentContext.getQueryService ().getUser (log.getUserCreate ());
            if (null != user)
            {
                logBean.setOperator (user.getUserId ());
            }
            LogEvent logEvent = log.getEvent ();
            if (null != logEvent)
            {
                LogEventBean eventBean = new LogEventBean ();
                eventBean.setId (logEvent.getId () + "");
                eventBean.setName (logEvent.getName ());
                eventBean.setEnabled (logEvent.isEnabled ());
                logBean.setEventBean (eventBean);
            }
            logBean.setDateTime (DateUtils.date2String (log.getDateCreate (), DateUtils.DATE_TIME_PATTERN));
            return logBean;
        }
        return null;
    }

}
