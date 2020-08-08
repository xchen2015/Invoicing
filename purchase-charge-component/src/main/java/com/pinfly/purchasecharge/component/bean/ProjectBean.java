package com.pinfly.purchasecharge.component.bean;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import com.pinfly.purchasecharge.core.model.OrderStatusCode;
import com.pinfly.purchasecharge.core.model.ProjectStatusCode;

public class ProjectBean extends BaseBean
{
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String name;
    @NotNull
    private CustomerBean customer;
    @NotBlank
    private String start;
    private String end;
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

    /**
     * 交易额
     */
    private float dealMoney;
    private float profit;

    private String userCreatedBy;
    private String userUpdatedBy;

    /**
     * 设备费
     */
    private List <OrderBean> orders;
    private String ordersText;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public CustomerBean getCustomer ()
    {
        return customer;
    }

    public void setCustomer (CustomerBean customer)
    {
        this.customer = customer;
    }

    public String getStart ()
    {
        return start;
    }

    public void setStart (String start)
    {
        this.start = start;
    }

    public String getEnd ()
    {
        return end;
    }

    public void setEnd (String end)
    {
        this.end = end;
    }

    public String getComment ()
    {
        return comment;
    }

    public void setComment (String comment)
    {
        this.comment = comment;
    }

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

    public float getDealMoney ()
    {
        return dealMoney;
    }

    public void setDealMoney (float dealMoney)
    {
        this.dealMoney = dealMoney;
    }

    public float getProfit ()
    {
        return profit;
    }

    public void setProfit (float profit)
    {
        this.profit = profit;
    }

    public String getUserCreatedBy ()
    {
        return userCreatedBy;
    }

    public void setUserCreatedBy (String userCreatedBy)
    {
        this.userCreatedBy = userCreatedBy;
    }

    public String getUserUpdatedBy ()
    {
        return userUpdatedBy;
    }

    public void setUserUpdatedBy (String userUpdatedBy)
    {
        this.userUpdatedBy = userUpdatedBy;
    }

    public List <OrderBean> getOrders ()
    {
        return orders;
    }

    public void setOrders (List <OrderBean> orders)
    {
        this.orders = orders;
        float orderDealMoney = 0;
        float orderProfit = 0;
        if (CollectionUtils.isNotEmpty (orders))
        {
            String ordersText = "";
            for (OrderBean orderBean : orders)
            {
                if (!OrderStatusCode.CANCELED.equals (orderBean.getStatusCode ()))
                {
                    ordersText += orderBean.getId () + ",";
                    orderDealMoney += orderBean.getReceivable ();
                    orderProfit += orderBean.getProfit ();
                }
            }
            if (StringUtils.isNotBlank (ordersText))
            {
                ordersText = ordersText.substring (0, ordersText.length () - 1);
            }
            this.setOrdersText (ordersText);
        }
        this.setDealMoney (this.getConstructionFee () + this.getOtherFee () + orderDealMoney);
        this.setProfit (this.getConstructionFee () + this.getOtherFee () + orderProfit);
    }

    public String getOrdersText ()
    {
        return ordersText;
    }

    public void setOrdersText (String ordersText)
    {
        this.ordersText = ordersText;
    }

}
