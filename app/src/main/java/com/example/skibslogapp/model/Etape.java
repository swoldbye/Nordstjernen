package com.example.skibslogapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Etape {


    private long id = -1;
    private long togtId = -1;
    private Date startDate = null;
    private Date endDate = null;
    private String startDestination = null;
    private String slutDestination = null;
    private String skipper = null;
    private List<String> besaetningList = new ArrayList<>() ;

    private int status = Status.NEW;

    public class Status{
        public static final int NEW = 0;
        public static final int ACTIVE = 1;
        public static final int FINISHED = 2;
    }


    public List<String> getBesaetning() {
        return besaetningList;
    }

    public void setBesaetning(List<String> besaetningList) {
        this.besaetningList = besaetningList;
    }

    public void addBesaetningsMedlem(String ... navne){
        besaetningList.addAll(Arrays.asList(navne));
    }

    public boolean removeBesaetningsMedlem(String navn){
        return besaetningList.remove(navn);
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


    public Etape(long id, long togtId, Date startDate, Date endDate, String startDestination) {
        this.id = id;
        this.togtId = togtId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startDestination = startDestination;
    }


    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }


    /**
     * Copies the given date object, and sets it as the end date.
     */
    public void setEndDate(Date date){
        endDate = new Date(date.getTime());
    }

    /**
     * Copies the given date object, and sets it as the start date.
     */
    public void setStartDate(Date date){
        startDate = new Date(date.getTime());
    }


    public String getSlutDestination() {
        return slutDestination;
    }

    public void setSlutDestination(String slutDestination) {
        this.slutDestination = slutDestination;
    }

    public String getSkipper() {
        return skipper;
    }

    public void setSkipper(String skipper) {
        this.skipper = skipper;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status){
        if( status < 0 || status > 2)
            throw new RuntimeException(String.format("Etape Status ID %d is out of bounds (must be between 0 and 2)", status));
        this.status = status;
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

    public void setStartDestination(String startDestination){
        this.startDestination = startDestination;
    }

    public String getStartDestination(){
        return startDestination;
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
