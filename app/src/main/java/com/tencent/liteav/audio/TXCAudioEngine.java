package com.tencent.liteav.audio;

import java.lang.ref.*;
import com.tencent.liteav.basic.b.*;
import android.content.*;
import com.tencent.liteav.audio.impl.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.audio.impl.Play.*;
import com.tencent.liteav.audio.impl.Record.*;
import java.util.*;
import com.tencent.liteav.basic.module.*;

public class TXCAudioEngine implements b
{
    private static final String TAG = "AudioEngine :TXCAudioEngine_java";
    static TXCAudioEngine sInstance;
    private final ArrayList<WeakReference<a>> mCallbackList;
    protected boolean mDeviceIsRecording;
    protected boolean mIsCustomRecord;
    protected static Context mContext;
    private static boolean has_trae;
    private static WeakReference<c> mAudioCoreDataListener;
    protected static HashMap<String, WeakReference<c>> mJitterDataListenerMap;
    protected static HashMap<String, WeakReference<b>> mJitterEventListenerMap;
    private static volatile boolean has_init;
    protected boolean mIsCallComed;
    
    public static TXCAudioEngine getInstance() {
        return TXCAudioEngine.sInstance;
    }
    
    private TXCAudioEngine() {
        this.mCallbackList = new ArrayList<WeakReference<a>>();
        this.mDeviceIsRecording = false;
        this.mIsCustomRecord = false;
        this.mIsCallComed = false;
    }
    
    public static synchronized void CreateInstance(final Context context, final String s) {
        CreateInstanceWithoutInitDevice(context, s);
        TXCAudioEngineJNI.nativeInitAudioDevice();
    }
    
    public static synchronized void CreateInstanceWithoutInitDevice(final Context context, final String s) {
        TXCLog.i("AudioEngine :TXCAudioEngine_java", "CreateInstance: ");
        TXCAudioEngine.mContext = context.getApplicationContext();
        if (TXCAudioEngine.has_init) {
            TXCLog.i("AudioEngine :TXCAudioEngine_java", "CreateInstance already created~ ");
            return;
        }
        if (TXCAudioEngineJNI.nativeCheckTraeEngine(context)) {
            TXCAudioEngine.has_trae = true;
        }
        TXCAudioEngineJNI.nativeUseSysAudioDevice(!TXCAudioEngine.has_trae);
        if (TXCAudioEngine.has_trae) {
            TXCAudioEngineJNI.InitTraeEngineLibrary(context);
            TXCAudioEngineJNI.nativeSetTRAEConfig(s);
            TXCAudioEngineJNI.nativeInitBeforeEngineCreate(context);
            com.tencent.liteav.audio.impl.a.a().a(context.getApplicationContext());
            com.tencent.liteav.audio.impl.a.a().a(TXCAudioEngine.sInstance);
            TXCAudioEngineJNI.nativeNewAudioSessionDuplicate(TXCAudioEngine.mContext);
        }
        else {
            TXCMultAudioTrackPlayer.getInstance();
            TXCAudioSysRecord.getInstance();
        }
        TXCAudioEngine.has_init = true;
    }
    
    public static boolean hasTrae() {
        return TXCAudioEngine.has_trae;
    }
    
    public static boolean enableAudioVolumeEvaluation(final boolean b, final int n) {
        TXCLog.i("AudioEngine :TXCAudioEngine_java", "enableAudioVolumeEvaluation : " + b + "interval:" + n);
        TXCAudioEngineJNI.nativeEnableAudioVolumeEvaluation(b, n);
        return true;
    }
    
    public void setAudioQuality(final int n, final int n2) {
        TXCAudioEngineJNI.nativeSetAudioQuality(n, n2);
    }
    
    public void setEncoderSampleRate(final int n) {
        TXCAudioEngineJNI.nativeSetEncoderSampleRate(n);
    }
    
    public void setEncoderChannels(final int n) {
        TXCAudioEngineJNI.nativeSetEncoderChannels(n);
    }
    
    public int getEncoderSampleRate() {
        return TXCAudioEngineJNI.nativeGetEncoderSampleRate();
    }
    
    public int getEncoderChannels() {
        return TXCAudioEngineJNI.nativeGetEncoderChannels();
    }
    
    public TXCAudioEncoderConfig getAudioEncoderConfig() {
        return TXCAudioEngineJNI.nativeGetEncoderConfig();
    }
    
