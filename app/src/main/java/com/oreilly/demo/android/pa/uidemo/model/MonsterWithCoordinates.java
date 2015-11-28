package com.oreilly.demo.android.pa.uidemo.model;

/**
 * Created by Michael on 11/21/2015.
 */
public class MonsterWithCoordinates extends Monster {
    private int x,y;
    public MonsterWithCoordinates(int x, int y, int color)
    {
        this.x = x;
        this.y = y;
        super.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public void setX(int x){
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }






}
