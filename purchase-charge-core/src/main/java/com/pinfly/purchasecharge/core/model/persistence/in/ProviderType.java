package com.pinfly.purchasecharge.core.model.persistence.in;

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
 * 供应商类型
 */
@Entity
@Table (name = "pc_provider_type")
public class ProviderType extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;

    private List <Provider> providers;

    public ProviderType ()
    {
    }

    public ProviderType (String name)
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

    @OneToMany (mappedBy = "providerType")
    public List <Provider> getProviders ()
    {
        return providers;
    }

    public void setProviders (List <Provider> providers)
    {
        this.providers = providers;
    }

}
