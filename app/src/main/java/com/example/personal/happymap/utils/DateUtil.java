package com.example.personal.happymap.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dell on 2016/7/25.
 */
public class DateUtil {

    public static String longToString(long l){
        Date date = new Date(l);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sd.format(date);
    }

    public static String dateToString(Date date){
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sd.format(date);
    }
}
