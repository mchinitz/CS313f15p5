package com.oreilly.demo.android.pa.uidemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.LinearLayout;


/**
 * Created by Michael on 11/22/2015.
 */
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
    }


}
