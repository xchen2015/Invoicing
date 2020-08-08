package com.cxx.purchasecharge.dal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pinfly.purchasecharge.core.model.persistence.goods.Goods;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsSerialNumber;
import com.pinfly.purchasecharge.dal.goods.GoodsSerialNumberDao;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = "classpath:META-INF/spring/purchase-charge-dao.xml")
public class GoodsSerialNumberDaoTest
{
    @Autowired
    GoodsSerialNumberDao goodsSerialNumberDao;

    @Test
    public void testSave ()
    {
        GoodsSerialNumber entity = new GoodsSerialNumber ();
        entity.setSerialNumber ("ssss012");
        Goods goods = new Goods ();
        goods.setId (12);
        entity.setGoods (goods);
        goodsSerialNumberDao.save (entity);

        Assert.assertNotNull (goodsSerialNumberDao.findBySerialNumber ("ssss012"));
    }
}
