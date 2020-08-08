package com.pinfly.purchasecharge.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pinfly.common.crypto.CryptoException;
import com.pinfly.common.util.EncryptUtil;
import com.pinfly.purchasecharge.core.config.ConfigurationProperties;
import com.pinfly.purchasecharge.core.config.PurchaseChargeProperties;
import com.pinfly.purchasecharge.core.model.AccountingModeCode;
import com.pinfly.purchasecharge.core.model.GoodsIssueStatusCode;
import com.pinfly.purchasecharge.core.model.GoodsStorageTransferData;
import com.pinfly.purchasecharge.core.model.OrderStatusCode;
import com.pinfly.purchasecharge.core.model.OrderTypeCode;
import com.pinfly.purchasecharge.core.model.PaymentTransferTypeCode;
import com.pinfly.purchasecharge.core.model.PaymentTypeCode;
import com.pinfly.purchasecharge.core.model.ProjectStatusCode;
import com.pinfly.purchasecharge.core.model.persistence.Accounting;
import com.pinfly.purchasecharge.core.model.persistence.AccountingType;
import com.pinfly.purchasecharge.core.model.persistence.DeliveryCompany;
import com.pinfly.purchasecharge.core.model.persistence.PaymentAccount;
import com.pinfly.purchasecharge.core.model.persistence.PaymentTransfer;
import com.pinfly.purchasecharge.core.model.persistence.PaymentWay;
import com.pinfly.purchasecharge.core.model.persistence.Project;
import com.pinfly.purchasecharge.core.model.persistence.goods.Goods;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsDepository;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsIssue;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsPicture;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsSerialNumber;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsStorage;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsStorageCheck;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsStoragePriceRevise;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsStorageTransfer;
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
import com.pinfly.purchasecharge.core.model.persistence.out.OrderDelivery;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOut;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOutItem;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.Authority;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.Role;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.User;
import com.pinfly.purchasecharge.core.util.PurchaseChargeConstants;
import com.pinfly.purchasecharge.core.util.PurchaseChargeUtils;
import com.pinfly.purchasecharge.dal.AccountingDao;
import com.pinfly.purchasecharge.dal.AccountingTypeDao;
import com.pinfly.purchasecharge.dal.DeliveryCompanyDao;
import com.pinfly.purchasecharge.dal.PaymentAccountDao;
import com.pinfly.purchasecharge.dal.PaymentTransferDao;
import com.pinfly.purchasecharge.dal.PaymentWayDao;
import com.pinfly.purchasecharge.dal.ProjectDao;
import com.pinfly.purchasecharge.dal.goods.GoodsDao;
import com.pinfly.purchasecharge.dal.goods.GoodsDepositoryDao;
import com.pinfly.purchasecharge.dal.goods.GoodsIssueDao;
import com.pinfly.purchasecharge.dal.goods.GoodsPictureDao;
import com.pinfly.purchasecharge.dal.goods.GoodsSerialNumberDao;
import com.pinfly.purchasecharge.dal.goods.GoodsStorageCheckDao;
import com.pinfly.purchasecharge.dal.goods.GoodsStorageDao;
import com.pinfly.purchasecharge.dal.goods.GoodsStoragePriceReviseDao;
import com.pinfly.purchasecharge.dal.goods.GoodsStorageTransferDao;
import com.pinfly.purchasecharge.dal.goods.GoodsTypeDao;
import com.pinfly.purchasecharge.dal.goods.GoodsUnitDao;
import com.pinfly.purchasecharge.dal.in.OrderInDao;
import com.pinfly.purchasecharge.dal.in.OrderInItemDao;
import com.pinfly.purchasecharge.dal.in.ProviderContactDao;
import com.pinfly.purchasecharge.dal.in.ProviderDao;
import com.pinfly.purchasecharge.dal.in.ProviderPaymentDao;
import com.pinfly.purchasecharge.dal.in.ProviderPaymentRecordDao;
import com.pinfly.purchasecharge.dal.in.ProviderTypeDao;
import com.pinfly.purchasecharge.dal.out.CustomerContactDao;
import com.pinfly.purchasecharge.dal.out.CustomerDao;
import com.pinfly.purchasecharge.dal.out.CustomerLevelDao;
import com.pinfly.purchasecharge.dal.out.CustomerPaymentDao;
import com.pinfly.purchasecharge.dal.out.CustomerPaymentRecordDao;
import com.pinfly.purchasecharge.dal.out.CustomerTypeDao;
import com.pinfly.purchasecharge.dal.out.OrderDeliveryDao;
import com.pinfly.purchasecharge.dal.out.OrderOutDao;
import com.pinfly.purchasecharge.dal.out.OrderOutItemDao;
import com.pinfly.purchasecharge.dal.usersecurity.AuthorityDao;
import com.pinfly.purchasecharge.dal.usersecurity.RoleDao;
import com.pinfly.purchasecharge.dal.usersecurity.UserDao;
import com.pinfly.purchasecharge.service.PersistenceService;
import com.pinfly.purchasecharge.service.QueryService;
import com.pinfly.purchasecharge.service.exception.PcServiceException;
import com.pinfly.purchasecharge.service.utils.DataCache;
import com.pinfly.purchasecharge.service.utils.PinYinUtil;

public class PersistenceServiceImpl implements PersistenceService
{
    private static final Logger logger = Logger.getLogger (PersistenceServiceImpl.class);

    private CustomerDao customerDao;
    private CustomerContactDao customerContactDao;
    private CustomerPaymentDao customerPaymentDao;
    private CustomerPaymentRecordDao customerPaymentRecordDao;
    private CustomerTypeDao customerTypeDao;
    private CustomerLevelDao customerLevelDao;

    private ProviderDao providerDao;
    private ProviderContactDao providerContactDao;
    private ProviderPaymentDao providerPaymentDao;
    private ProviderPaymentRecordDao providerPaymentRecordDao;
    private ProviderTypeDao providerTypeDao;

    private OrderOutDao orderOutDao;
    private OrderOutItemDao orderOutItemDao;
    private OrderInDao orderInDao;
    private OrderInItemDao orderInItemDao;
    private OrderDeliveryDao orderDeliveryDao;
    private DeliveryCompanyDao deliveryCompanyDao;

    private UserDao userDao;
    private RoleDao roleDao;
    private AuthorityDao authorityDao;

    private GoodsDao goodsDao;
    private GoodsUnitDao goodsUnitDao;
    private GoodsTypeDao goodsTypeDao;
    private GoodsDepositoryDao goodsDepositoryDao;
    private GoodsStorageDao goodsStorageDao;
    private GoodsPictureDao goodsPictureDao;
    private GoodsIssueDao goodsIssueDao;
    private GoodsSerialNumberDao goodsSerialNumberDao;
    private GoodsStorageTransferDao goodsStorageTransferDao;
    private GoodsStorageCheckDao goodsStorageCheckDao;
    private GoodsStoragePriceReviseDao goodsStoragePriceReviseDao;

    private AccountingTypeDao accountingTypeDao;
    private AccountingDao accountingDao;
    private PaymentAccountDao paymentAccountDao;
    private PaymentTransferDao paymentTransferDao;
    private PaymentWayDao paymentWayDao;
    private ProjectDao projectDao;

    private QueryService queryService;
    
    private static int goodsShortCodeIndex = 1;
    private static int customerShortCodeIndex = 1;
    private static int providerShortCodeIndex = 1;

    @Override
    @Transactional
    public Customer addCustomer (Customer customer) throws PcServiceException
    {
        if (null != customer)
        {
            if (StringUtils.isNotBlank (customer.getShortCode ()))
            {
                customer.setShortCode (customer.getShortCode ().toLowerCase ());
            }
            else 
            {
                String shortCode = PinYinUtil.getFirstSpell (customer.getShortName (), false);
                if(null != queryService.getCustomerByShortCode (shortCode)) 
                {
                    shortCode += (customerShortCodeIndex++);
                }
                customer.setShortCode (shortCode);
            }
            Customer dbCustomer = customerDao.save (customer);
            if (dbCustomer.getUnpayMoney () > 0)
            {
                CustomerPayment customerPayment = new CustomerPayment ();
                customerPayment.setCustomer (dbCustomer);
                customerPayment.setTypeCode (PaymentTypeCode.INITIAL_BALANCE);
                customerPayment.setAddUnPaid (0);
                customerPayment.setComment (PurchaseChargeProperties.getInstance ()
                                                                    .getConfig (ConfigurationProperties.PC_INITIAL_BALANCE_COMMENT.getName (),
                                                                                ConfigurationProperties.PC_INITIAL_BALANCE_COMMENT.getDefaultValue ()));
                customerPayment.setRemainUnPaid (dbCustomer.getUnpayMoney ());
                customerPayment.setUserCreatedBy (dbCustomer.getUserSigned ());
                addCustomerPayment (customerPayment);
            }
            return dbCustomer;
        }
        return null;
    }

    @Override
    @Transactional
    public Customer updateCustomer (Customer customer) throws PcServiceException
    {
        if (null != customer)
        {
            if (StringUtils.isNotBlank (customer.getShortCode ()))
            {
                customer.setShortCode (customer.getShortCode ().toLowerCase ());
            }
            else 
            {
                String shortCode = PinYinUtil.getFirstSpell (customer.getShortName (), false);
                if(null != queryService.getCustomerByShortCode (shortCode)) 
                {
                    shortCode += (customerShortCodeIndex++);
                }
                customer.setShortCode (shortCode);
            }
            List <CustomerContact> contactList = customerContactDao.findByCustomer (customer.getId ());
            if (CollectionUtils.isNotEmpty (contactList))
            {
                customerContactDao.delete (contactList);
            }
            return customerDao.save (customer);
        }
        return null;
    }

    private void deleteCustomer (Customer customer) throws PcServiceException
    {
        if (null != customer)
        {
            String customerName = StringUtils.isNotBlank (customer.getShortName ()) ? customer.getShortName ()
                                                                                   : customerDao.findOne (customer.getId ())
                                                                                                .getShortName ();
            if (CollectionUtils.isNotEmpty (orderOutDao.findByCustomerId (customer.getId ())))
            {
                throw new PcServiceException (
                                              PurchaseChargeProperties.getInstance ()
                                                                      .getConfigFormatted ("pc.customerCannotBeDeleted",
                                                                                           customerName));
            }
            else
            {
                try
                {
                    customerDao.delete (customer);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                }
            }
        }
    }

    @Override
    public void deleteCustomer (List <Customer> customers) throws PcServiceException
    {
        if (CollectionUtils.isNotEmpty (customers))
        {
            for (Customer customer : customers)
            {
                deleteCustomer (customer);
            }
        }
    }

    @Override
    public CustomerContact addCustomerContact (CustomerContact contact) throws PcServiceException
    {
        return customerContactDao.save (contact);
    }

    @Override
    public CustomerContact updateCustomerContact (CustomerContact contact) throws PcServiceException
    {
        return customerContactDao.save (contact);
    }

    @Override
    public void deleteCustomerContact (List <CustomerContact> contacts) throws PcServiceException
    {
        customerContactDao.delete (contacts);
    }

    @Override
    public List <User> addUsers (List <User> users) throws PcServiceException
    {
        List <User> userList = new ArrayList <User> ();
        if (CollectionUtils.isNotEmpty (users))
        {
            for (User user : users)
            {
                try
                {
                    if (StringUtils.isBlank (user.getPwd ()))
                    {
                        if (PurchaseChargeProperties.getPasswordEncryptFlag ())
                        {
                            user.setPwd (EncryptUtil.encryptWithCryptTool (PurchaseChargeProperties.getDefaultPassword ()));
                        }
                    }
                    else
                    {
                        if (PurchaseChargeProperties.getPasswordEncryptFlag ())
                        {
                            user.setPwd (EncryptUtil.encryptWithCryptTool (user.getPwd ()));
                        }
                    }
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                }
                userList.add (user);
            }
        }
        return (List <User>) userDao.save (userList);
    }

    @Override
    public User updateUser (User user) throws PcServiceException
    {
        User dbUser = userDao.findOne (user.getId ());
        if (null != user && null != dbUser)
        {
            String oldUserId = dbUser.getUserId ();
            if (PurchaseChargeProperties.getDefaultUser ().equals (oldUserId) && !oldUserId.equals (user.getUserId ()))
            {
                throw new PcServiceException (
                                              PurchaseChargeProperties.getInstance ()
                                                                      .getConfigFormatted ("pc.systemUserIdCannotBeUpdated",
                                                                                           oldUserId));
            }
        }
        return userDao.save (user);
    }

    @Override
    public void deleteUser (List <User> users) throws PcServiceException
    {
        if (CollectionUtils.isNotEmpty (users))
        {
            List <User> deletableUsers = new ArrayList <User> ();
            List <String> notDeletableUsers = new ArrayList <String> ();
            for (User user : users)
            {
                String userId = StringUtils.isNotBlank (user.getUserId ()) ? user.getUserId ()
                                                                          : userDao.findOne (user.getId ())
                                                                                   .getUserId ();
                if (PurchaseChargeProperties.getDefaultUser ().equals (userId))
                {
                    notDeletableUsers.add (userId);
                }
                else
                {
                    deletableUsers.add (user);
                }
            }
            if (deletableUsers.size () == 0 || deletableUsers.size () < users.size ())
            {
                throw new PcServiceException (
                                              PurchaseChargeProperties.getInstance ()
                                                                      .getConfigFormatted ("pc.systemUserCannotBeDeleted",
                                                                                           notDeletableUsers.toString ()));
            }
            userDao.delete (deletableUsers);
        }
    }

