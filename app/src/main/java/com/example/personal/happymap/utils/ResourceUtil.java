package com.example.personal.happymap.utils;

import android.graphics.drawable.Drawable;

import com.example.personal.happymap.R;
import com.example.personal.happymap.global.HappyMapApplication;

/**
 * Created by dell on 2016/7/22.
 */
public class ResourceUtil {

    public static int getColor(int colorId){
        int color = HappyMapApplication.getgContext().getResources().getColor(colorId);
        return color;
    }

    public static Drawable getDrawable(int drawableId){
        Drawable drawable = HappyMapApplication.getgContext().getResources().getDrawable(drawableId);
        return drawable;
    }

}
