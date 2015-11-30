package com.oreilly.demo.android.pa.uidemo.controller;

import android.view.MotionEvent;
import android.view.View;

import com.oreilly.demo.android.pa.uidemo.model.Model;
import com.oreilly.demo.android.pa.uidemo.view.GameView;

/**
 * Created by Michael on 11/29/2015.
 */
public class DefaultOnTouchListener implements View.OnTouchListener {

    @Override
    public boolean onTouch(View v, MotionEvent event) {
       new Model(1,1).set_status(true); //the way to actually change the status in part of the
        //controller
       boolean result = ((GameView)(v)).onPress(event);
       new Model(1,1).set_status(false);
       return result;
    }
}
