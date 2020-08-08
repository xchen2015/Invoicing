package com.pinfly.purchasecharge.component.bean;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 货物序列号
 */
public class GoodsSerialNumberBean extends BaseBean
{
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String serialNumber;
    @NotNull
    private GoodsBean goodsBean;

    private OrderItemBean orderInItemBean;
    private OrderItemBean orderOutItemBean;

    public String getSerialNumber ()
    {
        return serialNumber;
    }

    public void setSerialNumber (String serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public GoodsBean getGoodsBean ()
    {
        return goodsBean;
    }

    public void setGoodsBean (GoodsBean goodsBean)
    {
        this.goodsBean = goodsBean;
    }

    public OrderItemBean getOrderInItemBean ()
    {
        return orderInItemBean;
    }

    public void setOrderInItemBean (OrderItemBean orderInItemBean)
    {
        this.orderInItemBean = orderInItemBean;
    }

    public OrderItemBean getOrderOutItemBean ()
    {
        return orderOutItemBean;
    }

    public void setOrderOutItemBean (OrderItemBean orderOutItemBean)
    {
        this.orderOutItemBean = orderOutItemBean;
    }

}
