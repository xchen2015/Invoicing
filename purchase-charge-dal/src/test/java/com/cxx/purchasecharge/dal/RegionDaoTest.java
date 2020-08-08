package com.cxx.purchasecharge.dal;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pinfly.purchasecharge.core.model.persistence.Region;
import com.pinfly.purchasecharge.dal.RegionDao;

@SuppressWarnings ("all")
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = "classpath:META-INF/spring/purchase-charge-dao.xml")
public class RegionDaoTest
{
    @Autowired
    private RegionDao regionDao;

    @Test
    public void testSave ()
    {
        List <Region> list = saveRegionList ();
        Assert.assertTrue (list.size () == 1);

    }

    private List <Region> saveRegionList ()
    {
        List <Region> regions = new ArrayList <Region> ();
        Region region = new Region ("test");
        regions.add (region);
        return (List <Region>) regionDao.save (regions);
    }

    @Test
    // @After
    public void testDeleteAll ()
    {
        regionDao.deleteAll ();
        List <Region> list = (List <Region>) regionDao.findAll ();
        Assert.assertTrue (list.size () == 0);
    }
}
