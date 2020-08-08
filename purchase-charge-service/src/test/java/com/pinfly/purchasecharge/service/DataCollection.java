package com.pinfly.purchasecharge.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cxx.purchasecharge.core.model.AccountingModeCode;
import com.cxx.purchasecharge.core.model.CustomerTypeCode;
import com.cxx.purchasecharge.core.model.OrderStatusCode;
import com.cxx.purchasecharge.core.model.OrderTypeCode;
import com.cxx.purchasecharge.core.model.persistence.Order;
import com.cxx.purchasecharge.core.util.DateUtils;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations =
{ "classpath:META-INF/spring/purchase-charge-service.xml", "classpath:META-INF/spring/purchase-charge-dao-test.xml" })
public class DataCollection
{
    @Autowired
    private PersistenceService persistenceService;
    @Autowired
    private QueryService queryService;

    static
    {
        System.setProperty ("cxx.props.dir", "C:/ChenXiangxiao/Cxx/Common/properties");
        System.setProperty ("pinfly.props.dir", "C:/ChenXiangxiao/Cxx/Common/properties");
        System.setProperty ("pinfly.common.dir", "C:/ChenXiangxiao/Cxx/Common");
//        System.setProperty ("cxx.props.dir", "C:/cxx/Carefx/Common/properties");
//        System.setProperty ("pinfly.props.dir", "C:/cxx/Carefx/Common/properties");
//        System.setProperty ("pinfly.common.dir", "C:/cxx/Carefx/Common");
    }

    @Test
    public void testCollectUser ()
    {
        List <com.cxx.purchasecharge.core.model.persistence.User> oldUsers = (List <com.cxx.purchasecharge.core.model.persistence.User>) com.cxx.purchasecharge.dal.DaoContext.getUserDao ()
                                                                                                                                                                              .findAll ();
        List <com.pinfly.purchasecharge.core.model.persistence.usersecurity.User> newUsers = new ArrayList <com.pinfly.purchasecharge.core.model.persistence.usersecurity.User> ();
        for (com.cxx.purchasecharge.core.model.persistence.User oldUser : oldUsers)
        {
            com.pinfly.purchasecharge.core.model.persistence.usersecurity.User newUser = new com.pinfly.purchasecharge.core.model.persistence.usersecurity.User ();
            newUser.setUserId (oldUser.getUserId ());
            newUser.setAdmin (oldUser.isSystem ());
            newUser.setEnabled (oldUser.isEnabled ());
            newUser.setFirstName (oldUser.getFirstName ());
            newUser.setLastName (oldUser.getLastName ());
            newUser.setBirthday (oldUser.getBirthday ());
            newUser.setEmail (oldUser.getEmail ());
            newUser.setMobilePhone (oldUser.getMobilePhone ());
            newUser.setPwd (oldUser.getPwd ());
            newUsers.add (newUser);
        }
        persistenceService.addUsers (newUsers);
    }

    @Test
    public void testCollectRole ()
    {
        List <com.cxx.purchasecharge.core.model.persistence.Role> oldRoles = (List <com.cxx.purchasecharge.core.model.persistence.Role>) com.cxx.purchasecharge.dal.DaoContext.getRoleDao ()
                                                                                                                                                                              .findAll ();
        List <com.pinfly.purchasecharge.core.model.persistence.usersecurity.Role> newRoles = new ArrayList <com.pinfly.purchasecharge.core.model.persistence.usersecurity.Role> ();
        for (com.cxx.purchasecharge.core.model.persistence.Role oldRole : oldRoles)
        {
            com.pinfly.purchasecharge.core.model.persistence.usersecurity.Role newRole = new com.pinfly.purchasecharge.core.model.persistence.usersecurity.Role ();
            newRole.setName (oldRole.getName ());
            newRole.setEnabled (oldRole.isEnabled ());
            newRoles.add (newRole);
            // System.out.println (oldRole.getName ());
        }
        persistenceService.addRoles (newRoles);
    }

    @Test
    public void testCollectCustomer ()
    {
        List <com.cxx.purchasecharge.core.model.persistence.Customer> oldCustomers = (List <com.cxx.purchasecharge.core.model.persistence.Customer>) com.cxx.purchasecharge.dal.DaoContext.getCustomerDao ()
                                                                                                                                                                                          .findAll ();
        for (com.cxx.purchasecharge.core.model.persistence.Customer oldCustomer : oldCustomers)
        {
            if (CustomerTypeCode.CUSTOMER.equals (oldCustomer.getTypeCode ()))
            {
                // System.out.println (oldCustomer.getShortName ());
                com.pinfly.purchasecharge.core.model.persistence.out.Customer newCustomer = new com.pinfly.purchasecharge.core.model.persistence.out.Customer ();
                newCustomer.setShortName (oldCustomer.getShortName ());
                newCustomer.setComment (oldCustomer.getComment ());
                newCustomer.setDeleted (oldCustomer.isDeleted ());
                //newCustomer.setUnpayMoney (oldCustomer.getDealMoney ());
                newCustomer.setSharable (oldCustomer.isSharable ());
                if (0 != oldCustomer.getUserSigned ())
                {
                    com.cxx.purchasecharge.core.model.persistence.User oldUser = com.cxx.purchasecharge.dal.DaoContext.getUserDao ()
                                                                                                                      .findOne (oldCustomer.getUserSigned ());
                    if (null != oldUser)
                    {
                        long newUserUid = queryService.getUniqueIdByUserId (oldUser.getUserId ());
                        if (0 != newUserUid)
                        {
                            newCustomer.setUserSigned (newUserUid);
                        }
                    }
                }
                if (CollectionUtils.isNotEmpty (com.cxx.purchasecharge.dal.DaoContext.getContactDao ()
                                                                                     .findByCustomer (oldCustomer.getId ())))
                {
                    List <com.pinfly.purchasecharge.core.model.persistence.out.CustomerContact> customerContacts = new ArrayList <com.pinfly.purchasecharge.core.model.persistence.out.CustomerContact> ();
                    for (com.cxx.purchasecharge.core.model.persistence.Contact oldContact : com.cxx.purchasecharge.dal.DaoContext.getContactDao ()
                                                                                                                                 .findByCustomer (oldCustomer.getId ()))
                    {
                        com.pinfly.purchasecharge.core.model.persistence.out.CustomerContact customerContact = new com.pinfly.purchasecharge.core.model.persistence.out.CustomerContact ();
                        customerContact.setName (oldContact.getName ());
                        customerContact.setMobilePhone (oldContact.getMobilePhone ());
                        customerContact.setAddress (oldContact.getAddress ());
                        customerContact.setEmail (oldContact.getEmail ());
                        customerContact.setNetCommunityId (oldContact.getWangWang ());
                        customerContact.setPrefered (true);
                        customerContacts.add (customerContact);
                    }
                    newCustomer.setContacts (customerContacts);
                }
                persistenceService.addCustomer (newCustomer);
            }
        }
    }

