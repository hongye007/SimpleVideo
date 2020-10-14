package com.tencent.liteav.audio.impl;

import java.lang.ref.*;
import com.tencent.liteav.basic.log.*;
import android.content.*;
import com.tencent.liteav.basic.util.*;
import android.content.pm.*;
import java.io.*;
import com.tencent.liteav.basic.structs.*;
import com.tencent.liteav.basic.module.*;
import com.tencent.liteav.audio.*;

public class TXCAudioEngineJNI
{
    private static final String TAG = "TXCAudioEngineJNI";
    private static a mAudioDumpingListener;
    private static WeakReference<d> mAudioCaptureDataListener;
    
    public static void setAudioCaptureDataListener(final WeakReference<d> mAudioCaptureDataListener) {
        TXCAudioEngineJNI.mAudioCaptureDataListener = mAudioCaptureDataListener;
        nativeSetAudioEngineCaptureDataCallback(null != TXCAudioEngineJNI.mAudioCaptureDataListener);
        nativeSetAudioEngineCaptureRawDataCallback(null != TXCAudioEngineJNI.mAudioCaptureDataListener);
        nativeSetAudioEngineEncodedDataCallback(null != TXCAudioEngineJNI.mAudioCaptureDataListener);
    }
    
    public static void onRecordRawPcmData(final byte[] array, final long n, final int n2, final int n3, final int n4) {
        if (TXCAudioEngineJNI.mAudioCaptureDataListener != null && TXCAudioEngineJNI.mAudioCaptureDataListener.get() != null) {
            TXCAudioEngineJNI.mAudioCaptureDataListener.get().onRecordRawPcmData(array, n, n2, n3, n4, false);
        }
    }
    
    public static void onRecordPcmData(final byte[] array, final long n, final int n2, final int n3, final int n4) {
        if (TXCAudioEngineJNI.mAudioCaptureDataListener != null && TXCAudioEngineJNI.mAudioCaptureDataListener.get() != null) {
            TXCAudioEngineJNI.mAudioCaptureDataListener.get().onRecordPcmData(array, n, n2, n3, n4);
        }
    }
    
    public static void onRecordEncData(final byte[] array, final long n, final int n2, final int n3) {
        if (TXCAudioEngineJNI.mAudioCaptureDataListener != null && TXCAudioEngineJNI.mAudioCaptureDataListener.get() != null) {
            TXCAudioEngineJNI.mAudioCaptureDataListener.get().onRecordEncData(array, n, n2, n3, 16);
        }
    }
    
    public static void onRecordError(final int n, final String s) {
        TXCLog.e("TXCAudioEngineJNI", "onRecordError: " + n + ", " + s);
        if (TXCAudioEngineJNI.mAudioCaptureDataListener != null && TXCAudioEngineJNI.mAudioCaptureDataListener.get() != null) {
            TXCAudioEngineJNI.mAudioCaptureDataListener.get().onRecordError(n, s);
        }
    }
    
    public static void onEvent(final String s, final int n, final String s2, final String s3) {
        TXCAudioEngine.getInstance().onEvent(s, n, s2, s3);
    }
    
    public static void onError(final String s, final int n, final String s2, final String s3) {
        TXCAudioEngine.getInstance().onError(s, n, s2, s3);
    }
    
    public static void onLocalAudioWriteFail() {
        if (TXCAudioEngineJNI.mAudioDumpingListener != null) {
            TXCAudioEngineJNI.mAudioDumpingListener.onLocalAudioWriteFailed();
        }
    }
    
    public static void SetAudioDumpingListener(final a mAudioDumpingListener) {
        TXCAudioEngineJNI.mAudioDumpingListener = mAudioDumpingListener;
    }
    
    public static void InitTraeEngineLibrary(final Context context) {
        if (context == null) {
            TXCLog.e("TXCAudioEngineJNI", "InitTraeEngineLibrary failed, context is null!");
            return;
        }
        try {
            final ApplicationInfo applicationInfo = context.getApplicationInfo();
            final String nativeLibraryDir = applicationInfo.nativeLibraryDir;
            final String string = applicationInfo.dataDir + "/lib";
            final String string2 = "/data/data/" + applicationInfo.packageName + "/lib";
            String g = f.g();
            if (g == null) {
                g = "";
            }
            nativeAppendLibraryPath("add_libpath:" + nativeLibraryDir);
            nativeAppendLibraryPath("add_libpath:" + string);
            nativeAppendLibraryPath("add_libpath:" + string2);
            nativeAppendLibraryPath("add_libpath:" + g);
        }
        catch (UnsatisfiedLinkError unsatisfiedLinkError) {
            TXCLog.e("TXCAudioEngineJNI", "init trae engine library failed.", unsatisfiedLinkError);
        }
    }
    
