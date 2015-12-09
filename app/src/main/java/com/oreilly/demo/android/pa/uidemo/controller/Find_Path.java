package com.oreilly.demo.android.pa.uidemo.controller;

/**
 * Created by Michael on 12/1/2015.
 */

import android.graphics.Color;
import com.oreilly.demo.android.pa.uidemo.model.MonsterWithCoordinates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//Performs the technical calculations to determine how the monsters move.
//The recursion may seem extremely expensive, but the scenareo where monsters are trapped
//are not very frequent, and modifications near the end of the path are usually sufficient.
//Thus, the base case will be reached quickly, which immediately kills the recursion.
//If the number of iterations creeps up too much, we explicitly will generate new random movements
//and start over

public class Find_Path {

        int nrow;
        int ncol;
        int numberOfMonsters;
        int num_iter = 0;
        private List<MonsterWithCoordinates> coordinates;
        private Random random;
        private ArrayList<ArrayList<Integer>> [][] possible_destinations;

    public Find_Path(int nrow, int ncol, int numberOfMonsters, List<MonsterWithCoordinates> coordinates)
        {
            this.nrow = nrow;
            this.ncol = ncol;
            this.numberOfMonsters = numberOfMonsters;

            this.coordinates = coordinates;
            random = new Random();
            possible_destinations = enum_possible_destinations();

        }


        public ArrayList<ArrayList<Integer>> [][] enum_possible_destinations()
        {
            ArrayList<ArrayList<Integer>> [][] result = new ArrayList [nrow][ncol];
            for (int i=0; i<nrow; i++)
                for (int j=0; j<ncol; j++)
                {
                    result[i][j] = new ArrayList ();
                    for (int k=i-1; k<=i+1; k++)
                        if (k >= 0 && k < nrow)
                            for (int l=j-1; l<=j+1; l++)
                                if (l >= 0 && l < ncol && (k != i || l != j))
                                {
                                    ArrayList<Integer> point = new ArrayList ();
                                    point.add(k);
                                    point.add(l);
                                    result[i][j].add(point);
                                }
                    Collections.shuffle(result[i][j]);
                }
            return result;

        }

    public boolean recursion(combo[][] path, ArrayList<ArrayList<Integer>> [][] possible_destinations,
         List<MonsterWithCoordinates> coordinates, int index_in_nodes)
    {

        if (index_in_nodes >= numberOfMonsters)
            return true;
        num_iter ++;
        if (num_iter >= 10000)
            return false;
        int curr_x = coordinates.get(index_in_nodes).getX();
        int curr_y = coordinates.get(index_in_nodes).getY();
        for (ArrayList<Integer> point : possible_destinations[curr_x][curr_y])
        {
            if (!path[point.get(0)][point.get(1)].is_claimed)
            {
                path[point.get(0)][point.get(1)].is_claimed = true;
                path[point.get(0)][point.get(1)].index = index_in_nodes;
                if (recursion(path, possible_destinations, coordinates, index_in_nodes + 1))
                {
                    return true;
                }
                path[point.get(0)][point.get(1)].is_claimed = false;
            }
        }
        return false;
    }

    public void find_path()
    {
        combo [][] path = new combo [nrow][ncol];
        for (int i=0; i<nrow; i++)
            for (int j=0; j<ncol; j++)
            {
                path[i][j] = new combo(false, -1);
            }

        while (!recursion(path,possible_destinations, coordinates, 0))
        {
            num_iter = 0;
            for (int i=0; i<nrow; i++)
                for (int j=0; j<ncol; j++)
                {
                    assert(!path[i][j].is_claimed);
                    Collections.shuffle(possible_destinations[i][j]);
                }
        }

        /*if (!recursion(path,possible_destinations,coordinates,0))
            throw new RuntimeException();*/
        for (int i=0; i< numberOfMonsters; i++)
        {
            ArrayList<Integer> new_coordinates = find_value(path, i);
            coordinates.get(i).setX(new_coordinates.get(0));
            coordinates.get(i).setY(new_coordinates.get(1));
            coordinates.get(i).setColor(((random.nextInt(2) == 1) ? Color.GREEN : Color.YELLOW));

        }
    }

    public ArrayList find_value(combo[][] map, int value) {
        for (int i = 0; i < nrow; i++)
            for (int j = 0; j < ncol; j++)
                if (map[i][j].index == value) {
                    ArrayList<Integer> result = new ArrayList();
                    result.add(i);
                    result.add(j);
                    return result;
                }
        throw new RuntimeException("value " + value + " not found");
    }
}
