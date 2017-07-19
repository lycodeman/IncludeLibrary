package com.ly.includelibrary.after.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ly.includelibrary.R;
import com.ly.includelibrary.after.mvp.contract.LoginContract;
import com.ly.includelibrary.after.mvp.presenter.LoginPresenter;
import com.ly.includelibrary.before.User;
import com.ly.rxlibrary.base.activity.BaseActivity;

public class MvpActivity extends BaseActivity implements LoginContract.View{

    private EditText et_name;
    private Button btn_logon;
    private EditText et_password;
    private LoginPresenter loginPresenter;

    @Override
    public int requestLayout() {
        return R.layout.activity_mvp;
    }

    @Override
    public void initView() {
        super.initView();
        /**
         * 初始化数据
         */
        et_name = (EditText) findViewById(R.id.editText2);
        et_password = (EditText) findViewById(R.id.editText3);
        btn_logon = (Button) findViewById(R.id.button);
        C(btn_logon);
        loginPresenter = new LoginPresenter();
        loginPresenter.bind(this);//绑定View和Presenter，因为这个Activity已经实现了接口，已经包含了View对象
    }

    @Override
    protected void processClick(View view) {
        super.processClick(view);
        loginPresenter.login();
    }

    @Override
    public String getAccount() {
        return et_name.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return et_password.getText().toString().trim();
    }

    @Override
    public void loginSuccess(User user) {
       toast("登录成功");
    }

    @Override
    public void showNetworkError() {
        Toast.makeText(this, "当前网络不可用", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showVerifyFailed() {
        Toast.makeText(this, "输入的用户名或密码有误", Toast.LENGTH_SHORT).show();
    }
}
