package com.cxx.purchasecharge.app.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class LogManagementAroundAdvice implements MethodInterceptor
{

    @Override
    public Object invoke (MethodInvocation invocation) throws Throwable
    {
        System.out.println ("Enter LogManagementAroundAdvice");
        invocation.proceed ();
        return null;
    }
}
