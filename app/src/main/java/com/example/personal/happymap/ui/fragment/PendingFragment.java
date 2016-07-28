package com.example.personal.happymap.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.personal.happymap.R;

/**
 * Created by dell on 2016/7/27.
 */
public class PendingFragment extends BaseLazyFragment {

    View view;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pending,null);
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