    public int startLocalAudio(final int n, final boolean b) {
        TXCLog.i("AudioEngine :TXCAudioEngine_java", "startLocalAudio audioFormat:" + n);
        if (TXCAudioEngine.mContext == null) {
            TXCLog.i("AudioEngine :TXCAudioEngine_java", "Please call CreateInstance fisrt!!!");
            return -901;
        }
        TXCAudioEngineJNI.InitTraeEngineLibrary(TXCAudioEngine.mContext);
        TXCAudioEngineJNI.nativeStartLocalAudio(n, b);
        this.mDeviceIsRecording = true;
        return 0;
    }
    
    public int stopLocalAudio() {
        TXCLog.i("AudioEngine :TXCAudioEngine_java", "stopLocalAudio");
        TXCAudioEngineJNI.nativeStopLocalAudio();
        this.mDeviceIsRecording = false;
        return 0;
    }
    
    public void pauseLocalAudio() {
        TXCAudioEngineJNI.nativePauseLocalAudio();
    }
    
    public void resumeLocalAudio() {
        TXCAudioEngineJNI.nativeResumeLocalAudio();
    }
    
    public void EnableMixMode(final boolean b) {
        TXCAudioEngineJNI.nativeEnableMixMode(b);
    }
    
    public void enableEncodedDataPackWithTRAEHeaderCallback(final boolean b) {
        TXCAudioEngineJNI.nativeEnableEncodedDataPackWithTRAEHeaderCallback(b);
    }
    
    public void enableEncodedDataCallback(final boolean b) {
        TXCAudioEngineJNI.nativeEnableEncodedDataCallback(b);
    }
    
    public int pauseAudioCapture(final boolean b) {
        TXCLog.i("AudioEngine :TXCAudioEngine_java", "pauseAudioCapture: " + b);
        TXCAudioEngineJNI.pauseAudioCapture(b);
        return 0;
    }
    
    public int resumeAudioCapture() {
        TXCLog.i("AudioEngine :TXCAudioEngine_java", "resumeRecord");
        TXCAudioEngineJNI.resumeAudioCapture();
        return 0;
    }
    
    public void sendCustomPCMData(final byte[] array, final int n, final int n2) {
        TXCAudioEngineJNI.sendCustomPCMData(array, n, n2);
    }
    
    public void sendCustomPCMData(final com.tencent.liteav.basic.structs.a a) {
        TXCAudioEngineJNI.sendCustomPCMData(a);
    }
    
    public boolean setAudioCaptureDataListener(final d d) {
        TXCLog.i("AudioEngine :TXCAudioEngine_java", "setRecordListener ");
        if (d == null) {
            TXCAudioEngineJNI.setAudioCaptureDataListener(null);
        }
        else {
            TXCAudioEngineJNI.setAudioCaptureDataListener((WeakReference<d>)new WeakReference((T)d));
        }
        return true;
    }
    
    public boolean muteLocalAudio(final boolean b) {
        TXCLog.i("AudioEngine :TXCAudioEngine_java", "setRecordMute: " + b);
        TXCAudioEngineJNI.nativeMuteLocalAudio(b);
        return true;
    }
    
    public boolean setReverbType(final TXAudioEffectManager.TXVoiceReverbType txVoiceReverbType) {
        TXCLog.i("AudioEngine :TXCAudioEngine_java", "setReverbType: " + txVoiceReverbType.getNativeValue());
        TXCAudioEngineJNI.nativeSetRecordReverb(txVoiceReverbType.getNativeValue());
        return true;
    }
    
    public boolean setSoftwareCaptureVolume(final float n) {
        TXCLog.i("AudioEngine :TXCAudioEngine_java", "setRecordVolume: " + n);
        TXCAudioEngineJNI.nativeSetSoftwareCaptureVolume(n);
        return true;
    }
    
    public boolean setMixingPlayoutVolume(final float n) {
        TXCLog.i("AudioEngine :TXCAudioEngine_java", "setPlayoutVolume: " + n);
        TXCAudioEngineJNI.nativeSetMixingPlayoutVolume(n);
        return true;
    }
    
    public void enableSoftAEC(final boolean b, int n) {
        TXCLog.i("AudioEngine :TXCAudioEngine_java", "enableSoftAEC: enable = " + b + " level = " + n);
        if (!b) {
            n = 0;
        }
        TXCAudioEngineJNI.nativeSetSoftAEC(n);
    }
    
    public void enableSoftANS(final boolean b, int n) {
        TXCLog.i("AudioEngine :TXCAudioEngine_java", "enableSoftANS: enable = " + b + " level = " + n);
        if (!b) {
            n = 0;
        }
        TXCAudioEngineJNI.nativeSetSoftANS(n);
    }
    
