package com.oreilly.demo.android.pa.uidemo.controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.oreilly.demo.android.pa.uidemo.R;
import com.oreilly.demo.android.pa.uidemo.view.GameView;


/**
 * Created by Michael on 11/22/2015.
 */

//I'm not really sure this is a "controller." It acts more like a scoreView class rather than a controller class.
public class MonstersGameController extends Activity {

    private static View score_view;

    public void init_score_view()
    {
        score_view = findViewById(R.id.score);
        if (score_view == null)
            throw new RuntimeException();
    }

    public View getScore_view()
    {
        return score_view;
    }

    @Override
    public void onCreate(final Bundle SavedInstanceState)
    {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activitymain);
        init_score_view();

        GameView view = (GameView) findViewById(R.id.canvas);
        if (view == null)
            throw new RuntimeException();
        view.setOnTouchListener(new DefaultOnTouchListener());
    }
}
