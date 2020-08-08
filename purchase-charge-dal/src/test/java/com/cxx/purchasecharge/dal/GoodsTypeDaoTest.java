package com.cxx.purchasecharge.dal;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsType;
import com.pinfly.purchasecharge.dal.goods.GoodsTypeDao;

@SuppressWarnings ("all")
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = "classpath:META-INF/spring/purchase-charge-dao.xml")
public class GoodsTypeDaoTest extends GenericTest
{
    @Autowired
    private GoodsTypeDao goodsTypeDao;

    String parentName = "parentGoods1";
    String subName1 = "subGood1";
    String subName2 = "subGood2";
    String comment = "basic goods";

    @Test
    public void testSave ()
    {
        GoodsType goodsType = saveGoodsType (parentName);
        saveGoodsTypeWithParent (subName1, goodsType);
        saveGoodsTypeWithParent (subName2, goodsType);

        GoodsType subGoodsType1 = goodsTypeDao.findByName (subName1);
        Assert.assertNotNull (subGoodsType1);
        Assert.assertEquals (subName1, subGoodsType1.getName ());
        GoodsType subGoodsType2 = goodsTypeDao.findByName (subName2);
        Assert.assertNotNull (subGoodsType2);
        Assert.assertEquals (subName2, subGoodsType2.getName ());
    }

    @Test
    public void testFindByName ()
    {
        saveGoodsType (parentName);

        GoodsType goodsType = goodsTypeDao.findByName (parentName);
        Assert.assertNotNull (goodsType);
        Assert.assertEquals (parentName, goodsType.getName ());
    }

    @Test
    public void testFindByParent ()
    {
        GoodsType goodsType1 = saveGoodsType (parentName);
        GoodsType goodsType2 = saveGoodsTypeWithParent (subName1, goodsType1);
        saveGoodsTypeWithParent (subName1 + "1", goodsType2);
        saveGoodsTypeWithParent (subName1 + "2", goodsType2);
        GoodsType goodsType3 = saveGoodsTypeWithParent (subName2, goodsType1);
        saveGoodsTypeWithParent (subName2 + "1", goodsType3);
        saveGoodsTypeWithParent (subName2 + "2", goodsType3);

        // List <GoodsType> goodsTypes = goodsTypeDao.findByParent (null);
        // Assert.assertTrue (goodsTypes.size () == 1);
        // goodsTypes = goodsTypeDao.findByParent (goodsType.getId ());
        // Assert.assertTrue (goodsTypes.size () == 2);
    }

    private GoodsType saveGoodsType (String parentName)
    {
        GoodsType goodsType = new GoodsType ();
        goodsType.setName (parentName);
        return goodsTypeDao.save (goodsType);
    }

    private GoodsType saveGoodsTypeWithParent (String subName, GoodsType parent)
    {
        GoodsType goodsType = new GoodsType ();
        goodsType.setName (subName);
        goodsType.setParent (parent);
        return goodsTypeDao.save (goodsType);
    }

    @Test
    public void testFindAll ()
    {
        System.out.println (goodsTypeDao.findAll ());
    }

    @Test
    // @After
    public void testDeleteAll ()
    {
        goodsTypeDao.deleteAll ();
        List <GoodsType> list = (List <GoodsType>) goodsTypeDao.findAll ();
        Assert.assertTrue (list.size () == 0);
    }
}
