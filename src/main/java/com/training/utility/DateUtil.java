package com.training.utility;

import java.util.Date;
import java.util.Calendar;

public class DateUtil {
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    public static Date getCurrentDate() {
        return new Date();
    }
}