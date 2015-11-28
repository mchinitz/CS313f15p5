package com.oreilly.demo.android.pa.uidemo;

import com.oreilly.demo.android.pa.uidemo.model.clock.ClockModel;

/**
 * Created by Lisa on 11/27/2015.
 */
public class GameDurationObserver implements observer {

    private ClockModel clockModel;

    public GameDurationObserver(ClockModel clockModel)
    {
        this.clockModel = clockModel;
    }

    @Override
    public Object update() {
        if (clockModel.get_is_expired())
           clockModel.stop();
        return null;
    }
}
