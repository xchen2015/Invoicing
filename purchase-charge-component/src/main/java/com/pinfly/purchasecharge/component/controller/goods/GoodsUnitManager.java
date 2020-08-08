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

import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.ActionResult.ActionResultStatus;
import com.pinfly.purchasecharge.component.bean.GoodsUnitBean;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.controller.GenericController;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.BeanConvertUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsUnit;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/goodsUnit")
public class GoodsUnitManager extends GenericController <GoodsUnitBean>
{
    private static final Logger logger = Logger.getLogger (GoodsUnitManager.class);
    private String goodsUnitMessage = ComponentMessage.createMessage ("GOODS_UNIT", "GOODS_UNIT").getI18nMessageCode ();

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "goodsUnitManagement";
    }
    
    @Override
    protected String getJsName ()
    {
        return "javascript/goodsUnitMgmtJS";
    }

    @Override
    public String checkExist (@RequestParam String goodsUnitId, @RequestParam String name, HttpServletRequest request)
    {
        if (StringUtils.isBlank (goodsUnitId))
        {
            // new
            GoodsUnit obj = componentContext.getQueryService ().getGoodsUnit (name);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long id = Long.parseLong (goodsUnitId);
            GoodsUnit goodsUnit = componentContext.getQueryService ().getGoodsUnit (name);
            if (null != goodsUnit && goodsUnit.getId () != id)
            {
                return "false";
            }
        }

        return "true";
    }

    @Override
    public String getAllModel (HttpServletRequest request)
    {
        List <GoodsUnit> units = (List <GoodsUnit>) componentContext.getQueryService ().getAllGoodsUnit ();

        JSONArray jsonObject = JSONArray.fromObject (units);
        return jsonObject.toString ();
    }

    @Override
    public String addModel (GoodsUnitBean bean, BindingResult bindingResult, HttpServletRequest request)
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
                GoodsUnit unit = BeanConvertUtils.goodsUnitBean2GoodsUnit (bean);
                try
                {
                    unit = componentContext.getPersistenceService ().addGoodsUnit (unit);
                    ar = createAddSuccessResult (goodsUnitMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createAddFailedResult (goodsUnitMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("AddGoodsUnit", "LogEvent.AddGoodsUnit",
                                                                            request.getLocale ());
                        componentContext.getLogService ().log (logEventName,
                                                               loginUser.getUid (),
                                                               logEventName + ": "
                                                                       + LogUtil.createLogComment (unit));
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
    public String updateModel (GoodsUnitBean bean, BindingResult bindingResult, HttpServletRequest request)
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
                GoodsUnit unit = BeanConvertUtils.goodsUnitBean2GoodsUnit (bean);
                GoodsUnit oldUnit = componentContext.getQueryService ().getGoodsUnit (unit.getId ());
                try
                {
                    unit = componentContext.getPersistenceService ().updateGoodsUnit (unit);
                    ar = createUpdateSuccessResult (goodsUnitMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createUpdateFailedResult (goodsUnitMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("UpdateGoodsUnit",
                                                                            "LogEvent.UpdateGoodsUnit",
                                                                            request.getLocale ());
                        componentContext.getLogService ().log (logEventName,
                                                               loginUser.getUid (),
                                                               logEventName
                                                                       + ": "
                                                                       + LogUtil.createLogComment (oldUnit,
                                                                                                            unit));
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
            List <GoodsUnit> goodsUnits = new ArrayList <GoodsUnit> ();
            GoodsUnit goodsUnit;
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    goodsUnit = new GoodsUnit ();
                    goodsUnit.setId (Long.parseLong (typeId));
                    goodsUnits.add (goodsUnit);
                }
            }
            else
            {
                goodsUnit = new GoodsUnit ();
                goodsUnit.setId (Long.parseLong (ids));
                goodsUnits.add (goodsUnit);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    componentContext.getPersistenceService ().deleteGoodsUnit (goodsUnits);
                    ar = createDeleteSuccessResult (goodsUnitMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (goodsUnitMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("DeleteGoodsUnit",
                                                                            "LogEvent.DeleteGoodsUnit",
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
