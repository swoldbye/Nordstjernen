package com.example.skibslogapp;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.skibslogapp.datalayer.local.DAOException;
import com.example.skibslogapp.datalayer.local.EtapeDAO;
import com.example.skibslogapp.datalayer.local.LogpunktDAO;
import com.example.skibslogapp.datalayer.local.SQLiteConnector;
import com.example.skibslogapp.datalayer.local.TogtDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Logpunkt;
import com.example.skibslogapp.model.Togt;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLTest {

    static TogtDAO togtDAO;
    static EtapeDAO etapeDAO;
    static LogpunktDAO logpunktDAO;
    static Togt togt;
    static Etape etape;
    static Logpunkt logpunkt;


    static void setupTestData(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SQLiteConnector.enableTestMode(true, context);

        togtDAO = new TogtDAO(context);
        etapeDAO = new EtapeDAO(context);
        logpunktDAO = new LogpunktDAO(context);

        togt = new Togt("Test Togt");
        etape = new Etape();
        logpunkt = new Logpunkt();

        togt.setName("Tokes Sommercruise");
        togt.setSkipper("Toke");
        togt.setStartDestination("Roskilde Havn");
        togt.setSkib("Helge Ask");

        // Saving togter
        togtDAO.addTogt(togt);
        etapeDAO.addEtape(togt, etape);
        logpunktDAO.addLogpunkt(etape, logpunkt);
    }

    @Test
    public void overallTest(){

        setupTestData();

        // Loading togter
        Togt loadedTogt = togtDAO.getTogter().get(0);
        Etape loadedEtape = etapeDAO.getEtaper(loadedTogt).get(0);
        Logpunkt loadedLogpunkt = logpunktDAO.getLogpunkter(loadedEtape).get(0);

        // Checking they're correct
        assertTrue(loadedTogt.equals(togt));
        assertTrue(loadedEtape.equals(etape));
        assertTrue(loadedLogpunkt.equals(logpunkt));

        // Changing values
        togt.setId( togt.getId() + 1);
        logpunkt.setId(logpunkt.getId() + 1);
        etape.setId( etape.getId() + 1);

        // Checking they're false
        assertFalse(loadedTogt.equals(togt));
        assertFalse(loadedEtape.equals(etape));
        assertFalse(loadedLogpunkt.equals(logpunkt));

    }



    private class TestTogtObserver implements TogtDAO.TogtObserver{
        private Togt togt = null;

        Togt getTogt(){
            Togt togt = this.togt;
            this.togt = null;
            return togt;
        }

        @Override
        public void onUpdate(Togt togt) {
            this.togt = togt;
        }
    }

    @Test
    public void togtObserverTest(){

        // Setup
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SQLiteConnector.enableTestMode(true, context);

        TogtDAO togtDAO = new TogtDAO(context);
        EtapeDAO etapeDAO = new EtapeDAO(context);
        LogpunktDAO logpunktDAO = new LogpunktDAO(context);

        Togt togt = new Togt("Test Togt");
        Etape etape = new Etape();
        Logpunkt logpunkt = new Logpunkt();

        // Setting up observer
        TestTogtObserver observer = new TestTogtObserver();
        TogtDAO.addTogtObserver(observer);
        assertNull( observer.getTogt()  );

        // Saving togter
        togtDAO.addTogt(togt);
        assertNull( observer.getTogt()  );

        etapeDAO.addEtape(togt, etape);
        assertTrue( observer.getTogt().equals(togt) );

        logpunktDAO.addLogpunkt(etape, logpunkt);
        assertTrue( observer.getTogt().equals(togt) );

        SQLiteConnector.enableTestMode(false, context);
    }


    @Test
    public void deleteLogpunktTest(){

        setupTestData();

        // Loading Data
        Togt loadedTogt = togtDAO.getTogter().get(0);
        Etape loadedEtape = etapeDAO.getEtaper(loadedTogt).get(0);
        Logpunkt loadedLogpunkt = logpunktDAO.getLogpunkter(loadedEtape).get(0);

        assertTrue(logpunktDAO.logpunktExists(loadedLogpunkt));
        assertTrue(etapeDAO.etapeExists(loadedEtape));
        assertTrue(togtDAO.togtExists(togt));

        // Delete loaded togt
        logpunktDAO.deleteLogpunkt(logpunkt);

        // Check logpunkt was deleted
        assertFalse(logpunktDAO.logpunktExists(loadedLogpunkt));
        assertTrue(etapeDAO.etapeExists(loadedEtape));
        assertTrue(togtDAO.togtExists(togt));

        // Try to delete "fake" id
        try{
            Logpunkt fakeLogpunkt = new Logpunkt();
            logpunktDAO.deleteLogpunkt(fakeLogpunkt);
            fail("Logpunkt was deleted");
        }catch(DAOException exception){}
    }


    @Test
    public void deleteEtapeTest(){

        setupTestData();

        // Loading Data
        Togt loadedTogt = togtDAO.getTogter().get(0);
        Etape loadedEtape = etapeDAO.getEtaper(loadedTogt).get(0);
        Logpunkt loadedLogpunkt = logpunktDAO.getLogpunkter(loadedEtape).get(0);

        assertTrue(logpunktDAO.logpunktExists(loadedLogpunkt));
        assertTrue(etapeDAO.etapeExists(loadedEtape));
        assertTrue(togtDAO.togtExists(togt));

        // Delete loaded togt
        etapeDAO.deleteEtape(etape);

        // Check etape was deleted
        assertFalse(logpunktDAO.logpunktExists(loadedLogpunkt));
        assertFalse(etapeDAO.etapeExists(loadedEtape));
        assertTrue(togtDAO.togtExists(togt));

        // Try to delete "fake" id
        try{
            Etape fakeEtape = new Etape();
            etapeDAO.deleteEtape(fakeEtape);
            fail("Etape was deleted");
        }catch(DAOException exception){}
    }

    @Test
    public void deleteTogtTest(){

        setupTestData();

        // Loading Data
        Togt loadedTogt = togtDAO.getTogter().get(0);
        Etape loadedEtape = etapeDAO.getEtaper(loadedTogt).get(0);
        Logpunkt loadedLogpunkt = logpunktDAO.getLogpunkter(loadedEtape).get(0);

        assertTrue(logpunktDAO.logpunktExists(loadedLogpunkt));
        assertTrue(etapeDAO.etapeExists(loadedEtape));
        assertTrue(togtDAO.togtExists(togt));

        // Delete loaded togt
        togtDAO.deleteTogt(loadedTogt);

        // Check togt was deleted
        assertFalse(logpunktDAO.logpunktExists(loadedLogpunkt));
        assertFalse(etapeDAO.etapeExists(loadedEtape));
        assertFalse(togtDAO.togtExists(togt));

        // Try to delete "fake" id
        try{
            Togt fakeTogt = new Togt("Fake Togt");
            togtDAO.deleteTogt(fakeTogt);
            fail("Etape was deleted");
        }catch(DAOException exception){}
    }


    @Test
    public void besaetningConversionTest(){
        setupTestData();

        // From list to String
        ArrayList<String> besaetning = new ArrayList<>(Arrays.asList("Andreas","Jacob","Claes","Kristian","Simon","Malte"));
        String resultString;
        resultString = etapeDAO.besaetningToString(besaetning);
        assertEquals(resultString, "Andreas;Jacob;Claes;Kristian;Simon;Malte");

        // From string to list
        String besaetningString = "Andreas;Jacob;Claes;Kristian;Simon;Malte";
        List<String> resultList;
        resultList = etapeDAO.besaetningToList(besaetningString);

        assertEquals( resultList.get(0), "Andreas");
        assertEquals( resultList.get(1), "Jacob");
        assertEquals( resultList.get(2), "Claes");
        assertEquals( resultList.get(3), "Kristian");
        assertEquals( resultList.get(4), "Simon");
        assertEquals( resultList.get(5), "Malte");

        // From string to list
        besaetningString = "";
        resultList = etapeDAO.besaetningToList(besaetningString);

        assertEquals( resultList.size(), 0);
    }


    @Test
    public void besaetningSaveTest(){
        setupTestData();

        // Add initial members
        etape.addBesaetningsMedlem("Andreas", "Jacob");
        etapeDAO.updateEtape(etape);

        // Load from database
        Etape loadedEtape = etapeDAO.getEtaper(togt).get(0);
        assertEquals( loadedEtape.getBesaetning().get(0), "Andreas");
        assertEquals( loadedEtape.getBesaetning().get(1), "Jacob");

        // Adjust members
        loadedEtape.removeBesaetningsMedlem("Andreas");
        loadedEtape.addBesaetningsMedlem("Simon");
        etapeDAO.updateEtape(loadedEtape);

        // Reload from database
        loadedEtape = etapeDAO.getEtaper(togt).get(0);
        assertEquals( loadedEtape.getBesaetning().get(0), "Jacob");
        assertEquals( loadedEtape.getBesaetning().get(1), "Simon");

    }




}
