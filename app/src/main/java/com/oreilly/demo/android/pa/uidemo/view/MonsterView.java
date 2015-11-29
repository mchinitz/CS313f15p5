package com.oreilly.demo.android.pa.uidemo.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import java.util.List;

import com.oreilly.demo.android.pa.uidemo.model.MonsterWithCoordinates;
import com.oreilly.demo.android.pa.uidemo.observer;


/**
 * Created by Lucas on 11/27/2015.
 */


//Todo determine whether this class should extend View. It would be nice to get rid of the constructor 'preamble'.
//Draws monsters
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

    //Gets the coordinates of the i,j 'th corner within the canvas.
    public abstract List<Float> get_corner(int i, int j);

    //Draws a single monster to the canvas
    public final void draw_monster(Canvas canvas, Paint paint, MonsterWithCoordinates monster, float scale_factor) {
        //TODO implement this function

        //temporarily
        paint.setColor(monster.getColor());
        List<Float> corner = get_corner(monster.getX(),monster.getY());
        canvas.drawCircle(corner.get(0) + scale_factor / 2, corner.get(1) + scale_factor / 2, scale_factor / 2, paint);
    }


    public abstract Boolean is_expired();


    @Override
    protected void onDraw(Canvas canvas) {

        throw new RuntimeException("Should not be called");
    }

    //the MonsterView responds to all events by instructing Android to redraw the canvas.
    //The reason why update itself doesn't draw the monsters is that we must be provided with
    //the canvas directly from GameView.onDraw, for we don't know which canvas we will get.
    @Override
    public Object update() {

        gameView.invalidate(); //invalidates the view
        return null;
    }

    //Draws the monsters to the canvas
    public void draw(Canvas canvas, Paint paint, float scale_factor, List<MonsterWithCoordinates> monsterWithCoordinatesList)
    {

        if(monsterWithCoordinatesList == null) { throw new NullPointerException("monsters list may not be null!"); }
        for(MonsterWithCoordinates m : monsterWithCoordinatesList)
        {
            draw_monster(canvas,paint,m,scale_factor);
        }

    }
}
