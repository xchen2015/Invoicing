package com.pinfly.purchasecharge.component.controller.goods;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.ActionResult.ActionResultStatus;
import com.pinfly.purchasecharge.component.bean.GoodsDepositoryBean;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.controller.GenericController;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.BeanConvertUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsDepository;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/goodsDepository")
public class GoodsDepositoryManager extends GenericController <GoodsDepositoryBean>
{
    private static final Logger logger = Logger.getLogger (GoodsDepositoryManager.class);

    private String goodsDepositoryMessage = ComponentMessage.createMessage ("GOODS_DEPOSITORY", "GOODS_DEPOSITORY")
                                                            .getI18nMessageCode ();

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "goodsDepositoryMgmt";
    }

    @Override
    protected String getJsName ()
    {
        return "javascript/goodsDepositoryMgmtJS";
    }

    public @ResponseBody
    String checkExist (@RequestParam String goodsDepositoryId, @RequestParam String name, HttpServletRequest request)
    {
        if (StringUtils.isBlank (goodsDepositoryId))
        {
            // new
            GoodsDepository obj = componentContext.getQueryService ().getGoodsDepository (name);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long id = Long.parseLong (goodsDepositoryId);
            GoodsDepository goodsDepository = componentContext.getQueryService ().getGoodsDepository (name);
            if (null != goodsDepository && goodsDepository.getId () != id)
            {
                return "false";
            }
        }

        return "true";
    }

    public @ResponseBody
    String getAllModel (HttpServletRequest request)
    {
        List <GoodsDepository> goodsDepositorys = (List <GoodsDepository>) componentContext.getQueryService ()
                                                                                           .getAllGoodsDepository ();
        List <GoodsDepositoryBean> goodsDepositoryBeans = new ArrayList <GoodsDepositoryBean> ();
        if (null != goodsDepositorys)
        {
            for (GoodsDepository goodsDepository : goodsDepositorys)
            {
                goodsDepositoryBeans.add (BeanConvertUtils.goodsDepository2GoodsDepositoryBean (goodsDepository));
            }
        }
        JSONArray jsonObject = JSONArray.fromObject (goodsDepositoryBeans);
        return jsonObject.toString ();
    }

    public @ResponseBody
    String addModel (GoodsDepositoryBean goodsDepositoryBean, BindingResult bindingResult, HttpServletRequest request)
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
                GoodsDepository goodsDepository = BeanConvertUtils.goodsDepositoryBean2GoodsDepository (goodsDepositoryBean);
                try
                {
                    goodsDepository = componentContext.getPersistenceService ().addGoodsDepository (goodsDepository);
                    ar = createAddSuccessResult (goodsDepositoryMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createAddFailedResult (goodsDepositoryMessage);
                }

                if (ActionResult.ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("AddGoodsDepository",
                                                                            "LogEvent.AddGoodsDepository",
                                                                            request.getLocale ());
                        componentContext.getLogService ()
                                        .log (logEventName, loginUser.getUid (),
                                              logEventName + ": " + LogUtil.createLogComment (goodsDepository));
                    }
                    catch (Exception e)
                    {
                    }
                }
            }

            return AjaxUtils.getJsonObject (ar);
        }
    }

    public @ResponseBody
    String updateModel (GoodsDepositoryBean goodsDepositoryBean, BindingResult bindingResult, HttpServletRequest request)
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
                GoodsDepository goodsDepository = BeanConvertUtils.goodsDepositoryBean2GoodsDepository (goodsDepositoryBean);
                GoodsDepository oldGoodsDepository = componentContext.getQueryService ().getGoodsDepository (goodsDepository.getId ());
                try
                {
                    goodsDepository = componentContext.getPersistenceService ().updateGoodsDepository (goodsDepository);
                    ar = createUpdateSuccessResult (goodsDepositoryMessage);
                }
                catch (PcServiceException e) 
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createUpdateFailedResult (goodsDepositoryMessage);
                }

                if (ActionResult.ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("UpdateGoodsDepository",
                                                                            "LogEvent.UpdateGoodsDepository",
                                                                            request.getLocale ());
                        componentContext.getLogService ()
                                        .log (logEventName,
                                              loginUser.getUid (),
                                              logEventName
                                                      + ": "
                                                      + LogUtil.createLogComment (oldGoodsDepository,
                                                                                           goodsDepository));
                    }
                    catch (Exception e)
                    {
                    }
                }
            }

            return AjaxUtils.getJsonObject (ar);
        }
    }

    public @ResponseBody
    String deleteModels (@RequestParam String ids, HttpServletRequest request)
    {
        if (null != ids && ids.trim ().length () > 0)
        {
            List <GoodsDepository> depositories = new ArrayList <GoodsDepository> ();
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                GoodsDepository depository;
                for (String typeId : idArr)
                {
                    depository = new GoodsDepository ();
                    depository.setId (Long.parseLong (typeId));
                    depositories.add (depository);
                }

            }
            else
            {
                GoodsDepository gd = new GoodsDepository ();
                gd.setId (Long.parseLong (ids));
                depositories.add (gd);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    componentContext.getPersistenceService ().deleteGoodsDepository (depositories);
                    ar = createDeleteSuccessResult (goodsDepositoryMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (goodsDepositoryMessage);
                }

                if (ActionResult.ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("DeleteGoodsDepository",
                                                                            "LogEvent.DeleteGoodsDepository",
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
