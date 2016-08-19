package com.example.personal.happymap.data.contract;

import com.example.personal.happymap.BasePresenter;
import com.example.personal.happymap.BaseVeiw;
import com.example.personal.happymap.data.bean.GirlsBean;

import java.util.List;

/**
 * Created by dell on 2016/8/16.
 */
public interface GirlsContract {

    interface View extends BaseVeiw {

        void refresh(List<GirlsBean.ResultEntity> datas);
        
        void load(List<GirlsBean.ResultEntity> datas);

        void showError();

        void showNormal();
    }

    interface Presenter extends BasePresenter{
        void getGrils(int page,int size,boolean isRefresh);
    }
}