    public void enableSoftAGC(final boolean b, int n) {
        TXCLog.i("AudioEngine :TXCAudioEngine_java", "enableSoftAGC: enable = " + b + " level = " + n);
        if (!b) {
            n = 0;
        }
        TXCAudioEngineJNI.nativeSetSoftAGC(n);
    }
    
    public boolean setVoiceChangerType(final TXAudioEffectManager.TXVoiceChangerType txVoiceChangerType) {
        TXCLog.i("AudioEngine :TXCAudioEngine_java", "setVoiceChangerType " + txVoiceChangerType.getNativeValue());
        TXCAudioEngineJNI.nativeSetCaptureVoiceChanger(txVoiceChangerType.getNativeValue());
        return true;
    }
    
    public boolean setEncoderFECPercent(final float n) {
        TXCAudioEngineJNI.nativeSetEncoderFECPercent(n);
        return true;
    }
    
    public boolean setAudioEncoderParam(final int n, final int n2) {
        TXCAudioEngineJNI.nativeSetAudioEncoderParam(n, n2);
        return true;
    }
    
    public boolean enableCaptureEOSMode(final boolean b) {
        TXCLog.i("AudioEngine :TXCAudioEngine_java", "enableEosMode " + b);
        TXCAudioEngineJNI.nativeEnableCaptureEOSMode(b);
        return true;
    }
    
    public boolean isAudioDeviceCapturing() {
        final boolean nativeIsAudioDeviceCapturing = TXCAudioEngineJNI.nativeIsAudioDeviceCapturing();
        TXCLog.i("AudioEngine :TXCAudioEngine_java", "isRecording: " + nativeIsAudioDeviceCapturing);
        return nativeIsAudioDeviceCapturing;
    }
    
    public int getAECType() {
        return 2;
    }
    
    public int getSoftwareCaptureVolumeLevel() {
        return TXCAudioEngineJNI.nativeGetSoftwareCaptureVolumeLevel();
    }
    
    public void startRemoteAudio(final String s, final boolean b) {
        TXCAudioEngineJNI.nativeStartRemoteAudio(TXCAudioEngine.sInstance, b, s);
        TXCAudioEngineJNI.nativeSetRemoteAudioJitterCycle(s, com.tencent.liteav.basic.d.b.a().a("Audio", "LIVE_JitterCycle"));
        TXCAudioEngineJNI.nativeSetRemoteAudioBlockThreshold(s, com.tencent.liteav.basic.d.b.a().a("Audio", "LoadingThreshold"));
    }
    
    public void stopRemoteAudio(final String s) {
        if (s == null) {
            return;
        }
        TXCAudioEngineJNI.nativeStopRemoteAudio(s);
    }
    
    public void setSetAudioEngineRemoteStreamDataListener(final String s, final c c) {
        if (s == null) {
            return;
        }
        TXCAudioEngine.mJitterDataListenerMap.put(s, new WeakReference<c>(c));
        TXCAudioEngineJNI.nativeSetAudioEngineRemoteStreamDataListener(s, null != c);
    }
    
    public static void onAudioPlayPcmData(final String s, final byte[] array, final long n, final int n2, final int n3) {
        if (null != TXCAudioEngine.mJitterDataListenerMap.get(s)) {
            final c c = TXCAudioEngine.mJitterDataListenerMap.get(s).get();
            if (null != c) {
                c.onAudioPlayPcmData(s, array, n, n2, n3);
            }
        }
    }
    
    public void setRemoteAudioStreamEventListener(final String s, final b b) {
        if (s == null) {
            return;
        }
        TXCAudioEngine.mJitterEventListenerMap.put(s, new WeakReference<com.tencent.liteav.audio.b>(b));
    }
    
    public static void onAudioJitterBufferNotify(final String s, final int n, final String s2) {
        if (null != TXCAudioEngine.mJitterEventListenerMap.get(s)) {
            TXCLog.e("AudioEngine :TXCAudioEngine_java", "onAudioJitterBufferNotify  cur state " + n);
            final b b = TXCAudioEngine.mJitterEventListenerMap.get(s).get();
            if (b != null) {
                b.onAudioJitterBufferNotify(s, n, s2);
            }
        }
    }
    
    public void setRemoteAudioCacheParams(final String s, final boolean b, final int n, final int n2, final int n3) {
        TXCAudioEngineJNI.nativeSetRemoteAudioCacheParams(s, b, n, n2, n3);
    }
    