    @Override
    @Transactional
    public Goods addGoods (Goods goods) throws PcServiceException
    {
        if (null != goods)
        {
            if (null == goods.getLastUpdated ())
            {
                goods.setLastUpdated (new Date ());
            }
            if (StringUtils.isNotBlank (goods.getShortCode ()))
            {
                goods.setShortCode (goods.getShortCode ().toLowerCase ());
            }
            else 
            {
                String shortCode = PinYinUtil.getFirstSpell (goods.getName (), false);
                if(null != queryService.getGoodsByShortCode (shortCode)) 
                {
                    shortCode += (goodsShortCodeIndex++);
                }
                goods.setShortCode (shortCode);
            }
            goods.setAveragePrice (calculateGoodsAveragePrice (goods.getStorages ()));

            Goods persistGoods = goodsDao.save (goods);
            if (null != persistGoods)
            {
                //DataCache.refreshDataToCache (Goods.class.getName (), goodsDao);
                List <GoodsStorage> goodsStorages = persistGoods.getStorages ();
                if (!CollectionUtils.isEmpty (goodsStorages))
                {
                    List <GoodsStorageCheck> storageChecks = new ArrayList <GoodsStorageCheck> ();
                    for (GoodsStorage storage : goodsStorages)
                    {
                        GoodsStorageCheck storageCheck = new GoodsStorageCheck ();
                        storageCheck.setBid (PurchaseChargeUtils.generateStorageProfitBid ());
                        storageCheck.setGoods (persistGoods);
                        storageCheck.setDepository (storage.getDepository ());
                        storageCheck.setDateCreated (persistGoods.getLastUpdated ());
                        storageCheck.setAmountBeforeCheck (0);
                        storageCheck.setAmountAfterCheck (storage.getAmount ());
                        storageChecks.add (storageCheck);
                    } 
                    goodsStorageCheckDao.save (storageChecks);
                }
                return persistGoods;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public Goods updateGoods (Goods goods) throws PcServiceException
    {
        if (null != goods)
        {
            if (null == goods.getLastUpdated ())
            {
                goods.setLastUpdated (new Date ());
            }
            if (StringUtils.isNotBlank (goods.getShortCode ()))
            {
                goods.setShortCode (goods.getShortCode ().toLowerCase ());
            }
            else 
            {
                String shortCode = PinYinUtil.getFirstSpell (goods.getName (), false);
                if(null != queryService.getGoodsByShortCode (shortCode)) 
                {
                    shortCode += (goodsShortCodeIndex++);
                }
                goods.setShortCode (shortCode);
            }
            if(CollectionUtils.isNotEmpty (goods.getStorages ())) 
            {
                goods.setAveragePrice (calculateGoodsAveragePrice (goods.getStorages ()));
            }
            else 
            {
                goods.setAveragePrice (queryService.getGoods (goods.getId ()).getAveragePrice ());
            }

            Goods persistGoods = goodsDao.save (goods);
            if (null != persistGoods)
            {
                //DataCache.refreshDataToCache (Goods.class.getName (), goodsDao);
                List <GoodsStorage> goodsStorages = persistGoods.getStorages ();
                if (CollectionUtils.isNotEmpty (goods.getStorages ()) && CollectionUtils.isNotEmpty (goodsStorages))
                {
                    List <GoodsStorageCheck> storageChecks = new ArrayList <GoodsStorageCheck> ();
                    for (GoodsStorage storage : goodsStorages)
                    {
                        GoodsStorageCheck storageCheck = new GoodsStorageCheck ();
                        storageCheck.setBid (PurchaseChargeUtils.generateStorageProfitBid ());
                        storageCheck.setGoods (persistGoods);
                        storageCheck.setDepository (storage.getDepository ());
                        storageCheck.setDateCreated (new Date ());
                        storageCheck.setAmountBeforeCheck (0);
                        storageCheck.setAmountAfterCheck (storage.getAmount ());
                        storageChecks.add (storageCheck);
                    } 
                    goodsStorageCheckDao.save (storageChecks);
                }
                return persistGoods;
            }
        }
        return null;
    }
    
    private float calculateGoodsAveragePrice (List <GoodsStorage> goodsStorages)
    {
        float goodsAveragePrice = 0;
        if (CollectionUtils.isNotEmpty (goodsStorages))
        {
            long amount = 0;
            float worth = 0;
            for (GoodsStorage goodsStorage : goodsStorages)
            {
                amount += goodsStorage.getAmount ();
                worth += goodsStorage.getWorth ();
            }
            if(amount != 0) 
            {
                goodsAveragePrice = worth / amount;
            }
        }
        return goodsAveragePrice;
    }

    @Override
    public void updateGoodsAveragePrice (long goodsId) throws PcServiceException
    {
        float averagePrice = calculateGoodsAveragePrice (goodsStorageDao.findByGoods (goodsId));
        if(0 == averagePrice) 
        {
            Goods goods = queryService.getGoods (goodsId);
            if(goods.getAveragePrice () != 0) 
            {
                averagePrice = goods.getAveragePrice ();
            }
            else 
            {
                averagePrice = goods.getImportPrice ();
            }
        }
        goodsDao.updateAveragePrice (goodsId, averagePrice);
    }

    private void deleteGoods (Goods goods) throws PcServiceException
    {
        if (null != goods)
        {
            if (CollectionUtils.isNotEmpty (orderInItemDao.findByGoods (goods.getId ()))
                || CollectionUtils.isNotEmpty (orderOutItemDao.findByGoods (goods.getId ())))
            {
                String goodsName = StringUtils.isNotBlank (goods.getName ()) ? goods.getName ()
                                                                            : queryService.getGoods (goods.getId ())
                                                                                      .getName ();
                throw new PcServiceException (PurchaseChargeProperties.getInstance ()
                                                                      .getConfigFormatted ("pc.goodsCannotBeDeleted",
                                                                                           goodsName));
            }
            else
            {
                try
                {
                    goodsDao.delete (goods);
                    if(PurchaseChargeProperties.getAllowCacheGoods ()) 
                    {
                        DataCache.refreshDataToCache (Goods.class.getName (), goodsDao);
                    }
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                }
            }
        }
    }

    @Override
    public void deleteGoods (List <Goods> goodses) throws PcServiceException
    {
        if (CollectionUtils.isNotEmpty (goodses))
        {
            for (Goods goods : goodses)
            {
                deleteGoods (goods);
            }
        }
    }

    @Override
    public CustomerType addCustomerType (CustomerType customerType) throws PcServiceException
    {
        if (null != customerType)
        {
            customerType = customerTypeDao.save (customerType);
            DataCache.refreshDataToCache (CustomerType.class.getName (), customerTypeDao);
            return customerType;
        }
        return null;
    }

    @Override
    public CustomerType updateCustomerType (CustomerType customerType) throws PcServiceException
    {
        return addCustomerType (customerType);
    }

    @Override
    public void deleteCustomerType (List <CustomerType> customerTypes) throws PcServiceException
    {
        if (CollectionUtils.isNotEmpty (customerTypes))
        {
            List <CustomerType> deletableCustomerTypes = new ArrayList <CustomerType> ();
            List <String> notDeletableCustomerTypes = new ArrayList <String> ();
            for (CustomerType ct : customerTypes)
            {
                if (CollectionUtils.isNotEmpty (customerDao.findByType (ct.getId ())))
                {
                    notDeletableCustomerTypes.add (queryService.getCustomerType (ct.getId ()).getName ());
                }
                else
                {
                    deletableCustomerTypes.add (ct);
                }
            }
            if (0 == deletableCustomerTypes.size () || deletableCustomerTypes.size () < customerTypes.size ())
            {
                throw new PcServiceException (
                                              PurchaseChargeProperties.getInstance ()
                                                                      .getConfigFormatted ("pc.customerTypeCannotBeDeleted",
                                                                                           notDeletableCustomerTypes.toString ()));
            }
            customerTypeDao.delete (deletableCustomerTypes);
            DataCache.refreshDataToCache (CustomerType.class.getName (), customerTypeDao);
        }
    }

    @Override
    public CustomerLevel addCustomerLevel (CustomerLevel customerLevel) throws PcServiceException
    {
        if (null != customerLevel)
        {
            customerLevel = customerLevelDao.save (customerLevel);
            DataCache.refreshDataToCache (CustomerLevel.class.getName (), customerLevelDao);
            return customerLevel;
        }
        return null;
    }

    @Override
    public List <CustomerLevel> updateCustomerLevel (List <CustomerLevel> customerLevels) throws PcServiceException
    {
        /*
         * CustomerLevel dbLevel = customerLevelDao.findOne (customerLevel.getId
         * ()); if(null != dbLevel) { try { customerLevel =
         * customerLevelDao.save (customerLevel); if(customerLevel.getSaleMoney
         * () != dbLevel.getSaleMoney () || customerLevel.getProfitMoney () !=
         * dbLevel.getProfitMoney ()) { List <Customer> customers =
         * customerDao.findByLevel (customerLevel.getId ());
         * if(CollectionUtils.isNotEmpty (customers)) { for(Customer customer :
         * customers) { OrderSearchForm searchForm = new OrderSearchForm ();
         * searchForm.setCustomerId (customer.getId ()); float orderReceivable =
         * orderOutDao.countReceivableBy (searchForm, 0, true); float
         * orderProfit = orderOutDao.countProfitBy (searchForm, 0, true); List
         * <CustomerLevel> allLevels = (List <CustomerLevel>)
         * customerLevelDao.findAll (); for(CustomerLevel level : allLevels) {
         * if(orderReceivable >= level.getSaleMoney () && orderProfit >=
         * level.getProfitMoney ()) { customerDao.updateCustomerLevel
         * (customer.getId (), level.getId ()); break; } } } } } return
         * customerLevel; } catch (Exception e) {
         * 
         * } }
         */
        if (CollectionUtils.isNotEmpty (customerLevels))
        {
            customerLevels = (List <CustomerLevel>) customerLevelDao.save (customerLevels);
            DataCache.refreshDataToCache (CustomerLevel.class.getName (), customerLevelDao);
            return customerLevels;
        }
        return null;
    }

    @Override
    public void deleteCustomerLevel (List <CustomerLevel> customerLevels) throws PcServiceException
    {
        if (CollectionUtils.isNotEmpty (customerLevels))
        {
            List <CustomerLevel> deletableCustomerLevels = new ArrayList <CustomerLevel> ();
            List <String> notDeletableCustomerLevels = new ArrayList <String> ();
            for (CustomerLevel ct : customerLevels)
            {
                if (CollectionUtils.isNotEmpty (customerDao.findByLevel (ct.getId ())))
                {
                    notDeletableCustomerLevels.add (queryService.getCustomerLevel (ct.getId ()).getName ());
                }
                else
                {
                    deletableCustomerLevels.add (ct);
                }
            }
            if (0 == deletableCustomerLevels.size () || deletableCustomerLevels.size () < customerLevels.size ())
            {
                throw new PcServiceException (
                                              PurchaseChargeProperties.getInstance ()
                                                                      .getConfigFormatted ("pc.customerLevelCannotBeDeleted",
                                                                                           notDeletableCustomerLevels.toString ()));
            }
            customerLevelDao.delete (deletableCustomerLevels);
            DataCache.refreshDataToCache (CustomerLevel.class.getName (), customerLevelDao);
        }
    }

    @Override
    @Transactional
    public Provider addProvider (Provider provider) throws PcServiceException
    {
        if (null != provider)
        {
            if (StringUtils.isNotBlank (provider.getShortCode ()))
            {
                provider.setShortCode (provider.getShortCode ().toLowerCase ());
            }
            else 
            {
                String shortCode = PinYinUtil.getFirstSpell (provider.getShortName (), false);
                if(null != queryService.getProviderByShortCode (shortCode)) 
                {
                    shortCode += (providerShortCodeIndex++);
                }
                provider.setShortCode (shortCode);
            }
            Provider dbProvider = providerDao.save (provider);
            if (dbProvider.getUnpayMoney () > 0)
            {
                ProviderPayment providerPayment = new ProviderPayment ();
                providerPayment.setProvider (dbProvider);
                providerPayment.setTypeCode (PaymentTypeCode.INITIAL_BALANCE);
                providerPayment.setAddUnPaid (0);
                providerPayment.setComment (PurchaseChargeProperties.getInstance ()
                                                                    .getConfig (ConfigurationProperties.PC_INITIAL_BALANCE_COMMENT.getName (),
                                                                                ConfigurationProperties.PC_INITIAL_BALANCE_COMMENT.getDefaultValue ()));
                providerPayment.setRemainUnPaid (dbProvider.getUnpayMoney ());
                providerPayment.setUserCreatedBy (provider.getUserSigned ());
                addProviderPayment (providerPayment);
            }
            return dbProvider;
        }
        return null;
    }

    @Override
    @Transactional
    public Provider updateProvider (Provider provider) throws PcServiceException
    {
        if (null != provider)
        {
            Provider dbProvider = providerDao.findOne (provider.getId ());
            if (null != dbProvider)
            {
                if (PurchaseChargeProperties.getDefaultProvider ().equals (dbProvider.getShortName ()))
                {
                    throw new PcServiceException (
                                                  PurchaseChargeProperties.getInstance ()
                                                                          .getConfigFormatted ("pc.systemProviderCannotBeUpdated",
                                                                                               dbProvider.getShortName ()));
                }
                if (StringUtils.isNotBlank (provider.getShortCode ()))
                {
                    provider.setShortCode (provider.getShortCode ().toLowerCase ());
                }
                else 
                {
                    String shortCode = PinYinUtil.getFirstSpell (provider.getShortName (), false);
                    if(null != queryService.getProviderByShortCode (shortCode)) 
                    {
                        shortCode += (providerShortCodeIndex++);
                    }
                    provider.setShortCode (shortCode);
                }
                List <ProviderContact> contactList = providerContactDao.findByProvider (provider.getId ());
                if (CollectionUtils.isNotEmpty (contactList))
                {
                    providerContactDao.delete (contactList);
                }
                return providerDao.save (provider);
            }
        }
        return null;
    }

    private void deleteProvider (Provider provider) throws PcServiceException
    {
        if (null != provider)
        {
            String providerName = StringUtils.isNotBlank (provider.getShortName ()) ? provider.getShortName ()
                                                                                   : providerDao.findOne (provider.getId ())
                                                                                                .getShortName ();
            if (PurchaseChargeProperties.getDefaultProvider ().equals (providerName))
            {
                throw new PcServiceException (
                                              PurchaseChargeProperties.getInstance ()
                                                                      .getConfigFormatted ("pc.systemProviderCannotBeDeleted",
                                                                                           providerName));
            }
            else if (CollectionUtils.isNotEmpty (orderInDao.findByProviderId (provider.getId ())))
            {
                throw new PcServiceException (
                                              PurchaseChargeProperties.getInstance ()
                                                                      .getConfigFormatted ("pc.providerCannotBeDeleted",
                                                                                           providerName));
            }
            try
            {
                providerDao.delete (provider);
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage (), e);
            }
        }
    }

    @Override
    public void deleteProvider (List <Provider> providers) throws PcServiceException
    {
        if (CollectionUtils.isNotEmpty (providers))
        {
            for (Provider provider : providers)
            {
                deleteProvider (provider);
            }
        }
    }

    @Override
    public ProviderType addProviderType (ProviderType providerType) throws PcServiceException
    {
        if (null != providerType)
        {
            providerType = providerTypeDao.save (providerType);
            DataCache.refreshDataToCache (ProviderType.class.getName (), providerTypeDao);
            return providerType;
        }
        return null;
    }

    @Override
    public ProviderType updateProviderType (ProviderType providerType) throws PcServiceException
    {
        return addProviderType (providerType);
    }

    @Override
    public void deleteProviderType (List <ProviderType> providerTypes) throws PcServiceException
    {
        if (CollectionUtils.isNotEmpty (providerTypes))
        {
            List <ProviderType> deletableProviderTypes = new ArrayList <ProviderType> ();
            List <String> notDeletableProviderTypes = new ArrayList <String> ();
            for (ProviderType pt : providerTypes)
            {
                if (CollectionUtils.isNotEmpty (providerDao.findByType (pt.getId ())))
                {
                    notDeletableProviderTypes.add (queryService.getProviderType (pt.getId ()).getName ());
                }
                else
                {
                    deletableProviderTypes.add (pt);
                }
            }
            if (0 == deletableProviderTypes.size () || deletableProviderTypes.size () < providerTypes.size ())
            {
                throw new PcServiceException (
                                              PurchaseChargeProperties.getInstance ()
                                                                      .getConfigFormatted ("pc.providerTypeCannotBeDeleted",
                                                                                           notDeletableProviderTypes.toString ()));
            }
            providerTypeDao.delete (deletableProviderTypes);
            DataCache.refreshDataToCache (ProviderType.class.getName (), providerTypeDao);
        }
    }

    @Override
    public ProviderContact addProviderContact (ProviderContact contact) throws PcServiceException
    {
        return providerContactDao.save (contact);
    }

    @Override
    public ProviderContact updateProviderContact (ProviderContact contact) throws PcServiceException
    {
        return providerContactDao.save (contact);
    }

    @Override
    public void deleteProviderContact (List <ProviderContact> contacts) throws PcServiceException
    {
        providerContactDao.delete (contacts);
    }

    @Override
    @Transactional (propagation = Propagation.NESTED)
    public ProviderPayment addProviderPayment (ProviderPayment payment) throws PcServiceException
    {
        ProviderPayment providerPayment = null;
        if (null != payment && null != payment.getProvider () && null != payment.getTypeCode ())
        {
            if(StringUtils.isBlank (payment.getBid ())) 
            {
                payment.setBid (PurchaseChargeUtils.generateProviderPaymentBid ());
            }
            if (null == payment.getPaidDate ())
            {
                payment.setPaidDate (new Timestamp (System.currentTimeMillis ()));
            }
            PaymentTypeCode paymentTypeCode = payment.getTypeCode ();
            Provider provider = providerDao.findOne (payment.getProvider ().getId ());
            float allUnPaid = 0;
            if (PaymentTypeCode.INITIAL_BALANCE == paymentTypeCode)
            {
                allUnPaid = provider.getUnpayMoney ();
                payment.setRemainUnPaid (allUnPaid);
            }
            else if (PaymentTypeCode.IN_ORDER == paymentTypeCode)
            {
                allUnPaid = provider.getUnpayMoney () + payment.getAddUnPaid ();
                payment.setRemainUnPaid (allUnPaid);

                List <ProviderPaymentRecord> paymentRecords = payment.getPaymentRecords ();
                if (!CollectionUtils.isEmpty (paymentRecords))
                {
                    float sumPaid = 0;
                    for (ProviderPaymentRecord paymentRecord : paymentRecords)
                    {
                        sumPaid += paymentRecord.getPaid ();

                        // update account remain money
                        if (null != paymentRecord.getPaymentAccount ())
                        {
                            long accountId = paymentRecord.getPaymentAccount ().getId ();
                            PaymentAccount paymentAccount = queryService.getPaymentAccount (accountId);
                            if (null != paymentAccount)
                            {
                                PaymentTransfer paymentTransfer = new PaymentTransfer ();
                                float money = paymentRecord.getPaid ();
                                if (money > 0)
                                {
                                    paymentTransfer.setOutMoney (money);
                                }
                                else
                                {
                                    paymentTransfer.setInMoney (-money);
                                }
                                paymentTransfer.setTargetAccount (paymentAccount);
                                paymentTransfer.setSource (payment.getProvider ().getId () + "");
                                paymentTransfer.setTransferTypeCode (PaymentTransferTypeCode.PROVIDER_TRANSFER);
                                paymentTransfer.setUserCreatedBy (payment.getUserCreatedBy ());
                                paymentTransfer.setDateCreated (payment.getPaidDate ());
                                addPaymentTransfer (paymentTransfer);
                            }
                        }
                    }

                    allUnPaid = allUnPaid - sumPaid;
                    payment.setRemainUnPaid (allUnPaid);
                }
            }
            else if (PaymentTypeCode.IN_ORDER_RETURN == paymentTypeCode)
            {
                if (payment.getAddUnPaid () > 0)
                {
                    allUnPaid = provider.getUnpayMoney () - payment.getAddUnPaid ();
                    payment.setAddUnPaid (-payment.getAddUnPaid ());
                }
                else
                {
                    allUnPaid = provider.getUnpayMoney () + payment.getAddUnPaid ();
                }
                payment.setRemainUnPaid (allUnPaid);

                List <ProviderPaymentRecord> paymentRecords = payment.getPaymentRecords ();
                if (!CollectionUtils.isEmpty (paymentRecords))
                {
                    float sumPaid = 0;
                    for (ProviderPaymentRecord paymentRecord : paymentRecords)
                    {
                        sumPaid += paymentRecord.getPaid ();

                        // update account remain money
                        if (null != paymentRecord.getPaymentAccount ())
                        {
                            long accountId = paymentRecord.getPaymentAccount ().getId ();
                            PaymentAccount paymentAccount = queryService.getPaymentAccount (accountId);
                            if (null != paymentAccount)
                            {
                                PaymentTransfer paymentTransfer = new PaymentTransfer ();
                                float money = paymentRecord.getPaid ();
                                if (money > 0)
                                {
                                    paymentTransfer.setInMoney (money);
                                }
                                else
                                {
                                    paymentTransfer.setOutMoney (-money);
                                }
                                paymentTransfer.setTargetAccount (paymentAccount);
                                paymentTransfer.setSource (payment.getProvider ().getId () + "");
                                paymentTransfer.setTransferTypeCode (PaymentTransferTypeCode.PROVIDER_TRANSFER);
                                paymentTransfer.setUserCreatedBy (payment.getUserCreatedBy ());
                                paymentTransfer.setDateCreated (payment.getPaidDate ());
                                addPaymentTransfer (paymentTransfer);
                            }
                        }
                    }

                    allUnPaid = allUnPaid + sumPaid;
                    payment.setRemainUnPaid (allUnPaid);
                }
            }
            else if (PaymentTypeCode.IN_PAID_MONEY == paymentTypeCode)
            {
                payment.setAddUnPaid (0);
                List <ProviderPaymentRecord> paymentRecords = payment.getPaymentRecords ();
                if (!CollectionUtils.isEmpty (paymentRecords))
                {
                    float sumPaid = 0;
                    for (ProviderPaymentRecord paymentRecord : paymentRecords)
                    {
                        sumPaid += paymentRecord.getPaid ();

                        // update account remain money
                        if (null != paymentRecord.getPaymentAccount ())
                        {
                            long accountId = paymentRecord.getPaymentAccount ().getId ();
                            PaymentAccount paymentAccount = queryService.getPaymentAccount (accountId);
                            if (null != paymentAccount)
                            {
                                PaymentTransfer paymentTransfer = new PaymentTransfer ();
                                float money = paymentRecord.getPaid ();
                                if (money > 0)
                                {
                                    paymentTransfer.setOutMoney (money);
                                }
                                else
                                {
                                    paymentTransfer.setInMoney (-money);
                                }
                                // paymentAccount.setRemainMoney
                                // (paymentAccount.getRemainMoney () +
                                // money);
                                // paymentAccount =
                                // paymentAccountDao.save
                                // (paymentAccount);
                                paymentTransfer.setTargetAccount (paymentAccount);
                                paymentTransfer.setSource (payment.getProvider ().getId () + "");
                                paymentTransfer.setTransferTypeCode (PaymentTransferTypeCode.PROVIDER_TRANSFER);
                                paymentTransfer.setUserCreatedBy (payment.getUserCreatedBy ());
                                paymentTransfer.setDateCreated (payment.getPaidDate ());
                                addPaymentTransfer (paymentTransfer);
                            }
                        }
                    }

                    if (sumPaid == 0)
                    {
                        payment = null;
                    }
                    else
                    {
                        allUnPaid = provider.getUnpayMoney () - sumPaid;
                        payment.setRemainUnPaid (allUnPaid);
                    }
                }
                else
                {
                    payment = null;
                }
            }

            try
            {
                if (null != payment)
                {
                    providerPayment = providerPaymentDao.save (payment);
                }
            }
            catch (Exception e)
            {
                logger.warn ("Save provider payment failed", e);
                throw new PcServiceException (e);
            }

            try
            {
                if (null != providerPayment)
                {
                    providerDao.updateProviderCope (provider.getId (), allUnPaid);
                }
            }
            catch (Exception e)
            {
                logger.warn ("Update Provider Cope failed", e);
                throw new PcServiceException (e);
            }
        }
        return providerPayment;
    }

    @Override
    public ProviderPayment updateProviderPayment (ProviderPayment payment) throws PcServiceException
    {
        return providerPaymentDao.save (payment);
    }

    @Override
    public void deleteProviderPayment (ProviderPayment payment) throws PcServiceException
    {
        providerPaymentDao.delete (payment);
    }

    @Override
    public void deleteProviderPayment (List <ProviderPayment> payments) throws PcServiceException
    {
        providerPaymentDao.delete (payments);
    }

    @Override
    public List <Role> addRoles (List <Role> roles) throws PcServiceException
    {
        return (List <Role>) roleDao.save (roles);
    }

    @Override
    public Role updateRole (Role role) throws PcServiceException
    {
        if (null != role)
        {
            String roleName = StringUtils.isNotBlank (role.getName ()) ? role.getName ()
                                                                      : roleDao.findOne (role.getId ()).getName ();
            if (PurchaseChargeProperties.getDefaultRole ().equals (roleName))
            {
                throw new PcServiceException (
                                              PurchaseChargeProperties.getInstance ()
                                                                      .getConfigFormatted ("pc.systemRoleCannotBeUpdated",
                                                                                           roleName));
            }
            return roleDao.save (role);
        }
        return null;
    }

    @Override
    public void deleteRole (List <Role> roles) throws PcServiceException
    {
        if (CollectionUtils.isNotEmpty (roles))
        {
            List <Role> deletableRoles = new ArrayList <Role> ();
            List <String> notDeletableRoles = new ArrayList <String> ();
            for (Role role : roles)
            {
                String roleName = StringUtils.isNotBlank (role.getName ()) ? role.getName ()
                                                                          : roleDao.findOne (role.getId ()).getName ();
                if (PurchaseChargeProperties.getDefaultRole ().equals (roleName))
                {
                    notDeletableRoles.add (roleName);
                }
                else
                {
                    deletableRoles.add (role);
                }
            }
            if (deletableRoles.size () == 0 || deletableRoles.size () < roles.size ())
            {
                throw new PcServiceException (
                                              PurchaseChargeProperties.getInstance ()
                                                                      .getConfigFormatted ("pc.systemRoleCannotBeDeleted",
                                                                                           notDeletableRoles.toString ()));
            }
            roleDao.delete (deletableRoles);
        }
    }

    @Override
    public void updatePassword (String userId, String newPwd) throws PcServiceException
    {
        String password = newPwd;
        if (StringUtils.isNotBlank (userId) && StringUtils.isNotBlank (newPwd))
        {
            if (PurchaseChargeProperties.getPasswordEncryptFlag ())
            {
                try
                {
                    password = EncryptUtil.encryptWithCryptTool (newPwd);
                }
                catch (CryptoException e)
                {
                    logger.warn (e.getMessage (), e);
                }
            }
        }
        userDao.updatePassword (userId, password);
    }

    @Override
    public List <Authority> addAuthorities (List <Authority> authorities) throws PcServiceException
    {
        if (CollectionUtils.isNotEmpty (authorities))
        {
            authorities = (List <Authority>) authorityDao.save (authorities);
            DataCache.refreshDataToCache (Authority.class.getName (), authorityDao);
            return authorities;
        }
        return null;
    }

    @Override
    public Authority updateAuthority (Authority authority) throws PcServiceException
    {
        if (null != authority)
        {
            authority = authorityDao.save (authority);
            DataCache.refreshDataToCache (Authority.class.getName (), authorityDao);
            return authority;
        }
        return null;
    }

    @Override
    public void deleteAuthority (List <Authority> authorities) throws PcServiceException
    {
        authorityDao.delete (authorities);
        DataCache.refreshDataToCache (Authority.class.getName (), authorityDao);
    }

    @Override
    public GoodsType addGoodsType (GoodsType goodsType) throws PcServiceException
    {
        if (null != goodsType)
        {
            goodsType = goodsTypeDao.save (goodsType);
            DataCache.refreshDataToCache (GoodsType.class.getName (), goodsTypeDao);
            return goodsType;
        }
        return null;
    }

    @Override
    public GoodsType updateGoodsType (GoodsType goodsType) throws PcServiceException
    {
        return addGoodsType (goodsType);
    }

    @Override
    public void deleteGoodsType (List <GoodsType> goodsTypes) throws PcServiceException
    {
        if (CollectionUtils.isNotEmpty (goodsTypes))
        {
            List <GoodsType> deletableGoodsTypes = new ArrayList <GoodsType> ();
            List <String> notDeletableGoodsTypes = new ArrayList <String> ();
            for (GoodsType gt : goodsTypes)
            {
                if (0 < goodsDao.countByType (gt.getId ()))
                {
                    notDeletableGoodsTypes.add (queryService.getGoodsType (gt.getId ()).getName ());
                }
                else
                {
                    deletableGoodsTypes.add (gt);
                }
            }
            if (0 == deletableGoodsTypes.size () || deletableGoodsTypes.size () < goodsTypes.size ())
            {
                throw new PcServiceException (
                                              PurchaseChargeProperties.getInstance ()
                                                                      .getConfigFormatted ("pc.goodsTypeCannotBeDeleted",
                                                                                           notDeletableGoodsTypes.toString ()));
            }
            goodsTypeDao.delete (deletableGoodsTypes);
            DataCache.refreshDataToCache (GoodsType.class.getName (), goodsTypeDao);
        }
    }

    @Override
    public GoodsUnit addGoodsUnit (GoodsUnit goodsUnit) throws PcServiceException
    {
        if (null != goodsUnit)
        {
            goodsUnit = goodsUnitDao.save (goodsUnit);
            DataCache.refreshDataToCache (GoodsUnit.class.getName (), goodsUnitDao);
            return goodsUnit;
        }
        return null;
    }

    @Override
    public GoodsUnit updateGoodsUnit (GoodsUnit goodsUnit) throws PcServiceException
    {
        return addGoodsUnit (goodsUnit);
    }

    @Override
    public void deleteGoodsUnit (List <GoodsUnit> goodsUnits) throws PcServiceException
    {
        if (CollectionUtils.isNotEmpty (goodsUnits))
        {
            List <GoodsUnit> deletableGoodsUnits = new ArrayList <GoodsUnit> ();
            List <String> notDeletableGoodsUnits = new ArrayList <String> ();
            for (GoodsUnit gu : goodsUnits)
            {
                if (0 < goodsDao.countByUnit (gu.getId ()))
                {
                    notDeletableGoodsUnits.add (queryService.getGoodsUnit (gu.getId ()).getName ());
                }
                else
                {
                    deletableGoodsUnits.add (gu);
                }
            }
            if (0 == deletableGoodsUnits.size () || deletableGoodsUnits.size () < goodsUnits.size ())
            {
                throw new PcServiceException (
                                              PurchaseChargeProperties.getInstance ()
                                                                      .getConfigFormatted ("pc.goodsUnitCannotBeDeleted",
                                                                                           notDeletableGoodsUnits.toString ()));
            }
            goodsUnitDao.delete (deletableGoodsUnits);
            DataCache.refreshDataToCache (GoodsUnit.class.getName (), goodsUnitDao);
        }
    }

    @Override
    public GoodsDepository addGoodsDepository (GoodsDepository goodsDepository) throws PcServiceException
    {
        if (null != goodsDepository)
        {
            goodsDepository = goodsDepositoryDao.save (goodsDepository);
            DataCache.refreshDataToCache (GoodsDepository.class.getName (), goodsDepositoryDao);
            return goodsDepository;
        }
        return null;
    }

    @Override
    public GoodsDepository updateGoodsDepository (GoodsDepository goodsDepository) throws PcServiceException
    {
        if (null != goodsDepository)
        {
            if (PurchaseChargeProperties.getDefaultDepository ().equals (goodsDepository.getName ()))
            {
                throw new PcServiceException (
                                              PurchaseChargeProperties.getInstance ()
                                                                      .getConfigFormatted ("pc.systemDepositoryCannotBeUpdated",
                                                                                           goodsDepository.getName ()));
            }
            return addGoodsDepository (goodsDepository);
        }
        return null;
    }

    @Override
    public void deleteGoodsDepository (List <GoodsDepository> goodsDepositorys) throws PcServiceException
    {
        if (CollectionUtils.isNotEmpty (goodsDepositorys))
        {
            List <GoodsDepository> deletableGoodsDepositories = new ArrayList <GoodsDepository> ();
            List <String> notDeletableGoodsDepositories = new ArrayList <String> ();
            for (GoodsDepository gd : goodsDepositorys)
            {
                GoodsDepository dbDepository = queryService.getGoodsDepository (gd.getId ());
                if (PurchaseChargeProperties.getDefaultDepository ().equals (dbDepository.getName ()))
                {
                    throw new PcServiceException (
                                                  PurchaseChargeProperties.getInstance ()
                                                                          .getConfigFormatted ("pc.systemDepositoryCannotBeDeleted",
                                                                                               dbDepository.getName ()));
                }
                if (CollectionUtils.isEmpty (goodsStorageDao.findByGoodsDepository (gd.getId ())))
                {
                    deletableGoodsDepositories.add (gd);
                }
                else
                {
                    notDeletableGoodsDepositories.add (dbDepository.getName ());
                }
            }
            if (deletableGoodsDepositories.size () == 0
                || deletableGoodsDepositories.size () < goodsDepositorys.size ())
            {
                throw new PcServiceException (
                                              PurchaseChargeProperties.getInstance ()
                                                                      .getConfigFormatted ("pc.goodsDepositoryCannotBeDeleted",
                                                                                           notDeletableGoodsDepositories.toString ()));
            }
            goodsDepositoryDao.delete (deletableGoodsDepositories);
            DataCache.refreshDataToCache (GoodsDepository.class.getName (), goodsDepositoryDao);
        }
    }

    @Override
    public GoodsPicture addGoodsPicture (GoodsPicture goodsPicture) throws PcServiceException
    {
        return goodsPictureDao.save (goodsPicture);
    }

    @Override
    public GoodsPicture updateGoodsPicture (GoodsPicture goodsPicture) throws PcServiceException
    {
        return goodsPictureDao.save (goodsPicture);
    }

    @Override
    public void deleteGoodsPicture (GoodsPicture goodsPicture) throws PcServiceException
    {
        goodsPictureDao.delete (goodsPicture);
    }

    @Override
    public void deleteGoodsPicture (List <GoodsPicture> goodsPictures) throws PcServiceException
    {
        goodsPictureDao.delete (goodsPictures);
    }

    @Override
    public GoodsIssue addGoodsIssue (GoodsIssue goodsIssue) throws PcServiceException
    {
        return goodsIssueDao.save (goodsIssue);
    }

    @Override
    public GoodsIssue updateGoodsIssue (GoodsIssue goodsIssue) throws PcServiceException
    {
        return goodsIssueDao.save (goodsIssue);
    }

    @Override
    public void deleteGoodsIssue (List <GoodsIssue> goodsIssues) throws PcServiceException
    {
        if (CollectionUtils.isNotEmpty (goodsIssues))
        {
            List <GoodsIssue> deletableGoodsIssues = new ArrayList <GoodsIssue> ();
            List <String> notDeletableGoodsIssues = new ArrayList <String> ();
            for (GoodsIssue goodsIssue : goodsIssues)
            {
                if (GoodsIssueStatusCode.NEW.equals (goodsIssue.getStatusCode ())
                    || GoodsIssueStatusCode.INVALID.equals (goodsIssue.getStatusCode ()))
                {
                    deletableGoodsIssues.add (goodsIssue);
                }
                else
                {
                    notDeletableGoodsIssues.add (goodsIssue.getId () + "");
                }
            }
            if (deletableGoodsIssues.size () == 0 || deletableGoodsIssues.size () < goodsIssues.size ())
            {
                throw new PcServiceException (
                                              PurchaseChargeProperties.getInstance ()
                                                                      .getConfigFormatted ("pc.goodsIssueCannotBeDeleted",
                                                                                           notDeletableGoodsIssues.toString ()));
            }
            goodsIssueDao.delete (deletableGoodsIssues);
        }
    }

    @Override
    public GoodsSerialNumber addGoodsSerialNumber (GoodsSerialNumber goodsSerialNumber) throws PcServiceException
    {
        return goodsSerialNumberDao.save (goodsSerialNumber);
    }

    @Override
    public GoodsSerialNumber updateGoodsSerialNumber (GoodsSerialNumber goodsSerialNumber) throws PcServiceException
    {
        return goodsSerialNumberDao.save (goodsSerialNumber);
    }

    @Override
    public void deleteGoodsSerialNumber (List <GoodsSerialNumber> goodsSerialNumbers) throws PcServiceException
    {
        if (CollectionUtils.isNotEmpty (goodsSerialNumbers))
        {
            List <GoodsSerialNumber> deletableGoodsSerialNumbers = new ArrayList <GoodsSerialNumber> ();
            List <String> notDeletableGoodsSerialNumbers = new ArrayList <String> ();
            for (GoodsSerialNumber gsn : goodsSerialNumbers)
            {
                GoodsSerialNumber tempGoodsSerialNumber = goodsSerialNumberDao.findOne (gsn.getId ());
                String serialNumber = tempGoodsSerialNumber.getSerialNumber ();
                GoodsIssue goodsIssue = goodsIssueDao.findBySerialNumber (serialNumber);
                if (null != goodsIssue)
                {
                    notDeletableGoodsSerialNumbers.add (serialNumber);
                }
                else
                {
                    deletableGoodsSerialNumbers.add (gsn);
                }
            }
            if (0 == deletableGoodsSerialNumbers.size ()
                || deletableGoodsSerialNumbers.size () < goodsSerialNumbers.size ())
            {
                throw new PcServiceException (
                                              PurchaseChargeProperties.getInstance ()
                                                                      .getConfigFormatted ("pc.goodsSerialCannotBeDeleted",
                                                                                           notDeletableGoodsSerialNumbers.toString ()));
            }
            goodsSerialNumberDao.delete (deletableGoodsSerialNumbers);
        }
    }

    @Override
    @Transactional
    public GoodsStorageTransferData addGoodsStorageTransfer (GoodsStorageTransferData goodsStorageTransferData)
                                                                                              throws PcServiceException
    {
        GoodsStorageTransferData storageTransferData = null;
        if (null != goodsStorageTransferData && null != goodsStorageTransferData.getGoods ()
            && null != goodsStorageTransferData.getFromDepository () && null != goodsStorageTransferData.getToDepository ())
        {
            if (goodsStorageTransferData.getFromDepository ().getId () == goodsStorageTransferData.getToDepository ().getId ())
            {
                throw new PcServiceException (PurchaseChargeProperties.getInstance ()
                                                                      .getConfig ("pc.storageFromAndStorageToNotSame"));
            }

            if (0 < goodsStorageTransferData.getTransferAmount ())
            {
                GoodsStorage fromStorageDepo = goodsStorageDao.findByGoodsAndDepository (goodsStorageTransferData.getGoods ()
                                                                                         .getId (),
                                                                                         goodsStorageTransferData.getFromDepository ()
                                                                                         .getId ());
                GoodsStorage toStorageDepo = goodsStorageDao.findByGoodsAndDepository (goodsStorageTransferData.getGoods ()
                                                                                       .getId (),
                                                                                       goodsStorageTransferData.getToDepository ()
                                                                                       .getId ());
                
                if (null != fromStorageDepo)
                {
                    if (goodsStorageTransferData.getTransferAmount () <= fromStorageDepo.getAmount ())
                    {
                        goodsStorageTransferData.setFromDepositoryAmount (fromStorageDepo.getAmount ());
                        long amount = fromStorageDepo.getAmount () - goodsStorageTransferData.getTransferAmount ();
                        fromStorageDepo.setAmount (amount);
                        fromStorageDepo.setWorth (fromStorageDepo.getPrice () * amount);
                        goodsStorageDao.save (fromStorageDepo);
                        
                        GoodsStorageTransfer fromStorageTransfer = new GoodsStorageTransfer ();
                        fromStorageTransfer.setGoods (goodsStorageTransferData.getGoods ());
                        fromStorageTransfer.setDepository (goodsStorageTransferData.getFromDepository ());
                        fromStorageTransfer.setAmountBeforeTransfer (goodsStorageTransferData.getFromDepositoryAmount ());
                        fromStorageTransfer.setAmountAfterTransfer (goodsStorageTransferData.getFromDepositoryAmount () - goodsStorageTransferData.getTransferAmount ());
                        String bid = PurchaseChargeUtils.generateStorageTransferBid ();
                        fromStorageTransfer.setBid (bid);
                        if(null == goodsStorageTransferData.getDateCreated ()) 
                        {
                            fromStorageTransfer.setDateCreated (new Date ());
                        }
                        else 
                        {
                            fromStorageTransfer.setDateCreated (goodsStorageTransferData.getDateCreated ());
                        }
                        fromStorageTransfer.setUserCreatedBy (goodsStorageTransferData.getUserCreatedBy ());
                        fromStorageTransfer.setComment (goodsStorageTransferData.getComment ());
                        fromStorageTransfer = goodsStorageTransferDao.save (fromStorageTransfer);

                        GoodsStorageTransfer toStorageTransfer = new GoodsStorageTransfer ();
                        toStorageTransfer.setGoods (goodsStorageTransferData.getGoods ());
                        toStorageTransfer.setDepository (goodsStorageTransferData.getToDepository ());
                        toStorageTransfer.setBid (bid);
                        if(null == goodsStorageTransferData.getDateCreated ()) 
                        {
                            toStorageTransfer.setDateCreated (new Date ());
                        }
                        else 
                        {
                            toStorageTransfer.setDateCreated (goodsStorageTransferData.getDateCreated ());
                        }
                        toStorageTransfer.setUserCreatedBy (goodsStorageTransferData.getUserCreatedBy ());
                        toStorageTransfer.setComment (goodsStorageTransferData.getComment ());
                        if (null == toStorageDepo)
                        {
                            goodsStorageTransferData.setToDepositoryAmount (0);
                            toStorageTransfer.setAmountBeforeTransfer (goodsStorageTransferData.getToDepositoryAmount ());
                            toStorageTransfer.setAmountAfterTransfer (goodsStorageTransferData.getToDepositoryAmount () + goodsStorageTransferData.getTransferAmount ());
                            toStorageTransfer = goodsStorageTransferDao.save (toStorageTransfer);
                            
                            toStorageDepo = new GoodsStorage ();
                            toStorageDepo.setGoods (goodsStorageTransferData.getGoods ());
                            toStorageDepo.setAmount (goodsStorageTransferData.getTransferAmount ());
                            toStorageDepo.setDepository (goodsStorageTransferData.getToDepository ());
                            toStorageDepo.setPrice (fromStorageDepo.getPrice ());
                            toStorageDepo.setWorth (fromStorageDepo.getPrice () * goodsStorageTransferData.getTransferAmount ());
                            goodsStorageDao.save (toStorageDepo);
                        }
                        else
                        {
                            goodsStorageTransferData.setToDepositoryAmount (toStorageDepo.getAmount ());
                            toStorageTransfer.setAmountBeforeTransfer (goodsStorageTransferData.getToDepositoryAmount ());
                            toStorageTransfer.setAmountAfterTransfer (goodsStorageTransferData.getToDepositoryAmount () + goodsStorageTransferData.getTransferAmount ());
                            toStorageTransfer = goodsStorageTransferDao.save (toStorageTransfer);
                            
                            amount = toStorageDepo.getAmount () + goodsStorageTransferData.getTransferAmount ();
                            toStorageDepo.setAmount (amount);
                            float price = (toStorageDepo.getWorth () + fromStorageDepo.getPrice ()
                                                                       * goodsStorageTransferData.getTransferAmount ())
                                          / amount;
                            toStorageDepo.setPrice (price);
                            toStorageDepo.setWorth (toStorageDepo.getWorth () + fromStorageDepo.getPrice ()
                                                    * goodsStorageTransferData.getTransferAmount ());
                            goodsStorageDao.save (toStorageDepo);
                        }
                        
                        storageTransferData = new GoodsStorageTransferData ();
                        storageTransferData.setBid (fromStorageTransfer.getBid ());
                        storageTransferData.setGoods (fromStorageTransfer.getGoods ());
                        storageTransferData.setFromDepository (fromStorageTransfer.getDepository ());
                        storageTransferData.setToDepository (toStorageTransfer.getDepository ());
                        storageTransferData.setFromDepositoryAmount (fromStorageTransfer.getAmountBeforeTransfer ());
                        storageTransferData.setToDepositoryAmount (toStorageTransfer.getAmountBeforeTransfer ());
                        storageTransferData.setTransferAmount (fromStorageTransfer.getAmountBeforeTransfer () - fromStorageTransfer.getAmountAfterTransfer ());
                        storageTransferData.setDateCreated (fromStorageTransfer.getDateCreated ());
                        storageTransferData.setUserCreatedBy (fromStorageTransfer.getUserCreatedBy ());
                        storageTransferData.setComment (fromStorageTransfer.getComment ());
                        
                        updateGoodsAveragePrice (goodsStorageTransferData.getGoods ().getId ());
                    }
                    else
                    {
                        logger.warn ("Storage of goods(" + fromStorageDepo.getGoods ().getName () + ") in "
                                     + fromStorageDepo.getDepository ().getName () + " is not enough");
                        throw new PcServiceException (
                                                      PurchaseChargeProperties.getInstance ()
                                                                              .getConfigFormatted (PurchaseChargeConstants.STORAGE_NOT_ENOUGH,
                                                                                                   fromStorageDepo.getGoods ()
                                                                                                                  .getName (),
                                                                                                   fromStorageDepo.getDepository ()
                                                                                                                  .getName ()));
                    }
                }
            }
        }
        return storageTransferData;
    }

    @Override
    @Transactional
    public GoodsStorageCheck addGoodsStorageCheck (GoodsStorageCheck storageCheck) throws PcServiceException
    {
        GoodsStorageCheck persistentStorageCheck = null;
        if(null != storageCheck && null != storageCheck.getGoods () && null != storageCheck.getDepository ()) 
        {
            if(storageCheck.getAmountAfterCheck () != storageCheck.getAmountBeforeCheck ()) 
            {
                if(StringUtils.isBlank (storageCheck.getBid ())) 
                {
                    if(storageCheck.getAmountAfterCheck () > storageCheck.getAmountBeforeCheck ()) 
                    {
                        storageCheck.setBid (PurchaseChargeUtils.generateStorageProfitBid ());
                    }
                    else 
                    {
                        storageCheck.setBid (PurchaseChargeUtils.generateStorageLossBid ());
                    }
                }
                if(null == storageCheck.getDateCreated ()) 
                {
                    storageCheck.setDateCreated (new Date ());
                }
                persistentStorageCheck = goodsStorageCheckDao.save (storageCheck);
                if (null != persistentStorageCheck)
                {
                    GoodsStorage goodsStorage = goodsStorageDao.findByGoodsAndDepository (persistentStorageCheck.getGoods ()
                                                                                                                .getId (),
                                                                                          persistentStorageCheck.getDepository ()
                                                                                                                .getId ());
                    if (null != goodsStorage)
                    {
                        long amount = persistentStorageCheck.getAmountAfterCheck ();
                        float price = goodsStorage.getPrice ();
                        goodsStorage.setAmount (amount);
                        goodsStorage.setPrice (price);
                        goodsStorage.setWorth (price * amount);
                        goodsStorageDao.save (goodsStorage);
                        updateGoodsAveragePrice (persistentStorageCheck.getGoods ().getId ());
                    }
                }
            }
        }
        return persistentStorageCheck;
    }

    @Override
    @Transactional
    public GoodsStoragePriceRevise addGoodsStoragePriceRevise (GoodsStoragePriceRevise storagePriceRevise)
                                                                                                          throws PcServiceException
    {
        GoodsStoragePriceRevise persistentStoragePriceRevise = null;
        if(null != storagePriceRevise && null != storagePriceRevise.getGoods () && null != storagePriceRevise.getDepository ()) 
        {
            if(storagePriceRevise.getPriceAfterRevise () != storagePriceRevise.getPriceBeforeRevise ()) 
            {
                if(StringUtils.isBlank (storagePriceRevise.getBid ())) 
                {
                    if(storagePriceRevise.getPriceAfterRevise () > storagePriceRevise.getPriceBeforeRevise ()) 
                    {
                        storagePriceRevise.setBid (PurchaseChargeUtils.generateStoragePriceUpBid ());
                    }
                    else 
                    {
                        storagePriceRevise.setBid (PurchaseChargeUtils.generateStoragePriceDownBid ());
                    }
                }
                if(null == storagePriceRevise.getDateCreated ()) 
                {
                    storagePriceRevise.setDateCreated (new Date ());
                }
                persistentStoragePriceRevise = goodsStoragePriceReviseDao.save (storagePriceRevise);
                if (null != persistentStoragePriceRevise)
                {
                    GoodsStorage goodsStorage = goodsStorageDao.findByGoodsAndDepository (persistentStoragePriceRevise.getGoods ()
                                                                                                                .getId (),
                                                                                                                persistentStoragePriceRevise.getDepository ()
                                                                                                                .getId ());
                    if (null != goodsStorage)
                    {
                        long amount = goodsStorage.getAmount ();
                        float price = persistentStoragePriceRevise.getPriceAfterRevise ();
                        goodsStorage.setAmount (amount);
                        goodsStorage.setPrice (price);
                        goodsStorage.setWorth (price * amount);
                        goodsStorageDao.save (goodsStorage);
                        updateGoodsAveragePrice (persistentStoragePriceRevise.getGoods ().getId ());
                    }
                }
            }
        }
        return persistentStoragePriceRevise;
    }

    @Override
    @Transactional (propagation = Propagation.NESTED)
    public PaymentTransfer addPaymentTransfer (PaymentTransfer accountTransfer) throws PcServiceException
    {
        if (null != accountTransfer && null != accountTransfer.getTargetAccount ()
            && null != accountTransfer.getTransferTypeCode ())
        {
            PaymentAccount targetAccount = queryService.getPaymentAccount (accountTransfer.getTargetAccount ().getId ());
            if (null == targetAccount)
            {
                String accountNotExist = PurchaseChargeProperties.getInstance ()
                                                                 .getConfig ("pc.paymentAccountNotExist");
                logger.warn (accountNotExist);
                throw new PcServiceException (accountNotExist);
            }

            if (accountTransfer.getInMoney () > 0 && accountTransfer.getOutMoney () > 0)
            {
                logger.warn ("in money and out money can not have value in the same time");
                throw new PcServiceException (PurchaseChargeProperties.getInstance ()
                                                                      .getConfig ("pc.systemDataException"));
            }

            if (PaymentTransferTypeCode.INTERNAL_TRANSFER.equals (accountTransfer.getTransferTypeCode ()))
            {
                if ((accountTransfer.getTargetAccount ().getId () + "").equals (accountTransfer.getSource ()))
                {
                    throw new PcServiceException (
                                                  PurchaseChargeProperties.getInstance ()
                                                                          .getConfig ("pc.accountFromAndAccountToNotSame"));
                }
            }
            if (null == accountTransfer.getDateCreated ())
            {
                accountTransfer.setDateCreated (new Timestamp (System.currentTimeMillis ()));
            }

            if (0 < accountTransfer.getOutMoney ())
            {
                if(!PurchaseChargeProperties.getAllowNegativePaymentFlag ()) 
                {
                    if (targetAccount.getRemainMoney () < accountTransfer.getOutMoney ())
                    {
                        throw new PcServiceException (
                                                      PurchaseChargeProperties.getInstance ()
                                                      .getConfigFormatted ("pc.accountRemainMoneyNotEnough",
                                                                           targetAccount.getName ()));
                    }
                }

                try
                {
                    targetAccount.setRemainMoney (targetAccount.getRemainMoney () - accountTransfer.getOutMoney ());
                    targetAccount = paymentAccountDao.save (targetAccount);
                    if (PaymentTransferTypeCode.INTERNAL_TRANSFER.equals (accountTransfer.getTransferTypeCode ()))
                    {
                        long toAccountId = Long.parseLong (accountTransfer.getSource ());
                        PaymentAccount toAccount = queryService.getPaymentAccount (toAccountId);
                        if (null != toAccount)
                        {
                            // toAccount.setRemainMoney
                            // (toAccount.getRemainMoney () +
                            // accountTransfer.getOutMoney ());
                            // paymentAccountDao.save (toAccount);
                            PaymentTransfer internalTransfer = new PaymentTransfer ();
                            internalTransfer.setTargetAccount (toAccount);
                            internalTransfer.setTransferTypeCode (PaymentTransferTypeCode.INTERNAL_TRANSFER);
                            internalTransfer.setSource (targetAccount.getId () + "");
                            internalTransfer.setInMoney (accountTransfer.getOutMoney ());
                            internalTransfer.setUserCreatedBy (accountTransfer.getUserCreatedBy ());
                            addPaymentTransfer (internalTransfer);
                        }
                    }

                    accountTransfer.setRemainMoney (targetAccount.getRemainMoney ());
                    return paymentTransferDao.save (accountTransfer);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage ());
                    throw new RuntimeException (e);
                }
            }
            else if (0 < accountTransfer.getInMoney ())
            {
                try
                {
                    if (!PaymentTransferTypeCode.INITIAL_REMAIN.equals (accountTransfer.getTransferTypeCode ()))
                    {
                        targetAccount.setRemainMoney (targetAccount.getRemainMoney () + accountTransfer.getInMoney ());
                        targetAccount = paymentAccountDao.save (targetAccount);
                    }

                    accountTransfer.setRemainMoney (targetAccount.getRemainMoney ());
                    return paymentTransferDao.save (accountTransfer);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage ());
                    throw new RuntimeException (e);
                }
            }
        }

        return null;
    }

