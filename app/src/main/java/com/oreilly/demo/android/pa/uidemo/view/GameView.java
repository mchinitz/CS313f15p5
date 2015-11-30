package com.oreilly.demo.android.pa.uidemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.oreilly.demo.android.pa.uidemo.Constants;
import com.oreilly.demo.android.pa.uidemo.GameDurationObserver;
import com.oreilly.demo.android.pa.uidemo.MonsterGame;
import com.oreilly.demo.android.pa.uidemo.UpdateMonstersListener;

import java.util.ArrayList;
import java.util.List;
import com.oreilly.demo.android.pa.uidemo.model.Model;
import com.oreilly.demo.android.pa.uidemo.model.clock.DefaultClockModel;
import com.oreilly.demo.android.pa.uidemo.observer;

/**
 * Created by Michael on 11/22/2015.
 */

//This class draws the board and displays the score. The drawing of the monsters is reserved
    //for the MonsterView
public class GameView extends View {
    protected int m = Constants.m, n = Constants.n;
    protected Model model;

    private Boolean is_constants_constructor_called = false;
    protected int width, height;
    protected MonsterGame monsterGame;
    private List<observer> observerList;
    private Paint paint;
    private List<Float> [][] list_of_corners;
    protected MonsterView monsterView;
    private float loc_pressed [];

    public GameView(Context context) {
        super(context);
    }

    public GameView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }


    //since the constructors for a View are intended for use by Android, we need a separate
    //constructor to be called the first time onDraw gets called.
    public void Constructor()
    {
        loc_pressed = new float [2];
        width = getWidth();
        height = getHeight();
        observerList = new ArrayList<>();
        model = new Model(m,n);
        monsterGame = new MonsterGame(this);
        paint = new Paint();
        list_of_corners = init_corners();

        monsterGame.getClockModel().Register_Observer(new UpdateMonstersListener(model, monsterGame) {

            @Override
            public int[] get_coordinates() {
                return find_indices(loc_pressed);
            }
        });
        monsterGame.getClockModel().Register_Observer(new MonsterView() {

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

    //Given coordinates of where mouse is pressed, returns the indices which correspond to the square
    //surrounding the index. It would be difficult to accomplish the i and j loops in a function because
    //of the use of 2-D indexing
    public int [] find_indices(float [] coordinates)
    {

        int [] results = new int [2];
        if (coordinates[0] < 0 || coordinates[0] >= width || coordinates[1] < 0 || coordinates[1] >= height)
            return null;
        if (coordinates[0] < list_of_corners[0][0].get(0))
            results[0] = 0;
        else
            for (int i = 1; i <= m; i++) {
                if (list_of_corners[i][0].get(0) > coordinates[0] || i==m) {
                    results[0] = i - 1;
                    break;
                }
            }
        if (coordinates[1] < list_of_corners[0][0].get(1))
            results[1] = 0;
        else
            for (int j=1; j<=n; j++)
            {
                if (list_of_corners[0][j].get(1) > coordinates[1] || j==n)
                {
                    results[1] = j-1;
                    break;
                }

            }
        return results;
    }


    //these two functions together return a list of all of the endpoints of the squares in the board.
    //the first is an auxillary function whose sole purpose is for the second
    public float get_coord(int index, int max_index, float max_in_direction)
    {
        if (index == 0)
            return max_in_direction * 0.1f / max_index;
        else if (index < max_index)
            return max_in_direction * 1f * index / max_index;
        else
            return max_in_direction * (max_index - 0.1f) / max_index;
    }

    public List<Float> [][] init_corners()
    {
        List<Float> [][] result = new List [m+1][n+1];
        for (int i=0; i <= m; i++) {
            Float x = get_coord(i,m,width);
            for (int j = 0; j <= n; j++) {
                Float y = get_coord(j,n,height);
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


    //--------------------------------------------------//

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

    public boolean onPress(MotionEvent event)
    {
        if (monsterView.is_expired())
            return false; //prevents user from cheating by eliminating monsters after the end of the game
       int action = event.getAction() & event.ACTION_MASK;

        //at some point
    if (action != event.ACTION_DOWN && action != event.ACTION_POINTER_DOWN)
           return false; //not a mouse-pressing event

       loc_pressed[0] = event.getX();
       loc_pressed[1] = event.getY();

       //Log.d("Mouse pressed at ", "" + width + " " + height + " " + loc_pressed[0] + " " + loc_pressed[1]);

       model.set_status(true);
       monsterGame.getClockModel().NotifyAll();
       return true;
    }
}