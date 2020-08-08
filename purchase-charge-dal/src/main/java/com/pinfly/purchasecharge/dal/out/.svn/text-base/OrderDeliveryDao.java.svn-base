package com.pinfly.purchasecharge.dal.out;

import java.util.Date;
import java.util.List;

import com.pinfly.purchasecharge.core.model.persistence.out.OrderDelivery;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface OrderDeliveryDao extends MyGenericDao <OrderDelivery, Long>
{
    OrderDelivery findByOrder (long orderId);
    
    OrderDelivery findByNumber (String number);

    List <OrderDelivery> findByCompany (long companyId);

    List <OrderDelivery> findBy (long customerId, Date start, Date end);
}
