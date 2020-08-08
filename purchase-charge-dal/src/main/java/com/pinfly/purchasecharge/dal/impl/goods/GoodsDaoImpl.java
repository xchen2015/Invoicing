package com.pinfly.purchasecharge.dal.impl.goods;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pinfly.purchasecharge.core.model.GoodsStorageCourse;
import com.pinfly.purchasecharge.core.model.GoodsStorageCourseSearchForm;
import com.pinfly.purchasecharge.core.model.persistence.goods.Goods;
import com.pinfly.purchasecharge.dal.common.GenericDaoUtils;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.exception.PCDalException;
import com.pinfly.purchasecharge.dal.goods.GoodsDao;

public class GoodsDaoImpl extends MyGenericDaoImpl <Goods, Long> implements GoodsDao
{
    private static final Logger logger = Logger.getLogger (GoodsDaoImpl.class);
    // private String countRestAmountSql =
    // "select sum(gs.amount) from pc_goods g, pc_goods_storage gs where g.id = gs.goods_id";
    // private String countRestWorthSql =
    // "select sum(gs.worth) from pc_goods g, pc_goods_storage gs where g.id = gs.goods_id";
    // private String getAllGoodsStorageSql =
    // "select g.name as goodsName, g.shortCode as goodsCode, gd.name as goodsDepository, gs.price as goodsPrice, gs.amount as goodsAmount, gs.worth as goodsWorth from pc_goods g, pc_goods_storage gs, pc_goods_depository gd where g.id = gs.goods_id and gd.id = gs.depository_id order by g.shortCode";
    // private String getGoodsStorageSql =
    // "select g.name as goodsName, g.shortCode as goodsCode, gd.name as goodsDepository, gs.price as goodsPrice, gs.amount as goodsAmount, gs.worth as goodsWorth from pc_goods g, pc_goods_storage gs, pc_goods_depository gd where g.id = gs.goods_id and gd.id = gs.depository_id and (g.name=?1 or g.shortCode=?2) order by g.shortCode";
    private String countHasStorageSql = "select count(distinct(g.name)) from pc_goods g, pc_goods_storage gs where g.id = gs.goods_id and gs.amount > 0";

    public GoodsDaoImpl ()
    {
        super (Goods.class);
    }

