package com.oreilly.demo.android.pa.uidemo;

import android.graphics.Color;

import com.oreilly.demo.android.pa.uidemo.model.MonsterWithCoordinates;
import com.oreilly.demo.android.pa.uidemo.view.monster_observer;
import com.oreilly.demo.android.pa.uidemo.model.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by Michael on 11/21/2015.
 */

//Responds to events by removing or shifting monsters on the board.
public abstract class UpdateMonstersListener implements monster_observer {

    private Model model;
    private Random random;
    public UpdateMonstersListener(Model model)
    {
        this.model = model;
        random = new Random();
    }

    //For each monster, randomly select an adjacent square on the board (never the same square).
    //Also, randomly select a new color.
    @Override
    public Object update() {

        if (model.get_status())
        {
            int [] coordinates = get_coordinates();
            model.monsterWithCoordinates.removeAll(model.Find_Monsters_on_Square(coordinates[0], coordinates[1]));
        }
        else {

            List<MonsterWithCoordinates> cpy_board = new ArrayList<>();
            //The reason for copying the board is so that all changes to the board are independent from each other.

            int m = model.getM();
            int n = model.getN();
            if (m <= 1 && n <= 1)
                throw new AssertionError();
            //do random movements here

            for (MonsterWithCoordinates monsterWithCoordinates : model.monsterWithCoordinates)
            {
                    int i = monsterWithCoordinates.getX();
                    int j = monsterWithCoordinates.getY();
                        //Choose coordinates randomly until get a valid coordinate to move to
                        while (true) {
                            int deltaX = random.nextInt(3) - 1;
                            int deltaY = random.nextInt(3) - 1;
                            if ((deltaX != 0 || deltaY != 0) && (i + deltaX >= 0) && (i + deltaX < m) && (j + deltaY >= 0) && (j + deltaY < n)) // if in bounds
                            {
                                MonsterWithCoordinates copy_monsterWithCoordinates = new MonsterWithCoordinates(i + deltaX, j + deltaY,
                                ((random.nextInt(2) == 1) ? Color.GREEN : Color.YELLOW));
                                cpy_board.add(copy_monsterWithCoordinates);
                                break;
                            }
                        }
                    }
            model.monsterWithCoordinates = cpy_board;

        }


        return null;
    }
}

