package com.oreilly.demo.android.pa.uidemo.model.clock;


import com.oreilly.demo.android.pa.uidemo.MonstersGameController;

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

    // TODO make accurate by keeping track of partial seconds when canceled etc.

    private Timer timer;
    private int time_to_wait;
    private boolean is_expired = false;

    public DefaultClockModel(int time)
    {
        time_to_wait = time;
    }


    @Override
    public void start() {

        timer = new Timer();
        MonstersGameController controller = this;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (this) {

                    runOnUiThread(() -> {
                        time_to_wait -= 1000;
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
        }, /*initial delay before first scheduled event*/ 0, /*periodic delay*/ 1000);

    }

    //TODO cite http://stackoverflow.com/questions/17471808/android-add-id-to-a-layout and http://stackoverflow.com/questions/4018772/calling-setcontentview-multiple-times

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