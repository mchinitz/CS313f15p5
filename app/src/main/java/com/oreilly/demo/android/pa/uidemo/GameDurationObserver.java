package com.oreilly.demo.android.pa.uidemo;

import com.oreilly.demo.android.pa.uidemo.model.clock.ClockModel;

/**
 * Created by Michael on 11/27/2015.
 */

//Responds to the time expiring by stopping the timer
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
