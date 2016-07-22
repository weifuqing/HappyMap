package com.example.personal.happymap.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by dell on 2016/7/20.
 */
public class ToastUtil {

    private static Toast toast;
    public static void show(Context context,String content){
        if(toast==null){
            toast = Toast.makeText(context.getApplicationContext(),content,Toast.LENGTH_SHORT);
        }else {
            toast.setText(content);
        }
        toast.show();
    }
}
