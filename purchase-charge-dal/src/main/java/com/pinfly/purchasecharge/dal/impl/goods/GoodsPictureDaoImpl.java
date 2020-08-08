package com.pinfly.purchasecharge.dal.impl.goods;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsPicture;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.goods.GoodsPictureDao;

public class GoodsPictureDaoImpl extends MyGenericDaoImpl <GoodsPicture, Long> implements GoodsPictureDao
{
    private static final Logger logger = Logger.getLogger (GoodsPictureDaoImpl.class);

    public GoodsPictureDaoImpl ()
    {
        super (GoodsPicture.class);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <GoodsPicture> findByGoods (long goodsId)
    {
        logger.debug (goodsId);
        String sql = "select gp from GoodsPicture gp where gp.goods.id = ?1";
        Query query = getEntityManager ().createQuery (sql, GoodsPicture.class);
        query.setParameter (1, goodsId);
        return query.getResultList ();
    }

    @Override
    public GoodsPicture findByFileNameAndContentType (String fileName, String contentType)
    {
        String sql = "select gp from GoodsPicture gp where gp.fileName=?1 and gp.contentType=?2";
        Query query = getEntityManager ().createQuery (sql, GoodsPicture.class);
        query.setParameter (1, fileName);
        query.setParameter (2, contentType);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (GoodsPicture) query.getResultList ().get (0)
                                                                  : null;
    }

}
