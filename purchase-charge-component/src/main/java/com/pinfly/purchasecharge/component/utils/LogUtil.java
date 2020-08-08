package com.pinfly.purchasecharge.component.utils;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.pinfly.purchasecharge.core.model.AccountingModeCode;
import com.pinfly.purchasecharge.core.model.GoodsIssueStatusCode;
import com.pinfly.purchasecharge.core.model.OrderStatusCode;
import com.pinfly.purchasecharge.core.model.OrderTypeCode;
import com.pinfly.purchasecharge.core.model.PaymentAccountMode;
import com.pinfly.purchasecharge.core.model.PaymentTransferTypeCode;
import com.pinfly.purchasecharge.core.model.PaymentTypeCode;
import com.pinfly.purchasecharge.core.model.ReceiptTypeCode;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.core.util.ModelClassHelper;

@SuppressWarnings ("all")
public class LogUtil
{
    private static Logger logger = Logger.getLogger (LogUtil.class);

    /**
     * generate a string for target object in simple type of property
     * @param target
     * @return
     */
    public static String createLogComment (Object target)
    {
        String comment = "";
        if (null != target)
        {
            List <Map <String, Object>> targetFieldInfos = ModelClassHelper.getFieldsInfo (target);
            for (Iterator <Map <String, Object>> targetIter = targetFieldInfos.iterator (); targetIter.hasNext ();)
            {
                Map <String, Object> targetMap = (Map <String, Object>) targetIter.next ();
                Class fieldType = (Class) targetMap.get (ModelClassHelper.FIELD_TYPE);
                Object fieldValue = targetMap.get (ModelClassHelper.FIELD_VALUE);
                if (isLogable (fieldValue))
                {
                    if (isInternalObject (fieldType))
                    {
                        comment += "[";
                        String subObjectInfo = "";
                        List <Map <String, Object>> targetFieldInfos2 = ModelClassHelper.getFieldsInfo (fieldValue);
                        for (Iterator <Map <String, Object>> targetIter2 = targetFieldInfos2.iterator (); targetIter2.hasNext ();)
                        {
                            Map <String, Object> targetMap2 = (Map <String, Object>) targetIter2.next ();
                            Class fieldType2 = (Class) targetMap2.get (ModelClassHelper.FIELD_TYPE);
                            Object fieldValue2 = targetMap2.get (ModelClassHelper.FIELD_VALUE);
                            if (BeanUtils.isSimpleValueType (fieldType2) && isLogable (fieldValue2))
                            {
                                subObjectInfo += convert (fieldValue2) + " ";
                            }
                        }
                        subObjectInfo = subObjectInfo.substring (0, subObjectInfo.length () - 1);
                        comment += subObjectInfo;
                        comment += "] ";
                    }
                    else
                    {
                        if (BeanUtils.isSimpleValueType (fieldType))
                        {
                            comment += convert (fieldValue) + " ";
                        }
                    }
                }
            }
        }
        return comment;
    }

    /**
     * find out the difference between source and target in simple type of property
     * @param source
     * @param target
     * @return
     */
    public static String createLogComment (Object source, Object target)
    {
        String comment = "";
        if (null != source && null != target)
        {
            if (source.getClass ().getName ().equals (target.getClass ().getName ()))
            {
                if (source.getClass ().isEnum ())
                {
                    comment = (source.toString () + " -> " + target.toString ());
                }
                else
                {
                    List <Map <String, Object>> sourceFieldInfos = ModelClassHelper.getFieldsInfo (source);
                    List <Map <String, Object>> targetFieldInfos = ModelClassHelper.getFieldsInfo (target);
                    for (Iterator <Map <String, Object>> sourceIter = sourceFieldInfos.iterator (); sourceIter.hasNext ();)
                    {
                        Map <String, Object> sourceMap = (Map <String, Object>) sourceIter.next ();
                        String sourceFieldName = sourceMap.get (ModelClassHelper.FIELD_NAME).toString ();
                        Class sourceFieldType = (Class) sourceMap.get (ModelClassHelper.FIELD_TYPE);
                        Object sourceFieldValue = sourceMap.get (ModelClassHelper.FIELD_VALUE);
                        for (Iterator <Map <String, Object>> targetIter = targetFieldInfos.iterator (); targetIter.hasNext ();)
                        {
                            Map <String, Object> targetMap = (Map <String, Object>) targetIter.next ();
                            String targetFieldName = targetMap.get (ModelClassHelper.FIELD_NAME).toString ();
                            Class targetFieldType = (Class) targetMap.get (ModelClassHelper.FIELD_TYPE);
                            Object targetFieldValue = targetMap.get (ModelClassHelper.FIELD_VALUE);
                            if (sourceFieldName.equals (targetFieldName)
                                && BeanUtils.isSimpleValueType (sourceFieldType)
                                && BeanUtils.isSimpleValueType (targetFieldType)
                                && !isEquals (sourceFieldValue, targetFieldValue))
                            {
                                comment += (convert (sourceFieldValue) + " -> " + convert (targetFieldValue) + " ");
                            }
                        }
                    }
                }
            }
        }
        return comment;
    }

