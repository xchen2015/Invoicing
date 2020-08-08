package com.cxx.purchasecharge.dal;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pinfly.purchasecharge.core.model.persistence.usersecurity.Role;
import com.pinfly.purchasecharge.dal.usersecurity.RoleDao;

@SuppressWarnings ("all")
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = "classpath:META-INF/spring/purchase-charge-dao.xml")
public class RoleDaoTest
{
    @Autowired
    private RoleDao roleDao;

    @Test
    public void testAdd ()
    {
        Role role = roleDao.save (new Role ("role1"));
        Assert.assertTrue (role.getId () != 0);
    }

    @Test
    @After
    public void testDeleteAll ()
    {
        roleDao.deleteAll ();
        List <Role> roles = (List <Role>) roleDao.findAll ();
        Assert.assertTrue (roles.size () == 0);
    }
}
