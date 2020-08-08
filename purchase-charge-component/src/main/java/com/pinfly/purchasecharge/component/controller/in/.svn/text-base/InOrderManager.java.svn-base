package com.pinfly.purchasecharge.component.controller.in;

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
import com.pinfly.purchasecharge.component.bean.OrderBean;
import com.pinfly.purchasecharge.component.bean.OrderItemBean;
import com.pinfly.purchasecharge.component.bean.PaymentAccountBean;
import com.pinfly.purchasecharge.component.bean.SearchRequestForm;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.controller.GenericController;
import com.pinfly.purchasecharge.component.controller.out.OutOrderManager;
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
import com.pinfly.purchasecharge.core.model.persistence.in.OrderIn;
import com.pinfly.purchasecharge.core.model.persistence.in.OrderInItem;
import com.pinfly.purchasecharge.core.model.persistence.in.ProviderPayment;
import com.pinfly.purchasecharge.core.util.Arith;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.core.util.PurchaseChargeConstants;
import com.pinfly.purchasecharge.core.util.PurchaseChargeUtils;
import com.pinfly.purchasecharge.dal.DaoContext;
import com.pinfly.purchasecharge.service.aspect.ServiceAspect;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/inOrder")
public class InOrderManager extends GenericController <OrderBean>
{
    private static final Logger logger = Logger.getLogger (InOrderManager.class);
    private String inOrderMessage = ComponentMessage.createMessage ("IN_ORDER", "IN_ORDER").getI18nMessageCode ();
    private String inOrderStatusMessage = ComponentMessage.createMessage ("IN_ORDER_STATUS", "IN_ORDER_STATUS")
                                                          .getI18nMessageCode ();
    @Autowired
    ServiceAspect serviceAspect;

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "inOrderManagement";
    }
    
    @RequestMapping (value = "/getAddView")
    protected String getAddView ()
    {
        return "inOrderAdd";
    }

    @RequestMapping (value = "/getOrderItemQueryView")
    protected String getOrderItemQueryView ()
    {
        return "inOrderDetailQuery";
    }

    @Override
    protected String getJsName ()
    {
        return "javascript/inOrderMgmtJS";
    }

    @Override
    protected String getCssName ()
    {
        return "stylesheet/inOrderMgmtCSS";
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
            if (OrderTypeCode.IN.toString ().equals (type))
            {
                typeCode = OrderTypeCode.IN;
            }
            else if (OrderTypeCode.IN_RETURN.toString ().equals (type))
            {
                typeCode = OrderTypeCode.IN_RETURN;
            }
            OrderSearchForm searchForm = BeanConvertUtils.searchRequestForm2OrderSearchForm (searchRequestForm);
            Page <OrderIn> orderPage = null;
            float sumReceivable = 0;
            float sumPaidMoney = 0;
            List <Map <String, String>> footer = new ArrayList <Map <String, String>> ();
            if (null != searchForm && StringUtils.isNotBlank (searchForm.getOrderBid ()))
            {
                OrderIn ord = componentContext.getQueryService ().getOrderIn (searchForm.getOrderBid ());
                List <OrderIn> list = new ArrayList <OrderIn> ();
                orderPage = new PageImpl <OrderIn> (list, pageable, 0);
                if (null != ord)
                {
                    list.add (ord);
                    orderPage = new PageImpl <OrderIn> (list, pageable, 1);
                }
                footer = countTotalForOrders (orderPage.getContent ());
            }
            else
            {
                orderPage = componentContext.getQueryService ().findOrderInBySearchForm (typeCode, pageable,
                                                                                         searchForm,
                                                                                         loginUser.getUserId (),
                                                                                         loginUser.isAdmin ());
                sumReceivable = componentContext.getQueryService ()
                        .countOrderInReceivableBySearchForm (typeCode, searchForm,
                                                             loginUser.getUserId (),
                                                             loginUser.isAdmin ());
                sumPaidMoney = componentContext.getQueryService ()
                                                .countOrderInPaidMoneyBySearchForm (typeCode, searchForm,
                                                                                     loginUser.getUserId (),
                                                                                     loginUser.isAdmin ());
                
                Map <String, String> map = new HashMap <String, String> ();
                map.put ("bid", "合计");
                map.put (PurchaseChargeConstants.RECEIVABLE_MONEY, Arith.round (sumReceivable, -1) + "");
                map.put (PurchaseChargeConstants.PAID_MONEY, Arith.round (sumPaidMoney, -1) + "");
                footer.add (map);
            }
            long total = orderPage.getTotalElements ();
            List <OrderBean> orderBeans = new ArrayList <OrderBean> ();
            PaymentTypeCode paymentTypeCode = OrderTypeCode.IN.equals (typeCode) ? PaymentTypeCode.IN_ORDER : PaymentTypeCode.IN_ORDER_RETURN;
            for(OrderIn orderIn : orderPage.getContent ()) 
            {
                PaymentAccount paymentAccount = componentContext.getQueryService ().getPaymentAccountByOrderId (orderIn.getTypeCode (), orderIn.getId () + "", paymentTypeCode);
                orderBeans.add (BeanConvertUtils.orderIn2OrderBean (orderIn, paymentAccount));
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
        if (StringUtils.isNotBlank (orderId))
        {
            List <OrderInItem> orderItems = componentContext.getQueryService ().getOrderInItemByOrder (Long.parseLong (orderId), false);
            if (!CollectionUtils.isEmpty (orderItems))
            {
                GenericPagingResult <OrderItemBean> orderPagingResult = new GenericPagingResult <OrderItemBean> ();
                orderPagingResult.setRows (BeanConvertUtils.orderInItemList2OrderItemBeanList (orderItems));
                orderPagingResult.setTotal (orderItems.size ());
                orderPagingResult.setFooter (countTotalForOrderItems (orderItems));

                JSONObject jsonObject = JSONObject.fromObject (orderPagingResult);
                return jsonObject.toString ();
            }
        }
        return "";
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
                OrderIn order = null;
                try
                {
                    bean.setUserCreated (loginUser.getUserId ());
                    bean.setOrderItemBeans (OutOrderManager.parseOrderItem (bean));
                    order = BeanConvertUtils.orderBean2OrderIn (bean);
                    
                    PaymentAccount paymentAccount = null;
                    PaymentAccountBean accountBean = bean.getPaymentAccountBean ();
                    if(null != accountBean && StringUtils.isNotBlank (accountBean.getId ())) 
                    {
                        paymentAccount = BeanConvertUtils.paymentAccountBean2PaymentAccount (accountBean);
                    }
                    order = componentContext.getPersistenceService ().addOrderIn (order, paymentAccount);
                    serviceAspect.executeAfterChangeGoods ();
                    ar = createAddSuccessResult (inOrderMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createAddFailedResult (inOrderMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("AddInOrderImport",
                                                                            "LogEvent.AddInOrderImport",
                                                                            request.getLocale ());
                        if (OrderTypeCode.IN_RETURN.equals (order.getTypeCode ()))
                        {
                            logEventName = LogEventName.createEventName ("AddInOrderExport",
                                                                         "LogEvent.AddInOrderExport",
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
    public String updateModel (@Valid OrderBean bean, BindingResult bindingResult, HttpServletRequest request)
    {
        if (bindingResult.hasErrors ())
        {
            logger.warn (bindingResult.getAllErrors ());
            return createBadRequestResult (null);
        }
        else
        {
            ActionResult ar = ActionResult.createActionResult ().build ();
            try
            {
                LoginUser loginUser = componentContext.getLoginUser (request);
                if (null != loginUser)
                {
                    bean.setUserCreated (loginUser.getUserId ());
                    // componentContext.getPersistenceService
                    // ().updateOrderStatus (Long.parseLong
                    // (bean.getId ()), bean.getStatusCode (),
                    // bean.getComment ());
                    ar = createUpdateSuccessResult (inOrderStatusMessage);
                }
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage (), e);
                ar = createUpdateFailedResult (inOrderStatusMessage);
            }
            return AjaxUtils.getJsonObject (ar);
        }
    }

    @Override
    public String deleteModels (@RequestParam String ids, HttpServletRequest request)
    {
        if (null != ids && ids.trim ().length () > 0)
        {
            List <OrderIn> orders = new ArrayList <OrderIn> ();
            OrderIn order;
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    order = new OrderIn ();
                    order.setId (Long.parseLong (typeId));
                    orders.add (order);
                }
            }
            else
            {
                order = new OrderIn ();
                order.setId (Long.parseLong (ids));
                orders.add (order);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    componentContext.getPersistenceService ().deleteOrderIn (orders);
                    ar = createDeleteSuccessResult (inOrderMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (inOrderMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("DeleteInOrderImport",
                                                                            "LogEvent.DeleteInOrderImport",
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
            logger.warn (ActionResultStatus.BAD_REQUEST.toString ());
            return createBadRequestResult (null);
        }
    }

    @RequestMapping (value = "/getOrderItemsByOrderIds", method = RequestMethod.POST)
    public @ResponseBody
    String getOrderItemsByOrderIds (@RequestParam String orderIds)
    {
        if (StringUtils.isNotBlank (orderIds))
        {
            List <OrderInItem> orderItems = new ArrayList <OrderInItem> ();
            if (orderIds.indexOf (";") != -1)
            {
                String[] idArr = orderIds.split (";");
                for (String orderId : idArr)
                {
                    long oId = Long.parseLong (orderId);
                    orderItems.addAll (componentContext.getQueryService ().getOrderInItemByOrder (oId, true));
                }
            }
            else
            {
                long oId = Long.parseLong (orderIds);
                orderItems.addAll (componentContext.getQueryService ().getOrderInItemByOrder (oId, true));
            }

            if (!CollectionUtils.isEmpty (orderItems))
            {
                GenericPagingResult <OrderItemBean> orderPagingResult = new GenericPagingResult <OrderItemBean> ();
                orderPagingResult.setRows (BeanConvertUtils.orderInItemList2OrderItemBeanList (orderItems));
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
        List <OrderItemBean> orderInItems = new ArrayList <OrderItemBean> ();
        if (StringUtils.isNotBlank (goodsId))
        {
            long gid = Long.parseLong (goodsId);
            Timestamp end = DateUtils.date2Timestamp (new Date ());
            Timestamp start = DateUtils.date2Timestamp (DateUtils.getDateBefore (end, 90));
            List <OrderIn> orderIns = DaoContext.getOrderInDao ().findByRangeCreateDate (start, end);
            for (OrderIn orderIn : orderIns)
            {
                List <OrderInItem> orderItems = componentContext.getQueryService ()
                                                                .getOrderInItemByOrder (orderIn.getId (), true);
                for (OrderInItem orderInItem : orderItems)
                {
                    if (null != orderInItem.getGoods () && gid == orderInItem.getGoods ().getId ())
                    {
                        orderInItems.add (BeanConvertUtils.orderInItem2OrderItemBean (orderInItem));
                    }
                }
            }
        }

        JSONArray jsonArr = JSONArray.fromObject (orderInItems);
        return jsonArr.toString ();
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
                for (String orderColumnHeader : OutOrderManager.staticOrderColumnHeaders)
                {
                    if (configuredColumnHeaders.contains (orderColumnHeader))
                    {
                        availableColumnHeaders.add (orderColumnHeader);
                    }
                }
                if (availableColumnHeaders.isEmpty ())
                {
                    html.append ("No correct order header found, expected headers are "
                                 + OutOrderManager.staticOrderColumnHeaders);
                    writer.print (html.toString ());
                }

                if (StringUtils.isNotBlank (orderId))
                {
                    List <OrderInItem> orderItems = componentContext.getQueryService ().getOrderInItemByOrder (Long.parseLong (orderId), false);
                    if (!CollectionUtils.isEmpty (orderItems))
                    {
                        String trs = "";
                        for (OrderInItem orderItem : orderItems)
                        {
                            String tr = "";
                            tr += "<tr>";
                            for (String columnHeader : availableColumnHeaders)
                            {
                                if (OutOrderManager.staticOrderColumnHeaders[0].equalsIgnoreCase (columnHeader))
                                {
                                    tr += "<td>" + orderItem.getGoods ().getShortCode () + "</td>";
                                }
                                else if (OutOrderManager.staticOrderColumnHeaders[1].equalsIgnoreCase (columnHeader))
                                {
                                    tr += "<td>" + orderItem.getGoods ().getName () + "</td>";
                                }
                                else if (OutOrderManager.staticOrderColumnHeaders[2].equalsIgnoreCase (columnHeader))
                                {
                                    tr += "<td>" + orderItem.getGoods ().getUnit ().getName () + "</td>";
                                }
                                else if (OutOrderManager.staticOrderColumnHeaders[3].equalsIgnoreCase (columnHeader))
                                {
                                    tr += "<td>" + orderItem.getUnitPrice () + "</td>";
                                }
                                else if (OutOrderManager.staticOrderColumnHeaders[4].equalsIgnoreCase (columnHeader))
                                {
                                    tr += "<td>" + orderItem.getDepository ().getName () + "</td>";
                                }
                                else if (OutOrderManager.staticOrderColumnHeaders[5].equalsIgnoreCase (columnHeader))
                                {
                                    tr += "<td>" + orderItem.getAmount () + "</td>";
                                }
                                else if (OutOrderManager.staticOrderColumnHeaders[6].equalsIgnoreCase (columnHeader))
                                {
                                    tr += "<td>" + orderItem.getSum () + "</td>";
                                }
                                else if (OutOrderManager.staticOrderColumnHeaders[7].equalsIgnoreCase (columnHeader))
                                {
                                    tr += "<td>" + BeanConvertUtils.getString (orderItem.getComment ()) + "</td>";
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
    
    @RequestMapping (value = "/getLatestGoodsUnitPrice", method = RequestMethod.POST)
    public @ResponseBody
    String getLatestGoodsUnitPrice (@RequestParam String providerId, @RequestParam String goodsId)
    {
        List <OrderItemBean> orderItemList = new ArrayList <OrderItemBean> ();
        if (StringUtils.isNotBlank (providerId) && StringUtils.isNotBlank (goodsId))
        {
            long goodsUid = Long.parseLong (goodsId);
            List <OrderIn> orderList = componentContext.getQueryService ()
                                                        .getOrderInByProvider (Long.parseLong (providerId));
            if (!CollectionUtils.isEmpty (orderList))
            {
                for (OrderIn order : orderList)
                {
                    List <OrderInItem> orderItems = componentContext.getQueryService ().getOrderInItemByOrder (order.getId (), false);
                    if (!CollectionUtils.isEmpty (orderItems))
                    {
                        for (OrderInItem orderItem : orderItems)
                        {
                            if (goodsUid == orderItem.getGoods ().getId ())
                            {
                                OrderItemBean orderItemBean = BeanConvertUtils.orderInItem2OrderItemBean (orderItem);
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
                latestItem.setUnitPrice (goods.getImportPrice ());
            }
        }
        return JSONObject.fromObject (latestItem).toString ();
    }
    
    @RequestMapping (value = "/searchOrderItem", method = RequestMethod.POST)
    public @ResponseBody
    String searchOrderItem (SearchRequestForm searchRequestForm, HttpServletRequest request)
    {
        OrderItemSearchForm orderItemSearchForm = OutOrderManager.convert (searchRequestForm);
        List <OrderInItem> orderItems = DaoContext.getOrderInItemDao ().findBySearchForm (orderItemSearchForm);
        
        GenericPagingResult <OrderItemBean> orderPagingResult = new GenericPagingResult <OrderItemBean> ();
        orderPagingResult.setRows (BeanConvertUtils.orderInItemList2OrderItemBeanList (orderItems));
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

    private List <Map <String, String>> countTotalForOrderItems (List <OrderInItem> orderItemList)
    {
        List <Map <String, String>> footer = new ArrayList <Map <String, String>> ();
        if (!CollectionUtils.isEmpty (orderItemList))
        {
            Map <String, String> map = new HashMap <String, String> ();
            int amountTotal = 0;
            float sumTotal = 0;
            for (OrderInItem ordItem : orderItemList)
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
    private List <Map <String, Object>> countOrderPaid (float dealMoney, List <ProviderPayment> payments)
    {
        List <Map <String, Object>> footer = new ArrayList <Map <String, Object>> ();
        if (!CollectionUtils.isEmpty (payments))
        {
            Map <String, Object> map = new HashMap <String, Object> ();
            float countOrderPaid = 0;
            String paidResult = "";
            for (ProviderPayment op : payments)
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

    private List <Map <String, String>> countTotalForOrders (List <OrderIn> orderList)
    {
        List <Map <String, String>> footer = new ArrayList <Map <String, String>> ();
        if (!CollectionUtils.isEmpty (orderList))
        {
            Map <String, String> map = new HashMap <String, String> ();
            float dealMoneyTotal = 0;
            float receivableMoneyTotal = 0;
            // float paidMoneyTotal = 0;
            for (OrderIn ord : orderList)
            {
                if (ord.getStatusCode () == OrderStatusCode.COMPLETED || ord.getStatusCode () == OrderStatusCode.NEW)
                {
                    dealMoneyTotal += ord.getDealMoney ();
                    receivableMoneyTotal += ord.getReceivable ();
                    // paidMoneyTotal += ord.getPaidMoney ();
                }
            }
            // footer.put (PurchaseChargeConstants.CUSTOMER_NAME, "当页合计");
            map.put ("id", "合计");
            map.put (PurchaseChargeConstants.DEAL_MONEY, dealMoneyTotal + "");
            map.put (PurchaseChargeConstants.RECEIVABLE_MONEY, receivableMoneyTotal + "");
            // footer.put (PurchaseChargeConstants.PAID_MONEY, paidMoneyTotal +
            // "");
            footer.add (map);
        }
        return footer;
    }

    private String parseSortField (final String sortField)
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
        }
        return sortFieldAfterParse;
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

}
