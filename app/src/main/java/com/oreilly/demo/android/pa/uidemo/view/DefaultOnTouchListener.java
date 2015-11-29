package com.oreilly.demo.android.pa.uidemo.view;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Michael on 11/29/2015.
 */
public class DefaultOnTouchListener implements View.OnTouchListener {

    @Override
    public boolean onTouch(View v, MotionEvent event) {

       return ((GameView)(v)).onPress(event);
    }
}
