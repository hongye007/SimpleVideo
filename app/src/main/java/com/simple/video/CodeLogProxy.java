package com.simple.video;

import android.util.Log;

public class CodeLogProxy {

    private static CodeLogProxy mInstance = null;

    private CodeLogProxy() {

    }

    public static CodeLogProxy getInstance() {
        if (mInstance == null) {
            synchronized (CodeLogProxy.class) {
                if (mInstance == null) {
                    mInstance = new CodeLogProxy();
                }
            }
        }
        return mInstance;
    }

    public void i(Class clazz, String sub, String msg) {
        Log.i(clazz.getName() + "-" + sub, msg);
    }

    public void e(Class clazz, String sub, String msg) {
        Log.e(clazz.getName() + "-" + sub, msg);
    }

    public void w(Class clazz, String sub, String msg) {
        Log.w(clazz.getName() + "-" + sub, msg);
    }

    public void d(Class clazz, String sub, String msg) {
        Log.d(clazz.getName() + "-" + sub, msg);
    }
}
