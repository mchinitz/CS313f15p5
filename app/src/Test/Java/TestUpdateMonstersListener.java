import android.graphics.Color;

import com.oreilly.demo.android.pa.uidemo.controller.Find_Path;
import com.oreilly.demo.android.pa.uidemo.controller.MonsterGame;
import com.oreilly.demo.android.pa.uidemo.controller.UpdateMonstersListener;
import com.oreilly.demo.android.pa.uidemo.controller.combo;
import com.oreilly.demo.android.pa.uidemo.model.Model;
import com.oreilly.demo.android.pa.uidemo.model.MonsterWithCoordinates;
import com.oreilly.demo.android.pa.uidemo.model.MonsterWithImage;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//The point of mockito here is because we want a mock view


/**
 * Created by Michael on 11/22/2015.
 */

class Check_Find_Path extends Find_Path
{

    private long initial_time;

    public Check_Find_Path(int nrow, int ncol, int k, List<MonsterWithCoordinates> coordinates) {
        super(nrow, ncol, k, coordinates);
        initial_time = System.currentTimeMillis();
    }

    @Override
    public boolean recursion(combo[][] path, ArrayList<ArrayList<Integer>> [][] possible_destinations,
                             List<MonsterWithCoordinates> coordinates, int index_in_nodes)
    {
        boolean result = super.recursion(path, possible_destinations, coordinates, index_in_nodes);
        if (System.currentTimeMillis() - initial_time > 2000)
            throw new RuntimeException("Test took too long, and thus failed");
        return result;
    }
}

class Check_Update_Listener extends UpdateMonstersListener
{
    private Model model;

    public Check_Update_Listener(Model model, MonsterGame monsterGame) {
        super(model, monsterGame);
        this.model = model;

    }

    @Override
    public int[] get_coordinates() {
        return null;
    }

    @Override
    public Object update()
    {
        new Check_Find_Path(model.getM(), model.getN(), model.getK(),model.monsterWithCoordinates).find_path();
        return null;
    }
}

public class TestUpdateMonstersListener  {



    Model model, model2;
    UpdateMonstersListener observer;
    public TestUpdateMonstersListener()
    {
        model = new Model(3,3,1);
        observer = new UpdateMonstersListener(model,mock(MonsterGame.class)) {

            @Override
            public int[] get_coordinates() {
                int sample_coordinates[] = new int[2];
                sample_coordinates[0] = 0;
                sample_coordinates[1] = 0;
                return sample_coordinates;
            }

        };
        model2 = new Model(10,10,50);
        init_coords();
    }

    //Todo get rid of once Model is correct

    public void init_coords()
    {
        List<Integer []> possible_coordinates = new ArrayList ();
        for (int i=0; i<model2.getM(); i++)
            for (int j=0; j<model2.getN(); j++) {
                Integer [] pair = {i,j};
                possible_coordinates.add(pair);
            }
        Collections.shuffle(possible_coordinates);
        for (int i=0; i<model2.getK(); i++)
        {
            int color = (new Random().nextInt(2) == 1) ? Color.GREEN : Color.YELLOW;
            model2.monsterWithCoordinates.add(new MonsterWithCoordinates(possible_coordinates.get(i)[0], possible_coordinates.get(i)[1],color));
        }

    }




    @Test
    public void TestUpdate()
    {
        model.monsterWithCoordinates.clear();
        model.monsterWithCoordinates.add(new MonsterWithCoordinates(0, 0, Color.GREEN));
        List<MonsterWithCoordinates> original_board = model.clone_monsters_list(model.monsterWithCoordinates);

        int num_times_in_squares[][] = new int[3][3];
        int num_times_green = 0;


        //Use monte carlo to test randomness
        for (int i=0; i<100000; i++)
        {
            observer.update();
            for (MonsterWithCoordinates monsterWithCoordinates : model.monsterWithCoordinates)
            {
                num_times_in_squares[monsterWithCoordinates.getX()][monsterWithCoordinates.getY()]++;
                if (monsterWithCoordinates.getColor() == Color.GREEN)
                    num_times_green ++;
            }
            model.monsterWithCoordinates = model.clone_monsters_list(original_board);
        }

        //now test randomness up to 10% deviation from uniformly distributed
        assertTrue(num_times_green >= 45000 && num_times_green <= 55000);
        for (int i=0; i<3; i++)
            assertEquals(0,num_times_in_squares[i][2]);
        assertEquals(0, num_times_in_squares[0][0]);
        //should never jump to the right column

        for (int i=0; i<2; i++)
            for (int j=0; j<2; j++)
                if (i > 0 || j > 0)
                {
                    assert(num_times_in_squares[i][j] >= 30000);
                    //assert that the adjacent squares receive at least 90% * 1/3
                    assert(num_times_in_squares[i][j] <= 36667);
                }
    }

    //this checks the time taken to update positions
    @Test
    public void TestUpdate2()
    {
        Check_Update_Listener test_class = new Check_Update_Listener(model2,mock(MonsterGame.class));
        for (int i=0; i<50; i++)
        {
            int initial_head_coords [] = {model2.monsterWithCoordinates.get(0).getX(), model2.monsterWithCoordinates.get(0).getY()};
            test_class.update();
            int final_head_coords [] = {model2.monsterWithCoordinates.get(0).getX(), model2.monsterWithCoordinates.get(0).getY()};
            for (int j=0; j<2; j++)
                assert(Math.abs(final_head_coords[j] - initial_head_coords[j]) <= 1);

        }
    }




}
