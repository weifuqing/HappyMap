package com.example.personal.happymap.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.personal.happymap.R;
import com.example.personal.happymap.data.bean.GirlsBean;
import com.example.personal.happymap.data.contract.GirlsContract;
import com.example.personal.happymap.ui.view.PinchImageView;

import org.w3c.dom.ProcessingInstruction;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by dell on 2016/8/19.
 */
public class GirlAdapter extends PagerAdapter {

    private ArrayList<GirlsBean.ResultEntity> datas;
    private Context context;
    private LayoutInflater layoutInflater;
    private View currentView;

    public GirlAdapter(Context context,ArrayList<GirlsBean.ResultEntity> datas){
        this.context = context;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        currentView = (View) object;
    }

    public View getPrimaryItem(){
        return currentView;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = layoutInflater.inflate(R.layout.item_girl_detail,container);
        String imageUrl = datas.get(position).getUrl();
        PinchImageView pinchImageView = (PinchImageView) view.findViewById(R.id.iv_girl_detail);
        Glide.with(context)
                .load(imageUrl)
                .thumbnail(0.2f)
                .into(pinchImageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        container.removeViewAt(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
