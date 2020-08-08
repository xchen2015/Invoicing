package com.cxx.purchasecharge.dal;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pinfly.purchasecharge.dal.auditlog.LogEventDao;

@SuppressWarnings ("all")
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = "classpath:META-INF/spring/purchase-charge-dao.xml")
public class LogEventTypeDaoTest
{
    @Autowired
    private LogEventDao logEventTypeDao;

    @Test
    public void test ()
    {

    }

    @Test
    @After
    public void testDeleteAll ()
    {
        logEventTypeDao.deleteAll ();
    }
}
