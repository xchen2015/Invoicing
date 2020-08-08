package com.pinfly.purchasecharge.dal.out;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pinfly.purchasecharge.core.model.persistence.out.Customer;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;
import com.pinfly.purchasecharge.dal.exception.PCDalException;

public interface CustomerDao extends MyGenericDao <Customer, Long>
{
    Customer findByShortName (String shortName);

    Customer findByShortCode (String shortCode);

    List <Customer> findByType (long customerType);

    List <Customer> findByLevel (long customerLevel);

    Page <Customer> findByFuzzy (Pageable pageable, String searchKey, long signUser, boolean admin);

    Page <Customer> findCustomerByFuzzy (Pageable pageable, String searchKey, long signUser, boolean admin);

    List <Customer> findAll (long signUser, boolean admin);

    List <Customer> findByShortNameLike (String name, long signUser, boolean admin);

    void updateCustomerAccounts (long customerId, float money) throws PCDalException;

    void updateCustomerLevel (long customerId, long levelId) throws PCDalException;

    float countUnPay (String searchKey, long signUser, boolean admin);

}
