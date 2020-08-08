package com.pinfly.purchasecharge.dal.in;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pinfly.purchasecharge.core.model.PaymentSearchForm;
import com.pinfly.purchasecharge.core.model.PaymentTypeCode;
import com.pinfly.purchasecharge.core.model.persistence.in.ProviderPayment;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface ProviderPaymentDao extends MyGenericDao <ProviderPayment, Long>
{
    public ProviderPayment findByBid (String bid);
    
    public List <ProviderPayment> findByProviderId (long providerId);

    public List <ProviderPayment> findByAdvance (long providerId, Timestamp start, Timestamp end);

    public Page <ProviderPayment> findPagePayment (PaymentSearchForm searchForm, Pageable pageable);

    public float countNewUnPaid (PaymentSearchForm searchForm);
    
    public float countPaid (PaymentSearchForm searchForm);

    public ProviderPayment findLast (long providerId);
    
    public ProviderPayment findByReceiptIdAndType (String receiptId, PaymentTypeCode typeCode);

}
