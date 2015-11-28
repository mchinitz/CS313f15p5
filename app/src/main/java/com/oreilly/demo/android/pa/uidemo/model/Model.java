package com.oreilly.demo.android.pa.uidemo.model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Michael on 11/21/2015.
 */

//TODO
public class Model {

    public List<MonsterWithCoordinates> monsterWithCoordinates;
    private Boolean is_user_initiated = false;
    private int m,n;
    public Model(int m, int n)
    {
        this.m = m;
        this.n = n;
        monsterWithCoordinates = new ArrayList ();
        for (int i=0; i<m; i++)
            for (int j=0; j<n; j++)
            {
                int color = (new Random().nextInt(2) == 1) ? Color.GREEN : Color.YELLOW;
                monsterWithCoordinates.add(new MonsterWithCoordinates(i,j,color));
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

    public Boolean get_status()
    {
        return is_user_initiated;
    }

  public List<MonsterWithCoordinates> Find_Monsters_on_Square(int x, int y)
  {
      List<MonsterWithCoordinates> result = new ArrayList<> ();
      int len = monsterWithCoordinates.size();
      for (int i=0; i<len; i++)
          if (monsterWithCoordinates.get(i).getX() == x && monsterWithCoordinates.get(i).getY() == y)
              result.add(monsterWithCoordinates.get(i));
      return result;
  }

  public List<MonsterWithCoordinates> clone_monsters_list(List<MonsterWithCoordinates> monsterWithCoordinates)
  {
      List<MonsterWithCoordinates> copy = new ArrayList<> ();
      int length = monsterWithCoordinates.size();
      for (int i=0; i<length; i++)
          copy.add(monsterWithCoordinates.get(i));
      return copy;
  }

}
