package com.pinfly.purchasecharge.dal.impl.in;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.pinfly.purchasecharge.core.model.OrderSearchForm;
import com.pinfly.purchasecharge.core.model.OrderStatusCode;
import com.pinfly.purchasecharge.core.model.OrderTypeCode;
import com.pinfly.purchasecharge.core.model.persistence.in.OrderIn;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.dal.common.GenericDaoUtils;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.exception.PCDalException;
import com.pinfly.purchasecharge.dal.in.OrderInDao;

public class OrderInDaoImpl extends MyGenericDaoImpl <OrderIn, Long> implements OrderInDao
{
    private static final Logger logger = Logger.getLogger (OrderInDaoImpl.class);

    public OrderInDaoImpl ()
    {
        super (OrderIn.class);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <OrderIn> findByProviderName (String customerName)
    {
        String sql = "select o from OrderIn o where o.provider.shortName=?1";
        Query query = getEntityManager ().createQuery (sql, OrderIn.class);
        query.setParameter (1, customerName);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <OrderIn> findByProviderId (long customerId)
    {
        String sql = "select o from OrderIn o where o.provider.id=?1";
        Query query = getEntityManager ().createQuery (sql, OrderIn.class);
        query.setParameter (1, customerId);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <OrderIn> findByOrderStatusCode (OrderStatusCode statusCode)
    {
        String sql = "select o from OrderIn o where o.statusCode=?1";
        Query query = getEntityManager ().createQuery (sql, OrderIn.class);
        query.setParameter (1, statusCode);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <OrderIn> findByUserSigned (long userId)
    {
        String sql = "select o from OrderIn o where o.provider.userSigned=?1";
        Query query = getEntityManager ().createQuery (sql, OrderIn.class);
        query.setParameter (1, userId);
        return query.getResultList ();
    }
    
    @Override
    @SuppressWarnings ("unchecked")
    public List <OrderIn> findByRangeCreateDate (OrderTypeCode typeCode, OrderStatusCode statusCode, Timestamp start,
                                                 Timestamp end)
                                                 {
        String sql = "select o from OrderIn o where o.typeCode = ?1 and o.statusCode=?2 and o.dateCreated between ?3 and ?4 order by o.dateCreated desc";
        Query query = getEntityManager ().createQuery (sql, OrderIn.class);
        query.setParameter (1, typeCode);
        query.setParameter (2, statusCode);
        query.setParameter (3, start);
        query.setParameter (4, end);
        return query.getResultList ();
                                                 }

    @Override
    @SuppressWarnings ("unchecked")
    public List <OrderIn> findByRangeCreateDate (Timestamp start, Timestamp end)
    {
        String sql = "select o from OrderIn o where o.dateCreated between ?1 and ?2 order by o.dateCreated desc";
        Query query = getEntityManager ().createQuery (sql, OrderIn.class);
        query.setParameter (1, start);
        query.setParameter (2, end);
        return query.getResultList ();
    }

    @Override
    @Transactional
    public int updateStatus (OrderStatusCode statusCode, String comment, long id)
    {
        String sql = "update OrderIn o set o.statusCode = ?1, o.comment = ?2 where o.id = ?3";
        Query query = getEntityManager ().createQuery (sql);
        query.setParameter (1, statusCode);
        query.setParameter (2, comment);
        query.setParameter (3, id);
        return query.executeUpdate ();
    }

    @Override
    @Transactional
    public int updateReceivable (float receivable, long id)
    {
        String sql = "update OrderIn o set o.receivable = ?1 where o.id = ?2";
        Query query = getEntityManager ().createQuery (sql);
        query.setParameter (1, receivable);
        query.setParameter (2, id);
        return query.executeUpdate ();
    }

    @Override
    public Page <OrderIn> findByFuzzy (OrderTypeCode typeCode, Pageable pageable, String searchKey, long signUser,
                                       boolean admin)
    {
        validateTypeCode (typeCode);
        String pageableLog = "offSet:" + pageable.getOffset () + ",pageNumber:" + pageable.getPageNumber ()
                             + ",pageSize:" + pageable.getPageSize () + ",sort:" + pageable.getSort ();
        logger.debug ("Find paging order by fuzzy, pageable: (" + pageableLog + "), searchkey: " + searchKey);
        Page <OrderIn> orderPage;
        String keyWord = (null == searchKey ? "" : searchKey.trim ());
        if (!admin && 0 != signUser)
        {
            String selectCountSql = "SELECT count(o) ";
            String fromSql = "FROM OrderIn o where ";
            String conditionSql = "o.provider.userSigned = ?1 and o.typeCode = ?2 ";
            if (StringUtils.isNotBlank (searchKey))
            {
                conditionSql += "and (o.provider.shortName like ?3 or o.provider.shortCode like ?4)";
            }
            Query countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
            countQuery.setParameter (1, signUser);
            countQuery.setParameter (2, typeCode);
            if (StringUtils.isNotBlank (searchKey))
            {
                countQuery.setParameter (3, keyWord + "%");
                countQuery.setParameter (4, keyWord + "%");
            }
            int total = Integer.parseInt (countQuery.getResultList ().get (0).toString ());

            String querySql = "SELECT o ";
            querySql += fromSql;
            querySql += conditionSql;
            String orderBySql = " ORDER BY ";
            querySql += orderBySql;

            String sortSqlStr = "o.provider.shortName asc";
            try
            {
                sortSqlStr = GenericDaoUtils.parseSort (OrderIn.class, pageable, "o");
            }
            catch (Exception e)
            {
                // TODO: handle exception
            }
            querySql += sortSqlStr;

            Query query = getEntityManager ().createQuery (querySql);
            query.setParameter (1, signUser);
            query.setParameter (2, typeCode);
            if (StringUtils.isNotBlank (searchKey))
            {
                query.setParameter (3, keyWord + "%");
                query.setParameter (4, keyWord + "%");
            }

            query.setFirstResult (pageable.getOffset ());
            query.setMaxResults (pageable.getPageSize ());

            @SuppressWarnings ("unchecked")
            List <OrderIn> goodsList = query.getResultList ();
            orderPage = new PageImpl <OrderIn> (goodsList, pageable, total);
        }
        else
        {
            String selectCountSql = "SELECT count(o) ";
            String fromSql = "FROM OrderIn o where ";
            String conditionSql = "o.typeCode = ?1 ";
            if (StringUtils.isNotBlank (searchKey))
            {
                conditionSql += "and (o.provider.shortName like ?2 or o.provider.shortCode like ?3)";
            }
            Query countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
            countQuery.setParameter (1, typeCode);
            if (StringUtils.isNotBlank (searchKey))
            {
                countQuery.setParameter (2, keyWord + "%");
                countQuery.setParameter (3, keyWord + "%");
            }
            int total = Integer.parseInt (countQuery.getResultList ().get (0).toString ());

            String querySql = "SELECT o ";
            querySql += fromSql;
            querySql += conditionSql;
            String orderBySql = " ORDER BY ";
            querySql += orderBySql;

            String sortSqlStr = "o.provider.shortName asc";
            try
            {
                sortSqlStr = GenericDaoUtils.parseSort (OrderIn.class, pageable, "o");
            }
            catch (Exception e)
            {
                // TODO: handle exception
            }
            querySql += sortSqlStr;

            Query query = getEntityManager ().createQuery (querySql);
            query.setParameter (1, typeCode);
            if (StringUtils.isNotBlank (searchKey))
            {
                query.setParameter (2, keyWord + "%");
                query.setParameter (3, keyWord + "%");
            }

            query.setFirstResult (pageable.getOffset ());
            query.setMaxResults (pageable.getPageSize ());

            @SuppressWarnings ("unchecked")
            List <OrderIn> goodsList = query.getResultList ();
            orderPage = new PageImpl <OrderIn> (goodsList, pageable, total);
        }
        return orderPage;
    }

    @Override
    public Page <OrderIn> findBySearchForm (OrderTypeCode typeCode, Pageable pageable, OrderSearchForm searchForm,
                                            long signUser, boolean admin)
    {
        String pageableLog = "offSet:" + pageable.getOffset () + ",pageNumber:" + pageable.getPageNumber ()
                             + ",pageSize:" + pageable.getPageSize () + ",sort:" + pageable.getSort ();
        logger.debug ("Find paging order by fuzzy, pageable: (" + pageableLog + "), searchForm: " + searchForm);
        validateTypeCode (typeCode);
        Page <OrderIn> orderPage;
        if (!admin && 0 != signUser)
        {
            String selectCountSql = "SELECT count(o) ";
            String fromSql = "FROM OrderIn o where ";
            String conditionSql = "o.provider.userSigned = ?1 and o.typeCode = ?2 ";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.dateCreated between ?3 and ?4 and o.provider.id = ?5 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "and o.dateCreated between ?3 and ?4 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.provider.id = ?3 ";
                }
            }
            Query countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
            countQuery.setParameter (1, signUser);
            countQuery.setParameter (2, typeCode);
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    countQuery.setParameter (4, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                    countQuery.setParameter (5, searchForm.getCustomerId ());
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    countQuery.setParameter (4, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    countQuery.setParameter (3, searchForm.getCustomerId ());
                }
            }
            int total = Integer.parseInt (countQuery.getResultList ().get (0).toString ());

            String querySql = "SELECT o ";
            querySql += fromSql;
            querySql += conditionSql;
            String orderBySql = " ORDER BY ";
            querySql += orderBySql;

            String sortSqlStr = "o.provider.shortName asc";
            try
            {
                sortSqlStr = GenericDaoUtils.parseSort (OrderIn.class, pageable, "o");
            }
            catch (Exception e)
            {
                // TODO: handle exception
            }
            querySql += sortSqlStr;

            Query query = getEntityManager ().createQuery (querySql);
            query.setParameter (1, signUser);
            query.setParameter (2, typeCode);
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    query.setParameter (3, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    query.setParameter (4, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                    query.setParameter (5, searchForm.getCustomerId ());
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    query.setParameter (3, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    query.setParameter (4, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    query.setParameter (3, searchForm.getCustomerId ());
                }
            }

            query.setFirstResult (pageable.getOffset ());
            query.setMaxResults (pageable.getPageSize ());

            @SuppressWarnings ("unchecked")
            List <OrderIn> goodsList = query.getResultList ();
            orderPage = new PageImpl <OrderIn> (goodsList, pageable, total);
        }
        else
        {
            String selectCountSql = "SELECT count(o) ";
            String fromSql = "FROM OrderIn o where ";
            String conditionSql = "o.typeCode = ?1 ";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 and o.provider.id = ?4 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.provider.id = ?2 ";
                }
            }
            Query countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
            countQuery.setParameter (1, typeCode);
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                    countQuery.setParameter (4, searchForm.getCustomerId ());
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    countQuery.setParameter (2, searchForm.getCustomerId ());
                }
            }
            int total = Integer.parseInt (countQuery.getResultList ().get (0).toString ());

            String querySql = "SELECT o ";
            querySql += fromSql;
            querySql += conditionSql;
            String orderBySql = " ORDER BY ";
            querySql += orderBySql;

            String sortSqlStr = "o.provider.shortName asc";
            try
            {
                sortSqlStr = GenericDaoUtils.parseSort (OrderIn.class, pageable, "o");
            }
            catch (Exception e)
            {
                // TODO: handle exception
            }
            querySql += sortSqlStr;

            Query query = getEntityManager ().createQuery (querySql);
            query.setParameter (1, typeCode);
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    query.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    query.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                    query.setParameter (4, searchForm.getCustomerId ());
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    query.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    query.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    query.setParameter (2, searchForm.getCustomerId ());
                }
            }

