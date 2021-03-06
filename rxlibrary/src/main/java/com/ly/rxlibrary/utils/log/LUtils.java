package com.ly.rxlibrary.utils.log;

import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2017/6/15/015.
 */

public class LUtils {

    private static LUtils lUtils;

    /**
     * 是否打印log
     *
     * @param enable
     */
    public LUtils enableLogD(boolean enable) {
        allowD = enable;
        return lUtils;
    }

    public LUtils enableLogE(boolean enable) {
        allowE = enable;
        return lUtils;
    }

    public LUtils enableLogI(boolean enable) {
        allowI = enable;
        return lUtils;
    }

    public LUtils enableLogV(boolean enable) {
        allowV = enable;
        return lUtils;
    }

    public LUtils enableLogW(boolean enable) {
        allowW = enable;
        return lUtils;
    }

    public LUtils enableLogJ(boolean enable) {
        allowJ = enable;
        return lUtils;
    }

    public LUtils enableLogX(boolean enable) {
        allowX = enable;
        return lUtils;
    }

    /*----------------必须初始化才能使用-----------------*/
    public static boolean allowD = false;
    public static boolean allowE = false;
    public static boolean allowI = false;
    public static boolean allowV = false;
    public static boolean allowW = false;
    public static boolean allowJ = false;
    public static boolean allowX = false;


    public static LUtils creat() {
        if (lUtils == null) {
            synchronized (LUtils.class){
                if (lUtils==null){
                    lUtils = new LUtils();
                }
            }
        }
        return lUtils;
    }

    public static void v(String msg) {
        if (allowD) {
            Logger.v("verbose");
        }
    }

    public static void d(String msg) {
        if (allowD) {
            Logger.e(msg);
        }
    }

    public static void dList(Object msg) {
        if (allowD) {
            Logger.d(msg);
        }
    }

    public static void dXml(String msg) {
        if (allowD) {
            Logger.xml(msg);
        }
    }

    public static void i(String msg) {
        if (allowI) {
            Logger.i(msg);
        }
    }

    public static void w(String msg) {
        if (allowW) {
            Logger.w(msg);
        }
    }

    public static void w(Throwable tr) {
        if (allowW) {
            Logger.w(tr.getMessage());
        }
    }

    public static void w(String msg, Throwable tr) {
        if (allowW && null != msg) {
            Logger.w(msg, tr);
        }
    }

    public static void e(String msg) {
        if (allowE) {
            Logger.e(msg);
        }
    }

    public static void e(Throwable tr) {
        if (allowE) {
            Logger.e(tr.getMessage());
        }
    }

    public static void e(String msg, Throwable tr) {
        if (allowE && null != msg) {
            Logger.e(msg, tr);
        }
    }

    public static void json(String msg) {
        if (allowE && null != msg) {
            Logger.json(msg);
        }
    }

    public static void xml(String msg) {
        if (allowE && null != msg) {
            Logger.xml(msg);
        }
    }


}
