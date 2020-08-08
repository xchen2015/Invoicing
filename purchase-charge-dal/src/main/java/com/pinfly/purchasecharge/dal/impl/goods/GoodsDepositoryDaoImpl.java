package com.pinfly.purchasecharge.dal.impl.goods;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsDepository;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.goods.GoodsDepositoryDao;

public class GoodsDepositoryDaoImpl extends MyGenericDaoImpl <GoodsDepository, Long> implements GoodsDepositoryDao
{
    private static final Logger logger = Logger.getLogger (GoodsDepositoryDaoImpl.class);

    public GoodsDepositoryDaoImpl ()
    {
        super (GoodsDepository.class);
    }

    @Override
    public GoodsDepository findByName (String name)
    {
        logger.debug (name);
        String sql = "select gg from GoodsDepository gg where gg.name=?1";
        Query query = getEntityManager ().createQuery (sql, GoodsDepository.class);
        query.setParameter (1, name);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (GoodsDepository) query.getResultList ().get (0)
                                                                  : null;
    }
}
