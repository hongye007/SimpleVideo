package com.tencent.rtmp;

import com.tencent.liteav.basic.a.*;
import android.graphics.*;
import java.util.*;

public class TXLivePushConfig
{
    public static final int DEFAULT_MAX_VIDEO_BITRATE = 1200;
    public static final int DEFAULT_MIN_VIDEO_BITRATE = 800;
    int mCustomModeType;
    int mAudioSample;
    int mAudioBitrate;
    int mAudioChannels;
    int mVideoFPS;
    c mVideoResolution;
    int mVideoBitrate;
    int mMaxVideoBitrate;
    int mMinVideoBitrate;
    int mBeautyLevel;
    int mWhiteningLevel;
    int mRuddyLevel;
    int mEyeScaleLevel;
    int mFaceSlimLevel;
    int mConnectRetryCount;
    int mConnectRetryInterval;
    int mWatermarkX;
    int mWatermarkY;
    Bitmap mWatermark;
    float mWatermarkXF;
    float mWatermarkYF;
    float mWatermarkWidth;
    int mVideoEncodeGop;
    boolean mVideoEncoderXMirror;
    boolean mEnableHighResolutionCapture;
    boolean mEnableVideoHardEncoderMainProfile;
    String mVideoPreProcessLibrary;
    String mVideoPreProcessFuncName;
    String mAudioPreProcessLibrary;
    String mAudioPreProcessFuncName;
    boolean mFrontCamera;
    boolean mAutoAdjustBitrate;
    int mAutoAdjustStrategy;
    int mHardwareAccel;
    boolean mTouchFocus;
    boolean mEnableZoom;
    int mHomeOrientation;
    Bitmap mPauseImg;
    int mPauseTime;
    int mPauseFps;
    int mPauseFlag;
    boolean mEnableAec;
    boolean mEnableAgc;
    boolean mEnableAns;
    boolean mEnableAudioPreview;
    boolean mEnableScreenCaptureAutoRotate;
    boolean mEnablePureAudioPush;
    boolean mEnableNearestIP;
    int mVolumeType;
    int mLocalVideoMirrorType;
    int mRtmpChannelType;
    HashMap<String, String> mMetaData;
    
    public TXLivePushConfig() {
        this.mCustomModeType = 0;
        this.mAudioSample = 48000;
        this.mAudioChannels = 1;
        this.mVideoFPS = 20;
        this.mVideoResolution = c.c;
        this.mVideoBitrate = 1200;
        this.mMaxVideoBitrate = 1500;
        this.mMinVideoBitrate = 800;
        this.mBeautyLevel = 0;
        this.mWhiteningLevel = 0;
        this.mRuddyLevel = 0;
        this.mEyeScaleLevel = 0;
        this.mFaceSlimLevel = 0;
        this.mConnectRetryCount = 3;
        this.mConnectRetryInterval = 3;
        this.mWatermarkX = 0;
        this.mWatermarkY = 0;
        this.mWatermarkXF = 0.0f;
        this.mWatermarkYF = 0.0f;
        this.mWatermarkWidth = -1.0f;
        this.mVideoEncodeGop = 3;
        this.mVideoEncoderXMirror = false;
        this.mEnableHighResolutionCapture = true;
        this.mEnableVideoHardEncoderMainProfile = true;
        this.mFrontCamera = true;
        this.mAutoAdjustBitrate = false;
        this.mAutoAdjustStrategy = 0;
        this.mHardwareAccel = 2;
        this.mTouchFocus = true;
        this.mEnableZoom = false;
        this.mHomeOrientation = 1;
        this.mPauseImg = null;
        this.mPauseTime = 300;
        this.mPauseFps = 5;
        this.mPauseFlag = 1;
        this.mEnableAec = false;
        this.mEnableAgc = false;
        this.mEnableAns = false;
        this.mEnableAudioPreview = false;
        this.mEnableScreenCaptureAutoRotate = false;
        this.mEnablePureAudioPush = false;
        this.mEnableNearestIP = true;
        this.mVolumeType = 0;
        this.mLocalVideoMirrorType = 0;
        this.mRtmpChannelType = 0;
    }
    
    public void setHomeOrientation(final int mHomeOrientation) {
        this.mHomeOrientation = mHomeOrientation;
    }
    
