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
       boolean result = ((GameView)(v)).onPress(event);
       return result;
    }
}
