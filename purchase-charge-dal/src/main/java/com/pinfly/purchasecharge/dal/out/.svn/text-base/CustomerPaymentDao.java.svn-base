package com.pinfly.purchasecharge.dal.out;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pinfly.purchasecharge.core.model.PaymentSearchForm;
import com.pinfly.purchasecharge.core.model.PaymentTypeCode;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerPayment;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface CustomerPaymentDao extends MyGenericDao <CustomerPayment, Long>
{
    public CustomerPayment findByBid (String bid);
    
    public List <CustomerPayment> findByCustomerId (long customerId);

    public List <CustomerPayment> findByAdvance (PaymentSearchForm searchForm);

    public Page <CustomerPayment> findPagePayment (PaymentSearchForm searchForm, Pageable pageable);

    public float countPaid (PaymentSearchForm searchForm);
    
    public float countNewUnPaid (PaymentSearchForm searchForm);

    public CustomerPayment findLast (long customerId);
    
    public CustomerPayment findByReceiptIdAndType (String receiptId, PaymentTypeCode typeCode);

}
