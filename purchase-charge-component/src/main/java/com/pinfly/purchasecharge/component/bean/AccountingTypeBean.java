package com.pinfly.purchasecharge.component.bean;

import org.hibernate.validator.constraints.NotBlank;

import com.pinfly.purchasecharge.core.model.AccountingModeCode;

public class AccountingTypeBean extends BaseBean
{
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String name;
    private AccountingModeCode accountingMode;
    private String description;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public AccountingModeCode getAccountingMode ()
    {
        return accountingMode;
    }

    public void setAccountingMode (AccountingModeCode accountingMode)
    {
        this.accountingMode = accountingMode;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }
}
