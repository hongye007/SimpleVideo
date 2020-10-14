package com.tencent.liteav.trtc.impl;

import android.content.*;

public class TRTCAudioServerConfig
{
    public static final boolean DEFAULT_ENABLE_OPENSL = false;
    public static final boolean DEFAULT_ENABLE_AUTO_RESTART_DEVICE = false;
    public static final int DEFAULT_DEVICE_AUTO_RESTART_MIN_INTERVAL = 5000;
    public static final int DEFAULT_16K_PACKAGE_STRATEGY = 0;
    public static final int DEFAULT_MAX_SELECTED_PLAY_STREAMS = 0;
    private static final String AUDIO_SERVER_CONFIG = "trtc_audio_server_config";
    private static final String KEY_ENABLE_OPENSL = "enable_opensl";
    private static final String KEY_ENABLE_AUTO_RESTART_DEVICE = "enable_auto_restart_device";
    private static final String KEY_DEVICE_AUTO_RESTART_MIN_INTERVAL = "device_auto_restart_interval";
    private static final String KEY_16K_PACKAGE_STRATEGY = "16k_package_strategy";
    private static final String KEY_MAX_SELECTED_PLAY_STREAMS = "max_selected_play_streams";
    public boolean enableOpenSL;
    public boolean enableAutoRestartDevice;
    public int deviceAutoRestartMinInterval;
    public int audio16KPackageStrategy;
    public int maxSelectedPlayStreams;
    
    public TRTCAudioServerConfig() {
        this.enableOpenSL = false;
        this.enableAutoRestartDevice = false;
        this.deviceAutoRestartMinInterval = 5000;
        this.audio16KPackageStrategy = 0;
        this.maxSelectedPlayStreams = 0;
    }
    
    @Override
    public String toString() {
        return "enableOpenSL: " + this.enableOpenSL + ", enableAutoRestartDevice: " + this.enableAutoRestartDevice + ", deviceAutoRestartMinInterval: " + this.deviceAutoRestartMinInterval + ", audio16KPackageStrategy: " + this.audio16KPackageStrategy;
    }
    
    public static void saveToSharedPreferences(final Context context, final TRTCAudioServerConfig trtcAudioServerConfig) {
        synchronized (TRTCAudioServerConfig.class) {
            final SharedPreferences.Editor edit = context.getSharedPreferences("trtc_audio_server_config", 0).edit();
            edit.putBoolean("enable_opensl", trtcAudioServerConfig.enableOpenSL);
            edit.putBoolean("enable_auto_restart_device", trtcAudioServerConfig.enableAutoRestartDevice);
            edit.putInt("device_auto_restart_interval", trtcAudioServerConfig.deviceAutoRestartMinInterval);
            edit.putInt("16k_package_strategy", trtcAudioServerConfig.audio16KPackageStrategy);
            edit.putInt("max_selected_play_streams", trtcAudioServerConfig.maxSelectedPlayStreams);
            edit.apply();
        }
    }
    
    public static TRTCAudioServerConfig loadFromSharedPreferences(final Context context) {
        synchronized (TRTCAudioServerConfig.class) {
            final TRTCAudioServerConfig trtcAudioServerConfig = new TRTCAudioServerConfig();
            final SharedPreferences sharedPreferences = context.getSharedPreferences("trtc_audio_server_config", 0);
            trtcAudioServerConfig.enableOpenSL = sharedPreferences.getBoolean("enable_opensl", false);
            trtcAudioServerConfig.enableAutoRestartDevice = sharedPreferences.getBoolean("enable_auto_restart_device", false);
            trtcAudioServerConfig.deviceAutoRestartMinInterval = sharedPreferences.getInt("device_auto_restart_interval", 5000);
            trtcAudioServerConfig.audio16KPackageStrategy = sharedPreferences.getInt("16k_package_strategy", 0);
            trtcAudioServerConfig.maxSelectedPlayStreams = sharedPreferences.getInt("max_selected_play_streams", 0);
            return trtcAudioServerConfig;
        }
    }
}
