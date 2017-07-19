package com.ly.rx2lib.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ly.rx2lib.rxhttp.rxbus.BusFactory;
import com.ly.rx2lib.rxhttp.rxpermission.OnPermissionCallback;
import com.ly.rx2lib.rxhttp.rxpermission.PermissionManager;
import com.ly.rx2lib.utils.log.LUtils;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by dingmouren on 2017/5/2.
 */

public abstract class BaseLazyFragment extends RxFragment implements View.OnClickListener {

    // 标志位，标志已经初始化完成。使用时用isPrepared在子类中表示是否初始化完成
    //并在该fragment销毁时，置为false
    protected boolean isPrepared;
    protected boolean isVisible;
    protected boolean isFirstLoad;
    protected View rootView;
    private CompositeDisposable compositeDisposable;
    private SparseArray<View> mViews;
    private boolean mIsRegisterEvent = false;
    private boolean isNeedCheck;
    private Activity activity;
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (Activity) context;//解决getActivity（）报空指针的情况,在fragment中使用这个
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*保存fragment，由于内存重启造成的崩溃*/
        /*使用时要在activity中执行savedInstanceState的判断*/
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
        init();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isFirstLoad= true;
        rootView = inflater.inflate(requestLayout(),container,false);
        isPrepared=true;
        if (mIsRegisterEvent){
            BusFactory.getBus().register(this);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initInjector();
        initView();
        initListener();
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mIsRegisterEvent){
            BusFactory.getBus().unregister(this);
        }
        onUnsubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    /**
     * 设置布局前的初始化
     */
    public void  init(){}
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
    public void initInjector(){}
    /**
     * 初始化监听器
     */
    public void initListener(){}

    /**
     * 初始化数据
     */
    public void initData(){}

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

    public void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

    }

    /*-------------------------------懒加载操作-----------------------------------*/

    /**
     * 在这里实现Fragment数据的缓加载.
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {//可见时进入
            isVisible = true;
            onVisible();
        } else {//不可见时进入
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     * @param hidden hidden True if the fragment is now hidden, false if it is not
     *               visible.
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
        LUtils.e(getTargetFragment().getClass()+"====....>>>>"+hidden);
    }

    protected void onVisible(){
        lazyLoad();
    }


    /**
     * 要实现延迟加载Fragment内容,需要在 onCreateView
     * isPrepared = true;
     */
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad) {//全部满足才会加载
            return;
        }
        isFirstLoad = false;
        fetchData();
    }

    /**
     * 在此请求网络数据
     */
    public abstract void fetchData();

    /**
     * 这里使用了viewepager暂时用不到onInvisible()
     * 不可见时的操作
     */
    protected void onInvisible(){}

    /*------------------------------事件绑定------------------------------*/
    protected <E extends View> E F(View mVew,int viewId) {
        E view = (E) mViews.get(viewId);
        if (view == null) {
            view = (E) mVew.findViewById(viewId);
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
    protected abstract void processClick(View view);

    public BaseLazyFragment setRegisterEvent(boolean mIsRegisterEvent) {
        this.mIsRegisterEvent = mIsRegisterEvent;
        return this;
    }

    public BaseLazyFragment setPermission(boolean isNeedCheck, OnPermissionCallback permissionCallback, String[] permissions) {
        this.isNeedCheck = isNeedCheck;
        if (isNeedCheck){
            PermissionManager.instance().with(getActivity()).request(permissionCallback,permissions);
        }
        return this;
    }
}
