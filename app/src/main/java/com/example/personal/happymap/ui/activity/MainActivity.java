package com.example.personal.happymap.ui.activity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.personal.happymap.R;
import com.example.personal.happymap.ui.fragment.MapFragment;
import com.example.personal.happymap.ui.fragment.MobFragment;
import com.example.personal.happymap.utils.ToastUtil;

public class MainActivity extends FragmentActivity implements View.OnClickListener{

    private TextView tv_map;
    private TextView tv_mob;
    private TextView tv_girls;

    private Fragment currentFragment,mapFragment, mobFragment,girlsFragment;


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
        tv_girls = (TextView) findViewById(R.id.tv_girls);

    }

    public void initListener(){
        tv_map.setOnClickListener(this);
        tv_mob.setOnClickListener(this);
        tv_girls.setOnClickListener(this);
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
            case R.id.tv_girls:
                if(mobFragment ==null){
                    girlsFragment = new MobFragment();
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


    private long lastTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if(System.currentTimeMillis()-lastTime>2000){
                ToastUtil.showSnackbar(tv_map,"再按一次退出程序");
                lastTime = System.currentTimeMillis();
            }else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
}
