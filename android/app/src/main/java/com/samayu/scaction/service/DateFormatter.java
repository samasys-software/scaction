package com.samayu.scaction.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by NandhiniGovindasamy on 12/10/18.
 */

public class DateFormatter {
    private static final DateFormatter ourInstance = new DateFormatter();

    static DateFormatter getInstance() {
        return ourInstance;
    }


    public static String getMonthDateYearFormat(String currentDate) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(inputFormat.parse(currentDate));
//                SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
//                String inputDateStr=String.valueOf(currentCastingCall.getStartDate());
//                Date date = inputFormat.parse(inputDateStr);
//                String outputDateStr = outputFormat.format(date);
//                System.out.println(outputDateStr);
//

        }
        catch(Exception e){
            return null;
        }
    }

    public static String getMonthDateYearFormat(int year, int month, int day) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");

        return sdf.format(date);
    }
//    public static String getYearMonthDateFormat(int year,int month,int day){
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(0);
//        cal.set(year, month, day);
//        Date date = cal.getTime();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        return sdf.format(date);
//
//    }
    public static String getYearMonthDateFormat(String date){
        try {


            SimpleDateFormat inputFormat = new SimpleDateFormat("MMM-dd-yyyy");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            return sdf.format(inputFormat.parse(date));
        }
        catch (Exception e){
            return null;
        }

    }



}