    private static boolean isInternalObject (Class objectType)
    {
        return null != objectType && objectType.getName ().startsWith ("com.cxx.purchasecharge.core.model.persistence");
    }

    private static boolean isLogable (Object fieldValue)
    {
        return null != fieldValue && StringUtils.isNotBlank (fieldValue.toString ())
               && !"0".equals (fieldValue.toString ()) && !"0.0".equals (fieldValue.toString ());
    }

    private static boolean isEquals (Object source, Object target)
    {
        if (null == source && null == target)
        {
            return true;
        }
        if (null != source && null != target && source.getClass ().getName ().equals (target.getClass ().getName ()))
        {
            if (BeanUtils.isSimpleValueType (source.getClass ()))
            {
                return convert (source).equals (convert (target));
            }
            else
            {
                return source.toString ().equals (target.toString ());
            }
        }
        if (null == source || null == target)
        {
            return false;
        }
        return false;
    }

    private static String convert (Object obj)
    {
        if (null != obj)
        {
            if (obj instanceof Date)
            {
                return DateUtils.date2String ((Date) obj, DateUtils.DATE_TIME_PATTERN);
            }
            if (obj.getClass ().isEnum ())
            {
                if (obj.equals (OrderStatusCode.NEW))
                {
                    return "新建的";
                }
                if (obj.equals (OrderStatusCode.CANCELED))
                {
                    return "取消的";
                }
                if (obj.equals (OrderStatusCode.COMPLETED))
                {
                    return "完成的";
                }
                if (obj.equals (OrderTypeCode.IN))
                {
                    return "入库正单";
                }
                if (obj.equals (OrderTypeCode.IN_RETURN))
                {
                    return "入库退单";
                }
                if (obj.equals (OrderTypeCode.OUT))
                {
                    return "出库正单";
                }
                if (obj.equals (OrderTypeCode.OUT_RETURN))
                {
                    return "出库退单";
                }
                if (obj.equals (AccountingModeCode.IN_COME))
                {
                    return "收入记账";
                }
                if (obj.equals (AccountingModeCode.OUT_LAY))
                {
                    return "支出记账";
                }
                if (obj.equals (GoodsIssueStatusCode.NEW))
                {
                    return "新建的";
                }
                if (obj.equals (GoodsIssueStatusCode.APPROVED))
                {
                    return "批准的";
                }
                if (obj.equals (GoodsIssueStatusCode.PROCESSED))
                {
                    return "处理中的";
                }
                if (obj.equals (GoodsIssueStatusCode.COMPLETED))
                {
                    return "完成的";
                }
                if (obj.equals (GoodsIssueStatusCode.INVALID))
                {
                    return "无效的";
                }
                if (obj.equals (PaymentAccountMode.CASH))
                {
                    return "现金账号";
                }
                if (obj.equals (PaymentAccountMode.DEPOSIT))
                {
                    return "储蓄账号";
                }
                if (obj.equals (PaymentTransferTypeCode.ACCOUNTING_IN))
                {
                    return "记账收入";
                }
                if (obj.equals (PaymentTransferTypeCode.ACCOUNTING_OUT))
                {
                    return "记账支出";
                }
                if (obj.equals (PaymentTransferTypeCode.CUSTOMER_TRANSFER))
                {
                    return "客户收款";
                }
                if (obj.equals (PaymentTransferTypeCode.PROVIDER_TRANSFER))
                {
                    return "供应商付款";
                }
                if (obj.equals (PaymentTransferTypeCode.INITIAL_REMAIN))
                {
                    return "期初结余";
                }
                if (obj.equals (PaymentTransferTypeCode.INTERNAL_TRANSFER))
                {
                    return "内部转账";
                }
                if (obj.equals (PaymentTypeCode.IN_ORDER))
                {
                    return "新增应付款";
                }
                if (obj.equals (PaymentTypeCode.IN_ORDER_RETURN))
                {
                    return "退回应付款";
                }
                if (obj.equals (PaymentTypeCode.IN_PAID_MONEY))
                {
                    return "已付应付款";
                }
                if (obj.equals (PaymentTypeCode.OUT_ORDER))
                {
                    return "新增应收款";
                }
                if (obj.equals (PaymentTypeCode.OUT_ORDER_RETURN))
                {
                    return "退回应收款";
                }
                if (obj.equals (PaymentTypeCode.OUT_PAID_MONEY))
                {
                    return "已收应收款";
                }
                if (obj.equals (PaymentTypeCode.INITIAL_BALANCE))
                {
                    return "期初应付/收款";
                }
                if (obj.equals (ReceiptTypeCode.ORDINARY))
                {
                    return "普通税票";
                }
                if (obj.equals (ReceiptTypeCode.VALUE_ADDED))
                {
                    return "增值税票";
                }
            }
            return obj.toString ();
        }
        return "";
    }

    public static void main (String[] args)
    {
        System.out.println (convert (OrderStatusCode.NEW));
    }
}
