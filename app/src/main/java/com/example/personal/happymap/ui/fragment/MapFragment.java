package com.example.personal.happymap.ui.fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.personal.happymap.base.BaseLazyFragment;
import com.example.personal.happymap.mapUtils.MapLocationManager;
import com.example.personal.happymap.mapUtils.MyPoiOverlay;
import com.example.personal.happymap.R;
import com.example.personal.happymap.utils.DateUtil;
import com.example.personal.happymap.utils.DialogUtil;
import com.example.personal.happymap.utils.FileUtil;
import com.example.personal.happymap.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by dell on 2016/7/14.
 */
public class MapFragment extends BaseLazyFragment implements View.OnClickListener{
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
    private MapLocationManager mapLocationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();

        if(mapLocationManager!=null){
            mapLocationManager.stopLocation();
        }
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
        if (poiListener == null) {
            poiListener = new MyPoiListener();
            poiSearch.setOnGetPoiSearchResultListener(poiListener);
        }

        //地图加载成功开启定位
        mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                location();
            }
        });
    }

    @Override
    public void fetchData() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_heat:
                mBaiduMap.setBaiduHeatMapEnabled(isHeat = !isHeat);
                break;
            case R.id.bt_normal:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case R.id.bt_satellite:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.bt_traffic:
                mBaiduMap.setTrafficEnabled(isTraffic = !isTraffic);
                break;
            case R.id.bt_search:
                showSearchDialog();
//                catchPicture();
                break;
        }
    }


    //Poi检索监听
    class MyPoiListener implements OnGetPoiSearchResultListener {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                ToastUtil.show(getActivity(), "未找到结果");
                return;
            }
            if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                mBaiduMap.clear();
                //创建PoiOverlay
                PoiOverlay overlay = new MyPoiOverlay(poiSearch, mBaiduMap);
                //设置overlay可以处理标注点击事件
                mBaiduMap.setOnMarkerClickListener(overlay);
                //设置PoiOverlay数据
                overlay.setData(poiResult);
                //添加PoiOverlay到地图中
                overlay.addToMap();
                overlay.zoomToSpan();
                ToastUtil.show(getActivity(), poiResult.getTotalPoiNum() + "个兴趣点，共" + poiResult.getTotalPageNum() + "页");
                return;
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
            if (poiDetailResult.error != PoiDetailResult.ERRORNO.NO_ERROR) {
                ToastUtil.show(getActivity(), "未查询到结果");
            } else {
                ToastUtil.show(getActivity(), poiDetailResult.getName() + ":::" +
                        poiDetailResult.getAddress());
            }
        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    }


    //搜索兴趣点
    private void showSearchDialog() {
        View view = View.inflate(getActivity(), R.layout.dialog_poi_search, null);
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
                if (!TextUtils.isEmpty(city) && !TextUtils.isEmpty(keyword)) ;
                {
                    MyPoiOverlay.citySearch(poiSearch, 1, city, keyword);
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

    //截屏
    private void catchPicture(){
        mBaiduMap.snapshot(new BaiduMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap bitmap) {
                File file = new File(FileUtil.PICTURE, DateUtil.dateToString(new Date())+".jpg");
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                    if(bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos)){
                        fos.flush();
                        ToastUtil.show(getActivity(), "保存成功" + file.getPath());
                        FileUtil.sendBroadcast(getActivity(),file);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(fos!=null){
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    //定位
    private void location(){
        mBaiduMap.setMyLocationEnabled(true);
        mapLocationManager = new MapLocationManager(mBaiduMap);
        mapLocationManager.startLocation();
    }

}
