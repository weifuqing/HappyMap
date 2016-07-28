package com.example.personal.happymap.ui.activity;

import android.annotation.SuppressLint;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.personal.happymap.R;
import com.example.personal.happymap.ui.fragment.MapFragment;
import com.example.personal.happymap.ui.fragment.PendingFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private RelativeLayout mContentView;
    private View mControlsView;
    private TextView tv_map;
    private TextView tv_pending;

    private Fragment currentFragment,mapFragment,pendingFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initView();
        initListener();
        tv_map.callOnClick();
    }

    public void initView(){
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = (RelativeLayout) findViewById(R.id.rl_content);
        tv_map = (TextView) findViewById(R.id.tv_map);
        tv_pending = (TextView) findViewById(R.id.tv_pending);

    }

    public void initListener(){
        tv_map.setOnClickListener(this);
        tv_pending.setOnClickListener(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.tv_map:
                if(mapFragment==null){
                    mapFragment = new MapFragment();
                }
                changeFragment(getSupportFragmentManager().beginTransaction(),mapFragment);
                break;
            case R.id.tv_pending:
                if(pendingFragment==null){
                    pendingFragment = new PendingFragment();
                }
                changeFragment(getSupportFragmentManager().beginTransaction(),pendingFragment);
                break;
        }
    }

    public void changeFragment(FragmentTransaction transaction,Fragment fragment){
        if(currentFragment == fragment){
            return;
        }if(currentFragment==null){
            transaction.replace(R.id.rl_inner,fragment).commit();
            currentFragment = fragment;
            return;
        }
        if(!fragment.isAdded()){
            transaction.hide(currentFragment).add(R.id.rl_inner,fragment).commit();
        }else {
            transaction.hide(currentFragment).show(fragment).commit();
        }
        currentFragment = fragment;
    }
}
