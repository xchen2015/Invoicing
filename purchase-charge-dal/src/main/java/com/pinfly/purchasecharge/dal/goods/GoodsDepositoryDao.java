package com.pinfly.purchasecharge.dal.goods;

import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsDepository;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface GoodsDepositoryDao extends MyGenericDao <GoodsDepository, Long>
{
    public GoodsDepository findByName (String name);
}
