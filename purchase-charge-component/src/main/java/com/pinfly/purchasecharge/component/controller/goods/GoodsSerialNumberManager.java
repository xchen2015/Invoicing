package com.pinfly.purchasecharge.component.controller.goods;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.DataGridRequestForm;
import com.pinfly.purchasecharge.component.bean.GoodsSerialNumberBean;
import com.pinfly.purchasecharge.component.bean.SearchRequestForm;
import com.pinfly.purchasecharge.component.bean.ActionResult.ActionResultStatus;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.controller.GenericController;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.BeanConvertUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsSerialNumber;
import com.pinfly.purchasecharge.dal.DaoContext;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/goodsSerialNumber")
public class GoodsSerialNumberManager extends GenericController <GoodsSerialNumberBean>
{
    private static final Logger logger = Logger.getLogger (GoodsSerialNumberManager.class);
    private String goodsSerialNumberMessage = ComponentMessage.createMessage ("GOODS_SERIAL_NUMBER",
                                                                              "GOODS_SERIAL_NUMBER")
                                                              .getI18nMessageCode ();

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "goodsSerialNumberManagement";
    }

    @Override
    protected String checkExist (@RequestParam String serialNumberId, @RequestParam String serialNumber,
                                 HttpServletRequest request)
    {
        if (StringUtils.isBlank (serialNumberId))
        {
            // new
            GoodsSerialNumber obj = DaoContext.getGoodsSerialNumberDao ().findBySerialNumber (serialNumber);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long id = Long.parseLong (serialNumberId);
            GoodsSerialNumber obj = DaoContext.getGoodsSerialNumberDao ().findBySerialNumber (serialNumber);
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
        long gid = 0;
        if (null != searchRequestForm && StringUtils.isNotBlank (searchRequestForm.getGoodsId ()))
        {
            try
            {
                gid = Long.parseLong (searchRequestForm.getGoodsId ());
            }
            catch (Exception e)
            {
            }
        }
        List <GoodsSerialNumber> goodsSerialNumbers = DaoContext.getGoodsSerialNumberDao ().findByGoods (gid);
        List <GoodsSerialNumberBean> serialNumberBeans = new ArrayList <GoodsSerialNumberBean> ();
        if (CollectionUtils.isNotEmpty (goodsSerialNumbers))
        {
            for (GoodsSerialNumber serialNumber : goodsSerialNumbers)
            {
                serialNumberBeans.add (BeanConvertUtils.convert (serialNumber));
            }
        }

        JSONArray jsonArr = JSONArray.fromObject (serialNumberBeans);
        return jsonArr.toString ();
    }

    @Override
    protected String addModel (@Valid GoodsSerialNumberBean bean, BindingResult bindingResult,
                               HttpServletRequest request)
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
                GoodsSerialNumber goodsSerialNumber = BeanConvertUtils.convert (bean);
                try
                {
                    goodsSerialNumber = componentContext.getPersistenceService ()
                                                        .addGoodsSerialNumber (goodsSerialNumber);
                    ar = createAddSuccessResult (goodsSerialNumberMessage);
                }
                catch (Exception e)
                {
                    ar = createAddFailedResult (goodsSerialNumberMessage);
                    logger.warn (e.getMessage (), e);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("AddGoodsSerialNumber",
                                                                            "LogEvent.AddGoodsSerialNumber",
                                                                            request.getLocale ());
                        componentContext.getLogService ()
                                        .log (logEventName, loginUser.getUid (),
                                              logEventName + ": " + LogUtil.createLogComment (goodsSerialNumber));
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
    protected String updateModel (@Valid GoodsSerialNumberBean bean, BindingResult bindingResult,
                                  HttpServletRequest request)
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
            if (null != loginUser && StringUtils.isNotBlank (bean.getId ()))
            {
                GoodsSerialNumber goodsSerialNumber = BeanConvertUtils.convert (bean);
                GoodsSerialNumber oldGoodsSerialNumber = DaoContext.getGoodsSerialNumberDao ()
                                                                   .findOne (goodsSerialNumber.getId ());
                try
                {
                    goodsSerialNumber = componentContext.getPersistenceService ()
                                                        .updateGoodsSerialNumber (goodsSerialNumber);
                    ar = createUpdateSuccessResult (goodsSerialNumberMessage);
                }
                catch (Exception e)
                {
                    ar = createUpdateFailedResult (goodsSerialNumberMessage);
                    logger.warn (e.getMessage (), e);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("UpdateGoodsSerialNumber",
                                                                            "LogEvent.UpdateGoodsSerialNumber",
                                                                            request.getLocale ());
                        componentContext.getLogService ()
                                        .log (logEventName,
                                              loginUser.getUid (),
                                              logEventName
                                                      + ": "
                                                      + LogUtil.createLogComment (oldGoodsSerialNumber,
                                                                                           goodsSerialNumber));
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
            List <GoodsSerialNumber> goodsSerialNumbers = new ArrayList <GoodsSerialNumber> ();
            GoodsSerialNumber goodsSerialNumber;
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    goodsSerialNumber = new GoodsSerialNumber ();
                    goodsSerialNumber.setId (Long.parseLong (typeId));
                    goodsSerialNumbers.add (goodsSerialNumber);
                }
            }
            else
            {
                goodsSerialNumber = new GoodsSerialNumber ();
                goodsSerialNumber.setId (Long.parseLong (ids));
                goodsSerialNumbers.add (goodsSerialNumber);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    componentContext.getPersistenceService ().deleteGoodsSerialNumber (goodsSerialNumbers);
                    ar = createDeleteSuccessResult (goodsSerialNumberMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (goodsSerialNumberMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("DeleteGoodsSerialNumber",
                                                                            "LogEvent.DeleteGoodsSerialNumber",
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