    @Test
    public void testCollectProvider ()
    {
        List <com.cxx.purchasecharge.core.model.persistence.Customer> oldCustomers = (List <com.cxx.purchasecharge.core.model.persistence.Customer>) com.cxx.purchasecharge.dal.DaoContext.getCustomerDao ()
                                                                                                                                                                                          .findAll ();
        for (com.cxx.purchasecharge.core.model.persistence.Customer oldCustomer : oldCustomers)
        {
            if (CustomerTypeCode.PROVIDER.equals (oldCustomer.getTypeCode ()))
            {
                // System.out.println (oldCustomer.getShortName ());
                com.pinfly.purchasecharge.core.model.persistence.in.Provider newProvider = new com.pinfly.purchasecharge.core.model.persistence.in.Provider ();
                newProvider.setShortName (oldCustomer.getShortName ());
                newProvider.setComment (oldCustomer.getComment ());
                newProvider.setDeleted (oldCustomer.isDeleted ());
                //newProvider.setUnpayMoney (oldCustomer.getDealMoney ());
                newProvider.setSharable (oldCustomer.isSharable ());
                if (0 != oldCustomer.getUserSigned ())
                {
                    com.cxx.purchasecharge.core.model.persistence.User oldUser = com.cxx.purchasecharge.dal.DaoContext.getUserDao ()
                                                                                                                      .findOne (oldCustomer.getUserSigned ());
                    if (null != oldUser)
                    {
                        long newUserUid = queryService.getUniqueIdByUserId (oldUser.getUserId ());
                        if (0 != newUserUid)
                        {
                            newProvider.setUserSigned (newUserUid);
                        }
                    }
                }
                if (CollectionUtils.isNotEmpty (com.cxx.purchasecharge.dal.DaoContext.getContactDao ()
                                                                                     .findByCustomer (oldCustomer.getId ())))
                {
                    List <com.pinfly.purchasecharge.core.model.persistence.in.ProviderContact> providerContacts = new ArrayList <com.pinfly.purchasecharge.core.model.persistence.in.ProviderContact> ();
                    for (com.cxx.purchasecharge.core.model.persistence.Contact oldContact : com.cxx.purchasecharge.dal.DaoContext.getContactDao ()
                                                                                                                                 .findByCustomer (oldCustomer.getId ()))
                    {
                        com.pinfly.purchasecharge.core.model.persistence.in.ProviderContact providerContact = new com.pinfly.purchasecharge.core.model.persistence.in.ProviderContact ();
                        providerContact.setName (oldContact.getName ());
                        providerContact.setMobilePhone (oldContact.getMobilePhone ());
                        providerContact.setAddress (oldContact.getAddress ());
                        providerContact.setEmail (oldContact.getEmail ());
                        providerContact.setNetCommunityId (oldContact.getWangWang ());
                        providerContact.setPrefered (true);
                        providerContacts.add (providerContact);
                    }
                    newProvider.setContacts (providerContacts);
                }
                persistenceService.addProvider (newProvider);
            }
        }
    }

    @Test
    public void testCollectGoodsUnit ()
    {
        List <com.cxx.purchasecharge.core.model.persistence.GoodsUnit> oldGoodsUnits = (List <com.cxx.purchasecharge.core.model.persistence.GoodsUnit>) com.cxx.purchasecharge.dal.DaoContext.getGoodsUnitDao ()
                                                                                                                                                                                             .findAll ();
        for (com.cxx.purchasecharge.core.model.persistence.GoodsUnit oldGoodsUnit : oldGoodsUnits)
        {
            // System.out.println (oldGoodsUnit.getName ());
            com.pinfly.purchasecharge.core.model.persistence.goods.GoodsUnit newGoodsUnit = new com.pinfly.purchasecharge.core.model.persistence.goods.GoodsUnit ();
            newGoodsUnit.setName (oldGoodsUnit.getName ());
            persistenceService.addGoodsUnit (newGoodsUnit);
        }
    }

