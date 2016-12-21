package com.example.personal.happymap.ui.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by dell on 2016/12/20.
 */
public class NetImageView  extends ImageView{
    public NetImageView(Context context) {
        this(context,null);
    }

    public NetImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NetImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //设置src
    public void setImageUrl(Activity activity,String imageUrl,int errorImageId){
        Glide.with(activity)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//全尺寸缓存
                .error(errorImageId)
                .into(this);
    }
    public void setImageUrl(Fragment fragment,String imageUrl,int errorImageId){
        Glide.with(fragment)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//全尺寸缓存
                .error(errorImageId)
                .into(this);
    }
}
