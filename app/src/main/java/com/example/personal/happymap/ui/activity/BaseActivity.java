package com.example.personal.happymap.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dell on 2016/8/18.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        initView();
        initListener();
    }

    abstract void initView();
    abstract void initListener();
}
