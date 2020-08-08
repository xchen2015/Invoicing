package com.pinfly.purchasecharge.component.bean;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import com.pinfly.purchasecharge.core.util.Arith;

/**
 * 客户
 */
public class CustomerBean extends BaseBean
{
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String shortName;
    @NotBlank
    private String shortCode;
    private float unpayMoney;

    private boolean sharable = false;
    private boolean deleted = false;

    private String comment;

    // @NotBlank
    private String userSignedTo;
    private String userCreated;
    private String userUpdated;

    private CustomerTypeBean typeBean;
    private CustomerLevelBean levelBean;
    private List <PaymentBean> paymentBeans;
    private List <ContactBean> contactBeans;
    private List <OrderBean> orderBeans;

    private String contactList;
    private long lastPaidDate;
    private long lastSaleDate;
    
    private String group;

    public CustomerBean ()
    {
    }

    public CustomerBean (String shortName)
    {
        this.shortName = shortName;
    }

    public String getShortName ()
    {
        return shortName;
    }

    public void setShortName (String shortName)
    {
        this.shortName = shortName;
    }

    public String getComment ()
    {
        return comment;
    }

    public void setComment (String comment)
    {
        this.comment = comment;
    }

    public String getUserSignedTo ()
    {
        return userSignedTo;
    }

    public void setUserSignedTo (String userSignedTo)
    {
        this.userSignedTo = userSignedTo;
    }

    public String getUserCreated ()
    {
        return userCreated;
    }

    public void setUserCreated (String userCreated)
    {
        this.userCreated = userCreated;
    }

    public String getUserUpdated ()
    {
        return userUpdated;
    }

    public void setUserUpdated (String userUpdated)
    {
        this.userUpdated = userUpdated;
    }

    public boolean isSharable ()
    {
        return sharable;
    }

    public void setSharable (boolean sharable)
    {
        this.sharable = sharable;
    }

    public List <ContactBean> getContactBeans ()
    {
        return contactBeans;
    }

    public void setContactBeans (List <ContactBean> contactBeans)
    {
        this.contactBeans = contactBeans;
    }

    public CustomerTypeBean getTypeBean ()
    {
        return typeBean;
    }

    public void setTypeBean (CustomerTypeBean typeBean)
    {
        this.typeBean = typeBean;
    }

    public CustomerLevelBean getLevelBean ()
    {
        return levelBean;
    }

    public void setLevelBean (CustomerLevelBean levelBean)
    {
        this.levelBean = levelBean;
    }

    public List <PaymentBean> getPaymentBeans ()
    {
        return paymentBeans;
    }

    public void setPaymentBeans (List <PaymentBean> paymentBeans)
    {
        this.paymentBeans = paymentBeans;
        if (null != paymentBeans)
        {
            for (PaymentBean paymentBean : paymentBeans)
            {
                paymentBean.setCustomerBean (this);
            }
        }
    }

    public String getShortCode ()
    {
        return shortCode;
    }

    public void setShortCode (String shortCode)
    {
        this.shortCode = shortCode;
    }

    public float getUnpayMoney ()
    {
        return Arith.round (unpayMoney, -1);
    }

    public void setUnpayMoney (float unpayMoney)
    {
        this.unpayMoney = unpayMoney;
    }

    public boolean isDeleted ()
    {
        return deleted;
    }

    public void setDeleted (boolean deleted)
    {
        this.deleted = deleted;
    }

    public List <OrderBean> getOrderBeans ()
    {
        return orderBeans;
    }

    public void setOrderBeans (List <OrderBean> orderBeans)
    {
        this.orderBeans = orderBeans;
    }

    public String getContactList ()
    {
        return contactList;
    }

    public void setContactList (String contactList)
    {
        this.contactList = contactList;
    }

    public void setLastPaidDate (long lastPaidDate)
    {
        this.lastPaidDate = lastPaidDate;
    }

    public void setLastSaleDate (long lastSaleDate)
    {
        this.lastSaleDate = lastSaleDate;
    }

    public long getLastPaidDate ()
    {
        return lastPaidDate;
    }

    public long getLastSaleDate ()
    {
        return lastSaleDate;
    }

    public String getGroup ()
    {
        return group;
    }

    public void setGroup (String group)
    {
        this.group = group;
    }

    public static String getSpan (long interval)
    {
        if (0 < interval && 60 > interval)
        {
            return interval + "秒钟前";
        }
        else if (60 <= interval && 3600 > interval)
        {
            return interval / 60 + "分钟前";
        }
        else if (3600 <= interval && 24 * 3600 > interval)
        {
            return interval / 3600 + "小时前";
        }
        else if (24 * 3600 <= interval && 30 * 24 * 3600 > interval)
        {
            return interval / (24 * 3600) + "天前";
        }
        else if (30 * 24 * 3600 <= interval && 12 * 30 * 24 * 3600 > interval)
        {
            return interval / (30 * 24 * 3600) + "月前";
        }
        else if (12 * 30 * 24 * 3600 <= interval)
        {
            return interval / (12 * 30 * 24 * 3600) + "年前";
        }
        return "";
    }

}
