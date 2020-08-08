package com.pinfly.purchasecharge.component.controller.goods;

import gwtupload.server.MyUploadServlet;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.ActionResult.ActionResultStatus;
import com.pinfly.purchasecharge.component.bean.DataGridRequestForm;
import com.pinfly.purchasecharge.component.bean.GenericPagingResult;
import com.pinfly.purchasecharge.component.bean.GoodsBean;
import com.pinfly.purchasecharge.component.bean.GoodsDepositoryBean;
import com.pinfly.purchasecharge.component.bean.GoodsStorageBean;
import com.pinfly.purchasecharge.component.bean.GoodsStorageCourseBean;
import com.pinfly.purchasecharge.component.bean.SearchRequestForm;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.controller.GenericController;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.BeanConvertUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.GoodsStorageCourse;
import com.pinfly.purchasecharge.core.model.GoodsStorageCourseSearchForm;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.OrderTypeCode;
import com.pinfly.purchasecharge.core.model.persistence.goods.Goods;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsPicture;
import com.pinfly.purchasecharge.core.util.Arith;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.dal.DaoContext;
import com.pinfly.purchasecharge.service.aspect.ServiceAspect;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/goods")
public class GoodsManager extends GenericController <GoodsBean>
{
    private static final Logger logger = Logger.getLogger (GoodsManager.class);
    private String goodsMessage = ComponentMessage.createMessage ("GOODS", "GOODS").getI18nMessageCode ();
    
