package com.pinfly.purchasecharge.component.controller.goods;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.ActionResult.ActionResultStatus;
import com.pinfly.purchasecharge.component.bean.DataGridRequestForm;
import com.pinfly.purchasecharge.component.bean.GoodsBean;
import com.pinfly.purchasecharge.component.bean.GoodsStorageBean;
import com.pinfly.purchasecharge.component.bean.GoodsStorageTransferBean;
import com.pinfly.purchasecharge.component.bean.GoodsTypeBean;
import com.pinfly.purchasecharge.component.bean.GoodsUnitBean;
import com.pinfly.purchasecharge.component.bean.SearchRequestForm;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.controller.GenericController;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.BeanConvertUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.GoodsStorageTransferData;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.persistence.goods.Goods;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsDepository;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsStorage;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsStorageCheck;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsStoragePriceRevise;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsStorageTransfer;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.User;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.dal.DaoContext;
import com.pinfly.purchasecharge.service.aspect.ServiceAspect;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/goodsStorage")
public class GoodsStorageManager extends GenericController <GoodsStorageTransferBean>
{
    private static final Logger logger = Logger.getLogger (GoodsStorageManager.class);
    private String goodsStorageTransferMessage = ComponentMessage.createMessage ("GOODS_STORAGE_TRANSFER",
                                                                                 "GOODS_STORAGE_TRANSFER")
                                                                 .getI18nMessageCode ();
    private String goodsStorageCheckMessage = ComponentMessage.createMessage ("GOODS_STORAGE_CHECK",
                                                                              "GOODS_STORAGE_CHECK")
                                                              .getI18nMessageCode ();
    private String goodsStoragePriceReviseMessage = ComponentMessage.createMessage ("GOODS_STORAGE_PRICE_REVISE",
                                                                               "GOODS_STORAGE_PRICE_REVISE")
                                                               .getI18nMessageCode ();
    public static final String autoShowStorageWarning = "autoShowStorageWarning";
    private List <GoodsBean> goodsStorageWarns = new ArrayList <GoodsBean> ();
    
