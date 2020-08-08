package com.pinfly.purchasecharge.dal;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pinfly.purchasecharge.core.model.PaymentTransferSearchForm;
import com.pinfly.purchasecharge.core.model.persistence.PaymentTransfer;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface PaymentTransferDao extends MyGenericDao <PaymentTransfer, Long>
{
    public List <PaymentTransfer> findByAccount (long accountId);

    public Page <PaymentTransfer> findBySearchForm (Pageable pageable, PaymentTransferSearchForm searchForm);
    
    public float countInMoney (PaymentTransferSearchForm searchForm);
    
    public float countOutMoney (PaymentTransferSearchForm searchForm);

}
