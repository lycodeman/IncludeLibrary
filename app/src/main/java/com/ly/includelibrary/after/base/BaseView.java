package com.ly.includelibrary.after.base;

/**
 * Created by CodeManLY on 2017/7/13 0013.
 */

public interface BaseView {
    void showLoading(String title);
    void stopLoading();
    void showErrorTip(String msg);
}