    @Override
    @SuppressWarnings ("unchecked")
    @Transactional (propagation = Propagation.NOT_SUPPORTED)
    public List <Goods> findAll ()
    {
        String sql = "select * from pc_goods g order by convert(g.name USING gbk) COLLATE gbk_chinese_ci asc";
        Query query = getEntityManager ().createNativeQuery (sql, Goods.class);
        List <Goods> goodsList = query.getResultList ();
        for (Goods goods : goodsList)
        {
            goods.getUnit ();
            goods.getPreferedDepository ();
            goods.getStorages ();
        }
        return goodsList;
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <Goods> findByName (String name)
    {
        String sql = "select g from Goods g where g.name=?1";
        Query query = getEntityManager ().createQuery (sql, Goods.class);
        query.setParameter (1, name);
        return query.getResultList ();
    }

    @Override
    public Goods findByBarCode (String barCode)
    {
        String sql = "select g from Goods g where g.barCode=?1";
        Query query = getEntityManager ().createQuery (sql, Goods.class);
        query.setParameter (1, barCode);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (Goods) query.getResultList ().get (0) : null;
    }

    @Override
    public Goods findByShortCode (String shortCode)
    {
        if (StringUtils.isNotBlank (shortCode))
        {
            String sql = "select g from Goods g where g.shortCode=?1";
            Query query = getEntityManager ().createQuery (sql, Goods.class);
            query.setParameter (1, shortCode.toLowerCase ());
            return CollectionUtils.isNotEmpty (query.getResultList ()) ? (Goods) query.getResultList ().get (0) : null;
        }
        return null;
    }

    @Override
    public Goods findBySpecificationModel (String name, String specificationModel)
    {
        if (StringUtils.isNotBlank (name) && StringUtils.isNotBlank (specificationModel))
        {
            String sql = "select g from Goods g where g.name = ?1 and g.specificationModel = ?2";
            Query query = getEntityManager ().createQuery (sql, Goods.class);
            query.setParameter (1, name);
            query.setParameter (2, specificationModel);
            return CollectionUtils.isNotEmpty (query.getResultList ()) ? (Goods) query.getResultList ().get (0) : null;
        }
        return null;
    }

    @Override
    @SuppressWarnings ("unchecked")
    @Transactional (propagation = Propagation.NOT_SUPPORTED)
    public List <Goods> findByTypeAndDepository (long typeId, long depositoryId)
    {
        List <Goods> goodsList = new ArrayList <Goods> ();
        if (typeId != 0 && depositoryId != 0)
        {
            String sql = "SELECT g.* FROM pc_goods g, pc_goods_type gt, pc_goods_storage gs where g.type_id=gt.id and g.id=gs.goods_id and gt.id=?1 and gs.depository_id=?2 order by g.name";
            Query query = getEntityManager ().createNativeQuery (sql, Goods.class);
            query.setParameter (1, typeId);
            query.setParameter (2, depositoryId);
            goodsList = query.getResultList ();
        }
        else if (typeId != 0)
        {
            String sql = "select g from Goods g where g.type.id=?1 order by g.name";
            Query query = getEntityManager ().createQuery (sql, Goods.class);
            query.setParameter (1, typeId);
            goodsList = query.getResultList ();
        }
        else if (depositoryId != 0)
        {
            String sql = "SELECT g.* FROM pc_goods g, pc_goods_storage gs where g.id=gs.goods_id and gs.depository_id=?1 order by g.name";
            Query query = getEntityManager ().createNativeQuery (sql, Goods.class);
            query.setParameter (1, depositoryId);
            goodsList = query.getResultList ();
        }
        else
        {
            String sql = "SELECT g.* FROM pc_goods g order by g.name";
            Query query = getEntityManager ().createNativeQuery (sql, Goods.class);
            goodsList = query.getResultList ();
        }
        for (Goods goods : goodsList)
        {
            goods.getUnit ();
            goods.getPreferedDepository ();
            goods.getStorages ();
        }
        return goodsList;
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <Goods> findGoods (String nameOrCode)
    {
        if (StringUtils.isNotBlank (nameOrCode))
        {
            String sql = "select g from Goods g where g.name like ?1 or g.shortCode like ?2";
            Query query = getEntityManager ().createQuery (sql, Goods.class);
            query.setParameter (1, "%" + nameOrCode + "%");
            query.setParameter (2, "%" + nameOrCode + "%");
            return query.getResultList ();
        }
        return null;
    }

    @Override
    public int countByType (long goodsType)
    {
        String sql = "SELECT count(g) from Goods g where g.type.id=?1";
        Query query = getEntityManager ().createQuery (sql);
        query.setParameter (1, goodsType);
        int amount = 0;
        if (CollectionUtils.isNotEmpty (query.getResultList ()))
        {
            amount = Integer.parseInt (query.getResultList ().get (0).toString ());
        }
        return amount;
    }

    @Override
    public int countByUnit (long goodsUnit)
    {
        String sql = "SELECT count(g) from Goods g where g.unit.id=?1";
        Query query = getEntityManager ().createQuery (sql);
        query.setParameter (1, goodsUnit);
        int amount = 0;
        if (CollectionUtils.isNotEmpty (query.getResultList ()))
        {
            amount = Integer.parseInt (query.getResultList ().get (0).toString ());
        }
        return amount;
    }

    @Override
    @Transactional (propagation = Propagation.NOT_SUPPORTED)
    public List <Goods> findByFuzzyName (String nameOrCode)
    {
        logger.debug ("Find goods by fuzzy name: " + nameOrCode);
        String searchKey = (null == nameOrCode ? "" : nameOrCode.trim ());
        String sql = "select * from pc_goods g where g.name like ?1 or g.shortCode like ?2 order by convert(g.name USING gbk) COLLATE gbk_chinese_ci asc";
        Query query = getEntityManager ().createNativeQuery (sql, Goods.class);
        query.setParameter (1, "%" + searchKey + "%");
        query.setParameter (2, "%" + searchKey + "%");
        @SuppressWarnings ("unchecked")
        List <Goods> goodsList = query.getResultList ();
        for (Goods goods : goodsList)
        {
            goods.getUnit ();
            goods.getPreferedDepository ();
            goods.getStorages ();
        }

        return goodsList;
    }

    @Override
    public Page <Goods> findByFuzzy (long goodsTypeId, Pageable pageable, String searchKey, boolean showHasStorage)
    {
        String pageableLog = "offSet:" + pageable.getOffset () + ",pageNumber:" + pageable.getPageNumber ()
                             + ",pageSize:" + pageable.getPageSize () + ",sort:" + pageable.getSort ();
        logger.debug ("Find paging goods by fuzzy, pageable: (" + pageableLog + "), searchkey: " + searchKey);
        String keyWord = (null == searchKey ? "" : searchKey.trim ());
        if (0 != goodsTypeId)
        {
            String selectCountSql = "SELECT count(g) ";
            String fromSql = "FROM Goods g where ";
            String conditionSql = "g.type.id = ?1 ";
            if (StringUtils.isNotBlank (searchKey))
            {
                conditionSql += "and (g.name like ?2 or g.specificationModel like ?3 or g.barCode like ?4 or g.shortCode like ?5)";
            }
            if (showHasStorage)
            {
                conditionSql += " and g.id in (select distinct(gs.goods.id) from GoodsStorage gs where gs.amount <> 0)";
            }

            Query countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
            countQuery.setParameter (1, goodsTypeId);
            if (StringUtils.isNotBlank (searchKey))
            {
                countQuery.setParameter (2, "%" + keyWord + "%");
                countQuery.setParameter (3, "%" + keyWord + "%");
                countQuery.setParameter (4, "%" + keyWord + "%");
                countQuery.setParameter (5, "%" + keyWord + "%");
            }
            int total = Integer.parseInt (countQuery.getResultList ().get (0).toString ());

            String querySql = "SELECT g ";
            querySql += fromSql;
            querySql += conditionSql;
            String orderBySql = " ORDER BY ";
            querySql += orderBySql;

            String sortSqlStr = "g.name asc";
            try
            {
                sortSqlStr = GenericDaoUtils.parseSort (Goods.class, pageable, "g");
            }
            catch (Exception e)
            {
                // TODO: handle exception
            }
            querySql += sortSqlStr;

            Query query = getEntityManager ().createQuery (querySql);
            query.setParameter (1, goodsTypeId);
            if (StringUtils.isNotBlank (searchKey))
            {
                query.setParameter (2, "%" + keyWord + "%");
                query.setParameter (3, "%" + keyWord + "%");
                query.setParameter (4, "%" + keyWord + "%");
                query.setParameter (5, "%" + keyWord + "%");
            }

            query.setFirstResult (pageable.getOffset ());
            query.setMaxResults (pageable.getPageSize ());

            @SuppressWarnings ("unchecked")
            List <Goods> goodsList = query.getResultList ();
            Page <Goods> goodsPage = new PageImpl <Goods> (goodsList, pageable, total);
            return goodsPage;
        }
        else
        {
            String selectCountSql = "SELECT count(g) ";
            String fromSql = "FROM Goods g ";
            String conditionSql = "";
            if (StringUtils.isNotBlank (searchKey) && showHasStorage)
            {
                conditionSql += "where (g.name like ?1 or "
                                + "g.specificationModel like ?2 or "
                                + "g.barCode like ?3 or "
                                + "g.shortCode like ?4) and g.id in (select distinct(gs.goods.id) from GoodsStorage gs where gs.amount <> 0)";
            }
            if (StringUtils.isNotBlank (searchKey))
            {
                conditionSql += "where (g.name like ?1 or " + "g.specificationModel like ?2 or "
                                + "g.barCode like ?3 or " + "g.shortCode like ?4)";
            }
            if (showHasStorage)
            {
                conditionSql += "where g.id in (select distinct(gs.goods.id) from GoodsStorage gs where gs.amount <> 0)";
            }

            Query countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
            if (StringUtils.isNotBlank (searchKey))
            {
                countQuery.setParameter (1, "%" + keyWord + "%");
                countQuery.setParameter (2, "%" + keyWord + "%");
                countQuery.setParameter (3, "%" + keyWord + "%");
                countQuery.setParameter (4, "%" + keyWord + "%");
            }
            int total = Integer.parseInt (countQuery.getResultList ().get (0).toString ());

            String querySql = "SELECT g ";
            querySql += fromSql;
            querySql += conditionSql;
            String orderBySql = " ORDER BY ";
            querySql += orderBySql;

            String sortSqlStr = "g.name asc";
            try
            {
                sortSqlStr = GenericDaoUtils.parseSort (Goods.class, pageable, "g");
            }
            catch (Exception e)
            {
                // TODO: handle exception
            }
            querySql += sortSqlStr;

            Query query = getEntityManager ().createQuery (querySql);
            if (StringUtils.isNotBlank (searchKey))
            {
                query.setParameter (1, "%" + keyWord + "%");
                query.setParameter (2, "%" + keyWord + "%");
                query.setParameter (3, "%" + keyWord + "%");
                query.setParameter (4, "%" + keyWord + "%");
            }

            query.setFirstResult (pageable.getOffset ());
            query.setMaxResults (pageable.getPageSize ());

            @SuppressWarnings ("unchecked")
            List <Goods> goodsList = query.getResultList ();
            Page <Goods> goodsPage = new PageImpl <Goods> (goodsList, pageable, total);
            return goodsPage;
        }
    }

    @Override
    public Page <GoodsStorageCourse> findStorageCourseFromOrderIn (Pageable pageable,
                                                                   GoodsStorageCourseSearchForm searchForm)
    {
        if (null != searchForm && searchForm.getGoodsId () != 0)
        {
            String countSql = "select count(*) from pc_order_in o, pc_provider c, pc_orderIn_item oi, pc_goods g where o.id=oi.orderIn_id and o.provider_id=c.id and oi.goods_id=g.id and g.id=?1 ";
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                countSql += "and o.dateCreated between ?2 and ?3 ";
            }
            Query countQuery = getEntityManager ().createNativeQuery (countSql);
            countQuery.setParameter (1, searchForm.getGoodsId ());
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                countQuery.setParameter (2, searchForm.getStartDate ());
                countQuery.setParameter (3, searchForm.getEndDate ());
            }
            int total = 0;
            if (CollectionUtils.isNotEmpty (countQuery.getResultList ()) && null != countQuery.getResultList ().get (0))
            {
                total = Integer.parseInt (countQuery.getResultList ().get (0).toString ());
            }

            String querySql = "select g.id as goodsId, g.name as goodsName, g.shortCode as goodsCode, g.specificationModel as goodsSpecificationModel, gd.name as goodsDepository, u.userId as userCreated, oi.unitPrice as goodsPrice, o.id as orderId, o.bid as orderBid, o.dateCreated as orderCreate, c.id as customerId, c.shortName as customerName, o.typeCode as orderTypeCode, o.statusCode as orderStatusCode, oi.amount as goodsAmount "
                              + "from pc_order_in o, pc_provider c, pc_orderIn_item oi, pc_goods g, pc_goods_depository gd, pc_user u "
                              + "where o.id=oi.orderIn_id and o.provider_id=c.id and oi.goods_id=g.id and oi.depository_id=gd.id and o.userCreatedBy=u.id and g.id=?1 ";
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                querySql += "and o.dateCreated between ?2 and ?3 ";
            }
            querySql += "order by o.dateCreated desc";
            Query queryQ = getEntityManager ().createNativeQuery (querySql, GoodsStorageCourse.class);
            queryQ.setParameter (1, searchForm.getGoodsId ());
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                queryQ.setParameter (2, searchForm.getStartDate ());
                queryQ.setParameter (3, searchForm.getEndDate ());
            }

