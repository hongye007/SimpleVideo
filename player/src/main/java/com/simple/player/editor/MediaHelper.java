package com.simple.player.editor;

import android.text.TextUtils;
import android.util.Log;

import com.simple.player.tools.MediaEnvHelper;

import java.io.File;

/**
 * 描述：
 * <p>
 * Created BY zhouwenye  2020/5/12-10:50
 */
public class MediaHelper {

    public MediaHelper(String origin, String target) {
        MediaEnvHelper.loadLibrariesOnce();
        mModel.originFile = origin;
        mModel.targetFile = target;
    }

    private MediaEditModel mModel = new MediaEditModel();

    public MediaHelper setTimeClip(long start, long end) {
        mModel.startTime = start;
        mModel.endTime = end;
        return this;
    }

    public int processVideo() {
        if (TextUtils.isEmpty(mModel.originFile) || TextUtils.isEmpty(mModel.targetFile)) {
            return -101;
        }
        Log.i("java-tag","origin: "+new File(mModel.originFile).exists()+"  target:"+new File(mModel.targetFile).exists());
        if (mModel.endTime == 0 || mModel.endTime <= mModel.startTime + 1) {
            return -102;
        }
        return jni_processVideo(mModel);
    }

    native int jni_processVideo(MediaEditModel model);
}
