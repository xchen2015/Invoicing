package com.cxx.purchasecharge.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.pinfly.purchasecharge.component.bean.OrderItemBean;

public class JSONTest
{
    static
    {
        // ApplicationContext applicationContext = new
        // ClassPathXmlApplicationContext (
        // "META-INF/spring/purchase-charge-dao.xml");
        // orderDao = (OutOrderDao) applicationContext.getBean ("orderDao");
    }

    @Test
    public void testMap () throws JSONException
    {
        Map <String, Object> map = new HashMap <String, Object> ();
        map.put ("key1", "value1");
        map.put ("key2", 10.2);
        JSONArray jsonArray = JSONArray.fromObject (map);
        System.out.println (jsonArray);
        JSONObject jsonObject = JSONObject.fromObject (map);
        System.out.println (jsonObject);
        String[] sArr = new String[2];
        sArr[0] = "s1";
        sArr[1] = "s2";
        System.out.println (JSONArray.fromObject (sArr));
    }

    @Test
    public void testStr ()
    {
        String s = "0,102,,780.0,10,7800.0,gg;0,101,,195.0,8,1560.0,gg;";
        List <OrderItemBean> orderItemBeans = new ArrayList <OrderItemBean> ();
        String orderItemListStr = s;
        if (StringUtils.isNotBlank (orderItemListStr))
        {
            String[] orderItemArr = orderItemListStr.split (";");
            if (orderItemArr.length > 0)
            {
                for (String orderItem : orderItemArr)
                {
                    if (StringUtils.isNotBlank (orderItem))
                    {
                        String[] itemArr = orderItem.split (",");
                        OrderItemBean orderItemBean = new OrderItemBean ();
                        orderItemBean.setId (itemArr[0]);
                        orderItemBean.setGoodsId (itemArr[1]);
                        orderItemBean.setGoodsUnit (itemArr[2]);
                        orderItemBean.setUnitPrice (StringUtils.isNotBlank (itemArr[3]) ? Float.parseFloat (itemArr[3])
                                                                                       : 0);
                        orderItemBean.setAmount (StringUtils.isNotBlank (itemArr[4]) ? Integer.parseInt (itemArr[4])
                                                                                    : 0);
                        orderItemBean.setSum (StringUtils.isNotBlank (itemArr[5]) ? Float.parseFloat (itemArr[5]) : 0);
                        orderItemBean.setComment (itemArr[6]);
                        orderItemBeans.add (orderItemBean);
                    }
                }
            }
        }
        System.out.println (orderItemBeans);
    }

}
