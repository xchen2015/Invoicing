package com.pinfly.purchasecharge.component.controller.goods;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.DataGridRequestForm;
import com.pinfly.purchasecharge.component.bean.GoodsIssueBean;
import com.pinfly.purchasecharge.component.bean.SearchRequestForm;
import com.pinfly.purchasecharge.component.bean.ActionResult.ActionResultStatus;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.controller.GenericController;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.BeanConvertUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.GoodsIssueSearchForm;
import com.pinfly.purchasecharge.core.model.GoodsIssueStatusCode;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsIssue;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.dal.DaoContext;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/goodsIssue")
public class GoodsIssueManager extends GenericController <GoodsIssueBean>
{
    private static final Logger logger = Logger.getLogger (GoodsIssueManager.class);
    private String goodsIssueMessage = ComponentMessage.createMessage ("GOODS_ISSUE", "GOODS_ISSUE")
                                                       .getI18nMessageCode ();

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "goodsIssueManagement";
    }

    @Override
    protected String getModelBySearchForm (DataGridRequestForm dataGridRequestForm,
                                           SearchRequestForm searchRequestForm, HttpServletRequest request)
    {
        List <GoodsIssue> goodsIssues = new ArrayList <GoodsIssue> ();
        GoodsIssueSearchForm issueSearchForm = new GoodsIssueSearchForm ();
        if (null != searchRequestForm)
        {
            long goodsId = 0;
            long customerId = 0;
            try
            {
                if (StringUtils.isNotBlank (searchRequestForm.getGoodsId ()))
                {
                    goodsId = Long.parseLong (searchRequestForm.getGoodsId ());
                }
                if (StringUtils.isNotBlank (searchRequestForm.getCustomerId ()))
                {
                    customerId = Long.parseLong (searchRequestForm.getCustomerId ());
                }
                issueSearchForm.setGoodsId (goodsId);
                issueSearchForm.setCustomerId (customerId);
                if (null != searchRequestForm.getIssueStatusCode ())
                {
                    issueSearchForm.setIssueStatusCode (searchRequestForm.getIssueStatusCode ());
                }
                issueSearchForm.setGoodsSerialNumber (searchRequestForm.getGoodsSerialNumber ());

                goodsIssues = DaoContext.getGoodsIssueDao ().findBySearchForm (issueSearchForm);
            }
            catch (Exception e)
            {
            }
        }

        List <GoodsIssueBean> goodsIssueBeans = new ArrayList <GoodsIssueBean> ();
        if (CollectionUtils.isNotEmpty (goodsIssues))
        {
            for (GoodsIssue goodsIssue : goodsIssues)
            {
                goodsIssueBeans.add (BeanConvertUtils.convert (goodsIssue));
            }
        }

        JSONArray jsonArr = JSONArray.fromObject (goodsIssueBeans);
        return jsonArr.toString ();
    }

    @Override
    protected String checkExist (@RequestParam String issueId, @RequestParam String goodsSerial,
                                 HttpServletRequest request)
    {
        if (StringUtils.isBlank (issueId))
        {
            // new
            GoodsIssue obj = DaoContext.getGoodsIssueDao ().findBySerialNumber (goodsSerial);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long id = Long.parseLong (issueId);
            GoodsIssue obj = DaoContext.getGoodsIssueDao ().findBySerialNumber (goodsSerial);
            if (null != obj && obj.getId () != id)
            {
                return "false";
            }
        }

        return "true";
    }

    @Override
    protected String addModel (@Valid GoodsIssueBean bean, BindingResult bindingResult, HttpServletRequest request)
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
                bean.setStatusCode (GoodsIssueStatusCode.NEW);
                bean.setUpdateUser (BeanConvertUtils.convert (loginUser));
                bean.setCreateDate (DateUtils.date2String (new Date (), DateUtils.DATE_PATTERN));
                bean.setUpdateDate (DateUtils.date2String (new Date (), DateUtils.DATE_PATTERN));
                GoodsIssue goodsIssue = BeanConvertUtils.convert (bean);

                try
                {
                    goodsIssue = componentContext.getPersistenceService ().addGoodsIssue (goodsIssue);
                    ar = createAddSuccessResult (goodsIssueMessage);
                }
                catch (Exception e)
                {
                    ar = createAddFailedResult (goodsIssueMessage);
                    logger.warn (e.getMessage (), e);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("AddGoodsIssue", "LogEvent.AddGoodsIssue",
                                                                            request.getLocale ());
                        componentContext.getLogService ().log (logEventName,
                                                               loginUser.getUid (),
                                                               logEventName + ": "
                                                                       + LogUtil.createLogComment (goodsIssue));
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
    protected String updateModel (@Valid GoodsIssueBean bean, BindingResult bindingResult, HttpServletRequest request)
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
            if (null != loginUser && null != bean && StringUtils.isNotBlank (bean.getId ()))
            {
                bean.setUpdateUser (BeanConvertUtils.convert (loginUser));
                bean.setUpdateDate (DateUtils.date2String (new Date (), DateUtils.DATE_PATTERN));
                GoodsIssue goodsIssue = BeanConvertUtils.convert (bean);
                GoodsIssue oldGoodsIssue = DaoContext.getGoodsIssueDao ().findOne (goodsIssue.getId ());

                try
                {
                    goodsIssue = componentContext.getPersistenceService ().updateGoodsIssue (goodsIssue);
                    ar = createUpdateSuccessResult (goodsIssueMessage);
                }
                catch (Exception e)
                {
                    ar = createUpdateFailedResult (goodsIssueMessage);
                    logger.warn (e.getMessage (), e);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("UpdateGoodsIssue",
                                                                            "LogEvent.UpdateGoodsIssue",
                                                                            request.getLocale ());
                        componentContext.getLogService ()
                                        .log (logEventName,
                                              loginUser.getUid (),
                                              logEventName + ": "
                                                      + LogUtil.createLogComment (oldGoodsIssue, goodsIssue));
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
            List <GoodsIssue> goodsIssues = new ArrayList <GoodsIssue> ();
            GoodsIssue goodsIssue;
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    goodsIssue = new GoodsIssue ();
                    goodsIssue.setId (Long.parseLong (typeId));
                    goodsIssues.add (goodsIssue);
                }
            }
            else
            {
                goodsIssue = new GoodsIssue ();
                goodsIssue.setId (Long.parseLong (ids));
                goodsIssues.add (goodsIssue);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    componentContext.getPersistenceService ().deleteGoodsIssue (goodsIssues);
                    ar = createDeleteSuccessResult (goodsIssueMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (goodsIssueMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("DeleteGoodsIssue",
                                                                            "LogEvent.DeleteGoodsIssue",
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
