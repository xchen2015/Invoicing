package com.pinfly.purchasecharge.core.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.pinfly.common.config.BaseConfigUtil;
import com.pinfly.purchasecharge.core.model.OrderTypeCode;

public class PurchaseChargeUtils
{
    private static final Logger LOGGER = Logger.getLogger (PurchaseChargeUtils.class);

    public static String readFileToString (String fileNameWithExtension) throws IOException
    {
        String fileStr = "";
        String propsDir = BaseConfigUtil.getPfyPropertiesDir ();

        if (StringUtils.isNotBlank (propsDir))
        {
            propsDir = propsDir.trim ();
            if (!propsDir.endsWith (File.separator))
            {
                propsDir += File.separator;
            }
            String file = propsDir + fileNameWithExtension;
            LOGGER.info (fileNameWithExtension + " file path is " + file);

            fileStr = FileUtils.readFileToString (new File (file), "utf-8");
        }
        return fileStr;
    }

    public static String getGoodsImageStoreDirectory ()
    {
        String commonDir = BaseConfigUtil.getCxxCommonDir ();
        if (StringUtils.isNotBlank (commonDir))
        {
            commonDir = commonDir.trim ();
            if (!commonDir.endsWith (File.separator))
            {
                commonDir += File.separator;
            }
            return commonDir + "images";
        }
        return "";
    }

    public static String getDataBackupStoreDirectory ()
    {
        String commonDir = BaseConfigUtil.getCxxCommonDir ();
        if (StringUtils.isNotBlank (commonDir))
        {
            commonDir = commonDir.trim ();
            if (!commonDir.endsWith (File.separator))
            {
                commonDir += File.separator;
            }
            return commonDir + "backup";
        }
        return "";
    }
    
    public static String generateOrderBid (OrderTypeCode orderTypeCode, String uniqueId) 
    {
        String orderBid = "";
        String IdValue = StringUtils.isNotBlank (uniqueId) ? uniqueId : IDGenerator.getIdentityID () + "";
        if (OrderTypeCode.OUT.equals (orderTypeCode)) 
        {
            orderBid = PurchaseChargeConstants.XIAO_SHOU + IdValue;
        }
        else if (OrderTypeCode.OUT_RETURN.equals (orderTypeCode)) 
        {
            orderBid = PurchaseChargeConstants.XIAO_SHOU_TUI_HUO + IdValue;
        }
        else if (OrderTypeCode.IN.equals (orderTypeCode)) 
        {
            orderBid = PurchaseChargeConstants.CAI_GOU + IdValue;
        }
        else if (OrderTypeCode.IN_RETURN.equals (orderTypeCode)) 
        {
            orderBid = PurchaseChargeConstants.CAI_GOU_TUI_HUO + IdValue;
        }
        return orderBid;
    }
    
    public static String generateCustomerPaymentBid () 
    {
        return PurchaseChargeConstants.SHOU_KUAN + IDGenerator.getIdentityID ();
    }
    
    public static String generateProviderPaymentBid () 
    {
        return PurchaseChargeConstants.FU_KUAN + IDGenerator.getIdentityID ();
    }
    
    public static String generateStorageProfitBid () 
    {
        return PurchaseChargeConstants.KU_CUN_PAN_YING + IDGenerator.getIdentityID ();
    }
    
    public static String generateStorageLossBid () 
    {
        return PurchaseChargeConstants.KU_CUN_PAN_KUI + IDGenerator.getIdentityID ();
    }
    
    public static String generateStoragePriceUpBid () 
    {
        return PurchaseChargeConstants.KU_CUN_PRICE_UP + IDGenerator.getIdentityID ();
    }
    
    public static String generateStoragePriceDownBid () 
    {
        return PurchaseChargeConstants.KU_CUN_PRICE_DOWN + IDGenerator.getIdentityID ();
    }
    
    public static String generateStorageTransferBid () 
    {
        return PurchaseChargeConstants.KU_CUN_DIAO_BO + IDGenerator.getIdentityID ();
    }
}
