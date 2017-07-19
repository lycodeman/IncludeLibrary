package com.ly.includelibrary.before;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.ly.includelibrary.R;
import com.ly.rxlibrary.base.activity.BaseActivity;
import com.ly.rxlibrary.modle.request.LoginRequest;
import com.ly.rxlibrary.modle.response.LoginResponse;
import com.ly.rxlibrary.net.api.Api;
import com.ly.rxlibrary.net.api.ApiObserver;
import com.ly.rxlibrary.rx.rxbus.RxBus;
import com.ly.rxlibrary.rx.rxbus.Subscribe;
import com.ly.rxlibrary.rx.rxpermission.OnPermissionCallback;
import com.ly.rxlibrary.rx.transformer.DefaultTransformer;
import com.ly.rxlibrary.utils.app.AppUtils;
import com.ly.rxlibrary.utils.log.LUtils;
import com.ly.rxlibrary.utils.sp.SPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity {

    @BindView(R.id.test_tv)
    Button testTv;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private Unbinder bind;
    private boolean isFlag=true;
    private void testRxBus() {
        RxBus.getDefault().post(new LoginRequest("222", "iiiii"));
//        testRetrofit();

    }

    @Override
    public void init() {
        super.init();
        permissions = needPermissions;
        setRegisterEvent(true);
    }

    private void testRetrofit() {
        AppUtils.getApi().login(new LoginRequest("13000000000", "222222")).
                compose(DefaultTransformer.<LoginResponse>create())
                .subscribe(new ApiObserver<LoginResponse>() {
                            @Override
                            public void getDisposable(Disposable disposable) {
                                addDisposable(disposable);
                            }

                            @Override
                            public void onNext(@NonNull LoginResponse loginRequest) {
                                toast("====="+loginRequest.toString());
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                LUtils.e(e.getMessage());
                            }
                        }
                )
        ;


    }

    @Subscribe
    public void getData(LoginRequest loginRequest) {
        testTv.setText(loginRequest.getLoginName());
//        tv.setText("====");
        Log.e("", "getData: " + "===============");
        Toast.makeText(this, loginRequest.toString(), Toast.LENGTH_SHORT).show();

    }

    private void testRXBinding() {
    /*-------------------------rxbinding测试-----------------------------------*/
        RxView.clicks(testTv).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
//                testRxBus();
//                testBugly();
                Context context=null;
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void testBugly() {
        Retrofit builder=new Retrofit.Builder()
                .baseUrl("")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Map<String,String> stringMap=new HashMap<>();
         /*form "api_version=1" --form "app_id=xxxxxx" --form "app_key=xxxxxx"
    --form "symbolType=1"  --form "bundleId=com.demo.test"
    --form "productVersion=1.0" --form "channel=xxx"
     --form "fileName=mapping.txt" --form "file=@mapping.txt" --verbose*/
        stringMap.put("api_version","1");
        stringMap.put("app_id","xxxxxx");
        stringMap.put("app_key","xxxxxx");
        stringMap.put("symbolType","1");
        stringMap.put("bundleId","com.demo.test");
        stringMap.put("productVersion","1.0");
        stringMap.put("channel","xxx");
        stringMap.put("fileName","mapping.txt");
        stringMap.put("file","@mapping.txt");
        builder.create(Api.class).postBugly(stringMap);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentOne());
        fragmentList.add(new FragmentTwo());
        fragmentList.add(new FragmentThr());
        fragmentList.add(new Fragmentf());
        viewPager.setAdapter(new ViewAdapter(getSupportFragmentManager(), fragmentList));
        viewPager.setCurrentItem(1);
    }

    @Override
    public int requestLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initInjector() {
        super.initInjector();
        bind = ButterKnife.bind(this);
//        RxBus.getDefault().register(this);
    }

    @Override
    public void initView() {
        super.initView();
        testRXBinding();
        /*请求权限组时会弹出多次，这里要自行处理*/
        setPermission(true, new OnPermissionCallback() {
            @Override
            public void onRequestAllow(String permissionName) {
//                DialogUtil.showTips(MainActivity.this, "权限控制",
//                        "允许" + "\n" + permissionName);
            }

            @Override
            public void onRequestRefuse(String permissionName) {
                if (isFlag){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("权限请求失败，请手动开启！")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    toast("请求失败");
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    toast("跳转到设置界面");
                                }
                            }).create().show();
                    isFlag=false;
                }

            }

            @Override
            public void onRequestNoAsk(final String permissionName) {
//                DialogUtil.showTips(MainActivity.this, "权限控制",
//                        "没有回掉" + "\n" + permissionName);
            }
        }, permissions);
//        testSP();
    }

    @Override
    public void processAct() {

    }

    @Override
    public void releaseResource() {

    }

    private void testSP() {
    /*----------------------SPUtils测试--------------------------------*/
        LoginRequest obeject = SPUtils.getInstanse(this)
                .setFileName("login")
                .put("userName", "ly")
                .saveObeject("user", new LoginRequest("11", "00"))
                .getObeject("user", LoginRequest.class);
        Log.e("====", "\n onCreate: " + obeject.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        RxBus.getDefault().unregister(this);
        bind.unbind();
    }

    @Override
    protected void processClick(View view) {

    }
}
