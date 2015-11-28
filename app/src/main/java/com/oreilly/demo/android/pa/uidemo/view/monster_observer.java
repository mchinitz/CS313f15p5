package com.oreilly.demo.android.pa.uidemo.view;

import com.oreilly.demo.android.pa.uidemo.observer;

/**
 * Created by Lisa on 11/27/2015.
 */
public interface monster_observer extends observer {

    public void draw_monster(int x, int y);
    public int [] get_coordinates();
}
