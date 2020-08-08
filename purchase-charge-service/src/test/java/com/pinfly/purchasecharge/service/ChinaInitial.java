package com.pinfly.purchasecharge.service;

import com.pinfly.purchasecharge.service.utils.PinYinUtil;

public class ChinaInitial
{
    public static void main (String[] args)
    {
        String str = "长沙，我是中国人afdafaYYII4323)(---+=";
        System.out.println ("'" + str + "'的中文首字母：" + PinYinUtil.getFirstSpell (str, false));
    }

}