    @Autowired
    ServiceAspect serviceAspect;

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "goodsStorageTransfer";
    }

    @Override
    protected String getJsName ()
    {
        return "javascript/goodsStorageTransferJS";
    }

    @RequestMapping (value = "/getCheckStorageViewName")
    protected String getCheckStorageViewName ()
    {
        return "goodsStorageCheck";
    }

    @RequestMapping (value = "/getCheckStorageJsName")
    protected String getCheckStorageJsName ()
    {
        return "javascript/goodsStorageCheckJS";
    }
    
    @RequestMapping (value = "/getWarnStorageViewName")
    protected String getWarnStorageViewName (HttpServletRequest request)
    {
        getStorageWarn ();
        if(null != request.getSession (false)) 
        {
            Object obj = request.getSession (false).getAttribute (autoShowStorageWarning);
            if(null != obj) 
            {
                String autoShowFlag = (String)obj;
                if(!"0".equals (autoShowFlag)) 
                {
                    if(CollectionUtils.isNotEmpty (goodsStorageWarns)) 
                    {
                        request.getSession (false).setAttribute (autoShowStorageWarning, "1");
                    }
                }
            }
            else 
            {
                if(CollectionUtils.isNotEmpty (goodsStorageWarns)) 
                {
                    request.getSession (false).setAttribute (autoShowStorageWarning, "1");
                }
            }
        }
        return "goodsStorageWarn";
    }
    
    @RequestMapping (value = "/getWarnStorageJsName")
    protected String getWarnStorageJsName ()
    {
        return "javascript/goodsStorageWarnJS";
    }

    @RequestMapping (value = "/getPriceReviseViewName")
    protected String getPriceReviseViewName ()
    {
        return "goodsStoragePriceRevise";
    }

    @RequestMapping (value = "/getPriceReviseJsName")
    protected String getPriceReviseJsName ()
    {
        return "javascript/goodsStoragePriceReviseJS";
    }

    @RequestMapping (value = "/getStorageStatisticViewName")
    protected String getStorageStatisticViewName ()
    {
        return "goodsStorageQuery";
    }

    @Override
    protected String getCssName ()
    {
        // return "stylesheet/goodsMgmtCSS";
        return null;
    }

    @Override
    protected String getModelBySearchForm (DataGridRequestForm dataGridRequestForm,
                                           SearchRequestForm searchRequestForm, HttpServletRequest request)
    {
        long goodsId = 0;
        long goodsDepository = 0;
        List <GoodsStorageTransfer> list = null;
        try
        {
            if (StringUtils.isNotBlank (searchRequestForm.getGoodsId ()))
            {
                goodsId = Long.parseLong (searchRequestForm.getGoodsId ());
            }
            if (StringUtils.isNotBlank (searchRequestForm.getGoodsDepository ()))
            {
                goodsDepository = Long.parseLong (searchRequestForm.getGoodsDepository ());
            }
            list = DaoContext.getGoodsStorageTransferDao ().findBy (goodsId, goodsDepository);
        }
        catch (Exception e)
        {
            logger.warn (e.getMessage ());
        }

        List <GoodsStorageTransferBean> list2 = new ArrayList <GoodsStorageTransferBean> ();
        if (CollectionUtils.isNotEmpty (list))
        {
            for (GoodsStorageTransfer storageTransfer : list)
            {
                GoodsStorageTransferBean transferBean = new GoodsStorageTransferBean ();
                transferBean.setId (storageTransfer.getId () + "");
                transferBean.setBid (storageTransfer.getBid ());
                transferBean.setGoodsBean (BeanConvertUtils.goods2GoodsBean (storageTransfer.getGoods ()));
                transferBean.setFromDepositoryBean (BeanConvertUtils.goodsDepository2GoodsDepositoryBean (storageTransfer.getDepository ()));
                transferBean.setFromDepositoryAmount (storageTransfer.getAmountBeforeTransfer ());
                transferBean.setToDepositoryAmount (storageTransfer.getAmountAfterTransfer ());
                transferBean.setTransferAmount (storageTransfer.getAmountAfterTransfer () - storageTransfer.getAmountBeforeTransfer ());
                transferBean.setComment (storageTransfer.getComment ());
                transferBean.setDateCreated (DateUtils.date2String (storageTransfer.getDateCreated (),
                                                                   DateUtils.DATE_TIME_NO_SECOND_PATTERN));
                User user = DaoContext.getUserDao ().findOne (storageTransfer.getUserCreatedBy ());
                if (null != user)
                {
                    transferBean.setUserCreated (user.getUserId ());
                }
                list2.add (transferBean);
            }
        }

        JSONArray json = JSONArray.fromObject (list2);
        return json.toString ();
    }

    @Override
    public String addModel (GoodsStorageTransferBean bean, BindingResult bindingResult, HttpServletRequest request)
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
            if (loginUser != null)
            {
                GoodsStorageTransferData storageTransfer = null;
                try
                {
                    storageTransfer = new GoodsStorageTransferData ();
                    if (null != bean.getGoodsBean () && StringUtils.isNotBlank (bean.getGoodsBean ().getId ()))
                    {
                        Goods goods = new Goods ();
                        goods.setId (Long.parseLong (bean.getGoodsBean ().getId ()));
                        storageTransfer.setGoods (goods);
                    }
                    if (null != bean.getFromDepositoryBean ()
                        && StringUtils.isNotBlank (bean.getFromDepositoryBean ().getId ()))
                    {
                        GoodsDepository fromDepo = new GoodsDepository ();
                        fromDepo.setId (Long.parseLong (bean.getFromDepositoryBean ().getId ()));
                        storageTransfer.setFromDepository (fromDepo);
                    }
                    if (null != bean.getToDepositoryBean ()
                        && StringUtils.isNotBlank (bean.getToDepositoryBean ().getId ()))
                    {
                        GoodsDepository toDepo = new GoodsDepository ();
                        toDepo.setId (Long.parseLong (bean.getToDepositoryBean ().getId ()));
                        storageTransfer.setToDepository (toDepo);
                    }
                    storageTransfer.setTransferAmount (bean.getTransferAmount ());
                    storageTransfer.setComment (bean.getComment ());
                    storageTransfer.setUserCreatedBy (loginUser.getUid ());

                    componentContext.getPersistenceService ().addGoodsStorageTransfer (storageTransfer);
                    serviceAspect.executeAfterChangeGoods ();
                    ar = createAddSuccessResult (goodsStorageTransferMessage);
                }
                catch (PcServiceException e)
                {
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createAddFailedResult (goodsStorageTransferMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("AddGoodsStorageTransfer",
                                                                            "LogEvent.AddGoodsStorageTransfer",
                                                                            request.getLocale ());
                        componentContext.getLogService ()
                                        .log (logEventName, loginUser.getUid (),
                                              logEventName + ": " + LogUtil.createLogComment (storageTransfer));
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
            return AjaxUtils.getJsonObject (ar);
        }
    }
    
    @RequestMapping (value = "/getGoodsStorage", method = RequestMethod.POST)
    public @ResponseBody
    String getGoodsStorage (@RequestParam String goodsType, @RequestParam String goodsId)
    {
        List <Goods> goods = new ArrayList <Goods> ();
        try
        {
            if (StringUtils.isNotBlank (goodsType) || StringUtils.isNotBlank (goodsId))
            {
                if (StringUtils.isNotBlank (goodsId))
                {
                    long gid = Long.parseLong (goodsId);
                    Goods g = componentContext.getQueryService ().getGoods (gid);
                    if (null != g)
                    {
                        goods.add (g);
                    }
                }
                else if (StringUtils.isNotBlank (goodsType))
                {
                    long typeId = Long.parseLong (goodsType);
                    goods = componentContext.getQueryService ().getGoodsByTypeAndDepository (typeId, 0);
                }
            }
        }
        catch (Exception e)
        {
            logger.warn (e.getMessage (), e);
        }
        
        List <GoodsStorageBean> goodsStorageBeans = new ArrayList <GoodsStorageBean> ();
        for (Goods g : goods)
        {
            String gType = null != g.getType () ? g.getType ().getName () : "";
            String gUnit = null != g.getUnit () ? g.getUnit ().getName () : "";
            if (CollectionUtils.isNotEmpty (g.getStorages ()))
            {
                for (GoodsStorage gs : g.getStorages ())
                {
                    GoodsStorageBean goodsStorageBean = BeanConvertUtils.goodsStorage2GoodsStorageBean (gs);
                    GoodsBean goodsBean = new GoodsBean ();
                    goodsBean.setName (g.getName ());
                    goodsBean.setShortCode (g.getShortCode ());
                    goodsBean.setSpecificationModel (g.getSpecificationModel ());
                    GoodsTypeBean typeBean = new GoodsTypeBean ();
                    typeBean.setText (gType);
                    goodsBean.setTypeBean (typeBean);
                    GoodsUnitBean unitBean = new GoodsUnitBean ();
                    unitBean.setName (gUnit);
                    goodsBean.setUnitBean (unitBean);
                    goodsStorageBean.setGoodsBean (goodsBean);
                    goodsStorageBeans.add (goodsStorageBean);
                }
            }
        }
        
        JSONArray jsonArray = JSONArray.fromObject (goodsStorageBeans);
        return jsonArray.toString ();
    }

    @RequestMapping (value = "/getGoodsStorageStatistic", method = RequestMethod.POST)
    public @ResponseBody
    String getGoodsStorageStatistic (@RequestParam String goodsType, @RequestParam String goodsId)
    {
        List <Goods> goodses = new ArrayList <Goods> ();
        try
        {
            if (StringUtils.isNotBlank (goodsType) || StringUtils.isNotBlank (goodsId))
            {
                if (StringUtils.isNotBlank (goodsId))
                {
                    long gid = Long.parseLong (goodsId);
                    Goods g = componentContext.getQueryService ().getGoods (gid);
                    if (null != g)
                    {
                        goodses.add (g);
                    }
                }
                else if (StringUtils.isNotBlank (goodsType))
                {
                    long typeId = Long.parseLong (goodsType);
                    goodses = componentContext.getQueryService ().getGoodsByTypeAndDepository (typeId, 0);
                }
            }
        }
        catch (Exception e)
        {
            logger.warn (e.getMessage (), e);
        }

        List <GoodsDepository> goodsDepositories = componentContext.getQueryService ().getAllGoodsDepository ();
        List <Goods> goodses2 = new ArrayList <Goods> ();
        List <GoodsBean> goodsBeans = new ArrayList <GoodsBean> ();
        for (Goods goods : goodses)
        {
            List <GoodsStorage> goodsStorages = new ArrayList <GoodsStorage> ();
            for(GoodsDepository goodsDepository : goodsDepositories) 
            {
                boolean existStorage = false;
                for (GoodsStorage goodsStorage : goods.getStorages ())
                {
                    if(goodsStorage.getDepository ().getId () == goodsDepository.getId ()) 
                    {
                        goodsStorages.add (goodsStorage);
                        existStorage = true;
                        break;
                    }
                }
                if(!existStorage) 
                {
                    GoodsStorage goodsStorage = new GoodsStorage ();
                    goodsStorage.setDepository (goodsDepository);
                    goodsStorage.setGoods (goods);
                    goodsStorage.setAmount (0);
                    goodsStorage.setWorth (0);
                    goodsStorages.add (goodsStorage);
                }
            }
            goods.setStorages (goodsStorages);
            goodses2.add (goods);
        }
        
        for (Goods g : goodses2)
        {
            goodsBeans.add(BeanConvertUtils.goods2GoodsBean (g));
        }
        JSONArray jsonArray = JSONArray.fromObject (goodsBeans);
        return jsonArray.toString ();
    }

    @RequestMapping (value = "/generateStorageCheckRecord", method = RequestMethod.POST)
    public @ResponseBody String generateStorageCheckRecord (HttpServletRequest request)
    {
        ActionResult ar = ActionResult.createActionResult ().build ();
        LoginUser loginUser = componentContext.getLoginUser (request);
        if (loginUser != null && loginUser.isAdmin ())
        {
            List <GoodsStorageCheck> storageChecks = new ArrayList <GoodsStorageCheck> ();
            String logComment = "";
            String goodsStorageCheckInfo = request.getParameter ("goodsStorageCheckInfo");
            if (StringUtils.isNotBlank (goodsStorageCheckInfo))
            {
                String[] goodsStorageCheckArr = goodsStorageCheckInfo.split (";");
                for (String goodsStorageCheckItem : goodsStorageCheckArr)
                {
                    String[] goodsStorageCheckItemArr = goodsStorageCheckItem.split (",");
                    if (2 == goodsStorageCheckItemArr.length)
                    {
                        long goodsStorageId = 0;
                        long actualAmount = 0;
                        
                        try
                        {
                            if (StringUtils.isNotBlank (goodsStorageCheckItemArr[0]))
                            {
                                goodsStorageId = Long.parseLong (goodsStorageCheckItemArr[0]);
                            }
                            if (StringUtils.isNotBlank (goodsStorageCheckItemArr[1]))
                            {
                                actualAmount = Long.parseLong (goodsStorageCheckItemArr[1]);
                            }
                        }
                        catch (Exception e)
                        {
                        }
                        
                        GoodsStorage goodsStorage = DaoContext.getGoodsStorageDao ().findOne (goodsStorageId);
                        if (null != goodsStorage)
                        {
                            GoodsStorageCheck storageCheck = new GoodsStorageCheck ();
                            if (goodsStorage.getAmount () != actualAmount)
                            {
                                storageCheck.setGoods (goodsStorage.getGoods ());
                                storageCheck.setDepository (goodsStorage.getDepository ());
                                storageCheck.setAmountBeforeCheck (goodsStorage.getAmount ());
                                storageCheck.setAmountAfterCheck (actualAmount);
                                storageCheck.setUserCreatedBy (loginUser.getUid ());
                                storageChecks.add (storageCheck);
                                logComment += (goodsStorage.getGoods ().getName () + " " + goodsStorage.getDepository ().getName () + " " + goodsStorage.getAmount () + "->" + actualAmount + " ");
                            }
                        }
                    }
                }
            }
            
            try
            {
                if (CollectionUtils.isNotEmpty (storageChecks))
                {
                    for(GoodsStorageCheck storageCheck : storageChecks) 
                    {
                        componentContext.getPersistenceService ().addGoodsStorageCheck (storageCheck);
                    }
                    ar = createAddSuccessResult (goodsStorageCheckMessage);
                }
                serviceAspect.executeAfterChangeGoods ();
            }
            catch (PcServiceException e)
            {
                logger.warn (e.getMessage (), e);
                ar = createServerErrorMessageResult (e.getMessage ());
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage (), e);
                ar = createAddFailedResult (goodsStorageCheckMessage);
            }
            
            if (ActionResultStatus.OK.equals (ar.getStatus ()))
            {
                try
                {
                    String logEventName = LogEventName.createEventName ("AddGoodsStorageCheck",
                                                                        "LogEvent.AddGoodsStorageCheck",
                                                                        request.getLocale ());
                    componentContext.getLogService ().log (logEventName,
                                                           loginUser.getUid (),
                                                           logEventName + ": " + logComment);
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
    
    @RequestMapping (value = "/generateStoragePriceRevise", method = RequestMethod.POST)
    public @ResponseBody String generateStoragePriceRevise (HttpServletRequest request)
    {
        ActionResult ar = ActionResult.createActionResult ().build ();
        LoginUser loginUser = componentContext.getLoginUser (request);
        if (loginUser != null && loginUser.isAdmin ())
        {
            List <GoodsStoragePriceRevise> storagePriceRevises = new ArrayList <GoodsStoragePriceRevise> ();
            String logComment = "";
            String goodsStorageCheckInfo = request.getParameter ("goodsStoragePriceReviseInfo");
            if (StringUtils.isNotBlank (goodsStorageCheckInfo))
            {
                String[] goodsStorageCheckArr = goodsStorageCheckInfo.split (";");
                for (String goodsStorageCheckItem : goodsStorageCheckArr)
                {
                    String[] goodsStorageCheckItemArr = goodsStorageCheckItem.split (",");
                    if (2 == goodsStorageCheckItemArr.length)
                    {
                        long goodsStorageId = 0;
                        long revisePrice = 0;

                        try
                        {
                            if (StringUtils.isNotBlank (goodsStorageCheckItemArr[0]))
                            {
                                goodsStorageId = Long.parseLong (goodsStorageCheckItemArr[0]);
                            }
                            if (StringUtils.isNotBlank (goodsStorageCheckItemArr[1]))
                            {
                                revisePrice = Long.parseLong (goodsStorageCheckItemArr[1]);
                            }
                        }
                        catch (Exception e)
                        {
                        }

                        GoodsStorage goodsStorage = DaoContext.getGoodsStorageDao ().findOne (goodsStorageId);
                        if (null != goodsStorage)
                        {
                            GoodsStoragePriceRevise storagePriceRevise = new GoodsStoragePriceRevise ();
                            if (goodsStorage.getPrice () != revisePrice)
                            {
                                storagePriceRevise.setGoods (goodsStorage.getGoods ());
                                storagePriceRevise.setDepository (goodsStorage.getDepository ());
                                storagePriceRevise.setPriceBeforeRevise (goodsStorage.getPrice ());
                                storagePriceRevise.setPriceAfterRevise (revisePrice);
                                storagePriceRevise.setUserCreatedBy (loginUser.getUid ());
                                storagePriceRevises.add (storagePriceRevise);
                                logComment += (goodsStorage.getGoods ().getName () + " " + goodsStorage.getDepository ().getName () + " " + goodsStorage.getPrice () + "->" + revisePrice + " ");
                            }
                        }
                    }
                }
            }

            try
            {
                if (CollectionUtils.isNotEmpty (storagePriceRevises))
                {
                    for (GoodsStoragePriceRevise storagePriceRevise : storagePriceRevises)
                    {
                        componentContext.getPersistenceService ().addGoodsStoragePriceRevise (storagePriceRevise);
                    }
                    ar = createAddSuccessResult (goodsStoragePriceReviseMessage);
                }
                serviceAspect.executeAfterChangeGoods ();
            }
            catch (PcServiceException e)
            {
                logger.warn (e.getMessage (), e);
                ar = createServerErrorMessageResult (e.getMessage ());
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage (), e);
                ar = createAddFailedResult (goodsStoragePriceReviseMessage);
            }

            if (ActionResultStatus.OK.equals (ar.getStatus ()))
            {
                try
                {
                    String logEventName = LogEventName.createEventName ("AddGoodsStoragePriceRevise",
                                                                        "LogEvent.AddGoodsStoragePriceRevise",
                                                                        request.getLocale ());
                    componentContext.getLogService ().log (logEventName,
                                                           loginUser.getUid (),
                                                           logEventName + ": " + logComment);
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

    @RequestMapping (value = "/getGoodsStorageWarn", method = RequestMethod.POST)
    public @ResponseBody String getGoodsStorageWarn ()
    {
        getStorageWarn ();
        JSONArray jsonArr = JSONArray.fromObject (goodsStorageWarns);
        return jsonArr.toString ();
    }
    
    @RequestMapping (value = "/getStorageCheckRecord", method = RequestMethod.POST)
    public @ResponseBody String getStorageCheckRecord (DataGridRequestForm dataGridRequestForm, SearchRequestForm searchRequestForm,
                                                                   HttpServletRequest request)
    {
        logger.debug (dataGridRequestForm);
        long goodsId = 0;
        long goodsDepository = 0;
        List <GoodsStorageCheck> storageChecks = new ArrayList <GoodsStorageCheck> ();
        try
        {
            if (StringUtils.isNotBlank (searchRequestForm.getGoodsId ()))
            {
                goodsId = Long.parseLong (searchRequestForm.getGoodsId ());
            }
            if (StringUtils.isNotBlank (searchRequestForm.getGoodsDepository ()))
            {
                goodsDepository = Long.parseLong (searchRequestForm.getGoodsDepository ());
            }
            storageChecks = DaoContext.getGoodsStorageCheckDao ().findBy (goodsId, goodsDepository);
        }
        catch (Exception e)
        {
            logger.warn (e.getMessage ());
        }
        List <GoodsStorageBean> storageBeans = new ArrayList <GoodsStorageBean> ();
        LoginUser loginUser = componentContext.getLoginUser (request);
        if (null != loginUser)
        {
            for(GoodsStorageCheck storageCheck : storageChecks) 
            {
                GoodsStorageBean storageBean = new GoodsStorageBean ();
                storageBean.setId (storageCheck.getId () + "");
                storageBean.setBid (storageCheck.getBid ());
                storageBean.setGoodsBean (BeanConvertUtils.goods2GoodsBean (storageCheck.getGoods ()));
                storageBean.setDepositoryBean (BeanConvertUtils.goodsDepository2GoodsDepositoryBean (storageCheck.getDepository ()));
                storageBean.setCurrentAmount (storageCheck.getAmountBeforeCheck ());
                storageBean.setActualAmount (storageCheck.getAmountAfterCheck ());
                storageBean.setDateCreated (DateUtils.date2String (storageCheck.getDateCreated (),
                                                                   DateUtils.DATE_TIME_NO_SECOND_PATTERN));
                User user = DaoContext.getUserDao ().findOne (storageCheck.getUserCreatedBy ());
                if (null != user)
                {
                    storageBean.setUserCreated (user.getUserId ());
                }
                storageBeans.add (storageBean);
            }
        }
        JSONArray jsonObject = JSONArray.fromObject (storageBeans);
        return jsonObject.toString ();
    }
    
    @RequestMapping (value = "/getStoragePriceRevise", method = RequestMethod.POST)
    public @ResponseBody String getStoragePriceRevise (DataGridRequestForm dataGridRequestForm, SearchRequestForm searchRequestForm,
                                        HttpServletRequest request)
    {
        logger.debug (dataGridRequestForm);
        long goodsId = 0;
        long goodsDepository = 0;
        List <GoodsStoragePriceRevise> storagePriceRevises = new ArrayList <GoodsStoragePriceRevise> ();
        try
        {
            if (StringUtils.isNotBlank (searchRequestForm.getGoodsId ()))
            {
                goodsId = Long.parseLong (searchRequestForm.getGoodsId ());
            }
            if (StringUtils.isNotBlank (searchRequestForm.getGoodsDepository ()))
            {
                goodsDepository = Long.parseLong (searchRequestForm.getGoodsDepository ());
            }
            storagePriceRevises = DaoContext.getGoodsStoragePriceReviseDao ().findBy (goodsId, goodsDepository);
        }
        catch (Exception e)
        {
            logger.warn (e.getMessage ());
        }
        List <GoodsStorageBean> storageBeans = new ArrayList <GoodsStorageBean> ();
        LoginUser loginUser = componentContext.getLoginUser (request);
        if (null != loginUser)
        {
            for(GoodsStoragePriceRevise storagePriceRevise : storagePriceRevises) 
            {
                GoodsStorageBean storageBean = new GoodsStorageBean ();
                storageBean.setId (storagePriceRevise.getId () + "");
                storageBean.setBid (storagePriceRevise.getBid ());
                storageBean.setGoodsBean (BeanConvertUtils.goods2GoodsBean (storagePriceRevise.getGoods ()));
                storageBean.setDepositoryBean (BeanConvertUtils.goodsDepository2GoodsDepositoryBean (storagePriceRevise.getDepository ()));
                storageBean.setCurrentPrice (storagePriceRevise.getPriceBeforeRevise ());
                storageBean.setRevisePrice (storagePriceRevise.getPriceAfterRevise ());
                storageBean.setDateCreated (DateUtils.date2String (storagePriceRevise.getDateCreated (),
                                                                   DateUtils.DATE_TIME_NO_SECOND_PATTERN));
                User user = DaoContext.getUserDao ().findOne (storagePriceRevise.getUserCreatedBy ());
                if (null != user)
                {
                    storageBean.setUserCreated (user.getUserId ());
                }
                storageBeans.add (storageBean);
            }
        }
        JSONArray jsonObject = JSONArray.fromObject (storageBeans);
        return jsonObject.toString ();
    }
    
    @RequestMapping (value = "/setAutoShowWarning", method = RequestMethod.POST)
    public @ResponseBody
    String setAutoShowWarning (@RequestParam String autoShowWarning, HttpServletRequest request)
    {
        if (StringUtils.isNotBlank (autoShowWarning))
        {
            request.getSession (false).setAttribute (autoShowStorageWarning, autoShowWarning);
            return "true";
        }
        return "false";
    }
    
    private void getStorageWarn () 
    {
        if(CollectionUtils.isEmpty (goodsStorageWarns)) 
        {
            List <Goods> goodses = componentContext.getQueryService ().getAllGoods ();
            if (CollectionUtils.isNotEmpty (goodses))
            {
                for (Goods goods : goodses)
                {
                    List <GoodsStorage> storages = goods.getStorages ();
                    if (CollectionUtils.isNotEmpty (storages))
                    {
                        int currentStock = 0;
                        for (GoodsStorage goodsStorage : storages)
                        {
                            currentStock += goodsStorage.getAmount ();
                        }
                        if ((currentStock < 0) || (goods.getMinStock () > 0 && currentStock < goods.getMinStock ())
                                || (goods.getMaxStock () > 0 && currentStock > goods.getMaxStock ()))
                        {
                            goodsStorageWarns.add (BeanConvertUtils.goods2GoodsBean (goods));
                        }
                    }
                }
            }
        }
    }

}
