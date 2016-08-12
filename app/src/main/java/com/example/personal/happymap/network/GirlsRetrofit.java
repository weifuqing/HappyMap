package com.example.personal.happymap.network;

import com.example.personal.happymap.global.HappyMapApplication;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dell on 2016/8/9.
 */
public class GirlsRetrofit {

    private static Retrofit retrofit;
    public static Retrofit getRetrofit(){

        if(retrofit==null){
            synchronized (GirlsRetrofit.class){
                if(retrofit==null){
                    retrofit = new Retrofit.Builder()
                            .baseUrl(Netaddress.girls)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(HappyMapApplication.defaultOkHttpClient())
                            .build();
                }
            }
        }

        return retrofit;
    }
}