    @Test
    public void testCollectGoodsType ()
    {
        List <com.cxx.purchasecharge.core.model.persistence.GoodsType> oldGoodsTypes = (List <com.cxx.purchasecharge.core.model.persistence.GoodsType>) com.cxx.purchasecharge.dal.DaoContext.getGoodsTypeDao ()
                                                                                                                                                                                             .findAll ();
        for (com.cxx.purchasecharge.core.model.persistence.GoodsType oldGoodsType : oldGoodsTypes)
        {
            // System.out.println (oldGoodsType.getName ());
            if (null == oldGoodsType.getParent ())
            {
                com.pinfly.purchasecharge.core.model.persistence.goods.GoodsType newGoodsType = new com.pinfly.purchasecharge.core.model.persistence.goods.GoodsType ();
                newGoodsType.setName (oldGoodsType.getName ());
                persistenceService.addGoodsType (newGoodsType);
            }
            else
            {
                com.pinfly.purchasecharge.core.model.persistence.goods.GoodsType newGoodsType = new com.pinfly.purchasecharge.core.model.persistence.goods.GoodsType ();
                newGoodsType.setName (oldGoodsType.getName ());
                com.cxx.purchasecharge.core.model.persistence.GoodsType oldParent = oldGoodsType.getParent ();
                com.pinfly.purchasecharge.core.model.persistence.goods.GoodsType newParent = queryService.getGoodsType (oldParent.getName ());
                if (null != newParent)
                {
                    newGoodsType.setParent (newParent);
                    persistenceService.addGoodsType (newGoodsType);
                }
            }
        }
    }

    static String defaultDepository = "默认仓库";
    static String defaultProvider = "期初库存";

    @Test
    public void testAddDefaultDepository ()
    {
        com.pinfly.purchasecharge.core.model.persistence.goods.GoodsDepository depository = new com.pinfly.purchasecharge.core.model.persistence.goods.GoodsDepository ();
        depository.setName (defaultDepository);
        depository.setEnabled (true);
        if(null == queryService.getGoodsDepository (defaultDepository)) 
        {
            persistenceService.addGoodsDepository (depository);
        }
    }

    @Test
    public void testAddDefaultProvider ()
    {
        com.pinfly.purchasecharge.core.model.persistence.in.Provider provider = new com.pinfly.purchasecharge.core.model.persistence.in.Provider ();
        provider.setShortName (defaultProvider);
        provider.setShortCode (defaultProvider);
        if (null == queryService.getProvider (defaultProvider))
        {
            persistenceService.addProvider (provider);
        }
    }

    @Test
    public void testCollectGoods ()
    {
        List <com.cxx.purchasecharge.core.model.persistence.Goods> oldGoodss = (List <com.cxx.purchasecharge.core.model.persistence.Goods>) com.cxx.purchasecharge.dal.DaoContext.getGoodsDao ()
                                                                                                                                                                                 .findAll ();
        // System.out.println (oldGoodss.size ());
        for (com.cxx.purchasecharge.core.model.persistence.Goods oldGoods : oldGoodss)
        {
            // System.out.println (oldGoods.getName ());
            com.pinfly.purchasecharge.core.model.persistence.goods.Goods newGoods = new com.pinfly.purchasecharge.core.model.persistence.goods.Goods ();
            newGoods.setName (oldGoods.getName ());
            newGoods.setBarCode (oldGoods.getBarCode ());
            if (StringUtils.isNotBlank (oldGoods.getSpecification ()))
            {
                newGoods.setSpecificationModel (oldGoods.getSpecification ());
            }
            newGoods.setComment (oldGoods.getComment ());
            newGoods.setTradePrice (oldGoods.getTradePrice ());
            newGoods.setRetailPrice (oldGoods.getRetailPrice ());
            newGoods.setImportPrice (oldGoods.getImportPrice ());
            newGoods.setLastUpdated (oldGoods.getStorage ().getLastUpdated ());
            newGoods.setMaxStock (oldGoods.getStorage ().getMaxStock ());
            newGoods.setMinStock (oldGoods.getStorage ().getMinStock ());
            newGoods.setUserUpdatedBy (oldGoods.getStorage ().getUserUpdatedBy ());
            if (null != oldGoods.getType ())
            {
                com.pinfly.purchasecharge.core.model.persistence.goods.GoodsType newGoodsType = queryService.getGoodsType (oldGoods.getType ()
                                                                                                                                   .getName ());
                if (null != newGoodsType)
                {
                    newGoods.setType (newGoodsType);
                }
            }
            if (null != oldGoods.getUnit ())
            {
                com.pinfly.purchasecharge.core.model.persistence.goods.GoodsUnit newGoodsUnit = queryService.getGoodsUnit (oldGoods.getUnit ()
                                                                                                                                   .getName ());
                if (null != newGoodsUnit)
                {
                    newGoods.setUnit (newGoodsUnit);
                }
            }

            List <com.pinfly.purchasecharge.core.model.persistence.goods.GoodsStorage> storages = new ArrayList <com.pinfly.purchasecharge.core.model.persistence.goods.GoodsStorage> ();
            if (oldGoods.getStorage ().getInitialStock () > 0)
            {
                com.pinfly.purchasecharge.core.model.persistence.goods.GoodsStorage storage = new com.pinfly.purchasecharge.core.model.persistence.goods.GoodsStorage ();
                storage.setDepository (queryService.getGoodsDepository (defaultDepository));
                storage.setAmount (oldGoods.getStorage ().getInitialStock ());
                storage.setPrice (oldGoods.getImportPrice ());
                storage.setWorth (oldGoods.getImportPrice () * oldGoods.getStorage ().getInitialStock ());
                storages.add (storage);
                newGoods.setStorages (storages);
            }
            persistenceService.addGoods (newGoods);
        }
    }

