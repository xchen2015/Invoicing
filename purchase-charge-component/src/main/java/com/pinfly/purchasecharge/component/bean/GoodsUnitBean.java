package com.pinfly.purchasecharge.component.bean;

import org.hibernate.validator.constraints.NotBlank;

public class GoodsUnitBean extends BaseBean
{
    private static final long serialVersionUID = 7118174668290265090L;

    @NotBlank
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
