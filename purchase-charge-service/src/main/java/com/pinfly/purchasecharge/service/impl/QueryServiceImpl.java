package com.pinfly.purchasecharge.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pinfly.common.crypto.CryptoException;
import com.pinfly.common.util.EncryptUtil;
import com.pinfly.purchasecharge.core.config.PurchaseChargeProperties;
import com.pinfly.purchasecharge.core.model.AccountingModeCode;
import com.pinfly.purchasecharge.core.model.AccountingSearchForm;
import com.pinfly.purchasecharge.core.model.GoodsStorageCourse;
import com.pinfly.purchasecharge.core.model.GoodsStorageCourseSearchForm;
import com.pinfly.purchasecharge.core.model.OrderSearchForm;
import com.pinfly.purchasecharge.core.model.OrderTypeCode;
import com.pinfly.purchasecharge.core.model.PaymentSearchForm;
import com.pinfly.purchasecharge.core.model.PaymentTransferTypeCode;
import com.pinfly.purchasecharge.core.model.PaymentTypeCode;
import com.pinfly.purchasecharge.core.model.RoleAuthority;
import com.pinfly.purchasecharge.core.model.UserRole;
import com.pinfly.purchasecharge.core.model.persistence.Accounting;
import com.pinfly.purchasecharge.core.model.persistence.AccountingType;
import com.pinfly.purchasecharge.core.model.persistence.DeliveryCompany;
import com.pinfly.purchasecharge.core.model.persistence.PaymentAccount;
import com.pinfly.purchasecharge.core.model.persistence.PaymentWay;
import com.pinfly.purchasecharge.core.model.persistence.goods.Goods;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsDepository;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsType;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsUnit;
import com.pinfly.purchasecharge.core.model.persistence.in.OrderIn;
import com.pinfly.purchasecharge.core.model.persistence.in.OrderInItem;
import com.pinfly.purchasecharge.core.model.persistence.in.Provider;
import com.pinfly.purchasecharge.core.model.persistence.in.ProviderContact;
import com.pinfly.purchasecharge.core.model.persistence.in.ProviderPayment;
import com.pinfly.purchasecharge.core.model.persistence.in.ProviderPaymentRecord;
import com.pinfly.purchasecharge.core.model.persistence.in.ProviderType;
import com.pinfly.purchasecharge.core.model.persistence.out.Customer;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerContact;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerLevel;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerPayment;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerPaymentRecord;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerType;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOut;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOutItem;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.Authority;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.Role;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.User;
import com.pinfly.purchasecharge.dal.AccountingDao;
import com.pinfly.purchasecharge.dal.AccountingTypeDao;
import com.pinfly.purchasecharge.dal.DeliveryCompanyDao;
import com.pinfly.purchasecharge.dal.PaymentAccountDao;
import com.pinfly.purchasecharge.dal.PaymentWayDao;
import com.pinfly.purchasecharge.dal.goods.GoodsDao;
import com.pinfly.purchasecharge.dal.goods.GoodsDepositoryDao;
import com.pinfly.purchasecharge.dal.goods.GoodsPictureDao;
import com.pinfly.purchasecharge.dal.goods.GoodsTypeDao;
import com.pinfly.purchasecharge.dal.goods.GoodsUnitDao;
import com.pinfly.purchasecharge.dal.in.OrderInDao;
import com.pinfly.purchasecharge.dal.in.OrderInItemDao;
import com.pinfly.purchasecharge.dal.in.ProviderContactDao;
import com.pinfly.purchasecharge.dal.in.ProviderDao;
import com.pinfly.purchasecharge.dal.in.ProviderPaymentDao;
import com.pinfly.purchasecharge.dal.in.ProviderTypeDao;
import com.pinfly.purchasecharge.dal.out.CustomerContactDao;
import com.pinfly.purchasecharge.dal.out.CustomerDao;
import com.pinfly.purchasecharge.dal.out.CustomerLevelDao;
import com.pinfly.purchasecharge.dal.out.CustomerPaymentDao;
import com.pinfly.purchasecharge.dal.out.CustomerTypeDao;
import com.pinfly.purchasecharge.dal.out.OrderOutDao;
import com.pinfly.purchasecharge.dal.out.OrderOutItemDao;
import com.pinfly.purchasecharge.dal.usersecurity.AuthorityDao;
import com.pinfly.purchasecharge.dal.usersecurity.RoleDao;
import com.pinfly.purchasecharge.dal.usersecurity.UserDao;
import com.pinfly.purchasecharge.service.QueryService;
import com.pinfly.purchasecharge.service.utils.DataCache;

public class QueryServiceImpl implements QueryService
{
    private static final Logger logger = Logger.getLogger (QueryServiceImpl.class);

    private CustomerDao customerDao;
    private CustomerContactDao customerContactDao;
    private CustomerPaymentDao customerPaymentDao;
    private CustomerTypeDao customerTypeDao;
    private CustomerLevelDao customerLevelDao;

    private ProviderDao providerDao;
    private ProviderContactDao providerContactDao;
    private ProviderPaymentDao providerPaymentDao;
    private ProviderTypeDao providerTypeDao;

    private OrderOutDao orderOutDao;
    private OrderOutItemDao orderOutItemDao;
    private OrderInDao orderInDao;
    private OrderInItemDao orderInItemDao;

    private UserDao userDao;
    private RoleDao roleDao;
    private AuthorityDao authorityDao;

