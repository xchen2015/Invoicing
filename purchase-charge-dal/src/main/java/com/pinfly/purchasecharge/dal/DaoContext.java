package com.pinfly.purchasecharge.dal;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
import com.pinfly.purchasecharge.dal.out.OrderReceiptDao;
import com.pinfly.purchasecharge.dal.usersecurity.AuthorityDao;
import com.pinfly.purchasecharge.dal.usersecurity.RoleDao;
import com.pinfly.purchasecharge.dal.usersecurity.UserDao;

public class DaoContext
{
    private static ApplicationContext applicationContext;
    private static UserDao userDao;
    private static RoleDao roleDao;
    private static AuthorityDao authorityDao;
    private static AccountingDao accountingDao;
    private static AccountingTypeDao accountingTypeDao;
    private static ContractDao contractDao;
    private static ProjectDao projectDao;

    private static CustomerDao customerDao;
    private static CustomerContactDao customerContactDao;
    private static CustomerTypeDao customerTypeDao;
    private static CustomerLevelDao customerLevelDao;
    private static CustomerPaymentDao customerPaymentDao;
    private static CustomerPaymentRecordDao customerPaymentRecordDao;
    private static OrderOutDao orderOutDao;
    private static OrderOutItemDao orderOutItemDao;
    private static OrderReceiptDao orderReceiptDao;
    private static OrderDeliveryDao orderDeliveryDao;

    private static ProviderDao providerDao;
    private static ProviderContactDao providerContactDao;
    private static ProviderTypeDao providerTypeDao;
    private static ProviderPaymentDao providerPaymentDao;
    private static ProviderPaymentRecordDao providerPaymentRecordDao;
    private static OrderInDao orderInDao;
    private static OrderInItemDao orderInItemDao;

    private static GoodsDao goodsDao;
    private static GoodsStorageDao goodsStorageDao;
    private static GoodsDepositoryDao goodsDepositoryDao;
    private static GoodsTypeDao goodsTypeDao;
    private static GoodsUnitDao goodsUnitDao;
    private static GoodsPictureDao goodsPictureDao;
    private static GoodsIssueDao goodsIssueDao;
    private static GoodsSerialNumberDao goodsSerialNumberDao;
    private static GoodsStorageTransferDao goodsStorageTransferDao;
    private static GoodsStorageCheckDao goodsStorageCheckDao;
    private static GoodsStoragePriceReviseDao goodsStoragePriceReviseDao;

    private static PaymentAccountDao paymentAccountDao;
    private static PaymentWayDao paymentWayDao;
    private static PaymentTransferDao paymentTransferDao;
    private static RegionDao regionDao;
    private static DeliveryCompanyDao deliveryCompanyDao;

    static
    {
        applicationContext = new ClassPathXmlApplicationContext ("classpath:META-INF/spring/purchase-charge-dao.xml");
    }

    public static ApplicationContext getApplicationContext ()
    {
        return applicationContext;
    }

    public static UserDao getUserDao ()
    {
        if (null == userDao)
        {
            userDao = applicationContext.getBean (UserDao.class);
        }
        return userDao;
    }

    public static RoleDao getRoleDao ()
    {
        if (null == roleDao)
        {
            roleDao = applicationContext.getBean (RoleDao.class);
        }
        return roleDao;
    }

    public static AuthorityDao getAuthorityDao ()
    {
        if (null == authorityDao)
        {
            authorityDao = applicationContext.getBean (AuthorityDao.class);
        }
        return authorityDao;
    }

    public static AccountingDao getAccountingDao ()
    {
        if (null == accountingDao)
        {
            accountingDao = applicationContext.getBean (AccountingDao.class);
        }
        return accountingDao;
    }

    public static AccountingTypeDao getAccountingTypeDao ()
    {
        if (null == accountingTypeDao)
        {
            accountingTypeDao = applicationContext.getBean (AccountingTypeDao.class);
        }
        return accountingTypeDao;
    }

    public static ContractDao getContractDao ()
    {
        if (null == contractDao)
        {
            contractDao = applicationContext.getBean (ContractDao.class);
        }
        return contractDao;
    }

