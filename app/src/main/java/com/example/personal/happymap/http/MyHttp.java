package com.example.personal.happymap.http;

import com.example.personal.happymap.global.HappyMapApplication;
import com.example.personal.happymap.utils.ToastUtil;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.http.Url;

/**
 * Created by dell on 2016/9/13.
 */
public class MyHttp{



    public void get(String urls){
        URL url = null;
        HttpURLConnection conn;
        OutputStream os;
        try {
            url = new URL(urls);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();
            os = conn.getOutputStream();

        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.show(HappyMapApplication.getgContext(),"网络连接异常");
        }finally {

        }
    }

    public void post(){

    }
}
