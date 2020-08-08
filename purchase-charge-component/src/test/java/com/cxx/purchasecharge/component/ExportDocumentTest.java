package com.cxx.purchasecharge.component;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.pinfly.purchasecharge.core.model.persistence.goods.Goods;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsDepository;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsStorage;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsType;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsUnit;
import com.pinfly.purchasecharge.framework.print.ExportExcel;
import com.pinfly.purchasecharge.framework.print.ExportPdf;
import com.pinfly.purchasecharge.framework.print.PdfMetadata;

public class ExportDocumentTest
{
    public static final String RESULT = "src/test/java/com/cxx/purchasecharge/component/testPdf.pdf";
    public static final String RESULT2 = "src/test/java/com/cxx/purchasecharge/component/testExcel.xlsx";

    public static void main (String[] args) throws Exception
    {
        List <Goods> goodsList = new ArrayList <Goods> ();
        Goods goods = new Goods ();
        goods.setName ("货物1");
        goods.setShortCode ("goods001");
        goods.setBarCode ("ffdsa4564");
        // goods.setTradePrice (3.5f);
        goods.setImportPrice (2.0f);
        goods.setSpecificationModel ("156464646");
        GoodsDepository goodsDepository = new GoodsDepository ();
        goodsDepository.setName ("仓库01");
        goods.setPreferedDepository (goodsDepository);
        GoodsType type = new GoodsType ();
        type.setName ("交换机");
        goods.setType (type);
        GoodsUnit unit = new GoodsUnit ();
        unit.setName ("个");
        goods.setUnit (unit);

        GoodsDepository goodsDepository2 = new GoodsDepository ();
        goodsDepository2.setName ("仓库02");
        List <GoodsStorage> storages = new ArrayList <GoodsStorage> ();
        GoodsStorage storage = new GoodsStorage ();
        storage.setGoods (goods);
        storage.setDepository (goodsDepository);
        storage.setPrice (2.1f);
        storage.setAmount (10);
        storage.setWorth (21);
        storages.add (storage);

        storage = new GoodsStorage ();
        storage.setGoods (goods);
        storage.setDepository (goodsDepository2);
        storage.setPrice (2.2f);
        storage.setAmount (10);
        storage.setWorth (22);
        storages.add (storage);

        goods.setStorages (storages);
        goodsList.add (goods);

        OutputStream out = new FileOutputStream (RESULT);
        List <String> headers = new ArrayList <String> ();
        headers.add ("name");
        headers.add ("shortCode");
        String[] headerss =
        {};
        headerss = headers.toArray (headerss);

        PdfMetadata metadata = new PdfMetadata ();
        metadata.setMark ("品飞信息");
        metadata.setTitle ("品飞信息");
        ExportPdf.export (metadata, headerss, goodsList, out);

        OutputStream out2 = new FileOutputStream (RESULT2);
        ExportExcel.export ("品飞信息", headerss, goodsList, out2);
    }

}