    @Autowired
    ServiceAspect serviceAspect;

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "goodsManagement";
    }

    @Override
    protected String getJsName ()
    {
        return "javascript/goodsMgmtJS";
    }

    @Override
    protected String getCssName ()
    {
        return "stylesheet/goodsMgmtCSS";
    }
    
    public @ResponseBody
    String checkExist (@RequestParam String goodsId, @RequestParam String shortCode, HttpServletRequest request)
    {
        if (StringUtils.isBlank (goodsId))
        {
            // new
            Goods obj = componentContext.getQueryService ().getGoodsByShortCode (shortCode);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long id = Long.parseLong (goodsId);
            Goods goods = componentContext.getQueryService ().getGoodsByShortCode (shortCode);
            if (null != goods && goods.getId () != id)
            {
                return "false";
            }
        }

        return "true";
    }

    @RequestMapping (value = "/checkSpecificationModelExist", method = RequestMethod.POST)
    public @ResponseBody String checkSpecificationModelExist (@RequestParam String goodsId, @RequestParam String goodsName, @RequestParam String specificationModel,
                              HttpServletRequest request)
    {
        if (StringUtils.isBlank (goodsId))
        {
            // new
            Goods goods = componentContext.getQueryService ()
                                                   .getGoodsBySpecificationModel (goodsName, specificationModel);
            if (null != goods)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long id = Long.parseLong (goodsId);
            Goods goods = componentContext.getQueryService ()
                                                   .getGoodsBySpecificationModel (goodsName, specificationModel);
            if (null != goods && goods.getId () != id)
            {
                return "false";
            }
        }

        return "true";
    }

    @Override
    public String getAllModel (HttpServletRequest request)
    {
        List <Goods> allGoods = (List <Goods>) componentContext.getQueryService ().getAllGoods ();
        JSONArray jsonObject = JSONArray.fromObject (BeanConvertUtils.goodsList2GoodsBeanList (allGoods));
        return jsonObject.toString ();
    }

    public String getPagedModel (DataGridRequestForm dataGridRequestForm, HttpServletRequest request)
    {
        logger.debug (dataGridRequestForm);
        LoginUser loginUser = componentContext.getLoginUser (request);
        GenericPagingResult <GoodsBean> goodsPagingResult = new GenericPagingResult <GoodsBean> ();
        if (loginUser != null)
        {
            int page = dataGridRequestForm.getPage () - 1;
            int size = dataGridRequestForm.getRows ();
            String sortField = parseSortField (dataGridRequestForm.getSort ());
            String order = dataGridRequestForm.getOrder ();
            Pageable pageable = new PageRequest (page, size, new Sort ("asc".equalsIgnoreCase (order) ? Direction.ASC
                                                                                                     : Direction.DESC,
                                                                       sortField));
            String searchKey = (null == dataGridRequestForm.getSearchKey () ? "" : dataGridRequestForm.getSearchKey ()
                                                                                                      .trim ());

            String goodsType = request.getParameter ("goodsTypeId");
            String showHasStorage = request.getParameter ("showHasStorage");
            long goodsTypeId = StringUtils.isNotBlank (goodsType) ? Long.parseLong (goodsType) : 0;

            long count = 0;
            List <GoodsBean> goodsBeans = new ArrayList <GoodsBean> ();
            Page <Goods> goodsPage = null;
            try
            {
                goodsPage = componentContext.getQueryService ().getGoods (goodsTypeId, pageable, searchKey,
                                                                          "1".equals (showHasStorage));
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage ());
            }

            if (null != goodsPage)
            {
                goodsBeans = BeanConvertUtils.goodsList2GoodsBeanList (goodsPage.getContent ());
                count = goodsPage.getTotalElements ();
            }
            goodsPagingResult.setRows (goodsBeans);
            goodsPagingResult.setTotal (count);

            float countGoodsRestAmount = 0;
            float countGoodsRestWorth = 0;
            try
            {
                countGoodsRestAmount = componentContext.getQueryService ()
                                                       .countGoodsRestAmount (goodsTypeId, searchKey);
                countGoodsRestWorth = componentContext.getQueryService ().countGoodsRestWorth (goodsTypeId, searchKey);
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage ());
            }

            List <Map <String, String>> footer = new ArrayList <Map <String, String>> ();
            Map <String, String> map = new HashMap <String, String> ();
            map.put ("name", "合计");
            map.put ("totalStock", countGoodsRestAmount + "");
            map.put ("totalValue", Arith.round (countGoodsRestWorth, -1) + "");
            footer.add (map);
            goodsPagingResult.setFooter (footer);
        }

        JSONObject jsonObject = JSONObject.fromObject (goodsPagingResult);
        return jsonObject.toString ();
    }

    @Override
    public String addModel (GoodsBean bean, BindingResult bindingResult, HttpServletRequest request)
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
                Goods goods = null;
                try
                {
                    bean.setUserCreated (loginUser.getUserId ());
                    bean.setStorageBeans (parseGoodsStorageBean (bean));
                    goods = BeanConvertUtils.goodsBean2Goods (bean);
                    goods = componentContext.getPersistenceService ().addGoods (goods);
                    serviceAspect.executeAfterChangeGoods ();
                    ar = createAddSuccessResult (goodsMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createAddFailedResult (goodsMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("AddGoods", "LogEvent.AddGoods",
                                                                            request.getLocale ());
                        componentContext.getLogService ().log (logEventName,
                                                               loginUser.getUid (),
                                                               logEventName + ": "
                                                                       + LogUtil.createLogComment (goods));
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
    public String updateModel (GoodsBean bean, BindingResult bindingResult, HttpServletRequest request)
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
                Goods goods = null;
                Goods oldGoods = null;
                try
                {
                    bean.setUserCreated (loginUser.getUserId ());
                    bean.setStorageBeans (parseGoodsStorageBean (bean));
                    goods = BeanConvertUtils.goodsBean2Goods (bean);
                    oldGoods = componentContext.getQueryService ().getGoods (goods.getId ());
                    goods = componentContext.getPersistenceService ().updateGoods (goods);
                    serviceAspect.executeAfterChangeGoods ();
                    ar = createUpdateSuccessResult (goodsMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createUpdateFailedResult (goodsMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("UpdateGoods", "LogEvent.UpdateGoods",
                                                                            request.getLocale ());
                        componentContext.getLogService ().log (logEventName,
                                                               loginUser.getUid (),
                                                               logEventName
                                                                       + ": "
                                                                       + LogUtil.createLogComment (oldGoods,
                                                                                                            goods));
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
            List <Goods> goodses = new ArrayList <Goods> ();
            Goods goods;
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    goods = new Goods ();
                    goods.setId (Long.parseLong (typeId));
                    goodses.add (goods);
                }
            }
            else
            {
                goods = new Goods ();
                goods.setId (Long.parseLong (ids));
                goodses.add (goods);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    componentContext.getPersistenceService ().deleteGoods (goodses);
                    ar = createDeleteSuccessResult (goodsMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (goodsMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("DeleteGoods", "LogEvent.DeleteGoods",
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

    @RequestMapping (value = "/getGoodsByNameLike", method = RequestMethod.POST)
    public void getGoodsByNameLike (@RequestParam String name, HttpServletResponse response) throws Exception
    {
        logger.debug (name);
        if (StringUtils.isNotBlank (name))
        {
            response.setContentType ("text/html;charset=utf-8");
            PrintWriter writer = response.getWriter ();

            List <Goods> goodsList = new ArrayList <Goods> ();
            goodsList.addAll (componentContext.getQueryService ().getGoodsByFuzzyName (name));
            try 
            {
                long goodsId = Long.parseLong (name);
                Goods goods = componentContext.getQueryService ().getGoods (goodsId);
                if(null != goods) 
                {
                    goodsList.add (goods);
                }
            }
            catch (Exception e) 
            {
                logger.warn (e.getMessage ());
            }
            JSONArray jsonObject = JSONArray.fromObject (BeanConvertUtils.goodsList2GoodsBeanList (goodsList));
            writer.write ("flightHandler(" + jsonObject.toString () + ")");
        }
    }

    @RequestMapping (value = "/getGoodsByNameCodeLike", method = RequestMethod.POST)
    public void getGoodsByNameCodeLike (@RequestParam String goodsType, @RequestParam String name,
                                        HttpServletResponse response) throws Exception
    {
        logger.debug (name);
        if (StringUtils.isNotBlank (name))
        {
            response.setContentType ("text/html;charset=utf-8");
            PrintWriter writer = response.getWriter ();

            List <Goods> goodsList = componentContext.getQueryService ().getGoodsByNameOrCode (name);
            List <Goods> goodsList2 = new ArrayList <Goods> ();
            if (CollectionUtils.isNotEmpty (goodsList))
            {
                for (Goods goods : goodsList)
                {
                    if (StringUtils.isNotBlank (goodsType))
                    {
                        long goodsTypeId = Long.parseLong (goodsType);
                        if (null != goods.getType () && goodsTypeId == goods.getType ().getId ())
                        {
                            goodsList2.add (goods);
                        }
                    }
                    else
                    {
                        goodsList2.add (goods);
                    }
                }
            }
            JSONArray jsonObject = JSONArray.fromObject (BeanConvertUtils.goodsList2GoodsBeanList (goodsList2));
            writer.write ("flightHandler(" + jsonObject.toString () + ")");
        }
    }
    
    @RequestMapping (value = "/getGoodsById", method = RequestMethod.POST)
    public @ResponseBody
    String getGoodsById (@RequestParam String goodsId, HttpServletRequest request) throws Exception
    {
        logger.debug (goodsId);
        if (StringUtils.isNotBlank (goodsId))
        {
            try
            {
                long gid = Long.parseLong (goodsId);
                Goods goods = componentContext.getQueryService ().getGoods (gid);
                if (null != goods)
                {
                    List <Goods> goodsList = new ArrayList <Goods> ();
                    goodsList.add (goods);
                    JSONArray jsonObject = JSONArray.fromObject (BeanConvertUtils.goodsList2GoodsBeanList (goodsList));
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

    @RequestMapping (value = "/getGoodsByTypeOrDepository", method = RequestMethod.POST)
    public @ResponseBody
    String getGoodsByTypeOrDepository (@RequestParam String goodsTypeId, @RequestParam String goodsDepositoryId, HttpServletRequest request) throws Exception
    {
        long gTypeId = 0;
        long gDepositoryId = 0;
        if (StringUtils.isNotBlank (goodsTypeId))
        {
            try 
            {
                gTypeId = Long.parseLong (goodsTypeId);
            }
            catch (Exception e) {
                gTypeId = 0;
                logger.warn (e.getMessage ());
            }
        }
        if(StringUtils.isNotBlank (goodsDepositoryId)) 
        {
            try 
            {
                gDepositoryId = Long.parseLong (goodsDepositoryId);
            }
            catch (Exception e) {
                gDepositoryId = 0;
                logger.warn (e.getMessage ());
            }
        }
        
        List <Goods> goodsList = componentContext.getQueryService ().getGoodsByTypeAndDepository (gTypeId, gDepositoryId);
        JSONArray jsonObject = JSONArray.fromObject (BeanConvertUtils.goodsList2GoodsBeanList (goodsList));
        return jsonObject.toString ();
    }

    @RequestMapping (value = "/getStorageCourse", method = RequestMethod.POST)
    public @ResponseBody
    String getStorageCourse (DataGridRequestForm dataGridRequestForm, SearchRequestForm requestForm, HttpServletRequest request)
    {
        GenericPagingResult <GoodsStorageCourseBean> goodsPagingResult = new GenericPagingResult <GoodsStorageCourseBean> ();
        if (null != requestForm && StringUtils.isNotBlank (requestForm.getGoodsId ()))
        {
            int page = dataGridRequestForm.getPage () - 1;
            int size = dataGridRequestForm.getRows ();
            Pageable pageable = new PageRequest (page, size);

            GoodsStorageCourseSearchForm searchForm = new GoodsStorageCourseSearchForm ();
            searchForm.setGoodsId (Long.parseLong (requestForm.getGoodsId ()));
            if (StringUtils.isNotBlank (requestForm.getStartDate ())
                && StringUtils.isNotBlank (requestForm.getEndDate ()))
            {
                searchForm.setStartDate (DateUtils.string2Date (requestForm.getStartDate () + " 00:00:00",
                                                                DateUtils.DATE_TIME_PATTERN));
                searchForm.setEndDate (DateUtils.string2Date (requestForm.getEndDate () + " 23:59:59",
                                                              DateUtils.DATE_TIME_PATTERN));
            }
            String type = request.getParameter ("orderType");
            if (OrderTypeCode.OUT.toString ().equals (type))
            {
                searchForm.setOrderTypeCode (OrderTypeCode.OUT);
            }
            else if (OrderTypeCode.IN.toString ().equals (type))
            {
                searchForm.setOrderTypeCode (OrderTypeCode.IN);
            }

            Page <GoodsStorageCourse> goodsPage = componentContext.getQueryService ()
                                                                  .getGoodsStorageCourse (pageable, searchForm);

            goodsPagingResult.setTotal (goodsPage.getTotalElements ());

            List <GoodsStorageCourse> goodsStorageCourses = goodsPage.getContent ();
            List <GoodsStorageCourseBean> goodsStorageCourseBeans = new ArrayList <GoodsStorageCourseBean> ();
            for (GoodsStorageCourse goodsStorageCourse : goodsStorageCourses)
            {
                goodsStorageCourseBeans.add (BeanConvertUtils.goodsStorageCourse2GoodsStorageCourseBean (goodsStorageCourse));
            }
            goodsPagingResult.setRows (goodsStorageCourseBeans);
            
            long sumAmount = componentContext.getQueryService ().countGoodsStorageCourse (searchForm);
            List<Map<String, String>> footer = new ArrayList <Map<String,String>> ();
            Map<String, String> map = new HashMap<String, String>();
            map.put ("goodsName", "合计");
            map.put ("goodsAmount", sumAmount + "");
            footer.add (map);
            goodsPagingResult.setFooter (footer);
        }

        JSONObject jsonObject = JSONObject.fromObject (goodsPagingResult);
        return jsonObject.toString ();
    }

    @RequestMapping (value = "/getGoodsPicture", method = RequestMethod.POST)
    public @ResponseBody
    String getGoodsPicture (@RequestParam String goodsId, HttpServletRequest request)
    {
        List <GoodsPicture> pics = new ArrayList <GoodsPicture> ();
        try
        {
            if (StringUtils.isNotBlank (goodsId))
            {
                long gid = Long.parseLong (goodsId);
                pics = DaoContext.getGoodsPictureDao ().findByGoods (gid);
            }
        }
        catch (Exception e)
        {
            logger.warn (e.getMessage (), e);
        }

        JSONArray json = JSONArray.fromObject (pics);
        return json.toString ();
    }

    @RequestMapping (value = "/setGoodsIdToSession", method = RequestMethod.POST)
    public @ResponseBody
    String setGoodsIdToSession (@RequestParam String goodsId, HttpServletRequest request)
    {
        if (StringUtils.isNotBlank (goodsId))
        {
            request.getSession ().setAttribute (MyUploadServlet.GOODS_ID, goodsId);
            return "true";
        }
        return "false";
    }

    @RequestMapping (value = "/exportGoods", method = RequestMethod.GET)
    public List <GoodsBean> exportGoods (@RequestParam String ids, HttpServletRequest request, Model model)
    {
        List <GoodsBean> goodsesExported = new ArrayList <GoodsBean> ();
        if (null != ids && ids.trim ().length () > 0)
        {
            List <Goods> goodses = new ArrayList <Goods> ();
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    goodses.add (componentContext.getQueryService ().getGoods (Long.parseLong (typeId)));
                }
            }
            else
            {
                goodses.add (componentContext.getQueryService ().getGoods (Long.parseLong (ids)));
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    goodsesExported = BeanConvertUtils.goodsList2GoodsBeanList (goodses);
                    model.addAttribute ("goodsesExported", goodsesExported);
                    ar = createServerOkMessageResult ("");
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult ("");
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        // String logEventName = LogEventName.createEventName
                        // ("DeleteGoods", "LogEvent.DeleteGoods",
                        // request.getLocale ());
                        // componentContext.getLogService ().log (logEventName,
                        // loginUser.getUid (), logEventName + ": " + ids);
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
        }
        return goodsesExported;
    }

    private String parseSortField (final String sortField)
    {
        String sortFieldAfterParse = "name";
        if (!StringUtils.isBlank (sortField))
        {
            sortFieldAfterParse = sortField;
        }
        return sortFieldAfterParse;
    }

    private List <GoodsStorageBean> parseGoodsStorageBean (GoodsBean goodsBean)
    {
        List <GoodsStorageBean> storageBeans = new ArrayList <GoodsStorageBean> ();
        if (null != goodsBean)
        {
            if (StringUtils.isNotBlank (goodsBean.getStorageList ()))
            {
                String[] storageArr = goodsBean.getStorageList ().split (";");
                if (storageArr.length > 0)
                {
                    for (String storageS : storageArr)
                    {
                        String[] itemArr = storageS.split (",");
                        GoodsStorageBean gsb = new GoodsStorageBean ();
                        gsb.setId (itemArr[0]);
                        GoodsDepositoryBean depositoryBean = new GoodsDepositoryBean ();
                        depositoryBean.setId (itemArr[1]);
                        gsb.setDepositoryBean (depositoryBean);
                        gsb.setGoodsBean (goodsBean);
                        if (StringUtils.isNotBlank (itemArr[2]))
                        {
                            gsb.setCurrentAmount (Long.parseLong (itemArr[2]));
                        }
                        if (StringUtils.isNotBlank (itemArr[3]))
                        {
                            gsb.setCurrentPrice (Float.parseFloat (itemArr[3]));
                        }
                        if (StringUtils.isNotBlank (itemArr[2]) && StringUtils.isNotBlank (itemArr[3]))
                        {
                            gsb.setWorth (Long.parseLong (itemArr[2]) * Float.parseFloat (itemArr[3]));
                        }
                        storageBeans.add (gsb);
                    }
                }
            }
        }
        return storageBeans;
    }

}
