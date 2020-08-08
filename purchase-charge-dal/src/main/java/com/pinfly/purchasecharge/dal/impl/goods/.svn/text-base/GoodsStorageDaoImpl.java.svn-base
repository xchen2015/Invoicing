package com.pinfly.purchasecharge.dal.impl.goods;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsStorage;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.goods.GoodsStorageDao;

public class GoodsStorageDaoImpl extends MyGenericDaoImpl <GoodsStorage, Long> implements GoodsStorageDao
{
    private static final Logger logger = Logger.getLogger (GoodsStorageDaoImpl.class);

    public GoodsStorageDaoImpl ()
    {
        super (GoodsStorage.class);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <GoodsStorage> findByGoods (long goodsId)
    {
        logger.debug ("getByGoods goodsId: " + goodsId);
        String sql = "select gs from GoodsStorage gs where gs.goods.id=?1";
        Query query = getEntityManager ().createQuery (sql, GoodsStorage.class);
        query.setParameter (1, goodsId);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <GoodsStorage> findByGoodsDepository (long goodsDepositoryId)
    {
        logger.debug ("getByGoods goodsDepositoryId: " + goodsDepositoryId);
        String sql = "select gs from GoodsStorage gs where gs.depository.id=?1";
        Query query = getEntityManager ().createQuery (sql, GoodsStorage.class);
        query.setParameter (1, goodsDepositoryId);
        return query.getResultList ();
    }

    @Override
    public GoodsStorage findByGoodsAndDepository (long goodsId, long depositoryId)
    {
        logger.debug ("getByGoodsAndDepository goodsId: " + goodsId + ", depositoryId: " + depositoryId);
        String sql = "select gs from GoodsStorage gs where gs.goods.id=?1 and gs.depository.id=?2";
        Query query = getEntityManager ().createQuery (sql, GoodsStorage.class);
        query.setParameter (1, goodsId);
        query.setParameter (2, depositoryId);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (GoodsStorage) query.getResultList ().get (0)
                                                                  : null;
    }
}
