package com.cxx.purchasecharge.component;

import java.sql.Timestamp;

import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.AccountingModeCode;
import com.pinfly.purchasecharge.core.model.PaymentAccountMode;
import com.pinfly.purchasecharge.core.model.persistence.Accounting;
import com.pinfly.purchasecharge.core.model.persistence.AccountingType;
import com.pinfly.purchasecharge.core.model.persistence.PaymentAccount;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderDelivery;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOut;

public class LogUtilTest
{
    public static void main (String[] args)
    {
        Accounting a = new Accounting ();
        AccountingType at = new AccountingType ();
        at.setAccountingMode (AccountingModeCode.IN_COME);
        at.setId (1);
        at.setName ("at1");
        a.setType (at);
        a.setCreated (new Timestamp (System.currentTimeMillis ()));
        PaymentAccount pa = new PaymentAccount ();
        pa.setAccountId ("pa1");
        pa.setName ("paName1");
        pa.setAccountMode (PaymentAccountMode.CASH);
        pa.setId (2);
        pa.setRemainMoney (1000);
        a.setPaymentAccount (pa);
        a.setId (3);
        a.setComment ("test comment");
        a.setCreated (new Timestamp (System.currentTimeMillis ()));
        a.setUpdated (new Timestamp (System.currentTimeMillis ()));
        a.setMoney (100);
        a.setUserCreatedBy (251);
        a.setUserUpdatedBy (251);

        OrderOut orderOut = new OrderOut ();
        orderOut.setId (1);
        OrderDelivery orderDelivery = new OrderDelivery ();
        orderDelivery.setId (2);
        orderDelivery.setOrderOut (orderOut);
        orderOut.setOrderDelivery (orderDelivery);

        System.out.println (LogUtil.createLogComment (orderOut));
    }
}
