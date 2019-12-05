package com.example.skibslogapp.model;

import java.util.Calendar;
import java.util.Date;

public class Logpunkt {
    private long id = -1;

    private Date date; // And time

    private int roere = -1;
    private String vindretning = null;
    private int kurs = -1;
    private String note = null;
    private boolean mandOverBord = false;

    private String sejlfoering = null;
    private String sejlstilling = null;

    int hals = -1;

    /** If date is null, it sets it to current time */
    public Logpunkt(Date date ){
        if( date == null )
            this.date = new Date(System.currentTimeMillis());
        else
            this.date = date;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRoere() {
        return roere;
    }

    public void setRoere(int roere) {
        this.roere = roere;
    }

    public String getVindretning() {
        return vindretning;
    }

    public void setVindretning(String vindretning) {
        this.vindretning = vindretning;
    }

    public int getKurs() {
        return kurs;
    }

    public void setKurs(int kurs) {
        this.kurs = kurs;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public String getSejlfoering() {
        return sejlfoering;
    }

    public void setSejlfoering(String sejlfoering) {
        this.sejlfoering = sejlfoering;
    }

    public String getSejlstilling() {
        return sejlstilling;
    }

    public void setSejlstilling(String sejlstilling) {
        this.sejlstilling = sejlstilling;
    }

    public int getHals() {
        return hals;
    }

    public void setHals(int hals) {
        this.hals = hals;
    }

    public boolean getMandOverBord() {
        return mandOverBord;
    }

    public void setMandOverBord(boolean mandOverBord) {
        this.mandOverBord = mandOverBord;
    }


    @Override
    public String toString(){

        // Converting date to string
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String dateString = String.format("%d/%d-%d %02d:%02d",
                cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.MONTH)+1,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE)
        );

        return String.format(
            "Logpunkt{ id: %d, date: %s, mob: %s, kurs: %s, vind: %s, sejls.: %s, sejlf. %s, roere: %s, note: %s}",
                id,
                dateString,
                mandOverBord ? "true" : "false",
                kurs >=0 ? kurs : "-",
                vindretning != null ? kurs : "-",
                sejlstilling != null ? sejlstilling : "-",
                sejlfoering != null ? sejlfoering : "-",
                roere >= 0 ? roere : "-",
                note != null ? note : "-"
        );
    }
}
