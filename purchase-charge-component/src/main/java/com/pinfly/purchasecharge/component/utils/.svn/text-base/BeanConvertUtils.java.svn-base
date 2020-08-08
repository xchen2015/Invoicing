package com.pinfly.purchasecharge.component.utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import com.pinfly.purchasecharge.component.bean.AccountingBean;
import com.pinfly.purchasecharge.component.bean.AccountingTypeBean;
import com.pinfly.purchasecharge.component.bean.ContactBean;
import com.pinfly.purchasecharge.component.bean.CustomerBean;
import com.pinfly.purchasecharge.component.bean.CustomerLevelBean;
import com.pinfly.purchasecharge.component.bean.CustomerTypeBean;
import com.pinfly.purchasecharge.component.bean.GoodsBean;
import com.pinfly.purchasecharge.component.bean.GoodsDepositoryBean;
import com.pinfly.purchasecharge.component.bean.GoodsIssueBean;
import com.pinfly.purchasecharge.component.bean.GoodsSerialNumberBean;
import com.pinfly.purchasecharge.component.bean.GoodsStorageBean;
import com.pinfly.purchasecharge.component.bean.GoodsStorageCourseBean;
import com.pinfly.purchasecharge.component.bean.GoodsTypeBean;
import com.pinfly.purchasecharge.component.bean.GoodsUnitBean;
import com.pinfly.purchasecharge.component.bean.OrderBean;
import com.pinfly.purchasecharge.component.bean.OrderItemBean;
import com.pinfly.purchasecharge.component.bean.PaymentAccountBean;
import com.pinfly.purchasecharge.component.bean.PaymentBean;
import com.pinfly.purchasecharge.component.bean.PaymentRecordBean;
import com.pinfly.purchasecharge.component.bean.PaymentWayBean;
import com.pinfly.purchasecharge.component.bean.ProjectBean;
import com.pinfly.purchasecharge.component.bean.SearchRequestForm;
import com.pinfly.purchasecharge.component.bean.usersecurity.AuthorityBean;
import com.pinfly.purchasecharge.component.bean.usersecurity.RoleBean;
import com.pinfly.purchasecharge.component.bean.usersecurity.UserBean;
import com.pinfly.purchasecharge.component.controller.out.CustomerLevelManager;
import com.pinfly.purchasecharge.component.controller.out.OutOrderDeliveryManager;
import com.pinfly.purchasecharge.core.model.AccountingModeCode;
import com.pinfly.purchasecharge.core.model.AccountingSearchForm;
import com.pinfly.purchasecharge.core.model.GoodsStorageCourse;
import com.pinfly.purchasecharge.core.model.LogSearchForm;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.OrderSearchForm;
import com.pinfly.purchasecharge.core.model.OrderStatusCode;
import com.pinfly.purchasecharge.core.model.PaymentSearchForm;
import com.pinfly.purchasecharge.core.model.persistence.Accounting;
import com.pinfly.purchasecharge.core.model.persistence.AccountingType;
import com.pinfly.purchasecharge.core.model.persistence.PaymentAccount;
import com.pinfly.purchasecharge.core.model.persistence.PaymentWay;
import com.pinfly.purchasecharge.core.model.persistence.Project;
import com.pinfly.purchasecharge.core.model.persistence.goods.Goods;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsDepository;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsIssue;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsSerialNumber;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsStorage;
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
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerPayment;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerPaymentRecord;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerType;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOut;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOutItem;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.Authority;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.Role;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.User;
import com.pinfly.purchasecharge.core.util.Arith;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.dal.DaoContext;

public class BeanConvertUtils
{
    public static UserBean user2UserBean (User user)
    {
        if (null != user)
        {
            UserBean userBean = new UserBean ();
            BeanUtils.copyProperties (user, userBean, new String[]
            { "id" });
            userBean.setId (user.getId () + "");
            userBean.setBod (DateUtils.date2String (user.getBirthday (), DateUtils.DATE_PATTERN));
            userBean.setRoleBeans (roleList2RoleBeanList (user.getRoles ()));
            return userBean;
        }
        return null;
    }

    public static List <UserBean> userList2UserBeanList (List <User> userList)
    {
        if (null != userList)
        {
            List <UserBean> userBeanList = new ArrayList <UserBean> ();
            for (User user : userList)
            {
                userBeanList.add (user2UserBean (user));
            }
            return userBeanList;
        }
        return null;
    }

    public static User userBean2User (UserBean userBean)
    {
        if (null != userBean)
        {
            User user = new User ();
            BeanUtils.copyProperties (userBean, user, new String[]
            { "id" });
            if (StringUtils.isNotBlank (userBean.getId ()))
            {
                user.setId (Long.parseLong (userBean.getId ()));
            }
            if (StringUtils.isNotBlank (userBean.getBod ()))
            {
                user.setBirthday (DateUtils.string2Date (userBean.getBod (), DateUtils.DATE_PATTERN));
            }
            user.setRoles (roleBeanList2RoleList (userBean.getRoleBeans ()));
            return user;
        }
        return null;
    }

    public static RoleBean role2RoleBean (Role role)
    {
        if (null != role)
        {
            RoleBean roleBean = new RoleBean ();
            BeanUtils.copyProperties (role, roleBean, new String[]
            { "id" });
            roleBean.setId (role.getId () + "");
            roleBean.setUserBeans (userList2UserBeanList (role.getUsers ()));
            roleBean.setAuthorityBeans (authorityList2AuthorityBeanList (role.getAuthorities ()));
            return roleBean;
        }
        return null;
    }

    public static Role roleBean2Role (RoleBean roleBean)
    {
        if (null != roleBean)
        {
            Role role = new Role ();
            BeanUtils.copyProperties (roleBean, role, new String[]
            { "id" });
            role.setId (StringUtils.isNotBlank (roleBean.getId ()) ? Long.parseLong (roleBean.getId ()) : 0);
            role.setAuthorities (authorityBeanList2AuthorityList (roleBean.getAuthorityBeans ()));
            return role;
        }
        return null;
    }

    public static List <Role> roleBeanList2RoleList (List <RoleBean> roleBeans)
    {
        List <Role> roles = new ArrayList <Role> ();
        if (!CollectionUtils.isEmpty (roleBeans))
        {
            for (RoleBean roleBean : roleBeans)
            {
                roles.add (roleBean2Role (roleBean));
            }
        }
        return roles;
    }

    public static List <RoleBean> roleList2RoleBeanList (List <Role> roles)
    {
        List <RoleBean> roleBeans = new ArrayList <RoleBean> ();
        if (!CollectionUtils.isEmpty (roles))
        {
            for (Role role : roles)
            {
                roleBeans.add (role2RoleBean (role));
            }
        }
        return roleBeans;
    }

    public static AuthorityBean authority2AuthorityBean (Authority authority)
    {
        if (null != authority)
        {
            AuthorityBean authorityBean = new AuthorityBean ();
            BeanUtils.copyProperties (authority, authorityBean, new String[]
            { "id" });
            authorityBean.setId (authority.getId () + "");
            authorityBean.setRoleBeans (roleList2RoleBeanList (authority.getRoles ()));
            // authorityBean.setResourceBeans (resourceList2ResourceBeanList
            // (authority.getResources ()));
            return authorityBean;
        }
        return null;
    }

    public static List <AuthorityBean> authorityList2AuthorityBeanList (List <Authority> authorities)
    {
        List <AuthorityBean> authorityBeans = new ArrayList <AuthorityBean> ();
        if (!CollectionUtils.isEmpty (authorities))
        {
            for (Authority authority : authorities)
            {
                authorityBeans.add (authority2AuthorityBean (authority));
            }
        }
        return authorityBeans;
    }

    public static Authority authorityBean2Authority (AuthorityBean authorityBean)
    {
        if (null != authorityBean)
        {
            Authority authority = new Authority ();
            BeanUtils.copyProperties (authorityBean, authority, new String[]
            { "id" });
            authority.setId (StringUtils.isNotBlank (authorityBean.getId ()) ? Long.parseLong (authorityBean.getId ())
                                                                            : 0);
            // authority.setResources (resourceBeansList2ResourceList
            // (authorityBean.getResourceBeans ()));
            return authority;
        }
        return null;
    }

    public static List <Authority> authorityBeanList2AuthorityList (List <AuthorityBean> authorityBeans)
    {
        List <Authority> authorities = new ArrayList <Authority> ();
        if (!CollectionUtils.isEmpty (authorityBeans))
        {
            for (AuthorityBean ab : authorityBeans)
            {
                authorities.add (authorityBean2Authority (ab));
            }
        }
        return authorities;
    }

