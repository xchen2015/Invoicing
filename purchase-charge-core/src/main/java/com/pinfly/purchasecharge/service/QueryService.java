package com.pinfly.purchasecharge.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
import com.pinfly.purchasecharge.core.model.persistence.in.ProviderType;
import com.pinfly.purchasecharge.core.model.persistence.out.Customer;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerContact;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerLevel;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerPayment;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerType;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOut;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOutItem;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.Authority;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.Role;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.User;

public interface QueryService
{
    /****************** Customer Service start ********************/
    Customer getCustomer (long id);

    Customer getCustomer (String name);

    Customer getCustomerByShortCode (String shortCode);

    Customer getCustomerByContact (long contactId);

    List <Customer> getCustomerByShortNameLike (String name, String signUser, boolean admin);

    List <Customer> getAllCustomer (String signUser, boolean admin);

    // Page <Customer> findByFuzzy (Pageable pageable, String searchKey, String
    // signUser, boolean admin);
    Page <Customer> findCustomerByFuzzy (Pageable pageable, String searchKey, String signUser, boolean admin);

    CustomerContact getCustomerContact (long id);

    CustomerContact getCustomerContact (String name);

    // Page <CustomerContact> findCustomerContactByFuzzy (Pageable pageable,
    // String searchKey, String signUser, boolean admin);

    List <CustomerType> getAllCustomerType ();

    CustomerType getCustomerType (String name);

    CustomerType getCustomerType (long id);

    List <CustomerLevel> getAllCustomerLevel ();

    CustomerLevel getCustomerLevel (String name);

    CustomerLevel getCustomerLevel (long customerId);

    CustomerLevel getCustomerLevelById (long id);

    Page <CustomerPayment> findPagedCustomerPayment (PaymentSearchForm searchForm, Pageable pageable);

    List <CustomerPayment> findCustomerPaymentByAdvance (PaymentSearchForm searchForm);

    float countCustomerUnpay (String searchKey, String signUser, boolean admin);

    float countCustomerPaid (PaymentSearchForm searchForm);
    
    float countCustomerNewUnPaid (PaymentSearchForm searchForm);

    /****************** Customer Service end ********************/

    /****************** Provider Service start ********************/
    Provider getProvider (long id);

    Provider getProvider (String name);

    Provider getProviderByShortCode (String shortCode);

    Provider getProviderByContact (long contactId);

    List <Provider> getProviderByShortNameLike (String name, String signUser, boolean admin);

    List <Provider> getAllProvider (String signUser, boolean admin);

    // Page <Provider> findByFuzzy (Pageable pageable, String searchKey, String
    // signUser, boolean admin);
    Page <Provider> findProviderByFuzzy (Pageable pageable, String searchKey, String signUser, boolean admin);

    ProviderContact getProviderContact (long id);

    ProviderContact getProviderContact (String name);

    // Page <ProviderContact> findProviderContactByFuzzy (Pageable pageable,
    // String searchKey, String signUser, boolean admin);

    List <ProviderType> getAllProviderType ();

    ProviderType getProviderType (String name);

    ProviderType getProviderType (long id);

    Page <ProviderPayment> findPagedProviderPayment (PaymentSearchForm searchForm, Pageable pageable);

    List <ProviderPayment> findProviderPaymentByAdvance (long providerId, Timestamp start, Timestamp end);

    float countProviderUnpay (String searchKey, String signUser, boolean admin);

    float countProviderPaid (PaymentSearchForm searchForm);
    
    float countProviderNewUnPaid (PaymentSearchForm searchForm);

    /****************** Provider Service end ********************/

    /****************** OrderOut Service start ********************/
    OrderOut getOrderOut (long id);
    
    OrderOut getOrderOut (String bid);

    List <OrderOut> getOrderOutByCustomer (long customerId);

    Page <OrderOut> findOrderOutByFuzzy (OrderTypeCode typeCode, Pageable pageable, String searchKey, String signUser,
                                         boolean admin);

    Page <OrderOut> findOrderOutBySearchForm (OrderTypeCode typeCode, Pageable pageable, OrderSearchForm searchForm,
                                              String signUser, boolean admin);

    Page <OrderOut> findOrderOutBySearchForm (Pageable pageable, OrderSearchForm searchForm, String signUser,
                                              boolean admin);

    List <OrderOutItem> getOrderOutItemByOrder (long orderId, boolean resultWithOrder);
    
    List <OrderOutItem> getOrderOutItemByGoods (long goodsId);
    
    float countOrderOutReceivableBySearchForm (OrderTypeCode orderType, OrderSearchForm searchForm, String signUser, boolean admin);
    
    float countOrderOutPaidMoneyBySearchForm (OrderTypeCode orderType, OrderSearchForm searchForm, String signUser, boolean admin);

    float countOrderOutProfitBySearchForm (OrderTypeCode orderType, OrderSearchForm searchForm, String signUser, boolean admin);

    /****************** OrderOut Service end ********************/

    /****************** OrderIn Service start ********************/
    OrderIn getOrderIn (long id);
    
