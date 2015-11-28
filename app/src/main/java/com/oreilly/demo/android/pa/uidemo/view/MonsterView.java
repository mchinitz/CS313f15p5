package com.oreilly.demo.android.pa.uidemo.view;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import java.util.List;

import com.oreilly.demo.android.pa.uidemo.model.MonsterWithCoordinates;
import com.oreilly.demo.android.pa.uidemo.observer;


/**
 * Created by Lucas on 11/27/2015.
 */
public class MonsterView extends View implements observer {

    List<MonsterWithCoordinates> monsterWithCoordinatesList;

    public MonsterView(Context context) {
        super(context);
        setFocusableInTouchMode(true);
    }

    public MonsterView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setFocusableInTouchMode(true);
    }

    public MonsterView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        setFocusableInTouchMode(true);
    }

    public void setMonsters(List<MonsterWithCoordinates> monsterWithCoordinatesList)
    {
        this.monsterWithCoordinatesList = monsterWithCoordinatesList;
    }


    @Override protected void onDraw(Canvas canvas)
    {
        if (true)
            throw new RuntimeException("canvas.drawBitmap parameters not set up yet");
        if(monsterWithCoordinatesList == null) { return; }
        for(MonsterWithCoordinates m : monsterWithCoordinatesList)
        {
            //canvas.drawBitmap(/*bitmap file*/, m.getX(), m.getY());
        }
    }

    @Override
    public Object update() {
        throw new RuntimeException("not implemented");
    }


}
