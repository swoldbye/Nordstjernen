package com.example.skibslogapp;

import android.content.Context;

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

        // Setup
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SQLiteConnector.enableTestMode(true, context);

        TogtDAO togtDAO = new TogtDAO(context);
        EtapeDAO etapeDAO = new EtapeDAO(context);
        LogpunktDAO logpunktDAO = new LogpunktDAO(context);

        Togt togt = new Togt("Test Togt");
        Etape etape = new Etape();
        Logpunkt logpunkt = new Logpunkt();

        // Saving togter
        togtDAO.addTogt(togt);
        etapeDAO.addEtape(togt, etape);
        logpunktDAO.addLogpunkt(etape, logpunkt);

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

        SQLiteConnector.enableTestMode(false, context);
    }

}
