package com.cxx.purchasecharge.dal;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.pinfly.purchasecharge.core.model.OrderSearchForm;
import com.pinfly.purchasecharge.core.model.OrderStatusCode;
import com.pinfly.purchasecharge.core.model.OrderTypeCode;
import com.pinfly.purchasecharge.core.model.persistence.goods.Goods;
import com.pinfly.purchasecharge.core.model.persistence.out.Customer;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOut;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOutItem;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.core.util.IDGenerator;
import com.pinfly.purchasecharge.dal.goods.GoodsDao;
import com.pinfly.purchasecharge.dal.out.CustomerDao;
import com.pinfly.purchasecharge.dal.out.OrderOutDao;
import com.pinfly.purchasecharge.dal.out.OrderOutItemDao;
import com.pinfly.purchasecharge.dal.usersecurity.UserDao;

//@SuppressWarnings ("all")
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = "classpath:META-INF/spring/purchase-charge-dao.xml")
public class OutOrderDaoTest extends GenericTest
{
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private OrderOutItemDao orderItemDao;
    @Autowired
    private OrderOutDao orderDao;
    @Autowired
    private UserDao userDao;

    // private List <OrderItem> orderItems;
    private float paidMoney = 0;
    private String comment = "good";
    private OrderStatusCode statusCode = OrderStatusCode.NEW;

    @Test
    public void testSave ()
    {
        OrderOut order = saveOrder ("customer08", "admin", new String[]
        { "dlink15", "dlink16" });

        Assert.assertEquals (2, order.getOrderItems ().size ());
        Assert.assertEquals (paidMoney, order.getPaidMoney ());
        Assert.assertEquals (comment, order.getComment ());
        Assert.assertEquals (statusCode, order.getStatusCode ());
    }

    private OrderOut saveOrder (String customerName, String signUser, String... goodsNames)
    {
        long userUniqueId = userDao.getUniqueIdByUserId (signUser);

        Customer customer = new Customer ();
        customer.setShortName (customerName);
        Customer customer2 = customerDao.save (customer);

        OrderOut order = new OrderOut ();
        order.setId (IDGenerator.getIdentityID ());
        order.setComment (comment);
        order.setPaidMoney (paidMoney);
        // order.setPayDays (payDays);
        order.setStatusCode (statusCode);
        order.setOrderItems (createOrderItems (goodsNames));
        order.setCustomer (customer2);

        preprocessOrder (order);

        return orderDao.save (order);
    }

    private OrderOut preprocessOrder (OrderOut order)
    {
        order.setDateCreated (new java.sql.Timestamp (System.currentTimeMillis ()));
        order.setLastUpdated (new java.sql.Timestamp (System.currentTimeMillis ()));
        calculateDealPayAndProfit (order);
        return order;
    }

    private void calculateDealPayAndProfit (OrderOut order)
    {
        float sum = 0;
        float profit = 0;
        if (null != order)
        {
            List <OrderOutItem> orderItems = order.getOrderItems ();
            if (!CollectionUtils.isEmpty (orderItems))
            {
                for (OrderOutItem orderItem : orderItems)
                {
                    sum += orderItem.getUnitPrice () * orderItem.getAmount ();
                    profit += (orderItem.getUnitPrice () - orderItem.getGoods ().getImportPrice ())
                              * orderItem.getAmount ();
                }
                order.setDealMoney (sum);
                order.setProfit (profit);
            }
        }
    }

    private List <OrderOutItem> createOrderItems (String... goodsNames)
    {
        List <OrderOutItem> orderItems = new ArrayList <OrderOutItem> ();
        for (String goodsName : goodsNames)
        {
            OrderOutItem orderItem = createOrderItem (goodsName, 20, 10, 15, 20, 17);
            orderItems.add (orderItem);
        }

        return orderItems;
    }

    private OrderOutItem createOrderItem (String goodsName, float unitPrice, int amount, float importPrice,
                                          float retailPrice, float tradePrice)
    {
        OrderOutItem orderItem = new OrderOutItem ();
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
        // long userUniqueId = 0;
        long userUniqueId = userDao.getUniqueIdByUserId ("boss");
        int page = 0;
        int size = 10;
        Pageable pageable = new PageRequest (page, size, new Sort ("customer.shortName"));
        Page <OrderOut> orderList = orderDao.findByFuzzy (OrderTypeCode.OUT, pageable, searchKey, userUniqueId, true);
        // Assert.assertEquals (2, orderList.getNumberOfElements ());
        for (OrderOut order : orderList.getContent ())
        {
            System.out.println (order.getId ());
        }
    }

    @Test
    public void testFindBySearchForm ()
    {
        // saveOrder ("customer01", "admin", new String[]
        // { "dlink1", "dlink2" });
        // saveOrder ("customer02", "salesman", new String[]
        // { "dlink3", "dlink4" });

        // long userUniqueId = 0;
        long userUniqueId = userDao.getUniqueIdByUserId ("boss");
        int page = 0;
        int size = 10;
        Pageable pageable = new PageRequest (page, size, new Sort ("customer.shortName"));

        OrderSearchForm searchForm = null;
        searchForm = new OrderSearchForm ();

        int year = 2014;
        int month = 2;
        int day = 24;
        Calendar c = Calendar.getInstance ();
        c.set (year, month, day, 0, 0);
        Date std = c.getTime ();
        Calendar c2 = Calendar.getInstance ();
        c2.set (year, month, day, 23, 59);
        Date endd = c2.getTime ();
        searchForm.setStartDate (std);
        searchForm.setEndDate (endd);

        searchForm.setCustomerId (0);

        Page <OrderOut> orderList = orderDao.findBySearchForm (OrderTypeCode.IN, pageable, searchForm, userUniqueId,
                                                               true);
        // Assert.assertEquals (2, orderList.getNumberOfElements ());
        for (OrderOut order : orderList.getContent ())
        {
            System.out.println (order.getId () + " " + order.getDateCreated () + " " + order.getCustomer ().getId ());
        }
    }

