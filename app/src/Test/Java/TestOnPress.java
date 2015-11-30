/**
 * Created by Michael on 11/28/2015.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;

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

class GameViewTest extends GameView
{

    public GameViewTest(Context context) {
        super(context);
        m = 5;
        n = 5;
    }

    public Model curr_model;
    public MonsterGame curr_monster_game;

    @Override
    public void Constructor()
    {
        super.Constructor();
        model.monsterWithCoordinates.clear();
        Random random = new Random();
        //For this test, there will initially be one monster per square.
        for (int i=0; i<m; i++)
            for (int j=0; j<n; j++) {
                int color = (random.nextInt(2) == 1) ? Color.GREEN : Color.YELLOW;
                model.monsterWithCoordinates.add(new MonsterWithCoordinates(i,j,color));
            }
    }

    public void TestConstructor()
    {
        curr_model = model;
        curr_monster_game = monsterGame;
    }

    public int getM()
    {
        return m;
    }

    public int getN()
    {
        return n;
    }

    //It is neccessary to overwrite the width and height after the call to getWidth and getHeight.
    //We cannot have a legitimate test with width = height = 0.
    @Override
    public List<Float> [][] init_corners()
    {
        width = 100;
        height = 200;
        return super.init_corners();
    }

    public int [] super_find_indices(float [] coordinates)
    {
        return super.find_indices(coordinates);
    }

    @Override
    public int [] find_indices(float [] coordinates)
    {
        coordinates[0] = 45;
        coordinates[1] = 135;
        return super.find_indices(coordinates);
    }

    @Override
    public boolean onPress(MotionEvent event)
    {
        curr_model.set_status(true); //this must be done, for the OnTouchListener won't work outside
        //of Android
        return super.onPress(event);
    }
}


public class TestOnPress {

    private int locX = 40;
    private int locY = 120;

    public GameViewTest gameViewTest = new GameViewTest(mock(Context.class));

    public MonsterWithCoordinates find_monster_at_indices(int x, int y)
    {
        for (MonsterWithCoordinates monsterWithCoordinates : gameViewTest.curr_model.monsterWithCoordinates)
            if (monsterWithCoordinates.getX() == x && monsterWithCoordinates.getY() == y)
                return monsterWithCoordinates;
        throw new RuntimeException("No monster with specified indices found. Test failed");
    }


    public int [] initialize()
    {
        gameViewTest.Constructor();
        gameViewTest.TestConstructor();
        float [] coords = {locX,locY};
        int [] indices = gameViewTest.find_indices(coords);
        MonsterWithCoordinates monster = find_monster_at_indices(indices[0],indices[1]);
        int opposite_color = (monster.getColor() == Color.GREEN) ? Color.YELLOW : Color.GREEN;
        gameViewTest.curr_model.monsterWithCoordinates.add(new MonsterWithCoordinates(indices[0], indices[1], opposite_color));
        gameViewTest.curr_monster_game.play_game();
        return indices;
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
        int [] indices = initialize();

        //assert that the correct observers are registered
        List<observer> Observers = gameViewTest.curr_monster_game.getObservers();
        assertTrue(((Observers.size() == 3 && Observers.get(0) instanceof UpdateMonstersListener &&
                Observers.get(1) instanceof MonsterView &&
                Observers.get(2) instanceof GameDurationObserver)));

        gameViewTest.onPress(mock(MotionEvent.class)); //press at the origin
        List<MonsterWithCoordinates> list_at_loc = (gameViewTest.curr_model.Find_Monsters_on_Square(indices[0],indices[1]));
        assertEquals(list_at_loc.size(),1);
        assertEquals(list_at_loc.get(0).getColor(),Color.GREEN);
        assertEquals(gameViewTest.curr_monster_game.getCurr_score(), 1); //eliminated exactly one monster
    }
}
