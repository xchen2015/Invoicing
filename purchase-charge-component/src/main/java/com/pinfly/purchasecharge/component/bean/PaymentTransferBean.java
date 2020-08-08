package com.pinfly.purchasecharge.component.bean;

import javax.validation.constraints.NotNull;

import com.pinfly.purchasecharge.core.model.PaymentTransferTypeCode;

/**
 * 
 * @author xiang
 * 
 */
public class PaymentTransferBean extends BaseBean
{
    private static final long serialVersionUID = 7118174668290265090L;

    @NotNull
    private PaymentAccountBean targetAccountBean;
    private PaymentTransferTypeCode transferTypeCode = PaymentTransferTypeCode.CUSTOMER_TRANSFER;
    private String source;
    private float inMoney;
    private float outMoney;
    private float remainMoney;
    private String createDate;
    private String comment;
    private String userCreated;

    public PaymentAccountBean getTargetAccountBean ()
    {
        return targetAccountBean;
    }

    public void setTargetAccountBean (PaymentAccountBean targetAccountBean)
    {
        this.targetAccountBean = targetAccountBean;
    }

    public PaymentTransferTypeCode getTransferTypeCode ()
    {
        return transferTypeCode;
    }

    public void setTransferTypeCode (PaymentTransferTypeCode transferTypeCode)
    {
        this.transferTypeCode = transferTypeCode;
    }

    public String getSource ()
    {
        return source;
    }

    public void setSource (String source)
    {
        this.source = source;
    }

    public float getInMoney ()
    {
        return inMoney;
    }

    public void setInMoney (float inMoney)
    {
        this.inMoney = inMoney;
    }

    public float getOutMoney ()
    {
        return outMoney;
    }

    public void setOutMoney (float outMoney)
    {
        this.outMoney = outMoney;
    }

    public float getRemainMoney ()
    {
        return remainMoney;
    }

    public void setRemainMoney (float remainMoney)
    {
        this.remainMoney = remainMoney;
    }

    public String getCreateDate ()
    {
        return createDate;
    }

    public void setCreateDate (String createDate)
    {
        this.createDate = createDate;
    }

    public String getComment ()
    {
        return comment;
    }

    public void setComment (String comment)
    {
        this.comment = comment;
    }

    public String getUserCreated ()
    {
        return userCreated;
    }

    public void setUserCreated (String userCreated)
    {
        this.userCreated = userCreated;
    }

}
