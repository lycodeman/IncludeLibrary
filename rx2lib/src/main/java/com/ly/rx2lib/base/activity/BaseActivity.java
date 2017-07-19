package com.ly.rx2lib.base.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.ly.rx2lib.rxhttp.rxbus.BusFactory;
import com.ly.rx2lib.rxhttp.rxpermission.OnPermissionCallback;
import com.ly.rx2lib.rxhttp.rxpermission.PermissionManager;
import com.ly.rx2lib.utils.refresh.Themeable;
import com.ly.rx2lib.utils.refresh.UiElementInizializer;
import com.ly.rx2lib.utils.refresh.ViewUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by dingmouren on 2017/5/2.
 */

public abstract class BaseActivity extends RxAppCompatActivity implements UiElementInizializer
    ,ActivityCompat.OnRequestPermissionsResultCallback, View.OnClickListener {
    private static final String TAG = BaseActivity.class.getName();
    private CompositeDisposable compositeDisposable;
    private SparseArray<View> mViews;
    private boolean mIsRegisterEvent = false;//是否开启rxbus，要在super之前调用
    protected String[] permissions;//需要开启的权限组
//    protected RxPermissions rxPermissions;
    private boolean isNeedCheck = false;//是否开启权限,要在super之前调用

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setColorNoTranslucent(this, Resources.getSystem().getColor(android.R.color.black));
        init();
        initTheme();
        setContentView(requestLayout());
        initInjector();
        initView();
        initListener();
        initData();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUiElements();
        processAct();

    }

    /**
     * 设置布局前的初始化
     */
    public void  init(){}
    /**
     * 初始化主题
     */
    public void initTheme(){}
    /**
     * 布局
     * @return
     */
    public abstract int requestLayout();

    /**
     * View需要初始化的
     */
    public  void initView(){}

    /**
     * 初始化注入
     */
    public void initInjector(){
        mViews=new SparseArray<>();
        if (mIsRegisterEvent){
            BusFactory.getBus().register(this);
        }

    }

    /**
     * 初始化监听器
     */
    public void initListener(){}

    /**
     * 初始化数据
     */
    public void initData(){}

    /**
     * 当在用户界面的时候，执行的操作
     */
    public void processAct(){}

    /**
     * 释放资源
     */
    public void releaseResource(){};

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onUnsubscribe();
        releaseResource();
        if (mIsRegisterEvent){
            BusFactory.getBus().unregister(this);
        }
    }

    /**
     * 更新组件UI
     */
    @Override
    public void updateUiElements() {
        for (View view : ViewUtil.getAllChildren(findViewById(android.R.id.content))){
            if (view instanceof Themeable){
                ((Themeable)view).refreshTheme();
            }
        }
        updateStatusBarUI();
    }

    /**
     * 更改状态栏颜色
     */
    public void updateStatusBarUI(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
        }
    }

    /**
     * 所有rx订阅后，需要调用此方法，用于在detachView时取消订阅
     */
    protected void addDisposable(Disposable disposable) {
        if (compositeDisposable == null)
            compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(disposable);
    }

    /**
     * 取消本页面所有订阅
     */
    protected void onUnsubscribe() {
        if (compositeDisposable != null && compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
    
    /*----------------------------添加6.0权限-----------------------------------*/
    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    protected <E extends View> E F(int viewId) {
        E view = (E) mViews.get(viewId);
        if (view == null) {
            view = (E) findViewById(viewId);
            mViews.put(viewId, view);
        }
        return view;
    }

    protected <E extends View> void C(E view) {
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        processClick(view);
    }

    /**
     * 点击事件处理
     * @param view
     */
    protected void processClick(View view){};

    public BaseActivity setRegisterEvent(boolean mIsRegisterEvent) {
        this.mIsRegisterEvent = mIsRegisterEvent;
        return this;
    }

    public BaseActivity setPermission(boolean isNeedCheck, OnPermissionCallback permissionCallback, String[] permissions) {
        this.isNeedCheck = isNeedCheck;
        if (isNeedCheck){
            PermissionManager.instance().with(this).request(permissionCallback,permissions);
        }
        return this;
    }
}
