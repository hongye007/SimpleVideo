package com.simple.player.media;

import android.view.Surface;

import com.simple.player.tools.MediaEnvHelper;

/**
 * 描述：
 * <p>
 * Created BY zhouwenye  2020/5/12-00:06
 */
public class DPPlayer {

    public DPPlayer(){
        MediaEnvHelper.loadLibrariesOnce();
    }

    public void prepare(String url, Surface surface) {
        media_init(url,surface);
    }

    public void play() {
        media_play();
    }

    native void media_init(String url, Surface surface);

    native void media_play();
}
