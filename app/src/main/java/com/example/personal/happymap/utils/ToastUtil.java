package com.example.personal.happymap.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by dell on 2016/7/20.
 */
public class ToastUtil {

    private static Toast toast;
    private static Snackbar snackbar;
    public static void show(Context context,String content){
        if(toast==null){
            toast = Toast.makeText(context.getApplicationContext(),content,Toast.LENGTH_SHORT);
        }else {
            toast.setText(content);
        }
        toast.show();
    }

    public static void showSnackbar(View view,String content){
        if(snackbar==null){
            snackbar = Snackbar.make(view,content,Snackbar.LENGTH_SHORT);
        }else {
            snackbar.setText(content);
        }
        snackbar.show();
    }
}
