package com.example.personal.happymap.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dell on 2016/9/12.
 */
public class CacheUtil {

    static SharedPreferences sp;
    private static final String TAG = "happy_map";

    public static void putString(Context context,String key,String value){
        sp = context.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context,String key){
        sp = context.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        return sp.getString(key,null);
    }

    public static void putBoolean(Context context,String key,boolean value){
        sp = context.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context,String key){
        sp = context.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    public static void putInt(Context context,String key,int value){
        sp = context.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(Context context,String key){
        sp = context.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        return sp.getInt(key, -1);
    }
}
