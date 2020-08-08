package com.pinfly.purchasecharge.core.model.persistence.usersecurity;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.pinfly.purchasecharge.core.model.BaseModel;

/**
 * 系统用户
 */
@Entity
@Table (name = "pc_user")
public class User extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String userId;
    private String pwd;
    private String passwordHint;
    private boolean admin = false;
    private boolean enabled = true;
    private boolean accountLocked = false;
    private boolean credentialsExpired = false;

    private String firstName;
    private String lastName;
    private String mobilePhone;
    private String email;
    private String netCommunityId;
    private Date birthday;
    private String birthAddress;
    private String comments;

    private List <Role> roles;

    public User ()
    {
    }

    public User (String userId)
    {
        this.userId = userId;
    }

    public User (String userId, String password)
    {
        this.userId = userId;
        this.pwd = password;
    }

    public User (String userId, String password, List <Role> roles)
    {
        this (userId, password);
        this.roles = roles;
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
    public String getUserId ()
    {
        return userId;
    }

    public void setUserId (String userId)
    {
        this.userId = userId;
    }

    @Column (nullable = false)
    public String getPwd ()
    {
        return pwd;
    }

    public void setPwd (String pwd)
    {
        this.pwd = pwd;
    }

    public String getPasswordHint ()
    {
        return passwordHint;
    }

    public void setPasswordHint (String passwordHint)
    {
        this.passwordHint = passwordHint;
    }

    public boolean isEnabled ()
    {
        return enabled;
    }

    public void setEnabled (boolean enabled)
    {
        this.enabled = enabled;
    }

    public boolean isAccountLocked ()
    {
        return accountLocked;
    }

    public void setAccountLocked (boolean accountLocked)
    {
        this.accountLocked = accountLocked;
    }

    public boolean isCredentialsExpired ()
    {
        return credentialsExpired;
    }

    public void setCredentialsExpired (boolean credentialsExpired)
    {
        this.credentialsExpired = credentialsExpired;
    }

    public boolean isAdmin ()
    {
        return admin;
    }

    public void setAdmin (boolean admin)
    {
        this.admin = admin;
    }

    // @OneToMany (mappedBy = "users", fetch = FetchType.LAZY, cascade =
    // { CascadeType.ALL })
    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable (name = "pc_user_role", joinColumns =
    { @JoinColumn (name = "USER_ID", referencedColumnName = "id") }, inverseJoinColumns =
    { @JoinColumn (name = "ROLE_ID", referencedColumnName = "id") })
    public List <Role> getRoles ()
    {
        return roles;
    }

    public void setRoles (List <Role> roles)
    {
        this.roles = roles;
    }

    public String getFirstName ()
    {
        return firstName;
    }

    public void setFirstName (String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName ()
    {
        return lastName;
    }

    public void setLastName (String lastName)
    {
        this.lastName = lastName;
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

    public String getNetCommunityId ()
    {
        return netCommunityId;
    }

    public void setNetCommunityId (String netCommunityId)
    {
        this.netCommunityId = netCommunityId;
    }

    @Temporal (value = TemporalType.DATE)
    public Date getBirthday ()
    {
        return birthday;
    }

    public void setBirthday (Date birthday)
    {
        this.birthday = birthday;
    }

    public String getBirthAddress ()
    {
        return birthAddress;
    }

    public void setBirthAddress (String birthAddress)
    {
        this.birthAddress = birthAddress;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    @Column (length = 2048)
    public String getComments ()
    {
        return comments;
    }

    public void setComments (String comments)
    {
        this.comments = comments;
    }
}
