package com.pinfly.purchasecharge.dal.impl.in;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.core.model.OrderItemSearchForm;
import com.pinfly.purchasecharge.core.model.persistence.in.OrderInItem;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.in.OrderInItemDao;

public class OrderInItemDaoImpl extends MyGenericDaoImpl <OrderInItem, Long> implements OrderInItemDao
{
    @SuppressWarnings ("unused")
    private static final Logger logger = Logger.getLogger (OrderInItemDaoImpl.class);

    public OrderInItemDaoImpl ()
    {
        super (OrderInItem.class);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <OrderInItem> findByOrder (long orderId, boolean resultWithOrder)
    {
        String sql = "select o from OrderInItem o where o.orderIn.id=?1";
        if(resultWithOrder) 
        {
            sql = "select o from OrderInItem o join fetch o.orderIn where o.orderIn.id=?1";
        }
        Query query = getEntityManager ().createQuery (sql, OrderInItem.class);
        query.setParameter (1, orderId);
        return query.getResultList ();
    }
    
    @Override
    @SuppressWarnings ("unchecked")
    public List <OrderInItem> findByGoods (long goodsId)
    {
        String sql = "select o from OrderInItem o where o.goods.id=?1";
        Query query = getEntityManager ().createQuery (sql, OrderInItem.class);
        query.setParameter (1, goodsId);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <OrderInItem> findBySearchForm (OrderItemSearchForm searchForm)
    {
        String sql = "select oii from OrderInItem oii join fetch oii.orderIn ";
        if(null != searchForm) 
        {
            if(0 != searchForm.getGoodsId () && 0 != searchForm.getCustomerId () && 0!= searchForm.getUserCreatedBy () && null != searchForm.getStartDate () && null != searchForm.getEndDate ()) 
            {
                sql += "where oii.goods.id=?1 and oii.orderIn.provider.id=?2 and oii.orderIn.userCreatedBy=?3 and oii.orderIn.dateCreated between ?4 and ?5";
            }
            else if(0 != searchForm.getGoodsId () && 0 != searchForm.getCustomerId () && null != searchForm.getStartDate () && null != searchForm.getEndDate ()) 
            {
                sql += "where oii.goods.id=?1 and oii.orderIn.provider.id=?2 and oii.orderIn.dateCreated between ?3 and ?4";
            }
            else if(0 != searchForm.getGoodsId () && 0!= searchForm.getUserCreatedBy () && null != searchForm.getStartDate () && null != searchForm.getEndDate ()) 
            {
                sql += "where oii.goods.id=?1 and oii.orderIn.userCreatedBy=?2 and oii.orderIn.dateCreated between ?3 and ?4";
            }
            else if(0 != searchForm.getCustomerId () && 0!= searchForm.getUserCreatedBy () && null != searchForm.getStartDate () && null != searchForm.getEndDate ()) 
            {
                sql += "where oii.orderIn.provider.id=?1 and oii.orderIn.userCreatedBy=?2 and oii.orderIn.dateCreated between ?3 and ?4";
            }
            else if(0 != searchForm.getGoodsId () && null != searchForm.getStartDate () && null != searchForm.getEndDate ()) 
            {
                sql += "where oii.goods.id=?1 and oii.orderIn.dateCreated between ?2 and ?3";
            }
            else if(0 != searchForm.getCustomerId () && null != searchForm.getStartDate () && null != searchForm.getEndDate ()) 
            {
                sql += "where oii.orderIn.provider.id=?1 and oii.orderIn.dateCreated between ?2 and ?3";
            }
            else if(0 != searchForm.getUserCreatedBy () && null != searchForm.getStartDate () && null != searchForm.getEndDate ()) 
            {
                sql += "where oii.orderIn.userCreatedBy=?1 and oii.orderIn.dateCreated between ?2 and ?3";
            }
            else if(null != searchForm.getStartDate () && null != searchForm.getEndDate ()) 
            {
                sql += "where oii.orderIn.dateCreated between ?1 and ?2";
            }
            else if(0 != searchForm.getGoodsId ()) 
            {
                sql += "where oii.goods.id=?1";
            }
            else if(0 != searchForm.getCustomerId ()) 
            {
                sql += "where oii.orderIn.provider.id=?1";
            }
            else if(0!= searchForm.getUserCreatedBy ()) 
            {
                sql += "where oii.orderIn.userCreatedBy=?1";
            }
            sql += " order by oii.orderIn.dateCreated desc";
            
            Query query = getEntityManager ().createQuery (sql, OrderInItem.class);
            if(0 != searchForm.getGoodsId () && 0 != searchForm.getCustomerId () && 0!= searchForm.getUserCreatedBy () && null != searchForm.getStartDate () && null != searchForm.getEndDate ()) 
            {
                query.setParameter (1, searchForm.getGoodsId ());
                query.setParameter (2, searchForm.getCustomerId ());
                query.setParameter (3, searchForm.getUserCreatedBy ());
                query.setParameter (4, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                query.setParameter (5, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if(0 != searchForm.getGoodsId () && 0 != searchForm.getCustomerId () && null != searchForm.getStartDate () && null != searchForm.getEndDate ()) 
            {
                query.setParameter (1, searchForm.getGoodsId ());
                query.setParameter (2, searchForm.getCustomerId ());
                query.setParameter (3, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                query.setParameter (4, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if(0 != searchForm.getGoodsId () && 0!= searchForm.getUserCreatedBy () && null != searchForm.getStartDate () && null != searchForm.getEndDate ()) 
            {
                query.setParameter (1, searchForm.getGoodsId ());
                query.setParameter (2, searchForm.getUserCreatedBy ());
                query.setParameter (3, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                query.setParameter (4, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if(0 != searchForm.getCustomerId () && 0!= searchForm.getUserCreatedBy () && null != searchForm.getStartDate () && null != searchForm.getEndDate ()) 
            {
                query.setParameter (1, searchForm.getCustomerId ());
                query.setParameter (2, searchForm.getUserCreatedBy ());
                query.setParameter (3, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                query.setParameter (4, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if(0 != searchForm.getGoodsId () && null != searchForm.getStartDate () && null != searchForm.getEndDate ()) 
            {
                query.setParameter (1, searchForm.getGoodsId ());
                query.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                query.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if(0 != searchForm.getCustomerId () && null != searchForm.getStartDate () && null != searchForm.getEndDate ()) 
            {
                query.setParameter (1, searchForm.getCustomerId ());
                query.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                query.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if(0 != searchForm.getUserCreatedBy () && null != searchForm.getStartDate () && null != searchForm.getEndDate ()) 
            {
                query.setParameter (1, searchForm.getUserCreatedBy ());
                query.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                query.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if(null != searchForm.getStartDate () && null != searchForm.getEndDate ()) 
            {
                query.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                query.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if(0 != searchForm.getGoodsId ()) 
            {
                query.setParameter (1, searchForm.getGoodsId ());
            }
            else if(0 != searchForm.getCustomerId ()) 
            {
                query.setParameter (1, searchForm.getCustomerId ());
            }
            else if(0!= searchForm.getUserCreatedBy ()) 
            {
                query.setParameter (1, searchForm.getUserCreatedBy ());
            }
            return query.getResultList ();
        }
        return null;
    }
}
