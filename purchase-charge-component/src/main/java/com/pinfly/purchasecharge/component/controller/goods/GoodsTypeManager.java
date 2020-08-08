package com.pinfly.purchasecharge.component.controller.goods;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import com.pinfly.purchasecharge.component.bean.BaseBean;
import com.pinfly.purchasecharge.component.bean.GoodsTypeBean;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.controller.GenericController;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsType;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/goodsType")
public class GoodsTypeManager extends GenericController <GoodsTypeBean>
{
    private static final Logger logger = Logger.getLogger (GoodsTypeManager.class);
    private String goodsTypeMessage = ComponentMessage.createMessage ("GOODS_TYPE", "GOODS_TYPE").getI18nMessageCode ();
    public static final String VIRTUAL_ALL_PARENT_ID = "0";

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "goodsTypeManagement";
    }
    
    @Override
    protected String getJsName ()
    {
        return "javascript/goodsTypeMgmtJS";
    }

    @Override
    public String checkExist (@RequestParam String goodsTypeId, @RequestParam String name, HttpServletRequest request)
    {
        if (StringUtils.isBlank (goodsTypeId))
        {
            // new
            GoodsType obj = componentContext.getQueryService ().getGoodsType (name);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long id = Long.parseLong (goodsTypeId);
            GoodsType goodsType = componentContext.getQueryService ().getGoodsType (name);
            if (null != goodsType && goodsType.getId () != id)
            {
                return "false";
            }
        }

        return "true";
    }

    @Override
    public String getAllModel (HttpServletRequest request)
    {
        List <GoodsType> goodsTypes = (List <GoodsType>) componentContext.getQueryService ().getAllGoodsType ();
        List <GoodsTypeBean> goodsTypeBeans = new ArrayList <GoodsTypeBean> ();
        if (null != goodsTypes)
        {
            for (GoodsType goodsType : goodsTypes)
            {
                if (null != goodsType && null == goodsType.getParent ())
                {
                    GoodsTypeBean goodsTypeBean = goodsType2GoodsTypeBean (goodsType);
                    goodsTypeBeans.add (goodsTypeBean);
                }
            }
        }
        JSONArray jsonObject = JSONArray.fromObject (goodsTypeBeans);
        return jsonObject.toString ();
    }

    @Override
    public void addModelWithResponse (@Valid GoodsTypeBean bean, BindingResult bindingResult, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception
    {
        response.setContentType ("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter ();
        if (bindingResult.hasErrors ())
        {
            logger.warn (bindingResult.getAllErrors ());
            writer.write (createBadRequestResult (null));
        }
        else
        {
            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                if(checkExist (bean.getId (), bean.getText (), null) == "false") 
                {
                    String message = componentContext.getMessage ("generic.message.unrepeatable.warn", new String[]
                    { componentContext.getMessage (goodsTypeMessage) + "'" + bean.getText () + "'" });
                    ar = ActionResult.notAcceptable ().withMessage (message).build ();
                    writer.write (AjaxUtils.getJsonObject (ar));
                }
                else 
                {
                    GoodsType goodsType = new GoodsType (bean.getText ());
                    String parentId = bean.getParentId ();
                    if (StringUtils.isNotBlank (parentId))
                    {
                        long pid = Long.parseLong (parentId);
                        if (0 != pid)
                        {
                            GoodsType parentGoodsType = new GoodsType ();
                            parentGoodsType.setId (pid);
                            goodsType.setParent (parentGoodsType);
                        }
                    }
                    
                    try
                    {
                        goodsType = componentContext.getPersistenceService ().addGoodsType (goodsType);
                        JSONObject goodsTypeJson = JSONObject.fromObject (goodsType);
                        writer.write (goodsTypeJson.toString ());
                        ar = createAddSuccessResult ("");
                    }
                    catch (Exception e)
                    {
                        logger.warn (e.getMessage (), e);
                        ar = createAddFailedResult (goodsTypeMessage);
                        writer.write (createAddFailedResultString (goodsTypeMessage));
                    }
                    
                    if (ActionResultStatus.OK.equals (ar.getStatus ()))
                    {
                        try
                        {
                            String logEventName = LogEventName.createEventName ("AddGoodsType", "LogEvent.AddGoodsType",
                                                                                request.getLocale ());
                            componentContext.getLogService ().log (logEventName,
                                                                   loginUser.getUid (),
                                                                   logEventName + ": "
                                                                           + LogUtil.createLogComment (goodsType));
                        }
                        catch (Exception e)
                        {
                        }
                    }
                }
            }
        }
    }

    @Override
    public void updateModelWithResponse (@Valid GoodsTypeBean bean, BindingResult bindingResult, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception
    {
        response.setContentType ("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter ();
        if (bindingResult.hasErrors ())
        {
            logger.warn (bindingResult.getAllErrors ());
            writer.print (createBadRequestResult (null));
        }
        else
        {
            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                if(checkExist (bean.getId (), bean.getText (), null) == "false") 
                {
                    String message = componentContext.getMessage ("generic.message.unrepeatable.warn", new String[]
                    { componentContext.getMessage (goodsTypeMessage) + "'" + bean.getText () + "'" });
                    ar = ActionResult.notAcceptable ().withMessage (message).build ();
                    writer.write (AjaxUtils.getJsonObject (ar));
                }
                else 
                {
                    GoodsType goodsType = new GoodsType (bean.getText ());
                    GoodsType oldGoodsType = null;
                    String id = bean.getId ();
                    if (StringUtils.isNotBlank (id))
                    {
                        goodsType.setId (Long.parseLong (id));
                    }
                    String parentId = bean.getParentId ();
                    if (StringUtils.isNotBlank (parentId) && !"0".equals (parentId))
                    {
                        GoodsType parentGoodsType = new GoodsType ();
                        parentGoodsType.setId (Long.parseLong (parentId));
                        goodsType.setParent (parentGoodsType);
                    }
                    
                    try
                    {
                        oldGoodsType = componentContext.getQueryService ().getGoodsType (goodsType.getId ());
                        goodsType = componentContext.getPersistenceService ().updateGoodsType (goodsType);
                        JSONObject goodsTypeJson = JSONObject.fromObject (goodsType);
                        writer.print (goodsTypeJson.toString ());
                        ar = createUpdateSuccessResult ("");
                    }
                    catch (Exception e)
                    {
                        logger.warn (e.getMessage (), e);
                        ar = createUpdateFailedResult (goodsTypeMessage);
                        writer.print (createUpdateFailedResultString (goodsTypeMessage));
                    }
                    
                    if (ActionResultStatus.OK.equals (ar.getStatus ()))
                    {
                        try
                        {
                            String logEventName = LogEventName.createEventName ("UpdateGoodsType",
                                                                                "LogEvent.UpdateGoodsType",
                                                                                request.getLocale ());
                            componentContext.getLogService ()
                            .log (logEventName,
                                  loginUser.getUid (),
                                  logEventName + ": "
                                          + LogUtil.createLogComment (oldGoodsType, goodsType));
                        }
                        catch (Exception e)
                        {
                        }
                    }
                }
            }
        }
    }

    @Override
    public String deleteModels (@RequestParam String ids, HttpServletRequest request)
    {
        if (null != ids && ids.trim ().length () > 0)
        {
            List <GoodsType> types = new ArrayList <GoodsType> ();
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                GoodsType type;
                for (String typeId : idArr)
                {
                    type = new GoodsType (Long.parseLong (typeId));
                    types.add (type);
                }
            }
            else
            {
                GoodsType gt = new GoodsType (Long.parseLong (ids));
                types.add (gt);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    componentContext.getPersistenceService ().deleteGoodsType (types);
                    ar = createDeleteSuccessResult (goodsTypeMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (goodsTypeMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("DeleteGoodsType",
                                                                            "LogEvent.DeleteGoodsType",
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
    protected String getModelById (String goodsTypeId, HttpServletRequest request)
    {
        logger.debug (goodsTypeId);
        if (StringUtils.isNotBlank (goodsTypeId))
        {
            try
            {
                long gid = Long.parseLong (goodsTypeId);
                GoodsType goodsType = componentContext.getQueryService ().getGoodsType (gid);
                if (null != goodsType)
                {
                    List <GoodsTypeBean> goodsTypeList = new ArrayList <GoodsTypeBean> ();
                    goodsTypeList.add (simpleGoodsType2GoodsTypeBean (goodsType));
                    JSONArray jsonObject = JSONArray.fromObject (goodsTypeList);
                    return jsonObject.toString ();
                }
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage (), e);
            }
        }
        return "";
    }

    @RequestMapping (value = "/asyncGetGoodsType", method = RequestMethod.POST)
    public @ResponseBody
    String asyncGetGoodsType (BaseBean baseBean)
    {
        List <GoodsTypeBean> goodsTypeBeans = new ArrayList <GoodsTypeBean> ();
        if (StringUtils.isBlank (baseBean.getId ()))
        {
            List <GoodsType> goodsTypes = (List <GoodsType>) componentContext.getQueryService ().getAllGoodsType ();
            if (null != goodsTypes)
            {
                for (GoodsType goodsType : goodsTypes)
                {
                    if (null != goodsType && null == goodsType.getParent ())
                    {
                        GoodsTypeBean goodsTypeBean = goodsType2GoodsTypeBean (goodsType);
                        goodsTypeBeans.add (goodsTypeBean);
                    }
                }
            }
        }
        else
        {
            List <GoodsType> goodsTypeList = componentContext.getQueryService ()
                                                             .getGoodsTypeByParent (Long.parseLong (baseBean.getId ()));
            if (CollectionUtils.isNotEmpty (goodsTypeList))
            {
                for (GoodsType goodsType : goodsTypeList)
                {
                    goodsTypeBeans.add (goodsType2GoodsTypeBean (goodsType));
                }
            }
        }

        JSONArray jsonObject = JSONArray.fromObject (goodsTypeBeans);
        return jsonObject.toString ();
    }

    @RequestMapping (value = "/getAllGoodsType", method = RequestMethod.POST)
    public @ResponseBody
    String getAllGoodsType (BaseBean baseBean)
    {
        List <GoodsTypeBean> goodsTypeBeans = new ArrayList <GoodsTypeBean> ();
        if (StringUtils.isBlank (baseBean.getId ()))
        {
            GoodsTypeBean virtualAllBean = generateVirtualAllType ();
            List <GoodsType> goodsTypes = (List <GoodsType>) componentContext.getQueryService ().getAllGoodsType ();
            if (null != goodsTypes)
            {
                List <GoodsTypeBean> virtualGoodsTypeBeans = new ArrayList <GoodsTypeBean> ();
                for (GoodsType goodsType : goodsTypes)
                {
                    if (null != goodsType && null == goodsType.getParent ())
                    {
                        GoodsTypeBean goodsTypeBean = goodsType2GoodsTypeBean (goodsType);
                        goodsTypeBean.setParentId (VIRTUAL_ALL_PARENT_ID);
                        virtualGoodsTypeBeans.add (goodsTypeBean);
                    }
                }
                virtualAllBean.setChildren (virtualGoodsTypeBeans);
            }
            goodsTypeBeans.add (virtualAllBean);
        }
        else
        {
            List <GoodsType> goodsTypeList = componentContext.getQueryService ()
                                                             .getGoodsTypeByParent (Long.parseLong (baseBean.getId ()));
            if (CollectionUtils.isNotEmpty (goodsTypeList))
            {
                for (GoodsType goodsType : goodsTypeList)
                {
                    goodsTypeBeans.add (goodsType2GoodsTypeBean (goodsType));
                }
            }
        }

        JSONArray jsonObject = JSONArray.fromObject (goodsTypeBeans);
        return jsonObject.toString ();
    }

    private GoodsTypeBean goodsType2GoodsTypeBean (GoodsType goodsType)
    {
        GoodsTypeBean goodsTypeBean = new GoodsTypeBean ();
        if (null != goodsType.getParent ())
        {
            goodsTypeBean.setId (goodsType.getId () + "");
            goodsTypeBean.setText (goodsType.getName ());
            goodsTypeBean.setState ("open");
            goodsTypeBean.setGoodsAmount (componentContext.getQueryService ().countGoodsByType (goodsType.getId ()));
            List <GoodsTypeBean> children = new ArrayList <GoodsTypeBean> ();
            List <GoodsType> list = componentContext.getQueryService ().getGoodsTypeByParent (goodsType.getId ());
            if (CollectionUtils.isNotEmpty (list))
            {
                for (GoodsType type : list)
                {
                    children.add (goodsType2GoodsTypeBean (type));
                }
            }
            goodsTypeBean.setChildren (children);
        }
        else
        {
            goodsTypeBean.setId (goodsType.getId () + "");
            goodsTypeBean.setText (goodsType.getName ());
            goodsTypeBean.setState ("open");
            goodsTypeBean.setGoodsAmount (componentContext.getQueryService ().countGoodsByType (goodsType.getId ()));
            List <GoodsTypeBean> children = new ArrayList <GoodsTypeBean> ();
            List <GoodsType> list = componentContext.getQueryService ().getGoodsTypeByParent (goodsType.getId ());
            if (CollectionUtils.isNotEmpty (list))
            {
                for (GoodsType type : list)
                {
                    children.add (goodsType2GoodsTypeBean (type));
                }
            }
            goodsTypeBean.setChildren (children);
        }
        return goodsTypeBean;
    }

    public static GoodsTypeBean simpleGoodsType2GoodsTypeBean (GoodsType goodsType)
    {
        if (null != goodsType)
        {
            GoodsTypeBean bean = new GoodsTypeBean ();
            bean.setId (goodsType.getId () + "");
            bean.setText (goodsType.getName ());
            if (null != goodsType.getParent ())
            {
                bean.setParentId (goodsType.getParent ().getId () + "");
            }
            return bean;
        }
        return null;
    }
    
    private GoodsTypeBean generateVirtualAllType () 
    {
        GoodsTypeBean virtualAllBean = new GoodsTypeBean ();
        virtualAllBean.setText (componentContext.getMessage ("all"));
        virtualAllBean.setId (VIRTUAL_ALL_PARENT_ID);
        virtualAllBean.setState ("open");
        virtualAllBean.setGoodsAmount (componentContext.getQueryService ().countGoods ());
        return virtualAllBean;
    }

    @SuppressWarnings ("unused")
    private void deleteChildrenType (long id)
    {
        List <GoodsType> childGoodsTypes = componentContext.getQueryService ().getGoodsTypeByParent (id);
        componentContext.getPersistenceService ().deleteGoodsType (childGoodsTypes);
        for (GoodsType type : childGoodsTypes)
        {
            deleteChildrenType (type.getId ());
        }
    }

    @Override
    public void afterPropertiesSet () throws Exception
    {
    }

}
