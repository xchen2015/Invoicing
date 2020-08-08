package com.pinfly.purchasecharge.component.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;

import com.pinfly.purchasecharge.core.config.PurchaseChargeProperties;
import com.pinfly.purchasecharge.core.model.AccountingModeCode;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.PaymentAccountMode;
import com.pinfly.purchasecharge.core.model.RoleAuthority;
import com.pinfly.purchasecharge.core.model.persistence.AccountingType;
import com.pinfly.purchasecharge.core.model.persistence.DeliveryCompany;
import com.pinfly.purchasecharge.core.model.persistence.PaymentAccount;
import com.pinfly.purchasecharge.core.model.persistence.PaymentWay;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsDepository;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsUnit;
import com.pinfly.purchasecharge.core.model.persistence.in.Provider;
import com.pinfly.purchasecharge.core.model.persistence.in.ProviderType;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerType;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.Role;
import com.pinfly.purchasecharge.core.util.PurchaseChargeConstants;
import com.pinfly.purchasecharge.dal.DaoContext;
import com.pinfly.purchasecharge.service.LogService;
import com.pinfly.purchasecharge.service.PersistenceService;
import com.pinfly.purchasecharge.service.QueryService;

public class ComponentContext implements Serializable, InitializingBean, MessageSource
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger (ComponentContext.class);

    public static Map <String, String> orderTemplateMap = new HashMap <String, String> ();
    private static Map <RequestMatcher, Collection <ConfigAttribute>> securityResourceMap = new HashMap <RequestMatcher, Collection <ConfigAttribute>> ();
    private ResourceBundleMessageSource messageSource;
    private boolean initialized;

    private QueryService queryService;
    private PersistenceService persistenceService;
    private LogService logService;
    private Locale locale;

    public LoginUser getLoginUser (HttpServletRequest request)
    {
        LoginUser loginUser = null;
        if (null != request && null != request.getSession (false))
        {
            Object obj = request.getSession (false).getAttribute (PurchaseChargeConstants.LOGIN_USER);
            if (null != obj)
            {
                loginUser = (LoginUser) obj;
            }
            // else
            // {
            // LOGGER.info ("Component Dev Mode is running...");
            // loginUser = new LoginUser ();
            // loginUser.setAdmin (true);
            // loginUser.setUserId ("admin");
            // loginUser.setFullName ("admin");
            // }
        }
        return loginUser;
    }

    public void setLoginUserToSession (HttpServletRequest request, LoginUser loginUser)
    {
        if (null != request)
        {
            HttpSession session = request.getSession (false);
            if (null != session)
            {
                session.setAttribute (PurchaseChargeConstants.LOGIN_USER, loginUser);
                this.locale = request.getLocale ();
            }
        }
    }

    public Map <RequestMatcher, Collection <ConfigAttribute>> getSecurityResourceMap (boolean isRetrieveLatest)
    {
        if (securityResourceMap.isEmpty () || isRetrieveLatest)
        {
            loadAllResource ();
        }
        return securityResourceMap;
    }

    public void setMessageSource (ResourceBundleMessageSource messageSource)
    {
        this.messageSource = messageSource;
    }

    public void setInitialized (boolean initialized)
    {
        this.initialized = initialized;
    }

    private void loadAllResource ()
    {
        List <Role> roles = (List <Role>) getQueryService ().getAllRole ();
        /*
         * 应当是资源为key， 权限为value。 资源通常为url(.do/.html)， 权限就是角色能访问的资源。
         * 一个资源可以由多个权限来访问。
         */
        securityResourceMap = new HashMap <RequestMatcher, Collection <ConfigAttribute>> ();
        for (Role role : roles)
        {
            if(role.isEnabled ()) 
            {
                ConfigAttribute configAttribute = new SecurityConfig (role.getName ());
                List <RoleAuthority> roleAuthorities = getQueryService ().getRoleAuthorityByRole (role.getName ());
                for (RoleAuthority ra : roleAuthorities)
                {
                    RequestMatcher requestMatcher = new AntPathRequestMatcher (ra.getAuthorityValue ());
                    
                    /*
                     * 判断资源和权限的对应关系，如果已经存在相关的资源url，则要通过该url为key提取出权限集合， 将权限增加到权限集合中。
                     */
                    if (securityResourceMap.containsKey (requestMatcher))
                    {
                        Collection <ConfigAttribute> atts = securityResourceMap.get (requestMatcher);
                        atts.add (configAttribute);
                        securityResourceMap.put (requestMatcher, atts);
                    }
                    else
                    {
                        Collection <ConfigAttribute> atts = new ArrayList <ConfigAttribute> ();
                        atts.add (configAttribute);
                        securityResourceMap.put (requestMatcher, atts);
                    }
                }
            }
        }
    }

    private void initDefaultProvider ()
    {
        String providerName = PurchaseChargeProperties.getDefaultProvider ();
        if(StringUtils.isNotBlank (providerName)) 
        {
            Provider defaultProvider = new Provider ();
            defaultProvider.setShortName (providerName);
            defaultProvider.setShortCode (providerName);
            
            try
            {
                if (queryService.getProvider (providerName) == null)
                {
                    defaultProvider = persistenceService.addProvider (defaultProvider);
                    LOGGER.info ("Init default provider successfully. shortName: " + defaultProvider.getShortName ());
                }
            }
            catch (Exception e)
            {
                LOGGER.warn ("Init default provider failed", e);
            }
        }
    }
    
    private void initDefaultDepository () 
    {
        String depositoryName = PurchaseChargeProperties.getDefaultDepository ();
        if(StringUtils.isNotBlank (depositoryName)) 
        {
            GoodsDepository defaultDepository = new GoodsDepository ();
            defaultDepository.setName (depositoryName);
            defaultDepository.setEnabled (true);
            
            try 
            {
                if(null == queryService.getGoodsDepository (depositoryName)) 
                {
                    defaultDepository = persistenceService.addGoodsDepository (defaultDepository);
                    LOGGER.info ("Init default depository successfully. name: " + defaultDepository.getName ());
                }
            }
            catch (Exception e) 
            {
                LOGGER.warn ("Init default depository failed", e);
            }
        }
    }
    
    private void initDefaultDelivery () 
    {
        String[] defaultDeliveryStr = PurchaseChargeProperties.getDefaultDelivery ();
        if(null != defaultDeliveryStr && defaultDeliveryStr.length > 0) 
        {
            List <DeliveryCompany> deliveryCompanies = new ArrayList <DeliveryCompany> ();
            for(String deliveryName : defaultDeliveryStr) 
            {
                if(null == queryService.getDeliveryCompany (deliveryName)) 
                {
                    DeliveryCompany deliveryCompany = new DeliveryCompany ();
                    deliveryCompany.setName (deliveryName);
                    deliveryCompanies.add (deliveryCompany);
                }
            }
            
            try 
            {
                deliveryCompanies = (List <DeliveryCompany>)DaoContext.getDeliveryCompanyDao ().save (deliveryCompanies);
                LOGGER.info ("Init default DeliveryCompany successfully. " + deliveryCompanies);
            }
            catch (Exception e) 
            {
                LOGGER.warn ("Init default DeliveryCompany failed", e);
            }
        }
    }
    
    private void initDefaultPaymentWay () 
    {
        String[] defaultPaymentWayStr = PurchaseChargeProperties.getDefaultPaymentWay ();
        if(null != defaultPaymentWayStr && defaultPaymentWayStr.length > 0) 
        {
            List <PaymentWay> paymentWays = new ArrayList <PaymentWay> ();
            for(String paymentWayName : defaultPaymentWayStr) 
            {
                if(null == queryService.getPaymentWay (paymentWayName)) 
                {
                    PaymentWay paymentWay = new PaymentWay ();
                    paymentWay.setName (paymentWayName);
                    paymentWays.add (paymentWay);
                }
            }
            
            try 
            {
                paymentWays = (List <PaymentWay>)DaoContext.getPaymentWayDao ().save (paymentWays);
                LOGGER.info ("Init default PaymentWay successfully. " + paymentWays);
            }
            catch (Exception e) 
            {
                LOGGER.warn ("Init default PaymentWay failed", e);
            }
        }
    }
    
    private void initDefaultPaymentAccount () 
    {
        String paymentAccountName = PurchaseChargeProperties.getDefaultPaymentAccount ();
        if(StringUtils.isNotBlank (paymentAccountName)) 
        {
            PaymentAccount paymentAccount = new PaymentAccount ();
            paymentAccount.setName (paymentAccountName);
            paymentAccount.setAccountId (paymentAccountName);
            paymentAccount.setAccountMode (PaymentAccountMode.CASH);
            
            try 
            {
                if(null == queryService.getPaymentAccountByName (paymentAccountName)) 
                {
                    paymentAccount = persistenceService.addPaymentAccount (paymentAccount);
                    LOGGER.info ("Init default paymentAccount successfully. name: " + paymentAccount.getName ());
                }
            }
            catch (Exception e) 
            {
                LOGGER.warn ("Init default paymentAccount failed", e);
            }
        }
    }
    
    private void initDefaultCustomerType ()
    {
        String[] customerTypes = PurchaseChargeProperties.getDefaultCustomerType ();
        if(null != customerTypes && customerTypes.length > 0) 
        {
            List <CustomerType> types = new ArrayList <CustomerType> ();
            for(String customerTypeName : customerTypes) 
            {
                if(null == queryService.getCustomerType (customerTypeName)) 
                {
                    CustomerType customerType = new CustomerType ();
                    customerType.setName (customerTypeName);
                    types.add (customerType);
                }
            }
            
            try 
            {
                types = (List <CustomerType>) DaoContext.getCustomerTypeDao ().save (types);
                LOGGER.info ("Init default CustomerType successfully. " + types);
            }
            catch (Exception e) 
            {
                LOGGER.warn ("Init default CustomerType failed", e);
            }
        }
    }
    
    private void initDefaultGoodsUnit ()
    {
        String[] goodsUnits = PurchaseChargeProperties.getDefaultGoodsUnit ();
        if(null != goodsUnits && goodsUnits.length > 0) 
        {
            List <GoodsUnit> units = new ArrayList <GoodsUnit> ();
            for(String goodsUnitName : goodsUnits) 
            {
                if(null == queryService.getGoodsUnit (goodsUnitName)) 
                {
                    GoodsUnit goodsUnit = new GoodsUnit ();
                    goodsUnit.setName (goodsUnitName);
                    units.add (goodsUnit);
                }
            }
            
            try 
            {
                units = (List <GoodsUnit>) DaoContext.getGoodsUnitDao ().save (units);
                LOGGER.info ("Init default GoodsUnit successfully. " + units);
            }
            catch (Exception e) 
            {
                LOGGER.warn ("Init default GoodsUnit failed", e);
            }
        }
    }
    
    private void initDefaultProviderType ()
    {
        String[] providerTypes = PurchaseChargeProperties.getDefaultProviderType ();
        if(null != providerTypes && providerTypes.length > 0) 
        {
            List <ProviderType> types = new ArrayList <ProviderType> ();
            for(String providerTypeName : providerTypes) 
            {
                if(null == queryService.getProviderType (providerTypeName)) 
                {
                    ProviderType providerType = new ProviderType ();
                    providerType.setName (providerTypeName);
                    types.add (providerType);
                }
            }
            
            try 
            {
                types = (List <ProviderType>) DaoContext.getProviderTypeDao ().save (types);
                LOGGER.info ("Init default ProviderType successfully. " + types);
            }
            catch (Exception e) 
            {
                LOGGER.warn ("Init default ProviderType failed", e);
            }
        }
    }
    
    private void initDefaultAccountingType ()
    {
        List <AccountingType> types = new ArrayList <AccountingType> ();
        String[] accountingOutTypes = PurchaseChargeProperties.getDefaultAccountingOutType ();
        if(null != accountingOutTypes && accountingOutTypes.length > 0) 
        {
            for(String accountingOutTypeName : accountingOutTypes) 
            {
                if(null == queryService.getAccountingType (accountingOutTypeName)) 
                {
                    AccountingType accountingType = new AccountingType ();
                    accountingType.setName (accountingOutTypeName);
                    accountingType.setAccountingMode (AccountingModeCode.OUT_LAY);
                    types.add (accountingType);
                }
            }
        }
        
        String[] accountingInTypes = PurchaseChargeProperties.getDefaultAccountingInType ();
        if(null != accountingInTypes && accountingInTypes.length > 0) 
        {
            for(String accountingInTypeName : accountingInTypes) 
            {
                if(null == queryService.getAccountingType (accountingInTypeName)) 
                {
                    AccountingType accountingType = new AccountingType ();
                    accountingType.setName (accountingInTypeName);
                    accountingType.setAccountingMode (AccountingModeCode.IN_COME);
                    types.add (accountingType);
                }
            }
        }
        
        try 
        {
            types = (List <AccountingType>) DaoContext.getAccountingTypeDao ().save (types);
            LOGGER.info ("Init default AccountingType successfully. " + types);
        }
        catch (Exception e) 
        {
            LOGGER.warn ("Init default AccountingType failed", e);
        }
    }

    @Override
    public void afterPropertiesSet () throws Exception
    {
        if(this.initialized) 
        {
            initDefaultProvider ();
            initDefaultDepository ();
            initDefaultDelivery ();
            initDefaultPaymentAccount ();
            initDefaultPaymentWay ();
            initDefaultCustomerType ();
            initDefaultProviderType ();
            initDefaultGoodsUnit ();
            initDefaultAccountingType ();
        }
    }

    @Override
    public String getMessage (String code, Object[] args, String defaultMessage, Locale locale)
    {
        if (null == locale)
        {
            return this.messageSource.getMessage (code, args, defaultMessage, this.locale);
        }
        return this.messageSource.getMessage (code, args, defaultMessage, locale);
    }

    @Override
    public String getMessage (String code, Object[] args, Locale locale) throws NoSuchMessageException
    {
        if (null == locale)
        {
            return this.messageSource.getMessage (code, args, this.locale);
        }
        return this.messageSource.getMessage (code, args, locale);
    }

    @Override
    public String getMessage (MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException
    {
        if (null == locale)
        {
            return this.messageSource.getMessage (resolvable, this.locale);
        }
        return this.messageSource.getMessage (resolvable, locale);
    }

    public String getMessage (String code, Object[] args, String defaultMessage)
    {
        return getMessage (code, args, defaultMessage, null);
    }

    public String getMessage (String code, Object[] args) throws NoSuchMessageException
    {
        return getMessage (code, args, this.locale);
    }

    public String getMessage (String code) throws NoSuchMessageException
    {
        return getMessage (code, null, this.locale);
    }

    public QueryService getQueryService ()
    {
        return queryService;
    }

    public void setQueryService (QueryService queryService)
    {
        this.queryService = queryService;
    }

    public PersistenceService getPersistenceService ()
    {
        return persistenceService;
    }

    public void setPersistenceService (PersistenceService persistenceService)
    {
        this.persistenceService = persistenceService;
    }

    public LogService getLogService ()
    {
        return logService;
    }

    public void setLogService (LogService logService)
    {
        this.logService = logService;
    }

}
