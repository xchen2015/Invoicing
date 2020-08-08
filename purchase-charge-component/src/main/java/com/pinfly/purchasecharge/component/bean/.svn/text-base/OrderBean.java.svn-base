package com.pinfly.purchasecharge.component.bean;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.pinfly.purchasecharge.core.model.OrderStatusCode;
import com.pinfly.purchasecharge.core.model.OrderTypeCode;
import com.pinfly.purchasecharge.core.util.Arith;

public class OrderBean extends BaseBean
{
    private static final long serialVersionUID = 1L;

    private String bid;
    @NotNull
    private CustomerBean customerBean;
    private String orderItemList;
    private List <OrderItemBean> orderItemBeans;
    /**
     * 应付额
     */
    private float dealMoney;
    /**
     * 折扣率
     */
    private float discount = 1;
    /**
     * 应收款
     */
    private float receivable;
    /**
     * 实付额
     */
    private float paidMoney = 0;
    private PaymentAccountBean paymentAccountBean;
    /**
     * 利润
     */
    private float profit;
    private String createTime;
    private String updateTime;

    /**
     * 操作员
     */
    @NotBlank
    private String userCreated;
    private String userUpdated;
    private String comment;

    private OrderStatusCode statusCode = OrderStatusCode.NEW;
    @NotNull
    private OrderTypeCode typeCode;
    private ProjectBean projectBean;
    private OrderDeliveryBean deliveryBean;
    
    private AccountingTypeBean accountingTypeBean;
    private float accountingMoney;

    public String getBid ()
    {
        return bid;
    }

    public void setBid (String bid)
    {
        this.bid = bid;
    }

    public List <OrderItemBean> getOrderItemBeans ()
    {
        return orderItemBeans;
    }

    public void setOrderItemBeans (List <OrderItemBean> orderItemBeans)
    {
        this.orderItemBeans = orderItemBeans;
        if (null != orderItemBeans)
        {
            for (OrderItemBean orderItem : orderItemBeans)
            {
                orderItem.setOrderId (this.getId ());
            }
        }
    }

    public float getDealMoney ()
    {
        return dealMoney;
    }

    public float getDiscount ()
    {
        return discount;
    }

    public void setDiscount (float discount)
    {
        this.discount = discount;
    }

    public float getReceivable ()
    {
        return receivable;
    }

    public void setReceivable (float receivable)
    {
        this.receivable = receivable;
    }

    public void setDealMoney (float dealMoney)
    {
        this.dealMoney = dealMoney;
    }

    public float getPaidMoney ()
    {
        return paidMoney;
    }

    public void setPaidMoney (float paidMoney)
    {
        this.paidMoney = paidMoney;
    }

    public PaymentAccountBean getPaymentAccountBean ()
    {
        return paymentAccountBean;
    }

    public void setPaymentAccountBean (PaymentAccountBean paymentAccountBean)
    {
        this.paymentAccountBean = paymentAccountBean;
    }

    public float getProfit ()
    {
        return Arith.round (profit, -1);
    }

    public void setProfit (float profit)
    {
        this.profit = profit;
    }

    public String getComment ()
    {
        return comment;
    }

    public void setComment (String comment)
    {
        this.comment = comment;
    }

    public String getCreateTime ()
    {
        return createTime;
    }

    public void setCreateTime (String createTime)
    {
        this.createTime = createTime;
    }

    public String getUpdateTime ()
    {
        return updateTime;
    }

    public void setUpdateTime (String updateTime)
    {
        this.updateTime = updateTime;
    }

    public OrderStatusCode getStatusCode ()
    {
        return statusCode;
    }

    public void setStatusCode (OrderStatusCode statusCode)
    {
        this.statusCode = statusCode;
    }

    public OrderTypeCode getTypeCode ()
    {
        return typeCode;
    }

    public void setTypeCode (OrderTypeCode typeCode)
    {
        this.typeCode = typeCode;
    }

    public CustomerBean getCustomerBean ()
    {
        return customerBean;
    }

    public void setCustomerBean (CustomerBean customerBean)
    {
        this.customerBean = customerBean;
    }

    public String getOrderItemList ()
    {
        return orderItemList;
    }

    public void setOrderItemList (String orderItemList)
    {
        this.orderItemList = orderItemList;
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

    public ProjectBean getProjectBean ()
    {
        return projectBean;
    }

    public void setProjectBean (ProjectBean projectBean)
    {
        this.projectBean = projectBean;
    }

    public OrderDeliveryBean getDeliveryBean ()
    {
        return deliveryBean;
    }

    public void setDeliveryBean (OrderDeliveryBean deliveryBean)
    {
        this.deliveryBean = deliveryBean;
    }

    public AccountingTypeBean getAccountingTypeBean ()
    {
        return accountingTypeBean;
    }

    public void setAccountingTypeBean (AccountingTypeBean accountingTypeBean)
    {
        this.accountingTypeBean = accountingTypeBean;
    }

    public float getAccountingMoney ()
    {
        return accountingMoney;
    }

    public void setAccountingMoney (float accountingMoney)
    {
        this.accountingMoney = accountingMoney;
    }

}
