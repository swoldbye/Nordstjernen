package com.example.skibslogapp.model;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

public class Logpunkt {
    private long id = -1;
    private long etapeId = -1;
    private long togtId = -1;

    private Date date; // And time

    private int roere = -1;
    private String vindretning = null;
    private int kurs = -1;
    private String note = null;
    private boolean mandOverBord = false;

    private String sejlfoering = null;
    private String sejlstilling = null;
    private String stroem = null;

    int hals = -1;

    public Logpunkt(Date date, String vindretning, int kurs, boolean mandOverBord, String sejlfoering, String sejlstilling, String stroem, String note){
        this.date = date;
        this.vindretning = vindretning;
        this.kurs = kurs;
        this.mandOverBord = mandOverBord;
        this.sejlfoering = sejlfoering;
        this.sejlstilling = sejlstilling;
        this.stroem = stroem;
        this.note = note;
    }


    public Logpunkt(){
        this(null);
    }


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


    public long getEtapeId() {
        return etapeId;
    }

    public void setEtapeId(long etapeId) {
        this.etapeId = etapeId;
    }

    public long getTogtId() {
        return togtId;
    }

    public void setTogtId(long togtId) {
        this.togtId = togtId;
    }


    public String getStroem() {
        return stroem;
    }

    public void setStroem(String stroem) {
        this.stroem = stroem;
    }

    public String getTimeString(){
        if( date == null ) return "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.format( "%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

    public String getKursString(){
        if( kurs == -1 ) return "";
        return Integer.toString(kurs);
    }

    public String getSejloeringString(){
        if( sejlfoering == null ) return "";
        return sejlfoering;
    }

    public String getSejlstillingString(){
        if( sejlstilling == null ) return "";
        return sejlstilling;
    }

    public String getNoteString(){
        if( note == null ) return "";
        return note;
    }

    /**
     * Compares the Logpunkt with another Logpunkt, comparing
     * all values.
     *
     * @param otherPunkt Logpunkt to compare with
     */
    public boolean equals( Logpunkt otherPunkt ){
        return
            id == otherPunkt.id     &&
            kurs == otherPunkt.kurs &&
            hals == otherPunkt.hals &&
            sejlfoering == otherPunkt.sejlfoering &&
            sejlstilling == otherPunkt.sejlstilling &&
            vindretning == otherPunkt.vindretning &&
            mandOverBord == otherPunkt.mandOverBord &&
            date.equals(otherPunkt.date) &&
            roere == otherPunkt.roere;

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
            "Logpunkt{ id: %d, etapeId: %d, date: %s, mob: %s, kurs: %s, vind: %s, sejls.: %s, sejlf. %s, roere: %s, note: %s}",
                id,
                etapeId,
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
