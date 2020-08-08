package com.pinfly.purchasecharge.dal.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import com.pinfly.purchasecharge.core.model.persistence.Project;
import com.pinfly.purchasecharge.dal.ProjectDao;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;

public class ProjectDaoImpl extends MyGenericDaoImpl <Project, Long> implements ProjectDao
{

    public ProjectDaoImpl ()
    {
        super (Project.class);
    }

    @Override
    public Project findByName (String name)
    {
        String sql = "select p from Project p where p.name = ?1";
        Query query = getEntityManager ().createQuery (sql, Project.class);
        query.setParameter (1, name);
        return (CollectionUtils.isNotEmpty (query.getResultList ())) ? (Project) query.getResultList ().get (0) : null;
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <Project> findByNameLike (String name)
    {
        String sql = "select p from Project p where p.name like ?1 order by p.start desc";
        Query query = getEntityManager ().createQuery (sql, Project.class);
        query.setParameter (1, "%" + name + "%");
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <Project> findByCustomer (long customerId)
    {
        String sql = "select p from Project p where p.customer.id = ?1 order by p.start desc";
        Query query = getEntityManager ().createQuery (sql, Project.class);
        query.setParameter (1, customerId);
        return query.getResultList ();
    }

}