    private GoodsDao goodsDao;
    private GoodsUnitDao goodsUnitDao;
    private GoodsTypeDao goodsTypeDao;
    private GoodsDepositoryDao goodsDepositoryDao;
    @SuppressWarnings ("unused")
    private GoodsPictureDao goodsPictureDao;

    private AccountingTypeDao accountingTypeDao;
    private AccountingDao accountingDao;
    private PaymentAccountDao paymentAccountDao;
    private PaymentWayDao paymentWayDao;
    private DeliveryCompanyDao deliveryCompanyDao;

    @Override
    public CustomerContact getCustomerContact (long id)
    {
        return customerContactDao.findOne (id);
    }

    @Override
    public CustomerContact getCustomerContact (String name)
    {
        return customerContactDao.findByName (name);
    }

    /*
     * @Override public Page <CustomerContact> findCustomerContactByFuzzy
     * (Pageable pageable, String searchKey, String signUser, boolean admin) {
     * return customerContactDao.findByFuzzy (pageable, searchKey,
     * getUniqueIdByUserId(signUser), admin); }
     */

    @Override
    public Customer getCustomer (long id)
    {
        return customerDao.findOne (id);
    }

    @Override
    public Customer getCustomer (String name)
    {
        return customerDao.findByShortName (name);
    }

    @Override
    public Customer getCustomerByShortCode (String shortCode)
    {
        return customerDao.findByShortCode (shortCode);
    }

    @Override
    public Provider getProviderByShortCode (String shortCode)
    {
        return providerDao.findByShortCode (shortCode);
    }

    @Override
    public Customer getCustomerByContact (long contactId)
    {
        CustomerContact contact = customerContactDao.findOne (contactId);
        if (null != contact)
        {
            return contact.getCustomer ();
        }
        return null;
    }

    @Override
    public List <Customer> getCustomerByShortNameLike (String name, String signUser, boolean admin)
    {
        return customerDao.findByShortNameLike (name, getUniqueIdByUserId (signUser), admin);
    }

    @Override
    public List <Customer> getAllCustomer (String signUser, boolean admin)
    {
        return customerDao.findAll (getUniqueIdByUserId (signUser), admin);
    }

    @Override
    public Page <Customer> findCustomerByFuzzy (Pageable pageable, String searchKey, String signUser, boolean admin)
    {
        Page <Customer> customerPage = customerDao.findCustomerByFuzzy (pageable, searchKey,
                                                                        getUniqueIdByUserId (signUser), admin);
        if (null != customerPage)
        {
            List <Customer> customers = customerPage.getContent ();
            for (Customer customer : customers)
            {
                customer.setCustomerLevel (getCustomerLevel (customer.getId ()));
            }
        }
        return customerPage;
    }

    @Override
    public float countCustomerUnpay (String searchKey, String signUser, boolean admin)
    {
        return customerDao.countUnPay (searchKey, getUniqueIdByUserId (signUser), admin);
    }

    @Override
    public float countProviderUnpay (String searchKey, String signUser, boolean admin)
    {
        return providerDao.countUnPay (searchKey, getUniqueIdByUserId (signUser), admin);
    }

    @Override
    public float countCustomerPaid (PaymentSearchForm searchForm)
    {
        return customerPaymentDao.countPaid (searchForm);
    }

    @Override
    public float countProviderPaid (PaymentSearchForm searchForm)
    {
        return providerPaymentDao.countPaid (searchForm);
    }

    @Override
    public float countCustomerNewUnPaid (PaymentSearchForm searchForm)
    {
        return customerPaymentDao.countNewUnPaid (searchForm);
    }

    @Override
    public float countProviderNewUnPaid (PaymentSearchForm searchForm)
    {
        return providerPaymentDao.countNewUnPaid (searchForm);
    }

    @Override
    public OrderOut getOrderOut (long id)
    {
        return orderOutDao.findOne (id);
    }

    @Override
    public OrderOut getOrderOut (String bid)
    {
        return orderOutDao.findByBid (bid);
    }

    @Override
    public OrderIn getOrderIn (String bid)
    {
        return orderInDao.findByBid (bid);
    }

    @Override
    public List <OrderOutItem> getOrderOutItemByOrder (long orderId, boolean resultWithOrder)
    {
        return orderOutItemDao.findByOrder (orderId, resultWithOrder);
    }
    
    @Override
    public Page <OrderOut> findOrderOutByFuzzy (OrderTypeCode typeCode, Pageable pageable, String searchKey,
                                                String signUser, boolean admin)
    {
        return orderOutDao.findByFuzzy (typeCode, pageable, searchKey, getUniqueIdByUserId (signUser), admin);
    }

    @Override
    public Page <OrderOut> findOrderOutBySearchForm (OrderTypeCode typeCode, Pageable pageable,
                                                     OrderSearchForm searchForm, String signUser, boolean admin)
    {
        if(null != typeCode) 
        {
            return orderOutDao.findBySearchForm (typeCode, pageable, searchForm, getUniqueIdByUserId (signUser), admin);
        }
        return orderOutDao.findBySearchForm (pageable, searchForm, getUniqueIdByUserId (signUser), admin);
    }

    @Override
    public Page <OrderOut> findOrderOutBySearchForm (Pageable pageable, OrderSearchForm searchForm, String signUser,
                                                     boolean admin)
    {
        return orderOutDao.findBySearchForm (pageable, searchForm, getUniqueIdByUserId (signUser), admin);
    }

