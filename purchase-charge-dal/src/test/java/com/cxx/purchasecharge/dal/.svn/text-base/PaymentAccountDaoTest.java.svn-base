package com.cxx.purchasecharge.dal;

import java.util.Calendar;
import java.util.Date;
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

import com.pinfly.purchasecharge.core.model.PaymentSearchForm;
import com.pinfly.purchasecharge.core.model.persistence.PaymentAccount;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerPayment;
import com.pinfly.purchasecharge.dal.PaymentAccountDao;
import com.pinfly.purchasecharge.dal.out.CustomerPaymentDao;

@SuppressWarnings ("all")
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = "classpath:META-INF/spring/purchase-charge-dao.xml")
public class PaymentAccountDaoTest extends GenericTest
{
    @Autowired
    private PaymentAccountDao paymentTypeDao;
    @Autowired
    private CustomerPaymentDao customerPaymentDao;

    @Test
    // @After
    public void testDeleteAll ()
    {
        paymentTypeDao.deleteAll ();
        List <PaymentAccount> roles = (List <PaymentAccount>) paymentTypeDao.findAll ();
        Assert.assertTrue (roles.size () == 0);
    }

    @Test
    public void testFindByCustomerId ()
    {
        List <CustomerPayment> list = customerPaymentDao.findByCustomerId (4901);
        System.out.println (list.size ());
    }

    @Test
    public void testFindByAdvance ()
    {
        int year = 2014;
        int month = 2;
        int day = 24;
        Calendar c = Calendar.getInstance ();
        c.set (year, month, day, 0, 0);
        Date start2 = c.getTime ();
        System.out.println (start2);
        Calendar c2 = Calendar.getInstance ();
        c2.set (year, 4, day, 23, 59);
        Date end2 = c2.getTime ();

        Date start = new Date (114, 1, 2, 0, 0);
        System.out.println (start);
        Date end = new Date (114, 5, 2, 0, 0);

        PaymentSearchForm paymentSearchForm = new PaymentSearchForm ();
        paymentSearchForm.setCustomerId (4901);
        paymentSearchForm.setStartDate (start);
        paymentSearchForm.setEndDate (end);
        List <CustomerPayment> list = customerPaymentDao.findByAdvance (paymentSearchForm);
        System.out.println (list.size ());
    }

    @Test
    public void testFindByAdvance2 ()
    {
        Date start = new Date (114, 1, 2, 0, 0);
        Date end = new Date (114, 5, 2, 0, 0);

        PaymentSearchForm searchForm = new PaymentSearchForm ();
        searchForm.setCustomerId (4901);
        searchForm.setStartDate (start);
        searchForm.setEndDate (end);

        int page = 0;
        int size = 10;
        Pageable pageable = new PageRequest (page, size, new Sort ("paidDate"));

        Page <CustomerPayment> paymentPage = customerPaymentDao.findPagePayment (searchForm, pageable);
        System.out.println (paymentPage.getContent ().size ());
        System.out.println (customerPaymentDao.countPaid (searchForm));
    }

}
