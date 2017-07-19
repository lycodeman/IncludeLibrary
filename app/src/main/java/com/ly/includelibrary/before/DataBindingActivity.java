package com.ly.includelibrary.before;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ly.includelibrary.R;
import com.ly.includelibrary.databinding.ActivityDataBindingBinding;

public class DataBindingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDataBindingBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        viewDataBinding.setUser(new User("ly","21"));
    }
}
