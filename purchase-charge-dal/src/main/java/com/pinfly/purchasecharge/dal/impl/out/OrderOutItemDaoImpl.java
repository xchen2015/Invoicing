package com.pinfly.purchasecharge.dal.impl.out;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.core.model.OrderItemSearchForm;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOutItem;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.out.OrderOutItemDao;

public class OrderOutItemDaoImpl extends MyGenericDaoImpl <OrderOutItem, Long> implements OrderOutItemDao
{
    @SuppressWarnings ("unused")
    private static final Logger logger = Logger.getLogger (OrderOutItemDaoImpl.class);

    public OrderOutItemDaoImpl ()
    {
        super (OrderOutItem.class);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <OrderOutItem> findByOrder (long orderId, boolean resultWithOrder)
    {
        String sql = "select o from OrderOutItem o where o.orderOut.id=?1";
        if(resultWithOrder) 
        {
            sql = "select o from OrderOutItem o join fetch o.orderOut where o.orderOut.id=?1";
        }
        Query query = getEntityManager ().createQuery (sql, OrderOutItem.class);
        query.setParameter (1, orderId);
        return query.getResultList ();
    }
    
    @Override
    @SuppressWarnings ("unchecked")
    public List <OrderOutItem> findByGoods (long goodsId)
    {
        String sql = "select o from OrderOutItem o where o.goods.id=?1";
        Query query = getEntityManager ().createQuery (sql, OrderOutItem.class);
        query.setParameter (1, goodsId);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <OrderOutItem> findBySearchForm (OrderItemSearchForm searchForm)
    {
        String sql = "select ooi from OrderOutItem ooi join fetch ooi.orderOut ";
        if(null != searchForm) 
        {
            if(0 != searchForm.getGoodsId () && 0 != searchForm.getCustomerId () && 0!= searchForm.getUserCreatedBy () && null != searchForm.getStartDate () && null != searchForm.getEndDate ()) 
            {
                sql += "where ooi.goods.id=?1 and ooi.orderOut.customer.id=?2 and ooi.orderOut.userCreatedBy=?3 and ooi.orderOut.dateCreated between ?4 and ?5";
            }
            else if(0 != searchForm.getGoodsId () && 0 != searchForm.getCustomerId () && null != searchForm.getStartDate () && null != searchForm.getEndDate ()) 
            {
                sql += "where ooi.goods.id=?1 and ooi.orderOut.customer.id=?2 and ooi.orderOut.dateCreated between ?3 and ?4";
            }
            else if(0 != searchForm.getGoodsId () && 0!= searchForm.getUserCreatedBy () && null != searchForm.getStartDate () && null != searchForm.getEndDate ()) 
            {
                sql += "where ooi.goods.id=?1 and ooi.orderOut.userCreatedBy=?2 and ooi.orderOut.dateCreated between ?3 and ?4";
            }
            else if(0 != searchForm.getCustomerId () && 0!= searchForm.getUserCreatedBy () && null != searchForm.getStartDate () && null != searchForm.getEndDate ()) 
            {
                sql += "where ooi.orderOut.customer.id=?1 and ooi.orderOut.userCreatedBy=?2 and ooi.orderOut.dateCreated between ?3 and ?4";
            }
            else if(0 != searchForm.getGoodsId () && null != searchForm.getStartDate () && null != searchForm.getEndDate ()) 
            {
                sql += "where ooi.goods.id=?1 and ooi.orderOut.dateCreated between ?2 and ?3";
            }
            else if(0 != searchForm.getCustomerId () && null != searchForm.getStartDate () && null != searchForm.getEndDate ()) 
            {
                sql += "where ooi.orderOut.customer.id=?1 and ooi.orderOut.dateCreated between ?2 and ?3";
            }
            else if(0 != searchForm.getUserCreatedBy () && null != searchForm.getStartDate () && null != searchForm.getEndDate ()) 
            {
                sql += "where ooi.orderOut.userCreatedBy=?1 and ooi.orderOut.dateCreated between ?2 and ?3";
            }
            else if(null != searchForm.getStartDate () && null != searchForm.getEndDate ()) 
            {
                sql += "where ooi.orderOut.dateCreated between ?1 and ?2";
            }
            else if(0 != searchForm.getGoodsId ()) 
            {
                sql += "where ooi.goods.id=?1";
            }
            else if(0 != searchForm.getCustomerId ()) 
            {
                sql += "where ooi.orderOut.customer.id=?1";
            }
            else if(0!= searchForm.getUserCreatedBy ()) 
            {
                sql += "where ooi.orderOut.userCreatedBy=?1";
            }
            sql += " order by ooi.orderOut.dateCreated desc";
            
            Query query = getEntityManager ().createQuery (sql, OrderOutItem.class);
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
