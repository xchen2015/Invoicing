package com.pinfly.purchasecharge.component.controller.out;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.ActionResult.ActionResultStatus;
import com.pinfly.purchasecharge.component.bean.DataGridRequestForm;
import com.pinfly.purchasecharge.component.bean.DeliveryCompanyBean;
import com.pinfly.purchasecharge.component.bean.GenericPagingResult;
import com.pinfly.purchasecharge.component.bean.OrderBean;
import com.pinfly.purchasecharge.component.bean.OrderDeliveryBean;
import com.pinfly.purchasecharge.component.bean.SearchRequestForm;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.controller.GenericController;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.BeanConvertUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.OrderSearchForm;
import com.pinfly.purchasecharge.core.model.OrderTypeCode;
import com.pinfly.purchasecharge.core.model.persistence.DeliveryCompany;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderDelivery;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOut;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.User;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.dal.DaoContext;

@Controller
@RequestMapping ("/outOrderDelivery")
public class OutOrderDeliveryManager extends GenericController <OrderDeliveryBean>
{
    private static final Logger logger = Logger.getLogger (OutOrderDeliveryManager.class);
    private static String outOrderDeliveryMessage = ComponentMessage.createMessage ("ORDER_DELIVERY", "ORDER_DELIVERY")
            .getI18nMessageCode ();

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "outOrderDelivery";
    }

    @Override
    protected String getModelBySearchForm (DataGridRequestForm dataGridRequestForm,
                                           SearchRequestForm searchRequestForm, HttpServletRequest request)
    {
        logger.debug (dataGridRequestForm);
        GenericPagingResult <OrderBean> orderPagingResult = new GenericPagingResult <OrderBean> ();
        
        int page = dataGridRequestForm.getPage () - 1;
        int size = dataGridRequestForm.getRows ();
        String sortField = OutOrderManager.parseSortField (dataGridRequestForm.getSort ());
        String order = dataGridRequestForm.getOrder ();
        Pageable pageable = new PageRequest (page, size, new Sort ("asc".equalsIgnoreCase (order) ? Direction.ASC
                                                                                                 : Direction.DESC,
                                                                   sortField));

        LoginUser loginUser = componentContext.getLoginUser (request);
        if (null != loginUser)
        {
            String type = request.getParameter ("type");
            OrderTypeCode typeCode = OrderTypeCode.OUT.toString ().equals (type) ? OrderTypeCode.OUT
                                                                                : OrderTypeCode.OUT_RETURN;
            OrderSearchForm searchForm = BeanConvertUtils.searchRequestForm2OrderSearchForm (searchRequestForm);
            Page <OrderOut> orderPage = null;
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
            }
            else
            {
                orderPage = componentContext.getQueryService ().findOrderOutBySearchForm (typeCode, pageable,
                                                                                          searchForm,
                                                                                          loginUser.getUserId (),
                                                                                          loginUser.isAdmin ());
            }

            long total = orderPage.getTotalElements ();
            orderPagingResult.setRows (BeanConvertUtils.orderOutList2OrderBeanList (orderPage.getContent ()));
            orderPagingResult.setTotal (total);
        }
        JSONObject jsonObject = JSONObject.fromObject (orderPagingResult);
        return jsonObject.toString ();
    }

    @Override
    protected String updateModel (@Valid OrderDeliveryBean bean, BindingResult bindingResult, HttpServletRequest request)
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
                bean.setUserCreated (loginUser.getUserId ());
                OrderDelivery orderDelivery = convert (bean);
                DeliveryCompany newDeliveryCompany = componentContext.getQueryService ().getDeliveryCompany (orderDelivery.getCompany ().getId ());
                OrderDelivery oldOrderDelivery = DaoContext.getOrderDeliveryDao ().findOne (orderDelivery.getId ());
                DeliveryCompany oldDeliveryCompany = null;
                if(null != oldOrderDelivery) 
                {
                    oldDeliveryCompany = oldOrderDelivery.getCompany ();
                }
                try
                {
                    orderDelivery = componentContext.getPersistenceService ().updateOrderDelivery (orderDelivery);
                    if (null != orderDelivery)
                    {
                        ar = createUpdateSuccessResult (outOrderDeliveryMessage);
                    }
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createUpdateFailedResult (outOrderDeliveryMessage);
                }

                if (ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventName = LogEventName.createEventName ("UpdateOrderDelivery",
                                                                            "LogEvent.UpdateOrderDelivery",
                                                                            request.getLocale ());
                        String logComment = newDeliveryCompany.getName () + " " +  orderDelivery.getAmount () + " " + orderDelivery.getNumber () + " " + orderDelivery.getFee ();
                        if(null != oldDeliveryCompany && null != oldOrderDelivery) 
                        {
                            logComment = LogUtil.createLogComment (oldDeliveryCompany, newDeliveryCompany)
                                    + " " + LogUtil.createLogComment (oldOrderDelivery, orderDelivery);
                        }
                        componentContext.getLogService ().log (logEventName, loginUser.getUid (),
                                                               logEventName + ": " + logComment);
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
            return AjaxUtils.getJsonObject (ar);
        }
    }
    
    public static OrderDelivery convert (OrderDeliveryBean bean)
    {
        OrderDelivery orderDelivery = null;
        if (null != bean)
        {
            orderDelivery = new OrderDelivery ();
            if (StringUtils.isNotBlank (bean.getId ()))
            {
                orderDelivery.setId (Long.parseLong (bean.getId ()));
            }
            if (null != bean.getDeliveryCompanyBean ()
                && StringUtils.isNotBlank (bean.getDeliveryCompanyBean ().getId ()))
            {
                DeliveryCompany deliveryCompany = new DeliveryCompany ();
                deliveryCompany.setId (Long.parseLong (bean.getDeliveryCompanyBean ().getId ()));
                orderDelivery.setCompany (deliveryCompany);
            }
            orderDelivery.setAmount (bean.getAmount ());
            orderDelivery.setFee (bean.getFee ());
            orderDelivery.setPaid (bean.getPaid ());
            orderDelivery.setNumber (bean.getNumber ());
            orderDelivery.setDateCreated (DateUtils.string2Date (bean.getDateCreated (), DateUtils.DATE_PATTERN));
            if (null != bean.getOrderBean () && StringUtils.isNotBlank (bean.getOrderBean ().getId ()))
            {
                OrderOut order = new OrderOut ();
                order.setId (Long.parseLong (bean.getOrderBean ().getId ()));
                orderDelivery.setOrderOut (order);
            }
            User user = DaoContext.getUserDao ().findByUserId (bean.getUserCreated ());
            if (null != user)
            {
                orderDelivery.setUserCreatedBy (user.getId ());
            }

        }
        return orderDelivery;
    }

    public static OrderDeliveryBean convert (OrderDelivery bean)
    {
        OrderDeliveryBean orderDelivery = null;
        if (null != bean)
        {
            orderDelivery = new OrderDeliveryBean ();
            orderDelivery.setId (bean.getId () + "");
            if (null != bean.getCompany ())
            {
                DeliveryCompanyBean deliveryCompany = new DeliveryCompanyBean ();
                deliveryCompany.setId (bean.getCompany ().getId () + "");
                deliveryCompany.setName (bean.getCompany ().getName ());
                orderDelivery.setDeliveryCompanyBean (deliveryCompany);
            }
            orderDelivery.setAmount (bean.getAmount ());
            orderDelivery.setFee (bean.getFee ());
            orderDelivery.setPaid (bean.getPaid ());
            orderDelivery.setNumber (bean.getNumber ());
            orderDelivery.setDateCreated (DateUtils.date2String (bean.getDateCreated (), DateUtils.DATE_PATTERN));
            if (null != bean.getOrderOut ())
            {
                OrderBean order = new OrderBean ();
                order.setId (bean.getOrderOut ().getId () + "");
                orderDelivery.setOrderBean (order);
            }
            User user = DaoContext.getUserDao ().findOne (bean.getUserCreatedBy ());
            if (null != user)
            {
                orderDelivery.setUserCreated (user.getUserId ());
            }

        }
        return orderDelivery;
    }

}
