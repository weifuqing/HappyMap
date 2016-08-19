package com.example.personal.happymap.data.source.remote;

import com.example.personal.happymap.data.bean.GirlsBean;
import com.example.personal.happymap.data.contract.GirlsContract;
import com.example.personal.happymap.data.contract.GirlsPresenter;
import com.example.personal.happymap.data.source.GirlsDataSource;
import com.example.personal.happymap.network.GirlsRetrofit;
import com.example.personal.happymap.network.GirlsService;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dell on 2016/8/16.
 */
public class GirlsRemoteDataSource implements GirlsDataSource{

    @Override
    public void getGirls(int page, int size, final LoadGirlsCallback callback) {
        GirlsRetrofit.getRetrofit()
                .create(GirlsService.class)
                .getGirls("福利",size,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GirlsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onDataNotAvailable();
                    }

                    @Override
                    public void onNext(GirlsBean girlsBean) {
                        callback.onGrilsLoaded(girlsBean);
                    }
                });
    }

    @Override
    public void getGirl(LoadGirlsCallback callback) {
        getGirls(1,1,callback);
    }
}
