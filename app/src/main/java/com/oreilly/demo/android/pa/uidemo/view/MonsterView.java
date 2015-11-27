package com.oreilly.demo.android.pa.uidemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.oreilly.demo.android.pa.uidemo.Observer;
import com.oreilly.demo.android.pa.uidemo.model.Monster;

import java.util.List;

/**
 * Created by Lucas on 11/27/2015.
 */
public class MonsterView extends View implements Observer {

    List<Monster> monsterList;

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

    public void setMonsters(List<Monster> monsterList)
    {
        this.monsterList = monsterList;
    }

    @Override protected void onDraw(Canvas canvas)
    {
        if(monsterList == null) { return; }
        for(Monster m : monsterList)
        {
            canvas.drawBitmap(/*bitmap file*/, m.getX(), m.getY());
        }
    }

    @Override public Object update()
    {
       this.onDraw()
        return null;
    }
}
