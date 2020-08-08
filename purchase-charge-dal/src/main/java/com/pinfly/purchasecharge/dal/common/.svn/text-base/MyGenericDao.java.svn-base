package com.pinfly.purchasecharge.dal.common;

import java.io.Serializable;

public interface MyGenericDao <T, ID extends Serializable>
{
    T save (T t);

    Iterable <T> save (Iterable <T> ts);

    T findOne (ID id);

    boolean exists (ID id);

    Iterable <T> findAll ();

    Iterable <T> findAll (Iterable <ID> ids);

    long count ();

    void delete (ID id);

    void delete (T entity);

    void delete (Iterable <T> ts);

    void deleteAll ();
}
