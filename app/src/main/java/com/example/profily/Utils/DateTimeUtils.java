package com.example.profily.Utils;


import java.util.Date;

public class DateTimeUtils {

    public static String getFormattedElapsedTime(Date from) {
        return getFormattedElapsedTime(from, new Date());
    }

    public static String getFormattedElapsedTime(Date from, Date to) {
        long elapsedTime = to.getTime() - from.getTime();

        // Seconds
        long seconds = elapsedTime / 1000;
        if (seconds < 60) {
            return "" + seconds + " sec";
        }
        // Minutes
        long minutes = seconds / 60;
        if (minutes < 60) {
            return "" + minutes + " min";
        }
        // Hours
        long hours = minutes / 60;
        if (hours < 24) {
            return "" + hours + " hour" + getPluralForm(hours);
        }
        // Days
        long days = hours / 24;
        if (days < 31) {
            return "" + days + " day" + getPluralForm(days);
        }
        // Months
        long months = days / 31;
        if (months < 12) {
            return "" + months + " month" + getPluralForm(months);
        }
        // Years
        long years = months / 12;
        return "" + years + " year" + getPluralForm(years);
    }

    private static String getPluralForm(long number) {
        return number > 1 ? "s" : "";
    }
}
