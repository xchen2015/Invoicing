package com.pinfly.purchasecharge.dal;

import java.util.List;

import com.pinfly.purchasecharge.core.model.AccountingModeCode;
import com.pinfly.purchasecharge.core.model.persistence.AccountingType;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface AccountingTypeDao extends MyGenericDao <AccountingType, Long>
{
    public AccountingType findByName (String name);

    public List <AccountingType> findByMode (AccountingModeCode mode);

}
