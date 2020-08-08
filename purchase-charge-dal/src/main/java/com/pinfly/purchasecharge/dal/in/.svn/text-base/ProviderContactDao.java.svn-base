package com.pinfly.purchasecharge.dal.in;

import java.util.List;

import com.pinfly.purchasecharge.core.model.persistence.in.ProviderContact;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface ProviderContactDao extends MyGenericDao <ProviderContact, Long>
{
    public ProviderContact findByName (String name);

    public List <ProviderContact> findByProvider (long providerId);

    List <ProviderContact> findByFuzzyName (String keyWord);

}
