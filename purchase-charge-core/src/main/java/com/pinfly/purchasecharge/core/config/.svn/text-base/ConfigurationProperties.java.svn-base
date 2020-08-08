package com.pinfly.purchasecharge.core.config;

public enum ConfigurationProperties
{
    JPA_DB_DRIVER("jpa.dbDriver", true, null),
    JPA_DB_URL("jpa.dbUrl", true, null),
    JPA_DB_INSTANCE("jpa.dbInstance", true, null),
    JPA_DB_USER ("jpa.dbUser", true, null),
    JPA_DB_PASSWORD ("jpa.dbPassword", true, null),
    
    PC_DATA_INIT_FLAG ("pc.dataInitFlag", false, "false"),
    PC_PASSWORD_ENCRYPT_FLAG ("pc.passwordEncryptFlag", false, "true"),
    PC_ALLOW_NEGATIVE_STORAGE_FLAG ("pc.allowNegativeStorageFlag", false, "true"),
    PC_ALLOW_NEGATIVE_PRICE_FLAG ("pc.allowNegativePriceFlag", false, "true"),
    PC_ALLOW_NEGATIVE_PAYMENT_FLAG ("pc.allowNegativePaymentFlag", false, "true"),
    PC_ALLOW_CACHE_GOODS ("pc.allowCacheGoods", false, "true"),
    PC_ALLOW_CACHE_PAYMENT_ACCOUNT ("pc.allowCachePaymentAccount", false, "true"),
    PC_INORDER_ADDPAID_COMMENT ("pc.inorder.addPaid.comment", false, "新增采购进货单"),
    PC_INORDER_DELPAID_COMMENT ("pc.inorder.delPaid.comment", false, "新增采购退货单"),
    PC_OUTORDER_ADDPAID_COMMENT ("pc.outorder.addPaid.comment", false, "新增销售进货单"),
    PC_OUTORDER_DELPAID_COMMENT ("pc.outorder.delPaid.comment", false, "新增销售退货单"),
    PC_INITIAL_BALANCE_COMMENT ("pc.initialBalance.comment", false, "期初余额"),
    PC_DEFAULT_PAYMENT_DUE_DAYS ("pc.defaultPaymentDueDays", false, "30"),
    PC_DEFAULT_CUSTOMER_MAX_DEBT ("pc.defaultCustomerMaxDebt", false, "10000"),
    
    PC_DEFAULT_USER ("pc.defaultUser", false, "admin"),
    PC_DEFAULT_PASSWORD ("pc.defaultPassword", false, "PinFei2!"),
    PC_DEFAULT_ROLE ("pc.defaultRole", false, "admin"),
    PC_DEFAULT_DELIVERY ("pc.defaultDelivery", false, "自提,无需物流"),
    PC_DEFAULT_DEPOSITORY ("pc.defaultDepository", false, "默认仓库"),
    PC_DEFAULT_PAYMENT_WAY ("pc.defaultPaymentWay", false, "现金"),
    PC_DEFAULT_PAYMENT_ACCOUNT ("pc.defaultPaymentAccount", false, "现金"),
    PC_DEFAULT_CUSTOMER_TYPE ("pc.defaultCustomerType", false, "零售客户"),
    PC_DEFAULT_PROVIDER_TYPE ("pc.defaultProviderType", false, "总代理"),
    PC_DEFAULT_GOODS_UNIT ("pc.defaultGoodsUnit", false, "个"),
    PC_DEFAULT_ACCOUNTING_OUT_TYPE ("pc.defaultAccountingOutType", false, "房租,水电费,运费"),
    PC_DEFAULT_ACCOUNTING_IN_TYPE ("pc.defaultAccountingInType", false, "安装施工"),
    PC_DEFAULT_PROVIDER ("pc.defaultProvider", false, "期初库存");

    private String name;
    private boolean mandatory;
    private String defaultValue;

    private ConfigurationProperties (String name, boolean mandatory, String defaultValue)
    {
        this.name = name;
        this.mandatory = mandatory;
        this.defaultValue = defaultValue;
    }

    public static ConfigurationProperties lookup (String name)
    {
        if (name != null)
        {
            for (ConfigurationProperties confProp : values ())
            {
                if (confProp.getName ().equalsIgnoreCase (name))
                {
                    return confProp;
                }
            }
        }
        return null;
    }

    public String getName ()
    {
        return name;
    }

    public boolean isMandatory ()
    {
        return mandatory;
    }

    public String getDefaultValue ()
    {
        return defaultValue;
    }
}