    @Override
    @Transactional
    public OrderOut addOrderOut (OrderOut order, PaymentAccount paymentAccount) throws PcServiceException
    {
        if (null != order && null != order.getTypeCode ())
        {
            validateOrderOut(order);
            
            order.setBid (PurchaseChargeUtils.generateOrderBid (order.getTypeCode (), order.getBid ()));
            OrderOut dbOrder = orderOutDao.findOne (order.getId ());
            if (null == dbOrder)
            {
                if (null == order.getDateCreated ())
                {
                    order.setDateCreated (new Timestamp (System.currentTimeMillis ()));
                }
                if (OrderTypeCode.OUT == order.getTypeCode ())
                {
                    order.setStatusCode (OrderStatusCode.NEW);
                }
                else if (OrderTypeCode.OUT_RETURN == order.getTypeCode ())
                {
                    order.setStatusCode (OrderStatusCode.COMPLETED);
                }
                order.setLastUpdated (new Timestamp (System.currentTimeMillis ()));
                prepareForAddOrderOut (order);
                calculateOrderOutMoney (order);

                try
                {
                    OrderOut orderOut = orderOutDao.save (order);

                    if (null != orderOut)
                    {
                        if (OrderTypeCode.OUT_RETURN.equals (orderOut.getTypeCode ()))
                        {
                            String paymentComment = PurchaseChargeProperties.getInstance ()
                                                                            .getConfig (ConfigurationProperties.PC_OUTORDER_DELPAID_COMMENT.getName ());
                            CustomerPayment cp = new CustomerPayment ();
                            cp.setReceiptId (orderOut.getBid ());
                            cp.setPaidDate (orderOut.getDateCreated ());
                            cp.setComment (paymentComment);
                            cp.setCustomer (orderOut.getCustomer ());
                            cp.setUserCreatedBy (orderOut.getUserCreatedBy ());
                            cp.setTypeCode (PaymentTypeCode.OUT_ORDER_RETURN);
                            cp.setAddUnPaid (orderOut.getReceivable ());
                            if (orderOut.getPaidMoney () > 0 && null != paymentAccount)
                            {
                                List <CustomerPaymentRecord> paymentRecords = new ArrayList <CustomerPaymentRecord> ();
                                CustomerPaymentRecord cpr = new CustomerPaymentRecord ();
                                cpr.setPaid (orderOut.getPaidMoney ());
                                cpr.setPaymentAccount (paymentAccount);
                                paymentRecords.add (cpr);
                                cp.setPaymentRecords (paymentRecords);
                            }
                            addCustomerPayment (cp);
                        }
                        else if (OrderTypeCode.OUT.equals (orderOut.getTypeCode ()))
                        {
                            if (orderOut.getPaidMoney () > 0 && null != paymentAccount)
                            {
                                CustomerPayment cp = new CustomerPayment ();
                                cp.setReceiptId (orderOut.getBid ());
                                cp.setPaidDate (orderOut.getDateCreated ());
                                cp.setCustomer (orderOut.getCustomer ());
                                cp.setUserCreatedBy (orderOut.getUserCreatedBy ());
                                cp.setTypeCode (PaymentTypeCode.OUT_PAID_MONEY);
                                List <CustomerPaymentRecord> paymentRecords = new ArrayList <CustomerPaymentRecord> ();
                                CustomerPaymentRecord cpr = new CustomerPaymentRecord ();
                                cpr.setPaid (orderOut.getPaidMoney ());
                                cpr.setPaymentAccount (paymentAccount);
                                paymentRecords.add (cpr);
                                cp.setPaymentRecords (paymentRecords);
                                addCustomerPayment (cp);
                            }
                        }

                        // update goods storage
                        List <OrderOutItem> persistOrderItems = order.getOrderItems ();
                        if (!CollectionUtils.isEmpty (persistOrderItems))
                        {
                            for (OrderOutItem orderItem : persistOrderItems)
                            {
                                GoodsStorage goodsStorage = goodsStorageDao.findByGoodsAndDepository (orderItem.getGoods ()
                                                                                                              .getId (),
                                                                                                     orderItem.getDepository ()
                                                                                                              .getId ());
                                if (null != goodsStorage)
                                {
                                    long amount = 0;
                                    amount = goodsStorage.getAmount () - orderItem.getAmount ();
                                    goodsStorage.setAmount (amount);
                                    goodsStorage.setWorth (goodsStorage.getPrice () * amount);
                                    goodsStorageDao.save (goodsStorage);
                                }
                                else
                                {
                                    goodsStorage = new GoodsStorage ();
                                    goodsStorage.setGoods (orderItem.getGoods ());
                                    goodsStorage.setDepository (orderItem.getDepository ());
                                    if (OrderTypeCode.OUT_RETURN == orderOut.getTypeCode ())
                                    {
                                        goodsStorage.setAmount (-orderItem.getAmount ());
                                    }
                                    else if (OrderTypeCode.OUT == orderOut.getTypeCode ())
                                    {
                                        if(PurchaseChargeProperties.getAllowNegativeStorageFlag ()) 
                                        {
                                            goodsStorage.setAmount (-orderItem.getAmount ());
                                        }
                                    }
                                    float goodsImportPrice = queryService.getGoods (orderItem.getGoods ().getId ()).getImportPrice ();
                                    goodsStorage.setPrice (goodsImportPrice);
                                    goodsStorage.setWorth (goodsStorage.getAmount () * goodsImportPrice);
                                    goodsStorageDao.save (goodsStorage);
                                }
                                updateGoodsAveragePrice (orderItem.getGoods ().getId ());
                            }
                        }
                    }

                    return orderOut;
                }
                catch (PcServiceException e)
                {
                    throw e;
                }
                catch (Exception e)
                {
                    logger.warn ("Save order out failed.", e);
                    throw new PcServiceException (
                                                  PurchaseChargeProperties.getInstance ()
                                                                          .getConfig (PurchaseChargeConstants.ADD_ORDEROUT_FAIL));
                }
            }
        }
        return null;
    }
    
