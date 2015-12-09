/**
 * Created by Michael on 11/28/2015.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;

import com.oreilly.demo.android.pa.uidemo.controller.Board_Calculations;
import com.oreilly.demo.android.pa.uidemo.controller.GameDurationObserver;
import com.oreilly.demo.android.pa.uidemo.controller.MonsterGame;
import com.oreilly.demo.android.pa.uidemo.controller.UpdateMonstersListener;
import com.oreilly.demo.android.pa.uidemo.model.Model;
import com.oreilly.demo.android.pa.uidemo.model.MonsterWithCoordinates;
import com.oreilly.demo.android.pa.uidemo.observer;
import com.oreilly.demo.android.pa.uidemo.view.GameView;
import com.oreilly.demo.android.pa.uidemo.view.MonsterView;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Random;

class GameViewTest extends Board_Calculations
{

    public GameViewTest(Context context) {
        super(new GameView(context));
        gameView.m = 5;
        gameView.n = 5;
        gameView.numberOfMonsters = 0;
    }

    public Model curr_model;
    public MonsterGame curr_monster_game;
    private float I,J;

    @Override
    public void Constructor()
    {
        super.Constructor();
        //gameView.model.monsterWithCoordinates.clear();
        Random random = new Random();
        //For this test, there will initially be one monster in every square
        for (int i=0; i<gameView.m; i++)
            for (int j=0; j<gameView.n; j++) {
                int color = (random.nextInt(2) == 1) ? Color.GREEN : Color.YELLOW;
                gameView.model.monsterWithCoordinates.add(new MonsterWithCoordinates(i,j,color));
            }
    }

    public void TestConstructor()
    {
        curr_model = gameView.model;
        curr_monster_game = gameView.monsterGame;
    }


    public int getM()
    {
        return gameView.m;
    }

    public int getN()
    {
        return gameView.n;
    }

    public GameView getGameView()
    {
        return gameView;
    }

    //It is necessary to overwrite the width and height after the call to getWidth and getHeight.
    //We cannot have a legitimate test with width = height = 0.
    @Override
    public List<Float> [][] init_corners()
    {
        gameView.width = 100;
        gameView.height = 200;
        return super.init_corners();
    }

    public int [] super_find_indices(float [] coordinates)
    {
        return super.find_indices(coordinates);
    }

    public void set_target_coords(float I, float J)
    {
        this.I = I;
        this.J = J;

    }

    @Override
    public int [] find_indices(float [] coordinates)
    {
        coordinates[0] = I;
        coordinates[1] = J;
        return super.find_indices(coordinates);
    }


}

public class TestOnPress {



    public GameViewTest gameViewTest = new GameViewTest(mock(Context.class));

    public void initialize()
    {
        gameViewTest.Constructor();
        gameViewTest.TestConstructor();

        //we will explicitly set the first monster's color to be green, and the second yellow
        gameViewTest.curr_model.monsterWithCoordinates.get(0).setColor(Color.GREEN);
        gameViewTest.curr_model.monsterWithCoordinates.get(1).setColor(Color.YELLOW);
        gameViewTest.curr_monster_game.play_game();
    }

    @Test
    public void Test_Coordinate_Transformations()
    {
        initialize();
        int m = gameViewTest.getM();
        int n = gameViewTest.getN();

        for (int i=0; i<m; i++)
            for (int j=0; j<n; j++)
            {
                float test_coords [] = {100 * (i + 0.5f) / m, 200 * (j + 0.5f) / n};

                int [] indices = gameViewTest.super_find_indices(test_coords);
                assertEquals(indices[0], i);
                assertEquals(indices[1], j);
            }
    }

    @Test
    public void Test()
    {
        initialize();
        gameViewTest.set_target_coords(0f, 0f);
        //assert that the correct observers are registered
        List<observer> Observers = gameViewTest.curr_monster_game.getObservers();
        assertTrue(((Observers.size() == 3 && Observers.get(0) instanceof UpdateMonstersListener &&
                Observers.get(1) instanceof MonsterView &&
                Observers.get(2) instanceof GameDurationObserver)));

        gameViewTest.getGameView().onPress(mock(MotionEvent.class)); //press at the origin
        List<MonsterWithCoordinates> list_at_loc = (gameViewTest.curr_model.Find_Monsters_on_Square(0,0));
        assertEquals(list_at_loc.size(), 1);
        assertEquals(gameViewTest.curr_monster_game.getCurr_score(), 0); //eliminated no monsters yet

        //now press at (0,1)
        gameViewTest.set_target_coords(0f,40f);
        gameViewTest.getGameView().onPress(mock(MotionEvent.class));
        List<MonsterWithCoordinates> list_at_loc2 = (gameViewTest.curr_model.Find_Monsters_on_Square(0,1));
        assertEquals(list_at_loc2.size(),0);
        assertEquals(gameViewTest.curr_monster_game.getCurr_score(), 1); //eliminated exactly one monster
    }
}
