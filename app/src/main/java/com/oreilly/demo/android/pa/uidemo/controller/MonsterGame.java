package com.oreilly.demo.android.pa.uidemo.controller;

import com.oreilly.demo.android.pa.uidemo.model.Constants;
import com.oreilly.demo.android.pa.uidemo.model.clock.ClockModel;
import com.oreilly.demo.android.pa.uidemo.observer;
import com.oreilly.demo.android.pa.uidemo.view.GameView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Michael on 11/27/2015.
 */

//This class contains the rules of the game, except for the movement of the monsters.
    //It keeps track of time. Every second, it notifies the observers that a second has elapsed
    //(so monsters should be moved). Also, it responds to the user clicking on a square
    //(not yet implemented).
public class MonsterGame {

    private int gameplay_time = Constants.gameplay_time;
    private ClockModel clockModel;
    private List<observer> Observers;
    private int curr_score = 0;
    public MonsterGame()
    {
       Observers = new ArrayList<> ();
        //use gameplay_time + 1 since actually an event occurs immediately
       clockModel = new DefaultClockModel((gameplay_time +1) * 1000) {

            @Override
            public void Register_Observer(observer o)
            {
                Observers.add(o);
            }

            @Override
            public void NotifyAll() {
                for (observer o : Observers)
                    o.update();
            }
        };
    }

    public int getCurr_score()
    {
        return curr_score;
    }

    public List<observer> getObservers()
    {
        return Observers;
    }

    public ClockModel getClockModel()
    {
        return clockModel;
    }

    public List<observer> get_Observers()
    {
        return Observers;
    }

    public void update_score(int num_points_scored)
    {
        curr_score += num_points_scored;
    }

//There are three observers. The first updates the positions of the monsters. The second redraws the monsters.
    //The third keeps track of the remaining time for the game.
    public void play_game()
    {
        clockModel.start();
    }



}
