package com.cxx.purchasecharge.dal;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsType;
import com.pinfly.purchasecharge.dal.goods.GoodsUnitDao;

@SuppressWarnings ("all")
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = "classpath:META-INF/spring/purchase-charge-dao.xml")
public class GoodsUnitDaoTest extends GenericTest
{
    @Autowired
    private GoodsUnitDao goodsUnitDao;

    @Test
    public void testFindAll ()
    {
        System.out.println (goodsUnitDao.findAll ());
    }

    @Test
    // @After
    public void testDeleteAll ()
    {
        goodsUnitDao.deleteAll ();
    }
}
