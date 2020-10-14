package com.tencent.rtmp.sharp.jni;

import android.media.*;
import android.content.*;
import android.os.*;
import java.util.*;

public class VivoKTVHelper
{
    private static final String TAG = "VivoKTVHelper";
    private static final String KEY_KTV_MODE = "vivo_ktv_mode";
    private static final String KEY_VOL_MIC = "vivo_ktv_volume_mic";
    private static final String KEY_MIC_SRC = "vivo_ktv_rec_source";
    private static final String KEY_MIC_TYPE = "vivo_ktv_mic_type";
    private static final String KEY_PRESET = "vivo_ktv_preset_effect";
    private static final String KEY_PLAY_SRC = "vivo_ktv_play_source";
    private static final String KEY_EXT_SPKR = "vivo_ktv_ext_speaker";
    private final Object mParamLock;
    private AudioManager mAudioManager;
    private Context mContext;
    private static VivoKTVHelper mVivoKTVHelper;
    
    public VivoKTVHelper(final Context mContext) {
        this.mParamLock = new Object();
        this.mContext = mContext;
        this.mAudioManager = (AudioManager)this.mContext.getSystemService("audio");
    }
    
    public static VivoKTVHelper getInstance(final Context context) {
        if (VivoKTVHelper.mVivoKTVHelper == null) {
            VivoKTVHelper.mVivoKTVHelper = new VivoKTVHelper(context);
        }
        return VivoKTVHelper.mVivoKTVHelper;
    }
    
    public boolean isDeviceSupportKaraoke() {
        if (Build.MODEL.trim().contains("vivo")) {
            final StringTokenizer stringTokenizer = new StringTokenizer(this.mAudioManager.getParameters("vivo_ktv_mic_type"), "=");
            if (stringTokenizer.countTokens() != 2) {
                return false;
            }
            if (stringTokenizer.nextToken().equals("vivo_ktv_mic_type")) {
                final int int1 = Integer.parseInt(stringTokenizer.nextToken());
                if (int1 == 1 || int1 == 0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void openKTVDevice() {
        this.mAudioManager.setParameters("vivo_ktv_mode=1");
        this.isDeviceSupportKaraoke();
    }
    
    public void closeKTVDevice() {
        this.mAudioManager.setParameters("vivo_ktv_mode=0");
    }
    
    public void setMicVolParam(final int n) {
        synchronized (this.mParamLock) {
            if (null != this.mAudioManager) {
                final StringBuilder sb = new StringBuilder();
                sb.append("vivo_ktv_volume_mic").append("=").append(n);
                this.mAudioManager.setParameters(sb.toString());
            }
        }
    }
    
    public void setVoiceOutParam(final int n) {
        synchronized (this.mParamLock) {
            if (null != this.mAudioManager) {
                this.mAudioManager.setParameters("vivo_ktv_rec_source=" + n);
            }
        }
    }
    
    public void setPreModeParam(final int n) {
        synchronized (this.mParamLock) {
            if (null != this.mAudioManager) {
                this.mAudioManager.setParameters("vivo_ktv_preset_effect=" + n);
            }
        }
    }
    
    public void setPlayFeedbackParam(final int n) {
        synchronized (this.mParamLock) {
            if (null != this.mAudioManager) {
                this.mAudioManager.setParameters("vivo_ktv_play_source=" + n);
            }
        }
    }
    
    public void setExtSpeakerParam(final int n) {
        synchronized (this.mParamLock) {
            if (null != this.mAudioManager) {
                final StringBuilder sb = new StringBuilder();
                sb.append("vivo_ktv_ext_speaker").append("=").append(n);
                this.mAudioManager.setParameters(sb.toString());
            }
        }
    }
    
    public int getExtSpeakerParam() {
        return this.getKTVParam("vivo_ktv_ext_speaker");
    }
    
    public int getPlayFeedbackParam() {
        return this.getKTVParam("vivo_ktv_play_source");
    }
    
    public int getPreModeParam() {
        return this.getKTVParam("vivo_ktv_preset_effect");
    }
    
    public int getMicTypeParam() {
        return this.getKTVParam("vivo_ktv_mic_type");
    }
    
    public int getVoiceOutParam() {
        return this.getKTVParam("vivo_ktv_rec_source");
    }
    
    public int getMicVolParam() {
        return this.getKTVParam("vivo_ktv_volume_mic");
    }
    
    private int getKTVParam(final String s) {
        if (Build.MODEL.trim().contains("vivo")) {
            final StringTokenizer stringTokenizer = new StringTokenizer(this.mAudioManager.getParameters(s), "=");
            if (stringTokenizer.countTokens() == 2) {
                if (s.equals(stringTokenizer.nextToken())) {
                    return Integer.parseInt(stringTokenizer.nextToken().trim());
                }
            }
        }
        return 0;
    }
}
