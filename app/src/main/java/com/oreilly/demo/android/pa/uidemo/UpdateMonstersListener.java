package com.oreilly.demo.android.pa.uidemo;

import android.view.View;

import java.util.List;


/**
 * Created by Lisa on 11/21/2015.
 */

//whenever there is a change to the monsters
public abstract class UpdateMonstersListener implements observer {

    private Model model;
    private View view;
    private int m,n; //dimensions of board

    public UpdateMonstersListener(Model model, View view, int m, int n)
    {
        this.model = model;
        this.view = view;
        this.m = m;
        this.n = n;
    }

    public void draw_monster(int x, int y)
    {
        throw new RuntimeException("Not implemented");
    }

    //returns the coordinates of the location at which the mouse is pressed by the user
    public abstract int [] get_coordinates();

    @Override
    public Object update() {
        if (model.get_status())
            draw_monster(get_coordinates()[0], get_coordinates()[1]);
        else
       {
           for (int i=0; i<m; i++) {
               for (int j = 0; j < n; j++) {
                   draw_monster(i, j);
               }
           }
       }
        view.invalidate();
        throw new RuntimeException("Not implemented");

    }
}
