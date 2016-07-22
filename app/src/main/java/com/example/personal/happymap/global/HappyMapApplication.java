package com.example.personal.happymap.global;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by dell on 2016/7/15.
 */
public class HappyMapApplication extends Application {

    private static Context gContext;

    @Override
    public void onCreate() {
        super.onCreate();
        gContext = this;
        //地图初始化
        SDKInitializer.initialize(this);
    }

    public static Context getgContext() {
        return gContext;
    }
}
