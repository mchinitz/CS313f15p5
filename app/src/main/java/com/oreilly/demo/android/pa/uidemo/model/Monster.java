package com.oreilly.demo.android.pa.uidemo.model;

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColor() {
        return color;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setColor(int color) {
        this.color = color;
    }





}
