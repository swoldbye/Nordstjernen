package com.example.skibslogapp.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateToString {

    /**
     *  Convert a Date to string format using same formatting pattern
     *  as SimpleDateFormat
     *
     * @param pattern Format pattern (see: https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html)
     * @param date The date you want to convert
     * @return String representation of date
     */
    public static String format(String pattern, Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return new SimpleDateFormat(pattern, Locale.UK).format(cal);
    }

    /**
     * Convert Date to String in format hours:minutes */
    public static String time(Date date){
        return format("kk:mm", date);
    }

    /**
     * Convert Date to String in format day/month */
    public static String dayMonth(Date date){
        return format("dd/MM", date);
    }

    /**
     * Convert Date to String in format day/month-year */
    public static String dayMonthYear(Date date){
        return format("dd/MM-yyyy", date);
    }

    public static String full(Date date){
        return dayMonthYear(date) + " " + time(date);
    }
}
