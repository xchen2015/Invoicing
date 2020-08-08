package com.pinfly.purchasecharge.dal.out;

import java.util.Date;
import java.util.List;

import com.pinfly.purchasecharge.core.model.persistence.out.OrderReceipt;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface OrderReceiptDao extends MyGenericDao <OrderReceipt, Long>
{
    OrderReceipt findByOrder (long orderId);

    List <OrderReceipt> findBy (long customerId, Date start, Date end);
}
