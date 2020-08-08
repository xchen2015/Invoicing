package com.pinfly.purchasecharge.service;

import java.sql.Timestamp;
import java.util.List;

import com.pinfly.purchasecharge.core.model.GoodsStorageTransferData;
import com.pinfly.purchasecharge.core.model.OrderStatusCode;
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
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsStorageCheck;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsStoragePriceRevise;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsType;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsUnit;
import com.pinfly.purchasecharge.core.model.persistence.in.OrderIn;
import com.pinfly.purchasecharge.core.model.persistence.in.Provider;
import com.pinfly.purchasecharge.core.model.persistence.in.ProviderContact;
import com.pinfly.purchasecharge.core.model.persistence.in.ProviderPayment;
import com.pinfly.purchasecharge.core.model.persistence.in.ProviderType;
import com.pinfly.purchasecharge.core.model.persistence.out.Customer;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerContact;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerLevel;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerPayment;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerType;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderDelivery;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOut;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.Authority;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.Role;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.User;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

public interface PersistenceService
{
    /****************** Customer Service start ********************/
    Customer addCustomer (Customer customer) throws PcServiceException;

    Customer updateCustomer (Customer customer) throws PcServiceException;

    void deleteCustomer (List <Customer> customers) throws PcServiceException;

    CustomerType addCustomerType (CustomerType customerType) throws PcServiceException;

    CustomerType updateCustomerType (CustomerType customerType) throws PcServiceException;

    void deleteCustomerType (List <CustomerType> customerTypes) throws PcServiceException;

    CustomerLevel addCustomerLevel (CustomerLevel customerLevel) throws PcServiceException;

    List <CustomerLevel> updateCustomerLevel (List <CustomerLevel> customerLevels) throws PcServiceException;

    void deleteCustomerLevel (List <CustomerLevel> customerLevels) throws PcServiceException;

    CustomerContact addCustomerContact (CustomerContact contact) throws PcServiceException;

    CustomerContact updateCustomerContact (CustomerContact contact) throws PcServiceException;

    void deleteCustomerContact (List <CustomerContact> contacts) throws PcServiceException;

    CustomerPayment addCustomerPayment (CustomerPayment payment) throws PcServiceException;

    CustomerPayment updateCustomerPayment (CustomerPayment payment) throws PcServiceException;

    void deleteCustomerPayment (CustomerPayment payment) throws PcServiceException;

    void deleteCustomerPayment (List <CustomerPayment> payments) throws PcServiceException;

    /****************** Customer Service end ********************/

    /****************** Provider Service start ********************/
    Provider addProvider (Provider provider) throws PcServiceException;

    Provider updateProvider (Provider provider) throws PcServiceException;

    void deleteProvider (List <Provider> providers) throws PcServiceException;

    ProviderType addProviderType (ProviderType providerType) throws PcServiceException;

    ProviderType updateProviderType (ProviderType providerType) throws PcServiceException;

    void deleteProviderType (List <ProviderType> providerTypes) throws PcServiceException;

    ProviderContact addProviderContact (ProviderContact contact) throws PcServiceException;

    ProviderContact updateProviderContact (ProviderContact contact) throws PcServiceException;

    void deleteProviderContact (List <ProviderContact> contacts) throws PcServiceException;

    ProviderPayment addProviderPayment (ProviderPayment payment) throws PcServiceException;

    ProviderPayment updateProviderPayment (ProviderPayment payment) throws PcServiceException;

    void deleteProviderPayment (ProviderPayment payment) throws PcServiceException;

    void deleteProviderPayment (List <ProviderPayment> payments) throws PcServiceException;

    /****************** Provider Service end ********************/

    /****************** User,Role Service start ********************/
    List <User> addUsers (List <User> users) throws PcServiceException;

    User updateUser (User user) throws PcServiceException;

    void deleteUser (List <User> users) throws PcServiceException;

    void updatePassword (String userId, String newPwd) throws PcServiceException;

    List <Role> addRoles (List <Role> roles) throws PcServiceException;

    Role updateRole (Role role) throws PcServiceException;

    void deleteRole (List <Role> roles) throws PcServiceException;

    List <Authority> addAuthorities (List <Authority> authorities) throws PcServiceException;

    Authority updateAuthority (Authority authority) throws PcServiceException;

    void deleteAuthority (List <Authority> authorities) throws PcServiceException;

    /****************** User,Role Service end ********************/

    /****************** Goods Service start ********************/
    Goods addGoods (Goods goods) throws PcServiceException;

    Goods updateGoods (Goods goods) throws PcServiceException;
    void updateGoodsAveragePrice (long goodsId) throws PcServiceException;

    void deleteGoods (List <Goods> goodses) throws PcServiceException;

    GoodsType addGoodsType (GoodsType goodsType) throws PcServiceException;

    GoodsType updateGoodsType (GoodsType goodsType) throws PcServiceException;

    void deleteGoodsType (List <GoodsType> goodsTypes) throws PcServiceException;

    GoodsUnit addGoodsUnit (GoodsUnit goodsUnit) throws PcServiceException;

    GoodsUnit updateGoodsUnit (GoodsUnit goodsUnit) throws PcServiceException;

    void deleteGoodsUnit (List <GoodsUnit> goodsUnits) throws PcServiceException;

