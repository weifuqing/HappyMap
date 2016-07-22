package com.example.personal.happymap.ui.fragment;

import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.personal.happymap.MapUtils.MyPoiOverlay;
import com.example.personal.happymap.R;
import com.example.personal.happymap.utils.DialogUtil;
import com.example.personal.happymap.utils.ToastUtil;

/**
 * Created by dell on 2016/7/14.
 */
public class MapFragment extends BaseLazyFragment implements View.OnClickListener {

    private MapView mapView;
    private View view_fragment;
    private Button bt_normal;
    private Button bt_traffic;
    private Button bt_heat;
    private Button bt_satellite;
    private Button bt_search;

    private BaiduMap mBaiduMap;
    private boolean isTraffic;
    private boolean isHeat;

    private PoiSearch poiSearch;
    private OnGetPoiSearchResultListener poiListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        view_fragment = inflater.inflate(R.layout.fragment_map, null);

        initView();
        initListener();
        return view_fragment;
    }

    @Override
    public void initView() {
        mapView = (MapView) view_fragment.findViewById(R.id.bmapView);
        mBaiduMap = mapView.getMap();
        mapView.setLogoPosition(LogoPosition.logoPostionRightBottom);


        bt_heat = (Button) view_fragment.findViewById(R.id.bt_heat);
        bt_traffic = (Button) view_fragment.findViewById(R.id.bt_traffic);
        bt_normal = (Button) view_fragment.findViewById(R.id.bt_normal);
        bt_satellite = (Button) view_fragment.findViewById(R.id.bt_satellite);
        bt_search = (Button) view_fragment.findViewById(R.id.bt_search);
    }

    @Override
    public void initListener() {
        bt_heat.setOnClickListener(this);
        bt_satellite.setOnClickListener(this);
        bt_normal.setOnClickListener(this);
        bt_traffic.setOnClickListener(this);
        bt_search.setOnClickListener(this);

        poiSearch = PoiSearch.newInstance();
        if(poiListener==null) {
            poiListener = new MyPoiListener();
            poiSearch.setOnGetPoiSearchResultListener(poiListener);
        }
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_heat:
                mBaiduMap.setBaiduHeatMapEnabled(isHeat=!isHeat);
                break;
            case R.id.bt_normal:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case R.id.bt_satellite:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.bt_traffic:
                mBaiduMap.setTrafficEnabled(isTraffic=!isTraffic);
                break;
            case R.id.bt_search:
                showSearchDialog();
                break;
        }
    }

    //Poi检索监听
    class MyPoiListener implements OnGetPoiSearchResultListener {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if(poiResult==null||poiResult.error== SearchResult.ERRORNO.RESULT_NOT_FOUND){
                ToastUtil.show(getActivity(),"未找到结果");
                return;
            }
            if(poiResult.error==SearchResult.ERRORNO.NO_ERROR){
                mBaiduMap.clear();
                //创建PoiOverlay
                PoiOverlay overlay = new MyPoiOverlay(poiSearch,mBaiduMap);
                //设置overlay可以处理标注点击事件
                mBaiduMap.setOnMarkerClickListener(overlay);
                //设置PoiOverlay数据
                overlay.setData(poiResult);
                //添加PoiOverlay到地图中
                overlay.addToMap();
                overlay.zoomToSpan();
                ToastUtil.show(getActivity(),poiResult.getTotalPoiNum()+"个兴趣点，共"+poiResult.getTotalPageNum()+"页");
                return;
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
            if(poiDetailResult.error!=PoiDetailResult.ERRORNO.NO_ERROR){
                ToastUtil.show(getActivity(),"未查询到结果");
            }else {
                ToastUtil.show(getActivity(),poiDetailResult.getName()+":::"+
                        poiDetailResult.getAddress());
            }
        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };

    private void showSearchDialog(){
        View view = View.inflate(getActivity(),R.layout.dialog_poi_search,null);
        final Dialog dialog = DialogUtil.creatDialog(getActivity(), view);
        final EditText et_city = (EditText) view.findViewById(R.id.et_city);
        final EditText et_keyword = (EditText) view.findViewById(R.id.et_keyword);
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                String city = et_city.getText().toString().trim();
                String keyword = et_keyword.getText().toString().trim();
                if(!TextUtils.isEmpty(city)&&!TextUtils.isEmpty(keyword));{
                    MyPoiOverlay.citySearch(poiSearch,1,city,keyword);
                }
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
