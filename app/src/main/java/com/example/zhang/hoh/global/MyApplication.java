package com.example.zhang.hoh.global;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.example.sdk.global.GlobalApplication;
import com.example.sdk.utils.LogUtils;
import com.example.zhang.hoh.utils.LocalManageUtil;

public class MyApplication  extends GlobalApplication{

    public static MyApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        LogUtils.setDebuggable(true);
        //设置语言，默认为中文
        LocalManageUtil.setApplicationLanguage(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        //保存系统选择语言
        LocalManageUtil.saveSystemCurrentLanguage(base);
        super.attachBaseContext(base);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //保存系统选择语言
        LocalManageUtil.onConfigurationChanged(getApplicationContext());
    }
}
