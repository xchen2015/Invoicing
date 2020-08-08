package com.pinfly.purchasecharge.dal.common;

import java.io.Serializable;
import java.util.List;

public interface GenericDao <T, PK extends Serializable>
{
    public void insert (T object);

    public void update (T object);

    public void delete (T object);

    public void delete (PK id);

    public T get (PK id);

    public List <T> getAll ();

    public long count (String[] searchFields, String searchKey);
}
