package com.oreilly.demo.android.pa.uidemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.oreilly.demo.android.pa.uidemo.Model;
import com.oreilly.demo.android.pa.uidemo.MonstersGameController;

/**
 * Created by Lisa on 11/22/2015.
 */
public class GameView extends View {
    private Model model;

    private Boolean is_constants_constructor_called = false;
    int width, height;

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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!is_constants_constructor_called)
        {
            Constructor();
            is_constants_constructor_called = true;
        }
        //for now
       int m = 5, n = 5;
       model = new Model(m,n);
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



    }

}
