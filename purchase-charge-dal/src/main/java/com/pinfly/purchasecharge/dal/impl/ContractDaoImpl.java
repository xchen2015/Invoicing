package com.pinfly.purchasecharge.dal.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.pinfly.purchasecharge.core.model.ContractTypeCode;
import com.pinfly.purchasecharge.core.model.persistence.Contract;
import com.pinfly.purchasecharge.dal.ContractDao;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;

public class ContractDaoImpl extends MyGenericDaoImpl <Contract, Long> implements ContractDao
{

    public ContractDaoImpl ()
    {
        super (Contract.class);
    }

    @Override
    public Contract findByName (String name)
    {
        String sql = "select p from Contract p where p.name = ?1";
        Query query = getEntityManager ().createQuery (sql, Contract.class);
        query.setParameter (1, name);
        return (CollectionUtils.isNotEmpty (query.getResultList ())) ? (Contract) query.getResultList ().get (0) : null;
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <Contract> findByNameLike (String name)
    {
        String sql = "select p from Contract p where p.name like ?1 order by p.dateCreated desc";
        Query query = getEntityManager ().createQuery (sql, Contract.class);
        query.setParameter (1, "%" + name + "%");
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <Contract> findByTypeOrSource (ContractTypeCode typeCode, String source)
    {
        if (null != typeCode && StringUtils.isNotBlank (source))
        {
            String sql = "select p from Contract p where p.typeCode = ?1 and p.source = ?2 order by p.dateCreated desc";
            Query query = getEntityManager ().createQuery (sql, Contract.class);
            query.setParameter (1, typeCode);
            query.setParameter (2, source);
            return query.getResultList ();
        }
        else if (null != typeCode)
        {
            String sql = "select p from Contract p where p.typeCode = ?1 order by p.dateCreated desc";
            Query query = getEntityManager ().createQuery (sql, Contract.class);
            query.setParameter (1, typeCode);
            return query.getResultList ();
        }
        else if (StringUtils.isNotBlank (source))
        {
            String sql = "select p from Contract p where p.source = ?1 order by p.dateCreated desc";
            Query query = getEntityManager ().createQuery (sql, Contract.class);
            query.setParameter (1, source);
            return query.getResultList ();
        }
        return null;
    }

}