    @Override
    public void validateOrderOut (OrderOut order) throws PcServiceException
    {
        if (null == order.getCustomer ())
        {
            throw new PcServiceException (
                                          PurchaseChargeProperties.getInstance ()
                                                                  .getConfig (PurchaseChargeConstants.NO_CUSTOMER_ERROR));
        }
        // check same goods and depository
        if (CollectionUtils.isNotEmpty (order.getOrderItems ()))
        {
            Map <String, Long> sameGoodsCountMap = new HashMap <String, Long> ();
            long goodsId;
            long goodsDepositoryId;
            for (OrderOutItem orderItem : order.getOrderItems ())
            {
                if(null != orderItem.getGoods () && null != orderItem.getDepository ()) 
                {
                    goodsId = orderItem.getGoods ().getId ();
                    goodsDepositoryId = orderItem.getDepository ().getId ();
                    String goodsIdAndDepository = goodsId + "_" + goodsDepositoryId;
                    if(sameGoodsCountMap.containsKey (goodsIdAndDepository)) 
                    {
                        throw new PcServiceException (
                                                      PurchaseChargeProperties.getInstance ()
                                                      .getConfigFormatted ("pc.orderItemFindDuplicateGoodsAndDepository", queryService.getGoods (goodsId).getName (), queryService.getGoodsDepository (goodsDepositoryId).getName ()));
                    }
                    else 
                    {
                        sameGoodsCountMap.put (goodsIdAndDepository, goodsDepositoryId);
                    }
                }
            }
        }
        // check goods storage and price
        if (CollectionUtils.isNotEmpty (order.getOrderItems ()))
        {
            for (OrderOutItem orderItem : order.getOrderItems ())
            {
                GoodsStorage goodsStorage = goodsStorageDao.findByGoodsAndDepository (orderItem.getGoods ().getId (),
                                                                                     orderItem.getDepository ()
                                                                                              .getId ());
                if (null != goodsStorage)
                {
                    long amount = 0;
                    if (OrderTypeCode.OUT == order.getTypeCode ())
                    {
                        amount = goodsStorage.getAmount () - orderItem.getAmount ();
                        if (amount < 0)
                        {
                            if(!PurchaseChargeProperties.getAllowNegativeStorageFlag ()) 
                            {
                                logger.warn ("Storage of goods(" + goodsStorage.getGoods ().getName () + ") in "
                                        + goodsStorage.getDepository ().getName () + " is not enough");
                                throw new PcServiceException (
                                                              PurchaseChargeProperties.getInstance ()
                                                              .getConfigFormatted (PurchaseChargeConstants.STORAGE_NOT_ENOUGH,
                                                                                   goodsStorage.getGoods ()
                                                                                   .getName (),
                                                                                   goodsStorage.getDepository ()
                                                                                   .getName ()));
                            }
                        }

                        // check price
                        if(!PurchaseChargeProperties.getAllowNegativePriceFlag ()) 
                        {
                            if (goodsStorage.getGoods ().getImportPrice () > orderItem.getUnitPrice ())
                            {
                                throw new PcServiceException (
                                                              PurchaseChargeProperties.getInstance ()
                                                              .getConfigFormatted ("pc.exportPriceMustGreaterThanAveragePrice",
                                                                                   goodsStorage.getGoods ()
                                                                                   .getName ()));
                            }
                        }
                    }
                }
                else
                {
                    if (OrderTypeCode.OUT == order.getTypeCode ())
                    {
                        if(!PurchaseChargeProperties.getAllowNegativeStorageFlag ()) 
                        {
                            Goods g = queryService.getGoods (orderItem.getGoods ().getId ());
                            GoodsDepository gd = queryService.getGoodsDepository (orderItem.getDepository ().getId ());
                            if (null != g && null != gd)
                            {
                                String goodsNotInDepository = PurchaseChargeProperties.getInstance ()
                                        .getConfigFormatted (PurchaseChargeConstants.GOODS_NOT_IN_DEPOSITORY,
                                                             g.getName (),
                                                             gd.getName ());
                                logger.warn (goodsNotInDepository);
                                throw new PcServiceException (goodsNotInDepository);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public OrderOut updateOrderOut (OrderOut order) throws PcServiceException
    {
        throw new RuntimeException ("Not implemented yet");
    }

    @Override
    public void deleteOrderOut (List <OrderOut> orders) throws PcServiceException
    {
        if (CollectionUtils.isNotEmpty (orders))
        {
            List <OrderOut> deletableOrders = new ArrayList <OrderOut> ();
            List <String> notDeletableOrders = new ArrayList <String> ();
            for (OrderOut order : orders)
            {
                OrderOut dbOrder = orderOutDao.findOne (order.getId ());
                if ((OrderStatusCode.CANCELED.equals (dbOrder.getStatusCode ()))
                    || (OrderStatusCode.NEW.equals (dbOrder.getStatusCode ()) && 0 == dbOrder.getReceivable ()))
                {
                    deletableOrders.add (order);
                }
                else
                {
                    notDeletableOrders.add (order.getId () + "");
                }
            }
            if (deletableOrders.size () == 0 || deletableOrders.size () < orders.size ())
            {
                throw new PcServiceException (
                                              PurchaseChargeProperties.getInstance ()
                                                                      .getConfigFormatted ("pc.orderOutCannotBeDeleted",
                                                                                           notDeletableOrders.toString ()));
            }
            orderOutDao.delete (deletableOrders);
        }
    }

    @Override
    @Transactional (propagation = Propagation.NESTED)
    public boolean updateOrderOutStatus (long orderId, OrderStatusCode statusCode, String comment, Timestamp updateTime)
                                                                                                  throws PcServiceException
    {
        try
        {
            OrderOut order = orderOutDao.findOne (orderId);
            if (null != order)
            {
                if (!OrderStatusCode.NEW.equals (order.getStatusCode ())
                    && !OrderStatusCode.SHIPPED.equals (order.getStatusCode ()))
                {
                    return false;
                }
                String comments = order.getComment ();
                if (StringUtils.isNotBlank (comment))
                {
                    comments += (StringUtils.isNotBlank (comments) ? "  " : "") + comment;
                }
                if(null == updateTime) 
                {
                    updateTime = new Timestamp (System.currentTimeMillis ());
                }
                orderOutDao.updateStatus (statusCode, comments, orderId, updateTime);

                if (OrderTypeCode.OUT == order.getTypeCode ())
                {
                    if (OrderStatusCode.COMPLETED.equals (statusCode))
                    {
                        String paymentComment = PurchaseChargeProperties.getInstance ()
                                                                        .getConfig (ConfigurationProperties.PC_OUTORDER_ADDPAID_COMMENT.getName ());
                        CustomerPayment cp = new CustomerPayment ();
                        cp.setReceiptId (order.getBid ());
                        cp.setComment (paymentComment);
                        cp.setCustomer (order.getCustomer ());
                        cp.setUserCreatedBy (order.getUserCreatedBy ());
                        cp.setTypeCode (PaymentTypeCode.OUT_ORDER);
                        cp.setAddUnPaid (order.getReceivable ());
                        cp.setPaidDate (updateTime);
                        addCustomerPayment (cp);
                    }
                    else if (OrderStatusCode.CANCELED.equals (statusCode))
                    {
                        // clear profit
                        orderOutDao.updateReceivable (0, order.getId ());
                        orderOutDao.updateProfit (0, order.getId ());

                        // update goods storage
                        List <OrderOutItem> persistOrderItems = orderOutItemDao.findByOrder (orderId, true);
                        if (!CollectionUtils.isEmpty (persistOrderItems))
                        {
                            for (OrderOutItem orderItem : persistOrderItems)
                            {
                                GoodsStorage goodsStorage = goodsStorageDao.findByGoodsAndDepository (orderItem.getGoods ()
                                                                                                              .getId (),
                                                                                                     orderItem.getDepository ()
                                                                                                              .getId ());
                                if (null != goodsStorage)
                                {
                                    long amount = goodsStorage.getAmount () + orderItem.getAmount ();
                                    goodsStorage.setAmount (amount);
                                    goodsStorage.setWorth (goodsStorage.getPrice () * amount);
                                    goodsStorageDao.save (goodsStorage);
                                }
                            }
                        }

                        // back paid money
                        if (order.getPaidMoney () > 0)
                        {
                            CustomerPayment cp = new CustomerPayment ();
                            cp.setAddUnPaid (order.getPaidMoney ());
                            cp.setCustomer (order.getCustomer ());
                            cp.setReceiptId (order.getBid ());
                            cp.setUserCreatedBy (order.getUserCreatedBy ());
                            cp.setTypeCode (PaymentTypeCode.OUT_ORDER_RETURN);
                            cp.setPaidDate (updateTime);
                            addCustomerPayment (cp);
                        }
                    }
                }
            }

            return true;
        }
        catch (Exception e)
        {
            logger.warn ("Update Order status fail.", e);
            throw new PcServiceException (e.getMessage ());
        }
    }

    @Override
    @Transactional (propagation = Propagation.NESTED)
    public CustomerPayment addCustomerPayment (CustomerPayment payment) throws PcServiceException
    {
        CustomerPayment customerPayment = null;
        if (null != payment && null != payment.getCustomer () && null != payment.getTypeCode ())
        {
            if(StringUtils.isBlank (payment.getBid ())) 
            {
                payment.setBid (PurchaseChargeUtils.generateCustomerPaymentBid ());
            }
            if (null == payment.getPaidDate ())
            {
                payment.setPaidDate (new Timestamp (System.currentTimeMillis ()));
            }
            PaymentTypeCode paymentTypeCode = payment.getTypeCode ();
            Customer customer = customerDao.findOne (payment.getCustomer ().getId ());
            float allUnPaid = 0;
            if (PaymentTypeCode.INITIAL_BALANCE == paymentTypeCode)
            {
                allUnPaid = customer.getUnpayMoney ();
                payment.setRemainUnPaid (allUnPaid);
            }
            else if (PaymentTypeCode.OUT_ORDER == paymentTypeCode)
            {
                allUnPaid = customer.getUnpayMoney () + payment.getAddUnPaid ();
                payment.setRemainUnPaid (allUnPaid);
            }
            else if (PaymentTypeCode.OUT_ORDER_RETURN == paymentTypeCode)
            {
                if (payment.getAddUnPaid () > 0)
                {
                    allUnPaid = customer.getUnpayMoney () - payment.getAddUnPaid ();
                    payment.setAddUnPaid (-payment.getAddUnPaid ());
                }
                else
                {
                    allUnPaid = customer.getUnpayMoney () + payment.getAddUnPaid ();
                }
                payment.setRemainUnPaid (allUnPaid);

                List <CustomerPaymentRecord> paymentRecords = payment.getPaymentRecords ();
                if (!CollectionUtils.isEmpty (paymentRecords))
                {
                    float sumPaid = 0;
                    for (CustomerPaymentRecord paymentRecord : paymentRecords)
                    {
                        sumPaid += paymentRecord.getPaid ();

                        // update account remain money
                        if (null != paymentRecord.getPaymentAccount ())
                        {
                            long accountId = paymentRecord.getPaymentAccount ().getId ();
                            PaymentAccount paymentAccount = queryService.getPaymentAccount (accountId);
                            if (null != paymentAccount)
                            {
                                float money = paymentRecord.getPaid ();
                                PaymentTransfer paymentTransfer = new PaymentTransfer ();
                                if (money > 0)
                                {
                                    paymentTransfer.setOutMoney (money);
                                }
                                else
                                {
                                    paymentTransfer.setInMoney (-money);
                                }
                                paymentTransfer.setTargetAccount (paymentAccount);
                                paymentTransfer.setSource (payment.getCustomer ().getId () + "");
                                paymentTransfer.setTransferTypeCode (PaymentTransferTypeCode.CUSTOMER_TRANSFER);
                                paymentTransfer.setUserCreatedBy (payment.getUserCreatedBy ());
                                paymentTransfer.setDateCreated (payment.getPaidDate ());
                                addPaymentTransfer (paymentTransfer);
                            }
                        }
                    }

                    allUnPaid = allUnPaid + sumPaid;
                    payment.setRemainUnPaid (allUnPaid);
                }
            }
            else if (PaymentTypeCode.OUT_PAID_MONEY == paymentTypeCode)
            {
                payment.setAddUnPaid (0);
                List <CustomerPaymentRecord> paymentRecords = payment.getPaymentRecords ();
                if (!CollectionUtils.isEmpty (paymentRecords))
                {
                    float sumPaid = 0;
                    for (CustomerPaymentRecord paymentRecord : paymentRecords)
                    {
                        sumPaid += paymentRecord.getPaid ();

                        // update account remain money
                        if (null != paymentRecord.getPaymentAccount ())
                        {
                            long accountId = paymentRecord.getPaymentAccount ().getId ();
                            PaymentAccount paymentAccount = queryService.getPaymentAccount (accountId);
                            if (null != paymentAccount)
                            {
                                float money = paymentRecord.getPaid ();
                                PaymentTransfer paymentTransfer = new PaymentTransfer ();
                                if (money > 0)
                                {
                                    // paymentAccount.setRemainMoney
                                    // (paymentAccount.getRemainMoney () +
                                    // money);
                                    // paymentAccountDao.save (paymentAccount);
                                    paymentTransfer.setInMoney (money);
                                }
                                else
                                {
                                    paymentTransfer.setOutMoney (-money);
                                }
                                paymentTransfer.setTargetAccount (paymentAccount);
                                paymentTransfer.setSource (payment.getCustomer ().getId () + "");
                                paymentTransfer.setTransferTypeCode (PaymentTransferTypeCode.CUSTOMER_TRANSFER);
                                paymentTransfer.setUserCreatedBy (payment.getUserCreatedBy ());
                                paymentTransfer.setDateCreated (payment.getPaidDate ());
                                addPaymentTransfer (paymentTransfer);
                            }
                        }
                    }

                    if (sumPaid == 0)
                    {
                        payment = null;
                    }
                    else
                    {
                        allUnPaid = customer.getUnpayMoney () - sumPaid;
                        payment.setRemainUnPaid (allUnPaid);
                    }
                }
                else
                {
                    payment = null;
                }
            }

            try
            {
                if (null != payment)
                {
                    customerPayment = customerPaymentDao.save (payment);
                }
            }
            catch (Exception e)
            {
                logger.warn ("Save customer payment failed", e);
                throw new PcServiceException (e);
            }

            try
            {
                if (null != customerPayment)
                {
                    customerDao.updateCustomerAccounts (customer.getId (), allUnPaid);
                }
            }
            catch (Exception e)
            {
                logger.warn ("Update customer accounts failed", e);
                throw new PcServiceException (e);
            }
        }
        return customerPayment;
    }

    @Override
    public CustomerPayment updateCustomerPayment (CustomerPayment payment) throws PcServiceException
    {
        return customerPaymentDao.save (payment);
    }

    @Override
    public void deleteCustomerPayment (CustomerPayment payment) throws PcServiceException
    {
        customerPaymentDao.delete (payment);
    }

    @Override
    public void deleteCustomerPayment (List <CustomerPayment> payments) throws PcServiceException
    {
        customerPaymentDao.delete (payments);
    }

    @Override
    @Transactional (propagation = Propagation.NESTED)
    public OrderIn addOrderIn (OrderIn order, PaymentAccount paymentAccount) throws PcServiceException
    {
        // add order and order item
        if (null != order && null != order.getTypeCode ())
        {
            validateOrderIn(order);

            order.setBid (PurchaseChargeUtils.generateOrderBid (order.getTypeCode (), order.getBid ()));
            if (orderInDao.findOne (order.getId ()) == null)
            {
                if (null == order.getDateCreated ())
                {
                    order.setDateCreated (new Timestamp (System.currentTimeMillis ()));
                }
                order.setLastUpdated (new Timestamp (System.currentTimeMillis ()));
                order.setStatusCode (OrderStatusCode.COMPLETED);

                prepareForAddOrderIn (order);
                //
                if (!PurchaseChargeProperties.getDefaultProvider ().equals (order.getProvider ().getShortName ()))
                {
                    calculateOrderInMoney (order);
                }

                try
                {
                    OrderIn orderIn = orderInDao.save (order);
                    if (null != orderIn)
                    {
                        String providerName = orderIn.getProvider ().getShortName ();
                        if (StringUtils.isBlank (providerName))
                        {
                            providerName = providerDao.findOne (orderIn.getProvider ().getId ()).getShortName ();
                        }
                        if (!PurchaseChargeProperties.getDefaultProvider ().equals (providerName))
                        {
                            ProviderPayment pp = new ProviderPayment ();
                            pp.setPaidDate (orderIn.getDateCreated ());
                            pp.setProvider (orderIn.getProvider ());
                            pp.setReceiptId (orderIn.getBid ());
                            pp.setUserCreatedBy (orderIn.getUserCreatedBy ());
                            if (OrderTypeCode.IN.equals (orderIn.getTypeCode ()))
                            {
                                pp.setComment (PurchaseChargeProperties.getInstance ()
                                                                       .getConfig (ConfigurationProperties.PC_INORDER_ADDPAID_COMMENT.getName ()));
                                pp.setTypeCode (PaymentTypeCode.IN_ORDER);
                                if (orderIn.getPaidMoney () > 0 && null != paymentAccount)
                                {
                                    List <ProviderPaymentRecord> paymentRecords = new ArrayList <ProviderPaymentRecord> ();
                                    ProviderPaymentRecord ppr = new ProviderPaymentRecord ();
                                    ppr.setPaid (orderIn.getPaidMoney ());
                                    ppr.setPaymentAccount (paymentAccount);
                                    paymentRecords.add (ppr);
                                    pp.setPaymentRecords (paymentRecords);
                                }
                            }
                            else if (OrderTypeCode.IN_RETURN.equals (orderIn.getTypeCode ()))
                            {
                                pp.setComment (PurchaseChargeProperties.getInstance ()
                                                                       .getConfig (ConfigurationProperties.PC_INORDER_DELPAID_COMMENT.getName ()));
                                pp.setTypeCode (PaymentTypeCode.IN_ORDER_RETURN);
                                if (orderIn.getPaidMoney () > 0 && null != paymentAccount)
                                {
                                    List <ProviderPaymentRecord> paymentRecords = new ArrayList <ProviderPaymentRecord> ();
                                    ProviderPaymentRecord ppr = new ProviderPaymentRecord ();
                                    ppr.setPaid (orderIn.getPaidMoney ());
                                    ppr.setPaymentAccount (paymentAccount);
                                    paymentRecords.add (ppr);
                                    pp.setPaymentRecords (paymentRecords);
                                }
                            }
                            pp.setAddUnPaid (orderIn.getReceivable ());
                            addProviderPayment (pp);

                            // update goods storage
                            List <OrderInItem> persistOrderItems = order.getOrderItems ();
                            if (!CollectionUtils.isEmpty (persistOrderItems))
                            {
                                for (OrderInItem orderItem : persistOrderItems)
                                {
                                    GoodsStorage goodsStorage = goodsStorageDao.findByGoodsAndDepository (orderItem.getGoods ()
                                                                                                                  .getId (),
                                                                                                         orderItem.getDepository ()
                                                                                                                  .getId ());
                                    if (null == goodsStorage)
                                    {
                                        goodsStorage = new GoodsStorage ();
                                        goodsStorage.setGoods (orderItem.getGoods ());
                                        goodsStorage.setDepository (orderItem.getDepository ());
                                        goodsStorage.setAmount (orderItem.getAmount ());
                                        goodsStorage.setPrice (orderItem.getUnitPrice ());
                                        goodsStorage.setWorth (orderItem.getAmount () * orderItem.getUnitPrice ());
                                        goodsStorageDao.save (goodsStorage);
                                    }
                                    else
                                    {
                                        long amount = 0;
                                        float averagePrice = 0;
                                        if (OrderTypeCode.IN == orderIn.getTypeCode ())
                                        {
                                            amount = goodsStorage.getAmount () + orderItem.getAmount ();
                                            if(amount != 0) 
                                            {
                                                averagePrice = (goodsStorage.getPrice () * goodsStorage.getAmount () + orderItem.getUnitPrice ()
                                                        * orderItem.getAmount ())
                                                        / amount;
                                            }
                                            else 
                                            {
                                                averagePrice = goodsStorage.getPrice ();
                                            }
                                        }
                                        else if (OrderTypeCode.IN_RETURN == orderIn.getTypeCode ())
                                        {
                                            amount = goodsStorage.getAmount () + orderItem.getAmount ();
                                            if (amount != 0)
                                            {
                                                averagePrice = (goodsStorage.getPrice ()
                                                                * goodsStorage.getAmount () + orderItem.getUnitPrice ()
                                                                                              * orderItem.getAmount ())
                                                               / amount;
                                            }
                                            else
                                            {
                                                averagePrice = goodsStorage.getPrice ();
                                            }
                                        }
                                        goodsStorage.setAmount (amount);
                                        goodsStorage.setPrice (averagePrice);
                                        goodsStorage.setWorth (averagePrice * amount);
                                        goodsStorageDao.save (goodsStorage);
                                    }
                                    updateGoodsAveragePrice (orderItem.getGoods ().getId ());
                                }
                            }
                        }
                    }
                    return orderIn;
                }
                catch (PcServiceException e)
                {
                    throw e;
                }
                catch (Exception e)
                {
                    logger.warn (PurchaseChargeProperties.getInstance ()
                                                         .getConfig (PurchaseChargeConstants.ADD_ORDERIN_FAIL), e);
                    throw new PcServiceException (
                                                  PurchaseChargeProperties.getInstance ()
                                                                          .getConfig (PurchaseChargeConstants.ADD_ORDERIN_FAIL));
                }
            }
        }
        return null;
    }
    
    @Override
    public void validateOrderIn (OrderIn order) throws PcServiceException
    {
        if (null == order.getProvider ())
        {
            throw new PcServiceException (
                                          PurchaseChargeProperties.getInstance ()
                                                                  .getConfig (PurchaseChargeConstants.NO_PROVIDER_ERROR));
        }
        // check same goods and depository
        if (CollectionUtils.isNotEmpty (order.getOrderItems ()))
        {
            Map <String, Long> sameGoodsCountMap = new HashMap <String, Long> ();
            long goodsId;
            long goodsDepositoryId;
            for (OrderInItem orderItem : order.getOrderItems ())
            {
                if(null != orderItem.getGoods () && null != orderItem.getDepository ()) 
                {
                    goodsId = orderItem.getGoods ().getId ();
                    goodsDepositoryId = orderItem.getDepository ().getId ();
                    String goodsIdAndDepository = goodsId + "_" + goodsDepositoryId;
                    if(sameGoodsCountMap.containsKey (goodsIdAndDepository)) 
                    {
                        throw new PcServiceException (
                                                      PurchaseChargeProperties.getInstance ()
                                                      .getConfigFormatted ("pc.orderItemFindDuplicateGoodsAndDepository", queryService.getGoods (goodsId).getName (), queryService.getGoodsDepository (goodsDepositoryId).getName ()));
                    }
                    else 
                    {
                        sameGoodsCountMap.put (goodsIdAndDepository, goodsDepositoryId);
                    }
                }
            }
        }
        // check goods storage
        if (!CollectionUtils.isEmpty (order.getOrderItems ()))
        {
            for (OrderInItem orderItem : order.getOrderItems ())
            {
                if (OrderTypeCode.IN_RETURN == order.getTypeCode ())
                {
                    if(!PurchaseChargeProperties.getAllowNegativeStorageFlag ()) 
                    {
                        GoodsStorage goodsStorage = goodsStorageDao.findByGoodsAndDepository (orderItem.getGoods ()
                                                                                              .getId (),
                                                                                              orderItem.getDepository ()
                                                                                              .getId ());
                        if (null != goodsStorage)
                        {
                            long amount = goodsStorage.getAmount () - orderItem.getAmount ();
                            if (amount < 0)
                            {
                                logger.warn ("Storage of goods(" + goodsStorage.getGoods ().getName () + ") in "
                                        + goodsStorage.getDepository ().getName () + " is not enough");
                                throw new PcServiceException (
                                                              PurchaseChargeProperties.getInstance ()
                                                              .getConfigFormatted (PurchaseChargeConstants.STORAGE_NOT_ENOUGH,
                                                                                   goodsStorage.getGoods ()
                                                                                   .getName (),
                                                                                   goodsStorage.getDepository ()
                                                                                   .getName ()));
                            }
                        }
                    }
                }
                if(OrderTypeCode.IN.equals (order.getTypeCode ())) 
                {
                    // check price
                    if(!PurchaseChargeProperties.getAllowNegativePriceFlag ()) 
                    {
                        Goods goods = queryService.getGoods (orderItem.getGoods ().getId ());
                        if(null != goods) 
                        {
                            float goodsRetailPrice = goods.getRetailPrice ();
                            float goodsTradePrice = goods.getTradePrice ();
                            if((goodsRetailPrice > 0 && goodsRetailPrice < orderItem.getUnitPrice ()) || (goodsTradePrice > 0 && goodsTradePrice < orderItem.getUnitPrice ())) 
                            {
                                throw new PcServiceException (
                                                              PurchaseChargeProperties.getInstance ()
                                                              .getConfigFormatted ("pc.importPriceMustLowerThanTradePriceOrRetailPrice",
                                                                                   goods.getName ()));
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public OrderIn updateOrderIn (OrderIn order) throws PcServiceException
    {
        throw new RuntimeException ("Not implemented yet");
    }

    @Override
    public void deleteOrderIn (List <OrderIn> orders) throws PcServiceException
    {
        if (CollectionUtils.isNotEmpty (orders))
        {
            List <OrderIn> deletableOrders = new ArrayList <OrderIn> ();
            List <String> notDeletableOrders = new ArrayList <String> ();
            for (OrderIn order : orders)
            {
                OrderIn dbOrder = orderInDao.findOne (order.getId ());
                if (OrderTypeCode.IN.equals (dbOrder.getTypeCode ()) && 0 == dbOrder.getReceivable ())
                {
                    deletableOrders.add (order);
                }
                else
                {
                    notDeletableOrders.add (order.getId () + "");
                }
            }
            if (deletableOrders.size () == 0 || deletableOrders.size () < orders.size ())
            {
                throw new PcServiceException (
                                              PurchaseChargeProperties.getInstance ()
                                                                      .getConfigFormatted ("pc.orderInCannotBeDeleted",
                                                                                           notDeletableOrders.toString ()));
            }
            orderInDao.delete (deletableOrders);
        }
    }

    @Override
    public boolean updateOrderInStatus (long orderId, OrderStatusCode statusCode, String comment)
    {
        throw new RuntimeException ("not supported");
    }

    @Override
    @Transactional
    public PaymentAccount addPaymentAccount (PaymentAccount paymentAccount) throws PcServiceException
    {
        if (null != paymentAccount)
        {
            try
            {
                if (0 < paymentAccount.getRemainMoney ())
                {
                    paymentAccount = paymentAccountDao.save (paymentAccount);

                    PaymentTransfer accountTransfer = new PaymentTransfer ();
                    accountTransfer.setTargetAccount (paymentAccount);
                    accountTransfer.setTransferTypeCode (PaymentTransferTypeCode.INITIAL_REMAIN);
                    accountTransfer.setDateCreated (new Timestamp (System.currentTimeMillis ()));
                    accountTransfer.setInMoney (paymentAccount.getRemainMoney ());
                    accountTransfer.setRemainMoney (paymentAccount.getRemainMoney ());
                    addPaymentTransfer (accountTransfer);
                }
                else
                {
                    paymentAccount = paymentAccountDao.save (paymentAccount);
                }
                if(PurchaseChargeProperties.getAllowCachePaymentAccount ()) 
                {
                    DataCache.refreshDataToCache (PaymentAccount.class.getName (), paymentAccountDao);
                }
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage ());
                throw new PcServiceException (e.getMessage ());
            }
            return paymentAccount;
        }
        return null;
    }

    @Override
    public PaymentAccount updatePaymentAccount (PaymentAccount paymentAccount) throws PcServiceException
    {
        if (null != paymentAccount)
        {
            PaymentAccount dbPAccount = queryService.getPaymentAccount (paymentAccount.getId ());
            if (null != dbPAccount)
            {
                if (PurchaseChargeProperties.getDefaultPaymentAccount ().equals (dbPAccount.getName ()))
                {
                    throw new PcServiceException (
                                                  PurchaseChargeProperties.getInstance ()
                                                                          .getConfigFormatted ("pc.systemPaymentAccountCannotBeUpdated",
                                                                                               dbPAccount.getName ()));
                }
            }
            paymentAccount = paymentAccountDao.save (paymentAccount);
            if(PurchaseChargeProperties.getAllowCachePaymentAccount ()) 
            {
                DataCache.refreshDataToCache (PaymentAccount.class.getName (), paymentAccountDao);
            }
            return paymentAccount;
        }
        return null;
    }

    @Override
    public void deletePaymentAccount (List <PaymentAccount> paymentAccounts) throws PcServiceException
    {
        if (CollectionUtils.isNotEmpty (paymentAccounts))
        {
            List <PaymentAccount> deletablePaymentAccounts = new ArrayList <PaymentAccount> ();
            List <String> notDeletablePaymentAccounts = new ArrayList <String> ();
            for (PaymentAccount pa : paymentAccounts)
            {
                PaymentAccount dbPAccount = queryService.getPaymentAccount (pa.getId ());
                if (null != dbPAccount)
                {
                    if (PurchaseChargeProperties.getDefaultPaymentAccount ().equals (dbPAccount.getName ()))
                    {
                        throw new PcServiceException (
                                                      PurchaseChargeProperties.getInstance ()
                                                                              .getConfigFormatted ("pc.systemPaymentAccountCannotBeDeleted",
                                                                                                   dbPAccount.getName ()));
                    }
                }
                if (CollectionUtils.isNotEmpty (paymentTransferDao.findByAccount (pa.getId ())))
                {
                    notDeletablePaymentAccounts.add (dbPAccount.getName ());
                }
                else
                {
                    deletablePaymentAccounts.add (pa);
                }
            }
            if (0 == deletablePaymentAccounts.size () || deletablePaymentAccounts.size () < paymentAccounts.size ())
            {
                throw new PcServiceException (
                                              PurchaseChargeProperties.getInstance ()
                                                                      .getConfigFormatted ("pc.paymentAccountCannotBeDeleted",
                                                                                           notDeletablePaymentAccounts.toString ()));
            }
            paymentAccountDao.delete (deletablePaymentAccounts);
            if(PurchaseChargeProperties.getAllowCachePaymentAccount ()) 
            {
                DataCache.refreshDataToCache (PaymentAccount.class.getName (), paymentAccountDao);
            }
        }
    }

    @Override
    public AccountingType addAccountingType (AccountingType accountingType) throws PcServiceException
    {
        if (null != accountingType)
        {
            accountingType = accountingTypeDao.save (accountingType);
            DataCache.refreshDataToCache (AccountingType.class.getName (), accountingTypeDao);
            return accountingType;
        }
        return null;
    }

    @Override
    public AccountingType updateAccountingType (AccountingType accountingType) throws PcServiceException
    {
        return addAccountingType (accountingType);
    }

    @Override
    public void deleteAccountingType (List <AccountingType> accountingTypes) throws PcServiceException
    {
        if (CollectionUtils.isNotEmpty (accountingTypes))
        {
            List <AccountingType> deletableExpenseTypes = new ArrayList <AccountingType> ();
            List <String> notDeletableType = new ArrayList <String> ();
            for (AccountingType expenseType : accountingTypes)
            {
                List <Accounting> expenses = accountingDao.findByType (expenseType.getId ());
                if (CollectionUtils.isEmpty (expenses))
                {
                    deletableExpenseTypes.add (expenseType);
                }
                else
                {
                    notDeletableType.add (queryService.getAccountingType (expenseType.getId ()).getName ());
                }
            }
            if (0 == deletableExpenseTypes.size () || accountingTypes.size () > deletableExpenseTypes.size ())
            {
                logger.warn ("The part of expenseType that you want to delete can be deleted");
                throw new PcServiceException (
                                              PurchaseChargeProperties.getInstance ()
                                                                      .getConfigFormatted ("pc.expenseTypeCannotBeDeleted",
                                                                                           notDeletableType.toString ()));
            }
            accountingTypeDao.delete (deletableExpenseTypes);
            DataCache.refreshDataToCache (AccountingType.class.getName (), accountingTypeDao);
        }
    }

    @Override
    @Transactional
    public Accounting addAccounting (Accounting accounting) throws PcServiceException
    {
        Accounting result = null;
        if (null != accounting && null != accounting.getType () && null != accounting.getPaymentAccount ())
        {
            if (null == accounting.getCreated ())
            {
                accounting.setCreated (new Timestamp (System.currentTimeMillis ()));
            }
            AccountingType type = queryService.getAccountingType (accounting.getType ().getId ());
            if (AccountingModeCode.IN_COME == type.getAccountingMode ())
            {
                float money = accounting.getMoney ();
                if (money < 0)
                {
                    accounting.setMoney (Math.abs (money));
                }
            }
            else if (AccountingModeCode.OUT_LAY == type.getAccountingMode ())
            {
                float money = accounting.getMoney ();
                if (money > 0)
                {
                    accounting.setMoney (-money);
                }
            }

            try
            {
                result = accountingDao.save (accounting);
                if (null != result)
                {
                    PaymentAccount paymentAccount = result.getPaymentAccount ();
                    if (AccountingModeCode.IN_COME == result.getType ().getAccountingMode ())
                    {
                        // paymentAccount.setRemainMoney
                        // (paymentAccount.getRemainMoney () + result.getMoney
                        // ());
                    }
                    else if (AccountingModeCode.OUT_LAY == result.getType ().getAccountingMode ())
                    {
                        // paymentAccount.setRemainMoney
                        // (paymentAccount.getRemainMoney () + result.getMoney
                        // ());
                    }
                    // paymentAccountDao.save (paymentAccount);
                    PaymentTransfer paymentTransfer = new PaymentTransfer ();
                    paymentTransfer.setTargetAccount (paymentAccount);
                    paymentTransfer.setSource (result.getType ().getId () + "");
                    paymentTransfer.setUserCreatedBy (result.getUserCreatedBy ());
                    if (AccountingModeCode.IN_COME == result.getType ().getAccountingMode ())
                    {
                        paymentTransfer.setTransferTypeCode (PaymentTransferTypeCode.ACCOUNTING_IN);
                        paymentTransfer.setInMoney (result.getMoney ());
                    }
                    else if (AccountingModeCode.OUT_LAY == result.getType ().getAccountingMode ())
                    {
                        paymentTransfer.setTransferTypeCode (PaymentTransferTypeCode.ACCOUNTING_OUT);
                        paymentTransfer.setOutMoney (-result.getMoney ());
                    }
                    addPaymentTransfer (paymentTransfer);
                }
            }
            catch (PcServiceException e) 
            {
                throw e;
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage ());
                throw new PcServiceException (e.getMessage ());
            }
        }
        return result;
    }

    @Override
    public Accounting updateAccounting (Accounting accounting) throws PcServiceException
    {
        return accountingDao.save (accounting);
    }

    @Override
    @Transactional
    public void deleteAccounting (Accounting accounting) throws PcServiceException
    {
        try
        {
            Accounting dbAccounting = accountingDao.findOne (accounting.getId ());
            PaymentAccount paymentAccount = dbAccounting.getPaymentAccount ();
            if (AccountingModeCode.IN_COME == dbAccounting.getType ().getAccountingMode ())
            {
                //paymentAccount.setRemainMoney (paymentAccount.getRemainMoney () - dbAccounting.getMoney ());
            }
            else if (AccountingModeCode.OUT_LAY == dbAccounting.getType ().getAccountingMode ())
            {
                //paymentAccount.setRemainMoney (paymentAccount.getRemainMoney () - dbAccounting.getMoney ());
            }
            accountingDao.delete (accounting);
            // paymentAccountDao.save (paymentAccount);
            PaymentTransfer paymentTransfer = new PaymentTransfer ();
            paymentTransfer.setTargetAccount (paymentAccount);
            paymentTransfer.setSource (dbAccounting.getType ().getId () + "");
            paymentTransfer.setUserCreatedBy (dbAccounting.getUserCreatedBy ());
            if (AccountingModeCode.IN_COME == dbAccounting.getType ().getAccountingMode ())
            {
                paymentTransfer.setTransferTypeCode (PaymentTransferTypeCode.ACCOUNTING_OUT);
                paymentTransfer.setOutMoney (dbAccounting.getMoney ());
            }
            else if (AccountingModeCode.OUT_LAY == dbAccounting.getType ().getAccountingMode ())
            {
                paymentTransfer.setTransferTypeCode (PaymentTransferTypeCode.ACCOUNTING_IN);
                paymentTransfer.setInMoney (-dbAccounting.getMoney ());
            }
            addPaymentTransfer (paymentTransfer);
        }
        catch (PcServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            logger.warn (e.getMessage ());
            throw new PcServiceException (e.getMessage ());
        }
    }

    @Override
    public void deleteAccounting (List <Accounting> accountings) throws PcServiceException
    {
        try
        {
            if (CollectionUtils.isNotEmpty (accountings))
            {
                for (Accounting accounting : accountings)
                {
                    deleteAccounting (accounting);
                }
            }
        }
        catch (Exception e)
        {
            logger.warn (e.getMessage ());
            throw new PcServiceException (e.getMessage ());
        }
    }

    @Override
    public PaymentWay addPaymentWay (PaymentWay paymentWay) throws PcServiceException
    {
        if (null != paymentWay)
        {
            paymentWay = paymentWayDao.save (paymentWay);
            DataCache.refreshDataToCache (PaymentWay.class.getName (), paymentWayDao);
            return paymentWay;
        }
        return null;
    }

    @Override
    public PaymentWay updatePaymentWay (PaymentWay paymentWay) throws PcServiceException
    {
        if (null != paymentWay)
        {
            String[] configuredPWays = PurchaseChargeProperties.getDefaultPaymentWay ();
            if (null != configuredPWays && configuredPWays.length > 0)
            {
                PaymentWay dbPaymentWay = queryService.getPaymentWay (paymentWay.getId ());
                for (String pWay : configuredPWays)
                {
                    if (pWay.equals (dbPaymentWay.getName ()))
                    {
                        throw new PcServiceException (
                                                      PurchaseChargeProperties.getInstance ()
                                                                              .getConfigFormatted ("pc.systemPaymentWayCannotBeUpdated",
                                                                                                   pWay));
                    }
                }
            }
            return addPaymentWay (paymentWay);
        }
        return null;
    }

    @Override
    public void deletePaymentWay (List <PaymentWay> paymentWays) throws PcServiceException
    {
        if (CollectionUtils.isNotEmpty (paymentWays))
        {
            List <PaymentWay> deletablePaymentWays = new ArrayList <PaymentWay> ();
            List <String> notDeletablePaymentWays = new ArrayList <String> ();
            String[] configuredPWays = PurchaseChargeProperties.getDefaultPaymentWay ();
            for (PaymentWay pw : paymentWays)
            {
                PaymentWay dbPaymentWay = queryService.getPaymentWay (pw.getId ());
                if (null != configuredPWays && configuredPWays.length > 0)
                {
                    for (String pWay : configuredPWays)
                    {
                        if (pWay.equals (dbPaymentWay.getName ()))
                        {
                            throw new PcServiceException (
                                                          PurchaseChargeProperties.getInstance ()
                                                                                  .getConfigFormatted ("pc.systemPaymentWayCannotBeDeleted",
                                                                                                       pWay));
                        }
                    }
                }
                if (CollectionUtils.isEmpty (customerPaymentRecordDao.findByPaymentWay (pw.getId ()))
                    && CollectionUtils.isEmpty (providerPaymentRecordDao.findByPaymentWay (pw.getId ())))
                {
                    deletablePaymentWays.add (pw);
                }
                else
                {
                    notDeletablePaymentWays.add (dbPaymentWay.getName ());
                }
            }
            if (deletablePaymentWays.size () == 0 || deletablePaymentWays.size () < paymentWays.size ())
            {
                throw new PcServiceException (
                                              PurchaseChargeProperties.getInstance ()
                                                                      .getConfigFormatted ("pc.paymentWayCannotBeDeleted",
                                                                                           notDeletablePaymentWays.toString ()));
            }
            paymentWayDao.delete (deletablePaymentWays);
            DataCache.refreshDataToCache (PaymentWay.class.getName (), paymentWayDao);
        }
    }

    @Override
    @Transactional
    public Project addProject (Project project) throws PcServiceException
    {
        Project dbProject = null;
        if (null != project)
        {
            dbProject = projectDao.save (project);
            if (CollectionUtils.isNotEmpty (project.getOrders ()))
            {
                OrderOut dbOrder = null;
                for (OrderOut order : project.getOrders ())
                {
                    dbOrder = orderOutDao.findOne (order.getId ());
                    dbOrder.setProject (dbProject);
                    orderOutDao.save (dbOrder);
                }
            }
        }
        return dbProject;
    }

    @Override
    @Transactional
    public Project updateProject (Project project) throws PcServiceException
    {
        Project dbProject = null;
        if (null != project && 0 != project.getId ())
        {
            Project oldProject = projectDao.findOne (project.getId ());
            if (null != oldProject)
            {
                String comment = oldProject.getComment ();
                if (StringUtils.isNotBlank (project.getComment ()))
                {
                    comment += ((StringUtils.isNotBlank (comment)) ? "  " : "") + project.getComment ();
                }
                project.setComment (comment);
                dbProject = projectDao.save (project);
                if (CollectionUtils.isNotEmpty (oldProject.getOrders ()))
                {
                    for (OrderOut order : oldProject.getOrders ())
                    {
                        order.setProject (null);
                        orderOutDao.save (order);
                    }
                }
                if (CollectionUtils.isNotEmpty (project.getOrders ()))
                {
                    for (OrderOut order : project.getOrders ())
                    {
                        OrderOut dbOrder = orderOutDao.findOne (order.getId ());
                        dbOrder.setProject (dbProject);
                        orderOutDao.save (dbOrder);
                    }
                }
            }
        }
        return dbProject;
    }

    @Override
    public void deleteProject (List <Project> projects) throws PcServiceException
    {
        if (CollectionUtils.isNotEmpty (projects))
        {
            List <Project> deletableProjects = new ArrayList <Project> ();
            List <String> notDeletableProject = new ArrayList <String> ();
            for (Project project : projects)
            {
                Project dbProject = projectDao.findOne (project.getId ());
                if (null != dbProject)
                {
                    List <OrderOut> orders = dbProject.getOrders ();
                    if (CollectionUtils.isEmpty (orders)
                        || ProjectStatusCode.CANCELED.equals (dbProject.getStatusCode ()))
                    {
                        deletableProjects.add (project);
                    }
                    else
                    {
                        notDeletableProject.add (dbProject.getName ());
                    }
                }
            }
            if (0 == deletableProjects.size () || projects.size () > deletableProjects.size ())
            {
                logger.warn ("The part of project that you want to delete can be deleted");
                throw new PcServiceException (
                                              PurchaseChargeProperties.getInstance ()
                                                                      .getConfigFormatted ("pc.projectCannotBeDeleted",
                                                                                           notDeletableProject.toString ()));
            }
            projectDao.delete (deletableProjects);
        }
    }

    @Override
    public boolean updateProjectStatus (long projectId, ProjectStatusCode statusCode, String comment)
                                                                                                     throws PcServiceException
    {
        Project dbProject = projectDao.findOne (projectId);
        if (null != dbProject)
        {
            if (ProjectStatusCode.NEW.equals (dbProject.getStatusCode ())
                || ProjectStatusCode.APPROVED.equals (dbProject.getStatusCode ())
                || ProjectStatusCode.PROCESSED.equals (dbProject.getStatusCode ()))
            {
                String comments = dbProject.getComment ();
                if (StringUtils.isNotBlank (comment))
                {
                    comments += ((StringUtils.isNotBlank (comments)) ? "  " : "") + comment;
                }
                dbProject.setComment (comments);
                dbProject.setStatusCode (statusCode);
                projectDao.save (dbProject);
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public OrderDelivery updateOrderDelivery (OrderDelivery orderDelivery) throws PcServiceException
    {
        if (null != orderDelivery)
        {
            OrderDelivery dbOrderDelivery = orderDeliveryDao.save (orderDelivery);
            if (null != dbOrderDelivery && dbOrderDelivery.getId () != orderDelivery.getId ())
            {
                OrderOut order = dbOrderDelivery.getOrderOut ();
                if (null != order)
                {
                    updateOrderOutStatus (order.getId (), OrderStatusCode.SHIPPED, "", null);
                }
            }
            return dbOrderDelivery;
        }
        return null;
    }

    @Override
    public DeliveryCompany addDeliveryCompany (DeliveryCompany deliveryCompany) throws PcServiceException
    {
        if (null != deliveryCompany)
        {
            deliveryCompany = deliveryCompanyDao.save (deliveryCompany);
            DataCache.refreshDataToCache (DeliveryCompany.class.getName (), deliveryCompanyDao);
            return deliveryCompany;
        }
        return null;
    }

    @Override
    public DeliveryCompany updateDeliveryCompany (DeliveryCompany deliveryCompany) throws PcServiceException
    {
        if (null != deliveryCompany)
        {
            String[] defaultDeliveryCompanies = PurchaseChargeProperties.getDefaultDelivery ();
            if (null != defaultDeliveryCompanies && defaultDeliveryCompanies.length > 0)
            {
                for (String defaultDelivery : defaultDeliveryCompanies)
                {
                    if (defaultDelivery.equals (deliveryCompany.getName ()))
                    {
                        throw new PcServiceException (
                                                      PurchaseChargeProperties.getInstance ()
                                                                              .getConfigFormatted ("pc.systemDeliveryCannotBeUpdated",
                                                                                                   deliveryCompany.getName ()));
                    }
                }
            }
        }
        return addDeliveryCompany (deliveryCompany);
    }

    @Override
    public void deleteDeliveryCompany (List <DeliveryCompany> deliveryCompanies) throws PcServiceException
    {
        if (CollectionUtils.isNotEmpty (deliveryCompanies))
        {
            List <DeliveryCompany> deletableDeliverys = new ArrayList <DeliveryCompany> ();
            List <String> notDeletableDelivery = new ArrayList <String> ();
            String[] defaultDeliveryCompanies = PurchaseChargeProperties.getDefaultDelivery ();
            for (DeliveryCompany deliveryCompany : deliveryCompanies)
            {
                DeliveryCompany dbDeliveryCompany = queryService.getDeliveryCompany (deliveryCompany.getId ());
                if (null != defaultDeliveryCompanies && defaultDeliveryCompanies.length > 0)
                {
                    for (String defaultDelivery : defaultDeliveryCompanies)
                    {
                        if (defaultDelivery.equals (dbDeliveryCompany.getName ()))
                        {
                            throw new PcServiceException (
                                                          PurchaseChargeProperties.getInstance ()
                                                                                  .getConfigFormatted ("pc.systemDeliveryCannotBeDeleted",
                                                                                                       defaultDelivery));
                        }
                    }
                }
                if (CollectionUtils.isNotEmpty (orderDeliveryDao.findByCompany (deliveryCompany.getId ())))
                {
                    notDeletableDelivery.add (dbDeliveryCompany.getName ());
                }
                else
                {
                    deletableDeliverys.add (deliveryCompany);
                }
            }

            if (0 == deletableDeliverys.size () || deliveryCompanies.size () > deletableDeliverys.size ())
            {
                logger.warn ("The part of deliveryCompanies that you want to delete can be deleted");
                throw new PcServiceException (
                                              PurchaseChargeProperties.getInstance ()
                                                                      .getConfigFormatted ("pc.deliveryCannotBeDeleted",
                                                                                           notDeletableDelivery.toString ()));
            }
            deliveryCompanyDao.delete (deletableDeliverys);
            DataCache.refreshDataToCache (DeliveryCompany.class.getName (), deliveryCompanyDao);
        }
    }

    @Override
    public void resetData (String userId, String password) throws PcServiceException
    {
        if(StringUtils.isNotBlank (userId) && StringUtils.isNotBlank (password)) 
        {
            User user = userDao.findByUserId (userId);
            if(null != user && user.getPwd ().equals (password)) 
            {
                resetData ();
            }
        }
    }
    
    private void resetData () throws PcServiceException 
    {
        
    }

    private void prepareForAddOrderOut (OrderOut order) 
    {
        if(OrderTypeCode.OUT_RETURN.equals (order.getTypeCode ())) 
        {
            List <OrderOutItem> orderOutItems = new ArrayList <OrderOutItem> ();
            if(CollectionUtils.isNotEmpty (order.getOrderItems ())) 
            {
                for(OrderOutItem orderOutItem : order.getOrderItems ()) 
                {
                    orderOutItem.setAmount (-orderOutItem.getAmount ());
                    orderOutItem.setSum (orderOutItem.getUnitPrice () * orderOutItem.getAmount ());
                    orderOutItems.add (orderOutItem);
                }
            }
        }
    }
    /**
     * 计算出库单应收，折后应收和利润
     * 
     * @param order
     */
    private void calculateOrderOutMoney (OrderOut order)
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
                    //float goodsCostPrice = queryService.getGoods (orderItem.getGoods ().getId ()).getAveragePrice ();
                    float goodsCostPrice = queryService.getGoods (orderItem.getGoods ().getId ()).getImportPrice ();
                    GoodsStorage goodsStorage = goodsStorageDao.findByGoodsAndDepository (orderItem.getGoods ().getId (), orderItem.getDepository ().getId ());
                    if(null != goodsStorage) 
                    {
                        goodsCostPrice = goodsStorage.getPrice ();
                    }
                    profit += (orderItem.getUnitPrice () - goodsCostPrice) * orderItem.getAmount ();
                }
                order.setDealMoney (sum);
                order.setReceivable (sum * order.getDiscount ());
                order.setProfit (profit);
                /*if (OrderTypeCode.OUT_RETURN == order.getTypeCode ())
                {
                    order.setProfit (-profit);
                    order.setDealMoney (-sum);
                    order.setReceivable (-(sum * order.getDiscount ()));
                }*/
            }
        }
    }

    private void prepareForAddOrderIn (OrderIn order) 
    {
        if(OrderTypeCode.IN_RETURN.equals (order.getTypeCode ())) 
        {
            List <OrderInItem> orderInItems = new ArrayList <OrderInItem> ();
            if(CollectionUtils.isNotEmpty (order.getOrderItems ())) 
            {
                for(OrderInItem orderInItem : order.getOrderItems ()) 
                {
                    orderInItem.setAmount (-orderInItem.getAmount ());
                    orderInItem.setSum (orderInItem.getUnitPrice () * orderInItem.getAmount ());
                    orderInItems.add (orderInItem);
                }
            }
        }
    }
    private void calculateOrderInMoney (OrderIn order) 
    {
        float sum = 0;
        List <OrderInItem> orderItems = order.getOrderItems ();
        if (!CollectionUtils.isEmpty (orderItems))
        {
            for (OrderInItem orderItem : orderItems)
            {
                sum += orderItem.getUnitPrice () * orderItem.getAmount ();
            }
            order.setDealMoney (sum);
            order.setReceivable (sum * order.getDiscount ());
            /*if (OrderTypeCode.IN_RETURN == order.getTypeCode ())
            {
                order.setDealMoney (-sum);
                order.setReceivable (-(sum * order.getDiscount ()));
            }*/
        }
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

    public void setOrderInDao (OrderInDao orderInDao)
    {
        this.orderInDao = orderInDao;
    }

    public void setOrderOutItemDao (OrderOutItemDao orderOutItemDao)
    {
        this.orderOutItemDao = orderOutItemDao;
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

    public void setGoodsStorageDao (GoodsStorageDao goodsStorageDao)
    {
        this.goodsStorageDao = goodsStorageDao;
    }

    public void setGoodsPictureDao (GoodsPictureDao goodsPictureDao)
    {
        this.goodsPictureDao = goodsPictureDao;
    }

    public void setGoodsIssueDao (GoodsIssueDao goodsIssueDao)
    {
        this.goodsIssueDao = goodsIssueDao;
    }

    public void setGoodsSerialNumberDao (GoodsSerialNumberDao goodsSerialNumberDao)
    {
        this.goodsSerialNumberDao = goodsSerialNumberDao;
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

    public void setGoodsStorageTransferDao (GoodsStorageTransferDao goodsStorageTransferDao)
    {
        this.goodsStorageTransferDao = goodsStorageTransferDao;
    }

    public void setGoodsStorageCheckDao (GoodsStorageCheckDao goodsStorageCheckDao)
    {
        this.goodsStorageCheckDao = goodsStorageCheckDao;
    }

    public void setGoodsStoragePriceReviseDao (GoodsStoragePriceReviseDao goodsStoragePriceReviseDao)
    {
        this.goodsStoragePriceReviseDao = goodsStoragePriceReviseDao;
    }

    public void setPaymentTransferDao (PaymentTransferDao paymentTransferDao)
    {
        this.paymentTransferDao = paymentTransferDao;
    }

    public void setCustomerPaymentRecordDao (CustomerPaymentRecordDao customerPaymentRecordDao)
    {
        this.customerPaymentRecordDao = customerPaymentRecordDao;
    }

    public void setProviderPaymentRecordDao (ProviderPaymentRecordDao providerPaymentRecordDao)
    {
        this.providerPaymentRecordDao = providerPaymentRecordDao;
    }

    public void setProjectDao (ProjectDao projectDao)
    {
        this.projectDao = projectDao;
    }

    public void setOrderDeliveryDao (OrderDeliveryDao orderDeliveryDao)
    {
        this.orderDeliveryDao = orderDeliveryDao;
    }

    public void setCustomerLevelDao (CustomerLevelDao customerLevelDao)
    {
        this.customerLevelDao = customerLevelDao;
    }

    public void setDeliveryCompanyDao (DeliveryCompanyDao deliveryCompanyDao)
    {
        this.deliveryCompanyDao = deliveryCompanyDao;
    }

    public void setQueryService (QueryService queryService)
    {
        this.queryService = queryService;
    }

}
