package com.pinfly.purchasecharge.dal.in;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pinfly.purchasecharge.core.model.persistence.in.Provider;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;
import com.pinfly.purchasecharge.dal.exception.PCDalException;

public interface ProviderDao extends MyGenericDao <Provider, Long>
{
    Provider findByShortName (String shortName);

    Provider findByShortCode (String shortCode);

    List <Provider> findByType (long providerType);

    Page <Provider> findByFuzzy (Pageable pageable, String searchKey, long signUser, boolean admin);

    Page <Provider> findProviderByFuzzy (Pageable pageable, String searchKey, long signUser, boolean admin);

    List <Provider> findAll (long signUser, boolean admin);

    List <Provider> findByShortNameLike (String name, long signUser, boolean admin);

    void updateProviderCope (long providerId, float money) throws PCDalException;

    float countUnPay (String searchKey, long signUser, boolean admin);

}
