package com.pinfly.purchasecharge.core.model.persistence.out;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pinfly.purchasecharge.core.model.BaseModel;

/**
 * 客户联系人
 */
@Entity
@Table (name = "pc_customer_contact")
public class CustomerContact extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private String mobilePhone;
    private String fixedPhone;
    private String netCommunityId;
    private String email;
    private String address;

    private boolean isPrefered;

    private Customer customer;

    public CustomerContact ()
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

    @Column (nullable = false)
    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    @Column (length = 50)
    public String getMobilePhone ()
    {
        return mobilePhone;
    }

    public void setMobilePhone (String mobilePhone)
    {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public boolean isPrefered ()
    {
        return isPrefered;
    }

    public void setPrefered (boolean isPrefered)
    {
        this.isPrefered = isPrefered;
    }

    public String getFixedPhone ()
    {
        return fixedPhone;
    }

    public void setFixedPhone (String fixedPhone)
    {
        this.fixedPhone = fixedPhone;
    }

    public String getNetCommunityId ()
    {
        return netCommunityId;
    }

    public void setNetCommunityId (String netCommunityId)
    {
        this.netCommunityId = netCommunityId;
    }

    @ManyToOne (optional = false)
    @JoinColumn (name = "customer_id")
    public Customer getCustomer ()
    {
        return customer;
    }

    public void setCustomer (Customer customer)
    {
        this.customer = customer;
    }

}