    public static Goods goodsBean2Goods (GoodsBean goodsBean)
    {
        Goods goods = null;
        if (goodsBean != null)
        {
            goods = new Goods ();
            BeanUtils.copyProperties (goodsBean, goods, new String[]
            { "id" });
            goods.setId (StringUtils.isNotBlank (goodsBean.getId ()) ? Long.parseLong (goodsBean.getId ()) : 0);

            GoodsTypeBean goodsTypeBean = goodsBean.getTypeBean ();
            if (null != goodsTypeBean && StringUtils.isNotBlank (goodsTypeBean.getId ())
                && !"0".equals (goodsTypeBean.getId ()))
            {
                GoodsType goodsType = new GoodsType (goodsTypeBean.getText ());
                goodsType.setId (StringUtils.isNotBlank (goodsTypeBean.getId ()) ? Long.parseLong (goodsTypeBean.getId ())
                                                                                : 0);
                // goodsType.setParent (new GoodsType (Long.parseLong
                // (goodsTypeBean.getParentId ())));
                goods.setType (goodsType);
            }

            GoodsUnitBean unitBean = goodsBean.getUnitBean ();
            if (null != unitBean && StringUtils.isNotBlank (unitBean.getId ()))
            {
                goods.setUnit (goodsUnitBean2GoodsUnit (unitBean));
            }
            List <GoodsStorageBean> storageBeans = goodsBean.getStorageBeans ();
            if (null != storageBeans && storageBeans.size () > 0)
            {
                List <GoodsStorage> storages = new ArrayList <GoodsStorage> ();
                for (GoodsStorageBean storageBean : storageBeans)
                {
                    storages.add (goodsStorageBean2GoodsStorage (storageBean));
                }
                goods.setStorages (storages);
            }
            GoodsDepositoryBean depositoryBean = goodsBean.getPreferedDepositoryBean ();
            if (null != depositoryBean && StringUtils.isNotBlank (depositoryBean.getId ()))
            {
                goods.setPreferedDepository (goodsDepositoryBean2GoodsDepository (depositoryBean));
            }
        }
        return goods;
    }

    public static GoodsBean goods2GoodsBean (Goods goods)
    {
        GoodsBean goodsBean = null;
        if (null != goods)
        {
            goodsBean = new GoodsBean ();
            BeanUtils.copyProperties (goods, goodsBean, new String[]
            { "id" });
            goodsBean.setId (goods.getId () + "");
            GoodsType goodsType = goods.getType ();
            if (null != goodsType)
            {
                GoodsTypeBean goodsTypeBean = new GoodsTypeBean ();
                goodsTypeBean.setId (goodsType.getId () + "");
                goodsTypeBean.setText (goodsType.getName ());
                // goodsTypeBean.setParentId (goodsType.getParent () != null ?
                // goodsType.getParent ().getId () + "" : "");
                goodsBean.setTypeBean (goodsTypeBean);
            }
            goodsBean.setUnitBean (goodsUnit2GoodsUnitBean (goods.getUnit ()));
            
            int sumAmount = 0;
            List <GoodsStorage> storages = goods.getStorages ();
            if (null != storages && storages.size () > 0)
            {
                List <GoodsStorageBean> storageBeans = new ArrayList <GoodsStorageBean> ();
                for (GoodsStorage storage : storages)
                {
                    sumAmount += storage.getAmount ();
                    storageBeans.add (goodsStorage2GoodsStorageBean (storage));
                }
                goodsBean.setStorageBeans (storageBeans);
            }

            GoodsDepository preferedDepository = goods.getPreferedDepository ();
            if (null != preferedDepository)
            {
                goodsBean.setPreferedDepositoryBean (goodsDepository2GoodsDepositoryBean (preferedDepository));
            }
            
            float goodsAveragePrice = goods.getAveragePrice ();
            goodsBean.setAveragePrice (goodsAveragePrice);
            goodsBean.setTotalStock (sumAmount);
            float sumValue = sumAmount * goodsAveragePrice;
            goodsBean.setTotalValue (Arith.round (sumValue, -1));
        }
        return goodsBean;
    }

    public static List <GoodsBean> goodsList2GoodsBeanList (List <Goods> goodsList)
    {
        List <GoodsBean> goodsBeanList = new ArrayList <GoodsBean> ();
        if (null != goodsList)
        {
            for (Goods goods : goodsList)
            {
                goodsBeanList.add (goods2GoodsBean (goods));
            }
        }
        return goodsBeanList;
    }

    public static GoodsUnitBean goodsUnit2GoodsUnitBean (GoodsUnit goodsUnit)
    {
        if (null != goodsUnit)
        {
            GoodsUnitBean unitBean = new GoodsUnitBean ();
            unitBean.setId (goodsUnit.getId () + "");
            unitBean.setName (goodsUnit.getName ());
            return unitBean;
        }
        return null;
    }

    public static List <GoodsUnitBean> goodsUnitList2GoodsUnitBeanList (List <GoodsUnit> goodsUnits)
    {
        List <GoodsUnitBean> unitBeans = new ArrayList <GoodsUnitBean> ();
        if (!CollectionUtils.isEmpty (goodsUnits))
        {
            for (GoodsUnit goodsUnit : goodsUnits)
            {
                unitBeans.add (goodsUnit2GoodsUnitBean (goodsUnit));
            }
        }
        return unitBeans;
    }

    public static GoodsUnit goodsUnitBean2GoodsUnit (GoodsUnitBean unitBean)
    {
        if (null != unitBean)
        {
            GoodsUnit unit = new GoodsUnit (unitBean.getName ());
            String unitId = unitBean.getId ();
            if (null != unitId && !"".equals (unitId))
            {
                unit.setId (Long.parseLong (unitId));
            }
            return unit;
        }
        return null;
    }

    public static CustomerBean customer2CustomerBean (Customer customer)
    {
        CustomerBean bean = null;
        if (null != customer)
        {
            bean = new CustomerBean ();
            BeanUtils.copyProperties (customer, bean, new String[]
            { "id" });
            bean.setId (customer.getId () + "");

            bean.setContactBeans (customerContactList2ContactBeanList (customer.getContacts ()));
            // bean.setPaymentBeans (customerPaymentList2PaymentBeanList
            // (customer.getPayments ()));
            bean.setTypeBean (customerType2CustomerTypeBean (customer.getCustomerType ()));
            bean.setLevelBean (CustomerLevelManager.convert (customer.getCustomerLevel ()));
            User user = DaoContext.getUserDao ().findOne (customer.getUserSigned ());
            if (null != user)
            {
                bean.setUserSignedTo (user.getUserId ());
            }
        }
        return bean;
    }

    public static CustomerBean provider2CustomerBean (Provider provider)
    {
        CustomerBean bean = null;
        if (null != provider)
        {
            bean = new CustomerBean ();
            BeanUtils.copyProperties (provider, bean, new String[]
            { "id" });
            bean.setId (provider.getId () + "");

            bean.setContactBeans (providerContactList2ContactBeanList (provider.getContacts ()));
            // bean.setPaymentBeans (providerPaymentList2PaymentBeanList
            // (provider.getPayments ()));
            bean.setTypeBean (providerType2CustomerTypeBean (provider.getProviderType ()));
            User user = DaoContext.getUserDao ().findOne (provider.getUserSigned ());
            if (null != user)
            {
                bean.setUserSignedTo (user.getUserId ());
            }
        }
        return bean;
    }

    public static List <CustomerBean> customerList2CustomerBeanList (List <Customer> customers)
    {
        List <CustomerBean> customerBeans = new ArrayList <CustomerBean> ();
        if (!CollectionUtils.isEmpty (customers))
        {
            for (Customer customer : customers)
            {
                customerBeans.add (customer2CustomerBean (customer));
            }
        }
        return customerBeans;
    }

    public static List <CustomerBean> providerList2CustomerBeanList (List <Provider> customers)
    {
        List <CustomerBean> customerBeans = new ArrayList <CustomerBean> ();
        if (!CollectionUtils.isEmpty (customers))
        {
            for (Provider customer : customers)
            {
                customerBeans.add (provider2CustomerBean (customer));
            }
        }
        return customerBeans;
    }

    public static Customer customerBean2Customer (CustomerBean bean)
    {
        Customer customer = null;
        if (null != bean)
        {
            customer = new Customer ();
            BeanUtils.copyProperties (bean, customer, new String[]
            { "id" });
            customer.setId (StringUtils.isNotBlank (bean.getId ()) ? Long.parseLong (bean.getId ()) : 0);
            // customer.setPayments (paymentBeanList2CustomerPaymentList
            // (bean.getPaymentBeans ()));
            List <CustomerContact> contacts = contactBeanList2CustomerContactList (bean.getContactBeans ());
            if (null != contacts)
            {
                customer.setContacts (contacts);
            }
            if (isNotBlank (bean.getUserSignedTo ()))
            {
                customer.setUserSigned (DaoContext.getUserDao ().getUniqueIdByUserId (bean.getUserSignedTo ()));
            }
            CustomerTypeBean typeBean = bean.getTypeBean ();
            if (null != typeBean)
            {
                customer.setCustomerType (customerTypeBean2CustomerType (typeBean));
            }
            CustomerLevelBean levelBean = bean.getLevelBean ();
            if (null != levelBean)
            {
                customer.setCustomerLevel (CustomerLevelManager.convert (levelBean));
            }
        }
        return customer;
    }

    public static Provider customerBean2Provider (CustomerBean bean)
    {
        Provider customer = null;
        if (null != bean)
        {
            customer = new Provider ();
            BeanUtils.copyProperties (bean, customer, new String[]
            { "id" });
            customer.setId (StringUtils.isNotBlank (bean.getId ()) ? Long.parseLong (bean.getId ()) : 0);
            // customer.setPayments (paymentBeanList2ProviderPaymentList
            // (bean.getPaymentBeans ()));
            List <ProviderContact> contacts = contactBeanList2ProviderContactList (bean.getContactBeans ());
            if (null != contacts)
            {
                customer.setContacts (contacts);
            }
            if (isNotBlank (bean.getUserSignedTo ()))
            {
                customer.setUserSigned (DaoContext.getUserDao ().getUniqueIdByUserId (bean.getUserSignedTo ()));
            }
            CustomerTypeBean typeBean = bean.getTypeBean ();
            if (null != typeBean)
            {
                customer.setProviderType (customerTypeBean2ProviderType (typeBean));
            }

        }
        return customer;
    }

