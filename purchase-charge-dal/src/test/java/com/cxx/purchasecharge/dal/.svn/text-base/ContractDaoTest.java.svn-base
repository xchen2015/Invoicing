package com.cxx.purchasecharge.dal;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pinfly.purchasecharge.core.model.persistence.Contract;
import com.pinfly.purchasecharge.dal.ContractDao;

@SuppressWarnings ("all")
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = "classpath:META-INF/spring/purchase-charge-dao.xml")
public class ContractDaoTest
{
    @Autowired
    private ContractDao contractDao;

    @Test
    public void test ()
    {

    }

    @Test
    @After
    public void testDeleteAll ()
    {
        contractDao.deleteAll ();
        List <Contract> roles = (List <Contract>) contractDao.findAll ();
        Assert.assertTrue (roles.size () == 0);
    }
}
