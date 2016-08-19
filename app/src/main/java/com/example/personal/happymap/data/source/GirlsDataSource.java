package com.example.personal.happymap.data.source;

import com.example.personal.happymap.data.bean.GirlsBean;

/**
 * Created by dell on 2016/8/16.
 */
public interface GirlsDataSource {
    interface LoadGirlsCallback{
        void onGrilsLoaded(GirlsBean girlsBean);
        void onDataNotAvailable();
    }

    void getGirls(int page, int size, LoadGirlsCallback callback);

    void getGirl(LoadGirlsCallback callback);
}