    public static ProviderType customerTypeBean2ProviderType (CustomerTypeBean typeBean)
    {
        if (null != typeBean)
        {
            ProviderType cs = new ProviderType ();
            if (StringUtils.isNotBlank (typeBean.getId ()))
            {
                cs.setId (Long.parseLong (typeBean.getId ()));
                if (StringUtils.isNotBlank (typeBean.getName ()))
                {
                    cs.setName (typeBean.getName ());
                }
                return cs;
            }
        }
        return null;
    }

    public static CustomerType customerTypeBean2CustomerType (CustomerTypeBean typeBean)
    {
        if (null != typeBean)
        {
            CustomerType cs = new CustomerType ();
            if (StringUtils.isNotBlank (typeBean.getId ()))
            {
                cs.setId (Long.parseLong (typeBean.getId ()));
                if (StringUtils.isNotBlank (typeBean.getName ()))
                {
                    cs.setName (typeBean.getName ());
                }
                return cs;
            }
        }
        return null;
    }

    public static CustomerTypeBean customerType2CustomerTypeBean (CustomerType type)
    {
        if (null != type)
        {
            CustomerTypeBean typeBean = new CustomerTypeBean ();
            typeBean.setId (type.getId () + "");
            typeBean.setName (type.getName ());
            return typeBean;
        }
        return null;
    }

    public static CustomerTypeBean providerType2CustomerTypeBean (ProviderType type)
    {
        if (null != type)
        {
            CustomerTypeBean typeBean = new CustomerTypeBean ();
            typeBean.setId (type.getId () + "");
            typeBean.setName (type.getName ());
            return typeBean;
        }
        return null;
    }

    public static ContactBean customerContact2ContactBean (CustomerContact contact)
    {
        ContactBean bean = null;
        if (null != contact)
        {
            bean = new ContactBean ();
            BeanUtils.copyProperties (contact, bean, new String[]
            { "id" });
            bean.setId (contact.getId () + "");
            // Customer customer = contact.getCustomer ();
            // if (null != customer)
            // {
            // bean.setCustomerId (customer.getId () + "");
            // bean.setCustomerName (customer.getShortName ());
            // }
        }
        return bean;
    }

    public static ContactBean providerContact2ContactBean (ProviderContact contact)
    {
        ContactBean bean = null;
        if (null != contact)
        {
            bean = new ContactBean ();
            BeanUtils.copyProperties (contact, bean, new String[]
            { "id" });
            bean.setId (contact.getId () + "");
            // Customer customer = contact.getCustomer ();
            // if (null != customer)
            // {
            // bean.setCustomerId (customer.getId () + "");
            // bean.setCustomerName (customer.getShortName ());
            // }
        }
        return bean;
    }

    public static List <ContactBean> customerContactList2ContactBeanList (List <CustomerContact> contacts)
    {
        List <ContactBean> contactBeans = new ArrayList <ContactBean> ();
        if (!CollectionUtils.isEmpty (contacts))
        {
            for (CustomerContact contact : contacts)
            {
                contactBeans.add (customerContact2ContactBean (contact));
            }
        }
        return contactBeans;
    }

    public static List <ContactBean> providerContactList2ContactBeanList (List <ProviderContact> contacts)
    {
        List <ContactBean> contactBeans = new ArrayList <ContactBean> ();
        if (!CollectionUtils.isEmpty (contacts))
        {
            for (ProviderContact contact : contacts)
            {
                contactBeans.add (providerContact2ContactBean (contact));
            }
        }
        return contactBeans;
    }

    public static CustomerContact contactBean2CustomerContact (ContactBean contactBean)
    {
        CustomerContact contact = null;
        if (null != contactBean)
        {
            contact = new CustomerContact ();
            BeanUtils.copyProperties (contactBean, contact, new String[]
            { "id" });
            contact.setId (StringUtils.isNotBlank (contactBean.getId ()) ? Long.parseLong (contactBean.getId ()) : 0);
            if (null != contactBean.getCustomerBean ())
            {
                Customer customer = new Customer ();
                customer.setId (Long.parseLong (contactBean.getCustomerBean ().getId ()));
                customer.setShortName (contactBean.getCustomerBean ().getShortName ());
                contact.setCustomer (customer);
            }
        }
        return contact;
    }

    public static ProviderContact contactBean2ProviderContact (ContactBean contactBean)
    {
        ProviderContact contact = null;
        if (null != contactBean)
        {
            contact = new ProviderContact ();
            BeanUtils.copyProperties (contactBean, contact, new String[]
            { "id" });
            contact.setId (StringUtils.isNotBlank (contactBean.getId ()) ? Long.parseLong (contactBean.getId ()) : 0);
            if (null != contactBean.getCustomerBean ())
            {
                Provider customer = new Provider ();
                customer.setId (Long.parseLong (contactBean.getCustomerBean ().getId ()));
                customer.setShortName (contactBean.getCustomerBean ().getShortName ());
                contact.setProvider (customer);
            }
        }
        return contact;
    }

    public static List <CustomerContact> contactBeanList2CustomerContactList (List <ContactBean> contactBeans)
    {
        List <CustomerContact> contacts = null;
        if (!CollectionUtils.isEmpty (contactBeans))
        {
            contacts = new ArrayList <CustomerContact> ();
            for (ContactBean contactBean : contactBeans)
            {
                contacts.add (contactBean2CustomerContact (contactBean));
            }
        }
        return contacts;
    }

    public static List <ProviderContact> contactBeanList2ProviderContactList (List <ContactBean> contactBeans)
    {
        List <ProviderContact> contacts = null;
        if (!CollectionUtils.isEmpty (contactBeans))
        {
            contacts = new ArrayList <ProviderContact> ();
            for (ContactBean contactBean : contactBeans)
            {
                contacts.add (contactBean2ProviderContact (contactBean));
            }
        }
        return contacts;
    }

    public static OrderOut orderBean2OrderOut (OrderBean orderBean)
    {
        OrderOut order = null;
        if (null != orderBean)
        {
            order = new OrderOut ();
            BeanUtils.copyProperties (orderBean, order, new String[]
            { "id" });
            if (StringUtils.isNotBlank (orderBean.getId ()))
            {
                order.setId (Long.parseLong (orderBean.getId ()));
            }
            order.setDateCreated (stringDateTime2JavaSqlTimestamp (orderBean.getCreateTime ()));
            order.setStatusCode (null == order.getStatusCode () || "".equals (order.getStatusCode ().toString ()) ? OrderStatusCode.NEW
                                                                                                                 : order.getStatusCode ());
            order.setOrderItems (orderItemBeanList2OrderOutItemList (orderBean.getOrderItemBeans ()));
            if (null != orderBean.getCustomerBean () && StringUtils.isNotBlank (orderBean.getCustomerBean ().getId ()))
            {
                order.setCustomer (customerBean2Customer (orderBean.getCustomerBean ()));
            }
            if (isNotBlank (orderBean.getUserCreated ()))
            {
                order.setUserCreatedBy (DaoContext.getUserDao ().getUniqueIdByUserId (orderBean.getUserCreated ()));
            }
            if (isNotBlank (orderBean.getUserUpdated ()))
            {
                order.setUserUpdatedBy (DaoContext.getUserDao ().getUniqueIdByUserId (orderBean.getUserUpdated ()));
            }
            if (null != orderBean.getProjectBean () && isNotBlank (orderBean.getProjectBean ().getId ()))
            {
                Project project = new Project ();
                project.setId (Long.parseLong (orderBean.getProjectBean ().getId ()));
                order.setProject (project);
            }
        }
        return order;
    }

    public static OrderIn orderBean2OrderIn (OrderBean orderBean)
    {
        OrderIn order = null;
        if (null != orderBean)
        {
            order = new OrderIn ();
            BeanUtils.copyProperties (orderBean, order, new String[]
            { "id" });
            if (StringUtils.isNotBlank (orderBean.getId ()))
            {
                order.setId (Long.parseLong (orderBean.getId ()));
            }
            order.setDateCreated (stringDateTime2JavaSqlTimestamp (orderBean.getCreateTime ()));
            order.setStatusCode (null == order.getStatusCode () || "".equals (order.getStatusCode ().toString ()) ? OrderStatusCode.NEW
                                                                                                                 : order.getStatusCode ());
            order.setOrderItems (orderItemBeanList2OrderInItemList (orderBean.getOrderItemBeans ()));
            if (null != orderBean.getCustomerBean () && StringUtils.isNotBlank (orderBean.getCustomerBean ().getId ()))
            {
                order.setProvider (customerBean2Provider (orderBean.getCustomerBean ()));
            }
            if (isNotBlank (orderBean.getUserCreated ()))
            {
                order.setUserCreatedBy (DaoContext.getUserDao ().getUniqueIdByUserId (orderBean.getUserCreated ()));
            }
            if (isNotBlank (orderBean.getUserUpdated ()))
            {
                order.setUserUpdatedBy (DaoContext.getUserDao ().getUniqueIdByUserId (orderBean.getUserUpdated ()));
            }
        }
        return order;
    }

