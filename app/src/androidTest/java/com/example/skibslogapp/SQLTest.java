package com.example.skibslogapp;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.skibslogapp.datalayer.local.EtapeDAO;
import com.example.skibslogapp.datalayer.local.LogpunktDAO;
import com.example.skibslogapp.datalayer.local.SQLiteConnector;
import com.example.skibslogapp.datalayer.local.TogtDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Logpunkt;
import com.example.skibslogapp.model.Togt;

import static org.junit.Assert.*;

import org.junit.Test;

public class SQLTest {


    @Test
    public void overallTest(){

        String logTag = "SQL-overallTest";

        // Setup
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SQLiteConnector.enableTestMode(true, context);

        TogtDAO togtDAO = new TogtDAO(context);
        EtapeDAO etapeDAO = new EtapeDAO(context);
        LogpunktDAO logpunktDAO = new LogpunktDAO(context);

        Togt togt = new Togt("Test Togt");
        Etape etape = new Etape();
        Logpunkt logpunkt = new Logpunkt();

        togt.setName("Tokes Sommercruise");
        togt.setSkipper("Toke");
        togt.setStartDestination("Roskilde Havn");
        togt.setSkib("Helge Ask");

        // Saving togter
        togtDAO.addTogt(togt);
        etapeDAO.addEtape(togt, etape);
        logpunktDAO.addLogpunkt(etape, logpunkt);

        // Loading togter
        Togt loadedTogt = togtDAO.getTogter().get(0);
        Etape loadedEtape = etapeDAO.getEtaper(loadedTogt).get(0);
        Logpunkt loadedLogpunkt = logpunktDAO.getLogpunkter(loadedEtape).get(0);

        Log.d(logTag, "Saved Togt: "+togt);
        Log.d(logTag, "Loaded Togt: "+loadedTogt);

        // Checking they're correct
        assertTrue(loadedTogt.equals(togt));
        assertTrue(loadedEtape.equals(etape));
        assertTrue(loadedLogpunkt.equals(logpunkt));

        // Changing values
        togt.setId( togt.getId() + 1);
        logpunkt.setId(logpunkt.getId() + 1);
        etape.setId( etape.getId() + 1);
        logpunkt.setLaengdegrad(0.001);

        // Checking they're false
        assertFalse(loadedTogt.equals(togt));
        assertFalse(loadedEtape.equals(etape));
        assertFalse(loadedLogpunkt.equals(logpunkt));

        SQLiteConnector.enableTestMode(false, context);
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



}
