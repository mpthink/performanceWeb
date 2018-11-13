/******************************************************************************
 *                       COPYRIGHT 2002 - 2012 BY DELL INC.
 *                          ALL RIGHTS RESERVED
 *
 * THIS DOCUMENT OR ANY PART OF THIS DOCUMENT MAY NOT BE REPRODUCED WITHOUT
 * WRITTEN PERMISSION FROM DELL INC.
 *****************************************************************************/

package com.dell.petshow.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author map6
 */
public class DateTimeUtil {

    private final static String defaultTimeFormatter = "yyyy-MM-dd HH:mm:ss";

    /**
     * Time Formatter should be yyyy-MM-dd HH:mm:ss
     * @param beginTime
     * @param endTime
     * @return
     */
    public static long countDaysBetweenTwoTimesWithDefaultFormatter(String beginTime, String endTime){
        return countDaysBetweenTwoTimes(beginTime,endTime,defaultTimeFormatter);
    }

    public static long countDaysBetweenTwoTimes(String beginTime, String endTime, String timeFormatter){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(timeFormatter);
        LocalDateTime time1 = LocalDateTime.parse(beginTime,df);
        LocalDateTime time2 = LocalDateTime.parse(endTime,df);
        return time1.until(time2, ChronoUnit.DAYS);
    }

    public static LocalDateTime pareWithDefaultFormatter(String time){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(defaultTimeFormatter);
        return LocalDateTime.parse(time, df);
    }

    public static LocalDateTime dateToLocalDateTime(Date date){
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    public static String localDateTimeToStringWithDefaultFormatter(LocalDateTime time){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(defaultTimeFormatter);
        return df.format(time);
    }

    public static String getCurrentChinaDateWithTimezone(){
        Instant now = Instant.now();
        ZoneId zoneId = ZoneId.of("GMT+5");
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(now, zoneId);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return df.format(zonedDateTime);
    }

    /**
     * @args command line arguments
     */
    public static void main(String[] args) {
        System.out.println(getCurrentChinaDateWithTimezone());
    }

}
