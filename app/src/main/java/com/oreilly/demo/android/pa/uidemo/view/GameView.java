package com.oreilly.demo.android.pa.uidemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.oreilly.demo.android.pa.uidemo.GameDurationObserver;
import com.oreilly.demo.android.pa.uidemo.MonsterGame;
import com.oreilly.demo.android.pa.uidemo.UpdateMonstersListener;

import java.util.ArrayList;
import java.util.List;
//=======
import com.oreilly.demo.android.pa.uidemo.model.Model;
import com.oreilly.demo.android.pa.uidemo.model.clock.DefaultClockModel;
import com.oreilly.demo.android.pa.uidemo.observer;

/**
 * Created by Michael on 11/22/2015.
 */

//This class draws the board and displays the score. The drawing of the monsters is reserved
    //for the MonsterView
    //TODO is this the real controller? (i.e. should we rename?)
public class GameView extends View {
    protected int m = 5, n = 5; //for now
    protected Model model;

    private Boolean is_constants_constructor_called = false;
    protected int width, height;
    protected MonsterGame monsterGame;
    private List<observer> observerList;
    private Paint paint;
    private List<Float> [][] list_of_corners;
    protected MonsterView monsterView;

    public GameView(Context context) {
        super(context);
    }


    public GameView(final Context context, final AttributeSet attrs, final int defStyle) {
        this(context);
    }

    public GameView(final Context context, final AttributeSet attrs) {
        this(context);
    }

    public float [] get_location_pressed()
    {
        //FIXME
        return new float [2];
    }


    //since the constructors for a View are intended for use by Android, we need a separate
    //constructor to be called the first time onDraw gets called.
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

//TODO correct this. Supposed to return the coordinates where mouse is pressed
            @Override
            public int[] get_coordinates() {
                return find_indices(get_location_pressed());
            }
        });
        monsterGame.getClockModel().Register_Observer(new MonsterView(getContext()) {

            @Override
            public Boolean is_expired()
            {
                return monsterGame.getClockModel().get_is_expired();
            }

            @Override
            public List<Float> get_corner(int i, int j)
            {
                return list_of_corners[i][j];
            }

        });
        monsterGame.getClockModel().Register_Observer(new GameDurationObserver(monsterGame.getClockModel()));
        observerList = monsterGame.get_Observers();
        monsterView = (MonsterView)(observerList.get(1));
        monsterView.setGameView(this); //to force the correct onDraw method to be called
    }

    //Given coordinates of where mouse is pressed, returs the indices which correspond to the square
    //surrounding the index
    public int [] find_indices(float [] coordinates)
    {

        int [] results = new int [2];

        if (coordinates[0] < list_of_corners[0][0].get(0) || coordinates[1] < list_of_corners[0][0].get(1))
            throw new RuntimeException("Must press mouse within the board");
        for (int i=0; i<m; i++)
        {
            if (list_of_corners[i][0].get(0) > coordinates[0])
            {
                results[0] = i;
                break;
            }
            else if (i == m - 1)
                throw new RuntimeException("Must press mouse within the board");
        }
        for (int j=0; j<n; j++)
        {
            if (list_of_corners[0][j].get(1) > coordinates[1])
            {
                results[1] = j;
                break;
            }
            else if (j == n - 1)
                throw new RuntimeException("Must press mouse within the board");
        }
        return results;
    }

    //returns a list of all of the endpoints of the squares in the board.
    public List<Float> [][] init_corners()
    {

        List<Float> [][] result = new List [m+1][n+1];
        for (int i=0; i <= m; i++) {
            Float x;
            if (i == 0)
                x = width * 0.1f/m;
            else if (i < m)
                x = width * 1f * i / m;
            else
                x = (float)(width * (m - 0.1) / m);

            for (int j = 0; j <= n; j++) {
                Float y;
                if (j == 0)
                    y = height * 0.1f/ n;
                else if (j < n)
                    y = height * 1f * j / n;
                else
                    y = (float)(height * (n - 0.1) / n);

                result[i][j] = new ArrayList();
                result[i][j].add(x);
                result[i][j].add(y);
            }
        }
        return result;
    }

    //returns the length of the bounding box around any of the monsters.
    //TODO do we really want the monster to not fill up the box (i.e. using 0.9f)
    public float calculate_scale_factor()
    {
        return Math.min(0.9f * width / m, 0.9f * height / n);
    }

    //Displays the score
    public void set_textview(int value)
    {
        TextView view = (TextView)((DefaultClockModel)(monsterGame.getClockModel())).getScore_view();
        view.setText("Current Score: " + new Integer(value).toString());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!is_constants_constructor_called)
        {
            Constructor();
       }
       set_textview(monsterGame.getCurr_score());

       paint.setColor(Color.RED);
       paint.setStyle(Paint.Style.FILL_AND_STROKE);
       //When drawing the board, make sure to stay away from the edges, so that the user can
        //see the border
       for (int i=0; i<=m; i++)
       {
           float x = list_of_corners[i][0].get(0);
           canvas.drawLine(x,height * 0.1f / n,x,(float)(height * (n - 0.1) / n),paint);
       }
       for (int j=0; j<=n; j++)
       {
           float y = list_of_corners[0][j].get(1);
           canvas.drawLine(width * 0.1f / m,y,(float)(width * (m - 0.1) / m),y,paint);
       }
        monsterView.draw(canvas, paint, calculate_scale_factor(), model.monsterWithCoordinates);

        if (!is_constants_constructor_called)
        {
            is_constants_constructor_called = true; //don't start a new game during subsequent draws
            monsterGame.play_game();
        }


    }



    public void onPress()
    {
        //at some point

       model.set_status(true);
       monsterGame.getClockModel().NotifyAll();

    }
}
