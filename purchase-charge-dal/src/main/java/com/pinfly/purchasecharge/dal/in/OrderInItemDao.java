package com.pinfly.purchasecharge.dal.in;

import java.util.List;

import com.pinfly.purchasecharge.core.model.OrderItemSearchForm;
import com.pinfly.purchasecharge.core.model.persistence.in.OrderInItem;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface OrderInItemDao extends MyGenericDao <OrderInItem, Long>
{
    public List <OrderInItem> findByOrder (long orderId, boolean resultWithOrder);

    public List <OrderInItem> findByGoods (long goodsId);
    
    public List <OrderInItem> findBySearchForm (OrderItemSearchForm searchForm);

}
