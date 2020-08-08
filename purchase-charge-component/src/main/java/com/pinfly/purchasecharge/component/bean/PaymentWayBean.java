package com.pinfly.purchasecharge.component.bean;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author xiang
 * 
 */
public class PaymentWayBean extends BaseBean
{
    private static final long serialVersionUID = 7118174668290265090L;

    @NotNull
    private String name;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

}