    static Comparator <com.cxx.purchasecharge.core.model.persistence.Order> comparator = new Comparator <com.cxx.purchasecharge.core.model.persistence.Order> ()
    {
        @Override
        public int compare (Order o1, Order o2)
        {
            return o1.getCreateDate ().compareTo (o2.getCreateDate ());
        }
    };

    List <com.cxx.purchasecharge.core.model.persistence.Order> warnOrders = new ArrayList <com.cxx.purchasecharge.core.model.persistence.Order> ();
    List <com.cxx.purchasecharge.core.model.persistence.Order> warnOrders2 = new ArrayList <com.cxx.purchasecharge.core.model.persistence.Order> ();

    @Test
    public void testCollectInOrder ()
    {
        List <com.cxx.purchasecharge.core.model.persistence.Order> oldOrders = (List <com.cxx.purchasecharge.core.model.persistence.Order>) com.cxx.purchasecharge.dal.DaoContext.getOrderDao ()
                                                                                                                                                                                 .findAll ();
        List <com.cxx.purchasecharge.core.model.persistence.Order> oldOrders2 = new ArrayList <com.cxx.purchasecharge.core.model.persistence.Order> ();
        for (com.cxx.purchasecharge.core.model.persistence.Order o : oldOrders)
        {
            oldOrders2.add (o);
        }
        Collections.sort (oldOrders2, comparator);
        for (com.cxx.purchasecharge.core.model.persistence.Order oldOrder : oldOrders2)
        {
            if (OrderTypeCode.IN.equals (oldOrder.getTypeCode ()) || 140525204534l == oldOrder.getId ())
            {
                // System.out.println (oldOrder.getId ());
                addOrderIn (oldOrder, OrderTypeCode.IN);
            }
            if (OrderTypeCode.IN_RETURN.equals (oldOrder.getTypeCode ()))
            {
                addOrderIn (oldOrder, OrderTypeCode.IN_RETURN);
//                if (140525204534l != oldOrder.getId () && 141226162146l != oldOrder.getId ()
//                    && 150210113652l != oldOrder.getId ())
//                {
//                }
            }
        }
        /*
         * for(com.cxx.purchasecharge.core.model.persistence.Order oldOrder :
         * oldOrders2) { if(OrderTypeCode.IN_RETURN.equals (oldOrder.getTypeCode
         * ())) { if(140525204534l != oldOrder.getId () && 141226162146l !=
         * oldOrder.getId ()) { addOrderIn (oldOrder, OrderTypeCode.IN_RETURN);
         * } } }
         */
        System.out.println ("报错入库订单数量------" + count2);
        for (com.cxx.purchasecharge.core.model.persistence.Order oldOrder : warnOrders)
        {
            if (OrderTypeCode.IN.equals (oldOrder.getTypeCode ()) || 140525204534l == oldOrder.getId ())
            {
                // System.out.println (oldOrder.getId ());
                addOrderIn (oldOrder, OrderTypeCode.IN);
            }
            if (OrderTypeCode.IN_RETURN.equals (oldOrder.getTypeCode ()))
            {
                addOrderIn (oldOrder, OrderTypeCode.IN_RETURN);
//                if (140525204534l != oldOrder.getId () && 141226162146l != oldOrder.getId ())
//                {
//                }
            }
        }
    }

    private void addOrderIn (com.cxx.purchasecharge.core.model.persistence.Order oldOrder, OrderTypeCode typeCode)
    {
        com.pinfly.purchasecharge.core.model.persistence.in.OrderIn newOrder = new com.pinfly.purchasecharge.core.model.persistence.in.OrderIn ();
        newOrder.setBid (oldOrder.getId () + "");
        newOrder.setComment (oldOrder.getComment ());
        newOrder.setDateCreated (new Timestamp (oldOrder.getCreateDate ().getTime ()));
        newOrder.setDiscount (oldOrder.getDiscount ());
        newOrder.setLastUpdated (new Timestamp (oldOrder.getLastUpdated ().getTime ()));
        newOrder.setPaidMoney (oldOrder.getPaidMoney ());
        if (null != oldOrder.getCustomer ())
        {
            com.pinfly.purchasecharge.core.model.persistence.in.Provider provider = queryService.getProvider (oldOrder.getCustomer ()
                                                                                                                      .getShortName ());
            if (null != provider)
            {
                newOrder.setProvider (provider);
            }
        }

        if (CollectionUtils.isNotEmpty (com.cxx.purchasecharge.dal.DaoContext.getOrderItemDao ()
                                                                             .findByOrder (oldOrder.getId ())))
        {
            List <com.pinfly.purchasecharge.core.model.persistence.in.OrderInItem> orderItems = new ArrayList <com.pinfly.purchasecharge.core.model.persistence.in.OrderInItem> ();
            for (com.cxx.purchasecharge.core.model.persistence.OrderItem oldOrderItem : com.cxx.purchasecharge.dal.DaoContext.getOrderItemDao ()
                                                                                                                             .findByOrder (oldOrder.getId ()))
            {
                com.pinfly.purchasecharge.core.model.persistence.in.OrderInItem orderItem = new com.pinfly.purchasecharge.core.model.persistence.in.OrderInItem ();
                List <com.pinfly.purchasecharge.core.model.persistence.goods.Goods> goodses = queryService.getGoods (oldOrderItem.getGoods ()
                                                                                                                                 .getName ());
                if (null != goodses && goodses.size () > 0)
                {
                    orderItem.setGoods (goodses.get (0));
                    orderItem.setDepository (queryService.getGoodsDepository (defaultDepository));
                    orderItem.setAmount (oldOrderItem.getAmount ());
                    orderItem.setComment (oldOrderItem.getComment ());
                    orderItem.setUnitPrice (oldOrderItem.getUnitPrice ());
                    orderItem.setSum (oldOrderItem.getSum ());
                    orderItems.add (orderItem);
                }
            }
            newOrder.setOrderItems (orderItems);
        }

        if (OrderTypeCode.IN.equals (typeCode))
        {
            newOrder.setTypeCode (com.pinfly.purchasecharge.core.model.OrderTypeCode.IN);
        }
        if (OrderTypeCode.IN_RETURN.equals (typeCode))
        {
            newOrder.setTypeCode (com.pinfly.purchasecharge.core.model.OrderTypeCode.IN_RETURN);
        }
        if (0 != oldOrder.getUserOperatedBy ())
        {
            com.cxx.purchasecharge.core.model.persistence.User oldUser = com.cxx.purchasecharge.dal.DaoContext.getUserDao ()
                                                                                                              .findOne (oldOrder.getUserOperatedBy ());
            if (null != oldUser)
            {
                long newUserUid = queryService.getUniqueIdByUserId (oldUser.getUserId ());
                if (0 != newUserUid)
                {
                    newOrder.setUserCreatedBy (newUserUid);
                    newOrder.setUserUpdatedBy (newUserUid);
                }
            }
        }

        try
        {
            persistenceService.addOrderIn (newOrder, null);
        }
        catch (Exception e)
        {
            warnOrders.add (oldOrder);
            count2++;
            System.out.println ("报错入库订单号-----" + newOrder.getBid () + "  " + e.getMessage ());
        }
    }