    public static OrderBean orderOut2OrderBean (OrderOut order, PaymentAccount paymentAccount)
    {
        OrderBean orderBean = orderOut2OrderBean (order);
        if(null != paymentAccount) 
        {
            orderBean.setPaymentAccountBean (paymentAccount2PaymentAccountBean (paymentAccount));
        }
        return orderBean;
    }
    public static OrderBean orderOut2OrderBean (OrderOut order)
    {
        OrderBean orderBean = null;
        if (null != order)
        {
            orderBean = new OrderBean ();
            BeanUtils.copyProperties (order, orderBean, new String[]
            { "id" });
            orderBean.setId (order.getId () + "");
            orderBean.setCreateTime (DateUtils.date2String (order.getDateCreated (), DateUtils.DATE_TIME_PATTERN));
            orderBean.setUpdateTime (DateUtils.date2String (order.getLastUpdated (), DateUtils.DATE_TIME_PATTERN));
            orderBean.setOrderItemBeans (orderOutItemList2OrderItemBeanList (order.getOrderItems ()));
            orderBean.setCustomerBean (customer2CustomerBean (order.getCustomer ()));

            User user = DaoContext.getUserDao ().findOne (order.getUserCreatedBy ());
            if (null != user)
            {
                orderBean.setUserCreated (user.getUserId ());
            }
            user = DaoContext.getUserDao ().findOne (order.getUserUpdatedBy ());
            if (null != user)
            {
                orderBean.setUserUpdated (user.getUserId ());
            }
            if (null != order.getProject ())
            {
                ProjectBean projectBean = new ProjectBean ();
                projectBean.setId (order.getProject ().getId () + "");
                projectBean.setName (order.getProject ().getName ());
                orderBean.setProjectBean (projectBean);
            }
            if (null != order.getOrderDelivery ())
            {
                orderBean.setDeliveryBean (OutOrderDeliveryManager.convert (order.getOrderDelivery ()));
            }
        }
        return orderBean;
    }

    public static OrderBean orderIn2OrderBean (OrderIn order, PaymentAccount paymentAccount)
    {
        OrderBean orderBean = orderIn2OrderBean (order);
        if(null != paymentAccount) 
        {
            orderBean.setPaymentAccountBean (paymentAccount2PaymentAccountBean (paymentAccount));
        }
        return orderBean;
    }
    public static OrderBean orderIn2OrderBean (OrderIn order)
    {
        OrderBean orderBean = null;
        if (null != order)
        {
            orderBean = new OrderBean ();
            BeanUtils.copyProperties (order, orderBean, new String[]
            { "id" });
            orderBean.setId (order.getId () + "");
            orderBean.setCreateTime (DateUtils.date2String (order.getDateCreated (), DateUtils.DATE_TIME_PATTERN));
            orderBean.setOrderItemBeans (orderInItemList2OrderItemBeanList (order.getOrderItems ()));
            orderBean.setCustomerBean (provider2CustomerBean (order.getProvider ()));

            User user = DaoContext.getUserDao ().findOne (order.getUserCreatedBy ());
            if (null != user)
            {
                orderBean.setUserCreated (user.getUserId ());
            }
            user = DaoContext.getUserDao ().findOne (order.getUserUpdatedBy ());
            if (null != user)
            {
                orderBean.setUserUpdated (user.getUserId ());
            }
        }
        return orderBean;
    }

    public static List <OrderBean> orderOutList2OrderBeanList (List <OrderOut> orders)
    {
        List <OrderBean> orderBeans = new ArrayList <OrderBean> ();
        if (!CollectionUtils.isEmpty (orders))
        {
            for (OrderOut order : orders)
            {
                orderBeans.add (orderOut2OrderBean (order));
            }
        }
        return orderBeans;
    }

    public static List <OrderBean> orderInList2OrderBeanList (List <OrderIn> orders)
    {
        List <OrderBean> orderBeans = new ArrayList <OrderBean> ();
        if (!CollectionUtils.isEmpty (orders))
        {
            for (OrderIn order : orders)
            {
                orderBeans.add (orderIn2OrderBean (order));
            }
        }
        return orderBeans;
    }

    public static OrderItemBean orderOutItem2OrderItemBean (OrderOutItem orderItem)
    {
        OrderItemBean orderItemBean = null;
        if (null != orderItem)
        {
            orderItemBean = new OrderItemBean ();
            BeanUtils.copyProperties (orderItem, orderItemBean, new String[]
            { "id" });
            orderItemBean.setId (orderItem.getId () + "");
            if (null != orderItem.getOrderOut ())
            {
                OrderOut order = (OrderOut) orderItem.getOrderOut ();
                orderItemBean.setOrderId (order.getId () + "");
                orderItemBean.setOrderBid (order.getBid ());
                orderItemBean.setOrderCreate (DateUtils.date2String (order.getDateCreated (),
                                                                     DateUtils.DATE_TIME_PATTERN));
                if (null != order.getCustomer ())
                {
                    orderItemBean.setCustomerName (order.getCustomer ().getShortName ());
                }
                orderItemBean.setTypeCode (order.getTypeCode ());
                orderItemBean.setStatusCode (order.getStatusCode ());
                User user = DaoContext.getUserDao ().findOne (order.getUserCreatedBy ());
                if (null != user)
                {
                    orderItemBean.setUserCreated (user.getUserId ());
                }
            }
            Goods goods = orderItem.getGoods ();
            if (null != goods)
            {
                orderItemBean.setGoodsId (goods.getId () + "");
                orderItemBean.setGoodsName (goods.getName ());
                if (null != goods.getUnit ())
                {
                    orderItemBean.setGoodsUnit (goods.getUnit ().getName ());
                }
            }
            if (null != orderItem.getDepository ())
            {
                orderItemBean.setDepositoryBean (goodsDepository2GoodsDepositoryBean (orderItem.getDepository ()));
            }
        }
        return orderItemBean;
    }

    public static OrderItemBean orderInItem2OrderItemBean (OrderInItem orderItem)
    {
        OrderItemBean orderItemBean = null;
        if (null != orderItem)
        {
            orderItemBean = new OrderItemBean ();
            BeanUtils.copyProperties (orderItem, orderItemBean, new String[]
            { "id" });
            orderItemBean.setId (orderItem.getId () + "");
            if (null != orderItem.getOrderIn ())
            {
                OrderIn order = (OrderIn) orderItem.getOrderIn ();
                orderItemBean.setOrderId (order.getId () + "");
                orderItemBean.setOrderBid (order.getBid ());
                orderItemBean.setOrderCreate (DateUtils.date2String (order.getDateCreated (),
                                                                     DateUtils.DATE_TIME_PATTERN));
                if (null != order.getProvider ())
                {
                    orderItemBean.setCustomerName (order.getProvider ().getShortName ());
                }
                orderItemBean.setTypeCode (order.getTypeCode ());
                orderItemBean.setStatusCode (order.getStatusCode ());
                User user = DaoContext.getUserDao ().findOne (order.getUserCreatedBy ());
                if (null != user)
                {
                    orderItemBean.setUserCreated (user.getUserId ());
                }
            }
            Goods goods = orderItem.getGoods ();
            if (null != goods)
            {
                orderItemBean.setGoodsId (goods.getId () + "");
                orderItemBean.setGoodsName (goods.getName ());
                GoodsUnit goodsUnit = goods.getUnit ();
                if (null != goodsUnit)
                {
                    orderItemBean.setGoodsUnit (goodsUnit.getName ());
                }
            }
            if (null != orderItem.getDepository ())
            {
                orderItemBean.setDepositoryBean (goodsDepository2GoodsDepositoryBean (orderItem.getDepository ()));
            }
        }
        return orderItemBean;
    }

    public static OrderOutItem orderItemBean2OrderOutItem (OrderItemBean orderItemBean)
    {
        OrderOutItem orderItem = null;
        if (null != orderItemBean)
        {
            orderItem = new OrderOutItem ();
            BeanUtils.copyProperties (orderItemBean, orderItem, new String[]
            { "id" });
            orderItem.setId (StringUtils.isNotBlank (orderItemBean.getId ()) ? Long.parseLong (orderItemBean.getId ())
                                                                            : 0);
            if (!StringUtils.isBlank (orderItemBean.getGoodsId ()))
            {
                Goods goods = new Goods ();
                goods.setId (Long.parseLong (orderItemBean.getGoodsId ()));
                orderItem.setGoods (goods);
            }
            if (!StringUtils.isBlank (orderItemBean.getOrderId ()))
            {
                OrderOut order = new OrderOut ();
                order.setId (Long.parseLong (orderItemBean.getOrderId ()));
                orderItem.setOrderOut (order);
            }
            GoodsDepositoryBean depositoryBean = orderItemBean.getDepositoryBean ();
            if (null != depositoryBean && StringUtils.isNotBlank (depositoryBean.getId ()))
            {
                orderItem.setDepository (goodsDepositoryBean2GoodsDepository (depositoryBean));
            }
        }
        return orderItem;
    }

