package com.pinfly.purchasecharge.dal.out;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pinfly.purchasecharge.core.model.OrderSearchForm;
import com.pinfly.purchasecharge.core.model.OrderStatusCode;
import com.pinfly.purchasecharge.core.model.OrderTypeCode;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOut;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;
import com.pinfly.purchasecharge.dal.exception.PCDalException;

public interface OrderOutDao extends MyGenericDao <OrderOut, Long>
{
    public OrderOut findByBid (String bid);
    
    public List <OrderOut> findByCustomerName (String customerName);

    public List <OrderOut> findByCustomerId (long customerId);

    public List <OrderOut> findByOrderStatusCode (OrderStatusCode statusCode);

    public List <OrderOut> findOldOrder (OrderStatusCode statusCode, Timestamp date);

    public List <OrderOut> findByUserSigned (long userId);

    public List <OrderOut> findByRangeCreateDate (OrderTypeCode typeCode, OrderStatusCode statusCode, Timestamp start,
                                                  Timestamp end);
    
    public List <OrderOut> findByRangeCreateDate (Timestamp start, Timestamp end);

    public int updateStatus (OrderStatusCode statusCode, String comment, long id, Timestamp updateTime);

    public int updateProfit (float profit, long id);

    public int updateReceivable (float receivable, long id);

    public Page <OrderOut> findByFuzzy (OrderTypeCode typeCode, Pageable pageable, String searchKey, long signUser,
                                        boolean admin) throws PCDalException;

    public Page <OrderOut> findBySearchForm (OrderTypeCode typeCode, Pageable pageable, OrderSearchForm searchForm,
                                             long signUser, boolean admin) throws PCDalException;

    public Page <OrderOut> findBySearchForm (Pageable pageable, OrderSearchForm searchForm, long signUser, boolean admin)
                                                                                                                         throws PCDalException;
    
    public float countReceivableBy (OrderTypeCode typeCode, OrderSearchForm searchForm, long signUser, boolean admin);
    
    public float countReceivableBy (OrderSearchForm searchForm, long signUser, boolean admin);

    public float countPaidMoneyBy (OrderTypeCode typeCode, OrderSearchForm searchForm, long signUser, boolean admin);

    public float countPaidMoneyBy (OrderSearchForm searchForm, long signUser, boolean admin);

    public float countProfitBy (OrderTypeCode typeCode, OrderSearchForm searchForm, long signUser, boolean admin);

    public float countProfitBy (OrderSearchForm searchForm, long signUser, boolean admin);

    public OrderOut findLast (long customerId);

    public Timestamp findOldest ();
}
