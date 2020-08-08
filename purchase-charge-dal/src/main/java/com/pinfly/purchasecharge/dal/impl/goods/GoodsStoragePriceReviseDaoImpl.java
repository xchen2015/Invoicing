package com.pinfly.purchasecharge.dal.impl.goods;

import java.util.List;

import javax.persistence.Query;

import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsStoragePriceRevise;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.goods.GoodsStoragePriceReviseDao;

public class GoodsStoragePriceReviseDaoImpl extends MyGenericDaoImpl <GoodsStoragePriceRevise, Long> implements
                                                                                                    GoodsStoragePriceReviseDao
{

    public GoodsStoragePriceReviseDaoImpl ()
    {
        super (GoodsStoragePriceRevise.class);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <GoodsStoragePriceRevise> findBy (long goodsId, long depository)
    {
        if (0 != goodsId && 0 != depository)
        {
            String sql = "select gst from GoodsStoragePriceRevise gst where gst.goods.id = ?1 and gst.depository.id = ?2 order by gst.dateCreated desc";
            Query query = getEntityManager ().createQuery (sql, GoodsStoragePriceRevise.class);
            query.setParameter (1, goodsId);
            query.setParameter (2, depository);
            return query.getResultList ();
        }
        else if (0 != goodsId)
        {
            String sql = "select gst from GoodsStoragePriceRevise gst where gst.goods.id = ?1 order by gst.dateCreated desc";
            Query query = getEntityManager ().createQuery (sql, GoodsStoragePriceRevise.class);
            query.setParameter (1, goodsId);
            return query.getResultList ();
        }
        else if (0 != depository)
        {
            String sql = "select gst from GoodsStoragePriceRevise gst where gst.depository.id = ?1 order by gst.dateCreated desc";
            Query query = getEntityManager ().createQuery (sql, GoodsStoragePriceRevise.class);
            query.setParameter (1, depository);
            return query.getResultList ();
        }
        else
        {
            String sql = "select gst from GoodsStoragePriceRevise gst order by gst.dateCreated desc";
            Query query = getEntityManager ().createQuery (sql, GoodsStoragePriceRevise.class);
            return query.getResultList ();
        }
    }

}