            if (null != pageable)
            {
                queryQ.setFirstResult (pageable.getOffset ());
                queryQ.setMaxResults (pageable.getPageSize ());
            }

            @SuppressWarnings ("unchecked")
            List <GoodsStorageCourse> goodsStorageCourses = queryQ.getResultList ();
            Page <GoodsStorageCourse> goodsStorageCoursePage = new PageImpl <GoodsStorageCourse> (goodsStorageCourses,
                                                                                                  pageable, total);

            return goodsStorageCoursePage;
        }
        return null;
    }

    @Override
    public Page <GoodsStorageCourse> findStorageCourseFromOrderOut (Pageable pageable,
                                                                    GoodsStorageCourseSearchForm searchForm)
    {
        if (null != searchForm && searchForm.getGoodsId () != 0)
        {
            String countSql = "select count(*) from pc_order_out o, pc_customer c, pc_orderOut_item oi, pc_goods g where o.id=oi.orderOut_id and o.customer_id=c.id and oi.goods_id=g.id and g.id=?1 ";
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                countSql += "and o.dateCreated between ?2 and ?3 ";
            }
            Query countQuery = getEntityManager ().createNativeQuery (countSql);
            countQuery.setParameter (1, searchForm.getGoodsId ());
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                countQuery.setParameter (2, searchForm.getStartDate ());
                countQuery.setParameter (3, searchForm.getEndDate ());
            }
            int total = 0;
            if (CollectionUtils.isNotEmpty (countQuery.getResultList ()) && null != countQuery.getResultList ().get (0))
            {
                total = Integer.parseInt (countQuery.getResultList ().get (0).toString ());
            }

            String querySql = "select g.id as goodsId, g.name as goodsName, g.shortCode as goodsCode, g.specificationModel as goodsSpecificationModel, gd.name as goodsDepository, u.userId as userCreated, oi.unitPrice as goodsPrice, o.id as orderId, o.bid as orderBid, o.dateCreated as orderCreate, c.id as customerId, c.shortName as customerName, o.typeCode as orderTypeCode, o.statusCode as orderStatusCode, oi.amount as goodsAmount "
                              + "from pc_order_out o, pc_customer c, pc_orderOut_item oi, pc_goods g, pc_goods_depository gd, pc_user u "
                              + "where o.id=oi.orderOut_id and o.customer_id=c.id and oi.goods_id=g.id and oi.depository_id=gd.id and o.userCreatedBy=u.id and g.id=?1 ";
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                querySql += "and o.dateCreated between ?2 and ?3 ";
            }
            querySql += "order by o.dateCreated desc";
            Query queryQ = getEntityManager ().createNativeQuery (querySql, GoodsStorageCourse.class);
            queryQ.setParameter (1, searchForm.getGoodsId ());
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                queryQ.setParameter (2, searchForm.getStartDate ());
                queryQ.setParameter (3, searchForm.getEndDate ());
            }

            if (null != pageable)
            {
                queryQ.setFirstResult (pageable.getOffset ());
                queryQ.setMaxResults (pageable.getPageSize ());
            }

            @SuppressWarnings ("unchecked")
            List <GoodsStorageCourse> goodsStorageCourses = queryQ.getResultList ();
            Page <GoodsStorageCourse> goodsStorageCoursePage = new PageImpl <GoodsStorageCourse> (goodsStorageCourses,
                                                                                                  pageable, total);

            return goodsStorageCoursePage;
        }
        return null;
    }

    @Override
    public Page <GoodsStorageCourse> findStorageCourse (Pageable pageable, GoodsStorageCourseSearchForm searchForm)
    {
        if (null != searchForm && searchForm.getGoodsId () != 0)
        {
            String countOrderOutStorageCourse = "(select g.id as goodsId, g.name as goodsName, g.shortCode as goodsCode, g.specificationModel as goodsSpecificationModel, gd.name as goodsDepository, u.userId as userCreated, ooi.unitPrice as goodsPrice, oo.id as orderId, oo.bid as orderBid, oo.dateCreated as orderCreate, c.id as customerId, c.shortName as customerName, oo.typeCode as orderTypeCode, oo.statusCode as orderStatusCode, ooi.amount as goodsAmount from pc_order_out oo, pc_orderOut_item ooi, pc_customer c, pc_goods g, pc_goods_depository gd, pc_user u where oo.id=ooi.orderOut_id and oo.customer_id=c.id and ooi.goods_id=g.id and ooi.depository_id=gd.id and oo.userCreatedBy=u.id and g.id=?1)";
            String countOrderInStorageCourse = "(select g.id as goodsId, g.name as goodsName, g.shortCode as goodsCode, g.specificationModel as goodsSpecificationModel, gd.name as goodsDepository, u.userId as userCreated, oii.unitPrice as goodsPrice, oi.id as orderId, oi.bid as orderBid, oi.dateCreated as orderCreate, p.id as customerId, p.shortName as customerName, oi.typeCode as orderTypeCode, oi.statusCode as orderStatusCode, oii.amount as goodsAmount from pc_order_in oi, pc_orderIn_item oii, pc_provider p, pc_goods g, pc_goods_depository gd, pc_user u where oi.id=oii.orderIn_id and oi.provider_id=p.id and oii.goods_id=g.id and oii.depository_id=gd.id and oi.userCreatedBy=u.id and g.id=?2)";
            String countSql = "select count(*) from (" + countOrderOutStorageCourse + " union "
                              + countOrderInStorageCourse + ") gsc ";
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                countSql += "where gsc.orderCreate between ?3 and ?4 ";
            }
            Query countQuery = getEntityManager ().createNativeQuery (countSql);
            countQuery.setParameter (1, searchForm.getGoodsId ());
            countQuery.setParameter (2, searchForm.getGoodsId ());
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                countQuery.setParameter (3, searchForm.getStartDate ());
                countQuery.setParameter (4, searchForm.getEndDate ());
            }
            int total = 0;
            if (CollectionUtils.isNotEmpty (countQuery.getResultList ()) && null != countQuery.getResultList ().get (0))
            {
                total = Integer.parseInt (countQuery.getResultList ().get (0).toString ());
            }

            String querySql = "select * from (" + countOrderOutStorageCourse + " union " + countOrderInStorageCourse
                              + ") gsc ";
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                querySql += "where gsc.orderCreate between ?3 and ?4 ";
            }
            querySql += "order by gsc.orderCreate desc";
            Query queryQ = getEntityManager ().createNativeQuery (querySql, GoodsStorageCourse.class);
            queryQ.setParameter (1, searchForm.getGoodsId ());
            queryQ.setParameter (2, searchForm.getGoodsId ());
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                queryQ.setParameter (3, searchForm.getStartDate ());
                queryQ.setParameter (4, searchForm.getEndDate ());
            }

            if (null != pageable)
            {
                queryQ.setFirstResult (pageable.getOffset ());
                queryQ.setMaxResults (pageable.getPageSize ());
            }

            @SuppressWarnings ("unchecked")
            List <GoodsStorageCourse> goodsStorageCourses = queryQ.getResultList ();
            Page <GoodsStorageCourse> goodsStorageCoursePage = new PageImpl <GoodsStorageCourse> (goodsStorageCourses,
                                                                                                  pageable, total);

            return goodsStorageCoursePage;
        }
        return null;
    }

    @Override
    public int countStorageCourseFromOrderIn (GoodsStorageCourseSearchForm searchForm)
    {
        int sumAmount = 0;
        if (null != searchForm && searchForm.getGoodsId () != 0)
        {
            String countSql = "select sum(oi.amount) from pc_order_in o, pc_provider c, pc_orderIn_item oi, pc_goods g where o.id=oi.orderIn_id and o.provider_id=c.id and oi.goods_id=g.id and g.id=?1 ";
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                countSql += "and o.dateCreated between ?2 and ?3 ";
            }
            Query countQuery = getEntityManager ().createNativeQuery (countSql);
            countQuery.setParameter (1, searchForm.getGoodsId ());
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                countQuery.setParameter (2, searchForm.getStartDate ());
                countQuery.setParameter (3, searchForm.getEndDate ());
            }
            if (CollectionUtils.isNotEmpty (countQuery.getResultList ()) && null != countQuery.getResultList ().get (0))
            {
                sumAmount = Integer.parseInt (countQuery.getResultList ().get (0).toString ());
            }
        }
        return sumAmount;
    }

    @Override
    public int countStorageCourseFromOrderOut (GoodsStorageCourseSearchForm searchForm)
    {
        int sumAmount = 0;
        if (null != searchForm && searchForm.getGoodsId () != 0)
        {
            String countSql = "select sum(oi.amount) from pc_order_out o, pc_customer c, pc_orderOut_item oi, pc_goods g where o.id=oi.orderOut_id and o.customer_id=c.id and oi.goods_id=g.id and g.id=?1 ";
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                countSql += "and o.dateCreated between ?2 and ?3 ";
            }
            Query countQuery = getEntityManager ().createNativeQuery (countSql);
            countQuery.setParameter (1, searchForm.getGoodsId ());
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                countQuery.setParameter (2, searchForm.getStartDate ());
                countQuery.setParameter (3, searchForm.getEndDate ());
            }
            if (CollectionUtils.isNotEmpty (countQuery.getResultList ()) && null != countQuery.getResultList ().get (0))
            {
                sumAmount = Integer.parseInt (countQuery.getResultList ().get (0).toString ());
            }
        }
        return sumAmount;
    }

    @Override
    public long countRestAmount (long goodsTypeId, String searchKey)
    {
        String keyWord = (null == searchKey ? "" : searchKey.trim ());
        if (0 != goodsTypeId)
        {
            String selectCountSql = "select sum(gs.amount) ";
            String fromSql = "from GoodsStorage gs where ";
            String conditionSql = "gs.goods.type.id = ?1 ";
            if (StringUtils.isNotBlank (searchKey))
            {
                conditionSql += "and (gs.goods.name like ?2 or gs.goods.specificationModel like ?3 or gs.goods.barCode like ?4 or gs.goods.shortCode like ?5)";
            }

            Query countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
            countQuery.setParameter (1, goodsTypeId);
            if (StringUtils.isNotBlank (searchKey))
            {
                countQuery.setParameter (2, "%" + keyWord + "%");
                countQuery.setParameter (3, "%" + keyWord + "%");
                countQuery.setParameter (4, "%" + keyWord + "%");
                countQuery.setParameter (5, "%" + keyWord + "%");
            }
            if (CollectionUtils.isNotEmpty (countQuery.getResultList ()))
            {
                return Long.parseLong (countQuery.getResultList ().get (0).toString ());
            }
        }
        else
        {
            String selectCountSql = "select sum(gs.amount) ";
            String fromSql = "from GoodsStorage gs ";
            String conditionSql = "";
            if (StringUtils.isNotBlank (searchKey))
            {
                conditionSql += "where (gs.goods.name like ?1 or " + "gs.goods.specificationModel like ?2 or "
                                + "gs.goods.barCode like ?3 or " + "gs.goods.shortCode like ?4)";
            }

            Query countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
            if (StringUtils.isNotBlank (searchKey))
            {
                countQuery.setParameter (1, "%" + keyWord + "%");
                countQuery.setParameter (2, "%" + keyWord + "%");
                countQuery.setParameter (3, "%" + keyWord + "%");
                countQuery.setParameter (4, "%" + keyWord + "%");
            }
            if (CollectionUtils.isNotEmpty (countQuery.getResultList ()))
            {
                return Long.parseLong (countQuery.getResultList ().get (0).toString ());
            }
        }
        return 0;
    }

    @Override
    public float countRestWorth (long goodsTypeId, String searchKey)
    {
        String keyWord = (null == searchKey ? "" : searchKey.trim ());
        if (0 != goodsTypeId)
        {
            String selectCountSql = "select sum(gs.worth) ";
            String fromSql = "from GoodsStorage gs where ";
            String conditionSql = "gs.goods.type.id = ?1 ";
            if (StringUtils.isNotBlank (searchKey))
            {
                conditionSql += "and (gs.goods.name like ?2 or gs.goods.specificationModel like ?3 or gs.goods.barCode like ?4 or gs.goods.shortCode like ?5)";
            }

            Query countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
            countQuery.setParameter (1, goodsTypeId);
            if (StringUtils.isNotBlank (searchKey))
            {
                countQuery.setParameter (2, "%" + keyWord + "%");
                countQuery.setParameter (3, "%" + keyWord + "%");
                countQuery.setParameter (4, "%" + keyWord + "%");
                countQuery.setParameter (5, "%" + keyWord + "%");
            }
            if (CollectionUtils.isNotEmpty (countQuery.getResultList ()))
            {
                return Float.parseFloat (countQuery.getResultList ().get (0).toString ());
            }
        }
        else
        {
            String selectCountSql = "select sum(gs.worth) ";
            String fromSql = "from GoodsStorage gs ";
            String conditionSql = "";
            if (StringUtils.isNotBlank (searchKey))
            {
                conditionSql += "where (gs.goods.name like ?1 or " + "gs.goods.specificationModel like ?2 or "
                                + "gs.goods.barCode like ?3 or " + "gs.goods.shortCode like ?4)";
            }

            Query countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
            if (StringUtils.isNotBlank (searchKey))
            {
                countQuery.setParameter (1, "%" + keyWord + "%");
                countQuery.setParameter (2, "%" + keyWord + "%");
                countQuery.setParameter (3, "%" + keyWord + "%");
                countQuery.setParameter (4, "%" + keyWord + "%");
            }
            if (CollectionUtils.isNotEmpty (countQuery.getResultList ()))
            {
                return Float.parseFloat (countQuery.getResultList ().get (0).toString ());
            }
        }
        return 0;
    }

    @Override
    public int countHasStorage ()
    {
        Query countQuery = getEntityManager ().createNativeQuery (countHasStorageSql);
        if (CollectionUtils.isNotEmpty (countQuery.getResultList ()))
        {
            return Integer.parseInt (countQuery.getResultList ().get (0).toString ());
        }
        return 0;
    }

    @Override
    public int countGoodsIncrement (Date start, Date end)
    {
        String sql = "select count(g) from Goods g where g.lastUpdated between ?1 and ?2";
        Query query = getEntityManager ().createQuery (sql);
        query.setParameter (1, start);
        query.setParameter (2, end);
        if (CollectionUtils.isNotEmpty (query.getResultList ()))
        {
            return Integer.parseInt (query.getResultList ().get (0).toString ());
        }
        return 0;
    }

    @Override
    @Transactional
    public void updateAveragePrice (long goodsId, float averagePrice) throws PCDalException
    {
        Query query = getEntityManager ().createQuery ("update Goods g set g.averagePrice = ?1 where g.id = ?2");
        query.setParameter (1, averagePrice);
        query.setParameter (2, goodsId);
        query.executeUpdate ();
    }

}
