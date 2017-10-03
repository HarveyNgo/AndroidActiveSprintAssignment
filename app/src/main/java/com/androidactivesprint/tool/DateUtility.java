package com.androidactivesprint.tool;

/**
 * Created by Hung on 10/3/2017.
 */


import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Hung on 8/8/2017.
 */

public class DateUtility {

    private static Calendar currentCalendar = Calendar.getInstance();


    public static int getCurrentDayOfMonth() {
        return currentCalendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getCurrentMonth() {
        return currentCalendar.get(Calendar.MONTH);
    }

    public static int getCurrentYear() {
        return currentCalendar.get(Calendar.YEAR);
    }

    public static String formatDate(Date date, DateFormatDefinition format) {
        return formatDate(date, format.getFormat());
    }

    public static String formatDate(int year, int month, int day, DateFormatDefinition format) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return formatDate(calendar.getTime(), format);
    }

    private static String formatDate(Date date, String format) {
        if (date == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static Date toDate(String str, DateFormatDefinition format) {
        return parse(str, format.getFormat());
    }

    public static Date toLocalTimeZoneDate(String str, DateFormatDefinition format) {
        if (str == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format.getFormat());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        sdf.setLenient(false);
        return sdf.parse(str, new ParsePosition(0));
    }

    public static Date toLocalTimeZoneDate(Date date, DateFormatDefinition format) {
        String str = formatDate(date, format);
        if (str == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format.getFormat());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        sdf.setLenient(false);
        return sdf.parse(str, new ParsePosition(0));
    }

    public static Date toDate(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        return calendar.getTime();
    }

    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    public static int getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    private static Date parse(String src, String format) {
        if (src == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        return sdf.parse(src, new ParsePosition(0));
    }

    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

//    public static Date getCurrentDate() {
//        //DateFormat dateFormat = loading_new SimpleDateFormat("yyyy/MM/dd");
//        Date date = loading_new Date();
//        return date;
//    }

    public static int getAge(int year, int month, int day) {
        Calendar getAgeCal = Calendar.getInstance();
        getAgeCal.set(year, month, day);
        Calendar today = Calendar.getInstance();


        int age = today.get(Calendar.YEAR) - getAgeCal.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < getAgeCal.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }

    public static String dayIntervalAgo(String dateTime) {
        String dayAgo = "";
        try {
            String prefixDate = dateTime.substring(0, 19);
            String subfixDate = dateTime.substring(dateTime.length()-6, dateTime.length());
            dateTime=prefixDate+subfixDate;
            System.out.println("dateTime: "+dateTime);
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            Date date = dateformat.parse(dateTime);
            Date now = new Date();
            CharSequence ago =
                    DateUtils.getRelativeTimeSpanString(date.getTime(), now.getTime(), DateUtils.SECOND_IN_MILLIS);
            dayAgo = ago.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dayAgo;
    }


    public enum DateFormatDefinition {
        YYYY("yyyy"),
        MM("MM"),
        DD("dd"),
        YY_MM("yy/MM"),
        YYYY_MM("yyyy/MM"),
        YYYY_MM_NO_SLASH("yyyyMM"),
        MM_DD("MM/dd"),
        MM_DD_YYYY("MM/dd/yyyy"),
        DD_MMM_YYYY("dd MMM yyyy"),
        MM_DD_NO_SLASH("MMdd"),
        YY_MM_DD("yy/MM/dd"),
        YYYY_MM_DD("yyyy/MM/dd"),
        YYYY_MM_DD_NO_SLASH("yyyyMMdd"),
        HH_MM("HH:mm"),
        HH_MM_SS("HH:mm:ss"),
        YYYY_MM_DD_HH_MM("yyyy/MM/dd HH:mm"),
        MM_DD_YYYY_HH_MM("MM/dd/yyyy HH:mm"),
        YYYY_MM_DD_HH_MM_SS("yyyy/MM/dd HH:mm:ss"),
        YYYY_MM_DD_HH_MM_NO_SLASH("yyyyMMddHHmm"),
        YYYY_MM_DD_HH_MM_SS_NO_SLASH("yyyyMMddHHmmss"),
        YYYY_MM_DD_HH_MM_SS_SSS("yyyy/MM/dd HH:mm:ss SSS"),
        YYYY_MM_DD_HH_MM_SS_SSS_NO_SLASH("yyyyMMddHHmmssSSS"),
        YYYY_MM_DD_HYPHEN("yyyy-MM-dd"),
        YYYY_MM_DD_T_HH_MM_SS_Z("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'"),
        YYYY_MM_DD_T_HH_MM_SS_POWER("yyyy-MM-dd'T'HH:mm:ssZ"),
        DD_MMMM_YYYY("dd MMMM yyyy"),
        YYYY_MM_DD_T_HH_MM_SS("yyyy-MM-dd'T'HH:mm:ss"),
        DD_MMM_at_H_MM_A("dd MMM 'at' h:mm a");

        private String format;

        DateFormatDefinition(String format) {
            this.format = format;
        }

        public String getFormat() {
            return format;
        }

        @Override
        public String toString() {
            return format;
        }
    }

}
