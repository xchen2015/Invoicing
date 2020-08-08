package com.pinfly.purchasecharge.core.model.persistence.auditlog;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * 操作日志主表
 * 
 * @author xiang
 * 
 */
@Entity
@Table (name = "pc_log")
public class Log extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private LogEvent event;
    private long userCreate;
    private Timestamp dateCreate;
    private String comment;

    public Log ()
    {
    }

    public Log (LogEvent event, long userCreate, String comment)
    {
        this.event = event;
        this.userCreate = userCreate;
        this.comment = comment;
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
    public long getUserCreate ()
    {
        return userCreate;
    }

    public void setUserCreate (long userCreate)
    {
        this.userCreate = userCreate;
    }

    @ManyToOne (optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn (name = "event_id")
    public LogEvent getEvent ()
    {
        return event;
    }

    public void setEvent (LogEvent event)
    {
        this.event = event;
    }

    @Column (nullable = false)
    @Temporal (value = TemporalType.TIMESTAMP)
    public Timestamp getDateCreate ()
    {
        return dateCreate;
    }

    public void setDateCreate (Timestamp dateCreate)
    {
        this.dateCreate = dateCreate;
    }

    @Column (length = 2048)
    public String getComment ()
    {
        return comment;
    }

    public void setComment (String comment)
    {
        this.comment = comment;
    }

}