    GoodsDepository addGoodsDepository (GoodsDepository goodsDepository) throws PcServiceException;

    GoodsDepository updateGoodsDepository (GoodsDepository goodsDepository) throws PcServiceException;

    void deleteGoodsDepository (List <GoodsDepository> goodsDepositorys) throws PcServiceException;

    GoodsPicture addGoodsPicture (GoodsPicture goodsPicture) throws PcServiceException;

    GoodsPicture updateGoodsPicture (GoodsPicture goodsPicture) throws PcServiceException;

    void deleteGoodsPicture (GoodsPicture goodsPicture) throws PcServiceException;

    void deleteGoodsPicture (List <GoodsPicture> goodsPictures) throws PcServiceException;

    GoodsIssue addGoodsIssue (GoodsIssue goodsIssue) throws PcServiceException;

    GoodsIssue updateGoodsIssue (GoodsIssue goodsIssue) throws PcServiceException;

    void deleteGoodsIssue (List <GoodsIssue> goodsIssues) throws PcServiceException;

    GoodsSerialNumber addGoodsSerialNumber (GoodsSerialNumber goodsSerialNumber) throws PcServiceException;

    GoodsSerialNumber updateGoodsSerialNumber (GoodsSerialNumber goodsSerialNumber) throws PcServiceException;

    void deleteGoodsSerialNumber (List <GoodsSerialNumber> goodsSerialNumbers) throws PcServiceException;

    GoodsStorageTransferData addGoodsStorageTransfer (GoodsStorageTransferData storageTransfer) throws PcServiceException;
    
    GoodsStorageCheck addGoodsStorageCheck (GoodsStorageCheck storageCheck) throws PcServiceException;
    
    GoodsStoragePriceRevise addGoodsStoragePriceRevise (GoodsStoragePriceRevise storagePriceRevise) throws PcServiceException;

    /****************** Goods Service end ********************/

    /****************** OrderOut Service start ********************/
    void validateOrderOut (OrderOut order) throws PcServiceException;    
    OrderOut addOrderOut (OrderOut order, PaymentAccount paymentAccount) throws PcServiceException;
    
    OrderOut updateOrderOut (OrderOut order) throws PcServiceException;

    void deleteOrderOut (List <OrderOut> orders) throws PcServiceException;

    boolean updateOrderOutStatus (long orderId, OrderStatusCode statusCode, String comment, Timestamp updateTime) throws PcServiceException;

    OrderDelivery updateOrderDelivery (OrderDelivery orderDelivery) throws PcServiceException;

    /****************** OrderOut Service end ********************/

    /****************** Project Service start ********************/
    Project addProject (Project project) throws PcServiceException;

    Project updateProject (Project project) throws PcServiceException;

    void deleteProject (List <Project> projects) throws PcServiceException;

    boolean updateProjectStatus (long projectId, ProjectStatusCode statusCode, String comment)
                                                                                              throws PcServiceException;

    /****************** Project Service end ********************/

    /****************** OrderIn Service start ********************/
    void validateOrderIn (OrderIn order) throws PcServiceException;
    OrderIn addOrderIn (OrderIn order, PaymentAccount paymentAccount) throws PcServiceException;

    OrderIn updateOrderIn (OrderIn order) throws PcServiceException;

    void deleteOrderIn (List <OrderIn> orders) throws PcServiceException;

    boolean updateOrderInStatus (long orderId, OrderStatusCode statusCode, String comment);

    /****************** OrderIn Service end ********************/

    /****************** Payment Service start ********************/
    PaymentAccount addPaymentAccount (PaymentAccount paymentAccount) throws PcServiceException;

    PaymentAccount updatePaymentAccount (PaymentAccount paymentAccount) throws PcServiceException;

    void deletePaymentAccount (List <PaymentAccount> paymentAccounts) throws PcServiceException;

    AccountingType addAccountingType (AccountingType accountingType) throws PcServiceException;

    AccountingType updateAccountingType (AccountingType accountingType) throws PcServiceException;

    void deleteAccountingType (List <AccountingType> accountingTypes) throws PcServiceException;

    Accounting addAccounting (Accounting accounting) throws PcServiceException;

    Accounting updateAccounting (Accounting accounting) throws PcServiceException;

    void deleteAccounting (Accounting accounting) throws PcServiceException;

    void deleteAccounting (List <Accounting> accountings) throws PcServiceException;

    PaymentWay addPaymentWay (PaymentWay paymentWay) throws PcServiceException;

    PaymentWay updatePaymentWay (PaymentWay paymentWay) throws PcServiceException;

    void deletePaymentWay (List <PaymentWay> paymentWays) throws PcServiceException;

    PaymentTransfer addPaymentTransfer (PaymentTransfer accountTransfer) throws PcServiceException;
    /****************** Payment Service end ********************/
    
    DeliveryCompany addDeliveryCompany (DeliveryCompany deliveryCompany) throws PcServiceException;
    
    DeliveryCompany updateDeliveryCompany (DeliveryCompany deliveryCompany) throws PcServiceException;
    
    void deleteDeliveryCompany (List <DeliveryCompany> deliveryCompanies) throws PcServiceException;
    
    void resetData (String userId, String password) throws PcServiceException;

}