    public void muteRemoteAudio(final String s, final boolean b) {
        if (s == null) {
            return;
        }
        TXCAudioEngineJNI.nativeMuteRemoteAudio(s, b);
    }
    
    public void muteRemoteAudioInSpeaker(final String s, final boolean b) {
        if (s == null) {
            return;
        }
        TXCAudioEngineJNI.nativeMuteRemoteAudioInSpeaker(s, b);
    }
    
    public void setRemotePlayoutVolume(final String s, final int n) {
        if (s == null) {
            return;
        }
        TXCAudioEngineJNI.nativeSetRemotePlayoutVolume(s, n);
    }
    
    public boolean isRemoteAudioPlaying(final String s) {
        return s != null && TXCAudioEngineJNI.nativeIsRemoteAudioPlaying(s);
    }
    
    public int getRemotePlayoutVolumeLevel(final String s) {
        if (s == null) {
            return 0;
        }
        return TXCAudioEngineJNI.nativeGetRemotePlayoutVolumeLevel(s);
    }
    
    public int getPlaySampleRate() {
        return 48000;
    }
    
    public int getPlayChannels() {
        return 2;
    }
    
    public int getPlayAECType() {
        if (TXCAudioEngine.has_trae) {
            return 2;
        }
        return 0;
    }
    
    public void addEventCallback(final WeakReference<a> weakReference) {
        if (weakReference == null) {
            return;
        }
        synchronized (this.mCallbackList) {
            this.mCallbackList.add(weakReference);
            TXCAudioEngineJNI.nativeSetEventCallbackEnabled(true);
        }
    }
    
