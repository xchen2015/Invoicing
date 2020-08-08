package com.cxx.purchasecharge.dal;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pinfly.purchasecharge.core.model.GoodsStorageCourse;
import com.pinfly.purchasecharge.core.model.GoodsStorageCourseSearchForm;
import com.pinfly.purchasecharge.core.model.persistence.goods.Goods;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsType;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsUnit;
import com.pinfly.purchasecharge.dal.goods.GoodsDao;
import com.pinfly.purchasecharge.dal.goods.GoodsTypeDao;

@SuppressWarnings ("all")
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = "classpath:META-INF/spring/purchase-charge-dao.xml")
public class GoodsDaoTest extends GenericTest
{
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private GoodsTypeDao goodsTypeDao;

    String name = "good1";
    String comment = "basic goods";
    private String imagePath = "c:/test/test.jpg";
    private String maker = "cxx";
    private String barCode = "cxx1564341";
    private float importPrice = 1.0f;
    private float tradePrice = 2.0f;
    private float retailPrice = 3.0f;
    private String unit = "个";

    @Test
    public void testSave ()
    {
        String name = "bbc01";
        Goods goods = createGoods (name, importPrice, retailPrice, tradePrice);
        goods = goodsDao.save (goods);

        Assert.assertNotNull (goods);
        Assert.assertEquals (name, goods.getName ());
        Assert.assertEquals (barCode, goods.getBarCode ());
        Assert.assertEquals (comment, goods.getComment ());
        Assert.assertEquals (importPrice, goods.getImportPrice ());
        // Assert.assertEquals (type, goods.getType ());
    }

    private Goods createGoods (String name, float importPrice, float retailPrice, float tradePrice)
    {
        Goods goods = new Goods (name);
        goods.setBarCode (barCode);
        goods.setComment (comment);
        goods.setUnit (new GoodsUnit (0));
        GoodsType type = null;
        goods.setType (type);

        return goods;
    }

    @Test
    public void testFindByName ()
    {
        Goods goods = new Goods ();
        goods.setName (name);
        goods.setComment (comment);
        goodsDao.save (goods);

        List <Goods> goodses = goodsDao.findByName (name);
        Assert.assertNotNull (goodses);
        Assert.assertTrue (goodses.size () > 0);
    }

    @Test
    public void testFindByFuzzyName ()
    {
        List <Goods> goodsList = goodsDao.findByFuzzyName ("ddgg");
        for (Goods goods : goodsList)
        {
            // System.out.println (goods.getUnit ());
            // System.out.println (goods.getPreferedDepository ());
            System.out.println (goods.getStorages ());
            Assert.assertNotNull (goods.getUnit ());
            System.out.println (goods.getPreferedDepository ());
            Assert.assertNotNull (goods.getPreferedDepository ());
            System.out.println (goods.getStorages ());
            Assert.assertNotNull (goods.getStorages ());
        }
    }

    @Test
    public void testFindByFuzzy ()
    {
        long goodsTypeId = 702;

        String searchKey = "g";
        int page = 0;
        int size = 10;
        Pageable pageable = new PageRequest (page, size, new Sort ("name"));
        Page <Goods> goodsList = goodsDao.findByFuzzy (goodsTypeId, pageable, searchKey, false);
        Assert.assertNotNull (goodsList);
        Assert.assertNotNull (goodsList.getContent ());
        System.out.println (goodsList.getContent ().size ());
        for (Goods goods : goodsList.getContent ())
        {
            System.out.println (goods.getType ().getId () + " " + goods.getName ());
        }
    }

    @Test
    public void testFindByBarCode ()
    {
        Goods goods = new Goods ();
        goods.setName (name);
        goods.setBarCode (barCode);
        goodsDao.save (goods);

        goods = goodsDao.findByBarCode (barCode);
        Assert.assertNotNull (goods);
        Assert.assertEquals (name, goods.getName ());
        Assert.assertEquals (barCode, goods.getBarCode ());
    }

    @Test
    public void testFindByType ()
    {
        Goods goods = new Goods ();
        goods.setName (name);
        goods.setBarCode (barCode);

        GoodsType goodsType = new GoodsType ();
        goodsType.setName ("goods type 1");
        goodsType = goodsTypeDao.save (goodsType);
        goods.setType (goodsType);
        goodsDao.save (goods);

        List <Goods> goodses = goodsDao.findByTypeAndDepository (goodsType.getId (), 0);
        Assert.assertNotNull (goodses);
        Assert.assertTrue (goodses.size () > 0);
        Assert.assertEquals (name, goodses.get (0).getName ());
        Assert.assertEquals (barCode, goodses.get (0).getBarCode ());
    }

    @Test
    public void testFindGoodsStorageCourse ()
    {
        int page = 0;
        int size = 10;
        Pageable pageable = new PageRequest (page, size);

        GoodsStorageCourseSearchForm searchForm = new GoodsStorageCourseSearchForm ();
        searchForm.setGoodsId (9052);

        Page <GoodsStorageCourse> goodsPage = goodsDao.findStorageCourseFromOrderIn (pageable, searchForm);
        System.out.println (goodsPage.getContent ().size ());
    }

    @Test
    // @After
    public void testDeleteAll ()
    {
        goodsTypeDao.deleteAll ();
        goodsDao.deleteAll ();
    }
}
