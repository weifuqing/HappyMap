package com.example.personal.happymap.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;


/**
 * Created by dell on 2016/7/21.
 */
public class DialogUtil {

    public static void showDialog(Context context,String title,String message,DialogInterface.OnClickListener positiveListener,DialogInterface.OnClickListener negativeListener){
        new AlertDialog.Builder(context).setTitle(title)
                .setMessage(message)
                .setNegativeButton("取消", negativeListener)
                .setPositiveButton("确定",positiveListener)
                .setCancelable(false)
                .show();
    }

    public static AlertDialog creatDialog(Context context,View view){
        AlertDialog.Builder builder =new AlertDialog.Builder(context);
        AlertDialog alertDialog = builder.create();
        alertDialog.setView(view);
        return alertDialog;
    }
}
