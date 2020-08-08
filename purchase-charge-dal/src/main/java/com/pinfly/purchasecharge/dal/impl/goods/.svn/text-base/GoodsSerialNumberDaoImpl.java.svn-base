package com.pinfly.purchasecharge.dal.impl.goods;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsSerialNumber;
import com.pinfly.purchasecharge.core.model.persistence.in.OrderInItem;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOutItem;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.goods.GoodsSerialNumberDao;

public class GoodsSerialNumberDaoImpl extends MyGenericDaoImpl <GoodsSerialNumber, Long> implements
                                                                                        GoodsSerialNumberDao
{
    public GoodsSerialNumberDaoImpl ()
    {
        super (GoodsSerialNumber.class);
    }

    @Override
    public GoodsSerialNumber findBySerialNumber (String serialNumber)
    {
        String sql = "select g from GoodsSerialNumber g where g.serialNumber=?1";
        Query query = getEntityManager ().createQuery (sql, GoodsSerialNumber.class);
        query.setParameter (1, serialNumber);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (GoodsSerialNumber) query.getResultList ().get (0)
                                                                  : null;
    }

    @Override
    @Transactional (propagation = Propagation.NOT_SUPPORTED)
    public List <GoodsSerialNumber> findByGoods (long goodsId)
    {
        String sql = "select g from GoodsSerialNumber g";
        if (0 != goodsId)
        {
            sql = "select g from GoodsSerialNumber g where g.goods.id=?1";
        }
        Query query = getEntityManager ().createQuery (sql, GoodsSerialNumber.class);
        if (0 != goodsId)
        {
            query.setParameter (1, goodsId);
        }

        @SuppressWarnings ("unchecked")
        List <GoodsSerialNumber> result = query.getResultList ();
        if (CollectionUtils.isNotEmpty (result))
        {
            for (GoodsSerialNumber serialNumber : result)
            {
                OrderInItem orderInItem = serialNumber.getOrderInItem ();
                if (null != orderInItem)
                {
                    orderInItem.getOrderIn ();
                }
                OrderOutItem orderOutItem = serialNumber.getOrderOutItem ();
                if (null != orderOutItem)
                {
                    orderOutItem.getOrderOut ();
                }
            }
        }
        return result;
    }

}
