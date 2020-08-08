package com.pinfly.purchasecharge.dal.impl.out;

import java.sql.Timestamp;
import java.util.Date;
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
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOut;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.dal.common.GenericDaoUtils;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.exception.PCDalException;
import com.pinfly.purchasecharge.dal.out.OrderOutDao;

public class OrderOutDaoImpl extends MyGenericDaoImpl <OrderOut, Long> implements OrderOutDao
{
    private static final Logger logger = Logger.getLogger (OrderOutDaoImpl.class);

    public OrderOutDaoImpl ()
    {
        super (OrderOut.class);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <OrderOut> findByCustomerName (String customerName)
    {
        String sql = "select o from OrderOut o where o.customer.shortName=?1";
        Query query = getEntityManager ().createQuery (sql, OrderOut.class);
        query.setParameter (1, customerName);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <OrderOut> findByCustomerId (long customerId)
    {
        String sql = "select o from OrderOut o where o.customer.id=?1";
        Query query = getEntityManager ().createQuery (sql, OrderOut.class);
        query.setParameter (1, customerId);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <OrderOut> findByOrderStatusCode (OrderStatusCode statusCode)
    {
        String sql = "select o from OrderOut o where o.statusCode=?1";
        Query query = getEntityManager ().createQuery (sql, OrderOut.class);
        query.setParameter (1, statusCode);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <OrderOut> findOldOrder (OrderStatusCode statusCode, Timestamp date)
    {
        String sql = "select o from OrderOut o where o.statusCode=?1 and o.dateCreated < ?2";
        Query query = getEntityManager ().createQuery (sql, OrderOut.class);
        query.setParameter (1, statusCode);
        query.setParameter (2, date);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <OrderOut> findByUserSigned (long userId)
    {
        String sql = "select o from OrderOut o where o.customer.userSigned=?1";
        Query query = getEntityManager ().createQuery (sql, OrderOut.class);
        query.setParameter (1, userId);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <OrderOut> findByRangeCreateDate (OrderTypeCode typeCode, OrderStatusCode statusCode, Timestamp start,
                                                  Timestamp end)
    {
        String sql = "select o from OrderOut o where o.typeCode = ?1 and o.statusCode=?2 and o.dateCreated between ?3 and ?4 order by o.dateCreated desc";
        Query query = getEntityManager ().createQuery (sql, OrderOut.class);
        query.setParameter (1, typeCode);
        query.setParameter (2, statusCode);
        query.setParameter (3, start);
        query.setParameter (4, end);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <OrderOut> findByRangeCreateDate (Timestamp start, Timestamp end)
    {
        String sql = "select o from OrderOut o where o.dateCreated between ?1 and ?2 order by o.dateCreated desc";
        Query query = getEntityManager ().createQuery (sql, OrderOut.class);
        query.setParameter (1, start);
        query.setParameter (2, end);
        return query.getResultList ();
    }

    @Override
    @Transactional
    public int updateStatus (OrderStatusCode statusCode, String comment, long id, Timestamp updateTime)
    {
        String sql = "update OrderOut o set o.statusCode = ?1, o.comment = ?2, o.lastUpdated = ?3 where o.id = ?4";
        Query query = getEntityManager ().createQuery (sql);
        query.setParameter (1, statusCode);
        query.setParameter (2, comment);
        query.setParameter (3, updateTime);
        query.setParameter (4, id);
        return query.executeUpdate ();
    }

    @Override
    @Transactional
    public int updateProfit (float profit, long id)
    {
        String sql = "update OrderOut o set o.profit = ?1 where o.id = ?2";
        Query query = getEntityManager ().createQuery (sql);
        query.setParameter (1, profit);
        query.setParameter (2, id);
        return query.executeUpdate ();
    }

    @Override
    @Transactional
    public int updateReceivable (float receivable, long id)
    {
        String sql = "update OrderOut o set o.receivable = ?1 where o.id = ?2";
        Query query = getEntityManager ().createQuery (sql);
        query.setParameter (1, receivable);
        query.setParameter (2, id);
        return query.executeUpdate ();
    }

    @Override
    public Page <OrderOut> findByFuzzy (OrderTypeCode typeCode, Pageable pageable, String searchKey, long signUser,
                                        boolean admin)
    {
        validateTypeCode (typeCode);
        String pageableLog = "offSet:" + pageable.getOffset () + ",pageNumber:" + pageable.getPageNumber ()
                             + ",pageSize:" + pageable.getPageSize () + ",sort:" + pageable.getSort ();
        logger.debug ("Find paging order by fuzzy, pageable: (" + pageableLog + "), searchkey: " + searchKey);
        Page <OrderOut> orderPage;
        String keyWord = (null == searchKey ? "" : searchKey.trim ());
        if (!admin && 0 != signUser)
        {
            String selectCountSql = "SELECT count(o) ";
            String fromSql = "FROM OrderOut o where ";
            String conditionSql = "o.customer.userSigned = ?1 and o.typeCode = ?2 ";
            if (StringUtils.isNotBlank (searchKey))
            {
                conditionSql += "and (o.customer.shortName like ?3 or o.customer.shortCode like ?4)";
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

            String sortSqlStr = "o.customer.shortName asc";
            try
            {
                sortSqlStr = GenericDaoUtils.parseSort (OrderOut.class, pageable, "o");
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
            List <OrderOut> goodsList = query.getResultList ();
            orderPage = new PageImpl <OrderOut> (goodsList, pageable, total);
        }
        else
        {
            String selectCountSql = "SELECT count(o) ";
            String fromSql = "FROM OrderOut o where ";
            String conditionSql = "o.typeCode = ?1 ";
            if (StringUtils.isNotBlank (searchKey))
            {
                conditionSql += "and (o.customer.shortName like ?2 or o.customer.shortCode like ?3)";
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

            String sortSqlStr = "o.customer.shortName asc";
            try
            {
                sortSqlStr = GenericDaoUtils.parseSort (OrderOut.class, pageable, "o");
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
            List <OrderOut> goodsList = query.getResultList ();
            orderPage = new PageImpl <OrderOut> (goodsList, pageable, total);
        }
        return orderPage;
    }

    @Override
    public Page <OrderOut> findBySearchForm (OrderTypeCode typeCode, Pageable pageable, OrderSearchForm searchForm,
                                             long signUser, boolean admin)
    {
        String pageableLog = "offSet:" + pageable.getOffset () + ",pageNumber:" + pageable.getPageNumber ()
                             + ",pageSize:" + pageable.getPageSize () + ",sort:" + pageable.getSort ();
        logger.debug ("Find paging order by fuzzy, pageable: (" + pageableLog + "), searchForm: " + searchForm);
        validateTypeCode (typeCode);
        Page <OrderOut> orderPage;
        if (!admin && 0 != signUser)
        {
            String selectCountSql = "SELECT count(o) ";
            String fromSql = "FROM OrderOut o where ";
            String conditionSql = "o.customer.userSigned = ?1 and o.typeCode = ?2 ";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.dateCreated between ?3 and ?4 and o.customer.id = ?5 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "and o.dateCreated between ?3 and ?4 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.customer.id = ?3 ";
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

            String sortSqlStr = "o.customer.shortName asc";
            try
            {
                sortSqlStr = GenericDaoUtils.parseSort (OrderOut.class, pageable, "o");
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
            List <OrderOut> goodsList = query.getResultList ();
            orderPage = new PageImpl <OrderOut> (goodsList, pageable, total);
        }
        else
        {
            String selectCountSql = "SELECT count(o) ";
            String fromSql = "FROM OrderOut o where ";
            String conditionSql = "o.typeCode = ?1 ";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 and o.customer.id = ?4 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.customer.id = ?2 ";
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

            String sortSqlStr = "o.customer.shortName asc";
            try
            {
                sortSqlStr = GenericDaoUtils.parseSort (OrderOut.class, pageable, "o");
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
            List <OrderOut> goodsList = query.getResultList ();
            orderPage = new PageImpl <OrderOut> (goodsList, pageable, total);
        }
        return orderPage;
    }

    @Override
    public Page <OrderOut> findBySearchForm (Pageable pageable, OrderSearchForm searchForm, long signUser, boolean admin)
                                                                                                                         throws PCDalException
    {
        String pageableLog = "offSet:" + pageable.getOffset () + ",pageNumber:" + pageable.getPageNumber ()
                             + ",pageSize:" + pageable.getPageSize () + ",sort:" + pageable.getSort ();
        logger.debug ("Find paging order by fuzzy, pageable: (" + pageableLog + "), searchForm: " + searchForm);
        Page <OrderOut> orderPage;
        if (!admin && 0 != signUser)
        {
            String selectCountSql = "SELECT count(o) ";
            String fromSql = "FROM OrderOut o where ";
            String conditionSql = "o.customer.userSigned = ?1 ";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 and o.customer.id = ?4 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.customer.id = ?2 ";
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

            String sortSqlStr = "o.customer.shortName asc";
            try
            {
                sortSqlStr = GenericDaoUtils.parseSort (OrderOut.class, pageable, "o");
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
            List <OrderOut> goodsList = query.getResultList ();
            orderPage = new PageImpl <OrderOut> (goodsList, pageable, total);
        }
        else
        {
            String selectCountSql = "SELECT count(o) ";
            String fromSql = "FROM OrderOut o where ";
            String conditionSql = "";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "o.dateCreated between ?1 and ?2 and o.customer.id = ?3 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "o.dateCreated between ?1 and ?2 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "o.customer.id = ?1 ";
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

            String sortSqlStr = "o.customer.shortName asc";
            try
            {
                sortSqlStr = GenericDaoUtils.parseSort (OrderOut.class, pageable, "o");
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
            List <OrderOut> goodsList = query.getResultList ();
            orderPage = new PageImpl <OrderOut> (goodsList, pageable, total);
        }
        return orderPage;
    }

    @Override
    public float countReceivableBy (OrderTypeCode typeCode, OrderSearchForm searchForm, long signUser, boolean admin)
    {
        float sumReceivable = 0;
        if (!admin && 0 != signUser)
        {
            String selectCountSql = "SELECT function ";
            String fromSql = "FROM OrderOut o where ";
            String conditionSql = "o.customer.userSigned = ?1 and o.typeCode = ?2 ";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.dateCreated between ?3 and ?4 and o.customer.id = ?5 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "and o.dateCreated between ?3 and ?4 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.customer.id = ?3 ";
                }
            }

            String countSql = selectCountSql + fromSql + conditionSql;
            String countReceivable = countSql.replaceFirst ("function", "sum(o.receivable)");
            Query countQuery = getEntityManager ().createQuery (countReceivable);
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
            sumReceivable = Float.parseFloat (countQuery.getResultList ().get (0).toString ());
        }
        else
        {
            String selectCountSql = "SELECT function ";
            String fromSql = "FROM OrderOut o where ";
            String conditionSql = "o.typeCode = ?1 ";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 and o.customer.id = ?4 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.customer.id = ?2 ";
                }
            }

            String countSql = selectCountSql + fromSql + conditionSql;
            String countReceivable = countSql.replaceFirst ("function", "sum(o.receivable)");
            Query countQuery = getEntityManager ().createQuery (countReceivable);
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
            sumReceivable = Float.parseFloat (countQuery.getResultList ().get (0).toString ());
        }
        return sumReceivable;
    }

    @Override
    public float countReceivableBy (OrderSearchForm searchForm, long signUser, boolean admin)
    {
        float sumReceivable = 0;
        if (!admin && 0 != signUser)
        {
            String selectCountSql = "SELECT function ";
            String fromSql = "FROM OrderOut o where ";
            String conditionSql = "o.customer.userSigned = ?1 ";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 and o.customer.id = ?4 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.customer.id = ?2 ";
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
            String fromSql = "FROM OrderOut o where ";
            String conditionSql = "";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "o.dateCreated between ?1 and ?2 and o.customer.id = ?3 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "o.dateCreated between ?1 and ?2 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "o.customer.id = ?1 ";
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
    public float countPaidMoneyBy (OrderTypeCode typeCode, OrderSearchForm searchForm, long signUser, boolean admin)
    {
        float sumReceivable = 0;
        if (!admin && 0 != signUser)
        {
            String selectCountSql = "SELECT function ";
            String fromSql = "FROM OrderOut o where ";
            String conditionSql = "o.customer.userSigned = ?1 and o.typeCode = ?2 ";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.dateCreated between ?3 and ?4 and o.customer.id = ?5 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "and o.dateCreated between ?3 and ?4 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.customer.id = ?3 ";
                }
            }

            String countSql = selectCountSql + fromSql + conditionSql;
            String countReceivable = countSql.replaceFirst ("function", "sum(o.paidMoney)");
            Query countQuery = getEntityManager ().createQuery (countReceivable);
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
            sumReceivable = Float.parseFloat (countQuery.getResultList ().get (0).toString ());
        }
        else
        {
            String selectCountSql = "SELECT function ";
            String fromSql = "FROM OrderOut o where ";
            String conditionSql = "o.typeCode = ?1 ";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 and o.customer.id = ?4 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.customer.id = ?2 ";
                }
            }

            String countSql = selectCountSql + fromSql + conditionSql;
            String countReceivable = countSql.replaceFirst ("function", "sum(o.paidMoney)");
            Query countQuery = getEntityManager ().createQuery (countReceivable);
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
            String fromSql = "FROM OrderOut o where ";
            String conditionSql = "o.customer.userSigned = ?1 ";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 and o.customer.id = ?4 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.customer.id = ?2 ";
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
            String fromSql = "FROM OrderOut o where ";
            String conditionSql = "";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "o.dateCreated between ?1 and ?2 and o.customer.id = ?3 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "o.dateCreated between ?1 and ?2 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "o.customer.id = ?1 ";
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
    public float countProfitBy (OrderTypeCode typeCode, OrderSearchForm searchForm, long signUser, boolean admin)
    {
        float sumProfit = 0;
        if (!admin && 0 != signUser)
        {
            String selectCountSql = "SELECT function ";
            String fromSql = "FROM OrderOut o where ";
            String conditionSql = "o.customer.userSigned = ?1 and o.typeCode = ?2 ";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.dateCreated between ?3 and ?4 and o.customer.id = ?5 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "and o.dateCreated between ?3 and ?4 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.customer.id = ?3 ";
                }
            }

            String countSql = selectCountSql + fromSql + conditionSql;
            String countProfit = countSql.replaceFirst ("function", "sum(o.profit)");
            Query countQuery = getEntityManager ().createQuery (countProfit);
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
            sumProfit = Float.parseFloat (countQuery.getResultList ().get (0).toString ());
        }
        else
        {
            String selectCountSql = "SELECT function ";
            String fromSql = "FROM OrderOut o where ";
            String conditionSql = "o.typeCode = ?1 ";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 and o.customer.id = ?4 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.customer.id = ?2 ";
                }
            }

            String countSql = selectCountSql + fromSql + conditionSql;
            String countProfit = countSql.replaceFirst ("function", "sum(o.profit)");
            Query countQuery = getEntityManager ().createQuery (countProfit);
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
            sumProfit = Float.parseFloat (countQuery.getResultList ().get (0).toString ());
        }
        return sumProfit;
    }

    @Override
    public float countProfitBy (OrderSearchForm searchForm, long signUser, boolean admin)
    {
        float sumProfit = 0;
        if (!admin && 0 != signUser)
        {
            String selectCountSql = "SELECT function ";
            String fromSql = "FROM OrderOut o where ";
            String conditionSql = "o.customer.userSigned = ?1 ";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 and o.customer.id = ?4 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "and o.dateCreated between ?2 and ?3 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "and o.customer.id = ?2 ";
                }
            }

            String countSql = selectCountSql + fromSql + conditionSql;
            String countProfit = countSql.replaceFirst ("function", "sum(o.profit)");
            Query countQuery = getEntityManager ().createQuery (countProfit);
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
            sumProfit = Float.parseFloat (countQuery.getResultList ().get (0).toString ());
        }
        else
        {
            String selectCountSql = "SELECT function ";
            String fromSql = "FROM OrderOut o where ";
            String conditionSql = "";
            if (null != searchForm)
            {
                if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
                {
                    conditionSql += "o.dateCreated between ?1 and ?2 and o.customer.id = ?3 ";
                }
                else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
                {
                    conditionSql += "o.dateCreated between ?1 and ?2 ";
                }
                else if (0 != searchForm.getCustomerId ())
                {
                    conditionSql += "o.customer.id = ?1 ";
                }
            }

            String countSql = selectCountSql + fromSql + conditionSql;
            String countProfit = countSql.replaceFirst ("function", "sum(o.profit)");
            Query countQuery = getEntityManager ().createQuery (countProfit);
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
            sumProfit = Float.parseFloat (countQuery.getResultList ().get (0).toString ());
        }
        return sumProfit;
    }

    @Override
    public OrderOut findLast (long customerId)
    {
        String sql = "select o from OrderOut o where o.dateCreated = (select max(o1.dateCreated) from OrderOut o1 where o1.customer.id = ?1)";
        Query query = getEntityManager ().createQuery (sql, OrderOut.class);
        query.setParameter (1, customerId);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (OrderOut) query.getResultList ().get (0) : null;
    }

    @Override
    public OrderOut findByBid (String bid)
    {
        String sql = "select o from OrderOut o where o.bid = ?1";
        Query query = getEntityManager ().createQuery (sql, OrderOut.class);
        query.setParameter (1, bid);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (OrderOut) query.getResultList ().get (0) : null;
    }

    @Override
    public Timestamp findOldest ()
    {
        String sql = "select min(o1.dateCreated) from OrderOut o1";
        Query query = getEntityManager ().createQuery (sql, Date.class);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (Timestamp) query.getResultList ().get (0) : null;
    }

    private void validateTypeCode (OrderTypeCode typeCode)
    {
        if (null == typeCode)
        {
            throw new PCDalException ("OrderOut type code can not null.");
        }
    }
}