    public static OrderInItem orderItemBean2OrderInItem (OrderItemBean orderItemBean)
    {
        OrderInItem orderItem = null;
        if (null != orderItemBean)
        {
            orderItem = new OrderInItem ();
            BeanUtils.copyProperties (orderItemBean, orderItem, new String[]
            { "id" });
            orderItem.setId (StringUtils.isNotBlank (orderItemBean.getId ()) ? Long.parseLong (orderItemBean.getId ())
                                                                            : 0);
            if (!StringUtils.isBlank (orderItemBean.getGoodsId ()))
            {
                Goods goods = new Goods ();
                goods.setId (Long.parseLong (orderItemBean.getGoodsId ()));
                orderItem.setGoods (goods);
            }
            if (!StringUtils.isBlank (orderItemBean.getOrderId ()))
            {
                OrderIn order = new OrderIn ();
                order.setId (Long.parseLong (orderItemBean.getOrderId ()));
                orderItem.setOrderIn (order);
            }
            GoodsDepositoryBean depositoryBean = orderItemBean.getDepositoryBean ();
            if (null != depositoryBean && StringUtils.isNotBlank (depositoryBean.getId ()))
            {
                orderItem.setDepository (goodsDepositoryBean2GoodsDepository (depositoryBean));
            }
        }
        return orderItem;
    }

    public static List <OrderItemBean> orderOutItemList2OrderItemBeanList (List <OrderOutItem> orderItems)
    {
        List <OrderItemBean> orderItemBeans = new ArrayList <OrderItemBean> ();
        if (!CollectionUtils.isEmpty (orderItems))
        {
            for (OrderOutItem orderItem : orderItems)
            {
                orderItemBeans.add (orderOutItem2OrderItemBean (orderItem));
            }
        }
        return orderItemBeans;
    }

    public static List <OrderItemBean> orderInItemList2OrderItemBeanList (List <OrderInItem> orderItems)
    {
        List <OrderItemBean> orderItemBeans = new ArrayList <OrderItemBean> ();
        if (!CollectionUtils.isEmpty (orderItems))
        {
            for (OrderInItem orderItem : orderItems)
            {
                orderItemBeans.add (orderInItem2OrderItemBean (orderItem));
            }
        }
        return orderItemBeans;
    }

    public static List <OrderOutItem> orderItemBeanList2OrderOutItemList (List <OrderItemBean> orderItemBeans)
    {
        List <OrderOutItem> orderItems = new ArrayList <OrderOutItem> ();
        if (!CollectionUtils.isEmpty (orderItemBeans))
        {
            for (OrderItemBean orderItemBean : orderItemBeans)
            {
                orderItems.add (orderItemBean2OrderOutItem (orderItemBean));
            }
        }
        return orderItems;
    }

    public static List <OrderInItem> orderItemBeanList2OrderInItemList (List <OrderItemBean> orderItemBeans)
    {
        List <OrderInItem> orderItems = new ArrayList <OrderInItem> ();
        if (!CollectionUtils.isEmpty (orderItemBeans))
        {
            for (OrderItemBean orderItemBean : orderItemBeans)
            {
                orderItems.add (orderItemBean2OrderInItem (orderItemBean));
            }
        }
        return orderItems;
    }

    public static PaymentBean customerPayment2PaymentBean (CustomerPayment payment, boolean nested)
    {
        if (null != payment)
        {
            PaymentBean paymentBean = new PaymentBean ();
            BeanUtils.copyProperties (payment, paymentBean, new String[]
            { "id" });
            paymentBean.setId (payment.getId () + "");
            if (null != payment.getCustomer ())
            {
                paymentBean.setCustomerBean (customer2CustomerBean (payment.getCustomer ()));
            }
            paymentBean.setPayDate (DateUtils.date2String (payment.getPaidDate (),
                                                           DateUtils.DATE_TIME_PATTERN));
            User user = DaoContext.getUserDao ().findOne (payment.getUserCreatedBy ());
            if (null != user)
            {
                paymentBean.setUserCreated (user.getUserId ());
            }
            if (nested)
            {
                if (!CollectionUtils.isEmpty (payment.getPaymentRecords ()))
                {
                    List <PaymentRecordBean> recordBeans = new ArrayList <PaymentRecordBean> ();
                    for (CustomerPaymentRecord paymentRecord : payment.getPaymentRecords ())
                    {
                        recordBeans.add (customerPaymentRecord2PaymentRecordBean (paymentRecord));
                    }
                    paymentBean.setPaymentRecordBeans (recordBeans);
                }
            }
            return paymentBean;
        }
        return null;
    }

    public static PaymentBean providerPayment2PaymentBean (ProviderPayment payment, boolean nested)
    {
        if (null != payment)
        {
            PaymentBean paymentBean = new PaymentBean ();
            BeanUtils.copyProperties (payment, paymentBean, new String[]
            { "id" });
            paymentBean.setId (payment.getId () + "");
            if (null != payment.getProvider ())
            {
                paymentBean.setCustomerBean (provider2CustomerBean (payment.getProvider ()));
            }
            paymentBean.setPayDate (DateUtils.date2String (payment.getPaidDate (),
                                                           DateUtils.DATE_TIME_PATTERN));
            User user = DaoContext.getUserDao ().findOne (payment.getUserCreatedBy ());
            if (null != user)
            {
                paymentBean.setUserCreated (user.getUserId ());
            }
            if (nested)
            {
                if (!CollectionUtils.isEmpty (payment.getPaymentRecords ()))
                {
                    List <PaymentRecordBean> recordBeans = new ArrayList <PaymentRecordBean> ();
                    for (ProviderPaymentRecord paymentRecord : payment.getPaymentRecords ())
                    {
                        recordBeans.add (providerPaymentRecord2PaymentRecordBean (paymentRecord));
                    }
                    paymentBean.setPaymentRecordBeans (recordBeans);
                }
            }
            return paymentBean;
        }
        return null;
    }

    public static List <PaymentBean> customerPaymentList2PaymentBeanList (List <CustomerPayment> payments)
    {
        if (!CollectionUtils.isEmpty (payments))
        {
            List <PaymentBean> paymentBeans = new ArrayList <PaymentBean> ();
            for (CustomerPayment payment : payments)
            {
                paymentBeans.add (customerPayment2PaymentBean (payment, true));
            }
            return paymentBeans;
        }
        return null;
    }

    public static List <PaymentBean> providerPaymentList2PaymentBeanList (List <ProviderPayment> payments)
    {
        if (!CollectionUtils.isEmpty (payments))
        {
            List <PaymentBean> paymentBeans = new ArrayList <PaymentBean> ();
            for (ProviderPayment payment : payments)
            {
                paymentBeans.add (providerPayment2PaymentBean (payment, true));
            }
            return paymentBeans;
        }
        return null;
    }

    public static CustomerPayment paymentBean2CustomerPayment (PaymentBean paymentBean)
    {
        if (null != paymentBean)
        {
            CustomerPayment payment = new CustomerPayment ();
            BeanUtils.copyProperties (paymentBean, payment, new String[]
            { "id" });
            payment.setId (StringUtils.isNotBlank (paymentBean.getId ()) ? Long.parseLong (paymentBean.getId ()) : 0);
            payment.setPaidDate (stringDateTime2JavaSqlTimestamp (paymentBean.getPayDate ()));
            if (isNotBlank (paymentBean.getUserCreated ()))
            {
                payment.setUserCreatedBy (DaoContext.getUserDao ().getUniqueIdByUserId (paymentBean.getUserCreated ()));
            }
            if (null != paymentBean.getCustomerBean ()
                && StringUtils.isNotBlank (paymentBean.getCustomerBean ().getId ()))
            {
                payment.setCustomer (customerBean2Customer (paymentBean.getCustomerBean ()));
            }
            if (!CollectionUtils.isEmpty (paymentBean.getPaymentRecordBeans ()))
            {
                List <CustomerPaymentRecord> paymentRecords = new ArrayList <CustomerPaymentRecord> ();
                for (PaymentRecordBean recordBean : paymentBean.getPaymentRecordBeans ())
                {
                    paymentRecords.add (paymentRecordBean2CustomerPaymentRecord (recordBean));
                }
                payment.setPaymentRecords (paymentRecords);
            }
            return payment;
        }
        return null;
    }

    public static ProviderPayment paymentBean2ProviderPayment (PaymentBean paymentBean)
    {
        if (null != paymentBean)
        {
            ProviderPayment payment = new ProviderPayment ();
            BeanUtils.copyProperties (paymentBean, payment, new String[]
            { "id" });
            payment.setId (StringUtils.isNotBlank (paymentBean.getId ()) ? Long.parseLong (paymentBean.getId ()) : 0);
            payment.setPaidDate (stringDateTime2JavaSqlTimestamp (paymentBean.getPayDate ()));
            if (isNotBlank (paymentBean.getUserCreated ()))
            {
                payment.setUserCreatedBy (DaoContext.getUserDao ().getUniqueIdByUserId (paymentBean.getUserCreated ()));
            }
            if (null != paymentBean.getCustomerBean ()
                && StringUtils.isNotBlank (paymentBean.getCustomerBean ().getId ()))
            {
                payment.setProvider (customerBean2Provider (paymentBean.getCustomerBean ()));
            }
            if (!CollectionUtils.isEmpty (paymentBean.getPaymentRecordBeans ()))
            {
                List <ProviderPaymentRecord> paymentRecords = new ArrayList <ProviderPaymentRecord> ();
                for (PaymentRecordBean recordBean : paymentBean.getPaymentRecordBeans ())
                {
                    paymentRecords.add (paymentRecordBean2ProviderPaymentRecord (recordBean));
                }
                payment.setPaymentRecords (paymentRecords);
            }
            return payment;
        }
        return null;
    }

