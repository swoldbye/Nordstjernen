package com.example.skibslogapp.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Etape {

    private long id = -1;
    private long togtId = -1;
    private Date startDate = null;
    private Date endDate = null;
    private List<String> besaetningList = null;

    public List<String> getBesaetning() {
        return besaetningList;
    }

    public void setBesaetning(List<String> besaetningList) {
        this.besaetningList = besaetningList;
    }

    /**
     * Constructs a new Etape object. Note that this will NOT
     * be saved to the local database automatically.
     * The start date will automatically be set to the current
     * device time.
     */
    public Etape(){
        this(new Date(System.currentTimeMillis()));
    }


    /**
     * Constructs a new Etape object. Note that this will NOT
     * be saved to the local database automatically.
     *
     * @param startDate The starting date of the Etape
     * */
    public Etape( Date startDate){
        this.startDate = startDate;
    }


    public Etape(long id, long togtId, Date startDate, Date endDate) {
        this.id = id;
        this.togtId = togtId;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }


    /**
     * If Date is null, the date will automatically be set
     * to the current date.
     */
    public void setEndDate(Date date){
        endDate = date;
    }

    /**
     * If Date is null, the date will automatically be set
     * to the current date.
     */
    public void setStartDate(Date date){
        startDate = date;
    }


    /**
     * Returns the ID of the Etape. If the Etape hasn't been
     * added to the local database, the id will be -1.
     */
    public long getId(){
        return id;
    }

    /**
     * Should not be used by other than the DAO adding the
     * Etape to the database.
     */
    public void setId(long id){
        this.id = id;
    }



    public long getTogtId() {
        return togtId;
    }

    public void setTogtId(long togtId) {
        this.togtId = togtId;
    }


    @Override
    public String toString(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);

        String startDateString = "";
        if( startDate != null ) {
            startDateString = String.format("%d/%d-%d",
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.YEAR)
            );
        }

        String endDateString = "";
        if( endDate != null){
            cal.setTime(endDate);
            endDateString = String.format("%d/%d-%d",
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.MONTH)+1,
                    cal.get(Calendar.YEAR)
            );
        }

        return String.format( "{Etape{ id: %d, togtId: %d, start: %s, end: %s }",
                id,
                togtId,
                startDateString,
                endDateString
        );
    }


    public boolean equals(Etape e){
        return
                id == e.id &&
                togtId == e.togtId &&
                Objects.equals(startDate, e.startDate) &&
                Objects.equals(endDate, e.endDate);
    }
}
