package com.pinfly.purchasecharge.component.controller.out;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.ActionResult.ActionResultStatus;
import com.pinfly.purchasecharge.component.bean.ComponentContext;
import com.pinfly.purchasecharge.component.bean.DataGridRequestForm;
import com.pinfly.purchasecharge.component.bean.GenericPagingResult;
import com.pinfly.purchasecharge.component.bean.GoodsDepositoryBean;
import com.pinfly.purchasecharge.component.bean.OrderBean;
import com.pinfly.purchasecharge.component.bean.OrderItemBean;
import com.pinfly.purchasecharge.component.bean.OrderPrinter;
import com.pinfly.purchasecharge.component.bean.PaymentAccountBean;
import com.pinfly.purchasecharge.component.bean.SearchRequestForm;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.controller.GenericController;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.BeanConvertUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.OrderItemSearchForm;
import com.pinfly.purchasecharge.core.model.OrderSearchForm;
import com.pinfly.purchasecharge.core.model.OrderStatusCode;
import com.pinfly.purchasecharge.core.model.OrderTypeCode;
import com.pinfly.purchasecharge.core.model.PaymentTypeCode;
import com.pinfly.purchasecharge.core.model.persistence.PaymentAccount;
import com.pinfly.purchasecharge.core.model.persistence.goods.Goods;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerPayment;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOut;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOutItem;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.User;
import com.pinfly.purchasecharge.core.util.Arith;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.core.util.PurchaseChargeConstants;
import com.pinfly.purchasecharge.core.util.PurchaseChargeUtils;
import com.pinfly.purchasecharge.dal.DaoContext;
import com.pinfly.purchasecharge.service.aspect.ServiceAspect;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/outOrder")
public class OutOrderManager extends GenericController <OrderBean>
{
    private static final Logger logger = Logger.getLogger (OutOrderManager.class);

