package com.pinfly.purchasecharge.dal.common;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings ("all")
@Transactional (readOnly = true)
public class MyGenericDaoImpl <T, ID extends Serializable> implements MyGenericDao <T, ID>
{
    private static final Logger LOGGER = Logger.getLogger (MyGenericDaoImpl.class);

    private Class <T> persistenceClass;
    @PersistenceContext (unitName = "pc-persistence-unit")
    private EntityManager entityManager;
    private SimpleJpaRepository <T, ID> jpaRepository;

    public MyGenericDaoImpl (Class <T> persistenceClass)
    {
        this.persistenceClass = persistenceClass;
    }

    protected EntityManager getEntityManager ()
    {
        return entityManager;
    }

    private void initJpaRepository ()
    {
        if (null == jpaRepository)
        {
            this.jpaRepository = new SimpleJpaRepository <T, ID> (persistenceClass, entityManager);
        }
    }

    @Override
    @Transactional
    public T save (T t)
    {
        initJpaRepository ();
        return this.jpaRepository.save (t);
    }

    @Override
    @Transactional
    public Iterable <T> save (Iterable <T> ts)
    {
        initJpaRepository ();
        return this.jpaRepository.save (ts);
    }

    @Override
    public T findOne (ID id)
    {
        initJpaRepository ();
        return this.jpaRepository.findOne (id);
    }

    @Override
    public boolean exists (ID id)
    {
        initJpaRepository ();
        return this.jpaRepository.exists (id);
    }

    @Override
    public Iterable <T> findAll ()
    {
        initJpaRepository ();
        return this.jpaRepository.findAll ();
    }

    @Override
    public Iterable <T> findAll (Iterable <ID> ids)
    {
        initJpaRepository ();
        return this.jpaRepository.findAll (ids);
    }

    @Override
    public long count ()
    {
        initJpaRepository ();
        return this.jpaRepository.count ();
    }

    @Override
    @Transactional
    public void delete (ID id)
    {
        initJpaRepository ();
        this.jpaRepository.delete (id);
    }

    @Override
    @Transactional
    public void delete (T entity)
    {
        initJpaRepository ();
        this.jpaRepository.delete (entity);
    }

    @Override
    @Transactional
    public void delete (Iterable <T> ts)
    {
        initJpaRepository ();
        this.jpaRepository.delete (ts);
    }

    @Override
    @Transactional
    public void deleteAll ()
    {
        initJpaRepository ();
        this.jpaRepository.deleteAll ();
    }

}
