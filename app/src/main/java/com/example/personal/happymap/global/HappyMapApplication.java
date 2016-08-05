package com.example.personal.happymap.global;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.example.personal.happymap.utils.FileUtil;

import java.io.File;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by dell on 2016/7/15.
 */
public class HappyMapApplication extends Application {

    private static Context gContext;
    private static final String APPLICATION_NAME = "HappyMap";

    @Override
    public void onCreate() {
        super.onCreate();
        gContext = this;
        //地图初始化
        SDKInitializer.initialize(this);
        //shareSdk初始化
        ShareSDK.initSDK(this);
        if(!FileUtil.SDCARD.exists()){
            FileUtil.SDCARD.mkdirs();
        }
        if(!FileUtil.PICTURE.exists()){
            FileUtil.PICTURE.mkdirs();
        }
    }

    public static Context getgContext() {
        return gContext;
    }

    public static String getApplicationName(){
        return APPLICATION_NAME;
    }
}