    @Override
    public float countOrderOutReceivableBySearchForm (OrderTypeCode orderType, OrderSearchForm searchForm, String signUser, boolean admin)
    {
        if(null != orderType) 
        {
            return orderOutDao.countReceivableBy (orderType, searchForm, getUniqueIdByUserId (signUser), admin);
        }
        return orderOutDao.countReceivableBy (searchForm, getUniqueIdByUserId (signUser), admin);
    }

    @Override
    public float countOrderOutPaidMoneyBySearchForm (OrderTypeCode orderType, OrderSearchForm searchForm,
                                                     String signUser, boolean admin)
    {
        if(null != orderType) 
        {
            return orderOutDao.countPaidMoneyBy (orderType, searchForm, getUniqueIdByUserId (signUser), admin);
        }
        return orderOutDao.countPaidMoneyBy (searchForm, getUniqueIdByUserId (signUser), admin);
    }

    @Override
    public float countOrderOutProfitBySearchForm (OrderTypeCode orderType, OrderSearchForm searchForm, String signUser, boolean admin)
    {
        if(null != orderType) 
        {
            return orderOutDao.countProfitBy (orderType, searchForm, getUniqueIdByUserId (signUser), admin);
        }
        return orderOutDao.countProfitBy (searchForm, getUniqueIdByUserId (signUser), admin);
    }

    @Override
    public float countOrderInReceivableBySearchForm (OrderTypeCode orderType, OrderSearchForm searchForm, String signUser, boolean admin)
    {
        if(null != orderType) 
        {
            return orderInDao.countReceivableBy (orderType, searchForm, getUniqueIdByUserId (signUser), admin);
        }
        return orderInDao.countReceivableBy (searchForm, getUniqueIdByUserId (signUser), admin);
    }
    
    @Override
    public float countOrderInPaidMoneyBySearchForm (OrderTypeCode orderType, OrderSearchForm searchForm,
                                                    String signUser, boolean admin)
    {
        if(null != orderType) 
        {
            return orderInDao.countPaidMoneyBy (orderType, searchForm, getUniqueIdByUserId (signUser), admin);
        }
        return orderInDao.countPaidMoneyBy (searchForm, getUniqueIdByUserId (signUser), admin);
    }

    @Override
    public List <OrderOut> getOrderOutByCustomer (long customerId)
    {
        return orderOutDao.findByCustomerId (customerId);
    }

    @Override
    public List <OrderOutItem> getOrderOutItemByGoods (long goodsId)
    {
        return orderOutItemDao.findByGoods (goodsId);
    }

    @Override
    public List <Goods> getGoods (String name)
    {
        return goodsDao.findByName (name);
    }

    @Override
    public Goods getGoodsBySpecificationModel (String name, String specificationModel)
    {
        return goodsDao.findBySpecificationModel (name, specificationModel);
    }

    @Override
    public List <Goods> getGoodsByTypeAndDepository (long typeId, long depositoryId)
    {
        return goodsDao.findByTypeAndDepository (typeId, depositoryId);
    }

    @Override
    public Goods getGoodsByShortCode (String shortCode)
    {
        return goodsDao.findByShortCode (shortCode);
    }

    @Override
    public long countGoodsRestAmount (long goodsTypeId, String searchKey)
    {
        return goodsDao.countRestAmount (goodsTypeId, searchKey);
    }

    @Override
    public float countGoodsRestWorth (long goodsTypeId, String searchKey)
    {
        return goodsDao.countRestWorth (goodsTypeId, searchKey);
    }

