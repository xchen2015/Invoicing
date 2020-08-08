package com.pinfly.purchasecharge.core.model.persistence;

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
 * 区域字典表
 */
@Entity
@Table (name = "pc_region")
public class Region extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;

    private Region parent;

    public Region ()
    {
    }

    public Region (long id)
    {
        this.id = id;
    }

    public Region (String name)
    {
        this.name = name;
    }

    public Region (long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Region (long id, String name, Region parent)
    {
        this.name = name;
        this.parent = parent;
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

    @ManyToOne
    @JoinColumn (name = "parent_id")
    public Region getParent ()
    {
        return parent;
    }

    public void setParent (Region parent)
    {
        this.parent = parent;
    }
}
