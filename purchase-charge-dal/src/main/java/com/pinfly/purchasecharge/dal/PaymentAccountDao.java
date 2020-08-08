package com.pinfly.purchasecharge.dal;

import com.pinfly.purchasecharge.core.model.persistence.PaymentAccount;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface PaymentAccountDao extends MyGenericDao <PaymentAccount, Long>
{
    public PaymentAccount findByName (String name);

    public PaymentAccount findByAccount (String number);

}
