package com.example.personal.happymap.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.personal.happymap.R;
import com.example.personal.happymap.ui.fragment.GirlsFragment;
import com.example.personal.happymap.ui.fragment.MapFragment;
import com.example.personal.happymap.ui.fragment.MobFragment;
import com.example.personal.happymap.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_map;
    private TextView tv_mob;
    private TextView tv_girls;

    private Fragment currentFragment,mapFragment, mobFragment,girlsFragment;

    Unbinder unbinder;

    @BindView(R.id.tb_home)
    Toolbar tb_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        initView();
        initListener();
        tv_map.callOnClick();
//        tv_mob.callOnClick();
    }

    public void initView(){
        tv_map = (TextView) findViewById(R.id.tv_map);
        tv_mob = (TextView) findViewById(R.id.tv_mob);
        tv_girls = (TextView) findViewById(R.id.tv_girls);
        tb_home.setTitle("HappyMap");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_about:
                startActivity(new Intent(this,AboutMeActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

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
                if(girlsFragment ==null){
                    girlsFragment = new GirlsFragment();
                }
                changeFragment(getSupportFragmentManager().beginTransaction(), girlsFragment,"girls");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
