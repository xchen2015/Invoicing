package com.cxx.purchasecharge.dal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pinfly.purchasecharge.core.model.persistence.usersecurity.Authority;
import com.pinfly.purchasecharge.dal.usersecurity.AuthorityDao;

@SuppressWarnings ("all")
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = "classpath:META-INF/spring/purchase-charge-dao.xml")
public class AuthorityDaoTest extends GenericTest
{
    @Autowired
    AuthorityDao authorityDao;

    @Test
    public void testAdd ()
    {
        Authority authority = new Authority ("a1");
        authorityDao.save (authority);
    }

    @Test
    public void testGetAll ()
    {
        System.out.println (authorityDao.findAll ());
    }

    @Test
    public void testIsSystem ()
    {
        String name = "adminAuthority";
        System.out.println (authorityDao.isSystem (name));
    }

    @Test
    // @After
    public void testDeleteAll ()
    {
        authorityDao.deleteAll ();
    }

}
