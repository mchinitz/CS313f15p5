package com.oreilly.demo.android.pa.uidemo.model.clock;

/**
 * The active model of the internal clock that periodically emits tick events.
 *
 * @author laufer
 */
public interface ClockModel extends Subject {
    void start();
    void stop();
    boolean get_is_expired();
    public int get_time_to_wait();
}
