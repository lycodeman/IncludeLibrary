package com.ly.includelibrary.after.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ly.includelibrary.R;
import com.ly.includelibrary.after.adapter.HomeAdapter;
import com.ly.includelibrary.before.DataBindingActivity;
import com.ly.includelibrary.before.LayoutActivity;
import com.ly.includelibrary.before.MainActivity;
import com.ly.includelibrary.before.RxLifeActivity;
import com.ly.rxlibrary.base.activity.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.home_rv)
    RecyclerView homeRv;
    private ArrayList<Map<String, Class>> maps;
    private Unbinder unbinder;

    @Override
    public int requestLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        super.initView();
        unbinder = ButterKnife.bind(this);
        getData();
        homeRv.setLayoutManager(new LinearLayoutManager(this));
        HomeAdapter homeAdapter = new HomeAdapter(R.layout.home_item, maps);
        homeAdapter.openLoadAnimation();
//        homeAdapter.setNotDoAnimationCount(3);
        homeRv.setAdapter(homeAdapter);
    }

    @Override
    protected void processClick(View view) {
        unbinder.unbind();
    }

    @Override
    public void releaseResource() {
        super.releaseResource();
    }

    private String[] names={"MainActivity","LayoutActivity","RxLifeActivity",
            "UIActivity","DataBindingActivity","MvpActivity"};
    private Class[] className={MainActivity.class, LayoutActivity.class,
            RxLifeActivity.class,UIActivity.class, DataBindingActivity.class,MvpActivity.class};
    private List<String> strings= Arrays.asList(names);
    private List<Class> classes=Arrays.asList(className);

    private void getData() {
        maps = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            Map<String,Class> map = new HashMap<String,Class>();
            map.put(strings.get(i), classes.get(i));
            maps.add(map);
        }

    }
}
