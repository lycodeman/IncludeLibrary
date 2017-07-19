package com.ly.rxlibrary.utils.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author xp
 *         ly在前者基础上增加了，链式调用，单例模式，以及存放object对象
 *         （注意context的调用最好使用application,以免造成泄露）
 */
public final class SPUtils {

    private static Context context;
    private static SPUtils spUtils;

    private SPUtils() {
    }

    public static SPUtils getInstanse(Context mContext) {
        if (spUtils == null) {
            synchronized (SPUtils.class) {
                if (spUtils == null) {
                    spUtils = new SPUtils();
                }
            }
        }
        if (context == null) {
            context = mContext.getApplicationContext();
        }
        return spUtils;
    }

    private static String FILE_NAME;

    /**
     * 保存在手机里面的文件名
     */

    public SPUtils setFileName(String fileName) {
        FILE_NAME = fileName;
        return spUtils;
    }

    public <T> SPUtils put(String key, T value) {
        if (FILE_NAME == null || "".equals(FILE_NAME)) {
            Toast.makeText(context, "找不到FILE_NAME", Toast.LENGTH_SHORT).show();
            Log.e("SPUtils", "找不到FILE_NAME");
            return spUtils;
        }
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (value == null) {
            editor.putString(key, null);
        } else {
            if (value.getClass() == Boolean.class) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value.getClass() == Float.class) {
                editor.putFloat(key, (Float) value);
            } else if (value.getClass() == Integer.class) {
                editor.putInt(key, (Integer) value);
            } else if (value.getClass() == Long.class) {
                editor.putLong(key, (Long) value);
            } else if (value.getClass() == String.class) {
                editor.putString(key, (String) value);
            } else {
                throw new RuntimeException("the put value type can't support.");
            }
        }

        SharedPreferencesCompat.apply(editor);
        return spUtils;
    }

    public String get(String key, String defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public boolean get(String key, boolean defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public float get(String key, float defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getFloat(key, defaultValue);
    }

    public int get(String key, int defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    public long get(String key, long defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getLong(key, defaultValue);
    }

    public void remove(String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit().remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    public SPUtils clear() {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit().clear();
        SharedPreferencesCompat.apply(editor);
        return spUtils;
    }

    public <E> SPUtils saveObeject(String entiytName, E entity) {
        spUtils.put(entiytName, new Gson().toJson(entity));
        return spUtils;
    }

    public <E> E getObeject(String entiytName, Class<E> entity) {
        E ob = null;
        ob = new Gson().fromJson(spUtils.get(entiytName, ""),  entity);
        return ob;
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return 只支持基本数据类型
     */
    public Object getParam(Context context, String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return spUtils;
    }

    /**
     * 检查是否可以调用apply方法，否则调用commit
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }
            return null;
        }

        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }

}
