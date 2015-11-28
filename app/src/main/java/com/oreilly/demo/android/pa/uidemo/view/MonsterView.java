package com.oreilly.demo.android.pa.uidemo.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import java.util.List;

import com.oreilly.demo.android.pa.uidemo.model.MonsterWithCoordinates;
import com.oreilly.demo.android.pa.uidemo.observer;


/**
 * Created by Lucas on 11/27/2015.
 */
public abstract class MonsterView extends View implements observer {

    private GameView gameView;

    public MonsterView(Context context) {
        super(context);
        setFocusableInTouchMode(true);
    }

    public MonsterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusableInTouchMode(true);
    }

    public MonsterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFocusableInTouchMode(true);
    }


    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    private long initial_time = System.currentTimeMillis();

    public abstract Pair<Float,Float> get_corner(int i, int j);

    public final void draw_monster(Canvas canvas, Paint paint, MonsterWithCoordinates monster, float scale_factor) {
        //TODO implement this function

        //temporarily
        paint.setColor(monster.getColor());
        Pair<Float,Float> corner = get_corner(monster.getX(),monster.getY());
        canvas.drawCircle(corner.first + scale_factor / 2, corner.second + scale_factor / 2, scale_factor / 2, paint);
    }


    public abstract Boolean is_expired();


    @Override
    protected void onDraw(Canvas canvas) {

        throw new RuntimeException("Should not be called");
    }

    @Override
    public Object update() {

        gameView.invalidate(); //invalidates the view
        return null;
    }

    public void draw(Canvas canvas, Paint paint, float scale_factor, List<MonsterWithCoordinates> monsterWithCoordinatesList)
    {

        if(monsterWithCoordinatesList == null) { throw new NullPointerException("monsters list may not be null!"); }
        for(MonsterWithCoordinates m : monsterWithCoordinatesList)
        {
            draw_monster(canvas,paint,m,scale_factor);
        }

    }
}
