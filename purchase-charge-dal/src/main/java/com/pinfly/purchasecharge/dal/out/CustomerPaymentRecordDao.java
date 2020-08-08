package com.pinfly.purchasecharge.dal.out;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pinfly.purchasecharge.core.model.PaymentSearchForm;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerPaymentRecord;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface CustomerPaymentRecordDao extends MyGenericDao <CustomerPaymentRecord, Long>
{
    List <CustomerPaymentRecord> findByPaymentWay (long paymentWayId);

    Page <CustomerPaymentRecord> findPagePaymentRecord (Pageable pageable, PaymentSearchForm searchForm);
}