    public static List <CustomerPayment> paymentBeanList2CustomerPaymentList (List <PaymentBean> paymentBeans)
    {
        if (!CollectionUtils.isEmpty (paymentBeans))
        {
            List <CustomerPayment> payments = new ArrayList <CustomerPayment> ();
            for (PaymentBean paymentBean : paymentBeans)
            {
                payments.add (paymentBean2CustomerPayment (paymentBean));
            }
            return payments;
        }
        return null;
    }

    public static List <ProviderPayment> paymentBeanList2ProviderPaymentList (List <PaymentBean> paymentBeans)
    {
        if (!CollectionUtils.isEmpty (paymentBeans))
        {
            List <ProviderPayment> payments = new ArrayList <ProviderPayment> ();
            for (PaymentBean paymentBean : paymentBeans)
            {
                payments.add (paymentBean2ProviderPayment (paymentBean));
            }
            return payments;
        }
        return null;
    }

    public static PaymentAccount paymentAccountBean2PaymentAccount (PaymentAccountBean typeBean)
    {
        if (typeBean != null)
        {
            PaymentAccount type = new PaymentAccount ();
            type.setId (StringUtils.isNotBlank (typeBean.getId ()) ? Long.parseLong (typeBean.getId ()) : 0);
            type.setName (typeBean.getName ());
            type.setAccountId (typeBean.getAccountId ());
            type.setRemainMoney (typeBean.getRemainMoney ());
            type.setAccountMode (typeBean.getAccountMode ());
            return type;
        }
        return null;
    }

    public static PaymentAccountBean paymentAccount2PaymentAccountBean (PaymentAccount type)
    {
        if (type != null)
        {
            PaymentAccountBean typeBean = new PaymentAccountBean ();
            typeBean.setId (type.getId () + "");
            typeBean.setName (type.getName ());
            typeBean.setAccountId (type.getAccountId ());
            typeBean.setRemainMoney (type.getRemainMoney ());
            typeBean.setAccountMode (type.getAccountMode ());
            return typeBean;
        }
        return null;
    }

    public static AccountingType accountingTypeBean2AccountingType (AccountingTypeBean typeBean)
    {
        if (null != typeBean)
        {
            AccountingType type = new AccountingType ();
            type.setId (StringUtils.isNotBlank (typeBean.getId ()) ? Long.parseLong (typeBean.getId ()) : 0);
            type.setName (typeBean.getName ());
            type.setAccountingMode (typeBean.getAccountingMode ());
            return type;
        }
        return null;
    }

    public static AccountingTypeBean accountingType2AccountingTypeBean (AccountingType type)
    {
        if (null != type)
        {
            AccountingTypeBean typeBean = new AccountingTypeBean ();
            typeBean.setId (type.getId () + "");
            typeBean.setName (type.getName ());
            typeBean.setAccountingMode (type.getAccountingMode ());
            return typeBean;
        }
        return null;
    }

    public static List <AccountingTypeBean> accountingTypeList2AccountingTypeBeanList (List <AccountingType> types)
    {
        List <AccountingTypeBean> typeBeans = new ArrayList <AccountingTypeBean> ();
        if (!CollectionUtils.isEmpty (types))
        {
            for (AccountingType type : types)
            {
                typeBeans.add (accountingType2AccountingTypeBean (type));
            }
        }
        return typeBeans;
    }

    public static Accounting accountingBean2Accounting (AccountingBean accountingBean)
    {
        if (null != accountingBean)
        {
            Accounting accounting = new Accounting ();
            BeanUtils.copyProperties (accountingBean, accounting, new String[]
            { "id" });
            accounting.setId (isNotBlank (accountingBean.getId ()) ? Long.parseLong (accountingBean.getId ()) : 0);
            accounting.setType (accountingTypeBean2AccountingType (accountingBean.getTypeBean ()));
            accounting.setPaymentAccount (paymentAccountBean2PaymentAccount (accountingBean.getPaymentAccountBean ()));
            if (isNotBlank (accountingBean.getCreateDate ()))
            {
                accounting.setCreated (stringDateTime2JavaSqlTimestamp (accountingBean.getCreateDate ()));
            }
            if (isNotBlank (accountingBean.getUpdateDate ()))
            {
                accounting.setUpdated (stringDateTime2JavaSqlTimestamp (accountingBean.getUpdateDate ()));
            }
            accounting.setCustomerId (accountingBean.getCustomerId ());
            accounting.setOrderId (accountingBean.getOrderId ());
            return accounting;
        }
        return null;
    }

    public static AccountingBean accounting2AccountingBean (Accounting accounting)
    {
        if (null != accounting)
        {
            AccountingBean accountingBean = new AccountingBean ();
            BeanUtils.copyProperties (accounting, accountingBean, new String[]
            { "id" });
            accountingBean.setId (accounting.getId () + "");
            accountingBean.setTypeBean (accountingType2AccountingTypeBean (accounting.getType ()));
            accountingBean.setPaymentAccountBean (paymentAccount2PaymentAccountBean (accounting.getPaymentAccount ()));
            accountingBean.setCreateDate (DateUtils.date2String (accounting.getCreated (),
                                                                 DateUtils.DATE_TIME_PATTERN));
            accountingBean.setUpdateDate (DateUtils.date2String (accounting.getUpdated (),
                                                                 DateUtils.DATE_TIME_PATTERN));
            User user = DaoContext.getUserDao ().findOne (accounting.getUserCreatedBy ());
            if (null != user)
            {
                accountingBean.setUserCreated (user.getUserId ());
            }
            user = DaoContext.getUserDao ().findOne (accounting.getUserUpdatedBy ());
            if (null != user)
            {
                accountingBean.setUserUpdated (user.getUserId ());
            }
            accountingBean.setOrderId (accounting.getOrderId ());
            accountingBean.setCustomerId (accounting.getCustomerId ());

            return accountingBean;
        }
        return null;
    }

    public static List <AccountingBean> accountingList2AccountingBeanList (List <Accounting> accountings)
    {
        if (null != accountings)
        {
            List <AccountingBean> accountingBeans = new ArrayList <AccountingBean> ();
            for (Accounting accounting : accountings)
            {
                accountingBeans.add (accounting2AccountingBean (accounting));
            }
            return accountingBeans;
        }
        return null;
    }

    public static OrderSearchForm searchRequestForm2OrderSearchForm (SearchRequestForm searchRequestForm)
    {
        if (null != searchRequestForm)
        {
            OrderSearchForm orderSearchForm = new OrderSearchForm ();
            if (StringUtils.isNotBlank (searchRequestForm.getStartDate ())
                && StringUtils.isNotBlank (searchRequestForm.getEndDate ()))
            {
                orderSearchForm.setStartDate (DateUtils.string2Date (searchRequestForm.getStartDate () + " 00:00:00.000",
                                                                     DateUtils.DATE_TIME_MILLISECOND_PATTERN));
                orderSearchForm.setEndDate (DateUtils.string2Date (searchRequestForm.getEndDate () + " 23:59:59.999",
                                                                   DateUtils.DATE_TIME_MILLISECOND_PATTERN));
            }
            try
            {
                orderSearchForm.setCustomerId (StringUtils.isNotBlank (searchRequestForm.getCustomerId ()) ? Long.parseLong (searchRequestForm.getCustomerId ())
                                                                                                          : 0);
                orderSearchForm.setOrderId (StringUtils.isNotBlank (searchRequestForm.getOrderId ()) ? Long.parseLong (searchRequestForm.getOrderId ())
                                                                                                    : 0);
            }
            catch (Exception e)
            {
                orderSearchForm.setCustomerId (0);
                orderSearchForm.setOrderId (0);
            }
            orderSearchForm.setOrderBid (searchRequestForm.getOrderBid ());
            
            long userCreatedBy = 0;
            try 
            {
                if(StringUtils.isNotBlank (searchRequestForm.getUserCreate ())) 
                {
                    userCreatedBy = Long.parseLong (searchRequestForm.getUserCreate ());
                }
            }
            catch (Exception e) {
            }
            orderSearchForm.setUserCreatedBy (userCreatedBy);
            return orderSearchForm;
        }
        return null;
    }

