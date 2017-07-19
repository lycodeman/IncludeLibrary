package com.huben.NewRX;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.huben.NewRX.base.activity.BaseActivity;
import com.huben.NewRX.modle.response.LogisticsInfo;
import com.huben.NewRX.rxhttp.download.DownloadHelper;
import com.huben.NewRX.rxhttp.download.listener.DownloadListener;
import com.huben.NewRX.rxhttp.manager.ServiceManager;
import com.huben.NewRX.rxhttp.result.HttpResponseResult;
import com.huben.NewRX.rxhttp.rxjava.observable.DialogTransformer;
import com.huben.NewRX.rxhttp.rxjava.observable.ResultTransformer;
import com.huben.NewRX.rxhttp.rxjava.observable.SchedulerTransformer;
import com.huben.NewRX.rxhttp.rxjava.observer.BaseObserver;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity {

    @Override
    public int requestLayout() {
        return R.layout.activity_main;
    }

    private Button btGet1;
    private Button btGet2;
    private Button btPost;
    private Button btDown;

    private void assignViews() {
        btGet1 = (Button) findViewById(R.id.bt_get_1);
        btGet2 = (Button) findViewById(R.id.bt_get_2);
        btPost = (Button) findViewById(R.id.bt_post);
        btDown = (Button) findViewById(R.id.bt_down);
    }

    @Override
    public void initInjector() {
        super.initInjector();
        assignViews();


        //非标准restful 接口
        btGet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceManager
                        .getApiService()
                        .testGet1()
                        .compose(SchedulerTransformer.<String>transformer())//线程转换
                        .compose(MainActivity.this.<String>bindToLifecycle())//绑定生命周期，防止内存泄露
                        .compose(new DialogTransformer(MainActivity.this,"z哼在家在",true).<String>transformer())//progressDialog
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(String s) {
                                Logger.e(s);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Logger.e(e.toString());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });


            }
        });

        btGet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceManager
                        .getApiService()
                        .testGet2()
                        .compose(MainActivity.this.<HttpResponseResult<List<String>>>bindToLifecycle())
                        .compose(ResultTransformer.<List<String>>transformer())
                        .compose(new DialogTransformer(MainActivity.this).<List<String>>transformer())
                        .subscribe(new BaseObserver<List<String>>() {
                            @Override
                            protected void onSuccess(List<String> stringList) {
                                String[] str = stringList.toArray(new String[stringList.size()]);
                                Logger.e(Arrays.toString(str));
                            }
                        });
            }
        });

        btPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> map = new HashMap<>();
                map.put("logisticsid", 20);
                map.put("logisticsno", "1000817443587");



                ServiceManager
                        .getApiService()
                        .getLogisticsInfo(map)
                        .compose(MainActivity.this.<HttpResponseResult<LogisticsInfo>>bindToLifecycle())
                        .compose(ResultTransformer.<LogisticsInfo>transformer())
                        .compose(new DialogTransformer(MainActivity.this).<LogisticsInfo>transformer())
                        .subscribe(new BaseObserver<LogisticsInfo>() {
                            @Override
                            protected void onSuccess(LogisticsInfo logisticsInfo) {
                                Logger.e(logisticsInfo.toString());

                            }
                        });
            }
        });


        btDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });

    }

    private void download() {

        String url = "http://downloads.easemob.com/downloads/easemob-sdk-3.3.1_r1.zip";
        String dirPath = getExternalCacheDir().getAbsolutePath();
        String fileName = "download_test.zip";


        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("更新");
        progressDialog.setMessage("更新中，请稍候...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setProgress(10);
        progressDialog.setCancelable(false);

        final DownloadHelper downloadHelper = new DownloadHelper(url, dirPath, fileName);

        downloadHelper.downloadFile(new DownloadListener() {
            @Override
            public void update(long bytesRead, long contentLength) {
                Logger.e("----bytesRead=" + bytesRead);
                Logger.e("----contentLength=" + contentLength);
                progressDialog.setProgress((int) ((100 * bytesRead) / contentLength));
            }

            @Override
            public void onSuccess(File file) {
                progressDialog.cancel();
                Logger.e(file.getAbsolutePath() + "----" + file.length());
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.cancel();
                Logger.e("----" + t.toString());
            }
        });


        progressDialog.setButton3("取消下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadHelper.cancelDownload();
            }
        });

        progressDialog.show();

    }



}
