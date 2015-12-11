package com.oreilly.demo.android.pa.uidemo.controller;

import android.graphics.Paint;

import com.oreilly.demo.android.pa.uidemo.model.Model;
import com.oreilly.demo.android.pa.uidemo.view.GameView;
import com.oreilly.demo.android.pa.uidemo.view.MonsterView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 11/29/2015.
 */

//This class performs the calculations to build and maintain the board. The results
    //are sent directly to the view.
public class Board_Calculations {


    protected GameView gameView;

    public Board_Calculations(GameView gameView)
    {
        this.gameView = gameView;
    }

    //since the constructors for a View are intended for use by Android, we need a separate
    //constructor to be called the first time onDraw gets called.
    public void Constructor()
    {

        gameView.loc_pressed = new float [2];
        gameView.width = gameView.getWidth();
        gameView.height = gameView.getHeight();
        gameView.observerList = new ArrayList<>();
        gameView.model = new Model(gameView.m,gameView.n, gameView.numberOfMonsters);
        gameView.monsterGame = new MonsterGame();
        gameView.paint = new Paint();
        gameView.list_of_corners = init_corners();

        gameView.monsterGame.getClockModel().Register_Observer(new UpdateMonstersListener(gameView.model, gameView.monsterGame) {

            @Override
            public int[] get_coordinates() {
                return find_indices(gameView.loc_pressed);
            }
        });
        gameView.monsterGame.getClockModel().Register_Observer(new MonsterView() {

            @Override
            public Boolean is_expired()
            {
                return gameView.monsterGame.getClockModel().get_is_expired();
            }

            @Override
            public List<Float> get_corner(int i, int j)
            {
                return gameView.list_of_corners[i][j];
            }

        });
        gameView.monsterGame.getClockModel().Register_Observer(new GameDurationObserver(gameView.monsterGame.getClockModel()));
        gameView.observerList = gameView.monsterGame.get_Observers();
        gameView.monsterView = (MonsterView)(gameView.observerList.get(1));
        gameView.monsterView.setGameView(gameView); //to force the correct onDraw method to be called

    }

    //Given coordinates of where mouse is pressed, returns the indices which correspond to the square
    //surrounding the index. It would be difficult to accomplish the i and j loops in a function because
    //of the use of 2-D indexing
    public int [] find_indices(float [] coordinates)
    {

        int [] results = new int [2];
        if (coordinates[0] < 0 || coordinates[0] >= gameView.width || coordinates[1] < 0 || coordinates[1] >= gameView.height)
            return null;
        if (coordinates[0] < gameView.list_of_corners[0][0].get(0))
            results[0] = 0;
        else
            for (int i = 1; i <= gameView.m; i++) {
                if (gameView.list_of_corners[i][0].get(0) > coordinates[0] || i==gameView.m) {
                    results[0] = i - 1;
                    break;
                }
            }
        if (coordinates[1] < gameView.list_of_corners[0][0].get(1))
            results[1] = 0;
        else
            for (int j=1; j<=gameView.n; j++)
            {
                if (gameView.list_of_corners[0][j].get(1) > coordinates[1] || j==gameView.n)
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
        List<Float> [][] result = new List [gameView.m+1][gameView.n+1];
        for (int i=0; i <= gameView.m; i++) {
            Float x = get_coord(i,gameView.m,gameView.width);
            for (int j = 0; j <= gameView.n; j++) {
                Float y = get_coord(j,gameView.n,gameView.height);
                result[i][j] = new ArrayList();
                result[i][j].add(x);
                result[i][j].add(y);
            }
        }
        return result;
    }

    //returns the length of the bounding box around any of the monsters.
    public float calculate_scale_factor()
    {
        return Math.min(0.9f * gameView.width / gameView.m, 0.9f * gameView.height / gameView.n);
    }




}
