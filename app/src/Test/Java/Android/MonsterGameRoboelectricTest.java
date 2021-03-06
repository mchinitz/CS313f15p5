package Android;

import android.graphics.Canvas;
import android.support.annotation.UiThread;
import android.test.UiThreadTest;

import com.oreilly.demo.android.pa.uidemo.R;
import com.oreilly.demo.android.pa.uidemo.controller.DefaultClockModel;
import com.oreilly.demo.android.pa.uidemo.controller.MonstersGameController;
import com.oreilly.demo.android.pa.uidemo.controller.UpdateMonstersListener;
import com.oreilly.demo.android.pa.uidemo.model.Constants;
import com.oreilly.demo.android.pa.uidemo.view.GameView;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

import static org.mockito.Mockito.verify;

/**
 * Created by Michael on 12/10/2015.
 */

//The entire idea behind this test is that if the unit tests pass, we only need to test whether
//UpdateMonstersListener.update is called once with the right model state,
// for we know update is correct.


public abstract class MonsterGameRoboelectricTest {


    public abstract MonstersGameController getActivity();


    @Test
    public void set_up_properly()
    {
        assert(getActivity() != null);
    }


    @UiThreadTest //[1]
    public void test_monster_game() {


        GameView gameView = (GameView) (getActivity().findViewById(R.id.canvas));
        assert(gameView != null);

        gameView.onDraw(new Canvas());
        assert (gameView.observerList.get(0) instanceof UpdateMonstersListener);
        DefaultClockModel defaultClockModel = (DefaultClockModel)(gameView.monsterGame.getClockModel());

        while (defaultClockModel.get_queue_size() == 0);
        defaultClockModel.empty_queue();

        //before creating mock, empties out all initial interactions
        gameView.observerList.set(0, mock(UpdateMonstersListener.class));

        try
        {
            Thread.currentThread().sleep(Constants.gameplay_time + 50);
        }
        catch (Exception e)
        {
            throw new RuntimeException();
        }

        defaultClockModel.empty_queue();
        verify(gameView.observerList.get(0)).update();
        assertFalse(gameView.model.get_status());
    }
}
