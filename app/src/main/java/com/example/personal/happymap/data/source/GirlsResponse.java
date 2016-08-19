package com.example.personal.happymap.data.source;

import com.example.personal.happymap.data.source.local.GirlsLocalDataSource;
import com.example.personal.happymap.data.source.remote.GirlsRemoteDataSource;

/**
 * Created by dell on 2016/8/16.
 */
public class GirlsResponse implements GirlsDataSource{


    private GirlsRemoteDataSource remoteDataSource;
    private GirlsLocalDataSource localDataSource;

    public GirlsResponse() {
        this.remoteDataSource = new GirlsRemoteDataSource();
        this.localDataSource = new GirlsLocalDataSource();
    }

    @Override
    public void getGirls(int page, int size, LoadGirlsCallback callback) {
        remoteDataSource.getGirls(page,size,callback);
    }

    @Override
    public void getGirl(LoadGirlsCallback callback) {
        remoteDataSource.getGirl(callback);
    }
}
