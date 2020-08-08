package com.pinfly.purchasecharge.component.controller.report;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinfly.purchasecharge.component.bean.BaseBean;
import com.pinfly.purchasecharge.component.bean.PieChartDataBean;
import com.pinfly.purchasecharge.component.controller.GenericController;
import com.pinfly.purchasecharge.core.model.persistence.goods.Goods;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOut;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOutItem;
import com.pinfly.purchasecharge.core.util.Arith;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.dal.out.OrderOutDao;
import com.pinfly.purchasecharge.dal.out.OrderOutItemDao;

@Controller
@RequestMapping ("/reportGoods")
public class ReportGoodsManager extends GenericController <BaseBean>
{
    @SuppressWarnings ("unused")
    private static final Logger logger = Logger.getLogger (ReportGoodsManager.class);

    @Autowired
    private OrderOutDao orderOutDao;
    @Autowired
    private OrderOutItemDao orderOutItemDao;

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "reportGoods";
    }

    @RequestMapping (value = "/generatePieForGoodsSalesAmount", method = RequestMethod.POST)
    public @ResponseBody
    String generatePieForGoodsSalesAmount (@RequestParam String goodsType, @RequestParam String start,
                                           @RequestParam String end)
    {
        Timestamp std = DateUtils.date2Timestamp (DateUtils.string2Date (start + " 00:00:00.000", DateUtils.DATE_TIME_MILLISECOND_PATTERN));
        Timestamp endd = DateUtils.date2Timestamp (DateUtils.string2Date (end + " 23:59:59.999", DateUtils.DATE_TIME_MILLISECOND_PATTERN));

        Map <String, Long> goodsSalesQuantityMap = new HashMap <String, Long> ();
        Long count = 0L;
        List <OrderOut> orderOutFinishedList = orderOutDao.findByRangeCreateDate (std, endd);
        for (OrderOut order : orderOutFinishedList)
        {
            List <OrderOutItem> oiList = orderOutItemDao.findByOrder (order.getId (), true);
            for (OrderOutItem oi : oiList)
            {
                boolean flag = false;
                if (StringUtils.isNotBlank (goodsType))
                {
                    long goodsTypeId = Long.parseLong (goodsType);
                    Goods goods = oi.getGoods ();
                    if (null != goods && null != goods.getType () && goodsTypeId == goods.getType ().getId ())
                    {
                        flag = true;
                    }
                }
                else
                {
                    flag = true;
                }

                if (flag)
                {
                    count += oi.getAmount ();
                    String goodsName = oi.getGoods ().getName ();
                    if (!goodsSalesQuantityMap.containsKey (goodsName))
                    {
                        goodsSalesQuantityMap.put (goodsName, oi.getAmount ());
                    }
                    else
                    {
                        Long amount = goodsSalesQuantityMap.get (goodsName);
                        goodsSalesQuantityMap.put (goodsName, amount + oi.getAmount ());
                    }
                }
            }
        }

        List <PieChartDataBean> chartDatas = new ArrayList <PieChartDataBean> ();
        for (Map.Entry <String, Long> entry : goodsSalesQuantityMap.entrySet ())
        {
            if (entry.getValue () > 0)
            {
                PieChartDataBean chartDataBean = new PieChartDataBean ();
                chartDataBean.setName (entry.getKey ());
                chartDataBean.setY (entry.getValue () / (count * 1.00f));
                chartDatas.add (chartDataBean);
            }
        }

        if (CollectionUtils.isNotEmpty (chartDatas))
        {
            Collections.sort (chartDatas);
            chartDatas.get (0).setSliced (true);
        }

        JSONArray json = JSONArray.fromObject (chartDatas);
        return json.toString ();
    }

    @RequestMapping (value = "/generatePieForGoodsSalesSum", method = RequestMethod.POST)
    public @ResponseBody
    String generatePieForGoodsSalesSum (@RequestParam String goodsType, @RequestParam String start,
                                        @RequestParam String end)
    {
        Timestamp std = DateUtils.date2Timestamp (DateUtils.string2Date (start + " 00:00:00.000", DateUtils.DATE_TIME_MILLISECOND_PATTERN));
        Timestamp endd = DateUtils.date2Timestamp (DateUtils.string2Date (end + " 23:59:59.999", DateUtils.DATE_TIME_MILLISECOND_PATTERN));

        Map <String, Float> goodsSalesQuantityMap = new HashMap <String, Float> ();
        float count = 0;
        List <OrderOut> orderOutFinishedList = orderOutDao.findByRangeCreateDate (std, endd);
        for (OrderOut order : orderOutFinishedList)
        {
            List <OrderOutItem> oiList = orderOutItemDao.findByOrder (order.getId (), true);
            for (OrderOutItem oi : oiList)
            {
                boolean flag = false;
                if (StringUtils.isNotBlank (goodsType))
                {
                    long goodsTypeId = Long.parseLong (goodsType);
                    Goods goods = oi.getGoods ();
                    if (null != goods && null != goods.getType () && goodsTypeId == goods.getType ().getId ())
                    {
                        flag = true;
                    }
                }
                else
                {
                    flag = true;
                }

                if (flag)
                {
                    count += oi.getSum ();
                    String goodsName = oi.getGoods ().getName ();
                    if (!goodsSalesQuantityMap.containsKey (goodsName))
                    {
                        goodsSalesQuantityMap.put (goodsName, oi.getSum ());
                    }
                    else
                    {
                        Float amount = goodsSalesQuantityMap.get (goodsName);
                        goodsSalesQuantityMap.put (goodsName, amount + oi.getSum ());
                    }
                }
            }
        }

        List <PieChartDataBean> chartDatas = new ArrayList <PieChartDataBean> ();
        for (Map.Entry <String, Float> entry : goodsSalesQuantityMap.entrySet ())
        {
            if (entry.getValue () > 0)
            {
                PieChartDataBean chartDataBean = new PieChartDataBean ();
                chartDataBean.setName (entry.getKey ());
                chartDataBean.setY (entry.getValue () / (count * 1.00f));
                chartDatas.add (chartDataBean);
            }
        }

        if (CollectionUtils.isNotEmpty (chartDatas))
        {
            Collections.sort (chartDatas);
            chartDatas.get (0).setSliced (true);
        }

        JSONArray json = JSONArray.fromObject (chartDatas);
        return json.toString ();
    }

    @RequestMapping (value = "/generateColumnForGoodsSalesAmount", method = RequestMethod.POST)
    public @ResponseBody
    String generateColumnForGoodsSalesAmount (@RequestParam String goodsType, @RequestParam String start,
                                              @RequestParam String end)
    {
        Timestamp std = DateUtils.date2Timestamp (DateUtils.string2Date (start + " 00:00:00.000", DateUtils.DATE_TIME_MILLISECOND_PATTERN));
        Timestamp endd = DateUtils.date2Timestamp (DateUtils.string2Date (end + " 23:59:59.999", DateUtils.DATE_TIME_MILLISECOND_PATTERN));

        Map <String, Long> goodsSalesQuantityMap = new HashMap <String, Long> ();
        List <OrderOut> orderOutFinishedList = orderOutDao.findByRangeCreateDate (std, endd);
        for (OrderOut order : orderOutFinishedList)
        {
            List <OrderOutItem> oiList = orderOutItemDao.findByOrder (order.getId (), true);
            for (OrderOutItem oi : oiList)
            {
                boolean flag = false;
                if (StringUtils.isNotBlank (goodsType))
                {
                    long goodsTypeId = Long.parseLong (goodsType);
                    Goods goods = oi.getGoods ();
                    if (null != goods && null != goods.getType () && goodsTypeId == goods.getType ().getId ())
                    {
                        flag = true;
                    }
                }
                else
                {
                    flag = true;
                }

                if (flag)
                {
                    String goodsName = oi.getGoods ().getName ();
                    if (!goodsSalesQuantityMap.containsKey (goodsName))
                    {
                        goodsSalesQuantityMap.put (goodsName, oi.getAmount ());
                    }
                    else
                    {
                        Long amount = goodsSalesQuantityMap.get (goodsName);
                        goodsSalesQuantityMap.put (goodsName, amount + oi.getAmount ());
                    }
                }
            }
        }

        List <PieChartDataBean> chartDatas = new ArrayList <PieChartDataBean> ();
        for (Map.Entry <String, Long> entry : goodsSalesQuantityMap.entrySet ())
        {
            PieChartDataBean chartDataBean = new PieChartDataBean ();
            chartDataBean.setName (entry.getKey ());
            chartDataBean.setY (entry.getValue ());
            chartDatas.add (chartDataBean);
        }

        JSONArray json = JSONArray.fromObject (chartDatas);
        return json.toString ();
    }

    @RequestMapping (value = "/generateColumnForGoodsSalesSum", method = RequestMethod.POST)
    public @ResponseBody
    String generateColumnForGoodsSalesSum (@RequestParam String goodsType, @RequestParam String start,
                                           @RequestParam String end)
    {
        Timestamp std = DateUtils.date2Timestamp (DateUtils.string2Date (start + " 00:00:00.000", DateUtils.DATE_TIME_MILLISECOND_PATTERN));
        Timestamp endd = DateUtils.date2Timestamp (DateUtils.string2Date (end + " 23:59:59.999", DateUtils.DATE_TIME_MILLISECOND_PATTERN));

        Map <String, Float> goodsSalesQuantityMap = new HashMap <String, Float> ();
        List <OrderOut> orderOutFinishedList = orderOutDao.findByRangeCreateDate (std, endd);
        for (OrderOut order : orderOutFinishedList)
        {
            List <OrderOutItem> oiList = orderOutItemDao.findByOrder (order.getId (), true);
            for (OrderOutItem oi : oiList)
            {
                boolean flag = false;
                if (StringUtils.isNotBlank (goodsType))
                {
                    long goodsTypeId = Long.parseLong (goodsType);
                    Goods goods = oi.getGoods ();
                    if (null != goods && null != goods.getType () && goodsTypeId == goods.getType ().getId ())
                    {
                        flag = true;
                    }
                }
                else
                {
                    flag = true;
                }

                if (flag)
                {
                    String goodsName = oi.getGoods ().getName ();
                    if (!goodsSalesQuantityMap.containsKey (goodsName))
                    {
                        goodsSalesQuantityMap.put (goodsName, oi.getSum ());
                    }
                    else
                    {
                        Float amount = goodsSalesQuantityMap.get (goodsName);
                        goodsSalesQuantityMap.put (goodsName, amount + oi.getSum ());
                    }
                }
            }
        }

        List <PieChartDataBean> chartDatas = new ArrayList <PieChartDataBean> ();
        for (Map.Entry <String, Float> entry : goodsSalesQuantityMap.entrySet ())
        {
            PieChartDataBean chartDataBean = new PieChartDataBean ();
            chartDataBean.setName (entry.getKey ());
            chartDataBean.setY (Arith.round (entry.getValue (), -1));
            chartDatas.add (chartDataBean);
        }

        JSONArray json = JSONArray.fromObject (chartDatas);
        return json.toString ();
    }

}
