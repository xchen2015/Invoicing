package com.pinfly.purchasecharge.component.bean;

import javax.validation.constraints.NotNull;

import com.pinfly.purchasecharge.component.bean.usersecurity.UserBean;
import com.pinfly.purchasecharge.core.model.GoodsIssueStatusCode;

/**
 * 问题货物
 * 
 * @author xiang
 */
public class GoodsIssueBean extends BaseBean
{
    private static final long serialVersionUID = 1L;

    /**
     * 描述
     */
    private String description;
    private String comment;
    private GoodsIssueStatusCode statusCode = GoodsIssueStatusCode.NEW;
    /**
     * 谁负责处理
     */
    private UserBean signUser;
    private UserBean updateUser;

    private String createDate;
    /**
     * 最后一次更新的时间
     */
    private String updateDate;
    /**
     * 返修客户
     */
    private CustomerBean customerBean;
    private CustomerBean providerBean;
    @NotNull
    private GoodsBean goodsBean;
    private String goodsSerial;

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getComment ()
    {
        return comment;
    }

    public void setComment (String comment)
    {
        this.comment = comment;
    }

    public GoodsIssueStatusCode getStatusCode ()
    {
        return statusCode;
    }

    public void setStatusCode (GoodsIssueStatusCode statusCode)
    {
        this.statusCode = statusCode;
    }

    public UserBean getSignUser ()
    {
        return signUser;
    }

    public void setSignUser (UserBean signUser)
    {
        this.signUser = signUser;
    }

    public String getUpdateDate ()
    {
        return updateDate;
    }

    public void setUpdateDate (String updateDate)
    {
        this.updateDate = updateDate;
    }

    public UserBean getUpdateUser ()
    {
        return updateUser;
    }

    public void setUpdateUser (UserBean updateUser)
    {
        this.updateUser = updateUser;
    }

    public CustomerBean getCustomerBean ()
    {
        return customerBean;
    }

    public void setCustomerBean (CustomerBean customerBean)
    {
        this.customerBean = customerBean;
    }

    public CustomerBean getProviderBean ()
    {
        return providerBean;
    }

    public void setProviderBean (CustomerBean providerBean)
    {
        this.providerBean = providerBean;
    }

    public String getCreateDate ()
    {
        return createDate;
    }

    public void setCreateDate (String createDate)
    {
        this.createDate = createDate;
    }

    public GoodsBean getGoodsBean ()
    {
        return goodsBean;
    }

    public void setGoodsBean (GoodsBean goodsBean)
    {
        this.goodsBean = goodsBean;
    }

    public String getGoodsSerial ()
    {
        return goodsSerial;
    }

    public void setGoodsSerial (String goodsSerial)
    {
        this.goodsSerial = goodsSerial;
    }

}
