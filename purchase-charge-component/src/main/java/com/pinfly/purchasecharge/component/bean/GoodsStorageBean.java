package com.pinfly.purchasecharge.component.bean;

import javax.validation.constraints.NotNull;

public class GoodsStorageBean extends BaseBean
{
    private static final long serialVersionUID = 1L;

    @NotNull
    private GoodsBean goodsBean;
    @NotNull
    private GoodsDepositoryBean depositoryBean;
    private String bid;
    private long currentAmount;
    private float currentPrice;
    private float worth;

    private long actualAmount;
    private float revisePrice;
    
    private String dateCreated;
    private String userCreated;

    public GoodsDepositoryBean getDepositoryBean ()
    {
        return depositoryBean;
    }

    public void setDepositoryBean (GoodsDepositoryBean depositoryBean)
    {
        this.depositoryBean = depositoryBean;
    }

    public long getCurrentAmount ()
    {
        return currentAmount;
    }

    public void setCurrentAmount (long currentAmount)
    {
        this.currentAmount = currentAmount;
    }

    public float getCurrentPrice ()
    {
        return currentPrice;
    }

    public void setCurrentPrice (float currentPrice)
    {
        this.currentPrice = currentPrice;
    }

    public float getWorth ()
    {
        return worth;
    }

    public void setWorth (float worth)
    {
        this.worth = worth;
    }

    public GoodsBean getGoodsBean ()
    {
        return goodsBean;
    }

    public void setGoodsBean (GoodsBean goodsBean)
    {
        this.goodsBean = goodsBean;
    }

    public long getActualAmount ()
    {
        return actualAmount;
    }

    public void setActualAmount (long actualAmount)
    {
        this.actualAmount = actualAmount;
    }

    public float getRevisePrice ()
    {
        return revisePrice;
    }

    public void setRevisePrice (float revisePrice)
    {
        this.revisePrice = revisePrice;
    }

    public String getDateCreated ()
    {
        return dateCreated;
    }

    public void setDateCreated (String dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public String getUserCreated ()
    {
        return userCreated;
    }

    public void setUserCreated (String userCreated)
    {
        this.userCreated = userCreated;
    }

    public String getBid ()
    {
        return bid;
    }

    public void setBid (String bid)
    {
        this.bid = bid;
    }

}
