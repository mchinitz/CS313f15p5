package com.oreilly.demo.android.pa.uidemo;

import com.oreilly.demo.android.pa.uidemo.model.clock.ClockModel;
import com.oreilly.demo.android.pa.uidemo.model.clock.DefaultClockModel;
import com.oreilly.demo.android.pa.uidemo.view.GameView;
import com.oreilly.demo.android.pa.uidemo.view.MonsterView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Lisa on 11/27/2015.
 */
public class MonsterGame {

    private int gameplay_time = 5; //for now, and in seconds
    private ClockModel clockModel;
    private List<observer> Observers;
    private GameView gameView;
    public MonsterGame(GameView gameView)
    {
       Observers = new ArrayList<> ();
       this.gameView = gameView;
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
                if (clockModel.get_is_expired())
                    throw new RuntimeException("not implemented");
            }
        };
    }

    public ClockModel getClockModel()
    {
        return clockModel;
    }

    public List<observer> get_Observers()
    {
        return Observers;
    }

//There are three observers. The first updates the positions of the monsters. The second redraws the monsters.
    //The third keeps track of the remaining time for the game.
    public void play_game()
    {

        //register the appropriate observers
        //TODO move this to a unit test
        if (!((Observers.size() == 3 && Observers.get(0) instanceof UpdateMonstersListener &&
                Observers.get(1) instanceof MonsterView &&
                Observers.get(2) instanceof GameDurationObserver)))
            throw new RuntimeException();


        clockModel.start();
    }



}
