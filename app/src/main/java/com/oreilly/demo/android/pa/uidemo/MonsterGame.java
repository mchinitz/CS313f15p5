package com.oreilly.demo.android.pa.uidemo;

import com.oreilly.demo.android.pa.uidemo.model.clock.ClockModel;
import com.oreilly.demo.android.pa.uidemo.model.clock.DefaultClockModel;
import com.oreilly.demo.android.pa.uidemo.view.GameView;

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
    public MonsterGame(List<observer> Observers, GameView gameView)
    {
       this.Observers = Observers;
       this.gameView = gameView;
       clockModel = new DefaultClockModel(gameplay_time * 1000) {

            @Override
            public void Register_Observer(observer o)
            {
                Observers.add(o);
            }

            @Override
            public void NotifyAll() {
                for (observer o : Observers)
                    o.update();
                gameView.OnDraw(gameView.getCanvas()); //calls onDraw()
            }
        };
    }

    public ClockModel getClockModel()
    {
        return clockModel;
    }



    public void play_game()
    {

        //register the appropriate observers
        //TODO remove
        if (!((Observers.size() == 2 && Observers.get(0) instanceof UpdateMonstersListener && Observers.get(1) instanceof GameDurationObserver)))
            throw new RuntimeException();
        for (int i=0; i<2; i++)
            clockModel.Register_Observer(Observers.get(i));
        clockModel.start();
    }



}
