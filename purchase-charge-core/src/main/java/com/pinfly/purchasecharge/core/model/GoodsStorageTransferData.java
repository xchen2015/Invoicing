package com.pinfly.purchasecharge.core.model;

import java.util.Date;

import com.pinfly.purchasecharge.core.model.persistence.goods.Goods;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsDepository;

/**
 * 货物库存调拨
 */
public class GoodsStorageTransferData extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private String bid;
    private Goods goods;
    private GoodsDepository fromDepository;
    private long fromDepositoryAmount;
    private GoodsDepository toDepository;
    private long toDepositoryAmount;
    
    private long transferAmount;
    private Date dateCreated;
    private long userCreatedBy;
    private String comment;

    public String getBid ()
    {
        return bid;
    }

    public void setBid (String bid)
    {
        this.bid = bid;
    }

    public GoodsDepository getFromDepository ()
    {
        return fromDepository;
    }

    public void setFromDepository (GoodsDepository fromDepository)
    {
        this.fromDepository = fromDepository;
    }

    public GoodsDepository getToDepository ()
    {
        return toDepository;
    }

    public void setToDepository (GoodsDepository toDepository)
    {
        this.toDepository = toDepository;
    }

    public Goods getGoods ()
    {
        return goods;
    }

    public void setGoods (Goods goods)
    {
        this.goods = goods;
    }

    public long getTransferAmount ()
    {
        return transferAmount;
    }

    public void setTransferAmount (long transferAmount)
    {
        this.transferAmount = transferAmount;
    }

    public Date getDateCreated ()
    {
        return dateCreated;
    }

    public void setDateCreated (Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public String getComment ()
    {
        return comment;
    }

    public void setComment (String comment)
    {
        this.comment = comment;
    }

    public long getUserCreatedBy ()
    {
        return userCreatedBy;
    }

    public void setUserCreatedBy (long userCreatedBy)
    {
        this.userCreatedBy = userCreatedBy;
    }

    public long getFromDepositoryAmount ()
    {
        return fromDepositoryAmount;
    }

    public void setFromDepositoryAmount (long fromDepositoryAmount)
    {
        this.fromDepositoryAmount = fromDepositoryAmount;
    }

    public long getToDepositoryAmount ()
    {
        return toDepositoryAmount;
    }

    public void setToDepositoryAmount (long toDepositoryAmount)
    {
        this.toDepositoryAmount = toDepositoryAmount;
    }

}
