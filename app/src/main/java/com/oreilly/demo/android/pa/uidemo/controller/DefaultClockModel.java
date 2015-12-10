package com.oreilly.demo.android.pa.uidemo.controller;


import com.oreilly.demo.android.pa.uidemo.controller.MonstersGameController;
import com.oreilly.demo.android.pa.uidemo.model.Constants;
import com.oreilly.demo.android.pa.uidemo.model.clock.ClockModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Queue;
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
    private Queue<Method> queue;

    public DefaultClockModel(int time)
    {
        time_to_wait = time;
        queue = new ArrayDeque<Method>();

    }

    public int get_queue_size()
    {
        return queue.size();
    }

    @Override
    public float get_time_to_wait()
    {
        return time_to_wait / 1000.0f;
    }

    //The reason for this is it is the way to let Roboelectric execute the method that Android
    //would have while running the app.
    public void empty_queue()
    {
        while (!queue.isEmpty())
        {
            try {
                queue.peek().invoke(this);
            } catch (Exception e)
            {
                e.printStackTrace();
                throw new RuntimeException();
            }
            queue.remove();
        }
    }

    public boolean method_to_fire()
    {
        time_to_wait -= Constants.amount_time_between_movements;
        if (time_to_wait == 0) {
            is_expired = true;
        }
        // fire event
        NotifyAll();
        if (get_is_expired())
            return true;
        else
            return false;
    }

    @Override
    public void start() {

        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (this) {
                    try {
                        queue.add(DefaultClockModel.class.getMethod("method_to_fire"));
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException();
                    }

                    runOnUiThread (() -> {
                        if (method_to_fire())
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