package com.example.skibslogapp.model;

import java.util.Calendar;
import java.util.Date;

public class Etape {

    private int id = -1;
    private Date startDate = null;
    private Date endDate = null;

    public Etape(int id, Date startDate, Date endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Etape(){
        startDate = new Date( System.currentTimeMillis() );
        System.out.println( "Created new etape with start date: "  + startDate.toString() );
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
        if( date == null ){
            endDate = new Date(System.currentTimeMillis());
        }else{
            endDate = date;
        }
    }

    public int getId(){
        return id;
    }

    @Override
    public String toString(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);

        String startDateString = String.format("%d/%d-%d",
                cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.MONTH)+1,
                cal.get(Calendar.YEAR)
                );

        cal.setTime(endDate);
        String endDateString = String.format("%d/%d-%d",
                cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.MONTH)+1,
                cal.get(Calendar.YEAR)
        );

        return String.format( "{Etape{ id: %d, start: %s, end: %s }",
                id,
                startDateString,
                endDateString
        );
    }
}
