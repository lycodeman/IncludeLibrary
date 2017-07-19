package com.ly.includelibrary.before;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ly.includelibrary.R;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.Observable;

public class RxLifeActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_life);
//        Observable.fromCallable().compose(this.bindToLifecycle()).compose(bindUntilEvent(ActivityEvent.PAUSE))
//                .subscribe();
    }
}
