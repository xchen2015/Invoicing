package com.cxx.purchasecharge.component;

import com.pinfly.purchasecharge.component.bean.usersecurity.UserBean;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.User;

public class SpringBeanUtilTest
{

    public static void main (String[] args) throws Throwable
    {
        A a = new A (1);
        a.setAge (20);
        B b = new B ();
        // org.springframework.beans.BeanUtils.copyProperties (a, b);
        org.springframework.beans.BeanUtils.copyProperties (a, b, new String[]
        { "id" });
        System.out.println ("B.id = " + b.getId ());
        System.out.println ("B.age = " + b.getAge ());

        User user = new User ();
        user.setId (2);
        user.setNetCommunityId ("22");

        UserBean userBean = new UserBean ();
        org.springframework.beans.BeanUtils.copyProperties (user, userBean, new String[]
        { "id", "qq" });
        System.out.println ("B.id = " + userBean.getId ());
        System.out.println ("B.age = " + userBean.getNetCommunityId ());
    }
}

class A
{
    private long id;
    private long age;

    public A (long id)
    {
        this.id = id;
    }

    public long getId ()
    {
        return id;
    }

    public void setId (long id)
    {
        this.id = id;
    }

    public long getAge ()
    {
        return age;
    }

    public void setAge (long age)
    {
        this.age = age;
    }
}

class B
{
    private String id;
    private String age;

    public B ()
    {
    }

    public B (String id)
    {
        this.id = id;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public long getAge ()
    {
        return Long.parseLong (age);
    }

    public void setAge (long age)
    {
        this.age = age + "";
    }
}