    @Override
    public long getUniqueIdByUserId (String userId)
    {
        if (StringUtils.isNotBlank (userId))
        {
            return userDao.getUniqueIdByUserId (userId);
        }
        return 0;
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <CustomerType> getAllCustomerType ()
    {
        String key = CustomerType.class.getName ();
        Object data = DataCache.getDataFromCache (key);
        List <CustomerType> customerTypes = new ArrayList <CustomerType> ();
        if(null == data) 
        {
            customerTypes = (List <CustomerType>) customerTypeDao.findAll ();
            DataCache.putDataToCache (key, customerTypes);
        }
        else 
        {
            customerTypes = (List <CustomerType>) data;
        }
        return customerTypes;
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <CustomerLevel> getAllCustomerLevel ()
    {
        String key = CustomerLevel.class.getName ();
        Object data = DataCache.getDataFromCache (key);
        List <CustomerLevel> customerLevels = new ArrayList <CustomerLevel> ();
        if(null == data) 
        {
            customerLevels = (List <CustomerLevel>) customerLevelDao.findAll ();
            DataCache.putDataToCache (key, customerLevels);
        }
        else 
        {
            customerLevels = (List <CustomerLevel>) data;
        }
        return customerLevels;
    }

    @Override
    public CustomerLevel getCustomerLevel (String name)
    {
        return customerLevelDao.findByName (name);
    }

    @Override
    public CustomerLevel getCustomerLevel (long customerId)
    {
        OrderSearchForm searchForm = new OrderSearchForm ();
        searchForm.setCustomerId (customerId);
        float orderReceivable = orderOutDao.countReceivableBy (searchForm, 0, true);
        float orderProfit = orderOutDao.countProfitBy (searchForm, 0, true);
        List <CustomerLevel> allLevels = getAllCustomerLevel ();
        List <CustomerLevel> descLevels = new ArrayList <CustomerLevel> ();
        for (CustomerLevel level : allLevels)
        {
            descLevels.add (level);
        }
        Collections.reverse (descLevels);
        for (CustomerLevel level : descLevels)
        {
            if (orderProfit >= level.getProfitMoney ())
            {
                return level;
            }
            if (orderReceivable >= level.getSaleMoney ())
            {
                return level;
            }
        }
        return null;
    }

    @Override
    public Provider getProvider (long id)
    {
        return providerDao.findOne (id);
    }

    @Override
    public Provider getProvider (String name)
    {
        return providerDao.findByShortName (name);
    }

    @Override
    public Provider getProviderByContact (long contactId)
    {
        ProviderContact contact = providerContactDao.findOne (contactId);
        if(null != contact) 
        {
            return contact.getProvider ();
        }
        return null;
    }

    @Override
    public List <Provider> getProviderByShortNameLike (String name, String signUser, boolean admin)
    {
        return providerDao.findByShortNameLike (name, getUniqueIdByUserId (signUser), admin);
    }

    @Override
    public List <Provider> getAllProvider (String signUser, boolean admin)
    {
        return providerDao.findAll (getUniqueIdByUserId (signUser), admin);
    }

    @Override
    public Page <Provider> findProviderByFuzzy (Pageable pageable, String searchKey, String signUser, boolean admin)
    {
        return providerDao.findProviderByFuzzy (pageable, searchKey, getUniqueIdByUserId (signUser), admin);
    }

    @Override
    public ProviderContact getProviderContact (long id)
    {
        return providerContactDao.findOne (id);
    }

    @Override
    public ProviderContact getProviderContact (String name)
    {
        return providerContactDao.findByName (name);
    }

    /*
     * @Override public Page<ProviderContact>
     * findProviderContactByFuzzy(Pageable pageable, String searchKey, String
     * signUser, boolean admin) { return
     * providerContactDao.findByFuzzy(pageable, searchKey,
     * getUniqueIdByUserId(signUser), admin); }
     */

    @Override
    @SuppressWarnings ("unchecked")
    public List <ProviderType> getAllProviderType ()
    {
        String key = ProviderType.class.getName ();
        Object data = DataCache.getDataFromCache (key);
        List <ProviderType> providerTypes = new ArrayList <ProviderType> ();
        if(null == data) 
        {
            providerTypes = (List <ProviderType>) providerTypeDao.findAll ();
            DataCache.putDataToCache (key, providerTypes);
        }
        else 
        {
            providerTypes = (List <ProviderType>) data;
        }
        return providerTypes;
    }

    @Override
    public OrderIn getOrderIn (long id)
    {
        return orderInDao.findOne (id);
    }

    @Override
    public List <OrderIn> getOrderInByProvider (long providerId)
    {
        return orderInDao.findByProviderId (providerId);
    }

    @Override
    public Page <OrderIn> findOrderInByFuzzy (OrderTypeCode typeCode, Pageable pageable, String searchKey,
                                              String signUser, boolean admin)
    {
        return orderInDao.findByFuzzy (typeCode, pageable, searchKey, getUniqueIdByUserId (signUser), admin);
    }

    @Override
    public Page <OrderIn> findOrderInBySearchForm (OrderTypeCode typeCode, Pageable pageable,
                                                   OrderSearchForm searchForm, String signUser, boolean admin)
    {
        if(null != typeCode) 
        {
            return orderInDao.findBySearchForm (typeCode, pageable, searchForm, getUniqueIdByUserId (signUser), admin);
        }
        return orderInDao.findBySearchForm (pageable, searchForm, getUniqueIdByUserId (signUser), admin);
    }

    @Override
    public Page <OrderIn> findOrderInBySearchForm (Pageable pageable, OrderSearchForm searchForm, String signUser,
                                                   boolean admin)
    {
        return orderInDao.findBySearchForm (pageable, searchForm, getUniqueIdByUserId (signUser), admin);
    }

    @Override
    public List <OrderInItem> getOrderInItemByOrder (long orderId, boolean resultWithOrder)
    {
        return orderInItemDao.findByOrder (orderId, resultWithOrder);
    }
    
    @Override
    public List <OrderInItem> getOrderInItemByGoods (long goodsId)
    {
        return orderInItemDao.findByGoods (goodsId);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <GoodsUnit> getAllGoodsUnit ()
    {
        String key = GoodsUnit.class.getName ();
        Object data = DataCache.getDataFromCache (key);
        List <GoodsUnit> goodsUnits = new ArrayList <GoodsUnit> ();
        if(null == data) 
        {
            goodsUnits = (List <GoodsUnit>) goodsUnitDao.findAll ();
            DataCache.putDataToCache (key, goodsUnits);
        }
        else 
        {
            goodsUnits = (List <GoodsUnit>) data;
        }
        return goodsUnits;
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <GoodsType> getAllGoodsType ()
    {
        String key = GoodsType.class.getName ();
        Object data = DataCache.getDataFromCache (key);
        List <GoodsType> goodsTypes = new ArrayList <GoodsType> ();
        if(null == data) 
        {
            goodsTypes = (List <GoodsType>) goodsTypeDao.findAll ();
            DataCache.putDataToCache (key, goodsTypes);
        }
        else 
        {
            goodsTypes = (List <GoodsType>) data;
        }
        return goodsTypes;
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <GoodsDepository> getAllGoodsDepository ()
    {
        String key = GoodsDepository.class.getName ();
        Object data = DataCache.getDataFromCache (key);
        List <GoodsDepository> goodsDepositories = new ArrayList <GoodsDepository> ();
        if(null == data) 
        {
            goodsDepositories = (List <GoodsDepository>) goodsDepositoryDao.findAll ();
            DataCache.putDataToCache (key, goodsDepositories);
        }
        else 
        {
            goodsDepositories = (List <GoodsDepository>) data;
        }
        return goodsDepositories;
    }

    @Override
    public List <User> getAllUser ()
    {
        return (List <User>) userDao.findAll ();
    }

    @Override
    public List <Role> getAllRole ()
    {
        return (List <Role>) roleDao.findAll ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <Authority> getAllAuthority ()
    {
        String key = Authority.class.getName ();
        Object data = DataCache.getDataFromCache (key);
        List <Authority> authorities = new ArrayList <Authority> ();
        if(null == data) 
        {
            authorities = (List <Authority>) authorityDao.findAll ();
            DataCache.putDataToCache (key, authorities);
        }
        else 
        {
            authorities = (List <Authority>) data;
        }
        return authorities;
    }

    @Override
    public Page <User> findUser (Pageable pageable, String searchKey)
    {
        return userDao.findByFuzzy (pageable, searchKey);
    }

    @Override
    public User getUser (String userId)
    {
        return userDao.findByUserId (userId);
    }

    @Override
    public User getUser (long userUid)
    {
        return userDao.findOne (userUid);
    }

    @Override
    public boolean checkPassword (String userId, String password)
    {
        if (StringUtils.isNotBlank (userId) && StringUtils.isNotBlank (password))
        {
            User user = userDao.findByUserId (userId);
            try
            {
                if (null != user)
                {
                    if (password.equals (EncryptUtil.decryptWithCryptTool (user.getPwd ())))
                    {
                        return true;
                    }
                }
            }
            catch (CryptoException e)
            {
                logger.warn (e.getMessage (), e);
            }
        }
        return false;
    }

    @Override
    public Role getRole (String name)
    {
        return roleDao.findByName (name);
    }

    @Override
    public List <Authority> getParentAuthority (long authId)
    {
        return authorityDao.findByParent (authId);
    }

    @Override
    public Authority getAuthority (String name)
    {
        return authorityDao.findByName (name);
    }

    @Override
    public List <RoleAuthority> getRoleAuthorityByRole (String roleName)
    {
        return userDao.findRoleAuthorityByRole (roleName);
    }

    @Override
    public List <UserRole> getUserRoleByUserId (String userId)
    {
        return userDao.findUserRoleByUserId (userId);
    }

    @Override
    public List <CustomerPayment> findCustomerPaymentByAdvance (PaymentSearchForm searchForm)
    {
        return customerPaymentDao.findByAdvance (searchForm);
    }

    @Override
    public List <ProviderPayment> findProviderPaymentByAdvance (long providerId, Timestamp start, Timestamp end)
    {
        return providerPaymentDao.findByAdvance (providerId, start, end);
    }

    @Override
    public Page <CustomerPayment> findPagedCustomerPayment (PaymentSearchForm searchForm, Pageable pageable)
    {
        return customerPaymentDao.findPagePayment (searchForm, pageable);
    }

    @Override
    public Page <ProviderPayment> findPagedProviderPayment (PaymentSearchForm searchForm, Pageable pageable)
    {
        return providerPaymentDao.findPagePayment (searchForm, pageable);
    }

    @Override
    public Page <Accounting> findPageAccounting (AccountingSearchForm searchForm, Pageable pageable)
    {
        return accountingDao.findPageAccounting (searchForm, pageable);
    }

    @Override
    public float countAccountingExpense (AccountingSearchForm searchForm)
    {
        searchForm.setAccountingModeCode (AccountingModeCode.OUT_LAY);
        return accountingDao.countAccounting (searchForm);
    }

    @Override
    public float countAccountingIncome (AccountingSearchForm searchForm)
    {
        searchForm.setAccountingModeCode (AccountingModeCode.IN_COME);
        return accountingDao.countAccounting (searchForm);
    }

    @Override
    public float countAccountingRevenue (AccountingSearchForm searchForm)
    {
        searchForm.setAccountingModeCode (null);
        return accountingDao.countAccounting (searchForm);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <AccountingType> getAllAccountingType ()
    {
        String key = AccountingType.class.getName ();
        Object data = DataCache.getDataFromCache (key);
        List <AccountingType> accountingTypes = new ArrayList <AccountingType> ();
        if(null == data) 
        {
            accountingTypes = (List <AccountingType>) accountingTypeDao.findAll ();
            DataCache.putDataToCache (key, accountingTypes);
        }
        else 
        {
            accountingTypes = (List <AccountingType>) data;
        }
        return accountingTypes;
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <PaymentAccount> getAllPaymentAccount ()
    {
        String key = PaymentAccount.class.getName ();
        Object data = null;
        if(PurchaseChargeProperties.getAllowCachePaymentAccount ()) 
        {
            data = DataCache.getDataFromCache (key);
        }
        List <PaymentAccount> paymentAccounts = new ArrayList <PaymentAccount> ();
        if(null == data) 
        {
            paymentAccounts = (List <PaymentAccount>) paymentAccountDao.findAll ();
            if(PurchaseChargeProperties.getAllowCachePaymentAccount ()) 
            {
                DataCache.putDataToCache (key, paymentAccounts);
            }
        }
        else 
        {
            paymentAccounts = (List <PaymentAccount>) data;
        }
        return paymentAccounts;
    }

    @Override
    public List <Accounting> findAccountingByType (long typeId)
    {
        return accountingDao.findByType (typeId);
    }

    @Override
    public List <AccountingType> getAccountingType (AccountingModeCode modeCode)
    {
        return accountingTypeDao.findByMode (modeCode);
    }

    @Override
    public AccountingType getAccountingType (String name)
    {
        return accountingTypeDao.findByName (name);
    }

    @Override
    public PaymentAccount getPaymentAccountByName (String name)
    {
        return paymentAccountDao.findByName (name);
    }

    @Override
    public PaymentAccount getPaymentAccountByAccount (String account)
    {
        return paymentAccountDao.findByAccount (account);
    }

    @Override
    public GoodsUnit getGoodsUnit (String name)
    {
        return goodsUnitDao.findByName (name);
    }

    @Override
    public GoodsType getGoodsType (String name)
    {
        return goodsTypeDao.findByName (name);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public GoodsType getGoodsType (long id)
    {
        String key = GoodsType.class.getName ();
        Object data = DataCache.getDataFromCache (key);
        if(null != data) 
        {
            List <GoodsType> types = (List <GoodsType>) data;
            for(GoodsType type : types) 
            {
                if(id == type.getId ()) 
                {
                    return type;
                }
            }
        }
        return goodsTypeDao.findOne (id);
    }

    @Override
    public List <GoodsType> getGoodsTypeByParent (long parentId)
    {
        return goodsTypeDao.findByParent (parentId);
    }

    @Override
    public GoodsDepository getGoodsDepository (String name)
    {
        return goodsDepositoryDao.findByName (name);
    }

    @Override
    public CustomerType getCustomerType (String name)
    {
        return customerTypeDao.findByName (name);
    }

    @Override
    public ProviderType getProviderType (String name)
    {
        return providerTypeDao.findByName (name);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <Goods> getAllGoods ()
    {
        String key = Goods.class.getName ();
        Object data = null;
        if(PurchaseChargeProperties.getAllowCacheGoods ()) 
        {
            data = DataCache.getDataFromCache (key);
        }
        List <Goods> goodses = new ArrayList <Goods> ();
        if(null == data) 
        {
            goodses = (List <Goods>) goodsDao.findAll ();
            if(PurchaseChargeProperties.getAllowCacheGoods ()) 
            {
                DataCache.putDataToCache (key, goodses);
            }
        }
        else 
        {
            goodses = (List <Goods>) data;
        }
        return goodses;
    }

    @Override
    public Page <Goods> getGoods (long goodsTypeId, Pageable pageable, String searchKey, boolean showHasStorage)
    {
        return goodsDao.findByFuzzy (goodsTypeId, pageable, searchKey, showHasStorage);
    }

    @Override
    public List <Goods> getGoodsByFuzzyName (String name)
    {
        return goodsDao.findByFuzzyName (name);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public Goods getGoods (long id)
    {
        if(PurchaseChargeProperties.getAllowCacheGoods ()) 
        {
            String key = Goods.class.getName ();
            Object data = DataCache.getDataFromCache (key);
            if(null != data) 
            {
                List <Goods> goodsList = (List <Goods>) data;
                for(Goods goods : goodsList) 
                {
                    if(id == goods.getId ()) 
                    {
                        return goods;
                    }
                }
            }
        }
        return goodsDao.findOne (id);
    }

    @Override
    public long countGoods ()
    {
        return goodsDao.count ();
    }

    @Override
    public long countGoodsIncrement (Date start, Date end)
    {
        return goodsDao.countGoodsIncrement (start, end);
    }

    @Override
    public long countGoodsHasStorage ()
    {
        return goodsDao.countHasStorage ();
    }

    @Override
    public long countGoodsByType (long type)
    {
        return goodsDao.countByType (type);
    }

    @Override
    public Page <GoodsStorageCourse> getGoodsStorageCourse (Pageable pageable, GoodsStorageCourseSearchForm searchForm)
    {
        if (null != searchForm)
        {
            if (OrderTypeCode.OUT == searchForm.getOrderTypeCode ())
            {
                return goodsDao.findStorageCourseFromOrderOut (pageable, searchForm);
            }
            else if(OrderTypeCode.IN == searchForm.getOrderTypeCode ())
            {
                return goodsDao.findStorageCourseFromOrderIn (pageable, searchForm);
            }
            else 
            {
                return goodsDao.findStorageCourse (pageable, searchForm);
            }
        }
        return null;
    }

    @Override
    public long countGoodsStorageCourse (GoodsStorageCourseSearchForm searchForm)
    {
        if (OrderTypeCode.OUT == searchForm.getOrderTypeCode ())
        {
            return goodsDao.countStorageCourseFromOrderOut (searchForm);
        }
        else if(OrderTypeCode.IN == searchForm.getOrderTypeCode ())
        {
            return goodsDao.countStorageCourseFromOrderIn (searchForm);
        }
        return 0;
    }

    @Override
    public List <Goods> getGoodsByNameOrCode (String nameOrCode)
    {
        return goodsDao.findGoods (nameOrCode);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public PaymentWay getPaymentWay (long id)
    {
        String key = PaymentWay.class.getName ();
        Object data = DataCache.getDataFromCache (key);
        if(null != data) 
        {
            List <PaymentWay> ways = (List <PaymentWay>) data;
            for(PaymentWay way : ways) 
            {
                if(id == way.getId ()) 
                {
                    return way;
                }
            }
        }
        return paymentWayDao.findOne (id);
    }

    @Override
    public PaymentWay getPaymentWay (String name)
    {
        return paymentWayDao.findByName (name);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <PaymentWay> getAllPaymentWay ()
    {
        String key = PaymentWay.class.getName ();
        Object data = DataCache.getDataFromCache (key);
        List <PaymentWay> paymentWays = new ArrayList <PaymentWay> ();
        if(null == data) 
        {
            paymentWays = (List <PaymentWay>) paymentWayDao.findAll ();
            DataCache.putDataToCache (key, paymentWays);
        }
        else 
        {
            paymentWays = (List <PaymentWay>) data;
        }
        return paymentWays;
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <DeliveryCompany> getAllDeliveryCompany ()
    {
        String key = DeliveryCompany.class.getName ();
        Object data = DataCache.getDataFromCache (key);
        List <DeliveryCompany> deliveryCompanys = new ArrayList <DeliveryCompany> ();
        if(null == data) 
        {
            deliveryCompanys = (List <DeliveryCompany>) deliveryCompanyDao.findAll ();
            DataCache.putDataToCache (key, deliveryCompanys);
        }
        else 
        {
            deliveryCompanys = (List <DeliveryCompany>) data;
        }
        return deliveryCompanys;
    }

    @Override
    public DeliveryCompany getDeliveryCompany (String name)
    {
        return deliveryCompanyDao.findByName (name);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public CustomerType getCustomerType (long id)
    {
        String key  = CustomerType.class.getName ();
        Object data = DataCache.getDataFromCache (key);
        if(null != data) 
        {
            List <CustomerType> types = (List <CustomerType>) data;
            for (CustomerType type : types) 
            {
                if(id == type.getId ()) 
                {
                    return type;
                }
            }
        }
        return customerTypeDao.findOne (id);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public CustomerLevel getCustomerLevelById (long id)
    {
        String key  = CustomerLevel.class.getName ();
        Object data = DataCache.getDataFromCache (key);
        if(null != data) 
        {
            List <CustomerLevel> levels = (List <CustomerLevel>) data;
            for (CustomerLevel level : levels) 
            {
                if(id == level.getId ()) 
                {
                    return level;
                }
            }
        }
        return customerLevelDao.findOne (id);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public ProviderType getProviderType (long id)
    {
        String key  = ProviderType.class.getName ();
        Object data = DataCache.getDataFromCache (key);
        if(null != data) 
        {
            List <ProviderType> types = (List <ProviderType>) data;
            for (ProviderType type : types) 
            {
                if(id == type.getId ()) 
                {
                    return type;
                }
            }
        }
        return providerTypeDao.findOne (id);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public GoodsUnit getGoodsUnit (long id)
    {
        String key  = GoodsUnit.class.getName ();
        Object data = DataCache.getDataFromCache (key);
        if(null != data) 
        {
            List <GoodsUnit> units = (List <GoodsUnit>) data;
            for (GoodsUnit unit : units) 
            {
                if(id == unit.getId ()) 
                {
                    return unit;
                }
            }
        }
        return goodsUnitDao.findOne (id);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public GoodsDepository getGoodsDepository (long id)
    {
        String key  = GoodsDepository.class.getName ();
        Object data = DataCache.getDataFromCache (key);
        if(null != data) 
        {
            List <GoodsDepository> depositories = (List <GoodsDepository>) data;
            for (GoodsDepository depository : depositories) 
            {
                if(id == depository.getId ()) 
                {
                    return depository;
                }
            }
        }
        return goodsDepositoryDao.findOne (id);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public AccountingType getAccountingType (long id)
    {
        String key  = AccountingType.class.getName ();
        Object data = DataCache.getDataFromCache (key);
        if(null != data) 
        {
            List <AccountingType> types = (List <AccountingType>) data;
            for (AccountingType type : types) 
            {
                if(id == type.getId ()) 
                {
                    return type;
                }
            }
        }
        return accountingTypeDao.findOne (id);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public PaymentAccount getPaymentAccount (long id)
    {
        if(PurchaseChargeProperties.getAllowCachePaymentAccount ()) 
        {
            String key  = PaymentAccount.class.getName ();
            Object data = DataCache.getDataFromCache (key);
            if(null != data) 
            {
                List <PaymentAccount> accounts = (List <PaymentAccount>) data;
                for (PaymentAccount account : accounts) 
                {
                    if(id == account.getId ()) 
                    {
                        return account;
                    }
                }
            }
        }
        return paymentAccountDao.findOne (id);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public DeliveryCompany getDeliveryCompany (long id)
    {
        String key  = DeliveryCompany.class.getName ();
        Object data = DataCache.getDataFromCache (key);
        if(null != data) 
        {
            List <DeliveryCompany> companies = (List <DeliveryCompany>) data;
            for (DeliveryCompany company : companies) 
            {
                if(id == company.getId ()) 
                {
                    return company;
                }
            }
        }
        return deliveryCompanyDao.findOne (id);
    }
    
    @Override
    public PaymentAccount getPaymentAccountByOrderId (OrderTypeCode orderTypeCode, String orderId, PaymentTypeCode paymentTypeCode)
    {
        PaymentAccount paymentAccount = null;
        if(StringUtils.isNotBlank (orderId) && null != paymentTypeCode) 
        {
            if(OrderTypeCode.OUT.equals (orderTypeCode) || OrderTypeCode.OUT_RETURN.equals (orderTypeCode)) 
            {
                CustomerPayment cp = customerPaymentDao.findByReceiptIdAndType (orderId, paymentTypeCode);
                if(null != cp) 
                {
                    List <CustomerPaymentRecord> paymentRecords = cp.getPaymentRecords ();
                    if(CollectionUtils.isNotEmpty (paymentRecords)) 
                    {
                        paymentAccount = paymentRecords.get (0).getPaymentAccount ();
                    }
                }
            }
            else if(OrderTypeCode.IN.equals (orderTypeCode) || OrderTypeCode.IN_RETURN.equals (orderTypeCode)) 
            {
                ProviderPayment pp = providerPaymentDao.findByReceiptIdAndType (orderId, paymentTypeCode);
                if(null != pp) 
                {
                    List <ProviderPaymentRecord> paymentRecords = pp.getPaymentRecords ();
                    if(CollectionUtils.isNotEmpty (paymentRecords)) 
                    {
                        paymentAccount = paymentRecords.get (0).getPaymentAccount ();
                    }
                }
            }
        }
        return paymentAccount;
    }

    public String getPaymentTransferSource (PaymentTransferTypeCode transferTypeCode, long sourceId) 
    {
        String sourceName = "";
        if(PaymentTransferTypeCode.CUSTOMER_TRANSFER.equals (transferTypeCode)) 
        {
            Customer customer = customerDao.findOne (sourceId);
            if(null != customer) 
            {
                sourceName = customer.getShortName ();
            }
        }
        else if(PaymentTransferTypeCode.PROVIDER_TRANSFER.equals (transferTypeCode)) 
        {
            Provider provider = providerDao.findOne (sourceId);
            if(null != provider) 
            {
                sourceName = provider.getShortName ();
            }
        }
        else if(PaymentTransferTypeCode.ACCOUNTING_IN.equals (transferTypeCode) || PaymentTransferTypeCode.ACCOUNTING_OUT.equals (transferTypeCode)) 
        {
            AccountingType accountingType = accountingTypeDao.findOne (sourceId);
            if(null != accountingType) 
            {
                sourceName = accountingType.getName ();
            }
        }
        else if(PaymentTransferTypeCode.INTERNAL_TRANSFER.equals (transferTypeCode)) 
        {
            PaymentAccount paymentAccount = paymentAccountDao.findOne (sourceId);
            if(null != paymentAccount) 
            {
                sourceName = paymentAccount.getName ();
            }
        }
        return sourceName;
    }

    public void setPaymentWayDao (PaymentWayDao paymentWayDao)
    {
        this.paymentWayDao = paymentWayDao;
    }

    public void setCustomerDao (CustomerDao customerDao)
    {
        this.customerDao = customerDao;
    }

    public void setCustomerContactDao (CustomerContactDao customerContactDao)
    {
        this.customerContactDao = customerContactDao;
    }

    public void setCustomerPaymentDao (CustomerPaymentDao customerPaymentDao)
    {
        this.customerPaymentDao = customerPaymentDao;
    }

    public void setCustomerTypeDao (CustomerTypeDao customerTypeDao)
    {
        this.customerTypeDao = customerTypeDao;
    }

    public void setProviderDao (ProviderDao providerDao)
    {
        this.providerDao = providerDao;
    }

    public void setProviderContactDao (ProviderContactDao providerContactDao)
    {
        this.providerContactDao = providerContactDao;
    }

    public void setProviderPaymentDao (ProviderPaymentDao providerPaymentDao)
    {
        this.providerPaymentDao = providerPaymentDao;
    }

    public void setProviderTypeDao (ProviderTypeDao providerTypeDao)
    {
        this.providerTypeDao = providerTypeDao;
    }

    public void setOrderOutDao (OrderOutDao orderOutDao)
    {
        this.orderOutDao = orderOutDao;
    }

    public void setOrderOutItemDao (OrderOutItemDao orderOutItemDao)
    {
        this.orderOutItemDao = orderOutItemDao;
    }

    public void setOrderInDao (OrderInDao orderInDao)
    {
        this.orderInDao = orderInDao;
    }

    public void setOrderInItemDao (OrderInItemDao orderInItemDao)
    {
        this.orderInItemDao = orderInItemDao;
    }

    public void setUserDao (UserDao userDao)
    {
        this.userDao = userDao;
    }

    public void setRoleDao (RoleDao roleDao)
    {
        this.roleDao = roleDao;
    }

    public void setAuthorityDao (AuthorityDao authorityDao)
    {
        this.authorityDao = authorityDao;
    }

    public void setGoodsDao (GoodsDao goodsDao)
    {
        this.goodsDao = goodsDao;
    }

    public void setGoodsUnitDao (GoodsUnitDao goodsUnitDao)
    {
        this.goodsUnitDao = goodsUnitDao;
    }

    public void setGoodsTypeDao (GoodsTypeDao goodsTypeDao)
    {
        this.goodsTypeDao = goodsTypeDao;
    }

    public void setGoodsDepositoryDao (GoodsDepositoryDao goodsDepositoryDao)
    {
        this.goodsDepositoryDao = goodsDepositoryDao;
    }

    public void setGoodsPictureDao (GoodsPictureDao goodsPictureDao)
    {
        this.goodsPictureDao = goodsPictureDao;
    }

    public void setAccountingTypeDao (AccountingTypeDao accountingTypeDao)
    {
        this.accountingTypeDao = accountingTypeDao;
    }

    public void setAccountingDao (AccountingDao accountingDao)
    {
        this.accountingDao = accountingDao;
    }

    public void setPaymentAccountDao (PaymentAccountDao paymentAccountDao)
    {
        this.paymentAccountDao = paymentAccountDao;
    }

    public void setCustomerLevelDao (CustomerLevelDao customerLevelDao)
    {
        this.customerLevelDao = customerLevelDao;
    }

    public void setDeliveryCompanyDao (DeliveryCompanyDao deliveryCompanyDao)
    {
        this.deliveryCompanyDao = deliveryCompanyDao;
    }

}
