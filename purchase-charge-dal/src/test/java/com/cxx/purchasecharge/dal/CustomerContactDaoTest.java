package com.cxx.purchasecharge.dal;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pinfly.purchasecharge.core.model.persistence.out.CustomerContact;
import com.pinfly.purchasecharge.dal.out.CustomerContactDao;
import com.pinfly.purchasecharge.dal.usersecurity.UserDao;

@SuppressWarnings ("all")
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = "classpath:META-INF/spring/purchase-charge-dao.xml")
public class CustomerContactDaoTest extends GenericTest
{
    @Autowired
    private CustomerContactDao contactDao;
    @Autowired
    private UserDao userDao;

    @Test
    public void testSave ()
    {
        String name = "contactName";
        CustomerContact cc = saveContact (name);
        Assert.assertTrue (cc.getId () != 0);
        Assert.assertEquals (name, cc.getName ());
    }

    @Test
    public void testFindByFuzzyName ()
    {
        String name = "contactName";
        saveContact (name);
        saveContact ("fdasfdt");
        saveContact ("aaaaa");
        saveContact ("abbbb");
        saveContact ("fddddd");

        List <CustomerContact> contactList = contactDao.findByFuzzyName ("");
        for (CustomerContact cc : contactList)
        {
            System.out.println (cc.getName ());
        }
    }

    @Test
    public void testFindByName ()
    {
        String name = "陈祥孝";

        CustomerContact contact = contactDao.findByName (name);
        Assert.assertTrue (contact != null);
    }

    private CustomerContact saveContact (String name)
    {
        CustomerContact contact = new CustomerContact ();
        contact.setName (name);
        return contactDao.save (contact);
    }

    @Test
    // @After
    public void testDeleteAll ()
    {
        contactDao.deleteAll ();
        List <CustomerContact> roles = (List <CustomerContact>) contactDao.findAll ();
        Assert.assertTrue (roles.size () == 0);
    }
}
