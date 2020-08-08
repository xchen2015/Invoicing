package com.cxx.purchasecharge.app.aop;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = "classpath:spring-aop-test.xml")
public class AopTest
{
    @Autowired
    AopDao dao;

    @Test
    public void testAdd ()
    {
        User user = new User ();
        user.setName ("testName");
        user.setAge (26);
        List <Car> cars = new ArrayList <Car> ();
        cars.add (new Car ("1", "bm", 2, "white"));
        user.setCars (cars);
        dao.addUser (user);
    }

    @Test
    public void testUpdate ()
    {
        User user = new User ();
        user.setName ("testName");
        user.setAge (26);
        dao.updateUser (user);
    }

    @Test
    public void testDelete ()
    {
        User user = new User ();
        // user.setId (1);
        user.setName ("testName");
        user.setAge (26);
        dao.deleteUser (user);
    }
}
