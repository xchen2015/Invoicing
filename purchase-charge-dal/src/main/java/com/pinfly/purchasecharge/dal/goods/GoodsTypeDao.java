package com.pinfly.purchasecharge.dal.goods;

import java.util.List;

import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsType;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface GoodsTypeDao extends MyGenericDao <GoodsType, Long>
{
    public GoodsType findByName (String name);

    public List <GoodsType> findByParent (Long parentId);

}