    static int count = 0;
    static int count2 = 0;

    @Test
    public void testCollectOutOrder ()
    {
        List <com.cxx.purchasecharge.core.model.persistence.Order> oldOrders = (List <com.cxx.purchasecharge.core.model.persistence.Order>) com.cxx.purchasecharge.dal.DaoContext.getOrderDao ()
                                                                                                                                                                                 .findAll ();
        List <com.cxx.purchasecharge.core.model.persistence.Order> oldOrders2 = new ArrayList <com.cxx.purchasecharge.core.model.persistence.Order> ();
        for (com.cxx.purchasecharge.core.model.persistence.Order o : oldOrders)
        {
            oldOrders2.add (o);
        }
        Collections.sort (oldOrders2, comparator);
        for (com.cxx.purchasecharge.core.model.persistence.Order oldOrder : oldOrders2)
        {
            if (OrderTypeCode.OUT_RETURN.equals (oldOrder.getTypeCode ()))
            {
                // System.out.println (oldOrder.getId ());
                addOrderOut (oldOrder);
            }
            if (OrderTypeCode.OUT.equals (oldOrder.getTypeCode ())
                && !OrderStatusCode.CANCEL.equals (oldOrder.getStatusCode ()))
            {
                // System.out.println (oldOrder.getId ());
                addOrderOut (oldOrder);
            }
        }
        /*
         * for(com.cxx.purchasecharge.core.model.persistence.Order oldOrder :
         * oldOrders2) { if(OrderTypeCode.OUT.equals (oldOrder.getTypeCode ())
         * && !OrderStatusCode.CANCEL.equals (oldOrder.getStatusCode ())) {
         * //System.out.println (oldOrder.getId ()); addOrderOut (oldOrder); } }
         */
        System.out.println ("报错出库订单数量------" + count);
        for (com.cxx.purchasecharge.core.model.persistence.Order oldOrder : warnOrders2)
        {
            if (OrderTypeCode.OUT_RETURN.equals (oldOrder.getTypeCode ()))
            {
                // System.out.println (oldOrder.getId ());
                addOrderOut (oldOrder);
            }
            if (OrderTypeCode.OUT.equals (oldOrder.getTypeCode ())
                && !OrderStatusCode.CANCEL.equals (oldOrder.getStatusCode ()))
            {
                // System.out.println (oldOrder.getId ());
                addOrderOut (oldOrder);
            }
        }
    }

