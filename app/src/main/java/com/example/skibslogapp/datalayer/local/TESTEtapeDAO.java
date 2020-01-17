package com.example.skibslogapp.datalayer.local;

import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Togt;

import java.util.ArrayList;
import java.util.List;

public class TESTEtapeDAO {

    private List<Etape> TestListEtapeDAO;

    public TESTEtapeDAO(){
        TestListEtapeDAO = new ArrayList<>();

        Etape testEtape1 = new Etape();
        testEtape1.setId(1);
        testEtape1.setTogtId(1);


        Etape testEtape2 = new Etape();
        testEtape2.setId(2);
        testEtape2.setTogtId(1);

        TestListEtapeDAO.add(testEtape1);
        TestListEtapeDAO.add(testEtape2);
    }

    public List<Etape> getEtape(Togt togt){

        List<Etape> returnDAOEtape = new ArrayList<Etape>();

        for(Etape a : TestListEtapeDAO){

            if(a.getTogtId()==togt.getId()){
                returnDAOEtape.add(a);
            }
        }

        return returnDAOEtape;

    }
}
