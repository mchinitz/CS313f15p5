package com.oreilly.demo.android.pa.uidemo;

/**
 * Created by Lisa on 11/21/2015.
 */
public class Monster {
    private int x,y,color;
    public Monster(int x, int y, int color)
    {
        this.x = x;
        this.y = y;
        this.color = color;
    }
    public int [] get_data()
    {
        int data[] = {x,y,color};
        return data;
    }
    public void set_data(int data[])
    {
        if (data.length != 3)
            throw new RuntimeException("data for monster setter must be of length = 3");
        x = data[0];
        y = data[1];
        color = data[2];
    }





}
