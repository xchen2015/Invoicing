package com.pinfly.purchasecharge.core.model.persistence.in;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.collections.CollectionUtils;

import com.pinfly.purchasecharge.core.model.BaseModel;

/**
 * 供应商
 */
@Entity
@Table (name = "pc_provider")
public class Provider extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String shortName;
    private String shortCode;
    private float unpayMoney;

    private boolean sharable = false;
    private boolean deleted = false;

    private String comment;
    private long userSigned;

    private List <ProviderContact> contacts;
    private List <ProviderPayment> payments;
    private ProviderType providerType;
    private List <OrderIn> orders;

    public Provider ()
    {
    }

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    public long getId ()
    {
        return id;
    }

    public void setId (long id)
    {
        this.id = id;
    }

    @Column (nullable = false, unique = true)
    public String getShortName ()
    {
        return shortName;
    }

    public void setShortName (String shortName)
    {
        this.shortName = shortName;
    }

    @Column (unique = true)
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
        return unpayMoney;
    }

    public void setUnpayMoney (float unpayMoney)
    {
        this.unpayMoney = unpayMoney;
    }

    @Column (length = 1024)
    public String getComment ()
    {
        return comment;
    }

    public void setComment (String comment)
    {
        this.comment = comment;
    }

    public long getUserSigned ()
    {
        return userSigned;
    }

    public void setUserSigned (long userSigned)
    {
        this.userSigned = userSigned;
    }

    public boolean isDeleted ()
    {
        return deleted;
    }

    public void setDeleted (boolean deleted)
    {
        this.deleted = deleted;
    }

    public boolean isSharable ()
    {
        return sharable;
    }

    public void setSharable (boolean sharable)
    {
        this.sharable = sharable;
    }

    @OneToMany (mappedBy = "provider", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    public List <ProviderContact> getContacts ()
    {
        return contacts;
    }

    public void setContacts (List <ProviderContact> contacts)
    {
        this.contacts = contacts;
        if (CollectionUtils.isNotEmpty (contacts))
        {
            for (ProviderContact pc : contacts)
            {
                pc.setProvider (this);
            }
        }
    }

    @OneToMany (mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
    public List <ProviderPayment> getPayments ()
    {
        return payments;
    }

    public void setPayments (List <ProviderPayment> payments)
    {
        this.payments = payments;
    }

    @ManyToOne
    @JoinColumn (name = "provider_type_id")
    public ProviderType getProviderType ()
    {
        return providerType;
    }

    public void setProviderType (ProviderType providerType)
    {
        this.providerType = providerType;
    }

    @OneToMany (mappedBy = "provider")
    public List <OrderIn> getOrders ()
    {
        return orders;
    }

    public void setOrders (List <OrderIn> orders)
    {
        this.orders = orders;
    }

}
