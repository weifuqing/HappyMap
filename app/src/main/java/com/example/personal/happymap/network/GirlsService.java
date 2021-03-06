package com.example.personal.happymap.network;



import com.example.personal.happymap.data.bean.GirlsBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by dell on 2016/8/8.
 */
public interface GirlsService {

    @GET("api/data/{type}/{count}/{page}")
    Observable<GirlsBean> getGirls(
            @Path("type") String type,
            @Path("count") int count,
            @Path("page") int page
    );
}
