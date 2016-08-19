package com.example.personal.happymap.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.personal.happymap.R;
import com.example.personal.happymap.data.bean.GirlsBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dell on 2016/8/18.
 */
public class GirlActivity extends BaseActivity implements ViewPager.OnPageChangeListener{


    private Unbinder unbinder;

    @BindView(R.id.tb_girl)
    Toolbar tb_girl;
    @BindView(R.id.vp_girls)
    ViewPager vp_girls;

    private ArrayList<GirlsBean.ResultEntity> datas;
    private int current;

    @Override
    void initView() {
        setContentView(R.layout.activity_girl);
        unbinder = ButterKnife.bind(this);

        Intent intent = getIntent();
        datas = intent.getParcelableArrayListExtra("girls");
        current = intent.getIntExtra("current",0);

        tb_girl.setTitle("美女");
        setSupportActionBar(tb_girl);
        tb_girl.setNavigationIcon(R.drawable.ic_back);
    }

    @Override
    void initListener() {

        tb_girl.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
