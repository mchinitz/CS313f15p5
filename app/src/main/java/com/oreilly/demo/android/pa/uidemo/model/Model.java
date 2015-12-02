package com.oreilly.demo.android.pa.uidemo.model;

import android.graphics.Color;

import com.oreilly.demo.android.pa.uidemo.model.Constants;
import com.oreilly.demo.android.pa.uidemo.model.MonsterWithCoordinates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Michael on 11/21/2015.
 */

//This class defines the board and populates monsters on the board
public class Model {

    private final String IMAGE_FILE = "monster.png";
    private List<MonsterWithImage> monsterWithImage;
    public List<MonsterWithCoordinates> monsterWithCoordinates;
    private Boolean is_user_initiated = false;
    private int m,n;


    //TODO I believe the for loops in this class are not ended properly
    public Model(int m, int n) {
        this.m = m;
        this.n = n;
        monsterWithCoordinates = new ArrayList();


        //populates the board initially with one monster per square.
        //TODO the initial state is wrong
        for (int i = 0; i < m; i++)
        {
            for (int j = 0; j < n; j++) {
                int color = (new Random().nextInt(2) == 1) ? Color.GREEN : Color.YELLOW;
                monsterWithCoordinates.add(new MonsterWithCoordinates(i, j, color));
                monsterWithImage.add(new MonsterWithImage(new MonsterWithCoordinates(i, j, color), IMAGE_FILE));
                //populates the board initially with k monsters, whose positions are random but distinct
            }
        }
        Random random = new Random();
        List<Integer []> coordinates = new ArrayList ();
        for(int i=0; i<m; i++)
        {
            for(int j = 0; j < n; j++) {
                Integer[] pair = {i, j};
                coordinates.add(pair);
            }
        }
        Collections.shuffle(coordinates);
        int num_monsters = 0;
        for (int i=0; i<m; i++)
        {
            for(int j = 0; j < n; j++) {
                if (num_monsters >= Constants.k) {
                    return;
                }
                int color = (random.nextInt(2) == 1) ? Color.GREEN : Color.YELLOW;

                monsterWithCoordinates.add(new MonsterWithCoordinates(coordinates.get(num_monsters)[0],
                        coordinates.get(num_monsters)[1], color));
                num_monsters++;
            }
        }
    }

    public int getM()
    {
        return m;
    }

    public int getN()
    {
        return n;
    }

    public List<MonsterWithImage> getMonsterWithImage(){return monsterWithImage;}

    //Returns the context for the change in the numbers and locations of monsters.
    //If the user presses the mouse, then the interaction is to eliminate all monsters on the
    //square that are yellow
    public Boolean get_status()
    {
        return is_user_initiated;
    }

    public void set_status(Boolean status)
    {
        is_user_initiated = status;
    }

   //returns a list of all monsters on a particular square. Used to move the monsters
  public List<MonsterWithCoordinates> Find_Monsters_on_Square(int x, int y)
  {
      List<MonsterWithCoordinates> result = new ArrayList<> ();
      int len = monsterWithCoordinates.size();
      for (int i=0; i<len; i++)
          if (monsterWithCoordinates.get(i).getX() == x && monsterWithCoordinates.get(i).getY() == y)
              result.add(monsterWithCoordinates.get(i));
      return result;
  }

    //Returns a copy of the list of monsters.
  public List<MonsterWithCoordinates> clone_monsters_list(List<MonsterWithCoordinates> monsterWithCoordinates)
  {
      List<MonsterWithCoordinates> copy = new ArrayList<> ();
      int length = monsterWithCoordinates.size();
      for (int i=0; i<length; i++)
          copy.add(new MonsterWithCoordinates(monsterWithCoordinates.get(i).getX(),monsterWithCoordinates.get(i).getX(),monsterWithCoordinates.get(i).getColor()));
      return copy;
  }

}
