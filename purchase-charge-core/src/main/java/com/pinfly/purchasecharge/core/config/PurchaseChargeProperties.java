package com.pinfly.purchasecharge.core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.pinfly.common.config.BaseConfigUtil;

public class PurchaseChargeProperties extends Properties
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger (PurchaseChargeProperties.class);
    public static final String PURCHASE_CHARGE_PROPERTIES_FILE = "pc-config.properties";

    private static PurchaseChargeProperties purchaseChargeProperties = getInstance (PURCHASE_CHARGE_PROPERTIES_FILE);

    private PurchaseChargeProperties (String fileName)
    {
        String propsDir = BaseConfigUtil.getPfyPropertiesDir ();
        InputStream inputStream = null;

        if (StringUtils.isNotBlank (propsDir))
        {
            propsDir = propsDir.trim ();
            if (!propsDir.endsWith (File.separator))
            {
                propsDir += File.separator;
            }
            String propertiesFile = propsDir + PURCHASE_CHARGE_PROPERTIES_FILE;
            logger.info (PURCHASE_CHARGE_PROPERTIES_FILE + " file path is " + propertiesFile);

            try
            {
                inputStream = new FileInputStream (propertiesFile);
                load (new InputStreamReader (inputStream, "UTF-8"));

                validateProperties (this);
            }
            catch (FileNotFoundException e)
            {
                logger.warn ("Can't find " + PURCHASE_CHARGE_PROPERTIES_FILE, e);
            }
            catch (IOException e)
            {
                logger.warn ("Can't load " + PURCHASE_CHARGE_PROPERTIES_FILE, e);
            }
        }
        else
        {
            logger.warn ("Can't find properties directory.");
        }
    }

    private Properties validateProperties (Properties properties)
    {
        List <String> errors = new ArrayList <String> ();

        // check for mandatory and complete properties with default values when
        // missing
        for (ConfigurationProperties confProps : ConfigurationProperties.values ())
        {
            if (properties.get (confProps.getName ()) == null)
            {
                if (confProps.isMandatory ())
                {
                    errors.add ("The mandatory Property '" + confProps.getName () + "' is not set.");
                }
                else if (confProps.getDefaultValue () != null)
                {
                    properties.setProperty (confProps.getName (), confProps.getDefaultValue ());
                }
            }
        }

        if (!errors.isEmpty ())
        {
            logger.error (errors.toString ());
            throw new IllegalArgumentException (errors.toString ());
        }

        return properties;
    }

    public static synchronized PurchaseChargeProperties getInstance (String fileName)
    {
        if (purchaseChargeProperties == null)
        {
            purchaseChargeProperties = new PurchaseChargeProperties (fileName);
        }
        return purchaseChargeProperties;
    }

    public static synchronized PurchaseChargeProperties getInstance ()
    {
        if (purchaseChargeProperties == null)
        {
            purchaseChargeProperties = new PurchaseChargeProperties (PURCHASE_CHARGE_PROPERTIES_FILE);
        }
        return purchaseChargeProperties;
    }

    public static String getDbDriver ()
    {
        return purchaseChargeProperties.getProperty (ConfigurationProperties.JPA_DB_DRIVER.getName ());
    }

    public static String getDbUrl ()
    {
        return purchaseChargeProperties.getProperty (ConfigurationProperties.JPA_DB_URL.getName ());
    }

    public static String getDbInstance ()
    {
        return purchaseChargeProperties.getProperty (ConfigurationProperties.JPA_DB_INSTANCE.getName ());
    }

    public static String getDbUser ()
    {
        return purchaseChargeProperties.getProperty (ConfigurationProperties.JPA_DB_USER.getName ());
    }

    public static String getDbPassword ()
    {
        return purchaseChargeProperties.getProperty (ConfigurationProperties.JPA_DB_PASSWORD.getName ());
    }

    public static String getDefaultUser ()
    {
        return purchaseChargeProperties.getProperty (ConfigurationProperties.PC_DEFAULT_USER.getName ());
    }

    public static String getDefaultRole ()
    {
        return purchaseChargeProperties.getProperty (ConfigurationProperties.PC_DEFAULT_ROLE.getName ());
    }

    public static String getDefaultPassword ()
    {
        return purchaseChargeProperties.getProperty (ConfigurationProperties.PC_DEFAULT_PASSWORD.getName ());
    }
    
    public static String getDefaultProvider ()
    {
        return purchaseChargeProperties.getProperty (ConfigurationProperties.PC_DEFAULT_PROVIDER.getName ());
    }
    
    public static String getDefaultDepository ()
    {
        return purchaseChargeProperties.getProperty (ConfigurationProperties.PC_DEFAULT_DEPOSITORY.getName ());
    }

    public static String[] getDefaultDelivery ()
    {
        String deliveryStr = purchaseChargeProperties.getProperty (ConfigurationProperties.PC_DEFAULT_DELIVERY.getName ());
        if(StringUtils.isNotBlank (deliveryStr)) 
        {
            return deliveryStr.split (",");
        }
        return null;
    }
    
    public static String[] getDefaultPaymentWay ()
    {
        String paymentWayStr = purchaseChargeProperties.getProperty (ConfigurationProperties.PC_DEFAULT_PAYMENT_WAY.getName ());
        if(StringUtils.isNotBlank (paymentWayStr)) 
        {
            return paymentWayStr.split (",");
        }
        return null;
    }
    
    public static String getDefaultPaymentAccount ()
    {
        return purchaseChargeProperties.getProperty (ConfigurationProperties.PC_DEFAULT_PAYMENT_ACCOUNT.getName ());
    }
    
    public static String[] getDefaultCustomerType ()
    {
        String customerTypeStr = purchaseChargeProperties.getProperty (ConfigurationProperties.PC_DEFAULT_CUSTOMER_TYPE.getName ());
        if(StringUtils.isNotBlank (customerTypeStr)) 
        {
            return customerTypeStr.split (",");
        }
        return null;
    }
    
    public static String[] getDefaultProviderType ()
    {
        String providerTypeStr = purchaseChargeProperties.getProperty (ConfigurationProperties.PC_DEFAULT_PROVIDER_TYPE.getName ());
        if(StringUtils.isNotBlank (providerTypeStr)) 
        {
            return providerTypeStr.split (",");
        }
        return null;
    }
    
    public static String[] getDefaultGoodsUnit ()
    {
        String goodsUnitStr = purchaseChargeProperties.getProperty (ConfigurationProperties.PC_DEFAULT_GOODS_UNIT.getName ());
        if(StringUtils.isNotBlank (goodsUnitStr)) 
        {
            return goodsUnitStr.split (",");
        }
        return null;
    }
    
    public static String[] getDefaultAccountingOutType ()
    {
        String accountingTypeStr = purchaseChargeProperties.getProperty (ConfigurationProperties.PC_DEFAULT_ACCOUNTING_OUT_TYPE.getName ());
        if(StringUtils.isNotBlank (accountingTypeStr)) 
        {
            return accountingTypeStr.split (",");
        }
        return null;
    }
    
    public static String[] getDefaultAccountingInType ()
    {
        String accountingTypeStr = purchaseChargeProperties.getProperty (ConfigurationProperties.PC_DEFAULT_ACCOUNTING_IN_TYPE.getName ());
        if(StringUtils.isNotBlank (accountingTypeStr)) 
        {
            return accountingTypeStr.split (",");
        }
        return null;
    }

    public static boolean getDataInitFlag ()
    {
        return Boolean.parseBoolean (purchaseChargeProperties.getProperty (ConfigurationProperties.PC_DATA_INIT_FLAG.getName ()));
    }
    
    public static boolean getPasswordEncryptFlag ()
    {
        return Boolean.parseBoolean (purchaseChargeProperties.getProperty (ConfigurationProperties.PC_PASSWORD_ENCRYPT_FLAG.getName ()));
    }

    public static boolean getAllowNegativeStorageFlag ()
    {
        return Boolean.parseBoolean (purchaseChargeProperties.getProperty (ConfigurationProperties.PC_ALLOW_NEGATIVE_STORAGE_FLAG.getName ()));
    }
    
    public static boolean getAllowNegativePriceFlag ()
    {
        return Boolean.parseBoolean (purchaseChargeProperties.getProperty (ConfigurationProperties.PC_ALLOW_NEGATIVE_PRICE_FLAG.getName ()));
    }
    
    public static boolean getAllowNegativePaymentFlag ()
    {
        return Boolean.parseBoolean (purchaseChargeProperties.getProperty (ConfigurationProperties.PC_ALLOW_NEGATIVE_PAYMENT_FLAG.getName ()));
    }
    
    public static boolean getAllowCacheGoods ()
    {
        return Boolean.parseBoolean (purchaseChargeProperties.getProperty (ConfigurationProperties.PC_ALLOW_CACHE_GOODS.getName ()));
    }
    
    public static boolean getAllowCachePaymentAccount ()
    {
        return Boolean.parseBoolean (purchaseChargeProperties.getProperty (ConfigurationProperties.PC_ALLOW_CACHE_PAYMENT_ACCOUNT.getName ()));
    }

    public static int getDefaultPaymentDueDays ()
    {
        return Integer.parseInt (purchaseChargeProperties.getProperty (ConfigurationProperties.PC_DEFAULT_PAYMENT_DUE_DAYS.getName ()));
    }

    public static float getDefaultCustomerMaxDebt ()
    {
        return Float.parseFloat (purchaseChargeProperties.getProperty (ConfigurationProperties.PC_DEFAULT_CUSTOMER_MAX_DEBT.getName ()));
    }

    public String getConfig (String key)
    {
        return purchaseChargeProperties.getProperty (key);
    }

    public String getConfig (String key, String defaultValue)
    {
        String value = defaultValue;
        try
        {
            value = purchaseChargeProperties.getProperty (key);
        }
        catch (Exception e)
        {
            logger.warn ("The " + key + " not exist", e);
        }
        return value;
    }

    @SuppressWarnings ("all")
    public String getConfigFormatted (String key, String... arguments)
    {
        String value = "";
        try
        {
            String pattern = purchaseChargeProperties.getProperty (key);
            value = MessageFormat.format (pattern, arguments);
        }
        catch (Exception e)
        {
            logger.warn ("The " + key + " getConfigFormatted not exist", e);
        }
        return value;
    }

}
