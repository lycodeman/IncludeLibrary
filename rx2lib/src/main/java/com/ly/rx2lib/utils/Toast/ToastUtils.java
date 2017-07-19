package com.ly.rx2lib.utils.Toast;

import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;

import com.huben.NewRX.utils.ContextUtils;


public class ToastUtils {

    private static Toast toast;

    public static void show(String text) {
        if (toast == null) {
            toast = Toast.makeText(ContextUtils.getContext(), "", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        toast.setText(text);
        toast.show();
    }

    public static void show(@StringRes int resId) {
        show(ContextUtils.getContext().getResources().getString(resId));
    }

}
