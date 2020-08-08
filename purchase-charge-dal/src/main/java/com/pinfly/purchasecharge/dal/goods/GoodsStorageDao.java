package com.pinfly.purchasecharge.dal.goods;

import java.util.List;

import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsStorage;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface GoodsStorageDao extends MyGenericDao <GoodsStorage, Long>
{
    List <GoodsStorage> findByGoods (long goodsId);

    List <GoodsStorage> findByGoodsDepository (long goodsDepositoryId);

    GoodsStorage findByGoodsAndDepository (long goodsId, long depositoryId);
}
