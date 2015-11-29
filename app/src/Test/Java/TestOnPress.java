/**
 * Created by Lisa on 11/28/2015.
 */

import android.content.Context;
import android.graphics.Color;

import com.oreilly.demo.android.pa.uidemo.MonsterGame;
import com.oreilly.demo.android.pa.uidemo.UpdateMonstersListener;
import com.oreilly.demo.android.pa.uidemo.model.Model;
import com.oreilly.demo.android.pa.uidemo.model.MonsterWithCoordinates;
import com.oreilly.demo.android.pa.uidemo.view.GameView;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.List;

class GameViewTest extends GameView
{

    public GameViewTest(Context context) {
        super(context);
        m = 5;
        n = 5;
    }

    public Model curr_model;
    public MonsterGame curr_monster_game;

    public void TestConstructor()
    {
        curr_model = model;
        curr_monster_game = monsterGame;
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

    @Override
    public float [] get_location_pressed()
    {
        float [] coordinates = new float [2];
        coordinates[0] = 45;
        coordinates[1] = 135;
        return coordinates;
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

    //NOTE: THIS IS GOING TO FAIL UNTIL AT LEAST THE UPDATEMONSTERSLISTENER is invoked
    @Test
    public void Test()
    {
        int [] indices = initialize();

        gameViewTest.onPress(); //press at the origin
        List<MonsterWithCoordinates> list_at_loc = (gameViewTest.curr_model.Find_Monsters_on_Square(indices[0],indices[1]));
        assertEquals(list_at_loc.size(),1);
        assertEquals(list_at_loc.get(0).getColor(),Color.GREEN);
    }


}
