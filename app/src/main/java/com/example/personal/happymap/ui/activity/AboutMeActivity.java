package com.example.personal.happymap.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.personal.happymap.R;
import com.example.personal.happymap.network.Netaddress;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by dell on 2016/8/24.
 */
public class AboutMeActivity extends BaseActivity {

    private Unbinder unbinder;


    @BindView(R.id.tb_about)
    Toolbar tb_about;
    @BindView(R.id.iv_about_title)
    ImageView iv_about_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }

    @Override
    void initView() {
        setContentView(R.layout.activity_aboutme);
        unbinder = ButterKnife.bind(this);

        tb_about.setTitle("关于我");
        tb_about.setNavigationIcon(R.drawable.ic_back);

        Glide.with(this)
                .load(Netaddress.currentGirl)
                .bitmapTransform(new BlurTransformation(this, 15))//毛玻璃效果
                .into(iv_about_title);

    }

    @Override
    void initListener() {
        tb_about.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
    }
}
