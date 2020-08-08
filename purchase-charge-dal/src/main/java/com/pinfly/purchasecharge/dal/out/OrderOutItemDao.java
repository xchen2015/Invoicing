package com.pinfly.purchasecharge.dal.out;

import java.util.List;

import com.pinfly.purchasecharge.core.model.OrderItemSearchForm;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOutItem;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface OrderOutItemDao extends MyGenericDao <OrderOutItem, Long>
{
    public List <OrderOutItem> findByOrder (long orderId, boolean resultWithOrder);

    public List <OrderOutItem> findByGoods (long goodsId);
    
    public List <OrderOutItem> findBySearchForm (OrderItemSearchForm searchForm);

}
