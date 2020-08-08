package com.pinfly.purchasecharge.core.model.persistence.goods;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pinfly.purchasecharge.core.model.BaseModel;

/**
 * 货物图片
 */
@Entity
@Table (name = "pc_goods_picture")
public class GoodsPicture extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String fileName;
    private String contentType;

    private Goods goods;

    public GoodsPicture ()
    {
    }

    public GoodsPicture (String fileName, String contentType)
    {
        this.fileName = fileName;
        this.contentType = contentType;
    }

    public GoodsPicture (String fileName, String contentType, Goods goods)
    {
        this.fileName = fileName;
        this.contentType = contentType;
        this.goods = goods;
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

    @Column (nullable = false)
    public String getFileName ()
    {
        return fileName;
    }

    public void setFileName (String fileName)
    {
        this.fileName = fileName;
    }

    @Column (nullable = false)
    public String getContentType ()
    {
        return contentType;
    }

    public void setContentType (String contentType)
    {
        this.contentType = contentType;
    }

    @ManyToOne (fetch = FetchType.LAZY, cascade =
    { CascadeType.REFRESH }, optional = false)
    @JoinColumn (name = "goods_id")
    public Goods getGoods ()
    {
        return goods;
    }

    public void setGoods (Goods goods)
    {
        this.goods = goods;
    }
}
