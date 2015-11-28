package com.oreilly.demo.android.pa.uidemo;

import android.graphics.Color;
import android.view.View;

import com.oreilly.demo.android.pa.uidemo.view.monster_observer;

import java.util.List;
import java.util.Random;


/**
 * Created by Lisa on 11/21/2015.
 */

//whenever there is a change to the monsters
public abstract class UpdateMonstersListener implements monster_observer {

    private Model model;
    private View view;
    private Random random;
    public UpdateMonstersListener(Model model, View view)
    {
        this.model = model;
        this.view = view;
        random = new Random();
    }

    //returns the coordinates of the location at which the mouse is pressed by the user

    @Override
    public Object update() {
        if (model.get_status())
            model.monsters[get_coordinates()[0]][get_coordinates()[1]].clear();
        else {
            List<Monster> cpy_board [][] = model.clone_monsters_list(model.monsters);
            int m = model.monsters.length;
            int n = model.monsters[0].length;
            if (m <= 1 && n <= 1)
                throw new AssertionError();
            //do random movements here
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    int len = model.monsters[i][j].size();
                    for (int k = len - 1; k >= 0; k --) {
                        while (true) {
                            int deltaX = random.nextInt(3) - 1;
                            int deltaY = random.nextInt(3) - 1;
                            if ((deltaX != 0 || deltaY != 0) && (i + deltaX >= 0) && (i + deltaX < m) && (j + deltaY >= 0) && (j + deltaY < n)) // if in bounds
                            {
                                int new_data[] = new int[3];
                                new_data[0] = i + deltaX;
                                new_data[1] = j + deltaY;
                                if (random.nextInt(2) == 1)
                                    new_data[2] = Color.GREEN;
                                else
                                    new_data[2] = Color.YELLOW;
                                cpy_board[i][j].get(k).set_data(new_data);
                                cpy_board[new_data[0]][new_data[1]].add(cpy_board[i][j].get(k));
                                cpy_board[i][j].remove(k);
                                break;
                            }
                        }
                    }
                }
            }
            model.monsters = model.clone_monsters_list(cpy_board);
        }
        for (int i=0; i<model.monsters.length; i++) {
            for (int j = 0; j < model.monsters[0].length; j++) {
                if (!model.monsters[i][j].isEmpty())
                    draw_monster(i, j);
            }
        }
        view.invalidate();
        return null;
    }
}

//so we should be ready to try to implement the change wrt getting data in monster class.