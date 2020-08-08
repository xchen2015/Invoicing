package com.pinfly.purchasecharge.core.model.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.collections.CollectionUtils;

import com.pinfly.purchasecharge.core.model.BaseModel;
import com.pinfly.purchasecharge.core.model.ProjectStatusCode;
import com.pinfly.purchasecharge.core.model.persistence.out.Customer;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOut;

/**
 * 工程
 */
@Entity
@Table (name = "pc_project")
public class Project extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private Customer customer;
    private Date start;
    private Date end;
    private String comment;
    private ProjectStatusCode statusCode = ProjectStatusCode.NEW;

    /**
     * 施工费
     */
    private float constructionFee;

    /**
     * 辅材及其他费
     */
    private float otherFee;

    private long userCreatedBy;
    private long userUpdatedBy;

    /**
     * 设备费
     */
    private List <OrderOut> orders;

    public Project ()
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

    @Column (nullable = false, unique = true)
    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    @ManyToOne (optional = false)
    @JoinColumn (name = "customer_id", nullable = false)
    public Customer getCustomer ()
    {
        return customer;
    }

    public void setCustomer (Customer customer)
    {
        this.customer = customer;
    }

    @Column (nullable = false)
    @Temporal (value = TemporalType.TIMESTAMP)
    public Date getStart ()
    {
        return start;
    }

    public void setStart (Date start)
    {
        this.start = start;
    }

    @Temporal (value = TemporalType.TIMESTAMP)
    public Date getEnd ()
    {
        return end;
    }

    public void setEnd (Date end)
    {
        this.end = end;
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

    @Column (nullable = false)
    @Enumerated (EnumType.STRING)
    public ProjectStatusCode getStatusCode ()
    {
        return statusCode;
    }

    public void setStatusCode (ProjectStatusCode statusCode)
    {
        this.statusCode = statusCode;
    }

    public float getConstructionFee ()
    {
        return constructionFee;
    }

    public void setConstructionFee (float constructionFee)
    {
        this.constructionFee = constructionFee;
    }

    public float getOtherFee ()
    {
        return otherFee;
    }

    public void setOtherFee (float otherFee)
    {
        this.otherFee = otherFee;
    }

    @Column (nullable = false)
    public long getUserCreatedBy ()
    {
        return userCreatedBy;
    }

    public void setUserCreatedBy (long userCreatedBy)
    {
        this.userCreatedBy = userCreatedBy;
    }

    @Column (nullable = false)
    public long getUserUpdatedBy ()
    {
        return userUpdatedBy;
    }

    public void setUserUpdatedBy (long userUpdatedBy)
    {
        this.userUpdatedBy = userUpdatedBy;
    }

    @OneToMany (mappedBy = "project", fetch = FetchType.EAGER)
    public List <OrderOut> getOrders ()
    {
        return orders;
    }

    public void setOrders (List <OrderOut> orders)
    {
        this.orders = orders;
        if (CollectionUtils.isNotEmpty (orders))
        {
            for (OrderOut order : orders)
            {
                order.setProject (this);
            }
        }
    }

}
