package com.example.youkedemo.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.youkedemo.R;

public class SatrtYKYActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satrt_yky);
    }

    public void startYKY(View view) {
        startActivity(new Intent(this,ActivityMain.class));
    }
}
