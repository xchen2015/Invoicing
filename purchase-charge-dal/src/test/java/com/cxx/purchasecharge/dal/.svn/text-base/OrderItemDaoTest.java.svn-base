package com.cxx.purchasecharge.dal;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pinfly.purchasecharge.core.model.persistence.out.OrderOut;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOutItem;
import com.pinfly.purchasecharge.dal.out.OrderOutItemDao;

@SuppressWarnings ("all")
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = "classpath:META-INF/spring/purchase-charge-dao.xml")
public class OrderItemDaoTest extends GenericTest
{
    @Autowired
    private OrderOutItemDao orderItemDao;

    @Test
    public void testGetOrderItemByOrderId ()
    {
        long orderId = 140613174051L;
        OrderOut order = new OrderOut ();
        order.setId (orderId);
        List <OrderOutItem> orderItems = orderItemDao.findByOrder (orderId, true);
        for (OrderOutItem oi : orderItems)
        {
            System.out.println (oi);
        }
    }

    @Test
    // @After
    public void testDeleteAll ()
    {
        orderItemDao.deleteAll ();
    }
}
