package com.example.personal.happymap.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.personal.happymap.R;
import com.example.personal.happymap.ui.fragment.MapFragment;
import com.example.personal.happymap.ui.fragment.MobFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_map;
    private TextView tv_mob;

    private Fragment currentFragment,mapFragment, mobFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initView();
        initListener();
        tv_map.callOnClick();
//        tv_mob.callOnClick();
    }

    public void initView(){
        tv_map = (TextView) findViewById(R.id.tv_map);
        tv_mob = (TextView) findViewById(R.id.tv_mob);

    }

    public void initListener(){
        tv_map.setOnClickListener(this);
        tv_mob.setOnClickListener(this);
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
                changeFragment(getSupportFragmentManager().beginTransaction(),mapFragment,"map");
                break;
            case R.id.tv_mob:
                if(mobFragment ==null){
                    mobFragment = new MobFragment();
                }
                changeFragment(getSupportFragmentManager().beginTransaction(), mobFragment,"mob");
                break;
        }
    }

    public void changeFragment(FragmentTransaction transaction,Fragment fragment,String tab){
        if(currentFragment == fragment){
            return;
        }if(currentFragment==null){
            transaction.replace(R.id.rl_content,fragment).commit();
            currentFragment = fragment;
            return;
        }
        if(!fragment.isAdded()){
            transaction.hide(currentFragment).add(R.id.rl_content,fragment,tab).commit();
        }else {
            transaction.hide(currentFragment).show(fragment).commit();
        }
        currentFragment = fragment;
    }
}
