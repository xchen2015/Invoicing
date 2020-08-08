package com.pinfly.purchasecharge.dal.common;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.apache.openjpa.util.ObjectExistsException;
import org.apache.openjpa.util.ObjectNotFoundException;
import org.springframework.data.support.IsNewStrategy;
import org.springframework.data.support.IsNewStrategyFactory;
import org.springframework.data.support.IsNewStrategyFactorySupport;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings ("all")
public abstract class AbstractGenericDao <T, PK extends Serializable> implements GenericDao <T, PK>
{
    private static final Logger logger = Logger.getLogger (AbstractGenericDao.class);

    private Class <T> persistenceClass;
    private EntityManager entityManager;

    @PersistenceContext (unitName = "pc-persistence-unit")
    public void setEntityManager (EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    public AbstractGenericDao ()
    {
    }

    public AbstractGenericDao (final Class <T> persistenceClass)
    {
        logger.info ("persistence class : " + persistenceClass.getName ());
        this.persistenceClass = persistenceClass;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cxx.purchasecharge.dal.GenericDao#insert(java.lang.Object)
     */
    @Transactional
    public void insert (T object)
    {
        Object primaryKey = GenericDaoUtils.getPrimaryKey (object);
        logger.info ("object's primary key when insert : " + primaryKey);
        if (null != primaryKey)
        {
            @SuppressWarnings ("unchecked")
            PK id = (PK) primaryKey;
            T dbObject = get (id);
            if (null != dbObject)
            {
                logger.info (object.getClass ().getSimpleName () + " with id=" + primaryKey + " exist.");
                throw new ObjectExistsException (object.getClass ().getSimpleName () + " with id=" + primaryKey
                                                 + " exist.");
            }
        }
        this.entityManager.persist (object);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cxx.purchasecharge.dal.GenericDao#update(java.lang.Object)
     */
    @Transactional
    public void update (T object)
    {
        Object primaryKey = GenericDaoUtils.getPrimaryKey (object);
        logger.info ("object's primary key when update : " + primaryKey);
        if (null != primaryKey)
        {
            @SuppressWarnings ("unchecked")
            PK id = (PK) primaryKey;
            T dbObject = get (id);
            if (null == dbObject)
            {
                logger.info (object.getClass ().getSimpleName () + " with id=" + primaryKey + " not exist.");
                throw new ObjectNotFoundException (object.getClass ().getSimpleName () + " with id=" + primaryKey
                                                   + " not exist.");
            }
            this.entityManager.merge (object);
        }
        else
        {
            logger.info (object.getClass ().getSimpleName () + " with id=" + primaryKey + " not exist.");
            throw new ObjectNotFoundException (object.getClass ().getSimpleName () + " with id=" + primaryKey
                                               + " not exist.");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cxx.purchasecharge.dal.GenericDao#delete(java.lang.Object)
     */
    @Transactional
    public void delete (T object)
    {
        Object primaryKey = GenericDaoUtils.getPrimaryKey (object);
        logger.info ("object's primary key when delete : " + primaryKey);
        if (null != primaryKey)
        {
            @SuppressWarnings ("unchecked")
            PK id = (PK) primaryKey;
            T dbObject = get (id);
            this.entityManager.remove (dbObject);
        }
        else
        {
            logger.info (object.getClass ().getSimpleName () + " with id=" + primaryKey + " not exist.");
            throw new ObjectNotFoundException (object.getClass ().getSimpleName () + " with id=" + primaryKey
                                               + " not exist.");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cxx.purchasecharge.dal.GenericDao#delete(java.io.Serializable)
     */
    @Transactional
    public void delete (PK id)
    {
        T dbObject = get (id);
        if (null != dbObject)
        {
            this.entityManager.remove (dbObject);
        }
        else
        {
            logger.info ("Object with id=" + id + " not exist.");
            throw new ObjectNotFoundException ("Object with id=" + id + " not exist.");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cxx.purchasecharge.dal.GenericDao#get(java.io.Serializable)
     */
    @Override
    public T get (PK id)
    {
        return this.entityManager.find (this.persistenceClass, id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cxx.purchasecharge.dal.GenericDao#getAll()
     */
    @Override
    public List <T> getAll ()
    {
        String classShortName = this.persistenceClass.getSimpleName ();
        String classAlias = Character.toLowerCase (classShortName.charAt (0)) + "";
        String querySql = "select " + classAlias + " from " + classShortName + " " + classAlias;
        logger.info ("the sql of getAll" + classShortName + ": " + querySql);
        // List <T> list = (List <T>) this.entityManager.createNativeQuery
        // (querySql);
        Query query = this.entityManager.createQuery (querySql);
        @SuppressWarnings ("unchecked")
        List <T> list = (List <T>) query.getResultList ();
        return list;
    }

    @Override
    public long count (String[] searchFields, String searchKey)
    {
        if (null == searchFields || searchFields.length == 0)
        {
            return 0;
        }
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder ();
        CriteriaQuery <Long> criteriaQuery = criteriaBuilder.createQuery (Long.class);
        Root <T> resultObj = criteriaQuery.from (this.persistenceClass);
        criteriaQuery.select (criteriaBuilder.count (resultObj));
        // Predicate predicate = null;
        // int predicateCount = searchFields.length;
        // for (int i = 0; i < predicateCount; i++)
        // {
        // }
        criteriaQuery.where (criteriaBuilder.or (criteriaBuilder.like (resultObj.<String> get (searchFields[0]),
                                                                       criteriaBuilder.parameter (String.class, "param")),
                                                 criteriaBuilder.like (resultObj.<String> get (searchFields[1]),
                                                                       criteriaBuilder.parameter (String.class, "param"))));

        TypedQuery <Long> tq = this.entityManager.createQuery (criteriaQuery);
        String key = (null == searchKey ? "" : searchKey.trim ());
        tq.setParameter ("param", key + "%");
        return tq.getSingleResult ();
    }

    public PagingResult <T> getPagingResult (String[] searchFields, String searchKey, String sortField, boolean isASC,
                                             int startIndex, int endIndex)
    {
        String key = (null == searchKey ? "" : searchKey.trim ());
        int totalCount = (int) count (searchFields, key);

        startIndex = Math.max (0, startIndex);
        endIndex = (endIndex < 0 || endIndex >= totalCount) ? totalCount - 1 : endIndex;
        logger.info ("start is " + startIndex + ", end is " + endIndex);

        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder ();
        CriteriaQuery <T> criteriaQuery = criteriaBuilder.createQuery (this.persistenceClass);
        Root <T> resultObj = criteriaQuery.from (this.persistenceClass);
        criteriaQuery.select (resultObj);
        for (String searchField : searchFields)
        {
            criteriaQuery.where (criteriaBuilder.like (resultObj.<String> get (searchField),
                                                       criteriaBuilder.parameter (String.class, "param")));
        }

        if (isASC)
        {
            criteriaQuery.orderBy (criteriaBuilder.asc (resultObj.<String> get (sortField)));
        }
        else
        {
            criteriaQuery.orderBy (criteriaBuilder.desc (resultObj.<String> get (sortField)));
        }

        TypedQuery <T> dLTypedQuery = this.entityManager.createQuery (criteriaQuery);
        dLTypedQuery.setParameter ("param", key + "%");
        dLTypedQuery.setFirstResult (startIndex);
        dLTypedQuery.setMaxResults (endIndex - startIndex + 1);

        List <T> distributionLists = dLTypedQuery.getResultList ();
        // this.entityManager.close ();
        PagingResult <T> distributionListPagedResult = new PagingResult <T> ();
        distributionListPagedResult.setList (distributionLists);
        distributionListPagedResult.setCount (totalCount);
        return distributionListPagedResult;
    }

}