    public static boolean nativeCheckTraeEngine(final Context context) {
        if (context == null) {
            TXCLog.e("TXCAudioEngineJNI", "nativeCheckTraeEngine failed, context is null!");
            return false;
        }
        if (f.a("traeimp-rtmp")) {
            TXCLog.e("TXCAudioEngineJNI", "link traeimp-rtmp success !");
            return true;
        }
        final ApplicationInfo applicationInfo = context.getApplicationInfo();
        final String nativeLibraryDir = applicationInfo.nativeLibraryDir;
        final String string = applicationInfo.dataDir + "/lib";
        final String string2 = "/data/data/" + applicationInfo.packageName + "/lib";
        String g = f.g();
        if (g == null) {
            g = "";
        }
        final String s = "/libtraeimp-rtmp.so";
        boolean b = false;
        if (new File(nativeLibraryDir + s).exists()) {
            b = true;
        }
        else {
            TXCLog.w("TXCAudioEngineJNI", "nativeCheckTraeEngine load so error " + nativeLibraryDir + s);
            if (new File(string + s).exists()) {
                b = true;
            }
            else {
                TXCLog.w("TXCAudioEngineJNI", "nativeCheckTraeEngine load so error " + string + s);
                if (new File(string2 + s).exists()) {
                    b = true;
                }
                else {
                    TXCLog.w("TXCAudioEngineJNI", "nativeCheckTraeEngine load so error " + string2 + s);
                    if (new File(g + s).exists()) {
                        b = true;
                    }
                    else {
                        TXCLog.w("TXCAudioEngineJNI", "nativeCheckTraeEngine load so error " + g + s);
                    }
                }
            }
        }
        if (b) {
            return true;
        }
        TXCLog.e("TXCAudioEngineJNI", "nativeCheckTraeEngine failed, can not find trae libs !");
        return false;
    }
    
    public static void pauseAudioCapture(final boolean b) {
        nativePauseAudioCapture(b);
    }
    
    public static void resumeAudioCapture() {
        nativeResumeAudioCapture();
    }
    
    public static void sendCustomPCMData(final byte[] array, final int n, final int n2) {
        nativeSendCustomPCMData(array, array.length, 0L, n, n2);
    }
    
    public static void sendCustomPCMData(final com.tencent.liteav.basic.structs.a a) {
        nativeSendCustomPCMData(a.audioData, a.audioData.length, a.timestamp, a.sampleRate, a.channelsPerSample);
    }
    
    public static StatusBucket getStatus(final int n) {
        return nativeGetStatus(n);
    }
    
    public static native void nativeSendCustomPCMData(final byte[] p0, final int p1, final long p2, final int p3, final int p4);
    
    public static native void nativeAppendLibraryPath(final String p0);
    
    public static native void nativeCacheClassForNative();
    
    public static native void nativeSetAudioQuality(final int p0, final int p1);
    
    public static native void nativeStartLocalAudio(final int p0, final boolean p1);
    
    public static native void nativeStopLocalAudio();
    
    public static native void nativePauseLocalAudio();
    
    public static native void nativeResumeLocalAudio();
    
    public static native void nativeEnableMixMode(final boolean p0);
    
    public static native void nativeEnableEncodedDataPackWithTRAEHeaderCallback(final boolean p0);
    
    public static native void nativeEnableEncodedDataCallback(final boolean p0);
    
    public static native void nativeMuteLocalAudio(final boolean p0);
    
    public static native void nativeSetRecordReverb(final int p0);
    
    public static native void nativeSetSoftwareCaptureVolume(final float p0);
    
    public static native void nativeSetCaptureVoiceChanger(final int p0);
    
    public static native void nativeEnableAudioVolumeEvaluation(final boolean p0, final int p1);
    
    public static native void nativeEnableCaptureEOSMode(final boolean p0);
    
    public static native int nativeGetSoftwareCaptureVolumeLevel();
    
    public static native void nativeSetAudioRoute(final int p0);
    
    public static native void nativeSetMixingPlayoutVolume(final float p0);
    
    public static native void nativeSetSoftAEC(final int p0);
    
