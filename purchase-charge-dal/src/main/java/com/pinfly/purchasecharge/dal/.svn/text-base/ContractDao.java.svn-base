package com.pinfly.purchasecharge.dal;

import java.util.List;

import com.pinfly.purchasecharge.core.model.ContractTypeCode;
import com.pinfly.purchasecharge.core.model.persistence.Contract;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface ContractDao extends MyGenericDao <Contract, Long>
{
    Contract findByName (String name);

    List <Contract> findByNameLike (String name);

    List <Contract> findByTypeOrSource (ContractTypeCode typeCode, String source);
}
