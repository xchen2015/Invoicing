package com.cxx.purchasecharge.dal;

import java.util.ArrayList;
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

import com.pinfly.purchasecharge.core.model.persistence.out.Customer;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerContact;
import com.pinfly.purchasecharge.dal.out.CustomerDao;
import com.pinfly.purchasecharge.dal.out.CustomerTypeDao;
import com.pinfly.purchasecharge.dal.usersecurity.UserDao;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = "classpath:META-INF/spring/purchase-charge-dao.xml")
public class CustomerDaoTest extends GenericTest
{
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CustomerTypeDao customerTypeDao;
    @Autowired
    private UserDao userDao;

    String shortName = "testCustomer01";
    String address = "road1";
    String email = "email01@harris.com";
    String fax = "010120";
    String fixedPhone = "4561689";

    private boolean isDeleted = false;
    private String comment = "company comment";

    private Date createDate = new Date ();
    private Date lastUpdated = new Date ();

    @Test
    public void testFindById ()
    {
        long id = 1;
        // System.out.println (customerDao.findOne (id));
    }

    @Test
    public void testFindAllCustomer ()
    {
        // long start = System.currentTimeMillis ();
        // long signUser = userDao.getUniqueIdByUserId ("boss");
        // List <Customer> customers = customerDao.findAllCustomer (null,
        // signUser, true);
        //
        // System.out.println ("spend time--" + (System.currentTimeMillis () -
        // start));
        // System.out.println (customers.size ());
        // for (Customer c : customers)
        // {
        // System.out.println (c.getShortName ());
        // System.out.println (c.getContacts ());
        // }
    }

    @Test
    public void testFindByShortName ()
    {
        // saveCustomer ("customer001", "salesman");

        Customer customer2 = customerDao.findByShortName ("aa");
        Assert.assertNotNull (customer2);
        System.out.println (customer2.getContacts ());
    }

    @Test
    public void testFindByFuzzy ()
    {
        // saveCustomer (shortName, "admin");
        // saveCustomer ("testfdsafasd", "admin");
        // saveCustomer ("dfafdsa", "salesman");
        // saveCustomer ("sljoyho", "salesman");

        String searchKey = "";
        // long signUser = 0;
        long signUser = userDao.getUniqueIdByUserId ("admin");
        int page = 0;
        int size = 10;
        Pageable pageable = new PageRequest (page, size, new Sort ("shortName"));
        long start = System.currentTimeMillis ();
        Page <Customer> customerList = customerDao.findByFuzzy (pageable, searchKey, signUser, false);
        long end = System.currentTimeMillis ();
        System.out.println ("spend time--" + (end - start));
        // Assert.assertEquals (1, customerList.getNumberOfElements ());
        for (Customer customer : customerList.getContent ())
        {
            System.out.println (customer.getShortName ());
            System.out.println (customer.getContacts ());
        }
    }

    @Test
    public void testFindCustomerByFuzzy ()
    {
        String searchKey = "c";
        // long signUser = 0;
        long signUser = userDao.getUniqueIdByUserId ("boss");
        int page = 0;
        int size = 10;
        Pageable pageable = new PageRequest (page, size, new Sort ("shortName"));
        long start = System.currentTimeMillis ();
        Page <Customer> customerList = customerDao.findCustomerByFuzzy (pageable, searchKey, signUser, false);
        long end = System.currentTimeMillis ();
        System.out.println ("spend time--" + (end - start));
        // Assert.assertEquals (1, customerList.getNumberOfElements ());
        for (Customer customer : customerList.getContent ())
        {
            System.out.println (customer.getShortName ());
        }
    }

    @Test
    public void testUpdate ()
    {
        long id = 1801;
        // Customer c = customerDao.findOne (id);
        // System.out.println (c.getContact ());
        Customer c = new Customer ();
        c.setId (id);
        c.setShortName ("provider02");
        c.setContacts (null);
        customerDao.save (c);
        System.out.println (customerDao.findOne (id).getContacts ());
    }

    private Customer saveCustomer (String shortName, String signUser)
    {
        // long userUniqueId = userDao.getUniqueIdByUserId (signUser);

        Customer customer = new Customer ();
        customer.setShortName (shortName);
        // customer.setUserCreatedBy (userUniqueId);
        CustomerContact contact = new CustomerContact ();
        contact.setName ("contact01");
        List <CustomerContact> contacts = new ArrayList <CustomerContact> ();
        contacts.add (contact);
        customer.setContacts (contacts);
        customer.setDeleted (isDeleted);
        customer.setComment (comment);

        return customer = customerDao.save (customer);
    }

    // @After
    @Test
    public void cleanDBData ()
    {
        customerDao.deleteAll ();

        List <Customer> customers = (List <Customer>) customerDao.findAll ();
        Assert.assertTrue (customers.size () == 0);
    }

}
