package com.ly.rxlibrary.utils.app;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;

import com.ly.rxlibrary.R;
import com.ly.rxlibrary.net.api.Api;
import com.ly.rxlibrary.net.api.HttpMethods;
import com.ly.rxlibrary.rx.rxpermission.RxPermissions;

import io.reactivex.Observer;
import io.reactivex.functions.Consumer;


public final class AppUtils {

    /**
     * 第一次和第二次的退出间隔时间基准
     */
    private static final long EXIT_TWICE_INTERVAL = 2000;
    private static long mExitTime = 0;

    /**
     * 第二次按退出则返回true,否则返回false
     */
    public static boolean exitTwice() {
        long newExitTime = System.currentTimeMillis();
        if (newExitTime - mExitTime > EXIT_TWICE_INTERVAL) {
            mExitTime = newExitTime;
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取api
     */
    public static Api getApi(){
        return HttpMethods.getInstance().createService(Api.class);
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    public static void setDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context.getApplicationContext());
        builder.setTitle("网络提示信息");
        builder.setMessage("网络不可用，如果继续，请先设置网络！");
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                /**
                 * 判断手机系统的版本！如果API大于10 就是3.0+
                 * 因为3.0以上的版本的设置和3.0以下的设置不一样，调用的方法不同
                 */
                if (Build.VERSION.SDK_INT > 10) {
                    intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
                } else {
                    intent = new Intent();
                    ComponentName component = new ComponentName(
                            "com.android.settings",
                            "com.android.settings.WirelessSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create();
        builder.show();
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     *
     */
    public static void showMissingPermissionDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.notifyTitle);
        builder.setMessage(R.string.notifyMsg);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppManager.exitApp();
                    }
                })
                .setPositiveButton(R.string.setting,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings(context);
                    }
                });

        builder.setCancelable(false);
        builder.show();
    }

    /**
     *  启动应用的设置
     *
     * @since 2.5.0
     *
     */
    private static void startAppSettings(Context context) {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    public static void sendSMS(Context context,String phoneNum,String smsBody){
        Uri smsToUri = Uri.parse("smsto:"+phoneNum);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", smsBody);
        context.startActivity(intent);
    }

    public static void callPhone(Context context,String phoneNum){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            new RxPermissions((Activity) context).request(Manifest.permission.CALL_PHONE)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) {
                            if (aBoolean){
                                Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneNum));
                                context.startActivity(intent);
                            }else {
                                Toast.makeText(context, "拒绝权限将无法使用客服电话", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+phoneNum));
            context.startActivity(intent);
        }
    }
}
