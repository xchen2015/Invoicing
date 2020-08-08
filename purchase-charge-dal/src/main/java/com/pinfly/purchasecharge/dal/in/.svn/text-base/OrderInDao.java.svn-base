package com.pinfly.purchasecharge.dal.in;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pinfly.purchasecharge.core.model.OrderSearchForm;
import com.pinfly.purchasecharge.core.model.OrderStatusCode;
import com.pinfly.purchasecharge.core.model.OrderTypeCode;
import com.pinfly.purchasecharge.core.model.persistence.in.OrderIn;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;
import com.pinfly.purchasecharge.dal.exception.PCDalException;

public interface OrderInDao extends MyGenericDao <OrderIn, Long>
{
    OrderIn findByBid (String bid);
    
    public List <OrderIn> findByProviderName (String customerName);

    public List <OrderIn> findByProviderId (long customerId);

    public List <OrderIn> findByOrderStatusCode (OrderStatusCode statusCode);

    public List <OrderIn> findByUserSigned (long userId);

    public List <OrderIn> findByRangeCreateDate (OrderTypeCode typeCode, OrderStatusCode statusCode, Timestamp start,
                                                 Timestamp end);
    public List <OrderIn> findByRangeCreateDate (Timestamp start, Timestamp end);

    public int updateStatus (OrderStatusCode statusCode, String comment, long id);

    public int updateReceivable (float receivable, long id);

    public Page <OrderIn> findByFuzzy (OrderTypeCode typeCode, Pageable pageable, String searchKey, long signUser,
                                       boolean admin) throws PCDalException;

    public Page <OrderIn> findBySearchForm (OrderTypeCode typeCode, Pageable pageable, OrderSearchForm searchForm,
                                            long signUser, boolean admin) throws PCDalException;

    public Page <OrderIn> findBySearchForm (Pageable pageable, OrderSearchForm searchForm, long signUser, boolean admin)
                                                                                                                        throws PCDalException;
    
    public float countReceivableBy (OrderSearchForm searchForm, long signUser, boolean admin);
    
    public float countReceivableBy (OrderTypeCode orderType, OrderSearchForm searchForm, long signUser, boolean admin);

    public float countPaidMoneyBy (OrderSearchForm searchForm, long signUser, boolean admin);
    
    public float countPaidMoneyBy (OrderTypeCode orderType, OrderSearchForm searchForm, long signUser, boolean admin);

    public OrderIn findLast (long providerId);

}
