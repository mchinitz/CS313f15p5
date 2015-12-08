package com.oreilly.demo.android.pa.uidemo.model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Michael on 11/21/2015.
 */

//This class defines the board and populates monsters on the board
public class Model {

    public List<MonsterWithCoordinates> monsterWithCoordinates;
    private Boolean is_user_initiated = false;
    private int m,n,k;

    public Model(int m, int n, int k) {
        this.m = m;
        this.n = n;
        this.k = k;

        monsterWithCoordinates = new ArrayList();


        //populates the board initially with k monsters, at most one monster per square.

        List<Integer []> possible_coordinates = new ArrayList ();
        for (int i=0; i<m; i++)
            for (int j=0; j<n; j++) {
                Integer [] pair = {i,j};
                possible_coordinates.add(pair);
            }
        Collections.shuffle(possible_coordinates);
        for (int i=0; i<k; i++)
        {
            int color = (new Random().nextInt(2) == 1) ? Color.GREEN : Color.YELLOW;
            monsterWithCoordinates.add(new MonsterWithCoordinates(possible_coordinates.get(i)[0], possible_coordinates.get(i)[1], color));

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

    public int getK() {return k;}

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
