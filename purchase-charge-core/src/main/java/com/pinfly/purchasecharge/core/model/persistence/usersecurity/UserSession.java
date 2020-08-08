package com.pinfly.purchasecharge.core.model.persistence.usersecurity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.pinfly.purchasecharge.core.model.BaseModel;

/**
 * 用户安全会话
 * 
 */
@Entity
@Table (name = "pc_user_session")
public class UserSession extends BaseModel
{
    private static final long serialVersionUID = 2611151383140968220L;

    private long sessionId;
    private String sessionKey;
    private User user;
    private Date dateCreated;

    /** default constructor */
    public UserSession ()
    {
    }

    /** Common constructor */
    /** full constructor */
    public UserSession (String sessionKey, User user, Date dateCreated)
    {
        this.sessionKey = sessionKey;
        this.user = user;
        this.dateCreated = dateCreated;
    }

    /** full constructor */
    public UserSession (long sessionId, String sessionKey, User user, Date dateCreated)
    {
        this.sessionId = sessionId;
        this.sessionKey = sessionKey;
        this.user = user;
        this.dateCreated = dateCreated;
    }

    // Property accessors
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "session_id", unique = true, nullable = false)
    public long getSessionId ()
    {
        return sessionId;
    }

    public void setSessionId (long sessionId)
    {
        this.sessionId = sessionId;
    }

    @Column (name = "session_key")
    public String getSessionKey ()
    {
        return sessionKey;
    }

    public void setSessionKey (String sessionKey)
    {
        this.sessionKey = sessionKey;
    }

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "user_id", nullable = false)
    public User getUser ()
    {
        return user;
    }

    public void setUser (User user)
    {
        this.user = user;
    }

    @Temporal (TemporalType.TIMESTAMP)
    @Column (name = "date_created", nullable = false, length = 8)
    public Date getDateCreated ()
    {
        return dateCreated;
    }

    public void setDateCreated (Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

}