    private void addOrderOut (com.cxx.purchasecharge.core.model.persistence.Order oldOrder)
    {
        com.pinfly.purchasecharge.core.model.persistence.out.OrderOut newOrder = new com.pinfly.purchasecharge.core.model.persistence.out.OrderOut ();
        newOrder.setBid (oldOrder.getId () + "");
        newOrder.setComment (oldOrder.getComment ());
        newOrder.setDateCreated (new Timestamp (oldOrder.getCreateDate ().getTime ()));
        newOrder.setDiscount (oldOrder.getDiscount ());
        newOrder.setLastUpdated (new Timestamp (oldOrder.getLastUpdated ().getTime ()));
        newOrder.setPaidMoney (oldOrder.getPaidMoney ());
        if (null != oldOrder.getCustomer ())
        {
            com.pinfly.purchasecharge.core.model.persistence.out.Customer customer = queryService.getCustomer (oldOrder.getCustomer ()
                                                                                                                       .getShortName ());
            if (null != customer)
            {
                newOrder.setCustomer (customer);
            }
        }

        if (CollectionUtils.isNotEmpty (com.cxx.purchasecharge.dal.DaoContext.getOrderItemDao ()
                                                                             .findByOrder (oldOrder.getId ())))
        {
            List <com.pinfly.purchasecharge.core.model.persistence.out.OrderOutItem> orderItems = new ArrayList <com.pinfly.purchasecharge.core.model.persistence.out.OrderOutItem> ();
            for (com.cxx.purchasecharge.core.model.persistence.OrderItem oldOrderItem : com.cxx.purchasecharge.dal.DaoContext.getOrderItemDao ()
                                                                                                                             .findByOrder (oldOrder.getId ()))
            {
                com.pinfly.purchasecharge.core.model.persistence.out.OrderOutItem orderItem = new com.pinfly.purchasecharge.core.model.persistence.out.OrderOutItem ();
                List <com.pinfly.purchasecharge.core.model.persistence.goods.Goods> goodses = queryService.getGoods (oldOrderItem.getGoods ()
                                                                                                                                 .getName ());
                if (null != goodses && goodses.size () > 0)
                {
                    orderItem.setGoods (goodses.get (0));
                    orderItem.setDepository (queryService.getGoodsDepository (defaultDepository));
                    orderItem.setAmount (oldOrderItem.getAmount ());
                    orderItem.setComment (oldOrderItem.getComment ());
                    orderItem.setUnitPrice (oldOrderItem.getUnitPrice ());
                    orderItem.setSum (oldOrderItem.getSum ());
                    orderItems.add (orderItem);
                }
            }
            newOrder.setOrderItems (orderItems);
        }

        if (OrderTypeCode.OUT.equals (oldOrder.getTypeCode ()))
        {
            newOrder.setTypeCode (com.pinfly.purchasecharge.core.model.OrderTypeCode.OUT);
        }
        if (OrderTypeCode.OUT_RETURN.equals (oldOrder.getTypeCode ()))
        {
            newOrder.setTypeCode (com.pinfly.purchasecharge.core.model.OrderTypeCode.OUT_RETURN);
        }
        if (0 != oldOrder.getUserOperatedBy ())
        {
            com.cxx.purchasecharge.core.model.persistence.User oldUser = com.cxx.purchasecharge.dal.DaoContext.getUserDao ()
                                                                                                              .findOne (oldOrder.getUserOperatedBy ());
            if (null != oldUser)
            {
                long newUserUid = queryService.getUniqueIdByUserId (oldUser.getUserId ());
                if (0 != newUserUid)
                {
                    newOrder.setUserCreatedBy (newUserUid);
                    newOrder.setUserUpdatedBy (newUserUid);
                }
            }
        }

        try
        {
            newOrder = persistenceService.addOrderOut (newOrder, null);
            if (OrderStatusCode.CANCEL.equals (oldOrder.getStatusCode ()))
            {
                persistenceService.updateOrderOutStatus (newOrder.getId (),
                                                         com.pinfly.purchasecharge.core.model.OrderStatusCode.CANCELED,
                                                         "", newOrder.getDateCreated ());
            }
            if (OrderStatusCode.FINISHED.equals (oldOrder.getStatusCode ()))
            {
                persistenceService.updateOrderOutStatus (newOrder.getId (),
                                                         com.pinfly.purchasecharge.core.model.OrderStatusCode.COMPLETED,
                                                         "", newOrder.getDateCreated ());
            }
        }
        catch (Exception e)
        {
            warnOrders2.add (oldOrder);
            count++;
            System.out.println ("报错出库订单号-----" + newOrder.getBid () + "  " + e.getMessage ());
        }
    }

    static String defaultPaymentAccount = "现金";

    @Test
    public void testAddPaymentAccount ()
    {
        List <com.cxx.purchasecharge.core.model.persistence.PaymentAccount> oldPaymentAccounts = (List <com.cxx.purchasecharge.core.model.persistence.PaymentAccount>) com.cxx.purchasecharge.dal.DaoContext.getPaymentAccountDao ()
                                                                                                                                                                                                            .findAll ();
        for (com.cxx.purchasecharge.core.model.persistence.PaymentAccount oldPaymentAccount : oldPaymentAccounts)
        {
            com.pinfly.purchasecharge.core.model.persistence.PaymentAccount newPaymentAccount = new com.pinfly.purchasecharge.core.model.persistence.PaymentAccount ();
            newPaymentAccount.setAccountId (oldPaymentAccount.getNumber ());
            newPaymentAccount.setName (oldPaymentAccount.getName ());
            newPaymentAccount.setAccountMode (oldPaymentAccount.getName ().equals (defaultPaymentAccount) ? com.pinfly.purchasecharge.core.model.PaymentAccountMode.CASH
                                                                                                         : com.pinfly.purchasecharge.core.model.PaymentAccountMode.DEPOSIT);
            // newPaymentAccount.setRemainMoney (1000000); 
            if(null == queryService.getPaymentAccountByName (newPaymentAccount.getName ())) 
            {
                persistenceService.addPaymentAccount (newPaymentAccount);
            }
        }
    }

