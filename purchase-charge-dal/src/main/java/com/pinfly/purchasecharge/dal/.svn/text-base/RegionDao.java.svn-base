package com.pinfly.purchasecharge.dal;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.pinfly.purchasecharge.core.model.persistence.Region;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface RegionDao extends MyGenericDao <Region, Long>
{

    @Query (value = "select g from Region g where g.parent.id=?1")
    public List <Region> findByParent (Long parentId);

    public Region findByName (String name);
}
