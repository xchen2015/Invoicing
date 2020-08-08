package com.pinfly.purchasecharge.component.bean;

import org.hibernate.validator.constraints.NotBlank;

import com.pinfly.purchasecharge.core.model.ContractTypeCode;

public class ContractBean extends BaseBean
{
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String name;
    @NotBlank
    private String source;
    private String sourceName;
    private float dealMoney;
    @NotBlank
    private String dateCreated;
    private String userCreatedBy;

    private ContractTypeCode typeCode = ContractTypeCode.ORDER_OUT;
    private String comment;
    private String filePath;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public float getDealMoney ()
    {
        return dealMoney;
    }

    public void setDealMoney (float dealMoney)
    {
        this.dealMoney = dealMoney;
    }

    public String getComment ()
    {
        return comment;
    }

    public void setComment (String comment)
    {
        this.comment = comment;
    }

    public String getFilePath ()
    {
        return filePath;
    }

    public void setFilePath (String filePath)
    {
        this.filePath = filePath;
    }

    public ContractTypeCode getTypeCode ()
    {
        return typeCode;
    }

    public void setTypeCode (ContractTypeCode typeCode)
    {
        this.typeCode = typeCode;
    }

    public String getSource ()
    {
        return source;
    }

    public void setSource (String source)
    {
        this.source = source;
    }

    public void setSourceName (String sourceName)
    {
        this.sourceName = sourceName;
    }

    public String getSourceName ()
    {
        return sourceName;
    }

    public String getDateCreated ()
    {
        return dateCreated;
    }

    public void setDateCreated (String dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public String getUserCreatedBy ()
    {
        return userCreatedBy;
    }

    public void setUserCreatedBy (String userCreatedBy)
    {
        this.userCreatedBy = userCreatedBy;
    }

}
