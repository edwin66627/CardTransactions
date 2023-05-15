package com.nexus.utils;

import java.util.Calendar;
import java.util.Date;

public class AppUtility {
    public static Long generateRandomLongNumber(Long bottomLimit, Long topLimit){
        return bottomLimit + (long) (Math.random() * (topLimit - bottomLimit));
    }

    public static Date addYearsToDate(Calendar date, int yearsToAdd){
        date.add(Calendar.YEAR, 3);
        return date.getTime();
    }

}
