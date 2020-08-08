package com.pinfly.purchasecharge.core.model.persistence.goods;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.pinfly.purchasecharge.core.model.BaseModel;

/**
 * 货物仓库
 */
@Entity
@Table (name = "pc_goods_depository")
public class GoodsDepository extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private boolean enabled = true;

    private List <Goods> goodses;

    // private List<GoodsStorage> storages;

    public GoodsDepository ()
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

    public boolean isEnabled ()
    {
        return enabled;
    }

    public void setEnabled (boolean enabled)
    {
        this.enabled = enabled;
    }

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "preferedDepository", cascade =
    { CascadeType.REFRESH }, orphanRemoval = true)
    public List <Goods> getGoodses ()
    {
        return goodses;
    }

    public void setGoodses (List <Goods> goodses)
    {
        this.goodses = goodses;
    }

    /*
     * @OneToMany (mappedBy = "depository") public List<GoodsStorage>
     * getStorages() { return storages; }
     * 
     * public void setStorages(List<GoodsStorage> storages) { this.storages =
     * storages; }
     */

}
