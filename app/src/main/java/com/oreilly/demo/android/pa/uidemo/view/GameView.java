package com.oreilly.demo.android.pa.uidemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.oreilly.demo.android.pa.uidemo.GameDurationObserver;
import com.oreilly.demo.android.pa.uidemo.MonsterGame;
import com.oreilly.demo.android.pa.uidemo.UpdateMonstersListener;

import java.util.ArrayList;
import java.util.List;
//=======
import com.oreilly.demo.android.pa.uidemo.model.Model;
import com.oreilly.demo.android.pa.uidemo.observer;

/**
 * Created by Michael on 11/22/2015.
 */
public class GameView extends View {
    private int m = 5, n = 5; //for now
    private Model model;

    private Boolean is_constants_constructor_called = false;
    int width, height;
    private MonsterGame monsterGame;
    private List<observer> observerList;
    private Canvas canvas;

    public GameView(Context context) {
        super(context);
    }


    public GameView(final Context context, final AttributeSet attrs, final int defStyle) {
        this(context);
    }

    public GameView(final Context context, final AttributeSet attrs) {
        this(context);
    }



    public void Constructor()
    {
        width = getWidth();
        height = getHeight();
        observerList = new ArrayList<>();
        model = new Model(m,n);
        monsterGame = new MonsterGame(observerList, this);

        observerList.add(new UpdateMonstersListener(model, this) {
            @Override
            public void draw_monster(int x, int y) {

            }

            @Override
            public int[] get_coordinates() {
                return new int[2];
            }
        });
        observerList.add(new GameDurationObserver(monsterGame.getClockModel()));

    }

    public Canvas getCanvas()
    {
        return canvas;
    }


    //The entire point of this function is so we can call onDraw() publically
    @SuppressLint("WrongCall")
    public void OnDraw(Canvas canvas)
    {
        onDraw(canvas);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!is_constants_constructor_called)
        {
            Constructor();
        }
        this.canvas = canvas;

       Paint paint = new Paint();
       paint.setColor(Color.RED);
       paint.setStyle(Paint.Style.STROKE);
       //When drawing the board, make sure to stay away from the edges, so that the user can
        //see the border
       for (int i=0; i<=m; i++)
       {
           float x;
           if (i == 0)
               x = width * 0.1f/m;
           else if (i < m)
               x = width * 1f * i / m;
           else
               x = (float)(width * (m - 0.1) / m);
           canvas.drawLine(x,height * 0.1f / n,x,(float)(height * (n - 0.1) / n),paint);
       }
       for (int j=0; j<=n; j++)
       {
           float y;
           if (j == 0)
               y = height * 0.1f/ n;
           else if (j < n)
               y = height * 1f * j / n;
           else
                y = (float)(height * (n - 0.1) / n);
           canvas.drawLine(width * 0.1f / m,y,(float)(width * (m - 0.1) / m),y,paint);
       }

        if (!is_constants_constructor_called)
        {
            is_constants_constructor_called = true; //don't start a new game during subsequent draws
            monsterGame.play_game();
        }


    }

}
