import android.graphics.Color;
import android.view.View;

import com.oreilly.demo.android.pa.uidemo.UpdateMonstersListener;
import com.oreilly.demo.android.pa.uidemo.model.Model;
import com.oreilly.demo.android.pa.uidemo.model.MonsterWithCoordinates;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;

import java.util.List;

//The point of mockito here is because we want a mock view
import static org.mockito.Mockito.*;

/**
 * Created by Michael on 11/22/2015.
 */


public class TestUpdateMonstersListener  {

    Model model;
    UpdateMonstersListener observer;
    public TestUpdateMonstersListener()
    {
        model = new Model(3,3);
        observer = new UpdateMonstersListener(model) {

            @Override
            public int[] get_coordinates() {
                int sample_coordinates[] = new int[2];
                sample_coordinates[0] = 0;
                sample_coordinates[1] = 0;
                return sample_coordinates;
            }
        };
    }

    @Test
    public void TestUpdate()
    {
        model.monsterWithCoordinates.clear();
        model.monsterWithCoordinates.add(new MonsterWithCoordinates(0, 0, Color.GREEN));
        model.monsterWithCoordinates.add(new MonsterWithCoordinates(0, 0, Color.YELLOW));
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
            assertEquals(2, model.Find_Monsters_on_Square(0,0).size());
        }

        //now test randomness up to 10% deviation from uniformly distributed
        assertTrue(num_times_green >= 90000 && num_times_green <= 110000);
        for (int i=0; i<3; i++)
            assertEquals(0,num_times_in_squares[i][2]);
        assertEquals(0, num_times_in_squares[0][0]);
        //should never jump to the right column
        for (int i=0; i<2; i++)
            for (int j=0; j<2; j++)
                if (i > 0 || j > 0)
                {
                    assert(num_times_in_squares[i][j] >= 60000);
                    //assert that the adjacent squares receive at least 90% * 1/3
                    assert(num_times_in_squares[i][j] <= 73334);
                }
    }




}
