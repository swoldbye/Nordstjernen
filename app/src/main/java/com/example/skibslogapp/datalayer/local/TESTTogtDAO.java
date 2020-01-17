package com.example.skibslogapp.datalayer.local;

import com.example.skibslogapp.model.Togt;

import java.util.ArrayList;
import java.util.List;

public class TESTTogtDAO {

    private List<Togt>  TestListTogtDAO;
    public TESTTogtDAO(){
        TestListTogtDAO = new ArrayList<>();
        Togt test1 = new Togt("Test 1");
        test1.setSkipper("skipper1");
        test1.setId(1);

        Togt test2 = new Togt("Test 2");
        test2.setSkipper("skipper2");
        test2.setId(2);

        Togt test3 = new Togt("Test 3");
        test3.setSkipper("skipper3");
        test3.setId(3);

        TestListTogtDAO.add(test1);
        TestListTogtDAO.add(test2);
        TestListTogtDAO.add(test3);

    }

    public List<Togt> getTogter(){
        return TestListTogtDAO;
    }
}