    public static ProjectDao getProjectDao ()
    {
        if (null == projectDao)
        {
            projectDao = applicationContext.getBean (ProjectDao.class);
        }
        return projectDao;
    }

    public static CustomerDao getCustomerDao ()
    {
        if (null == customerDao)
        {
            customerDao = applicationContext.getBean (CustomerDao.class);
        }
        return customerDao;
    }

    public static CustomerTypeDao getCustomerTypeDao ()
    {
        if (null == customerTypeDao)
        {
            customerTypeDao = applicationContext.getBean (CustomerTypeDao.class);
        }
        return customerTypeDao;
    }

    public static CustomerLevelDao getCustomerLevelDao ()
    {
        if (null == customerLevelDao)
        {
            customerLevelDao = applicationContext.getBean (CustomerLevelDao.class);
        }
        return customerLevelDao;
    }

    public static GoodsDao getGoodsDao ()
    {
        if (null == goodsDao)
        {
            goodsDao = applicationContext.getBean (GoodsDao.class);
        }
        return goodsDao;
    }

    public static GoodsStorageDao getGoodsStorageDao ()
    {
        if (null == goodsStorageDao)
        {
            goodsStorageDao = applicationContext.getBean (GoodsStorageDao.class);
        }
        return goodsStorageDao;
    }

    public static GoodsDepositoryDao getGoodsDepositoryDao ()
    {
        if (null == goodsDepositoryDao)
        {
            goodsDepositoryDao = applicationContext.getBean (GoodsDepositoryDao.class);
        }
        return goodsDepositoryDao;
    }

    public static GoodsTypeDao getGoodsTypeDao ()
    {
        if (null == goodsTypeDao)
        {
            goodsTypeDao = applicationContext.getBean (GoodsTypeDao.class);
        }
        return goodsTypeDao;
    }

    public static GoodsUnitDao getGoodsUnitDao ()
    {
        if (null == goodsUnitDao)
        {
            goodsUnitDao = applicationContext.getBean (GoodsUnitDao.class);
        }
        return goodsUnitDao;
    }

    public static GoodsPictureDao getGoodsPictureDao ()
    {
        if (null == goodsPictureDao)
        {
            goodsPictureDao = applicationContext.getBean (GoodsPictureDao.class);
        }
        return goodsPictureDao;
    }

    public static GoodsIssueDao getGoodsIssueDao ()
    {
        if (null == goodsIssueDao)
        {
            goodsIssueDao = applicationContext.getBean (GoodsIssueDao.class);
        }
        return goodsIssueDao;
    }

    public static GoodsSerialNumberDao getGoodsSerialNumberDao ()
    {
        if (null == goodsSerialNumberDao)
        {
            goodsSerialNumberDao = applicationContext.getBean (GoodsSerialNumberDao.class);
        }
        return goodsSerialNumberDao;
    }

    public static PaymentAccountDao getPaymentAccountDao ()
    {
        if (null == paymentAccountDao)
        {
            paymentAccountDao = applicationContext.getBean (PaymentAccountDao.class);
        }
        return paymentAccountDao;
    }

    public static PaymentWayDao getPaymentWayDao ()
    {
        if (null == paymentWayDao)
        {
            paymentWayDao = applicationContext.getBean (PaymentWayDao.class);
        }
        return paymentWayDao;
    }

    public static RegionDao getRegionDao ()
    {
        if (null == regionDao)
        {
            regionDao = applicationContext.getBean (RegionDao.class);
        }
        return regionDao;
    }

    public static CustomerContactDao getCustomerContactDao ()
    {
        if (null == customerContactDao)
        {
            customerContactDao = applicationContext.getBean (CustomerContactDao.class);
        }
        return customerContactDao;
    }

    public static CustomerPaymentDao getCustomerPaymentDao ()
    {
        if (null == customerPaymentDao)
        {
            customerPaymentDao = applicationContext.getBean (CustomerPaymentDao.class);
        }
        return customerPaymentDao;
    }

    public static OrderOutDao getOrderOutDao ()
    {
        if (null == orderOutDao)
        {
            orderOutDao = applicationContext.getBean (OrderOutDao.class);
        }
        return orderOutDao;
    }

