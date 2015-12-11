package com.oreilly.demo.android.pa.uidemo.model;


/**
 * Created by Michael on 11/28/2015.
 */


/*
This class is reusable in the sense that any application involving monsters can use this minimal
classes without worrying about irrelevant attributes. To ensure reusability, monsters have deliberately
not been given x and y coordinates. MonsterWithCoordinates serves as a wrapper around monster
objects, and these have x and y coordinates.

 */
public class Monster {

    protected Integer color;


    public int getColor() {
        return color;
    }


    public void setColor(int color) {
        this.color = color;
    }

}
