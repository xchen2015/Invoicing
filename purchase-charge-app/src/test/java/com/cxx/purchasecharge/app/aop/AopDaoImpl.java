package com.cxx.purchasecharge.app.aop;

public class AopDaoImpl implements AopDao
{
    private static int id = 0;

    public User addUser (User user)
    {
        System.out.println ("argu: " + user);
        System.out.println ("addUser method is called");
        user.setId (id);
        id++;
        return user;
    }

    public User updateUser (User user)
    {
        System.out.println ("argu: " + user);
        System.out.println ("updateUser method is called");
        user.setAge (30);
        return user;
    }

    public User deleteUser (User user)
    {
        System.out.println ("argu: " + user);
        System.out.println ("deleteUser method is called");
        return user;
    }

}
