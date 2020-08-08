package com.pinfly.purchasecharge.component.bean;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

public class GoodsBean extends BaseBean
{
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String name;
    @NotBlank
    private String shortCode;

    private String specificationModel;
    private String barCode;
    private String comment;

    private float importPrice;
    private float retailPrice;
    private float tradePrice;
    private float averagePrice;
    private long totalStock;
    private float totalValue;
    private long minStock;
    private long maxStock;

    private GoodsUnitBean unitBean;
    private GoodsTypeBean typeBean;
    private GoodsDepositoryBean preferedDepositoryBean;
    private List <GoodsPhotoBean> photoBeans;
    private List <GoodsStorageBean> storageBeans;

    private String storageList;
    private String userCreated;
    private String userUpdated;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getSpecificationModel ()
    {
        return specificationModel;
    }

    public void setSpecificationModel (String specificationModel)
    {
        this.specificationModel = specificationModel;
    }

    public String getBarCode ()
    {
        return barCode;
    }

    public void setBarCode (String barCode)
    {
        this.barCode = barCode;
    }

    public String getComment ()
    {
        return comment;
    }

    public void setComment (String comment)
    {
        this.comment = comment;
    }

    public float getImportPrice ()
    {
        return importPrice;
    }

    public void setImportPrice (float importPrice)
    {
        this.importPrice = importPrice;
    }

    public GoodsUnitBean getUnitBean ()
    {
        return unitBean;
    }

    public void setUnitBean (GoodsUnitBean unitBean)
    {
        this.unitBean = unitBean;
    }

    public GoodsTypeBean getTypeBean ()
    {
        return typeBean;
    }

    public void setTypeBean (GoodsTypeBean typeBean)
    {
        this.typeBean = typeBean;
    }

    public String getUserCreated ()
    {
        return userCreated;
    }

    public void setUserCreated (String userCreated)
    {
        this.userCreated = userCreated;
    }

    public String getUserUpdated ()
    {
        return userUpdated;
    }

    public void setUserUpdated (String userUpdated)
    {
        this.userUpdated = userUpdated;
    }

    public String getShortCode ()
    {
        return shortCode;
    }

    public void setShortCode (String shortCode)
    {
        this.shortCode = shortCode;
    }

    public float getRetailPrice ()
    {
        return retailPrice;
    }

    public void setRetailPrice (float retailPrice)
    {
        this.retailPrice = retailPrice;
    }

    public float getTradePrice ()
    {
        return tradePrice;
    }

    public void setTradePrice (float tradePrice)
    {
        this.tradePrice = tradePrice;
    }

    public float getAveragePrice ()
    {
        return averagePrice;
    }

    public void setAveragePrice (float averagePrice)
    {
        this.averagePrice = averagePrice;
    }

    public long getTotalStock ()
    {
        return totalStock;
    }

    public void setTotalStock (long totalStock)
    {
        this.totalStock = totalStock;
    }

    public long getMinStock ()
    {
        return minStock;
    }

    public void setMinStock (long minStock)
    {
        this.minStock = minStock;
    }

    public long getMaxStock ()
    {
        return maxStock;
    }

    public void setMaxStock (long maxStock)
    {
        this.maxStock = maxStock;
    }

    public float getTotalValue ()
    {
        return totalValue;
    }

    public void setTotalValue (float totalValue)
    {
        this.totalValue = totalValue;
    }

    public List <GoodsPhotoBean> getPhotoBeans ()
    {
        return photoBeans;
    }

    public void setPhotoBeans (List <GoodsPhotoBean> photoBeans)
    {
        this.photoBeans = photoBeans;
    }

    public GoodsDepositoryBean getPreferedDepositoryBean ()
    {
        return preferedDepositoryBean;
    }

    public void setPreferedDepositoryBean (GoodsDepositoryBean preferedDepositoryBean)
    {
        this.preferedDepositoryBean = preferedDepositoryBean;
    }

    public List <GoodsStorageBean> getStorageBeans ()
    {
        return storageBeans;
    }

    public void setStorageBeans (List <GoodsStorageBean> storageBeans)
    {
        this.storageBeans = storageBeans;
    }

    public String getStorageList ()
    {
        return storageList;
    }

    public void setStorageList (String storageList)
    {
        this.storageList = storageList;
    }

}
