package com.pinfly.purchasecharge.core.model.persistence.goods;

import java.util.Date;

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
 * 货物库存盘点
 */
@Entity
@Table (name = "pc_goods_storage_check")
public class GoodsStorageCheck extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String bid;
    private Goods goods;
    private GoodsDepository depository;
    private long amountBeforeCheck;
    private long amountAfterCheck;
    private Date dateCreated;
    private long userCreatedBy;

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
    public String getBid ()
    {
        return bid;
    }

    public void setBid (String bid)
    {
        this.bid = bid;
    }

    @ManyToOne (cascade =
    { CascadeType.REFRESH }, optional = false)
    @JoinColumn (name = "depository_id")
    public GoodsDepository getDepository ()
    {
        return depository;
    }

    public void setDepository (GoodsDepository depository)
    {
        this.depository = depository;
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

    public long getAmountBeforeCheck ()
    {
        return amountBeforeCheck;
    }

    public void setAmountBeforeCheck (long amountBeforeCheck)
    {
        this.amountBeforeCheck = amountBeforeCheck;
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

    public long getUserCreatedBy ()
    {
        return userCreatedBy;
    }

    public void setUserCreatedBy (long userCreatedBy)
    {
        this.userCreatedBy = userCreatedBy;
    }

    public long getAmountAfterCheck ()
    {
        return amountAfterCheck;
    }

    public void setAmountAfterCheck (long amountAfterCheck)
    {
        this.amountAfterCheck = amountAfterCheck;
    }

}
