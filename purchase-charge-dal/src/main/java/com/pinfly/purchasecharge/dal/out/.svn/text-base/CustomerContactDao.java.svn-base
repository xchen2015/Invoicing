package com.pinfly.purchasecharge.dal.out;

import java.util.List;

import com.pinfly.purchasecharge.core.model.persistence.out.CustomerContact;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface CustomerContactDao extends MyGenericDao <CustomerContact, Long>
{
    public CustomerContact findByName (String name);

    public List <CustomerContact> findByCustomer (long customerId);

    List <CustomerContact> findByFuzzyName (String keyWord);

}
