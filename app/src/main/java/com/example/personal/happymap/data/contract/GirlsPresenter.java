package com.example.personal.happymap.data.contract;

import com.example.personal.happymap.data.bean.GirlsBean;
import com.example.personal.happymap.data.source.GirlsDataSource;
import com.example.personal.happymap.data.source.GirlsResponse;

/**
 * Created by dell on 2016/8/16.
 */
public class GirlsPresenter implements GirlsContract.Presenter {

    public static final String TAG = "GirlsPresenter";

    private GirlsContract.View mView;
    private GirlsResponse mResponse;

    public GirlsPresenter(GirlsContract.View mView) {
        this.mView = mView;
        mResponse = new GirlsResponse();
    }

    @Override
    public void getGrils(int page, int size, final boolean isRefresh) {
        mResponse.getGirls(page, size, new GirlsDataSource.LoadGirlsCallback() {
            @Override
            public void onGrilsLoaded(GirlsBean girlsBean) {
                if (isRefresh) {
                    mView.refresh(girlsBean.getResults());
                } else {
                    mView.load(girlsBean.getResults());
                }
                mView.showNormal();
            }

            @Override
            public void onDataNotAvailable() {
                if (isRefresh)
                    mView.showError();
            }
        });
    }

    @Override
    public void start() {
        getGrils(1, 20, true);
    }
}
