package com.pinfly.purchasecharge.service.aspect;

import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.core.config.PurchaseChargeProperties;
import com.pinfly.purchasecharge.core.model.persistence.goods.Goods;
import com.pinfly.purchasecharge.dal.goods.GoodsDao;
import com.pinfly.purchasecharge.service.utils.DataCache;

public class ServiceAspect
{
    private static final Logger logger = Logger.getLogger (ServiceAspect.class);
    private GoodsDao goodsDao;
    
    public void executeAfterChangeGoods () 
    {
        if(PurchaseChargeProperties.getAllowCacheGoods ()) 
        {
            DataCache.refreshDataToCache (Goods.class.getName (), goodsDao);
        }
        logger.info ("Execute After Change Goods");
    }

    public void setGoodsDao (GoodsDao goodsDao)
    {
        this.goodsDao = goodsDao;
    }
}
