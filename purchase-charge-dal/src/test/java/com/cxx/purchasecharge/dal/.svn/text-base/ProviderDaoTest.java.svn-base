package com.cxx.purchasecharge.dal;

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

import com.pinfly.purchasecharge.core.model.persistence.in.Provider;
import com.pinfly.purchasecharge.dal.in.ProviderDao;
import com.pinfly.purchasecharge.dal.in.ProviderTypeDao;
import com.pinfly.purchasecharge.dal.usersecurity.UserDao;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = "classpath:META-INF/spring/purchase-charge-dao.xml")
public class ProviderDaoTest extends GenericTest
{
    @Autowired
    private ProviderDao providerDao;
    @Autowired
    private ProviderTypeDao providerTypeDao;
    @Autowired
    private UserDao userDao;

    String shortName = "testCustomer01";
    String address = "road1";
    String email = "email01@harris.com";
    String fax = "010120";
    String fixedPhone = "4561689";

    private boolean isDeleted = false;
    private String scale = "1-20人";
    private String goodsIntention = "network";
    private String legalPerson = "cxx";
    private String memberAccount = "cxx001";
    private Date registerDate = new Date ();
    private long registerFund = 10000000;

    private String companyName = "company01";
    private String companyIntroduction = "company good";
    private String zipCode = "4651313";
    private String webSite = "www.cxx.com";
    private String comment = "company comment";

    private Date createDate = new Date ();
    private Date lastUpdated = new Date ();

    /**
     * 湖南-永州-东安-白牙市镇
     */
    private String region = "湖南-永州-东安-白牙市镇";

    @Test
    public void testFindById ()
    {
        long id = 1;
        // System.out.println (providerDao.findById (id));
    }

    @Test
    public void testFindByShortName ()
    {
        saveCustomer (shortName, "salesman");

        Provider customer2 = providerDao.findByShortName (shortName);
        Assert.assertNotNull (customer2);
    }

    @Test
    public void testFindByFuzzy ()
    {
        // saveCustomer (shortName, "admin");
        // saveCustomer ("testfdsafasd", "admin");
        // saveCustomer ("dfafdsa", "salesman");
        // saveCustomer ("sljoyho", "salesman");

        String searchKey = "";
        long userUniqueId = 0;
        // long userUniqueId = userDao.getUniqueIdByUserId ("boss");
        int page = 0;
        int size = 10;
        Pageable pageable = new PageRequest (page, size, new Sort ("shortName"));
        Page <Provider> customerList = providerDao.findByFuzzy (pageable, searchKey, userUniqueId, true);
        // Assert.assertEquals (1, customerList.getNumberOfElements ());
        for (Provider customer : customerList.getContent ())
        {
            System.out.println (customer.getShortName ());
        }
    }

    private Provider saveCustomer (String shortName, String signUser)
    {
        long userUniqueId = userDao.getUniqueIdByUserId (signUser);

        Provider customer = new Provider ();
        customer.setShortName (shortName);

        customer.setDeleted (isDeleted);
        customer.setComment (comment);

        return customer = providerDao.save (customer);
    }

    // @After
    @Test
    public void cleanDBData ()
    {
        providerDao.deleteAll ();

        List <Provider> customers = (List <Provider>) providerDao.findAll ();
        Assert.assertTrue (customers.size () == 0);
    }

}
