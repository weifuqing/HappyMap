package com.example.personal.happymap.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.personal.happymap.R;
import com.example.personal.happymap.base.BaseLazyFragment;
import com.example.personal.happymap.utils.DialogUtil;
import com.example.personal.happymap.utils.ToastUtil;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

/**
 * Created by dell on 2016/7/27.
 */
public class MobFragment extends BaseLazyFragment {

    View view;
    TextView tv_log;
    TextView tv_share;
    PlatformActionListener platformListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mob,null);
        initView();
        initListener();
        return view;
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void initView() {
        tv_log = (TextView) view.findViewById(R.id.tv_log);
        tv_share = (TextView) view.findViewById(R.id.tv_share);
    }

    @Override
    public void initListener() {
        tv_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.showShare(getActivity(),"标题","http://www.baidu.com/",
                        "内容","http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=宠物&step_word=&pn=10&spn=0&di=150312788230&pi=&rn=1&tn=baiduimagedetail&is=&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2692675169%2C1336708413&os=2516010038%2C679951130&simid=4284787642%2C897453028&adpicid=0&ln=1000&fr=&fmq=1463574658346_R&fm=&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=10&oriquery=&objurl=http%3A%2F%2Fa.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Ff9dcd100baa1cd11aa2ca018bf12c8fcc3ce2d74.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fzit1w5_z%26e3Bkwt17_z%26e3Bv54AzdH3Fq7jfpt5gAzdH3F89ndacnbbll0dnc0mcl_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0",
                        "http://www.sina.com/","评论","标题site","https://www.hao123.com");
            }
        });
    }

    private void login(){

        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        weibo.SSOSetting(false);  //设置false表示使用SSO授权方式
        platformListener = new MyPlatformListener();
        weibo.setPlatformActionListener(platformListener); // 设置分享事件回调
        weibo.authorize();
    }

    class MyPlatformListener implements PlatformActionListener{

        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            ToastUtil.show(getActivity(), (String) hashMap.get("nickname"));
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            ToastUtil.show(getActivity(),"授权失败");
        }

        @Override
        public void onCancel(Platform platform, int i) {

        }
    }
}
