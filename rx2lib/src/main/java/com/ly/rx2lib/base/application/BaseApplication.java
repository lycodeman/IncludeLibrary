package com.ly.rx2lib.base.application;

import android.app.Application;

import com.ly.rx2lib.utils.ContextUtils;
import com.ly.rx2lib.utils.app.AppManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * @Description: Application基类
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2016-12-19 14:49
 */
public abstract class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化appmanager
        AppManager.getInstance().init(this);
        ContextUtils.init(this);
        //初始化Logger日志
        Logger.addLogAdapter(new AndroidLogAdapter());
        //允许打印种类
//        LUtils.creat().
//                enableLogD(true).//logd
//                enableLogE(true).//loge
//                enableLogI(true).//logi
//                enableLogJ(true).//logjson
//                enableLogV(true).//logv
//                enableLogW(true).//logw
//                enableLogX(true);//logxml
        //初始化其他配置
        initConfigs();
    }

    /**
     * 初始化配置
     */
    protected abstract void initConfigs();
}
