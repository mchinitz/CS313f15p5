package com.oreilly.demo.android.pa.uidemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lisa on 11/21/2015.
 */
public class Model {

    private List<Monster> monsters[][];
    private Boolean is_user_initiated;
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


}
