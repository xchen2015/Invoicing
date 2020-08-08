package com.pinfly.purchasecharge.core.model.persistence.goods;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.pinfly.purchasecharge.core.model.BaseModel;
import com.pinfly.purchasecharge.core.model.GoodsIssueStatusCode;
import com.pinfly.purchasecharge.core.model.persistence.in.Provider;
import com.pinfly.purchasecharge.core.model.persistence.out.Customer;

/**
 * 问题货物
 * 
 * @author xiang
 */
@Entity
@Table (name = "pc_goods_issue")
public class GoodsIssue extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;

    private String description;
    private String comment;
    private GoodsIssueStatusCode statusCode = GoodsIssueStatusCode.NEW;

    /**
     * 谁负责处理
     */
    private long signUser;
    private long userUpdatedBy;

    private Date dateCreated;
    /**
     * 最后一次更新的时间
     */
    private Date lastUpdated;

    private Goods goods;
    private String goodsSerial;
    private Customer customer;
    private Provider provider;

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

    @Column (length = 2048)
    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
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
    public GoodsIssueStatusCode getStatusCode ()
    {
        return statusCode;
    }

    public void setStatusCode (GoodsIssueStatusCode statusCode)
    {
        this.statusCode = statusCode;
    }

    public long getSignUser ()
    {
        return signUser;
    }

    public void setSignUser (long signUser)
    {
        this.signUser = signUser;
    }

    public long getUserUpdatedBy ()
    {
        return userUpdatedBy;
    }

    public void setUserUpdatedBy (long userUpdatedBy)
    {
        this.userUpdatedBy = userUpdatedBy;
    }

    @Temporal (value = TemporalType.TIMESTAMP)
    public Date getLastUpdated ()
    {
        return lastUpdated;
    }

    public void setLastUpdated (Date lastUpdated)
    {
        this.lastUpdated = lastUpdated;
    }

    @ManyToOne (cascade =
    { CascadeType.REFRESH }, optional = false)
    @JoinColumn (name = "goods_id")
    public Goods getGoods ()
    {
        return goods;
    }

    public void setGoods (Goods goods)
    {
        this.goods = goods;
    }

    public String getGoodsSerial ()
    {
        return goodsSerial;
    }

    public void setGoodsSerial (String goodsSerial)
    {
        this.goodsSerial = goodsSerial;
    }

    @Column (nullable = false)
    @Temporal (value = TemporalType.TIMESTAMP)
    public Date getDateCreated ()
    {
        return dateCreated;
    }

    public void setDateCreated (Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    @ManyToOne (cascade =
    { CascadeType.REFRESH })
    @JoinColumn (name = "customer_id")
    public Customer getCustomer ()
    {
        return customer;
    }

    public void setCustomer (Customer customer)
    {
        this.customer = customer;
    }

    @ManyToOne (cascade =
    { CascadeType.REFRESH })
    @JoinColumn (name = "provider_id")
    public Provider getProvider ()
    {
        return provider;
    }

    public void setProvider (Provider provider)
    {
        this.provider = provider;
    }

}
