package com.ly.includelibrary.before;

import com.ly.rxlibrary.base.application.BaseApplication;
import com.ly.rxlibrary.utils.log.viewlog.ViseLog;
import com.ly.rxlibrary.utils.log.viewlog.inner.LogcatTree;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class MyAPP extends BaseApplication {
    @Override
    protected void initConfigs() {
        LeakCanary.install(this);
        initLog();
        CrashReport.initCrashReport(getApplicationContext(), "5d02082728", true);
    }
    private void initLog() {
        ViseLog.getLogConfig()
                .configAllowLog(true)//是否输出日志
                .configShowBorders(false);//是否排版显示
        ViseLog.plant(new LogcatTree());//添加打印日志信息到Logcat的树
    }
}
