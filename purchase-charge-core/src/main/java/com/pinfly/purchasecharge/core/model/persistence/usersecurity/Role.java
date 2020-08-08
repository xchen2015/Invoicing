package com.pinfly.purchasecharge.core.model.persistence.usersecurity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.pinfly.purchasecharge.core.model.BaseModel;

/**
 * 系统角色
 */
@Entity
@Table (name = "pc_role")
public class Role extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private boolean enabled = true;

    private List <User> users;
    private List <Authority> authorities;

    public Role ()
    {
    }

    public Role (String name)
    {
        this.name = name;
    }

    public Role (String name, List <Authority> authorities)
    {
        this (name);
        this.authorities = authorities;
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

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable (name = "pc_role_authority", joinColumns =
    { @JoinColumn (name = "role_id", referencedColumnName = "id") }, inverseJoinColumns =
    { @JoinColumn (name = "authority_id", referencedColumnName = "id") })
    public List <Authority> getAuthorities ()
    {
        return authorities;
    }

    public void setAuthorities (List <Authority> authorities)
    {
        this.authorities = authorities;
    }

    @ManyToMany (mappedBy = "roles")
    public List <User> getUsers ()
    {
        return users;
    }

    public void setUsers (List <User> users)
    {
        this.users = users;
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
