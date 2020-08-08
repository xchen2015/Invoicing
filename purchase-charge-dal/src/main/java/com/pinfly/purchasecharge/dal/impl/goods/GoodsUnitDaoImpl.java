package com.pinfly.purchasecharge.dal.impl.goods;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsUnit;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.goods.GoodsUnitDao;

public class GoodsUnitDaoImpl extends MyGenericDaoImpl <GoodsUnit, Long> implements GoodsUnitDao
{
    private static final Logger logger = Logger.getLogger (GoodsUnitDaoImpl.class);

    public GoodsUnitDaoImpl ()
    {
        super (GoodsUnit.class);
    }

    @Override
    public GoodsUnit findByName (String name)
    {
        logger.debug (name);
        String sql = "select g from GoodsUnit g where g.name=?1";
        Query query = getEntityManager ().createQuery (sql, GoodsUnit.class);
        query.setParameter (1, name);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (GoodsUnit) query.getResultList ().get (0) : null;
    }
}
