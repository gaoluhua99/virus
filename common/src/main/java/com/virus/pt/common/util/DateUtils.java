package com.virus.pt.common.util;

import java.util.Calendar;

public class DateUtils {
    public static String getDatePath() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + PathUtils.SPEA
                + month + PathUtils.SPEA
                + day + PathUtils.SPEA;
    }
}