    public static PaymentSearchForm searchRequestForm2PaymentSearchForm (SearchRequestForm searchRequestForm)
    {
        if (null != searchRequestForm)
        {
            PaymentSearchForm paymentSearchForm = new PaymentSearchForm ();
            if (StringUtils.isNotBlank (searchRequestForm.getStartDate ())
                && StringUtils.isNotBlank (searchRequestForm.getEndDate ()))
            {
                paymentSearchForm.setStartDate (DateUtils.string2Date (searchRequestForm.getStartDate () + " 00:00:00.000",
                                                                       DateUtils.DATE_TIME_MILLISECOND_PATTERN));
                paymentSearchForm.setEndDate (DateUtils.string2Date (searchRequestForm.getEndDate () + " 23:59:59.999",
                                                                     DateUtils.DATE_TIME_MILLISECOND_PATTERN));
            }
            paymentSearchForm.setCustomerId (StringUtils.isNotBlank (searchRequestForm.getCustomerId ()) ? Long.parseLong (searchRequestForm.getCustomerId ())
                                                                                                        : 0);
            paymentSearchForm.setPaymentAccount (StringUtils.isNotBlank (searchRequestForm.getPaymentAccountId ()) ? Long.parseLong (searchRequestForm.getPaymentAccountId ())
                                                                                                                  : 0);
            if (null != searchRequestForm.getPaymentTypeCode ())
            {
                paymentSearchForm.setTypeCode (searchRequestForm.getPaymentTypeCode ());
            }
            return paymentSearchForm;
        }
        return null;
    }

    public static AccountingSearchForm searchRequestForm2AccountingSearchForm (SearchRequestForm searchRequestForm)
    {
        if (null != searchRequestForm)
        {
            AccountingSearchForm accountingSearchForm = new AccountingSearchForm ();
            if (StringUtils.isNotBlank (searchRequestForm.getStartDate ())
                && StringUtils.isNotBlank (searchRequestForm.getEndDate ()))
            {
                accountingSearchForm.setStartDate (DateUtils.string2Date (searchRequestForm.getStartDate ()
                                                                          + " 00:00:00.000", DateUtils.DATE_TIME_MILLISECOND_PATTERN));
                accountingSearchForm.setEndDate (DateUtils.string2Date (searchRequestForm.getEndDate () + " 23:59:59.999",
                                                                        DateUtils.DATE_TIME_MILLISECOND_PATTERN));
            }
            accountingSearchForm.setAccountingTypeId (StringUtils.isNotBlank (searchRequestForm.getAccountingTypeId ()) ? Long.parseLong (searchRequestForm.getAccountingTypeId ())
                                                                                                                       : 0);
            accountingSearchForm.setAccountingModeCode (convertAccountingModeCode (searchRequestForm.getAccountingMode ()));
            return accountingSearchForm;
        }
        return null;
    }

    private static AccountingModeCode convertAccountingModeCode (String mode)
    {
        AccountingModeCode modeCode = null;
        if (isNotBlank (mode))
        {
            if (AccountingModeCode.IN_COME.toString ().equalsIgnoreCase (mode))
            {
                modeCode = AccountingModeCode.IN_COME;
            }
            else if (AccountingModeCode.OUT_LAY.toString ().equalsIgnoreCase (mode))
            {
                modeCode = AccountingModeCode.OUT_LAY;
            }
        }
        return modeCode;
    }

    public static LogSearchForm searchRequestForm2LogSearchForm (SearchRequestForm searchRequestForm)
    {
        if (null != searchRequestForm)
        {
            LogSearchForm logSearchForm = new LogSearchForm ();
            if (StringUtils.isNotBlank (searchRequestForm.getStartDate ())
                && StringUtils.isNotBlank (searchRequestForm.getEndDate ()))
            {
                logSearchForm.setStartDate (DateUtils.string2Date (searchRequestForm.getStartDate () + " 00:00:00.000",
                                                                   DateUtils.DATE_TIME_MILLISECOND_PATTERN));
                logSearchForm.setEndDate (DateUtils.string2Date (searchRequestForm.getEndDate () + " 23:59:59.999",
                                                                 DateUtils.DATE_TIME_MILLISECOND_PATTERN));
            }
            if (StringUtils.isNotBlank (searchRequestForm.getUserCreate ()))
            {
                logSearchForm.setUserCreate (Long.parseLong (searchRequestForm.getUserCreate ()));
            }
            if (StringUtils.isNotBlank (searchRequestForm.getLogEventId ()))
            {
                logSearchForm.setLogEventId (Long.parseLong (searchRequestForm.getLogEventId ()));
            }
            return logSearchForm;
        }
        return null;
    }

    public static GoodsStorageCourseBean goodsStorageCourse2GoodsStorageCourseBean (GoodsStorageCourse goodsStorageCourse)
    {
        if (null != goodsStorageCourse)
        {
            GoodsStorageCourseBean goodsStorageCourseBean = new GoodsStorageCourseBean ();
            BeanUtils.copyProperties (goodsStorageCourse, goodsStorageCourseBean);
            goodsStorageCourseBean.setOrderCreateDate (DateUtils.date2String (goodsStorageCourse.getOrderCreate (),
                                                                              DateUtils.DATE_TIME_PATTERN));
            return goodsStorageCourseBean;
        }
        return null;
    }

    public static GoodsDepository goodsDepositoryBean2GoodsDepository (GoodsDepositoryBean bean)
    {
        if (null != bean)
        {
            GoodsDepository goodsDepository = new GoodsDepository ();
            goodsDepository.setId (isNotBlank (bean.getId ()) ? Long.parseLong (bean.getId ()) : 0);
            goodsDepository.setName (bean.getName ());
            goodsDepository.setEnabled (bean.isEnabled ());
            return goodsDepository;
        }
        return null;
    }

    public static GoodsDepositoryBean goodsDepository2GoodsDepositoryBean (GoodsDepository depository)
    {
        if (null != depository)
        {
            GoodsDepositoryBean bean = new GoodsDepositoryBean ();
            bean.setId (depository.getId () + "");
            bean.setName (depository.getName ());
            bean.setEnabled (depository.isEnabled ());
            return bean;
        }
        return null;
    }

    public static GoodsStorage goodsStorageBean2GoodsStorage (GoodsStorageBean bean)
    {
        if (null != bean)
        {
            GoodsStorage storage = new GoodsStorage ();
            BeanUtils.copyProperties (bean, storage, new String[]
            { "id" });
            if (StringUtils.isNotBlank (bean.getId ()))
            {
                storage.setId (Long.parseLong (bean.getId ()));
            }
            storage.setAmount (bean.getCurrentAmount ());
            storage.setPrice (bean.getCurrentPrice ());
            storage.setDepository (goodsDepositoryBean2GoodsDepository (bean.getDepositoryBean ()));
            return storage;
        }
        return null;
    }

    public static GoodsStorageBean goodsStorage2GoodsStorageBean (GoodsStorage storage)
    {
        if (null != storage)
        {
            GoodsStorageBean storageBean = new GoodsStorageBean ();
            BeanUtils.copyProperties (storage, storageBean, new String[]
            { "id" });
            storageBean.setId (storage.getId () + "");
            storageBean.setCurrentAmount (storage.getAmount ());
            storageBean.setCurrentPrice (storage.getPrice ());
            storageBean.setDepositoryBean (goodsDepository2GoodsDepositoryBean (storage.getDepository ()));
            return storageBean;
        }
        return null;
    }

    public static PaymentRecordBean providerPaymentRecord2PaymentRecordBean (ProviderPaymentRecord paymentRecord)
    {
        if (null != paymentRecord)
        {
            PaymentRecordBean ppr = new PaymentRecordBean ();
            BeanUtils.copyProperties (paymentRecord, ppr, new String[]
            { "id" });
            ppr.setId (paymentRecord.getId () + "");
            ppr.setPaymentBean (providerPayment2PaymentBean (paymentRecord.getPayment (), false));
            ppr.setPaymentAccountBean (paymentAccount2PaymentAccountBean (paymentRecord.getPaymentAccount ()));
            ppr.setPaymentWayBean (paymentWay2PaymentWayBean (paymentRecord.getPaymentWay ()));
            return ppr;
        }
        return null;
    }

    public static PaymentRecordBean customerPaymentRecord2PaymentRecordBean (CustomerPaymentRecord paymentRecord)
    {
        if (null != paymentRecord)
        {
            PaymentRecordBean ppr = new PaymentRecordBean ();
            BeanUtils.copyProperties (paymentRecord, ppr, new String[]
            { "id" });
            ppr.setId (paymentRecord.getId () + "");
            ppr.setPaymentBean (customerPayment2PaymentBean (paymentRecord.getPayment (), false));
            ppr.setPaymentAccountBean (paymentAccount2PaymentAccountBean (paymentRecord.getPaymentAccount ()));
            ppr.setPaymentWayBean (paymentWay2PaymentWayBean (paymentRecord.getPaymentWay ()));
            return ppr;
        }
        return null;
    }

    public static ProviderPaymentRecord paymentRecordBean2ProviderPaymentRecord (PaymentRecordBean paymentRecord)
    {
        if (null != paymentRecord)
        {
            ProviderPaymentRecord ppr = new ProviderPaymentRecord ();
            BeanUtils.copyProperties (paymentRecord, ppr, new String[]
            { "id" });
            if (StringUtils.isNotBlank (paymentRecord.getId ()))
            {
                ppr.setId (Long.parseLong (paymentRecord.getId ()));
            }
            ppr.setPaymentAccount (paymentAccountBean2PaymentAccount (paymentRecord.getPaymentAccountBean ()));
            ppr.setPaymentWay (paymentWayBean2PaymentWay (paymentRecord.getPaymentWayBean ()));
            return ppr;
        }
        return null;
    }

