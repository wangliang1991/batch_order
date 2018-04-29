package com.liang.batchOrder.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimeUtil {

    private static Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);

    public static final String FORMAT_yyyyMMdd = "yyyyMMdd";
    public static final String FORMAT_yyyy_MM_dd = "yyyy-MM-dd";


    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date parseDate(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            logger.error("parse date fail, dateStr={}, format={}.", dateStr, format, e);
            return null;
        }
    }

    public static String addDayByNum(String date, int day) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_yyyy_MM_dd);
        try {
            Date dateTemp = formatter.parse(date);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(dateTemp);
            calendar.add(GregorianCalendar.DAY_OF_MONTH, day);
            SimpleDateFormat format = new SimpleDateFormat(FORMAT_yyyy_MM_dd);
            return format.format(calendar.getTime());
        } catch (ParseException e) {
            logger.error("method addDayByNum ParseException, date={},day={}", date, day, e);
            return date;
        }
    }

    public static Date addDayByNum(Date date, int day) {
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(GregorianCalendar.DAY_OF_MONTH, day);
            return calendar.getTime();
        } catch (Exception e) {
            logger.error("method addDayByNum Exceptioon, date={},day={}", date, day, e);
            return date;
        }
    }

    /**
     * 获取当天的时间，时分秒和毫秒均为0
     * @return
     */
    public static Date getCurrentDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取两天相差天数
     * @param startDate 起始时间
     * @param endDate 截止时间
     * @return 相隔天数，正值
     */
    public static int getIntervalDay(Date startDate, Date endDate) {
        Calendar d1 = new GregorianCalendar();
        d1.setTime(startDate);
        Calendar d2 = new GregorianCalendar();
        d2.setTime(endDate);

        long ei = d2.getTimeInMillis() - d1.getTimeInMillis();
        return Math.abs((int) (ei / (1000 * 60 * 60 * 24)));
    }

    /**
     * only date interval
     *
     * @param startDate
     * @param endDate
     * @return
     */

    public static int getOnlyDayInterval(Date startDate, Date endDate) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(startDate);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(endDate);
        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        return (int) ((cal2.getTimeInMillis() - cal1.getTimeInMillis()) / (1000L * 3600 * 24));
    }
}