    public void setTouchFocus(final boolean mTouchFocus) {
        this.mTouchFocus = mTouchFocus;
    }
    
    public void setEnableZoom(final boolean mEnableZoom) {
        this.mEnableZoom = mEnableZoom;
    }
    
    public void setWatermark(final Bitmap mWatermark, final int mWatermarkX, final int mWatermarkY) {
        this.mWatermark = mWatermark;
        this.mWatermarkX = mWatermarkX;
        this.mWatermarkY = mWatermarkY;
    }
    
    public void setWatermark(final Bitmap mWatermark, final float mWatermarkXF, final float mWatermarkYF, final float mWatermarkWidth) {
        this.mWatermark = mWatermark;
        this.mWatermarkXF = mWatermarkXF;
        this.mWatermarkYF = mWatermarkYF;
        this.mWatermarkWidth = mWatermarkWidth;
    }
    
    public void setLocalVideoMirrorType(final int mLocalVideoMirrorType) {
        this.mLocalVideoMirrorType = mLocalVideoMirrorType;
    }
    
    public void setPauseImg(final Bitmap mPauseImg) {
        this.mPauseImg = mPauseImg;
    }
    
    public void setPauseImg(final int mPauseTime, final int mPauseFps) {
        this.mPauseTime = mPauseTime;
        this.mPauseFps = mPauseFps;
    }
    
    public void setPauseFlag(final int mPauseFlag) {
        this.mPauseFlag = mPauseFlag;
    }
    
    public void setVideoResolution(final int n) {
        switch (n) {
            case 0: {
                this.mVideoResolution = c.b;
                break;
            }
            case 1: {
                this.mVideoResolution = c.c;
                break;
            }
            case 2: {
                this.mVideoResolution = c.d;
                break;
            }
            case 3: {
                this.mVideoResolution = c.e;
                break;
            }
            case 4: {
                this.mVideoResolution = c.f;
                break;
            }
            case 5: {
                this.mVideoResolution = c.g;
                break;
            }
            case 6: {
                this.mVideoResolution = c.h;
                break;
            }
            case 7: {
                this.mVideoResolution = c.i;
                break;
            }
            case 8: {
                this.mVideoResolution = c.j;
                break;
            }
            case 9: {
                this.mVideoResolution = c.k;
                break;
            }
            case 10: {
                this.mVideoResolution = c.l;
                break;
            }
            case 11: {
                this.mVideoResolution = c.m;
                break;
            }
            case 12: {
                this.mVideoResolution = c.n;
                break;
            }
            case 13: {
                this.mVideoResolution = c.o;
                break;
            }
            case 14: {
                this.mVideoResolution = c.p;
                break;
            }
            case 15: {
                this.mVideoResolution = c.q;
                break;
            }
            case 16: {
                this.mVideoResolution = c.r;
                break;
            }
            case 17: {
                this.mVideoResolution = c.s;
                break;
            }
            case 18: {
                this.mVideoResolution = c.t;
                break;
            }
            case 19: {
                this.mVideoResolution = c.u;
                break;
            }
            case 30: {
                this.mVideoResolution = c.w;
                break;
            }
            case 31: {
                this.mVideoResolution = c.x;
                break;
            }
        }
    }
    
    public void setVideoFPS(final int mVideoFPS) {
        this.mVideoFPS = mVideoFPS;
    }
    
    public void setVideoEncodeGop(final int mVideoEncodeGop) {
        this.mVideoEncodeGop = mVideoEncodeGop;
    }
    
    public void setVideoBitrate(final int mVideoBitrate) {
        this.mVideoBitrate = mVideoBitrate;
    }
    
    public void setMaxVideoBitrate(final int mMaxVideoBitrate) {
        this.mMaxVideoBitrate = mMaxVideoBitrate;
    }
    
    public void setMinVideoBitrate(final int mMinVideoBitrate) {
        this.mMinVideoBitrate = mMinVideoBitrate;
    }
    
    public void setAutoAdjustBitrate(final boolean mAutoAdjustBitrate) {
        this.mAutoAdjustBitrate = mAutoAdjustBitrate;
    }
    
    public void setAutoAdjustStrategy(final int mAutoAdjustStrategy) {
        this.mAutoAdjustStrategy = mAutoAdjustStrategy;
    }
    
    public void setAudioSampleRate(final int mAudioSample) {
        this.mAudioSample = mAudioSample;
    }
    