            query.setFirstResult (pageable.getOffset ());
            query.setMaxResults (pageable.getPageSize ());

            @SuppressWarnings ("unchecked")
            List <OrderIn> goodsList = query.getResultList ();
            orderPage = new PageImpl <OrderIn> (goodsList, pageable, total);
        }
        return orderPage;
    }

    @Override
    public Page <OrderIn> findBySearchForm (Pageable pageable, OrderSearchForm searchForm, long signUser, boolean admin)
                                                                                                                        throws PCDalException
    {
        String pageableLog = "offSet:" + pageable.getOffset () + ",pageNumber:" + pageable.getPageNumber ()
                             + ",pageSize:" + pageable.getPageSize () + ",sort:" + pageable.getSort ();
        logger.debug ("Find paging order by fuzzy, pageable: (" + pageableLog + "), searchForm: " + searchForm);
        Page <OrderIn> orderPage;
        if (!admin && 0 != signUser)
        {
            String selectCountSql = "SELECT count(o) ";
            String fromSql = "FROM OrderIn o where ";
            String conditionSql = "o.provider.userSigned = ?1 ";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 and o.provider.id = ?4 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.provider.id = ?2 ";
                }
            }
            Query countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
            countQuery.setParameter (1, signUser);
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                    countQuery.setParameter (4, searchForm.getCustomerId ());
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    countQuery.setParameter (2, searchForm.getCustomerId ());
                }
            }
            int total = Integer.parseInt (countQuery.getResultList ().get (0).toString ());

            String querySql = "SELECT o ";
            querySql += fromSql;
            querySql += conditionSql;
            String orderBySql = " ORDER BY ";
            querySql += orderBySql;

            String sortSqlStr = "o.provider.shortName asc";
            try
            {
                sortSqlStr = GenericDaoUtils.parseSort (OrderIn.class, pageable, "o");
            }
            catch (Exception e)
            {
                // TODO: handle exception
            }
            querySql += sortSqlStr;

            Query query = getEntityManager ().createQuery (querySql);
            query.setParameter (1, signUser);
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    query.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    query.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                    query.setParameter (4, searchForm.getCustomerId ());
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    query.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    query.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    query.setParameter (2, searchForm.getCustomerId ());
                }
            }

            query.setFirstResult (pageable.getOffset ());
            query.setMaxResults (pageable.getPageSize ());

            @SuppressWarnings ("unchecked")
            List <OrderIn> goodsList = query.getResultList ();
            orderPage = new PageImpl <OrderIn> (goodsList, pageable, total);
        }
        else
        {
            String selectCountSql = "SELECT count(o) ";
            String fromSql = "FROM OrderIn o where ";
            String conditionSql = "";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "o.dateCreated between ?1 and ?2 and o.provider.id = ?3 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "o.dateCreated between ?1 and ?2 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "o.provider.id = ?1 ";
                }
            }
            Query countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    countQuery.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                    countQuery.setParameter (3, searchForm.getCustomerId ());
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    countQuery.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    countQuery.setParameter (1, searchForm.getCustomerId ());
                }
            }
            int total = Integer.parseInt (countQuery.getResultList ().get (0).toString ());

            String querySql = "SELECT o ";
            querySql += fromSql;
            querySql += conditionSql;
            String orderBySql = " ORDER BY ";
            querySql += orderBySql;

            String sortSqlStr = "o.provider.shortName asc";
            try
            {
                sortSqlStr = GenericDaoUtils.parseSort (OrderIn.class, pageable, "o");
            }
            catch (Exception e)
            {
                // TODO: handle exception
            }
            querySql += sortSqlStr;

            Query query = getEntityManager ().createQuery (querySql);
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    query.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    query.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                    query.setParameter (3, searchForm.getCustomerId ());
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    query.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    query.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    query.setParameter (1, searchForm.getCustomerId ());
                }
            }

            query.setFirstResult (pageable.getOffset ());
            query.setMaxResults (pageable.getPageSize ());

            @SuppressWarnings ("unchecked")
            List <OrderIn> goodsList = query.getResultList ();
            orderPage = new PageImpl <OrderIn> (goodsList, pageable, total);
        }
        return orderPage;
    }
    
    @Override
    public float countReceivableBy (OrderSearchForm searchForm, long signUser, boolean admin)
    {
        float sumReceivable = 0;
        if (!admin && 0 != signUser)
        {
            String selectCountSql = "SELECT function ";
            String fromSql = "FROM OrderIn o where ";
            String conditionSql = "o.provider.userSigned = ?1 ";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                        && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 and o.provider.id = ?4 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.provider.id = ?2 ";
                }
            }
            
            String countSql = selectCountSql + fromSql + conditionSql;
            String countReceivable = countSql.replaceFirst ("function", "sum(o.receivable)");
            Query countQuery = getEntityManager ().createQuery (countReceivable);
            countQuery.setParameter (1, signUser);
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                        && 0 != searchForm.getCustomerId ())
                {
                    countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                    countQuery.setParameter (4, searchForm.getCustomerId ());
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    countQuery.setParameter (2, searchForm.getCustomerId ());
                }
            }
            sumReceivable = Float.parseFloat (countQuery.getResultList ().get (0).toString ());
        }
        else
        {
            String selectCountSql = "SELECT function ";
            String fromSql = "FROM OrderIn o where ";
            String conditionSql = "";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                        && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "o.dateCreated between ?1 and ?2 and o.provider.id = ?3 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "o.dateCreated between ?1 and ?2 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "o.provider.id = ?1 ";
                }
            }
            
            String countSql = selectCountSql + fromSql + conditionSql;
            String countReceivable = countSql.replaceFirst ("function", "sum(o.receivable)");
            Query countQuery = getEntityManager ().createQuery (countReceivable);
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                        && 0 != searchForm.getCustomerId ())
                {
                    countQuery.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                    countQuery.setParameter (3, searchForm.getCustomerId ());
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    countQuery.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    countQuery.setParameter (1, searchForm.getCustomerId ());
                }
            }
            sumReceivable = Float.parseFloat (countQuery.getResultList ().get (0).toString ());
        }
        return sumReceivable;
    }
    
    @Override
    public float countReceivableBy (OrderTypeCode orderType, OrderSearchForm searchForm, long signUser, boolean admin)
    {
        float sumReceivable = 0;
        if (!admin && 0 != signUser)
        {
            String selectCountSql = "SELECT function ";
            String fromSql = "FROM OrderIn o where ";
            String conditionSql = "o.provider.userSigned = ?1 and o.typeCode = ?2 ";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                        && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.dateCreated between ?3 and ?4 and o.provider.id = ?5 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "and o.dateCreated between ?3 and ?4 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.provider.id = ?3 ";
                }
            }
            
            String countSql = selectCountSql + fromSql + conditionSql;
            String countReceivable = countSql.replaceFirst ("function", "sum(o.receivable)");
            Query countQuery = getEntityManager ().createQuery (countReceivable);
            countQuery.setParameter (1, signUser);
            countQuery.setParameter (2, orderType);
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                        && 0 != searchForm.getCustomerId ())
                {
                    countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    countQuery.setParameter (4, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                    countQuery.setParameter (5, searchForm.getCustomerId ());
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    countQuery.setParameter (4, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    countQuery.setParameter (3, searchForm.getCustomerId ());
                }
            }
            sumReceivable = Float.parseFloat (countQuery.getResultList ().get (0).toString ());
        }
        else
        {
            String selectCountSql = "SELECT function ";
            String fromSql = "FROM OrderIn o where ";
            String conditionSql = "o.typeCode = ?1 ";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                        && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 and o.provider.id = ?4 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.provider.id = ?2 ";
                }
            }
            
            String countSql = selectCountSql + fromSql + conditionSql;
            String countReceivable = countSql.replaceFirst ("function", "sum(o.receivable)");
            Query countQuery = getEntityManager ().createQuery (countReceivable);
            countQuery.setParameter (1, orderType);
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                        && 0 != searchForm.getCustomerId ())
                {
                    countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                    countQuery.setParameter (4, searchForm.getCustomerId ());
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    countQuery.setParameter (2, searchForm.getCustomerId ());
                }
            }
            sumReceivable = Float.parseFloat (countQuery.getResultList ().get (0).toString ());
        }
        return sumReceivable;
    }
    
    @Override
    public float countPaidMoneyBy (OrderSearchForm searchForm, long signUser, boolean admin)
    {
        float sumReceivable = 0;
        if (!admin && 0 != signUser)
        {
            String selectCountSql = "SELECT function ";
            String fromSql = "FROM OrderIn o where ";
            String conditionSql = "o.provider.userSigned = ?1 ";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                        && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 and o.provider.id = ?4 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.provider.id = ?2 ";
                }
            }
            
            String countSql = selectCountSql + fromSql + conditionSql;
            String countReceivable = countSql.replaceFirst ("function", "sum(o.paidMoney)");
            Query countQuery = getEntityManager ().createQuery (countReceivable);
            countQuery.setParameter (1, signUser);
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                        && 0 != searchForm.getCustomerId ())
                {
                    countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                    countQuery.setParameter (4, searchForm.getCustomerId ());
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    countQuery.setParameter (2, searchForm.getCustomerId ());
                }
            }
            sumReceivable = Float.parseFloat (countQuery.getResultList ().get (0).toString ());
        }
        else
        {
            String selectCountSql = "SELECT function ";
            String fromSql = "FROM OrderIn o where ";
            String conditionSql = "";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                        && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "o.dateCreated between ?1 and ?2 and o.provider.id = ?3 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "o.dateCreated between ?1 and ?2 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "o.provider.id = ?1 ";
                }
            }
            
            String countSql = selectCountSql + fromSql + conditionSql;
            String countReceivable = countSql.replaceFirst ("function", "sum(o.paidMoney)");
            Query countQuery = getEntityManager ().createQuery (countReceivable);
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                        && 0 != searchForm.getCustomerId ())
                {
                    countQuery.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                    countQuery.setParameter (3, searchForm.getCustomerId ());
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    countQuery.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    countQuery.setParameter (1, searchForm.getCustomerId ());
                }
            }
            sumReceivable = Float.parseFloat (countQuery.getResultList ().get (0).toString ());
        }
        return sumReceivable;
    }

    @Override
    public float countPaidMoneyBy (OrderTypeCode orderType, OrderSearchForm searchForm, long signUser, boolean admin)
    {
        float sumReceivable = 0;
        if (!admin && 0 != signUser)
        {
            String selectCountSql = "SELECT function ";
            String fromSql = "FROM OrderIn o where ";
            String conditionSql = "o.provider.userSigned = ?1 and o.typeCode = ?2 ";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.dateCreated between ?3 and ?4 and o.provider.id = ?5 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "and o.dateCreated between ?3 and ?4 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.provider.id = ?3 ";
                }
            }

            String countSql = selectCountSql + fromSql + conditionSql;
            String countReceivable = countSql.replaceFirst ("function", "sum(o.paidMoney)");
            Query countQuery = getEntityManager ().createQuery (countReceivable);
            countQuery.setParameter (1, signUser);
            countQuery.setParameter (2, orderType);
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    countQuery.setParameter (4, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                    countQuery.setParameter (5, searchForm.getCustomerId ());
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    countQuery.setParameter (4, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    countQuery.setParameter (3, searchForm.getCustomerId ());
                }
            }
            sumReceivable = Float.parseFloat (countQuery.getResultList ().get (0).toString ());
        }
        else
        {
            String selectCountSql = "SELECT function ";
            String fromSql = "FROM OrderIn o where ";
            String conditionSql = "o.typeCode = ?1 ";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 and o.provider.id = ?4 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.provider.id = ?2 ";
                }
            }

            String countSql = selectCountSql + fromSql + conditionSql;
            String countReceivable = countSql.replaceFirst ("function", "sum(o.paidMoney)");
            Query countQuery = getEntityManager ().createQuery (countReceivable);
            countQuery.setParameter (1, orderType);
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                    countQuery.setParameter (4, searchForm.getCustomerId ());
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                    countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    countQuery.setParameter (2, searchForm.getCustomerId ());
                }
            }
            sumReceivable = Float.parseFloat (countQuery.getResultList ().get (0).toString ());
        }
        return sumReceivable;
    }

    @Override
    public OrderIn findLast (long providerId)
    {
        String sql = "select o from OrderIn o where o.dateCreated = (select max(o1.dateCreated) from OrderIn o1 where o1.provider.id = ?1)";
        Query query = getEntityManager ().createQuery (sql, OrderIn.class);
        query.setParameter (1, providerId);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (OrderIn) query.getResultList ().get (0) : null;
    }
    
    @Override
    public OrderIn findByBid (String bid)
    {
        String sql = "select o from OrderIn o where o.bid = ?1";
        Query query = getEntityManager ().createQuery (sql, OrderIn.class);
        query.setParameter (1, bid);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (OrderIn) query.getResultList ().get (0) : null;
    }

    private void validateTypeCode (OrderTypeCode typeCode)
    {
        if (null == typeCode)
        {
            throw new PCDalException ("OrderIn type code can not null.");
        }
    }
}