    @Test
    public void testAddCustomerPayment ()
    {
        com.cxx.purchasecharge.core.model.PaymentSearchForm paymentSearchForm = new com.cxx.purchasecharge.core.model.PaymentSearchForm ();
        paymentSearchForm.setTypeCode (CustomerTypeCode.CUSTOMER);
        paymentSearchForm.setStartDate (DateUtils.string2Date ("2014-02-25", DateUtils.DATE_PATTERN));
        paymentSearchForm.setEndDate (DateUtils.string2Date ("2015-05-06", DateUtils.DATE_PATTERN));
        int page = 0;
        int size = 3000;
        Pageable pageable = new PageRequest (page, size, new Sort (Direction.ASC, "paidDate"));
        Page <com.cxx.purchasecharge.core.model.persistence.Payment> oldPayments = com.cxx.purchasecharge.dal.DaoContext.getPaymentDao ()
                                                                                                                        .findPagePaymentBy (paymentSearchForm,
                                                                                                                                            pageable);
        int count = 0;
        for (com.cxx.purchasecharge.core.model.persistence.Payment oldPayment : oldPayments.getContent ())
        {
            if (oldPayment.getOrderId () == 0)
            {
                count++;
                com.pinfly.purchasecharge.core.model.persistence.out.CustomerPayment customerPayment = new com.pinfly.purchasecharge.core.model.persistence.out.CustomerPayment ();
                com.pinfly.purchasecharge.core.model.persistence.out.Customer customer = queryService.getCustomer (oldPayment.getCustomer ()
                                                                                                                             .getShortName ());
                if (null != customer)
                {
                    customerPayment.setCustomer (customer);
                    customerPayment.setPaidDate (new Timestamp (oldPayment.getPaidDate ().getTime ()));
                    customerPayment.setComment (oldPayment.getComment ());
                    customerPayment.setTypeCode (com.pinfly.purchasecharge.core.model.PaymentTypeCode.OUT_PAID_MONEY);
                    com.cxx.purchasecharge.core.model.persistence.User oldUser = com.cxx.purchasecharge.dal.DaoContext.getUserDao ()
                                                                                                                      .findOne (oldPayment.getUserCreatedBy ());
                    if (null != oldUser)
                    {
                        long newUserUid = queryService.getUniqueIdByUserId (oldUser.getUserId ());
                        if (0 != newUserUid)
                        {
                            customerPayment.setUserCreatedBy (newUserUid);
                        }
                    }

                    List <com.pinfly.purchasecharge.core.model.persistence.out.CustomerPaymentRecord> paymentRecords = new ArrayList <com.pinfly.purchasecharge.core.model.persistence.out.CustomerPaymentRecord> ();
                    com.pinfly.purchasecharge.core.model.persistence.out.CustomerPaymentRecord paymentRecord = new com.pinfly.purchasecharge.core.model.persistence.out.CustomerPaymentRecord ();
                    paymentRecord.setPaid (oldPayment.getPaid ());
                    paymentRecord.setComment (oldPayment.getComment ());
                    if (null != oldPayment.getPaymentAccount ())
                    {
                        com.pinfly.purchasecharge.core.model.persistence.PaymentAccount newPaymentAccount = queryService.getPaymentAccountByName (oldPayment.getPaymentAccount ()
                                                                                                                                                            .getName ());
                        if (null != newPaymentAccount)
                        {
                            paymentRecord.setPaymentAccount (newPaymentAccount);
                        }
                    }
                    paymentRecords.add (paymentRecord);
                    customerPayment.setPaymentRecords (paymentRecords);
                    persistenceService.addCustomerPayment (customerPayment);
                }
            }
        }
        System.out.println ("CustomerPayment----" + count);
    }

    @Test
    public void testAddProviderPayment ()
    {
        com.cxx.purchasecharge.core.model.PaymentSearchForm paymentSearchForm = new com.cxx.purchasecharge.core.model.PaymentSearchForm ();
        paymentSearchForm.setTypeCode (CustomerTypeCode.PROVIDER);
        paymentSearchForm.setStartDate (DateUtils.string2Date ("2014-02-25", DateUtils.DATE_PATTERN));
        paymentSearchForm.setEndDate (DateUtils.string2Date ("2015-05-06", DateUtils.DATE_PATTERN));
        int page = 0;
        int size = 3000;
        Pageable pageable = new PageRequest (page, size, new Sort (Direction.ASC, "paidDate"));
        Page <com.cxx.purchasecharge.core.model.persistence.Payment> oldPayments = com.cxx.purchasecharge.dal.DaoContext.getPaymentDao ()
                                                                                                                        .findPagePaymentBy (paymentSearchForm,
                                                                                                                                            pageable);
        int count = 0;
        for (com.cxx.purchasecharge.core.model.persistence.Payment oldPayment : oldPayments.getContent ())
        {
            if (oldPayment.getOrderId () == 0)
            {
                count++;
                com.pinfly.purchasecharge.core.model.persistence.in.ProviderPayment providerPayment = new com.pinfly.purchasecharge.core.model.persistence.in.ProviderPayment ();
                com.pinfly.purchasecharge.core.model.persistence.in.Provider provider = queryService.getProvider (oldPayment.getCustomer ()
                                                                                                                            .getShortName ());
                if (null != provider)
                {
                    providerPayment.setProvider (provider);
                    providerPayment.setPaidDate (new Timestamp (oldPayment.getPaidDate ().getTime ()));
                    providerPayment.setComment (oldPayment.getComment ());
                    providerPayment.setTypeCode (com.pinfly.purchasecharge.core.model.PaymentTypeCode.IN_PAID_MONEY);
                    com.cxx.purchasecharge.core.model.persistence.User oldUser = com.cxx.purchasecharge.dal.DaoContext.getUserDao ()
                                                                                                                      .findOne (oldPayment.getUserCreatedBy ());
                    if (null != oldUser)
                    {
                        long newUserUid = queryService.getUniqueIdByUserId (oldUser.getUserId ());
                        if (0 != newUserUid)
                        {
                            providerPayment.setUserCreatedBy (newUserUid);
                        }
                    }

                    List <com.pinfly.purchasecharge.core.model.persistence.in.ProviderPaymentRecord> paymentRecords = new ArrayList <com.pinfly.purchasecharge.core.model.persistence.in.ProviderPaymentRecord> ();
                    com.pinfly.purchasecharge.core.model.persistence.in.ProviderPaymentRecord paymentRecord = new com.pinfly.purchasecharge.core.model.persistence.in.ProviderPaymentRecord ();
                    paymentRecord.setPaid (oldPayment.getPaid ());
                    paymentRecord.setComment (oldPayment.getComment ());
                    if (null != oldPayment.getPaymentAccount ())
                    {
                        com.pinfly.purchasecharge.core.model.persistence.PaymentAccount newPaymentAccount = queryService.getPaymentAccountByName (oldPayment.getPaymentAccount ()
                                                                                                                                                            .getName ());
                        if (null != newPaymentAccount && newPaymentAccount.getRemainMoney () > oldPayment.getPaid ())
                        {
                            paymentRecord.setPaymentAccount (newPaymentAccount);
                        }
                        else
                        {
                            newPaymentAccount = null;
                            List <com.pinfly.purchasecharge.core.model.persistence.PaymentAccount> newPaymentAccounts = queryService.getAllPaymentAccount ();
                            for (com.pinfly.purchasecharge.core.model.persistence.PaymentAccount pa : newPaymentAccounts)
                            {
                                if (pa.getRemainMoney () > oldPayment.getPaid ())
                                {
                                    newPaymentAccount = pa;
                                    break;
                                }
                            }
                            if (null != newPaymentAccount)
                            {
                                paymentRecord.setPaymentAccount (newPaymentAccount);
                            }
                        }
                    }
                    paymentRecords.add (paymentRecord);
                    providerPayment.setPaymentRecords (paymentRecords);
                    persistenceService.addProviderPayment (providerPayment);
                }
            }
        }
        System.out.println ("ProviderPayment----" + count);
    }

