package com.example.personal.happymap.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.example.personal.happymap.R;
import com.example.personal.happymap.data.bean.GirlsBean;
import com.example.personal.happymap.data.contract.GirlsContract;
import com.example.personal.happymap.data.contract.GirlsPresenter;
import com.example.personal.happymap.ui.activity.GirlActivity;
import com.example.personal.happymap.ui.adapter.GirlsAdapter;
import com.example.personal.happymap.utils.ToastUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dell on 2016/8/8.
 */
public class GirlsFragment extends BaseLazyFragment implements GirlsContract.View,RecyclerArrayAdapter.OnLoadMoreListener,SwipeRefreshLayout.OnRefreshListener {

    private View view;
    private Unbinder unbinder;

    private ArrayList<GirlsBean.ResultEntity> list;
    private GirlsAdapter mAdapter;
    private GirlsPresenter mPresenter;

    int page = 1;
    int size = 20;

    @BindView(R.id.rv_girls)
    EasyRecyclerView rv_girls;
    @BindView(R.id.vs_error)
    ViewStub vs_error;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_girls,null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        mPresenter = new GirlsPresenter(this);
        mPresenter.start();
        initListener();
        return view;
    }

    @Override
    public void fetchData() {
    }

    @Override
    public void initView() {
        list = new ArrayList<>();
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rv_girls.setLayoutManager(manager);

        mAdapter = new GirlsAdapter(getContext());
        rv_girls.setAdapter(mAdapter);

        mAdapter.setMore(R.layout.layout_load_more, this);
        mAdapter.setNoMore(R.layout.layout_no_more);
        mAdapter.setError(R.layout.layout_error);
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), GirlActivity.class);
                intent.putParcelableArrayListExtra("girls", list);
                intent.putExtra("current", position);
                startActivity(intent);
            }
        });

        rv_girls.setRefreshListener(this);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void refresh(List<GirlsBean.ResultEntity> datas) {
        list.clear();
        list.addAll(datas);
        mAdapter.clear();
        mAdapter.addAll(datas);
    }

    @Override
    public void load(List<GirlsBean.ResultEntity> datas) {
        list.addAll(datas);
        mAdapter.addAll(datas);
    }

    @Override
    public void showError() {
        rv_girls.showError();

        if(vs_error!=null){
            vs_error.setVisibility(View.VISIBLE);
            return;
        }
    }

    @Override
    public void showNormal() {
        if(vs_error!=null){
            vs_error.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadMore() {
        if(list.size()%20==0){
            page++;
            mPresenter.getGrils(page,size,false);
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.getGrils(1,size,true);
        page = 1;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
