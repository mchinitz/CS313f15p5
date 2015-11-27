package com.oreilly.demo.android.pa.uidemo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lisa on 11/21/2015.
 */

//TODO
public class Model {

    public List<Monster> monsters[][];
    private Boolean is_user_initiated = false;
    public Model(int m, int n)
    {

        monsters = new List [m][n];
        for (int i=0; i<m; i++)
            for (int j=0; j<n; j++)
                monsters[i][j] = new ArrayList ();
    }

    public Boolean get_status()
    {
        return is_user_initiated;
    }

    //The reason for this function, vs. just clone, is otherwise the array lists in the array
    //won't actually get copied by value.
    public List<Monster> [][] clone_monsters_list(List<Monster> [][] original)
    {
        int nrow = original.length;
        int ncol = original[0].length;

        List<Monster> [][] cpy = new ArrayList [nrow][ncol];
        for (int i=0; i<nrow; i++)
            for (int j=0; j<ncol; j++)
            {
                cpy[i][j] = new ArrayList<Monster> ();
                int len = original[i][j].size();
                for (int k=0; k<len; k++)
                    cpy[i][j].add(original[i][j].get(k));
            }
        return cpy;
    }

}
