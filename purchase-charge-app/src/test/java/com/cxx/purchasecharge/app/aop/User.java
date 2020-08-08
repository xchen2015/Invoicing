package com.cxx.purchasecharge.app.aop;

import java.util.List;

public class User
{
    int id;
    String name;
    int age;

    List <Car> cars;

    public User ()
    {
    }

    /**
     * @param id
     * @param name
     * @param age
     * @param cars
     */
    public User (int id, String name, int age, List <Car> cars)
    {
        super ();
        this.id = id;
        this.name = name;
        this.age = age;
        this.cars = cars;
    }

    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public int getAge ()
    {
        return age;
    }

    public void setAge (int age)
    {
        this.age = age;
    }

    public List <Car> getCars ()
    {
        return cars;
    }

    public void setCars (List <Car> cars)
    {
        this.cars = cars;
    }

    @Override
    public String toString ()
    {
        return "User [id=" + id + ", name=" + name + ", age=" + age + ", cars=" + cars + "]";
    }
}

class Car
{
    String id;
    String brand;
    int age;
    String color;

    public Car ()
    {
    }

    /**
     * @param id
     * @param brand
     * @param age
     * @param color
     */
    public Car (String id, String brand, int age, String color)
    {
        super ();
        this.id = id;
        this.brand = brand;
        this.age = age;
        this.color = color;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getBrand ()
    {
        return brand;
    }

    public void setBrand (String brand)
    {
        this.brand = brand;
    }

    public int getAge ()
    {
        return age;
    }

    public void setAge (int age)
    {
        this.age = age;
    }

    public String getColor ()
    {
        return color;
    }

    public void setColor (String color)
    {
        this.color = color;
    }

    @Override
    public String toString ()
    {
        return "Car [id=" + id + ", brand=" + brand + ", age=" + age + ", color=" + color + "]";
    }
}

enum Mode
{
    INSERT, UPDATE, DELETE
}
