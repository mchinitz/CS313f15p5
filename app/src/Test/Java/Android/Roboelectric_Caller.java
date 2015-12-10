package Android;

import com.oreilly.demo.android.pa.uidemo.controller.MonstersGameController;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by Lisa on 12/10/2015.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml", emulateSdk=18)
public class Roboelectric_Caller extends MonsterGameRoboelectricTest {

    private static String TAG = "countdown-android-activity-robolectric";

    private MonstersGameController activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(MonstersGameController.class).create().start().get();
    }

    @Override
    public MonstersGameController getActivity() {
        return activity;
    }



}
