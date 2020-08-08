package com.pinfly.purchasecharge.core.model.persistence;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.pinfly.purchasecharge.core.model.AccountingModeCode;
import com.pinfly.purchasecharge.core.model.BaseModel;

/**
 * 记账类型
 */
@Entity
@Table (name = "pc_accounting_type")
public class AccountingType extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private AccountingModeCode accountingMode;

    private List <Accounting> accountings;

    public AccountingType ()
    {
    }

    public AccountingType (String name, AccountingModeCode accountingMode)
    {
        this.name = name;
        this.accountingMode = accountingMode;
    }

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    public long getId ()
    {
        return id;
    }

    public void setId (long id)
    {
        this.id = id;
    }

    @Column (nullable = false, unique = true)
    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    @Column (nullable = false)
    @Enumerated (EnumType.STRING)
    public AccountingModeCode getAccountingMode ()
    {
        return accountingMode;
    }

    public void setAccountingMode (AccountingModeCode accountingMode)
    {
        this.accountingMode = accountingMode;
    }

    @OneToMany (mappedBy = "type")
    public List <Accounting> getAccountings ()
    {
        return accountings;
    }

    public void setAccountings (List <Accounting> accountings)
    {
        this.accountings = accountings;
    }

}
