package com.oreilly.demo.android.pa.uidemo;

import java.util.List;


/**
 * Created by Lisa on 11/21/2015.
 */

//whenever there is a change to
public class UpdateMonstersListener implements observer {

    private Model model;

    UpdateMonstersListener(Model model)
    {
        this.model = model;
    }

    public void draw_monster(int x, int y)
    {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Object update() {

        throw new RuntimeException("Not implemented");

    }
}
