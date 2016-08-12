package com.example.personal.happymap.mapUtils;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiSearch;

/**
 * Created by dell on 2016/7/20.
 */
public class MyPoiOverlay extends PoiOverlay{

    /**
     * 构造函数
     *
     * @param baiduMap 该 PoiOverlay 引用的 BaiduMap 对象
     */

    PoiSearch poiSearch;
    public MyPoiOverlay(BaiduMap baiduMap) {
        super(baiduMap);
    }

    public MyPoiOverlay(PoiSearch poiSearch,BaiduMap baiduMap){
        this(baiduMap);
        this.poiSearch = poiSearch;
    }

    @Override
    public boolean onPoiClick(int i) {
        super.onPoiClick(i);
        PoiInfo poiInfo = getPoiResult().getAllPoi().get(i);
        poiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(poiInfo.uid));

        return true;
    }

    //城市内搜索
    public static void citySearch(PoiSearch poiSearch,int page,String city,String keyWord){
        PoiCitySearchOption poiCitySearchOption = new PoiCitySearchOption();
        poiCitySearchOption.city(city);
        poiCitySearchOption.keyword(keyWord);
        poiCitySearchOption.pageCapacity(15);
        poiCitySearchOption.pageNum(page);
        poiSearch.searchInCity(poiCitySearchOption);
    }

    //范围搜索
    public static void boundSearch(PoiSearch poiSearch,int page,String keyWord,
                                   double latitude,double longitude){
        PoiBoundSearchOption poiBoundSearchOption = new PoiBoundSearchOption();
        LatLng southwest = new LatLng(latitude - 0.01, longitude - 0.012);// 西南
        LatLng northeast = new LatLng(latitude + 0.01, longitude + 0.012);// 东北
        LatLngBounds bounds = new LatLngBounds.Builder().include(southwest)
                .include(northeast).build();// 得到一个地理范围对象
        poiBoundSearchOption.keyword(keyWord);
        poiBoundSearchOption.bound(bounds);
        poiBoundSearchOption.pageNum(page);
        poiSearch.searchInBound(poiBoundSearchOption);
    }

    //附近搜索
    public static void nearSearch(PoiSearch poiSearch,int page,String keyWord,
                                    double latitude,double longitude,int radius){
        PoiNearbySearchOption poiNearbySearchOption = new PoiNearbySearchOption();
        poiNearbySearchOption.location(new LatLng(latitude,longitude));
        poiNearbySearchOption.radius(radius);
        poiNearbySearchOption.keyword(keyWord);
        poiNearbySearchOption.pageNum(page);
        poiSearch.searchNearby(poiNearbySearchOption);
    }



}
