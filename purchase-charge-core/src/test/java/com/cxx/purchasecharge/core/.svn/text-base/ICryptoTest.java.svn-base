package com.cxx.purchasecharge.core;

import org.junit.Test;

import com.pinfly.common.crypto.CryptoFactory;
import com.pinfly.purchasecharge.core.util.PurchaseChargeConstants;

public class ICryptoTest
{
    static
    {
        System.setProperty (PurchaseChargeConstants.PINFLY_PROPS_DIR, "C:/ChenXiangxiao/Cxx/Common/properties");
        System.setProperty (PurchaseChargeConstants.COMMON_PROPERTY_DIR, "C:/ChenXiangxiao/Cxx/Common");
        // System.setProperty (CoreConstants.PINFLY_PROPS_DIR,
        // "C:/cxx/Carefx/Common/properties");
        // System.setProperty (CoreConstants.COMMON_PROPERTY_DIR,
        // "C:/cxx/Carefx/Common");
    }

    @Test
    public void test () throws Exception
    {
        String plain = "carefx";
        String ciperText = CryptoFactory.getInstance ().encrypt (plain);
        System.out.println (ciperText);
        String decryptText = CryptoFactory.getInstance ().decrypt (ciperText);
        System.out.println (plain.equals (decryptText));
        System.out.println (decryptText);
    }

}
