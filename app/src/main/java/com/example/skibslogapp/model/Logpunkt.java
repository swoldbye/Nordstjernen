package com.example.skibslogapp.model;

import com.example.skibslogapp.model.Position.Position;
import com.example.skibslogapp.view.opretLog.LogViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Logpunkt {

    // Ids
    private long id = -1;
    private long etapeId = -1;
    private long togtId = -1;

    // Dates (and times)
    private Date date;
    private Date creationDate; // And time

    private Position position;

    private boolean mandOverBord = false;

    private String vindretning = null;
    private int vindhastighed = -1;
    private String stroemRetning = null;
    private int stroemhastighed = -1;
    private int kurs = -1;
    private String note = null;

    private String sejlfoering = null;
    private String sejlstilling = null;
    private int roere = -1;




    private int hals = -1;

    public Logpunkt(){
        this(null);
    }


    /** If date is null, it sets it to current time */
    public Logpunkt(Date date ){
        /* Time is saved in variable to secure accurate equality between
            log date and creation date */
        long time = System.currentTimeMillis();
        if( date == null )
            this.date = new Date(time);
        else
            this.date = date;
        this. creationDate = new Date(time);
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

    /**
     * Sets the creation date of the logpunkt.
     * Note: The creation date is automatically set to the curret time
     * when creating a new logpunkt.
     * @param date
     */
    public void setCreationDate(Date date){
        creationDate = date;
    }

    public Date getCreationDate(Date date){
        return creationDate;
    }

    public String getStroemRetning() {
        return stroemRetning;
    }

    public void setStroemRetning(String stroemRetning) {
        this.stroemRetning = stroemRetning;
    }

    public void setPosition(Position position) {
            this.position = position;
    }

    public String getTimeString(){
        if( date == null ) return "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.format(Locale.US, "%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
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

    public int getVindhastighed() {
        return vindhastighed;
    }

    public void setVindhastighed(int vindhastighed) {
        this.vindhastighed = vindhastighed;
    }

    public int getStroemhastighed() {
        return stroemhastighed;
    }

    public void setStroemhastighed(int stroemhastighed) {
        this.stroemhastighed = stroemhastighed;
    }

    public boolean isMandOverBord() {
        return mandOverBord;
    }


    public Position getPosition() {
        return position;
    }

    /**
     * Compares the Logpunkt with another Logpunkt, comparing
     * all values.
     *
     * @param otherPunkt Logpunkt to compare with
     */
    public boolean equals( Logpunkt otherPunkt ){
        /* .equals is not used on strings here, because the
        *   strings may be null. It's not optimal, and may in
        *   special cases cause problems.*/
        return
            id == otherPunkt.id     &&
            kurs == otherPunkt.kurs &&
            hals == otherPunkt.hals &&
            Objects.equals(sejlfoering, otherPunkt.sejlfoering) &&
            Objects.equals(sejlstilling, otherPunkt.sejlstilling) &&
            Objects.equals(vindretning, otherPunkt.vindretning) &&
            vindhastighed == otherPunkt.vindhastighed &&
            Objects.equals(stroemRetning, otherPunkt.stroemRetning) &&
            stroemhastighed == otherPunkt.stroemhastighed &&
            mandOverBord == otherPunkt.mandOverBord &&
            date.equals(otherPunkt.date) &&
            creationDate.equals(otherPunkt.creationDate) &&
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

        cal.setTime(creationDate);
        String creationDateString = String.format("%d/%d-%d %02d:%02d",
                cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.MONTH)+1,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE)
        );

        return String.format(
                Locale.US,
            "Logpunkt{ id: %d, etapeId: %d, date: %s, creationDate: %s, pos.: %s, mob: %s, kurs: %s, vind: %s, vindhast.: %d, strøm: %s, strømhast.: %d, sejls.: %s, sejlf. %s, roere: %s, note: %s }",
                id,
                etapeId,
                dateString,
                creationDateString,
                position != null ? position : "-",
                mandOverBord ? "true" : "false",
                kurs >=0 ? kurs : "-",
                vindretning != null ? vindretning : "-",
                vindhastighed,
                stroemRetning != null ? stroemRetning : "-",
                stroemhastighed,
                sejlstilling != null ? sejlstilling : "-",
                sejlfoering != null ? sejlfoering : "-",
                roere >= 0 ? roere : "-",
                note != null ? note : "-"
        );
    }

    public void setInformation(LogViewModel logVM) {
        this.setVindretning( logVM.getWindDirection() );
        this.setVindhastighed( logVM.getWindSpeed() );
        this.setStroemRetning( logVM.getWaterCurrentDirection() );
        this.setStroemhastighed( logVM.getWaterCurrentSpeed() );
        this.setSejlstilling( logVM.getSailPosition() );
        this.setRoere(logVM.getCurrRowers());
        this.setSejlfoering( logVM.getSails().equals("") ?
                logVM.getSails().concat(logVM.getOrientation()) : logVM.getSails().concat("-" + logVM.getOrientation() ));
        this.setKurs(logVM.getCourse());
        this.setNote( logVM.getNoteTxt() );
    }
}
