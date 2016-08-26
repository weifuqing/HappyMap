package com.example.personal.happymap.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.personal.happymap.R;
import com.example.personal.happymap.data.bean.GirlsBean;
import com.example.personal.happymap.ui.adapter.GirlAdapter;
import com.example.personal.happymap.ui.view.PinchImageView;
import com.example.personal.happymap.utils.BitmapUtil;
import com.example.personal.happymap.utils.FileUtil;
import com.example.personal.happymap.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by dell on 2016/8/18.
 */
public class GirlActivity extends BaseActivity implements ViewPager.OnPageChangeListener {


    private Unbinder unbinder;

    @BindView(R.id.tb_girl)
    Toolbar tb_girl;
    @BindView(R.id.vp_girls)
    ViewPager vp_girls;

    private ArrayList<GirlsBean.ResultEntity> datas;
    private int current;
    private GirlAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }

    @Override
    void initView() {
        setContentView(R.layout.activity_girl);
        unbinder = ButterKnife.bind(this);

        Intent intent = getIntent();
        datas = intent.getParcelableArrayListExtra("girls");
        current = intent.getIntExtra("current", 0);

        tb_girl.setTitle("美女");
        setSupportActionBar(tb_girl);
        tb_girl.setNavigationIcon(R.drawable.ic_back);

        mAdapter = new GirlAdapter(this, datas);
        vp_girls.setAdapter(mAdapter);
        vp_girls.setCurrentItem(current);
        vp_girls.setOnPageChangeListener(this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_girl, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_share:
                shareGirl();
                return true;
            case R.id.action_save:
                saveGirl();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 根据图片获得主题色
     */
    private void changeColor() {
        PinchImageView imageView = getCurrentImageView();
        Bitmap bitmap = BitmapUtil.drawableToBitmap(imageView.getDrawable());
        Palette.Builder builder = Palette.from(bitmap);
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch vir = palette.getVibrantSwatch();
                if (vir == null) {
                    return;
                }
                tb_girl.setBackgroundColor(vir.getRgb());
            }
        });
    }

    private void saveGirl() {
        String imageUrl = datas.get(current).getUrl();
        PinchImageView imageView = getCurrentImageView();
        Bitmap bitmap = BitmapUtil.drawableToBitmap(imageView.getDrawable());
        Observable.just(BitmapUtil.saveBitmap(bitmap, FileUtil.PICTURE.getAbsolutePath(), imageUrl.substring(imageUrl.lastIndexOf('/') + 1,
                imageUrl.length()), true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            ToastUtil.showSnackbar(vp_girls, "下载完成");
                        } else {
                            ToastUtil.showSnackbar(vp_girls, "下载失败");
                        }
                    }
                });
    }

    private void shareGirl() {
        PinchImageView imageView = getCurrentImageView();
        Bitmap bitmap = BitmapUtil.drawableToBitmap(imageView.getDrawable());
        Observable.just(BitmapUtil.saveBitmap(bitmap, FileUtil.PICTURE.getAbsolutePath(), "share.jpg", false))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            Uri uri = Uri.fromFile(new File(FileUtil.PICTURE, "share.jpg"));
                            Intent shareIntent = new Intent();
                            shareIntent.setAction(Intent.ACTION_SEND);
                            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                            shareIntent.setType("image/*");
                            startActivity(Intent.createChooser(shareIntent, "分享美女到"));
                        } else {
                            ToastUtil.showSnackbar(vp_girls, "分享失败");
                        }
                    }
                });
    }

    private PinchImageView getCurrentImageView() {
        View currentItem = mAdapter.getPrimaryItem();
        if (currentItem == null) {
            return null;
        }
        PinchImageView imageView = (PinchImageView) currentItem.findViewById(R.id.iv_girl_detail);
        if (imageView == null) {
            return null;
        }
        return imageView;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        current = position;
        changeColor();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
