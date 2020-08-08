package com.cxx.purchasecharge.app.aop;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LogManagementAspect
{
    /**
     * add操作执行之前执行的方法
     * 
     * @param joinpoint包含action所有的相关配置信息和request等内置对象。
     * @throwsClassNotFoundException
     * @throwsInstantiationException
     * @throwsIllegalAccessException
     */
    public void before (JoinPoint joinpoint) throws ClassNotFoundException, InstantiationException,
                                            IllegalAccessException
    {
        Object idObj = ModelClassHelper.getFieldValueByName ("id", joinpoint.getArgs ()[0]);
        if (null != idObj && "0".equals (idObj.toString ()))
        {
            logDo (joinpoint);
            System.out.println ("被拦截方法调用之前调用此方法 before");
        }
    }

    // @Around ("execution(* com.cxx.purchasecharge.aop.AopDao.update*(..))")
    public void around (ProceedingJoinPoint joinpoint) throws Throwable
    {
        logDo (joinpoint);
        System.out.println ("被拦截方法调用前后调用此方法 around");

        System.out.println (joinpoint.proceed ());

        logDo (joinpoint);
        System.out.println ("被拦截方法调用前后调用此方法 around");
    }

    public void after (JoinPoint joinpoint) throws ClassNotFoundException, InstantiationException,
                                           IllegalAccessException
    {
        Object idObj = ModelClassHelper.getFieldValueByName ("id", joinpoint.getArgs ()[0]);
        if (null != idObj && !"0".equals (idObj.toString ()))
        {
            logDo (joinpoint);
            System.out.println ("被拦截方法调用之前调用此方法 after");
        }
    }

    private void logDo (JoinPoint joinpoint)
    {
        // 此方法返回的是一个数组，数组中包括request以及ActionCofig等类对象
        Object[] argumnets = joinpoint.getArgs ();

        for (int i = 0; i < argumnets.length; i++)
        {
            Object obj = argumnets[i];
            // 打印实体类
            String modelName = obj.getClass ().getSimpleName ();
            System.out.println ("实体类：" + modelName);
            // System.out.println("实体对应表：t_"+ modelName.toLowerCase());
            System.out.println ("==========================");

            // 打印参数对象信息
            List <Map <String, Object>> fieldInfos = ModelClassHelper.getFieldsInfo (obj);
            for (Iterator <Map <String, Object>> iter = fieldInfos.iterator (); iter.hasNext ();)
            {
                Map <String, Object> map = (Map <String, Object>) iter.next ();
                System.out.println ("字段类型：" + map.get ("type"));
                System.out.println ("字段名称：" + map.get ("name"));
                System.out.println ("字段值：" + map.get ("value"));
                System.out.println ("==========================");
            }
        }

        // 打印操作类型
        String methodName = joinpoint.getSignature ().getName ();
        System.out.println ("方法名：" + methodName);
        System.out.println ("操作类型: " + getOperationType (methodName));
    }

    /**
     * 根据方法名称，映射操作类型
     * 
     * @param methodName
     * @return
     */
    private String getOperationType (String methodName)
    {
        String type = "查询";
        if (methodName.indexOf ("add") > -1)
        {
            type = "增加";
        }
        else if (methodName.indexOf ("del") > -1)
        {
            type = "删除";
        }
        else if (methodName.indexOf ("update") > -1)
        {
            type = "更新";
        }
        return type;
    }
}
