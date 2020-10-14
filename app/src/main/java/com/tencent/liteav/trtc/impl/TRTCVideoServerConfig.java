package com.tencent.liteav.trtc.impl;

import android.content.*;

public class TRTCVideoServerConfig
{
    private static final String VIDEO_SERVER_CONFIG = "trtc_video_server_config";
    private static final String KEY_ENABLE_DEC_VUI = "enable_hw_vui";
    public boolean enableHWVUI;
    
    public TRTCVideoServerConfig() {
        this.enableHWVUI = true;
    }
    
    @Override
    public String toString() {
        return "enableHWVUI: " + this.enableHWVUI;
    }
    
    public static void saveToSharedPreferences(final Context context, final TRTCVideoServerConfig trtcVideoServerConfig) {
        synchronized (TRTCVideoServerConfig.class) {
            final SharedPreferences.Editor edit = context.getSharedPreferences("trtc_video_server_config", 0).edit();
            edit.putBoolean("enable_hw_vui", trtcVideoServerConfig.enableHWVUI);
            edit.apply();
        }
    }
    
    public static TRTCVideoServerConfig loadFromSharedPreferences(final Context context) {
        synchronized (TRTCVideoServerConfig.class) {
            final TRTCVideoServerConfig trtcVideoServerConfig = new TRTCVideoServerConfig();
            trtcVideoServerConfig.enableHWVUI = context.getSharedPreferences("trtc_video_server_config", 0).getBoolean("enable_hw_vui", true);
            return trtcVideoServerConfig;
        }
    }
}