    public static native void nativeSetSoftANS(final int p0);
    
    public static native void nativeSetSoftAGC(final int p0);
    
    public static native void nativeSetAudioEncoderParam(final int p0, final int p1);
    
    public static native void nativeSetEncoderFECPercent(final float p0);
    
    public static native boolean nativeIsAudioDeviceCapturing();
    
    public static native boolean nativeIsAudioDevicePlaying();
    
    public static native void nativePauseAudioCapture(final boolean p0);
    
    public static native void nativeResumeAudioCapture();
    
    public static native void nativeUnInitEngine();
    
    public static native void nativeSetSystemVolumeType(final int p0);
    
    public static native void nativeSetPlayoutDevice(final int p0);
    
    public static native void nativeInitBeforeEngineCreate(final Context p0);
    
    public static native void nativeSetAudioEngineCaptureDataCallback(final boolean p0);
    
    public static native void nativeSetAudioEngineCaptureRawDataCallback(final boolean p0);
    
    public static native void nativeSetAudioEngineEncodedDataCallback(final boolean p0);
    
    public static native void nativeNewAudioSessionDuplicate(final Context p0);
    
    public static native void nativeDeleteAudioSessionDuplicate();
    
    public static native void nativeSetEventCallbackEnabled(final boolean p0);
    
    public static native StatusBucket nativeGetStatus(final int p0);
    
    public static native void nativeEnableAudioEarMonitoring(final boolean p0);
    
    public static native void nativeSetAudioEarMonitoringVolume(final int p0);
    
    public static native String nativeStartRemoteAudio(final TXCAudioEngine p0, final boolean p1, final String p2);
    
    public static native void nativeStopRemoteAudio(final String p0);
    
    public static native void nativeSetRemoteAudioCacheParams(final String p0, final boolean p1, final int p2, final int p3, final int p4);
    
    public static native void nativeSetRemoteAudioJitterCycle(final String p0, final long p1);
    
    public static native void nativeSetRemoteAudioBlockThreshold(final String p0, final long p1);
    
    public static native void nativeMuteRemoteAudio(final String p0, final boolean p1);
    
    public static native void nativeMuteRemoteAudioInSpeaker(final String p0, final boolean p1);
    
    public static native void nativeSetRemotePlayoutVolume(final String p0, final int p1);
    
    public static native void nativeSetAudioEngineRemoteStreamDataListener(final String p0, final boolean p1);
    
    public static native int nativeGetRemotePlayoutVolumeLevel(final String p0);
    
    public static native boolean nativeIsRemoteAudioPlaying(final String p0);
    
    public static native int nativeGetMixingPlayoutVolumeLevel();
    
    public static native void nativeSetPlayoutDataListener(final boolean p0);
    
    public static native int nativeStartLocalAudioDumping(final int p0, final int p1, final String p2);
    
    public static native void nativeStopLocalAudioDumping();
    
    public static native void nativeUseSysAudioDevice(final boolean p0);
    
    public static native void nativeInitAudioDevice();
    
    public static native void nativeSetEncoderSampleRate(final int p0);
    
    public static native void nativeSetEncoderChannels(final int p0);
    
    public static native int nativeGetEncoderSampleRate();
    
    public static native int nativeGetEncoderChannels();
    
    public static native TXCAudioEncoderConfig nativeGetEncoderConfig();
    
    public static native void nativeSetTRAEConfig(final String p0);
    
    public static native void nativeClean();
    
    public static native void nativeSetCaptureDataCallbackFormat(final int p0, final int p1, final int p2);
    
    public static native void nativeSetPlayoutDataCallbackFormat(final int p0, final int p1, final int p2);
    
    public static native void nativeSetRemoteStreamDataCallbackFormat(final String p0, final int p1, final int p2, final int p3);
    
    public static native void nativeEnableAutoRestartDevice(final boolean p0);
    
    public static native void nativeSetMaxSelectedPlayStreams(final int p0);
    
    public static native void nativeSetAudioPlayoutTunnelEnabled(final boolean p0);
    
    public static native void nativeForceCallbackMixedPlayAudioFrame(final boolean p0);
    
    static {
        f.f();
        nativeCacheClassForNative();
        TXCAudioEngineJNI.mAudioDumpingListener = null;
        TXCAudioEngineJNI.mAudioCaptureDataListener = null;
    }
    
    public interface a
    {
        void onLocalAudioWriteFailed();
    }
}
