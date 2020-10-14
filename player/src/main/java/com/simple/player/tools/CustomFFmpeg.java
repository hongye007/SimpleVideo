package com.simple.player.tools;

import android.view.Surface;

import com.simple.player.tools.MediaEnvHelper;

/**
 * 描述：
 * <p>
 * Created BY zhouwenye  2020/4/17-22:39
 */
public class CustomFFmpeg {

    public static void nativeHello() {
        MediaEnvHelper.loadLibrariesOnce();
        printHello();
    }
    static native void printHello();
}
