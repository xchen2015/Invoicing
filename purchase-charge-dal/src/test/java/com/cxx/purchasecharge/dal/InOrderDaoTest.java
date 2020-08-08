package com.cxx.purchasecharge.dal;

import java.util.ArrayList;
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
import org.springframework.util.CollectionUtils;

import com.pinfly.purchasecharge.core.model.OrderStatusCode;
import com.pinfly.purchasecharge.core.model.OrderTypeCode;
import com.pinfly.purchasecharge.core.model.persistence.goods.Goods;
import com.pinfly.purchasecharge.core.model.persistence.in.OrderIn;
import com.pinfly.purchasecharge.core.model.persistence.in.OrderInItem;
import com.pinfly.purchasecharge.core.model.persistence.in.Provider;
import com.pinfly.purchasecharge.core.util.IDGenerator;
import com.pinfly.purchasecharge.dal.goods.GoodsDao;
import com.pinfly.purchasecharge.dal.in.OrderInDao;
import com.pinfly.purchasecharge.dal.in.OrderInItemDao;
import com.pinfly.purchasecharge.dal.in.ProviderDao;
import com.pinfly.purchasecharge.dal.usersecurity.UserDao;

//@SuppressWarnings ("all")
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = "classpath:META-INF/spring/purchase-charge-dao.xml")
public class InOrderDaoTest extends GenericTest
{
    @Autowired
    private ProviderDao providerDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private OrderInItemDao orderItemDao;
    @Autowired
    private OrderInDao orderDao;
    @Autowired
    private UserDao userDao;

    // private List <OrderItem> orderItems;
    private float paidMoney = 0;
    private String comment = "good";
    private OrderStatusCode statusCode = OrderStatusCode.NEW;

    @Test
    public void testSave ()
    {
        OrderIn order = saveOrder ("customer08", "admin", new String[]
        { "dlink15", "dlink16" });

        Assert.assertEquals (2, order.getOrderItems ().size ());
        Assert.assertEquals (paidMoney, order.getPaidMoney ());
        Assert.assertEquals (comment, order.getComment ());
        Assert.assertEquals (statusCode, order.getStatusCode ());
    }

    private OrderIn saveOrder (String customerName, String signUser, String... goodsNames)
    {
        // long userUniqueId = 0;
        long userUniqueId = userDao.getUniqueIdByUserId (signUser);

        Provider customer = new Provider ();
        customer.setShortName (customerName);
        Provider customer2 = providerDao.save (customer);

        OrderIn order = new OrderIn ();
        order.setId (IDGenerator.getIdentityID ());
        order.setComment (comment);
        order.setPaidMoney (paidMoney);
        order.setStatusCode (statusCode);
        order.setProvider (customer2);
        order.setOrderItems (createOrderItems (goodsNames));
        // order.setPayDays (payDays);

        preprocessOrder (order);

        return orderDao.save (order);
    }

    private OrderIn preprocessOrder (OrderIn order)
    {
        order.setDateCreated (new java.sql.Timestamp (System.currentTimeMillis ()));
        order.setLastUpdated (new java.sql.Timestamp (System.currentTimeMillis ()));
        calculateDealPayAndProfit (order);
        return order;
    }

    private void calculateDealPayAndProfit (OrderIn order)
    {
        float sum = 0;
        if (null != order)
        {
            List <OrderInItem> orderItems = order.getOrderItems ();
            if (!CollectionUtils.isEmpty (orderItems))
            {
                for (OrderInItem orderItem : orderItems)
                {
                    sum += orderItem.getUnitPrice () * orderItem.getAmount ();
                }
                order.setDealMoney (sum);
            }
        }
    }

    private List <OrderInItem> createOrderItems (String... goodsNames)
    {
        List <OrderInItem> orderItems = new ArrayList <OrderInItem> ();
        for (String goodsName : goodsNames)
        {
            OrderInItem orderItem = createOrderItem (goodsName, 20, 10, 15, 20, 17);
            orderItems.add (orderItem);
        }

        return orderItems;
    }

    private OrderInItem createOrderItem (String goodsName, float unitPrice, int amount, float importPrice,
                                         float retailPrice, float tradePrice)
    {
        OrderInItem orderItem = new OrderInItem ();
        orderItem.setAmount (amount);
        orderItem.setUnitPrice (unitPrice);
        orderItem.setSum (unitPrice * amount);
        Goods goods = new Goods ();
        goods.setName (goodsName);
        goods.setImportPrice (importPrice);
        goods = goodsDao.save (goods);
        orderItem.setGoods (goods);

        return orderItem;
    }

    @Test
    public void testFindByFuzzy ()
    {
        // saveOrder ("customer01", "admin", new String[]
        // { "dlink1", "dlink2" });
        // saveOrder ("customer02", "salesman", new String[]
        // { "dlink3", "dlink4" });

        String searchKey = "";
        long userUniqueId = 0;
        // long userUniqueId = userDao.getUniqueIdByUserId ("boss");
        int page = 0;
        int size = 10;
        Pageable pageable = new PageRequest (page, size, new Sort ("customer.shortName"));
        Page <OrderIn> orderList = orderDao.findByFuzzy (OrderTypeCode.IN, pageable, searchKey, userUniqueId, true);
        // Assert.assertEquals (2, orderList.getNumberOfElements ());
        for (OrderIn order : orderList.getContent ())
        {
            System.out.println (order.getId () + " " + order.getUserCreatedBy ());
        }
    }

    @Test
    public void testUpdateStatus ()
    {
        long id = 201;
        int result = orderDao.updateStatus (OrderStatusCode.CANCELED, "update to cancel", id);
        Assert.assertTrue (result > 0);
    }

    @Test
    public void testDelete ()
    {
        long id = 13111311024800004L;
        orderDao.delete (id);
        Assert.assertNull (orderDao.findOne (id));
    }

    @Test
    // @After
    public void testDeleteAll ()
    {
        orderItemDao.deleteAll ();
        List <OrderInItem> orderItems = (List <OrderInItem>) orderItemDao.findAll ();
        Assert.assertTrue (orderItems.size () == 0);

        orderDao.deleteAll ();
        List <OrderIn> list = (List <OrderIn>) orderDao.findAll ();
        Assert.assertTrue (list.size () == 0);

        providerDao.deleteAll ();
        List <Provider> customers = (List <Provider>) providerDao.findAll ();
        Assert.assertTrue (customers.size () == 0);

        goodsDao.deleteAll ();
        List <Goods> goodsList = (List <Goods>) goodsDao.findAll ();
        Assert.assertTrue (goodsList.size () == 0);
    }
}