    public void onEvent(final String s, final int n, final String s2, final String s3) {
        final ArrayList<a> list = new ArrayList<a>();
        synchronized (this.mCallbackList) {
            if (this.mCallbackList.size() <= 0) {
                return;
            }
            final Iterator<WeakReference<a>> iterator = this.mCallbackList.iterator();
            while (iterator.hasNext()) {
                final a a = iterator.next().get();
                if (a != null) {
                    list.add(a);
                }
                else {
                    iterator.remove();
                }
            }
            if (this.mCallbackList.size() <= 0) {
                TXCAudioEngineJNI.nativeSetEventCallbackEnabled(false);
            }
        }
        final Iterator<a> iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().onEvent(s, n, s2, s3);
        }
    }
    
    public void onError(final String s, final int n, final String s2, final String s3) {
        final ArrayList<a> list = new ArrayList<a>();
        synchronized (this.mCallbackList) {
            if (this.mCallbackList.size() <= 0) {
                return;
            }
            final Iterator<WeakReference<a>> iterator = this.mCallbackList.iterator();
            while (iterator.hasNext()) {
                final a a = iterator.next().get();
                if (a != null) {
                    list.add(a);
                }
                else {
                    iterator.remove();
                }
            }
            if (this.mCallbackList.size() <= 0) {
                TXCAudioEngineJNI.nativeSetEventCallbackEnabled(false);
            }
        }
        final Iterator<a> iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().onError(s, n, s2, s3);
        }
    }
    
    public static void setPlayoutDataListener(final c c) {
        TXCAudioEngine.mAudioCoreDataListener = new WeakReference<c>(c);
        TXCAudioEngineJNI.nativeSetPlayoutDataListener(null != c);
    }
    
    public static void onCorePlayPcmData(final byte[] array, final long n, final int n2, final int n3) {
        if (TXCAudioEngine.mAudioCoreDataListener != null) {
            final c c = TXCAudioEngine.mAudioCoreDataListener.get();
            if (c != null) {
                c.onAudioPlayPcmData(null, array, n, n2, n3);
            }
        }
    }
    
    public static int getMixingPlayoutVolumeLevel() {
        return TXCAudioEngineJNI.nativeGetMixingPlayoutVolumeLevel();
    }
    
    public StatusBucket getStatus(final int n) {
        return TXCAudioEngineJNI.getStatus(n);
    }
    
    public static void setAudioRoute(final int n) {
        TXCLog.i("AudioEngine :TXCAudioEngine_java", "setAudioRoute: " + n);
        TXCAudioEngineJNI.nativeSetAudioRoute(n);
    }
    
    public static void setSystemVolumeType(final int n) {
        TXCLog.i("AudioEngine :TXCAudioEngine_java", "setSystemVolumeType: " + n);
        TXCAudioEngineJNI.nativeSetSystemVolumeType(n);
    }
    
    public static void enableAudioEarMonitoring(final boolean b) {
        TXCLog.i("AudioEngine :TXCAudioEngine_java", "enableAudioEarMonitoring: " + b);
        TXCAudioEngineJNI.nativeEnableAudioEarMonitoring(b);
    }
    
    public static void setAudioEarMonitoringVolume(final int n) {
        TXCLog.i("AudioEngine :TXCAudioEngine_java", "setAudioEarMonitoringVolume: " + n);
        TXCAudioEngineJNI.nativeSetAudioEarMonitoringVolume(n);
    }
    
    public int startLocalAudioDumping(final int n, final int n2, final String s) {
        return TXCAudioEngineJNI.nativeStartLocalAudioDumping(n, n2, s);
    }
    
    public void stopLocalAudioDumping() {
        TXCAudioEngineJNI.nativeStopLocalAudioDumping();
    }
    
    public void setAudioDumpingListener(final TXCAudioEngineJNI.a a) {
        TXCAudioEngineJNI.SetAudioDumpingListener(a);
    }
    
    public void setCaptureDataCallbackFormat(final int n, final int n2, final int n3) {
        TXCLog.i("AudioEngine :TXCAudioEngine_java", "setCaptureDataCallbackFormat: sampleRate-" + n + " channels-" + n2 + " length-" + n3);
        TXCAudioEngineJNI.nativeSetCaptureDataCallbackFormat(n, n2, n3);
    }
    
    public void setPlayoutDataCallbackFormat(final int n, final int n2, final int n3) {
        TXCLog.i("AudioEngine :TXCAudioEngine_java", "setPlayoutDataCallbackFormat: sampleRate-" + n + " channels-" + n2 + " length-" + n3);
        TXCAudioEngineJNI.nativeSetPlayoutDataCallbackFormat(n, n2, n3);
    }
    
    public void setRemoteStreamDataCallbackFormat(final String s, final int n, final int n2, final int n3) {
        TXCLog.i("AudioEngine :TXCAudioEngine_java", "setRemoteStreamDataCallbackFormat: id-" + s + " sampleRate-" + n + " channels-" + n2 + " length-" + n3);
        TXCAudioEngineJNI.nativeSetRemoteStreamDataCallbackFormat(s, n, n2, n3);
    }
    
    public void clean() {
        TXCAudioEngineJNI.nativeClean();
    }
    
    public void forceCallbackMixedPlayAudioFrame(final boolean b) {
        TXCAudioEngineJNI.nativeForceCallbackMixedPlayAudioFrame(b);
    }
    
    @Override
    public void onCallStateChanged(final int n) {
        switch (n) {
            case 1: {
                TXCLog.i("AudioEngine :TXCAudioEngine_java", "TelephonyManager.CALL_STATE_RINGING!");
                break;
            }
            case 2: {
                TXCLog.i("AudioEngine :TXCAudioEngine_java", "TelephonyManager.CALL_STATE_OFFHOOK!");
                TXCAudioEngineJNI.pauseAudioCapture(true);
                TXAudioEffectManagerImpl.getInstance().interruptAllMusics();
                this.mIsCallComed = true;
                break;
            }
            case 0: {
                TXCLog.i("AudioEngine :TXCAudioEngine_java", "TelephonyManager.CALL_STATE_IDLE!");
                if (this.mIsCallComed) {
                    this.mIsCallComed = false;
                    TXCAudioEngineJNI.resumeAudioCapture();
                    TXAudioEffectManagerImpl.getInstance().recoverAllMusics();
                    break;
                }
                break;
            }
        }
    }
    
    public void enableAutoRestartDevice(final boolean b) {
        TXCAudioEngineJNI.nativeEnableAutoRestartDevice(b);
    }
    
    public void setMaxSelectedPlayStreams(final int n) {
        TXCAudioEngineJNI.nativeSetMaxSelectedPlayStreams(n);
    }
    
    static {
        TXCAudioEngine.sInstance = new TXCAudioEngine();
        TXCAudioEngine.mContext = null;
        TXCAudioEngine.has_trae = false;
        TXCAudioEngine.mAudioCoreDataListener = null;
        TXCAudioEngine.mJitterDataListenerMap = new HashMap<String, WeakReference<c>>();
        TXCAudioEngine.mJitterEventListenerMap = new HashMap<String, WeakReference<b>>();
        TXCAudioEngine.has_init = false;
    }
}