    public void setAudioChannels(final int mAudioChannels) {
        this.mAudioChannels = mAudioChannels;
    }
    
    public void enablePureAudioPush(final boolean mEnablePureAudioPush) {
        this.mEnablePureAudioPush = mEnablePureAudioPush;
    }
    
    public void enableScreenCaptureAutoRotate(final boolean mEnableScreenCaptureAutoRotate) {
        this.mEnableScreenCaptureAutoRotate = mEnableScreenCaptureAutoRotate;
    }
    
    public void enableHighResolutionCaptureMode(final boolean mEnableHighResolutionCapture) {
        this.mEnableHighResolutionCapture = mEnableHighResolutionCapture;
    }
    
    public void setVideoEncoderXMirror(final boolean mVideoEncoderXMirror) {
        this.mVideoEncoderXMirror = mVideoEncoderXMirror;
    }
    
    public void enableAudioEarMonitoring(final boolean mEnableAudioPreview) {
        this.mEnableAudioPreview = mEnableAudioPreview;
    }
    
    public void setConnectRetryCount(final int mConnectRetryCount) {
        this.mConnectRetryCount = mConnectRetryCount;
    }
    
    public void setConnectRetryInterval(final int mConnectRetryInterval) {
        this.mConnectRetryInterval = mConnectRetryInterval;
    }
    
    public void setCustomModeType(final int mCustomModeType) {
        this.mCustomModeType = mCustomModeType;
    }
    
    public void enableAEC(final boolean mEnableAec) {
        this.mEnableAec = mEnableAec;
    }
    
    public void enableAGC(final boolean mEnableAgc) {
        this.mEnableAgc = mEnableAgc;
    }
    
    public void enableANS(final boolean mEnableAns) {
        this.mEnableAns = mEnableAns;
    }
    
    public void setVolumeType(final int mVolumeType) {
        this.mVolumeType = mVolumeType;
    }
    
    public void setHardwareAcceleration(int mHardwareAccel) {
        if (mHardwareAccel < 0) {
            mHardwareAccel = 2;
        }
        if (mHardwareAccel > 2) {
            mHardwareAccel = 2;
        }
        this.mHardwareAccel = mHardwareAccel;
    }
    
    public void enableVideoHardEncoderMainProfile(final boolean mEnableVideoHardEncoderMainProfile) {
        this.mEnableVideoHardEncoderMainProfile = mEnableVideoHardEncoderMainProfile;
    }
    
    public void setMetaData(final HashMap<String, String> mMetaData) {
        this.mMetaData = mMetaData;
    }
    
    @Deprecated
    public void setFrontCamera(final boolean mFrontCamera) {
        this.mFrontCamera = mFrontCamera;
    }
    
    @Deprecated
    public void setBeautyFilter(final int mBeautyLevel, final int mWhiteningLevel, final int mRuddyLevel) {
        this.mBeautyLevel = mBeautyLevel;
        this.mWhiteningLevel = mWhiteningLevel;
        this.mRuddyLevel = mRuddyLevel;
    }
    
    @Deprecated
    public void setEyeScaleLevel(final int mEyeScaleLevel) {
        this.mEyeScaleLevel = mEyeScaleLevel;
    }
    
    @Deprecated
    public void setFaceSlimLevel(final int mFaceSlimLevel) {
        this.mFaceSlimLevel = mFaceSlimLevel;
    }
    
    @Deprecated
    public void setRtmpChannelType(final int mRtmpChannelType) {
        this.mRtmpChannelType = mRtmpChannelType;
    }
    
    @Deprecated
    public void enableNearestIP(final boolean mEnableNearestIP) {
        this.mEnableNearestIP = mEnableNearestIP;
    }
    
    public void setCustomVideoPreProcessLibrary(final String mVideoPreProcessLibrary, final String mVideoPreProcessFuncName) {
        this.mVideoPreProcessLibrary = mVideoPreProcessLibrary;
        this.mVideoPreProcessFuncName = mVideoPreProcessFuncName;
    }
    
    public void setCustomAudioPreProcessLibrary(final String mAudioPreProcessLibrary, final String mAudioPreProcessFuncName) {
        this.mAudioPreProcessLibrary = mAudioPreProcessLibrary;
        this.mAudioPreProcessFuncName = mAudioPreProcessFuncName;
    }
}
