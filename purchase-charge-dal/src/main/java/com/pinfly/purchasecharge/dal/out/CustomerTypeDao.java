package com.pinfly.purchasecharge.dal.out;

import com.pinfly.purchasecharge.core.model.persistence.out.CustomerType;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface CustomerTypeDao extends MyGenericDao <CustomerType, Long>
{
    public CustomerType findByName (String name);

}
