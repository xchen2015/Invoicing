package com.pinfly.purchasecharge.dal;

import java.util.List;

import com.pinfly.purchasecharge.core.model.persistence.Project;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface ProjectDao extends MyGenericDao <Project, Long>
{
    public Project findByName (String name);

    public List <Project> findByNameLike (String name);

    public List <Project> findByCustomer (long customerId);

}