    @Test
    public void testFindOutOrderBySearchForm ()
    {
        // long userUniqueId = 0;
        long userUniqueId = userDao.getUniqueIdByUserId ("boss");
        int page = 0;
        int size = 10;
        Pageable pageable = new PageRequest (page, size, new Sort ("customer.shortName"));

        OrderSearchForm searchForm = null;
        searchForm = new OrderSearchForm ();

        int year = 2014;
        int month = 5;
        int day = 1;
        Calendar c = Calendar.getInstance ();
        c.set (year, month, day, 0, 0);
        Date std = c.getTime ();
        Calendar c2 = Calendar.getInstance ();
        c2.set (2014, 5, 26, 23, 59);
        Date endd = c2.getTime ();
        searchForm.setStartDate (std);
        searchForm.setEndDate (endd);

        searchForm.setCustomerId (0);

        Page <OrderOut> orderList = orderDao.findBySearchForm (pageable, searchForm, userUniqueId, true);
        System.out.println (orderList.getTotalElements ());
        for (OrderOut order : orderList.getContent ())
        {
            System.out.println (order.getId () + " " + order.getDateCreated () + " " + order.getCustomer ().getId ());
        }
    }

    @Test
    public void testFindByRangeCreateDate ()
    {
        int year = 2014;
        int month = 7;
        int day = 1;
        Calendar c = Calendar.getInstance ();
        c.set (year, month, day, 0, 0);
        Timestamp std = new Timestamp (c.getTimeInMillis ());
        Calendar c2 = Calendar.getInstance ();
        c2.set (2014, 7, 29, 23, 59);
        Timestamp endd = new Timestamp (c2.getTimeInMillis ());

        std = DateUtils.date2Timestamp (DateUtils.string2Date ("2014-08-01 00:00:00", DateUtils.DATE_TIME_PATTERN));
        endd = DateUtils.date2Timestamp (DateUtils.string2Date ("2014-08-29 23:59:59", DateUtils.DATE_TIME_PATTERN));

        Map <String, Long> goodsSalesQuantityMap = new HashMap <String, Long> ();
        Map <String, Float> goodsSalesPecentageMap = new HashMap <String, Float> ();
        Long count = 0L;
        List <OrderOut> orderOutFinishedList = orderDao.findByRangeCreateDate (OrderTypeCode.OUT,
                                                                               OrderStatusCode.COMPLETED, std, endd);
        for (OrderOut order : orderOutFinishedList)
        {
            List <OrderOutItem> oiList = orderItemDao.findByOrder (order.getId (), true);
            for (OrderOutItem oi : oiList)
            {
                count += oi.getAmount ();
                if (!goodsSalesQuantityMap.containsKey (oi.getGoods ().getName ()))
                {
                    goodsSalesQuantityMap.put (oi.getGoods ().getName (), oi.getAmount ());
                }
                else
                {
                    Long amount = goodsSalesQuantityMap.get (oi.getGoods ().getName ());
                    goodsSalesQuantityMap.put (oi.getGoods ().getName (), amount + oi.getAmount ());
                }
            }
        }

        List <OrderOut> orderOutReturnedList = orderDao.findByRangeCreateDate (OrderTypeCode.OUT_RETURN,
                                                                               OrderStatusCode.COMPLETED, std, endd);
        for (OrderOut order : orderOutReturnedList)
        {
            List <OrderOutItem> oiList = orderItemDao.findByOrder (order.getId (), true);
            for (OrderOutItem oi : oiList)
            {
                count -= oi.getAmount ();
                if (!goodsSalesQuantityMap.containsKey (oi.getGoods ().getName ()))
                {
                    // goodsSalesMap.put(oi.getGoods().getName(),
                    // oi.getAmount());
                }
                else
                {
                    Long amount = goodsSalesQuantityMap.get (oi.getGoods ().getName ());
                    goodsSalesQuantityMap.put (oi.getGoods ().getName (), amount - oi.getAmount ());
                }
            }
        }

        System.out.println ("count: " + count);
        for (Map.Entry <String, Long> entry : goodsSalesQuantityMap.entrySet ())
        {
            goodsSalesPecentageMap.put (entry.getKey (), entry.getValue () / (count * 1.00f));
        }
        System.out.println ("quantity: " + goodsSalesQuantityMap);
        System.out.println ("pecentage: " + goodsSalesPecentageMap);
        System.out.println ("finish out order: " + orderOutFinishedList.size ());
        System.out.println ("return out order: " + orderOutReturnedList.size ());
    }

    @Test
    public void testUpdateStatus ()
    {
        long id = 201;
        int result = orderDao.updateStatus (OrderStatusCode.CANCELED, "update to cancel", id, new Timestamp(System.currentTimeMillis ()));
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
        List <OrderOutItem> orderItems = (List <OrderOutItem>) orderItemDao.findAll ();
        Assert.assertTrue (orderItems.size () == 0);

        orderDao.deleteAll ();
        List <OrderOut> list = (List <OrderOut>) orderDao.findAll ();
        Assert.assertTrue (list.size () == 0);

        customerDao.deleteAll ();
        List <Customer> customers = (List <Customer>) customerDao.findAll ();
        Assert.assertTrue (customers.size () == 0);

        goodsDao.deleteAll ();
        List <Goods> goodsList = (List <Goods>) goodsDao.findAll ();
        Assert.assertTrue (goodsList.size () == 0);
    }
}
