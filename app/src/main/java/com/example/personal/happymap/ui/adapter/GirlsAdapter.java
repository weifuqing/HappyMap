package com.example.personal.happymap.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.personal.happymap.R;
import com.example.personal.happymap.data.bean.GirlsBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

/**
 * Created by dell on 2016/8/18.
 */
public class GirlsAdapter extends RecyclerArrayAdapter<GirlsBean.ResultEntity>{
    public GirlsAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new GirlsViewHolder(parent);
    }

    class GirlsViewHolder extends BaseViewHolder<GirlsBean.ResultEntity>{

        ImageView iv_girl;

        public GirlsViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_girl );
            iv_girl = $(R.id.iv_girl);
        }

        @Override
        public void setData(GirlsBean.ResultEntity data) {
            super.setData(data);
            Glide.with(getContext())
                    .load(data.getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(iv_girl);
        }
    }
}
