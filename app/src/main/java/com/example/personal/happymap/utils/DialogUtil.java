package com.example.personal.happymap.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import java.util.SplittableRandom;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


/**
 * Created by dell on 2016/7/21.
 */
public class DialogUtil {

    public static void showDialog(Context context,String title,String message,DialogInterface.OnClickListener positiveListener,DialogInterface.OnClickListener negativeListener){
        new AlertDialog.Builder(context).setTitle(title)
                .setMessage(message)
                .setNegativeButton("取消", negativeListener)
                .setPositiveButton("确定",positiveListener)
                .setCancelable(false)
                .show();
    }

    public static AlertDialog creatDialog(Context context,View view){
        AlertDialog.Builder builder =new AlertDialog.Builder(context);
        AlertDialog alertDialog = builder.create();
        alertDialog.setView(view);
        return alertDialog;
    }


    public static void showShare(Context context,String title,String titleUrl,String text,
                           String imageUrl,
                           String url,String comment,String site,String siteUrl) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(titleUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(text);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl(imageUrl);
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(comment);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(site);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(siteUrl);
        // 启动分享GUI
        oks.show(context);
    }
}
