package com.pinfly.purchasecharge.dal.impl.goods;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.pinfly.purchasecharge.core.model.GoodsIssueSearchForm;
import com.pinfly.purchasecharge.core.model.GoodsIssueStatusCode;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsIssue;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.goods.GoodsIssueDao;

public class GoodsIssueDaoImpl extends MyGenericDaoImpl <GoodsIssue, Long> implements GoodsIssueDao
{
    public GoodsIssueDaoImpl ()
    {
        super (GoodsIssue.class);
    }

    @Override
    public GoodsIssue findBySerialNumber (String serialNumber)
    {
        String sql = "select g from GoodsIssue g where g.goodsSerial=?1";
        Query query = getEntityManager ().createQuery (sql, GoodsIssue.class);
        query.setParameter (1, serialNumber);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (GoodsIssue) query.getResultList ().get (0) : null;
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <GoodsIssue> findByGoods (long goods)
    {
        String sql = "select g from GoodsIssue g where g.goods.id=?1 order by g.dateCreated desc";
        Query query = getEntityManager ().createQuery (sql, GoodsIssue.class);
        query.setParameter (1, goods);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <GoodsIssue> findByProvider (long provider)
    {
        String sql = "select g from GoodsIssue g where g.provider.id=?1 order by g.dateCreated desc";
        Query query = getEntityManager ().createQuery (sql, GoodsIssue.class);
        query.setParameter (1, provider);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <GoodsIssue> findByCustomer (long customer)
    {
        String sql = "select g from GoodsIssue g where g.customer.id=?1 order by g.dateCreated desc";
        Query query = getEntityManager ().createQuery (sql, GoodsIssue.class);
        query.setParameter (1, customer);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <GoodsIssue> findByStatus (GoodsIssueStatusCode statusCode)
    {
        String sql = "select g from GoodsIssue g where g.statusCode=?1 order by g.dateCreated desc";
        Query query = getEntityManager ().createQuery (sql, GoodsIssue.class);
        query.setParameter (1, statusCode);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <GoodsIssue> findBySearchForm (GoodsIssueSearchForm searchForm)
    {
        if (null != searchForm)
        {
            if (0 != searchForm.getGoodsId () && 0 != searchForm.getCustomerId ()
                && null != searchForm.getIssueStatusCode ())
            {
                String sql = "select g from GoodsIssue g where g.goods.id=?1 and g.customer.id=?2 and g.statusCode=?3 order by g.dateCreated desc";
                Query query = getEntityManager ().createQuery (sql, GoodsIssue.class);
                query.setParameter (1, searchForm.getGoodsId ());
                query.setParameter (2, searchForm.getCustomerId ());
                query.setParameter (3, searchForm.getIssueStatusCode ());
                return query.getResultList ();
            }
            else if (0 != searchForm.getGoodsId () && 0 != searchForm.getCustomerId ())
            {
                String sql = "select g from GoodsIssue g where g.goods.id=?1 and g.customer.id=?2 order by g.dateCreated desc";
                Query query = getEntityManager ().createQuery (sql, GoodsIssue.class);
                query.setParameter (1, searchForm.getGoodsId ());
                query.setParameter (2, searchForm.getCustomerId ());
                return query.getResultList ();
            }
            else if (0 != searchForm.getGoodsId () && null != searchForm.getIssueStatusCode ())
            {
                String sql = "select g from GoodsIssue g where g.goods.id=?1 and g.statusCode=?2 order by g.dateCreated desc";
                Query query = getEntityManager ().createQuery (sql, GoodsIssue.class);
                query.setParameter (1, searchForm.getGoodsId ());
                query.setParameter (2, searchForm.getIssueStatusCode ());
                return query.getResultList ();
            }
            else if (0 != searchForm.getCustomerId () && null != searchForm.getIssueStatusCode ())
            {
                String sql = "select g from GoodsIssue g where g.customer.id=?1 and g.statusCode=?2 order by g.dateCreated desc";
                Query query = getEntityManager ().createQuery (sql, GoodsIssue.class);
                query.setParameter (1, searchForm.getCustomerId ());
                query.setParameter (2, searchForm.getIssueStatusCode ());
                return query.getResultList ();
            }
            else if (StringUtils.isNotBlank (searchForm.getGoodsSerialNumber ()))
            {
                List <GoodsIssue> list = new ArrayList <GoodsIssue> ();
                list.add (findBySerialNumber (searchForm.getGoodsSerialNumber ()));
                return list;
            }
            else if (0 != searchForm.getGoodsId ())
            {
                return findByGoods (searchForm.getGoodsId ());
            }
            else if (0 != searchForm.getCustomerId ())
            {
                return findByCustomer (searchForm.getCustomerId ());
            }
            else if (0 != searchForm.getProviderId ())
            {
                return findByProvider (searchForm.getProviderId ());
            }
            else if (null != searchForm.getIssueStatusCode ())
            {
                return findByStatus (searchForm.getIssueStatusCode ());
            }
            else if (null == searchForm.getIssueStatusCode ())
            {
                String sql = "select g from GoodsIssue g order by g.dateCreated desc";
                Query query = getEntityManager ().createQuery (sql, GoodsIssue.class);
                return query.getResultList ();
            }
        }
        return null;
    }

    @Override
    @Transactional
    public int updateStatus (long id, String comment, GoodsIssueStatusCode statusCode)
    {
        String sql = "update GoodsIssue g set g.statusCode = ?1, g.comment = ?2 where g.id = ?3";
        Query query = getEntityManager ().createQuery (sql);
        query.setParameter (1, statusCode);
        query.setParameter (2, comment);
        query.setParameter (3, id);
        return query.executeUpdate ();
    }

}
