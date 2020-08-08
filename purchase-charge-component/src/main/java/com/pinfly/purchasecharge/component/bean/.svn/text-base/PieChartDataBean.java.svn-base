package com.pinfly.purchasecharge.component.bean;

public class PieChartDataBean extends BaseBean implements Comparable <PieChartDataBean>
{
    private static final long serialVersionUID = 1L;

    private String name;
    private float y;
    private boolean sliced = false;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public float getY ()
    {
        return y;
    }

    public void setY (float y)
    {
        this.y = y;
    }

    public boolean isSliced ()
    {
        return sliced;
    }

    public void setSliced (boolean sliced)
    {
        this.sliced = sliced;
    }

    @Override
    public int compareTo (PieChartDataBean o)
    {
        if (o != null)
        {
            return ((Float) o.getY ()).compareTo ((Float) this.y);
        }
        return 1;
    }
}