    public static CustomerPaymentRecord paymentRecordBean2CustomerPaymentRecord (PaymentRecordBean paymentRecord)
    {
        if (null != paymentRecord)
        {
            CustomerPaymentRecord cpr = new CustomerPaymentRecord ();
            BeanUtils.copyProperties (paymentRecord, cpr, new String[]
            { "id" });
            if (StringUtils.isNotBlank (paymentRecord.getId ()))
            {
                cpr.setId (Long.parseLong (paymentRecord.getId ()));
            }
            cpr.setPaymentAccount (paymentAccountBean2PaymentAccount (paymentRecord.getPaymentAccountBean ()));
            cpr.setPaymentWay (paymentWayBean2PaymentWay (paymentRecord.getPaymentWayBean ()));
            return cpr;
        }
        return null;
    }

    public static PaymentWayBean paymentWay2PaymentWayBean (PaymentWay paymentWay)
    {
        if (null != paymentWay)
        {
            PaymentWayBean bean = new PaymentWayBean ();
            bean.setId (paymentWay.getId () + "");
            bean.setName (paymentWay.getName ());
            return bean;
        }
        return null;
    }

    public static PaymentWay paymentWayBean2PaymentWay (PaymentWayBean paymentWay)
    {
        if (null != paymentWay)
        {
            PaymentWay way = new PaymentWay ();
            if (StringUtils.isNotBlank (paymentWay.getId ()))
            {
                way.setId (Long.parseLong (paymentWay.getId ()));
            }
            way.setName (paymentWay.getName ());
            return way;
        }
        return null;
    }

    public static GoodsIssue convert (GoodsIssueBean bean)
    {
        if (null != bean)
        {
            GoodsIssue goodsIssue = new GoodsIssue ();
            goodsIssue.setId (StringUtils.isNotBlank (bean.getId ()) ? Long.parseLong (bean.getId ()) : 0);
            goodsIssue.setDescription (bean.getDescription ());
            goodsIssue.setComment (bean.getComment ());
            goodsIssue.setDateCreated (DateUtils.string2Date (bean.getCreateDate (), DateUtils.DATE_PATTERN));
            goodsIssue.setLastUpdated (DateUtils.string2Date (bean.getUpdateDate (), DateUtils.DATE_PATTERN));
            goodsIssue.setStatusCode (bean.getStatusCode ());
            if (null != bean.getCustomerBean () && StringUtils.isNotBlank (bean.getCustomerBean ().getId ()))
            {
                goodsIssue.setCustomer (customerBean2Customer (bean.getCustomerBean ()));
            }
            if (null != bean.getProviderBean () && StringUtils.isNotBlank (bean.getProviderBean ().getId ()))
            {
                goodsIssue.setProvider (customerBean2Provider (bean.getProviderBean ()));
            }
            if (null != bean.getGoodsBean () && StringUtils.isNotBlank (bean.getGoodsBean ().getId ()))
            {
                goodsIssue.setGoods (goodsBean2Goods (bean.getGoodsBean ()));
            }
            goodsIssue.setGoodsSerial (bean.getGoodsSerial ());
            if (null != bean.getSignUser () && StringUtils.isNotBlank (bean.getSignUser ().getId ()))
            {
                goodsIssue.setSignUser (Long.parseLong (bean.getSignUser ().getId ()));
            }
            if (null != bean.getUpdateUser () && StringUtils.isNotBlank (bean.getUpdateUser ().getId ()))
            {
                goodsIssue.setUserUpdatedBy (Long.parseLong (bean.getUpdateUser ().getId ()));
            }
            return goodsIssue;
        }
        return null;
    }

    public static GoodsIssueBean convert (GoodsIssue issue)
    {
        if (null != issue)
        {
            GoodsIssueBean bean = new GoodsIssueBean ();
            bean.setId (issue.getId () + "");
            bean.setCustomerBean (customer2CustomerBean (issue.getCustomer ()));
            bean.setProviderBean (provider2CustomerBean (issue.getProvider ()));
            bean.setDescription (issue.getDescription ());
            bean.setComment (issue.getComment ());
            bean.setStatusCode (issue.getStatusCode ());
            bean.setCreateDate (DateUtils.date2String (issue.getDateCreated (), DateUtils.DATE_PATTERN));
            bean.setUpdateDate (DateUtils.date2String (issue.getLastUpdated (), DateUtils.DATE_PATTERN));
            User user = DaoContext.getUserDao ().findOne (issue.getSignUser ());
            if (null != user)
            {
                bean.setSignUser (user2UserBean (user));
            }
            user = DaoContext.getUserDao ().findOne (issue.getUserUpdatedBy ());
            if (null != user)
            {
                bean.setUpdateUser (user2UserBean (user));
            }
            bean.setGoodsBean (goods2GoodsBean (issue.getGoods ()));
            bean.setGoodsSerial (issue.getGoodsSerial ());
            return bean;
        }
        return null;
    }

    public static GoodsSerialNumberBean convert (GoodsSerialNumber serialNumber)
    {
        if (null != serialNumber)
        {
            GoodsSerialNumberBean goodsSerialNumberBean = new GoodsSerialNumberBean ();
            goodsSerialNumberBean.setId (serialNumber.getId () + "");
            goodsSerialNumberBean.setSerialNumber (serialNumber.getSerialNumber ());
            goodsSerialNumberBean.setGoodsBean (goods2GoodsBean (serialNumber.getGoods ()));

            OrderInItem orderInItem = serialNumber.getOrderInItem ();
            if (null != orderInItem)
            {
                goodsSerialNumberBean.setOrderInItemBean (orderInItem2OrderItemBean (orderInItem));
            }
            OrderOutItem orderOutItem = serialNumber.getOrderOutItem ();
            if (null != orderOutItem)
            {
                goodsSerialNumberBean.setOrderOutItemBean (orderOutItem2OrderItemBean (orderOutItem));
            }
            return goodsSerialNumberBean;
        }
        return null;
    }

    public static GoodsSerialNumber convert (GoodsSerialNumberBean serialBean)
    {
        if (null != serialBean)
        {
            GoodsSerialNumber goodsSerialNumber = new GoodsSerialNumber ();
            goodsSerialNumber.setId (StringUtils.isNotBlank (serialBean.getId ()) ? Long.parseLong (serialBean.getId ())
                                                                                 : 0);
            goodsSerialNumber.setSerialNumber (serialBean.getSerialNumber ());
            goodsSerialNumber.setGoods (goodsBean2Goods (serialBean.getGoodsBean ()));
            if (null != serialBean.getOrderInItemBean ()
                && StringUtils.isNotBlank (serialBean.getOrderInItemBean ().getId ()))
            {
                goodsSerialNumber.setOrderInItem (orderItemBean2OrderInItem (serialBean.getOrderInItemBean ()));
            }
            if (null != serialBean.getOrderOutItemBean ()
                && StringUtils.isNotBlank (serialBean.getOrderOutItemBean ().getId ()))
            {
                goodsSerialNumber.setOrderOutItem (orderItemBean2OrderOutItem (serialBean.getOrderOutItemBean ()));
            }
            return goodsSerialNumber;
        }
        return null;
    }

    public static UserBean convert (LoginUser loginUser)
    {
        if (null != loginUser)
        {
            UserBean userBean = new UserBean ();
            userBean.setId (loginUser.getUid () + "");
            userBean.setUserId (loginUser.getUserId ());
            return userBean;
        }
        return null;
    }
    
    public static OrderItemBean getLatestOrderItem (List <OrderItemBean> orderItemList)
    {
        if (!CollectionUtils.isEmpty (orderItemList))
        {
            Collections.sort (orderItemList, new Comparator <OrderItemBean> ()
            {
                @Override
                public int compare (OrderItemBean o1, OrderItemBean o2)
                {
                    return DateUtils.string2Date (o2.getOrderCreate (), DateUtils.DATE_TIME_PATTERN)
                                    .compareTo (DateUtils.string2Date (o1.getOrderCreate (),
                                                                       DateUtils.DATE_TIME_PATTERN));
                }
            });
            return orderItemList.get (0);
        }
        return null;
    }

    public static boolean isNotBlank (String s)
    {
        return StringUtils.isNotBlank (s) && !"null".equals (s.trim ());
    }

    public static String getString (String s)
    {
        if (isNotBlank (s))
        {
            return s.trim ();
        }
        return "";
    }
    
    public static Date stringDateTime2JavaUtilDate(String dateTime) 
    {
        Calendar now = Calendar.getInstance ();
        Date d = now.getTime ();
        if(StringUtils.isNotBlank (dateTime) && dateTime.matches ("\\d{4}-\\d{2}-\\d{2}")) 
        {
            d = DateUtils.string2Date (dateTime + " " + now.get (Calendar.HOUR_OF_DAY) + ":" + now.get (Calendar.MINUTE) + ":" + now.get (Calendar.SECOND) + "." + now.get (Calendar.MILLISECOND), DateUtils.DATE_TIME_MILLISECOND_PATTERN);
        }
        if(StringUtils.isNotBlank (dateTime) && dateTime.matches ("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")) 
        {
            d = DateUtils.string2Date (dateTime + ":" + now.get (Calendar.SECOND) + "." + now.get (Calendar.MILLISECOND), DateUtils.DATE_TIME_MILLISECOND_PATTERN);
        }
        return d;
    }
    
    public static Timestamp stringDateTime2JavaSqlTimestamp(String dateTime) 
    {
        return new Timestamp (stringDateTime2JavaUtilDate (dateTime).getTime ());
    }
    
}
