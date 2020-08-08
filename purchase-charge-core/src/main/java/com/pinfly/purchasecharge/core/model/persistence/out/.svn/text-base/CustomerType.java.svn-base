package com.pinfly.purchasecharge.core.model.persistence.out;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.pinfly.purchasecharge.core.model.BaseModel;

/**
 * 客户类型
 */
@Entity
@Table (name = "pc_customer_type")
public class CustomerType extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;

    private List <Customer> customers;

    public CustomerType ()
    {
    }

    public CustomerType (String name)
    {
        this.name = name;
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
    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    @OneToMany (mappedBy = "customerType")
    public List <Customer> getCustomers ()
    {
        return customers;
    }

    public void setCustomers (List <Customer> customers)
    {
        this.customers = customers;
    }

}
