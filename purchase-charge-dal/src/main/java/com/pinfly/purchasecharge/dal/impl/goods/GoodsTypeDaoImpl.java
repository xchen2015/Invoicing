package com.pinfly.purchasecharge.dal.impl.goods;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsType;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.goods.GoodsTypeDao;

public class GoodsTypeDaoImpl extends MyGenericDaoImpl <GoodsType, Long> implements GoodsTypeDao
{
    private static final Logger logger = Logger.getLogger (GoodsTypeDaoImpl.class);

    public GoodsTypeDaoImpl ()
    {
        super (GoodsType.class);
    }

    @Override
    public GoodsType findByName (String name)
    {
        logger.debug (name);
        String sql = "select g from GoodsType g where g.name=?1";
        Query query = getEntityManager ().createQuery (sql, GoodsType.class);
        query.setParameter (1, name);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (GoodsType) query.getResultList ().get (0) : null;
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <GoodsType> findByParent (Long parentId)
    {
        String sql = "select g from GoodsType g where g.parent.id=?1";
        Query query = getEntityManager ().createQuery (sql, GoodsType.class);
        query.setParameter (1, parentId);
        return query.getResultList ();
    }
}
