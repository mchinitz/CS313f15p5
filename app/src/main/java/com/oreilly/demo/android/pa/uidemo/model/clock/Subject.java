package com.oreilly.demo.android.pa.uidemo.model.clock;

import com.oreilly.demo.android.pa.uidemo.observer;

import java.util.List;

/**
 * Created by Lisa on 11/27/2015.
 */

//TODO Michael, what is the purpose of this interface?
public interface Subject {

    public void NotifyAll();
    public void Register_Observer(observer o);
}
