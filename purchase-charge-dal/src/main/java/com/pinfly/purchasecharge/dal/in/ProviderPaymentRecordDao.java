package com.pinfly.purchasecharge.dal.in;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pinfly.purchasecharge.core.model.PaymentSearchForm;
import com.pinfly.purchasecharge.core.model.persistence.in.ProviderPaymentRecord;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface ProviderPaymentRecordDao extends MyGenericDao <ProviderPaymentRecord, Long>
{
    List <ProviderPaymentRecord> findByPaymentWay (long paymentWayId);

    Page <ProviderPaymentRecord> findPagePaymentRecord (Pageable pageable, PaymentSearchForm searchForm);
}
