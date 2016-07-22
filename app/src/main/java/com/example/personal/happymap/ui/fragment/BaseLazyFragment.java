package com.example.personal.happymap.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dell on 2016/7/14.
 */
public abstract class BaseLazyFragment extends Fragment {


    protected boolean isViewInitialized;
    protected boolean isVisibleToUser;
    protected boolean isDataInitialized;

    public BaseLazyFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitialized = true;
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    public boolean prepareFetchData(){
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate){
        if(isViewInitialized&&isVisibleToUser&&(!isDataInitialized||forceUpdate)){
            fetchData();
            isDataInitialized = true;
            return true;
        }else {
            return false;
        }
    }

    //加载数据
    public abstract void fetchData();

    //初始化view
    public abstract void initView();

    //设置监听事件
    public abstract void initListener();
}
