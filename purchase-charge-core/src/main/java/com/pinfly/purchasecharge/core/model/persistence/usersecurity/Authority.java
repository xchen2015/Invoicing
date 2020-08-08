package com.pinfly.purchasecharge.core.model.persistence.usersecurity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pinfly.purchasecharge.core.model.BaseModel;

/**
 * 系统权限
 */
@Entity
@Table (name = "pc_authority")
public class Authority extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private String url;
    private Authority parent;
    private boolean enabled = true;

    private List <Role> roles;

    public Authority ()
    {
    }

    public Authority (String name)
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

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    @ManyToMany (mappedBy = "authorities")
    public List <Role> getRoles ()
    {
        return roles;
    }

    public void setRoles (List <Role> roles)
    {
        this.roles = roles;
    }

    @ManyToOne
    @JoinColumn (name = "parent_id")
    public Authority getParent ()
    {
        return parent;
    }

    public void setParent (Authority parent)
    {
        this.parent = parent;
    }

    public boolean isEnabled ()
    {
        return enabled;
    }

    public void setEnabled (boolean enabled)
    {
        this.enabled = enabled;
    }

}
