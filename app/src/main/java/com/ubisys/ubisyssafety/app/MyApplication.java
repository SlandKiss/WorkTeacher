package com.ubisys.ubisyssafety.app;

import ubisyssafety.ubisys.baselibray.base.BaseApplication;
import ubisyssafety.ubisys.baselibray.http.HttpUtils;
import ubisyssafety.ubisys.baselibray.utils.CrashHandler;
import ubisyssafety.ubisys.framelibrary.framehttp.OkHttpEngine;

/**
 * Created by Administrator on 2017/7/7.
 */

public class MyApplication extends BaseApplication{

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
        HttpUtils.init(new OkHttpEngine());
    }
}
