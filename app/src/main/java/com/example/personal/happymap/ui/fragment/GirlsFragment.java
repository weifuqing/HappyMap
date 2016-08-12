package com.example.personal.happymap.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.personal.happymap.R;

/**
 * Created by dell on 2016/8/8.
 */
public class GirlsFragment extends BaseLazyFragment{

    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_girls,container);
        initView();
        initListener();
        return view;
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }
}
