package com.simple.player.tools;

/**
 * 描述：
 * <p>
 * Created BY zhouwenye  2020/5/12-00:19
 */
public class MediaEnvHelper {

    private static volatile boolean mIsLibLoaded = false;

    public static void loadLibrariesOnce() {
        synchronized (MediaEnvHelper.class) {
            if (!mIsLibLoaded) {
                System.loadLibrary("dp-media");
                System.loadLibrary("dpffmpeg");
                mIsLibLoaded = true;
            }
        }
    }
}
