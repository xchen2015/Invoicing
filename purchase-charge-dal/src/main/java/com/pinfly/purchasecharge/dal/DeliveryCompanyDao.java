package com.pinfly.purchasecharge.dal;

import com.pinfly.purchasecharge.core.model.persistence.DeliveryCompany;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface DeliveryCompanyDao extends MyGenericDao <DeliveryCompany, Long>
{
    DeliveryCompany findByName (String name);
}
