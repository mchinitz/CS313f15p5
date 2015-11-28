package com.oreilly.demo.android.pa.uidemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
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
    private Paint paint;
    private Pair<Float,Float> [][] list_of_corners;
    private MonsterView monsterView;

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
        monsterGame = new MonsterGame(this);
        paint = new Paint();
        list_of_corners = init_corners();

        monsterGame.getClockModel().Register_Observer(new UpdateMonstersListener(model) {

//TODO correct this
            @Override
            public int[] get_coordinates() {
                return new int[2];
            }
        });
        monsterGame.getClockModel().Register_Observer(new MonsterView(getContext()) {

            @Override
            public Boolean is_expired()
            {
                return monsterGame.getClockModel().get_is_expired();
            }

            @Override
            public Pair<Float,Float> get_corner(int i, int j)
            {
                return list_of_corners[i][j];
            }

        });
        monsterGame.getClockModel().Register_Observer(new GameDurationObserver(monsterGame.getClockModel()));
        observerList = monsterGame.get_Observers();
        monsterView = (MonsterView)(observerList.get(1));
        monsterView.setGameView(this); //to force the correct onDraw method to be called
    }


    public Pair<Float,Float> [][] init_corners()
    {
        Pair<Float,Float> [][] result = new Pair [m+1][n+1];
        for (int i=0; i <= m; i++) {
            float x;
            if (i == 0)
                x = width * 0.1f/m;
            else if (i < m)
                x = width * 1f * i / m;
            else
                x = (float)(width * (m - 0.1) / m);

            for (int j = 0; j <= n; j++) {
                float y;
                if (j == 0)
                    y = height * 0.1f/ n;
                else if (j < n)
                    y = height * 1f * j / n;
                else
                    y = (float)(height * (n - 0.1) / n);
                result[i][j] = new Pair (x,y);
            }
        }
        return result;
    }


    public float calculate_scale_factor()
    {
        return Math.min(0.9f * width / m, 0.9f * height / n);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        if (!is_constants_constructor_called)
        {
            Constructor();
        }
       paint.setColor(Color.RED);
       paint.setStyle(Paint.Style.FILL_AND_STROKE);
       //When drawing the board, make sure to stay away from the edges, so that the user can
        //see the border
       for (int i=0; i<=m; i++)
       {
           float x = list_of_corners[i][0].first;
           canvas.drawLine(x,height * 0.1f / n,x,(float)(height * (n - 0.1) / n),paint);
       }
       for (int j=0; j<=n; j++)
       {
           float y = list_of_corners[0][j].second;
           canvas.drawLine(width * 0.1f / m,y,(float)(width * (m - 0.1) / m),y,paint);
       }
        monsterView.draw(canvas,paint,calculate_scale_factor(),model.monsterWithCoordinates);

        if (!is_constants_constructor_called)
        {
            is_constants_constructor_called = true; //don't start a new game during subsequent draws
            monsterGame.play_game();
        }


    }
}
