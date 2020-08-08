package com.pinfly.purchasecharge.dal.goods;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pinfly.purchasecharge.core.model.GoodsStorageCourse;
import com.pinfly.purchasecharge.core.model.GoodsStorageCourseSearchForm;
import com.pinfly.purchasecharge.core.model.persistence.goods.Goods;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;
import com.pinfly.purchasecharge.dal.exception.PCDalException;

public interface GoodsDao extends MyGenericDao <Goods, Long>
{
    public List <Goods> findByName (String name);

    public Goods findByBarCode (String barCode);

    public Goods findByShortCode (String shortCode);

    public Goods findBySpecificationModel (String name, String specificationModel);

    public int countByType (long goodsType);

    public int countByUnit (long goodsUnit);

    public int countHasStorage ();

    public int countGoodsIncrement (Date start, Date end);

    public List <Goods> findByTypeAndDepository (long typeId, long depositoryId);

    List <Goods> findByFuzzyName (String nameOrCode);

    List <Goods> findGoods (String nameOrCode);

    long countRestAmount (long goodsTypeId, String searchKey);

    float countRestWorth (long goodsTypeId, String searchKey);

    Page <Goods> findByFuzzy (long goodsTypeId, Pageable pageable, String searchKey, boolean showHasStorage);

    int countStorageCourseFromOrderIn (GoodsStorageCourseSearchForm searchForm);
    
    Page <GoodsStorageCourse> findStorageCourseFromOrderIn (Pageable pageable, GoodsStorageCourseSearchForm searchForm);
    
    int countStorageCourseFromOrderOut (GoodsStorageCourseSearchForm searchForm);

    Page <GoodsStorageCourse> findStorageCourseFromOrderOut (Pageable pageable, GoodsStorageCourseSearchForm searchForm);
    
    Page <GoodsStorageCourse> findStorageCourse (Pageable pageable, GoodsStorageCourseSearchForm searchForm);
    
    void updateAveragePrice (long goodsId, float averagePrice) throws PCDalException;

}
