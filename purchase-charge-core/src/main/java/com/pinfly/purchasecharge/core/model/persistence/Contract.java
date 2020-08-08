package com.pinfly.purchasecharge.core.model.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.pinfly.purchasecharge.core.model.BaseModel;
import com.pinfly.purchasecharge.core.model.ContractTypeCode;

/**
 * 合同
 */
@Entity
@Table (name = "pc_contract")
public class Contract extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private float dealMoney;
    private ContractTypeCode typeCode = ContractTypeCode.ORDER_OUT;
    private String source;
    private Date dateCreated;
    private long userCreatedBy;
    private String comment;
    private String filePath;

    public Contract ()
    {
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

    public float getDealMoney ()
    {
        return dealMoney;
    }

    public void setDealMoney (float dealMoney)
    {
        this.dealMoney = dealMoney;
    }

    @Column (length = 1024)
    public String getComment ()
    {
        return comment;
    }

    public void setComment (String comment)
    {
        this.comment = comment;
    }

    public long getUserCreatedBy ()
    {
        return userCreatedBy;
    }

    public void setUserCreatedBy (long userCreatedBy)
    {
        this.userCreatedBy = userCreatedBy;
    }

    public String getFilePath ()
    {
        return filePath;
    }

    public void setFilePath (String filePath)
    {
        this.filePath = filePath;
    }

    @Column (nullable = false)
    @Enumerated (EnumType.STRING)
    public ContractTypeCode getTypeCode ()
    {
        return typeCode;
    }

    public void setTypeCode (ContractTypeCode typeCode)
    {
        this.typeCode = typeCode;
    }

    @Column (nullable = false)
    public String getSource ()
    {
        return source;
    }

    public void setSource (String source)
    {
        this.source = source;
    }

    @Column (nullable = false)
    @Temporal (value = TemporalType.TIMESTAMP)
    public Date getDateCreated ()
    {
        return dateCreated;
    }

    public void setDateCreated (Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

}