    public static final String[] staticOrderColumnHeaders =
    { "goodsId", "goodsName", "goodsUnit", "goodsPrice", "goodsDepository", "goodsAmount", "goodsMoney", "comment" };
    private static String outOrderMessage = ComponentMessage.createMessage ("OUT_ORDER", "OUT_ORDER")
                                                            .getI18nMessageCode ();
    private static String outOrderStatusMessage = ComponentMessage.createMessage ("OUT_ORDER_STATUS",
                                                                                  "OUT_ORDER_STATUS")
                                                                  .getI18nMessageCode ();
    @Autowired
    ServiceAspect serviceAspect;

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "outOrderManagement";
    }
    
    @RequestMapping (value = "/getAddView")
    protected String getAddView ()
    {
        return "outOrderAdd";
    }

    @RequestMapping (value = "/getOrderItemQueryView")
    protected String getOrderItemQueryView ()
    {
        return "outOrderDetailQuery";
    }

    @Override
    protected String getJsName ()
    {
        return "javascript/outOrderMgmtJS";
    }

    @Override
    protected String getCssName ()
    {
        return "stylesheet/outOrderMgmtCSS";
    }

    @RequestMapping (value = "/outOrderProfitQuery")
    public String redirectToOrderProfitView (ModelMap model)
    {
        return "outOrderProfitQuery";
    }

    @Override
    public String getModelBySearchForm (DataGridRequestForm dataGridRequestForm, SearchRequestForm searchRequestForm,
                                        HttpServletRequest request)
    {
        logger.debug (dataGridRequestForm);
        GenericPagingResult <OrderBean> orderPagingResult = new GenericPagingResult <OrderBean> ();
        
        int page = dataGridRequestForm.getPage () - 1;
        int size = dataGridRequestForm.getRows ();
        String sortField = parseSortField (dataGridRequestForm.getSort ());
        String order = dataGridRequestForm.getOrder ();
        Pageable pageable = new PageRequest (page, size, new Sort ("asc".equalsIgnoreCase (order) ? Direction.ASC
                                                                                                 : Direction.DESC,
                                                                   sortField));

        LoginUser loginUser = componentContext.getLoginUser (request);
        if (null != loginUser)
        {
            String type = request.getParameter ("type");
            OrderTypeCode typeCode = null;
            if(OrderTypeCode.OUT.toString ().equals (type)) 
            {
                typeCode = OrderTypeCode.OUT;
            }
            else if(OrderTypeCode.OUT_RETURN.toString ().equals (type)) 
            {
                typeCode = OrderTypeCode.OUT_RETURN;
            }
            
            OrderSearchForm searchForm = BeanConvertUtils.searchRequestForm2OrderSearchForm (searchRequestForm);
            Page <OrderOut> orderPage = null;
            float sumReceivable = 0;
            float sumPaidMoney = 0;
            float sumProfit = 0;
            List <Map <String, String>> footer = new ArrayList <Map <String, String>> ();
            if (null != searchForm && StringUtils.isNotBlank (searchForm.getOrderBid ()))
            {
                OrderOut ord = componentContext.getQueryService ().getOrderOut (searchForm.getOrderBid ());
                List <OrderOut> list = new ArrayList <OrderOut> ();
                orderPage = new PageImpl <OrderOut> (list, pageable, 0);
                if (null != ord)
                {
                    list.add (ord);
                    orderPage = new PageImpl <OrderOut> (list, pageable, 1);
                }
                footer = countTotalForOrders (orderPage.getContent ());
            }
            else
            {
                orderPage = componentContext.getQueryService ().findOrderOutBySearchForm (typeCode, pageable,
                                                                                          searchForm,
                                                                                          loginUser.getUserId (),
                                                                                          loginUser.isAdmin ());
                sumReceivable = componentContext.getQueryService ()
                        .countOrderOutReceivableBySearchForm (typeCode, searchForm,
                                                              loginUser.getUserId (),
                                                              loginUser.isAdmin ());
                sumPaidMoney = componentContext.getQueryService ()
                                                .countOrderOutPaidMoneyBySearchForm (typeCode, searchForm,
                                                                                      loginUser.getUserId (),
                                                                                      loginUser.isAdmin ());
                sumProfit = componentContext.getQueryService ()
                                            .countOrderOutProfitBySearchForm (typeCode, searchForm,
                                                                              loginUser.getUserId (),
                                                                              loginUser.isAdmin ());
                
                Map <String, String> map = new HashMap <String, String> ();
                map.put ("bid", "合计");
                map.put (PurchaseChargeConstants.RECEIVABLE_MONEY, Arith.round (sumReceivable, -1) + "");
                map.put (PurchaseChargeConstants.PAID_MONEY, Arith.round (sumPaidMoney, -1) + "");
                map.put (PurchaseChargeConstants.PROFIT_MONEY, Arith.round (sumProfit, -1) + "");
                footer.add (map);
            }

            long total = orderPage.getTotalElements ();
            List <OrderBean> orderBeans = new ArrayList <OrderBean> ();
            PaymentTypeCode paymentTypeCode = OrderTypeCode.OUT.equals (typeCode) ? PaymentTypeCode.OUT_PAID_MONEY : PaymentTypeCode.OUT_ORDER_RETURN;
            for(OrderOut orderOut : orderPage.getContent ()) 
            {
                PaymentAccount paymentAccount = componentContext.getQueryService ().getPaymentAccountByOrderId (orderOut.getTypeCode (), orderOut.getId () + "", paymentTypeCode);
                orderBeans.add (BeanConvertUtils.orderOut2OrderBean (orderOut, paymentAccount));
            }
            orderPagingResult.setRows (orderBeans);
            orderPagingResult.setTotal (total);
            orderPagingResult.setFooter (footer);
        }
        JSONObject jsonObject = JSONObject.fromObject (orderPagingResult);
        return jsonObject.toString ();
    }

    @Override
    public String getModelById (@RequestParam String orderId, HttpServletRequest request)
    {
        GenericPagingResult <OrderItemBean> orderPagingResult = new GenericPagingResult <OrderItemBean> ();
        if (StringUtils.isNotBlank (orderId))
        {
            List <OrderOutItem> orderItems = componentContext.getQueryService ().getOrderOutItemByOrder (Long.parseLong (orderId), false);
            if (!CollectionUtils.isEmpty (orderItems))
            {
                orderPagingResult.setRows (BeanConvertUtils.orderOutItemList2OrderItemBeanList (orderItems));
                orderPagingResult.setTotal (orderItems.size ());
                orderPagingResult.setFooter (countTotalForOrderItems (orderItems));
            }
        }
        JSONObject jsonObject = JSONObject.fromObject (orderPagingResult);
        return jsonObject.toString ();
    }

    @Override
    public String addModel (@Valid OrderBean bean, BindingResult bindingResult, HttpServletRequest request)
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
                OrderOut order = null;
                try
                {
                    bean.setUserCreated (loginUser.getUserId ());
                    bean.setOrderItemBeans (parseOrderItem (bean));
                    order = BeanConvertUtils.orderBean2OrderOut (bean);
                    
                    PaymentAccount paymentAccount = null;
                    PaymentAccountBean accountBean = bean.getPaymentAccountBean ();
                    if(null != accountBean && StringUtils.isNotBlank (accountBean.getId ())) 
                    {
                        paymentAccount = BeanConvertUtils.paymentAccountBean2PaymentAccount (accountBean);
                    }
                    
                    /*AccountingType accountingType = null;
                    AccountingTypeBean accountingTypeBean = bean.getAccountingTypeBean ();
                    if(null != accountingTypeBean && StringUtils.isNotBlank (accountingTypeBean.getId ())) 
                    {
                        accountingType = BeanConvertUtils.accountingTypeBean2AccountingType (bean.getAccountingTypeBean ());
                    }*/
                    
                    order = componentContext.getPersistenceService ().addOrderOut (order, paymentAccount);
                    serviceAspect.executeAfterChangeGoods ();
                    ar = createAddSuccessResult (outOrderMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createAddFailedResult (outOrderMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("AddOutOrderImport",
                                                                            "LogEvent.AddOutOrderImport",
                                                                            request.getLocale ());
                        if (OrderTypeCode.OUT_RETURN.equals (order.getTypeCode ()))
                        {
                            logEventName = LogEventName.createEventName ("AddOutOrderExport",
                                                                         "LogEvent.AddOutOrderExport",
                                                                         request.getLocale ());
                        }
                        componentContext.getLogService ().log (logEventName,
                                                               loginUser.getUid (),
                                                               logEventName + ": "
                                                                       + LogUtil.createLogComment (order));
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
    public String updateModel (OrderBean bean, BindingResult bindingResult, HttpServletRequest request)
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
                long orderId = Long.parseLong (bean.getId ());
                OrderStatusCode orderStatusCode = DaoContext.getOrderOutDao ().findOne (orderId).getStatusCode ();
                try
                {
                    boolean flag = componentContext.getPersistenceService ()
                                                   .updateOrderOutStatus (orderId, bean.getStatusCode (),
                                                                          bean.getComment (), null);
                    if (flag)
                    {
                        ar = createUpdateSuccessResult (outOrderStatusMessage);
                    }
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createUpdateFailedResult (outOrderStatusMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("UpdateOutOrderStatus",
                                                                            "LogEvent.UpdateOutOrderStatus",
                                                                            request.getLocale ());
                        componentContext.getLogService ()
                                        .log (logEventName,
                                              loginUser.getUid (),
                                              logEventName
                                                      + ": "
                                                      + LogUtil.createLogComment (orderStatusCode,
                                                                                           bean.getStatusCode ()));
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
            List <OrderOut> orders = new ArrayList <OrderOut> ();
            OrderOut order;
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    order = new OrderOut ();
                    order.setId (Long.parseLong (typeId));
                    orders.add (order);
                }
            }
            else
            {
                order = new OrderOut ();
                order.setId (Long.parseLong (ids));
                orders.add (order);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    componentContext.getPersistenceService ().deleteOrderOut (orders);
                    ar = createDeleteSuccessResult (outOrderMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (outOrderMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("DeleteOutOrderImport",
                                                                            "LogEvent.DeleteOutOrderImport",
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

    @RequestMapping (value = "/getOrderItemsByOrderIds", method = RequestMethod.POST)
    public @ResponseBody
    String getOrderItemsByOrderIds (@RequestParam String orderIds)
    {
        if (StringUtils.isNotBlank (orderIds))
        {
            List <OrderOutItem> orderItems = new ArrayList <OrderOutItem> ();
            if (orderIds.indexOf (";") != -1)
            {
                String[] idArr = orderIds.split (";");
                for (String orderId : idArr)
                {
                    long oId = Long.parseLong (orderId);
                    orderItems.addAll (componentContext.getQueryService ().getOrderOutItemByOrder (oId, true));
                }
            }
            else
            {
                long oId = Long.parseLong (orderIds);
                orderItems.addAll (componentContext.getQueryService ().getOrderOutItemByOrder (oId, true));
            }

            if (!CollectionUtils.isEmpty (orderItems))
            {
                GenericPagingResult <OrderItemBean> orderPagingResult = new GenericPagingResult <OrderItemBean> ();
                orderPagingResult.setRows (BeanConvertUtils.orderOutItemList2OrderItemBeanList (orderItems));
                orderPagingResult.setTotal (orderItems.size ());
                orderPagingResult.setFooter (countTotalForOrderItems (orderItems));

                JSONObject jsonObject = JSONObject.fromObject (orderPagingResult);
                return jsonObject.toString ();
            }
        }
        return "";
    }

    @RequestMapping (value = "/getOrderItemsByGoods", method = RequestMethod.POST)
    public @ResponseBody
    String getOrderItemsByGoods (@RequestParam String goodsId, HttpServletRequest request)
    {
        List <OrderItemBean> orderOutItems = new ArrayList <OrderItemBean> ();
        if (StringUtils.isNotBlank (goodsId))
        {
            long gid = Long.parseLong (goodsId);
            Timestamp end = DateUtils.date2Timestamp (new Date ());
            Timestamp start = DateUtils.date2Timestamp (DateUtils.getDateBefore (end, 90));
            List <OrderOut> orderOuts = DaoContext.getOrderOutDao ().findByRangeCreateDate (start, end);
            for (OrderOut orderOut : orderOuts)
            {
                List <OrderOutItem> orderItems = componentContext.getQueryService ()
                                                                 .getOrderOutItemByOrder (orderOut.getId (), true);
                for (OrderOutItem orderOutItem : orderItems)
                {
                    if (null != orderOutItem.getGoods () && gid == orderOutItem.getGoods ().getId ())
                    {
                        orderOutItems.add (BeanConvertUtils.orderOutItem2OrderItemBean (orderOutItem));
                    }
                }
            }
        }

        JSONArray jsonArr = JSONArray.fromObject (orderOutItems);
        return jsonArr.toString ();
    }

    @RequestMapping (value = "/getLatestGoodsUnitPrice", method = RequestMethod.POST)
    public @ResponseBody
    String getLatestGoodsUnitPrice (@RequestParam String customerId, @RequestParam String goodsId)
    {
        List <OrderItemBean> orderItemList = new ArrayList <OrderItemBean> ();
        if (StringUtils.isNotBlank (customerId) && StringUtils.isNotBlank (goodsId))
        {
            long goodsUid = Long.parseLong (goodsId);
            List <OrderOut> orderList = componentContext.getQueryService ()
                                                        .getOrderOutByCustomer (Long.parseLong (customerId));
            if (!CollectionUtils.isEmpty (orderList))
            {
                for (OrderOut order : orderList)
                {
                    List <OrderOutItem> orderItems = componentContext.getQueryService ().getOrderOutItemByOrder (order.getId (), false);
                    if (!CollectionUtils.isEmpty (orderItems))
                    {
                        for (OrderOutItem orderItem : orderItems)
                        {
                            if (goodsUid == orderItem.getGoods ().getId ())
                            {
                                OrderItemBean orderItemBean = BeanConvertUtils.orderOutItem2OrderItemBean (orderItem);
                                orderItemBean.setOrderCreate (DateUtils.date2String (order.getDateCreated (),
                                                                                     DateUtils.DATE_TIME_PATTERN));
                                orderItemList.add (orderItemBean);
                            }
                        }
                    }
                }
            }
        }

        OrderItemBean latestItem = BeanConvertUtils.getLatestOrderItem (orderItemList);
        if (null == latestItem)
        {
            Goods goods = componentContext.getQueryService ().getGoods (Long.parseLong (goodsId));
            if(null != goods) 
            {
                latestItem = new OrderItemBean ();
                latestItem.setUnitPrice (goods.getRetailPrice ());
            }
        }
        return JSONObject.fromObject (latestItem).toString ();
    }

    @RequestMapping (value = "/getOrderProfit", method = RequestMethod.POST)
    public @ResponseBody
    String getOrderProfit (DataGridRequestForm dataGridRequestForm, SearchRequestForm searchRequestForm,
                           HttpServletRequest request)
    {
        logger.debug (dataGridRequestForm);
        int page = dataGridRequestForm.getPage () - 1;
        int size = dataGridRequestForm.getRows ();
        String sortField = (null == dataGridRequestForm.getSort ()) ? "customer.shortName"
                                                                   : PurchaseChargeConstants.CREATE_DATE;
        String order = dataGridRequestForm.getOrder ();
        Pageable pageable = new PageRequest (page, size, new Sort ("asc".equalsIgnoreCase (order) ? Direction.ASC
                                                                                                 : Direction.DESC,
                                                                   sortField));

        GenericPagingResult <OrderBean> orderPagingResult = new GenericPagingResult <OrderBean> ();
        LoginUser loginUser = componentContext.getLoginUser (request);
        if (null != loginUser)
        {
            OrderSearchForm searchForm = BeanConvertUtils.searchRequestForm2OrderSearchForm (searchRequestForm);
            User user = componentContext.getQueryService ().getUser (searchForm.getUserCreatedBy ());
            if(null != user) 
            {
                List <Map <String, String>> footer = new ArrayList <Map <String, String>> ();
                Page <OrderOut> orderPage = componentContext.getQueryService ()
                        .findOrderOutBySearchForm (pageable, searchForm,
                                                   user.getUserId (),
                                                   user.isAdmin ());
                float sumReceivable = componentContext.getQueryService ()
                        .countOrderOutReceivableBySearchForm (null, searchForm,
                                                              user.getUserId (),
                                                              user.isAdmin ());
                float sumProfit = componentContext.getQueryService ()
                        .countOrderOutProfitBySearchForm (null, searchForm, user.getUserId (),
                                                          user.isAdmin ());
                
                Map <String, String> map = new HashMap <String, String> ();
                map.put ("bid", "合计");
                map.put (PurchaseChargeConstants.RECEIVABLE_MONEY, sumReceivable + "");
                map.put (PurchaseChargeConstants.PROFIT_MONEY, sumProfit + "");
                footer.add (map);
                
                long total = orderPage.getTotalElements ();
                orderPagingResult.setRows (BeanConvertUtils.orderOutList2OrderBeanList (orderPage.getContent ()));
                orderPagingResult.setTotal (total);
                orderPagingResult.setFooter (footer);
            }
        }
        JSONObject jsonObject = JSONObject.fromObject (orderPagingResult);
        return jsonObject.toString ();
    }

    @RequestMapping (value = "/getOrderItemTable", method = RequestMethod.POST)
    public void getOrderItemTable (@RequestParam String orderId, @RequestParam String templateFile,
                                   HttpServletResponse response) throws Exception
    {
        response.setContentType ("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter ();
        StringBuffer html = new StringBuffer ("");

        if (StringUtils.isBlank (templateFile))
        {
            html.append ("Can't generate order item");
            writer.print (html.toString ());
        }
        else
        {
            String orderTableTemplate = "";
            if (ComponentContext.orderTemplateMap.containsKey (templateFile))
            {
                orderTableTemplate = ComponentContext.orderTemplateMap.get (templateFile);
            }
            else
            {
                orderTableTemplate = PurchaseChargeUtils.readFileToString (templateFile);
                ComponentContext.orderTemplateMap.put (templateFile, orderTableTemplate);
            }

            List <String> configuredColumnHeaders = getOrderColumnHeaderTemplate (orderTableTemplate);
            if (CollectionUtils.isEmpty (configuredColumnHeaders))
            {
                html.append ("No order header found");
                writer.print (html.toString ());
            }
            else
            {
                // validate
                List <String> availableColumnHeaders = new ArrayList <String> ();
                for (String orderColumnHeader : staticOrderColumnHeaders)
                {
                    if (configuredColumnHeaders.contains (orderColumnHeader))
                    {
                        availableColumnHeaders.add (orderColumnHeader);
                    }
                }
                if (availableColumnHeaders.isEmpty ())
                {
                    html.append ("No correct order header found, expected headers are " + staticOrderColumnHeaders);
                    writer.print (html.toString ());
                }

                if (StringUtils.isNotBlank (orderId))
                {
                    List <OrderOutItem> orderItems = componentContext.getQueryService ().getOrderOutItemByOrder (Long.parseLong (orderId), false);
                    if (!CollectionUtils.isEmpty (orderItems))
                    {
                        String trs = "";
                        for (OrderOutItem orderItem : orderItems)
                        {
                            String tr = "";
                            tr += "<tr>";
                            for (String columnHeader : availableColumnHeaders)
                            {
                                if (staticOrderColumnHeaders[0].equalsIgnoreCase (columnHeader))
                                {
                                    tr += "<td>" + orderItem.getGoods ().getShortCode () + "</td>";
                                }
                                else if (staticOrderColumnHeaders[1].equalsIgnoreCase (columnHeader))
                                {
                                    tr += "<td>" + orderItem.getGoods ().getName () + "</td>";
                                }
                                else if (staticOrderColumnHeaders[2].equalsIgnoreCase (columnHeader))
                                {
                                    tr += "<td>" + orderItem.getGoods ().getUnit ().getName () + "</td>";
                                }
                                else if (staticOrderColumnHeaders[3].equalsIgnoreCase (columnHeader))
                                {
                                    tr += "<td>" + orderItem.getUnitPrice () + "</td>";
                                }
                                else if (staticOrderColumnHeaders[4].equalsIgnoreCase (columnHeader))
                                {
                                    tr += "<td>" + orderItem.getDepository ().getName () + "</td>";
                                }
                                else if (staticOrderColumnHeaders[5].equalsIgnoreCase (columnHeader))
                                {
                                    tr += "<td>" + orderItem.getAmount () + "</td>";
                                }
                                else if (staticOrderColumnHeaders[6].equalsIgnoreCase (columnHeader))
                                {
                                    tr += "<td>" + orderItem.getSum () + "</td>";
                                }
                                else if (staticOrderColumnHeaders[7].equalsIgnoreCase (columnHeader))
                                {
                                    tr += "<td>" + orderItem.getComment () + "</td>";
                                }
                            }
                            tr += "</tr>";
                            trs += tr;
                        }

                        orderTableTemplate = orderTableTemplate.replace ("{:orderContent}", trs);
                        List <Map <String, String>> orderItemCount = countTotalForOrderItems (orderItems);
                        orderTableTemplate = orderTableTemplate.replace ("{:amountSum}",
                                                                         orderItemCount.get (0)
                                                                                       .get (PurchaseChargeConstants.ORDER_AMOUNT));
                        orderTableTemplate = orderTableTemplate.replace ("{:moneySum}",
                                                                         orderItemCount.get (0)
                                                                                       .get (PurchaseChargeConstants.ORDER_SUM));
                    }
                    writer.print (html.append (orderTableTemplate).toString ());
                }
            }
        }
    }

    @RequestMapping (value = "/generateOrderBid", method = RequestMethod.POST)
    public @ResponseBody
    String generateOrderBid (OrderTypeCode orderTypeCode)
    {
        return PurchaseChargeUtils.generateOrderBid (orderTypeCode, "");
    }
    

    @RequestMapping (value = "/searchOrderItem", method = RequestMethod.POST)
    public @ResponseBody
    String searchOrderItem (SearchRequestForm searchRequestForm, HttpServletRequest request)
    {
        OrderItemSearchForm orderItemSearchForm = convert (searchRequestForm);
        List <OrderOutItem> orderItems = DaoContext.getOrderOutItemDao ().findBySearchForm (orderItemSearchForm);
        GenericPagingResult <OrderItemBean> orderPagingResult = new GenericPagingResult <OrderItemBean> ();
        orderPagingResult.setRows (BeanConvertUtils.orderOutItemList2OrderItemBeanList (orderItems));
        orderPagingResult.setTotal (orderItems.size ());
        orderPagingResult.setFooter (countTotalForOrderItems (orderItems));

        JSONObject jsonObject = JSONObject.fromObject (orderPagingResult);
        return jsonObject.toString ();
    }
    
    @RequestMapping (value = "/validateOrder", method = RequestMethod.POST)
    public @ResponseBody
    String validateOrder (@Valid OrderBean bean, BindingResult bindingResult, HttpServletRequest request)
    {
        return "";
    }

    @RequestMapping (value = "/printOrder", method = RequestMethod.POST)
    public void printOrder (OrderPrinter orderPrinter, HttpServletResponse response) throws Exception
    {
        response.setContentType ("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter ();

        StringBuffer html = new StringBuffer ("");
        if (StringUtils.isBlank (orderPrinter.getTemplate ()))
        {
            html.append ("Can't print order");
            writer.print (html.toString ());
        }
        else
        {
            String orderPrintTemplate = "";
            if (ComponentContext.orderTemplateMap.containsKey (orderPrinter.getTemplate ()))
            {
                orderPrintTemplate = ComponentContext.orderTemplateMap.get (orderPrinter.getTemplate ());
            }
            else
            {
                orderPrintTemplate = PurchaseChargeUtils.readFileToString (orderPrinter.getTemplate ());
                ComponentContext.orderTemplateMap.put (orderPrinter.getTemplate (), orderPrintTemplate);
            }

            List <String> configuredColumnHeaders = getOrderColumnHeaderTemplate (orderPrintTemplate);
            if (CollectionUtils.isEmpty (configuredColumnHeaders))
            {
                html.append ("No order header found");
                writer.print (html.toString ());
            }
            else
            {
                // validate
                List <String> availableColumnHeaders = new ArrayList <String> ();
                for (String orderColumnHeader : staticOrderColumnHeaders)
                {
                    if (configuredColumnHeaders.contains (orderColumnHeader))
                    {
                        availableColumnHeaders.add (orderColumnHeader);
                    }
                }
                if (availableColumnHeaders.isEmpty ())
                {
                    html.append ("No correct order header found, expected headers are " + staticOrderColumnHeaders);
                    writer.print (html.toString ());
                }

                if (null != orderPrinter && StringUtils.isNotBlank (orderPrinter.getOrderId ()))
                {
                    OrderOut order = componentContext.getQueryService ()
                                                     .getOrderOut (Long.parseLong (orderPrinter.getOrderId ()));
                    if (null != order)
                    {
                        List <OrderOutItem> orderItems = componentContext.getQueryService ()
                                                                         .getOrderOutItemByOrder (order.getId (), false);
                        if (!CollectionUtils.isEmpty (orderItems))
                        {
                            String trs = "";
                            for (OrderOutItem orderItem : orderItems)
                            {
                                String tr = "";
                                tr += "<tr>";
                                for (String columnHeader : availableColumnHeaders)
                                {
                                    if (staticOrderColumnHeaders[0].equalsIgnoreCase (columnHeader))
                                    {
                                        tr += "<td>" + orderItem.getGoods ().getShortCode () + "</td>";
                                    }
                                    else if (staticOrderColumnHeaders[1].equalsIgnoreCase (columnHeader))
                                    {
                                        tr += "<td>" + orderItem.getGoods ().getName () + "</td>";
                                    }
                                    else if (staticOrderColumnHeaders[2].equalsIgnoreCase (columnHeader))
                                    {
                                        tr += "<td>" + orderItem.getGoods ().getUnit ().getName () + "</td>";
                                    }
                                    else if (staticOrderColumnHeaders[3].equalsIgnoreCase (columnHeader))
                                    {
                                        tr += "<td>" + orderItem.getUnitPrice () + "</td>";
                                    }
                                    else if (staticOrderColumnHeaders[4].equalsIgnoreCase (columnHeader))
                                    {
                                        tr += "<td>" + orderItem.getDepository ().getName () + "</td>";
                                    }
                                    else if (staticOrderColumnHeaders[5].equalsIgnoreCase (columnHeader))
                                    {
                                        tr += "<td>" + orderItem.getAmount () + "</td>";
                                    }
                                    else if (staticOrderColumnHeaders[6].equalsIgnoreCase (columnHeader))
                                    {
                                        tr += "<td>" + orderItem.getSum () + "</td>";
                                    }
                                    else if (staticOrderColumnHeaders[7].equalsIgnoreCase (columnHeader))
                                    {
                                        tr += "<td>" + orderItem.getComment () + "</td>";
                                    }
                                }
                                tr += "</tr>";
                                trs += tr;
                            }

                            orderPrintTemplate = orderPrintTemplate.replace ("{:title}", orderPrinter.getTitle ());
                            orderPrintTemplate = orderPrintTemplate.replace ("{:orderId}", order.getBid () + "");
                            orderPrintTemplate = orderPrintTemplate.replace ("{:printDate}",
                                                                             DateUtils.date2String (new Date (),
                                                                                                    DateUtils.DATE_TIME_PATTERN));
                            orderPrintTemplate = orderPrintTemplate.replace ("{:orderDate}",
                                                                             DateUtils.date2String (order.getDateCreated (),
                                                                                                    DateUtils.DATE_PATTERN));
                            orderPrintTemplate = orderPrintTemplate.replace ("{:customerName}", order.getCustomer ()
                                                                                                     .getShortName ());
                            orderPrintTemplate = orderPrintTemplate.replace ("{:contactName}",
                                                                             orderPrinter.getContactName ());
                            orderPrintTemplate = orderPrintTemplate.replace ("{:contactPhone}",
                                                                             orderPrinter.getContactPhone ());
                            orderPrintTemplate = orderPrintTemplate.replace ("{:contactAddress}",
                                                                             orderPrinter.getContactAddress ());
                            orderPrintTemplate = orderPrintTemplate.replace ("{:orderContent}", trs);
                            List <Map <String, String>> orderItemCount = countTotalForOrderItems (orderItems);
                            orderPrintTemplate = orderPrintTemplate.replace ("{:amountSum}",
                                                                             orderItemCount.get (0)
                                                                                           .get (PurchaseChargeConstants.ORDER_AMOUNT));
                            orderPrintTemplate = orderPrintTemplate.replace ("{:moneySum}",
                                                                             orderItemCount.get (0)
                                                                                           .get (PurchaseChargeConstants.ORDER_SUM));
                            User user = componentContext.getQueryService ().getUser (order.getUserCreatedBy ());
                            if (null != user)
                            {
                                orderPrintTemplate = orderPrintTemplate.replace ("{:operator}", user.getUserId ());
                            }
                            user = componentContext.getQueryService ().getUser (order.getCustomer ().getUserSigned ());
                            if (null != user)
                            {
                                orderPrintTemplate = orderPrintTemplate.replace ("{:salesman}", user.getUserId ());
                            }
                            orderPrintTemplate = orderPrintTemplate.replace ("{:footer}", orderPrinter.getFooter ());
                            writer.print (html.append (orderPrintTemplate).toString ());
                        }
                    }
                }
            }
        }
    }
    
    private List <String> getOrderColumnHeaderTemplate (String templateStr) throws IOException
    {
        Pattern pattern = Pattern.compile ("<th id=\".*@\"");
        Matcher matcher = pattern.matcher (templateStr);
        List <String> configuredColumnHeaders = new ArrayList <String> ();
        while (matcher.find ())
        {
            String th = matcher.group ();
            Pattern pattern2 = Pattern.compile ("\".*@");
            Matcher matcher2 = pattern2.matcher (th);
            while (matcher2.find ())
            {
                String ths = matcher2.group ();
                configuredColumnHeaders.add (ths.substring (1, ths.lastIndexOf ("@")));
            }
        }
        return configuredColumnHeaders;
    }

    private List <Map <String, String>> countTotalForOrderItems (List <OrderOutItem> orderItemList)
    {
        List <Map <String, String>> footer = new ArrayList <Map <String, String>> ();
        if (!CollectionUtils.isEmpty (orderItemList))
        {
            Map <String, String> map = new HashMap <String, String> ();
            int amountTotal = 0;
            float sumTotal = 0;
            for (OrderOutItem ordItem : orderItemList)
            {
                amountTotal += ordItem.getAmount ();
                sumTotal += ordItem.getSum ();
            }
            map.put (PurchaseChargeConstants.ORDER_UNIT_PRICE, "合计");
            map.put (PurchaseChargeConstants.ORDER_AMOUNT, amountTotal + "");
            map.put (PurchaseChargeConstants.ORDER_SUM, sumTotal + "");
            footer.add (map);
        }
        return footer;
    }

    @SuppressWarnings ("unused")
    private List <Map <String, Object>> countOrderPaid (float dealMoney, List <CustomerPayment> payments)
    {
        List <Map <String, Object>> footer = new ArrayList <Map <String, Object>> ();
        if (!CollectionUtils.isEmpty (payments))
        {
            Map <String, Object> map = new HashMap <String, Object> ();
            float countOrderPaid = 0;
            String paidResult = "";
            for (CustomerPayment op : payments)
            {
                // countOrderPaid += op.getPaid ();
            }
            if (dealMoney == countOrderPaid)
            {
                paidResult = "回款完成";
            }
            else if (dealMoney > countOrderPaid)
            {
                paidResult = "欠" + (dealMoney - countOrderPaid);
            }
            map.put (PurchaseChargeConstants.PAY_DATE, "状态");
            map.put (PurchaseChargeConstants.PAID, paidResult);
            footer.add (map);
        }
        else
        {
            Map <String, Object> map = new HashMap <String, Object> ();
            map.put (PurchaseChargeConstants.PAY_DATE, "状态");
            map.put (PurchaseChargeConstants.PAID, "尚无回款");
            footer.add (map);
        }
        return footer;
    }

    private List <Map <String, String>> countTotalForOrders (List <OrderOut> orderList)
    {
        List <Map <String, String>> footer = new ArrayList <Map <String, String>> ();
        if (!CollectionUtils.isEmpty (orderList))
        {
            Map <String, String> map = new HashMap <String, String> ();
            float dealMoneyTotal = 0;
            // float paidMoneyTotal = 0;
            float receivableMoneyTotal = 0;
            float profitMoneyTotal = 0;
            for (OrderOut ord : orderList)
            {
                if (ord.getStatusCode () == OrderStatusCode.COMPLETED || ord.getStatusCode () == OrderStatusCode.NEW)
                {
                    dealMoneyTotal += ord.getDealMoney ();
                    receivableMoneyTotal += ord.getReceivable ();
                    // paidMoneyTotal += ord.getPaidMoney ();
                    profitMoneyTotal += ord.getProfit ();
                }
            }
            // map.put (PurchaseChargeConstants.CUSTOMER_NAME, "当页合计");
            map.put ("id", "合计");
            map.put (PurchaseChargeConstants.DEAL_MONEY, dealMoneyTotal + "");
            map.put (PurchaseChargeConstants.RECEIVABLE_MONEY, receivableMoneyTotal + "");
            // map.put (PurchaseChargeConstants.PAID_MONEY, paidMoneyTotal +
            // "");
            map.put (PurchaseChargeConstants.PROFIT_MONEY, profitMoneyTotal + "");
            footer.add (map);
        }
        return footer;
    }

    public static String parseSortField (final String sortField)
    {
        String sortFieldAfterParse = PurchaseChargeConstants.CUSTOMER_DOT_SHORT_NAME;
        if (!StringUtils.isBlank (sortField))
        {
            sortFieldAfterParse = sortField;
            if (PurchaseChargeConstants.CUSTOMER_NAME.equalsIgnoreCase (sortField))
            {
                sortFieldAfterParse = PurchaseChargeConstants.CUSTOMER_DOT_SHORT_NAME;
            }
            else if (PurchaseChargeConstants.PAY_TIME.equalsIgnoreCase (sortField))
            {
                sortFieldAfterParse = PurchaseChargeConstants.PAY_DATE;
            }
            else if (PurchaseChargeConstants.CREATE_TIME.equalsIgnoreCase (sortField))
            {
                sortFieldAfterParse = PurchaseChargeConstants.CREATE_DATE;
            }
            else if (PurchaseChargeConstants.UPDATE_TIME.equalsIgnoreCase (sortField))
            {
                sortFieldAfterParse = PurchaseChargeConstants.UPDATE_DATE;
            }
        }
        return sortFieldAfterParse;
    }

    public static List <OrderItemBean> parseOrderItem (OrderBean orderBean)
    {
        List <OrderItemBean> orderItemBeans = new ArrayList <OrderItemBean> ();
        String orderItemListStr = orderBean.getOrderItemList ();

        if (StringUtils.isNotBlank (orderItemListStr))
        {
            String[] orderItemArr = orderItemListStr.split (";");
            if (orderItemArr.length > 0)
            {
                for (String orderItem : orderItemArr)
                {
                    if (StringUtils.isNotBlank (orderItem))
                    {
                        String[] itemArr = orderItem.split (",");
                        OrderItemBean orderItemBean = new OrderItemBean ();
                        orderItemBean.setId (itemArr[0]);
                        orderItemBean.setGoodsId (itemArr[1]);
                        orderItemBean.setGoodsUnit (itemArr[2]);
                        orderItemBean.setUnitPrice (StringUtils.isNotBlank (itemArr[3]) ? Float.parseFloat (itemArr[3])
                                                                                       : 0);
                        orderItemBean.setAmount (StringUtils.isNotBlank (itemArr[4]) ? Integer.parseInt (itemArr[4])
                                                                                    : 0);
                        orderItemBean.setSum (StringUtils.isNotBlank (itemArr[5]) ? Float.parseFloat (itemArr[5]) : 0);
                        if (StringUtils.isNotBlank (itemArr[6]))
                        {
                            GoodsDepositoryBean depositoryBean = new GoodsDepositoryBean ();
                            depositoryBean.setId (itemArr[6]);
                            orderItemBean.setDepositoryBean (depositoryBean);
                        }
                        try
                        {
                            orderItemBean.setComment (BeanConvertUtils.getString (itemArr[7]));
                        }
                        catch (ArrayIndexOutOfBoundsException e)
                        {
                            orderItemBean.setComment ("");
                        }
                        orderItemBeans.add (orderItemBean);
                    }
                }
            }
        }

        return orderItemBeans;
    }
    
    public static OrderItemSearchForm convert (SearchRequestForm searchRequestForm) 
    {
        if(null != searchRequestForm) 
        {
            OrderItemSearchForm orderItemSearchForm = new OrderItemSearchForm ();
            if(StringUtils.isNotBlank (searchRequestForm.getStartDate ())) 
            {
                orderItemSearchForm.setStartDate (DateUtils.string2Date (searchRequestForm.getStartDate () + " 00:00:00.000",
                                                                         DateUtils.DATE_TIME_MILLISECOND_PATTERN));
            }
            if(StringUtils.isNotBlank (searchRequestForm.getEndDate ())) 
            {
                orderItemSearchForm.setEndDate (DateUtils.string2Date (searchRequestForm.getEndDate () + " 23:59:59.999",
                                                                       DateUtils.DATE_TIME_MILLISECOND_PATTERN));
            }
            long goodsId = 0;
            try 
            {
                if(StringUtils.isNotBlank (searchRequestForm.getGoodsId ())) 
                {
                    goodsId = Long.parseLong (searchRequestForm.getGoodsId ());
                }
            }
            catch (Exception e) {
            }
            orderItemSearchForm.setGoodsId (goodsId);
            
            long customerId = 0;
            try 
            {
                if(StringUtils.isNotBlank (searchRequestForm.getCustomerId ())) 
                {
                    customerId = Long.parseLong (searchRequestForm.getCustomerId ());
                }
            }
            catch (Exception e) {
            }
            orderItemSearchForm.setCustomerId (customerId);
            
            long userCreatedBy = 0;
            try 
            {
                if(StringUtils.isNotBlank (searchRequestForm.getUserCreate ())) 
                {
                    userCreatedBy = Long.parseLong (searchRequestForm.getUserCreate ());
                }
            }
            catch (Exception e) {
            }
            orderItemSearchForm.setUserCreatedBy (userCreatedBy);
            return orderItemSearchForm;
        }
        return null;
    }

}
