package com.pinfly.purchasecharge.component.bean;

import javax.validation.constraints.NotNull;

public class GoodsStorageTransferBean extends BaseBean
{
    private static final long serialVersionUID = 1L;

    @NotNull
    private GoodsBean goodsBean;
    @NotNull
    private GoodsDepositoryBean fromDepositoryBean;
    @NotNull
    private GoodsDepositoryBean toDepositoryBean;
    private String bid;
    private long fromDepositoryAmount;
    private long toDepositoryAmount;
    private long transferAmount;
    
    private String dateCreated;
    private String userCreated;
    private String comment;

    public GoodsDepositoryBean getFromDepositoryBean ()
    {
        return fromDepositoryBean;
    }

    public void setFromDepositoryBean (GoodsDepositoryBean fromDepositoryBean)
    {
        this.fromDepositoryBean = fromDepositoryBean;
    }

    public GoodsDepositoryBean getToDepositoryBean ()
    {
        return toDepositoryBean;
    }

    public void setToDepositoryBean (GoodsDepositoryBean toDepositoryBean)
    {
        this.toDepositoryBean = toDepositoryBean;
    }

    public String getBid ()
    {
        return bid;
    }

    public void setBid (String bid)
    {
        this.bid = bid;
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

    public long getTransferAmount ()
    {
        return transferAmount;
    }

    public void setTransferAmount (long transferAmount)
    {
        this.transferAmount = transferAmount;
    }

    public String getDateCreated ()
    {
        return dateCreated;
    }

    public void setDateCreated (String dateCreated)
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

    public String getUserCreated ()
    {
        return userCreated;
    }

    public void setUserCreated (String userCreated)
    {
        this.userCreated = userCreated;
    }

    public GoodsBean getGoodsBean ()
    {
        return goodsBean;
    }

    public void setGoodsBean (GoodsBean goodsBean)
    {
        this.goodsBean = goodsBean;
    }

}
