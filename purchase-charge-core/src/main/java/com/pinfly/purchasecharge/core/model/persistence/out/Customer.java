package com.pinfly.purchasecharge.core.model.persistence.out;

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
 * 客户
 */
@Entity
@Table (name = "pc_customer")
public class Customer extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String shortName;
    private String shortCode;
    private float unpayMoney;

    private boolean sharable = false;
    private boolean deleted = false;

    private long userSigned;
    private String comment;

    private List <CustomerContact> contacts;
    private List <CustomerPayment> payments;
    private CustomerType customerType;
    private CustomerLevel customerLevel;
    private List <OrderOut> orders;

    public Customer ()
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

    @OneToMany (mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    public List <CustomerContact> getContacts ()
    {
        return contacts;
    }

    public void setContacts (List <CustomerContact> contacts)
    {
        this.contacts = contacts;
        if (CollectionUtils.isNotEmpty (contacts))
        {
            for (CustomerContact contact : contacts)
            {
                contact.setCustomer (this);
            }
        }
    }

    @OneToMany (mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    public List <CustomerPayment> getPayments ()
    {
        return payments;
    }

    public void setPayments (List <CustomerPayment> payments)
    {
        this.payments = payments;
    }

    @ManyToOne
    @JoinColumn (name = "customer_type_id")
    public CustomerType getCustomerType ()
    {
        return customerType;
    }

    public void setCustomerType (CustomerType customerType)
    {
        this.customerType = customerType;
    }

    @ManyToOne
    @JoinColumn (name = "customer_level_id")
    public CustomerLevel getCustomerLevel ()
    {
        return customerLevel;
    }

    public void setCustomerLevel (CustomerLevel customerLevel)
    {
        this.customerLevel = customerLevel;
    }

    @OneToMany (mappedBy = "customer")
    public List <OrderOut> getOrders ()
    {
        return orders;
    }

    public void setOrders (List <OrderOut> orders)
    {
        this.orders = orders;
    }

}
