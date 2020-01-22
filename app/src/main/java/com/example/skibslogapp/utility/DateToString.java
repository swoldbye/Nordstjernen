package com.example.skibslogapp.utility;

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
        if( date == null ) return "";
        return new SimpleDateFormat(pattern, Locale.UK).format(date);
    }

    /**
     * Convert Date to String in format hours:minutes */
    public static String time(Date date){
        return format("HH:mm", date);
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
