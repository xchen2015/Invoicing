package com.pinfly.purchasecharge.core.model.persistence.auditlog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pinfly.purchasecharge.core.model.BaseModel;

/**
 * 操作日志事件:新增、更新、删除等
 */
@Entity
@Table (name = "pc_log_event")
public class LogEvent extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private boolean enabled = true;

    public LogEvent ()
    {
    }

    public LogEvent (String name)
    {
        this.name = name;
    }

    public LogEvent (String name, boolean enabled)
    {
        this.name = name;
        this.enabled = enabled;
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

    public boolean isEnabled ()
    {
        return enabled;
    }

    public void setEnabled (boolean enabled)
    {
        this.enabled = enabled;
    }

}
