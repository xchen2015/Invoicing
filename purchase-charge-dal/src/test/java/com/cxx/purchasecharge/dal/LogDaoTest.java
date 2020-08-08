package com.cxx.purchasecharge.dal;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pinfly.purchasecharge.core.model.LogSearchForm;
import com.pinfly.purchasecharge.core.model.persistence.auditlog.Log;
import com.pinfly.purchasecharge.dal.auditlog.LogDao;

@SuppressWarnings ("all")
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = "classpath:META-INF/spring/purchase-charge-dao.xml")
public class LogDaoTest
{
    @Autowired
    private LogDao logDao;

    @Test
    public void testSave ()
    {
        Log log = new Log ();
        log.setUserCreate (1);
        log.setComment ("test");

        Log log2 = logDao.save (log);
        System.out.println (log2);
    }

    @Test
    public void testFindBySearchForm ()
    {
        int page = 0;
        int size = 10;
        Pageable pageable = new PageRequest (page, size, new Sort (Direction.DESC, "date"));

        LogSearchForm searchForm = null;

        searchForm = new LogSearchForm ();
        int year = 2014;
        int month = 3;
        int day = 3;
        Calendar c = Calendar.getInstance ();
        c.set (year, month, day, 0, 0);
        Date std = c.getTime ();
        Calendar c2 = Calendar.getInstance ();
        c2.set (year, month, day, 23, 59);
        Date endd = c2.getTime ();
        searchForm.setStartDate (std);
        searchForm.setEndDate (endd);

        searchForm.setUserCreate (1);

        Page <Log> logPage = logDao.findBySearchForm (pageable, searchForm);
        for (Log log : logPage.getContent ())
        {
            System.out.println (log.getUserCreate ());
        }
    }

    @Test
    // @After
    public void testDeleteAll ()
    {
        logDao.deleteAll ();
    }
}
