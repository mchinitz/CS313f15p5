package com.oreilly.demo.android.pa.uidemo.controller;


import com.oreilly.demo.android.pa.uidemo.controller.MonstersGameController;
import com.oreilly.demo.android.pa.uidemo.model.Constants;
import com.oreilly.demo.android.pa.uidemo.model.clock.ClockModel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * An implementation of the internal clock.
 *
 * @author laufer
 */

//Purpose of this is to use command pattern. Encapsulate the command run using the object

//Entire point of extending MonstersGameController is to be able to call runOnUiThread, and
//to reset content view
public abstract class DefaultClockModel extends MonstersGameController implements ClockModel {

    private Timer timer;
    private int time_to_wait;
    private boolean is_expired = false;

    public DefaultClockModel(int time)
    {
        time_to_wait = time;
    }

    @Override
    public float get_time_to_wait()
    {
        return time_to_wait / 1000.0f;
    }

    @Override
    public void start() {

        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (this) {

                    runOnUiThread(() -> {
                        time_to_wait -= Constants.amount_time_between_movements;
                        if (time_to_wait == 0) {
                            is_expired = true;
                        }
                        // fire event
                        NotifyAll();
                        if (get_is_expired())
                            return;
                    });
                }
            }
        }, /*initial delay before first scheduled event*/ 0, /*periodic delay*/ Constants.amount_time_between_movements);

    }
    @Override
    public boolean get_is_expired()
    {
        return is_expired;
    }

    @Override
    public void stop() {

        timer.cancel();
    }
}