    @Test
    public void testAddAccountingType ()
    {
        List <com.cxx.purchasecharge.core.model.persistence.AccountingType> oldAccountingTypes = (List <com.cxx.purchasecharge.core.model.persistence.AccountingType>) com.cxx.purchasecharge.dal.DaoContext.getAccountingTypeDao ()
                                                                                                                                                                                                            .findAll ();
        for (com.cxx.purchasecharge.core.model.persistence.AccountingType oldAccountingType : oldAccountingTypes)
        {
            com.pinfly.purchasecharge.core.model.persistence.AccountingType newAccountingType = new com.pinfly.purchasecharge.core.model.persistence.AccountingType ();
            newAccountingType.setName (oldAccountingType.getName ());
            newAccountingType.setAccountingMode (AccountingModeCode.IN_COME.equals (oldAccountingType.getAccountingMode ()) ? com.pinfly.purchasecharge.core.model.AccountingModeCode.IN_COME
                                                                                                                           : com.pinfly.purchasecharge.core.model.AccountingModeCode.OUT_LAY);
            persistenceService.addAccountingType (newAccountingType);
        }
    }

    @Test
    public void testAddAccounting ()
    {
        int count = 0;
        List <com.cxx.purchasecharge.core.model.persistence.Accounting> oldAccountings = (List <com.cxx.purchasecharge.core.model.persistence.Accounting>) com.cxx.purchasecharge.dal.DaoContext.getAccountingDao ()
                                                                                                                                                                                                .findAll ();
        for (com.cxx.purchasecharge.core.model.persistence.Accounting oldAccounting : oldAccountings)
        {
            count++;
            com.pinfly.purchasecharge.core.model.persistence.Accounting newAccounting = new com.pinfly.purchasecharge.core.model.persistence.Accounting ();
            newAccounting.setComment (oldAccounting.getComment ());
            newAccounting.setCreated (new Timestamp (oldAccounting.getCreated ().getTime ()));
            newAccounting.setUpdated (new Timestamp (oldAccounting.getUpdated ().getTime ()));
            com.pinfly.purchasecharge.core.model.persistence.AccountingType newAccountingType = queryService.getAccountingType (oldAccounting.getType ()
                                                                                                                                             .getName ());
            if (null != newAccountingType)
            {
                newAccounting.setType (newAccountingType);
            }
            newAccounting.setMoney (oldAccounting.getMoney ());
            newAccounting.setPaymentAccount (queryService.getPaymentAccountByName (defaultPaymentAccount));

            com.cxx.purchasecharge.core.model.persistence.User oldUser = com.cxx.purchasecharge.dal.DaoContext.getUserDao ()
                                                                                                              .findOne (oldAccounting.getUserCreatedBy ());
            if (null != oldUser)
            {
                long newUserUid = queryService.getUniqueIdByUserId (oldUser.getUserId ());
                if (0 != newUserUid)
                {
                    newAccounting.setUserCreatedBy (newUserUid);
                }
            }
            oldUser = com.cxx.purchasecharge.dal.DaoContext.getUserDao ().findOne (oldAccounting.getUserUpdatedBy ());
            if (null != oldUser)
            {
                long newUserUid = queryService.getUniqueIdByUserId (oldUser.getUserId ());
                if (0 != newUserUid)
                {
                    newAccounting.setUserUpdatedBy (newUserUid);
                }
            }
            persistenceService.addAccounting (newAccounting);
        }
        System.out.println ("Accounting----" + count);
    }

    @Test
    public void testCountPaymentAccount ()
    {
        List <com.pinfly.purchasecharge.core.model.persistence.PaymentAccount> newPaymentAccounts = (List <com.pinfly.purchasecharge.core.model.persistence.PaymentAccount>) queryService.getAllPaymentAccount ();
        float count = 0;
        for (com.pinfly.purchasecharge.core.model.persistence.PaymentAccount newPaymentAccount : newPaymentAccounts)
        {
            count += (newPaymentAccount.getRemainMoney ());
        }
        System.out.println ("PaymentAccount Remain All-----" + count);
    }

}
