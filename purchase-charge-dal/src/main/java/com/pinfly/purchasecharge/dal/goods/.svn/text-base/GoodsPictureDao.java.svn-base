package com.pinfly.purchasecharge.dal.goods;

import java.util.List;

import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsPicture;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface GoodsPictureDao extends MyGenericDao <GoodsPicture, Long>
{
    List <GoodsPicture> findByGoods (long goodsId);

    GoodsPicture findByFileNameAndContentType (String fileName, String contentType);

}