    OrderIn getOrderIn (String bid);

    List <OrderIn> getOrderInByProvider (long providerId);

    Page <OrderIn> findOrderInByFuzzy (OrderTypeCode typeCode, Pageable pageable, String searchKey, String signUser,
                                       boolean admin);

    Page <OrderIn> findOrderInBySearchForm (OrderTypeCode typeCode, Pageable pageable, OrderSearchForm searchForm,
                                            String signUser, boolean admin);

    Page <OrderIn> findOrderInBySearchForm (Pageable pageable, OrderSearchForm searchForm, String signUser,
                                            boolean admin);

    List <OrderInItem> getOrderInItemByOrder (long orderId, boolean resultWithOrder);
    
    List <OrderInItem> getOrderInItemByGoods (long goodsId);

    float countOrderInReceivableBySearchForm (OrderTypeCode orderType, OrderSearchForm searchForm, String signUser, boolean admin);
    
    float countOrderInPaidMoneyBySearchForm (OrderTypeCode orderType, OrderSearchForm searchForm, String signUser, boolean admin);
    
    /****************** OrderIn Service end ********************/


    /****************** Goods Service start ********************/
    List <Goods> getAllGoods ();

    Page <Goods> getGoods (long goodsTypeId, Pageable pageable, String searchKey, boolean showHasStorage);

    List <Goods> getGoodsByFuzzyName (String name);

    List <Goods> getGoodsByNameOrCode (String nameOrCode);

    List <Goods> getGoods (String name);

    Goods getGoodsBySpecificationModel (String name, String specificationModel);

    Goods getGoodsByShortCode (String shortCode);

    Goods getGoods (long id);

    long countGoods ();

    long countGoodsIncrement (Date start, Date end);

    long countGoodsHasStorage ();

    long countGoodsByType (long type);

    List <Goods> getGoodsByTypeAndDepository (long typeId, long depositoryId);

    List <GoodsUnit> getAllGoodsUnit ();

    GoodsUnit getGoodsUnit (String name);

    GoodsUnit getGoodsUnit (long id);

    List <GoodsType> getAllGoodsType ();

    GoodsType getGoodsType (String name);

    GoodsType getGoodsType (long id);

    List <GoodsType> getGoodsTypeByParent (long parentId);

    List <GoodsDepository> getAllGoodsDepository ();

    GoodsDepository getGoodsDepository (String name);

    GoodsDepository getGoodsDepository (long id);

    long countGoodsStorageCourse (GoodsStorageCourseSearchForm searchForm);
    
    Page <GoodsStorageCourse> getGoodsStorageCourse (Pageable pageable, GoodsStorageCourseSearchForm searchForm);

    long countGoodsRestAmount (long goodsTypeId, String searchKey);

    float countGoodsRestWorth (long goodsTypeId, String searchKey);

    /****************** Goods Service end ********************/

    /****************** User,Role Service start ********************/
    long getUniqueIdByUserId (String userId);

    List <User> getAllUser ();

    Page <User> findUser (Pageable pageable, String searchKey);

    User getUser (String userId);

    User getUser (long userUid);

    boolean checkPassword (String userId, String password);

    List <Role> getAllRole ();

    Role getRole (String name);

    List <Authority> getAllAuthority ();

    List <Authority> getParentAuthority (long authId);

    Authority getAuthority (String name);

    List <RoleAuthority> getRoleAuthorityByRole (String roleName);

    List <UserRole> getUserRoleByUserId (String userId);

    /****************** User,Role Service end ********************/

    /****************** Payment Service start ********************/
    Page <Accounting> findPageAccounting (AccountingSearchForm searchForm, Pageable pageable);

    List <Accounting> findAccountingByType (long typeId);

    float countAccountingExpense (AccountingSearchForm searchForm);

    float countAccountingIncome (AccountingSearchForm searchForm);

    float countAccountingRevenue (AccountingSearchForm searchForm);

    List <AccountingType> getAllAccountingType ();

    List <AccountingType> getAccountingType (AccountingModeCode modeCode);

    AccountingType getAccountingType (String name);

    AccountingType getAccountingType (long id);

    List <PaymentAccount> getAllPaymentAccount ();

    PaymentAccount getPaymentAccount (long id);

    PaymentAccount getPaymentAccountByName (String name);

    PaymentAccount getPaymentAccountByAccount (String account);
    
    PaymentAccount getPaymentAccountByOrderId (OrderTypeCode orderTypeCode, String orderId, PaymentTypeCode paymentTypeCode);
    
    String getPaymentTransferSource (PaymentTransferTypeCode transferTypeCode, long sourceId);

    PaymentWay getPaymentWay (long id);

    PaymentWay getPaymentWay (String name);

    List <PaymentWay> getAllPaymentWay ();

    /****************** Payment Service end ********************/

    List <DeliveryCompany> getAllDeliveryCompany ();

    DeliveryCompany getDeliveryCompany (String name);

    DeliveryCompany getDeliveryCompany (long id);

}
