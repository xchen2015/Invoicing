package com.pinfly.purchasecharge.dal.goods;

import java.util.List;

import com.pinfly.purchasecharge.core.model.GoodsIssueSearchForm;
import com.pinfly.purchasecharge.core.model.GoodsIssueStatusCode;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsIssue;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface GoodsIssueDao extends MyGenericDao <GoodsIssue, Long>
{
    public GoodsIssue findBySerialNumber (String serialNumber);

    public List <GoodsIssue> findByGoods (long goods);

    public List <GoodsIssue> findByProvider (long provider);

    public List <GoodsIssue> findByCustomer (long customer);

    public List <GoodsIssue> findByStatus (GoodsIssueStatusCode statusCode);

    public List <GoodsIssue> findBySearchForm (GoodsIssueSearchForm searchForm);

    public int updateStatus (long id, String comment, GoodsIssueStatusCode statusCode);

}