    public static OrderOutItemDao getOrderOutItemDao ()
    {
        if (null == orderOutItemDao)
        {
            orderOutItemDao = applicationContext.getBean (OrderOutItemDao.class);
        }
        return orderOutItemDao;
    }

    public static ProviderDao getProviderDao ()
    {
        if (null == providerDao)
        {
            providerDao = applicationContext.getBean (ProviderDao.class);
        }
        return providerDao;
    }

    public static ProviderContactDao getProviderContactDao ()
    {
        if (null == providerContactDao)
        {
            providerContactDao = applicationContext.getBean (ProviderContactDao.class);
        }
        return providerContactDao;
    }

    public static ProviderTypeDao getProviderTypeDao ()
    {
        if (null == providerTypeDao)
        {
            providerTypeDao = applicationContext.getBean (ProviderTypeDao.class);
        }
        return providerTypeDao;
    }

    public static ProviderPaymentDao getProviderPaymentDao ()
    {
        if (null == providerPaymentDao)
        {
            providerPaymentDao = applicationContext.getBean (ProviderPaymentDao.class);
        }
        return providerPaymentDao;
    }

    public static OrderInDao getOrderInDao ()
    {
        if (null == orderInDao)
        {
            orderInDao = applicationContext.getBean (OrderInDao.class);
        }
        return orderInDao;
    }

    public static OrderInItemDao getOrderInItemDao ()
    {
        if (null == orderInItemDao)
        {
            orderInItemDao = applicationContext.getBean (OrderInItemDao.class);
        }
        return orderInItemDao;
    }

    public static CustomerPaymentRecordDao getCustomerPaymentRecordDao ()
    {
        if (null == customerPaymentRecordDao)
        {
            customerPaymentRecordDao = applicationContext.getBean (CustomerPaymentRecordDao.class);
        }
        return customerPaymentRecordDao;
    }

    public static ProviderPaymentRecordDao getProviderPaymentRecordDao ()
    {
        if (null == providerPaymentRecordDao)
        {
            providerPaymentRecordDao = applicationContext.getBean (ProviderPaymentRecordDao.class);
        }
        return providerPaymentRecordDao;
    }
    
    public static GoodsStorageTransferDao getGoodsStorageTransferDao ()
    {
        if (null == goodsStorageTransferDao)
        {
            goodsStorageTransferDao = applicationContext.getBean (GoodsStorageTransferDao.class);
        }
        return goodsStorageTransferDao;
    }
    
    public static GoodsStorageCheckDao getGoodsStorageCheckDao ()
    {
        if (null == goodsStorageCheckDao)
        {
            goodsStorageCheckDao = applicationContext.getBean (GoodsStorageCheckDao.class);
        }
        return goodsStorageCheckDao;
    }

    public static GoodsStoragePriceReviseDao getGoodsStoragePriceReviseDao ()
    {
        if (null == goodsStoragePriceReviseDao)
        {
            goodsStoragePriceReviseDao = applicationContext.getBean (GoodsStoragePriceReviseDao.class);
        }
        return goodsStoragePriceReviseDao;
    }

    public static PaymentTransferDao getPaymentTransferDao ()
    {
        if (null == paymentTransferDao)
        {
            paymentTransferDao = applicationContext.getBean (PaymentTransferDao.class);
        }
        return paymentTransferDao;
    }

    public static OrderReceiptDao getOrderReceiptDao ()
    {
        if (null == orderReceiptDao)
        {
            orderReceiptDao = applicationContext.getBean (OrderReceiptDao.class);
        }
        return orderReceiptDao;
    }

    public static OrderDeliveryDao getOrderDeliveryDao ()
    {
        if (null == orderDeliveryDao)
        {
            orderDeliveryDao = applicationContext.getBean (OrderDeliveryDao.class);
        }
        return orderDeliveryDao;
    }

    public static DeliveryCompanyDao getDeliveryCompanyDao ()
    {
        if (null == deliveryCompanyDao)
        {
            deliveryCompanyDao = applicationContext.getBean (DeliveryCompanyDao.class);
        }
        return deliveryCompanyDao;
    }

}
