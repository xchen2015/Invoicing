package com.pinfly.purchasecharge.dal.impl.out;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pinfly.purchasecharge.core.model.persistence.out.Customer;
import com.pinfly.purchasecharge.dal.common.GenericDaoUtils;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.exception.PCDalException;
import com.pinfly.purchasecharge.dal.out.CustomerDao;

public class CustomerDaoImpl extends MyGenericDaoImpl <Customer, Long> implements CustomerDao
{
    private static final Logger logger = Logger.getLogger (CustomerDaoImpl.class);

    public CustomerDaoImpl ()
    {
        super (Customer.class);
    }

    @Override
    public Customer findByShortName (String shortName)
    {
        String sql = "select c from Customer c where c.shortName=?1";
        Query query = getEntityManager ().createQuery (sql, Customer.class);
        query.setParameter (1, shortName);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (Customer) query.getResultList ().get (0) : null;
    }

    @Override
    public Customer findByShortCode (String shortCode)
    {
        if (StringUtils.isNotBlank (shortCode))
        {
            String sql = "select c from Customer c where c.shortCode=?1";
            Query query = getEntityManager ().createQuery (sql, Customer.class);
            query.setParameter (1, shortCode.toLowerCase ());
            return CollectionUtils.isNotEmpty (query.getResultList ()) ? (Customer) query.getResultList ().get (0)
                                                                      : null;
        }
        return null;
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <Customer> findByType (long customerType)
    {
        String sql = "select c from Customer c where c.customerType.id=?1";
        Query query = getEntityManager ().createQuery (sql, Customer.class);
        query.setParameter (1, customerType);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <Customer> findByLevel (long customerLevel)
    {
        String sql = "select c from Customer c where c.customerLevel.id=?1";
        Query query = getEntityManager ().createQuery (sql, Customer.class);
        query.setParameter (1, customerLevel);
        return query.getResultList ();
    }

    @Override
    @Transactional (propagation = Propagation.NOT_SUPPORTED)
    public Page <Customer> findByFuzzy (Pageable pageable, String searchKey, long signUser, boolean admin)
    {
        String pageableLog = "offSet:" + pageable.getOffset () + ",pageNumber:" + pageable.getPageNumber ()
                             + ",pageSize:" + pageable.getPageSize () + ",sort:" + pageable.getSort ();
        logger.debug ("Find paging customer by fuzzy, pageable: (" + pageableLog + "), searchkey: " + searchKey);
        String keyWord = (null == searchKey ? "" : searchKey.trim ());
        Page <Customer> customerPage;
        if (!admin && 0 != signUser)
        {
            String selectCountSql = "SELECT count(c) ";
            String fromSql = "FROM Customer c where ";
            String conditionSql = "c.userSigned = ?1 or c.sharable = true ";
            if (StringUtils.isNotBlank (searchKey))
            {
                conditionSql += "and (c.shortName like ?2 or c.shortCode like ?3)";
            }
            Query countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
            countQuery.setParameter (1, signUser);
            if (StringUtils.isNotBlank (searchKey))
            {
                countQuery.setParameter (2, "%" + keyWord + "%");
                countQuery.setParameter (3, "%" + keyWord + "%");
            }
            int total = Integer.parseInt (countQuery.getResultList ().get (0).toString ());

            String querySql = "SELECT c ";
            querySql += fromSql;
            querySql += conditionSql;
            String orderBySql = " ORDER BY ";
            querySql += orderBySql;

            String sortSqlStr = "c.shortName asc";
            try
            {
                sortSqlStr = GenericDaoUtils.parseSort (Customer.class, pageable, "c");
            }
            catch (Exception e)
            {
                // TODO: handle exception
            }
            querySql += sortSqlStr;

            Query query = getEntityManager ().createQuery (querySql);
            query.setParameter (1, signUser);
            if (StringUtils.isNotBlank (searchKey))
            {
                query.setParameter (2, "%" + keyWord + "%");
                query.setParameter (3, "%" + keyWord + "%");
            }

            query.setFirstResult (pageable.getOffset ());
            query.setMaxResults (pageable.getPageSize ());

            @SuppressWarnings ("unchecked")
            List <Customer> customers = query.getResultList ();
            for (Customer c : customers)
            {
                c.getContacts ();
            }
            customerPage = new PageImpl <Customer> (customers, pageable, total);
        }
        else
        {
            String selectCountSql = "SELECT count(c) ";
            String fromSql = "FROM Customer c ";
            String conditionSql = "where ";
            if (StringUtils.isNotBlank (searchKey))
            {
                conditionSql += "(c.shortName like ?1 or c.shortCode like ?2)";
            }
            Query countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
            if (StringUtils.isNotBlank (searchKey))
            {
                countQuery.setParameter (1, "%" + keyWord + "%");
                countQuery.setParameter (2, "%" + keyWord + "%");
            }
            int total = Integer.parseInt (countQuery.getResultList ().get (0).toString ());

            String querySql = "SELECT c ";
            querySql += fromSql;
            querySql += conditionSql;
            String orderBySql = " ORDER BY ";
            querySql += orderBySql;

            String sortSqlStr = "c.shortName asc";
            try
            {
                sortSqlStr = GenericDaoUtils.parseSort (Customer.class, pageable, "c");
            }
            catch (Exception e)
            {
                // TODO: handle exception
            }
            querySql += sortSqlStr;

            Query query = getEntityManager ().createQuery (querySql);
            if (StringUtils.isNotBlank (searchKey))
            {
                query.setParameter (1, "%" + keyWord + "%");
                query.setParameter (2, "%" + keyWord + "%");
            }

            query.setFirstResult (pageable.getOffset ());
            query.setMaxResults (pageable.getPageSize ());

            @SuppressWarnings ("unchecked")
            List <Customer> customers = query.getResultList ();
            for (Customer c : customers)
            {
                c.getContacts ();
            }
            customerPage = new PageImpl <Customer> (customers, pageable, total);
        }
        return customerPage;
    }

    @Override
    public Page <Customer> findCustomerByFuzzy (Pageable pageable, String searchKey, long signUser, boolean admin)
    {
        String pageableLog = "offSet:" + pageable.getOffset () + ",pageNumber:" + pageable.getPageNumber ()
                             + ",pageSize:" + pageable.getPageSize () + ",sort:" + pageable.getSort ();
        logger.debug ("Find paging customer by fuzzy, pageable: (" + pageableLog + "), searchkey: " + searchKey);
        String keyWord = (null == searchKey ? "" : searchKey.trim ());
        Page <Customer> customerPage;
        if (!admin && 0 != signUser)
        {
            String selectCountSql = "SELECT count(c) ";
            String fromSql = "FROM Customer c where ";
            String conditionSql = "c.userSigned = ?1 or c.sharable = true ";
            if (StringUtils.isNotBlank (searchKey))
            {
                conditionSql += "and (c.shortName like ?2 or c.shortCode like ?3)";
            }
            Query countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
            countQuery.setParameter (1, signUser);
            if (StringUtils.isNotBlank (searchKey))
            {
                countQuery.setParameter (2, "%" + keyWord + "%");
                countQuery.setParameter (3, "%" + keyWord + "%");
            }
            int total = Integer.parseInt (countQuery.getResultList ().get (0).toString ());

            String querySql = "SELECT c ";
            querySql += fromSql;
            querySql += conditionSql;
            String orderBySql = " ORDER BY ";
            querySql += orderBySql;

            String sortSqlStr = "c.shortName asc";
            try
            {
                sortSqlStr = GenericDaoUtils.parseSort (Customer.class, pageable, "c");
            }
            catch (Exception e)
            {
                // TODO: handle exception
            }
            querySql += sortSqlStr;

            Query query = getEntityManager ().createQuery (querySql);
            query.setParameter (1, signUser);
            if (StringUtils.isNotBlank (searchKey))
            {
                query.setParameter (2, "%" + keyWord + "%");
                query.setParameter (3, "%" + keyWord + "%");
            }

            query.setFirstResult (pageable.getOffset ());
            query.setMaxResults (pageable.getPageSize ());

            @SuppressWarnings ("unchecked")
            List <Customer> customers = query.getResultList ();
            customerPage = new PageImpl <Customer> (customers, pageable, total);
        }
        else
        {
            String selectCountSql = "SELECT count(c) ";
            String fromSql = "FROM Customer c ";
            String conditionSql = "";
            if (StringUtils.isNotBlank (searchKey))
            {
                conditionSql += "where (c.shortName like ?1 or c.shortCode like ?2)";
            }
            Query countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
            if (StringUtils.isNotBlank (searchKey))
            {
                countQuery.setParameter (1, "%" + keyWord + "%");
                countQuery.setParameter (2, "%" + keyWord + "%");
            }
            int total = Integer.parseInt (countQuery.getResultList ().get (0).toString ());

            String querySql = "SELECT c ";
            querySql += fromSql;
            querySql += conditionSql;
            String orderBySql = " ORDER BY ";
            querySql += orderBySql;

            String sortSqlStr = "c.shortName asc";
            try
            {
                sortSqlStr = GenericDaoUtils.parseSort (Customer.class, pageable, "c");
            }
            catch (Exception e)
            {
                // TODO: handle exception
            }
            querySql += sortSqlStr;

            Query query = getEntityManager ().createQuery (querySql);
            if (StringUtils.isNotBlank (searchKey))
            {
                query.setParameter (1, "%" + keyWord + "%");
                query.setParameter (2, "%" + keyWord + "%");
            }

            query.setFirstResult (pageable.getOffset ());
            query.setMaxResults (pageable.getPageSize ());

            @SuppressWarnings ("unchecked")
            List <Customer> customers = query.getResultList ();
            customerPage = new PageImpl <Customer> (customers, pageable, total);
        }
        return customerPage;
    }

    @Override
    @SuppressWarnings ("unchecked")
    @Transactional (propagation = Propagation.NOT_SUPPORTED)
    public List <Customer> findAll (long signUser, boolean admin)
    {
        List <Customer> customers = new ArrayList <Customer> ();
        String nsql = "select c from Customer c ";
        if (!admin && 0 != signUser)
        {
            nsql += "where c.userSigned = ?1 ";
            nsql += "order by c.shortName asc";
            Query query = getEntityManager ().createQuery (nsql, Customer.class);
            query.setParameter (1, signUser);
            customers = query.getResultList ();
        }
        else
        {
            nsql += "order by c.shortName asc";
            Query query = getEntityManager ().createQuery (nsql, Customer.class);
            customers = query.getResultList ();
        }

        for (Customer c : customers)
        {
            c.getContacts ();
        }
        return customers;
    }

    /**
     * Native sql query speed is lower than JPQL
     * 
     * @param typeCode
     * @param signUser
     * @param admin
     * @return
     */
    @SuppressWarnings ("unchecked")
    @Transactional (propagation = Propagation.NOT_SUPPORTED)
    public List <Customer> getCustomer (long signUser, boolean admin)
    {
        List <Customer> customers = new ArrayList <Customer> ();
        String nsql = "select * from pc_customer c ";
        if (!admin && 0 != signUser)
        {
            nsql += "where c.userSigned = ?1 ";
            nsql += "order by convert(c.shortName USING gbk) COLLATE gbk_chinese_ci asc";
            Query query = getEntityManager ().createNativeQuery (nsql, Customer.class);
            query.setParameter (1, signUser);
            customers = query.getResultList ();
        }
        else
        {
            nsql += "order by convert(c.shortName USING gbk) COLLATE gbk_chinese_ci asc";
            Query query = getEntityManager ().createNativeQuery (nsql, Customer.class);
            customers = query.getResultList ();
        }

        for (Customer c : customers)
        {
            c.getContacts ();
        }
        return customers;
    }

    @SuppressWarnings ("unchecked")
    @Override
    @Transactional (propagation = Propagation.NOT_SUPPORTED)
    public List <Customer> findByShortNameLike (String name, long signUser, boolean admin)
    {
        logger.debug ("findByShortNameLike: " + name);
        List <Customer> customerList = new ArrayList <Customer> ();
        String searchKey = (null == name ? "" : name.trim ());
        if (!admin && 0 != signUser)
        {
            String sql = "select * from pc_customer c where c.userSigned = ?1 or c.sharable = true and (c.shortName like ?2 or c.shortCode like ?3) order by convert(c.shortName USING gbk) COLLATE gbk_chinese_ci asc";
            Query query = getEntityManager ().createNativeQuery (sql, Customer.class);
            query.setParameter (1, signUser);
            query.setParameter (2, "%" + searchKey + "%");
            query.setParameter (3, "%" + searchKey + "%");
            customerList = query.getResultList ();
        }
        else
        {
            String sql = "select * from pc_customer c where c.shortName like ?1 or c.shortCode like ?2 order by convert(c.shortName USING gbk) COLLATE gbk_chinese_ci asc";
            Query query = getEntityManager ().createNativeQuery (sql, Customer.class);
            query.setParameter (1, "%" + searchKey + "%");
            query.setParameter (2, "%" + searchKey + "%");
            customerList = query.getResultList ();
        }

        for (Customer c : customerList)
        {
            c.getContacts ();
        }
        return customerList;
    }

    @Override
    @Transactional
    public void updateCustomerAccounts (long customerId, float money) throws PCDalException
    {
        Query query = getEntityManager ().createQuery ("update Customer p set p.unpayMoney = ?1 where p.id = ?2");
        query.setParameter (1, money);
        query.setParameter (2, customerId);
        query.executeUpdate ();
    }

    @Override
    @Transactional
    public void updateCustomerLevel (long customerId, long levelId) throws PCDalException
    {
        Query query = getEntityManager ().createQuery ("update Customer p set p.customerLevel.id = ?1 where p.id = ?2");
        query.setParameter (1, levelId);
        query.setParameter (2, customerId);
        query.executeUpdate ();
    }

    @Override
    public float countUnPay (String searchKey, long signUser, boolean admin)
    {
        String keyWord = (null == searchKey ? "" : searchKey.trim ());

        float sumUnpay = 0;
        if (!admin && 0 != signUser)
        {
            String selectCountSql = "SELECT sum(c.unpayMoney) ";
            String fromSql = "FROM Customer c where ";
            String conditionSql = "c.userSigned = ?1 ";
            if (StringUtils.isNotBlank (searchKey))
            {
                conditionSql += "and (c.shortName like ?2 or c.shortCode like ?3)";
            }
            Query countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
            countQuery.setParameter (1, signUser);
            if (StringUtils.isNotBlank (searchKey))
            {
                countQuery.setParameter (2, keyWord + "%");
                countQuery.setParameter (3, keyWord + "%");
            }
            sumUnpay = Float.parseFloat (countQuery.getResultList ().get (0).toString ());
        }
        else
        {
            String selectCountSql = "SELECT sum(c.unpayMoney) ";
            String fromSql = "FROM Customer c ";
            String conditionSql = "";
            if (StringUtils.isNotBlank (searchKey))
            {
                conditionSql = "where (c.shortName like ?1 or c.shortCode like ?2)";
            }
            Query countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
            if (StringUtils.isNotBlank (searchKey))
            {
                countQuery.setParameter (1, keyWord + "%");
                countQuery.setParameter (2, keyWord + "%");
            }
            sumUnpay = Float.parseFloat (countQuery.getResultList ().get (0).toString ());
        }
        return sumUnpay;
    }

}
